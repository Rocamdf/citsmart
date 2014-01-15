package br.com.citframework.bo;

import br.com.citframework.dto.Usuario;

public abstract class BO implements IBO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5877487705971521103L;

    protected Usuario usuario;
    
    public BO(Usuario usuario){
    	
    	this.usuario = usuario;
    }
    
    
    
    
    

    
}
