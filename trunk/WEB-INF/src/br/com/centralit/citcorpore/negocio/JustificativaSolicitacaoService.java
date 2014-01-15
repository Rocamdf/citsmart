package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.JustificativaSolicitacaoDTO;
import br.com.citframework.service.CrudServicePojo;

/**
 * @author breno.guimaraes
 *
 */
@SuppressWarnings("rawtypes")
public interface JustificativaSolicitacaoService extends CrudServicePojo {
    public Collection<JustificativaSolicitacaoDTO> listAtivasParaSuspensao() throws Exception;
    public Collection<JustificativaSolicitacaoDTO> listAtivasParaAprovacao() throws Exception;
    
    /**
     * Retorna uma lista de justificativas ativas para requisicao viagem
     * @return
     * @throws Exception
     * @author thays.araujo
     */
	public Collection listAtivasParaViagem() throws Exception;
}
