package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.integracao.ProdutoTarefaLinBaseProjDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class ProdutoTarefaLinBaseProjServiceEjb extends CrudServicePojoImpl implements ProdutoTarefaLinBaseProjService {
	protected CrudDAO getDao() throws ServiceException {
		return new ProdutoTarefaLinBaseProjDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdTarefaLinhaBaseProjeto(Integer parm) throws Exception{
		ProdutoTarefaLinBaseProjDao dao = new ProdutoTarefaLinBaseProjDao();
		try{
			return dao.findByIdTarefaLinhaBaseProjeto(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdTarefaLinhaBaseProjeto(Integer parm) throws Exception{
		ProdutoTarefaLinBaseProjDao dao = new ProdutoTarefaLinBaseProjDao();
		try{
			dao.deleteByIdTarefaLinhaBaseProjeto(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByIdProdutoContrato(Integer parm) throws Exception{
		ProdutoTarefaLinBaseProjDao dao = new ProdutoTarefaLinBaseProjDao();
		try{
			return dao.findByIdProdutoContrato(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdProdutoContrato(Integer parm) throws Exception{
		ProdutoTarefaLinBaseProjDao dao = new ProdutoTarefaLinBaseProjDao();
		try{
			dao.deleteByIdProdutoContrato(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
