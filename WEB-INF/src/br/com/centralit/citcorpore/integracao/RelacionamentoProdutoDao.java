package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.RelacionamentoProdutoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
public class RelacionamentoProdutoDao extends CrudDaoDefaultImpl {
	public RelacionamentoProdutoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idTipoProduto" ,"idTipoProduto", true, false, false, false));
		listFields.add(new Field("idTipoProdutoRelacionado" ,"idTipoProdutoRelacionado", true, false, false, false));
		listFields.add(new Field("tipoRelacionamento" ,"tipoRelacionamento", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "RelacionamentoProduto";
	}
	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return RelacionamentoProdutoDTO.class;
	}
	public Collection find(IDto arg0) throws Exception {
		return null;
	}
	public Collection findByIdTipoProduto(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idTipoProduto", "=", parm)); 
		ordenacao.add(new Order("idTipoProdutoRelacionado"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdTipoProduto(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idTipoProduto", "=", parm));
		super.deleteByCondition(condicao);
	}
}
