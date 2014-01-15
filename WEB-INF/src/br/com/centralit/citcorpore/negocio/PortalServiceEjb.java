package br.com.centralit.citcorpore.negocio;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.AcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.AcordoServicoContratoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.ItemPedidoPortalDTO;
import br.com.centralit.citcorpore.bean.PedidoPortalDTO;
import br.com.centralit.citcorpore.bean.PortalDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.AcordoNivelServicoDao;
import br.com.centralit.citcorpore.integracao.ItemPedidoPortalDAO;
import br.com.centralit.citcorpore.integracao.PedidoPortalDAO;
import br.com.centralit.citcorpore.integracao.PortalDao;
import br.com.centralit.citcorpore.integracao.ServicoContratoDao;
import br.com.centralit.citcorpore.integracao.ServicoDao;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoDao;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilStrings;
public class PortalServiceEjb extends CrudServicePojoImpl implements PortalService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		
		return new PortalDao();
	}

	@Override
	protected void validaCreate(Object arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validaDelete(Object arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validaFind(Object arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validaUpdate(Object arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Collection findByCondition(Integer id) throws ServiceException, Exception {
		return this.getPortalDao().findByCondition(id);
	}
	
	 public PortalDao getPortalDao() throws ServiceException {
		 return (PortalDao) getDao();
    }
	 
	 @SuppressWarnings("unchecked")
	public Collection<PortalDTO> findByCondition(Integer idUsuario, Integer idItem) throws ServiceException, Exception{
		 return this.getPortalDao().findByCondition(idUsuario, idItem);
	 }

	@Override
	public Collection<PortalDTO> listByUsuario(Integer idUsuario)
			throws Exception {
		return this.getPortalDao().listByUsuario(idUsuario);
	}
	
	public PedidoPortalDTO criarPedidoSolicitacao(PedidoPortalDTO pedidoPortalDTO, UsuarioDTO usuarioDTO) throws ServiceException, Exception {
		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		SolicitacaoServicoDao solicitacaoServicoDao = new SolicitacaoServicoDao();
		PedidoPortalDAO pedidoPortalDAO = new PedidoPortalDAO();
		ItemPedidoPortalDAO itemPedidoPortalDAO = new ItemPedidoPortalDAO();
		ServicoDao servicoDao = new ServicoDao();
		ServicoContratoDao servicoContratoDao = new ServicoContratoDao();
		TransactionControler tc = new TransactionControlerImpl(solicitacaoServicoDao.getAliasDB());
		List<ServicoContratoDTO> listaServicos = pedidoPortalDTO.getListaServicoContrato();
		List<SolicitacaoServicoDTO> listaSolicitacoes = new ArrayList<SolicitacaoServicoDTO>();
		try {
			tc.start();
			pedidoPortalDAO.setTransactionControler(tc);
			servicoDao.setTransactionControler(tc);
			itemPedidoPortalDAO.setTransactionControler(tc);
			pedidoPortalDTO =  (PedidoPortalDTO) pedidoPortalDAO.create(pedidoPortalDTO);
			
			double valorTotal = 0.0;
			if(listaServicos != null) {
				for (ServicoContratoDTO servicoContratoDTO : listaServicos) {	
					ServicoContratoDTO servicoContratoDTOAux = (ServicoContratoDTO) servicoContratoDao.restore(servicoContratoDTO);
					
					SolicitacaoServicoDTO solicitacaoServicoDto = new SolicitacaoServicoDTO();
					ItemPedidoPortalDTO itemPedidoPortalDTO = new ItemPedidoPortalDTO();
					valorTotal += (servicoContratoDTO.getValorServico() == null ? 0.0 : servicoContratoDTO.getValorServico());
					
					solicitacaoServicoDto.setDescricao(UtilStrings.getParameter(servicoContratoDTO.getDescricao() + "<br>" +servicoContratoDTO.getObservacaoPortal()));
					
					
					/*Solicitação*/
					solicitacaoServicoDto.setIdContrato(servicoContratoDTOAux.getIdContrato());
					solicitacaoServicoDto.setIdServico(servicoContratoDTOAux.getIdServico());
					/*Restaurando o tipo demanda serviço*/
					ServicoDTO servicoDto = new ServicoDTO();
					servicoDto.setIdServico(servicoContratoDTOAux.getIdServico());
					servicoDto = (ServicoDTO) servicoDao.restore(servicoDto);
					solicitacaoServicoDto.setIdTipoDemandaServico(servicoDto.getIdTipoDemandaServico());
					
					setarValoresPadrao(solicitacaoServicoDto, usuarioDTO);
					relacionaImpactoUrgencia(solicitacaoServicoDto);
					
					solicitacaoServicoDto = (SolicitacaoServicoDTO) solicitacaoServicoService.create(solicitacaoServicoDto, tc, true, true, true);					
					
					listaSolicitacoes.add(solicitacaoServicoDto);
					
					itemPedidoPortalDTO.setIdPedidoPortal(pedidoPortalDTO.getIdPedidoPortal());;
					itemPedidoPortalDTO.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
					itemPedidoPortalDTO.setValor((servicoContratoDTO.getValorServico() == null ? 0.0 : servicoContratoDTO.getValorServico()));
					itemPedidoPortalDAO.create(itemPedidoPortalDTO);
						
				}
			}
			/*Pedido*/
			pedidoPortalDTO.setPrecoTotal(valorTotal);
			pedidoPortalDAO.update(pedidoPortalDTO);
			
			pedidoPortalDTO.setListaSolicitacoes(listaSolicitacoes);
			
			tc.commit();
			tc.close();
            tc = null;
            
		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
			throw new ServiceException(e);
		}
		return pedidoPortalDTO;
		
	}
	
	private void setarValoresPadrao(SolicitacaoServicoDTO solicitacaoServicoDto, UsuarioDTO usuario) throws Exception{
		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		String ORIGEM = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ORIGEM_PADRAO_SOLICITACAO, "");
		Integer idOrigem = (ORIGEM.trim().equalsIgnoreCase("") ?  0 : Integer.valueOf(ORIGEM.trim()));
		/*Inicializações obrigatórias*/
		solicitacaoServicoDto.setIdOrigem(idOrigem);
		solicitacaoServicoDto.setSituacao("EmAndamento");
		solicitacaoServicoDto.setRegistroexecucao("");
		solicitacaoServicoDto.setIdSolicitante(usuario.getIdEmpregado());
		
		/*Setando as informações do usuário*/
		solicitacaoServicoDto.setUsuarioDto(usuario);
		solicitacaoServicoDto.setRegistradoPor(usuario.getNomeUsuario());
		
		/*Restaurando as informações do empregado*/
		EmpregadoDTO empregadoDto = empregadoService.restoreByIdEmpregado(usuario.getIdEmpregado());
		solicitacaoServicoDto.setIdUnidade(empregadoDto.getIdUnidade());
		
		/*Setando as informações de contato*/
		solicitacaoServicoDto.setNomecontato(empregadoDto.getNome());
		solicitacaoServicoDto.setEmailcontato(empregadoDto.getEmail());
		solicitacaoServicoDto.setTelefonecontato(empregadoDto.getTelefone());	
	}
	
	private void relacionaImpactoUrgencia(SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {		
		ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
		ServicoContratoDTO servicoContratoDto = servicoContratoService.findByIdContratoAndIdServico(solicitacaoServicoDto.getIdContrato(), solicitacaoServicoDto.getIdServico());

		if (servicoContratoDto != null) {
			AcordoNivelServicoService acordoNivelServicoService = (AcordoNivelServicoService) ServiceLocator.getInstance().getService(AcordoNivelServicoService.class, null);
			AcordoNivelServicoDTO acordoNivelServicoDto = acordoNivelServicoService.findAtivoByIdServicoContrato(servicoContratoDto.getIdServicoContrato(), "T");
			if (acordoNivelServicoDto == null) {
				//Se nao houver acordo especifico, ou seja, associado direto ao servicocontrato, entao busca um acordo geral que esteja vinculado ao servicocontrato.
				AcordoServicoContratoService acordoServicoContratoService = (AcordoServicoContratoService) ServiceLocator.getInstance().getService(AcordoServicoContratoService.class, null);
				AcordoServicoContratoDTO acordoServicoContratoDTO = acordoServicoContratoService.findAtivoByIdServicoContrato(servicoContratoDto.getIdServicoContrato(), "T");
				if (acordoServicoContratoDTO != null){
					//Apos achar a vinculacao do acordo com o servicocontrato, entao faz um restore do acordo de nivel de servico.
					acordoNivelServicoDto = new AcordoNivelServicoDTO();
					acordoNivelServicoDto.setIdAcordoNivelServico(acordoServicoContratoDTO.getIdAcordoNivelServico());
					acordoNivelServicoDto = (AcordoNivelServicoDTO) new AcordoNivelServicoDao().restore(acordoNivelServicoDto);
					
					if (acordoNivelServicoDto != null){
						if (acordoNivelServicoDto.getImpacto() != null)
							solicitacaoServicoDto.setImpacto(acordoNivelServicoDto.getImpacto());
						else
							solicitacaoServicoDto.setImpacto("B");
						
						if (acordoNivelServicoDto.getUrgencia() != null)
							solicitacaoServicoDto.setUrgencia(acordoNivelServicoDto.getUrgencia());
						else
							solicitacaoServicoDto.setUrgencia("B");
					}
				}
			}			
		} else {
			solicitacaoServicoDto.setImpacto("B");
			solicitacaoServicoDto.setUrgencia("B");
		}
	}

}
