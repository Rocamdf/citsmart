package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.integracao.FornecedorDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class FornecedorServiceEjb extends CrudServicePojoImpl implements FornecedorService {
	
	private static final long serialVersionUID = 6749460411034506461L;
	
	protected CrudDAO getDao() throws ServiceException {
		return new FornecedorDao();
	}

	protected void validaCreate(Object arg0) throws Exception {};
	
	protected void validaDelete(Object arg0) throws Exception {};
	
	protected void validaFind(Object arg0) throws Exception {};
	
	protected void validaUpdate(Object arg0) throws Exception {};

	@Override
	public Collection<FornecedorDTO> listEscopoFornecimento(FornecedorDTO fornecedorDto) throws Exception {
		FornecedorDao dao = new FornecedorDao();
		try {
			return dao.listEscopoFornecimento(fornecedorDto);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean consultarCargosAtivos(FornecedorDTO fornecedor) throws Exception {
		FornecedorDao fornecedorDao = new FornecedorDao();
		
		return fornecedorDao.consultarCargosAtivos(fornecedor);
	}
	
	public boolean excluirFornecedor(FornecedorDTO fornecedor) throws Exception{
		FornecedorDao fornecedorDao = new FornecedorDao();
		
		return fornecedorDao.excluirFornecedor(fornecedor);
	}
	
}
