package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ImpactoDTO;
import br.com.centralit.citcorpore.bean.MatrizPrioridadeDTO;
import br.com.centralit.citcorpore.bean.UrgenciaDTO;
import br.com.centralit.citcorpore.integracao.ImpactoDAO;
import br.com.centralit.citcorpore.integracao.MatrizPrioridadeDAO;
import br.com.centralit.citcorpore.integracao.UrgenciaDAO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

/**
 * Classe que encapsula os serviços referente ao cadastro de Prioridade 
 * 
 * @author rodrigo.oliveira
 *
 */
@SuppressWarnings("rawtypes")
public class PrioridadeSolicitacoesServiceEjb extends CrudServicePojoImpl implements PrioridadeSolicitacoesService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1869867090119163288L;
	
	private ImpactoDAO impactoDAO;
	private UrgenciaDAO urgenciaDAO;
	private MatrizPrioridadeDAO matrizPrioridadeDAO;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return null;
	}

	@Override
	protected void validaCreate(Object obj) throws Exception {

	}

	@Override
	protected void validaUpdate(Object obj) throws Exception {

	}

	@Override
	protected void validaDelete(Object obj) throws Exception {

	}

	@Override
	protected void validaFind(Object obj) throws Exception {

	}
	
	@Override
	public Collection findById(Integer idMatrizPrioridade) throws Exception {
		return null;
	}

	@Override
	public void restaurarGridMatrizPrioridade(DocumentHTML document, Collection<MatrizPrioridadeDTO> matrizPrioridade) {

	}

	@Override
	public Integer consultaValorPrioridade(Integer idImpacto, Integer idUrgencia) {
		return null;
	}

	@Override
	public boolean consultaCadastros() throws Exception {
		boolean flag = false;
				
		Collection listImpacto = getImpactoDAO().list();
		if(!listImpacto.isEmpty()){
			Collection listUrgencia = getUrgenciaDAO().list();
			if(!listUrgencia.isEmpty()){
				flag = true;
			}
		}
		return flag;
	}
	
	@Override
	public IDto restoreImpactoBySigla(ImpactoDTO impacto) throws Exception {
		ImpactoDTO impactoResp = new ImpactoDTO();
		List<ImpactoDTO> resp = getImpactoDAO().restoreBySigla(impacto.getSiglaImpacto().trim().toString().toUpperCase());
		if(resp == null){
			return null;
		}
		for (ImpactoDTO imp : resp) {
			impactoResp = imp;
		}
		return impactoResp;
	}

	@Override
	public IDto restoreUrgenciaBySigla(UrgenciaDTO urgencia) throws Exception {
		UrgenciaDTO urgenciaResp = new UrgenciaDTO();
		List<UrgenciaDTO> resp = getUrgenciaDAO().restoreBySigla(urgencia.getSiglaUrgencia().trim().toString().toUpperCase()); 
		if(resp == null){
			return null;
		}
		for (UrgenciaDTO urg : resp) {
			urgenciaResp = urg;
		}
		return urgenciaResp;
	}

	@Override
	public void createImpacto(IDto impacto) throws Exception {
		getImpactoDAO().create(impacto);
	}
	
	@Override
	public void deleteImpacto() throws Exception {
		getImpactoDAO().deleteImpacto();
	}
	
	@Override
	public void createUrgencia(IDto urgencia) throws Exception {
		getUrgenciaDAO().create(urgencia);
	}
	
	@Override
	public void deleteUrgencia() throws Exception {
		getUrgenciaDAO().deleteUrgencia();
	}

	@Override
	public void createMatrizPrioridade(IDto matrizPrioridade) throws Exception {
		getMatrizPrioridadeDAO().create(matrizPrioridade);		
	}
	
	@Override
	public void deleteMatrizPrioridade() throws Exception {
		getMatrizPrioridadeDAO().deleteMatriz();
	}
	
	@Override
	public Collection consultaImpacto() throws Exception {
		return getImpactoDAO().list();
	}

	@Override
	public Collection consultaUrgencia() throws Exception {
		return getUrgenciaDAO().list();
	}

	@Override
	public Collection consultaMatrizPrioridade() throws Exception {
		return getMatrizPrioridadeDAO().list();
	}

	@Override
	public IDto restoreImpacto(IDto impacto) throws Exception {
		return getImpactoDAO().restore(impacto);
	}

	@Override
	public IDto restoreUrgencia(IDto urgencia) throws Exception {
		return getUrgenciaDAO().restore(urgencia);
	}

	/**
	 * @return the impactoDAO
	 */
	public ImpactoDAO getImpactoDAO() {
		if(impactoDAO == null){
			impactoDAO = new ImpactoDAO();
		}
		return impactoDAO;
	}

	/**
	 * @return the urgenciaDAO
	 */
	public UrgenciaDAO getUrgenciaDAO() {
		if(urgenciaDAO == null){
			urgenciaDAO = new UrgenciaDAO();
		}
		return urgenciaDAO;
	}

	/**
	 * @return the matrizPrioridadeDAO
	 */
	public MatrizPrioridadeDAO getMatrizPrioridadeDAO() {
		if(matrizPrioridadeDAO == null){
			matrizPrioridadeDAO = new MatrizPrioridadeDAO();
		}
		return matrizPrioridadeDAO;
	}

	@Override
	public boolean verificaImpactoJaExiste(ImpactoDTO impacto) throws Exception {
		List<ImpactoDTO> lista = getImpactoDAO().restoreByNivel(impacto.getNivelImpacto());
		if(lista != null){
			return true;
		}
		return false;
	}
	
	@Override
	public boolean verificaUrgenciaJaExiste(UrgenciaDTO urgencia) throws Exception {
		List<UrgenciaDTO> lista = getUrgenciaDAO().restoreByNivel(urgencia.getNivelUrgencia());
		if(lista != null){
			return true;
		}
		return false;
	}

	

}
