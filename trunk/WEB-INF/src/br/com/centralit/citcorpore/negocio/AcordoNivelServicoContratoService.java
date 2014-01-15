package br.com.centralit.citcorpore.negocio;
import java.util.Collection;
import java.util.HashMap;

import br.com.centralit.citcorpore.bean.ResultadosEsperadosDTO;
import br.com.citframework.service.CrudServicePojo;

public interface AcordoNivelServicoContratoService extends CrudServicePojo {
	public Collection findByIdContrato(Integer parm) throws Exception;
	public void deleteByIdContrato(Integer parm) throws Exception;
	public boolean verificaDataContrato(HashMap mapFields) throws Exception;
	public Collection consultaResultadosEsperados(ResultadosEsperadosDTO resultadosEsperadosDTO) throws Exception;
}
