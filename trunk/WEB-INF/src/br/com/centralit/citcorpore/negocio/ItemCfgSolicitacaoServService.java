package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudServicePojo;


@SuppressWarnings("rawtypes")
public interface ItemCfgSolicitacaoServService extends CrudServicePojo {
	
	public Collection findByIdItemConfiguracao(Integer parm) throws Exception;

	public void deleteByIdItemConfiguracao(Integer parm) throws Exception;
	
	public Collection findByIdSolicitacaoServico(Integer parm) throws Exception;
}
