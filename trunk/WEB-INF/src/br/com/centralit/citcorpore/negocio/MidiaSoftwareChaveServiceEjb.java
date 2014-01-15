package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.MidiaSoftwareChaveDTO;
import br.com.centralit.citcorpore.integracao.MidiaSoftwareChaveDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class MidiaSoftwareChaveServiceEjb extends CrudServicePojoImpl implements MidiaSoftwareChaveService {
	
	private static final long serialVersionUID = -2253183314661440900L;
    
	protected CrudDAO getDao() throws ServiceException {
		return new MidiaSoftwareChaveDao();
	}
	
	private MidiaSoftwareChaveDao midiaSoftwareChaveDao() throws ServiceException {
		return (MidiaSoftwareChaveDao) getDao();
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
	public Collection<MidiaSoftwareChaveDTO> findByMidiaSoftware(Integer idMidiaSoftware) throws ServiceException, Exception {
		return this.midiaSoftwareChaveDao().findByMidiaSoftware(idMidiaSoftware);
	}

	@Override
	public void deleteByIdMidiaSoftware(Integer idMidiaSoftware) throws Exception {
		this.midiaSoftwareChaveDao().deleteByIdMidiaSoftware(idMidiaSoftware);
		
	}



}
