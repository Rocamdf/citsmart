package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.JustificativaRequisicaoMudancaDTO;
import br.com.centralit.citcorpore.integracao.JustificativaRequisicaoMudancaDao;
import br.com.centralit.citcorpore.negocio.JustificativaRequisicaoMudancaService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
@SuppressWarnings({  "serial" })
public class JustificativaRequisicaoMudancaServiceEjb extends CrudServicePojoImpl implements JustificativaRequisicaoMudancaService {

	@Override
	protected CrudDAO getDao() throws ServiceException {
		// TODO Auto-generated method stub
		return new JustificativaRequisicaoMudancaDao();
	}

	@Override
	protected void validaCreate(Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validaUpdate(Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validaDelete(Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validaFind(Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public Collection<JustificativaRequisicaoMudancaDTO> listAtivasParaSuspensao() throws Exception {
		JustificativaRequisicaoMudancaDao justificativaRequisicaoMudancaDao = new JustificativaRequisicaoMudancaDao();
		return justificativaRequisicaoMudancaDao.listAtivasParaSuspensao();
	}

}
