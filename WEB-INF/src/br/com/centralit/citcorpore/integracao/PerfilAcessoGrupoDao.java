package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoGrupoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
public class PerfilAcessoGrupoDao extends CrudDaoDefaultImpl {

	public PerfilAcessoGrupoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);

	}

	@Override
	public Collection find(IDto arg0) throws Exception {
		return null;
	}

	@Override
	public Collection getFields() {
		Collection listFields = new ArrayList();

		listFields.add(new Field("IDPERFIL", "idPerfilAcessoGrupo", true, false, false, false));
		listFields.add(new Field("IDGRUPO", "idGrupo", true, false, false, false));
		listFields.add(new Field("DATAINICIO", "dataInicio", true, false, false, false));
		listFields.add(new Field("DATAFIM", "dataFim", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "PERFILACESSOGRUPO";
	}

	public PerfilAcessoGrupoDTO listByIdGrupo(PerfilAcessoGrupoDTO obj) throws Exception {
		List list = new ArrayList();
		List fields = new ArrayList();
		String sql = "select idperfil from " + getTableName() + " where idgrupo = " + obj.getIdGrupo() + " ";
		fields.add("idPerfilAcessoGrupo");
		list = this.execSQL(sql, null);

		if (list != null && !list.isEmpty()) {
			return (PerfilAcessoGrupoDTO) this.listConvertion(getBean(), list, fields).get(0);
		} else {
			return null;
		}
	}

	/**
	 * Retorna perfil de acesso ativo do grupo.
	 * 
	 * @param grupo
	 * @return
	 * @throws Exception
	 */
	public PerfilAcessoGrupoDTO obterPerfilAcessoGrupo(GrupoDTO grupo) throws Exception {
		List list = new ArrayList();
		List fields = new ArrayList();
		StringBuffer sql = new StringBuffer();
		List parametros = new ArrayList();

		sql.append("SELECT idperfil FROM perfilacessogrupo WHERE idgrupo = ? AND datafim IS NULL");
		parametros.add(grupo.getIdGrupo());

		fields.add("idPerfilAcessoGrupo");

		list = this.execSQL(sql.toString(), parametros.toArray());

		if (list != null && !list.isEmpty()) {
			return (PerfilAcessoGrupoDTO) this.listConvertion(getBean(), list, fields).get(0);
		} else {
			return null;
		}
	}

	@Override
	public Class getBean() {
		return PerfilAcessoGrupoDTO.class;
	}

	@Override
	public Collection list() throws Exception {
		return null;
	}

	public void updateDataFim(PerfilAcessoGrupoDTO obj) throws Exception {
		List parametros = new ArrayList();
		parametros.add(UtilDatas.getDataAtual());
		parametros.add(obj.getIdGrupo());

		String sql = "UPDATE " + getTableName() + " SET DATAFIM = ? WHERE IDGRUPO = ? ";

		this.execUpdate(sql, parametros.toArray());
	}

	@Override
	public void delete(IDto obj) throws Exception {
		PerfilAcessoGrupoDTO dto = (PerfilAcessoGrupoDTO) obj;
		String sql = "DELETE FROM " + getTableName() + " WHERE IDGRUPO = " + dto.getIdGrupo() + " ";
		this.execUpdate(sql, null);
	}

	/**
	 * Retorna PerfilAcessoGrupo Ativos por idPerfilAcesso.
	 * 
	 * @param idPerfilAcesso
	 * @return
	 * @throws Exception
	 */
	public Collection findByIdPerfil(Integer idPerfilAcesso) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idPerfilAcessoGrupo", "=", idPerfilAcesso));
		condicao.add(new Condition("dataFim", "is", null));
		ordenacao.add(new Order("idPerfilAcessoGrupo"));
		return super.findByCondition(condicao, ordenacao);
	}

}
