package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.TipoDemandaServicoDTO;
import br.com.citframework.service.CrudServiceEjb2;

public interface TipoDemandaServicoService extends CrudServiceEjb2 {
	public Collection<TipoDemandaServicoDTO> listSolicitacoes() throws Exception;
}
