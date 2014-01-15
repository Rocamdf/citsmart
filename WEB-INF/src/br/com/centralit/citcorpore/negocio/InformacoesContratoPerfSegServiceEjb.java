package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.InformacoesContratoPerfSegDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class InformacoesContratoPerfSegServiceEjb extends CrudServicePojoImpl implements InformacoesContratoPerfSegService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected CrudDAO getDao() throws ServiceException {
		
		return new InformacoesContratoPerfSegDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
		
	}

	protected void validaDelete(Object arg0) throws Exception {
		
	}

	protected void validaFind(Object arg0) throws Exception {
		
	}

	protected void validaUpdate(Object arg0) throws Exception {
		
	}
	public Collection findByIdProntuarioEletronicoConfig(Integer idProntuarioEletronicoConfig) throws Exception {
		InformacoesContratoPerfSegDao dao = new InformacoesContratoPerfSegDao();
		return dao.findByIdInformacoesContratoConfig(idProntuarioEletronicoConfig);
	}
}
