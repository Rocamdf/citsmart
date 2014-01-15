package br.com.centralit.citcorpore.ajaxForms;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.bean.AdiantamentoViagemDTO;
import br.com.centralit.citcorpore.bean.CidadesDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.ControleFinanceiroViagemDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.IntegranteViagemDTO;
import br.com.centralit.citcorpore.bean.ItemControleFinanceiroViagemDTO;
import br.com.centralit.citcorpore.bean.JustificativaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.ParecerDTO;
import br.com.centralit.citcorpore.bean.RequisicaoViagemDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CentroResultadoService;
import br.com.centralit.citcorpore.negocio.CidadesService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.ControleFinanceiroViagemService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.ItemControleFinanceiroViagemService;
import br.com.centralit.citcorpore.negocio.JustificativaParecerService;
import br.com.centralit.citcorpore.negocio.JustificativaSolicitacaoService;
import br.com.centralit.citcorpore.negocio.ParecerService;
import br.com.centralit.citcorpore.negocio.ProjetoService;
import br.com.centralit.citcorpore.negocio.RequisicaoViagemService;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
@SuppressWarnings({"rawtypes","unchecked"})
public class AutorizacaoViagem  extends AjaxFormAction{

	
	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		
		
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		
		document.executeScript("$('#divJustificativa').hide();");
		
		RequisicaoViagemDTO requisicaoViagemDto = (RequisicaoViagemDTO) document.getBean();
		
		this.preencherComboCentroResultado(document, request, response);
		this.preencherComboProjeto(document, request, response, requisicaoViagemDto);
		this.preencherComboJustificativa(document, request, response);
		
