/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.PastaDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoPastaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.PerfilAcessoPastaDAO;
import br.com.centralit.citcorpore.util.Enumerados.PermissaoAcessoPasta;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.service.ServiceLocator;

/**
 * EJB de PerfilAcessoPasta.
 * 
 * @author valdoilo.damasceno
 */
public class PerfilAcessoPastaServiceEjb extends CrudServicePojoImpl implements PerfilAcessoPastaService {

	private static final long serialVersionUID = -5392012344043502590L;

	@Override
	public boolean verificarSeUsuarioAprovaBaseConhecimentoParaPastaSelecionada(UsuarioDTO usuario, Integer idPasta) throws Exception {
		return this.getPerfilAcessoPastaDao().usuarioAprovaBaseConhecimentoParaPastaSelecionada(usuario, idPasta);
	}

	/**
	 * Retorna DAO de PerfilAcessoPastaService.
	 * 
	 * @return PerfilAcessoPastaDAO
	 * @throws ServiceException
	 */
	public PerfilAcessoPastaDAO getPerfilAcessoPastaDao() throws ServiceException {
		return (PerfilAcessoPastaDAO) this.getDao();
	}

	@Override
	public List<PerfilAcessoPastaDTO> validaPasta(UsuarioDTO usuario) throws Exception {

		return this.getPerfilAcessoPastaDao().validaPasta(usuario);
	}

	@Override
	public Collection<PerfilAcessoPastaDTO> findByIdPasta(Integer idPasta) throws Exception {
		return getPerfilAcessoPastaDao().findByIdPasta(idPasta);
	}

	@Override
	public Collection<PerfilAcessoPastaDTO> listByIdPasta(Integer idPasta) throws Exception {

		PerfilAcessoPastaDAO perfilAcessoDao = new PerfilAcessoPastaDAO();

		return perfilAcessoDao.listByIdPasta(idPasta);
	}

	@Override
	public PermissaoAcessoPasta verificarPermissaoDeAcessoPasta(UsuarioDTO usuario, PastaDTO pastaDto) throws Exception {

		PerfilAcessoPastaDAO perfilAcessoPastaDao = new PerfilAcessoPastaDAO();

		PastaService pastaService = (PastaService) ServiceLocator.getInstance().getService(PastaService.class, null);

		pastaDto = pastaService.obterHerancaDePermissao(pastaDto);
		PermissaoAcessoPasta permissao = null;
		if(pastaDto != null){
				permissao = perfilAcessoPastaDao.verificarPermissaoDeAcessoPasta(usuario, pastaDto.getId());
		}

		return permissao;
	}

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new PerfilAcessoPastaDAO();
	}

	@Override
	protected void validaCreate(Object arg0) throws Exception {

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

}
