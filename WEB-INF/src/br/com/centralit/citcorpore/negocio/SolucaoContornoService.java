package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.SolucaoContornoDTO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServiceEjb2;

public interface SolucaoContornoService extends CrudServiceEjb2{
	
	public SolucaoContornoDTO findSolucaoContorno(SolucaoContornoDTO solucaoContorno) throws Exception;
	
	public SolucaoContornoDTO create(SolucaoContornoDTO solucaoContornoDto, TransactionControler tc) throws Exception;
	
	public SolucaoContornoDTO findByIdProblema(SolucaoContornoDTO solucaoContorno) throws Exception;

}
