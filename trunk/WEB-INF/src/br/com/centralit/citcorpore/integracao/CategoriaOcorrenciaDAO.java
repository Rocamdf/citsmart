package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.CategoriaOcorrenciaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * @author thiago.monteiro
 *
 * Classe de objetos responsáveis pelo acesso aos dados (DAO - Data Access Object) na tabela
 * categoriaocorrencia no banco de dados.
 */

@SuppressWarnings({ "rawtypes", "unchecked" })
public class CategoriaOcorrenciaDAO extends CrudDaoDefaultImpl {	
	private static final long serialVersionUID = -1232597794732575512L;

	public CategoriaOcorrenciaDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto obj) throws Exception {	
		return null;
	}

	@Override
	public Collection getFields() {
		Collection listFields = new ArrayList(); 
		listFields.add(new Field("idcategoriaocorrencia", "idCategoriaOcorrencia", true, true, false, false) );
		listFields.add(new Field("nome" ,"nome", false, false, false, false) );
		listFields.add(new Field("dataInicio", "dataInicio", false, false, false, false) );
		listFields.add(new Field("dataFim", "dataFim", false, false, false, false) );
		return listFields;
	}

	@Override	
	public String getTableName() {
		return "CATEGORIAOCORRENCIA";
	}

	@Override
	public Collection list() throws Exception { 
		List list = new ArrayList();
		list.add(new Order("nome") );
		return super.list(list);
	}

	@Override
	/**
	 * Método que retorna uma referência 
	 */
	public Class getBean() {		
		return CategoriaOcorrenciaDTO.class;
	}
	
	public boolean consultarCategoriaOcorrenciaAtiva(CategoriaOcorrenciaDTO categoriaOcorrencia) {		
		if (categoriaOcorrencia == null)
			return false;
		
		List parametros = new ArrayList();
		List resultado = new ArrayList();		
		String sql = String.format("select idcategoriaocorrencia from %s where nome = ? and dataFim is null", this.getTableName() );		
		
		if (categoriaOcorrencia.getIdCategoriaOcorrencia() != null) {
			sql += "and idcategoriaocorrencia <> " + categoriaOcorrencia.getIdCategoriaOcorrencia();
		}
		
		parametros.add(categoriaOcorrencia.getNome() );
		
		try {
			resultado = this.execSQL(sql, parametros.toArray() );
		} catch (PersistenceException e) {		
			e.printStackTrace();
		}		
 
		if (resultado != null && !resultado.isEmpty() ) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean consultarPorOcorrenciaSolicitacaoAssociadaCom(int idCategoriaOcorrencia) {		
		List parametros = new ArrayList();
		List resultado = new ArrayList();
		String sql = String.format(
				"select idocorrencia from ocorrenciasolicitacao where idcategoriaocorrencia in (select ? from %s)",
				this.getTableName()
		);
		parametros.add(idCategoriaOcorrencia);
		try {
			resultado = this.execSQL(sql, parametros.toArray() );			
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
		if (resultado != null && !resultado.isEmpty() )
			return true;
		else
			return false;
	}
	
	public boolean validaInsert(CategoriaOcorrenciaDTO categoriaOcorrencia) {
		return false;
	}
	
	public Collection findByNomeCategoriaOcorrencia(CategoriaOcorrenciaDTO categoriaOcorrencia) throws Exception {
		List condicao = new ArrayList(); 
		List ordem = new ArrayList();
		
		condicao.add(new Condition("nome", "=", categoriaOcorrencia.getNome() ) ); 
		ordem.add(new Order("nome") );
		condicao.add(new Condition(Condition.AND, "dataFim", "is", null) );
		return super.findByCondition(condicao, ordem);
	}
	
}
