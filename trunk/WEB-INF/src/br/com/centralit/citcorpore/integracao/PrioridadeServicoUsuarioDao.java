package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.PrioridadeServicoUsuarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class PrioridadeServicoUsuarioDao extends CrudDaoDefaultImpl {
	public PrioridadeServicoUsuarioDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idUsuario" ,"idUsuario", true, false, false, false));
		listFields.add(new Field("idAcordoNivelServico" ,"idAcordoNivelServico", true, false, false, false));
		listFields.add(new Field("idPrioridade" ,"idPrioridade", false, false, false, false));
		listFields.add(new Field("idServicoContrato" ,"idServicoContrato", false, false, false, false));
		listFields.add(new Field("dataInicio" ,"dataInicio", false, false, false, false));
		listFields.add(new Field("dataFim" ,"dataFim", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "PrioridadeServicoUsuario";
	}
	public Collection list() throws Exception {
		List ordenacao = new ArrayList();
		ordenacao.add(new Order("idUsuario"));
		return super.list(ordenacao);
	}

	public Class getBean() {
		return PrioridadeServicoUsuarioDTO.class;
	}
	public Collection find(IDto model) throws Exception {
		List ordenacao = new ArrayList();
		ordenacao.add(new Order("idUsuario"));
		return super.find(model, ordenacao);
	}
	
	public Collection findByIdAcordoNivelServico(Integer idAcordoNivelServico) throws Exception {
	    List condicao = new ArrayList();
	    List ordenacao = new ArrayList();
	    
	    condicao.add(new Condition("idAcordoNivelServico", "=", idAcordoNivelServico));
	    ordenacao.add(new Order("idUsuario"));
	    
	    return super.findByCondition(condicao, ordenacao);
	}
	
	public PrioridadeServicoUsuarioDTO findByIdAcordoNivelServicoAndIdUsuario(Integer idAcordoNivelServico, Integer idUsuario) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idAcordoNivelServico", "=", idAcordoNivelServico));
		condicao.add(new Condition("idUsuario", "=", idUsuario));
		condicao.add(new Condition("dataFim", "is", null));
		
		Collection col = super.findByCondition(condicao, null);
		if (col == null || col.size() == 0) return null;
		return (PrioridadeServicoUsuarioDTO) ((List) col).get(0);
	}
	
	public void deleteByIdAcordoNivelServico(Integer idAcordoNivelServico) throws Exception{
		List condicao = new ArrayList();
		condicao.add(new Condition("idAcordoNivelServico", "=", idAcordoNivelServico));
		super.deleteByCondition(condicao);
	}
	
}
