package br.com.centralit.citcorpore.negocio;

import java.util.List;

import br.com.centralit.citcorpore.bean.MidiaSoftwareChaveDTO;
import br.com.centralit.citcorpore.bean.MidiaSoftwareDTO;
import br.com.centralit.citcorpore.integracao.MidiaSoftwareChaveDao;
import br.com.centralit.citcorpore.integracao.MidiaSoftwareDAO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;

public class MidiaSoftwareServiceEjb extends CrudServicePojoImpl implements MidiaSoftwareService {

	@Override
	public IDto create(IDto model) throws ServiceException, LogicException {
		MidiaSoftwareDTO midiaSoftwareDTO = (MidiaSoftwareDTO) model;
		MidiaSoftwareDAO dao = (MidiaSoftwareDAO) getDao();
		
		MidiaSoftwareChaveDao midiaSoftwareChaveDao = new MidiaSoftwareChaveDao();
		
		 TransactionControler tc = new TransactionControlerImpl(dao.getAliasDB());
		
		 try {
			 midiaSoftwareChaveDao.setTransactionControler(tc);
	            tc.start();
	            midiaSoftwareDTO = (MidiaSoftwareDTO) dao.create(model);
	            
	            if(midiaSoftwareDTO.getMidiaSoftwareChaves() != null && !midiaSoftwareDTO.getMidiaSoftwareChaves().isEmpty()){
	            	for (MidiaSoftwareChaveDTO midiaSoftwareChaveDTO : midiaSoftwareDTO.getMidiaSoftwareChaves()) {
	            		midiaSoftwareChaveDTO.setIdMidiaSoftware(midiaSoftwareDTO.getIdMidiaSoftware());
	            		midiaSoftwareChaveDao.create(midiaSoftwareChaveDTO);
	               }
	            }
	            tc.commit();
	            tc.close();			 
		 }catch(Exception e) {
			 e.printStackTrace();
	            this.rollbackTransaction(tc, e);
		 }
		 
		return midiaSoftwareDTO;
	}

	@Override
	public void update(IDto model) throws ServiceException, LogicException {
		MidiaSoftwareDTO midiaSoftwareDTO = (MidiaSoftwareDTO) model;
		MidiaSoftwareDAO dao = (MidiaSoftwareDAO) getDao();
		
		MidiaSoftwareChaveDao midiaSoftwareChaveDao = new MidiaSoftwareChaveDao();
		
		 TransactionControler tc = new TransactionControlerImpl(dao.getAliasDB());
		
		 try {
			 midiaSoftwareChaveDao.setTransactionControler(tc);
	            tc.start();
	            dao.update(midiaSoftwareDTO);
	            midiaSoftwareChaveDao.deleteByIdMidiaSoftware(midiaSoftwareDTO.getIdMidiaSoftware());
	            if(midiaSoftwareDTO.getMidiaSoftwareChaves() != null && !midiaSoftwareDTO.getMidiaSoftwareChaves().isEmpty()){
	            	for (MidiaSoftwareChaveDTO midiaSoftwareChaveDTO : midiaSoftwareDTO.getMidiaSoftwareChaves()) {
	            		midiaSoftwareChaveDTO.setIdMidiaSoftware(midiaSoftwareDTO.getIdMidiaSoftware());
	            		midiaSoftwareChaveDao.create(midiaSoftwareChaveDTO);
	               }
	            }
	            tc.commit();
	            tc.close();			 
		 }catch(Exception e) {
			e.printStackTrace();
            this.rollbackTransaction(tc, e);
		 }	 
	}
	
	 public IDto restore(IDto model) throws ServiceException, LogicException  {
		 MidiaSoftwareDTO midiaSoftwareDTO = null;
		 MidiaSoftwareDAO dao = (MidiaSoftwareDAO) getDao();
         try{
        	 midiaSoftwareDTO = (MidiaSoftwareDTO)dao.restore(model);
             
        	 MidiaSoftwareChaveDTO midiaSoftwareChaveDTO = new MidiaSoftwareChaveDTO();

        	 midiaSoftwareChaveDTO.setIdMidiaSoftware(midiaSoftwareDTO.getIdMidiaSoftware());
        	 midiaSoftwareDTO.setMidiaSoftwareChaves((List<MidiaSoftwareChaveDTO>) new MidiaSoftwareChaveDao().findByMidiaSoftware(midiaSoftwareDTO.getIdMidiaSoftware()));
             
         }catch(Exception e){
             e.printStackTrace();
             throw new ServiceException(e);
         }
         return midiaSoftwareDTO;
     }
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6590577395956465122L;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new MidiaSoftwareDAO();
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
	public boolean consultarMidiasAtivas(MidiaSoftwareDTO midiaSoftware) throws Exception {
		MidiaSoftwareDAO dao = new MidiaSoftwareDAO();
		return dao.consultarMidiasAtivas(midiaSoftware);
	}

}
