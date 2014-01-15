/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.AnexoIncidenteDTO;
import br.com.centralit.citcorpore.bean.AnexoMudancaDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.citframework.service.CrudServiceEjb2;

/**
 * 
 * @author breno.guimaraes
 */
public interface AnexoMudancaService extends CrudServiceEjb2 {
    void create(Collection<UploadDTO> arquivosUpados, Integer idMudanca) throws Exception;
    Collection<AnexoMudancaDTO> consultarAnexosMudanca(Integer idMudanca)  throws Exception;
}
