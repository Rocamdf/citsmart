package br.com.centralit.citcorpore.integracao;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.LinguaDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class LinguaServiceEjb extends CrudServicePojoImpl implements LinguaService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		// TODO Auto-generated method stub
		return new LinguaDao();
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

	@Override
	public boolean consultarLinguaAtivas(LinguaDTO obj) throws Exception {
		LinguaDao dao = new LinguaDao();
		return dao.consultarLinguaAtivas(obj);
	}

	@Override
	public LinguaDTO getIdLingua(LinguaDTO obj) throws Exception {
		LinguaDao dao = new LinguaDao();
		return dao.getIdLingua(obj);
	}

	@Override
	public Collection<LinguaDTO> listarAtivos() throws Exception {
		
		return new LinguaDao().listarAtivos();
	}

}
