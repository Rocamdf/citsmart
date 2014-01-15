package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.NotificacaoUsuarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class NotificacaoUsuarioDao extends CrudDaoDefaultImpl {

	public NotificacaoUsuarioDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Collection find(IDto obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection getFields() {
		Collection listFields = new ArrayList();

		listFields.add(new Field("idnotificacao", "idNotificacao", true, false, false, false));
		listFields.add(new Field("idusuario", "idUsuario", true, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "NOTIFICACAOUSUARIO";
	}

	@Override
	public Collection list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class getBean() {
		// TODO Auto-generated method stub
		return NotificacaoUsuarioDTO.class;
	}
	
	public Collection<NotificacaoUsuarioDTO> listaIdUsuario(Integer idNotificacao) throws Exception {
		Object[] objs = new Object[] { idNotificacao };
		String sql = "SELECT  idusuario FROM " + getTableName() + " WHERE idnotificacao = ?  ";
		List lista = this.execSQL(sql, objs);

		List listRetorno = new ArrayList();
		listRetorno.add("idUsuario");
		if (lista != null && !lista.isEmpty()) {
			return this.engine.listConvertion(getBean(), lista, listRetorno);
		} else {
			return null;
		}
	}
	
	public void deleteByIdNotificacaoUsuario(Integer idNotificacao) throws Exception {
		List lstCondicao = new ArrayList();
		lstCondicao.add(new Condition("idNotificacao", "=", idNotificacao));
		super.deleteByCondition(lstCondicao);
	}

}
