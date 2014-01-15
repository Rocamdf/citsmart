package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.integracao.TabFederacaoDadosDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class TabFederacaoDadosServiceEjb extends CrudServicePojoImpl implements TabFederacaoDadosService {
	protected CrudDAO getDao() throws ServiceException {
		return new TabFederacaoDadosDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByNomeTabela(String parm) throws Exception{
		TabFederacaoDadosDao dao = new TabFederacaoDadosDao();
		try{
			return dao.findByNomeTabela(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByNomeTabela(String parm) throws Exception{
		TabFederacaoDadosDao dao = new TabFederacaoDadosDao();
		try{
			dao.deleteByNomeTabela(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByChaveFinal(String parm) throws Exception{
		TabFederacaoDadosDao dao = new TabFederacaoDadosDao();
		try{
			return dao.findByChaveFinal(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByChaveFinal(String parm) throws Exception{
		TabFederacaoDadosDao dao = new TabFederacaoDadosDao();
		try{
			dao.deleteByChaveFinal(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByChaveOriginal(String parm) throws Exception{
		TabFederacaoDadosDao dao = new TabFederacaoDadosDao();
		try{
			return dao.findByChaveOriginal(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByChaveOriginal(String parm) throws Exception{
		TabFederacaoDadosDao dao = new TabFederacaoDadosDao();
		try{
			dao.deleteByChaveOriginal(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByOrigem(String parm) throws Exception{
		TabFederacaoDadosDao dao = new TabFederacaoDadosDao();
		try{
			return dao.findByOrigem(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByOrigem(String parm) throws Exception{
		TabFederacaoDadosDao dao = new TabFederacaoDadosDao();
		try{
			dao.deleteByOrigem(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
