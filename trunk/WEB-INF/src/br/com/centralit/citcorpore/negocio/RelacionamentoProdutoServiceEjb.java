package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.integracao.RelacionamentoProdutoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;


@SuppressWarnings("rawtypes")
public class RelacionamentoProdutoServiceEjb extends CrudServicePojoImpl implements RelacionamentoProdutoService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2698909998607532383L;
	
	protected CrudDAO getDao() throws ServiceException {
		return new RelacionamentoProdutoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	
	public Collection findByIdTipoProduto(Integer parm) throws Exception{
		RelacionamentoProdutoDao dao = new RelacionamentoProdutoDao();
		try{
			return dao.findByIdTipoProduto(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdTipoProduto(Integer parm) throws Exception{
	    RelacionamentoProdutoDao dao = new RelacionamentoProdutoDao();
		try{
			dao.deleteByIdTipoProduto(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
