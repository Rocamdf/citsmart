package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceEjb2;

/**
 * @author thays.araujo e daniel
 * 
 */
/**
 * @author thays
 * 
 */
@SuppressWarnings("rawtypes")
public interface EmpregadoService extends CrudServiceEjb2 {

	void updateNotNull(IDto dto);

	public EmpregadoDTO restoreEmpregadosAtivosById(Integer idEmpregado);

	public EmpregadoDTO restoreEmpregadoSeAtivo(EmpregadoDTO empregadoDto);

	public EmpregadoDTO restoreByNomeEmpregado(EmpregadoDTO empregadoDTO) throws Exception;

	public void delete(EmpregadoDTO empregado) throws ServiceException, Exception;

	/**
	 * Restaura o idEmpregado
	 * 
	 * @param idEmpregado
	 * @return Restaura idEmpregado
	 * @throws Exception
	 */
	public EmpregadoDTO restoreByIdEmpregado(Integer idEmpregado) throws Exception;

	/**
	 * Calcula Custo da Hora e o Custo do Mï¿½s do empregado.
	 * 
	 * @param empregado
	 * @return EmpregadoDTO
	 * @throws ServiceException
	 * @throws RemoteException
	 * @throws LogicException
	 * @author thays.araujo
	 */
	public EmpregadoDTO calcularCustos(EmpregadoDTO empregado) throws ServiceException, RemoteException, LogicException;

	/**
	 * Lista empregados.
	 * 
	 * @param idGrupo
	 *            Identificador ï¿½nicio do grupo.
	 * @return Collection<EmpregadoDTO>
	 * @throws ServiceException
	 * @throws RemoteException
	 * @author daniel
	 */
	public Collection<EmpregadoDTO> listEmpregadosByIdGrupo(Integer idGrupo) throws ServiceException, RemoteException;

	/**
	 * @param idUnidade
	 * @return Collection<EmpregadoDTO>
	 * @throws ServiceException
	 * @throws RemoteException
	 * @author daniel
	 */
	public Collection<EmpregadoDTO> listEmpregadosByIdUnidade(Integer idUnidade) throws ServiceException, RemoteException;

	public Collection<EmpregadoDTO> listEmpregadosGrupo(Integer idEmpregado, Integer idGrupo) throws Exception;

	public Collection<EmpregadoDTO> listEmailContrato(Integer idContrato) throws Exception;

	public Collection<EmpregadoDTO> listEmailContrato() throws Exception;

	public EmpregadoDTO listEmpregadoContrato(Integer idContrato, String email) throws Exception;

	public EmpregadoDTO listEmpregadoContrato(String email) throws Exception;

	/**
	 * Faz a exclusão logica de empregado.
	 * 
	 * @param model
	 * @throws ServiceException
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void deleteEmpregado(IDto model) throws ServiceException, Exception;

	/**
	 * 
	 * @param idEmpregado
	 * @return Integer - idUnidade
	 * @throws Exception
	 * @author rodrigo.oliveira
	 */
	public Integer consultaUnidadeDoEmpregado(Integer idEmpregado) throws Exception;

	/**
	 * Retorna uma lista de email de empregados que receberão notificações de base de conhecimento
	 * 
	 * @param idConhecimento
	 * @return
	 * @throws Exception
	 * @author cleon.junior
	 */
	public Collection listarEmailsNotificacoesConhecimento(Integer idConhecimento) throws Exception;

	/**
	 * Retorna verdadeiro para ativo ou falso para inativo de acordo com nome do empregado passado.
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	public boolean verificarEmpregadosAtivos(EmpregadoDTO obj) throws Exception;

	public Collection findByNomeEmpregado(EmpregadoDTO empregadoDTO) throws Exception;

	public EmpregadoDTO restoreByEmail(String email) throws Exception;

	public Collection listarIdEmpregados(Integer limit, Integer offset) throws Exception;

	public EmpregadoDTO restauraTodos(EmpregadoDTO param) throws Exception;

	/**
	 * Retorna EmpregadoDTO (idEmpregado e Nome). Esta consulta é a mesma da LOOKUP_SOLICITANTE_CONTRATO.
	 * 
	 * @param nome
	 *            - Nome do Empregado (Campo Nome da tabela Empregados)
	 * @param idContrato
	 *            - Identificador do Contrato.
	 * @return Collection<EmpregadoDTO> - Lista de Empregados com Id e Nome.
	 * @throws Exception
	 * @author valdoilo.damasceno 29.10.2013
	 */
	public Collection<EmpregadoDTO> findSolicitanteByNomeAndIdContrato(String nome, Integer contrato) throws Exception;

	/**
	 * Pesquisa Empregado por Telefone ou Ramal. Retorna o primeiro Empregado encontrado para o Ramal ou Telefone informado. <<< ATENÇÃO >> o parâmetro Telefone antes de ser enviado para o método,
	 * deve ser tratado com o Método mascaraProcuraSql() da Classe Utilitária br.com.centralit.citcorpore.util.Telefone.java;
	 * 
	 * @param telefone
	 * @return EmpregadoDTO
	 * @author valdoilo.damasceno
	 */
	public EmpregadoDTO findByTelefoneOrRamal(String telefone);

	/**
	 * Restaura o EmpregadoDTO com o ID do Contrato Padrão (Primeiro contrato encontrado para o Empregado) a partir do ID Empregado informado.
	 * 
	 * @param idEmpregado
	 * @return EmpregadoDTO com IDContrato
	 * @author valdoilo.damasceno
	 */
	public EmpregadoDTO restoreEmpregadoWithIdContratoPadraoByIdEmpregado(Integer idEmpregado);
	
	
	/**
	 * Restaura o EmpregadoDTO com o Nome cargo a partir do ID Empregado informado.
	 * 
	 * @param idEmpregado
	 * @return EmpregadoDTO com NomeCargo
	 * @author maycon.fernandes
	 */
	public EmpregadoDTO restoreEmpregadoAndNomeCargoByIdEmpegado(Integer idEmpregado) throws Exception;
}
