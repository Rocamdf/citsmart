package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.EnderecoDTO;
import br.com.centralit.citcorpore.integracao.EnderecoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class EnderecoServiceEjb extends CrudServicePojoImpl implements EnderecoService {
	protected CrudDAO getDao() throws ServiceException {
		return new EnderecoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

    @Override
    public Collection<EnderecoDTO> recuperaEnderecosEntregaProduto() throws Exception {
        return new EnderecoDao().recuperaEnderecosEntregaProduto();
    }
    
    public EnderecoDTO recuperaEnderecoCompleto(EnderecoDTO endereco) throws Exception{
		EnderecoDao endDao = new EnderecoDao();
    	return endDao.recuperaEnderecoCompleto(endereco);
    }
    
    public EnderecoDTO recuperaEnderecoComUnidade(Integer idEndereco) throws Exception{ 
		EnderecoDao endDao = new EnderecoDao();
    	return endDao.recuperaEnderecoComUnidade(idEndereco);    	
    }

}
