package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ImagemHistoricoDTO;
import br.com.centralit.citcorpore.integracao.ImagemHistoricoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class ImagemHistoricoServiceEjb extends CrudServicePojoImpl implements ImagemHistoricoService{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected CrudDAO getDao() throws ServiceException {
		
		return new ImagemHistoricoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
		
	}

	protected void validaDelete(Object arg0) throws Exception {
		
	}

	protected void validaFind(Object arg0) throws Exception {
		
	}

	protected void validaUpdate(Object arg0) throws Exception {
		
	}
	public Collection listByIdContrato(Integer idContrato) throws Exception {
		ImagemHistoricoDao dao = new ImagemHistoricoDao();
		return dao.listByIdContrato(idContrato);
	}
	public Collection listByIdContratoAndAba(Integer idContrato, String aba) throws Exception {
		ImagemHistoricoDao dao = new ImagemHistoricoDao();
		return dao.listByIdContratoAndAba(idContrato, aba);
	}
	
	public ImagemHistoricoDTO findByNomeArquivo(final ImagemHistoricoDTO filter) 
            throws Exception
    {
	    return ((ImagemHistoricoDao)getDao()).findByNomeArquivo(filter);
    }
}