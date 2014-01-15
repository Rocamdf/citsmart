package br.com.centralit.citcorpore.integracao;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.LinguaDTO;
import br.com.citframework.service.CrudServiceEjb2;

public interface LinguaService extends CrudServiceEjb2 {

	public boolean consultarLinguaAtivas(LinguaDTO obj) throws Exception;
	
	public LinguaDTO getIdLingua(LinguaDTO obj) throws Exception;
	
	public Collection<LinguaDTO> listarAtivos() throws Exception;

}
