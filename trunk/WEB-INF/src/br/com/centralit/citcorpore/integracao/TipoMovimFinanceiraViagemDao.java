package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.TipoMovimFinanceiraViagemDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field ;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * @author ronnie.lopes
 * 
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class TipoMovimFinanceiraViagemDao extends CrudDaoDefaultImpl {

	private static final long serialVersionUID = 1153789196419291108L;

	/**
     * 
     */
	public TipoMovimFinanceiraViagemDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}


	public Collection getFields() {
		Collection listFields = new ArrayList();

		listFields.add(new Field("idtipoMovimFinanceiraViagem", "idtipoMovimFinanceiraViagem", true, true, false, false));
		listFields.add(new Field("nome", "nome", false, false, false, false));
		listFields.add(new Field("descricao", "descricao", false, false, false, false));
		listFields.add(new Field("classificacao", "classificacao", false, false, false, false));
		listFields.add(new Field("situacao", "situacao", false, false, false, false));
		listFields.add(new Field("exigePrestacaoConta", "exigePrestacaoConta", false, false, false, false));
		listFields.add(new Field("exigeJustificativa", "exigeJustificativa", false, false, false, false));
		listFields.add(new Field("permiteAdiantamento", "permiteAdiantamento", false, false, false, false));
		listFields.add(new Field("valorPadrao", "valorPadrao", false, false, false, false));
		listFields.add(new Field("tipo", "tipo", false, false, false, false));
		

		return listFields;
	}

	public String getTableName() {
		return "tipoMovimFinanceiraViagem";
	}


	public Collection find(IDto obj) throws Exception {
		List ordem = new ArrayList();
		ordem.add(new Order("nome"));
		return super.find(obj, ordem);
	}

	
	public Collection list() throws Exception {
		List list = new ArrayList();
		list.add(new Order("nome"));
		return super.list(list);
	}

	
	public Class getBean() {
		return TipoMovimFinanceiraViagemDTO.class;
	}
	
	/**
	 * 
	 * @param classificacao
	 * @return
	 * @throws Exception
	 * geber.costa
	 * 
	 * Método que traz todos os nomes da classificação escolhida(passada por parâmetro)
	 */
	public List<TipoMovimFinanceiraViagemDTO> listByClassificacao(String classificacao) throws Exception {
			String sql = "select nome,idtipoMovimFinanceiraViagem from "+getTableName()+" where classificacao = ? and situacao like 'A'";
			List parametro = new ArrayList();
			List<String> listRetorno = new ArrayList<String>();
			parametro.add(classificacao);
			listRetorno.add("nome");
			listRetorno.add("idtipoMovimFinanceiraViagem");
			List lista = this.execSQL(sql, parametro.toArray());
			return this.engine.listConvertion(TipoMovimFinanceiraViagemDTO.class, lista, listRetorno);
		}
	
	public List<TipoMovimFinanceiraViagemDTO> findByMovimentacao(Long idtipoMovimFinanceiraViagem) throws Exception {
		String sql = "select valorPadrao from "+getTableName()+" where idtipoMovimFinanceiraViagem = ? and situacao like 'A'";
		List parametro = new ArrayList();
		List<String> listRetorno = new ArrayList<String>();
		parametro.add(idtipoMovimFinanceiraViagem);
		listRetorno.add("valorPadrao");
		List lista = this.execSQL(sql, parametro.toArray());
		return this.engine.listConvertion(TipoMovimFinanceiraViagemDTO.class, lista, listRetorno);
	}
	
	public List<TipoMovimFinanceiraViagemDTO> recuperaTiposAtivos() throws Exception{
		List condicao = new ArrayList();
		condicao.add(new Condition("situacao", "=", "A"));
		return (List<TipoMovimFinanceiraViagemDTO>) super.findByCondition(condicao, null);
	}
	
}
