package br.com.centralit.citcorpore.metainfo.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.metainfo.integracao.GrupoVisaoCamposNegocioInfoSQLDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class GrupoVisaoCamposNegocioInfoSQLServiceEjb extends CrudServicePojoImpl implements GrupoVisaoCamposNegocioInfoSQLService {
	protected CrudDAO getDao() throws ServiceException {
		return new GrupoVisaoCamposNegocioInfoSQLDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdGrupoVisao(Integer parm) throws Exception{
		GrupoVisaoCamposNegocioInfoSQLDao dao = new GrupoVisaoCamposNegocioInfoSQLDao();
		try{
			return dao.findByIdGrupoVisao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdGrupoVisao(Integer parm) throws Exception{
		GrupoVisaoCamposNegocioInfoSQLDao dao = new GrupoVisaoCamposNegocioInfoSQLDao();
		try{
			dao.deleteByIdGrupoVisao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByIdCamposObjetoNegocio(Integer parm) throws Exception{
		GrupoVisaoCamposNegocioInfoSQLDao dao = new GrupoVisaoCamposNegocioInfoSQLDao();
		try{
			return dao.findByIdCamposObjetoNegocio(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdCamposObjetoNegocio(Integer parm) throws Exception{
		GrupoVisaoCamposNegocioInfoSQLDao dao = new GrupoVisaoCamposNegocioInfoSQLDao();
		try{
			dao.deleteByIdCamposObjetoNegocio(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
