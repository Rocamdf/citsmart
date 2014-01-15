package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ControleRendimentoDTO;
import br.com.centralit.citcorpore.bean.RelatorioListaNegraDTO;
import br.com.centralit.citcorpore.bean.SoftwaresListaNegraDTO;
import br.com.centralit.citcorpore.integracao.ControleRendimentoDao;
import br.com.centralit.citcorpore.integracao.SoftwaresListaNegraDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

@SuppressWarnings("rawtypes")
public class SoftwaresListaNegraServiceEjb extends CrudServicePojoImpl implements SoftwaresListaNegraService {

	private static final long serialVersionUID = -4101073129183404628L;

	protected CrudDAO getDao() throws ServiceException {
		return new SoftwaresListaNegraDao();
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
	
	public IDto create(IDto model) throws ServiceException, LogicException {
		SoftwaresListaNegraDTO softwaresListaNegraDTO = (SoftwaresListaNegraDTO) model;
		return super.create(softwaresListaNegraDTO);
	}
	
	public boolean verficiarSoftwareListaNegraMesmoNome(String nome) {
		ArrayList<Condition> condicoes = new ArrayList<Condition>();
		condicoes.add(new Condition("nomeSoftwaresListaNegra", "=", nome));
		Collection retorno = null;
		try {
			retorno = ((SoftwaresListaNegraDao) getDao()).findByCondition(condicoes, null);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno == null ? false : true;
	}

	@Override
	public Collection<RelatorioListaNegraDTO> listaRelatorioListaNegra(RelatorioListaNegraDTO relatorioListaNegraDTO) throws Exception{
		SoftwaresListaNegraDao listanegraDao = new SoftwaresListaNegraDao();
		try{
			return listanegraDao.listaRelatorioListaNegra(relatorioListaNegraDTO);
		} catch (Exception e){
			throw new ServiceException(e);
		}
	}
	
}
