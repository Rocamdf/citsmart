package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.ControleFinanceiroViagemDTO;
import br.com.centralit.citcorpore.bean.IntegranteViagemDTO;
import br.com.centralit.citcorpore.bean.ItemControleFinanceiroViagemDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.exception.LogicException;
import br.com.centralit.citcorpore.integracao.ControleFinanceiroViagemDAO;
import br.com.centralit.citcorpore.integracao.ItemControleFinanceiroViagemDAO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.WebUtil;

public class CompraViagemServiceEjb extends ComplemInfSolicitacaoServicoServiceEjb implements CompraViagemService{

	
	private static final long serialVersionUID = 1L;

	@Override
	public IDto deserializaObjeto(String serialize) throws Exception {
		ControleFinanceiroViagemDTO controleFinanceiroViagemDTO = null ;
		if(serialize!=null){
			controleFinanceiroViagemDTO = (ControleFinanceiroViagemDTO) WebUtil.deserializeObject(ControleFinanceiroViagemDTO.class, serialize);
		}
		return controleFinanceiroViagemDTO;
	}

	@Override
	public IDto create(TransactionControler tc, SolicitacaoServicoDTO solicitacaoServicoDto, IDto model)throws Exception {
			return null;
	}

	@Override
	public void update(TransactionControler tc, SolicitacaoServicoDTO solicitacaoServicoDto, IDto model)throws Exception {
		ControleFinanceiroViagemDTO controleFinanceiroViagemDto = (ControleFinanceiroViagemDTO) model;
		
		
		if(solicitacaoServicoDto.getAcaoFluxo().equalsIgnoreCase("E")){
			if(controleFinanceiroViagemDto.getConfirma() == null || !controleFinanceiroViagemDto.getConfirma().equalsIgnoreCase("S")){
				//throw new LogicException(i18n_Message("requisicaoViagem.cidadeOrigemCampoObrigatorio"));
				throw new LogicException("Necessária a Confirmação da Compra dos Itens para Avançar o fluxo");
			}
		}
		
		ItemControleFinanceiroViagemDAO itemControleFinanceiroViagemDao = new ItemControleFinanceiroViagemDAO();
		ControleFinanceiroViagemDAO controleFinanceiroViagemDao = new ControleFinanceiroViagemDAO();
		
		itemControleFinanceiroViagemDao.setTransactionControler(tc);
		controleFinanceiroViagemDao.setTransactionControler(tc);
		
		RequisicaoViagemService reqViagemService = (RequisicaoViagemService) ServiceLocator.getInstance().getService(RequisicaoViagemService.class, null);
		
		Collection<IntegranteViagemDTO> colIntegrantes =  reqViagemService.recuperaIntegrantesViagemBySolicitacao(solicitacaoServicoDto.getIdSolicitacaoServico());
		Collection<ItemControleFinanceiroViagemDTO> colItems = new ArrayList<ItemControleFinanceiroViagemDTO>();
		
		controleFinanceiroViagemDto.setIdResponsavelCompras(solicitacaoServicoDto.getUsuarioDto().getIdEmpregado());
		
		ControleFinanceiroViagemDTO controleAux = new ControleFinanceiroViagemDTO();
		controleAux = (ControleFinanceiroViagemDTO) controleFinanceiroViagemDao.restore(controleFinanceiroViagemDto);
		
		if(controleAux != null)
			controleFinanceiroViagemDto.setIdResponsavel(controleAux.getIdResponsavel());
		
		try {
			controleFinanceiroViagemDao.update(controleFinanceiroViagemDto);
			
			if(controleFinanceiroViagemDto.getConfirma() != null && controleFinanceiroViagemDto.getConfirma().equalsIgnoreCase("S")){
				if(colIntegrantes != null){
					for(IntegranteViagemDTO integrante : colIntegrantes){
						colItems = itemControleFinanceiroViagemDao.listaItensPrecisaCompra(solicitacaoServicoDto.getIdSolicitacaoServico(), integrante.getIdEmpregado());
						if(colItems != null){
							for(ItemControleFinanceiroViagemDTO itemDto : colItems){
								itemDto.setSituacao("Comprado");
								itemDto.setDataExecucao(UtilDatas.getDataHoraAtual());
								itemControleFinanceiroViagemDao.update(itemDto);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			rollbackTransaction(tc, e);
			e.printStackTrace();
		}
	}

	@Override
	public void delete(TransactionControler tc, SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {

	}

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new ControleFinanceiroViagemDAO();
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
	public void validaUpdate(SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {}
}
