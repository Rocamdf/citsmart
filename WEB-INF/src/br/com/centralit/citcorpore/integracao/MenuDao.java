package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.MenuDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoGrupoDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoUsuarioDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.MenuServiceEjb;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
public class MenuDao extends CrudDaoDefaultImpl {

	public MenuDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto arg0) throws Exception {
		return null;
	}

	@Override
	public Collection getFields() {
		Collection listFields = new ArrayList();

		listFields.add(new Field("IDMENU", "idMenu", true, true, false, false));
		listFields.add(new Field("IDMENUPAI", "idMenuPai", false, false, false, false));
		listFields.add(new Field("NOME", "nome", false, false, false, false));
		listFields.add(new Field("DATAINICIO", "dataInicio", false, false, false, false));
		listFields.add(new Field("DATAFIM", "dataFim", false, false, false, false));
		listFields.add(new Field("DESCRICAO", "descricao", false, false, false, false));
		listFields.add(new Field("ORDEM", "ordem", false, false, false, false));
		listFields.add(new Field("MENURAPIDO", "menuRapido", false, false, false, false));
		listFields.add(new Field("LINK", "link", false, false, false, false));
		listFields.add(new Field("IMAGEM", "imagem", false, false, false, false));
		listFields.add(new Field("MOSTRAR", "mostrar", false, false, false, false));

		return listFields;
	}

	public void updateNotNull(Collection<MenuDTO> menus) {
		try {
			for (MenuDTO menu : menus) {
				if (menu.getIdMenu() != null) {
					updateNotNull(menu);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateDataFim() throws PersistenceException {
		Object[] parametro = new Object[] { UtilDatas.getDataAtual() };

		String sql = "UPDATE " + getTableName() + " SET datafim = ?";
		execUpdate(sql, parametro);

	}

	public void deleteAll() throws PersistenceException {

		String sql = "delete from " + getTableName();
		super.execUpdate(sql, null);

	}
	
	public void deleteMenu(Integer idMenu) throws PersistenceException {

		String sql = "delete from " + getTableName() + " where idmenu = ?";
		super.execUpdate(sql, new Object[] {idMenu});

	}
	
	@Override
	public String getTableName() {
		return "MENU";
	}

	@Override
	public Collection list() throws Exception {
		List list = new ArrayList();
		list.add(new Order("nome"));
		return super.list(list);
	}

	@Override
	public Class getBean() {
		return MenuDTO.class;
	}

	public Collection<MenuDTO> listarMenusPorPerfil(UsuarioDTO usuario, Integer idMenuPai) throws Exception {
		return listaMenus(usuario, idMenuPai, false);
		//return listaMenusNovos(usuario, idMenuPai, false);
	}

	public Collection<MenuDTO> listarMenusPorPerfil(UsuarioDTO usuario, Integer idMenuPai, boolean menuRapido) throws Exception {
		return listaMenus(usuario, idMenuPai, menuRapido);
		//return listaMenusNovos(usuario, idMenuPai, menuRapido);
	}

	/*
	 * Lista os menus por perfil de acesso Metodo reutilizável
	 */
	public Collection<MenuDTO> listaMenus(UsuarioDTO usuario, Integer idMenuPai, boolean menuRapido) throws Exception {

		if (usuario == null) {
			return null;
		}
		
		List result = null;
		StringBuffer sql = new StringBuffer();
		ArrayList<Integer> parametros = new ArrayList<Integer>();

		sql.append("SELECT distinct m.idmenu, m.idmenupai, m.nome, m.datainicio, m.datafim, m.descricao, m.ordem, m.link, m.imagem, m.mostrar ");
		sql.append("FROM perfilacessomenu a ");
		sql.append("JOIN menu m ON m.idmenu = a.idmenu ");
		sql.append("WHERE m.datafim IS NULL AND (a.pesquisa <> 'N' OR a.grava <> 'N' OR a.deleta <> 'N') ");

		if (menuRapido) {
			sql.append(" AND (m.menuRapido = 'S'  OR m.link = '')");
		}

		if (idMenuPai == null) {
			if (!menuRapido)
				sql.append("AND m.idmenupai IS NULL ");
		} else {
			sql.append("AND m.idmenupai = ? ");
			parametros.add(idMenuPai);
		}

		PerfilAcessoUsuarioDAO perfilAcessoUsuarioDao = new PerfilAcessoUsuarioDAO();

		PerfilAcessoUsuarioDTO perfilAcessoEspecifico = new PerfilAcessoUsuarioDTO();

		perfilAcessoEspecifico = perfilAcessoUsuarioDao.obterPerfilAcessoUsuario(usuario);

		if (perfilAcessoEspecifico != null) {
			sql.append("AND (a.idperfilacesso = ? ");
			parametros.add(perfilAcessoEspecifico.getIdPerfilAcesso());

			GrupoDao grupoDao = new GrupoDao();
			PerfilAcessoGrupoDao perfilAcessoGrupoDao = new PerfilAcessoGrupoDao();

			Collection<GrupoDTO> gruposDoEmpregado = grupoDao.getGruposByIdEmpregado(usuario.getIdEmpregado());

			if (gruposDoEmpregado != null && !gruposDoEmpregado.isEmpty()) {
				for (GrupoDTO grupo : gruposDoEmpregado) {
					PerfilAcessoGrupoDTO perfilAcessoGrupo = perfilAcessoGrupoDao.obterPerfilAcessoGrupo(grupo);

					if (perfilAcessoGrupo != null) {
						sql.append("OR a.idperfilacesso = ? ");
						parametros.add(perfilAcessoGrupo.getIdPerfilAcessoGrupo());
					}
				}
			}
			sql.append(")");

		} else {
			GrupoDao grupoDao = new GrupoDao();
			PerfilAcessoGrupoDao perfilAcessoGrupoDao = new PerfilAcessoGrupoDao();

			Collection<GrupoDTO> gruposDoEmpregado = grupoDao.getGruposByIdEmpregado(usuario.getIdEmpregado());

			if (gruposDoEmpregado != null && !gruposDoEmpregado.isEmpty()) {
				boolean aux = true;
				for (GrupoDTO grupo : gruposDoEmpregado) {
					PerfilAcessoGrupoDTO perfilAcessoGrupo = perfilAcessoGrupoDao.obterPerfilAcessoGrupo(grupo);

					if (perfilAcessoGrupo != null) {
						if (aux) {
							sql.append("AND (a.idperfilacesso = ? ");
							parametros.add(perfilAcessoGrupo.getIdPerfilAcessoGrupo());
							aux = false;
						} else {
							sql.append("OR a.idperfilacesso = ? ");
							parametros.add(perfilAcessoGrupo.getIdPerfilAcessoGrupo());
						}
					}
				}
				if (!aux) {
					sql.append(")");
				}
			}
		}

		sql.append(" ORDER BY ordem, idmenupai");
		Object[] paramsFinal = parametros.size() == 0 ? null : parametros.toArray();
		List lista;
		try {
			lista = this.execSQL(sql.toString(), paramsFinal);

			List listRetorno = new ArrayList();
			listRetorno.add("idMenu");
			listRetorno.add("idMenuPai");
			listRetorno.add("nome");
			listRetorno.add("dataInicio");
			listRetorno.add("dataFim");
			listRetorno.add("descricao");
			listRetorno.add("ordem");
			listRetorno.add("link");
			listRetorno.add("imagem");
			listRetorno.add("mostrar");

			result = this.engine.listConvertion(MenuDTO.class, lista, listRetorno);
		} catch (PersistenceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Collection<MenuDTO> listaMenuByUsr(UsuarioDTO usuario) throws Exception {
		List result = null;
		StringBuffer sql = new StringBuffer();
		ArrayList<Integer> parametros = new ArrayList<Integer>();

		sql.append("SELECT distinct m.idmenu, m.idmenupai, m.nome, m.datainicio, m.datafim, m.descricao, m.ordem, m.link, m.imagem ");
		sql.append("FROM perfilacessomenu a ");
		sql.append("JOIN menu m ON m.idmenu = a.idmenu ");
		sql.append("WHERE m.datafim IS NULL AND (a.pesquisa <> 'N' OR a.grava <> 'N'  OR a.deleta <> 'N')");

		if (usuario != null) {

			PerfilAcessoUsuarioDAO perfilAcessoUsuarioDao = new PerfilAcessoUsuarioDAO();

			PerfilAcessoUsuarioDTO perfilAcessoEspecifico = new PerfilAcessoUsuarioDTO();

			perfilAcessoEspecifico = perfilAcessoUsuarioDao.obterPerfilAcessoUsuario(usuario);

			if (perfilAcessoEspecifico != null) {
				sql.append("AND (a.idperfilacesso = ? ");
				parametros.add(perfilAcessoEspecifico.getIdPerfilAcesso());

				GrupoDao grupoDao = new GrupoDao();
				PerfilAcessoGrupoDao perfilAcessoGrupoDao = new PerfilAcessoGrupoDao();

				Collection<GrupoDTO> gruposDoEmpregado = grupoDao.getGruposByIdEmpregado(usuario.getIdEmpregado());

				if (gruposDoEmpregado != null && !gruposDoEmpregado.isEmpty()) {
					for (GrupoDTO grupo : gruposDoEmpregado) {
						PerfilAcessoGrupoDTO perfilAcessoGrupo = perfilAcessoGrupoDao.obterPerfilAcessoGrupo(grupo);

						if (perfilAcessoGrupo != null) {
							sql.append("OR a.idperfilacesso = ? ");
							parametros.add(perfilAcessoGrupo.getIdPerfilAcessoGrupo());
						}
					}
				}
				sql.append(")");

			} else {
				GrupoDao grupoDao = new GrupoDao();
				PerfilAcessoGrupoDao perfilAcessoGrupoDao = new PerfilAcessoGrupoDao();

				Collection<GrupoDTO> gruposDoEmpregado = grupoDao.getGruposByIdEmpregado(usuario.getIdEmpregado());

				if (gruposDoEmpregado != null && !gruposDoEmpregado.isEmpty()) {
					boolean aux = true;
					for (GrupoDTO grupo : gruposDoEmpregado) {
						PerfilAcessoGrupoDTO perfilAcessoGrupo = perfilAcessoGrupoDao.obterPerfilAcessoGrupo(grupo);

						if (perfilAcessoGrupo != null) {
							if (aux) {
								sql.append("AND (a.idperfilacesso = ? ");
								parametros.add(perfilAcessoGrupo.getIdPerfilAcessoGrupo());
								aux = false;
							} else {
								sql.append("OR a.idperfilacesso = ? ");
								parametros.add(perfilAcessoGrupo.getIdPerfilAcessoGrupo());
							}
						}
					}
					if (!aux) {
						sql.append(")");
					}
				}
			}
		}

		sql.append("AND m.link IS NOT NULL");

		sql.append(" ORDER BY ordem, idmenupai");
		Object[] paramsFinal = parametros.size() == 0 ? null : parametros.toArray();
		List lista;
		try {
			lista = this.execSQL(sql.toString(), paramsFinal);

			List listRetorno = new ArrayList();
			listRetorno.add("idMenu");
			listRetorno.add("idMenuPai");
			listRetorno.add("nome");
			listRetorno.add("dataInicio");
			listRetorno.add("dataFim");
			listRetorno.add("descricao");
			listRetorno.add("ordem");
			listRetorno.add("link");
			listRetorno.add("imagem");

			result = this.engine.listConvertion(MenuDTO.class, lista, listRetorno);
		} catch (PersistenceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Integer getPerfilAcesso(UsuarioDTO usuario) throws Exception {
		Integer retorno = null;
		StringBuffer sqlPerfilUsuario = new StringBuffer();
		sqlPerfilUsuario.append("SELECT idperfil FROM perfilacessousuario WHERE idusuario = ? AND datafim IS NULL");

		StringBuffer sqlPerfilGrupo = new StringBuffer();
		sqlPerfilGrupo.append("SELECT idperfil FROM perfilacessogrupo WHERE idgrupo = ? AND datafim IS NULL");
		Object[] params01 = new Object[] { usuario.getIdUsuario() };
		Object[] params02 = new Object[] { usuario.getIdGrupo() };
		List lista;
		List camposConversao = new ArrayList();
		ArrayList<PerfilAcessoDTO> result;
		try {
			lista = this.execSQL(sqlPerfilUsuario.toString(), params01);
			if (lista.isEmpty()) {
				lista = this.execSQL(sqlPerfilGrupo.toString(), params02);
			}
			if (!lista.isEmpty()) {
				camposConversao.add("idPerfilAcesso");
				result = (ArrayList<PerfilAcessoDTO>) this.engine.listConvertion(PerfilAcessoDTO.class, lista, camposConversao);
				retorno = result.get(0).getIdPerfilAcesso();
			}
		} catch (PersistenceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	public Collection listarMenus() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT distinct menu.idMenu,menu.idMenuPai,menu.nome,menu.mostrar FROM MENU menu INNER JOIN MENU menuFilho ON menu.idMenu = menuFilho.idMenu ");
		sql.append("WHERE menu.idmenupai IS NULL and menu.datafim IS NULL and menuFilho.datafim is null");
		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), null);
		List listRetorno = new ArrayList();
		listRetorno.add("idMenu");
		listRetorno.add("idMenuPai");
		listRetorno.add("nome");
		listRetorno.add("mostrar");
		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		return result;
	}
	
	public Collection<MenuDTO> listaIdNomeMenus() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select m.idmenu, m.nome, p.idperfilacesso, p.pesquisa, p.grava, p.deleta from perfilacessomenu p inner join menu m on p.idmenu = m.idmenu ");
		sql.append(" where p.datafim is null ");
		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), null);
		List listRetorno = new ArrayList();
		listRetorno.add("idMenu");
		listRetorno.add("nome");
		listRetorno.add("idPerfilAcesso");
		listRetorno.add("pesquisa");
		listRetorno.add("grava");
		listRetorno.add("deleta");
		List result = null;
		if(lista != null && !lista.isEmpty()){
			result = this.engine.listConvertion(getBean(), lista, listRetorno);
		}
		return result;
	}
	
	public Collection<MenuDTO> listarMenusFilhoByIdMenuPai(Integer idMenuPai) throws Exception {
		List parametro = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select idmenu, link from menu where idmenupai = ? ");
		parametro.add(idMenuPai);
		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), parametro.toArray());
		List listRetorno = new ArrayList();
		listRetorno.add("idMenu");
		listRetorno.add("link");
		List result = null;
		if(lista != null && !lista.isEmpty()){
			result = this.engine.listConvertion(getBean(), lista, listRetorno);
		}
		return result;
	}
	
	
	public Collection<MenuDTO> listarSubMenus(MenuDTO submenu) throws Exception {
		List parametro = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT idMenu, idMenuPai, nome FROM " + getTableName() + " WHERE idmenupai = ? AND datafim IS NULL ");
		parametro.add(submenu.getIdMenu());
		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), parametro.toArray());
		List listRetorno = new ArrayList();
		listRetorno.add("idMenu");
		listRetorno.add("idMenuPai");
		listRetorno.add("nome");
		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		return result;
	}

	public Collection<MenuDTO> listarMenusPais() throws Exception {
		List parametro = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT idMenu, nome, descricao, ordem, link, imagem, datainicio, menurapido FROM " + getTableName() + " WHERE idmenupai is null AND dataFim IS NULL ");
		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), parametro.toArray());
		List listRetorno = new ArrayList();
		listRetorno.add("idMenu");
		listRetorno.add("nome");
		listRetorno.add("descricao");
		listRetorno.add("ordem");
		listRetorno.add("link");
		listRetorno.add("imagem");
		listRetorno.add("dataInicio");
		listRetorno.add("menuRapido");
		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		return result;
	}

	public Collection<MenuDTO> listarMenusFilhos(Integer idMenuPai) throws Exception {
		List parametro = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT idMenu, idMenuPai, nome, descricao, ordem, link, imagem, datainicio, menurapido  FROM " + getTableName() + " WHERE idmenupai = ? AND dataFim IS NULL ");
		parametro.add(idMenuPai);
		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), parametro.toArray());
		List listRetorno = new ArrayList();
		listRetorno.add("idMenu");
		listRetorno.add("idMenuPai");
		listRetorno.add("nome");
		listRetorno.add("descricao");
		listRetorno.add("ordem");
		listRetorno.add("link");
		listRetorno.add("imagem");
		listRetorno.add("dataInicio");
		listRetorno.add("menuRapido");
		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		return result;
	}
	
	public Collection<MenuDTO> listarMenuPai(Integer idMenuFilho) throws Exception {
		List parametro = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT idMenu, idMenuPai FROM " + getTableName() + " WHERE idmenu = ? AND dataFim IS NULL ");
		parametro.add(idMenuFilho);
		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), parametro.toArray());
		List listRetorno = new ArrayList();
		listRetorno.add("idMenu");
		listRetorno.add("idMenuPai");
		List result = null;
		if(lista != null && !lista.isEmpty()){
			result = this.engine.listConvertion(getBean(), lista, listRetorno);
		}
		return result;
	}

	/**
	 * Método para verificar se caso exista um menu com o mesmo nome
	 * 
	 * @author rodrigo.oliveira
	 * @param menuDTO
	 * @return Se caso exista menu com o mesmo nome retorna true
	 */
	public boolean verificaSeExisteMenu(MenuDTO menuDTO) throws Exception {

		List parametro = new ArrayList();
		List list = new ArrayList();
		String sql = "SELECT idmenu FROM " + getTableName() + " WHERE nome = ? AND dataFim IS NULL ";
		parametro.add(menuDTO.getNome());
		if (menuDTO.getIdMenu() != null) {
			sql += " AND idmenu <> ? ";
			parametro.add(menuDTO.getIdMenu());
		}

		list = this.execSQL(sql, parametro.toArray());

		if (list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}

	}

	public boolean verificaSeExisteMenuPorLink(MenuDTO menuDTO) throws Exception {

		List parametro = new ArrayList();
		List list = new ArrayList();
		String sql = "SELECT idmenu FROM " + getTableName() + " WHERE nome = ? AND dataFim IS NULL ";
		parametro.add(menuDTO.getNome());
		if (menuDTO.getIdMenu() != null) {
			sql += " AND idmenu <> ? ";
			parametro.add(menuDTO.getIdMenu());
		}

		list = this.execSQL(sql, parametro.toArray());

		if (list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}

	}

	public Integer buscarIdMenu(String link) throws Exception {
		List ordenacao = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT idmenu FROM menu where link = ? and dataFim is null");
		List lista = this.execSQL(sql.toString(), new Object[] { link });
		ordenacao.add("idMenu");
		if (lista != null && !lista.isEmpty()) {
			Collection<MenuDTO> result = this.engine.listConvertion(getBean(), lista, ordenacao);
			for (MenuDTO menu : result) {
				return menu.getIdMenu();
			}
			return null;
		} else {
			return null;
		}

	}

	public void alterarMenuPorNome(MenuDTO menuDTO) throws Exception {

		List parametro = new ArrayList();
		List list = new ArrayList();
		String sql = "UPDATE " + getTableName() + " SET idMenuPai = ?, link = ? WHERE nome = ? AND dataFim IS NULL ";
		parametro.add(menuDTO.getIdMenuPai());
		parametro.add(menuDTO.getLink());
		parametro.add(menuDTO.getNome());
		if (menuDTO.getIdMenu() != null) {
			sql += " AND idmenu <> ? ";
			parametro.add(menuDTO.getIdMenu());
		}

		this.execUpdate(sql, parametro.toArray());

	}

}
