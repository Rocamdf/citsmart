package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ModeloEmailDTO;
import br.com.centralit.citcorpore.integracao.ModeloEmailDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
 

@SuppressWarnings("rawtypes")
public class ModeloEmailServiceEjb extends CrudServicePojoImpl 
	implements ModeloEmailService {
	
	private static final long serialVersionUID = 1L;

	protected CrudDAO getDao() throws ServiceException {
		return new ModeloEmailDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
		ModeloEmailDTO modeloDto = (ModeloEmailDTO) arg0;
		
		if (modeloDto.getIdentificador() == null)
			throw new ServiceException(i18n_Message("modeloemail.IdentificadorNaoDefinido") );
		
		if (modeloDto.getIdentificador().indexOf(" ") >= 0)
			throw new ServiceException(i18n_Message("modeloemail.identificadorNaoPodeConterEspacos") );
		
		ModeloEmailDTO modeloAuxDto = new ModeloEmailDao().findByIdentificador(modeloDto.getIdentificador() );
		
		if (modeloAuxDto != null)
			throw new ServiceException(i18n_Message("modeloemail.jaExisteModeloEmailComEsseIdentificador") );
	}

	protected void validaDelete(Object arg0) throws Exception {

	}

	protected void validaFind(Object arg0) throws Exception {

	}

	protected void validaUpdate(Object arg0) throws Exception {
		ModeloEmailDTO modeloDto = (ModeloEmailDTO) arg0;
		
		if (modeloDto.getIdentificador().trim() != null)
			modeloDto.setIdentificador(modeloDto.getIdentificador().trim() );
		
		if (modeloDto.getIdentificador() == null)
			throw new ServiceException(i18n_Message("modeloemail.IdentificadorNaoDefinido") );
		
		if (modeloDto.getIdentificador().indexOf(" ") >= 0)
			throw new ServiceException(i18n_Message("modeloemail.identificadorNaoPodeConterEspacos") );
		
		ModeloEmailDTO modeloAuxDto = new ModeloEmailDao().findByIdentificador(modeloDto.getIdentificador() );
		
		if (modeloAuxDto != null && modeloAuxDto.getIdModeloEmail().intValue() != modeloDto.getIdModeloEmail().intValue() )
			throw new ServiceException(i18n_Message("modeloemail.jaExisteModeloEmailComEsseIdentificador") );
	}

	public Collection getAtivos() throws Exception {
		ModeloEmailDao mdao = new ModeloEmailDao();
		
		return mdao.getAtivos();
	}
	
	public ModeloEmailDTO findByIdentificador(String identificador) throws Exception {
		ModeloEmailDTO modeloEmailDTO = null;		
		
		if (identificador != null && !identificador.trim().equals("")  ) {
			ModeloEmailDao modeloEmailDAO = (ModeloEmailDao) getDao();
			
			modeloEmailDTO = modeloEmailDAO.findByIdentificador(identificador);
		}
		
		return modeloEmailDTO;
	}
}
