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

@SuppressWarnings({ "rawtypes", "unchecked" })
public class TableSearch extends AjaxFormAction {
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
		// ReturnTableSearchDTO returnTableSearchDTO = tableSearchService.findItens(tableSearchDTO, false, hashValores);
		hashValores.put("IDISPLAYLENGTH", "" + tableSearchDTO.getRows());
		String str = tableSearchService.findItens(tableSearchDTO, false, hashValores);

		/*
		 * Gson gson = new Gson();
		 * 
		 * String json = gson.toJson(returnTableSearchDTO);
		 */
		// request.setAttribute("json_response", "[" + str + "]");
		request.setAttribute("json_response", str);
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
