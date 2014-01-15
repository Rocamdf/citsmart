package br.com.centralit.citcorpore.metainfo.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudServicePojo;
public interface ScriptsVisaoService extends CrudServicePojo {
	public Collection findByIdVisao(Integer parm) throws Exception;
	public void deleteByIdVisao(Integer parm) throws Exception;
}
