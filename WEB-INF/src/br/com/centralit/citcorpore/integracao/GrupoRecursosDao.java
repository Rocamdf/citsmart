package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import br.com.centralit.citcorpore.bean.GrupoRecursosDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;;

public class GrupoRecursosDao extends CrudDaoDefaultImpl {
	public GrupoRecursosDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idGrupoRecurso" ,"idGrupoRecurso", true, true, false, false));
		listFields.add(new Field("nomeGrupoRecurso" ,"nomeGrupoRecurso", false, false, false, false));
		listFields.add(new Field("situacao" ,"situacao", false, false, false, false));
		listFields.add(new Field("deleted" ,"deleted", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "GrupoRecursos";
	}
	public Collection list() throws Exception {
		return super.list("nomeGrupoRecurso");
	}

	public Class getBean() {
		return GrupoRecursosDTO.class;
	}
	public Collection find(IDto arg0) throws Exception {
		return null;
	}
}
