package br.com.centralit.citcorpore.metainfo.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.metainfo.integracao.GrupoVisaoCamposNegocioLigacaoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class GrupoVisaoCamposNegocioLigacaoServiceEjb extends CrudServicePojoImpl implements GrupoVisaoCamposNegocioLigacaoService {
	protected CrudDAO getDao() throws ServiceException {
		return new GrupoVisaoCamposNegocioLigacaoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}
	
	public Collection findByIdGrupoVisaoAndIdCamposObjetoNegocio(Integer idGrpVisao, Integer idCamposObjetoNegocio) throws Exception {
		GrupoVisaoCamposNegocioLigacaoDao dao = new GrupoVisaoCamposNegocioLigacaoDao();
		try{
			return dao.findByIdGrupoVisaoAndIdCamposObjetoNegocio(idGrpVisao, idCamposObjetoNegocio);
		} catch (Exception e) {
			throw new ServiceException(e);
		}		
	}

	public Collection findByIdGrupoVisao(Integer parm) throws Exception{
		GrupoVisaoCamposNegocioLigacaoDao dao = new GrupoVisaoCamposNegocioLigacaoDao();
		try{
			return dao.findByIdGrupoVisao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdGrupoVisao(Integer parm) throws Exception{
		GrupoVisaoCamposNegocioLigacaoDao dao = new GrupoVisaoCamposNegocioLigacaoDao();
		try{
			dao.deleteByIdGrupoVisao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByIdCamposObjetoNegocio(Integer parm) throws Exception{
		GrupoVisaoCamposNegocioLigacaoDao dao = new GrupoVisaoCamposNegocioLigacaoDao();
		try{
			return dao.findByIdCamposObjetoNegocio(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdCamposObjetoNegocio(Integer parm) throws Exception{
		GrupoVisaoCamposNegocioLigacaoDao dao = new GrupoVisaoCamposNegocioLigacaoDao();
		try{
			dao.deleteByIdCamposObjetoNegocio(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByIdCamposObjetoNegocioLigacao(Integer parm) throws Exception{
		GrupoVisaoCamposNegocioLigacaoDao dao = new GrupoVisaoCamposNegocioLigacaoDao();
		try{
			return dao.findByIdCamposObjetoNegocioLigacao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdCamposObjetoNegocioLigacao(Integer parm) throws Exception{
		GrupoVisaoCamposNegocioLigacaoDao dao = new GrupoVisaoCamposNegocioLigacaoDao();
		try{
			dao.deleteByIdCamposObjetoNegocioLigacao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
