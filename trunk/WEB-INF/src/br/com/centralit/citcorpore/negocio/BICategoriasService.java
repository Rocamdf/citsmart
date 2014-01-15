package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudServicePojo;
public interface BICategoriasService extends CrudServicePojo {
	public Collection findByIdCategoriaPai(Integer parm) throws Exception;
	public Collection findSemPai() throws Exception;	
}
