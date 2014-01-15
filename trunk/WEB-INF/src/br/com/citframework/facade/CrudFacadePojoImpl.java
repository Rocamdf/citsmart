package br.com.citframework.facade;



import java.util.Collection;

import br.com.citframework.bo.CrudBO;
import br.com.citframework.dto.IDto;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;

import br.com.citframework.service.CrudServicePojo;


public abstract class CrudFacadePojoImpl  implements CrudServicePojo{
	

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -555365139160422965L;

	protected abstract CrudBO getBO() throws ServiceException;
    
    protected Usuario usuario;
	
	public IDto create(IDto model) throws ServiceException,  LogicException{
		//Instancia Objeto controlador de transacao
		try{
            return getBO().create(model);
		}catch(LogicException e){
			throw e;
		
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		

	}
	
	
	public IDto restore(IDto model) throws ServiceException,  LogicException{
		try{
			return getBO().restore(model);
		}catch(LogicException e){
			throw e;
		}catch(Exception e){
			throw new ServiceException(e);
		}		
	}
	
	public void update(IDto model) throws ServiceException,  LogicException{
		//Instancia Objeto controlador de transacao
		try{
            getBO().update(model);
		}catch(LogicException e){
			throw e;
		
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	
	
	public void delete(IDto model) throws ServiceException,  LogicException{
		//Instancia Objeto controlador de transacao
		try{
            getBO().delete(model);
		}catch(LogicException e){
			throw e;
		
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	

	
	public Collection list() throws ServiceException,  LogicException{
		try{
			return getBO().list();
		}catch(LogicException e){
			throw e;
		}catch(Exception e){
			throw new ServiceException(e);
		}	
	}	
	
	
	public Collection find(IDto obj) throws   LogicException, ServiceException {
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
