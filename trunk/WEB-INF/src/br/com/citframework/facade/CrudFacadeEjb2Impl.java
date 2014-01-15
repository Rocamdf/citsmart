package br.com.citframework.facade;

import java.rmi.RemoteException;
import java.util.Collection;

import br.com.citframework.bo.CrudBO;
import br.com.citframework.dto.IDto;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceEjb2;
import br.com.citframework.service.EjbSessionBean;

public abstract class CrudFacadeEjb2Impl extends EjbSessionBean implements CrudServiceEjb2{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7756516315744829332L;

	/**
	 * @return
	 * retorna o Crud Dao Especifico para o servico em questao
	 */
	protected abstract CrudBO getBO() throws ServiceException;
	

    
    protected  Usuario usuario;
	
	public IDto create(IDto model) throws ServiceException, RemoteException,LogicException{
		//Instancia Objeto controlador de transacao
		try{
            return getBO().create(model);
		}catch(LogicException e){
			throw e;
		
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		

	}
	
	
	public IDto restore(IDto model) throws ServiceException, RemoteException,LogicException{
		try{
			return getBO().restore(model);
		}catch(LogicException e){
			throw e;
		}catch(Exception e){
			throw new ServiceException(e);
		}		
	}
	
	public void update(IDto model) throws ServiceException, RemoteException,LogicException{
		//Instancia Objeto controlador de transacao
		try{
            getBO().update(model);
		}catch(LogicException e){
			throw e;
		
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	
	
	public void delete(IDto model) throws ServiceException, RemoteException,LogicException{
		//Instancia Objeto controlador de transacao
		try{
            getBO().delete(model);
		}catch(LogicException e){
			throw e;
		
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	

	
	public Collection list() throws ServiceException, RemoteException,LogicException{
		try{
			return getBO().list();
		}catch(LogicException e){
			throw e;
		}catch(Exception e){
			throw new ServiceException(e);
		}	
	}	
	
	
	public Collection find(IDto obj) throws RemoteException, LogicException, ServiceException {
		try{
			return getBO().find(obj);
		}catch(LogicException e){
			throw e;
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	

	public void setUsuario(Usuario usr) {
		this.usuario = usr;
		
	}
	
	


}
