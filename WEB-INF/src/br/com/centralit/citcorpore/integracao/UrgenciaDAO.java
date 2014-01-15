package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.UrgenciaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * 
 * @author rodrigo.oliveira
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class UrgenciaDAO extends CrudDaoDefaultImpl {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8452867069144739552L;

	public UrgenciaDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	@Override
	public Collection find(IDto obj) throws Exception {
		List list = new ArrayList();
		list.add(new Order("idUrgencia"));
		return super.find(obj, list);
	}

	@Override
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idUrgencia", "idUrgencia", true, true, false, false));
		listFields.add(new Field("nivelUrgencia", "nivelUrgencia", false, false, false, false));
		listFields.add(new Field("siglaUrgencia", "siglaUrgencia", false, false, false, false));
		return listFields;
	}

	@Override
	public String getTableName() {
		return "Urgencia";
	}

	@Override
	public Collection list() throws Exception {
		List list = new ArrayList();
		list.add(new Order("idUrgencia"));
		return super.list(list);
	}

	@Override
	public Class getBean() {
		return UrgenciaDTO.class;
	}

	public void deleteUrgencia() throws Exception{
//		String sql = "delete from " + getTableName() + " where idurgencia > 0";
//		this.execUpdate(sql, null);
		List condicao = new ArrayList();
		List parametros = new ArrayList();
		parametros.add(0);
		condicao.add(new Condition("idUrgencia", ">", parametros));
		super.deleteByCondition(condicao);
	}

	public List<UrgenciaDTO> restoreBySigla(String siglaUrgencia) throws Exception{
		List condicao = new ArrayList();
		condicao.add(new Condition("siglaUrgencia", "=", siglaUrgencia));
		return (List<UrgenciaDTO>) super.findByCondition(condicao, null);
	}
	
	public List<UrgenciaDTO> restoreByNivel(String nivelUrgencia) throws Exception{
		List condicao = new ArrayList();
		condicao.add(new Condition("nivelUrgencia", "=", nivelUrgencia));
		return (List<UrgenciaDTO>) super.findByCondition(condicao, null);
	}

}
