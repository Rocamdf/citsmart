package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudServicePojo;

public interface GaleriaImagensService extends CrudServicePojo {
	public Collection findByCategoria(Integer idCategoria) throws Exception;
	public Collection listOrderByCategoria() throws Exception;
}