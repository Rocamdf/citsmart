package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.InfoCatalogoServicoDTO;
import br.com.centralit.citcorpore.integracao.InfoCatalogoServicoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class InfoCatalogoServicoServiceEjb extends CrudServicePojoImpl implements InfoCatalogoServicoService {
	
	private static final long serialVersionUID = -2253183314661440900L;
    
	protected CrudDAO getDao() throws ServiceException {
		return new InfoCatalogoServicoDao();
	}
	
	private InfoCatalogoServicoDao infoCatalogoServicoDao() throws ServiceException {
		return (InfoCatalogoServicoDao) getDao();
	}
	
	protected void validaCreate(Object obj) throws Exception {
		
	}
	
	protected void validaDelete(Object obj) throws Exception {
		
	}
	
	protected void validaUpdate(Object obj) throws Exception {
		
	}
	
	protected void validaFind(Object obj) throws Exception {
		
	}

	@Override
	public Collection<InfoCatalogoServicoDTO> findByCatalogoServico(Integer idCatalogoServico) throws ServiceException, Exception {
		return this.infoCatalogoServicoDao().findByCatalogoServico(idCatalogoServico);
	}
	
	@Override
	public boolean findByContratoServico(Integer idContratoServico) throws ServiceException, Exception {
		return this.infoCatalogoServicoDao().findByContratoServico(idContratoServico);
	}
}
