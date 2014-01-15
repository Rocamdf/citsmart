package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ControleFinanceiroViagemDTO;
import br.com.centralit.citcorpore.bean.IntegranteViagemDTO;
import br.com.centralit.citcorpore.bean.ItemControleFinanceiroViagemDTO;
import br.com.centralit.citcorpore.bean.RequisicaoViagemDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.integracao.ControleFinanceiroViagemDAO;
import br.com.centralit.citcorpore.integracao.IntegranteViagemDao;
import br.com.centralit.citcorpore.integracao.ItemControleFinanceiroViagemDAO;
import br.com.centralit.citcorpore.integracao.RequisicaoViagemDAO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.util.UtilDatas;


public class ControleFinanceiroViagemImprevistoServiceEjb extends ComplemInfSolicitacaoServicoServiceEjb implements ControleFinanceiroViagemImprevistoService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6090732193076961992L;

	
	public IDto createAll( IDto model) throws ServiceException, LogicException{
		ControleFinanceiroViagemDTO controleFinanceiroViagemDto = (ControleFinanceiroViagemDTO) model;
		ControleFinanceiroViagemDAO controleDao = new  ControleFinanceiroViagemDAO();
		ItemControleFinanceiroViagemDAO itemDao = new ItemControleFinanceiroViagemDAO();
		
		TransactionControler tc = new TransactionControlerImpl(controleDao.getAliasDB());
		
		controleFinanceiroViagemDto.setDataHora(UtilDatas.getDataHoraAtual());
		
		try {
			
			controleDao.setTransactionControler(tc);
			itemDao.setTransactionControler(tc);
			
			controleFinanceiroViagemDto = (ControleFinanceiroViagemDTO) controleDao.create(controleFinanceiroViagemDto);
			
			if(controleFinanceiroViagemDto != null){
				if(controleFinanceiroViagemDto.getColItens() != null){
					
					for(ItemControleFinanceiroViagemDTO item : controleFinanceiroViagemDto.getColItens()){
						item.setIdControleFinanceiroViagem(controleFinanceiroViagemDto.getIdControleFinanceiroViagem());
						item.setIdAdiantamentoViagem(null);
						
						item.setDataHoraPrazoCotacao(UtilDatas.strToTimestamp(item.getPrazoCotacao() + " " + item.getHoraCotacao() + ":00"));
						
						itemDao.create(item);
					}
					
				}
			}
		} catch (Exception e) {
			rollbackTransaction(tc, e);
			e.printStackTrace();
		}
		
		
		return controleFinanceiroViagemDto;
	}
	
	@Override
	public IDto deserializaObjeto(String serialize) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validaCreate(SolicitacaoServicoDTO solicitacaoServicoDto,
			IDto model) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void validaDelete(SolicitacaoServicoDTO solicitacaoServicoDto,
			IDto model) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void validaUpdate(SolicitacaoServicoDTO solicitacaoServicoDto,
			IDto model) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IDto create(TransactionControler tc, SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(TransactionControler tc,	SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(TransactionControler tc, SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<RequisicaoViagemDTO> recuperaRequisicoesViagem(ControleFinanceiroViagemDTO controleFinanceiroViagemDto, Integer paginaSelecionada, Integer itensPorPagina) throws Exception {
		 RequisicaoViagemDAO dao = new RequisicaoViagemDAO();
		 RequisicaoViagemDTO requisicaoViagemDto = new RequisicaoViagemDTO();
		 
		 requisicaoViagemDto.setIdSolicitacaoServico(controleFinanceiroViagemDto.getIdSolicitacaoServico());
		 requisicaoViagemDto.setDataInicio(controleFinanceiroViagemDto.getDataInicio());
		 requisicaoViagemDto.setDataFim(controleFinanceiroViagemDto.getDataFim());
		 
	        try{
	            return dao.recuperaRequisicoesViagem(requisicaoViagemDto, paginaSelecionada, itensPorPagina);
	        } catch (Exception e) {
	            throw new ServiceException(e);
	        }
	}

	@Override
	protected CrudDAO getDao() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
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
	
	public Collection<IntegranteViagemDTO> recuperaIntegrantePorSolicitacao(Integer idSolictacaoServico) throws Exception{
		IntegranteViagemDao Dao = new IntegranteViagemDao();
		return Dao.findAllByIdSolicitacao(idSolictacaoServico);
	}

	@Override
	public Integer calculaTotalPaginas(Integer itensPorPagina, ControleFinanceiroViagemDTO controleFinanceiroViagemDto) throws Exception {
		RequisicaoViagemDAO dao = new RequisicaoViagemDAO();
		RequisicaoViagemDTO requisicaoViagemDto = new RequisicaoViagemDTO();
		 
		requisicaoViagemDto.setIdSolicitacaoServico(controleFinanceiroViagemDto.getIdSolicitacaoServico());
		requisicaoViagemDto.setDataInicio(controleFinanceiroViagemDto.getDataInicio());
		requisicaoViagemDto.setDataFim(controleFinanceiroViagemDto.getDataFim());

		try{
			return dao.calculaTotalPaginas(itensPorPagina, requisicaoViagemDto);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
}