package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ContratosUnidadesDTO;
import br.com.centralit.citcorpore.integracao.ContratosGruposDAO;
import br.com.centralit.citcorpore.integracao.ContratosUnidadesDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class ContratosUnidadesServiceEjb extends CrudServicePojoImpl implements ContratosUnidadesService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6885689926375094896L;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new ContratosGruposDAO();
	}

	@Override
	protected void validaCreate(Object obj) throws Exception {
		
	}

	@Override
	protected void validaUpdate(Object obj) throws Exception {
		
	}

	@Override
	protected void validaDelete(Object obj) throws Exception {
		
	}

	@Override
	protected void validaFind(Object obj) throws Exception {
		
	}

	@Override
	public Collection<ContratosUnidadesDTO> findByIdUnidade(Integer idUnidade)
			throws Exception {
			ContratosUnidadesDAO dao = new ContratosUnidadesDAO();
			try{
		return dao.findByIdUnidade(idUnidade);
			}
			catch (Exception e) {
				throw new ServiceException(e);
			}
	}

}
