package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ItemControleFinanceiroViagemDTO;
import br.com.centralit.citcorpore.integracao.ItemControleFinanceiroViagemDAO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings({"rawtypes","unchecked"})
public class ItemControleFinanceiroViagemServiceEjb extends CrudServicePojoImpl implements ItemControleFinanceiroViagemService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3414511296205878674L;

	@Override
	public boolean consultarItemAtivo(ItemControleFinanceiroViagemDTO obj)
			throws Exception {
		return false;
	}

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new ItemControleFinanceiroViagemDAO();
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
	public void deletarItem(IDto model, DocumentHTML document)
			throws ServiceException, Exception {
		ItemControleFinanceiroViagemDTO itemControleDto = (ItemControleFinanceiroViagemDTO) document.getBean();
		itemControleDto.setDataFim(UtilDatas.getDataAtual());
		document.alert(i18n_Message("MSG07"));
		
	}
	
	
	public Collection recuperaItensControleFinanceiro(ItemControleFinanceiroViagemDTO itemControle) throws Exception{
		ItemControleFinanceiroViagemDAO dao = new ItemControleFinanceiroViagemDAO();
		return dao.findByIdSolicitacaoAndIdEmpregado(itemControle.getIdSolicitacaoServico(), itemControle.getIdEmpregado());
	}

	@Override
	public boolean verificaExisteItens(Integer idSolicitacao) throws Exception {
		ItemControleFinanceiroViagemDAO dao = new ItemControleFinanceiroViagemDAO();
		Collection<ItemControleFinanceiroViagemDTO> lista = dao.listByidSolicitacao(idSolicitacao);
		if(lista == null || lista.isEmpty())
			return false;
		else
			return true;
	}
	
	public Collection<ItemControleFinanceiroViagemDTO> listaItensAdiantamento(Integer idSolicitacaoServico, Integer idEmpregado) throws Exception{
		ItemControleFinanceiroViagemDAO dao = new ItemControleFinanceiroViagemDAO();
		return dao.listaItensPrecisaAdiantamento(idSolicitacaoServico, idEmpregado);
	}
	
	public boolean verificaItensAdiantamento(Integer idSolicitacaoServico) throws Exception{
		ItemControleFinanceiroViagemDAO dao = new ItemControleFinanceiroViagemDAO();
		
		Collection<ItemControleFinanceiroViagemDTO> res = dao.listaItensPrecisaAdiantamentoPorSolicitacao(idSolicitacaoServico);
		if(res != null){
			for(ItemControleFinanceiroViagemDTO lista : res){
				if(lista.getIdAdiantamentoViagem() == null)
					return true;
			}
		}
		return false;
	}

	@Override
	public Collection<ItemControleFinanceiroViagemDTO> listaItensCompra(Integer idSolicitacaoServico, Integer idEmpregado) throws Exception {
		ItemControleFinanceiroViagemDAO dao = new ItemControleFinanceiroViagemDAO();
		return dao.listaItensPrecisaCompra(idSolicitacaoServico, idEmpregado);
	}
	
	
	@Override
	public void update(IDto model) throws ServiceException, LogicException{
		ItemControleFinanceiroViagemDTO itemControleFinanceiroViagemDto = (ItemControleFinanceiroViagemDTO) model;
		ItemControleFinanceiroViagemDAO itemDao = new ItemControleFinanceiroViagemDAO();
		
		TransactionControler tc = new TransactionControlerImpl(itemDao.getAliasDB());

		try {
			itemDao.setTransactionControler(tc);
			ItemControleFinanceiroViagemDTO itemControleFinanceiroViagemDtoAux = (ItemControleFinanceiroViagemDTO) itemDao.restore(itemControleFinanceiroViagemDto);
			if(itemControleFinanceiroViagemDto.getIdTipoMovimFinanceiraViagem() == null)
				itemControleFinanceiroViagemDto.setIdTipoMovimFinanceiraViagem(itemControleFinanceiroViagemDtoAux.getIdTipoMovimFinanceiraViagem());
			if(itemControleFinanceiroViagemDto.getOrigemCompras() != null && itemControleFinanceiroViagemDto.getOrigemCompras().equalsIgnoreCase("S")){
				
				itemControleFinanceiroViagemDtoAux.setAssento(itemControleFinanceiroViagemDto.getAssento());
				itemControleFinanceiroViagemDtoAux.setLocalizador(itemControleFinanceiroViagemDto.getLocalizador());
				itemControleFinanceiroViagemDtoAux.setTipoPassagem(itemControleFinanceiroViagemDto.getTipoPassagem());
				itemControleFinanceiroViagemDtoAux.setObservacao(itemControleFinanceiroViagemDto.getObservacao());
				
				itemDao.update(itemControleFinanceiroViagemDtoAux);
			}else{
				super.update(itemControleFinanceiroViagemDto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			rollbackTransaction(tc, e);
		}		
	}	
	
}
