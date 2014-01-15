package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudServicePojo;

/**
 * @author breno.guimaraes
 *
 */
public interface OcorrenciaMudancaService extends CrudServicePojo {
    public Collection findByIdRequisicaoMudanca(Integer idRequisicaoMudanca) throws Exception;
}
