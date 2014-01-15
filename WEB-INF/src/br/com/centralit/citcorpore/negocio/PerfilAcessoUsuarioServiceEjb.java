package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.PerfilAcessoUsuarioDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.PerfilAcessoUsuarioDAO;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;

public class PerfilAcessoUsuarioServiceEjb extends CrudServicePojoImpl implements PerfilAcessoUsuarioService {

	private static final long serialVersionUID = 5498049762623049303L;

	public void reativaPerfisUsuario(Integer idUsuario) {
		try {
			((PerfilAcessoUsuarioDAO) getDao()).reativaPerfisUsuario(idUsuario);
		} catch (PersistenceException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new PerfilAcessoUsuarioDAO();
	}

	@Override
	protected void validaCreate(Object arg0) throws Exception {
		PerfilAcessoUsuarioDTO dto = (PerfilAcessoUsuarioDTO) arg0;
		if (dto.getDataInicio() == null) {
			dto.setDataInicio(UtilDatas.getDataAtual());
		}
	}

	@Override
	protected void validaDelete(Object arg0) throws Exception {

	}

	@Override
	protected void validaFind(Object arg0) throws Exception {

	}

	@Override
	protected void validaUpdate(Object arg0) throws Exception {

	}

	@Override
	public PerfilAcessoUsuarioDTO listByIdUsuario(PerfilAcessoUsuarioDTO obj) throws Exception {
		try {
			PerfilAcessoUsuarioDAO dao = new PerfilAcessoUsuarioDAO();
			return dao.listByIdUsuario(obj);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public PerfilAcessoUsuarioDTO obterPerfilAcessoUsuario(UsuarioDTO usuario) throws Exception {

		PerfilAcessoUsuarioDAO perfilAcessoUsuarioDao = new PerfilAcessoUsuarioDAO();

		PerfilAcessoUsuarioDTO perfilAcessoUsuarioDto = new PerfilAcessoUsuarioDTO();

		perfilAcessoUsuarioDto = perfilAcessoUsuarioDao.obterPerfilAcessoUsuario(usuario);

		return perfilAcessoUsuarioDto;
	}

	@Override
	public Collection obterPerfisAcessoUsuario(UsuarioDTO usuario) throws Exception {
		try {
			PerfilAcessoUsuarioDAO dao = new PerfilAcessoUsuarioDAO();
			return dao.listPerfilByIdUsuario(usuario);
		} catch (Exception e) {
			throw new ServiceException(e);
		}		
	}
}
