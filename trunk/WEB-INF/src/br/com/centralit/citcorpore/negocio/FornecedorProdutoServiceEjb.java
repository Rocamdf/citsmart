package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.FornecedorProdutoDTO;
import br.com.centralit.citcorpore.integracao.FornecedorProdutoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class FornecedorProdutoServiceEjb extends CrudServicePojoImpl implements FornecedorProdutoService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8324371416300789175L;

	protected CrudDAO getDao() throws ServiceException {
		return new FornecedorProdutoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaFind(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Collection findByIdTipoProduto(Integer parm) throws Exception {
		FornecedorProdutoDao dao = new FornecedorProdutoDao();
		try {
			return dao.findByIdTipoProduto(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public FornecedorProdutoDTO findByIdTipoProdutoAndFornecedor(Integer parm, Integer parm2) throws Exception {
		FornecedorProdutoDao dao = new FornecedorProdutoDao();
		try {
			return dao.findByIdTipoProdutoAndFornecedor(parm, parm2);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	
}
