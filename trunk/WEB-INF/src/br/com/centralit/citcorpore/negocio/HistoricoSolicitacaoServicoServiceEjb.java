package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.HistoricoSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.RelatorioCargaHorariaDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.integracao.HistoricoSolicitacaoServicoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServicePojoImpl;

public class HistoricoSolicitacaoServicoServiceEjb extends CrudServicePojoImpl implements HistoricoSolicitacaoServicoService {

	private static final long serialVersionUID = 1L;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new HistoricoSolicitacaoServicoDao();
	}
	@Override
	protected void validaCreate(Object obj) throws Exception {

	}

	@Override
	protected void validaUpdate(Object obj) throws Exception {

	}

	@Override
	protected void validaDelete(Object obj) throws Exception {

	}

	@Override
	protected void validaFind(Object obj) throws Exception {

	}

	@Override
	public boolean findHistoricoSolicitacao(Integer idSolicitacaoServico)throws Exception {
		HistoricoSolicitacaoServicoDao historicoSolicitacaoServicoDao = new HistoricoSolicitacaoServicoDao();
		return historicoSolicitacaoServicoDao.findHistoricoSolicitacao(idSolicitacaoServico);
	}

	@Override
	public Collection<HistoricoSolicitacaoServicoDTO> restoreHistoricoServico(Integer idSolicitacaoServico)throws Exception {
		HistoricoSolicitacaoServicoDao historicoSolicitacaoServicoDao = new HistoricoSolicitacaoServicoDao();
		return historicoSolicitacaoServicoDao.restoreHistoricoServico(idSolicitacaoServico);
	}

	@Override
	public Collection<HistoricoSolicitacaoServicoDTO> findResponsavelAtual(Integer idSolicitacaoServico)throws Exception {
		HistoricoSolicitacaoServicoDao historicoSolicitacaoServicoDao = new HistoricoSolicitacaoServicoDao();
		return historicoSolicitacaoServicoDao.findResponsavelAtual(idSolicitacaoServico);
	}

	@Override
	public Collection<RelatorioCargaHorariaDTO> imprimirCargaHorariaUsuario(SolicitacaoServicoDTO solicitacaoServicoDTO)throws Exception {
		HistoricoSolicitacaoServicoDao historicoSolicitacaoServicoDao = new HistoricoSolicitacaoServicoDao();
		return historicoSolicitacaoServicoDao.imprimirCargaHorariaUsuario(solicitacaoServicoDTO);
	}
	
	@Override
	public Collection<SolicitacaoServicoDTO> imprimirSolicitacaoEncaminhada(SolicitacaoServicoDTO solicitacaoServicoDTO)throws Exception {
		HistoricoSolicitacaoServicoDao historicoSolicitacaoServicoDao = new HistoricoSolicitacaoServicoDao();
		return historicoSolicitacaoServicoDao.imprimirSolicitacaoEncaminhada(solicitacaoServicoDTO);
	}
	
	@Override
	public Collection<SolicitacaoServicoDTO> imprimirSolicitacaoEncaminhadaFilhas(SolicitacaoServicoDTO solicitacaoServicoDTO)throws Exception {
		HistoricoSolicitacaoServicoDao historicoSolicitacaoServicoDao = new HistoricoSolicitacaoServicoDao();
		return historicoSolicitacaoServicoDao.imprimirSolicitacaoEncaminhadaFilhas(solicitacaoServicoDTO);
	}
	
	@Override
	public Collection<RelatorioCargaHorariaDTO> imprimirCargaHorariaGrupo(SolicitacaoServicoDTO solicitacaoServicoDTO)throws Exception {
		HistoricoSolicitacaoServicoDao historicoSolicitacaoServicoDao = new HistoricoSolicitacaoServicoDao();
		return historicoSolicitacaoServicoDao.imprimirCargaHorariaGrupo(solicitacaoServicoDTO);
	}
	
	 public static IDto create(HistoricoSolicitacaoServicoDTO historicoSolicitacaoServicoDTO, TransactionControler tc) throws Exception{
		 IDto historico = new HistoricoSolicitacaoServicoDTO();
		 HistoricoSolicitacaoServicoDao historicoSolicitacaoServicoDao = new HistoricoSolicitacaoServicoDao();
		  if (tc != null){
			  historicoSolicitacaoServicoDao.setTransactionControler(tc);
			  historico = historicoSolicitacaoServicoDao.create(historicoSolicitacaoServicoDTO);
		  }
		return historico;
	 }
	 
	 public static void update(HistoricoSolicitacaoServicoDTO historicoSolicitacaoServicoDTO, TransactionControler tc) throws Exception{
		 HistoricoSolicitacaoServicoDao historicoSolicitacaoServicoDao = new HistoricoSolicitacaoServicoDao();
		  if (tc != null){
			  historicoSolicitacaoServicoDao.setTransactionControler(tc);
	    		historicoSolicitacaoServicoDao.update(historicoSolicitacaoServicoDTO);
		  }
	 }

}
