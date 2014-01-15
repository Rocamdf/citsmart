package br.com.centralit.bpm.servico;
import br.com.centralit.bpm.dto.TipoFluxoDTO;
import br.com.centralit.bpm.integracao.TipoFluxoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class TipoFluxoServiceEjb extends CrudServicePojoImpl implements TipoFluxoService {
	protected CrudDAO getDao() throws ServiceException {
		return new TipoFluxoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
		TipoFluxoDTO tipoFluxoDto = (TipoFluxoDTO) arg0;
		TipoFluxoDTO tipoFluxoAuxDto = new TipoFluxoDao().findByNome(tipoFluxoDto.getNomeFluxo());
		if (tipoFluxoAuxDto != null)
			throw new ServiceException("Já existe um tipo de fluxo com esse nome.");	
	}
	
	protected void validaDelete(Object arg0) throws Exception {}
	
	protected void validaFind(Object arg0) throws Exception {}
	
	protected void validaUpdate(Object arg0) throws Exception {
		TipoFluxoDTO tipoFluxoDto = (TipoFluxoDTO) arg0;
		TipoFluxoDTO tipoFluxoAuxDto = new TipoFluxoDao().findByNome(tipoFluxoDto.getNomeFluxo());
		
		if (tipoFluxoAuxDto != null && tipoFluxoAuxDto.getIdTipoFluxo().intValue() != tipoFluxoDto.getIdTipoFluxo().intValue())
			throw new ServiceException("Já existe um tipo de fluxo com esse nome.");	
	}

}
