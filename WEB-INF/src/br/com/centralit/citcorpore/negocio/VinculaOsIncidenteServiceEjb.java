package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.VinculaOsIncidenteDao;
import br.com.centralit.citcorpore.metainfo.integracao.VinculoVisaoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

/**
 * 
 * @author rodrigo.oliveira
 *
 */
public class VinculaOsIncidenteServiceEjb extends CrudServicePojoImpl implements VinculaOsIncidenteService {
	protected CrudDAO getDao() throws ServiceException {
		return new VinculaOsIncidenteDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}
	
	public Collection findByIdOS(Integer idOS) throws Exception{
		VinculaOsIncidenteDao dao = new VinculaOsIncidenteDao();
		try{
			return dao.findByIdOS(idOS);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	public void deleteByIdOs(Integer idOS) throws Exception{
		VinculaOsIncidenteDao dao = new VinculaOsIncidenteDao();
		try{
			dao.deleteByIdOs(idOS);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	public void deleteByIdAtividadeOS(Integer idAtividadeOS) throws Exception {
		VinculaOsIncidenteDao dao = new VinculaOsIncidenteDao();
		try{
			dao.deleteByIdAtividadeOS(idAtividadeOS);
		} catch (Exception e) {
			throw new ServiceException(e);
		}		
	}
	
	public boolean verificaServicoSelecionado(Integer idServicoContratoContabil) throws Exception {
		VinculaOsIncidenteDao dao = new VinculaOsIncidenteDao();
		try{
			return dao.verificaServicoSelecionado(idServicoContratoContabil);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	public boolean verificaServicoJaVinculado(Integer idOS, Integer idServicoContratoContabil) throws Exception {
		VinculaOsIncidenteDao dao = new VinculaOsIncidenteDao();
		try{
			return dao.verificaServicoJaVinculado(idOS, idServicoContratoContabil);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection findByIdAtividadeOS(Integer idAtividadeOS) throws Exception {
		VinculaOsIncidenteDao dao = new VinculaOsIncidenteDao();
		try{
			return dao.findByIdAtividadeOS(idAtividadeOS);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
}
