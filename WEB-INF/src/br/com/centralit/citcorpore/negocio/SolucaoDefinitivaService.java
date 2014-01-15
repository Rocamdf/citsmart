package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.SolucaoDefinitivaDTO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServiceEjb2;

public interface SolucaoDefinitivaService extends CrudServiceEjb2{
	public SolucaoDefinitivaDTO findSolucaoDefinitiva(SolucaoDefinitivaDTO solucaoDefinitiva) throws Exception;
	
	public SolucaoDefinitivaDTO create(SolucaoDefinitivaDTO solucaoDefinitiva, TransactionControler tc) throws Exception;
	
	public SolucaoDefinitivaDTO findByIdProblema(SolucaoDefinitivaDTO solucaoDefinitiva) throws Exception;
}
