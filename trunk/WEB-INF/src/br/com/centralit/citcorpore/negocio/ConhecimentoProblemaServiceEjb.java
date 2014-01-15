package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ConhecimentoProblemaDTO;
import br.com.centralit.citcorpore.integracao.ConhecimentoProblemaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
@SuppressWarnings("rawtypes")
public class ConhecimentoProblemaServiceEjb extends CrudServicePojoImpl implements ConhecimentoProblemaService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		// TODO Auto-generated method stub
		 return new ConhecimentoProblemaDao();
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
	public Collection findByIdProblema(Integer parm) throws Exception {
		ConhecimentoProblemaDao conhecimentoProblemaDao = new  ConhecimentoProblemaDao();
		return conhecimentoProblemaDao.findByIdProblema(parm);
	}

	@Override
	public Collection<ConhecimentoProblemaDTO> listaBaseConhecimentoByIdProblema(Integer parm) throws Exception {
		ConhecimentoProblemaDao conhecimentoProblemaDao = new  ConhecimentoProblemaDao();
		return conhecimentoProblemaDao.listaBaseConhecimentoByIdProblema(parm);
	}

}
