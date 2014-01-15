package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.GrupoRequisicaoMudancaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class GrupoRequisicaoMudancaServiceEjb extends CrudServicePojoImpl implements GrupoRequisicaoMudancaService {

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new GrupoRequisicaoMudancaDao();
	}

	@Override
	protected void validaCreate(Object obj) throws Exception {		
	}

	@Override
	protected void validaUpdate(Object obj) throws Exception {		
	}

	@Override
	protected void validaDelete(Object obj) throws Exception {	
	}

	@Override
	protected void validaFind(Object obj) throws Exception {	
	}

	@Override
	public void deleteByIdRequisicaoMudanca(Integer parm) throws Exception {
		GrupoRequisicaoMudancaDao dao = new GrupoRequisicaoMudancaDao();
		try {
			dao.deleteByIdRequisicaoMudanca(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public Collection listByIdHistoricoMudanca(Integer idHistoricoMudanca)  throws Exception {
	   GrupoRequisicaoMudancaDao dao = new GrupoRequisicaoMudancaDao();
		try {
			return dao.listByIdHistoricoMudanca(idHistoricoMudanca);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection findByIdRequisicaoMudanca(Integer parm) throws Exception {
		GrupoRequisicaoMudancaDao dao = new GrupoRequisicaoMudancaDao();
		try {
			return dao.findByIdRequisicaoMudanca(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void deleteByIdGrupo(Integer parm) throws Exception {
		GrupoRequisicaoMudancaDao dao = new GrupoRequisicaoMudancaDao();
		try {
			dao.deleteByIdGrupo(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection findByIdGrupo(Integer parm) throws Exception {
		GrupoRequisicaoMudancaDao dao = new GrupoRequisicaoMudancaDao();
		try {
			return dao.findByIdGrupo(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void deleteByIdGrupoRequisicaoMudanca(Integer parm) throws Exception {
		GrupoRequisicaoMudancaDao dao = new GrupoRequisicaoMudancaDao();
		try {
			dao.deleteByIdGrupoRequisicaoMudanca(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public Collection findByIdGrupoRequisicaoMudanca(Integer parm) throws Exception {
		GrupoRequisicaoMudancaDao dao = new GrupoRequisicaoMudancaDao();
		try {
			return dao.findByIdGrupoRequisicaoMudanca(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection findByIdMudancaEDataFim(Integer idRequisicaoMudanca) throws Exception {
		GrupoRequisicaoMudancaDao dao = new GrupoRequisicaoMudancaDao();
		try {
			return dao.findByIdMudancaEDataFim(idRequisicaoMudanca);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
