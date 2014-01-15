package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.InventarioXMLDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class InventarioXMLServiceEjb extends CrudServicePojoImpl implements InventarioXMLService {
    protected CrudDAO getDao() throws ServiceException {
	return new InventarioXMLDAO();
    }

    protected void validaCreate(Object arg0) throws Exception {
    }

    protected void validaDelete(Object arg0) throws Exception {
    }

    protected void validaFind(Object arg0) throws Exception {
    }

    protected void validaUpdate(Object arg0) throws Exception {
    }
    public boolean inventarioAtualizado(String ip, java.util.Date dataExpiracao) throws Exception {
		 return new InventarioXMLDAO().inventarioAtualizado(ip, dataExpiracao);
	}    
 }