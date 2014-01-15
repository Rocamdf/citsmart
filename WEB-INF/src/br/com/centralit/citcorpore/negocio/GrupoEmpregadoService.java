package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.RelatorioGruposUsuarioDTO;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.service.CrudServiceEjb2;

@SuppressWarnings("rawtypes")
public interface GrupoEmpregadoService extends CrudServiceEjb2 {

	public Collection<GrupoEmpregadoDTO> findByIdGrupo(Integer idGrupo) throws Exception;

	public Collection<GrupoEmpregadoDTO> findUsariosGrupo() throws Exception;

	public void gerarGridEmpregados(DocumentHTML document, Collection<GrupoEmpregadoDTO> grupoEmpregados) throws Exception;

	/**
	 * Fazer uma coleção de empregado se idEmpregado;
	 * 
	 * @param grupo
	 * @return true - existe; false - não existe;
	 * @throws PersistenceException
	 */
	public Collection findByIdEmpregado(Integer idEmpregado) throws Exception;

	/**
	 * Deleta Grupo Empregado por IdGrupo e IdEmpregado.
	 * 
	 * @param idGrupo
	 * @param idEmpregado
	 * @throws Exception
	 */
	public void deleteByIdGrupoAndEmpregado(Integer idGrupo, Integer idEmpregado) throws Exception;

	/**
	 * Retorna GrupoEmpregado do Tipo HelpDesk de acordo com o ID Contrato informado.
	 * 
	 * @param idContrato
	 *            - Identificador do contrato.
	 * @return Collection<GrupoEmpregadoDTO>
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public Collection<GrupoEmpregadoDTO> findGrupoEmpregadoHelpDeskByIdContrato(Integer idContrato);

	/**
	 * Retorna Lista de GrupoEmpregadoDTO com informações do Grupo e Empregados.
	 * 
	 * @param idGrupo
	 *            - Identificador único do Grupo.
	 * @return listGrupoEmpregadoDTO - Lista de GrupoEmpregadoDTO com informações do empregado.
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public Collection<GrupoEmpregadoDTO> findGrupoAndEmpregadoByIdGrupo(Integer idGrupo) throws Exception;
	public Collection<RelatorioGruposUsuarioDTO> listaRelatorioGruposUsuario(Integer idColaborador) throws Exception;
	public Collection findByIdEmpregadoNome(Integer idEmpregado) throws Exception;
	
	public Integer calculaTotalPaginas(Integer itensPorPagina, Integer idGrupo) throws Exception;	
	public Collection<GrupoEmpregadoDTO> paginacaoGrupoEmpregado(Integer idGrupo, Integer pgAtual, Integer qtdPaginacao) throws Exception;
	public boolean grupoempregado (Integer idEmpregado, Integer idGrupo) throws Exception;
	public Collection<GrupoEmpregadoDTO> findEmpregado(Integer idGrupo, Integer idEmpregado) throws Exception;
}
