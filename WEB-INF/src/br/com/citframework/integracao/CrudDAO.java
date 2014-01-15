package br.com.citframework.integracao;

import java.util.Collection;
import br.com.citframework.util.Crud;


public interface CrudDAO extends IDaoTransact,Crud{
      

     
      // Necessario ao engine de persistencia e auditoria
      public abstract String getTableName();
  	  public abstract Collection getFields();
      
      
      /*
      public void updateNotNull(IDto obj) throws Exception;
      public int updateByCondition(IDto obj,List condicao)throws Exception;
      public int updateNotNullByCondition(IDto obj,List condicao)throws Exception;
      public int deleteByCondition(List condicao)throws Exception;
      public Collection list(List ordenacao) throws Exception;
      public Collection list(String ordenacao) throws Exception;
      public Collection find(IDto obj, List ordenacao) throws Exception;
      public Collection findByCondition(List condicao,List ordenacao)throws Exception;
      */
      
      

      

}
