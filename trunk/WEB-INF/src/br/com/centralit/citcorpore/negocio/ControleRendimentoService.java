package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ControleRendimentoDTO;
import br.com.citframework.service.CrudServiceEjb2;

public interface ControleRendimentoService extends CrudServiceEjb2{

	public Collection<ControleRendimentoDTO> findByMesAno(String mes, String ano, Integer idGrupo) throws Exception;
	public Collection<ControleRendimentoDTO> findPontuacaoRendimento(String mes, String ano, Integer idGrupo) throws Exception;
	
}
