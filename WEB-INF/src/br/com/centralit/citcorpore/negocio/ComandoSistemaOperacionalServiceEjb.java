package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ComandoSistemaOperacionalDTO;
import br.com.centralit.citcorpore.integracao.ComandoSistemaOperacionalDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

/**
 * @author ygor.magalhaes
 * 
 */

@SuppressWarnings({"rawtypes", "unchecked"})
public class ComandoSistemaOperacionalServiceEjb extends CrudServicePojoImpl
		implements ComandoSistemaOperacionalService {

	protected CrudDAO getDao() throws ServiceException {
		return new ComandoSistemaOperacionalDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
		
	}

	protected void validaDelete(Object arg0) throws Exception {
		
	}

	protected void validaUpdate(Object arg0) throws Exception {
		
	}

	protected void validaFind(Object obj) throws Exception {
		
	}

	public Collection list(List ordenacao) throws LogicException, RemoteException, ServiceException {
		return null;
	}

	public Collection list(String ordenacao) throws LogicException, RemoteException, ServiceException {
		return null;
	}

	public ComandoSistemaOperacionalDTO pegarComandoSO(String so, String tipoExecucao) throws ServiceException, RemoteException {		
		try {
			ComandoSistemaOperacionalDao dao = (ComandoSistemaOperacionalDao) getDao();
			
			return dao.pegarComandoSO(so, tipoExecucao);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean pesquisarExistenciaComandoSO(ComandoSistemaOperacionalDTO comandoSODTO) throws ServiceException, RemoteException {
		boolean jaExiste = false;
		
		try {
			ComandoSistemaOperacionalDao dao = (ComandoSistemaOperacionalDao) getDao();
			 
			List<Condition> condicoes = new ArrayList<Condition>();
			
			condicoes.add(new Condition("comando", "=", comandoSODTO.getComando() ) );
			condicoes.add(new Condition("idComando", "=", comandoSODTO.getIdComando() ) );
			condicoes.add(new Condition("idSistemaOperacional", "=", comandoSODTO.getIdSistemaOperacional() ) );
			
			Collection colecao = dao.findByCondition(condicoes, null);
			
			if (colecao != null && !colecao.isEmpty() ) {
				jaExiste = true;
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
		return jaExiste;
	}
}
