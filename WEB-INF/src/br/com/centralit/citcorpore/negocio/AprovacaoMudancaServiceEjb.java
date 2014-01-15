package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.AprovacaoMudancaDTO;
import br.com.centralit.citcorpore.integracao.AprovacaoMudancaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

@SuppressWarnings("serial")
public class AprovacaoMudancaServiceEjb extends CrudServicePojoImpl  implements AprovacaoMudancaService {

	@Override
	protected CrudDAO getDao() throws ServiceException {
		
		return new AprovacaoMudancaDao();
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
	public Collection<AprovacaoMudancaDTO> listaAprovacaoMudancaPorIdRequisicaoMudanca(Integer idRequisicaoMudanca,Integer idGrupo, Integer idEmpregado) throws Exception {
		AprovacaoMudancaDao aprovacaoMudancaDao =  new AprovacaoMudancaDao();
		return aprovacaoMudancaDao.listaAprovacaoMudancaPorIdRequisicaoMudanca(idRequisicaoMudanca, idGrupo, idEmpregado);
	}

	@Override
	public Integer quantidadeAprovacaoMudancaPorVotoAprovada(AprovacaoMudancaDTO aprovacao,Integer idGrupo) throws Exception{
		AprovacaoMudancaDao aprovacaoMudancaDao =  new AprovacaoMudancaDao();
		return aprovacaoMudancaDao.quantidadeAprovacaoMudancaPorVotoAprovada(aprovacao,idGrupo);
	}

	@Override
	public Integer quantidadeAprovacaoMudancaPorVotoRejeitada(AprovacaoMudancaDTO aprovacao,Integer idGrupo) throws Exception {
		AprovacaoMudancaDao aprovacaoMudancaDao =  new AprovacaoMudancaDao();
		return aprovacaoMudancaDao.quantidadeAprovacaoMudancaPorVotoRejeitada(aprovacao,idGrupo);
	}

	@Override
	public Boolean validacaoAprovacaoMudanca(Integer idRequisicaoMudanca) throws Exception {
		AprovacaoMudancaDao aprovacaoMudancaDao =  new AprovacaoMudancaDao();
		return aprovacaoMudancaDao.validacaoAprovacaoMudanca(idRequisicaoMudanca);
	}

	@Override
	public Integer quantidadeAprovacaoMudanca(AprovacaoMudancaDTO aprovacao, Integer idGrupo) throws Exception {
		AprovacaoMudancaDao aprovacaoMudancaDao =  new AprovacaoMudancaDao();
		return aprovacaoMudancaDao.quantidadeAprovacaoMudanca(aprovacao, idGrupo);
	}

}
