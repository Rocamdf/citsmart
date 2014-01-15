package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.JustificativaRequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.integracao.JustificativaRequisicaoLiberacaoDao;
import br.com.centralit.citcorpore.negocio.JustificativaRequisicaoLiberacaoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
@SuppressWarnings({  "serial" })
public class JustificativaRequisicaoLiberacaoServiceEjb extends CrudServicePojoImpl implements JustificativaRequisicaoLiberacaoService {

	@Override
	protected CrudDAO getDao() throws ServiceException {
		// TODO Auto-generated method stub
		return new JustificativaRequisicaoLiberacaoDao();
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
	public Collection<JustificativaRequisicaoLiberacaoDTO> listAtivasParaSuspensao() throws Exception {
		JustificativaRequisicaoLiberacaoDao justificativaRequisicaoLiberacaoDao = new JustificativaRequisicaoLiberacaoDao();
		return justificativaRequisicaoLiberacaoDao.listAtivasParaSuspensao();
	}

}
