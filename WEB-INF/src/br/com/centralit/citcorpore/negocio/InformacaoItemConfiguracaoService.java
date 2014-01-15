package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.InformacaoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.citframework.service.CrudServiceEjb2;

/**
 * @author rosana.godinho
 *
 */
public interface InformacaoItemConfiguracaoService extends CrudServiceEjb2 {

    public InformacaoItemConfiguracaoDTO listByInformacao(ItemConfiguracaoDTO itemConfiguracao) throws Exception ;
}
