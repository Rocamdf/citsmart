package br.com.centralit.citcorpore.negocio;

import br.com.citframework.service.CrudServiceEjb2;

public interface InventarioXMLService  extends CrudServiceEjb2 {
	public boolean inventarioAtualizado (String ip, java.util.Date dataExpiracao) throws Exception;
}