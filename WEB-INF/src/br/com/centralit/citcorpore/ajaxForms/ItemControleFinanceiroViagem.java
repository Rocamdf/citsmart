package br.com.centralit.citcorpore.ajaxForms;

import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opera.core.systems.scope.protos.UmsProtos.Response;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.ItemControleFinanceiroViagemDTO;
import br.com.centralit.citcorpore.bean.RequisicaoViagemDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.TipoMovimFinanceiraViagemDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.TipoMovimFinanceiraViagemDao;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.FormaPagamentoService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.negocio.ItemControleFinanceiroViagemService;
import br.com.centralit.citcorpore.negocio.JustificativaSolicitacaoService;
import br.com.centralit.citcorpore.negocio.RequisicaoViagemService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.TipoMovimFinanceiraViagemService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({"rawtypes","unchecked", "unused"})
public class ItemControleFinanceiroViagem extends  AjaxFormAction{
	
	private UsuarioDTO usuario;

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ItemControleFinanceiroViagemDTO itemControleViagemDto = (ItemControleFinanceiroViagemDTO) document.getBean();
		this.recuperaInformacoesIntegrante(document, request, response, itemControleViagemDto);
		
		String origemCompras = request.getParameter("origemCompras");
		
		document.getSelectById("idTipoMovimFinanceiraViagem").setDisabled(true);
		document.getElementById("div_assento").setVisible(false);
		document.getElementById("div_localizador").setVisible(false);
		document.getElementById("div_tipoPassagem").setVisible(false);
		document.getElementById("quantidade").setReadonly(true);
		document.getElementById("divTabela").setVisible(false);
		
		usuario = br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
		
		this.comboTipoMovimentacaoFinanceira(document, request, response);
		this.comboFormaPagamento(document, request, response);
		this.comboJustificativaSolicitacao(document, request, response);
		this.comboClassificacao(document, request, response);
		this.comboFornecedor(document, request, response);
		if(itemControleViagemDto.getIdItemControleFinanceiroViagem() != null){
			this.restore(document, request, response, itemControleViagemDto);
		}
		
		RequisicaoViagemDTO requisicaoViagemDto = this.recuperaRequisicaoViagem(itemControleViagemDto.getIdSolicitacaoServico());
		if(requisicaoViagemDto != null){
			document.getElementById("dataInicioViagem").setValue(requisicaoViagemDto.getDataInicioViagem().toString());
		}
		
		document.executeScript("$('#nomeAdiantamento').hide()");
		
