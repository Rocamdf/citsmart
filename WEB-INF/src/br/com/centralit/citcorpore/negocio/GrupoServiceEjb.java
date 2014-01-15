package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.bpm.dto.PermissoesFluxoDTO;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.ContratosGruposDTO;
import br.com.centralit.citcorpore.bean.EventoGrupoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmailDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.LimiteAlcadaDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoGrupoDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.integracao.ContratoDao;
import br.com.centralit.citcorpore.integracao.ContratosGruposDAO;
import br.com.centralit.citcorpore.integracao.EventoGrupoDao;
import br.com.centralit.citcorpore.integracao.GrupoDao;
import br.com.centralit.citcorpore.integracao.GrupoEmailDao;
import br.com.centralit.citcorpore.integracao.GrupoEmpregadoDao;
import br.com.centralit.citcorpore.integracao.LimiteAlcadaDao;
import br.com.centralit.citcorpore.integracao.NotificacaoGrupoDao;
import br.com.centralit.citcorpore.integracao.PerfilAcessoGrupoDao;
import br.com.centralit.citcorpore.integracao.PermissoesFluxoDao;
import br.com.centralit.citcorpore.integracao.ServicoContratoDao;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;

/**
 * @author CentralIT
 */
@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
public class GrupoServiceEjb extends CrudServicePojoImpl implements GrupoService {

	private static final long serialVersionUID = 5889199013385676937L;

	private GrupoDTO grupoBean;

	private PerfilAcessoGrupoDao perfilAcessoGrupoDao = new PerfilAcessoGrupoDao();

