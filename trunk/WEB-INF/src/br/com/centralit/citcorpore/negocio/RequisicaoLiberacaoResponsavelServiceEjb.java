package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.integracao.RequisicaoLiberacaoResponsavelDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class RequisicaoLiberacaoResponsavelServiceEjb extends CrudServicePojoImpl implements RequisicaoLiberacaoResponsavelService {
	protected CrudDAO getDao() throws ServiceException {
		return new RequisicaoLiberacaoResponsavelDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdLiberacao(Integer parm) throws Exception{
		RequisicaoLiberacaoResponsavelDao dao = new RequisicaoLiberacaoResponsavelDao();
		try{
			return dao.findByIdLiberacao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	public Collection findByIdLiberacaoEDataFim(Integer parm) throws Exception{
		RequisicaoLiberacaoResponsavelDao dao = new RequisicaoLiberacaoResponsavelDao();
		try{
			return dao.findByIdLiberacaoEDataFim(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	public void deleteByIdLiberacao(Integer parm) throws Exception{
		RequisicaoLiberacaoResponsavelDao dao = new RequisicaoLiberacaoResponsavelDao();
		try{
			dao.deleteByIdRequisicaoLiberacao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