		if(requisicaoViagemDto.getIdSolicitacaoServico()!=null){
			restore(document, request, response, requisicaoViagemDto);
		}
		
	}
	
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response,RequisicaoViagemDTO requisicaoViagemDto) throws ServiceException, Exception{
		
		ControleFinanceiroViagemDTO controleFinanceiroViagemDto = new ControleFinanceiroViagemDTO();
		
		ParecerDTO parecerDto = new ParecerDTO();
		
		RequisicaoViagemService reqViagemService = (RequisicaoViagemService) ServiceLocator.getInstance().getService(RequisicaoViagemService.class, null);
		
		ControleFinanceiroViagemService controleFinanceiroViagemService = (ControleFinanceiroViagemService) ServiceLocator.getInstance().getService(ControleFinanceiroViagemService.class, null);
		
		ParecerService parecerService = (ParecerService) ServiceLocator.getInstance().getService(ParecerService.class, null);
		
		
		if(requisicaoViagemDto.getIdSolicitacaoServico()!=null){
			requisicaoViagemDto = (RequisicaoViagemDTO) reqViagemService.restore(requisicaoViagemDto);
			
			requisicaoViagemDto.setNomeCidadeOrigem(this.recuperaCidade(requisicaoViagemDto.getIdCidadeOrigem()));
			requisicaoViagemDto.setNomeCidadeDestino(this.recuperaCidade(requisicaoViagemDto.getIdCidadeDestino()));
			
			controleFinanceiroViagemDto = controleFinanceiroViagemService.buscarControleFinanceiroViagemPorIdSolicitacao(requisicaoViagemDto.getIdSolicitacaoServico());
			
			if(controleFinanceiroViagemDto!=null){
				requisicaoViagemDto.setNomeMoeda(controleFinanceiroViagemDto.getNomeMoeda());
				requisicaoViagemDto.setObservacoes(controleFinanceiroViagemDto.getObservacoes());
			}
			
			if(requisicaoViagemDto.getIdAprovacao()!=null){
				parecerDto.setIdParecer(requisicaoViagemDto.getIdAprovacao());
				parecerDto = (ParecerDTO) parecerService.restore(parecerDto);
				if(parecerDto!=null){
					this.preencherComboJustificativaAutorizacao(document, request, response);
					requisicaoViagemDto.setAutorizado(parecerDto.getAprovado());
					requisicaoViagemDto.setIdJustificativaAutorizacao(parecerDto.getIdJustificativa());
					requisicaoViagemDto.setComplemJustificativaAutorizacao(parecerDto.getComplementoJustificativa());
					document.executeScript("configuraJustificativa('"+requisicaoViagemDto.getAutorizado()+"')");
					
					
				}
			}
			this.informacoesItemControleFinanceiroPorIntegrateViagem(document, request, response);
			
			DecimalFormat df = new DecimalFormat("#,##0.00");
			
			Double valorTotal = reqViagemService.calculaValorTotalViagem(requisicaoViagemDto.getIdSolicitacaoServico());
			String divValor = "<h2 style='margin-top: 10px; padding-left: 20px;'> Valor Total da Viagem: " + df.format(valorTotal) + "</h2><hr>";
			document.getElementById("divValor").setInnerHTML(divValor);
			
		}
		
		
		
		
		
		HTMLForm form = document.getForm("form");
        form.clear();   
        form.setValues(requisicaoViagemDto);
       
            document.getElementById("nomeCidadeOrigem").setDisabled(true);
            document.getElementById("nomeCidadeDestino").setDisabled(true);
            document.getElementById("dataInicioViagem").setDisabled(true);
            document.getElementById("dataFimViagem").setDisabled(true);
            document.getElementById("qtdeDias").setDisabled(true);
            document.getElementById("idCentroCusto").setDisabled(true);
            document.getElementById("idProjeto").setDisabled(true);
            document.getElementById("idMotivoViagem").setDisabled(true);
            document.getElementById("descricaoMotivo").setDisabled(true);
            document.getElementById("nomeMoeda").setDisabled(true);
            document.getElementById("observacoes").setDisabled(true);
		
	}
	

	@Override
	public Class getBeanClass() {
		// TODO Auto-generated method stub
		return RequisicaoViagemDTO.class;
	}
	
	
	
	/**
	 * Preenche a combo de 'Centro Resultado' do formulário HTML
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void preencherComboCentroResultado(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		CentroResultadoService centroResultadoService = (CentroResultadoService) ServiceLocator.getInstance().getService(CentroResultadoService.class, WebUtil.getUsuarioSistema(request));
        HTMLSelect idCentroCusto = (HTMLSelect) document.getSelectById("idCentroCusto");
        idCentroCusto.removeAllOptions();
        idCentroCusto.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
        Collection colCCusto = centroResultadoService.listPermiteRequisicaoProduto();
        if(colCCusto != null && !colCCusto.isEmpty()){
        	 idCentroCusto.addOptions(colCCusto, "idCentroResultado", "nomeHierarquizado", null);
        }
           
	}
	
	/**
	 * Preenche a combo de 'Projeto' do formulário HTML
	 * @param document
	 * @param request
	 * @param response
	 * @param requisicaoViagemDto
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void preencherComboProjeto(DocumentHTML document, HttpServletRequest request, HttpServletResponse response,RequisicaoViagemDTO requisicaoViagemDto) throws Exception{
		 HTMLSelect idProjeto = (HTMLSelect) document.getSelectById("idProjeto");
	        idProjeto.removeAllOptions();
	        idProjeto.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
	        if (requisicaoViagemDto.getIdContrato() != null) {
	            ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, WebUtil.getUsuarioSistema(request));
	            ContratoDTO contratoDto = new ContratoDTO();
	            contratoDto.setIdContrato(requisicaoViagemDto.getIdContrato());
	            contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
	            if (contratoDto != null) {
	                ProjetoService projetoService = (ProjetoService) ServiceLocator.getInstance().getService(ProjetoService.class, WebUtil.getUsuarioSistema(request));
	                Collection colProjetos = projetoService.listHierarquia(contratoDto.getIdCliente(), true);
	                if(colProjetos != null && !colProjetos.isEmpty()) 
	                    idProjeto.addOptions(colProjetos, "idProjeto", "nomeHierarquizado", null);
	            }
	        }
	}
	
	/**
	 * Preenche as combos de 'Cidade Origem' e Cidade Destino.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	
	public void preencherComboCidades(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CidadesService cidadesService = (CidadesService) ServiceLocator.getInstance().getService(CidadesService.class, null);
		
		HTMLSelect comboCidadeOrigem = (HTMLSelect) document.getSelectById("idCidadeOrigem");
		HTMLSelect comboCidadeDestino = (HTMLSelect) document.getSelectById("idCidadeDestino");

		ArrayList<CidadesDTO> listCidade = (ArrayList) cidadesService.list();

		this.inicializaCombo(comboCidadeOrigem, request);
		this.inicializaCombo(comboCidadeDestino, request);
		if (listCidade != null) {
			comboCidadeOrigem.addOptions(listCidade, "idCidade", "nomeCidade", null);
			comboCidadeDestino.addOptions(listCidade, "idCidade", "nomeCidade", null);
		}
	}
	
	
	
	/**
	 * Preenche combo de 'justificativa solicitação'.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void preencherComboJustificativa(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JustificativaSolicitacaoService justificativaSolicitacaoService = (JustificativaSolicitacaoService)ServiceLocator.getInstance().getService(JustificativaSolicitacaoService.class, null);
		
		Collection<JustificativaSolicitacaoDTO> colJustificativas = justificativaSolicitacaoService.listAtivasParaViagem();
		
		HTMLSelect comboJustificativa = (HTMLSelect) document.getSelectById("idMotivoViagem");
		document.getSelectById("idMotivoViagem").removeAllOptions();
		inicializaCombo(comboJustificativa, request);
		if (colJustificativas != null){
			comboJustificativa.addOptions(colJustificativas, "idJustificativa", "descricaoJustificativa", null);
		}
	}
	
	
	/**
	 * Executa uma inicialização padrão para as combos. Basicamente deleta todas as opções, caso haja, e insere aprimeira linha com o valor "-- Selecione --".
	 * @param componenteCombo
	 * @param request
	 */
	public void inicializaCombo(HTMLSelect componenteCombo, HttpServletRequest request) {
		componenteCombo.removeAllOptions();
		componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
	}
	
	/**
	 * @param idCidade
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	public String recuperaCidade(Integer idCidade) throws Exception {
		CidadesDTO cidadeDto  = new CidadesDTO();
		CidadesService cidadesService = (CidadesService) ServiceLocator.getInstance().getService(CidadesService.class, null);
		if(idCidade !=null){
			cidadeDto.setIdCidade(idCidade);
			cidadeDto = (CidadesDTO) cidadesService.restore(cidadeDto);
			return cidadeDto.getNomeCidade();
		}
		return null;
	}
	
	public void informacoesItemControleFinanceiroPorIntegrateViagem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception{
		
		RequisicaoViagemDTO requisicaoViagemDto = (RequisicaoViagemDTO) document.getBean();
		
		RequisicaoViagemService reqViagemService = (RequisicaoViagemService) ServiceLocator.getInstance().getService(RequisicaoViagemService.class, null);
		
		Collection<IntegranteViagemDTO> colIntegrantes =  reqViagemService.recuperaIntegrantesViagemBySolicitacao(requisicaoViagemDto.getIdSolicitacaoServico());
		
		
		if(colIntegrantes !=null){
			
			HTMLTable tblControleFinaceiro;
			tblControleFinaceiro = document.getTableById("tblControleFinaceiro");
			tblControleFinaceiro.deleteAllRows();
			tblControleFinaceiro.addRowsByCollection(colIntegrantes, new String[]{"","idEmpregado","nome"}, new String[]{"idEmpregado"}, UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaAdicionado"), new String[]{"gerarImg"}, "addItemIntegrante", null);
		}		
	}
	
	/*public void atualizaGridItensControle(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception{
		
		RequisicaoViagemDTO requisicaoViagemDto = (RequisicaoViagemDTO) document.getBean();
		
		ItemControleFinanceiroViagemDTO itemControleFinanceiroViagemDto = new ItemControleFinanceiroViagemDTO();
		
		Double valorTotal = (double) 0;
		
		String valorTotalAdiamtamento = "";
		
		if(requisicaoViagemDto.getIdSolicitacaoServico() !=null && requisicaoViagemDto.getIdEmpregado() !=null){
			itemControleFinanceiroViagemDto.setIdSolicitacaoServico(requisicaoViagemDto.getIdSolicitacaoServico());
			itemControleFinanceiroViagemDto.setIdEmpregado(requisicaoViagemDto.getIdEmpregado());
			
			ItemControleFinanceiroViagemService itemService = (ItemControleFinanceiroViagemService) ServiceLocator.getInstance().getService(ItemControleFinanceiroViagemService.class, null);
			Collection<ItemControleFinanceiroViagemDTO> colItens = itemService.recuperaItensControleFinanceiro(itemControleFinanceiroViagemDto);
			
			if(colItens != null){
				
				HTMLTable tblItemControleFinaceiro;
				tblItemControleFinaceiro = document.getTableById("tblItemControleFinaceiro");
				tblItemControleFinaceiro.deleteAllRows();
				tblItemControleFinaceiro.addRowsByCollection(colItens, new String[] {"nomeTipoMovimFinanceira", "nomeFornecedor","valorUnitario"}, null, null, null, null, null);
				for(ItemControleFinanceiroViagemDTO itemControleFinanceiroDto : colItens){
					valorTotal =  valorTotal + itemControleFinanceiroDto.getValorAdiantamento();
				}
				valorTotalAdiamtamento = "<input type='text' id='valorTotal' name='valorTotal' value='"+valorTotal+"' readonly='readonly'/>";
				document.getElementById("valorTotalAdiamtamento").setInnerHTML(valorTotalAdiamtamento);
				document.executeScript("$('#POPUP_ITEMCONTROLEFINANCEIRO').dialog('open');");
			}else{
				document.alert(UtilI18N.internacionaliza(request, "MSG04"));
			}
		}
		
 	}*/
	
	/**
	 * Preenche combo de 'justificativa solicitação ' para autozização.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void preencherComboJustificativaAutorizacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JustificativaParecerService justificativaService = (JustificativaParecerService) ServiceLocator.getInstance().getService(JustificativaParecerService.class, WebUtil.getUsuarioSistema(request));
		
	    Collection colJustificativas = justificativaService.listAplicaveisRequisicao();
	    
		HTMLSelect comboJustificativaAutorizacao = (HTMLSelect) document.getSelectById("idJustificativaAutorizacao");
		
		document.getSelectById("idJustificativaAutorizacao").removeAllOptions();
		
		inicializaCombo(comboJustificativaAutorizacao, request);
		
		if (colJustificativas != null){
			comboJustificativaAutorizacao.addOptions(colJustificativas, "idJustificativa", "descricaoJustificativa", null);
		}
	}
	
	public void montarGridIntegrateViagem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception{
		
		RequisicaoViagemDTO requisicaoViagemDto = (RequisicaoViagemDTO) document.getBean();
		
		RequisicaoViagemService reqViagemService = (RequisicaoViagemService) ServiceLocator.getInstance().getService(RequisicaoViagemService.class, null);
		
		Collection<IntegranteViagemDTO> colIntegrantes =  reqViagemService.recuperaIntegrantesViagemBySolicitacao(requisicaoViagemDto.getIdSolicitacaoServico());
		
		
		if(colIntegrantes !=null){
			HTMLTable tblIntegranteViagem;
			tblIntegranteViagem = document.getTableById("tblIntegranteViagem");
			tblIntegranteViagem.deleteAllRows();
			tblIntegranteViagem.addRowsByCollection(colIntegrantes, new String[]{"idEmpregado","nome"}, null, null, new String[]{"gerarButtonDelete"}, null, null);
		}		
	}
	
	public void atualizaGridItensControle(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception{
		
		RequisicaoViagemDTO requisicaoViagemDto = (RequisicaoViagemDTO) document.getBean();
		
		ItemControleFinanceiroViagemDTO itemControleFinanceiroViagemDto = new ItemControleFinanceiroViagemDTO();
		
		Double valorTotal = (double) 0;
		
		if(requisicaoViagemDto.getIdSolicitacaoServico() !=null && requisicaoViagemDto.getIdEmpregado() !=null){
			itemControleFinanceiroViagemDto.setIdSolicitacaoServico(requisicaoViagemDto.getIdSolicitacaoServico());
			itemControleFinanceiroViagemDto.setIdEmpregado(requisicaoViagemDto.getIdEmpregado());
			
			ItemControleFinanceiroViagemService itemService = (ItemControleFinanceiroViagemService) ServiceLocator.getInstance().getService(ItemControleFinanceiroViagemService.class, null);
			Collection<ItemControleFinanceiroViagemDTO> colItens = itemService.recuperaItensControleFinanceiro(itemControleFinanceiroViagemDto);
			
			EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
			EmpregadoDTO empregadoDto = new EmpregadoDTO();
			
			empregadoDto.setIdEmpregado(requisicaoViagemDto.getIdEmpregado());
			empregadoDto = (EmpregadoDTO) empregadoService.restore(empregadoDto);
			
			String divNome = "<h2>" + empregadoDto.getNome() + "</h2>";
			document.getElementById("divNome").setInnerHTML(divNome);
			
			StringBuilder strTable = new StringBuilder();
			strTable.append("");
			if (colItens != null && colItens.size() > 0){
				
				strTable.append("<table width=\"100%\" class=\"table\">");
					strTable.append("<thead>");
						strTable.append("<tr>");
							strTable.append("<th>");
							strTable.append(UtilI18N.internacionaliza(request, "itemControleFinanceiroViagem.tipoMovimentacaoFinanceira"));
							strTable.append("</th>");
							strTable.append("<th>");
							strTable.append(UtilI18N.internacionaliza(request, "fornecedor"));
							strTable.append("</th>");
							strTable.append("<th>");
							strTable.append(UtilI18N.internacionaliza(request, "itemControleFinanceiroViagem.quantidade"));
							strTable.append("</th>");
							strTable.append("<th>");
							strTable.append(UtilI18N.internacionaliza(request, "itemControleFinanceiroViagem.valorUnitario"));
							strTable.append("</th>");
							strTable.append("<th  >");
							strTable.append(UtilI18N.internacionaliza(request, "coletaPreco.preco"));
							strTable.append("</th>");
						strTable.append("</tr>");
					strTable.append("</thead>");

					for(ItemControleFinanceiroViagemDTO item : colItens){
						
						valorTotal =  valorTotal + item.getValorAdiantamento();
						double qtde = 0;
						if(item.getQuantidade() != null)
							qtde = item.getQuantidade();
						
						strTable.append("<tr>");								
							strTable.append("<td class='tdPontilhada' style=\"text-align:left\">&nbsp&nbsp&nbsp" + (item.getNomeTipoMovimFinanceira() == null ? "" : item.getNomeTipoMovimFinanceira()) +"</td>");
							strTable.append("<td class='tdPontilhada' style=\"text-align:left\">&nbsp&nbsp&nbsp" + (item.getNomeFornecedor() == null ? "" : item.getNomeFornecedor()) +"</td>");
							strTable.append("<td class='tdPontilhada' style=\"text-align:center\">" + (item.getQuantidade() == null ? "" :  (int)qtde) +"</td>");
							strTable.append("<td class='tdPontilhada' style=\"text-align:center\">" + (item.getValorUnitario() == null ? "" : item.getValorUnitario()) +"</td>");
							strTable.append("<td class='tdPontilhada' style=\"text-align:center\">" + (item.getValorAdiantamento() == null ? "" : item.getValorAdiantamento()) +"</td>");	
					}
				
					strTable.append("<tr style=\"background-color:#DDDDDD\" >");			
					strTable.append("<td colspan='3'></td>");				
					strTable.append("<td style=\"text-align:right;font-weight:bold\">Valor Total</td>");				
					strTable.append("<td style=\"text-align:center\"> R$ "+ valorTotal +"</td>");				
					strTable.append("</tr>");	
				strTable.append("</table>");
				
				document.getElementById("divTblItens").setInnerHTML(strTable.toString());
			}
			
			document.getElementById("valorTotalAdiantado").setValue(valorTotal.toString());
			document.executeScript("$('#POPUP_ITEMCONTROLEFINANCEIRO').dialog('open');");
		}else{
			document.alert(UtilI18N.internacionaliza(request, "MSG04"));
		}
		
 	}

}
