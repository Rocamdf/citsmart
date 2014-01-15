package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ModeloEmailDTO;
import br.com.citframework.service.CrudServicePojo;

@SuppressWarnings({"rawtypes"})
public interface ModeloEmailService extends CrudServicePojo {
	public Collection getAtivos() throws Exception;	
	public ModeloEmailDTO findByIdentificador(String identificador) throws Exception;
}
