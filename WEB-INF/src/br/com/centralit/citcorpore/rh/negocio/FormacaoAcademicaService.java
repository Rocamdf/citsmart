package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudServiceEjb2;


public interface FormacaoAcademicaService extends CrudServiceEjb2 {
	
	public Collection findByNome(String nome) throws Exception;
    
}