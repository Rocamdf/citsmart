package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.integracao.DemandaDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class DemandaServiceEjb extends CrudServicePojoImpl implements DemandaService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4101073129183404628L;

	protected CrudDAO getDao() throws ServiceException {
		return new DemandaDao();
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

	public IDto create(IDto model) throws ServiceException, LogicException{
/*		//Instancia Objeto controlador de transacao
		CrudDAO crudDao = getDao();
		ExecucaoDemandaDao execucaoDemandaDao = new ExecucaoDemandaDao();
		TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
		FluxoDao fluxoDao = new FluxoDao();
		
		DemandaDTO demanda = (DemandaDTO)model;
		try{
			//Faz validacao, caso exista.
			validaCreate(model);
			
			//Seta o TransactionController para os DAOs
			crudDao.setTransactionControler(tc);
			execucaoDemandaDao.setTransactionControler(tc);
			
			//Inicia transacao
			tc.start();
			
			FluxoDTO fluxo = fluxoDao.getNextAtividadeByFluxo(demanda.getIdFluxo(), null);
			
			//Executa operacoes pertinentes ao negocio.
			model = crudDao.create(model);
			
			ExecucaoDemandaDTO execucaoDemanda = new ExecucaoDemandaDTO();
			execucaoDemanda.setIdAtividade(fluxo.getIdAtividade());
			execucaoDemanda.setIdDemanda(demanda.getIdDemanda());
			execucaoDemanda.setSituacao("N");
			execucaoDemanda.setGrupoExecutor(fluxo.getGrupoExecutor());
			execucaoDemanda = (ExecucaoDemandaDTO) execucaoDemandaDao.create(execucaoDemanda);
			
			//Faz commit e fecha a transacao.
			tc.commit();
			tc.close();
			
			return model;
		}catch(Exception e){
			this.rollbackTransaction(tc, e);
		}*/
		
		return model;

	}
	public Collection findByIdOS(Integer parm) throws Exception{
		DemandaDao dao = new DemandaDao();
		try{
			return dao.findByIdOS(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}	
}
