package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.LocalidadeItemConfiguracaoDTO;
import br.com.citframework.service.CrudServiceEjb2;

public interface LocalidadeItemConfiguracaoService extends CrudServiceEjb2 {
	
	public LocalidadeItemConfiguracaoDTO listByIdRegiao(LocalidadeItemConfiguracaoDTO obj) throws Exception;
	public LocalidadeItemConfiguracaoDTO listByIdUf(LocalidadeItemConfiguracaoDTO obj) throws Exception;
	

}
