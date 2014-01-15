package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.CentroResultadoDTO;

import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({"rawtypes", "unchecked"})
public class CentroResultadoDao extends CrudDaoDefaultImpl {

	private static final long serialVersionUID = -5409718089154035917L;
	
	public CentroResultadoDao() {		
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	private static final String SQL_GET_LISTA_SEM_PAI =
			"select " + 
		        "idcentroresultado, codigocentroresultado, nomecentroresultado, " + 
		        "idcentroresultadopai, permiterequisicaoproduto, situacao " +
		    "from " +
		        "centroresultado " +
		    "where " +
		        "idcentroresultadopai is null " +
		    "order by " +
		        "codigocentroresultado";
	
	public Collection getFields() {
		
		Collection listFields = new ArrayList();
		
		listFields.add(new Field("idcentroresultado" ,"idCentroResultado", true, true, false, false) );		
		listFields.add(new Field("codigocentroresultado" ,"codigoCentroResultado", false, false, false, false) );
		listFields.add(new Field("nomecentroresultado" ,"nomeCentroResultado", false, false, false, false) );
		listFields.add(new Field("idcentroresultadopai" ,"idCentroResultadoPai", false, false, false, false) );
        listFields.add(new Field("permiterequisicaoproduto" ,"permiteRequisicaoProduto", false, false, false, false) );
        listFields.add(new Field("situacao", "situacao", false, false, false, false) );
				
		return listFields;
	}
	
	public String getTableName() {
		return this.getOwner() + "centroresultado";
	}
	
	public Collection list() throws Exception {
		
        List ordenacao = new ArrayList();        
        ordenacao.add(new Order("idCentroResultado") );
        
        return super.list(ordenacao);
	}

    public Collection listAtivos() throws Exception {
    	
        List condicao = new ArrayList();
        List ordenacao = new ArrayList();
        
        condicao.add(new Condition("situacao", "=", "A") );
        ordenacao.add(new Order("nomeCentroResultado") );
        
        return super.findByCondition(condicao, ordenacao);
    }
    
    public Collection listPermiteRequisicaoProduto() throws Exception {
    	
        List condicao = new ArrayList();
        List ordenacao = new ArrayList();      
        
        condicao.add(new Condition("situacao", "=", "A") ); 
        condicao.add(new Condition("permiteRequisicaoProduto", "=", "S") ); 
        ordenacao.add(new Order("nomeCentroResultado") );
        
        return super.findByCondition(condicao, ordenacao);
    }
    
    public Collection findSemPai() throws Exception {
    	
        Object[] objs = new Object[] {};
        List lista = this.execSQL(SQL_GET_LISTA_SEM_PAI, objs);
        
        List listRetorno = new ArrayList();
        
        listRetorno.add("idCentroResultado"); 
        listRetorno.add("codigoCentroResultado");
        listRetorno.add("nomeCentroResultado");
        listRetorno.add("idCentroResultadoPai");
        listRetorno.add("permiteRequisicaoProduto");
        listRetorno.add("situacao");
        
        List result = this.engine.listConvertion(getBean(), lista, listRetorno);
        
        return result;
    }   
    
    public Collection findByIdPai(Integer idPai) throws Exception {
        List condicao = new ArrayList();
        List ordenacao = new ArrayList();        
        
        condicao.add(new Condition("idCentroResultadoPai", "=", idPai) ); 
        ordenacao.add(new Order("codigoCentroResultado") );
        
        return super.findByCondition(condicao, ordenacao);
    }
    
    public Class getBean() {
    	return CentroResultadoDTO.class;
	}
    
	public Collection find(IDto arg0) throws Exception {
		return super.find(arg0, null);
	}
	
	public boolean temFilhos(int idCentroResultado) {
		boolean resposta = false;
		
		try {
			List resultadoSQL = this.execSQL(String.format("select * from centroresultado where idcentroresultadopai = %d", idCentroResultado), null);
			
			if (!resultadoSQL.isEmpty() ) {
				resposta = true;
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resposta;
	}
	
	public Collection findByIdAlcada(Integer idAlcada) throws Exception {
		List resultado = null;
		
		if (idAlcada != null && idAlcada > 0) {
			try {
				resultado = this.execSQL(String.format("select * from alcadacentroresultado where idalcada = %d", idAlcada), null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return resultado;
	}
}