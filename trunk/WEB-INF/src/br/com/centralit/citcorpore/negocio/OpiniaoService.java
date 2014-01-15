package br.com.centralit.citcorpore.negocio;

import java.sql.Date;
import java.util.Collection;

import br.com.citframework.service.CrudServiceEjb2;

public interface OpiniaoService extends CrudServiceEjb2 {
	public Collection findByTipoAndPeriodo(String tipo, Integer idContrato, Date dataInicial, Date dataFinal) throws Exception;
}
