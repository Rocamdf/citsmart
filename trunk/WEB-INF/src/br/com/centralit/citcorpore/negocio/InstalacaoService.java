package br.com.centralit.citcorpore.negocio;

import br.com.citframework.service.CrudServicePojo;

public interface InstalacaoService extends CrudServicePojo {
	
	public boolean isSucessoInstalacao() throws Exception;  
	
}
