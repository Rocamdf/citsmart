package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.GrupoEmailDTO;
import br.com.citframework.service.CrudServiceEjb2;

public interface GrupoEmailService extends CrudServiceEjb2 {

	Collection<GrupoEmailDTO> findByIdGrupo(Integer idGrupo) throws Exception;

}
