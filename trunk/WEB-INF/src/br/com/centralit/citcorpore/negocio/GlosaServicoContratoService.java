/**
 * 
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudServicePojo;

/**
 * @author rodrigo.oliveira
 *
 */
public interface GlosaServicoContratoService extends CrudServicePojo {
	
	public Collection findByIdServicoContrato(Integer parm) throws Exception;
	
	public Integer quantidadeGlosaServico(Integer idServicoContrato) throws Exception;
	
	public void atualizaQuantidadeGlosa(Integer novaQuantidade, Integer idServicoContrato) throws Exception;
	
	
}
