package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudServiceEjb2;
public interface TempoAcordoNivelServicoService extends CrudServiceEjb2 {
	public Collection findByIdAcordoAndIdPrioridade(Integer idAcordoNivelServico, Integer idPrioridade) throws Exception;
	public Collection findByIdAcordoAndFaseAndIdPrioridade(Integer idAcordoNivelServico, Integer idFase, Integer idPrioridade) throws Exception;
}
