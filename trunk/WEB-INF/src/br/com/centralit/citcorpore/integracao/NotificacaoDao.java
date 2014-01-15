package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.NotificacaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class NotificacaoDao extends CrudDaoDefaultImpl {

	public NotificacaoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
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

		listFields.add(new Field("idnotificacao", "idNotificacao", true, true, false, false));
		listFields.add(new Field("titulo", "titulo", false, false, false, false));
		listFields.add(new Field("tiponotificacao", "tipoNotificacao", false, false, false, false));
		listFields.add(new Field("datainicio", "dataInicio", false, false, false, false));
		listFields.add(new Field("datafim", "dataFim", false, false, false, false));
		listFields.add(new Field("origemNotificacao", "origemNotificacao", false, false, false, false));
		listFields.add(new Field("idContrato", "idContrato", false, false, false, false));
		
		return listFields;
	}
	
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "NOTIFICACAO";
	}
	@Override
	public Collection list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class getBean() {
		// TODO Auto-generated method stub
		return NotificacaoDTO.class;
	}
	/**
	 * Retorna true caso titulo ja exista false caso titulo não exista ou esteje excluido.
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public boolean consultarNotificacaoAtivos(NotificacaoDTO obj) throws Exception {
		List parametro = new ArrayList();
		List list = new ArrayList();
		String sql = "select idnotificacao From " + getTableName() + "  where  titulo = ?   and dataFim is null ";

		if (obj.getIdNotificacao() != null) {
			sql += " and idnotificacao <> " + obj.getIdNotificacao();
		}
		parametro.add(obj.getTitulo());
		list = this.execSQL(sql, parametro.toArray());
		if (list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	public Collection<NotificacaoDTO> consultarNotificacaoAtivosOrigemServico(Integer idContrato) throws Exception {
		Object[] objs = new Object[] { idContrato };
		
		String sql =" select distinct n.idnotificacao, n.titulo, n.tiponotificacao, n.origemnotificacao, n.idContrato, n.datainicio, n.datafim from "+getTableName()+" n "+
					" inner join notificacaoservico nsc on n.idnotificacao = nsc.idnotificacao "+
					" inner join servico sc on nsc.idservico = sc.idservico  "+
					" where n.idcontrato = ? and n.origemnotificacao = 'S' and n.dataFim is null ";
		List lista = this.execSQL(sql, objs);
		
		List listRetorno = new ArrayList();
		listRetorno.add("idNotificacao");
		listRetorno.add("titulo");
		listRetorno.add("tipoNotificacao");
		listRetorno.add("origemnotificacao");
		listRetorno.add("idContrato");
		listRetorno.add("dataInicio");
		listRetorno.add("datafim");

		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		if (result == null || result.size() == 0)
			return null;
		return result;
		
	}
	public Collection<NotificacaoDTO> listaIdContrato(Integer idContrato) throws Exception {
		Object[] objs = new Object[] { idContrato };
		String sql = "SELECT  idContrato, idNotificacao FROM " + getTableName() + " WHERE idContrato = ?  AND datafim IS NULL ";
		List lista = this.execSQL(sql, objs);

		List listRetorno = new ArrayList();
		listRetorno.add("idContrato");
		listRetorno.add("idNotificacao");
		if (lista != null && !lista.isEmpty()) {
			return this.engine.listConvertion(getBean(), lista, listRetorno);
		} else {
			return null;
		}
	}
	
	public Collection<NotificacaoDTO> listaIdServico(Integer idServico) throws Exception {
		Object[] objs = new Object[] { idServico };
		String sql = "SELECT  " + this.getNamesFieldsStr() + " FROM " + getTableName() + " WHERE idNotificacao in (select idNotificacao from NOTIFICACAOSERVICO where idServico = ?)  AND datafim IS NULL ";
		List lista = this.execSQL(sql, objs);

		if (lista != null && !lista.isEmpty()) {
			return this.engine.listConvertion(getBean(), lista, this.getListNamesFieldClass());
		} else {
			return null;
		}
	}	

	@Override
	public void updateNotNull(IDto obj) throws Exception {
		// TODO Auto-generated method stub
		super.updateNotNull(obj);
	}
}
