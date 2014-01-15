package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.LigacaoRequisicaoLiberacaoHistoricoComprasDTO;
import br.com.centralit.citcorpore.bean.LigacaoRequisicaoLiberacaoHistoricoMidiaDTO;
import br.com.centralit.citcorpore.integracao.LigacaoRequisicaoLiberacaoMidiaDao;
import br.com.centralit.citcorpore.integracao.RequisicaoLiberacaoMidiaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class LigacaoRequisicaoLiberacaoComprasServiceEjb extends CrudServicePojoImpl implements LigacaoRequisicaoLiberacaoComprasService {
	protected CrudDAO getDao() throws ServiceException {
		return new LigacaoRequisicaoLiberacaoMidiaDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection<LigacaoRequisicaoLiberacaoHistoricoComprasDTO> findByIdLiberacao(Integer parm) throws Exception{
		RequisicaoLiberacaoMidiaDao requisicaoLiberacaoMidiaDao = new RequisicaoLiberacaoMidiaDao();
		try{
			return requisicaoLiberacaoMidiaDao.findByIdLiberacao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
