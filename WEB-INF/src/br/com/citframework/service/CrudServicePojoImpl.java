package br.com.citframework.service;


import java.util.Collection;

import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.citframework.dto.IDto;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;


public abstract class CrudServicePojoImpl  implements CrudServicePojo{
	

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2853082069519158546L;

	protected abstract CrudDAO getDao() throws ServiceException;
	
    protected abstract void validaCreate(Object obj) throws Exception;
    protected abstract void validaUpdate(Object obj) throws Exception;
    protected abstract void validaDelete(Object obj) throws Exception;
    protected abstract void validaFind(Object obj) throws Exception;
    
    protected Usuario usuario;
	
	/**
	 * @return
	 * retorna o Crud Dao Especifico para o servico em questao
	 */

	
	public IDto create( IDto model) throws ServiceException, LogicException{
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
			tc = null;

			return model;
		}catch(Exception e){
			this.rollbackTransaction(tc, e);
		}
		return model;
	}
	
	
	public IDto restore(IDto model) throws ServiceException, LogicException{
		try{
			return getDao().restore(model);
		}catch(LogicException e){
			throw new ServiceException(e);
		}catch(Exception e){
			throw new ServiceException(e);
		}		
	}
	
	public void update( IDto model) throws ServiceException, LogicException{
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
			tc = null;

		}catch(Exception e){
			this.rollbackTransaction(tc, e);
		}		
	}
	
	
	
	public void delete( IDto model) throws ServiceException, LogicException{
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
			tc = null;

		}catch(Exception e){
			this.rollbackTransaction(tc, e);
		}
	}
	
	
	public Collection list() throws ServiceException, LogicException{
		try{
			return getDao().list();
		}catch(LogicException e){
			throw new ServiceException(e);
		}catch(Exception e){
			throw new ServiceException(e);
		}	
	}
	
	
	public Collection find(IDto obj) throws  LogicException, ServiceException {
		try{
			validaFind(obj);
			return getDao().find(obj);
		}catch(LogicException e){
			throw new ServiceException(e);
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	
	/**
	 * Mensagem do arquivo Properties
	 * @param key
	 * @return Mensagem Internacionalizada
	 */
	public String i18n_Message(String key){
	   if(usuario != null){
		   if(UtilI18N.internacionaliza(usuario.getLocale(), key) != null){
			  return  UtilI18N.internacionaliza(usuario.getLocale(), key);
		   }
		   return  key;
	   }
	   return key;
	}
	
	protected void rollbackTransaction(TransactionControler tc,Exception ex) throws ServiceException,LogicException{
		try{
			ex.printStackTrace();
			if (tc.isStarted()) { //Se estiver startada, entao faz roolback.
				tc.rollback();
			}
		}catch (Exception e) {
			//e.printStackTrace();
		}
		try{
			tc.close();
		}catch (Exception e) {
			e.printStackTrace();
			//throw new ServiceException(e);
		}
		
		tc = null;
		
		try{
			System.gc();
		}catch (Exception e) {
		}
		
		if(ex instanceof LogicException){
			throw (LogicException)ex;
		}
		throw new ServiceException(ex);
	}

	public void setUsuario(Usuario usr) {
		this.usuario = usr;
		
	}
	
	


}
