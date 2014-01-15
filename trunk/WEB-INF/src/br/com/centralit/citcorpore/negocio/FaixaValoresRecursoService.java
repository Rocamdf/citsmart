package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudServicePojo;
public interface FaixaValoresRecursoService extends CrudServicePojo {
	public Collection findByIdRecurso(Integer parm) throws Exception;
	public void deleteByIdRecurso(Integer parm) throws Exception;
}
