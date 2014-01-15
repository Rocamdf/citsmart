package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.integracao.LiberacaoMudancaDao;
import br.com.centralit.citcorpore.integracao.LiberacaoProblemaDao;
import br.com.centralit.citcorpore.integracao.RequisicaoLiberacaoMidiaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class RequisicaoLiberacaoMidiaServiceEjb extends CrudServicePojoImpl implements RequisicaoLiberacaoMidiaService {
	protected CrudDAO getDao() throws ServiceException {
		return new RequisicaoLiberacaoMidiaDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdLiberacao(Integer parm) throws Exception{
		RequisicaoLiberacaoMidiaDao requisicaoLiberacaoMidiaDao = new RequisicaoLiberacaoMidiaDao();
		try{
			return requisicaoLiberacaoMidiaDao.findByIdLiberacao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByIdLiberacaoEDataFim(Integer parm) throws Exception{
		RequisicaoLiberacaoMidiaDao requisicaoLiberacaoMidiaDao = new RequisicaoLiberacaoMidiaDao();
		try{
			return requisicaoLiberacaoMidiaDao.findByIdLiberacaoEDataFim(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