	private TransactionControler transactionControler;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new GrupoDao();
	}

	public Collection findGruposAtivos() {
		ArrayList<Condition> condicoes = new ArrayList<Condition>();
		condicoes.add(new Condition("dataFim", "IS", null));
		try {
			return getGrupoDao().findByCondition(condicoes, null);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public IDto create(IDto model, HttpServletRequest request) throws ServiceException, LogicException {
		GrupoDTO grupoDto = (GrupoDTO) model;

		TransactionControler tc = new TransactionControlerImpl(getDao().getAliasDB());

		try {
			this.validaCreate(model);
			ArrayList<GrupoEmpregadoDTO> listaEmpregados = (ArrayList<GrupoEmpregadoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
					GrupoEmpregadoDTO.class, "empregadosSerializados", request);
			
			ArrayList<GrupoEmailDTO> listaEmails = (ArrayList<GrupoEmailDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
					GrupoEmailDTO.class, "emailsSerializados", request);

			GrupoDao grupoDao = new GrupoDao();
			PerfilAcessoGrupoDao perfilAcessogrupoDao = new PerfilAcessoGrupoDao();
			GrupoEmpregadoDao grupoEmpregadoDao = new GrupoEmpregadoDao();
			GrupoEmailDao grupoEmailDao = new GrupoEmailDao();
			PermissoesFluxoDao permissoesFluxoDao = new PermissoesFluxoDao();
			ContratosGruposDAO contratosGruposDao = new ContratosGruposDAO();

			//PermissoesFluxoDTO permissoesFluxoDTO = new PermissoesFluxoDTO();

			grupoDao.setTransactionControler(tc);
			perfilAcessogrupoDao.setTransactionControler(tc);
			grupoEmpregadoDao.setTransactionControler(tc);
			permissoesFluxoDao.setTransactionControler(tc);
			contratosGruposDao.setTransactionControler(tc);

			PerfilAcessoGrupoDTO dto = new PerfilAcessoGrupoDTO();

			tc.start();

			grupoDto.setDataInicio(UtilDatas.getDataAtual());

			grupoDto = ((GrupoDTO) grupoDao.create(grupoDto));

			dto.setIdGrupo(grupoDto.getIdGrupo());
			dto.setIdPerfilAcessoGrupo(grupoDto.getIdPerfilAcessoGrupo());
			dto.setDataInicio(grupoDto.getDataInicio());

			perfilAcessogrupoDao.create(dto);

			if (grupoDto.getPermissoesFluxos() != null) {
				for (PermissoesFluxoDTO permissoesFluxoAux : grupoDto.getPermissoesFluxos()) {
					permissoesFluxoAux.setIdGrupo(grupoDto.getIdGrupo());
					PermissoesFluxoDTO permissoesFluxoAux2 = (PermissoesFluxoDTO) permissoesFluxoDao.restore(permissoesFluxoAux);
					if (permissoesFluxoAux2 == null){
					    permissoesFluxoDao.create(permissoesFluxoAux);
					}else{
					    permissoesFluxoDao.update(permissoesFluxoAux);
					}
				}
			}

			if (listaEmpregados != null && !listaEmpregados.isEmpty()) {

				for (GrupoEmpregadoDTO grupoEmpregadoDto : listaEmpregados) {
					grupoEmpregadoDto.setIdGrupo(grupoDto.getIdGrupo());
					grupoEmpregadoDto.setIdEmpregado(grupoEmpregadoDto.getIdEmpregado());
					grupoEmpregadoDto.setEnviaEmail(grupoEmpregadoDto.getEnviaEmail());
					GrupoEmpregadoDTO grupoEmpregadoAux = (GrupoEmpregadoDTO) grupoEmpregadoDao.restore(grupoEmpregadoDto);
					if (grupoEmpregadoAux == null){
					    grupoEmpregadoDao.create(grupoEmpregadoDto);
					}
				}
			}
			
			if (listaEmails != null && !listaEmails.isEmpty()) {

				for (GrupoEmailDTO grupoEmailDto : listaEmails) {
					grupoEmailDto.setIdGrupo(grupoDto.getIdGrupo());
					GrupoEmailDTO grupoEmailAux = (GrupoEmailDTO) grupoEmailDao.restore(grupoEmailDto);
					if (grupoEmailAux == null){
					    grupoEmailDao.create(grupoEmailDto);
					}
				}
			}

			if (grupoDto.getIdContrato() != null) {

				for (int i = 0; i < grupoDto.getIdContrato().length; i++) {

					ContratosGruposDTO contratosGruposDTO = new ContratosGruposDTO();

					contratosGruposDTO.setIdGrupo(grupoDto.getIdGrupo());
					contratosGruposDTO.setIdContrato(grupoDto.getIdContrato()[i]);
					if (contratosGruposDTO.getIdContrato() != null){
					    contratosGruposDao.create(contratosGruposDTO);
					}
				}
			}

			tc.commit();
			tc.close();

		} catch (Exception e) {
			e.printStackTrace();
			this.rollbackTransaction(tc, e);
		}
		return grupoDto;
	}

	@Override
	public void update(IDto model, HttpServletRequest request) throws ServiceException, LogicException {
		GrupoDTO grupoDto = (GrupoDTO) model;
		TransactionControler tc = new TransactionControlerImpl(getDao().getAliasDB());
		try {
			this.validaUpdate(model);
			//ArrayList<GrupoEmpregadoDTO> listaEmpregados = (ArrayList<GrupoEmpregadoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
			//		GrupoEmpregadoDTO.class, "empregadosSerializados", request);		
			ArrayList<GrupoEmailDTO> listaEmails = (ArrayList<GrupoEmailDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
					GrupoEmailDTO.class, "emailsSerializados", request);
			GrupoDao grupoDao = new GrupoDao();
			PerfilAcessoGrupoDao perfilAcessogrupoDao = new PerfilAcessoGrupoDao();
			GrupoEmpregadoDao grupoEmpregadoDao = new GrupoEmpregadoDao();
			PermissoesFluxoDao permissoesFluxoDao = new PermissoesFluxoDao();
			ContratosGruposDAO contratosGruposDao = new ContratosGruposDAO();
			GrupoEmailDao grupoEmailDao = new GrupoEmailDao();

			grupoDao.setTransactionControler(tc);
			perfilAcessogrupoDao.setTransactionControler(tc);
			grupoEmpregadoDao.setTransactionControler(tc);
			grupoEmailDao.setTransactionControler(tc);
			permissoesFluxoDao.setTransactionControler(tc);
			contratosGruposDao.setTransactionControler(tc);

			//PermissoesFluxoDTO permissoesFluxoDTO = new PermissoesFluxoDTO();
			PerfilAcessoGrupoDTO dto = new PerfilAcessoGrupoDTO();

			tc.start();

			grupoDao.update(grupoDto);

			GrupoDTO grupo = (GrupoDTO) grupoDao.restore(grupoDto);

			dto.setIdPerfilAcessoGrupo(grupoDto.getIdPerfilAcessoGrupo());
			dto.setIdGrupo(grupoDto.getIdGrupo());
			dto.setDataInicio(grupoDto.getDataInicio());

			perfilAcessogrupoDao.delete(dto);

			perfilAcessogrupoDao.create(dto);

			permissoesFluxoDao.deleteByIdGrupo(grupoDto.getIdGrupo());
			if (grupoDto.getPermissoesFluxos() != null) {
				for (PermissoesFluxoDTO permissoesFluxoAux : grupoDto.getPermissoesFluxos()) {
					permissoesFluxoAux.setIdGrupo(grupoDto.getIdGrupo());

					permissoesFluxoDao.create(permissoesFluxoAux);
				}
			}
//			if (listaEmpregados != null && !listaEmpregados.isEmpty()) {
//				grupoEmpregadoDao.deleteByIdGrupo(grupoDto.getIdGrupo());
//				int count = 0;
//				for (GrupoEmpregadoDTO grupoEmpregadoDto : listaEmpregados) {
//					count++;
//					grupoEmpregadoDto.setIdGrupo(grupo.getIdGrupo());
//					grupoEmpregadoDto.setIdEmpregado(grupoEmpregadoDto.getIdEmpregado());
//
//					grupoEmpregadoDao.create(grupoEmpregadoDto);
//					
//					if (count == 100){
//						tc.commit();
//						count = 0;
//					}
//				}
//			}
			
			if (listaEmails != null && !listaEmails.isEmpty()) {
				grupoEmailDao.deleteByIdGrupo(grupoDto.getIdGrupo());
				for (GrupoEmailDTO grupoEmailDto: listaEmails) {
					grupoEmailDto.setIdGrupo(grupo.getIdGrupo());

					grupoEmailDao.create(grupoEmailDto);
				}
			}
			

			contratosGruposDao.deleteByIdGrupo(grupoDto.getIdGrupo());
			if (grupoDto.getIdContrato() != null) {
				for (int i = 0; i < grupoDto.getIdContrato().length; i++) {
					ContratosGruposDTO contratosGruposDTO = new ContratosGruposDTO();
					contratosGruposDTO.setIdGrupo(grupoDto.getIdGrupo());
					contratosGruposDTO.setIdContrato(grupoDto.getIdContrato()[i]);
					if (contratosGruposDTO.getIdContrato() != null){
					    contratosGruposDao.create(contratosGruposDTO);
					}
				}
			}

			tc.commit();
			tc.close();

		} catch (Exception e) {
			e.printStackTrace();
			this.rollbackTransaction(tc, e);
		}

	}

	@Override
	public void delete(IDto model, DocumentHTML document) throws ServiceException, LogicException {
		GrupoDTO grupoDto = (GrupoDTO) model;

		if(!validaExclusaoGrupoNosParametros(grupoDto, document)){
			return;
		}
			
		TransactionControler tc = new TransactionControlerImpl(getDao().getAliasDB());
		try {
			Integer idGrupo = 0;
			idGrupo = grupoDto.getIdGrupo();
			PerfilAcessoGrupoDao perfilAcessogrupoDao = new PerfilAcessoGrupoDao();
			PerfilAcessoGrupoDTO dto = new PerfilAcessoGrupoDTO();
			GrupoDao grupoDao = new GrupoDao();
			GrupoEmpregadoDao grupoEmpregadoDao = new GrupoEmpregadoDao();
			GrupoEmailDao grupoEmailDao = new GrupoEmailDao();
			PermissoesFluxoDao permissoesFluxoDao = new PermissoesFluxoDao();
			ContratoDao contratoDao = new ContratoDao();
			EventoGrupoDao eventoGrupoDao = new EventoGrupoDao();
			NotificacaoGrupoDao notificacaoGrupoDao = new NotificacaoGrupoDao();
			LimiteAlcadaDao limiteAlcadaDao = new LimiteAlcadaDao();
			ServicoContratoDao servicoContratoDao = new ServicoContratoDao();
			
			grupoDao.setTransactionControler(tc);
			perfilAcessogrupoDao.setTransactionControler(tc);
			grupoEmpregadoDao.setTransactionControler(tc);
			permissoesFluxoDao.setTransactionControler(tc);
			contratoDao.setTransactionControler(tc);
			eventoGrupoDao.setTransactionControler(tc);
			notificacaoGrupoDao.setTransactionControler(tc);
			limiteAlcadaDao.setTransactionControler(tc);
			servicoContratoDao.setTransactionControler(tc);
			
			tc.start();
			
			Collection<PermissoesFluxoDTO> permissaoGrupo = (Collection<PermissoesFluxoDTO>) permissoesFluxoDao.findByIdGrupo(idGrupo);
			Collection<GrupoEmailDTO> grupoDeEmail = (Collection<GrupoEmailDTO>) grupoEmailDao.findByIdGrupo(idGrupo);
			Collection<GrupoEmpregadoDTO> grupoDeEmpregados = (Collection<GrupoEmpregadoDTO>) grupoEmpregadoDao.findByIdGrupo(idGrupo);
			Collection<ContratoDTO> contratoDTO = (Collection<ContratoDTO>) contratoDao.findByIdGrupo(idGrupo);
			Collection<EventoGrupoDTO> eventoGrupoDTO = (Collection<EventoGrupoDTO>) eventoGrupoDao.findByIdGrupo(idGrupo);
			Collection<LimiteAlcadaDTO> limiteAlcadaDTO = (Collection<LimiteAlcadaDTO>) limiteAlcadaDao.findByIdGrupo(idGrupo);
			Collection<ServicoContratoDTO> colecaoServicosVinculados = servicoContratoDao.findAtivosByIdGrupo(grupoDto.getIdGrupo());
			
			if(eventoGrupoDTO != null){
				document.alert(i18n_Message("grupo.deletar.eventoGrupo"));
				return;
			}
			
			if(limiteAlcadaDTO != null){
				document.alert(i18n_Message("grupo.deletar.limiteAlcadaGrupo"));
				return;
			}
						
			if(contratoDTO != null){
				document.alert(i18n_Message("grupo.deletar.grupoContrato"));
				return;
			}
			
			if (grupoDeEmpregados != null) {
				document.alert(i18n_Message("grupo.deletar.grupoEmpregado"));
				return;
			}
			
			if(grupoDeEmail != null){
				document.alert(i18n_Message("grupo.deletar.grupoEmail"));
				return;
			}
			
			if(colecaoServicosVinculados != null){
				document.alert(i18n_Message("grupo.deletar.servicosGrupo"));
				return;
			}
			
			if (permissaoGrupo != null) {
				for (PermissoesFluxoDTO permissoesFluxo : permissaoGrupo) {
					if (permissoesFluxo.getCriar().equalsIgnoreCase("S")) {
						document.alert(i18n_Message("grupo.deletar.grupoFluxo"));
						return;
					}
					if (permissoesFluxo.getDelegar().equalsIgnoreCase("S")) {
						document.alert(i18n_Message("grupo.deletar.grupoFluxo"));
						return;
					}
					if (permissoesFluxo.getExecutar().equalsIgnoreCase("S")) {
						document.alert(i18n_Message("grupo.deletar.grupoFluxo"));
						return;
					}
					if (permissoesFluxo.getSuspender().equalsIgnoreCase("S")) {
						document.alert(i18n_Message("grupo.deletar.grupoFluxo"));
						return;
					}
					permissoesFluxoDao.delete(permissoesFluxo);
				}
				grupoDto.setSigla(null);
				grupoDto.setDataFim(UtilDatas.getDataAtual());
				grupoDao.update(grupoDto);
				dto.setIdGrupo(grupoDto.getIdGrupo());
				perfilAcessogrupoDao.updateDataFim(dto);
				notificacaoGrupoDao.deleteByIdGrupo(idGrupo);
				document.alert(i18n_Message("MSG07"));
				document.executeScript("deleteAllRows();");
				HTMLForm form = document.getForm("form");
				form.clear();
				document.executeScript("limpar_LOOKUP_GRUPO()");
			}
			
			tc.commit();
			tc.close();
		} catch (Exception e) {
			e.printStackTrace();
			this.rollbackTransaction(tc, e);
		}
	}

	/**
	 * Configura Bean de PerfilAcesso.
	 * 
	 * @param baseItemConfiguracaoBean
	 * @author valdoilo.damasceno
	 */
	private void setGrupoBean(IDto grupoBean) {
		this.grupoBean = (GrupoDTO) grupoBean;
	}

	/**
	 * Retorna Bean de BaseItemConfiguracao.
	 * 
	 * @return valor do atributo baseItemConfiguracaoBean.
	 * @author valdoilo.damasceno
	 */
	public GrupoDTO getGrupoDto() {
		return grupoBean;
	}

	/**
	 * Retorna DAO de AcessoMenuDao.
	 * 
	 * @return valor do atributo valorDao.
	 * @author valdoilo.damasceno
	 */
	public PerfilAcessoGrupoDao getPerfilAcessoGrupoDao() {
		return perfilAcessoGrupoDao;
	}

	/**
	 * Retorna DAO de PerfilAcessoDao.
	 * 
	 * @return BaseItemConfiguracaoDAO
	 * @throws ServiceException
	 * @author valdoilo.damasceno
	 */
	public GrupoDao getGrupoDao() throws ServiceException {
		return (GrupoDao) this.getDao();
	}

	/**
	 * Retorna Service de AcessoMenuService.
	 * 
	 * @return ValorService
	 * @throws ServiceException
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public PerfilAcessoGrupoService getPerfilAcessoGrupoService() throws ServiceException, Exception {
		return (PerfilAcessoGrupoService) ServiceLocator.getInstance().getService(PerfilAcessoGrupoService.class, null);
	}

	@Override
	protected void validaCreate(Object arg0) throws Exception {
		GrupoDTO grupoDto = (GrupoDTO) arg0;
		GrupoDao grupoDao = new GrupoDao();
		if(grupoDao.restoreBySigla(grupoDto.getSigla()) != null){
			throw new LogicException("Atenção, Já existe um grupo com esta sigla digite uma diferente.");
		}
	}

	@Override
	protected void validaDelete(Object arg0) throws Exception {
	}

	@Override
	protected void validaFind(Object arg0) throws Exception {
	}

	@Override
	protected void validaUpdate(Object arg0) throws Exception {
		GrupoDTO grupoDto = (GrupoDTO) arg0;
		GrupoDao grupoDao = new GrupoDao();
		if(grupoDao.restoreBySigla(grupoDto) != null){
			throw new LogicException("Atenção, Já existe um grupo com esta sigla digite uma diferente.");
		}
	}

	public Collection listaGrupoEmpregado() throws Exception {
		try {
			GrupoDao grupoDao = (GrupoDao) getDao();
			return grupoDao.listarGrupoEmpregado();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection listaGrupoUsuario() throws Exception {
		try {
			GrupoDao grupoDao = (GrupoDao) getDao();
			return grupoDao.listarGrupoUsuario();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection getGruposByPessoa(Integer idEmpregado) throws LogicException, RemoteException, ServiceException {
		GrupoDao grupoDao = (GrupoDao) getDao();
		try {
			return (List) grupoDao.getGruposByIdEmpregado(idEmpregado);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	public Collection getGruposByEmpregado(Integer idEmpregado) throws LogicException, RemoteException, ServiceException {
		GrupoDao grupoDao = (GrupoDao) getDao();
		try {
			return (List) grupoDao.getGruposByIdEmpregadoAll(idEmpregado);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection<GrupoDTO> listGruposServiceDesk() throws Exception {
		GrupoDao grupoDao = (GrupoDao) getDao();
		return grupoDao.listGruposServiceDesk();
	}
	
	public Collection<GrupoDTO> listGruposServiceDeskByIdContrato(Integer idContratoParm) throws Exception {
		GrupoDao grupoDao = (GrupoDao) getDao();
		return grupoDao.listGruposServiceDeskByIdContrato(idContratoParm);
	}	

	public boolean verificarSeGrupoExiste(GrupoDTO grupo) throws PersistenceException {
		GrupoDao grupoDao = new GrupoDao();
		return grupoDao.verificarSeGrupoExiste(grupo);
	}

	public Collection<GrupoDTO> listGrupoByIdContrato(Integer idContrato) throws Exception {
		GrupoDao grupoDao = new GrupoDao();

		return grupoDao.listGrupoByIdContrato(idContrato);
	}

	@Override
	public Collection getGruposByIdEmpregado(Integer idEmpregado) throws Exception {
		GrupoDao dao = new GrupoDao();
		return dao.getGruposByIdEmpregado(idEmpregado);
	}
	
	/**
	 * @see GrupoService#listarEmailsPorGrupo(Integer)
	 */
	@Override
	public Collection<String> listarEmailsPorGrupo(Integer idGrupo) throws Exception {
		GrupoDao grupoDao = new GrupoDao();
		return grupoDao.listarEmailsPorGrupo(idGrupo);
	}
	
	@Override
	public GrupoDTO listGrupoById(Integer idGrupo) throws Exception {
		GrupoDao grupoDao = new GrupoDao();
		return grupoDao.listGrupoById(idGrupo);
	}


	@Override
	public Collection listarGruposAtivos() throws Exception {
		
		return new GrupoDao().listarGruposAtivos();
	}

	@Override
	public Collection<GrupoDTO> listGruposComite() throws Exception {
		GrupoDao grupoDao = (GrupoDao) getDao();
		return grupoDao.listGruposComite();
	}

	@Override
	public Collection<GrupoDTO> listGruposNaoComite() throws Exception {
		GrupoDao grupoDao = (GrupoDao) getDao();
		return grupoDao.listGruposNaoComite();
	}
	public Collection<GrupoDTO> listAllGrupos() throws Exception {
		GrupoDao grupoDao = (GrupoDao) getDao();
		return grupoDao.listAllGrupos();
	}

	
	@Override
	public Collection<String> listarPessoasEmailPorGrupo(Integer idGrupo) throws Exception {
		GrupoDao grupoDao = new GrupoDao();
		return grupoDao.listarPessoasEmailPorGrupo(idGrupo);
	}

	@Override
	public Collection<GrupoDTO> listaGruposAtivos() throws Exception {
		GrupoDao grupoDao = new GrupoDao();
		return grupoDao.listaGruposAtivos();
	}

	@Override
	public Collection<GrupoDTO> listaGrupoEmpregado(Integer idEmpregado) throws Exception {
		GrupoDao grupoDao = new GrupoDao();
		return grupoDao.listaGrupoEmpregado(idEmpregado);
	}

	
	public Boolean validaExclusaoGrupoNosParametros(GrupoDTO grupoDto, DocumentHTML document){

		Enumerados.ParametroSistema[] parametrosArray = Enumerados.ParametroSistema.values();
		
		Integer idGrupo = 0;
		String nomeGrupo = "";
		
		//tratar para o parametro de id 45 - ldap, os ids podem ser passados com ;
		String ldapStr = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_LDAP, "0");
		String[] ldapArray = ldapStr.split(";");
		for (int i = 0; i < ldapArray.length; i++) {
			if(grupoDto.getIdGrupo().intValue() == Integer.parseInt(ldapArray[i])){
				document.alert(i18n_Message("grupo.deletar.parametro") + " 45 " + i18n_Message("grupo.deletar.parametroContinuacao"));
				return false;
			}
		}
		
		//tratar para todos os parametros do Enumerados
		for(int i = 0; i < parametrosArray.length; i++) {
			String nomeParametro = parametrosArray[i].name();
			if(nomeParametro.contains("ID_GRUPO_PADRAO")){
				try {
					if(ParametroUtil.getValor(parametrosArray[i]) != null){
						idGrupo = Integer.parseInt(ParametroUtil.getValor(parametrosArray[i]));
					}
					if(idGrupo.intValue() == grupoDto.getIdGrupo().intValue()){
						document.alert(i18n_Message("grupo.deletar.parametro") + " " +  parametrosArray[i].id() + " " + i18n_Message("grupo.deletar.parametroContinuacao"));
						return false;
					}
					
				} catch (Exception e) {
					System.out.println("Parametro do sistema de ID "+ parametrosArray[i].id() + " null ou fora do padrão numérico");
				}
			} else if(nomeParametro.contains("NOME_GRUPO_PADRAO")){
				try {
					if(ParametroUtil.getValor(parametrosArray[i]) != null){
						nomeGrupo = ParametroUtil.getValor(parametrosArray[i]);
					}
					if(nomeGrupo.equalsIgnoreCase(grupoDto.getNome())){
						document.alert(i18n_Message("grupo.deletar.parametro") + " " +  parametrosArray[i].id() + " " + i18n_Message("grupo.deletar.parametroContinuacao"));
						return false;
					}
				} catch (Exception e) {
					System.out.println("Parametro do sistema de ID "+ parametrosArray[i].id() + " null");
				}
				
			}
		}
		
		return true;
			
	}

	
	@Override
	public Collection<GrupoDTO> listGruposPorUsuario(int idUsuario) {
		GrupoDao dao = new GrupoDao();
		Collection<GrupoDTO> list = new ArrayList();
		try {
			list = dao.listGruposPorUsuario(idUsuario);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
	}
}
