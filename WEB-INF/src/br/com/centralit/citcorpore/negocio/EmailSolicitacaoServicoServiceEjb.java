package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.EmailSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.integracao.EmailSolicitacaoServicoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class EmailSolicitacaoServicoServiceEjb extends CrudServicePojoImpl implements EmailSolicitacaoServicoService {

    private static final long serialVersionUID = 1413958109573655318L;

    protected CrudDAO getDao() throws ServiceException {
	return new EmailSolicitacaoServicoDao();
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
    public EmailSolicitacaoServicoDTO listSituacao(String messageid) throws Exception {
	try {
	    EmailSolicitacaoServicoDao dao = new EmailSolicitacaoServicoDao();
	    return dao.listSituacao(messageid);
	} catch (Exception e) {
	    throw new ServiceException(e);
	}

    }

}
