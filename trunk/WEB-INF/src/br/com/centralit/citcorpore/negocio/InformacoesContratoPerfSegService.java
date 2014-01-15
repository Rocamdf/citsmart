package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudServicePojo;

public interface InformacoesContratoPerfSegService extends CrudServicePojo {
	public Collection findByIdProntuarioEletronicoConfig(Integer idProntuarioEletronicoConfig) throws Exception;
}
