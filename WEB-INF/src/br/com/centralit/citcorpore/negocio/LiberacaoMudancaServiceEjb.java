package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.LiberacaoMudancaDTO;
import br.com.centralit.citcorpore.integracao.LiberacaoMudancaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

@SuppressWarnings({ "rawtypes", "serial" })
public class LiberacaoMudancaServiceEjb extends CrudServicePojoImpl implements LiberacaoMudancaService {
	protected CrudDAO getDao() throws ServiceException {
		return new LiberacaoMudancaDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdLiberacao(Integer parm) throws Exception{
		LiberacaoMudancaDao dao = new LiberacaoMudancaDao();
		try{
			return dao.findByIdLiberacao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdLiberacao(Integer parm) throws Exception{
		LiberacaoMudancaDao dao = new LiberacaoMudancaDao();
		try{
			dao.deleteByIdLiberacao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	/**
	 * @author geber.costa
	 * @param idrequisicaoliberacao
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 * Lista por idrequisicaoliberacao, no caso ele listará apenas os que o status não for nulo.
	 */
	
	public Collection<LiberacaoMudancaDTO> listAll() throws ServiceException, Exception {
		LiberacaoMudancaDao dao = new LiberacaoMudancaDao();
		try{
			return dao.listAll();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}


	@Override
	public Collection findByIdRequisicaoMudanca(Integer idLiberacao, Integer idRequisicaoMudanca) throws Exception {
		LiberacaoMudancaDao dao = new LiberacaoMudancaDao();
		try {
			return dao.findByIdRequisicaoMudanca(idLiberacao,idRequisicaoMudanca);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
