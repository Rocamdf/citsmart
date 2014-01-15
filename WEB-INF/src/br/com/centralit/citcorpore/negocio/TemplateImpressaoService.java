package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudServicePojo;
public interface TemplateImpressaoService extends CrudServicePojo {
	public Collection findByIdTipoTemplateImp(Integer parm) throws Exception;
	public void deleteByIdTipoTemplateImp(Integer parm) throws Exception;
}
