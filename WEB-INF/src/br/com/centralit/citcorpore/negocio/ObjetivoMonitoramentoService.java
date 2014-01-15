package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.ObjetivoMonitoramentoDTO;
import br.com.citframework.service.CrudServicePojo;
public interface ObjetivoMonitoramentoService extends CrudServicePojo {
	@SuppressWarnings("rawtypes")
	public Collection findByIdObjetivoPlanoMelhoria(Integer parm) throws Exception;
	public void deleteByIdObjetivoPlanoMelhoria(Integer parm) throws Exception;
	/**
	 * Retorna uma lista de objetivos Monitoramento de acordo com o objetivo plano melhoria passado.
	 * @param obj
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<ObjetivoMonitoramentoDTO> listObjetivosMonitoramento(ObjetivoMonitoramentoDTO obj) throws Exception;
}
