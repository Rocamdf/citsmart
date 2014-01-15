package br.com.centralit.citcorpore.negocio;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ScriptsDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceEjb2;

public interface ScriptsService extends CrudServiceEjb2 {

	public ScriptsDTO buscaScriptPorId(Integer id) throws Exception;

	public void deletarScript(IDto model, DocumentHTML document) throws Exception;

	public String executaRotinaScripts() throws Exception;

	public List<String[]> executarScriptConsulta(ScriptsDTO script) throws Exception;

	public String executarScriptUpdate(ScriptsDTO script) throws Exception;

	public boolean haScriptDeVersaoComErro() throws Exception;

	/**
	 * Verifica se já existe algum ScriptDTO com o mesmo NOME
	 * 
	 * @return Retorna se já existe algum ScriptDTO com o mesmo NOME
	 * @author Murilo Gabriel
	 */
	public boolean temScriptsAtivos(ScriptsDTO script) throws Exception;

	public ScriptsDTO consultarScript(String nomeScript) throws Exception;

	public void marcaErrosScriptsComoCorrigidos() throws Exception;

	public String verificaPermissoesUsuarioBanco(HttpServletRequest request) throws ServiceException;

	public List<ScriptsDTO> pesquisaScriptsComFaltaPermissao() throws Exception;
}
