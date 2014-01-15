package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudServiceEjb2;

public interface ReuniaoRequisicaoMudancaService extends CrudServiceEjb2{
	
	public Collection findByIdRequisicaoMudanca(Integer parm) throws Exception ;

}
