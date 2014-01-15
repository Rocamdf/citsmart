package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ItemControleFinanceiroViagemDTO;
import br.com.centralit.citcorpore.bean.ItemPrestacaoContasViagemDTO;
import br.com.centralit.citcorpore.bean.PrestacaoContasViagemDTO;
import br.com.centralit.citcorpore.bean.RequisicaoViagemDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.integracao.ItemPrestacaoContasViagemDao;
import br.com.centralit.citcorpore.integracao.PrestacaoContasViagemDao;
import br.com.centralit.citcorpore.integracao.RequisicaoViagemDAO;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.WebUtil;

/**
 * @author ronnie.lopes
 * 
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class PrestacaoContasViagemServiceEjb extends ComplemInfSolicitacaoServicoServiceEjb implements PrestacaoContasViagemService {

    private static final long serialVersionUID = -2253183314661440900L;

    protected CrudDAO getDao() throws ServiceException {
    	return new PrestacaoContasViagemDao();
    }

    protected void validaCreate(Object obj) throws Exception {}
    protected void validaDelete(Object obj) throws Exception {}
    protected void validaUpdate(Object obj) throws Exception {}
    protected void validaFind(Object obj) throws Exception { }

    
	public Collection list(List ordenacao) throws LogicException, RemoteException, ServiceException {
		return null;
    }

    public Collection list(String ordenacao) throws LogicException, RemoteException, ServiceException {
    	return null;
    }

	@Override
	public IDto create(IDto model) throws ServiceException, LogicException {
		PrestacaoContasViagemDTO prestacaoContasViagemDto = (PrestacaoContasViagemDTO) model;
		TransactionControler tc = new TransactionControlerImpl(getDao().getAliasDB());
		try {
			
			this.validaCreate(model);
			
			PrestacaoContasViagemDao prestacaoContasViagemDao = new PrestacaoContasViagemDao();
			ItemPrestacaoContasViagemDao itemPrestacaoContasViagemDao = new ItemPrestacaoContasViagemDao();
			
			prestacaoContasViagemDao.setTransactionControler(tc);
			itemPrestacaoContasViagemDao.setTransactionControler(tc);
			
			prestacaoContasViagemDto.setDataHora(UtilDatas.getDataHoraAtual());
			
			prestacaoContasViagemDto = (PrestacaoContasViagemDTO) prestacaoContasViagemDao.create(prestacaoContasViagemDto);
			
			if(prestacaoContasViagemDto.getListaItemPrestacaoContasViagemDTO() != null){
				for(ItemPrestacaoContasViagemDTO itemPrestacaoContasViagemDTO : prestacaoContasViagemDto.getListaItemPrestacaoContasViagemDTO()){
					itemPrestacaoContasViagemDTO.setIdPrestacaoContasViagem(prestacaoContasViagemDto.getIdPrestacaoContasViagem());
					itemPrestacaoContasViagemDao.create(itemPrestacaoContasViagemDTO);
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			this.rollbackTransaction(tc, e);
		}
		
		return prestacaoContasViagemDto;
	}

	@Override
	public IDto deserializaObjeto(String serialize) throws Exception {
		PrestacaoContasViagemDTO prestacaoContasViagemDto = null;
		if(serialize != null){
			prestacaoContasViagemDto = (PrestacaoContasViagemDTO) WebUtil.deserializeObject(PrestacaoContasViagemDTO.class, serialize);
			if(prestacaoContasViagemDto != null && prestacaoContasViagemDto.getItensPrestacaoContasViagemSerialize() != null){
				prestacaoContasViagemDto.setListaItemPrestacaoContasViagemDTO(WebUtil.deserializeCollectionFromString(ItemPrestacaoContasViagemDTO.class, prestacaoContasViagemDto.getItensPrestacaoContasViagemSerialize()));
			}
		}
		return prestacaoContasViagemDto;
	}

	@Override
	public void validaCreate(SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		PrestacaoContasViagemDTO prestacaoContasViagemDto = (PrestacaoContasViagemDTO) model;
		if(!this.validaValorAdiantato(prestacaoContasViagemDto)){
			throw new LogicException(i18n_Message("requisicaoViagem.valorDiferencaPrestacaoAdiantamento"));
		}
	}
	
	@Override
	public void validaDelete(SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {}

	@Override
	public void validaUpdate(SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		PrestacaoContasViagemDTO prestacaoContasViagemDto = (PrestacaoContasViagemDTO) model;
		if(!this.validaValorAdiantato(prestacaoContasViagemDto)){
			throw new LogicException(i18n_Message("requisicaoViagem.valorDiferencaPrestacaoAdiantamento"));
		}
	}

	@Override
	public IDto create(TransactionControler tc, SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		PrestacaoContasViagemDTO prestacaoContasViagemDto = (PrestacaoContasViagemDTO) model;
		
		RequisicaoViagemDAO reqViagemDao = new RequisicaoViagemDAO();
		RequisicaoViagemDTO reqViagemDto = new RequisicaoViagemDTO();
		
		PrestacaoContasViagemDao prestacaoContasViagemDao = new PrestacaoContasViagemDao();
		ItemPrestacaoContasViagemDao itemPrestacaoContasViagemDao = new ItemPrestacaoContasViagemDao();
		SolicitacaoServicoDao solicitacaoServicoDao = new SolicitacaoServicoDao();
		
		prestacaoContasViagemDto.setDataHora(UtilDatas.getDataHoraAtual());
		
		reqViagemDao.setTransactionControler(tc);
		prestacaoContasViagemDao.setTransactionControler(tc);
		itemPrestacaoContasViagemDao.setTransactionControler(tc);
		solicitacaoServicoDao.setTransactionControler(tc);
		
		reqViagemDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
		
		reqViagemDto = (RequisicaoViagemDTO) reqViagemDao.restore(reqViagemDto);
		
		prestacaoContasViagemDto.setDataHora(UtilDatas.getDataHoraAtual());
		prestacaoContasViagemDto.setIdEmpregado(solicitacaoServicoDto.getUsuarioDto().getIdEmpregado());
		prestacaoContasViagemDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
		if(solicitacaoServicoDto.getAcaoFluxo().equalsIgnoreCase("E")){
			validaCreate(solicitacaoServicoDto, model);
			prestacaoContasViagemDto.setSituacao(PrestacaoContasViagemDTO.AGUARDANDO_CONFERENCIA);
			prestacaoContasViagemDto.setIdItemTrabalho(null);
			if(reqViagemDto != null){
				reqViagemDto.setEstado(RequisicaoViagemDTO.AGUARDANDO_CONFERENCIA);
				reqViagemDao.update(reqViagemDto);
			}
		}
		try{
			prestacaoContasViagemDto = (PrestacaoContasViagemDTO) prestacaoContasViagemDao.create(prestacaoContasViagemDto);
			
			if(prestacaoContasViagemDto.getListaItemPrestacaoContasViagemDTO() != null && prestacaoContasViagemDto.getListaItemPrestacaoContasViagemDTO().size() > 0){
				for(ItemPrestacaoContasViagemDTO itemPrestacaoContasViagemDTO : prestacaoContasViagemDto.getListaItemPrestacaoContasViagemDTO()){
					itemPrestacaoContasViagemDTO.setIdPrestacaoContasViagem(prestacaoContasViagemDto.getIdPrestacaoContasViagem());
					itemPrestacaoContasViagemDao.create(itemPrestacaoContasViagemDTO);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			this.rollbackTransaction(tc, e);
		}
		
		return prestacaoContasViagemDto;
	}

	@Override
	public void update(TransactionControler tc, SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		PrestacaoContasViagemDTO prestacaoContasViagemDto = (PrestacaoContasViagemDTO) model;

		
		if(prestacaoContasViagemDto.getIdPrestacaoContasViagem() == null){
			create(tc, solicitacaoServicoDto, model);
			return;
		}
		
		if(solicitacaoServicoDto.getAcaoFluxo().equalsIgnoreCase("E")){
			prestacaoContasViagemDto.setSituacao(PrestacaoContasViagemDTO.AGUARDANDO_CONFERENCIA);
			prestacaoContasViagemDto.setIdItemTrabalho(null);
		}		
		
		
		PrestacaoContasViagemDTO contasViagemDtoAux = new PrestacaoContasViagemDTO();
		
		PrestacaoContasViagemDao contasViagemDao = new PrestacaoContasViagemDao();
		ItemPrestacaoContasViagemDao itensViagemDao = new ItemPrestacaoContasViagemDao();
		
		contasViagemDao.setTransactionControler(tc);
		itensViagemDao.setTransactionControler(tc);
		
		contasViagemDtoAux = (PrestacaoContasViagemDTO) contasViagemDao.restore(prestacaoContasViagemDto);
		if(contasViagemDtoAux != null){
			contasViagemDtoAux.setListaItemPrestacaoContasViagemDTO(itensViagemDao.listByPrestacaoContas(prestacaoContasViagemDto.getIdPrestacaoContasViagem()));
		}
		
		prestacaoContasViagemDto.setDataHora(UtilDatas.getDataHoraAtual());
		prestacaoContasViagemDto.setIdEmpregado(solicitacaoServicoDto.getUsuarioDto().getIdEmpregado());
		
		if(solicitacaoServicoDto.getAcaoFluxo().equalsIgnoreCase("E")){
			this.validaUpdate(solicitacaoServicoDto, prestacaoContasViagemDto);
			RequisicaoViagemDAO reqViagemDao = new RequisicaoViagemDAO();
			RequisicaoViagemDTO reqViagemDto = new RequisicaoViagemDTO();
			reqViagemDao.setTransactionControler(tc);
			reqViagemDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
			reqViagemDto = (RequisicaoViagemDTO) reqViagemDao.restore(reqViagemDto);
			if(reqViagemDto != null){
				reqViagemDto.setEstado(RequisicaoViagemDTO.AGUARDANDO_CONFERENCIA);
				reqViagemDao.update(reqViagemDto);
			}
		}
		
		try{
			contasViagemDao.update(prestacaoContasViagemDto);
			
			if(prestacaoContasViagemDto.getListaItemPrestacaoContasViagemDTO() != null && prestacaoContasViagemDto.getListaItemPrestacaoContasViagemDTO().size() > 0){
				if(contasViagemDtoAux.getListaItemPrestacaoContasViagemDTO() != null && contasViagemDtoAux.getListaItemPrestacaoContasViagemDTO().size() > 0){
					for(ItemPrestacaoContasViagemDTO item : contasViagemDtoAux.getListaItemPrestacaoContasViagemDTO()){
						itensViagemDao.delete(item);
					}
					for(ItemPrestacaoContasViagemDTO itemPrestacaoContasViagemDTO : prestacaoContasViagemDto.getListaItemPrestacaoContasViagemDTO()){
						itemPrestacaoContasViagemDTO.setIdPrestacaoContasViagem(prestacaoContasViagemDto.getIdPrestacaoContasViagem());
						itensViagemDao.create(itemPrestacaoContasViagemDTO);
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			this.rollbackTransaction(tc, e);
		}
	}

	@Override
	public void delete(TransactionControler tc, SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {}

	@Override
	public Integer recuperaIdPrestacaoSeExistir(Integer idSolicitacaoServico, Integer idEmpregado) throws ServiceException, Exception {
		PrestacaoContasViagemDao dao = new PrestacaoContasViagemDao();
		PrestacaoContasViagemDTO prestacaoContasViagemDto = dao.findBySolicitacaoAndEmpregado(idSolicitacaoServico, idEmpregado);
		if(prestacaoContasViagemDto != null && prestacaoContasViagemDto.getIdPrestacaoContasViagem() != null)
			return prestacaoContasViagemDto.getIdPrestacaoContasViagem();
		else
			return null;
	}
	
	public boolean validaPeriodo(PrestacaoContasViagemDTO prestacaoContasViagemDto) throws ServiceException, Exception{
		RequisicaoViagemDTO requisicaoViagemDto = new RequisicaoViagemDTO();
		RequisicaoViagemDAO requisicaoViagemDao = new RequisicaoViagemDAO();
		
		requisicaoViagemDto.setIdRequisicaoMudanca(prestacaoContasViagemDto.getIdSolicitacaoServico());
		requisicaoViagemDto = (RequisicaoViagemDTO) requisicaoViagemDao.restore(requisicaoViagemDto);
		
		Timestamp dataRetorno = UtilDatas.strToTimestamp(requisicaoViagemDto.getDataFimViagem().toString());
		Timestamp dataPrestacao = prestacaoContasViagemDto.getDataHora();
		Timestamp dataLitimte = (Timestamp) UtilDatas.incrementaDiasEmData(dataRetorno, 5);
		
		if(dataPrestacao.compareTo(dataLitimte) >= 0){
			return true;
		}
		
		return false;
		
	}

	public boolean validaValorAdiantato(PrestacaoContasViagemDTO prestacaoContasViagemDto) throws ServiceException, Exception{
		ItemControleFinanceiroViagemService itemControleService = (ItemControleFinanceiroViagemService) ServiceLocator.getInstance().getService(ItemControleFinanceiroViagemService.class, null);
		Collection<ItemControleFinanceiroViagemDTO> colItens = itemControleService.listaItensAdiantamento(prestacaoContasViagemDto.getIdSolicitacaoServico(), prestacaoContasViagemDto.getIdEmpregado());
		double valorTotalAdiantado = 0;
		double valorPrestacaoContas = 0;
		if(colItens != null){
			for(ItemControleFinanceiroViagemDTO item : colItens){
				valorTotalAdiantado += item.getValorAdiantamento();
			}
		}
		if(prestacaoContasViagemDto.getListaItemPrestacaoContasViagemDTO() != null){
			for(ItemPrestacaoContasViagemDTO item : prestacaoContasViagemDto.getListaItemPrestacaoContasViagemDTO()){
				valorPrestacaoContas += item.getValor();
			}
		}
		
		
		if(valorPrestacaoContas >= valorTotalAdiantado){
			return true;
		}
		return false;
	}
	
	public PrestacaoContasViagemDTO recuperaCorrecao(RequisicaoViagemDTO requisicaoViagemDto) throws Exception{
		PrestacaoContasViagemDao dao = new PrestacaoContasViagemDao();
		PrestacaoContasViagemDTO prestacaoContas = null;
		List result = (List) dao.findBySolicitacaoEmpregadoSeCorrecao(requisicaoViagemDto.getIdSolicitacaoServico(), requisicaoViagemDto.getUsuarioDto().getIdEmpregado());
		if(result != null)
			prestacaoContas = (PrestacaoContasViagemDTO) result.get(0);
		return prestacaoContas;
		
	}
}

