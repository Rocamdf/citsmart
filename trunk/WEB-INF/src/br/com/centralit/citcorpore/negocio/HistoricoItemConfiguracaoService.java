/**
 * 
 */
package br.com.centralit.citcorpore.negocio;

import java.util.List;

import br.com.centralit.citcorpore.bean.HistoricoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.service.CrudServiceEjb2;

public interface HistoricoItemConfiguracaoService extends CrudServiceEjb2 {
	public List<HistoricoItemConfiguracaoDTO> listHistoricoItemByIditemconfiguracao(Integer idItemconfiguracao) throws Exception;
	public List<HistoricoItemConfiguracaoDTO> listHistoricoItemCfValorByIdHistoricoIC(Integer idHistoricoIC) throws Exception;
	public void updateNotNull(IDto obj) throws Exception ;
	public HistoricoItemConfiguracaoDTO maxIdHistorico(ItemConfiguracaoDTO itemConfiguracao) throws Exception;
	
}