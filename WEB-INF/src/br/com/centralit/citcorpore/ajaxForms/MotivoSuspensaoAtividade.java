package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.MotivoSuspensaoAtividadeDTO;
import br.com.centralit.citcorpore.negocio.MotivoSuspensaoAtividadeService;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings("rawtypes")
public class MotivoSuspensaoAtividade extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	}

	@Override
	public Class getBeanClass() {
		return MotivoSuspensaoAtividadeDTO.class;
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MotivoSuspensaoAtividadeDTO motivoSuspensaoAtividadeDTO = (MotivoSuspensaoAtividadeDTO) document.getBean();
		MotivoSuspensaoAtividadeService motivoSuspensaoAtividadeService = (MotivoSuspensaoAtividadeService) ServiceLocator.getInstance().getService(MotivoSuspensaoAtividadeService.class,
				WebUtil.getUsuarioSistema(request));

		if (motivoSuspensaoAtividadeDTO.getIdMotivo() == null || motivoSuspensaoAtividadeDTO.getIdMotivo().intValue() == 0) {
			boolean resultado = motivoSuspensaoAtividadeService.jaExisteRegistroComMesmoNome(motivoSuspensaoAtividadeDTO);
			if (resultado == true) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}
			motivoSuspensaoAtividadeService.create(motivoSuspensaoAtividadeDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else {
			motivoSuspensaoAtividadeService.update(motivoSuspensaoAtividadeDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}
		HTMLForm form = document.getForm("form");
		form.clear();

		document.executeScript("limpar_LOOKUP_MOTIVOSUSPENSAOATIVIDADE()");
	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MotivoSuspensaoAtividadeDTO motivoSuspensaoAtividadeDTO = (MotivoSuspensaoAtividadeDTO) document.getBean();
		MotivoSuspensaoAtividadeService motivoSuspensaoAtividadeService = (MotivoSuspensaoAtividadeService) ServiceLocator.getInstance().getService(MotivoSuspensaoAtividadeService.class,
				WebUtil.getUsuarioSistema(request));

		motivoSuspensaoAtividadeDTO = (MotivoSuspensaoAtividadeDTO) motivoSuspensaoAtividadeService.restore(motivoSuspensaoAtividadeDTO);

		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(motivoSuspensaoAtividadeDTO);

	}

	public void atualizaData(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MotivoSuspensaoAtividadeDTO motivoSuspensaoAtividadeDTO = (MotivoSuspensaoAtividadeDTO) document.getBean();
		MotivoSuspensaoAtividadeService motivoSuspensaoAtividadeService = (MotivoSuspensaoAtividadeService) ServiceLocator.getInstance().getService(MotivoSuspensaoAtividadeService.class,
				WebUtil.getUsuarioSistema(request));
		if (motivoSuspensaoAtividadeDTO.getIdMotivo().intValue() > 0) {
			motivoSuspensaoAtividadeDTO.setDataFim(UtilDatas.getDataAtual());

			motivoSuspensaoAtividadeService.update(motivoSuspensaoAtividadeDTO);

		}

		HTMLForm form = document.getForm("form");
		form.clear();
		document.alert(UtilI18N.internacionaliza(request, "MSG07"));

		document.executeScript("limpar_LOOKUP_MOTIVOSUSPENSAOATIVIDADE()");

	}

}
