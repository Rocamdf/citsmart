package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.RequisicaoMudancaResponsavelDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class RequisicaoMudancaResponsavelServiceEjb extends CrudServicePojoImpl implements RequisicaoMudancaResponsavelService {
	protected CrudDAO getDao() throws ServiceException {
		return new RequisicaoMudancaResponsavelDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdMudanca(Integer parm) throws Exception{
		RequisicaoMudancaResponsavelDao dao = new RequisicaoMudancaResponsavelDao();
		try{
			return dao.findByIdMudanca(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByIdMudancaEDataFim(Integer parm) throws Exception{
		RequisicaoMudancaResponsavelDao dao = new RequisicaoMudancaResponsavelDao();
		try{
			return dao.findByIdMudancaEDataFim(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdMudanca(Integer parm) throws Exception{
		RequisicaoMudancaResponsavelDao dao = new RequisicaoMudancaResponsavelDao();
		try{
			dao.deleteByIdRequisicaoMudanca(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
