/**
 * 
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.ContratosGruposDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.integracao.ContratosGruposDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.service.ServiceLocator;

/**
 * @author Centralit
 * 
 */
@SuppressWarnings({ "unchecked"})
public class ContratosGruposServiceEjb extends CrudServicePojoImpl implements ContratosGruposService {

	private static final long serialVersionUID = -2873316080894549326L;

	@Override
	public Collection<ContratosGruposDTO> findByIdGrupo(Integer idGrupo) throws Exception {
		ContratosGruposDAO dao = new ContratosGruposDAO();

		try {
			return dao.findByIdGrupo(idGrupo);

		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<ContratosGruposDTO> findByIdEmpregado(Integer idEmpregado) throws Exception {
		ContratosGruposDAO dao = new ContratosGruposDAO();

		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

		try {

			Collection<GrupoDTO> gruposEmpregado = (Collection<GrupoDTO>) grupoService.getGruposByPessoa(idEmpregado);

			return dao.findByGrupos(gruposEmpregado);

		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void deleteByIdGrupo(Integer idGrupo) throws Exception {
		ContratosGruposDAO dao = new ContratosGruposDAO();

		try {

			dao.deleteByIdGrupo(idGrupo);

		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<ContratosGruposDTO> findByGrupos(Collection<GrupoDTO> gruposEmpregado) throws Exception {
		ContratosGruposDAO dao = new ContratosGruposDAO();
		try {
			return dao.findByGrupos(gruposEmpregado);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<ContratosGruposDTO> findByIdContrato(Integer idContrato) throws Exception {
		ContratosGruposDAO dao = new ContratosGruposDAO();

		try {

			return dao.findByIdContrato(idContrato);

		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void deleteByIdContrato(Integer idContrato) throws Exception {
		ContratosGruposDAO dao = new ContratosGruposDAO();

		try {

			dao.deleteByIdContrato(idContrato);

		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new ContratosGruposDAO();
	}

	@Override
	protected void validaCreate(Object obj) throws Exception {

	}

	@Override
	protected void validaUpdate(Object obj) throws Exception {

	}

	@Override
	protected void validaDelete(Object obj) throws Exception {

	}

	@Override
	protected void validaFind(Object obj) throws Exception {

	}

	@Override
	public boolean hasContrato(Collection<GrupoEmpregadoDTO> gruposEmpregado, ContratoDTO contrato) throws Exception {
		ContratosGruposDAO dao = new ContratosGruposDAO();
		try {
			return dao.hasContrato(gruposEmpregado, contrato);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
