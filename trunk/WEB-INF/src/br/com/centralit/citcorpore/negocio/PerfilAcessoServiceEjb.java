/** CentralIT - CITSmart. */
package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.PastaDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoMenuDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoSituacaoFaturaDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoSituacaoOSDTO;
import br.com.centralit.citcorpore.integracao.PerfilAcessoDao;
import br.com.centralit.citcorpore.integracao.PerfilAcessoGrupoDao;
import br.com.centralit.citcorpore.integracao.PerfilAcessoMenuDao;
import br.com.centralit.citcorpore.integracao.PerfilAcessoPastaDAO;
import br.com.centralit.citcorpore.integracao.PerfilAcessoSituacaoFaturaDao;
import br.com.centralit.citcorpore.integracao.PerfilAcessoSituacaoOSDao;
import br.com.centralit.citcorpore.integracao.PerfilAcessoUsuarioDAO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;

/**
 * EJB de PerfilAcesso.
 * 
 * @author thays.araujo
 * 
 */
@SuppressWarnings("unchecked")
public class PerfilAcessoServiceEjb extends CrudServicePojoImpl implements PerfilAcessoService {

	private static final long serialVersionUID = -6851273199892919425L;

	private PerfilAcessoDTO perfilAcessoBean;

	private Collection<PerfilAcessoMenuDTO> acessoMenuDto;

	@Override
	public void gerarGridPerfilAcesso(DocumentHTML document, Collection<PerfilAcessoDTO> perfisDeAcessoDaPasta) throws Exception {
		Collection<PerfilAcessoDTO> todosOsPerfisDeAcesso = this.getPerfilAcessoDao().consultarPerfisDeAcessoAtivos();
		if (todosOsPerfisDeAcesso != null && !todosOsPerfisDeAcesso.isEmpty()) {
			int count = 0;
			document.executeScript("count = 0");
			for (PerfilAcessoDTO perfil : todosOsPerfisDeAcesso) {
				count++;

				document.executeScript("restoreRow()");
				document.executeScript("seqSelecionada = " + count);

				String perfilAcesso = (perfil.getNomePerfilAcesso() != null ? perfil.getNomePerfilAcesso() : "");
				String aprovaBaseConhecimento = (perfil.getAprovaBaseConhecimento() != null ? perfil.getAprovaBaseConhecimento() : "S");

				if (perfilAcesso != null) {
					perfilAcesso = perfilAcesso.replaceAll("'", "");
				}

				document.executeScript("setRestorePerfilAcesso('" + perfil.getIdPerfilAcesso() + "'," + "'" + br.com.citframework.util.WebUtil.codificaEnter(perfilAcesso) + "'," + "'"
						+ br.com.citframework.util.WebUtil.codificaEnter(aprovaBaseConhecimento) + "')");

				if (perfisDeAcessoDaPasta != null && !perfisDeAcessoDaPasta.isEmpty()) {
					for (PerfilAcessoDTO perfilAcessoPasta : perfisDeAcessoDaPasta) {
						document.executeScript("atribuirChecked('" + perfilAcessoPasta.getIdPerfilAcesso() + "'," + "'" + br.com.citframework.util.WebUtil.codificaEnter(perfilAcessoPasta.getAprovaBaseConhecimento())
								+ "')");

					}
				}
			}
			document.executeScript("exibeGrid()");
		} else {
			document.executeScript("ocultaGrid()");
		}
	}

	@Override
	public Collection<PerfilAcessoDTO> consultarPerfisDeAcesso(PastaDTO pastaBean) throws ServiceException, Exception {

		if (pastaBean == null) {

			return this.getPerfilAcessoDao().consultarPerfisDeAcessoAtivos();

		} else {

			return this.getPerfilAcessoDao().consultarPerfisDeAcessoAtivos(pastaBean);
		}
	}

