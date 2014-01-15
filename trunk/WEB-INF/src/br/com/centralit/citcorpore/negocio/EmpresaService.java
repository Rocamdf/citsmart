package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.EmpresaDTO;
import br.com.citframework.service.CrudServiceEjb2;

/**
 * @author rosana.godinho
 *
 */
public interface EmpresaService extends CrudServiceEjb2 {
	boolean jaExisteRegistroComMesmoNome(EmpresaDTO empresa);
}
