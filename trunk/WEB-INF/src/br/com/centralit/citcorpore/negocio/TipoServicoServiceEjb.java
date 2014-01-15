package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.TipoServicoDTO;
import br.com.centralit.citcorpore.integracao.TipoServicoDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

@SuppressWarnings("rawtypes")
public class TipoServicoServiceEjb extends CrudServicePojoImpl implements TipoServicoService {

	private static final long serialVersionUID = -4101073129183404628L;

	protected CrudDAO getDao() throws ServiceException {
		return new TipoServicoDao();
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

	public boolean verificarSeTipoServicoExiste(TipoServicoDTO tipoServicoDto) throws PersistenceException, ServiceException {
		TipoServicoDao tipoDao = (TipoServicoDao) this.getDao();
		return tipoDao.verificarSeTipoServicoExiste(tipoServicoDto);
	}

}
