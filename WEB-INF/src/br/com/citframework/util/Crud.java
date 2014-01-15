package br.com.citframework.util;

import java.io.Serializable;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.DicionarioDTO;
import br.com.citframework.dto.IDto;

public interface Crud extends Serializable{
	
	
	  public IDto create(IDto obj) throws Exception;
      
      public void update(IDto obj) throws Exception;
      
      public void delete(IDto obj) throws Exception;
      
      public IDto restore(IDto obj) throws Exception;
      
      public Collection find(IDto obj) throws Exception;
      
      public abstract Collection list() throws Exception;

      
      
      

}
