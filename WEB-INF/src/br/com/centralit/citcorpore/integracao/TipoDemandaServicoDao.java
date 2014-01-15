package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.AnexoBaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.TipoDemandaServicoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class TipoDemandaServicoDao extends CrudDaoDefaultImpl {

	public TipoDemandaServicoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	private static final long serialVersionUID = -2983316142102074344L;

	public Class getBean() {
		return TipoDemandaServicoDTO.class;
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();

		listFields.add(new Field("idTipoDemandaServico", "idTipoDemandaServico", true, true, false, false));
		listFields.add(new Field("nomeTipoDemandaServico", "nomeTipoDemandaServico", false, false, false, false));
		listFields.add(new Field("classificacao", "classificacao", false, false, false, false));
		listFields.add(new Field("deleted", "deleted", false, false, false, false));

		return listFields;
	}

	public String getTableName() {
		return "TIPODEMANDASERVICO";
	}

	public Collection find(IDto obj) throws Exception {
		return null;
	}

	public Collection list() throws Exception {
		List list = new ArrayList();
		list.add(new Order("nomeTipoDemandaServico"));
		return super.list(list);
	}

	public Collection<TipoDemandaServicoDTO> listSolicitacoes() throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("classificacao", "<>", "O"));
		condicao.add(new Condition("deleted", "is", null));
		condicao.add(new Condition(Condition.OR, "deleted", "<>", "Y"));
		ordenacao.add(new Order("nomeTipoDemandaServico"));
		return super.findByCondition(condicao, ordenacao);
	}
	
	/**
	 * Retorna lista de Tipo Demanda por nome.
	 * 
	 * @return Collection
	 * @throws Exception
	 */
	public Collection findByNome(TipoDemandaServicoDTO tipoDemandaServicoDTO) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();

		condicao.add(new Condition("nomeTipoDemandaServico", "=", tipoDemandaServicoDTO.getNomeTipoDemandaServico())); 
		ordenacao.add(new Order("nomeTipoDemandaServico"));
		return super.findByCondition(condicao, ordenacao);
	}

	
	/**
	 * @author euler.ramos
	 * @param tipoDemandaServicoDTO
	 * @return
	 * @throws Exception
	 */
	public Collection findByClassificacao(String classificacao) throws Exception {
		StringBuilder sql = new StringBuilder(); 
		sql.append("select * from tipodemandaservico");
		
		if ((classificacao!=null)&&(classificacao.length()>0)){
			sql.append(" where (classificacao in ("+classificacao+"))");
		}
		sql.append("order by idtipodemandaservico;");
		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), null);
		
		List listRetorno = new ArrayList();
		listRetorno.add("idTipoDemandaServico");
		listRetorno.add("nomeTipoDemandaServico");
		listRetorno.add("classificacao");
		listRetorno.add("deleted");

		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		return (result == null ? new ArrayList<AnexoBaseConhecimentoDTO>() : result);
	}
}