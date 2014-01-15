package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudServiceEjb2;

/**
 * @author ygor.magalhaes
 *
 */
public interface HistoricoAcaoCurriculoService extends CrudServiceEjb2 {
	 public Collection listByIdCurriculo(Integer idCurriculo) throws Exception;

}
