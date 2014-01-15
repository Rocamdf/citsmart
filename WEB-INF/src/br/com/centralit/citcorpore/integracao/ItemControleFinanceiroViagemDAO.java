package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ItemControleFinanceiroViagemDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;


@SuppressWarnings({"rawtypes","unchecked"})
public class ItemControleFinanceiroViagemDAO extends CrudDaoDefaultImpl {
	

	private static final long serialVersionUID = 1L;

	public ItemControleFinanceiroViagemDAO(){
		super(Constantes.getValue("DATABASE_ALIAS"),null);
	}

	@Override
	public Collection find(IDto obj) throws Exception {
		return null;
	}

	@Override
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("iditemcontrolefinanceiroviagem", "idItemControleFinanceiroViagem", true, true, false, false));
		listFields.add(new Field("idcontrolefinanceiroviagem", "idControleFinanceiroViagem", false, false, false, false));
		listFields.add(new Field("idformapagamento", "idFormaPagamento", false, false, false, false));
		listFields.add(new Field("idadiantamentoviagem", "idAdiantamentoViagem", false, false, false, false));
		listFields.add(new Field("idfornecedor", "idFornecedor", false, false, false, false));
		listFields.add(new Field("idjustificativa", "idJustificativa", false, false, false, false));
		listFields.add(new Field("idsolicitacaoservico", "idSolicitacaoServico", false, false, false, false));
		listFields.add(new Field("idempregado", "idEmpregado", false, false, false, false));
		listFields.add(new Field("idtipomovimfinanceiraviagem", "idTipoMovimFinanceiraViagem", false, false, false, false));
		listFields.add(new Field("complementojustificativa", "complementoJustificativa", false, false, false, false));
		listFields.add(new Field("quantidade", "quantidade", false, false, false, false));
		listFields.add(new Field("valorunitario", "valorUnitario", false, false, false, false));
		listFields.add(new Field("valoradiantamento", "valorAdiantamento", false, false, false, false));
		listFields.add(new Field("tipopassagem", "tipoPassagem", false, false, false, false));
		listFields.add(new Field("localizador", "localizador", false, false, false, false));
		listFields.add(new Field("assento", "assento", false, false, false, false));
		listFields.add(new Field("situacao", "situacao", false, false, false, false));
		listFields.add(new Field("dataFim", "dataFim", false, false, false, false));
		listFields.add(new Field("prazocotacao", "prazoCotacao", false, false, false, false));
		listFields.add(new Field("observacao", "observacao", false, false, false, false));
		listFields.add(new Field("dataexecucao", "dataExecucao", false, false, false, false));
		listFields.add(new Field("datahoraprazocotacao", "dataHoraPrazoCotacao", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return this.getOwner() + "itemcontrolefinanceiroviagem";
	}

	@Override
	public Collection list() throws Exception {
		return null;
	}

	@Override
	public Class getBean() {
		return ItemControleFinanceiroViagemDTO.class;
	}
	
	public Collection findByidItemControleFinanceiroViagem(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idItemControleFinanceiroViagem", "=", parm));
		condicao.add(new Condition(Condition.AND, "dataFim", "is", null));
		ordenacao.add(new Order("idItemControleFinanceiroViagem"));
		return super.findByCondition(condicao, ordenacao);
	}
	
	public boolean atualizaItensControleFinanceiro(Integer idControleFinanceiro, Integer idSolicitacaoServico, Integer idEmpregado) {
		List parametros = new ArrayList();
		parametros.add(idControleFinanceiro);
		parametros.add(idSolicitacaoServico);
		parametros.add(idEmpregado);
		String sql = "update " + getTableName() + " set idControleFinanceiroViagem = ? where idSolicitacaoServico = ? and idEmpregado = ?";
		
		try {
			super.execUpdate(sql, parametros.toArray());
			return true;
		} catch (PersistenceException e) {
			return false;
		}
	}
	
	public Collection<ItemControleFinanceiroViagemDTO> findByIdSolicitacaoAndIdEmpregado(Integer idSolicitacaoServico, Integer idEmpregado) throws Exception{
		List parametros = new ArrayList();
		List listRetorno = new ArrayList();
		List res = new ArrayList();
		
		parametros.add(idSolicitacaoServico);
		parametros.add(idEmpregado);
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("select item.iditemcontrolefinanceiroviagem, item.idcontrolefinanceiroviagem, item.idformapagamento, item.idadiantamentoviagem, item.idfornecedor, item.idjustificativa, ");
		sql.append("item.idsolicitacaoservico, item.idempregado, item.idtipomovimfinanceiraviagem, item.complementojustificativa, item.quantidade, item.valorunitario, item.valoradiantamento, ");
		sql.append("item.tipopassagem, item.localizador, item.assento, item.situacao, item.dataFim, item.prazocotacao, ");
		sql.append("f.nomefantasia, t.nome, e.nome , t.classificacao, item.datahoraprazocotacao ");
		sql.append("from itemcontrolefinanceiroviagem item ");
		sql.append("inner join fornecedor f on f.idfornecedor = item.idfornecedor ");
		sql.append("inner join tipomovimfinanceiraviagem t on t.idtipoMovimFinanceiraViagem = item.idtipomovimfinanceiraviagem ");
		sql.append("inner join empregados e on e.idempregado = item.idempregado ");
		sql.append("where item.idsolicitacaoservico = ? and item.idempregado = ? and item.datafim is null ");
		sql.append("and item.datafim is null ");
		sql.append("order by item.idtipomovimfinanceiraviagem");
		
		listRetorno = this.getCamposRetornoAll();
		listRetorno.add("nomeFornecedor");
		listRetorno.add("nomeTipoMovimFinanceira");
		listRetorno.add("nomeIntegrante");
		listRetorno.add("classificacao");
		listRetorno.add("dataHoraPrazoCotacao");
		
		res = this.execSQL(sql.toString(), parametros.toArray());
		
		if(res != null && !res.isEmpty())
			return  this.listConvertion(ItemControleFinanceiroViagemDTO.class, res, listRetorno);
		else
			return null;
	}
	
	private List getCamposRetornoAll(){
		List listRetorno = new ArrayList();
		
		listRetorno.add("idItemControleFinanceiroViagem");
		listRetorno.add("idControleFinanceiroViagem");
		listRetorno.add("idFormaPagamento");
		listRetorno.add("idAdiantamentoViagem");						
		listRetorno.add("idFornecedor");
		listRetorno.add("idJustificativa");
		listRetorno.add("idSolicitacaoServico");
		listRetorno.add("idEmpregado");
		listRetorno.add("idTipoMovimFinanceiraViagem");
		listRetorno.add("complementoJustificativa");
		listRetorno.add("quantidade");
		listRetorno.add("valorUnitario");
		listRetorno.add("valorAdiantamento");
		listRetorno.add("tipoPassagem");
		listRetorno.add("localizador");
		listRetorno.add("assento");
		listRetorno.add("situacao");
		listRetorno.add("dataFim");
		listRetorno.add("prazoCotacao");
		
		return listRetorno;
	}
	
	public Collection<ItemControleFinanceiroViagemDTO> listByidSolicitacaoAndIdEmpregado(Integer idSolicitacaoServico, Integer idEmpregado) throws Exception{
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
		condicao.add(new Condition(Condition.AND, "idEmpregado", "=", idEmpregado));
		condicao.add(new Condition(Condition.AND, "dataFim", "is", null));
		ordenacao.add(new Order("idTipoMovimFinanceiraViagem"));
		return super.findByCondition(condicao, ordenacao);
	}
	public Collection<ItemControleFinanceiroViagemDTO> listByidSolicitacao(Integer idSolicitacaoServico) throws Exception{
		List condicao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
		condicao.add(new Condition(Condition.AND, "dataFim", "is", null));
		return super.findByCondition(condicao, null);
	}
	
	public Collection<ItemControleFinanceiroViagemDTO> listaItensPrecisaAdiantamento(Integer idSolicitacaoServico, Integer idEmpregado) throws Exception{
		return this.listaItens(idSolicitacaoServico, idEmpregado, "S");
	}
	
	public Collection listaItensPrecisaAdiantamentoPorSolicitacao(Integer idSolicitacaoServico) throws Exception{
		StringBuilder sql = new StringBuilder();
		
		List parametros = new ArrayList();
		List listRetorno = new ArrayList();
		List lista = new ArrayList();
		
		sql.append("select item.iditemcontrolefinanceiroviagem, item.idcontrolefinanceiroviagem,  item.idadiantamentoviagem, ");
		sql.append("	item.idsolicitacaoservico, item.idtipomovimfinanceiraviagem ");
		sql.append("from itemcontrolefinanceiroviagem item ");
		sql.append("	inner join tipomovimfinanceiraviagem tipo on tipo.idtipoMovimFinanceiraViagem = item.idtipomovimfinanceiraviagem ");
		sql.append("	where item.idsolicitacaoservico = ? ");
		sql.append("	and tipo.situacao = 'A' ");
		sql.append("	and tipo.permiteAdiantamento = 'S' ");
		sql.append("	and item.datafim is null ");
		
		parametros.add(idSolicitacaoServico);
		
		lista = this.execSQL(sql.toString(), parametros.toArray());
		
		
		listRetorno.add("idItemControleFinanceiroViagem");
		listRetorno.add("idControleFinanceiroViagem");
		listRetorno.add("idAdiantamentoViagem");						
		listRetorno.add("idSolicitacaoServico");
		listRetorno.add("idTipoMovimFinanceiraViagem");

		
		if(lista != null && !lista.isEmpty())
			return this.listConvertion(ItemControleFinanceiroViagemDTO.class, lista, listRetorno);
		else
			return null;
	}

	public Collection<ItemControleFinanceiroViagemDTO> listaItensPrecisaCompra(Integer idSolicitacaoServico, Integer idEmpregado) throws Exception {
		return this.listaItens(idSolicitacaoServico, idEmpregado, "N");
	}
	
	private Collection<ItemControleFinanceiroViagemDTO> listaItens(Integer idSolicitacaoServico, Integer idEmpregado, String tipo) throws Exception {
		StringBuilder sql = new StringBuilder();
		
		List parametros = new ArrayList();
		List listRetorno = new ArrayList();
		List lista = new ArrayList();
		
		sql.append("select item.iditemcontrolefinanceiroviagem, item.idcontrolefinanceiroviagem, item.idformapagamento, item.idadiantamentoviagem, item.idfornecedor, item.idjustificativa, ");
		sql.append("	item.idsolicitacaoservico, item.idempregado, item.idtipomovimfinanceiraviagem, item.complementojustificativa, item.quantidade, item.valorunitario, item.valoradiantamento, ");
		sql.append("	item.tipopassagem, item.localizador, item.assento, item.situacao, item.dataFim, item.prazocotacao, tipo.nome, f.nomeFantasia, item.datahoraprazocotacao ");
		sql.append("from itemcontrolefinanceiroviagem item ");
		sql.append("	inner join tipomovimfinanceiraviagem tipo on tipo.idtipoMovimFinanceiraViagem = item.idtipomovimfinanceiraviagem ");
		sql.append("	inner join fornecedor f on f.idfornecedor = item.idfornecedor  ");
		sql.append("	where item.idsolicitacaoservico = ? ");
		sql.append("	and item.idempregado = ? ");
		sql.append("	and tipo.situacao = 'A' ");
		sql.append("	and tipo.permiteAdiantamento = ? ");
		sql.append("	and item.datafim is null ");
		
		parametros.add(idSolicitacaoServico);
		parametros.add(idEmpregado);
		parametros.add(tipo);
		
		lista = this.execSQL(sql.toString(), parametros.toArray());
		
		listRetorno = this.getCamposRetornoAll();
		listRetorno.add("nomeTipoMovimFinanceira");
		listRetorno.add("nomeFornecedor");
		listRetorno.add("dataHoraPrazoCotacao");
		
		if(lista != null && !lista.isEmpty())
			return this.listConvertion(ItemControleFinanceiroViagemDTO.class, lista, listRetorno);
		else
			return null;
	}

	public Collection<ItemControleFinanceiroViagemDTO> findAllBySolicitacao(Integer idSolicitacao) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacao));
		return super.findByCondition(condicao, null);
	}
	
}