	@Override
	public IDto create(IDto model) throws ServiceException, LogicException {
		this.setPerfilAcessoBean(model);

		PerfilAcessoDao perfilAcessoDao = this.getPerfilAcessoDao();

		TransactionControler transactionControler = new TransactionControlerImpl(perfilAcessoDao.getAliasDB());

		try {
			this.validaCreate(this.getPerfilAcessoBean());

			this.getPerfilAcessoDao().setTransactionControler(transactionControler);
			PerfilAcessoMenuDao perfilAcessoMenuDao = new PerfilAcessoMenuDao();
			PerfilAcessoSituacaoOSDao perfilAcessoSituacaoOSDao = new PerfilAcessoSituacaoOSDao();
			PerfilAcessoSituacaoFaturaDao perfilAcessoSituacaoFaturaDao = new PerfilAcessoSituacaoFaturaDao();
			perfilAcessoMenuDao.setTransactionControler(transactionControler);
			perfilAcessoSituacaoOSDao.setTransactionControler(transactionControler);
			perfilAcessoSituacaoFaturaDao.setTransactionControler(transactionControler);

			transactionControler.start();

			this.getPerfilAcessoBean().setDataInicio(UtilDatas.getDataAtual());
			this.setPerfilAcessoBean((PerfilAcessoDTO) this.getPerfilAcessoDao().create(this.getPerfilAcessoBean()));
			for (PerfilAcessoMenuDTO dto : this.getPerfilAcessoBean().getAcessoMenus()) {
				dto.setIdPerfilAcesso(this.getPerfilAcessoBean().getIdPerfilAcesso());
				dto.setDataInicio(UtilDatas.getDataAtual());
				perfilAcessoMenuDao.create(dto);
			}
			if (this.getPerfilAcessoBean().getSituacaoos() != null) {
				for (int i = 0; i < this.getPerfilAcessoBean().getSituacaoos().length; i++) {
					PerfilAcessoSituacaoOSDTO perfilAcessoSituacaoOSDTO = new PerfilAcessoSituacaoOSDTO();
					perfilAcessoSituacaoOSDTO.setIdPerfil(this.getPerfilAcessoBean().getIdPerfilAcesso());
					perfilAcessoSituacaoOSDTO.setSituacaoOs(this.getPerfilAcessoBean().getSituacaoos()[i]);
					perfilAcessoSituacaoOSDTO.setDataInicio(UtilDatas.getDataAtual());
					perfilAcessoSituacaoOSDao.create(perfilAcessoSituacaoOSDTO);
				}
			}
			if (this.getPerfilAcessoBean().getSituacaoFatura() != null) {
				for (int i = 0; i < this.getPerfilAcessoBean().getSituacaoFatura().length; i++) {
					PerfilAcessoSituacaoFaturaDTO perfilAcessoSituacaoFaturaDTO = new PerfilAcessoSituacaoFaturaDTO();
					perfilAcessoSituacaoFaturaDTO.setIdPerfil(this.getPerfilAcessoBean().getIdPerfilAcesso());
					perfilAcessoSituacaoFaturaDTO.setSituacaoFatura(this.getPerfilAcessoBean().getSituacaoFatura()[i]);
					perfilAcessoSituacaoFaturaDTO.setDataInicio(UtilDatas.getDataAtual());
					perfilAcessoSituacaoFaturaDao.create(perfilAcessoSituacaoFaturaDTO);
				}
			}

			transactionControler.commit();
			transactionControler.close();
		} catch (Exception e) {
			e.printStackTrace();
			this.rollbackTransaction(transactionControler, e);
		}
		return this.getPerfilAcessoBean();
	}

