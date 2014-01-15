package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudServicePojo;
public interface ImportConfigCamposService extends CrudServicePojo {
	public Collection findByIdImportConfig(Integer parm) throws Exception;
	public void deleteByIdImportConfig(Integer parm) throws Exception;
}
