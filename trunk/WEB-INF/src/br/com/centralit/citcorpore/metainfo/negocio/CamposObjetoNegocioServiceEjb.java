package br.com.centralit.citcorpore.metainfo.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.metainfo.integracao.CamposObjetoNegocioDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class CamposObjetoNegocioServiceEjb extends CrudServicePojoImpl implements CamposObjetoNegocioService {
	protected CrudDAO getDao() throws ServiceException {
		return new CamposObjetoNegocioDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}
	public Collection findByIdObjetoNegocio(Integer idObjetoNegocioParm) throws Exception{
		CamposObjetoNegocioDao camposObjetoNegocioDao = new CamposObjetoNegocioDao();
		return camposObjetoNegocioDao.findByIdObjetoNegocio(idObjetoNegocioParm);
	}
	public Collection findByIdObjetoNegocioAndNomeDB(Integer idObjetoNegocioParm, String nomeDBParm) throws Exception{
	    CamposObjetoNegocioDao camposObjetoNegocioDao = new CamposObjetoNegocioDao();
	    return camposObjetoNegocioDao.findByIdObjetoNegocioAndNomeDB(idObjetoNegocioParm, nomeDBParm);
	}

	@Override
	public void updateComplexidade(Integer idCampoObjNegocio1, Integer idCampoObjNegocio2) throws Exception {
		CamposObjetoNegocioDao camposObjetoNegocioDao = new CamposObjetoNegocioDao();
	    camposObjetoNegocioDao.updateComplexidade(idCampoObjNegocio1, idCampoObjNegocio2);
	}

	@Override
	public void updateFluxoServico(Integer idCampoObjNegocio1, Integer idCampoObjNegocio2, Integer idCampoObjNegocio3) throws Exception {
		CamposObjetoNegocioDao camposObjetoNegocioDao = new CamposObjetoNegocioDao();
	    camposObjetoNegocioDao.updateFluxoServico(idCampoObjNegocio1, idCampoObjNegocio2, idCampoObjNegocio3);
	}
	
	
}
