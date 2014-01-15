package br.com.centralit.citcorpore.rh.negocio;

import br.com.centralit.citcorpore.rh.integracao.LotacaoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class LotacaoServiceEjb extends CrudServicePojoImpl implements LotacaoService {
	protected CrudDAO getDao() throws ServiceException{ 
		return new LotacaoDao(); 
	}
	
	protected void validaCreate(Object arg0) throws Exception{
	}
	
	protected void validaDelete(Object arg0) throws Exception{
	}
	
	protected void validaFind(Object arg0) throws Exception{
	}
	
	protected void validaUpdate(Object arg0) throws Exception{
	}


}
