/**
 * 
 */
package br.com.citframework.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.citframework.dto.IDto;
import br.com.citframework.dto.LogTabela;
import br.com.citframework.dto.Usuario;
import br.com.citframework.util.Constantes;

/**
 * @author karem.ricarte
 *
 */
public class LogTabelaDao extends CrudDaoDefaultImpl{


	private static final long serialVersionUID = 1L;

	public LogTabelaDao(Usuario usuario) {
		super(Constantes.getValue("DATABASE_ALIAS"), usuario);
		
	}

	public Collection find(IDto dto) throws Exception {
		
		return null;
	}

	public Collection getFields() {
		List lista = new ArrayList();
		
		lista.add(new Field("idLog","idLog", true, true, false, false));
		lista.add(new Field("nomeTabela","nomeTabela", false, false, false, false));
		
		return lista;
	}

	public String getTableName() {
		
		return "logtabela";
	}

	public Collection list() throws Exception {
		List ordenacao = new ArrayList();
		
		ordenacao.add(new Order("idLog"));
		
		return list(ordenacao);
	}

	public Class getBean() {
		
		return LogTabela.class;
	}
	
	public LogTabela getLogByTabela(String nomeTabela) throws Exception {
		
       List condicoes = new ArrayList();
       condicoes.add(new Condition("nomeTabela","=",nomeTabela));
       Collection resultado = findByCondition(condicoes, null);
       if(resultado==null || resultado.size()==0)
    	   return null;
       return (LogTabela)((List)resultado).get(0);
	
	}
	

}
