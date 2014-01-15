package br.com.centralit.citcorpore.negocio;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.AcordoServicoContratoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.service.CrudServicePojo;

public interface AcordoServicoContratoService extends CrudServicePojo {
	public Collection findByIdAcordoNivelServico(Integer parm) throws Exception;
	public void deleteByIdAcordoNivelServicoAndContrato(Integer idAcordoNivelServico, Integer idContrato) throws Exception;
	public Collection findByIdServicoContrato(Integer parm) throws Exception;
	public void deleteByIdServicoContrato(Integer parm) throws Exception;
	public AcordoServicoContratoDTO findAtivoByIdServicoContrato(Integer idServicoContrato, String tipo) throws Exception;
	/**
	 * Método para verificar a existência de vínculo
	 * @param idAcordoNivelServico
	 * @return Se for true existe vínculo
	 * @throws Exception
	 * @author rodrigo.oliveira
	 */
	boolean existeAcordoServicoContrato(Integer idAcordoNivelServico, Integer idContrato) throws Exception;
	
	/**
	 * 
	 * @param idAcordoNivelServico
	 * @param idServicoContrato
	 * @return Coleção de serviços vinculados 
	 * @throws Exception
	 */
	public Collection<AcordoServicoContratoDTO> findByIdAcordoNivelServicoIdServicoContrato(Integer idAcordoNivelServico, Integer idServicoContrato) throws Exception;
	
	public void updateNotNull(IDto obj) throws Exception;
	
	public List<AcordoServicoContratoDTO> listAtivoByIdServicoContrato(Integer idAcordoServicoContrato, Integer idServicoContrato, String tipo) throws Exception;
}