		this.atualizaGridItensControle(document, request, response, itemControleViagemDto);
		if(origemCompras!= null && origemCompras.equalsIgnoreCase("S")){
			document.getSelectById("classificacao").setDisabled(true);
			document.getSelectById("idTipoMovimFinanceiraViagem").setDisabled(true);
			document.getSelectById("idJustificativa").setDisabled(true);
			document.getSelectById("idFornecedor").setDisabled(true);
			document.getSelectById("idFormaPagamento").setDisabled(true);
			document.getElementById("dataCotacao").setDisabled(true);
			document.getElementById("horaCotacao").setDisabled(true);
			document.getElementById("valorUnitario").setDisabled(true);
			document.getElementById("valorAdiantamento").setDisabled(true);
			document.getElementById("quantidade").setDisabled(true);
			document.getElementById("divTabela").setVisible(false);
			document.getElementById("origemCompras").setValue("S");
		}
	}

	@Override
	public Class getBeanClass() {		
		return ItemControleFinanceiroViagemDTO.class;
	}
	
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		usuario = br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
		ItemControleFinanceiroViagemDTO itemControleViagemDto = (ItemControleFinanceiroViagemDTO) document.getBean();
		ItemControleFinanceiroViagemService itemControleService = (ItemControleFinanceiroViagemService) ServiceLocator.getInstance().getService(ItemControleFinanceiroViagemService.class, WebUtil.getUsuarioSistema(request));
		
		document.getElementById("valorUnitario").setReadonly(false);
		String teste = request.getParameter("valorUnitario");
		
		itemControleViagemDto.setDataHoraPrazoCotacao(UtilDatas.strToTimestamp(itemControleViagemDto.getDataCotacao() + " " + itemControleViagemDto.getHoraCotacao() + ":00"));
		itemControleViagemDto.setPrazoCotacao(UtilDatas.strToSQLDate(itemControleViagemDto.getDataCotacao()));
		
		if(!validaPrazoCotacao(itemControleViagemDto)){
			document.alert("Prazo de Cotação Nao deve ser superior ao inicio da viagem");
			return;
		}
		
		if (itemControleViagemDto.getIdItemControleFinanceiroViagem()==null|| itemControleViagemDto.getIdItemControleFinanceiroViagem()== 0) {
			
			
			if (itemControleService.consultarItemAtivo(itemControleViagemDto)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}
			itemControleService.create(itemControleViagemDto);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));

		} else {
			if (itemControleService.consultarItemAtivo(itemControleViagemDto)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}
			itemControleService.update(itemControleViagemDto);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}

		HTMLForm form = document.getForm("form");
		form.clear();
		this.recuperaInformacoesIntegrante(document, request, response, itemControleViagemDto);
		if(itemControleViagemDto.getOrigemCompras() == null || !itemControleViagemDto.getOrigemCompras().equalsIgnoreCase("S")){
			this.atualizaGridItensControle(document, request, response, itemControleViagemDto);
		}else{
			document.executeScript("fecharPopup();"); 
		}
	}

	/**
	 *
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ItemControleFinanceiroViagemDTO itemControleFinanceiroViagemDto = (ItemControleFinanceiroViagemDTO) document.getBean();
		ItemControleFinanceiroViagemService itemControleFinanceiroViagemService = (ItemControleFinanceiroViagemService) ServiceLocator.getInstance()
				.getService(ItemControleFinanceiroViagemService.class, WebUtil.getUsuarioSistema(request));

		if (itemControleFinanceiroViagemDto.getIdItemControleFinanceiroViagem() > 0) {
			itemControleFinanceiroViagemService.deletarItem(itemControleFinanceiroViagemDto, document);
		}

		HTMLForm form = document.getForm("form");
		form.clear();
	}

	/**
	 * Metodo para restaura os campos.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, ItemControleFinanceiroViagemDTO itemControleFinanceiroViagemDto) throws Exception {		
		ItemControleFinanceiroViagemService itemService = (ItemControleFinanceiroViagemService) ServiceLocator.getInstance().getService(ItemControleFinanceiroViagemService.class, null);
		
		itemControleFinanceiroViagemDto = (ItemControleFinanceiroViagemDTO) itemService.restore(itemControleFinanceiroViagemDto);
		if(itemControleFinanceiroViagemDto != null){
			
			String classificacao = this.recuperaClassificacao(itemControleFinanceiroViagemDto);
			if(classificacao != null && classificacao.equalsIgnoreCase(Enumerados.ClassificacaoMovFinViagem.Passagem.toString())){
					document.getElementById("div_assento").setVisible(true);
					document.getElementById("div_tipoPassagem").setVisible(true);
					document.getElementById("div_localizador").setVisible(true);
					
			}
			itemControleFinanceiroViagemDto.setDataCotacao(UtilDatas.dateToSTR(itemControleFinanceiroViagemDto.getPrazoCotacao()));
			if(itemControleFinanceiroViagemDto.getDataHoraPrazoCotacao() != null)
				itemControleFinanceiroViagemDto.setHoraCotacao(UtilDatas.getHoraHHMM(itemControleFinanceiroViagemDto.getDataHoraPrazoCotacao()));
			itemControleFinanceiroViagemDto.setClassificacao(classificacao);
			HTMLForm form = document.getForm("form");
			form.clear();
			form.setValues(itemControleFinanceiroViagemDto);
			this.recuperaInformacoesIntegrante(document, request, response, itemControleFinanceiroViagemDto);
		}
	}
	/**
	 * @author geber.costa
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * 
	 * Traz os valores da tabela de tipoMovimFinanceiraViagem, baseado no tipo de classificação que o usuario escolher. Caso o usuário ainda não tenha escolhido a 'classificação' então
	 * esse campo tras todos os valores disponíveis porém fica desabilitado.
	 * Se o usuário selecionar alguma classificação essa combo fica disponível, caso o usuário escolha na classificação a opção 'Todos' então essa combo novamente fica desabilitada
	 */
	public void comboTipoMovimentacaoFinanceira(DocumentHTML document,HttpServletRequest request, HttpServletResponse response)throws Exception{
		
		document.getSelectById("idTipoMovimFinanceiraViagem").removeAllOptions();
		TipoMovimFinanceiraViagemService tipoMovService = (TipoMovimFinanceiraViagemService) ServiceLocator.getInstance().getService(TipoMovimFinanceiraViagemService.class, null);
		ArrayList<TipoMovimFinanceiraViagemDTO>colTipoMovimentacaoFinanceira = (ArrayList<TipoMovimFinanceiraViagemDTO>) tipoMovService.recuperaTipoAtivos();
		document.getSelectById("idTipoMovimFinanceiraViagem").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
        
		if(colTipoMovimentacaoFinanceira!=null){
			document.getSelectById("idTipoMovimFinanceiraViagem").addOptions(colTipoMovimentacaoFinanceira, "idtipoMovimFinanceiraViagem", "nome", null);
			/*for(TipoMovimFinanceiraViagemDTO listaMovimFinanceira : colTipoMovimentacaoFinanceira){
				document.getSelectById("idTipoMovimFinanceiraViagem").addOption( listaMovimFinanceira.getIdtipoMovimFinanceiraViagem().toString(),listaMovimFinanceira.getDescricao());
			}*/
		}
		document.getSelectById("idTipoMovimFinanceiraViagem").setDisabled(true);
	}
	
	/**
	 * @author geber.costa
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exceptio	
	 *  
	 *  Traz as formas de pagamento da tabela formapagamento
	 *  */
	public void comboFormaPagamento(DocumentHTML document,HttpServletRequest request, HttpServletResponse response)throws Exception{
		
		document.getSelectById("idFormaPagamento").removeAllOptions();
		FormaPagamentoService formaPagamentoService = (FormaPagamentoService) ServiceLocator.getInstance().getService(FormaPagamentoService.class, null);
		Collection colTipoMovimentacaoFinanceira = formaPagamentoService.list();
		document.getSelectById("idFormaPagamento").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		
		if(colTipoMovimentacaoFinanceira!=null){
			document.getSelectById("idFormaPagamento").addOptions(colTipoMovimentacaoFinanceira, "idFormaPagamento", "nomeFormaPagamento", null);
		}
	}
	
	/**
	 * @author geber.costa
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * 
	 * Traz os valores disponiveis da tabela justificativasolicitacao
	 */
	public void comboJustificativaSolicitacao(DocumentHTML document,HttpServletRequest request, HttpServletResponse response)throws Exception{
		
		document.getSelectById("idJustificativa").removeAllOptions();
		JustificativaSolicitacaoService justificativaSolicitacaoService = (JustificativaSolicitacaoService) ServiceLocator.getInstance().getService(JustificativaSolicitacaoService.class, null);
		Collection colJustificativa = justificativaSolicitacaoService.listAtivasParaViagem();
		document.getSelectById("idJustificativa").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		
		if(colJustificativa!=null){
			document.getSelectById("idJustificativa").addOptions(colJustificativa, "idJustificativa", "descricaoJustificativa", null);
		}
	}
	
	/**
	 * @author geber.costa
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void comboClassificacao(DocumentHTML document,HttpServletRequest request, HttpServletResponse response)throws Exception{
		
		document.getSelectById("classificacao").removeAllOptions();
		document.getSelectById("classificacao").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		for(Enumerados.ClassificacaoMovFinViagem lista : Enumerados.ClassificacaoMovFinViagem.values()){
			document.getSelectById("classificacao").addOption( lista.getDescricao(),lista.getDescricao());
		}
	}	
	
	 /**
	  * @author geber.costa
	  * @param document
	  * @param request
	  * @param response
	  * @throws Exception
	  */
	public void comboFornecedor(DocumentHTML document,HttpServletRequest request, HttpServletResponse response)throws Exception{
		
		document.getSelectById("idFornecedor").removeAllOptions();
		FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
		Collection listaFornecedores =  fornecedorService.list();
		if(listaFornecedores!=null){
			document.getSelectById("idFornecedor").addOption("", "" + UtilI18N.internacionaliza(request, "citcorpore.comum.selecione") + "");
			document.getSelectById("idFornecedor").addOptions(listaFornecedores, "idFornecedor", "nomeFantasia", null);
		}
	}	
	
	/**
	 * @author geber.costa
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * 
	 * Trata os inputs de acordo com a seguinte regra: Se a classificação for diferente de 'Passagem' os inputs de 'assento', 'tipo da passagem' e 'localizador' não estarão
	 * visíveis ao usuário, Se o tipo for da classificação for 'Diária'  o campo de 'valor unitário' vem com o seu respectivo valor e não pode ser editado.
	 *  Se a classificação não for do tipo 'Diária' o campo de 'valor unitário vem setado porém pode ser alterado.
	 * 
	 */
	public void tratarTipoMovimentacaoFinanceira(DocumentHTML document, HttpServletRequest request, HttpServletResponse response)throws Exception{
		
		ItemControleFinanceiroViagemDTO item  = (ItemControleFinanceiroViagemDTO) document.getBean();
		String classificacao = item.getClassificacao();
		classificacao = UtilStrings.removeCaracteresEspeciais(classificacao);
		TipoMovimFinanceiraViagemService tipoService = (TipoMovimFinanceiraViagemService) ServiceLocator.getInstance().getService(TipoMovimFinanceiraViagemService.class, null);
		List<TipoMovimFinanceiraViagemDTO> listaTipoMovimentacaoFinanceiraViagem = new ArrayList<TipoMovimFinanceiraViagemDTO>();
		
		listaTipoMovimentacaoFinanceiraViagem =  tipoService.listByClassificacao(classificacao);
		if (listaTipoMovimentacaoFinanceiraViagem != null) {
			document.getSelectById("idTipoMovimFinanceiraViagem").removeAllOptions();
			document.getSelectById("idTipoMovimFinanceiraViagem").addOption("", "" + UtilI18N.internacionaliza(request, "citcorpore.comum.selecione") + "");
			for (TipoMovimFinanceiraViagemDTO tipoMov : listaTipoMovimentacaoFinanceiraViagem) {
				document.getSelectById("idTipoMovimFinanceiraViagem").addOption(tipoMov.getIdtipoMovimFinanceiraViagem().toString(),tipoMov.getNome());
			}
		}
		
		this.tratarQuantidadePorClassificacao(document,classificacao, Enumerados.ClassificacaoMovFinViagem.Diaria.toString(),request);			
		
		if(classificacao.equalsIgnoreCase(Enumerados.ClassificacaoMovFinViagem.Passagem.toString())){
			document.getSelectById("div_assento").setVisible(true);
			document.getSelectById("div_tipoPassagem").setVisible(true);
			document.getSelectById("div_localizador").setVisible(true);
			document.getSelectById("valorUnitario").setReadonly(false);
			document.getSelectById("adiantamento").setReadonly(false);
			
		}else if(classificacao.equalsIgnoreCase(Enumerados.ClassificacaoMovFinViagem.Diaria.toString())){
			document.getSelectById("div_assento").setVisible(false);
			document.getSelectById("div_tipoPassagem").setVisible(false);
			document.getSelectById("div_localizador").setVisible(false);
			document.getSelectById("valorUnitario").setReadonly(true);
			document.getSelectById("adiantamento").setReadonly(true);

		}else{
			document.getSelectById("div_assento").setVisible(false);
			document.getSelectById("div_tipoPassagem").setVisible(false);
			document.getSelectById("div_localizador").setVisible(false);
			document.getSelectById("valorUnitario").setReadonly(false);
			document.getSelectById("adiantamento").setReadonly(false);
		}
		if(classificacao.equalsIgnoreCase("")){
			document.getSelectById("idTipoMovimFinanceiraViagem").setDisabled(true);
		}else{
			document.getSelectById("idTipoMovimFinanceiraViagem").setDisabled(false);
		}
		
	}

	public void atualizaGridItensControle(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, ItemControleFinanceiroViagemDTO itemControleFinanceiroViagemDto) throws ServiceException, Exception{
		ItemControleFinanceiroViagemService itemService = (ItemControleFinanceiroViagemService) ServiceLocator.getInstance().getService(ItemControleFinanceiroViagemService.class, null);
		Collection<ItemControleFinanceiroViagemDTO> colItens = itemService.recuperaItensControleFinanceiro(itemControleFinanceiroViagemDto);
		
		if(colItens != null){
			for(ItemControleFinanceiroViagemDTO item : colItens){
				item.setDataCotacao(UtilDatas.dateToSTR(item.getPrazoCotacao()));
				if(item.getDataHoraPrazoCotacao() != null)
					item.setHoraCotacao(UtilDatas.getHoraHHMM(item.getDataHoraPrazoCotacao()));
			}
			HTMLTable tblItemControleFinaceiro;
			tblItemControleFinaceiro = document.getTableById("tblItemControleFinaceiro");
			tblItemControleFinaceiro.deleteAllRows();
			tblItemControleFinaceiro.addRowsByCollection(colItens, new String[] {"","nomeTipoMovimFinanceira", "nomeFornecedor","valorAdiantamento",""}, null, null, new String[] {"gerarImgAlterar","gerarImgDeletar"}, "editarItem", null);
			document.getElementById("divTabela").setVisible(true);
		}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
		ItemControleFinanceiroViagemDTO itemControle = (ItemControleFinanceiroViagemDTO) document.getBean();
		
 	}
	
	/**
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 * @author geber.costa
	 * 
	 * Faz o tratamento do tipo da movimentação financeira.
	 * Se a Classificação for igual a diária então o adiantamento = valorUnitário * (quantidade + 1), se a classificação for qualquer outro diferente
	 * então adiantamento = valorUnitário * diária
	 * O tratamento para o adiantamento é feito , ele calcula e seta o valor na tela automaticamente.
	 * Esse método também faz o tratamento para casas decimais e 
	 * 
	 */
	public void tratarValores(DocumentHTML document, HttpServletRequest request, HttpServletResponse response)throws ServiceException,Exception{
		
		ItemControleFinanceiroViagemDTO itemControleFinanceiroViagemDto = (ItemControleFinanceiroViagemDTO) document.getBean();
		Integer movimentacao = itemControleFinanceiroViagemDto.getIdTipoMovimFinanceiraViagem();
		String classificacaoPeloDto = itemControleFinanceiroViagemDto.getClassificacao();
		classificacaoPeloDto = UtilStrings.removeCaracteresEspeciais(classificacaoPeloDto);
		
		TipoMovimFinanceiraViagemService tipoMovimService = (TipoMovimFinanceiraViagemService) ServiceLocator.getInstance().getService(TipoMovimFinanceiraViagemService.class, null);
		TipoMovimFinanceiraViagemDTO tipoMovimFinanceiraDto = new TipoMovimFinanceiraViagemDTO();
		tipoMovimFinanceiraDto.setIdtipoMovimFinanceiraViagem(itemControleFinanceiroViagemDto.getIdTipoMovimFinanceiraViagem());
		tipoMovimFinanceiraDto = (TipoMovimFinanceiraViagemDTO) tipoMovimService.restore(tipoMovimFinanceiraDto);
				
		
		if(tipoMovimFinanceiraDto != null && tipoMovimFinanceiraDto.getPermiteAdiantamento() != null && tipoMovimFinanceiraDto.getPermiteAdiantamento().equalsIgnoreCase("S")){
			document.executeScript("$('#nomeValorTotal').hide()");
			document.executeScript("$('#nomeAdiantamento').show()");
		}else{
			document.executeScript("$('#nomeAdiantamento').hide()");
			document.executeScript("$('#nomeValorTotal').show()");
		}
		
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		DecimalFormat decimalFormat = (DecimalFormat)nf;
		decimalFormat.applyPattern("#,##0.00");
		
			if(tipoMovimFinanceiraDto!=null){
				Double valorPadrao = tipoMovimFinanceiraDto.getValorPadrao() != null ? tipoMovimFinanceiraDto.getValorPadrao() : 0.0;
				String valorUnit = decimalFormat.format(valorPadrao);
				itemControleFinanceiroViagemDto.setValorUnitario(valorPadrao);
				document.getSelectById("valorUnitario").setValue(valorUnit);
			}
			
			if(classificacaoPeloDto.equalsIgnoreCase(Enumerados.ClassificacaoMovFinViagem.Diaria.toString())){
				tratarQuantidadePorClassificacao(document, classificacaoPeloDto, Enumerados.ClassificacaoMovFinViagem.Diaria.toString(), request);
				itemControleFinanceiroViagemDto.setQuantidade(Double.parseDouble(document.getElementById("quantidade").getValue()));
				itemControleFinanceiroViagemDto.setAdiantamento((itemControleFinanceiroViagemDto.getQuantidade()+1) * itemControleFinanceiroViagemDto.getValorUnitario());
			}
			else{
				itemControleFinanceiroViagemDto.setAdiantamento(itemControleFinanceiroViagemDto.getQuantidade() * itemControleFinanceiroViagemDto.getValorUnitario());
			}
			
			String adiantamentoFormatado  = decimalFormat.format(itemControleFinanceiroViagemDto.getAdiantamento());
			document.getSelectById("valorAdiantamento").setValue(adiantamentoFormatado);
	}
	

	/**
	 * 
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 * @author geber.costa
	 * 
	 * Método utilizado quando o usuário pode alterar o valor unitário, quando ele tirar o foco do campo automaticamente o javascript chama esse método
	 * que joga automaticamente o valor do adiantamento já calculado
	 */
	
	public void validarAdiantamento(DocumentHTML document, HttpServletRequest request, HttpServletResponse response)throws ServiceException,Exception{
		ItemControleFinanceiroViagemDTO itemControle = (ItemControleFinanceiroViagemDTO) document.getBean();
		String classificacaoPeloDto = itemControle.getClassificacao();
		classificacaoPeloDto = UtilStrings.removeCaracteresEspeciais(classificacaoPeloDto);
		
		if(itemControle.getValorUnitario()!=null){

				if(itemControle.getQuantidade()!=null){
					if(classificacaoPeloDto.equalsIgnoreCase(UtilStrings.removeCaracteresEspeciais(Enumerados.ClassificacaoMovFinViagem.Diaria.toString()))){
					
					ItemControleFinanceiroViagemDTO itemControleFinanceiroViagemDTO = (ItemControleFinanceiroViagemDTO) document.getBean();
					RequisicaoViagemDTO requisicaoViagemDto = new RequisicaoViagemDTO();
					RequisicaoViagemService requisicaoService =  (RequisicaoViagemService) ServiceLocator.getInstance().getService(RequisicaoViagemService.class, null);
					
					if(itemControleFinanceiroViagemDTO.getIdSolicitacaoServico()!=null){
						requisicaoViagemDto.setIdSolicitacaoServico(itemControleFinanceiroViagemDTO.getIdSolicitacaoServico());
						requisicaoViagemDto = (RequisicaoViagemDTO) requisicaoService.restore(requisicaoViagemDto);
					}
					
					document.getElementById("quantidade").setValue(requisicaoViagemDto.getQtdeDias().toString());
				}
				
				else{
					document.getElementById("quantidade").setValue(String.valueOf(itemControle.getQuantidade().intValue()));
				}
				itemControle.setAdiantamento(itemControle.getValorUnitario() * (itemControle.getQuantidade().intValue()));
				
				
				NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
				DecimalFormat decimal = (DecimalFormat)nf;
				decimal.applyPattern("#,##0.00");
				
				document.getElementById("quantidade").setReadonly(false);
				document.getElementById("valorAdiantamento").setValue(decimal.format(itemControle.getAdiantamento()));
			}else{
				document.getElementById("quantidade").setReadonly(true);
			}
		}
	}
	
	/**
	 * @author geber.costa
	 * @param classificacaoSelecionada
	 * @param classificacaoEnum
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 * 
	 * Se a classificação for do tipo Diária então o campo quantidade não será editavel, se o tipo for qualquer outro o campo quantidade será editável;
	 */
	public void tratarQuantidadePorClassificacao(DocumentHTML document,String classificacaoSelecionada, String classificacaoEnum,HttpServletRequest request)throws ServiceException,Exception{
		
		if(!classificacaoSelecionada.equalsIgnoreCase(classificacaoEnum)){
			document.getElementById("quantidade").setReadonly(false);
			document.getElementById("quantidade").setValue("1");
		}else{
			
			ItemControleFinanceiroViagemDTO itemControleFinanceiroViagemDTO = (ItemControleFinanceiroViagemDTO) document.getBean();
			RequisicaoViagemDTO requisicaoViagemDto = new RequisicaoViagemDTO();
			RequisicaoViagemService requisicaoService =  (RequisicaoViagemService) ServiceLocator.getInstance().getService(RequisicaoViagemService.class, null);
			if(itemControleFinanceiroViagemDTO.getIdSolicitacaoServico()!=null){
				requisicaoViagemDto.setIdSolicitacaoServico(itemControleFinanceiroViagemDTO.getIdSolicitacaoServico());
				requisicaoViagemDto = (RequisicaoViagemDTO) requisicaoService.restore(requisicaoViagemDto);
			}
			document.getElementById("quantidade").setValue(requisicaoViagemDto.getQtdeDias().toString());
			document.getElementById("quantidade").setReadonly(true);
		}
	}
	
	private void recuperaInformacoesIntegrante(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, ItemControleFinanceiroViagemDTO itemControleViagemDto) throws ServiceException, Exception{
		EmpregadoService empregadoService = (EmpregadoService)ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		EmpregadoDTO empregadoDto = new EmpregadoDTO();
		if(itemControleViagemDto.getIdEmpregado() != null){
			empregadoDto.setIdEmpregado(itemControleViagemDto.getIdEmpregado());
			empregadoDto = (EmpregadoDTO) empregadoService.restore(empregadoDto);
			document.getElementById("nomeIntegrante").setValue(empregadoDto.getNome());
			document.getElementById("nomeIntegrante").setDisabled(true);
			document.getElementById("idEmpregado").setValue(itemControleViagemDto.getIdEmpregado().toString());
			document.getElementById("idSolicitacaoServico").setValue(itemControleViagemDto.getIdSolicitacaoServico().toString());
			
		}
	}
	/**
	 * @author geber.costa
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 * 
	 * Pega o id do item, seta no objeto , recupera ele, e seta dataFim.
	 */
	public void setarDataFimItem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception{
		
		Integer idItemSelecionado = Integer.parseInt(request.getParameter("idItemSelecionado"));
		if(idItemSelecionado!=null){
			ItemControleFinanceiroViagemDTO itemControleDto = (ItemControleFinanceiroViagemDTO) document.getBean();
			itemControleDto.setIdItemControleFinanceiroViagem(idItemSelecionado);
			ItemControleFinanceiroViagemService itemControleService = (ItemControleFinanceiroViagemService) ServiceLocator.getInstance().getService(ItemControleFinanceiroViagemService.class, null);
			itemControleDto = (ItemControleFinanceiroViagemDTO) itemControleService.restore(itemControleDto);
			if(itemControleDto!=null){
				itemControleDto.setDataFim(UtilDatas.getDataAtual());
				itemControleService.update(itemControleDto);	
			}
		}
	}
	
	private RequisicaoViagemDTO recuperaRequisicaoViagem(Integer idSolicitacao) throws ServiceException, Exception{
		RequisicaoViagemService requisicaoViagemService = (RequisicaoViagemService) ServiceLocator.getInstance().getService(RequisicaoViagemService.class, null);
		RequisicaoViagemDTO requisicaoViagemDto = new RequisicaoViagemDTO();
		requisicaoViagemDto.setIdSolicitacaoServico(idSolicitacao);
		requisicaoViagemDto = (RequisicaoViagemDTO) requisicaoViagemService.restore(requisicaoViagemDto);
		return requisicaoViagemDto;
	}
	
	private boolean validaPrazoCotacao(ItemControleFinanceiroViagemDTO itemDto) throws ServiceException, Exception{
		RequisicaoViagemDTO requisicaoViagemDto = this.recuperaRequisicaoViagem(itemDto.getIdSolicitacaoServico());
		if(requisicaoViagemDto != null){
			Date dtInicioViagem = requisicaoViagemDto.getDataInicioViagem();
			Date dtPrazoCotacao = itemDto.getPrazoCotacao();
			if(dtInicioViagem != null && dtPrazoCotacao != null){
				if (dtPrazoCotacao.compareTo(dtInicioViagem) > 0) {
					return false;
				} else {
					return true;
				}
			}
		}
		return true;
	}
	
	private String recuperaClassificacao(ItemControleFinanceiroViagemDTO itemDto) throws Exception{
		TipoMovimFinanceiraViagemDTO tipoDto = new TipoMovimFinanceiraViagemDTO();
		TipoMovimFinanceiraViagemDao tipoDao = new TipoMovimFinanceiraViagemDao();
		
		tipoDto.setIdtipoMovimFinanceiraViagem(itemDto.getIdTipoMovimFinanceiraViagem());
		tipoDto = (TipoMovimFinanceiraViagemDTO) tipoDao.restore(tipoDto);
		
		if(tipoDto != null){
			return tipoDto.getClassificacao();
		}
		return null;
	}
}
