package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServicePojoImpl;

public class AprovacaoRequisicaoLiberacaoServiceEjb extends CrudServicePojoImpl
		implements AprovacaoRequisicaoLiberacaoService {

	@Override
	public IDto deserializaObjeto(String serialize) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validaCreate(SolicitacaoServicoDTO solicitacaoServicoDto,
			IDto model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void validaDelete(SolicitacaoServicoDTO solicitacaoServicoDto,
			IDto model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void validaUpdate(SolicitacaoServicoDTO solicitacaoServicoDto,
			IDto model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public IDto create(TransactionControler tc,
			SolicitacaoServicoDTO solicitacaoServicoDto, IDto model)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(TransactionControler tc,
			SolicitacaoServicoDTO solicitacaoServicoDto, IDto model)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(TransactionControler tc,
			SolicitacaoServicoDTO solicitacaoServicoDto, IDto model)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("rawtypes")
	@Override
	public Collection findByIdSolicitacaoServico(Integer parm) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteByIdSolicitacaoServico(Integer parm) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected CrudDAO getDao() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
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

}
