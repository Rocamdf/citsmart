package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ContratosUnidadesDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class ContratosUnidadesDAO  extends CrudDaoDefaultImpl {

	
	
	public ContratosUnidadesDAO(){
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8854870512383924774L;

	@Override
	public Collection find(IDto obj) throws Exception {
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Collection getFields() {
		Collection listFields = new ArrayList();

		listFields.add(new Field("idUnidade", "idUnidade", true, false, false, false));
		listFields.add(new Field("idContrato", "idContrato", true, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "contratosunidades";
	}

	@Override
	public Collection list() throws Exception {
		return null;
	}

	@SuppressWarnings("unchecked")
	public void deleteByIdUnidade(Integer idUnidade) throws Exception {

		@SuppressWarnings("rawtypes")
		List condicao = new ArrayList();

		condicao.add(new Condition("idUnidade", "=", idUnidade));

		super.deleteByCondition(condicao);
	}
	
	@Override
	public Class getBean() {
		return ContratosUnidadesDTO.class;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Collection<ContratosUnidadesDTO> findByIdUnidade(Integer idUnidade) throws Exception{
		
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		
		ordenacao.add(new Order("idContrato"));
		condicao.add(new Condition("idUnidade", "=", idUnidade));
		
		return super.findByCondition(condicao, ordenacao);
	}
	
	
}
