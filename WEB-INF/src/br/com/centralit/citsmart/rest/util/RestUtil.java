package br.com.centralit.citsmart.rest.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.ExecucaoSolicitacaoService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citsmart.rest.bean.RestSessionDTO;
import br.com.centralit.citsmart.rest.service.RestExecutionService;
import br.com.centralit.citsmart.rest.service.RestLogService;
import br.com.centralit.citsmart.rest.service.RestOperationService;
import br.com.centralit.citsmart.rest.service.RestParameterService;
import br.com.centralit.citsmart.rest.service.RestPermissionService;
import br.com.centralit.citsmart.rest.service.RestSessionService;
import br.com.citframework.dto.Usuario;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.Reflexao;

public class RestUtil {
	
    public static String stackToString(Exception e) {
        try {
          StringWriter sw = new StringWriter();
          PrintWriter pw = new PrintWriter(sw);
          e.printStackTrace(pw);
          return "------\n" + sw.toString() + "------\n";
        }
        catch(Exception e2) {
          return "bad stackToString";
        }
    }	

    public static br.com.citframework.dto.Usuario getUsuarioSistema(RestSessionDTO restSessionDto) throws Exception{
		if (restSessionDto == null)
			return null;
		
		br.com.citframework.dto.Usuario usr = new Usuario();
		UsuarioDTO usuario = (UsuarioDTO) restSessionDto.getHttpSession().getAttribute(Constantes.getValue("USUARIO_SESSAO") + "_CITCORPORE");
		if(usuario != null){
			if(restSessionDto.getHttpSession().getAttribute("locale") != null && !restSessionDto.getHttpSession().getAttribute("locale").equals("")){
				usuario.setLocale((String) restSessionDto.getHttpSession().getAttribute("locale"));
			}else{
				usuario.setLocale("");
			}
			Reflexao.copyPropertyValues(usuario, usr);
		}else{
			return null;
		}

		return usr;
	}
	
	public static RestSessionService getRestSessionService(RestSessionDTO restSessionDto) {
		try {
			return (RestSessionService) ServiceLocator.getInstance().getService(RestSessionService.class, getUsuarioSistema(restSessionDto));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static RestOperationService getRestOperationService(RestSessionDTO restSessionDto) {
		try {
			return (RestOperationService) ServiceLocator.getInstance().getService(RestOperationService.class, getUsuarioSistema(restSessionDto));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static RestPermissionService getRestPermissionService(RestSessionDTO restSessionDto) {
		try {
			return (RestPermissionService) ServiceLocator.getInstance().getService(RestPermissionService.class, getUsuarioSistema(restSessionDto));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static RestExecutionService getRestExecutionService(RestSessionDTO restSessionDto) {
		try {
			return (RestExecutionService) ServiceLocator.getInstance().getService(RestExecutionService.class, getUsuarioSistema(restSessionDto));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static RestLogService getRestLogService(RestSessionDTO restSessionDto) {
		try {
			return (RestLogService) ServiceLocator.getInstance().getService(RestLogService.class, getUsuarioSistema(restSessionDto));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static RestParameterService getRestParameterService(RestSessionDTO restSessionDto) {
		try {
			return (RestParameterService) ServiceLocator.getInstance().getService(RestParameterService.class, getUsuarioSistema(restSessionDto));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ExecucaoSolicitacaoService getExecucaoSolicitacaoService(RestSessionDTO restSessionDto) {
		try {
			return (ExecucaoSolicitacaoService) ServiceLocator.getInstance().getService(ExecucaoSolicitacaoService.class, getUsuarioSistema(restSessionDto));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean isValidSession(RestSessionDTO restSessionDto) {
		return restSessionDto != null && restSessionDto.isValid();
	}
	
	public static EmpregadoDTO getEmpregadoByLogin(String login) {
		 try {
			UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
			UsuarioDTO usuarioDto = usuarioService.restoreByLogin(login);
			if (usuarioDto == null)
				return null;
			
			EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
			return empregadoService.restoreByIdEmpregado(usuarioDto.getIdEmpregado());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	 public static String i18n_Message(RestSessionDTO restSessionDto, String key){
		   if(restSessionDto.getUser() != null){
			   String locale = "";
			   if (restSessionDto.getUser().getLocale() != null && !restSessionDto.getUser().getLocale().trim().equals(""))
				   locale = restSessionDto.getUser().getLocale();
 			   return  UtilI18N.internacionaliza(locale, key);
		   }
		   return key;
	 }

}
