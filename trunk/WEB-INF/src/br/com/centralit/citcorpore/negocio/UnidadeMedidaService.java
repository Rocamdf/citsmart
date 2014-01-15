package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.UnidadeMedidaDTO;
import br.com.citframework.service.CrudServiceEjb2;

public interface UnidadeMedidaService extends CrudServiceEjb2 {

	public boolean consultarUnidadesMedidas(UnidadeMedidaDTO unidadeMedidaDTO) throws Exception;
}
