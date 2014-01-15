/**
 * 
 */
package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;

import br.com.centralit.citcorpore.bean.GraficoPizzaDTO;
import br.com.centralit.citcorpore.bean.GraficosDTO;
import br.com.citframework.service.CrudServicePojo;

/**
 * @author breno.guimaraes
 *
 */
public interface GraficosService extends CrudServicePojo{
    ArrayList<GraficoPizzaDTO> getRelatorioPorNomeCategoria();
    ArrayList<GraficoPizzaDTO> getRelatorioPorSituacao();
    public ArrayList<GraficoPizzaDTO> getRelatorioPorGrupo();
}
