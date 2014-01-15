/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;

import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.citframework.service.CrudServiceEjb2;

/**
 * 
 * @author breno.guimaraes
 */
@Deprecated
public interface IncidentesRelacionadosService extends CrudServiceEjb2 {
    ArrayList<SolicitacaoServicoDTO> listIncidentesRelacionados(int idSolicitacao);
}
