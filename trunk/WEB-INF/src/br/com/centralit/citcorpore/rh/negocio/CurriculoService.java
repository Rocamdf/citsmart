package br.com.centralit.citcorpore.rh.negocio;

import br.com.citframework.service.CrudServicePojo;

public interface CurriculoService extends CrudServicePojo {
	public String retornarCaminhoFoto(Integer idCurriculo) throws Exception;
}
