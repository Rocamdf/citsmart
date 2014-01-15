package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.HistoricoValorDTO;
import br.com.citframework.service.CrudServiceEjb2;

public interface HistoricoValorService extends CrudServiceEjb2 {
	public List<HistoricoValorDTO> listHistoricoValorByIdHistoricoIc(Integer idHistoricoIc) throws Exception;
	public Collection findByIdHitoricoIC(Integer idHistoricoIc) throws Exception;
}
