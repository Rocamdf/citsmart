package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.CargosDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class CargosDao extends CrudDaoDefaultImpl {

	public CargosDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
		// TODO Auto-generated constructor stub
	}

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	@Override
	public Collection find(IDto obj) throws Exception {

		return null;
	}

	@Override
	public Collection getFields() {

		Collection listFields = new ArrayList();

		listFields.add(new Field("IDCARGO", "idCargo", true, true, false, false));
		listFields.add(new Field("NOMECARGO", "nomeCargo", false, false, false, false));
		listFields.add(new Field("DATAINICIO", "dataInicio", false, false, false, false));
		listFields.add(new Field("DATAFIM", "dataFim", false, false, false, false));
		listFields.add(new Field("IDDESCRICAOCARGO", "idDescricaoCargo", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "CARGOS";
	}

	@Override
	public Collection list() throws Exception {
		List list = new ArrayList();
		list.add(new Order("nomeCargo"));
		return super.list(list);
	}

	@Override
	public Class getBean() {
		return CargosDTO.class;
	}

	/**
	 * Retorna lista de status de usuário.
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public boolean consultarCargosAtivos(CargosDTO obj) throws Exception {
		List parametro = new ArrayList();
		List list = new ArrayList();
		String sql = "select idcargo From " + getTableName() + "  where  nomecargo = ?   and dataFim is null ";
		
		if(obj.getIdCargo() != null){
			sql+=" and idcargo <> "+ obj.getIdCargo();
		}
		
		parametro.add(obj.getNomeCargo());
		list = this.execSQL(sql, parametro.toArray());
		if (list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean validaInsert(CargosDTO obj){
		
		
		return false;
		
	}

	public Collection findByNomeCargos(CargosDTO cargosDTO) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();

		condicao.add(new Condition("nomeCargo", "=", cargosDTO.getNomeCargo())); 
		ordenacao.add(new Order("nomeCargo"));
		condicao.add(new Condition(Condition.AND, "dataFim", "is", null));
		return super.findByCondition(condicao, ordenacao);
	}
	
	public Collection<CargosDTO>  seCargoJaCadastrado(CargosDTO cargosDTO) throws Exception {
		List parametro = new ArrayList();
		List list = new ArrayList();
		String sql = "";
		sql = " select lower(nomecargo) from cargos where nomecargo = lower(?) ";

		parametro.add(cargosDTO.getNomeCargo().trim().toLowerCase());
		list = this.execSQL(sql, parametro.toArray());
		return list;
	}
	
	public Collection<CargosDTO> listarAtivos() throws Exception{
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();

		ordenacao.add(new Order("nomeCargo"));
		condicao.add(new Condition("dataFim", "is", null));
		return super.findByCondition(condicao, ordenacao);
	}
	
}
