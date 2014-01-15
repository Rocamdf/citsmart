package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.RegraEscalonamentoDTO;
import br.com.citframework.service.CrudServiceEjb2;

public interface EscalonamentoService extends CrudServiceEjb2 {

	Collection findByRegraEscalonamento(RegraEscalonamentoDTO regraEscalonamentoDTO);
	
}
