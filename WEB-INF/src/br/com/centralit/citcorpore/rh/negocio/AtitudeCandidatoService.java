package br.com.centralit.citcorpore.rh.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudServiceEjb2;
public interface AtitudeCandidatoService extends CrudServiceEjb2 {
	public Collection findByIdEntrevista(Integer parm) throws Exception;
	public void deleteByIdEntrevista(Integer parm) throws Exception;
}
