package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.RegraEscalonamentoDTO;
import br.com.centralit.citcorpore.integracao.EscalonamentoDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class EscalonamentoServiceEjb extends CrudServicePojoImpl implements EscalonamentoService {

	private static final long serialVersionUID = 1435665917865328478L;
	
	private static EscalonamentoDAO escalonamentoDAO;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new EscalonamentoDAO();
	}
	
	private static EscalonamentoDAO getEscalonamentoDAO() throws ServiceException {
		if (escalonamentoDAO == null) {
			escalonamentoDAO = (EscalonamentoDAO) new EscalonamentoDAO();
		}
		return escalonamentoDAO;
	}

	@Override
	protected void validaCreate(Object obj) throws Exception {
		
	}

	@Override
	protected void validaUpdate(Object obj) throws Exception {
		
	}

	@Override
	protected void validaDelete(Object obj) throws Exception {
		
	}

	@Override
	protected void validaFind(Object obj) throws Exception {
		
	}
	
	public Collection findByRegraEscalonamento(RegraEscalonamentoDTO regraEscalonamentoDTO) {
		try {
			return getEscalonamentoDAO().findByRegraEscalonamento(regraEscalonamentoDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
