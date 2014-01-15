package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudServicePojo;
public interface FaturaOSService extends CrudServicePojo {
	public Collection findByIdFatura(Integer parm) throws Exception;
	public void deleteByIdFatura(Integer parm) throws Exception;
	public Collection findByIdOs(Integer parm) throws Exception;
	public void deleteByIdOs(Integer parm) throws Exception;
}
