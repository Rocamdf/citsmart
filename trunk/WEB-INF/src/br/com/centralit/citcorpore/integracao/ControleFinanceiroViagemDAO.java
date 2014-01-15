package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ControleFinanceiroViagemDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings({"rawtypes","unchecked"})
public class ControleFinanceiroViagemDAO extends CrudDaoDefaultImpl{

	public ControleFinanceiroViagemDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	private static final long serialVersionUID = 1L;

	
	@Override
	public Collection find(IDto obj) throws Exception {
		return null;
	}

	
	@Override
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idcontrolefinanceiroviagem" ,"idControleFinanceiroViagem", true, true, false, false));
		listFields.add(new Field("idresponsavel" ,"idResponsavel", false, false, false, false));
		listFields.add(new Field("idmoeda" ,"idMoeda", false, false, false, false));
		listFields.add(new Field("datahora" ,"datahora", false, false, false, false));
		listFields.add(new Field("situacao" ,"situacao", false, false, false, false));
		listFields.add(new Field("observacoes" ,"observacoes", false, false, false, false));
		listFields.add(new Field("idResponsavelCompras" ,"idresponsavelcompras", false, false, false, false));
		
		return listFields;
	}

	@Override
	public String getTableName() {
		return this.getOwner() + "controlefinanceiroviagem";
	}

	@Override
	public Collection list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class getBean() {
		return ControleFinanceiroViagemDTO.class;
	}

	/**
	 * Retorna controle financeiro viagem de acordo com o idsolicitaçãoservico passado
	 * @param idSolicitacaoServico
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	public ControleFinanceiroViagemDTO buscarControleFinanceiroViagemPorIdSolicitacao(Integer idSolicitacaoServico) throws Exception{
		List parametros = new ArrayList();
		List listRetorno = new ArrayList();
		List res = new ArrayList();
		
		parametros.add(idSolicitacaoServico);
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("select controlefinanceiroviagem.idcontrolefinanceiroviagem,  ");
		sql.append("controlefinanceiroviagem.idresponsavel,controlefinanceiroviagem.idmoeda,controlefinanceiroviagem.datahora, ");
		sql.append("controlefinanceiroviagem.observacoes,moedas.nomemoeda ");
		sql.append("from itemcontrolefinanceiroviagem ");
		sql.append("inner join controlefinanceiroviagem on controlefinanceiroviagem.idcontrolefinanceiroviagem = itemcontrolefinanceiroviagem.idcontrolefinanceiroviagem  ");
		sql.append("inner join moedas on moedas.idmoeda = controlefinanceiroviagem.idmoeda  ");
		if(idSolicitacaoServico!=null){
			sql.append("where itemcontrolefinanceiroviagem.idsolicitacaoservico = ?  ");
		}
		
		
		listRetorno.add("idControleFinanceiroViagem");
		listRetorno.add("idResponsavel");
		listRetorno.add("idMoeda");
		listRetorno.add("datahora");
		listRetorno.add("observacoes");
		listRetorno.add("nomeMoeda");
		
		
		res = this.execSQL(sql.toString(), parametros.toArray());
		
		if(res != null && !res.isEmpty()){
			ControleFinanceiroViagemDTO controleFinanceiroViagemDTO = new ControleFinanceiroViagemDTO();
			controleFinanceiroViagemDTO = (ControleFinanceiroViagemDTO) this.listConvertion(ControleFinanceiroViagemDTO.class, res, listRetorno).get(0);
			return  controleFinanceiroViagemDTO;
		}else{
			return null;
		}
		
	}
}
