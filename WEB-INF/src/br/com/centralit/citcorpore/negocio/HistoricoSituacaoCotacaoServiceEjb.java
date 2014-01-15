package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.integracao.HistoricoSituacaoCotacaoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class HistoricoSituacaoCotacaoServiceEjb extends CrudServicePojoImpl implements HistoricoSituacaoCotacaoService {
	protected CrudDAO getDao() throws ServiceException {
		return new HistoricoSituacaoCotacaoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdCotacao(Integer parm) throws Exception{
		HistoricoSituacaoCotacaoDao dao = new HistoricoSituacaoCotacaoDao();
		try{
			return dao.findByIdCotacao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdCotacao(Integer parm) throws Exception{
		HistoricoSituacaoCotacaoDao dao = new HistoricoSituacaoCotacaoDao();
		try{
			dao.deleteByIdCotacao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByIdResponsavel(Integer parm) throws Exception{
		HistoricoSituacaoCotacaoDao dao = new HistoricoSituacaoCotacaoDao();
		try{
			return dao.findByIdResponsavel(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdResponsavel(Integer parm) throws Exception{
		HistoricoSituacaoCotacaoDao dao = new HistoricoSituacaoCotacaoDao();
		try{
			dao.deleteByIdResponsavel(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
