package br.com.centralit.citcorpore.bpm.negocio;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.bpm.dto.AtribuicaoFluxoDTO;
import br.com.centralit.bpm.dto.EventoFluxoDTO;
import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.dto.ObjetoNegocioFluxoDTO;
import br.com.centralit.bpm.integracao.AtribuicaoFluxoDao;
import br.com.centralit.bpm.negocio.InstanciaFluxo;
import br.com.centralit.bpm.negocio.Tarefa;
import br.com.centralit.citcorpore.bean.AlcadaDTO;
import br.com.centralit.citcorpore.bean.CentroResultadoDTO;
import br.com.centralit.citcorpore.bean.CidadesDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.IntegranteViagemDTO;
import br.com.centralit.citcorpore.bean.ItemControleFinanceiroViagemDTO;
import br.com.centralit.citcorpore.bean.ParecerDTO;
import br.com.centralit.citcorpore.bean.PrestacaoContasViagemDTO;
import br.com.centralit.citcorpore.bean.ProjetoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoViagemDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.CentroResultadoDao;
import br.com.centralit.citcorpore.integracao.CidadesDao;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.centralit.citcorpore.integracao.IntegranteViagemDao;
import br.com.centralit.citcorpore.integracao.ItemControleFinanceiroViagemDAO;
import br.com.centralit.citcorpore.integracao.ParecerDao;
import br.com.centralit.citcorpore.integracao.PrestacaoContasViagemDao;
import br.com.centralit.citcorpore.integracao.ProjetoDao;
import br.com.centralit.citcorpore.integracao.RequisicaoViagemDAO;
import br.com.centralit.citcorpore.integracao.UsuarioDao;
import br.com.centralit.citcorpore.mail.MensagemEmail;
import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoServiceEjb;
import br.com.centralit.citcorpore.negocio.alcada.AlcadaRequisicaoViagem;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilDatas;

public class ExecucaoRequisicaoViagem extends ExecucaoSolicitacao {

	public ExecucaoRequisicaoViagem() {
		super();
	}

	public String i18n_Message(UsuarioDTO usuario, String key) {
		if (usuario != null) {
			if (UtilI18N.internacionaliza(usuario.getLocale(), key) != null) {
				return UtilI18N.internacionaliza(usuario.getLocale(), key);
			}
			return key;
		}
		return key;
	}

	@Override
	public InstanciaFluxo inicia() throws Exception {
		return super.inicia();
	}

