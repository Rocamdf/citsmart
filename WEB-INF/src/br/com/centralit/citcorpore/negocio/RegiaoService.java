package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.RegiaoDTO;
import br.com.citframework.service.CrudServiceEjb2;

public interface RegiaoService  extends CrudServiceEjb2{
	
	public  RegiaoDTO listByIdRegiao(RegiaoDTO obj) throws Exception;

}
