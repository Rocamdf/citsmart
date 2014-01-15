package br.com.centralit.citcorpore.negocio;


import java.util.Collection;

import br.com.centralit.citcorpore.bean.MeuCatalogoDTO;
import br.com.centralit.citcorpore.integracao.MeuCatalogoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class MeuCatalogoServiceEjb extends CrudServicePojoImpl implements MeuCatalogoService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		
		return new MeuCatalogoDao();
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Collection findByCondition(Integer id) throws ServiceException, Exception {
		return this.getMeuCatalogoDao().findByCondition(id);
	}
	
	 public MeuCatalogoDao getMeuCatalogoDao() throws ServiceException {
		 return (MeuCatalogoDao) getDao();
    }
	@SuppressWarnings({ "unchecked" })
	@Override
	public Collection<MeuCatalogoDTO> findByIdServicoAndIdUsuario(Integer idServico, Integer idUsuario) throws ServiceException,Exception {
		return this.getMeuCatalogoDao().findByIdServicoAndIdUsuario(idServico, idUsuario);
	}

	 
	

}
