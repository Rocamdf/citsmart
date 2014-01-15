package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.IntegranteViagemDTO;
import br.com.centralit.citcorpore.bean.ParecerDTO;
import br.com.centralit.citcorpore.bean.RequisicaoViagemDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.exception.LogicException;
import br.com.centralit.citcorpore.integracao.ParecerDao;
import br.com.centralit.citcorpore.integracao.RequisicaoViagemDAO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.WebUtil;

@SuppressWarnings({"serial","unchecked"})
public class AutorizacaoViagemServiceEjb extends ComplemInfSolicitacaoServicoServiceEjb implements AutorizacaoViagemService{

	@Override
	public IDto deserializaObjeto(String serialize) throws Exception {
		RequisicaoViagemDTO requisicaoViagemDto = null;

		if (serialize != null) {
			requisicaoViagemDto = (RequisicaoViagemDTO) WebUtil.deserializeObject(RequisicaoViagemDTO.class, serialize);
			if (requisicaoViagemDto != null && requisicaoViagemDto.getIntegranteViagemSerialize() != null) {
				requisicaoViagemDto.setIntegranteViagem(WebUtil.deserializeCollectionFromString(IntegranteViagemDTO.class, requisicaoViagemDto.getIntegranteViagemSerialize()));
			}
		}

		return requisicaoViagemDto;
	}
	
	@Override
	public void delete(TransactionControler tc, SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {}

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new RequisicaoViagemDAO();
	}
	
	@Override
	protected void validaCreate(Object obj) throws Exception {}

	@Override
	protected void validaUpdate(Object obj) throws Exception {}

	@Override
	protected void validaDelete(Object obj) throws Exception {}

	@Override
	protected void validaFind(Object obj) throws Exception {}

	@Override
	public void validaCreate(SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {}

	@Override
	public void validaDelete(SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {}

	@Override
	public void validaUpdate(SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		this.validaAprovacao(solicitacaoServicoDto, model);
	}

	@Override
	public IDto create(TransactionControler tc, SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		return null;
	}

	@Override
	public void update(TransactionControler tc, SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		RequisicaoViagemDTO requisicaoViagemDto = (RequisicaoViagemDTO) model;
		ParecerDTO parecerDto = new ParecerDTO();
		
		

		ParecerDao parecerDao = new ParecerDao();
		RequisicaoViagemDAO requisicaoViagemDao = new RequisicaoViagemDAO();
		validaUpdate(solicitacaoServicoDto, model);
		if(solicitacaoServicoDto.getAcaoFluxo().equalsIgnoreCase("E")){
			if(requisicaoViagemDto.getAutorizado().equalsIgnoreCase("N")){
				requisicaoViagemDto.setEstado(RequisicaoViagemDTO.REJEITADA_PLANEJAMENTO);
			}else{
				requisicaoViagemDto.setEstado(RequisicaoViagemDTO.AGUARDANDO_FINANCEIRO);
			}
		}
		parecerDao.setTransactionControler(tc);
		requisicaoViagemDao.setTransactionControler(tc);
		
		parecerDto.setIdJustificativa(requisicaoViagemDto.getIdJustificativaAutorizacao());
		parecerDto.setIdResponsavel(solicitacaoServicoDto.getUsuarioDto().getIdEmpregado());
		parecerDto.setObservacoes(requisicaoViagemDto.getObservacoes());
		parecerDto.setComplementoJustificativa(requisicaoViagemDto.getComplemJustificativaAutorizacao());
		parecerDto.setAprovado(requisicaoViagemDto.getAutorizado());
		parecerDto.setDataHoraParecer(UtilDatas.getDataHoraAtual());
		
		parecerDto = (ParecerDTO) parecerDao.create(parecerDto);
		
		if(parecerDto !=null){
			requisicaoViagemDto.setIdAprovacao(parecerDto.getIdParecer());
			requisicaoViagemDao.updateNotNull(requisicaoViagemDto);
		}
		
	}
	
	public void validaAprovacao(SolicitacaoServicoDTO solicitacaoServicoDto, IDto model)throws Exception{
		RequisicaoViagemDTO requisicaoViagemDto = (RequisicaoViagemDTO) model;
		if (requisicaoViagemDto.getAutorizado() == null || requisicaoViagemDto.getAutorizado().equalsIgnoreCase("")) {
			throw new LogicException(i18n_Message("autorizacaoViagem.autorizacaoCampoObrigatorio"));
		}else{
			if(requisicaoViagemDto.getAutorizado().equalsIgnoreCase("N")){
				if(requisicaoViagemDto.getIdJustificativaAutorizacao() == null){
					throw new LogicException(i18n_Message("autorizacaoViagem.justificativaCampoObrigatorio"));
				}
				if(requisicaoViagemDto.getComplemJustificativaAutorizacao().equalsIgnoreCase("")){
					throw new LogicException(i18n_Message("autorizacaoViagem.complemJustificativaAutorizacaoCampoObrigatorio"));
				}
			}
		}
		
		
	}
	
}
