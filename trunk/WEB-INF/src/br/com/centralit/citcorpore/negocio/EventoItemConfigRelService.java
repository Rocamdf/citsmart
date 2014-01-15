package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.EventoItemConfigRelDTO;
import br.com.citframework.service.CrudServiceEjb2;

public interface EventoItemConfigRelService extends CrudServiceEjb2 {

	public Collection<EventoItemConfigRelDTO> listByEvento(Integer idEvento) throws Exception;
	
}
