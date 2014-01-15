package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceEjb2;

@SuppressWarnings("rawtypes")
public interface GrupoService extends CrudServiceEjb2 {
	/**
	 * Retorna lista de GRUPO que ainda não estï¿½o associados a EMPREGADOS.
	 * 
	 * @return
	 * @throws Exception
	 */
	public Collection listaGrupoEmpregado() throws Exception;

	/**
	 * Retorna lista de GRUPO que ainda não estï¿½o associados a USUï¿½RIO.
	 * 
	 * @return
	 * @throws Exception
	 */
	public Collection listaGrupoUsuario() throws Exception;

	public Collection getGruposByPessoa(Integer idEmpregado) throws LogicException, RemoteException, ServiceException;

	public Collection getGruposByEmpregado(Integer idEmpregado) throws LogicException, RemoteException, ServiceException;

	public Collection<GrupoDTO> listGruposServiceDesk() throws Exception;

	public IDto create(IDto model, HttpServletRequest request) throws ServiceException, LogicException;

	public void update(IDto grupoDto, HttpServletRequest request) throws ServiceException, LogicException;

	public void delete(IDto model, DocumentHTML document) throws ServiceException, LogicException;
	
	public Collection<GrupoDTO> listGruposPorUsuario(int idUsuario);

	Collection findGruposAtivos();

	/**
	 * Verifica se grupo informado já existe;
	 * 
	 * @param grupo
	 * @return true - existe; false - não existe;
	 * @throws PersistenceException
	 */
	public boolean verificarSeGrupoExiste(GrupoDTO grupo) throws PersistenceException;

	public Collection<GrupoDTO> listGrupoByIdContrato(Integer idContrato) throws Exception;

	public Collection<GrupoDTO> listGruposServiceDeskByIdContrato(Integer idContratoParm) throws Exception;

	/**
	 * Retorna Lista de Grupos do Empregado.
	 * 
	 * @param idEmpregado
	 *            - Identificador do Empregado.
	 * @return Collection<GrupoDTO> - Lista de Grupos.
	 * @throws Exception
	 */
	public Collection<GrupoDTO> getGruposByIdEmpregado(Integer idEmpregado) throws Exception;

	public Collection listarGruposAtivos() throws Exception;

	/**
	 * Retorna lista de e-mails que estão cadastrados para receber notificação
	 * 
	 * @param idGrupo
	 * @return
	 * @throws Exception
	 */
	public Collection<String> listarEmailsPorGrupo(Integer idGrupo) throws Exception;

	public Collection<String> listarPessoasEmailPorGrupo(Integer idGrupo) throws Exception;

	/**
	 * Retorna lista de GRUPO que são do Comite de Controle de Mudança
	 * 
	 * @author Riubbe Oliveira
	 * 
	 */
	public Collection<GrupoDTO> listGruposComite() throws Exception;

	/**
	 * Retorna lista de GRUPO que não são do Comite de Controle de Mudança
	 * 
	 * @author Riubbe Oliveira
	 * 
	 */
	public Collection<GrupoDTO> listGruposNaoComite() throws Exception;

	/**
	 * @param idGrupo
	 * @return
	 * @throws Exception
	 */
	public GrupoDTO listGrupoById(Integer idGrupo) throws Exception;

	public Collection<GrupoDTO> listAllGrupos() throws Exception;

	/**
	 * Retorna uma lista de grupos ativos
	 * 
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<GrupoDTO> listaGruposAtivos() throws Exception;

	public Collection<GrupoDTO> listaGrupoEmpregado(Integer idEmpregado) throws Exception;

}
