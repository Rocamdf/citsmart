package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.PerfilAcessoGrupoDTO;
import br.com.centralit.citcorpore.integracao.PerfilAcessoGrupoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class PerfilAcessoGrupoServiceEjb extends CrudServicePojoImpl implements PerfilAcessoGrupoService {

	private static final long serialVersionUID = 5303831527682189876L;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new PerfilAcessoGrupoDao();
	}

	@Override
	protected void validaCreate(Object arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void validaDelete(Object arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void validaFind(Object arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void validaUpdate(Object arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public PerfilAcessoGrupoDTO listByIdGrupo(PerfilAcessoGrupoDTO obj) throws Exception {
		try {
			PerfilAcessoGrupoDao dao = new PerfilAcessoGrupoDao();
			return dao.listByIdGrupo(obj);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
