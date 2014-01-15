package br.com.centralit.bpm.servico;
import java.util.Collection;

import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.citframework.service.CrudServiceEjb2;
public interface FluxoService extends CrudServiceEjb2 {
	public Collection listAll() throws Exception;
	public FluxoDTO findByTipoFluxo(Integer idTipoFluxo) throws Exception;
	public FluxoDTO criaEstrutura(FluxoDTO fluxoDto) throws Exception;
	public FluxoDTO criaFluxoEEstrutura(FluxoDTO fluxoDto) throws Exception;
}
