package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AutoCompleteDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;

import com.google.gson.Gson;

public class AutoCompleteServico extends AjaxFormAction {
	public Class getBeanClass() {
		return EmpregadoDTO.class;
	}

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//Corrige o enconding do parâmetro desejado.
		String consulta = new String(request.getParameter("query").getBytes("ISO-8859-1"), "UTF-8");  
		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
		
		String tipoDemanda = request.getParameter("tipoDemanda");
		Integer idTipoDemanda = null;
		if(tipoDemanda != null){
			idTipoDemanda = Integer.parseInt(tipoDemanda);
		}
		
		String contrato = request.getParameter("contrato");
		Integer idContrato = null;
		if(contrato != null){
			idContrato = Integer.parseInt(contrato);
		}
		
		String categoria = request.getParameter("categoria");		
		Integer idCategoria = null;
		if(categoria != null){
			idCategoria = Integer.parseInt(categoria);
		}
		
		Collection colRetorno = servicoService.findByNomeAndContratoAndTipoDemandaAndCategoria(idTipoDemanda, idContrato, idCategoria, consulta);
		AutoCompleteDTO autoCompleteDTO = new AutoCompleteDTO();
		Gson gson = new Gson();
		List lst = new ArrayList();
		List lstVal = new ArrayList();
		
		if (colRetorno != null){
			for (Iterator it = colRetorno.iterator(); it.hasNext();){
				ServicoDTO servicoDto = (ServicoDTO) it.next();
				if (servicoDto.getIdServico()!= null) {
					lst.add(servicoDto.getNomeServico()); 
					lstVal.add(servicoDto.getIdServico());
				}
			}
		}
		autoCompleteDTO.setQuery(consulta);
		autoCompleteDTO.setSuggestions(lst);
		autoCompleteDTO.setData(lstVal);
		
		String json = gson.toJson(autoCompleteDTO);
		request.setAttribute("json_response", json);
	}
}