package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.AdiantamentoViagemDTO;
import br.com.centralit.citcorpore.bean.ItemControleFinanceiroViagemDTO;
import br.com.centralit.citcorpore.bean.RequisicaoViagemDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.integracao.AdiantamentoViagemDAO;
import br.com.centralit.citcorpore.integracao.ItemControleFinanceiroViagemDAO;
import br.com.centralit.citcorpore.integracao.RequisicaoViagemDAO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.WebUtil;

public class AdiantamentoViagemServiceEjb extends ComplemInfSolicitacaoServicoServiceEjb implements AdiantamentoViagemService{
	
	private AdiantamentoViagemDAO adiantamentoViagemDao = null;
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public IDto deserializaObjeto(String serialize) throws Exception {
		AdiantamentoViagemDTO adiantamentoViagemDto = null;
		if(serialize != null){
			adiantamentoViagemDto = (AdiantamentoViagemDTO) WebUtil.deserializeObject(AdiantamentoViagemDTO.class, serialize);
		}
		return adiantamentoViagemDto;
	}

	@Override
	public void validaCreate(SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {}
	@Override
	public void validaDelete(SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {}
	@Override
	public void validaUpdate(SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		ItemControleFinanceiroViagemService itemService = (ItemControleFinanceiroViagemService) ServiceLocator.getInstance().getService(ItemControleFinanceiroViagemService.class, null); 
		if(itemService.verificaItensAdiantamento(solicitacaoServicoDto.getIdSolicitacaoServico()))
			throw new LogicException(i18n_Message("requisicaoViagem.confimarAdiantamentoObrigatorio"));
	}

	@Override
	public IDto create(TransactionControler tc, SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		return null;
	}

	@Override
	public void update(TransactionControler tc, SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		
		RequisicaoViagemDTO requisicaoViagemDto = new RequisicaoViagemDTO();
		RequisicaoViagemDAO dao = new RequisicaoViagemDAO();
		
		dao.setTransactionControler(tc);
		
		if(solicitacaoServicoDto.getAcaoFluxo().equalsIgnoreCase("E")){
			validaUpdate(solicitacaoServicoDto, model);
			requisicaoViagemDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
			requisicaoViagemDto = (RequisicaoViagemDTO) dao.restore(requisicaoViagemDto);
			requisicaoViagemDto.setEstado(RequisicaoViagemDTO.AGUARDANDO_PRESTACAOCONTAS);
			dao.update(requisicaoViagemDto);
		}
		return;		
	}

	@Override
	public void delete(TransactionControler tc, SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		
	}

	@Override
	protected CrudDAO getDao() throws ServiceException {
		if(adiantamentoViagemDao == null)
			return new AdiantamentoViagemDAO();
		else
			return adiantamentoViagemDao;
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
	public IDto create(IDto model) throws ServiceException, LogicException{
		AdiantamentoViagemDTO adiantamentoViagemDto = (AdiantamentoViagemDTO) model;
		AdiantamentoViagemDAO adiantamentoViagemDao = new AdiantamentoViagemDAO();
		ItemControleFinanceiroViagemDAO itemControleFinanceiroViagemDao = new ItemControleFinanceiroViagemDAO();
		
		TransactionControler tc = new TransactionControlerImpl(adiantamentoViagemDao.getAliasDB());
	
		try {
			adiantamentoViagemDao.setTransactionControler(tc);
			itemControleFinanceiroViagemDao.setTransactionControler(tc);
			
			adiantamentoViagemDto.setDataHora(UtilDatas.getDataHoraAtual());
			
			adiantamentoViagemDto = (AdiantamentoViagemDTO) adiantamentoViagemDao.create(adiantamentoViagemDto);
			if(adiantamentoViagemDto != null){
				Collection<ItemControleFinanceiroViagemDTO> colItens = itemControleFinanceiroViagemDao.listaItensPrecisaAdiantamento(adiantamentoViagemDto.getIdSolicitacaoServico(), adiantamentoViagemDto.getIdEmpregado());
				if(colItens != null){
					for(ItemControleFinanceiroViagemDTO item : colItens){
						item.setIdAdiantamentoViagem(adiantamentoViagemDto.getIdAdiantamentoViagem());
						item.setDataExecucao(UtilDatas.getDataHoraAtual());
						itemControleFinanceiroViagemDao.update(item);
					}
				}
			}
			
			tc.commit();
			tc.close();
			
		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
			e.printStackTrace();
		}

		return adiantamentoViagemDto;
	}
	
	@Override
	public void update(IDto model) throws ServiceException, LogicException {
		AdiantamentoViagemDTO adiantamentoViagemDto = (AdiantamentoViagemDTO) model;
		AdiantamentoViagemDAO adiantamentoViagemDao = new AdiantamentoViagemDAO();
		ItemControleFinanceiroViagemDAO itemControleFinanceiroViagemDao = new ItemControleFinanceiroViagemDAO();

		TransactionControler tc = new TransactionControlerImpl(adiantamentoViagemDao.getAliasDB());
	
		try {
			adiantamentoViagemDao.setTransactionControler(tc);
			itemControleFinanceiroViagemDao.setTransactionControler(tc);
			
			if(adiantamentoViagemDto != null){
				adiantamentoViagemDto.setDataHora(UtilDatas.getDataHoraAtual());
			}
			adiantamentoViagemDao.update(adiantamentoViagemDto);
			if(adiantamentoViagemDto != null){
				Collection<ItemControleFinanceiroViagemDTO> colItens = itemControleFinanceiroViagemDao.listaItensPrecisaAdiantamento(adiantamentoViagemDto.getIdSolicitacaoServico(), adiantamentoViagemDto.getIdEmpregado());
				if(colItens != null){
					for(ItemControleFinanceiroViagemDTO item : colItens){
						item.setIdAdiantamentoViagem(adiantamentoViagemDto.getIdAdiantamentoViagem());
						itemControleFinanceiroViagemDao.update(item);
					}
				}
			}
			
			tc.commit();
			tc.close();
			
		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
			e.printStackTrace();
		}
	}

	@Override
	public Integer recuperaIdAdiantamentoSeExistir(AdiantamentoViagemDTO adiantamentoViagemDto) throws Exception {
		AdiantamentoViagemDAO dao = new AdiantamentoViagemDAO();
		
		List<AdiantamentoViagemDTO> lista = dao.findBySolicitacaoAndEmpregado(adiantamentoViagemDto);
		if(lista != null && !lista.isEmpty()){
			adiantamentoViagemDto = lista.get(0);
			if(adiantamentoViagemDto.getIdAdiantamentoViagem() != null && adiantamentoViagemDto.getIdAdiantamentoViagem() > 0)
				return adiantamentoViagemDto.getIdAdiantamentoViagem();
		}
		return null;
	}

}
