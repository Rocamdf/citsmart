package br.com.citframework.bo;
import java.util.Collection;

import br.com.citframework.dto.IDto;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;

public abstract class CrudBOImpl extends BO implements CrudBO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3819333037228181681L;







	protected abstract CrudDAO getDao() throws Exception;
	
	
    protected abstract void validaCreate(Object obj) throws Exception;
    protected abstract void validaUpdate(Object obj) throws Exception;
    protected abstract void validaDelete(Object obj) throws Exception;
    protected abstract void validaFind(Object obj) throws Exception;

	/**
	 * @return
	 * retorna o Crud Dao Especifico para o servico em questao
	 */

	public CrudBOImpl(Usuario usuario){
		
		super(usuario);
	}
    
	public IDto create(IDto model) throws Exception{
		//Instancia Objeto controlador de transacao
		CrudDAO crudDao = getDao();
		TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
		try{
			//Faz validacao, caso exista.
			validaCreate(model);
			
			//Instancia ou obtem os DAOs necessarios.
			
			
			//Seta o TransactionController para os DAOs
			crudDao.setTransactionControler(tc);
			
			//Inicia transacao
			tc.start();
			
			//Executa operacoes pertinentes ao negocio.
			model = crudDao.create(model);
			
			//Faz commit e fecha a transacao.
			tc.commit();
			tc.close();
			
			//Gera auditoria.			
			
			return model;
		}catch(Exception e){
			this.rollbackTransaction(tc, e);
		}
		return model;
	}
	
	
	public IDto restore(IDto model) throws Exception{
		try{
			return getDao().restore(model);
		}catch(LogicException e){
			throw new ServiceException(e);
		}catch(Exception e){
			throw new ServiceException(e);
		}		
	}
	
	public void update(IDto model) throws Exception{
		//Instancia Objeto controlador de transacao
		CrudDAO crudDao = getDao();
		TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
		try{
			//Faz validacao, caso exista.
			validaUpdate(model);

			//Seta o TransactionController para os DAOs
			crudDao.setTransactionControler(tc);
			
			//Inicia transacao
			tc.start();
			
			//Executa operacoes pertinentes ao negocio.
			crudDao.update(model);
			
			//Faz commit e fecha a transacao.
			tc.commit();
			tc.close();
			

		}catch(Exception e){
			this.rollbackTransaction(tc, e);
		}		
	}
	
	
	
	public void delete(IDto model) throws Exception{
		//Instancia Objeto controlador de transacao
		CrudDAO crudDao = getDao();
		TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
		try{
			//Faz validacao, caso exista.
			validaDelete(model);

			//Seta o TransactionController para os DAOs
			crudDao.setTransactionControler(tc);
			
			//Inicia transacao
			tc.start();
			
			//Executa operacoes pertinentes ao negocio.
			crudDao.delete(model);
			
			//Faz commit e fecha a transacao.
			tc.commit();
			tc.close();
			

		}catch(Exception e){
			this.rollbackTransaction(tc, e);
		}
	}
	
	
	public Collection list() throws Exception{
		try{
			return getDao().list();
		}catch(LogicException e){
			throw new ServiceException(e);
		}catch(Exception e){
			throw new ServiceException(e);
		}	
	}
	
	
	
	
	public Collection find(IDto obj) throws  Exception {
		try{
			validaFind(obj);
			return getDao().find(obj);
		}catch(LogicException e){
			throw new ServiceException(e);
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	


	

	
	
	protected void rollbackTransaction(TransactionControler tc,Exception ex) throws Exception{
		try{
			ex.printStackTrace();
			if (tc.isStarted()) { //Se estiver startada, entao faz roolback.
				tc.rollback();
			}
			tc.close();
		}catch (Exception e) {
			
			e.printStackTrace();
			throw new ServiceException(e);
			
		}
		if(ex instanceof LogicException){
			throw (LogicException)ex;
		}
		throw new ServiceException(ex);
	}

	

	

	

}
