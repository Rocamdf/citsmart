package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudServicePojo;
public interface CategoriaSolucaoService extends CrudServicePojo {
	public Collection findByIdCategoriaSolucaoPai(Integer parm) throws Exception;
	public void deleteByIdCategoriaSolucaoPai(Integer parm) throws Exception;
	public Collection listHierarquia() throws Exception;
	public Collection getCollectionHierarquia(Integer idCateg, Integer nivel) throws Exception;
}
