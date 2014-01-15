package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bean.VersaoDTO;
import br.com.citframework.service.CrudServiceEjb2;

public interface VersaoService extends CrudServiceEjb2 {

	public VersaoDTO buscaVersaoPorNome(String nome) throws Exception;

	public void validaVersoes(UsuarioDTO usuario) throws Exception;

	public boolean haVersoesSemValidacao() throws Exception;

	public VersaoDTO versaoASerValidada() throws Exception;

	public Collection<VersaoDTO> versoesComErrosScripts() throws Exception;

}
