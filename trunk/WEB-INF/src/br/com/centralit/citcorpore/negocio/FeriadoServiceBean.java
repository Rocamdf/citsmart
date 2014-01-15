package br.com.centralit.citcorpore.negocio;

import java.sql.Date;

import br.com.centralit.citcorpore.integracao.FeriadoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;


public class FeriadoServiceBean extends CrudServicePojoImpl implements FeriadoService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected CrudDAO getDao() throws ServiceException {		
		return new FeriadoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
		
	}

	protected void validaDelete(Object arg0) throws Exception {
		
	}

	protected void validaFind(Object arg0) throws Exception {
		
	}

	protected void validaUpdate(Object arg0) throws Exception {
		
	}

    public boolean isFeriado(Date data, Integer idCidade, Integer idUf) throws Exception {
        return new FeriadoDao().isFeriado(data, idCidade, idUf);
    }
	
}