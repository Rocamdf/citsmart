package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.integracao.AvaliacaoColetaPrecoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class AvaliacaoColetaPrecoServiceEjb extends CrudServicePojoImpl implements AvaliacaoColetaPrecoService {
	protected CrudDAO getDao() throws ServiceException {
		return new AvaliacaoColetaPrecoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdColetaPreco(Integer idColetaPreco) throws Exception {
		AvaliacaoColetaPrecoDao dao = new AvaliacaoColetaPrecoDao();
		try{
			return dao.findByIdColetaPreco(idColetaPreco);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdColetaPreco(Integer idColetaPreco) throws Exception {
		AvaliacaoColetaPrecoDao dao = new AvaliacaoColetaPrecoDao();
		try{
			dao.deleteByIdColetaPreco(idColetaPreco);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
