/**
 * 
 */
package br.com.centralit.citcorpore.negocio;

import java.util.List;

import br.com.centralit.citcorpore.bean.HistoricoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.HistoricoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.service.CrudServiceEjb2;

public interface HistoricoLiberacaoService extends CrudServiceEjb2 {
	public List<HistoricoLiberacaoDTO> listHistoricoLiberacaoByIdRequisicaoLiberacao(Integer idRequisicaoLiberacao) throws Exception;
	/*public List<HistoricoItemConfiguracaoDTO> listHistoricoItemCfValorByIdHistoricoIC(Integer idHistoricoIC) throws Exception;*/
	public void updateNotNull(IDto obj) throws Exception ;
	public HistoricoLiberacaoDTO maxIdHistorico(RequisicaoLiberacaoDTO requisicaoLiberacaoDTO) throws Exception;
	
}