package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.OrigemOcorrenciaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * @author thiago.monteiro
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class OrigemOcorrenciaDAO extends CrudDaoDefaultImpl {
	private static final long serialVersionUID = -3508479826646240589L;

	public OrigemOcorrenciaDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	@Override
	public Collection find(IDto obj) throws Exception {
		return null;
	}

	@Override
	public Collection getFields() {
		Collection listFields = new ArrayList(); 
		listFields.add(new Field("idorigemocorrencia", "idOrigemOcorrencia", true, true, false, false) );
		listFields.add(new Field("nome" ,"nome", false, false, false, false) );
		listFields.add(new Field("dataInicio", "dataInicio", false, false, false, false) );
		listFields.add(new Field("dataFim", "dataFim", false, false, false, false) );
		return listFields;
	}

	@Override
	public String getTableName() {
		return "ORIGEMOCORRENCIA";
	}

	@Override
	public Collection list() throws Exception {
		List list = new ArrayList();
		list.add(new Order("nome") );
		return super.list(list);
	}

	@Override
	public Class getBean() {
		return OrigemOcorrenciaDTO.class;
	}
	
	public boolean consultarOrigemOcorrenciaAtiva(OrigemOcorrenciaDTO origemOcorrencia) {		
		if (origemOcorrencia == null)
			return false;
		
		List parametros = new ArrayList();
		List resultado = new ArrayList();		
		String sql = String.format("select idorigemocorrencia from %s where nome = ? and dataFim is null", this.getTableName() );		
		
		if (origemOcorrencia.getIdOrigemOcorrencia() != null) {
			sql += "and idorigemocorrencia <> " + origemOcorrencia.getIdOrigemOcorrencia();
		}
		
		parametros.add(origemOcorrencia.getNome() );
		
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
	
	public boolean consultarPorOcorrenciaSolicitacaoAssociadaCom(int idOrigemOcorrencia) {		
		List parametros = new ArrayList();
		List resultado = new ArrayList();
		String sql = String.format(
				"select idocorrencia from ocorrenciasolicitacao where idorigemocorrencia in (select ? from %s)",
				this.getTableName()
		);
		parametros.add(idOrigemOcorrencia);
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
	
	public boolean validaInsert(OrigemOcorrenciaDTO origemOcorrencia) {
		return false;
	}
	
	public Collection findByNomeOrigemOcorrencia(OrigemOcorrenciaDTO origemOcorrencia) throws Exception {
		List condicao = new ArrayList(); 
		List ordem = new ArrayList();
		
		condicao.add(new Condition("nome", "=", origemOcorrencia.getNome() ) ); 
		ordem.add(new Order("nome") );
		condicao.add(new Condition(Condition.AND, "dataFim", "is", null) );
		return super.findByCondition(condicao, ordem);
	}
}
