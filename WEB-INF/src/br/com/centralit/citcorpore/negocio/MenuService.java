package br.com.centralit.citcorpore.negocio;

import java.io.File;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.MenuDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.service.CrudService;

@SuppressWarnings("rawtypes")
public interface MenuService extends CrudService {
	public Collection listarMenus() throws Exception;

	public Collection<MenuDTO> listarSubMenus(MenuDTO submenu) throws Exception;

	public Collection<MenuDTO> listarMenusPorPerfil(UsuarioDTO usuario, Integer idMenuPai) throws Exception;
	
	public Collection<MenuDTO> listarMenusPorPerfil(UsuarioDTO usuario, Integer idMenuPai, boolean acessoRapido) throws Exception;

	public void criaMenus(Integer idUsuario) throws Exception;

	public Collection<MenuDTO> listaMenuByUsr(UsuarioDTO usuario) throws Exception;
	
	public Collection<MenuDTO> listarMenusPais() throws Exception;
	
	public Collection<MenuDTO> listarMenusFilhos(Integer idMenuPai) throws Exception;

	public void updateNotNull(Collection<MenuDTO> menus);

	/**
	 * Método para verificar se caso exista um menu com o mesmo nome
	 * 
	 * @author rodrigo.oliveira
	 * @param menuDTO
	 * @return Se caso exista menu com o mesmo nome retorna true
	 * @throws Exception
	 */
	public boolean verificaSeExisteMenu(MenuDTO menuDTO) throws Exception;

	/**
	 * buscar idMenu pelo link
	 * 
	 * @param link
	 * @return
	 * @throws Exception
	 */
	public Integer buscarIdMenu(String link) throws Exception;

	public void gerarCarga(File file) throws Exception;
	
	public void deletaMenusSemReferencia() throws Exception;
}
