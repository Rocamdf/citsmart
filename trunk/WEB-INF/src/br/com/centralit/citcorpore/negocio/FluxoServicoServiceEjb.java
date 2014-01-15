package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.FluxoServicoDTO;
import br.com.centralit.citcorpore.integracao.FluxoServicoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

@SuppressWarnings("rawtypes")
public class FluxoServicoServiceEjb extends CrudServicePojoImpl implements FluxoServicoService {

	private static final long serialVersionUID = -1337801713224159482L;

	protected CrudDAO getDao() throws ServiceException {
		return new FluxoServicoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaFind(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	public Collection findByIdServicoContrato(Integer parm) throws Exception {
		FluxoServicoDao dao = new FluxoServicoDao();
		try {
			return dao.findByIdServicoContrato(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public FluxoServicoDTO findPrincipalByIdServicoContrato(Integer idServicoContrato) throws Exception {
		FluxoServicoDao dao = new FluxoServicoDao();
		try {
			return dao.findPrincipalByIdServicoContrato(idServicoContrato);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean validarFluxoServico(FluxoServicoDTO fluxoServicoDTO) throws Exception {
		FluxoServicoDao dao = new FluxoServicoDao();
		try {
			return dao.validarFluxoServico(fluxoServicoDTO);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection findByIdFluxoServico(Integer parm) throws Exception {
		FluxoServicoDao dao = new FluxoServicoDao();
		try {
			return dao.findByIdFluxoServico(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
