package br.com.centralit.citcorpore.metainfo.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.metainfo.integracao.GrupoVisaoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class GrupoVisaoServiceEjb extends CrudServicePojoImpl implements GrupoVisaoService {
	protected CrudDAO getDao() throws ServiceException {
		return new GrupoVisaoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdVisao(Integer idVisao) throws Exception {
		GrupoVisaoDao grupoVisaoDao = new GrupoVisaoDao();
		return grupoVisaoDao.findByIdVisao(idVisao);
	}
	public Collection findByIdVisaoAtivos(Integer idVisao) throws Exception {
		GrupoVisaoDao grupoVisaoDao = new GrupoVisaoDao();
		return grupoVisaoDao.findByIdVisaoAtivos(idVisao);		
	}
}
