package br.com.centralit.citcorpore.negocio;

import java.io.File;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citcorpore.bean.DicionarioDTO;
import br.com.citframework.service.CrudServiceEjb2;

public interface DicionarioService extends CrudServiceEjb2 {

	public void criarMensagensNovos(HttpServletRequest request, String locale, Integer idLingua) throws Exception;
	
	public Collection<DicionarioDTO> listaDicionario(DicionarioDTO dicionarioDto) throws Exception;

	public void gerarCarga(File file) throws Exception;
	
	public DicionarioDTO verificarDicionarioAtivoByKey(DicionarioDTO obj) throws Exception;

}
