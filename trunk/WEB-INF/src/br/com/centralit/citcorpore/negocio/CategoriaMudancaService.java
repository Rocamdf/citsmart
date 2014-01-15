package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import br.com.citframework.service.CrudServicePojo;

@SuppressWarnings("rawtypes")
public interface CategoriaMudancaService extends CrudServicePojo {
	public Collection findByIdCategoriaMudanca(Integer parm) throws Exception;

	public void deleteByIdCategoriaMudanca(Integer parm) throws Exception;

	public Collection findByIdCategoriaMudancaPai(Integer parm) throws Exception;

	public void deleteByIdCategoriaMudancaPai(Integer parm) throws Exception;

	public Collection findByNomeCategoria(Integer parm) throws Exception;

	public void deleteByNomeCategoria(Integer parm) throws Exception;

	public Collection listHierarquia() throws Exception;

	public Collection findCategoriaAtivos();
}
