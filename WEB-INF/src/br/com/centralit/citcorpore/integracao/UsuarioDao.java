package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ContatoProblemaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoItemRequisicaoProduto;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class UsuarioDao extends CrudDaoDefaultImpl {

	private static final long serialVersionUID = 1L;

	public UsuarioDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto dto) throws Exception {
		List order = new ArrayList();
		order.add(new Order("nomeUsuario"));
		return super.find(dto, order);
	}

	@Override
	public Collection getFields() {
		Collection listFields = new ArrayList();

		listFields.add(new Field("IDUSUARIO", "idUsuario", true, true, false, false));
		listFields.add(new Field("IDUNIDADE", "idUnidade", false, false, false, false));
		listFields.add(new Field("IDEMPREGADO", "idEmpregado", false, false, false, false));
		listFields.add(new Field("IDEMPRESA", "idEmpresa", false, false, false, false));
		listFields.add(new Field("NOME", "nomeUsuario", false, false, false, false));
		listFields.add(new Field("LOGIN", "login", false, false, false, false));
		listFields.add(new Field("SENHA", "senha", false, false, false, false));
		listFields.add(new Field("STATUS", "status", false, false, false, false));
		listFields.add(new Field("LDAP", "ldap", false, false, false, false));
		listFields.add(new Field("ULTIMOACESSOPORTAL", "ultimoAcessoPortal", false, false, false, false));
		

		return listFields;
	}

	@Override
	public String getTableName() {
		return "USUARIO";
	}

	@Override
	public void updateNotNull(IDto obj) throws Exception {
		super.updateNotNull(obj);
	}
	
	/**
	 * Retorna lista de status de usuário.
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public UsuarioDTO listStatus(UsuarioDTO obj) throws Exception {
		List parametro = new ArrayList();
		List fields = new ArrayList();
		List list = new ArrayList();
		String sql = "select idEmpregado, nome, status, login from " + getTableName() + "  where  nome = ? AND status = 'A' ";
		parametro.add(obj.getNomeUsuario());
		list = this.execSQL(sql, parametro.toArray());
		fields.add("idEmpregado");
		fields.add("nomeUsuario");
		fields.add("status");
		if (list != null && !list.isEmpty()) {
			return (UsuarioDTO) this.listConvertion(getBean(), list, fields).get(0);
		} else {
			return null;
		}

	}

	/**
	 * Retorna lista de login por usuário.
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public UsuarioDTO listLogin(UsuarioDTO obj) throws Exception {
		List parametro = new ArrayList();
		List fields = new ArrayList();
		List list = new ArrayList();
		String sql = "select login from " + getTableName() + " where status = 'A' AND login = ? ";
		parametro.add(obj.getLogin());
		list = this.execSQL(sql, parametro.toArray());
		fields.add("login");
		if (list.isEmpty()) {
			return null;
		} else {
			return (UsuarioDTO) this.listConvertion(getBean(), list, fields).get(0);

		}
	}

	@Override
	public Class getBean() {
		return UsuarioDTO.class;
	}

	/**
	 * Restaura Usuário por Login.
	 * 
	 * @param login
	 * @return
	 * @throws Exception
	 */
	public UsuarioDTO restoreByLogin(String login) throws Exception {
		List ordem = new ArrayList();
		ordem.add(new Order("login"));
		ordem.add(new Order("status"));
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setLogin(login);
		List col = (List) super.find(usuario, ordem);
		if (col == null || col.size() == 0)
			return null;
		return (UsuarioDTO) col.get(0);
	}
	
	
	public UsuarioDTO restoreByID(Integer id) throws Exception {
		List ordem = new ArrayList();
		ordem.add(new Order("idUsuario"));
		ordem.add(new Order("status"));
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setIdUsuario(id);
		List col = (List) super.find(usuario, ordem);
		if (col == null || col.size() == 0)
			return null;
		return (UsuarioDTO) col.get(0);
	}

	

	public UsuarioDTO restoreByLoginSenha(String login, String senha) throws Exception {
		List ordem = new ArrayList();
		ordem.add(new Order("login"));
		ordem.add(new Order("status"));
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setLogin(login);
		usuario.setSenha(senha);
		List col = (List) super.find(usuario, ordem);
		if (col == null || col.size() == 0)
			return null;
		return (UsuarioDTO) col.get(0);
	}

	/**
	 * Restorna usuário por id do colaborador.
	 * 
	 * @param idEmpregado
	 * @return
	 * @throws Exception
	 */
	public UsuarioDTO restoreByIdEmpregado(Integer idEmpregado) throws Exception {
		List ordem = new ArrayList();
		ordem.add(new Order("idEmpregado"));
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setIdEmpregado(idEmpregado);
		List col = (List) find(usuario);
		if (col == null || col.size() == 0)
			return null;
		return (UsuarioDTO) col.get(0);
	}
	
	public UsuarioDTO restoreAtivoByIdEmpregado(Integer idEmpregado) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idEmpregado", "=", idEmpregado)); 
        condicao.add(new Condition("status", "=", "A")); 
		ordenacao.add(new Order("idUsuario",Order.DESC));
		List col = (List) super.findByCondition(condicao, ordenacao);
		if (col == null || col.size() == 0)
			return null;
		return (UsuarioDTO) col.get(0);
	}

	/**
	 * Restorna usuário por id do colaborador.
	 * 
	 * @param idEmpregado
	 * @return
	 * @throws Exception
	 */
	public UsuarioDTO restoreByIdEmpregadosDeUsuarios(Integer idEmpregado) throws Exception {
		List parametro = new ArrayList();
		List fields = new ArrayList();
		List list = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select idUsuario from " + getTableName() + "  where  idEmpregado = ? and status like 'A'");
		parametro.add(idEmpregado);
		list = this.execSQL(sql.toString(), parametro.toArray());
		fields.add("idUsuario");
		if (list != null && !list.isEmpty()) {
			return (UsuarioDTO) this.listConvertion(getBean(), list, fields).get(0);
		} else {
			return null;
		}
	}

	@Override
	public Collection list() throws Exception {
		List list = new ArrayList();
		list.add(new Order("nomeUsuario"));
		return super.list(list);
	}
	
	public boolean listSeVazio() throws Exception {
		StringBuffer sql = new StringBuffer();
		List prarametro = new ArrayList();
		
		sql.append("select count(*) from usuario");
		List lista = this.execSQL(sql.toString(), prarametro.toArray());
		List listRetorno = new ArrayList();
		listRetorno.add("seguencia");
		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO = (UsuarioDTO) result.get(0);
		if (usuarioDTO.getSeguencia() > 0){
			return true;
		}
		else return false;
		
	}
	
	public UsuarioDTO findById(Integer id) throws Exception{
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idusuario", "=", id));
		ordenacao.add(new Order("nomeUsuario"));
		Collection<UsuarioDTO> col =  super.findByCondition(condicao, ordenacao);
		if (col == null || col.size() == 0)
			return null;
		return (UsuarioDTO) ((ArrayList) col).get(0);
	
	}
	
	public Collection listAtivos() throws Exception{
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("status", "=", "A"));
		ordenacao.add(new Order("nomeUsuario"));
		return super.findByCondition(condicao, ordenacao);
	}

}
