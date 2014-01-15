package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.MotivoSuspensaoAtividadeDTO;
import br.com.centralit.citcorpore.integracao.MotivoSuspensaoAtividadeDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

@SuppressWarnings("rawtypes")
public class MotivoSuspensaoAtividadeServiceEjb extends CrudServicePojoImpl implements MotivoSuspensaoAtividadeService {

	private static final long serialVersionUID = -7529128307064317791L;

	protected CrudDAO getDao() throws ServiceException {
		return new MotivoSuspensaoAtividadeDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaFind(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	public boolean jaExisteRegistroComMesmoNome(MotivoSuspensaoAtividadeDTO motivoSuspensaoAtividadeDTO) {
		try {
			return ((MotivoSuspensaoAtividadeDao) getDao()).jaExisteRegistroComMesmoNome(motivoSuspensaoAtividadeDTO);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public Collection listarMotivosSuspensaoAtividadeAtivos() throws Exception {
		return ((MotivoSuspensaoAtividadeDao) getDao()).listarMotivosSuspensaoAtividadeAtivos();
	}

}
