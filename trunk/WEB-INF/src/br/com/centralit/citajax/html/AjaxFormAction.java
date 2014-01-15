package br.com.centralit.citajax.html;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilStrings;

public abstract class AjaxFormAction {

	public abstract void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception;

	/**
	 * Utilizado para setar qual classe representa o bean.
	 * 
	 * @return
	 */
	public abstract Class getBeanClass();

	public Object getUsuario(HttpServletRequest request) {
		return (Object) request.getSession().getAttribute("USUARIO");
	}

	public String getFromGed(Integer idControleGed) throws Exception {
		Integer idEmpresa = 1;
		ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
		ControleGEDDTO controleGEDDTO = new ControleGEDDTO();
		controleGEDDTO.setIdControleGED(idControleGed);
		controleGEDDTO = (ControleGEDDTO) controleGedService.restore(controleGEDDTO);
		String pasta = "";
		if (controleGEDDTO != null) {
			pasta = controleGEDDTO.getPasta();
		}

		String PRONTUARIO_GED_DIRETORIO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedDiretorio, null);
		if (PRONTUARIO_GED_DIRETORIO == null || PRONTUARIO_GED_DIRETORIO.trim().equalsIgnoreCase("")) {
			PRONTUARIO_GED_DIRETORIO = "";
		}

		if (PRONTUARIO_GED_DIRETORIO.equalsIgnoreCase("")) {
			PRONTUARIO_GED_DIRETORIO = Constantes.getValue("DIRETORIO_GED");
		}

		if (PRONTUARIO_GED_DIRETORIO == null || PRONTUARIO_GED_DIRETORIO.equalsIgnoreCase("")) {
			PRONTUARIO_GED_DIRETORIO = "/ged";
		}
		String PRONTUARIO_GED_INTERNO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedInterno, "S");
		if (PRONTUARIO_GED_INTERNO == null) {
			PRONTUARIO_GED_INTERNO = "S";
		}
		String prontuarioGedInternoBancoDados = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedInternoBD, "N");
		if (!UtilStrings.isNotVazio(prontuarioGedInternoBancoDados))
			prontuarioGedInternoBancoDados = "N";
		ControleGEDService controleGEDService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
		if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")) {
			if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && "S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) { // Se utiliza GED
				// interno e eh BD
				// FALTA IMPLEMENTAR!
			} else {
				String fileRec = CITCorporeUtil.caminho_real_app + "tempUpload/REC_FROM_GED_" + controleGEDDTO.getIdControleGED() + "." + controleGEDDTO.getExtensaoArquivo();
				CriptoUtils.decryptFile(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged", fileRec, System.getProperties().get("user.dir")
						+ Constantes.getValue("CAMINHO_CHAVE_PRIVADA"));

				return fileRec;
			}
		}
		return null;
	}

	public String i18n_Message(String key, UsuarioDTO usuario) {
		if (usuario != null) {
			if (UtilI18N.internacionaliza(usuario.getLocale(), key) != null) {
				return UtilI18N.internacionaliza(usuario.getLocale(), key);
			}
			return key;
		}
		return key;
	}
}
