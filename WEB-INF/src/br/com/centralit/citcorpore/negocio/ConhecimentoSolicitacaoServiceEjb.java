package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import br.com.centralit.citcorpore.bean.ConhecimentoSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.integracao.ConhecimentoSolicitacaoDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServicePojoImpl;

public class ConhecimentoSolicitacaoServiceEjb extends CrudServicePojoImpl implements ConhecimentoSolicitacaoService {

	private static final long serialVersionUID = 2936471606760791610L;
	private static ConhecimentoSolicitacaoDao conhecimentoSolicitacaoDao;
	
	protected CrudDAO getDao() throws ServiceException {
		return new ConhecimentoSolicitacaoDao();
	}
	private static ConhecimentoSolicitacaoDao getConhecimentoSolicitacaoDao() throws ServiceException {
		if (conhecimentoSolicitacaoDao == null) {
			conhecimentoSolicitacaoDao = (ConhecimentoSolicitacaoDao) new ConhecimentoSolicitacaoDao();
		}
		return conhecimentoSolicitacaoDao;
	}
	@Override
	public void rollbackTransaction(TransactionControler tc, Exception ex) throws ServiceException, LogicException {
		super.rollbackTransaction(tc, ex);
	}
	@Override
	public ConhecimentoSolicitacaoDTO restoreAll(Integer idSolicitacaoServico)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Collection findBySolictacaoServico(ConhecimentoSolicitacaoDTO bean) throws ServiceException, LogicException {
		try {
			return getConhecimentoSolicitacaoDao().findByidSolicitacaoServico(bean.getIdSolicitacaoServico());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
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
}
