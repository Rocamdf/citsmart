package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.JustificativaProblemaDTO;
import br.com.centralit.citcorpore.integracao.JustificativaProblemaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
@SuppressWarnings({  "serial" })
public class JustificativaProblemaServiceEjb extends CrudServicePojoImpl implements JustificativaProblemaService {

	@Override
	protected CrudDAO getDao() throws ServiceException {
		// TODO Auto-generated method stub
		return new JustificativaProblemaDao();
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

	
	@Override
	public Collection<JustificativaProblemaDTO> listAtivasParaSuspensao() throws Exception {
		JustificativaProblemaDao justificativaProblemaDao = new JustificativaProblemaDao();
		return justificativaProblemaDao.listAtivasParaSuspensao();
	}

}
