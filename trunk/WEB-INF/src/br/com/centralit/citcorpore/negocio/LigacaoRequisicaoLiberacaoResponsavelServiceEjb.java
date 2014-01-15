package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoResponsavelDTO;
import br.com.centralit.citcorpore.integracao.LigacaoRequisicaoLiberacaoResponsavelDao;
import br.com.centralit.citcorpore.integracao.RequisicaoLiberacaoMidiaDao;
import br.com.centralit.citcorpore.integracao.RequisicaoLiberacaoResponsavelDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class LigacaoRequisicaoLiberacaoResponsavelServiceEjb extends CrudServicePojoImpl implements LigacaoRequisicaoLiberacaoMidiaService {
	protected CrudDAO getDao() throws ServiceException {
		return new LigacaoRequisicaoLiberacaoResponsavelDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdLiberacao(Integer parm) throws Exception{
		RequisicaoLiberacaoResponsavelDao requisicaoLiberacaoResponsavelDao = new RequisicaoLiberacaoResponsavelDao();
		try{
			return requisicaoLiberacaoResponsavelDao.findByIdLiberacao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
