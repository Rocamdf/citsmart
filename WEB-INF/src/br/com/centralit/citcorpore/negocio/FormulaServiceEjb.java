package br.com.centralit.citcorpore.negocio;
import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.FormulaDTO;
import br.com.centralit.citcorpore.integracao.FormulaDao;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
@SuppressWarnings({"serial","rawtypes"})
public class FormulaServiceEjb extends CrudServicePojoImpl implements FormulaService {
	protected CrudDAO getDao() throws ServiceException {
		return new FormulaDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdentificador(String parm) throws Exception{
		FormulaDao dao = new FormulaDao();
		try{
			return dao.findByIdentificador(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdentificador(String parm) throws Exception{
		FormulaDao dao = new FormulaDao();
		try{
			dao.deleteByIdentificador(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public boolean existeRegistro(String nome){
		
		ArrayList<Condition> condicoes = new ArrayList<Condition>();
		condicoes.add(new Condition("nome", "=", nome));
		Collection retorno = null;
		try {
			retorno = ((FormulaDao) getDao()).findByCondition(condicoes, null);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno == null ? false : true;
		
	}

	@Override
	public boolean verificarSeIdentificadorExiste(FormulaDTO formula) throws PersistenceException {
		FormulaDao dao = new FormulaDao();
		return dao.verificarSeIdentificadorExiste(formula);
	}

	@Override
	public boolean verificarSeNomeExiste(FormulaDTO formula) throws PersistenceException {
		FormulaDao dao = new FormulaDao();
		return dao.verificarSeNomeExiste(formula);
	}
		
}
