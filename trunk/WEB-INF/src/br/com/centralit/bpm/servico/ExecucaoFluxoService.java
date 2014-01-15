package br.com.centralit.bpm.servico;
import java.util.List;

import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.citframework.service.CrudServiceEjb2;
public interface ExecucaoFluxoService extends CrudServiceEjb2 {
	public List<TarefaFluxoDTO> recuperaTarefas(String loginUsuario) throws Exception;
	public TarefaFluxoDTO recuperaTarefa(String loginUsuario, Integer idTarefa) throws Exception;
	public void delegaTarefa(String loginUsuario, Integer idTarefa, String usuarioDestino, String grupoDestino) throws Exception;
}