	@Override
	public void update(IDto model) throws ServiceException, LogicException {
		this.setPerfilAcessoBean(model);
		PerfilAcessoMenuDao perfilAcessoMenuDao = new PerfilAcessoMenuDao();
		PerfilAcessoDao perfilAcessoDao = new PerfilAcessoDao();
		PerfilAcessoSituacaoOSDao perfilAcessoSituacaoOSDao = new PerfilAcessoSituacaoOSDao();
		PerfilAcessoSituacaoFaturaDao perfilAcessoSituacaoFaturaDao = new PerfilAcessoSituacaoFaturaDao();
		TransactionControler transactionControler = new TransactionControlerImpl(perfilAcessoDao.getAliasDB());

		try {
			this.validaCreate(this.getPerfilAcessoBean());
			perfilAcessoDao.setTransactionControler(transactionControler);
			perfilAcessoMenuDao.setTransactionControler(transactionControler);
			perfilAcessoSituacaoOSDao.setTransactionControler(transactionControler);
			perfilAcessoSituacaoFaturaDao.setTransactionControler(transactionControler);
			transactionControler.start();

			this.getPerfilAcessoBean().setDataInicio(UtilDatas.getDataAtual());
			perfilAcessoDao.update(this.getPerfilAcessoBean());

			List<PerfilAcessoMenuDTO> perfisAcessoMenu = (List<PerfilAcessoMenuDTO>) perfilAcessoMenuDao.findByPerfilAcesso(this.getPerfilAcessoBean().getIdPerfilAcesso());

			if (perfisAcessoMenu != null) {
				for (PerfilAcessoMenuDTO perfilAcessoMenu : perfisAcessoMenu) {
					perfilAcessoMenuDao.delete(perfilAcessoMenu);
				}
			}

			if (this.getPerfilAcessoBean().getAcessoMenus() != null) {
				for (PerfilAcessoMenuDTO dto : this.getPerfilAcessoBean().getAcessoMenus()) {
					dto.setIdPerfilAcesso(this.getPerfilAcessoBean().getIdPerfilAcesso());
					dto.setDataInicio(UtilDatas.getDataAtual());
					perfilAcessoMenuDao.create(dto);
				}
			}

			perfilAcessoSituacaoOSDao.deleteByIdPerfil(this.getPerfilAcessoBean().getIdPerfilAcesso());
			if (this.getPerfilAcessoBean().getSituacaoos() != null) {
				for (int i = 0; i < this.getPerfilAcessoBean().getSituacaoos().length; i++) {
					PerfilAcessoSituacaoOSDTO perfilAcessoSituacaoOSDTO = new PerfilAcessoSituacaoOSDTO();
					perfilAcessoSituacaoOSDTO.setIdPerfil(this.getPerfilAcessoBean().getIdPerfilAcesso());
					perfilAcessoSituacaoOSDTO.setSituacaoOs(this.getPerfilAcessoBean().getSituacaoos()[i]);
					perfilAcessoSituacaoOSDTO.setDataInicio(UtilDatas.getDataAtual());
					perfilAcessoSituacaoOSDao.create(perfilAcessoSituacaoOSDTO);
				}
			}
			perfilAcessoSituacaoFaturaDao.deleteByIdPerfil(this.getPerfilAcessoBean().getIdPerfilAcesso());
			if (this.getPerfilAcessoBean().getSituacaoFatura() != null) {
				for (int i = 0; i < this.getPerfilAcessoBean().getSituacaoFatura().length; i++) {
					PerfilAcessoSituacaoFaturaDTO perfilAcessoSituacaoFaturaDTO = new PerfilAcessoSituacaoFaturaDTO();
					perfilAcessoSituacaoFaturaDTO.setIdPerfil(this.getPerfilAcessoBean().getIdPerfilAcesso());
					perfilAcessoSituacaoFaturaDTO.setSituacaoFatura(this.getPerfilAcessoBean().getSituacaoFatura()[i]);
					perfilAcessoSituacaoFaturaDTO.setDataInicio(UtilDatas.getDataAtual());
					perfilAcessoSituacaoFaturaDao.create(perfilAcessoSituacaoFaturaDTO);
				}
			}

			transactionControler.commit();
			transactionControler.close();
		} catch (Exception e) {
			e.printStackTrace();
			this.rollbackTransaction(transactionControler, e);
		}
	}

