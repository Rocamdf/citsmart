package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.AlcadaDTO;
import br.com.centralit.citcorpore.bean.ProcessamentoBatchDTO;
import br.com.citframework.service.CrudServicePojo;

public interface ProcessamentoBatchService extends CrudServicePojo {
	public Collection getAtivos() throws Exception;
	public boolean existeDuplicidade(ProcessamentoBatchDTO processamentoBatch) throws Exception;
	public boolean existeDuplicidadeClasse(ProcessamentoBatchDTO processamentoBatch) throws Exception;
}
