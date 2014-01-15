package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudServicePojo;
public interface RecursoService extends CrudServicePojo {
	public Collection findByIdGrupoRecurso(Integer parm) throws Exception;
	public void deleteByIdGrupoRecurso(Integer parm) throws Exception;
	public Collection findByHostName(String hostName) throws Exception;
}
