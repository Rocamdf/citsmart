package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.Collection;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ImpactoDTO;
import br.com.centralit.citcorpore.bean.MatrizPrioridadeDTO;
import br.com.centralit.citcorpore.bean.UrgenciaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceEjb2;

/**
 * 
 * @author rodrigo.oliveira
 *
 */
@SuppressWarnings("rawtypes")
public interface PrioridadeSolicitacoesService extends CrudServiceEjb2 {
	
	public Collection findById(Integer idMatrizPrioridade) throws Exception;
	
	public Collection list() throws LogicException, RemoteException, ServiceException;
	
	public void createImpacto(IDto impacto) throws Exception ;
	
	public void deleteImpacto() throws Exception;
	
	public void createUrgencia(IDto urgencia) throws Exception;
	
	public void deleteUrgencia() throws Exception;
	
	public void createMatrizPrioridade(IDto matrizPrioridade) throws Exception;
	
	public void deleteMatrizPrioridade() throws Exception;
	
	public void restaurarGridMatrizPrioridade(DocumentHTML document, Collection<MatrizPrioridadeDTO> matrizPrioridade);
	
	public Integer consultaValorPrioridade(Integer idImpacto, Integer idUrgencia);
	
	public boolean consultaCadastros() throws Exception;
	
	public Collection consultaImpacto() throws Exception;
	
	public Collection consultaUrgencia() throws Exception;
	
	public Collection consultaMatrizPrioridade() throws Exception;
	
	public IDto restoreImpacto(IDto impacto) throws Exception;
	
	public IDto restoreImpactoBySigla(ImpactoDTO impacto) throws Exception;
	
	public IDto restoreUrgencia(IDto urgencia) throws Exception;
	
	public IDto restoreUrgenciaBySigla(UrgenciaDTO impacto) throws Exception;
	
	public boolean verificaImpactoJaExiste(ImpactoDTO impacto) throws Exception;	
	
	public boolean verificaUrgenciaJaExiste(UrgenciaDTO urgencia) throws Exception;
	
}
