package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.CriterioAvaliacaoDTO;
import br.com.centralit.citcorpore.integracao.CriterioAvaliacaoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

@SuppressWarnings({ "rawtypes", "serial" })
public class CriterioAvaliacaoServiceEjb extends CrudServicePojoImpl implements CriterioAvaliacaoService {
	protected CrudDAO getDao() throws ServiceException {
		return new CriterioAvaliacaoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaFind(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	public Collection findByAplicavelCotacao() throws Exception {
		CriterioAvaliacaoDao dao = new CriterioAvaliacaoDao();
		try {
			return dao.findByAplicavelCotacao();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection findByAplicavelAvaliacaoSolicitante() throws Exception {
		CriterioAvaliacaoDao dao = new CriterioAvaliacaoDao();
		try {
			return dao.findByAplicavelAvaliacaoSolicitante();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection findByAplicavelAvaliacaoComprador() throws Exception {
		CriterioAvaliacaoDao dao = new CriterioAvaliacaoDao();
		try {
			return dao.findByAplicavelAvaliacaoComprador();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean verificarSeCriterioExiste(CriterioAvaliacaoDTO criterioAvaliacaoDto) throws Exception {
		CriterioAvaliacaoDao dao = new CriterioAvaliacaoDao();
		try {
			return dao.verificarSeCriterioExiste(criterioAvaliacaoDto);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
