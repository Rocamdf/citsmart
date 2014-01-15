package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudServiceEjb2;

public interface ProjetoService extends CrudServiceEjb2 {
    public Collection findByIdCliente(Integer parm) throws Exception;
    public Collection listHierarquia(Integer idCliente, boolean acrescentarInativos) throws Exception;

}
