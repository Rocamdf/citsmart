package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.bpm.negocio.ItemTrabalho;
import br.com.centralit.citcorpore.bean.AprovacaoSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.exception.LogicException;
import br.com.centralit.citcorpore.integracao.AprovacaoSolicitacaoServicoDao;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.WebUtil;

@SuppressWarnings("rawtypes")
public class AprovacaoSolicitacaoServicoServiceEjb extends ComplemInfSolicitacaoServicoServiceEjb implements AprovacaoSolicitacaoServicoService {

	private static final long serialVersionUID = -1117513136090394848L;

	protected CrudDAO getDao() throws ServiceException {
		return new AprovacaoSolicitacaoServicoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaFind(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	public Collection findByIdSolicitacaoServico(Integer parm) throws Exception {
		AprovacaoSolicitacaoServicoDao dao = new AprovacaoSolicitacaoServicoDao();
		try {
			return dao.findByIdSolicitacaoServico(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByIdSolicitacaoServico(Integer parm) throws Exception {
		AprovacaoSolicitacaoServicoDao dao = new AprovacaoSolicitacaoServicoDao();
		try {
			dao.deleteByIdSolicitacaoServico(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public IDto create(TransactionControler tc, SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(TransactionControler tc, SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public IDto deserializaObjeto(String serialize) throws Exception {
		AprovacaoSolicitacaoServicoDTO aprovacaoDto = null;

		if (serialize != null)
			aprovacaoDto = (AprovacaoSolicitacaoServicoDTO) WebUtil.deserializeObject(AprovacaoSolicitacaoServicoDTO.class, serialize);

		return aprovacaoDto;
	}

	@Override
	public void update(TransactionControler tc, SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		validaUpdate(solicitacaoServicoDto, model);

		AprovacaoSolicitacaoServicoDTO aprovacaoDto = (AprovacaoSolicitacaoServicoDTO) model;
		AprovacaoSolicitacaoServicoDao aprovacaoDao = new AprovacaoSolicitacaoServicoDao();
		aprovacaoDao.setTransactionControler(tc);

		aprovacaoDto.setIdResponsavel(solicitacaoServicoDto.getUsuarioDto().getIdEmpregado());
		aprovacaoDto.setDataHora(UtilDatas.getDataHoraAtual());
		aprovacaoDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
		aprovacaoDto.setIdTarefa(solicitacaoServicoDto.getIdTarefa());
		aprovacaoDto = (AprovacaoSolicitacaoServicoDTO) aprovacaoDao.create(aprovacaoDto);

		solicitacaoServicoDto.setIdUltimaAprovacao(aprovacaoDto.getIdAprovacaoSolicitacaoServico());
		SolicitacaoServicoDao solicitacaoDao = new SolicitacaoServicoDao();
		solicitacaoDao.setTransactionControler(tc);
		solicitacaoDao.atualizaIdUltimaAprovacao(solicitacaoServicoDto);

		solicitacaoServicoDto.setAprovacao(aprovacaoDto.getAprovacao());
	}

	public void validaAtualizacao(SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		AprovacaoSolicitacaoServicoDTO aprovacaoDto = (AprovacaoSolicitacaoServicoDTO) model;

		if (aprovacaoDto.getAprovacao() == null || aprovacaoDto.getAprovacao().trim().length() == 0)
			throw new LogicException("Aprovação não informada");
		if (aprovacaoDto.getAprovacao().equalsIgnoreCase("N") && aprovacaoDto.getIdJustificativa() == null)
			throw new LogicException("Justificativa não informada");
	}

	@Override
	public void validaCreate(SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		validaAtualizacao(solicitacaoServicoDto, model);
	}

	@Override
	public void validaDelete(SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void validaUpdate(SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		validaAtualizacao(solicitacaoServicoDto, model);
	}

	@Override
	public AprovacaoSolicitacaoServicoDTO findNaoAprovadaBySolicitacaoServico(SolicitacaoServicoDTO solicitacaoServicoDto) {
		return new AprovacaoSolicitacaoServicoDao().findNaoAprovadaBySolicitacaoServico(solicitacaoServicoDto);
	}
	
	@Override
	public void preparaSolicitacaoParaAprovacao(SolicitacaoServicoDTO solicitacaoDto, ItemTrabalho itemTrabalho, String aprovacao, Integer idJustificativa, String observacoes) throws Exception {
		AprovacaoSolicitacaoServicoDTO aprovacaoDto = new AprovacaoSolicitacaoServicoDTO();
		aprovacaoDto.setAprovacao(aprovacao);
		if (aprovacao.equalsIgnoreCase("N")) {
			aprovacaoDto.setIdJustificativa(idJustificativa);
			aprovacaoDto.setComplementoJustificativa(observacoes);
		}
		solicitacaoDto.setInformacoesComplementares(aprovacaoDto);
		solicitacaoDto.setAcaoFluxo(br.com.centralit.bpm.util.Enumerados.ACAO_EXECUTAR);
		solicitacaoDto.setIdTarefa(itemTrabalho.getIdItemTrabalho());
	}

}
