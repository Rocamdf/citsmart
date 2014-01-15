package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.MoedaDTO;
import br.com.centralit.citcorpore.integracao.MoedaDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class MoedaServiceEjb extends CrudServicePojoImpl implements MoedaService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4380580886736407555L;

	protected CrudDAO getDao() throws ServiceException {
		return new MoedaDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
		
	}

	protected void validaDelete(Object arg0) throws Exception {
		
	}

	protected void validaFind(Object arg0) throws Exception {
		
	}

	protected void validaUpdate(Object arg0) throws Exception {
		
	}
	
	@Override
	public boolean verificaSeCadastrado(MoedaDTO moedaDTO) throws PersistenceException {
		MoedaDao dao = new MoedaDao();
		return dao.verificaSeCadastrado(moedaDTO);
	}
	
	@Override
	public boolean verificaRelacionamento(MoedaDTO moedaDTO)	throws Exception {
		try {
			MoedaDao dao = (MoedaDao) getDao();
		    return dao.verificaRelacionamento(moedaDTO);
		} catch (Exception e) {
		    throw new ServiceException(e);
		}
	}	
	
	public void updateNotNull(IDto obj) throws Exception {
		try {
			MoedaDao dao = (MoedaDao) getDao();
		    dao.updateNotNull(obj);
		} catch (Exception e) {
		    throw new ServiceException(e);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public Collection findAllAtivos() throws Exception{
		MoedaDao dao = new MoedaDao();
		return dao.findAtivos();
	}

}
