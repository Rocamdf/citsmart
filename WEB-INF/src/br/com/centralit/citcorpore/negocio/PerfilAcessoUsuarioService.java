package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.PerfilAcessoUsuarioDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.service.CrudService;

public interface PerfilAcessoUsuarioService extends CrudService {

	public PerfilAcessoUsuarioDTO listByIdUsuario(PerfilAcessoUsuarioDTO obj) throws Exception;

	public void reativaPerfisUsuario(Integer idUsuario);

	/**
	 * Restaura o Perfil de Acesso específico do Usuário.
	 * 
	 * @param usuario
	 * @return PerfilAcessoUsuarioDTO
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public PerfilAcessoUsuarioDTO obterPerfilAcessoUsuario(UsuarioDTO usuario) throws Exception;
	
	/**
	 * Retorna a lista de id perfis de acesso do usuário informado
	 * 
	 * @param usuario
	 * @return Collection
	 * @throws Exception
	 * @author rodrigo.oliveira
	 */
	public Collection obterPerfisAcessoUsuario(UsuarioDTO usuario) throws Exception;

}
