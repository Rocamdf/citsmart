package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.RelatorioGruposUsuarioDTO;
import br.com.centralit.citcorpore.integracao.GrupoEmpregadoDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

@SuppressWarnings("rawtypes")
public class GrupoEmpregadoServiceEjb extends CrudServicePojoImpl implements GrupoEmpregadoService {

	private static final long serialVersionUID = -4101073129183404628L;

	protected CrudDAO getDao() throws ServiceException {
		return new GrupoEmpregadoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	protected void validaFind(Object obj) throws Exception {
	}

	public Collection list(List ordenacao) throws LogicException, RemoteException, ServiceException {
		return null;
	}

	public Collection list(String ordenacao) throws LogicException, RemoteException, ServiceException {
		return null;
	}

	public Collection<GrupoEmpregadoDTO> findByIdGrupo(Integer idGrupo) throws Exception {
		return new GrupoEmpregadoDao().findByIdGrupo(idGrupo);
	}

	public Collection<GrupoEmpregadoDTO> findUsariosGrupo() throws Exception {
		return new GrupoEmpregadoDao().findUsariosGrupo();
	}

	@Override
	public void gerarGridEmpregados(DocumentHTML document, Collection<GrupoEmpregadoDTO> grupoEmpregados) throws Exception {

	}

	@Override
	public Collection findByIdEmpregado(Integer idEmpregado) throws Exception {
		GrupoEmpregadoDao grupoEmpregadoDao = new GrupoEmpregadoDao();
		return grupoEmpregadoDao.findByIdEmpregado(idEmpregado);
	}

	@Override
	public void deleteByIdGrupoAndEmpregado(Integer idGrupo, Integer idEmpregado) throws Exception {

		GrupoEmpregadoDao grupoEmpregadoDao = new GrupoEmpregadoDao();

		grupoEmpregadoDao.deleteByIdGrupoAndEmpregado(idGrupo, idEmpregado);

	}

	@Override
	public Collection<GrupoEmpregadoDTO> findGrupoEmpregadoHelpDeskByIdContrato(Integer idContrato) {
		try {
			return new GrupoEmpregadoDao().findGrupoEmpregadoHelpDeskByIdContrato(idContrato);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Collection<GrupoEmpregadoDTO> findGrupoAndEmpregadoByIdGrupo(Integer idGrupo) throws Exception {
		return new GrupoEmpregadoDao().findGrupoAndEmpregadoByIdGrupo(idGrupo);
	}

	@Override
	public Collection<RelatorioGruposUsuarioDTO> listaRelatorioGruposUsuario(
			Integer idColaborador) throws Exception {
		try {
			return new GrupoEmpregadoDao().listaRelatorioGruposUsuario(idColaborador);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Collection findByIdEmpregadoNome(Integer idEmpregado)
			throws Exception {
		try {
			return new GrupoEmpregadoDao().findByIdEmpregadoNome(idEmpregado);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Integer calculaTotalPaginas(Integer itensPorPagina, Integer idGrupo)
			throws Exception {
		try {
			return new GrupoEmpregadoDao().calculaTotalPaginas(itensPorPagina, idGrupo);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Collection<GrupoEmpregadoDTO> paginacaoGrupoEmpregado(
			Integer idGrupo, Integer pgAtual, Integer qtdPaginacao)
			throws Exception {
		try {
			return new GrupoEmpregadoDao().paginacaoGrupoEmpregado(idGrupo, pgAtual, qtdPaginacao);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public boolean grupoempregado(Integer idEmpregado, Integer idGrupo)
			throws Exception {
		GrupoEmpregadoDao grupoempregadoDao = new GrupoEmpregadoDao();
		return grupoempregadoDao.grupoempregado(idEmpregado, idGrupo);
	}

	@Override
	public Collection<GrupoEmpregadoDTO> findEmpregado(Integer idGrupo,
			Integer idEmpregado) throws Exception {
		GrupoEmpregadoDao grupoempregadoDao = new GrupoEmpregadoDao();
		return grupoempregadoDao.findEmpregado(idGrupo, idEmpregado);
	}

}
