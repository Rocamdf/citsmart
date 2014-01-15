package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.bean.AdiantamentoViagemDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.IntegranteViagemDTO;
import br.com.centralit.citcorpore.bean.ItemControleFinanceiroViagemDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.AdiantamentoViagemService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.ItemControleFinanceiroViagemService;
import br.com.centralit.citcorpore.negocio.RequisicaoViagemService;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;

@SuppressWarnings({ "rawtypes"})
public class AdiantamentoViagem extends AjaxFormAction{

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		
		AdiantamentoViagemDTO adiantamentoViagemDto = (AdiantamentoViagemDTO) document.getBean();
		
		if(adiantamentoViagemDto.getIdSolicitacaoServico() != null)
			this.geraTblIntegrantesViagem(document, request, response, adiantamentoViagemDto.getIdSolicitacaoServico());
	}
	
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception{
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		AdiantamentoViagemService adiantamentoViagemService = (AdiantamentoViagemService) ServiceLocator.getInstance().getService(AdiantamentoViagemService.class, null);
		AdiantamentoViagemDTO adiantamentoViagemDto = (AdiantamentoViagemDTO) document.getBean();
		adiantamentoViagemDto.setIdResponsavel(usuario.getIdEmpregado());
		adiantamentoViagemDto.setSituacao("Confirmado");
		
		adiantamentoViagemDto.setIdAdiantamentoViagem(adiantamentoViagemService.recuperaIdAdiantamentoSeExistir(adiantamentoViagemDto));
		adiantamentoViagemDto.setValorTotalAdiantado(this.calculaValorTotalAdiantamento(adiantamentoViagemDto));
		
		if(adiantamentoViagemDto.getIdAdiantamentoViagem() == null){
			adiantamentoViagemService.create(adiantamentoViagemDto);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		}else{
			adiantamentoViagemService.update(adiantamentoViagemDto);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}
		
		
		HTMLForm form = document.getForm("form");
		form.clear();
		document.executeScript("fecharFrameItemControleFinanceiro();");
		
	}
	
	public void restorePorIntegrante(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception{
		AdiantamentoViagemDTO adiantamentoViagemDto = (AdiantamentoViagemDTO) document.getBean();
		AdiantamentoViagemService adiantamentoViagemService = (AdiantamentoViagemService) ServiceLocator.getInstance().getService(AdiantamentoViagemService.class, null);
		
		adiantamentoViagemDto.setIdAdiantamentoViagem(adiantamentoViagemService.recuperaIdAdiantamentoSeExistir(adiantamentoViagemDto));;
		
		if(adiantamentoViagemDto.getIdAdiantamentoViagem() != null){
			adiantamentoViagemDto = (AdiantamentoViagemDTO) adiantamentoViagemService.restore(adiantamentoViagemDto);
			if(adiantamentoViagemDto.getObservacoes() != null)
				document.getElementById("observacoesPopup").setValue(adiantamentoViagemDto.getObservacoes());
		}

	}
	
	@Override
	public Class getBeanClass() {
		return AdiantamentoViagemDTO.class;
	}
	
	private void geraTblIntegrantesViagem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, Integer idSolicitacaoServico) throws ServiceException, Exception{
		RequisicaoViagemService reqViagemService = (RequisicaoViagemService) ServiceLocator.getInstance().getService(RequisicaoViagemService.class, null);
		
		Collection<IntegranteViagemDTO> colIntegrantes =  reqViagemService.recuperaIntegrantesViagemBySolicitacao(idSolicitacaoServico);
		if(colIntegrantes != null){
			HTMLTable tblControleFinaceiro;
			tblControleFinaceiro = document.getTableById("tblControleFinaceiro");
			tblControleFinaceiro.deleteAllRows();
			tblControleFinaceiro.addRowsByCollection(colIntegrantes, new String[]{"","idEmpregado","nome"}, null, null, new String[]{"gerarImg"}, "carregaPopupAdiantamento", null);			
		}
	}
	
	public void atualizaGridItensControle(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception{
		
		AdiantamentoViagemDTO adiantamentoViagemDto = (AdiantamentoViagemDTO) document.getBean();
		
		ItemControleFinanceiroViagemDTO itemControleFinanceiroViagemDto = new ItemControleFinanceiroViagemDTO();
		
		Double valorTotal = (double) 0;
		
		if(adiantamentoViagemDto.getIdSolicitacaoServico() !=null && adiantamentoViagemDto.getIdEmpregado() !=null){
			itemControleFinanceiroViagemDto.setIdSolicitacaoServico(adiantamentoViagemDto.getIdSolicitacaoServico());
			itemControleFinanceiroViagemDto.setIdEmpregado(adiantamentoViagemDto.getIdEmpregado());
			
			ItemControleFinanceiroViagemService itemService = (ItemControleFinanceiroViagemService) ServiceLocator.getInstance().getService(ItemControleFinanceiroViagemService.class, null);
			Collection<ItemControleFinanceiroViagemDTO> colItens = itemService.listaItensAdiantamento(itemControleFinanceiroViagemDto.getIdSolicitacaoServico(), itemControleFinanceiroViagemDto.getIdEmpregado());
			
			EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
			EmpregadoDTO empregadoDto = new EmpregadoDTO();
			
			empregadoDto.setIdEmpregado(adiantamentoViagemDto.getIdEmpregado());
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
				
			}else{
				strTable.append("<h3>" + UtilI18N.internacionaliza(request, "requisicaoViagem.integranteViagemNaoAdiantamento") + "</h3>");
				document.executeScript("$('#divBtnGravar').hide();");
			}
			
			document.getElementById("divTblItens").setInnerHTML(strTable.toString());
			document.executeScript("$('#POPUP_ITEMCONTROLEFINANCEIRO').dialog('open');");
		}else{
			document.alert(UtilI18N.internacionaliza(request, "MSG04"));
		}
		
 	}

	private Double calculaValorTotalAdiantamento(AdiantamentoViagemDTO adiantamentoViagemDto) throws ServiceException, Exception{
		double valorTotal = 0;
		
		ItemControleFinanceiroViagemService itemService = (ItemControleFinanceiroViagemService) ServiceLocator.getInstance().getService(ItemControleFinanceiroViagemService.class, null);
		Collection<ItemControleFinanceiroViagemDTO> colItens = itemService.listaItensAdiantamento(adiantamentoViagemDto.getIdSolicitacaoServico(), adiantamentoViagemDto.getIdEmpregado());
		
		if(colItens != null){
			for(ItemControleFinanceiroViagemDTO item : colItens){
				if(item.getValorAdiantamento() != null)
					valorTotal += item.getValorAdiantamento();
			}
			
			if(valorTotal > 0)
				return valorTotal;
		}
		
		return null;
	}
	

}
