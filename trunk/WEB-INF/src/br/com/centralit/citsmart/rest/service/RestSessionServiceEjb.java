package br.com.centralit.citsmart.rest.service;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoUsuarioDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.exception.LogicException;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoUsuarioService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citsmart.rest.bean.RestSessionDTO;
import br.com.centralit.citsmart.rest.schema.CtLogin;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.PersistenceEngine;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilDatas;
public class RestSessionServiceEjb extends CrudServicePojoImpl implements RestSessionService {
	private static final HashMap<String,RestSessionDTO> mapSessions = new HashMap<String, RestSessionDTO>();
	
	protected CrudDAO getDao() throws ServiceException {
		return null;
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	@Override
	public RestSessionDTO newSession(HttpServletRequest httpRequest, CtLogin login) throws Exception {
		authentication(httpRequest, login);
		
		RestSessionDTO sessionDto = new RestSessionDTO();
		sessionDto.setCreation(UtilDatas.getDataHoraAtual());
		sessionDto.setHttpSession(httpRequest.getSession());
		
		sessionDto.setMaxTime(1800); 
		mapSessions.put(sessionDto.getSessionID(), sessionDto);
		return sessionDto;
	}

	@Override
	public RestSessionDTO getSession(String sessionID) {
		RestSessionDTO result = mapSessions.get(sessionID);
		if (result != null && !result.isValid()) {
			mapSessions.remove(result.getSessionID());
			result.setHttpSession(null);
			result = null;
		}
		return result;
	}
	
	@SuppressWarnings("unused")
	public CtLogin authentication(HttpServletRequest httpRequest, CtLogin login) throws Exception {
		boolean isAdmin = false;
		if (login != null) {
			if (login.getPassword() == null || login.getPassword().trim().equalsIgnoreCase("")) 
				throw new LogicException("Senha não informada");
			if (login.getUserName() == null || login.getUserName().trim().equalsIgnoreCase("")) 
				throw new LogicException("Usuário não informado");
		} else {
			throw new LogicException("Usuário não informado");
		}

		UsuarioDTO usrDto = new UsuarioDTO();
		UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);

		String metodoAutenticacao = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.METODO_AUTENTICACAO_Pasta, "2");

		isAdmin = ("admin".equalsIgnoreCase(login.getUserName()) || "consultor".equalsIgnoreCase(login.getUserName()));


		boolean veririficaVazio = usuarioService.listSeVazio();

		String algoritmo = br.com.citframework.util.Constantes.getValue("ALGORITMO_CRIPTOGRAFIA_SENHA");
		if (algoritmo == null || !algoritmo.trim().equalsIgnoreCase("")) {

			algoritmo = "SHA-1";
		}

		login.setPassword(CriptoUtils.generateHash(login.getPassword(), algoritmo));

		if (!veririficaVazio && "admin".equalsIgnoreCase(login.getUserName())) {

			usrDto.setDataInicio(UtilDatas.getDataAtual());
			usrDto.setLogin(login.getUserName());
			usrDto.setSenha(login.getPassword());
			usrDto.setNomeUsuario("Administrador");
			usrDto.setStatus("A");

			usuarioService.createFirs(usrDto);
		}
		UsuarioDTO usuarioBean = usuarioService.restoreByLogin(login.getUserName(), login.getPassword());
		if (usuarioBean == null) 
			throw new LogicException("Usuário não encontrado e/ou senha inválida");

		if (metodoAutenticacao == null || metodoAutenticacao.trim().equalsIgnoreCase("")) 
			throw new LogicException("Metodo de autenticação não configurado! Favor revisar os parametros do sistema!");

		if (metodoAutenticacao != null) {
			if (usuarioBean.getStatus().equalsIgnoreCase("A") && login.getPassword().equals(usuarioBean.getSenha())) {
				if (usuarioBean.getIdEmpresa() == null) {
					usuarioBean.setIdEmpresa(1);
				}
				Usuario usuarioFramework = new Usuario();
				UsuarioDTO usr = new UsuarioDTO();
				usr.setIdUsuario(usuarioBean.getIdUsuario());
				usr.setNomeUsuario(usuarioBean.getNomeUsuario());
				usr.setIdGrupo(usuarioBean.getIdGrupo());
				usr.setIdEmpresa(usuarioBean.getIdEmpresa());
				usr.setIdEmpregado(usuarioBean.getIdEmpregado());
				usr.setLogin(usuarioBean.getLogin());
				usr.setStatus(usuarioBean.getStatus());
				usr.setIdPerfilAcessoUsuario(getProfile(usuarioBean.getIdUsuario()));
				// utilizado para log
				PersistenceEngine.setUsuarioSessao(usuarioBean);
				Reflexao.copyPropertyValues(usr, usuarioFramework);
				br.com.citframework.util.WebUtil.setUsuario(usuarioFramework, httpRequest);
				GrupoService grupoSegService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
				Collection<GrupoDTO> colGrupos = grupoSegService.getGruposByPessoa(usuarioBean.getIdEmpregado());
				GrupoDTO grpSeg;

				String[] grupos = null;
				if (colGrupos != null) {
					grupos = new String[colGrupos.size()];
					for (int i = 0; i < colGrupos.size(); i++) {
						grpSeg = (GrupoDTO) ((List) colGrupos).get(i);
						grupos[i] = grpSeg.getSigla();
					}
				} else {
					grupos = new String[1];
					grupos[0] = "";
				}

				usr.setGrupos(grupos);
				usr.setColGrupos(colGrupos);
				usr.setLocale(ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.IDIOMAPADRAO," "));
				
				EmpregadoDTO empregadoDto = new EmpregadoDao().restoreByIdEmpregado(usuarioBean.getIdEmpregado());
				if (empregadoDto != null && empregadoDto.getIdUnidade() != null) 
					usr.setIdUnidade(empregadoDto.getIdUnidade());

				WebUtil.setUsuario(usr, httpRequest);
			} else {
				throw new LogicException("Usuário não encontrado");
			}
		}
		
		return login;
	}	
	
	private Integer getProfile(Integer idUsuario) throws ServiceException, Exception {
		PerfilAcessoUsuarioService perfilAcessoService = (PerfilAcessoUsuarioService) ServiceLocator.getInstance().getService(PerfilAcessoUsuarioService.class, null);
		PerfilAcessoUsuarioDTO perfilAcessoDTO = new PerfilAcessoUsuarioDTO();
		perfilAcessoDTO.setIdUsuario(idUsuario);
		perfilAcessoDTO = perfilAcessoService.listByIdUsuario(perfilAcessoDTO);
		if (perfilAcessoDTO == null) {
			return null;
		} else {
			return perfilAcessoDTO.getIdPerfilAcesso();
		}
	}	
	
}
