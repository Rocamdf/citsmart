package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.ContatoRequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.ContatoRequisicaoMudancaDTO;
import br.com.centralit.citcorpore.integracao.ContatoRequisicaoLiberacaoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class ContatoRequisicaoLiberacaoServiceEjb extends CrudServicePojoImpl
		implements ContatoRequisicaoLiberacaoService {

	@Override
	public ContatoRequisicaoLiberacaoDTO restoreContatosById(
			Integer idContatoRequisicaoLiberacao) {
		// TODO Auto-generated method stub
		ContatoRequisicaoLiberacaoDao contatoRequisicaoLiberacaoDao = new ContatoRequisicaoLiberacaoDao();
		ContatoRequisicaoLiberacaoDTO contatoRequisicaoLiberacaoDTO = new ContatoRequisicaoLiberacaoDTO();
		contatoRequisicaoLiberacaoDTO.setIdContatoRequisicaoLiberacao(idContatoRequisicaoLiberacao);
		try {
			contatoRequisicaoLiberacaoDTO = (ContatoRequisicaoLiberacaoDTO) contatoRequisicaoLiberacaoDao.restore(contatoRequisicaoLiberacaoDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Contato Requisicao Liberacão não foi encontrado com esse ID");
		}
		return contatoRequisicaoLiberacaoDTO;
	}
	@Override
	public synchronized IDto create(IDto model) throws ServiceException, LogicException {
		return super.create(model);
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
