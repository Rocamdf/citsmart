package br.com.centralit.citcorpore.rh.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.rh.integracao.FormacaoAcademicaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class FormacaoAcademicaServiceEjb extends CrudServicePojoImpl implements FormacaoAcademicaService {
	protected CrudDAO getDao() throws ServiceException{ 
		return new FormacaoAcademicaDao(); 
	}
	
	protected void validaCreate(Object arg0) throws Exception{}
	
	protected void validaDelete(Object arg0) throws Exception{}
	
	protected void validaFind(Object arg0) throws Exception{}
	
	protected void validaUpdate(Object arg0) throws Exception{}

	@Override
	public Collection findByNome(String nome) throws Exception {
		FormacaoAcademicaDao dao = new FormacaoAcademicaDao();
		return dao.findByNome(nome);
	}
	
}
