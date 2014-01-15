package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.EscalonamentoDTO;
import br.com.centralit.citcorpore.bean.RegraEscalonamentoDTO;
import br.com.centralit.citcorpore.integracao.EscalonamentoDAO;
import br.com.centralit.citcorpore.integracao.RegraEscalonamentoDAO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;

public class RegraEscalonamentoServiceEjb extends CrudServicePojoImpl implements RegraEscalonamentoService {

	private static final long serialVersionUID = -1356336779058072756L;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new RegraEscalonamentoDAO();
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
	public IDto create(IDto model) throws ServiceException, LogicException {
		RegraEscalonamentoDTO regraEscalonamentoDTO = (RegraEscalonamentoDTO) model;
		RegraEscalonamentoDAO regraEscalonamentoDAO = (RegraEscalonamentoDAO) getDao();
		EscalonamentoDAO escalonamentoDAO = new EscalonamentoDAO();
		
        TransactionControler tc = new TransactionControlerImpl(regraEscalonamentoDAO.getAliasDB());
        try{
        	regraEscalonamentoDAO.setTransactionControler(tc);
        	escalonamentoDAO.setTransactionControler(tc);
            
        	tc.start();
            
        	regraEscalonamentoDTO.setDataInicio(UtilDatas.getDataAtual());
        	regraEscalonamentoDTO = (RegraEscalonamentoDTO) regraEscalonamentoDAO.create(regraEscalonamentoDTO);
            
            mantemEscalonamentos(regraEscalonamentoDTO,escalonamentoDAO);
            
            tc.commit();
            tc.close();
        }catch(Exception e){
            this.rollbackTransaction(tc, e);
        }
		return regraEscalonamentoDTO;
	}

	@Override
	public void update(IDto model) throws ServiceException, LogicException {
		RegraEscalonamentoDTO regraEscalonamentoDTO = (RegraEscalonamentoDTO) model;
		RegraEscalonamentoDAO regraEscalonamentoDAO = (RegraEscalonamentoDAO) getDao();
		EscalonamentoDAO escalonamentoDAO = new EscalonamentoDAO();
		
        TransactionControler tc = new TransactionControlerImpl(regraEscalonamentoDAO.getAliasDB());
        try{
        	regraEscalonamentoDAO.setTransactionControler(tc);
        	escalonamentoDAO.setTransactionControler(tc);
            
        	tc.start();
            
        	regraEscalonamentoDAO.update(regraEscalonamentoDTO);
            
            mantemEscalonamentos(regraEscalonamentoDTO,escalonamentoDAO);
            
            tc.commit();
            tc.close();
        }catch(Exception e){
            this.rollbackTransaction(tc, e);
        }
	}
	
	@Override
	public void delete(IDto model) throws ServiceException, LogicException {
		RegraEscalonamentoDTO regraEscalonamentoDTO = (RegraEscalonamentoDTO) model;
		RegraEscalonamentoDAO regraEscalonamentoDAO = (RegraEscalonamentoDAO) getDao();
		EscalonamentoDAO escalonamentoDAO = new EscalonamentoDAO();		
        TransactionControler tc = new TransactionControlerImpl(regraEscalonamentoDAO.getAliasDB());
        try{
        	regraEscalonamentoDAO.setTransactionControler(tc);
        	escalonamentoDAO.setTransactionControler(tc);
            
        	tc.start();
            
        	regraEscalonamentoDTO.setDataFim(UtilDatas.getDataAtual());
        	
        	if (regraEscalonamentoDTO.getColEscalonamentoDTOs()!=null){
        		regraEscalonamentoDTO.getColEscalonamentoDTOs().clear();
        	}
        	regraEscalonamentoDAO.update(regraEscalonamentoDTO);
            
            mantemEscalonamentos(regraEscalonamentoDTO,escalonamentoDAO);
            
            tc.commit();
            tc.close();
        }catch(Exception e){
            this.rollbackTransaction(tc, e);
        }		
	}

	private void mantemEscalonamentos(RegraEscalonamentoDTO regraEscalonamentoDTO,EscalonamentoDAO escalonamentoDAO) throws Exception{
        //escalonamentoDAO.deleteByIdRegraEscalonamento(regraEscalonamentoDTO.getIdRegraEscalonamento());
        escalonamentoDAO.gravarDataFim(regraEscalonamentoDTO.getIdRegraEscalonamento());
        Collection<EscalonamentoDTO> colEscalonamentoDTOs = regraEscalonamentoDTO.getColEscalonamentoDTOs();
        if ((colEscalonamentoDTOs!=null)&&(colEscalonamentoDTOs.size()>0)){
            for (EscalonamentoDTO escalonamentoDTO : colEscalonamentoDTOs) {
            	escalonamentoDTO.setIdRegraEscalonamento(regraEscalonamentoDTO.getIdRegraEscalonamento());
            	escalonamentoDTO.setDataInicio(UtilDatas.getDataAtual());
            	escalonamentoDAO.create(escalonamentoDTO);
    		}
        }
	}
	
}
