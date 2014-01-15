package br.com.centralit.citcorpore.rh.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.rh.integracao.MovimentacaoPessoalDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class MovimentacaoPessoalServiceEjb extends CrudServicePojoImpl implements MovimentacaoPessoalService {
	protected CrudDAO getDao() throws ServiceException {
		return new MovimentacaoPessoalDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdEmpregado(Integer parm) throws Exception{
		MovimentacaoPessoalDao dao = new MovimentacaoPessoalDao();
		try{
			return dao.findByIdEmpregado(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdEmpregado(Integer parm) throws Exception{
		MovimentacaoPessoalDao dao = new MovimentacaoPessoalDao();
		try{
			dao.deleteByIdEmpregado(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
