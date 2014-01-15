package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.OcorrenciaDTO;
import br.com.centralit.citcorpore.integracao.OcorrenciaDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class OcorrenciaServiceEjb extends CrudServicePojoImpl implements OcorrenciaService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4101073129183404628L;

	protected CrudDAO getDao() throws ServiceException {
		return new OcorrenciaDao();
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
	public Collection findByDemanda(Integer idDemanda) throws LogicException, RemoteException, ServiceException{
		OcorrenciaDao ocorrenciaDao = (OcorrenciaDao)getDao();
		try {
			return ocorrenciaDao.findByDemanda(idDemanda);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}	
	
	public void updateResposta(IDto bean) throws LogicException, RemoteException, ServiceException{
		OcorrenciaDao ocorrenciaDao = (OcorrenciaDao)getDao();
		try {
			ocorrenciaDao.updateResposta(bean);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
	@Override
	public Collection<OcorrenciaDTO> findByIdSolicitacao(Integer idSolicitacao) throws Exception {
		OcorrenciaDao dao = new OcorrenciaDao();
		try {
			return dao.findByIdSolicitacao(idSolicitacao);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public OcorrenciaDTO findSiglaGrupoExecutorByIdSolicitacao(Integer idSolicitacao) throws Exception {
		OcorrenciaDao dao = new OcorrenciaDao();
		try {
			return dao.findSiglaGrupoExecutorByIdSolicitacao(idSolicitacao);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
