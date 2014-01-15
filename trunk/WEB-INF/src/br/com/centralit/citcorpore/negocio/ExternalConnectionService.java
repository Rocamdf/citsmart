package br.com.centralit.citcorpore.negocio;
import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.ImportManagerDTO;
import br.com.citframework.service.CrudServicePojo;
public interface ExternalConnectionService extends CrudServicePojo {
	public Collection getTables(Integer idExternalConnection) throws Exception;
	public Collection getLocalTables() throws Exception;
	public Collection getFieldsTable(Integer idExternalConnection, String tableName) throws Exception;
	public Collection getFieldsLocalTable(String tableName) throws Exception;
	
	public void processImport(ImportManagerDTO importManagerDTO, ArrayList colMatrizTratada) throws Exception;
}
