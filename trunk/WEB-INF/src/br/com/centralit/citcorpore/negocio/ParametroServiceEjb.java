package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.ParametroDTO;
import br.com.centralit.citcorpore.integracao.ParametroDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl; 


public class ParametroServiceEjb extends CrudServicePojoImpl implements ParametroService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6361770952023279100L;

	protected CrudDAO getDao() throws ServiceException {
		return new ParametroDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
		
	}

	protected void validaDelete(Object arg0) throws Exception {
		
	}

	protected void validaFind(Object arg0) throws Exception {
		
	}

	protected void validaUpdate(Object arg0) throws Exception {
		
	}
	
	public ParametroDTO getValue(String moduloParm, String nomeParametroParm, Integer idEmpresaParm) throws ServiceException, LogicException {
		ParametroDao parametroDao = new ParametroDao();
		try {
			return parametroDao.getValue(moduloParm, nomeParametroParm, idEmpresaParm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	public void setValue(String moduloParm, String nomeParametroParm, Integer idEmpresaParm, String valorParm, String detalhamentoParm) throws ServiceException, LogicException {
		ParametroDao parametroDao = new ParametroDao();
		try {
			parametroDao.setValue(moduloParm, nomeParametroParm, idEmpresaParm, valorParm, detalhamentoParm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}	
	
}
