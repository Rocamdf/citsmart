/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.IncidentesRelacionadosDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.integracao.IncidentesRelacionadosDAO;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

/**
 * @author breno.guimaraes
 */
@Deprecated
public class IncidentesRelacionadosServiceEjb extends CrudServicePojoImpl implements IncidentesRelacionadosService{

    private IncidentesRelacionadosDAO incidentesRelacionadosDao;
    private SolicitacaoServicoServiceEjb solicitacaoServicoServiceEjb;
    
    @Override
    protected CrudDAO getDao() throws ServiceException {
	if(incidentesRelacionadosDao == null){
	    incidentesRelacionadosDao = new IncidentesRelacionadosDAO();
	}
	return incidentesRelacionadosDao;
    }
    
    private IncidentesRelacionadosDAO getIncidentesRelacionadosDao(){
	try {
	    return ((IncidentesRelacionadosDAO) getDao());
	} catch (ServiceException e) {
	    e.printStackTrace();
	}
	return null;
    }
    
    private SolicitacaoServicoServiceEjb getSolicitacaoServicoEjb(){
	if(solicitacaoServicoServiceEjb == null){
	    solicitacaoServicoServiceEjb = new SolicitacaoServicoServiceEjb();
	}
	
	return solicitacaoServicoServiceEjb;
    }
    
    public ArrayList<SolicitacaoServicoDTO> listIncidentesRelacionados(int idSolicitacao){
	ArrayList<Condition> condicoes = new ArrayList<Condition>();
	condicoes.add(new Condition("idIncidente","=", idSolicitacao));
	SolicitacaoServicoDTO solicitacao = new SolicitacaoServicoDTO();
	
	ArrayList<SolicitacaoServicoDTO> retorno = new	ArrayList<SolicitacaoServicoDTO>(); 
	
	try {
	    //pega lista de ids dos incidentes relacionados ao passado como argumento. 
	    Collection<IncidentesRelacionadosDTO> incidentesRelacionados = getIncidentesRelacionadosDao().findByCondition(condicoes, null);
	    //preenche uma lista com os incidentes relacionados buscando pelos ids obtidos.
	    if(incidentesRelacionados != null){
        	    for(IncidentesRelacionadosDTO inc : incidentesRelacionados){
//        	    	solicitacao.setIdSolicitacaoServico(inc.getIdIncidenteRelacionado());
        	    	retorno.add((SolicitacaoServicoDTO) getSolicitacaoServicoEjb().restore(solicitacao));
        	    }
	    }
	
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return retorno;
    }
    
    @Override
    public IDto create(IDto dto) throws ServiceException, LogicException {
	IncidentesRelacionadosDTO icRelacionadoDto = (IncidentesRelacionadosDTO) dto; 
        return super.create(icRelacionadoDto);
    }

    
    
    @Override
    protected void validaCreate(Object arg0) throws Exception {
	
    }

    @Override
    protected void validaDelete(Object arg0) throws Exception {
	
	
    }

    @Override
    protected void validaFind(Object arg0) throws Exception {
	
    }
    
    @Override
    protected void validaUpdate(Object arg0) throws Exception {
	
    }
}
