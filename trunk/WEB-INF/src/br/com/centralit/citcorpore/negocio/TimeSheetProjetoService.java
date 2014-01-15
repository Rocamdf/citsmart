package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudServicePojo;
public interface TimeSheetProjetoService extends CrudServicePojo {
	public Collection findByIdRecursoTarefaLinBaseProj(Integer idRecursoTarefaLinBaseProj, Integer idEmpregado) throws Exception;
	public void deleteByIdRecursoTarefaLinBaseProj(Integer parm) throws Exception;
}
