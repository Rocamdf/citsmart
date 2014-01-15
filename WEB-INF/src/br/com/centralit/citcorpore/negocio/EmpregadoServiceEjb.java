package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoUsuarioDTO;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.centralit.citcorpore.integracao.GrupoEmpregadoDao;
import br.com.centralit.citcorpore.integracao.PerfilAcessoUsuarioDAO;
import br.com.centralit.citcorpore.integracao.ProblemaDAO;
import br.com.centralit.citcorpore.integracao.UsuarioDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;

/**
 * @author CentralIT
 */
@SuppressWarnings({ "unused", "rawtypes" })
public class EmpregadoServiceEjb extends CrudServicePojoImpl implements EmpregadoService {

	private static final long serialVersionUID = -2253183314661440900L;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new EmpregadoDao();
	}

	@Override
	protected void validaCreate(Object obj) throws Exception {
	}

	@Override
	protected void validaDelete(Object obj) throws Exception {
		SolicitacaoServicoServiceEjb solicitacaoServicoService = new SolicitacaoServicoServiceEjb();
		EmpregadoDTO empAux = (EmpregadoDTO) obj;
		if (solicitacaoServicoService.temSolicitacaoServicoAbertaDoEmpregado(empAux.getIdEmpregado())) {
			throw new LogicException(i18n_Message("colaborador.existemSolicitacoesNomeDesteEmpregado"));
		}
	}

	@Override
	protected void validaUpdate(Object obj) throws Exception {

		EmpregadoDTO empregado = (EmpregadoDTO) obj;
		UsuarioServiceEjb usuarioServiceEjb = new UsuarioServiceEjb();
		UsuarioDTO usuarioNovaSituacao = usuarioServiceEjb.restoreByIdEmpregado(empregado.getIdEmpregado());
		if (usuarioNovaSituacao != null) {
			if (empregado.getIdSituacaoFuncional() == 1) {
				// ativado
				usuarioNovaSituacao.setStatus("A");
			} else {
				// desativado
				usuarioNovaSituacao.setStatus("I");
			}

			usuarioServiceEjb.updateNotNull(usuarioNovaSituacao);
		}
	}

	@Override
	protected void validaFind(Object obj) throws Exception {
	}

	public EmpregadoDTO restoreEmpregadoSeAtivo(EmpregadoDTO empregadoDto) {
		try {
			return ((EmpregadoDao) getDao()).restoreEmpregadoSeAtivo(empregadoDto);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return new EmpregadoDTO();
	}

	@Override
	public EmpregadoDTO calcularCustos(EmpregadoDTO empregado) throws ServiceException, RemoteException, LogicException {
		double valorCustoTotal = 0;
		double valorSalario = 0;
		double custoHora = 0;
		if (empregado.getValorSalario() == null) {
			empregado.setValorSalario(new Double(0));
		}
		valorSalario = empregado.getValorSalario().doubleValue();
		if (empregado.getTipo().equalsIgnoreCase("E")) { // CLT
			valorCustoTotal = valorSalario * 1.78; // 78% de encargos
		} else if (empregado.getTipo().equalsIgnoreCase("P")) {// PJ
			valorCustoTotal = valorSalario * 1.1; // 10% de encargos
		} else if (empregado.getTipo().equalsIgnoreCase("F")) {// Free Lancer
			valorCustoTotal = valorSalario;
		} else if (empregado.getTipo().equalsIgnoreCase("S")) {// Estagio
			valorCustoTotal = valorSalario;
		} else if (empregado.getTipo().equalsIgnoreCase("X")) {// Socio
			valorCustoTotal = valorSalario;
		}
		// Acrescenta 25% de encargos na produtividade.
		if (empregado.getValorProdutividadeMedia() == null) {
			empregado.setValorProdutividadeMedia(new Double(0));
		}
		if (empregado.getValorPlanoSaudeMedia() == null) {
			empregado.setValorPlanoSaudeMedia(new Double(0));
		}
		if (empregado.getValorVRefMedia() == null) {
			empregado.setValorVRefMedia(new Double(0));
		}
		if (empregado.getValorVTraMedia() == null) {
			empregado.setValorVTraMedia(new Double(0));
		}
		valorCustoTotal = valorCustoTotal + (empregado.getValorProdutividadeMedia().doubleValue() * 1.25);
		valorCustoTotal = valorCustoTotal + empregado.getValorPlanoSaudeMedia().doubleValue();
		valorCustoTotal = valorCustoTotal + empregado.getValorVRefMedia().doubleValue();
		valorCustoTotal = valorCustoTotal + empregado.getValorVTraMedia().doubleValue();
		custoHora = valorCustoTotal / 168;
		empregado.setCustoPorHora(new Double(custoHora));
		empregado.setCustoTotalMes(new Double(valorCustoTotal));
		return empregado;
	}

	@Override
	public Collection<EmpregadoDTO> listEmpregadosByIdGrupo(Integer idGrupo) throws ServiceException, RemoteException {
		try {
			EmpregadoDao dao = (EmpregadoDao) getDao();
			return dao.listEmpregadosByIdGrupo(idGrupo);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<EmpregadoDTO> listEmpregadosByIdUnidade(Integer idUnidade) throws ServiceException, RemoteException {
		try {
			EmpregadoDao dao = (EmpregadoDao) getDao();
			return dao.listEmpregadosByIdUnidade(idUnidade);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection listarIdEmpregados(Integer limit, Integer offset) throws Exception {
		try {
			EmpregadoDao empregadoDao = (EmpregadoDao) getDao();
			return empregadoDao.listarIdEmpregados(limit, offset);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public EmpregadoDTO restoreByIdEmpregado(Integer idEmpregado) throws Exception {
		try {
			EmpregadoDao empregadoDao = (EmpregadoDao) getDao();
			return empregadoDao.restoreByIdEmpregado(idEmpregado);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public EmpregadoDTO restoreEmpregadosAtivosById(Integer idEmpregado) {
		try {
			EmpregadoDao empregadoDao = (EmpregadoDao) getDao();
			return empregadoDao.restoreEmpregadoAtivoById(idEmpregado);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new EmpregadoDTO();
	}

	/**
	 * @return valor do atributo usuarioService.
	 * @throws Exception
	 * @throws ServiceException
	 */
	public UsuarioService getUsuarioService() throws ServiceException, Exception {
		return (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
	}

	@Override
	public void update(IDto model) throws ServiceException, LogicException {
		EmpregadoDTO empregadoDto = (EmpregadoDTO) model;

		TransactionControler tc = new TransactionControlerImpl(getDao().getAliasDB());

		EmpregadoDao empregadoDao = new EmpregadoDao();
		UsuarioDao usuarioDao = new UsuarioDao();

		try {
			validaUpdate(model);

			empregadoDao.setTransactionControler(tc);
			usuarioDao.setTransactionControler(tc);

			tc.start();

			UsuarioServiceEjb usuarioServiceEjb = new UsuarioServiceEjb();
			UsuarioDTO usuarioDto = usuarioServiceEjb.restoreByIdEmpregado(empregadoDto.getIdEmpregado());

			if (usuarioDto != null) {
				usuarioDto.setNomeUsuario(empregadoDto.getNome());
				usuarioDao.updateNotNull(usuarioDto);
			}

			empregadoDao.update(empregadoDto);

			tc.commit();
			tc.close();

		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
		}
	}

	@Override
	public Collection<EmpregadoDTO> listEmpregadosGrupo(Integer idEmpregado, Integer idGrupo) throws Exception {
		try {
			EmpregadoDao empregadoDao = (EmpregadoDao) getDao();
			return empregadoDao.listEmpregadosGrupo(idEmpregado, idGrupo);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteEmpregado(IDto model) throws ServiceException, Exception {
		EmpregadoDTO empregadoDto = (EmpregadoDTO) model;

		TransactionControler tc = new TransactionControlerImpl(getDao().getAliasDB());

		GrupoEmpregadoDao grupoEmpregadoDao = new GrupoEmpregadoDao();
		UsuarioDao usuarioDao = new UsuarioDao();
		PerfilAcessoUsuarioDAO perfilAcessoUsuarioDao = new PerfilAcessoUsuarioDAO();
		EmpregadoDao empregadoDao = new EmpregadoDao();
		try {
			validaUpdate(model);
			validaDelete(model);

			empregadoDao.setTransactionControler(tc);
			grupoEmpregadoDao.setTransactionControler(tc);
			usuarioDao.setTransactionControler(tc);
			perfilAcessoUsuarioDao.setTransactionControler(tc);

			tc.start();

			Integer idEmpregado = 0;
			idEmpregado = empregadoDto.getIdEmpregado();
			UsuarioDTO usuarioDto = new UsuarioDTO();
			PerfilAcessoUsuarioDTO perfilAcessoUsuarioDTO = new PerfilAcessoUsuarioDTO();
			usuarioDto = (UsuarioDTO) usuarioDao.restoreByIdEmpregadosDeUsuarios(idEmpregado);

			if (usuarioDto != null) {
				usuarioDto = (UsuarioDTO) usuarioDao.restore(usuarioDto);
				usuarioDto.setStatus("I");
				usuarioDao.update(usuarioDto);

				perfilAcessoUsuarioDTO.setIdUsuario(usuarioDto.getIdUsuario());
				perfilAcessoUsuarioDTO = (PerfilAcessoUsuarioDTO) perfilAcessoUsuarioDao.restorePerfilAcessoUsuario(perfilAcessoUsuarioDTO);

				if (perfilAcessoUsuarioDTO != null) {

					perfilAcessoUsuarioDao.delete(perfilAcessoUsuarioDTO);
				}
			}

			empregadoDto.setDataFim(UtilDatas.getDataAtual());
			empregadoDao.update(model);

			EmpregadoDTO empregadoDTO = (EmpregadoDTO) model;

			tc.commit();
			tc.close();

		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
		}

	}

	@Override
	public void delete(EmpregadoDTO empregado) throws ServiceException, Exception {

	}

	public void updateNotNull(IDto dto) {
		try {
			((EmpregadoDao) getDao()).updateNotNull(dto);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public IDto create(IDto model) throws ServiceException, LogicException {

		CrudDAO crudDao = getDao();

		TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());

		try {

			validaCreate(model);

			crudDao.setTransactionControler(tc);

			tc.start();

			model = crudDao.create(model);

			tc.commit();
			tc.close();

			return model;
		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
		}
		return model;
	}

	@Override
	public Integer consultaUnidadeDoEmpregado(Integer idEmpregado) throws Exception {
		try {

			EmpregadoDao empregadoDao = (EmpregadoDao) getDao();

			return empregadoDao.consultaUnidadeDoEmpregado(idEmpregado);

		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<EmpregadoDTO> listEmailContrato(Integer idContrato) throws ServiceException {
		try {
			EmpregadoDao empregadoDao = (EmpregadoDao) getDao();
			return empregadoDao.listEmailContrato(idContrato);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<EmpregadoDTO> listEmailContrato() throws ServiceException {
		try {
			EmpregadoDao empregadoDao = (EmpregadoDao) getDao();
			return empregadoDao.listEmailContrato();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public EmpregadoDTO listEmpregadoContrato(Integer idContrato, String email) throws ServiceException {
		try {
			EmpregadoDao empregadoDao = (EmpregadoDao) getDao();
			return empregadoDao.listEmpregadoContrato(idContrato, email);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public EmpregadoDTO listEmpregadoContrato(String email) throws ServiceException {
		try {
			EmpregadoDao empregadoDao = (EmpregadoDao) getDao();
			return empregadoDao.listEmpregadoContrato(email);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection listarEmailsNotificacoesConhecimento(Integer idConhecimento) throws Exception {
		EmpregadoDao empregadoDao = (EmpregadoDao) getDao();
		return empregadoDao.listarEmailsNotificacoesConhecimento(idConhecimento);
	}

	@Override
	public boolean verificarEmpregadosAtivos(EmpregadoDTO obj) throws Exception {
		EmpregadoDao empregadoDao = (EmpregadoDao) getDao();
		return empregadoDao.verificarEmpregadosAtivos(obj);
	}

	public EmpregadoDTO restoreByEmail(String email) throws Exception {
		return ((EmpregadoDao) getDao()).restoreByEmail(email);
	}

	@Override
	public Collection findByNomeEmpregado(EmpregadoDTO empregadoDTO) throws Exception {
		return new EmpregadoDao().findByNomeEmpregado(empregadoDTO);
	}

	@Override
	public EmpregadoDTO restoreByNomeEmpregado(EmpregadoDTO empregadoDTO) throws Exception {
		return new EmpregadoDao().restoreByNomeEmpregado(empregadoDTO);
	}

	@Override
	public EmpregadoDTO restauraTodos(EmpregadoDTO param) throws Exception {
		EmpregadoDao empregadoDAO = new EmpregadoDao();
		return (EmpregadoDTO) empregadoDAO.restauraTodos(param);
	}

	public Collection<EmpregadoDTO> findSolicitanteByNomeAndIdContrato(String nome, Integer idContrato) throws Exception {
		EmpregadoDao empregadoDAO = new EmpregadoDao();

		return empregadoDAO.findSolicitanteByNomeAndIdContrato(nome, idContrato);
	}

	@Override
	public EmpregadoDTO findByTelefoneOrRamal(String telefone) {
		EmpregadoDao empregadoDAO = new EmpregadoDao();

		return empregadoDAO.findByTelefoneOrRamal(telefone);
	}
	
	/**
	 * Restaura o EmpregadoDTO com o Nome cargo a partir do ID Empregado informado.
	 * 
	 * @param idEmpregado
	 * @return EmpregadoDTO com NomeCargo
	 * @author maycon.fernandes
	 */
	public EmpregadoDTO restoreEmpregadoAndNomeCargoByIdEmpegado(Integer idEmpregado) throws Exception{
		EmpregadoDao empregadoDAO = new EmpregadoDao();
		return empregadoDAO.restoreEmpregadoAndNomeCargoByIdEmpegado(idEmpregado);
	}
	

	public EmpregadoDTO restoreEmpregadoWithIdContratoPadraoByIdEmpregado(Integer idEmpregado) {
		return new EmpregadoDao().restoreEmpregadoWithIdContratoPadraoByIdEmpregado(idEmpregado);
	}
}
