/**
 * 
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ImportanciaConhecimentoUsuarioDTO;
import br.com.centralit.citcorpore.integracao.ImportanciaConhecimentoUsuarioDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServicePojoImpl;

/**
 * @author Vadoilo Damasceno
 * 
 */
public class ImportanciaConhecimentoUsuarioServiceEjb extends CrudServicePojoImpl implements ImportanciaConhecimentoUsuarioService {

	private static final long serialVersionUID = 9207260826468281433L;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new ImportanciaConhecimentoUsuarioDAO();
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

		ImportanciaConhecimentoUsuarioDAO importanciaConhecimentoUsuarioDao = new ImportanciaConhecimentoUsuarioDAO();

		importanciaConhecimentoUsuarioDao.setTransactionControler(transactionControler);

		importanciaConhecimentoUsuarioDao.deleteByIdConhecimento(idBaseConhecimento);

	}

	@Override
	public void create(ImportanciaConhecimentoUsuarioDTO importanciaConhecimentoUsuario, TransactionControler transactionControler) throws Exception {
		ImportanciaConhecimentoUsuarioDAO importanciaConhecimentoUsuarioDao = new ImportanciaConhecimentoUsuarioDAO();

		importanciaConhecimentoUsuarioDao.setTransactionControler(transactionControler);

		importanciaConhecimentoUsuarioDao.create(importanciaConhecimentoUsuario);

	}

	@Override
	public Collection<ImportanciaConhecimentoUsuarioDTO> listByIdBaseConhecimento(Integer idBaseConhecimento) throws Exception {

		ImportanciaConhecimentoUsuarioDAO importanciaConhecimentoUsuarioDao = new ImportanciaConhecimentoUsuarioDAO();

		return importanciaConhecimentoUsuarioDao.listByIdBaseConhecimento(idBaseConhecimento);
	}

}
