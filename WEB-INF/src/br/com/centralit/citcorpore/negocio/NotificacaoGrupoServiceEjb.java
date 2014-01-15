package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.NotificacaoGrupoDTO;
import br.com.centralit.citcorpore.integracao.NotificacaoGrupoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

@SuppressWarnings("serial")
public class NotificacaoGrupoServiceEjb extends CrudServicePojoImpl implements NotificacaoGrupoService {

	@Override
	protected CrudDAO getDao() throws ServiceException {

		return new NotificacaoGrupoDao();
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
	public Collection<NotificacaoGrupoDTO> listaIdGrupo(Integer idNotificacao) throws Exception {
		NotificacaoGrupoDao dao = new NotificacaoGrupoDao();
		return dao.listaIdGrupo(idNotificacao);
	}

}
