package br.com.centralit.citcorpore.negocio;
import br.com.centralit.citcorpore.bean.ContatoRequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.ContatoRequisicaoMudancaDTO;
import br.com.centralit.citcorpore.integracao.ContatoRequisicaoLiberacaoDao;
import br.com.centralit.citcorpore.integracao.ContatoRequisicaoMudancaDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
@SuppressWarnings("serial")
public class ContatoRequisicaoMudancaServiceEjb extends CrudServicePojoImpl implements ContatoRequisicaoMudancaService {
	protected CrudDAO getDao() throws ServiceException {
		return new ContatoRequisicaoMudancaDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	@Override
	public synchronized IDto create(IDto model) throws ServiceException, LogicException {
		return super.create(model);
	}

	@Override
/*	public ContatoRequisicaoMudancaDTO restoreContatosById(
			Integer idContatoRequisicaoMudanca) {
		// TODO Auto-generated method stub
		return null;
	}*/
	public ContatoRequisicaoMudancaDTO restoreContatosById(Integer idContatoRequisicaoMudanca) {
		// TODO Auto-generated method stub
		ContatoRequisicaoMudancaDao contatoRequisicaoMudancaDao = new ContatoRequisicaoMudancaDao();
		ContatoRequisicaoMudancaDTO contatoRequisicaoMudancaDTO = new ContatoRequisicaoMudancaDTO();
		contatoRequisicaoMudancaDTO.setIdContatoRequisicaoMudanca(idContatoRequisicaoMudanca);
		try {
			contatoRequisicaoMudancaDTO = (ContatoRequisicaoMudancaDTO) contatoRequisicaoMudancaDao.restore(contatoRequisicaoMudancaDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Contato Requisicao Liberacão não foi encontrado com esse ID");
		}
		return contatoRequisicaoMudancaDTO;
	}

}
