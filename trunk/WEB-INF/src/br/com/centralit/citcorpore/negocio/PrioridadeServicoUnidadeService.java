package br.com.centralit.citcorpore.negocio;
import br.com.centralit.citcorpore.bean.PrioridadeServicoUnidadeDTO;
import br.com.citframework.service.CrudServiceEjb2;
public interface PrioridadeServicoUnidadeService extends CrudServiceEjb2 {
    public PrioridadeServicoUnidadeDTO restore(Integer idServicoContrato, Integer idUnidade) throws Exception;
}
