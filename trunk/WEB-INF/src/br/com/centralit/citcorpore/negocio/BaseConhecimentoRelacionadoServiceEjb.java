/**
 * 
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.BaseConhecimentoRelacionadoDTO;
import br.com.centralit.citcorpore.integracao.BaseConhecimentoRelacionadoDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServicePojoImpl;

/**
 * @author Vadoilo Damasceno
 * 
 */
public class BaseConhecimentoRelacionadoServiceEjb extends CrudServicePojoImpl implements BaseConhecimentoRelacionadoService {

	private static final long serialVersionUID = -529766483619862121L;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new BaseConhecimentoRelacionadoDAO();
	}

	@Override
	protected void validaCreate(Object obj) throws Exception {

	}

	@Override
	protected void validaUpdate(Object obj) throws Exception {

	}

	@Override
	protected void validaDelete(Object obj) throws Exception {

	}

	@Override
	protected void validaFind(Object obj) throws Exception {

	}

	@Override
	public void deleteByIdConhecimento(Integer idBaseConhecimento, TransactionControler transactionControler) throws Exception {

		BaseConhecimentoRelacionadoDAO baseConhecimentoRelacionadoDao = new BaseConhecimentoRelacionadoDAO();

		baseConhecimentoRelacionadoDao.setTransactionControler(transactionControler);

		baseConhecimentoRelacionadoDao.deleteByIdConhecimento(idBaseConhecimento);

	}

	@Override
	public void create(BaseConhecimentoRelacionadoDTO baseConhecimentoRelacionado, TransactionControler transactionControler) throws Exception {

		BaseConhecimentoRelacionadoDAO baseConhecimentoRelacionadoDao = new BaseConhecimentoRelacionadoDAO();

		baseConhecimentoRelacionadoDao.setTransactionControler(transactionControler);

		baseConhecimentoRelacionadoDao.create(baseConhecimentoRelacionado);

	}

	@Override
	public Collection<BaseConhecimentoRelacionadoDTO> listByIdBaseConhecimento(Integer idBaseConhecimento) throws Exception {

		BaseConhecimentoRelacionadoDAO baseConhecimentoRelacionadoDao = new BaseConhecimentoRelacionadoDAO();

		return baseConhecimentoRelacionadoDao.listByIdBaseConhecimento(idBaseConhecimento);
	}
	
	public Collection<BaseConhecimentoRelacionadoDTO> listByIdBaseConhecimentoRelacionado(Integer idBaseConhecimento) throws Exception {

		BaseConhecimentoRelacionadoDAO baseConhecimentoRelacionadoDao = new BaseConhecimentoRelacionadoDAO();

		return baseConhecimentoRelacionadoDao.listByIdBaseConhecimentoRelacionado(idBaseConhecimento);
	}	

}
