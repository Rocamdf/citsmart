package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.ControleFinanceiroViagemDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.IntegranteViagemDTO;
import br.com.centralit.citcorpore.bean.ItemControleFinanceiroViagemDTO;
import br.com.centralit.citcorpore.bean.RequisicaoViagemDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.exception.LogicException;
import br.com.centralit.citcorpore.integracao.ControleFinanceiroViagemDAO;
import br.com.centralit.citcorpore.integracao.IntegranteViagemDao;
import br.com.centralit.citcorpore.integracao.ItemControleFinanceiroViagemDAO;
import br.com.centralit.citcorpore.integracao.RequisicaoViagemDAO;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.WebUtil;

public class ControleFinanceiroViagemServiceEjb extends ComplemInfSolicitacaoServicoServiceEjb implements ControleFinanceiroViagemService {

	private static final long serialVersionUID = 1L;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new ControleFinanceiroViagemDAO();
	}


	@Override
	public Collection<ItemControleFinanceiroViagemDTO> recuperaItensControleFinanceiroBySolicitacao(
			Integer idSolicitacao) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<EmpregadoDTO> recuperaIntegrantesViagemBySolicitacao(Integer idSolicitacao) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public IDto create(TransactionControler tc, SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		ControleFinanceiroViagemDTO controleFinaceiroViagemDto = (ControleFinanceiroViagemDTO) model;
		controleFinaceiroViagemDto.setDataHora(UtilDatas.getDataHoraAtual());
		
		//this.validaObrigatoriedade(controleFinaceiroViagemDto);
		
		ControleFinanceiroViagemDAO controleFinanceiroViagemDao = new ControleFinanceiroViagemDAO();
		SolicitacaoServicoDao solicitacaoServicoDao = new SolicitacaoServicoDao();
		controleFinanceiroViagemDao.setTransactionControler(tc);
		solicitacaoServicoDao.setTransactionControler(tc);
		
		if(solicitacaoServicoDto.getIdSolicitacaoServico() != null){
			controleFinaceiroViagemDto.setIdResponsavel(solicitacaoServicoDto.getUsuarioDto().getIdUsuario());
			controleFinaceiroViagemDto = (ControleFinanceiroViagemDTO) controleFinanceiroViagemDao.create(controleFinaceiroViagemDto);
			if(controleFinaceiroViagemDto != null)
				this.validaItensControle(controleFinaceiroViagemDto, solicitacaoServicoDto.getIdSolicitacaoServico());
		}
		
		return controleFinaceiroViagemDto;
	}

	@Override
	public IDto deserializaObjeto(String serialize) throws Exception {
		ControleFinanceiroViagemDTO controleFinanceiroViagemDTO = null ;
		
		if(serialize!=null){
			controleFinanceiroViagemDTO = (ControleFinanceiroViagemDTO) WebUtil.deserializeObject(ControleFinanceiroViagemDTO.class, serialize);
		}
		return controleFinanceiroViagemDTO;
	}

	@Override
	public void validaCreate(SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void validaDelete(SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void validaUpdate(SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		// TODO Auto-generated method stub	
	}

	@Override
	public void update(TransactionControler tc, SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		ControleFinanceiroViagemDTO controleFinanceiroViagemDto = (ControleFinanceiroViagemDTO) model;
		RequisicaoViagemDTO requisicaoViagemDto = new RequisicaoViagemDTO();
		ControleFinanceiroViagemDAO controleFinanceiroDao = new ControleFinanceiroViagemDAO();
		RequisicaoViagemDAO requisicaoViagemDao = new RequisicaoViagemDAO();
		
		if(solicitacaoServicoDto.getAcaoFluxo().equalsIgnoreCase("E")){
			this.validaObrigatoriedade(controleFinanceiroViagemDto);
			requisicaoViagemDto.setEstado(RequisicaoViagemDTO.AGUARDANDO_APROVACAO);
		}
		controleFinanceiroDao.setTransactionControler(tc);
		requisicaoViagemDao.setTransactionControler(tc);
		
		
		requisicaoViagemDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
		requisicaoViagemDto = (RequisicaoViagemDTO) requisicaoViagemDao.restore(requisicaoViagemDto);
		if(controleFinanceiroViagemDto.getIdControleFinanceiroViagem() == null){
			this.create(tc,solicitacaoServicoDto, model);
			if(requisicaoViagemDto.getIdSolicitacaoServico()!=null){
				requisicaoViagemDao.updateNotNull(requisicaoViagemDto);
			}
			
			return;
		}
		
		if(solicitacaoServicoDto.getIdSolicitacaoServico() != null){
			controleFinanceiroViagemDto.setIdResponsavel(solicitacaoServicoDto.getUsuarioDto().getIdUsuario());
			
			ControleFinanceiroViagemDTO controleFinanceiroAux = new ControleFinanceiroViagemDTO();
			controleFinanceiroAux = (ControleFinanceiroViagemDTO) controleFinanceiroDao.restore(controleFinanceiroViagemDto);
			controleFinanceiroViagemDto.setDataHora(controleFinanceiroAux.getDataHora());
		
			controleFinanceiroDao.update(controleFinanceiroViagemDto);
			if(requisicaoViagemDto.getIdSolicitacaoServico()!=null){
				requisicaoViagemDao.updateNotNull(requisicaoViagemDto);
			}
		}
	}

	@Override
	public void delete(TransactionControler tc, SolicitacaoServicoDTO solicitacaoServicoDto, IDto model)throws Exception {}

	@Override
	protected void validaCreate(Object obj) throws Exception {}
	@Override
	protected void validaUpdate(Object obj) throws Exception {}
	@Override
	protected void validaDelete(Object obj) throws Exception {}
	@Override
	protected void validaFind(Object obj) throws Exception {}
	
	private void validaItensControle(ControleFinanceiroViagemDTO controleFinanceiroViagem, Integer idSolicitacaoServico) throws Exception{
		IntegranteViagemDao integranteDao = new IntegranteViagemDao();
		ItemControleFinanceiroViagemDAO itemDao = new ItemControleFinanceiroViagemDAO();
	
		Collection<IntegranteViagemDTO> colIntegrante = integranteDao.findAllByIdSolicitacao(idSolicitacaoServico);
		
		if(colIntegrante != null){
			for(IntegranteViagemDTO integranteDto : colIntegrante){
				itemDao.atualizaItensControleFinanceiro(controleFinanceiroViagem.getIdControleFinanceiroViagem(), integranteDto.getIdSolicitacaoServico(), integranteDto.getIdEmpregado());
			}
		}
	}


	@Override
	public ControleFinanceiroViagemDTO buscarControleFinanceiroViagemPorIdSolicitacao(Integer idSolicitacaoServico) throws Exception {
		ControleFinanceiroViagemDAO dao = new ControleFinanceiroViagemDAO();
		return dao.buscarControleFinanceiroViagemPorIdSolicitacao(idSolicitacaoServico);
	}
	
	public void validaObrigatoriedade(ControleFinanceiroViagemDTO controleFinanceiroViagemDto) throws Exception {
		if (controleFinanceiroViagemDto.getIdMoeda() == null) {
			throw new LogicException(i18n_Message("controleFinanceiro.MoedaObrigatorio"));
		}
		
		if(!this.verificaItensPorIntegrantesViagem(controleFinanceiroViagemDto.getIdSolicitacaoServico())){
			throw new LogicException(i18n_Message("controleFinanceiro.ItemControleObrigatorio"));
		}
	}
	
	public boolean verificaItensPorIntegrantesViagem(Integer idSolicitacaoServico) throws Exception{
		Collection<IntegranteViagemDTO> listaIntegrantes = new ArrayList<IntegranteViagemDTO>();
		Collection<ItemControleFinanceiroViagemDTO> listaItensControleDto = new ArrayList<ItemControleFinanceiroViagemDTO>();
		
		IntegranteViagemDao integranteViagemDao = new IntegranteViagemDao();
		ItemControleFinanceiroViagemDAO itensDao = new ItemControleFinanceiroViagemDAO();
		
		listaIntegrantes = integranteViagemDao.findAllByIdSolicitacao(idSolicitacaoServico);
		if(listaIntegrantes != null){
			for(IntegranteViagemDTO integrante : listaIntegrantes){
				listaItensControleDto = itensDao.listByidSolicitacaoAndIdEmpregado(idSolicitacaoServico, integrante.getIdEmpregado());
				if(listaItensControleDto == null){
					return false;
				}
			}
		}
		return true;
	}

}
