/**
 * 
 */
package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.CalendarioDTO;
import br.com.centralit.citcorpore.bean.ExcecaoCalendarioDTO;
import br.com.centralit.citcorpore.bean.JornadaTrabalhoDTO;
import br.com.centralit.citcorpore.negocio.CalendarioService;
import br.com.centralit.citcorpore.negocio.ExcecaoCalendarioService;
import br.com.centralit.citcorpore.negocio.JornadaTrabalhoService;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.WebUtil;

/**
 * @author breno.guimaraes
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class Calendario extends AjaxFormAction {

	private JornadaTrabalhoService getJornadaTrabalhoService() throws ServiceException, Exception {
		return (JornadaTrabalhoService) ServiceLocator.getInstance().getService(JornadaTrabalhoService.class, null);
	}

	private CalendarioService getCalendarioService() throws ServiceException, Exception {
		return (CalendarioService) ServiceLocator.getInstance().getService(CalendarioService.class, null);
	}

	private ExcecaoCalendarioService getExcecaoCalendarioService() throws ServiceException, Exception {
		return (ExcecaoCalendarioService) ServiceLocator.getInstance().getService(ExcecaoCalendarioService.class, null);
	}

	@Override
	public Class getBeanClass() {
		return CalendarioDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		preencheCombos(document, request);
	}

	private void preencheCombos(DocumentHTML document, HttpServletRequest request) throws LogicException, ServiceException, Exception {
		Collection<JornadaTrabalhoDTO> jornadas = getJornadaTrabalhoService().listarJornadasAtivas();
		geraCombo(document, "idJornadaSeg", jornadas, request);
		geraCombo(document, "idJornadaTer", jornadas, request);
		geraCombo(document, "idJornadaQua", jornadas, request);
		geraCombo(document, "idJornadaQui", jornadas, request);
		geraCombo(document, "idJornadaSex", jornadas, request);
		geraCombo(document, "idJornadaSab", jornadas, request);
		geraCombo(document, "idJornadaDom", jornadas, request);
		geraCombo(document, "idjornadaexcecao", jornadas, request);
	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		CalendarioDTO calendario = (CalendarioDTO) document.getBean();
		calendario = (CalendarioDTO) getCalendarioService().restore(calendario);
		HTMLForm form = document.getForm("form");
		preencheCombos(document, request);
		Collection<ExcecaoCalendarioDTO> excecoes = (Collection<ExcecaoCalendarioDTO>) getExcecaoCalendarioService().findByIdCalendario(calendario.getIdCalendario());

		form.clear();
		preencheCombos(document, request);
		form.setValues(calendario);

		if (excecoes != null) {
			String serializado = WebUtil.serializeObjects(excecoes);
			document.executeScript("restaurarTabelaExcecoes('" + serializado + "')");
		}
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CalendarioDTO calendario = (CalendarioDTO) document.getBean();
		ArrayList<ExcecaoCalendarioDTO> listaItens = (ArrayList) WebUtil.deserializeCollectionFromRequest(ExcecaoCalendarioDTO.class, "listaExecoesSerializada", request);

		if (calendario.getConsideraFeriados() == null || calendario.getDescricao() == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.camposObrigatorios"));
			return;
		}

		if (calendario.getIdCalendario() == null) {
			if (this.getCalendarioService().verificaSeExisteCalendario(calendario)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}
			CalendarioDTO criado = (CalendarioDTO) getCalendarioService().create(calendario);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else {
			if (this.getCalendarioService().verificaSeExisteCalendario(calendario)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}
			getCalendarioService().update(calendario);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}

		getExcecaoCalendarioService().deleteByIdCalendario(calendario.getIdCalendario());
		if (listaItens != null) {
			for (ExcecaoCalendarioDTO e : listaItens) {
				e.setIdCalendario(calendario.getIdCalendario());
				getExcecaoCalendarioService().create(e);
			}
		}

		HTMLForm form = document.getForm("form");
		form.clear();
		document.executeScript("tabExcecoes.limpaLista();");
		document.executeScript("setConsideraFeriados();");
	}
	
	/**
	 * @author euler.ramos
	 * @param idCalendario
	 * @return
	 * @throws Exception
	 */
	public void excluir(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CalendarioDTO calendario = (CalendarioDTO) document.getBean();
		if ((calendario.getIdCalendario() != null)&&(calendario.getIdCalendario().intValue() > 0)) {
			StringBuilder resposta = new StringBuilder();
			resposta.append(this.getCalendarioService().verificaSePermiteExcluir(document,request,calendario));
			if (resposta.toString().equalsIgnoreCase("excluir")){
				this.getExcecaoCalendarioService().deleteByIdCalendario(calendario.getIdCalendario());
				this.getCalendarioService().delete(calendario);
				document.alert(UtilI18N.internacionaliza(request, "MSG07"));
				HTMLForm form = document.getForm("form");
				form.clear();
			} else {
				document.alert(resposta.toString());
			}
		}
	}

	private void geraCombo(DocumentHTML document, String idComponente, Collection<JornadaTrabalhoDTO> lista, HttpServletRequest request) throws Exception {
		HTMLSelect comboTipoDemanda = (HTMLSelect) document.getSelectById(idComponente);
		comboTipoDemanda.removeAllOptions();

		comboTipoDemanda.addOption(String.valueOf(0), UtilI18N.internacionaliza(request, "calendario.naoHaJornadaTrabalho"));
		if (lista != null) {
			for (JornadaTrabalhoDTO j : lista) {
				comboTipoDemanda.addOption(j.getIdJornada().toString(), StringEscapeUtils.escapeJavaScript(j.getDescricao()));
			}
		}
	}
}
