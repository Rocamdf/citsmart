package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.integracao.ProdutoContratoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class ProdutoContratoServiceEjb extends CrudServicePojoImpl implements ProdutoContratoService {
	protected CrudDAO getDao() throws ServiceException {
		return new ProdutoContratoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdContrato(Integer parm) throws Exception{
		ProdutoContratoDao dao = new ProdutoContratoDao();
		try{
			return dao.findByIdContrato(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdContrato(Integer parm) throws Exception{
		ProdutoContratoDao dao = new ProdutoContratoDao();
		try{
			dao.deleteByIdContrato(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection getProdutosByIdMarcoPagamentoPrj(Integer idMarcoPagamentoPrjParm, Integer idLinhaBaseProjetoParm) throws Exception{
		ProdutoContratoDao dao = new ProdutoContratoDao();
		try{
			return dao.getProdutosByIdMarcoPagamentoPrj(idMarcoPagamentoPrjParm, idLinhaBaseProjetoParm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}		
	}
}
