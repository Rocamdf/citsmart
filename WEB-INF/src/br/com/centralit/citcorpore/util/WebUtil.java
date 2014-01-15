package br.com.centralit.citcorpore.util;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.InformacoesContratoItem;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilStrings;

public class WebUtil {

	public static void setUsuario(UsuarioDTO usuario, HttpServletRequest request) {
		request.getSession().setAttribute(Constantes.getValue("USUARIO_SESSAO") + "_CITCORPORE", usuario);
	}

	public static void setLocale(String locale, HttpServletRequest request) {
		request.getSession().setAttribute("locale", locale);
	}
	
	public static UsuarioDTO getUsuario(HttpServletRequest request) {
		UsuarioDTO user = (UsuarioDTO) request.getSession().getAttribute(Constantes.getValue("USUARIO_SESSAO") + "_CITCORPORE");
		return user;
	}
	
	public static br.com.citframework.dto.Usuario  getUsuarioSistema(HttpServletRequest request) throws Exception{
		br.com.citframework.dto.Usuario usr = new Usuario();
		UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute(Constantes.getValue("USUARIO_SESSAO") + "_CITCORPORE");
		if(usuario != null){
			if(request.getSession().getAttribute("locale") != null && !request.getSession().getAttribute("locale").equals("")){
				usuario.setLocale((String) request.getSession().getAttribute("locale"));
			}else{
				usuario.setLocale("");
			}
			
			Reflexao.copyPropertyValues(usuario, usr);
		}else{
			return null;
		}

		return usr;
	}

