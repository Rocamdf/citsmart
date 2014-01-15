package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.AssinaturaAprovacaoProjetoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class AssinaturaAprovacaoProjetoServiceEjb extends CrudServicePojoImpl implements AssinaturaAprovacaoProjetoService{
	
	protected CrudDAO getDao() throws ServiceException {
		return new AssinaturaAprovacaoProjetoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdProjeto(Integer parm) throws Exception{
		AssinaturaAprovacaoProjetoDao dao = new AssinaturaAprovacaoProjetoDao();
		try{
			return dao.findByIdProjeto(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdProjeto(Integer parm) throws Exception{
		AssinaturaAprovacaoProjetoDao dao = new AssinaturaAprovacaoProjetoDao();
		try{
			dao.deleteByIdProjeto(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByIdEmpregado(Integer parm) throws Exception{
		AssinaturaAprovacaoProjetoDao dao = new AssinaturaAprovacaoProjetoDao();
		try{
			return dao.findByIdEmpregado(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdEmpregado(Integer parm) throws Exception{
		AssinaturaAprovacaoProjetoDao dao = new AssinaturaAprovacaoProjetoDao();
		try{
			dao.deleteByIdEmpregado(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
}
