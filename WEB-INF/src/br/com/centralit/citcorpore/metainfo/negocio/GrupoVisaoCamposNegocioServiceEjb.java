package br.com.centralit.citcorpore.metainfo.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.metainfo.integracao.GrupoVisaoCamposNegocioDao;
import br.com.centralit.citcorpore.metainfo.integracao.GrupoVisaoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class GrupoVisaoCamposNegocioServiceEjb extends CrudServicePojoImpl implements GrupoVisaoCamposNegocioService {
	protected CrudDAO getDao() throws ServiceException {
		return new GrupoVisaoCamposNegocioDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdGrupoVisao(Integer idGrupoVisao) throws Exception {
		GrupoVisaoCamposNegocioDao dao = new GrupoVisaoCamposNegocioDao();
		return dao.findByIdGrupoVisao(idGrupoVisao);
	}	
	public Collection findByIdGrupoVisaoAtivos(Integer idGrupoVisao) throws Exception {
		GrupoVisaoCamposNegocioDao dao = new GrupoVisaoCamposNegocioDao();
		return dao.findByIdGrupoVisaoAtivos(idGrupoVisao);		
	}
}