	public static boolean isUserInGroup(HttpServletRequest req, String grupo) {
		UsuarioDTO usuario = WebUtil.getUsuario(req);
		if (usuario == null) {
			return false;
		}

		String[] grupos = usuario.getGrupos();
		String grpAux = UtilStrings.nullToVazio(grupo);
		for (int i = 0; i < grupos.length; i++) {
			if (grupos[i] != null) {
				if (grupos[i].trim().equalsIgnoreCase(grpAux.trim())) {
					return true;
				}
			}
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	public static void renderizaFilhos(InformacoesContratoItem itemProntuario,
			JspWriter out) throws IOException {
		if (itemProntuario.getColSubItens() == null)
			return;

		out.print("<div id='divMenu_" + itemProntuario.getNome()
				+ "' style='display:none'>");

		Iterator it = itemProntuario.getColSubItens().iterator();
		InformacoesContratoItem itemProntuarioTemp;
		for (; it.hasNext();) {
			itemProntuarioTemp = (InformacoesContratoItem) it.next();

			boolean subItens = (itemProntuarioTemp.getColSubItens() != null && itemProntuarioTemp
					.getColSubItens().size() > 0);

			out.print("<table width='100%'>");
			out.print("<tr id='trITEMMENU_" + itemProntuarioTemp.getNome()
					+ "'>");
			out.print("<td width='10%'>&nbsp;</td>");
			out.print("<td id='tdITEMMENU_"
					+ itemProntuarioTemp.getNome()
					+ "' style='cursor:pointer' class='bordaNaoSelecionaProntuario' onclick=\"setaAbaSelecionada('"
					+ itemProntuarioTemp.getNome() + "', " + subItens + ", '"
					+ itemProntuarioTemp.getPath() + "', 'tdITEMMENU_"
					+ itemProntuarioTemp.getNome() + "')\">");
			out.print(itemProntuarioTemp.getDescricao());

			WebUtil.renderizaFilhos(itemProntuarioTemp, out);
			out.print("</td>");
			out.print("</tr>");

			out.println("<script>arrayItensMenu[iItemMenu] = 'tdITEMMENU_"
					+ itemProntuarioTemp.getNome() + "';</script>");
			out.println("<script>iItemMenu++;</script>");

			out.print("<tr><td style='height:5px'></td></tr>");
			out.print("</table>");
		}

		out.print("</div>");
	}

	@SuppressWarnings("rawtypes")
	public static void renderizaFilhosSomenteQuestionarios(
			InformacoesContratoItem itemProntuario, JspWriter out)
			throws IOException {
		if (itemProntuario.getColSubItens() == null)
			return;

		out.print("<div id='divMenu2_" + itemProntuario.getNome()
				+ "' style='display:none'>");

		Iterator it = itemProntuario.getColSubItens().iterator();
		InformacoesContratoItem itemProntuarioTemp;
		for (; it.hasNext();) {
			itemProntuarioTemp = (InformacoesContratoItem) it.next();
			if (!UtilStrings.nullToVazio(itemProntuarioTemp.getFuncItem())
					.equalsIgnoreCase("1")) {
				continue;
			}

			boolean subItens = (itemProntuarioTemp.getColSubItens() != null && itemProntuarioTemp
					.getColSubItens().size() > 0);

			out.print("<table width='100%'>");
			out.print("<tr id='trITEMMENU2_" + itemProntuarioTemp.getNome()
					+ "'>");
			out.print("<td width='10%'>&nbsp;</td>");
			out.print("<td id='tdITEMMENU2_"
					+ itemProntuarioTemp.getNome()
					+ "' style='cursor:pointer' class='bordaNaoSelecionaProntuario' onclick=\"setaAbaSel2('"
					+ itemProntuarioTemp.getNome() + "', " + subItens + ", '"
					+ itemProntuarioTemp.getPath() + "', 'tdITEMMENU2_"
					+ itemProntuarioTemp.getNome() + "', false, '"
					+ itemProntuarioTemp.getIdQuestionario() + "')\">");
			out.print(itemProntuarioTemp.getDescricao());

			WebUtil.renderizaFilhosSomenteQuestionarios(itemProntuarioTemp, out);
			out.print("</td>");
			out.print("</tr>");

			out.println("<script>arrayItensMenu2[iItemMenu] = 'tdITEMMENU2_"
					+ itemProntuarioTemp.getNome() + "';</script>");
			out.println("<script>iItemMenu2++;</script>");

			out.print("<tr><td style='height:5px'></td></tr>");
			out.print("</table>");
		}
		out.print("</div>");
	}

	/**
	 * Retorna o Id da Empresa.
	 * 
	 * @param request
	 * @return
	 */
	public static Integer getIdEmpresa(HttpServletRequest req) {
		UsuarioDTO usuario = WebUtil.getUsuario(req);
		return usuario.getIdEmpresa();
	}
	
	public static EmpregadoDTO getColaborador(HttpServletRequest req) throws Exception{
		EmpregadoDTO empregadoDTO = new EmpregadoDTO();
		UsuarioDTO user = new UsuarioDTO();
		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		user = (UsuarioDTO) req.getSession().getAttribute(Constantes.getValue("USUARIO_SESSAO") + "_CITCORPORE");
		empregadoDTO.setIdEmpregado(user.getIdEmpregado());
		empregadoDTO = (EmpregadoDTO) empregadoService.restore(empregadoDTO);
		
		return empregadoDTO;
		
	}
	
	
	/**
	 * Valida se usuario esta ativo na sessao
	 * 
	 * @param request
	 * @return true:usuario esta na sessão || false:usuario não esta na sessão
	 */
	public static Boolean usuarioEstaNaSessao(HttpServletRequest request){
		
		if (WebUtil.getUsuario(request) == null) 
			return false;
		else
			return true;
		
	}
	
	
	/**
	 * Valida se o usuario esta na sessão, podendo direcionar o formulario para tela de login
	 * 
	 * @param request
	 * @param document
	 * @return true: usuario está na sessao ||
	 * 		   false: usuario não esta na sessao e a tela é redirecionada para a tela de login
	 */
	public static Boolean validarSeUsuarioEstaNaSessao(HttpServletRequest request, DocumentHTML document){
		
		if(!usuarioEstaNaSessao(request)){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return false;
		}
		
		return true;

	}
	
}
