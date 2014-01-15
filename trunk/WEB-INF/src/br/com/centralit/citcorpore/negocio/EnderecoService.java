package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.EnderecoDTO;
import br.com.citframework.service.CrudServiceEjb2;
public interface EnderecoService extends CrudServiceEjb2 {
    public Collection<EnderecoDTO> recuperaEnderecosEntregaProduto() throws Exception;
    public EnderecoDTO recuperaEnderecoCompleto(EnderecoDTO endereco) throws Exception;
    public EnderecoDTO recuperaEnderecoComUnidade(Integer idEndereco) throws Exception;
}
