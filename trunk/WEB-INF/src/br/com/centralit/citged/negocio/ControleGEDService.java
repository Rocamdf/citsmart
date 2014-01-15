package br.com.centralit.citged.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.AnexoBaseConhecimentoDTO;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.service.CrudServicePojo;

@SuppressWarnings("rawtypes")
public interface ControleGEDService extends CrudServicePojo {

	public String getProximaPastaArmazenar() throws Exception;

	public Collection listByIdTabelaAndID(Integer idTabela, Integer id) throws Exception;

	public Collection convertListControleGEDToUploadDTO(Collection colAnexosControleGED) throws Exception ;

	/**
	 * Pesquisa utilizada somente para arquivos anexados na Base de Conhecimento. idTabela = 4.
	 * 
	 * @param idTabela
	 * @param idBasePai
	 * @param idBaseFilho
	 * @return
	 * @throws PersistenceException
	 * @throws Exception
	 */
	public Collection listByIdTabelaAndIdBaseConhecimentoPaiEFilho(Integer idTabela, Integer idBasePai, Integer idBaseFilho) throws PersistenceException, Exception;

	public Collection listByIdTabelaAndIdBaseConhecimento(Integer idTabela, Integer idBaseConhecimento) throws Exception ;
	
	public Collection listByIdTabelaAndIdLiberacaoAndLigacao(Integer idTabela, Integer idRequisicaoLiberacao) throws Exception ;
	
	public ControleGEDDTO getControleGED(AnexoBaseConhecimentoDTO anexoBaseConhecimento) throws Exception;
	
}
