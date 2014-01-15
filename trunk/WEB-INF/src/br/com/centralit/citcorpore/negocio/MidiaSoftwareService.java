package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.MidiaSoftwareDTO;
import br.com.citframework.service.CrudServiceEjb2;

public interface MidiaSoftwareService extends CrudServiceEjb2 {

	public boolean consultarMidiasAtivas(MidiaSoftwareDTO midiaSoftware) throws Exception;

}
