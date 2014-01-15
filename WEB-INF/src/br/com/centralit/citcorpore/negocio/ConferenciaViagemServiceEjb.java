package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.ParecerDTO;
import br.com.centralit.citcorpore.bean.PrestacaoContasViagemDTO;
import br.com.centralit.citcorpore.bean.RequisicaoViagemDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.integracao.ParecerDao;
import br.com.centralit.citcorpore.integracao.PrestacaoContasViagemDao;
import br.com.centralit.citcorpore.integracao.RequisicaoViagemDAO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.WebUtil;

public class ConferenciaViagemServiceEjb extends ComplemInfSolicitacaoServicoServiceEjb implements ConferenciaViagemService {

	private static final long serialVersionUID = 1L;

	@Override
	public IDto deserializaObjeto(String serialize) throws Exception {
		PrestacaoContasViagemDTO prestacaoContasViagemDto = null;
		if(serialize != null){
			prestacaoContasViagemDto = (PrestacaoContasViagemDTO) WebUtil.deserializeObject(PrestacaoContasViagemDTO.class, serialize);
		}
		return prestacaoContasViagemDto;
	}

	@Override
	public void validaCreate(SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {}

	@Override
	public void validaDelete(SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {}

	@Override
	public void validaUpdate(SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {}

	@Override
	public IDto create(TransactionControler tc, SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		return null;
	}

	@Override
	public void update(TransactionControler tc, SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		PrestacaoContasViagemDTO prestacaoContasViagemDto = (PrestacaoContasViagemDTO) model;
		PrestacaoContasViagemDao prestacaoContasViagemDao = new PrestacaoContasViagemDao();
		RequisicaoViagemDTO requisicaoViagemDto = new RequisicaoViagemDTO();
		RequisicaoViagemDAO reqViagemDao = new RequisicaoViagemDAO();
		ParecerDTO parecerDto = new ParecerDTO();
		ParecerDao parecerDao = new ParecerDao();
	
		prestacaoContasViagemDao.setTransactionControler(tc);
		parecerDao.setTransactionControler(tc);
		reqViagemDao.setTransactionControler(tc);
		
		try{
			parecerDto.setIdJustificativa(prestacaoContasViagemDto.getIdJustificativaAutorizacao());
			parecerDto.setIdResponsavel(solicitacaoServicoDto.getUsuarioDto().getIdEmpregado());
	//		parecerDto.setObservacoes(prestacaoContasViagemDto.getObservacoes());
			parecerDto.setComplementoJustificativa(prestacaoContasViagemDto.getComplemJustificativaAutorizacao());
			parecerDto.setAprovado(prestacaoContasViagemDto.getAprovado());
			parecerDto.setDataHoraParecer(UtilDatas.getDataHoraAtual());
			
			parecerDto = (ParecerDTO) parecerDao.create(parecerDto);
			
			prestacaoContasViagemDto.setIdResponsavel(solicitacaoServicoDto.getUsuarioDto().getIdEmpregado());
			
			requisicaoViagemDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
			requisicaoViagemDto = (RequisicaoViagemDTO) reqViagemDao.restore(requisicaoViagemDto);
			
			PrestacaoContasViagemDTO prestacaoAux = (PrestacaoContasViagemDTO) prestacaoContasViagemDao.restore(prestacaoContasViagemDto);
			if(prestacaoAux != null){
				prestacaoContasViagemDto.setIdEmpregado(prestacaoAux.getIdEmpregado());
			}
			
			if(prestacaoContasViagemDto.getAprovado().equalsIgnoreCase("S")){
				prestacaoContasViagemDto.setSituacao(PrestacaoContasViagemDTO.APROVADA);
			}else{
				prestacaoContasViagemDto.setSituacao(PrestacaoContasViagemDTO.NAO_APROVADA);
				prestacaoContasViagemDto.setIdItemTrabalho(null);
			}
			
			reqViagemDao.update(requisicaoViagemDto);
			
			if(parecerDto != null)
				prestacaoContasViagemDto.setIdAprovacao(parecerDto.getIdParecer());
			
			prestacaoContasViagemDao.update(prestacaoContasViagemDto);
		
			
		}catch(Exception e){
			rollbackTransaction(tc, e);
		}
		
		
	}

	@Override
	public void delete(TransactionControler tc, SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {}

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new  PrestacaoContasViagemDao();
	}

	@Override
	protected void validaCreate(Object obj) throws Exception { }
	@Override
	protected void validaUpdate(Object obj) throws Exception { }
	@Override
	protected void validaDelete(Object obj) throws Exception { }
	@Override
	protected void validaFind(Object obj) throws Exception { }

}
