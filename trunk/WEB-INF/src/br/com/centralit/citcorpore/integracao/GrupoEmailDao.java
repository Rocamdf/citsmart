package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.GrupoEmailDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class GrupoEmailDao extends CrudDaoDefaultImpl {

	public GrupoEmailDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	private static final long serialVersionUID = -8936627363011830567L;

	public Class getBean() {
		return GrupoEmailDTO.class;
	}
	
	public Collection getFields() {
		Collection listFields = new ArrayList();
		
		listFields.add(new Field("idGrupo", "idGrupo", true, false, false, false));
		listFields.add(new Field("idEmpregado", "idEmpregado", false, false, false, false));
		listFields.add(new Field("nome", "nome", false, false, false, false));
		listFields.add(new Field("email", "email", true, false, false, false));		
		return listFields;
	}
	
	
	public Collection find(IDto obj) throws Exception {

		return null;
	}


	public String getTableName() {
		return "GRUPOSEMAILS";
	}


	public Collection list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<GrupoEmailDTO> findByIdGrupo(Integer idGrupo) throws Exception {
		Object[] objs = new Object[] { idGrupo };
		String sql = "SELECT G.idGrupo, GE.idEmpregado, GE.email, GE.nome " + "  FROM " + getTableName() + " GE INNER JOIN GRUPO G ON G.idGrupo = GE.idGrupo " + " WHERE GE.idGrupo = ? ";
		List lista = this.execSQL(sql, objs);

		List listRetorno = new ArrayList();
		listRetorno.add("idGrupo");
		listRetorno.add("idEmpregado");
		listRetorno.add("email");
		listRetorno.add("nome");
		if (lista != null && !lista.isEmpty()) {
			return this.engine.listConvertion(getBean(), lista, listRetorno);
		} else {
			return null;
		}
	}

	public void deleteByIdGrupoAndEmail(Integer idGrupo, String email) throws Exception {
		List lstCondicao = new ArrayList();
		lstCondicao.add(new Condition( "idGrupo", "=", idGrupo));
		lstCondicao.add(new Condition(Condition.AND, "email", "=", email));
		super.deleteByCondition(lstCondicao);
		
	}

	public void deleteByIdGrupo(Integer idGrupo) throws Exception {
		List lstCondicao = new ArrayList();
		lstCondicao.add(new Condition( "idGrupo", "=", idGrupo));
		super.deleteByCondition(lstCondicao);
	}
	


}
