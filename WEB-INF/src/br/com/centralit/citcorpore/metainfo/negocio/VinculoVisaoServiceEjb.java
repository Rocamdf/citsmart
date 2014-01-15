package br.com.centralit.citcorpore.metainfo.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.metainfo.integracao.VinculoVisaoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class VinculoVisaoServiceEjb extends CrudServicePojoImpl implements VinculoVisaoService {
	protected CrudDAO getDao() throws ServiceException {
		return new VinculoVisaoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}
	
	public Collection findByIdVisaoRelacionada(Integer parm) throws Exception {
		VinculoVisaoDao dao = new VinculoVisaoDao();
		try{
			return dao.findByIdVisaoRelacionada(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}		
	}
	
	public Collection findByIdGrupoVisaoPai(Integer parm) throws Exception{
		VinculoVisaoDao dao = new VinculoVisaoDao();
		try{
			return dao.findByIdGrupoVisaoPai(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdGrupoVisaoPai(Integer parm) throws Exception{
		VinculoVisaoDao dao = new VinculoVisaoDao();
		try{
			dao.deleteByIdGrupoVisaoPai(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByIdCamposObjetoNegocioPai(Integer parm) throws Exception{
		VinculoVisaoDao dao = new VinculoVisaoDao();
		try{
			return dao.findByIdCamposObjetoNegocioPai(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdCamposObjetoNegocioPai(Integer parm) throws Exception{
		VinculoVisaoDao dao = new VinculoVisaoDao();
		try{
			dao.deleteByIdCamposObjetoNegocioPai(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByIdGrupoVisaoFilho(Integer parm) throws Exception{
		VinculoVisaoDao dao = new VinculoVisaoDao();
		try{
			return dao.findByIdGrupoVisaoFilho(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdGrupoVisaoFilho(Integer parm) throws Exception{
		VinculoVisaoDao dao = new VinculoVisaoDao();
		try{
			dao.deleteByIdGrupoVisaoFilho(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByIdCamposObjetoNegocioFilho(Integer parm) throws Exception{
		VinculoVisaoDao dao = new VinculoVisaoDao();
		try{
			return dao.findByIdCamposObjetoNegocioFilho(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdCamposObjetoNegocioFilho(Integer parm) throws Exception{
		VinculoVisaoDao dao = new VinculoVisaoDao();
		try{
			dao.deleteByIdCamposObjetoNegocioFilho(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByIdCamposObjetoNegocioPaiNN(Integer parm) throws Exception{
		VinculoVisaoDao dao = new VinculoVisaoDao();
		try{
			return dao.findByIdCamposObjetoNegocioPaiNN(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdCamposObjetoNegocioPaiNN(Integer parm) throws Exception{
		VinculoVisaoDao dao = new VinculoVisaoDao();
		try{
			dao.deleteByIdCamposObjetoNegocioPaiNN(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByIdCamposObjetoNegocioFilhoNN(Integer parm) throws Exception{
		VinculoVisaoDao dao = new VinculoVisaoDao();
		try{
			return dao.findByIdCamposObjetoNegocioFilhoNN(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdCamposObjetoNegocioFilhoNN(Integer parm) throws Exception{
		VinculoVisaoDao dao = new VinculoVisaoDao();
		try{
			dao.deleteByIdCamposObjetoNegocioFilhoNN(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
