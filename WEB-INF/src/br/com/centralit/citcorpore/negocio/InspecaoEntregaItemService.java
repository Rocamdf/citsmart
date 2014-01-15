package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudServiceEjb2;
public interface InspecaoEntregaItemService extends CrudServiceEjb2 {
	public Collection findByIdEntrega(Integer parm) throws Exception;
	public void deleteByIdEntrega(Integer parm) throws Exception;
}
