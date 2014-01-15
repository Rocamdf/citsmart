package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoRequisicaoComprasDTO;
import br.com.centralit.citcorpore.integracao.RequisicaoLiberacaoRequisicaoComprasDAO;
import br.com.centralit.citcorpore.integracao.RequisicaoLiberacaoResponsavelDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class RequisicaoLiberacaoRequisicaoComprasServiceEjb extends CrudServicePojoImpl implements RequisicaoLiberacaoRequisicaoComprasService {
	protected CrudDAO getDao() throws ServiceException {
		return new RequisicaoLiberacaoRequisicaoComprasDAO();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdLiberacao(Integer param) throws Exception{
		RequisicaoLiberacaoRequisicaoComprasDAO dao = new RequisicaoLiberacaoRequisicaoComprasDAO();
		try{
			return dao.findByIdLiberacao(param);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByIdLiberacaoAndDataFim(Integer idRequisicaoLiberacao) throws Exception{
		RequisicaoLiberacaoRequisicaoComprasDAO dao = new RequisicaoLiberacaoRequisicaoComprasDAO();
		try{
			return dao.findByIdLiberacaoAndDataFim(idRequisicaoLiberacao);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdLiberacao(Integer param) throws Exception{
		RequisicaoLiberacaoRequisicaoComprasDAO dao = new RequisicaoLiberacaoRequisicaoComprasDAO();
		try{
			dao.deleteByIdRequisicaoLiberacao(param);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	public RequisicaoLiberacaoRequisicaoComprasDTO carregaItemRequisicaoComprasByidItemRequisicaProduto(Integer idItemrRequisicaoProduto) throws Exception{
		RequisicaoLiberacaoRequisicaoComprasDAO requisicaoLiberacaoRequisicaoComprasDAO = new RequisicaoLiberacaoRequisicaoComprasDAO();
		return requisicaoLiberacaoRequisicaoComprasDAO.carregaItemRequisicaoComprasByidItemRequisicaProduto(idItemrRequisicaoProduto);
	}
}
