package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ProblemaItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.integracao.ProblemaItemConfiguracaoDAO;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

@SuppressWarnings("rawtypes")
public class ProblemaItemConfiguracaoServiceEjb extends CrudServicePojoImpl implements ProblemaItemConfiguracaoService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected CrudDAO getDao() throws ServiceException {
		return new ProblemaItemConfiguracaoDAO();
	}

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaFind(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	public Collection findByIdProblemaItemConfiguracao(Integer parm) throws Exception {
		ProblemaItemConfiguracaoDAO dao = new ProblemaItemConfiguracaoDAO();
		try {
			return dao.findByIdProblemaItemConfiguracao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByIdProblemaItemConfiguracao(Integer parm) throws Exception {
		ProblemaItemConfiguracaoDAO dao = new ProblemaItemConfiguracaoDAO();
		try {
			dao.deleteByIdProblemaItemConfiguracao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection findByIdProblema(Integer parm) throws Exception {
		ProblemaItemConfiguracaoDAO dao = new ProblemaItemConfiguracaoDAO();
		try {
			return dao.findByIdProblema(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByIdProblema(Integer parm) throws Exception {
		ProblemaItemConfiguracaoDAO dao = new ProblemaItemConfiguracaoDAO();
		try {
			dao.deleteByIdProblema(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection findByIdItemConfiguracao(Integer parm) throws Exception {
		ProblemaItemConfiguracaoDAO dao = new ProblemaItemConfiguracaoDAO();
		try {
			return dao.findByIdItemConfiguracao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByIdItemConfiguracao(Integer parm) throws Exception {
		ProblemaItemConfiguracaoDAO dao = new ProblemaItemConfiguracaoDAO();
		try {
			dao.deleteByIdItemConfiguracao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public ProblemaItemConfiguracaoDTO restoreByIdProblema(Integer idProblema) throws Exception {
		try {
			ProblemaItemConfiguracaoDAO dao = new ProblemaItemConfiguracaoDAO();
			return dao.restoreByIdProblema(idProblema);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
