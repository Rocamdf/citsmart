package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.JobExecutionException;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.PesquisaItemConfiguracaoDTO;
import br.com.centralit.citcorpore.negocio.PesquisaItemConfiguracaoService;
import br.com.centralit.citcorpore.quartz.job.VerificaValidadeLicenca;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.citframework.service.ServiceLocator;

public class PesquisaItemConfiguracao extends AjaxFormAction {

	@SuppressWarnings("rawtypes")
	@Override
	public Class getBeanClass() {

		return PesquisaItemConfiguracaoDTO.class;
	}

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		document.focusInFirstActivateField(null);
		document.executeScript("$('#loading_overlay').hide();");
	}

	public void pesquisarItemConfiguracao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PesquisaItemConfiguracaoDTO pesquisaItemConfiguracaDto = (PesquisaItemConfiguracaoDTO) document.getBean();
		PesquisaItemConfiguracaoService pesquisaItemConfiguracaoService = (PesquisaItemConfiguracaoService) ServiceLocator.getInstance().getService(PesquisaItemConfiguracaoService.class, null);

		Collection<ItemConfiguracaoDTO> listaDeItem = pesquisaItemConfiguracaoService.listByIdItemconfiguracao(pesquisaItemConfiguracaDto);
		StringBuffer strAux = new StringBuffer();
		strAux.append("<div class='col_100' style='heigth: 250px; overflow: auto;'>");
		int i = 0;
		String cor = "#F2F2F2";
		if ((listaDeItem != null) && (listaDeItem.size() > 0)) {
			for (ItemConfiguracaoDTO item : listaDeItem) {
				String identificacao = item.getIdentificacao();

				if ((i % 2) == 0) {
					cor = "";
				} else {
					cor = "#F2F2F2";
				}
				strAux.append("<div class='col_25' style='border: 1px solid  #DDDDDD; text-align: center; width: 320px; display: block; float: left; margin-left: 1px; background-color: " + cor + "'>");
				strAux.append("<a href='#' onclick='popupAtivos(" + item.getIdItemConfiguracao() + ")' >" + identificacao + "</a>");
				strAux.append("</div>");
				i++;
			}
		} else {
			document.alert(UtilI18N.internacionaliza(request, "MSG04"));
		}

		strAux.append("</div>");
		document.getElementById("divPesquisaItemConfiguracao").setInnerHTML(strAux.toString());

	}

	public void verificarExpiracao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws JobExecutionException {
		VerificaValidadeLicenca verificaValidadeLicenca = new VerificaValidadeLicenca();
		verificaValidadeLicenca.execute(null);
		document.alert(UtilI18N.internacionaliza(request, "pesquisaItemConfiguracao.executadoVerifEnviadoEmailResponsaveis"));
	}

}
