package br.com.centralit.citcorpore.ajaxForms;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.BICategoriasDTO;
import br.com.centralit.citcorpore.bean.BIConsultaDTO;
import br.com.centralit.citcorpore.bean.DataManagerObjectsDTO;
import br.com.centralit.citcorpore.metainfo.bean.CamposObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.negocio.CamposObjetoNegocioService;
import br.com.centralit.citcorpore.metainfo.negocio.ObjetoNegocioService;
import br.com.centralit.citcorpore.negocio.BICategoriasService;
import br.com.centralit.citcorpore.negocio.BIConsultaService;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilStrings;

public class ListagemConsultasObjects extends AjaxFormAction{

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap hashValores = getFormFields(request);
		String idStr = (String) hashValores.get("ID");
		int id = -1;
		try {
			if (idStr != null && !idStr.equalsIgnoreCase("-1")){
				idStr = UtilStrings.apenasNumeros(idStr);
			}
			id = Integer.parseInt(idStr);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		BICategoriasService biCategoriasService = (BICategoriasService) ServiceLocator.getInstance().getService(BICategoriasService.class, null);
		BIConsultaService biConsultaService = (BIConsultaService) ServiceLocator.getInstance().getService(BIConsultaService.class, null);
		String strCab = "";
		if (id == -1) {
			strCab += "[{";
			strCab += "\"id\":-1,";
			strCab += "\"text\":\"" + UtilI18N.internacionaliza(request, "listagemConsultas.consultas") + "\",";
			strCab += "\"children\":[";
			Collection colObjsNeg = biCategoriasService.findSemPai();
			if (colObjsNeg != null) {
				boolean bPrim = true;
				for (Iterator it = colObjsNeg.iterator(); it.hasNext();) {
					BICategoriasDTO biCategoriasDTO = (BICategoriasDTO) it.next();
					if (!bPrim) {
						strCab += ",";
					}
					strCab += "{";
					strCab += "\"id\":\"G" + biCategoriasDTO.getIdCategoria() + "\",";
					strCab += "\"text\":\"" + biCategoriasDTO.getNomeCategoria() + "\",";
					strCab += "\"state\":\"closed\"";
					strCab += "}";
					bPrim = false;
				}
			}
			strCab += "]";
			strCab += "}]";
		} else {
			Collection col = biCategoriasService.findByIdCategoriaPai(id);
			Collection col2 = biConsultaService.findByIdCategoria(id);
			if (col != null || col2 != null) {
				if (col == null) col = new ArrayList();
				if (col2 == null) col2 = new ArrayList();
				strCab += "[";
				boolean bPrim = true;
				for (Iterator it = col.iterator(); it.hasNext();) {
					if (!bPrim) {
						strCab += ",";
					}
					BICategoriasDTO biCategoriasDTO = (BICategoriasDTO) it.next();
					// String strTam = "";
					strCab += "{";
					strCab += "\"id\":\"G" + biCategoriasDTO.getIdCategoria() + "\",";
					strCab += "\"text\":\"" + biCategoriasDTO.getNomeCategoria() + "\"";
					strCab += "}";
					bPrim = false;
				}
				bPrim = true;
				for (Iterator it = col2.iterator(); it.hasNext();) {
					if (!bPrim) {
						strCab += ",";
					}
					BIConsultaDTO biConsultaDTO = (BIConsultaDTO) it.next();
					// String strTam = "";
					strCab += "{";
					strCab += "\"id\":\"" + biConsultaDTO.getTipoConsulta() + biConsultaDTO.getIdConsulta() + "\",";
					strCab += "\"text\":\"" + biConsultaDTO.getNomeConsulta() + "\"";
					strCab += "}";
					bPrim = false;
				}				
				strCab += "]";
			}
		}

		request.setAttribute("json_retorno", strCab);
	}

	@Override
	public Class getBeanClass() {
		return DataManagerObjectsDTO.class;
	}

	private HashMap getFormFields(HttpServletRequest req) {
		try {
			req.setCharacterEncoding("ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			System.out.println("PROBLEMA COM CODIFICACAO DE CARACTERES!!! [AjaxProcessEvent.getFormFields()]");
			e.printStackTrace();
		}
		HashMap formFields = new HashMap();
		Enumeration en = req.getParameterNames();
		String[] strValores;
		while (en.hasMoreElements()) {
			String nomeCampo = (String) en.nextElement();
			strValores = req.getParameterValues(nomeCampo);
			if (strValores.length == 0) {
				formFields.put(nomeCampo.toUpperCase(), UtilStrings.decodeCaracteresEspeciais(req.getParameter(nomeCampo)));
			} else {
				if (strValores.length == 1) {
					formFields.put(nomeCampo.toUpperCase(), UtilStrings.decodeCaracteresEspeciais(strValores[0]));
				} else {
					formFields.put(nomeCampo.toUpperCase(), strValores);
				}
			}
		}
		return formFields;
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
