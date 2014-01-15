package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.EventoGrupoDTO;
import br.com.citframework.service.CrudServiceEjb2;

public interface EventoGrupoService extends CrudServiceEjb2 {
	
	Collection<EventoGrupoDTO> listByEvento(Integer idEvento) throws Exception;	
}
