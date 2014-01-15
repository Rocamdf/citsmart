package br.com.centralit.citquestionario.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citquestionario.bean.ExecutaSQLDTO;
import br.com.centralit.citquestionario.bean.ListagemDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;

public class ExecutaSQLDao extends CrudDaoDefaultImpl {
	public ExecutaSQLDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection find(IDto arg0) throws Exception {
		return null;
	}

	public Collection getFields() {
		return null;
	}

	public String getTableName() {
		return null;
	}

	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return ExecutaSQLDTO.class;
	}
	
	public Collection executaSQL(String sql) throws Exception {
	    if (sql != null) {
	        Collection linhas = new ArrayList();
            List lista = this.execSQL(sql,null);
            
            List listRetorno = new ArrayList();
            listRetorno.add("value");
            listRetorno.add("description");
            
            List result = this.engine.listConvertion(getBean(), lista, listRetorno);
            return result;            
	    }
	    return null;
	}

}