	@Override
	public InstanciaFluxo inicia(FluxoDTO fluxoDto, Integer idFase) throws Exception {

		String idGrupo = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_REQ_VIAGEM_EXECUCAO, null);
		if (idGrupo == null || idGrupo.trim().equals(""))
			throw new Exception(i18n_Message("citcorpore.comum.grupoPadraoNaoParametrizado"));
		getSolicitacaoServicoDto().setIdGrupoAtual(new Integer(idGrupo.trim()));
		return super.inicia(fluxoDto, idFase);

	}

	@Override
	public void mapObjetoNegocio(Map<String, Object> map) throws Exception {
		super.mapObjetoNegocio(map);
	}

	@Override
	public void executaEvento(EventoFluxoDTO eventoFluxoDto) throws Exception {
		super.executaEvento(eventoFluxoDto);
	}
	
	@Override
	public void complementaInformacoesEmail(SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
		super.complementaInformacoesEmail(solicitacaoServicoDto);
		
		RequisicaoViagemDTO requisicaoViagemDto = recuperaRequisicaoViagem();
		CentroResultadoDTO centroResultado =  new CentroResultadoDTO();
		ProjetoDTO projetoDto = new ProjetoDTO();
		CidadesDTO cidade = new CidadesDTO();
		StringBuffer strItens = new StringBuffer();
		 
		if(requisicaoViagemDto!=null){
			 centroResultado = recuperaCentroCusto(requisicaoViagemDto);
			 projetoDto = recuperaProjeto(requisicaoViagemDto);
			 	strItens.append("<b>Data Inicio: </b>"+requisicaoViagemDto.getDataInicioViagem()+"<br>");
			 	strItens.append("<b>Data Fim: </b>"+requisicaoViagemDto.getDataInicioViagem()+"<br>");
			 	if(requisicaoViagemDto.getIdCidadeOrigem()!=null){
			 		cidade = recuperaCidade(requisicaoViagemDto.getIdCidadeOrigem());
			 		strItens.append("<b>Cidade Origem: </b>"+cidade.getNomeCidade()+"<br>");
			 	}
			 	if(requisicaoViagemDto.getIdCidadeDestino()!=null){
			 		cidade = recuperaCidade(requisicaoViagemDto.getIdCidadeDestino());
			 		strItens.append("<b>Cidade Destino: </b>"+cidade.getNomeCidade()+"<br>");
			 	}
			 	if(centroResultado!=null){
			 		  strItens.append("<b>Centro de custo: </b>"+centroResultado.getNomeCentroResultado()+"<br>");
			 	}
			 	if(projetoDto!=null){
			 		strItens.append("<b>Projeto: </b>"+projetoDto.getNomeProjeto()+"<br>");
			 	}
			 	strItens.append("<b>Motivo: </b><br>");
			 	strItens.append(""+requisicaoViagemDto.getDescricaoMotivo()+"");
			 	
			 	solicitacaoServicoDto.setInformacoesComplementaresHTML(strItens.toString());
		}
	}

	public RequisicaoViagemDTO recuperaRequisicaoViagem() throws Exception {
		RequisicaoViagemDAO requisicaoViagemDao = new RequisicaoViagemDAO();
		setTransacaoDao(requisicaoViagemDao);
		SolicitacaoServicoDTO solicitacaoDto = getSolicitacaoServicoDto();
		RequisicaoViagemDTO requisicaoViagemDto = new RequisicaoViagemDTO();
		requisicaoViagemDto.setIdSolicitacaoServico(solicitacaoDto.getIdSolicitacaoServico());
		requisicaoViagemDto = (RequisicaoViagemDTO) requisicaoViagemDao.restore(requisicaoViagemDto);
		Reflexao.copyPropertyValues(solicitacaoDto, requisicaoViagemDto);
		return requisicaoViagemDto;
	}

	public CentroResultadoDTO recuperaCentroCusto(RequisicaoViagemDTO requisicaoViagemDto) throws Exception {
		CentroResultadoDTO centroCustoDto = new CentroResultadoDTO();
		centroCustoDto.setIdCentroResultado(requisicaoViagemDto.getIdCentroCusto());
		return (CentroResultadoDTO) new CentroResultadoDao().restore(centroCustoDto);
	}
	
	public ProjetoDTO recuperaProjeto(RequisicaoViagemDTO requisicaoViagemDto) throws Exception {
		ProjetoDTO projetoDto = new ProjetoDTO();
		if(requisicaoViagemDto.getIdProjeto()!=null){
			projetoDto.setIdProjeto(requisicaoViagemDto.getIdProjeto());
			return (ProjetoDTO) new ProjetoDao().restore(projetoDto);
			
		}
		return null;
		
	}
	
	public CidadesDTO recuperaCidade(Integer idCidade) throws Exception {
		CidadesDTO cidadeDto  = new CidadesDTO();
		if(idCidade !=null){
			cidadeDto.setIdCidade(idCidade);
			return (CidadesDTO) new CidadesDao().restore(cidadeDto);
		}
		return null;
	}

	public AlcadaDTO recuperaAlcada(RequisicaoViagemDTO requisicaoViagemDto) throws Exception {
		return new AlcadaRequisicaoViagem().determinaAlcada(requisicaoViagemDto, recuperaCentroCusto(requisicaoViagemDto), getTransacao());
	}

	public StringBuffer recuperaLoginAutorizadores() throws Exception {
		RequisicaoViagemDTO requisicaoViagemDto = recuperaRequisicaoViagem();
		return recuperaLoginAutorizadores(requisicaoViagemDto);
	}

	public StringBuffer recuperaLoginAutorizadores(RequisicaoViagemDTO requisicaoViagemDto) throws Exception {
		StringBuffer result = new StringBuffer();
		AlcadaDTO alcadaDto = recuperaAlcada(requisicaoViagemDto);
		if (alcadaDto != null && alcadaDto.getColResponsaveis() != null) {
			int i = 0;
			UsuarioDao usuarioDao = new UsuarioDao();
			for (EmpregadoDTO empregadoDto : alcadaDto.getColResponsaveis()) {
				UsuarioDTO usuarioDto = usuarioDao.restoreAtivoByIdEmpregado(empregadoDto.getIdEmpregado());
				if (usuarioDto != null) {
					if (i > 0)
						result.append(";");
					result.append(usuarioDto.getLogin());
					i++;
				}
			}
		}
		if (result.length() == 0)
			throw new LogicException("Não foi encontrado nenhum autorizador da requisição");
		return result;
	}

	public boolean exigeAutorizacao() throws Exception {
		RequisicaoViagemDTO requisicaoViagemDto = this.recuperaRequisicaoViagem();
		return exigeAutorizacao(requisicaoViagemDto);
	}

	public boolean exigeAutorizacao(RequisicaoViagemDTO requisicaoViagemDto) throws Exception {
		RequisicaoViagemDTO requisicaoAuxDto = this.recuperaRequisicaoViagem();
		AlcadaDTO alcadaDto = recuperaAlcada(requisicaoAuxDto);
		if (alcadaDto != null) {
			boolean result = false;
			if (alcadaDto.getColResponsaveis() != null) {
				result = true;
				for (EmpregadoDTO empregadoDto : alcadaDto.getColResponsaveis()) {
					if (getSolicitacaoServicoDto().getIdSolicitante().intValue() == empregadoDto.getIdEmpregado().intValue()) {
						result = false;
						break;
					}
				}
			}
			return result;
		} else{
			return false;
		}
			
	}

	public ExecucaoRequisicaoViagem(RequisicaoViagemDTO requisicaoViagemDto, TransactionControler tc) {
		super(requisicaoViagemDto, tc);
	}

	public ExecucaoRequisicaoViagem(TransactionControler tc) {
		super(tc);
	}
	
	 public boolean requisicaoAutorizada() throws Exception{
		 	boolean autorizado;
		 	autorizado = false;
	        RequisicaoViagemDTO requisicaoDto = recuperaRequisicaoViagem();
	        ParecerDTO parecerDto = new ParecerDTO();
	       
	        if(requisicaoDto.getIdAprovacao()!=null){
	        	 parecerDto = recuperaParecer(requisicaoDto);
	        	if(parecerDto!=null){
	        		requisicaoDto.setAutorizado(parecerDto.getAprovado());
	        		autorizado = requisicaoDto.getAutorizado() != null && requisicaoDto.getAutorizado().equalsIgnoreCase("S");
	        		
	        	}
	        }
	       return autorizado;
	 }
	 
	 public ParecerDTO recuperaParecer(RequisicaoViagemDTO requisicaoViagemDto) throws Exception {
		 ParecerDTO parecerDto = new ParecerDTO();
			if(requisicaoViagemDto.getIdAprovacao()!=null){
				parecerDto.setIdParecer(requisicaoViagemDto.getIdAprovacao());
				return (ParecerDTO) new ParecerDao().restore(parecerDto);
				
			}
			return null;
			
		}
	 
	 public boolean validaPrazoItens() throws Exception{
		 RequisicaoViagemDTO requisicaoViagemDto = this.recuperaRequisicaoViagem();
		 
//		 Date dataPrazoCotacao = null;
//		 Date dataAtual = UtilDatas.getDataAtual();
		 Timestamp dataHoraPrazoCotacao = null;
		 Timestamp dataHoraAtual = UtilDatas.getDataHoraAtual();
		 
		 Collection<IntegranteViagemDTO> colIntegrantes =  new IntegranteViagemDao().findAllByIdSolicitacao(requisicaoViagemDto.getIdSolicitacaoServico());
		 if(colIntegrantes!=null){
			 for(IntegranteViagemDTO integranteViagemDto : colIntegrantes){
				 Collection<ItemControleFinanceiroViagemDTO> colItens  = new  ItemControleFinanceiroViagemDAO().findByIdSolicitacaoAndIdEmpregado(integranteViagemDto.getIdSolicitacaoServico(),integranteViagemDto.getIdEmpregado());
				 if(colItens!=null){
					 for(ItemControleFinanceiroViagemDTO itemControleFinanceiroViagemDto : colItens){
						 if (dataHoraPrazoCotacao == null) {
//							 dataPrazoCotacao = itemControleFinanceiroViagemDto.getPrazoCotacao();
							 dataHoraPrazoCotacao = itemControleFinanceiroViagemDto.getDataHoraPrazoCotacao();
						}
						 if(itemControleFinanceiroViagemDto.getDataHoraPrazoCotacao().compareTo(dataHoraPrazoCotacao)<0){
							 dataHoraPrazoCotacao = itemControleFinanceiroViagemDto.getDataHoraPrazoCotacao();
						 }
					 }
					 
				 }
			 }
		 }
		 if(dataHoraPrazoCotacao!= null){
			 if(dataHoraPrazoCotacao.compareTo(dataHoraAtual)>0){
				 return true;
			 }
		 }
		 
		 return false;
			
	}
	 
	 public StringBuilder recuperaLoginIntegrantes() throws Exception{
		 RequisicaoViagemDTO requisicaoViagemDto = recuperaRequisicaoViagem();
		 return recuperaLoginIntegrantes(requisicaoViagemDto);
	 }
	 
	 public StringBuilder recuperaLoginIntegrantes(RequisicaoViagemDTO requisicaoViagemDto) throws LogicException{
		 StringBuilder result = new StringBuilder();
		 IntegranteViagemDao dao = new IntegranteViagemDao();
		 
		 try {
			Collection<IntegranteViagemDTO> colIntegrantes = dao.findAllByIdSolicitacao(requisicaoViagemDto.getIdSolicitacaoServico());
			if(colIntegrantes != null){
				int i = 0;
				for(IntegranteViagemDTO integrantes : colIntegrantes){
					UsuarioDao usuarioDao = new UsuarioDao();
					
					UsuarioDTO usuarioDto = usuarioDao.restoreAtivoByIdEmpregado(integrantes.getIdEmpregado());
					if (usuarioDto != null) {
						if (i > 0)
							result.append(";");
						result.append(usuarioDto.getLogin());
						i++;	
					}
				}
			}	
		} catch (Exception e) {
			e.printStackTrace();
		} 
		if (result.length() == 0)
				throw new LogicException("Não foi encontrado nenhum Integrante da requisição");
		return result; 
	 }

	@SuppressWarnings("unchecked")
	public void associaItemTrabalhoPrestacaoConferencia(Tarefa tarefa) throws Exception{
        PrestacaoContasViagemDao dao = new PrestacaoContasViagemDao();
        setTransacaoDao(dao);
        List<PrestacaoContasViagemDTO> listaItens = (List<PrestacaoContasViagemDTO>) dao.findBySolicitacaoAndTaferaConferencia(getSolicitacaoServicoDto().getIdSolicitacaoServico());
        if (listaItens != null) {
            PrestacaoContasViagemDTO prestacaoContas = listaItens.get(0);
            prestacaoContas.setIdItemTrabalho(tarefa.getIdItemTrabalho());
            dao.update(prestacaoContas);
        }
    }
	
	@SuppressWarnings("unchecked")
	public void associaItemTrabalhoPrestacaoCorrecao(Tarefa tarefa) throws Exception{
		PrestacaoContasViagemDao dao = new PrestacaoContasViagemDao();
		Integer idSolicitacaoServico = getSolicitacaoServicoDto().getIdSolicitacaoServico();
		setTransacaoDao(dao);
		List<PrestacaoContasViagemDTO> listaItens = (List<PrestacaoContasViagemDTO>) dao.findByCorrigirAndSolicitacao(idSolicitacaoServico);
        if (listaItens != null) {
           PrestacaoContasViagemDTO prestacaoContas = listaItens.get(0);
           prestacaoContas.setIdItemTrabalho(tarefa.getIdItemTrabalho());
           dao.update(prestacaoContas);
           this.validaEmpregadoAtribuicao(tarefa.getIdItemTrabalho(), prestacaoContas);
        }
	}
	
	 
	 @SuppressWarnings("unchecked")
	public boolean corrigirPrestacaoContas() throws Exception{
		 PrestacaoContasViagemDao dao = new PrestacaoContasViagemDao();
		 Integer idSolicitacao = getSolicitacaoServicoDto().getIdSolicitacaoServico();
			List<PrestacaoContasViagemDTO> listaItens = (List<PrestacaoContasViagemDTO>) dao.findByCorrigirAndSolicitacao(idSolicitacao);
			boolean isOk = (listaItens != null && listaItens.size() > 0);
			if(isOk){
				PrestacaoContasViagemDTO prestacaoDto =  listaItens.get(0);
				prestacaoDto.setSituacao(PrestacaoContasViagemDTO.EM_CORRECAO);
				dao.update(prestacaoDto);
				this.setaInicioTarefa();
			}
			return isOk;
	 }
	
	@SuppressWarnings({"unchecked"})
	public boolean isTarefaConferencia() throws Exception{
		PrestacaoContasViagemDao dao = new PrestacaoContasViagemDao();
		Integer idSolicitacao = getSolicitacaoServicoDto().getIdSolicitacaoServico();
		List<PrestacaoContasViagemDTO> listaItens = (List<PrestacaoContasViagemDTO>) dao.findBySolicitacaoAndTafera(idSolicitacao);
		boolean isOk = (listaItens != null && listaItens.size() > 0);
		if(isOk){
			PrestacaoContasViagemDTO dto  = listaItens.get(0);
			dto.setSituacao(PrestacaoContasViagemDTO.EM_CONFERENCIA);
			dao.update(dto);
			this.setaInicioTarefa();
		}
		return isOk;
	}
	
	public boolean isEstadoPrestacaoContas() throws Exception{
		PrestacaoContasViagemDao dao = new PrestacaoContasViagemDao();
		RequisicaoViagemDTO requisicaoViagemDto = recuperaRequisicaoViagem();
		boolean isOk = dao.isEstadoPrestacaoContas(requisicaoViagemDto);
		if(isOk)
			this.setaInicioTarefa();
		return isOk;
	}
	
	public boolean requisicaoViagemFinalizada() throws Exception{
		boolean isOk = false;
		PrestacaoContasViagemDao dao = new PrestacaoContasViagemDao();
		Integer idSolicitacaoServico = getSolicitacaoServicoDto().getIdSolicitacaoServico();
		
		isOk = dao.verificaSeTodasPrestacaoAprovadas(idSolicitacaoServico);
		 
		 if(isOk){
			RequisicaoViagemDAO reqViagemDao = new RequisicaoViagemDAO();
			RequisicaoViagemDTO requisicaoViagemDto = recuperaRequisicaoViagem();
			requisicaoViagemDto.setEstado(RequisicaoViagemDTO.FINALIZADA);
			reqViagemDao.update(requisicaoViagemDto);
		 }
		return isOk;
	}
	
	public void setaInicioTarefa() throws Exception{
		RequisicaoViagemDAO dao = new RequisicaoViagemDAO();
		RequisicaoViagemDTO requisicaoViagemDto = recuperaRequisicaoViagem();
		requisicaoViagemDto.setTarefaIniciada("S");
		dao.update(requisicaoViagemDto);
	}
	
	public void setaFimTarefa() throws Exception{
		RequisicaoViagemDAO dao = new RequisicaoViagemDAO();
		RequisicaoViagemDTO requisicaoViagemDto = recuperaRequisicaoViagem();
		requisicaoViagemDto.setTarefaIniciada("N");
		dao.update(requisicaoViagemDto);
	}
	
	public StringBuffer recuperaLoginResponsaveisAdiantamento() throws Exception{
		StringBuffer result = new StringBuffer();
		
		Integer idGrupo = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_RESPONSAVEL_ADIANTAMENTO_VIAGEM, getSolicitacaoServicoDto().getIdGrupoAtual().toString()));
		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		Collection<EmpregadoDTO> colEmpregado = empregadoService.listEmpregadosByIdGrupo(idGrupo);
		
		if(colEmpregado != null){
			int i = 0;
			UsuarioDao usuarioDao = new UsuarioDao();
			for (EmpregadoDTO empregadoDto : colEmpregado) {
				UsuarioDTO usuarioDto = usuarioDao.restoreAtivoByIdEmpregado(empregadoDto.getIdEmpregado());
				if (usuarioDto != null) {
					if (i > 0)
						result.append(";");
					result.append(usuarioDto.getLogin());
					i++;
				}
			}
		}
		if (result.length() == 0)
			throw new LogicException("Não foi encontrado nenhum responsavel para o Adiantamento");
		
		return result;
	}
	
	public StringBuffer recuperaLoginResponsaveisConferencia() throws Exception{
		StringBuffer result = new StringBuffer();
		
		Integer idGrupo = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_RESPONSAVEL_CONFERENCIA_VIAGEM, getSolicitacaoServicoDto().getIdGrupoAtual().toString()));
		
		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		Collection<EmpregadoDTO> colEmpregado = empregadoService.listEmpregadosByIdGrupo(idGrupo);
		
		if(colEmpregado != null){
			int i = 0;
			UsuarioDao usuarioDao = new UsuarioDao();
			for (EmpregadoDTO empregadoDto : colEmpregado) {
				UsuarioDTO usuarioDto = usuarioDao.restoreAtivoByIdEmpregado(empregadoDto.getIdEmpregado());
				if (usuarioDto != null) {
					if (i > 0)
						result.append(";");
					result.append(usuarioDto.getLogin());
					i++;
				}
			}
		}
		if (result.length() == 0)
			throw new LogicException("Não foi encontrado nenhum responsavel para a Conferência");
		
		return result;
	}
	
	public StringBuffer recuperaLoginResponsaveisCotacao() throws Exception{
		StringBuffer result = new StringBuffer();
		
		Integer idGrupo = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_RESPONSAVEL_COTACAO_VIAGEM, getSolicitacaoServicoDto().getIdGrupoAtual().toString()));
		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		Collection<EmpregadoDTO> colEmpregado = empregadoService.listEmpregadosByIdGrupo(idGrupo);
		
		if(colEmpregado != null){
			int i = 0;
			UsuarioDao usuarioDao = new UsuarioDao();
			for (EmpregadoDTO empregadoDto : colEmpregado) {
				UsuarioDTO usuarioDto = usuarioDao.restoreAtivoByIdEmpregado(empregadoDto.getIdEmpregado());
				if (usuarioDto != null) {
					if (i > 0)
						result.append(";");
					result.append(usuarioDto.getLogin());
					i++;
				}
			}
		}
		if (result.length() == 0)
			throw new LogicException("Não foi encontrado nenhum responsavel pela Cotação");
		
		return result;
	}
	
	public StringBuilder recuperaLoginIntegrante() throws Exception{
		RequisicaoViagemDTO requisicaoViagemDto = recuperaRequisicaoViagem();
		 StringBuilder result = new StringBuilder();
		 IntegranteViagemDao integranteViagemDao = new IntegranteViagemDao();
		 Collection<IntegranteViagemDTO> colIntegrantes = integranteViagemDao.findAllByIdSolicitacao(requisicaoViagemDto.getIdSolicitacaoServico());
		 
		 try {
			if(colIntegrantes != null){
				int i = 0;
				for(IntegranteViagemDTO integrante : colIntegrantes){
					UsuarioDao usuarioDao = new UsuarioDao();
					UsuarioDTO usuarioDto = usuarioDao.restoreAtivoByIdEmpregado(integrante.getIdEmpregado());
					if (usuarioDto != null) {
						if (i > 0)
							result.append(";");
						result.append(usuarioDto.getLogin());
						i++;
					}
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		} 
		if (result.length() == 0)
				throw new LogicException("Não foi encontrado nenhum Integrante da requisição");
		return result; 
	 }
	
	public StringBuilder recuperaLoginIntegranteCorrecao() throws Exception {
		RequisicaoViagemDTO requisicaoViagemDto = recuperaRequisicaoViagem();
		StringBuilder result = new StringBuilder();
		PrestacaoContasViagemDao dao = new PrestacaoContasViagemDao();
		PrestacaoContasViagemDTO prestacaoContasDto = new PrestacaoContasViagemDTO();
		prestacaoContasDto = dao.findNaoAprovados(requisicaoViagemDto.getIdSolicitacaoServico(), null);
		if(prestacaoContasDto == null){
			 return recuperaLoginIntegrante();
		}else{
			try {	
				UsuarioDao usuarioDao = new UsuarioDao();
				UsuarioDTO usuarioDto = usuarioDao.restoreAtivoByIdEmpregado(prestacaoContasDto.getIdEmpregado());
				if (usuarioDto != null) {
					result.append(usuarioDto.getLogin());
					result.append(";");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (result.length() == 0)
				throw new LogicException("Não foi encontrado nenhum Integrante da requisição");
			return result;
		}	
	}
	
	private void validaEmpregadoAtribuicao(Integer idTarefa, PrestacaoContasViagemDTO prestacaoDto) throws Exception{
		AtribuicaoFluxoDTO atribuicaoDto = new AtribuicaoFluxoDTO();
		AtribuicaoFluxoDao atribuicaoDao = new AtribuicaoFluxoDao();
		UsuarioDTO usuarioDto = null;
		UsuarioDao usuarioDao = new UsuarioDao();
		
		usuarioDto = usuarioDao.restoreByIdEmpregado(prestacaoDto.getIdEmpregado());
		
		if(usuarioDto != null){
			atribuicaoDto = (AtribuicaoFluxoDTO) atribuicaoDao.findByDisponiveisByIdItemTrabalho(idTarefa);
			if(atribuicaoDto != null){
				atribuicaoDto.setIdGrupo(null);
				atribuicaoDto.setIdUsuario(usuarioDto.getIdUsuario());
				atribuicaoDao.update(atribuicaoDto);
			}
		}
	}
	
	public void enviaEmailNaoAprovado(Tarefa tarefa) throws Exception{
		Integer idModeloEmail = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_MODELO_EMAIL_PRESTACAO_CONTAS_NAO_APROVADA, "70"));
		this.enviaEmailNaoAprovado(idModeloEmail,tarefa);
	}
	
	private void enviaEmailNaoAprovado(Integer idModeloEmail, Tarefa tarefa) throws Exception {
		if (idModeloEmail == null)
			return;
		
		PrestacaoContasViagemDao prestacaoContasViagemDao = new PrestacaoContasViagemDao();
		RequisicaoViagemDTO requisicaoViagemDto = recuperaRequisicaoViagem();
		
		PrestacaoContasViagemDTO prestacaoContasViagemDto = prestacaoContasViagemDao.findBySolicitacaoAndTarefa(requisicaoViagemDto.getIdSolicitacaoServico(), tarefa.getIdItemTrabalho());
		
		if(prestacaoContasViagemDto == null)
			return;
		
		EmpregadoDTO empregadoDto = new EmpregadoDTO();
		EmpregadoDao empregadoDao = new EmpregadoDao();
		empregadoDto.setIdEmpregado(prestacaoContasViagemDto.getIdEmpregado());
		empregadoDto = (EmpregadoDTO) empregadoDao.restore(empregadoDto);
		
		if(empregadoDto == null)
			return;
		
		String remetente = getRemetenteEmail();

		SolicitacaoServicoDTO solicitacaoAuxDto = new SolicitacaoServicoServiceEjb().restoreAll(getSolicitacaoServicoDto().getIdSolicitacaoServico(), getTransacao());
		if(solicitacaoAuxDto != null)
			solicitacaoAuxDto.setNomeTarefa(getSolicitacaoServicoDto().getNomeTarefa());

		complementaInformacoesEmail(solicitacaoAuxDto);
		/*Decodifica a mensagem a ser enviada*/
		if(solicitacaoAuxDto != null){
			solicitacaoAuxDto.setDescricao(StringEscapeUtils.unescapeJavaScript(solicitacaoAuxDto.getDescricao()));
			solicitacaoAuxDto.setResposta(StringEscapeUtils.unescapeJavaScript(solicitacaoAuxDto.getResposta()));
		}
		
		MensagemEmail mensagem = new MensagemEmail(idModeloEmail, new IDto[] { solicitacaoAuxDto });
		try {
			mensagem.envia(empregadoDto.getEmail(), null, remetente);
		} catch (Exception e) {
		}
	}
	
}
