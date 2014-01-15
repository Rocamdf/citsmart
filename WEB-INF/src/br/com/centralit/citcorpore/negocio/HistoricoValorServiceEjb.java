package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.HistoricoValorDTO;
import br.com.centralit.citcorpore.integracao.HistoricoValorDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

@SuppressWarnings("serial")
public class HistoricoValorServiceEjb extends CrudServicePojoImpl implements HistoricoValorService {

	@Override
	protected CrudDAO getDao() throws ServiceException {
		// TODO Auto-generated method stub
		 return new HistoricoValorDAO();
	}

	@Override
	protected void validaCreate(Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validaUpdate(Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validaDelete(Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validaFind(Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public List<HistoricoValorDTO> listHistoricoValorByIdHistoricoIc(Integer idHistoricoIc) throws Exception{
		HistoricoValorDAO historicoValorDao = new HistoricoValorDAO();
		
		return historicoValorDao.listHistoricoValorByIdHistoricoIc(idHistoricoIc);
	}
	
	@SuppressWarnings("rawtypes")
	public Collection findByIdHitoricoIC(Integer idHistoricoIc) throws Exception{
		HistoricoValorDAO historicoValorDao = new HistoricoValorDAO();
		
		return historicoValorDao.findByIdHitoricoIC(idHistoricoIc);
	}

}
