package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.AlcadaDTO;
import br.com.centralit.citcorpore.bean.ProcessamentoBatchDTO;
import br.com.centralit.citcorpore.integracao.AlcadaDao;
import br.com.centralit.citcorpore.integracao.ProcessamentoBatchDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class ProcessamentoBatchServiceEjb extends CrudServicePojoImpl implements ProcessamentoBatchService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected CrudDAO getDao() throws ServiceException {
		
		return new ProcessamentoBatchDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
		
	}

	protected void validaDelete(Object arg0) throws Exception {
		
	}

	protected void validaFind(Object arg0) throws Exception {
		
	}

	protected void validaUpdate(Object arg0) throws Exception {
		
	}
	public Collection getAtivos() throws Exception {
	    ProcessamentoBatchDao pdao = new ProcessamentoBatchDao();
		return pdao.getAtivos();
	}
	
	public boolean existeDuplicidade(ProcessamentoBatchDTO processamentoBatch) throws Exception {
		return new ProcessamentoBatchDao().existeDuplicidade(processamentoBatch);
	}
	
	public boolean existeDuplicidadeClasse(ProcessamentoBatchDTO processamentoBatch) throws Exception{
		return new ProcessamentoBatchDao().existeDuplicidadeClasse(processamentoBatch);
	}
}
