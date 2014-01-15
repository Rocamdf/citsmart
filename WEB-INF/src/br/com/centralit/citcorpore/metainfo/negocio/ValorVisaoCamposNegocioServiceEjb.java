package br.com.centralit.citcorpore.metainfo.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.metainfo.integracao.ValorVisaoCamposNegocioDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class ValorVisaoCamposNegocioServiceEjb extends CrudServicePojoImpl implements ValorVisaoCamposNegocioService {
	protected CrudDAO getDao() throws ServiceException {
		return new ValorVisaoCamposNegocioDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdGrupoVisao(Integer parm) throws Exception{
		ValorVisaoCamposNegocioDao dao = new ValorVisaoCamposNegocioDao();
		try{
			return dao.findByIdGrupoVisao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdGrupoVisao(Integer parm) throws Exception{
		ValorVisaoCamposNegocioDao dao = new ValorVisaoCamposNegocioDao();
		try{
			dao.deleteByIdGrupoVisao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByIdCamposObjetoNegocio(Integer parm) throws Exception{
		ValorVisaoCamposNegocioDao dao = new ValorVisaoCamposNegocioDao();
		try{
			return dao.findByIdCamposObjetoNegocio(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdCamposObjetoNegocio(Integer parm) throws Exception{
		ValorVisaoCamposNegocioDao dao = new ValorVisaoCamposNegocioDao();
		try{
			dao.deleteByIdCamposObjetoNegocio(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByIdGrupoVisaoAndIdCampoObjetoNegocio(Integer idGrupoVisao, Integer idCamposObjetoNegocio) throws Exception {
		ValorVisaoCamposNegocioDao dao = new ValorVisaoCamposNegocioDao();
		try{
			return dao.findByIdGrupoVisaoAndIdCampoObjetoNegocio(idGrupoVisao, idCamposObjetoNegocio);
		} catch (Exception e) {
			throw new ServiceException(e);
		}		
	}
}
