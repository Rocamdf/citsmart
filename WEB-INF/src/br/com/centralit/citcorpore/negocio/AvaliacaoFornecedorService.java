package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudServicePojo;

public interface AvaliacaoFornecedorService extends CrudServicePojo {
    public Collection findByIdFornecedor(Integer parm) throws Exception;
    public boolean fornecedorQualificado(Integer idFornecedor) throws Exception;
}
