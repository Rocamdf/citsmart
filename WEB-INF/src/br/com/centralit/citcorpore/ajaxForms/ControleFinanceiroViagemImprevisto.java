package br.com.centralit.citcorpore.ajaxForms;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.bean.ControleFinanceiroViagemDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.IntegranteViagemDTO;
import br.com.centralit.citcorpore.bean.ItemControleFinanceiroViagemDTO;
import br.com.centralit.citcorpore.bean.RequisicaoViagemDTO;
import br.com.centralit.citcorpore.bean.TipoMovimFinanceiraViagemDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.FornecedorDao;
import br.com.centralit.citcorpore.negocio.ControleFinanceiroViagemImprevistoService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.FormaPagamentoService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.negocio.ItemControleFinanceiroViagemService;
import br.com.centralit.citcorpore.negocio.JustificativaSolicitacaoService;
import br.com.centralit.citcorpore.negocio.RequisicaoViagemService;
import br.com.centralit.citcorpore.negocio.TipoMovimFinanceiraViagemService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;


@SuppressWarnings({"rawtypes","unchecked"})
public class ControleFinanceiroViagemImprevisto extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if(!WebUtil.validarSeUsuarioEstaNaSessao(request, document))
			return;
		
		@SuppressWarnings("unused")
		ControleFinanceiroViagemDTO controleFinanceiroViagemDto = (ControleFinanceiroViagemDTO) document.getBean();
		
		document.executeScript("$('#div_integrantes').hide()");
		
        document.getSelectById("idTipoMovimFinanceiraViagem").setDisabled(true);
		document.getElementById("div_assento").setVisible(false);
		document.getElementById("div_localizador").setVisible(false);
		document.getElementById("div_tipoPassagem").setVisible(false);
		document.getElementById("quantidade").setReadonly(true);
		
		this.comboTipoMovimentacaoFinanceira(document, request, response);
		this.comboFormaPagamento(document, request, response);
		this.comboJustificativaSolicitacao(document, request, response);
		this.comboClassificacao(document, request, response);
		this.comboFornecedor(document, request, response);
        
	}

	@Override
	public Class getBeanClass() {
		return ControleFinanceiroViagemDTO.class;
	}
	
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception{
		
		if(!WebUtil.validarSeUsuarioEstaNaSessao(request, document))
			return;
		
		ControleFinanceiroViagemDTO controleFinanceiroViagemDto = (ControleFinanceiroViagemDTO) document.getBean();
		ControleFinanceiroViagemImprevistoService controleService = (ControleFinanceiroViagemImprevistoService) ServiceLocator.getInstance().getService(ControleFinanceiroViagemImprevistoService.class, null);
		
		
		if(controleFinanceiroViagemDto != null){
			if(controleFinanceiroViagemDto.getItemSerialiados() != null){
				controleFinanceiroViagemDto.setColItens(br.com.citframework.util.WebUtil.deserializeCollectionFromString(ItemControleFinanceiroViagemDTO.class, controleFinanceiroViagemDto.getItemSerialiados()));
			}
			controleFinanceiroViagemDto.setIdResponsavel(WebUtil.getUsuario(request).getIdEmpregado());
		}
		
		if(controleFinanceiroViagemDto != null && controleFinanceiroViagemDto.getIdControleFinanceiroViagem() == null){
			controleFinanceiroViagemDto = (ControleFinanceiroViagemDTO) controleService.createAll(controleFinanceiroViagemDto); 
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		}
		
		document.executeScript("limparFormularioPrincipal()");
		
	}
	
	
	/**
	 * Preenche tabela 'Requisições Viagem' da página Controle Financeiro Viagem Imprevisto.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author ronnie.lopes
	 */
	public void pesquisaRequisicoesViagem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	        ControleFinanceiroViagemDTO controleFinanceiroViagemDto = (ControleFinanceiroViagemDTO) document.getBean();
	        Integer itensPorPagina = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.QUANT_RETORNO_PESQUISA, "5"));
	       
	        UsuarioDTO usuario = WebUtil.getUsuario(request);
	        if (usuario == null) {
	            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
	            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
	            return;
	        }
	        
	        HTMLTable tblRequisicoesViagem = document.getTableById("tblRequisicoesViagem");
	        tblRequisicoesViagem.deleteAllRows();
	        
	        controleFinanceiroViagemDto.setIdSolicitacaoServico(controleFinanceiroViagemDto.getIdSolicitacao());
	        
	        ControleFinanceiroViagemImprevistoService controleFinanceiroViagemImprevistoService = (ControleFinanceiroViagemImprevistoService) ServiceLocator.getInstance().getService(ControleFinanceiroViagemImprevistoService.class, WebUtil.getUsuarioSistema(request));
	        
	        Integer totalPaginas = controleFinanceiroViagemImprevistoService.calculaTotalPaginas(itensPorPagina, controleFinanceiroViagemDto);
	        Integer paginaSelecionada = controleFinanceiroViagemDto.getPaginaSelecionada();
	        if (paginaSelecionada == null) {
	        	paginaSelecionada = 1;
	        }
	        paginacaoGerenciamento(totalPaginas,paginaSelecionada,request, document);
	        Collection<RequisicaoViagemDTO> requisicoesViagem = controleFinanceiroViagemImprevistoService.recuperaRequisicoesViagem(controleFinanceiroViagemDto, paginaSelecionada, itensPorPagina);
	       
	        if (requisicoesViagem != null && !requisicoesViagem.isEmpty()) {
	        	tblRequisicoesViagem.addRowsByCollection(requisicoesViagem, new String[]{"","idSolicitacaoServico","dataInicio","dataFim","descricaoMotivo"}, null, null, new String[] {"gerarImgTblRequisicao"}, "gerarTblIntegrantes", null);  
	        }else{
	        	document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.resultado"));
	        }
	}
	
	public void paginacaoGerenciamento(Integer totalPaginas, Integer paginaSelecionada, HttpServletRequest request, DocumentHTML document) throws Exception {
		HTMLElement divPrincipal = document.getElementById("paginas");
		StringBuffer sb = new StringBuffer();
		final Integer adjacentes = 2;
		if (paginaSelecionada == null)
			paginaSelecionada = 1;
		sb.append(" <div id='itenPaginacaoGerenciamento' class='pagination pagination-right margin-none' > ");
		sb.append(" <ul>");
		sb.append(" <li " + (paginaSelecionada == 1 ? "class='disabled'" : "value='1' onclick='paginarItens(this.value);'") + " ><font style='background-color: #E6ECEF;  background-position: 100px; border-color: #E6ECEF; border: #B6B6B6; border-width: 1px; border-style: solid;'><a href='#'>"+UtilI18N.internacionaliza(request, "citcorpore.comum.primeiro")+"</a></li></font>");
		sb.append(" <li " + ((totalPaginas == 1 || paginaSelecionada == 1) ? "class='disabled'" : "value='"+(paginaSelecionada-1)+"' onclick='paginarItens(this.value);'") + "><font style='background-color: #E6ECEF; border-color: #E6ECEF; border: #B6B6B6; border-width: 1px; border-style: solid;'><a href='#'>"+UtilI18N.internacionaliza(request, "citcorpore.comum.anterior")+"</a></li></font>");
		if(totalPaginas <= 5) {
			for (int i = 1; i <= totalPaginas; i++) {
				if (i == paginaSelecionada) {
					sb.append("<li id='"+i+"' value='"+i+"' onclick='paginarItens(this.value);'><font style='background-color: #E6ECEF;  background-position: 100px; border-color: #E6ECEF; border: #B6B6B6; border-width: 1px; border-style: solid;'><a href='#'>"+i+"</a></li></font>");
				} else {
					sb.append("<li id='"+i+"' value='"+i+"' onclick='paginarItens(this.value);'><font style='background-color: #E6ECEF;  background-position: 100px; border-color: #E6ECEF; border: #B6B6B6; border-width: 1px; border-style: solid;'><a href='#'>"+i+"</a></li></font> " );
				}
			}
		} else {
			if (totalPaginas > 5) {
				if (paginaSelecionada < 1 + (2 * adjacentes)) {
					for (int i=1; i< 2 + (2 * adjacentes); i++) {
						if (i == paginaSelecionada) {
							sb.append("<li id='"+i+"' value='"+i+"' onclick='paginarItens(this.value);'><font style='background-color: #E6ECEF;  background-position: 100px; border-color: #E6ECEF; border: #B6B6B6; border-width: 1px; border-style: solid;'><a href='#'>"+i+"</a></li></font> ");
						} else {
							sb.append("<li id='"+i+"' value='"+i+"' onclick='paginarItens(this.value);'><font style='background-color: #E6ECEF;  background-position: 100px; border-color: #E6ECEF; border: #B6B6B6; border-width: 1px; border-style: solid;'><a href='#'>"+i+"</a></li></font> " );
						}
					}
				} else if (paginaSelecionada > (2 * adjacentes) && paginaSelecionada < totalPaginas - 3) {
					for (int i = paginaSelecionada-adjacentes; i<= paginaSelecionada + adjacentes; i++) {
						if (i == paginaSelecionada) {
							sb.append("<li id='"+i+"' value='"+i+"' onclick='paginarItens(this.value);'><font style='background-color: #E6ECEF;  background-position: 100px; border-color: #E6ECEF; border: #B6B6B6; border-width: 1px; border-style: solid;'><a href='#'>"+i+"</a></li></font> ");
						} else {
							sb.append("<li id='"+i+"' value='"+i+"' onclick='paginarItens(this.value);'><font style='background-color: #E6ECEF;  background-position: 100px; border-color: #E6ECEF; border: #B6B6B6; border-width: 1px; border-style: solid;'><a href='#'>"+i+"</a></li></font> ");
						}
					}
				} else {
					for (int i = totalPaginas - (0 + (2 * adjacentes)); i <= totalPaginas; i++) {
						if (i == paginaSelecionada) {
							sb.append("<li id='"+i+"' value='"+i+"' onclick='paginarItens(this.value);'><font style='background-color: #E6ECEF;  background-position: 100px; border-color: #E6ECEF; border: #B6B6B6; border-width: 1px; border-style: solid;'><a href='#'>"+i+"</a></li></font> ");
						} else {
							sb.append("<li id='"+i+"' value='"+i+"' onclick='paginarItens(this.value);'><font style='background-color: #E6ECEF;  background-position: 100px; border-color: #E6ECEF; border: #B6B6B6; border-width: 1px; border-style: solid;'><a href='#'>"+i+"</a></li></font> ");
						}
					}
				}
			}
		}
		sb.append(" <li " + ((totalPaginas == 1 || paginaSelecionada.equals(totalPaginas)) ? "class='disabled'" : "value='"+(paginaSelecionada+1)+"' onclick='paginarItens(this.value);'") + " ><font style='background-color: #E6ECEF; border-color: #E6ECEF; border: #B6B6B6; border-width: 1px; border-style: solid;'><a href='#'>"+UtilI18N.internacionaliza(request, "citcorpore.comum.proximo")+"</a></li></font>");
		sb.append(" <li id='"+totalPaginas+"' value='"+totalPaginas+"' onclick='paginarItens(this.value);'><font style='background-color: #E6ECEF; border-color: #E6ECEF; border: #B6B6B6; border-width: 1px; border-style: solid;'><a href='#'>"+UtilI18N.internacionaliza(request, "citcorpore.comum.ultimo")+"</a></li></font>");
		sb.append(" </ul>");
		sb.append(" </div>");
		divPrincipal.setInnerHTML(sb.toString());
		
		
		//Limpa a tabela dos integrantes da viagem a cada paginação
		document.executeScript("limparCamposDaSegundaGridDoFormularioPrincipal();");
	}
	
	
	
	/**
	 * Preenche tabela 'Integrantes Viagem' da página Controle Financeiro Viagem Imprevisto
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author ronnie.lopes
	 */
	public void pesquisaIntegrantesViagem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ControleFinanceiroViagemDTO controleFinanceiroViagemDto = (ControleFinanceiroViagemDTO) document.getBean();
		RequisicaoViagemDTO requisicaoViagemDto = new RequisicaoViagemDTO();
		
		requisicaoViagemDto.setIdSolicitacaoServico(controleFinanceiroViagemDto.getIdSolicitacaoServico());
		
		RequisicaoViagemService reqViagemService = (RequisicaoViagemService) ServiceLocator.getInstance().getService(RequisicaoViagemService.class, null);

		Collection<IntegranteViagemDTO> colIntegrantes =  reqViagemService.recuperaIntegrantesViagemBySolicitacao(requisicaoViagemDto.getIdSolicitacaoServico());


		if(colIntegrantes !=null){
			HTMLTable tblIntegranteViagem;
			tblIntegranteViagem = document.getTableById("tblIntegrantesViagem");
			tblIntegranteViagem.deleteAllRows();
			tblIntegranteViagem.addRowsByCollection(colIntegrantes, new String[]{"","idEmpregado","nome"}, null, null, new String[]{"gerarImg"}, "addItens", null);
		}	
		
		document.executeScript("$('#div_integrantes').show()");
	}



	
	public void comboFormaPagamento(DocumentHTML document,HttpServletRequest request, HttpServletResponse response)throws Exception{
		
		document.getSelectById("idFormaPagamentoAux").removeAllOptions();
		FormaPagamentoService formaPagamentoService = (FormaPagamentoService) ServiceLocator.getInstance().getService(FormaPagamentoService.class, null);
		Collection colTipoMovimentacaoFinanceira = formaPagamentoService.list();
		document.getSelectById("idFormaPagamentoAux").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		
		if(colTipoMovimentacaoFinanceira!=null){
			document.getSelectById("idFormaPagamentoAux").addOptions(colTipoMovimentacaoFinanceira, "idFormaPagamento", "nomeFormaPagamento", null);
		}
	}
	
	public void comboJustificativaSolicitacao(DocumentHTML document,HttpServletRequest request, HttpServletResponse response)throws Exception{
		
		document.getSelectById("idJustificativaAux").removeAllOptions();
		JustificativaSolicitacaoService justificativaSolicitacaoService = (JustificativaSolicitacaoService) ServiceLocator.getInstance().getService(JustificativaSolicitacaoService.class, null);
		Collection colJustificativa = justificativaSolicitacaoService.listAtivasParaViagem();
		document.getSelectById("idJustificativaAux").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		
		if(colJustificativa!=null){
			document.getSelectById("idJustificativaAux").addOptions(colJustificativa, "idJustificativa", "descricaoJustificativa", null);
		}
	}
	
	
	public void comboClassificacao(DocumentHTML document,HttpServletRequest request, HttpServletResponse response)throws Exception{
		
		document.getSelectById("classificacaoAux").removeAllOptions();
		document.getSelectById("classificacaoAux").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		for(Enumerados.ClassificacaoMovFinViagem lista : Enumerados.ClassificacaoMovFinViagem.values()){
			document.getSelectById("classificacaoAux").addOption( lista.getDescricao(),lista.getDescricao());
		}
	}	
	
	public void comboFornecedor(DocumentHTML document,HttpServletRequest request, HttpServletResponse response)throws Exception{
		
		document.getSelectById("idFornecedorAux").removeAllOptions();
		FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
		Collection listaFornecedores =  fornecedorService.list();
		if(listaFornecedores!=null){
			document.getSelectById("idFornecedorAux").addOption("", "" + UtilI18N.internacionaliza(request, "citcorpore.comum.selecione") + "");
			document.getSelectById("idFornecedorAux").addOptions(listaFornecedores, "idFornecedor", "nomeFantasia", null);
		}
	}
	
	public void tratarTipoMovimentacaoFinanceira(DocumentHTML document, HttpServletRequest request, HttpServletResponse response)throws Exception{
		ControleFinanceiroViagemDTO controleFinanceiroViagemDto = (ControleFinanceiroViagemDTO) document.getBean();

		String classificacao = controleFinanceiroViagemDto.getClassificacao();
		classificacao = UtilStrings.removeCaracteresEspeciais(classificacao);
		TipoMovimFinanceiraViagemService tipoService = (TipoMovimFinanceiraViagemService) ServiceLocator.getInstance().getService(TipoMovimFinanceiraViagemService.class, null);
		List<TipoMovimFinanceiraViagemDTO> listaTipoMovimentacaoFinanceiraViagem = new ArrayList<TipoMovimFinanceiraViagemDTO>();
		
		listaTipoMovimentacaoFinanceiraViagem =  tipoService.listByClassificacao(classificacao);
		if (listaTipoMovimentacaoFinanceiraViagem != null) {
			document.getSelectById("idTipoMovimFinanceiraViagemAux").removeAllOptions();
			document.getSelectById("idTipoMovimFinanceiraViagemAux").addOption("", "" + UtilI18N.internacionaliza(request, "citcorpore.comum.selecione") + "");
			for (TipoMovimFinanceiraViagemDTO tipoMov : listaTipoMovimentacaoFinanceiraViagem) {
				document.getSelectById("idTipoMovimFinanceiraViagemAux").addOption(tipoMov.getIdtipoMovimFinanceiraViagem().toString(),tipoMov.getNome());
			}
		}
		
		controleFinanceiroViagemDto.setQuantidade(1.0);
		
		if(classificacao.equalsIgnoreCase(Enumerados.ClassificacaoMovFinViagem.Passagem.toString())){
			
			document.getSelectById("div_assento").setVisible(true);
			document.getSelectById("div_tipoPassagem").setVisible(true);
			document.getSelectById("div_localizador").setVisible(true);
			
		}else if(classificacao.equalsIgnoreCase(Enumerados.ClassificacaoMovFinViagem.Diaria.toString())){
			
			document.getSelectById("div_assento").setVisible(false);
			document.getSelectById("div_tipoPassagem").setVisible(false);
			document.getSelectById("div_localizador").setVisible(false);
			
		}else{
			
			document.getSelectById("div_assento").setVisible(false);
			document.getSelectById("div_tipoPassagem").setVisible(false);
			document.getSelectById("div_localizador").setVisible(false);
			
		}
		
		if(classificacao.equalsIgnoreCase("")){
			document.getSelectById("idTipoMovimFinanceiraViagemAux").setDisabled(true);
		}else{
			document.getSelectById("idTipoMovimFinanceiraViagemAux").setDisabled(false);
		}
	}
	
	public void atualizaGridItensControle(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, ItemControleFinanceiroViagemDTO itemControleFinanceiroViagemDto) throws ServiceException, Exception{
		ItemControleFinanceiroViagemService itemService = (ItemControleFinanceiroViagemService) ServiceLocator.getInstance().getService(ItemControleFinanceiroViagemService.class, null);
		Collection<ItemControleFinanceiroViagemDTO> colItens = itemService.recuperaItensControleFinanceiro(itemControleFinanceiroViagemDto);
		
		if(colItens != null){
			HTMLTable tblItemControleFinaceiro;
			tblItemControleFinaceiro = document.getTableById("tblItemControleFinaceiro");
			tblItemControleFinaceiro.deleteAllRows();
			tblItemControleFinaceiro.addRowsByCollection(colItens, new String[] {"","nomeTipoMovimFinanceira", "nomeFornecedor","valorAdiantamento",""}, null, null, new String[] {"gerarImgAlterar","gerarImgDeletar"}, "editarItem", null);
		}
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
		
		ControleFinanceiroViagemDTO controleFinanceiroViagemDto = (ControleFinanceiroViagemDTO) document.getBean();

 		String classificacaoPeloDto = controleFinanceiroViagemDto.getClassificacao();
		classificacaoPeloDto = UtilStrings.removeCaracteresEspeciais(classificacaoPeloDto);
		
		TipoMovimFinanceiraViagemService tipoMovimService = (TipoMovimFinanceiraViagemService) ServiceLocator.getInstance().getService(TipoMovimFinanceiraViagemService.class, null);
		TipoMovimFinanceiraViagemDTO tipoMovimFinanceiraDto = new TipoMovimFinanceiraViagemDTO();
		
		tipoMovimFinanceiraDto.setIdtipoMovimFinanceiraViagem(controleFinanceiroViagemDto.getIdTipoMovimenta());
		tipoMovimFinanceiraDto = (TipoMovimFinanceiraViagemDTO) tipoMovimService.restore(tipoMovimFinanceiraDto);
				
		
		if(tipoMovimFinanceiraDto.getPermiteAdiantamento().equalsIgnoreCase("S")){
			document.executeScript("$('#nomeValorTotal').hide()");
			document.executeScript("$('#nomeAdiantamento').show()");
		}else{
			document.executeScript("$('#nomeAdiantamento').hide()");
			document.executeScript("$('#nomeValorTotal').show()");
		}
		
		DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
		
		
		//Inicializando os valores dos campos
		if(controleFinanceiroViagemDto.getQuantidade() == null)
			controleFinanceiroViagemDto.setQuantidade(0d);
		
		if(controleFinanceiroViagemDto.getValorUnitario() == null)
			controleFinanceiroViagemDto.setValorUnitario(0d);
		
		if(controleFinanceiroViagemDto.getAdiantamento() == null)
			controleFinanceiroViagemDto.setAdiantamento(0d);
		
		
		if(tipoMovimFinanceiraDto!=null){
				if(tipoMovimFinanceiraDto.getValorPadrao() != null){
					String valorUnit = decimalFormat.format(tipoMovimFinanceiraDto.getValorPadrao());
					controleFinanceiroViagemDto.setValorUnitario(tipoMovimFinanceiraDto.getValorPadrao());
					document.getSelectById("valorUnitarioAux").setValue(valorUnit);
				}
				
				controleFinanceiroViagemDto.setQuantidade(1.0);
				document.getElementById("quantidadeAux").setValue("1");
				
		}

		if(classificacaoPeloDto.equalsIgnoreCase(Enumerados.ClassificacaoMovFinViagem.Diaria.toString())){
			controleFinanceiroViagemDto.setAdiantamento((controleFinanceiroViagemDto.getQuantidade()+1) * controleFinanceiroViagemDto.getValorUnitario());
		}
		else {
			controleFinanceiroViagemDto.setAdiantamento(controleFinanceiroViagemDto.getQuantidade() * controleFinanceiroViagemDto.getValorUnitario());
		}
		
		document.getElementById("nomeTipoMovimFinanceira").setValue(tipoMovimFinanceiraDto.getNome());
		String adiantamentoFormatado  = decimalFormat.format(controleFinanceiroViagemDto.getAdiantamento());
		document.getSelectById("valorAdiantamentoAux").setValue(adiantamentoFormatado);
	}
	
	
	public void recuperaInformacoesIntegrante(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception{
		ControleFinanceiroViagemDTO controleFinanceiroViagemDto = (ControleFinanceiroViagemDTO) document.getBean();
		EmpregadoService empregadoService = (EmpregadoService)ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		EmpregadoDTO empregadoDto = new EmpregadoDTO();
		if(controleFinanceiroViagemDto.getIdEmpregado() != null){
			empregadoDto.setIdEmpregado(controleFinanceiroViagemDto.getIdEmpregado());
			empregadoDto = (EmpregadoDTO) empregadoService.restore(empregadoDto);
			document.getElementById("nomeIntegrante").setValue(empregadoDto.getNome());
			document.getElementById("nomeIntegrante").setDisabled(true);
			
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
			
			ItemControleFinanceiroViagemDTO itemControleDto = new ItemControleFinanceiroViagemDTO();
			itemControleDto.setIdItemControleFinanceiroViagem(idItemSelecionado);
			ItemControleFinanceiroViagemService itemControleService = (ItemControleFinanceiroViagemService) ServiceLocator.getInstance().getService(ItemControleFinanceiroViagemService.class, null);
			itemControleDto = (ItemControleFinanceiroViagemDTO) itemControleService.restore(itemControleDto);
			if(itemControleDto!=null){
				itemControleDto.setDataFim(UtilDatas.getDataAtual());
				itemControleService.update(itemControleDto);	
			}
		}
	}
	
	public void comboTipoMovimentacaoFinanceira(DocumentHTML document,HttpServletRequest request, HttpServletResponse response)throws Exception{
		
		document.getSelectById("idTipoMovimFinanceiraViagemAux").removeAllOptions();
		TipoMovimFinanceiraViagemService tipoMovService = (TipoMovimFinanceiraViagemService) ServiceLocator.getInstance().getService(TipoMovimFinanceiraViagemService.class, null);
		ArrayList<TipoMovimFinanceiraViagemDTO>colTipoMovimentacaoFinanceira = (ArrayList<TipoMovimFinanceiraViagemDTO>) tipoMovService.recuperaTipoAtivos();
		document.getSelectById("idTipoMovimFinanceiraViagemAux").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
        
		if(colTipoMovimentacaoFinanceira!=null){
			for(TipoMovimFinanceiraViagemDTO listaMovimFinanceira : colTipoMovimentacaoFinanceira){
				document.getSelectById("idTipoMovimFinanceiraViagemAux").addOption( listaMovimFinanceira.getIdtipoMovimFinanceiraViagem().toString(),listaMovimFinanceira.getDescricao());
			}
		}
		document.getSelectById("idTipoMovimFinanceiraViagemAux").setDisabled(true);
	}
	
	
	
	public void recuperaNomeFornecedor(DocumentHTML document,HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		ControleFinanceiroViagemDTO controleFinanceiroViagemDto = (ControleFinanceiroViagemDTO) document.getBean();
		Integer idItemSelecionado = null;
		
		if(controleFinanceiroViagemDto.getIdFornecedor() != null && controleFinanceiroViagemDto.getIdFornecedor().intValue() > 0)
			idItemSelecionado = controleFinanceiroViagemDto.getIdFornecedor();
		
		FornecedorDao dao = new FornecedorDao();
		FornecedorDTO fornecedorDto = new FornecedorDTO();
		
		if(idItemSelecionado != null && idItemSelecionado.intValue() > 0){
			
			fornecedorDto.setIdFornecedor(idItemSelecionado);
			fornecedorDto = (FornecedorDTO) dao.restore(fornecedorDto);
			if(fornecedorDto != null){
				document.getElementById("nomeFornecedor").setValue(fornecedorDto.getNomeFantasia());
			}
			
		}
	}
	
}