	public boolean excluirPerfilDeAcesso(PerfilAcessoDTO perfilDeAcesso) throws Exception {
		this.setPerfilAcessoBean(perfilDeAcesso);

		PerfilAcessoDao perfilAcessoDao = this.getPerfilAcessoDao();
		PerfilAcessoMenuDao perfilAcessoMenuDao = new PerfilAcessoMenuDao();
		PerfilAcessoPastaDAO perfilAcessoPastaDao = new PerfilAcessoPastaDAO();
		PerfilAcessoUsuarioDAO perfilAcessoUsuarioDao = new PerfilAcessoUsuarioDAO();
		PerfilAcessoGrupoDao perfilAcessoGrupoDao = new PerfilAcessoGrupoDao();

		if (perfilAcessoGrupoDao.findByIdPerfil(perfilDeAcesso.getIdPerfilAcesso()) != null || perfilAcessoUsuarioDao.findByIdPerfil(perfilDeAcesso.getIdPerfilAcesso()) != null
				|| perfilAcessoPastaDao.findByIdPerfil(perfilDeAcesso.getIdPerfilAcesso()) != null) {

			return false;

		} else {

			TransactionControler transaction = new TransactionControlerImpl(this.getDao().getAliasDB());
			perfilAcessoDao.setTransactionControler(transaction);
			perfilAcessoMenuDao.setTransactionControler(transaction);

			try {
				transaction.start();

				this.getPerfilAcessoBean().setDataFim(UtilDatas.getDataAtual());
				perfilAcessoDao.update(this.getPerfilAcessoBean());

				perfilAcessoMenuDao.excluirPerfisAcessoMenu(this.getPerfilAcessoBean());

				transaction.commit();
				transaction.close();

				return true;
			} catch (Exception e) {
				e.printStackTrace();
				this.rollbackTransaction(transaction, e);
				return false;
			}
		}
	}

	/**
	 * Configura Bean de PerfilAcesso.
	 * 
	 * @param baseItemConfiguracaoBean
	 * @author valdoilo.damasceno
	 */
	private void setPerfilAcessoBean(IDto perfilAcessobean) {
		this.perfilAcessoBean = (PerfilAcessoDTO) perfilAcessobean;
	}

	/**
	 * Retorna Bean de BaseItemConfiguracao.
	 * 
	 * @return valor do atributo baseItemConfiguracaoBean.
	 * @author valdoilo.damasceno
	 */
	public PerfilAcessoDTO getPerfilAcessoBean() {
		return perfilAcessoBean;
	}

	/**
	 * Retorna DAO de PerfilAcessoDao.
	 * 
	 * @return BaseItemConfiguracaoDAO
	 * @throws ServiceException
	 * @author valdoilo.damasceno
	 */
	public PerfilAcessoDao getPerfilAcessoDao() throws ServiceException {
		return (PerfilAcessoDao) this.getDao();
	}

	/**
	 * Retorna Service de AcessoMenuService.
	 * 
	 * @return ValorService
	 * @throws ServiceException
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public PerfilAcessoMenuService getAcessoMenuService() throws ServiceException, Exception {
		return (PerfilAcessoMenuService) ServiceLocator.getInstance().getService(PerfilAcessoMenuService.class, null);
	}

	/**
	 * @return valor do atributo perfisDeAcesso.
	 */
	public Collection<PerfilAcessoMenuDTO> getAcessoMenu() {
		return acessoMenuDto;
	}

	/**
	 * Define valor do atributo perfisDeAcesso.
	 * 
	 * @param perfisDeAcesso
	 */
	public void setAcessoMenu(Collection<PerfilAcessoMenuDTO> acessoMenu) {
		this.acessoMenuDto = acessoMenu;
	}

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new PerfilAcessoDao();
	}

	@Override
	protected void validaCreate(Object arg0) throws Exception {
	}

	@Override
	protected void validaDelete(Object arg0) throws Exception {
	}

	@Override
	protected void validaFind(Object arg0) throws Exception {
	}

	@Override
	protected void validaUpdate(Object arg0) throws Exception {
	}

	@Override
	public PerfilAcessoDTO listByName(PerfilAcessoDTO obj) throws Exception {
		try {

			return getPerfilAcessoDao().listByName(obj);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean verificarSePerfilAcessoExiste(PerfilAcessoDTO perfilAcesso) throws PersistenceException {
		PerfilAcessoDao perfilAcessoDao = new PerfilAcessoDao();

		return perfilAcessoDao.verificarSePerfilAcessoExiste(perfilAcesso);
	}

	@Override
	public Collection<PerfilAcessoDTO> consultarPerfisDeAcessoAtivos() throws Exception {

		PerfilAcessoDao perfilAcessoDao = new PerfilAcessoDao();

		Collection<PerfilAcessoDTO> perfisAtivos = new ArrayList<PerfilAcessoDTO>();

		perfisAtivos = perfilAcessoDao.consultarPerfisDeAcessoAtivos();

		return perfisAtivos;
	}

	
}
