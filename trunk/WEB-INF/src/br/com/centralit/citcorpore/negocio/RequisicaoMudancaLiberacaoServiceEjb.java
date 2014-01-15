package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.integracao.RequisicaoLiberacaoDao;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaLiberacaoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class RequisicaoMudancaLiberacaoServiceEjb extends CrudServicePojoImpl implements RequisicaoMudancaLiberacaoService {
	protected CrudDAO getDao() throws ServiceException {
		return new RequisicaoLiberacaoDao();
	}

	/*@Override
	public Collection findByIdSolicitante(Integer parm) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}*/

	@Override
	protected void validaCreate(Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validaUpdate(Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validaDelete(Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validaFind(Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public Collection findByIdRequisicaoMudanca(Integer parm) throws Exception {
		RequisicaoMudancaLiberacaoDao dao = new RequisicaoMudancaLiberacaoDao();
		try {
			return dao.findByIdRequisicaoMudanca(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
