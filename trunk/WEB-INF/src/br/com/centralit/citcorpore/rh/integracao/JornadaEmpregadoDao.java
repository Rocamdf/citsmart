package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.JornadaEmpregadoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class JornadaEmpregadoDao extends CrudDaoDefaultImpl {
	public JornadaEmpregadoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection getFields() {
		Collection listFields = new ArrayList();
		
		listFields.add(new Field("idJornada" ,"idJornada", true, true, false, false));
		listFields.add(new Field("descricao" ,"descricao", false, false, false, false));
		listFields.add(new Field("escala" ,"escala", false, false, false, false));
		listFields.add(new Field("considerarFeriados" ,"considerarFeriados", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return "RH_JornadaEmpregado";
	}
	 public Collection list() throws Exception {
	        List list = new ArrayList();
	        list.add(new Order("idJornada"));
	        return super.list(list);
	    }

	public Class getBean() {
		return JornadaEmpregadoDTO.class;
	}
	public Collection find(IDto arg0) throws Exception {
		return null;
	}
}
