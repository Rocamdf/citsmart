package br.com.centralit.citcorpore.metainfo.ajaxForms;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.framework.ParserRequest;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.metainfo.bean.TableSearchDTO;
import br.com.centralit.citcorpore.metainfo.negocio.TableSearchService;
import br.com.citframework.service.ServiceLocator;

@SuppressWarnings("rawtypes")
public class TableSearchVinc extends AjaxFormAction {
	private static boolean DEBUG = true;

	@Override
	public Class getBeanClass() {
		return TableSearchDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParserRequest parser = new ParserRequest();
		HashMap hashValores = parser.getFormFields(request);
		if (DEBUG) {
			debugValuesFromRequest(hashValores);
		}

		TableSearchService tableSearchService = (TableSearchService) ServiceLocator.getInstance().getService(TableSearchService.class, null);

		TableSearchDTO tableSearchDTO = (TableSearchDTO) document.getBean();
		String retorno = "";
		if (tableSearchDTO.getLoad() == null || !tableSearchDTO.getLoad().equalsIgnoreCase("false")) {
			if (tableSearchDTO.getJsonData() != null) {
				// HashMap map = JSONUtil.convertJsonToMap(tableSearchDTO.getJsonData(), true);
				if (tableSearchDTO.getMatriz() == null || !tableSearchDTO.getMatriz().equalsIgnoreCase("true")) {
					retorno = tableSearchService.findItens(tableSearchDTO, true, hashValores);
				} else {
					retorno = tableSearchService.getInfoMatriz(tableSearchDTO, true, hashValores);
					retorno = "[" + retorno + "]";
				}
			} else {
				if (tableSearchDTO.getMatriz() != null && tableSearchDTO.getMatriz().equalsIgnoreCase("true")) {
					retorno = tableSearchService.getInfoMatriz(tableSearchDTO, true, hashValores);
					retorno = "[" + retorno + "]";
				}
			}
		}

		if (retorno.trim().equalsIgnoreCase("")) {
			retorno = "{\"total\": \"0\",\"rows\":[]}";
		}

		request.setAttribute("json_response", "" + retorno + "");
	}

	public void debugValuesFromRequest(HashMap hashValores) {
		Set set = hashValores.entrySet();
		Iterator i = set.iterator();

		System.out.print("------- VALORES DO REQUEST: -------");
		while (i.hasNext()) {
			Map.Entry me = (Map.Entry) i.next();
			System.out.print("-------------> [" + me.getKey() + "]: [" + me.getValue() + "]");
		}
	}

}
