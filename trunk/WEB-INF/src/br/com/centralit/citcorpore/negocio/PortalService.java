package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.PedidoPortalDTO;
import br.com.centralit.citcorpore.bean.PortalDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceEjb2;

public interface PortalService extends CrudServiceEjb2 {

	public Collection<PortalDTO> findByCondition(Integer i) throws ServiceException, Exception;
	public Collection<PortalDTO> findByCondition(Integer idUsuario, Integer idItem) throws ServiceException, Exception;
	public Collection<PortalDTO> listByUsuario(Integer idUsuario) throws Exception;
	public PedidoPortalDTO criarPedidoSolicitacao(PedidoPortalDTO pedidoPortalDTO, UsuarioDTO usuarioDTO) throws ServiceException, Exception;
	
	
}
