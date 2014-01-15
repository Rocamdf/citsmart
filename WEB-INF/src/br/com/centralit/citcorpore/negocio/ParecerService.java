package br.com.centralit.citcorpore.negocio;
import br.com.centralit.citcorpore.bean.ParecerDTO;
import br.com.citframework.service.CrudServiceEjb2;
public interface ParecerService extends CrudServiceEjb2 {
	
	/**
	 * 
	 * @param obj
	 * @return boolean
	 * @throws Exception
	 * @author thays.araujo
	 */
	public boolean verificarSeExisteJustificativaParecer(ParecerDTO obj) throws Exception;
}
