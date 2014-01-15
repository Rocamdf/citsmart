package br.com.centralit.citcorpore.ajaxForms;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AutoCompleteDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.citframework.service.ServiceLocator;

import com.google.gson.Gson;

@SuppressWarnings("rawtypes")
public class AutoCompleteSolicitante extends AjaxFormAction {

	public Class getBeanClass() {
		return EmpregadoDTO.class;
	}

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//Corrige o enconding do parâmetro desejado.
		String consulta = new String(request.getParameter("query").getBytes("ISO-8859-1"), "UTF-8");  

		String idContratoStr = request.getParameter("contrato");
		Integer idContrato = null;
		if (idContratoStr != null && !idContratoStr.equals("-1")) {
			idContrato = Integer.parseInt(idContratoStr);
		}

		Collection<EmpregadoDTO> listEmpregadoDto = new ArrayList<EmpregadoDTO>();

		if (idContrato != null) {
			EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);

			listEmpregadoDto = empregadoService.findSolicitanteByNomeAndIdContrato(consulta, idContrato);
		}

		AutoCompleteDTO autoCompleteDTO = new AutoCompleteDTO();
		Gson gson = new Gson();

		List<String> listNome = new ArrayList<String>();
		List<Integer> listIdEmpregado = new ArrayList<Integer>();

		if (listEmpregadoDto != null) {
			for (EmpregadoDTO empregadoDto : listEmpregadoDto) {

				if (empregadoDto.getIdEmpregado() != null) {

					listNome.add(empregadoDto.getNome());
					listIdEmpregado.add(empregadoDto.getIdEmpregado());

				}
			}
		}
		autoCompleteDTO.setQuery(consulta);
		autoCompleteDTO.setSuggestions(listNome);
		autoCompleteDTO.setData(listIdEmpregado);

		String json = "";

		if (request.getParameter("colection") != null) {
			json = gson.toJson(listEmpregadoDto);
		} else {
			json = gson.toJson(autoCompleteDTO);
		}

		request.setAttribute("json_response", json);
	}
}