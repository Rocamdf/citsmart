/**
 * 
 */
package br.com.centralit.citcorpore.ajaxForms;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.ReleaseDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.CitBrowser;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citgerencial.util.Util;
import br.com.citframework.util.Constantes;


/**
 * @author valdoilo.damasceno
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class Index extends AjaxFormAction {
	private static String locale = null;
	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		
		if (usuario == null) {
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		
		Reader reader = null;
		HttpSession session = ((HttpServletRequest) request).getSession();
		if(session.getAttribute("locale") != null)
			locale = session.getAttribute("locale").toString();
		else
			locale = "pt";
		if(locale.equals(""))
			locale = "pt";
		String separator = System.getProperty("file.separator");
		String path = CITCorporeUtil.caminho_real_app + "XMLs" + separator + "release_"+locale+".xml";

		try {
			// reader = new FileReader(path);
			reader = (Reader) new InputStreamReader(new FileInputStream(path), "ISO-8859-1");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		XStream x = new XStream(new DomDriver("ISO-8859-1"));
		Collection<ReleaseDTO> listRelease = (Collection<ReleaseDTO>) x.fromXML(reader);

		this.preencherComboVersao(document, request, response, listRelease);

		try {
			if(reader != null){
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (listRelease != null && !listRelease.isEmpty()) {

			StringBuilder stringBuilder = new StringBuilder();

			stringBuilder.append("<div id='historicoRelease' style='overflow: auto; text-align: justify;'>");

			int countRelease = 0;
			for (ReleaseDTO releaseDto : listRelease) {

				stringBuilder.append("<div id='release" + countRelease + "' style='height:100%' >");
				stringBuilder.append("<div style='overflow: auto;' >");
				/*
				 * stringBuilder.append("<span style='font-weight:bold;'>"); stringBuilder.append(UtilI18N.internacionaliza(request, "release.versao")); stringBuilder.append(" ");
				 * stringBuilder.append(releaseDto.getVersao()); stringBuilder.append("</span>");
				 */
				stringBuilder.append("</br>");

				if (releaseDto.getConteudo() != null && !releaseDto.getConteudo().isEmpty()) {
					int i = 0;
					for (String item : releaseDto.getConteudo()) {
						++i;
						stringBuilder.append("<div>");
						stringBuilder.append("<span  style='font-weight:bold;'>");
						stringBuilder.append(i);
						stringBuilder.append(" ");
						stringBuilder.append("</span>");
						stringBuilder.append(Util.encodeHTML(item));
						stringBuilder.append("</div>");
						stringBuilder.append("</br>");
					}
				}
				stringBuilder.append("</div>");
				stringBuilder.append("</div>");
				++countRelease;
				break;
			}

			stringBuilder.append("</div>");

			document.getElementById("divRelease").setInnerHTML(stringBuilder.toString());

		}

		String mensagem = request.getParameter("mensagem");
		if (mensagem != null && !mensagem.isEmpty()) {
			document.alert(mensagem);
		}
		
		CitBrowser browser = new CitBrowser(request);
		if (browser.valido()<1){
			//document.alert(UtilI18N.internacionaliza(WebUtil.getUsuarioSistema(request).getLocale(), "login.incompativelComNavegador"));
			document.getElementById("content").setInnerHTML("<label style='font-size: 14px !important; line-height: 24px; color:red !important;'>"+UtilI18N.internacionaliza(WebUtil.getUsuarioSistema(request).getLocale(),"login.incompativelComNavegador")+"</label>");
			request.getSession().invalidate();
		}
	}

	public void preencherComboVersao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, Collection<ReleaseDTO> listRelease) throws Exception {
		if (listRelease != null && !listRelease.isEmpty()) {
			HTMLSelect comboVersao = (HTMLSelect) document.getSelectById("versao");

			if (comboVersao != null) {
				comboVersao.removeAllOptions();
				comboVersao.addOptions(listRelease, "versao", "versao", null);
			}
		}

	}

	public void buscaHistoricoPorVersao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReleaseDTO releaseDTO = (ReleaseDTO) document.getBean();

		Reader reader = null;

		String separator = System.getProperty("file.separator");
		String path = CITCorporeUtil.caminho_real_app + "XMLs" + separator + "release_"+locale+".xml";

		try {
			// reader = new FileReader(path);
			reader = (Reader) new InputStreamReader(new FileInputStream(path), "ISO-8859-1");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		XStream x = new XStream(new DomDriver("ISO-8859-1"));
		Collection<ReleaseDTO> listRelease = (Collection<ReleaseDTO>) x.fromXML(reader);

		try {
			if(reader != null){
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (listRelease != null && !listRelease.isEmpty()) {

			StringBuilder stringBuilder = new StringBuilder();

			stringBuilder.append("<div id='historicoRelease' style='overflow: auto;'>");

			int countRelease = 0;

			for (ReleaseDTO releaseDto : listRelease) {

				if (releaseDTO.getVersao().equals(releaseDto.getVersao())) {
					stringBuilder.append("<div id='release" + countRelease + "' style='width: 100%; text-align: justify;'>");
					stringBuilder.append("<div>");
					/*
					 * stringBuilder.append("<span style='font-weight:bold;'>"); stringBuilder.append(UtilI18N.internacionaliza(request, "release.versao")); stringBuilder.append(" ");
					 * stringBuilder.append(releaseDto.getVersao()); stringBuilder.append("</span>");
					 */
					stringBuilder.append("</br>");

					if (releaseDto.getConteudo() != null && !releaseDto.getConteudo().isEmpty()) {
						int i = 0;
						for (String item : releaseDto.getConteudo()) {
							++i;
							stringBuilder.append("<div>");
							stringBuilder.append("<span  style='font-weight:bold;'>");
							stringBuilder.append(i);
							stringBuilder.append(" ");
							stringBuilder.append("</span>");
							stringBuilder.append(Util.encodeHTML(item));
							stringBuilder.append("</div>");
							stringBuilder.append("</br>");
						}
					}
					stringBuilder.append("</div>");
					stringBuilder.append("</div>");

					++countRelease;
				}

				stringBuilder.append("</div>");

				document.getElementById("divRelease").setInnerHTML(stringBuilder.toString());
			}
		}
	}

	@Override
	public Class getBeanClass() {
		return ReleaseDTO.class;
	}

}
