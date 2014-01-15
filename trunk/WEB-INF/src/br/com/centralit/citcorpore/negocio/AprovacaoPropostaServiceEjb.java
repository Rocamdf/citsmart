package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.AprovacaoMudancaDTO;
import br.com.centralit.citcorpore.bean.AprovacaoPropostaDTO;
import br.com.centralit.citcorpore.integracao.AprovacaoMudancaDao;
import br.com.centralit.citcorpore.integracao.AprovacaoPropostaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class AprovacaoPropostaServiceEjb extends CrudServicePojoImpl  implements AprovacaoPropostaService{

	@Override
	protected CrudDAO getDao() throws ServiceException {
		
		return new AprovacaoPropostaDao();
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
	public Collection<AprovacaoPropostaDTO> listaAprovacaoPropostaPorIdRequisicaoMudanca(Integer idRequisicaoMudanca,Integer idGrupo, Integer idEmpregado) throws Exception {
		AprovacaoPropostaDao aprovacaoPropostaDao =  new AprovacaoPropostaDao();
		return aprovacaoPropostaDao.listaAprovacaoPropostaPorIdRequisicaoMudanca(idRequisicaoMudanca, idGrupo, idEmpregado);
	}

	@Override
	public Integer quantidadeAprovacaoPropostaPorVotoAprovada(AprovacaoPropostaDTO aprovacao,Integer idGrupo) throws Exception{
		AprovacaoPropostaDao aprovacaoPropostaDao =  new AprovacaoPropostaDao();
		return aprovacaoPropostaDao.quantidadeAprovacaoPropostaPorVotoAprovada(aprovacao, idGrupo);
	}

	@Override
	public Integer quantidadeAprovacaoPropostaPorVotoRejeitada(AprovacaoPropostaDTO aprovacao,Integer idGrupo) throws Exception {
		AprovacaoPropostaDao aprovacaoPropostaDao =  new AprovacaoPropostaDao();
		return aprovacaoPropostaDao.quantidadeAprovacaoPropostaPorVotoRejeitada(aprovacao, idGrupo);
	}

	@Override
	public Boolean validacaoAprovacaoProposta(Integer idRequisicaoMudanca) throws Exception {
		AprovacaoPropostaDao aprovacaoPropostaDao =  new AprovacaoPropostaDao();
		return aprovacaoPropostaDao.validacaoAprovacaoProposta(idRequisicaoMudanca);
	}

	@Override
	public Integer quantidadeAprovacaoProposta(AprovacaoPropostaDTO aprovacao, Integer idGrupo) throws Exception {
		AprovacaoPropostaDao aprovacaoPropostaDao =  new AprovacaoPropostaDao();
		return aprovacaoPropostaDao.quantidadeAprovacaoProposta(aprovacao, idGrupo);
	}

	
}
