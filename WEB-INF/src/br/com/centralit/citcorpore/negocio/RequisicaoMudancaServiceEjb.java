/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.htmlparser.jericho.Source;
import br.com.centralit.bpm.dto.PermissoesFluxoDTO;
import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.ajaxForms.RequisicaoMudanca;
import br.com.centralit.citcorpore.bean.AprovacaoMudancaDTO;
import br.com.centralit.citcorpore.bean.AprovacaoPropostaDTO;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.CalculoJornadaDTO;
import br.com.centralit.citcorpore.bean.ContatoRequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.GrupoRequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.HistoricoGEDDTO;
import br.com.centralit.citcorpore.bean.HistoricoMudancaDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.JustificativaRequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.LiberacaoMudancaDTO;
import br.com.centralit.citcorpore.bean.LigacaoRequisicaoMudancaHistoricoGrupoDTO;
import br.com.centralit.citcorpore.bean.LigacaoRequisicaoMudancaHistoricoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.LigacaoRequisicaoMudancaHistoricoProblemaDTO;
import br.com.centralit.citcorpore.bean.LigacaoRequisicaoMudancaHistoricoResponsavelDTO;
import br.com.centralit.citcorpore.bean.LigacaoRequisicaoMudancaHistoricoRiscosDTO;
import br.com.centralit.citcorpore.bean.LigacaoRequisicaoMudancaHistoricoServicoDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaMudancaDTO;
import br.com.centralit.citcorpore.bean.PesquisaRequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.ProblemaMudancaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaResponsavelDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaRiscoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaServicoDTO;
import br.com.centralit.citcorpore.bean.ReuniaoRequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoMudancaDTO;
import br.com.centralit.citcorpore.bean.TemplateSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.TipoMudancaDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.AprovacaoMudancaDao;
import br.com.centralit.citcorpore.integracao.AprovacaoPropostaDao;
import br.com.centralit.citcorpore.integracao.ContatoRequisicaoMudancaDao;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.centralit.citcorpore.integracao.GrupoDao;
import br.com.centralit.citcorpore.integracao.GrupoRequisicaoMudancaDao;
import br.com.centralit.citcorpore.integracao.HistoricoGEDDao;
import br.com.centralit.citcorpore.integracao.HistoricoMudancaDao;
import br.com.centralit.citcorpore.integracao.LiberacaoMudancaDao;
import br.com.centralit.citcorpore.integracao.LigacaoRequisicaoLiberacaoRiscosDao;
import br.com.centralit.citcorpore.integracao.LigacaoRequisicaoMudancaGrupoDao;
import br.com.centralit.citcorpore.integracao.LigacaoRequisicaoMudancaItemConfiguracaoDao;
import br.com.centralit.citcorpore.integracao.LigacaoRequisicaoMudancaProblemaDao;
import br.com.centralit.citcorpore.integracao.LigacaoRequisicaoMudancaResponsavelDao;
import br.com.centralit.citcorpore.integracao.LigacaoRequisicaoMudancaServicoDao;
import br.com.centralit.citcorpore.integracao.OcorrenciaMudancaDao;
import br.com.centralit.citcorpore.integracao.PermissoesFluxoDao;
import br.com.centralit.citcorpore.integracao.ProblemaMudancaDAO;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaDao;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaItemConfiguracaoDao;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaResponsavelDao;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaRiscoDao;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaServicoDao;
import br.com.centralit.citcorpore.integracao.ReuniaoRequisicaoMudancaDAO;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoMudancaDao;
import br.com.centralit.citcorpore.integracao.TemplateSolicitacaoServicoDao;
import br.com.centralit.citcorpore.integracao.TipoMudancaDAO;
import br.com.centralit.citcorpore.integracao.UsuarioDao;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia;
import br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoRequisicaoMudanca;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.integracao.ControleGEDDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;

import com.google.gson.Gson;

@SuppressWarnings({"rawtypes","unchecked"})
public class RequisicaoMudancaServiceEjb extends CrudServicePojoImpl implements RequisicaoMudancaService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private RequisicaoMudancaItemConfiguracaoService requisicaoMudancaItemConfiguracaoService;
	private static RequisicaoMudancaDao requisicaoMudancaDao;
	private ItemConfiguracaoService itemConfiguracaoService;




	protected CrudDAO getDao() throws ServiceException {
		return new RequisicaoMudancaDao();
	}

	private static RequisicaoMudancaDao getRequisicaoMudancaDao() throws ServiceException {
		if (requisicaoMudancaDao == null) {
			requisicaoMudancaDao = (RequisicaoMudancaDao) new RequisicaoMudancaDao();
		}
		return requisicaoMudancaDao;
	}

	

	private RequisicaoMudancaItemConfiguracaoService getRequisicaoMudancaItemConfiguracaoService() throws ServiceException, Exception {
		if (requisicaoMudancaItemConfiguracaoService == null) {
			requisicaoMudancaItemConfiguracaoService = (RequisicaoMudancaItemConfiguracaoService) ServiceLocator.getInstance().getService(RequisicaoMudancaItemConfiguracaoService.class, null);
		}
		return requisicaoMudancaItemConfiguracaoService;
	}



	@Override
	public void rollbackTransaction(TransactionControler tc, Exception ex) throws ServiceException, LogicException {
		super.rollbackTransaction(tc, ex);
	}

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaFind(Object arg0) throws Exception {

	}

	protected void validaUpdate(Object arg0) throws Exception {
	}
	
	@Override
	public void updateNotNull(IDto obj) throws Exception {
		RequisicaoMudancaDao requisicaoMudancaDao = new RequisicaoMudancaDao();
		requisicaoMudancaDao.updateNotNull(obj);

	}

	/* (non-Javadoc)
	 * @see br.com.citframework.service.CrudServicePojoImpl#create(br.com.citframework.dto.IDto)
	 */
	@Override
	public IDto create(IDto model) throws ServiceException, LogicException {
		
		ExecucaoMudancaServiceEjb execucaoMudancaService = new ExecucaoMudancaServiceEjb();
		SolicitacaoServicoMudancaDao solicitacaoServicoMudancaDao = new SolicitacaoServicoMudancaDao();
		TipoMudancaDAO tipoMudancaDAO = new TipoMudancaDAO();
		RequisicaoMudancaDao requisicaoMudancaDao = new RequisicaoMudancaDao();
		
		ContatoRequisicaoMudancaDTO contatoRequisicaoMudancaDto = new ContatoRequisicaoMudancaDTO();
		
		AprovacaoMudancaDao aprovacaoMudancaDao = new AprovacaoMudancaDao();
		AprovacaoPropostaDao aprovacaoPropostaDao = new AprovacaoPropostaDao();
		RequisicaoMudancaItemConfiguracaoDao requisicaoMudancaItemConfiguracaoDao = new RequisicaoMudancaItemConfiguracaoDao(); 
		ProblemaMudancaDAO problemaMudancaDao = new ProblemaMudancaDAO();
		RequisicaoMudancaRiscoDao requisicaoMudancaRiscoDao = new RequisicaoMudancaRiscoDao();
		LiberacaoMudancaDao liberacaoMudancaDao = new LiberacaoMudancaDao();
//		RequisicaoMudancaLiberacaoDao requisicaoMudancaLiberacaoDao = new RequisicaoMudancaLiberacaoDao();
		RequisicaoMudancaServicoDao requisicaoMudancaServicoDao = new RequisicaoMudancaServicoDao();
		RequisicaoMudancaResponsavelDao requisicaoMudancaResponsavelDao = new RequisicaoMudancaResponsavelDao();
		GrupoRequisicaoMudancaDao grupoRequisicaoMudancaDao = new GrupoRequisicaoMudancaDao();
		
		TipoMudancaDTO tipoMudancaDTO = new TipoMudancaDTO();
		RequisicaoMudancaDTO requisicaoMudancaDto = (RequisicaoMudancaDTO) model;
		
		TransactionControler tc = new TransactionControlerImpl(requisicaoMudancaDao.getAliasDB());

		try {
			
			tc.start();
			
			validaCreate(model);

			tipoMudancaDAO.setTransactionControler(tc);
			requisicaoMudancaDao.setTransactionControler(tc);
			solicitacaoServicoMudancaDao.setTransactionControler(tc);
			aprovacaoMudancaDao.setTransactionControler(tc);
			aprovacaoPropostaDao.setTransactionControler(tc);
			requisicaoMudancaItemConfiguracaoDao.setTransactionControler(tc);
			problemaMudancaDao.setTransactionControler(tc);
			requisicaoMudancaRiscoDao.setTransactionControler(tc);
			liberacaoMudancaDao.setTransactionControler(tc);
			requisicaoMudancaServicoDao.setTransactionControler(tc);
			requisicaoMudancaResponsavelDao.setTransactionControler(tc);
			grupoRequisicaoMudancaDao.setTransactionControler(tc);
			
			if(usuario == null){
				usuario = new Usuario();
			}
			
			if(usuario != null && requisicaoMudancaDto != null	){
				usuario.setLocale(requisicaoMudancaDto.getUsuarioDto().getLocale());
			}
			
			if (requisicaoMudancaDto != null && requisicaoMudancaDto.getDataHoraInicioAgendada() != null && requisicaoMudancaDto.getHoraAgendamentoInicial() != null
				&& requisicaoMudancaDto != null && requisicaoMudancaDto.getDataHoraInicioAgendada() != null && requisicaoMudancaDto.getHoraAgendamentoInicial() != null) {
				boolean resultado = seHoraFinalMenorQHoraInicial(requisicaoMudancaDto);
				if (resultado == true) {
					throw new LogicException(i18n_Message("requisicaoMudanca.horaFinalMenorQueInicial"));
				}
			}
			
			if (requisicaoMudancaDto != null && requisicaoMudancaDto.getDataHoraInicioAgendada() != null && requisicaoMudancaDto.getHoraAgendamentoInicial() != null) {
				boolean resultado = seHoraInicialMenorQAtual(requisicaoMudancaDto);
				if (resultado == true) {
					throw new LogicException(i18n_Message("requisicaoMudanca.horaInicialMenorQueAtual"));
				}
			}
			
			if (requisicaoMudancaDto != null && requisicaoMudancaDto.getDataHoraTerminoAgendada() != null && requisicaoMudancaDto.getHoraAgendamentoFinal() != null) {
				boolean resultado = seHoraFinalMenorQAtual(requisicaoMudancaDto);
				if (resultado == true) {
					throw new LogicException(i18n_Message("requisicaoMudanca.horaFinalMenorQueAtual"));
				}
			}
			
			if (requisicaoMudancaDto != null && requisicaoMudancaDto.getDataHoraInicioAgendada() != null && requisicaoMudancaDto.getHoraAgendamentoInicial() != null) {
				Timestamp dataHoraInicial = MontardataHoraAgendamentoInicial(requisicaoMudancaDto);
				requisicaoMudancaDto.setDataHoraInicioAgendada(dataHoraInicial);	
			}
			
			if (requisicaoMudancaDto != null && requisicaoMudancaDto.getDataHoraTerminoAgendada() != null && requisicaoMudancaDto.getHoraAgendamentoFinal() != null) {
				Timestamp dataHoraFinal = MontardataHoraAgendamentoFinal(requisicaoMudancaDto);
				requisicaoMudancaDto.setDataHoraTerminoAgendada(dataHoraFinal);	
				requisicaoMudancaDto.setDataHoraTermino(dataHoraFinal);
			}
			
			boolean resultado = validacaoGrupoExecutor(requisicaoMudancaDto);
			if (resultado == false) {
				throw new LogicException(i18n_Message("requisicaoMudanca.grupoSemPermissao"));
			}
			
			if(requisicaoMudancaDto.getDataHoraTerminoAgendada() != null && requisicaoMudancaDto.getDataHoraInicioAgendada() !=null){
				
			calculaTempoAtraso(requisicaoMudancaDto);
				
				
			}else{
				requisicaoMudancaDto.setPrazoHH(00);
				requisicaoMudancaDto.setPrazoMM(00);
			}
			
			if (requisicaoMudancaDto.getUsuarioDto() == null){
				throw new LogicException(i18n_Message("citcorpore.comum.usuarioNaoidentificado"));
			}
			
			
			if(requisicaoMudancaDto.getIdTipoMudanca()!=null){
				
				tipoMudancaDTO.setIdTipoMudanca(requisicaoMudancaDto.getIdTipoMudanca());
				tipoMudancaDTO = (TipoMudancaDTO)tipoMudancaDAO.restore(tipoMudancaDTO);
			}
			
		
			if(tipoMudancaDTO.getIdGrupoExecutor()==null){
				
				throw new LogicException(i18n_Message("citcorpore.comum.grupoExecutorNaoDefinido"));
			}
				
			if(tipoMudancaDTO.getIdCalendario()==null){
				
				throw new LogicException(i18n_Message("citcorpore.comum.calendarioNaoDefinido"));
			}
				
			if(requisicaoMudancaDto.getEhPropostaAux().equalsIgnoreCase("S")){
				requisicaoMudancaDto.setStatus(SituacaoRequisicaoMudanca.Proposta.name());
			} else 
				requisicaoMudancaDto.setStatus(SituacaoRequisicaoMudanca.Registrada.name());
			
			
			
			if(requisicaoMudancaDto.getIdGrupoAtual() == null){
				requisicaoMudancaDto.setIdGrupoAtual(tipoMudancaDTO.getIdGrupoExecutor());
			}
			requisicaoMudancaDto.setIdGrupoNivel1(requisicaoMudancaDto.getIdGrupoAtual());
			requisicaoMudancaDto.setIdCalendario(tipoMudancaDTO.getIdCalendario());
			requisicaoMudancaDto.setTempoDecorridoHH(new Integer(0));
			requisicaoMudancaDto.setTempoDecorridoMM(new Integer(0));
			requisicaoMudancaDto.setDataHoraSuspensao(null);
			requisicaoMudancaDto.setDataHoraReativacao(null);
			requisicaoMudancaDto.setSeqReabertura(new Integer(0));
			requisicaoMudancaDto.setDataHoraSolicitacao(new Timestamp(new java.util.Date().getTime()));
			requisicaoMudancaDto.setIdProprietario(requisicaoMudancaDto.getUsuarioDto().getIdEmpregado());
			
			
			
			
			/*if(requisicaoMudancaDto.getDataHoraTerminoAgendada() != null && requisicaoMudancaDto.getDataHoraInicioAgendada()!=null){
				determinaPrazo(requisicaoMudancaDto, tipoMudancaDTO.getIdCalendario());
			}else{
				requisicaoMudancaDto.setPrazoHH(00);
				requisicaoMudancaDto.setPrazoMM(00);
			}*/
			
			
			
			
			
			requisicaoMudancaDto.setDataHoraInicio(new Timestamp(new java.util.Date().getTime()));
			
			
			requisicaoMudancaDto.setDataHoraCaptura(requisicaoMudancaDto.getDataHoraInicio());
			
			contatoRequisicaoMudancaDto = this.criarContatoRequisicaoMudanca(requisicaoMudancaDto, tc);
			
			requisicaoMudancaDto.setIdContatoRequisicaoMudanca(contatoRequisicaoMudancaDto.getIdContatoRequisicaoMudanca());
			
			if(contatoRequisicaoMudancaDto.getIdLocalidade() != null){
				requisicaoMudancaDto.setIdLocalidade(contatoRequisicaoMudancaDto.getIdLocalidade());	
			}
			
			
			String remetente = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.RemetenteNotificacoesSolicitacao, null);
			if (remetente == null)
				throw new LogicException(i18n_Message("citcorpore.comum.notficacaoEmailParametrizado"));
			
			requisicaoMudancaDto = (RequisicaoMudancaDTO) requisicaoMudancaDao.create(requisicaoMudancaDto);
			
			this.criarOcorrenciaMudanca(requisicaoMudancaDto, tc);
			
			
			
			Source source = null;
			if(requisicaoMudancaDto != null){
				source = new Source(requisicaoMudancaDto.getRegistroexecucao());
				requisicaoMudancaDto.setRegistroexecucao(source.getTextExtractor().toString());
			}
			
			if (requisicaoMudancaDto != null && requisicaoMudancaDto.getRegistroexecucao() != null && !requisicaoMudancaDto.getRegistroexecucao().trim().equalsIgnoreCase("")){
				OcorrenciaMudancaDao ocorrenciaMudancaDao = new OcorrenciaMudancaDao();
				ocorrenciaMudancaDao.setTransactionControler(tc);
				OcorrenciaMudancaDTO ocorrenciaMudancaDto = new OcorrenciaMudancaDTO();	
				ocorrenciaMudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
				ocorrenciaMudancaDto.setDataregistro(UtilDatas.getDataAtual());
				ocorrenciaMudancaDto.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()));
				ocorrenciaMudancaDto.setTempoGasto(0);
				ocorrenciaMudancaDto.setDescricao(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Execucao.getDescricao());
				ocorrenciaMudancaDto.setDataInicio(UtilDatas.getDataAtual());
				ocorrenciaMudancaDto.setDataFim(UtilDatas.getDataAtual());
				ocorrenciaMudancaDto.setInformacoesContato("não se aplica");
				ocorrenciaMudancaDto.setRegistradopor(requisicaoMudancaDto.getUsuarioDto().getLogin());
				try {
					ocorrenciaMudancaDto.setDadosMudanca(new Gson().toJson(requisicaoMudancaDto));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ocorrenciaMudancaDto.setOrigem(br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia.OUTROS.getSigla().toString());
				ocorrenciaMudancaDto.setCategoria(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Criacao.getSigla());
				ocorrenciaMudancaDto.setOcorrencia(requisicaoMudancaDto.getRegistroexecucao());
				ocorrenciaMudancaDao.create(ocorrenciaMudancaDto);
			}
			
			

			if (requisicaoMudancaDto != null) {
				if (requisicaoMudancaDto.getListIdSolicitacaoServico() != null && requisicaoMudancaDto.getListIdSolicitacaoServico().size() > 0) {
					for (SolicitacaoServicoDTO solicitacaoServicoDTO : requisicaoMudancaDto.getListIdSolicitacaoServico()) {
						SolicitacaoServicoMudancaDTO solicitacaoServicoMudancaDTO = new SolicitacaoServicoMudancaDTO();
						solicitacaoServicoMudancaDTO.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
						solicitacaoServicoMudancaDTO.setIdSolicitacaoServico(solicitacaoServicoDTO.getIdSolicitacaoServico());
						solicitacaoServicoMudancaDao.create(solicitacaoServicoMudancaDTO);
					}
				}
				
				if(requisicaoMudancaDto.getListAprovacaoMudancaDTO()!=null){
					for(AprovacaoMudancaDTO aprovacaoMudancaDto : requisicaoMudancaDto.getListAprovacaoMudancaDTO() ){
						aprovacaoMudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
						aprovacaoMudancaDto.setDataHoraInicio(UtilDatas.getDataHoraAtual());
						aprovacaoMudancaDao.create(aprovacaoMudancaDto);
					}
				}
				
				if(requisicaoMudancaDto.getListAprovacaoPropostaDTO()!=null){
					for(AprovacaoPropostaDTO aprovacaoPropostaDto : requisicaoMudancaDto.getListAprovacaoPropostaDTO() ){
						aprovacaoPropostaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
						aprovacaoPropostaDto.setDataHoraInicio(UtilDatas.getDataHoraAtual());
						aprovacaoPropostaDao.create(aprovacaoPropostaDto);
					}
				}
				
				if(requisicaoMudancaDto.getListRequisicaoMudancaItemConfiguracaoDTO()!=null){
					for(RequisicaoMudancaItemConfiguracaoDTO requisicaoMudancaItemConfiguracaoDto : requisicaoMudancaDto.getListRequisicaoMudancaItemConfiguracaoDTO() ){
						requisicaoMudancaItemConfiguracaoDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
						requisicaoMudancaItemConfiguracaoDao.create(requisicaoMudancaItemConfiguracaoDto);
					}
				}
				
				if(requisicaoMudancaDto.getListProblemaMudancaDTO()!=null){
					for(ProblemaMudancaDTO problemaMudancaDto : requisicaoMudancaDto.getListProblemaMudancaDTO() ){
						problemaMudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
						problemaMudancaDao.create(problemaMudancaDto);
					}
				}
				
				if(requisicaoMudancaDto.getListRequisicaoMudancaRiscoDTO()!=null){
					for(RequisicaoMudancaRiscoDTO RequisicaoMudancaRiscoDto : requisicaoMudancaDto.getListRequisicaoMudancaRiscoDTO() ){
						RequisicaoMudancaRiscoDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
						requisicaoMudancaRiscoDao.create(RequisicaoMudancaRiscoDto);
					}
				}
				
				if(requisicaoMudancaDto.getListGrupoRequisicaoMudancaDTO()!=null){
					for(GrupoRequisicaoMudancaDTO grupoRequisicaoMudancaDto : requisicaoMudancaDto.getListGrupoRequisicaoMudancaDTO() ){
						grupoRequisicaoMudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
						grupoRequisicaoMudancaDao.create(grupoRequisicaoMudancaDto);
					}
				}
				
				//geber.costa
				if(requisicaoMudancaDto.getListLiberacaoMudancaDTO()!=null){
					for(LiberacaoMudancaDTO liberacaoMudancaDto : requisicaoMudancaDto.getListLiberacaoMudancaDTO() ){
						liberacaoMudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
						liberacaoMudancaDto.setIdLiberacao(liberacaoMudancaDto.getIdLiberacao());
						//O Trim foi utilizado para tratamento, os campos não virem com espaço
						if (liberacaoMudancaDto.getSituacaoLiberacao() != null) {
							liberacaoMudancaDto.setSituacaoLiberacao(liberacaoMudancaDto.getSituacaoLiberacao().trim());
						}
						if (liberacaoMudancaDto.getStatus() != null) {
							liberacaoMudancaDto.setStatus(liberacaoMudancaDto.getStatus().trim());
						}
						liberacaoMudancaDao.create(liberacaoMudancaDto);
					}
				}

				if(requisicaoMudancaDto.getDataHoraInicioAgendada() != null && requisicaoMudancaDto.getDataHoraTerminoAgendada() != null && (requisicaoMudancaDto.getIdGrupoAtvPeriodica() == null || requisicaoMudancaDto.getIdGrupoAtvPeriodica() == 0))
					throw new LogicException(i18n_Message("gerenciaservico.agendaratividade.informacoesGrupoAtividade"));
			
				if(requisicaoMudancaDto.getListRequisicaoMudancaServicoDTO()!=null){
					for(RequisicaoMudancaServicoDTO requisicaoMudancaServicoDto : requisicaoMudancaDto.getListRequisicaoMudancaServicoDTO() ){
						requisicaoMudancaServicoDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
						requisicaoMudancaServicoDao.create(requisicaoMudancaServicoDto);
					}
				}
			}

			//create Responsavel           
            Collection<RequisicaoMudancaResponsavelDTO> colRequisicaoMudancaResp = null;
            if(requisicaoMudancaDto != null){
            	colRequisicaoMudancaResp = requisicaoMudancaDto.getColResponsaveis();
            }
            if (colRequisicaoMudancaResp != null) {
                for (RequisicaoMudancaResponsavelDTO mudancaResponsavelDTO : colRequisicaoMudancaResp) {
                	mudancaResponsavelDTO.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
                	requisicaoMudancaResponsavelDao.create(mudancaResponsavelDTO);
                }
            }
            
            //gravar anexos de mudança
            HistoricoMudancaDTO historicoMudancaDTO = new HistoricoMudancaDTO();
            if(requisicaoMudancaDto != null && requisicaoMudancaDto.getColArquivosUpload() != null /*&& liberacaoDto.getColArquivosUpload().size() > 0*/){
            	this.gravaInformacoesGED(requisicaoMudancaDto, tc, historicoMudancaDTO);
            }
            
            //gravar anexos dos planos de reversao de mudança
            if(requisicaoMudancaDto != null && requisicaoMudancaDto.getColUploadPlanoDeReversaoGED() != null /*&& liberacaoDto.getColArquivosUpload().size() > 0*/){
            	this.gravaPlanoDeReversaoGED(requisicaoMudancaDto, tc, historicoMudancaDTO);
            }
			
            if(requisicaoMudancaDto != null){
            	execucaoMudancaService.registraMudanca(requisicaoMudancaDto, tc,requisicaoMudancaDto.getUsuarioDto());
            }
            
			tc.commit();
			tc.close();
			tc = null;
			
			if(requisicaoMudancaDto != null && requisicaoMudancaDto.getDataHoraInicioAgendada() != null){
				RequisicaoMudanca.salvaGrupoAtvPeriodicaEAgenda(requisicaoMudancaDto);
			}
		} catch (Exception e) {
			rollbackTransaction(tc, e);
			throw new ServiceException(e);
		}
		return model;
	}


	public void criarOcorrenciaMudanca(RequisicaoMudancaDTO requisicaoMudancaDto, TransactionControler tc) throws Exception {
		OcorrenciaMudancaDao ocorrenciaMudancaDao = new OcorrenciaMudancaDao();
		ocorrenciaMudancaDao.setTransactionControler(tc);
		OcorrenciaMudancaDTO ocorrenciaMudancaDto = new OcorrenciaMudancaDTO();	
		ocorrenciaMudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
		ocorrenciaMudancaDto.setDataregistro(UtilDatas.getDataAtual());
		ocorrenciaMudancaDto.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()));
		ocorrenciaMudancaDto.setTempoGasto(0);
		ocorrenciaMudancaDto.setDescricao(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Execucao.getDescricao());
		ocorrenciaMudancaDto.setDataInicio(UtilDatas.getDataAtual());
		ocorrenciaMudancaDto.setDataFim(UtilDatas.getDataAtual());
		ocorrenciaMudancaDto.setInformacoesContato("não se aplica");
		ocorrenciaMudancaDto.setRegistradopor(requisicaoMudancaDto.getUsuarioDto().getLogin());
		try {
			ocorrenciaMudancaDto.setDadosMudanca(new Gson().toJson(requisicaoMudancaDto));
		} catch (Exception e) {
			System.out.println("Falha na gravação de dadosMudanca - Objeto Gson");
			e.printStackTrace();
		}
		ocorrenciaMudancaDto.setOrigem(br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia.OUTROS.getSigla().toString());
		ocorrenciaMudancaDto.setCategoria(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Criacao.getSigla());
		ocorrenciaMudancaDao.create(ocorrenciaMudancaDto);
		
	}
	
	
	
	/**
	 * PODE SER UTILIZADO PARA SETAR OS CONTATOS DA REQUISICAO MUDANCA
	 * 
	 */
	public ContatoRequisicaoMudancaDTO criarContatoRequisicaoMudanca(RequisicaoMudancaDTO requisicaoMudancaDto, TransactionControler tc) throws ServiceException, LogicException {
		
		ContatoRequisicaoMudancaDao contatoRequisicaoMudancaDao = new ContatoRequisicaoMudancaDao();
		ContatoRequisicaoMudancaDTO contatoRequisicaoMudancaDto = new ContatoRequisicaoMudancaDTO();
		
		RequisicaoMudancaDao requisicaoMudancaDao = new RequisicaoMudancaDao();
		
		
		
		try{
			contatoRequisicaoMudancaDao.setTransactionControler(tc);
			requisicaoMudancaDao.setTransactionControler(tc);
		
			if(requisicaoMudancaDto.getIdContatoRequisicaoMudanca()!=null){
				
				contatoRequisicaoMudancaDto.setIdContatoRequisicaoMudanca(requisicaoMudancaDto.getIdContatoRequisicaoMudanca());
				contatoRequisicaoMudancaDto.setNomecontato(requisicaoMudancaDto.getNomeContato());
				contatoRequisicaoMudancaDto.setTelefonecontato(requisicaoMudancaDto.getTelefoneContato());
				contatoRequisicaoMudancaDto.setRamal(requisicaoMudancaDto.getRamal());
				if (requisicaoMudancaDto.getEmailSolicitante() != null) {
					contatoRequisicaoMudancaDto.setEmailcontato(requisicaoMudancaDto.getEmailSolicitante().trim());	
				}
				contatoRequisicaoMudancaDto.setObservacao(requisicaoMudancaDto.getObservacao());
				contatoRequisicaoMudancaDto.setIdLocalidade(requisicaoMudancaDto.getIdLocalidade());
				contatoRequisicaoMudancaDao.update(contatoRequisicaoMudancaDto);
				
			}else{
				contatoRequisicaoMudancaDto.setNomecontato(requisicaoMudancaDto.getNomeContato());
				contatoRequisicaoMudancaDto.setTelefonecontato(requisicaoMudancaDto.getTelefoneContato());
				contatoRequisicaoMudancaDto.setRamal(requisicaoMudancaDto.getRamal());
				if (requisicaoMudancaDto.getEmailSolicitante() != null) {
					contatoRequisicaoMudancaDto.setEmailcontato(requisicaoMudancaDto.getEmailSolicitante().trim());
				}
				contatoRequisicaoMudancaDto.setObservacao(requisicaoMudancaDto.getObservacao());
				contatoRequisicaoMudancaDto.setIdLocalidade(requisicaoMudancaDto.getIdLocalidade());
				contatoRequisicaoMudancaDto = (ContatoRequisicaoMudancaDTO) contatoRequisicaoMudancaDao.create(contatoRequisicaoMudancaDto);
			}
			
				
			
		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
			throw new ServiceException(e);
		}	
		return contatoRequisicaoMudancaDto;
	}

	@SuppressWarnings("unused")
	private void determinaPrazo(RequisicaoMudancaDTO requisicaoDto, Integer idCalendarioParm) throws Exception {
		if (requisicaoDto.getDataHoraTerminoAgendada() == null)
			throw new LogicException(i18n_Message("citcorpore.comum.Data/horaTerminoNaoDefinida"));

		CalculoJornadaDTO calculoDto = new CalculoJornadaDTO(idCalendarioParm, requisicaoDto.getDataHoraInicioAgendada());
		calculoDto = new CalendarioServiceEjb().calculaPrazoDecorrido(calculoDto, requisicaoDto.getDataHoraTerminoAgendada());
		requisicaoDto.setPrazoHH(calculoDto.getTempoDecorridoHH());
		requisicaoDto.setPrazoMM(calculoDto.getTempoDecorridoMM());
	}

	@SuppressWarnings("unused")
	private void determinaPrazo(RequisicaoMudancaDTO requisicaoDto) throws Exception {
		if (requisicaoDto.getDataHoraTerminoAgendada() == null)
			throw new LogicException(i18n_Message("citcorpore.comum.Data/horaTerminoNaoDefinida"));
		
		TipoMudancaDTO tipoMudancaDto = new TipoMudancaDTO();
		TipoMudancaDAO tipoMudancaDAO = new TipoMudancaDAO();
		tipoMudancaDto.setIdTipoMudanca(requisicaoDto.getIdTipoMudanca());
		tipoMudancaDto = (TipoMudancaDTO) tipoMudancaDAO.restore(tipoMudancaDto);
		
		CalculoJornadaDTO calculoDto = new CalculoJornadaDTO(tipoMudancaDto.getIdCalendario(), requisicaoDto.getDataHoraInicioAgendada());
		calculoDto = new CalendarioServiceEjb().calculaPrazoDecorrido(calculoDto, requisicaoDto.getDataHoraTerminoAgendada());
		requisicaoDto.setPrazoHH(calculoDto.getTempoDecorridoHH());
		requisicaoDto.setPrazoMM(calculoDto.getTempoDecorridoMM());
	}

	
	/**
	 * Lista os ics relacionados a requisição de mudança
	 * e atribui o nome do item de configuração para correta
	 * restauração de suas informações na table
	 * 
	 * @param requisicaoMudancaItemConfiguracaoDTO
	 * @throws ServiceException
	 * @throws Exception
	 */
	public ArrayList<RequisicaoMudancaItemConfiguracaoDTO> listItensRelacionadosRequisicaoMudanca(RequisicaoMudancaDTO requisicaoMudancaDTO) throws ServiceException, Exception{		
		ItemConfiguracaoDTO ic = null;
		RequisicaoMudancaItemConfiguracaoDao requisicaoMudancaItemConfiguracaoDao = new RequisicaoMudancaItemConfiguracaoDao();
		  
		ArrayList<RequisicaoMudancaItemConfiguracaoDTO> listaReqMudancaIC = (ArrayList<RequisicaoMudancaItemConfiguracaoDTO>) requisicaoMudancaItemConfiguracaoDao.findByIdMudancaEDataFim(requisicaoMudancaDTO.getIdRequisicaoMudanca());
			//		getRequisicaoMudancaItemConfiguracaoService().listByIdRequisicaoMudanca(requisicaoMudancaDTO.getIdRequisicaoMudanca());
		
		//atribui nome para os itens retornados
		if(listaReqMudancaIC != null){
			for(RequisicaoMudancaItemConfiguracaoDTO r : listaReqMudancaIC){
				ic = getItemConfiguracaoService().restoreByIdItemConfiguracao(r.getIdItemConfiguracao());
				if(ic != null){
					r.setNomeItemConfiguracao(ic.getIdentificacao());
				}
			}
		}
		
		return listaReqMudancaIC;
	}
	
	private ItemConfiguracaoService getItemConfiguracaoService() throws ServiceException, Exception{
		if(itemConfiguracaoService == null){
			itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
		}
		return itemConfiguracaoService;
	}
	
	
	/* (non-Javadoc)
	 * @see br.com.citframework.service.CrudServicePojoImpl#update(br.com.citframework.dto.IDto)
	 */
	
	public void update(IDto model, HttpServletRequest request) throws ServiceException, LogicException {
		RequisicaoMudancaDTO requisicaoMudancaDto = (RequisicaoMudancaDTO) model;
		RequisicaoMudancaDao requisicaoMudancaDao =  new RequisicaoMudancaDao();
		 LigacaoRequisicaoMudancaHistoricoResponsavelDTO ligacaoResponsavelDTO = new LigacaoRequisicaoMudancaHistoricoResponsavelDTO();
		 LigacaoRequisicaoMudancaResponsavelDao ligacaoResponsavelDao = new LigacaoRequisicaoMudancaResponsavelDao();
		 LigacaoRequisicaoMudancaHistoricoItemConfiguracaoDTO ligacaoRequisicaoMudancaHistoricoItemConfiguracaoDTO = new LigacaoRequisicaoMudancaHistoricoItemConfiguracaoDTO();
		 LigacaoRequisicaoMudancaItemConfiguracaoDao ligacaoRequisicaoMudancaItemConfiguracaoDao = new LigacaoRequisicaoMudancaItemConfiguracaoDao();
		 LigacaoRequisicaoMudancaHistoricoServicoDTO ligacaoRequisicaoMudancaHistoricoServicoDTO = new LigacaoRequisicaoMudancaHistoricoServicoDTO();
		 LigacaoRequisicaoMudancaServicoDao ligacaoRequisicaoMudancaServicoDao = new LigacaoRequisicaoMudancaServicoDao();
		 LigacaoRequisicaoMudancaHistoricoProblemaDTO ligacaoRequisicaoMudancaHistoricoProblemaDTO = new LigacaoRequisicaoMudancaHistoricoProblemaDTO();
		 LigacaoRequisicaoMudancaHistoricoGrupoDTO ligacaoRequisicaoMudancaHistoricoGrupoDTO = new LigacaoRequisicaoMudancaHistoricoGrupoDTO();
		 LigacaoRequisicaoMudancaProblemaDao ligacaoRequisicaoMudancaProblemaDao = new LigacaoRequisicaoMudancaProblemaDao();
		 LigacaoRequisicaoMudancaGrupoDao ligacaoRequisicaoMudancaGrupoDao = new LigacaoRequisicaoMudancaGrupoDao();
		 LigacaoRequisicaoMudancaHistoricoRiscosDTO ligaHistoricoRiscosDTO = new LigacaoRequisicaoMudancaHistoricoRiscosDTO();
		 LigacaoRequisicaoLiberacaoRiscosDao ligacaoriscoDao = new LigacaoRequisicaoLiberacaoRiscosDao();
		 
		 
		
		if(usuario == null){
			usuario = new Usuario();
		}
		
		if(usuario != null && requisicaoMudancaDto != null){
			usuario.setLocale(requisicaoMudancaDto.getUsuarioDto().getLocale());
		}
		
		try {
			requisicaoMudancaDto.setDataHoraInicio(getRequisicaoAtual(requisicaoMudancaDto.getIdRequisicaoMudanca()).getDataHoraInicio());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		if (requisicaoMudancaDto != null && requisicaoMudancaDto.getDataHoraInicioAgendada() != null && requisicaoMudancaDto.getHoraAgendamentoInicial() != null) {
			Timestamp dataHoraInicial = MontardataHoraAgendamentoInicial(requisicaoMudancaDto);
			requisicaoMudancaDto.setDataHoraInicioAgendada(dataHoraInicial);	
		}
		
		if (requisicaoMudancaDto != null && requisicaoMudancaDto.getDataHoraTerminoAgendada() != null && requisicaoMudancaDto.getHoraAgendamentoFinal() != null) {
			Timestamp dataHoraFinal = MontardataHoraAgendamentoFinal(requisicaoMudancaDto);
			requisicaoMudancaDto.setDataHoraTerminoAgendada(dataHoraFinal);	
			requisicaoMudancaDto.setDataHoraTermino(dataHoraFinal);
		}
		
		ContatoRequisicaoMudancaDTO contatoRequisicaoMudancaDto = new  ContatoRequisicaoMudancaDTO();
		TipoMudancaDTO tipoMudancaDto = new TipoMudancaDTO();
		SolicitacaoServicoMudancaDao solicitacaoServicoMudancaDao = new SolicitacaoServicoMudancaDao();
		
		AprovacaoMudancaDao aprovacaoMudancaDao = new AprovacaoMudancaDao();
		AprovacaoPropostaDao aprovacaoPropostaDao = new AprovacaoPropostaDao();
		
		
		TipoMudancaDAO tipoMudancaDAO = new TipoMudancaDAO();
		ProblemaMudancaDAO problemaMudancaDao = new ProblemaMudancaDAO();
		GrupoRequisicaoMudancaDao gruporequisicaomudancaDao = new GrupoRequisicaoMudancaDao();

		TransactionControler tc = new TransactionControlerImpl(requisicaoMudancaDao.getAliasDB());

		
		RequisicaoMudancaServicoDao requisicaoMudancaServicoDao = new RequisicaoMudancaServicoDao();
		RequisicaoMudancaItemConfiguracaoDao requisicaoMudancaItemConfiguracaoDao = new RequisicaoMudancaItemConfiguracaoDao();
		RequisicaoMudancaRiscoDao requisicaoMudancaRiscoDao = new RequisicaoMudancaRiscoDao();
		LiberacaoMudancaDao liberacaoMudancaDao = new LiberacaoMudancaDao();
		RequisicaoMudancaResponsavelDao mudancaResponsavelDao = new RequisicaoMudancaResponsavelDao();
		try {
			solicitacaoServicoMudancaDao.setTransactionControler(tc);
			requisicaoMudancaServicoDao.setTransactionControler(tc);
			requisicaoMudancaItemConfiguracaoDao.setTransactionControler(tc);
			tipoMudancaDAO.setTransactionControler(tc);
			requisicaoMudancaDao.setTransactionControler(tc);
			aprovacaoMudancaDao.setTransactionControler(tc);
			aprovacaoPropostaDao.setTransactionControler(tc);
			problemaMudancaDao.setTransactionControler(tc);
			gruporequisicaomudancaDao.setTransactionControler(tc);
			requisicaoMudancaRiscoDao.setTransactionControler(tc);
			liberacaoMudancaDao.setTransactionControler(tc);
			mudancaResponsavelDao.setTransactionControler(tc);
			ligacaoRequisicaoMudancaProblemaDao.setTransactionControler(tc);
			ligacaoRequisicaoMudancaGrupoDao.setTransactionControler(tc);
			ligacaoriscoDao.setTransactionControler(tc);
			tc.start();
			
			if(requisicaoMudancaDto != null){
				if(requisicaoMudancaDto.getIdTipoMudanca()!=null){
					tipoMudancaDto.setIdTipoMudanca(requisicaoMudancaDto.getIdTipoMudanca());
					tipoMudancaDto = (TipoMudancaDTO) tipoMudancaDAO.restore(tipoMudancaDto);
				}
				
				if(requisicaoMudancaDto.getListAprovacaoPropostaDTO()!=null && requisicaoMudancaDto.getFase().equalsIgnoreCase("Proposta")){
					aprovacaoPropostaDao.deleteByIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
					for(AprovacaoPropostaDTO aprovacaoPropostaDto : requisicaoMudancaDto.getListAprovacaoPropostaDTO() ){
						aprovacaoPropostaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
						aprovacaoPropostaDto.setDataHoraInicio(UtilDatas.getDataHoraAtual());
						aprovacaoPropostaDao.create(aprovacaoPropostaDto);
					}
				}
				
				if(requisicaoMudancaDto.getAcaoFluxo().equalsIgnoreCase("E") && requisicaoMudancaDto.getFase().equalsIgnoreCase("Proposta")){
					if(!requisicaoMudancaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoMudanca.Cancelada.name())){
						if(!validacaoAvancaFluxoProposta(requisicaoMudancaDto,tc)){
							throw new LogicException(i18n_Message("requisicaoMudanca.essaPropostaNaoFoiAprovada"));
						}
					}
				}
					
	
				if(requisicaoMudancaDto.getDataHoraTerminoAgendada() != null && requisicaoMudancaDto.getDataHoraInicioAgendada() !=null){
					
				calculaTempoAtraso(requisicaoMudancaDto);
					
					
				}else{
					requisicaoMudancaDto.setPrazoHH(00);
					requisicaoMudancaDto.setPrazoMM(00);
				}
				
				if(requisicaoMudancaDto.getStatus() != null && !requisicaoMudancaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoMudanca.Cancelada.name()) && !requisicaoMudancaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoMudanca.Rejeitada.name())){
					if(requisicaoMudancaDto.getAlterarSituacao().equalsIgnoreCase("N")){
						requisicaoMudancaDto.setStatus(getStatusAtual(requisicaoMudancaDto.getIdRequisicaoMudanca()));
					}
				}
			
			}
			
			//Gravando o historico
			HistoricoMudancaDTO historicoMudancaDTO = new HistoricoMudancaDTO();
			HistoricoMudancaDao historicoMudancaDao = new HistoricoMudancaDao();
			historicoMudancaDao.setTransactionControler(tc);
			if(requisicaoMudancaDto != null && requisicaoMudancaDto.getIdRequisicaoMudanca()!=null) {
				historicoMudancaDTO = (HistoricoMudancaDTO) historicoMudancaDao.create(this.createHistoricoMudanca(requisicaoMudancaDto));
				
				ControleGEDDao controleGEDDao = new ControleGEDDao();
				controleGEDDao.setTransactionControler(tc);
				
				historicoMudancaDTO.setColResponsaveis(this.listarColResponsaveis(historicoMudancaDTO));
				if(historicoMudancaDTO.getColResponsaveis() != null){
					for (RequisicaoMudancaResponsavelDTO requisicaoMudancaRespDTO : historicoMudancaDTO.getColResponsaveis()) {
						ligacaoResponsavelDTO.setIdRequisicaoMudanca(requisicaoMudancaRespDTO.getIdRequisicaoMudanca());
						ligacaoResponsavelDTO.setIdRequisicaoMudancaResp(requisicaoMudancaRespDTO.getIdRequisicaoMudancaResp());
						ligacaoResponsavelDTO.setIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
						ligacaoResponsavelDao.create(ligacaoResponsavelDTO);
						ligacaoResponsavelDTO = new LigacaoRequisicaoMudancaHistoricoResponsavelDTO();
					}
				}
				

				historicoMudancaDTO.setListRequisicaoMudancaItemConfiguracaoDTO(this.listarColItemConfiguracao(historicoMudancaDTO));
				if(historicoMudancaDTO.getListRequisicaoMudancaItemConfiguracaoDTO() != null){
					for (RequisicaoMudancaItemConfiguracaoDTO requisicaoMudancaItemConfiguracaoDTO : historicoMudancaDTO.getListRequisicaoMudancaItemConfiguracaoDTO()) {
						ligacaoRequisicaoMudancaHistoricoItemConfiguracaoDTO.setIdRequisicaoMudanca(requisicaoMudancaItemConfiguracaoDTO.getIdRequisicaoMudanca());
						ligacaoRequisicaoMudancaHistoricoItemConfiguracaoDTO.setIdrequisicaomudancaitemconfiguracao(requisicaoMudancaItemConfiguracaoDTO.getIdRequisicaoMudancaItemConfiguracao());
						ligacaoRequisicaoMudancaHistoricoItemConfiguracaoDTO.setIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
						ligacaoRequisicaoMudancaItemConfiguracaoDao.create(ligacaoRequisicaoMudancaHistoricoItemConfiguracaoDTO);
						ligacaoRequisicaoMudancaHistoricoItemConfiguracaoDTO = new LigacaoRequisicaoMudancaHistoricoItemConfiguracaoDTO();
					}
				}
				
				historicoMudancaDTO.setListRequisicaoMudancaServicoDTO(this.listarServico(historicoMudancaDTO));
				if(historicoMudancaDTO.getListRequisicaoMudancaServicoDTO() != null){
					for (RequisicaoMudancaServicoDTO requisicaoMudancaServicoDTO : historicoMudancaDTO.getListRequisicaoMudancaServicoDTO()) {
						ligacaoRequisicaoMudancaHistoricoServicoDTO.setIdRequisicaoMudanca(requisicaoMudancaServicoDTO.getIdRequisicaoMudanca());
						ligacaoRequisicaoMudancaHistoricoServicoDTO.setIdrequisicaomudancaservico(requisicaoMudancaServicoDTO.getIdRequisicaoMudancaServico());
						ligacaoRequisicaoMudancaHistoricoServicoDTO.setIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
						ligacaoRequisicaoMudancaServicoDao.create(ligacaoRequisicaoMudancaHistoricoServicoDTO);
						ligacaoRequisicaoMudancaHistoricoServicoDTO = new LigacaoRequisicaoMudancaHistoricoServicoDTO();
					}
				}
				
				historicoMudancaDTO.setListProblemaMudancaDTO(this.listarProblema(historicoMudancaDTO));
				if(historicoMudancaDTO.getListProblemaMudancaDTO() != null){
					for (ProblemaMudancaDTO problemaMudancaDTO : historicoMudancaDTO.getListProblemaMudancaDTO()) {
						ligacaoRequisicaoMudancaHistoricoProblemaDTO.setIdRequisicaoMudanca(problemaMudancaDTO.getIdRequisicaoMudanca());
						ligacaoRequisicaoMudancaHistoricoProblemaDTO.setIdProblemaMudanca(problemaMudancaDTO.getIdProblemaMudanca());
						ligacaoRequisicaoMudancaHistoricoProblemaDTO.setIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
						ligacaoRequisicaoMudancaProblemaDao.create(ligacaoRequisicaoMudancaHistoricoProblemaDTO);
						ligacaoRequisicaoMudancaHistoricoProblemaDTO = new LigacaoRequisicaoMudancaHistoricoProblemaDTO();
					}
				}
				
				historicoMudancaDTO.setListGrupoRequisicaoMudancaDTO(this.listarGrupo(historicoMudancaDTO));
				if(historicoMudancaDTO.getListGrupoRequisicaoMudancaDTO() != null){
					for (GrupoRequisicaoMudancaDTO gruporequisicaomudancaDTO : historicoMudancaDTO.getListGrupoRequisicaoMudancaDTO()) {
						ligacaoRequisicaoMudancaHistoricoGrupoDTO.setIdRequisicaoMudanca(gruporequisicaomudancaDTO.getIdRequisicaoMudanca());
						ligacaoRequisicaoMudancaHistoricoGrupoDTO.setIdGrupoRequisicaoMudanca(gruporequisicaomudancaDTO.getIdGrupoRequisicaoMudanca());
						ligacaoRequisicaoMudancaHistoricoGrupoDTO.setIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
						ligacaoRequisicaoMudancaGrupoDao.create(ligacaoRequisicaoMudancaHistoricoGrupoDTO);
						ligacaoRequisicaoMudancaHistoricoGrupoDTO = new LigacaoRequisicaoMudancaHistoricoGrupoDTO();
					}
				}
				
				historicoMudancaDTO.setListRequisicaoMudancaRiscoDTO(this.listarRiscos(historicoMudancaDTO));
				if(historicoMudancaDTO.getListRequisicaoMudancaRiscoDTO() != null){
					for (RequisicaoMudancaRiscoDTO riscoMudancaDTO : historicoMudancaDTO.getListRequisicaoMudancaRiscoDTO()) {
						ligaHistoricoRiscosDTO.setIdRequisicaoMudanca(historicoMudancaDTO.getIdRequisicaoMudanca());
						ligaHistoricoRiscosDTO.setIdRequisicaoMudancaRisco(riscoMudancaDTO.getIdRequisicaoMudancaRisco());
						ligaHistoricoRiscosDTO.setIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
						ligacaoriscoDao.create(ligaHistoricoRiscosDTO);
						ligacaoRequisicaoMudancaHistoricoProblemaDTO = new LigacaoRequisicaoMudancaHistoricoProblemaDTO();
					}
				}
				
				historicoMudancaDTO.setListLiberacaoMudancaDTO(listarLiberacoes(historicoMudancaDTO));
				if(historicoMudancaDTO.getListLiberacaoMudancaDTO() != null && historicoMudancaDTO.getListLiberacaoMudancaDTO().size() > 0){
					gravarLiberacaoHistorico(historicoMudancaDTO, tc);
				}
				
				//gravando historico mudancaSolicitacaoServico
				List<RequisicaoMudancaDTO> listSolicitacaoServicosMudanca =  new ArrayList<RequisicaoMudancaDTO>();
				listSolicitacaoServicosMudanca = this.listarSolicitacaoServico(historicoMudancaDTO);
				if(listSolicitacaoServicosMudanca != null){
					gravarSolicitacaoServicoHistoricos(historicoMudancaDTO, listSolicitacaoServicosMudanca, tc);
				}
				
				//gravando o historico de aprovação de mudança.
				historicoMudancaDTO.setListAprovacaoMudancaDTO(this.listarAprovacoes(historicoMudancaDTO));
				if(historicoMudancaDTO.getListAprovacaoMudancaDTO() != null){
					for (AprovacaoMudancaDTO aprovacaoMudancaDTO : historicoMudancaDTO.getListAprovacaoMudancaDTO()) {
						aprovacaoMudancaDTO.setIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
						aprovacaoMudancaDao.create(aprovacaoMudancaDTO);
					}
				}
				
			}
			
			
			
			
			contatoRequisicaoMudancaDto = this.criarContatoRequisicaoMudanca(requisicaoMudancaDto, tc);
			if(contatoRequisicaoMudancaDto!=null){
				requisicaoMudancaDto.setIdContatoRequisicaoMudanca(contatoRequisicaoMudancaDto.getIdContatoRequisicaoMudanca());
			}
			
			if(requisicaoMudancaDto != null){
				if(requisicaoMudancaDto.getIdGrupoAtual()==null){
					requisicaoMudancaDto.setIdGrupoAtual(tipoMudancaDto.getIdGrupoExecutor());
					
				}
				if(requisicaoMudancaDto.getAcaoFluxo().equalsIgnoreCase("E")){
					if(requisicaoMudancaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoMudanca.Executada.name())){
						if(requisicaoMudancaDto.getFechamento()==null ||requisicaoMudancaDto.getFechamento().equalsIgnoreCase("") ){
							throw new LogicException(i18n_Message("citcorpore.comum.informeFechamento"));
						}
					}
				}
				
				
				if(requisicaoMudancaDto.getStatus() != null && requisicaoMudancaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoMudanca.Cancelada.name())){
					if(requisicaoMudancaDto.getFechamento()==null ||requisicaoMudancaDto.getFechamento().equalsIgnoreCase("") ){
						throw new LogicException(i18n_Message("citcorpore.comum.informeFechamento"));
					}
				}
			
				solicitacaoServicoMudancaDao.deleteByIdMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
			}
			
			if (requisicaoMudancaDto != null) {
				if (requisicaoMudancaDto.getListIdSolicitacaoServico() != null && requisicaoMudancaDto.getListIdSolicitacaoServico().size() > 0) {
					for (SolicitacaoServicoDTO solicitacaoServicoDTO : requisicaoMudancaDto.getListIdSolicitacaoServico()) {
						SolicitacaoServicoMudancaDTO solicitacaoServicoMudancaDTO = new SolicitacaoServicoMudancaDTO();

						solicitacaoServicoMudancaDTO.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
						solicitacaoServicoMudancaDTO.setIdSolicitacaoServico(solicitacaoServicoDTO.getIdSolicitacaoServico());
						solicitacaoServicoMudancaDao.create(solicitacaoServicoMudancaDTO);
					}
				}
				
			
			}
			/*
			 * Tratando os relacionamentos
			 */
			
			/*
			 * Tratando exclusão e logo apos inclusão de todos os problemas vinculado a esta mudança
			 */
			
			/*problemaMudancaDao.deleteByIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());	
			if(requisicaoMudancaDto.getListProblemaMudancaDTO()!= null){			
				for(ProblemaMudancaDTO dto : requisicaoMudancaDto.getListProblemaMudancaDTO()){
					dto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
					problemaMudancaDao.create(dto);
				}
			}*/
			//adiciona e deleta logicamente os itens da grid de problemas
			if(requisicaoMudancaDto != null){
				 if(requisicaoMudancaDto.getListProblemaMudancaDTO() != null && requisicaoMudancaDto.getListProblemaMudancaDTO().size() > 0){
					 this.deleteAdicionaTabelaProblema(requisicaoMudancaDto, tc);
		            }else{
		            	ProblemaMudancaDAO problemadaoDao = new ProblemaMudancaDAO();
		        		Collection<ProblemaMudancaDTO> ListProblemaMudancaaDTO = problemadaoDao.findByIdMudancaEDataFim(requisicaoMudancaDto.getIdRequisicaoMudanca());
		        		if (ListProblemaMudancaaDTO != null && ListProblemaMudancaaDTO.size() > 0) {
							for (ProblemaMudancaDTO problemaMudancaDTO : ListProblemaMudancaaDTO) {
								problemaMudancaDTO.setDataFim(UtilDatas.getDataAtual());
								problemaMudancaDao.update(problemaMudancaDTO);
							}
						}
		            }
				 
				 if(requisicaoMudancaDto.getListGrupoRequisicaoMudancaDTO() != null && requisicaoMudancaDto.getListGrupoRequisicaoMudancaDTO().size() > 0){
					 this.deleteAdicionaTabelaGrupo(requisicaoMudancaDto, tc);
		            }else{
		            	GrupoRequisicaoMudancaDao gruporequisicaomudancaDao1 = new GrupoRequisicaoMudancaDao();
		        		Collection<GrupoRequisicaoMudancaDTO> ListGrupoRequisicaoMudanca = gruporequisicaomudancaDao1.findByIdMudancaEDataFim(requisicaoMudancaDto.getIdRequisicaoMudanca());
		        		if (ListGrupoRequisicaoMudanca != null && ListGrupoRequisicaoMudanca.size() > 0) {
							for (GrupoRequisicaoMudancaDTO gruporequisicaomudancaDTO : ListGrupoRequisicaoMudanca) {
								gruporequisicaomudancaDTO.setDataFim(UtilDatas.getDataAtual());
								gruporequisicaomudancaDao1.update(gruporequisicaomudancaDTO);
							}
						}
		            }
				
				
				/*requisicaoMudancaRiscoDao.deleteByIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());	
				if(requisicaoMudancaDto.getListRequisicaoMudancaRiscoDTO()!= null){			
					for(RequisicaoMudancaRiscoDTO dto : requisicaoMudancaDto.getListRequisicaoMudancaRiscoDTO()){
						dto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
						requisicaoMudancaRiscoDao.create(dto);
					}
				}*/
				 if(requisicaoMudancaDto.getListRequisicaoMudancaRiscoDTO() != null && requisicaoMudancaDto.getListRequisicaoMudancaRiscoDTO().size() > 0){
					 this.deleteAdicionaTabelaRiscos(requisicaoMudancaDto, tc);
		            }else{
		            	RequisicaoMudancaRiscoDao riscosDao = new RequisicaoMudancaRiscoDao();
		        		Collection<RequisicaoMudancaRiscoDTO> ListRiscosMudancaaDTO = riscosDao.findByIdRequisicaoMudancaEDataFim(requisicaoMudancaDto.getIdRequisicaoMudanca());
		        		if (ListRiscosMudancaaDTO != null && ListRiscosMudancaaDTO.size() > 0) {
							for (RequisicaoMudancaRiscoDTO riscosMudancaDTO : ListRiscosMudancaaDTO) {
								riscosMudancaDTO.setDataFim(UtilDatas.getDataAtual());
								riscosMudancaDTO.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
								riscosDao.update(riscosMudancaDTO);
							}
						}
		            }
				
				//geber.costa
				liberacaoMudancaDao.deleteByIdMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
				if(requisicaoMudancaDto.getListLiberacaoMudancaDTO()!= null){			
					for(LiberacaoMudancaDTO dto : requisicaoMudancaDto.getListLiberacaoMudancaDTO()){
						dto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
						liberacaoMudancaDao.create(dto);
					}
				}
			}
			ArrayList<RequisicaoMudancaServicoDTO>  servicosBanco = null;
			
			RequisicaoMudancaServicoDTO aux = null;
			if (requisicaoMudancaDto != null && requisicaoMudancaDto.getListRequisicaoMudancaServicoDTO() != null) {
				// se não existir no banco, cria, caso contrário, atualiza

				for (RequisicaoMudancaServicoDTO requisicaoMudancaServicoDTO : requisicaoMudancaDto.getListRequisicaoMudancaServicoDTO()) {
					requisicaoMudancaServicoDTO.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());

					aux = (RequisicaoMudancaServicoDTO) requisicaoMudancaServicoDao.restoreByChaveComposta(requisicaoMudancaServicoDTO);

					if (aux == null) {
						requisicaoMudancaServicoDao.create(requisicaoMudancaServicoDTO);
					} else {
						requisicaoMudancaServicoDao.update(aux);
					}
				}

			}
			// confere se existe algo no banco que não está na lista salva, e
			// deleta
			if(requisicaoMudancaDto != null){
				servicosBanco = requisicaoMudancaServicoDao.listByIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
			}
			if (servicosBanco != null) {
				for (RequisicaoMudancaServicoDTO i : servicosBanco) {
					if (!requisicaoMudancaServicoExisteNaLista(i, requisicaoMudancaDto.getListRequisicaoMudancaServicoDTO())) {
						i.setDataFim(UtilDatas.getDataAtual());
						requisicaoMudancaServicoDao.update(i);
					}
				}
			}

			ArrayList<RequisicaoMudancaItemConfiguracaoDTO> icsBanco = null;
			RequisicaoMudancaItemConfiguracaoDTO requisicaoMudancaItemConfiguracaoDTO2 = new RequisicaoMudancaItemConfiguracaoDTO();

			if (requisicaoMudancaDto != null && requisicaoMudancaDto.getListRequisicaoMudancaItemConfiguracaoDTO() != null) {
				// se não existir no banco, cria, caso contrário, atualiza
				for (RequisicaoMudancaItemConfiguracaoDTO requisicaoMudancaItemConfiguracaoDTO : requisicaoMudancaDto.getListRequisicaoMudancaItemConfiguracaoDTO()) {

					requisicaoMudancaItemConfiguracaoDTO.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());

					requisicaoMudancaItemConfiguracaoDTO2 = (RequisicaoMudancaItemConfiguracaoDTO) requisicaoMudancaItemConfiguracaoDao.restoreByChaveComposta(requisicaoMudancaItemConfiguracaoDTO);

					if (requisicaoMudancaItemConfiguracaoDTO2 == null) {
						requisicaoMudancaItemConfiguracaoDao.create(requisicaoMudancaItemConfiguracaoDTO);
					} else {
						requisicaoMudancaItemConfiguracaoDao.update(requisicaoMudancaItemConfiguracaoDTO2);
					}
				}
			}
			// confere se existe algo no banco que não está na lista salva, e
			// deleta
			if(requisicaoMudancaDto != null){
				icsBanco = requisicaoMudancaItemConfiguracaoDao.listByIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
			}
			if (icsBanco != null) {
				for (RequisicaoMudancaItemConfiguracaoDTO i : icsBanco) {
					if (!requisicaoMudancaICExisteNaLista(i, requisicaoMudancaDto.getListRequisicaoMudancaItemConfiguracaoDTO())) {
						i.setDataFim(UtilDatas.getDataAtual());
						requisicaoMudancaItemConfiguracaoDao.update(i);
					}
				}
			}
			
			//update Responsavel
			if(requisicaoMudancaDto != null && requisicaoMudancaDto.getColResponsaveis() != null && requisicaoMudancaDto.getColResponsaveis().size() > 0){
            	this.deleteAdicionaTabelaResponsavel(requisicaoMudancaDto, tc);
            }else{
            	RequisicaoMudancaResponsavelDao requisicaoMudancaResponsavelDao =  new RequisicaoMudancaResponsavelDao();
            	Collection<RequisicaoMudancaResponsavelDTO> responsavel = requisicaoMudancaResponsavelDao.findByIdMudancaEDataFim(requisicaoMudancaDto.getIdRequisicaoMudanca());
            	if(responsavel != null && responsavel.size() > 0){	
            		for (RequisicaoMudancaResponsavelDTO requisicaoLiberacaoResponsavelDTO : responsavel) {
            			requisicaoLiberacaoResponsavelDTO.setDataFim(UtilDatas.getDataAtual());
            			requisicaoMudancaResponsavelDao.update(requisicaoLiberacaoResponsavelDTO);
            		}
            	}
            }
			
			/*//update Responsavel
            mudancaResponsavelDao.deleteByIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
            Collection<RequisicaoMudancaResponsavelDTO> colMudancaResponsavelResp = requisicaoMudancaDto.getColResponsaveis();
            if (colMudancaResponsavelResp != null) {
                for (RequisicaoMudancaResponsavelDTO mudancaResponsavelDTO : colMudancaResponsavelResp) {
                	mudancaResponsavelDTO.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
                	mudancaResponsavelDao.create(mudancaResponsavelDTO);
                }
            }*/
			
//			// gravando a aprovação de mudança
//			if(requisicaoMudancaDto.getListAprovacaoMudancaDTO()!=null && !requisicaoMudancaDto.getFase().equalsIgnoreCase("Proposta")){
//				aprovacaoMudancaDao.deleteByIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
//				for(AprovacaoMudancaDTO aprovacaoMudancaDto : requisicaoMudancaDto.getListAprovacaoMudancaDTO() ){
//					aprovacaoMudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
//					aprovacaoMudancaDto.setDataHoraInicio(UtilDatas.getDataHoraAtual());
//					aprovacaoMudancaDao.create(aprovacaoMudancaDto);
//				}
//			}
			
			//gravando historico de anexos
			HistoricoGEDDao historicoGEDDao = new HistoricoGEDDao();
            Collection<HistoricoGEDDTO> colHistoricoGed = new ArrayList<HistoricoGEDDTO>();
            colHistoricoGed = historicoGEDDao.listByIdTabelaAndIdLiberacao(ControleGEDDTO.TABELA_REQUISICAOMUDANCA, requisicaoMudancaDto.getIdRequisicaoMudanca());
            if(colHistoricoGed != null && colHistoricoGed.size() > 0){
            	for (HistoricoGEDDTO historicoGEDDTO : colHistoricoGed) {
					historicoGEDDTO.setDataFim(UtilDatas.getDataAtual());
					historicoGEDDTO.setIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
					historicoGEDDao.update(historicoGEDDTO);
				}
            }
            if(requisicaoMudancaDto.getColArquivosUpload() != null /*&& liberacaoDto.getColArquivosUpload().size() > 0*/){
            	this.gravaInformacoesGED(requisicaoMudancaDto, tc, historicoMudancaDTO);
            }
            //gravando historico de anexos
            Collection<HistoricoGEDDTO> colHistoricoPlanoReversaoGed = new ArrayList<HistoricoGEDDTO>();
            colHistoricoPlanoReversaoGed = historicoGEDDao.listByIdTabelaAndIdLiberacao(ControleGEDDTO.TABELA_PLANO_REVERSAO_MUDANCA, requisicaoMudancaDto.getIdRequisicaoMudanca());
            if(colHistoricoPlanoReversaoGed != null && colHistoricoPlanoReversaoGed.size() > 0){
            	for (HistoricoGEDDTO historicoGEDDTO : colHistoricoPlanoReversaoGed) {
            		historicoGEDDTO.setDataFim(UtilDatas.getDataAtual());
            		historicoGEDDTO.setIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
            		historicoGEDDao.update(historicoGEDDTO);
            	}
            }
            if(requisicaoMudancaDto.getColUploadPlanoDeReversaoGED() != null /*&& liberacaoDto.getColArquivosUpload().size() > 0*/){
            	this.gravaPlanoDeReversaoGED(requisicaoMudancaDto, tc, historicoMudancaDTO);
            }
            
			if(requisicaoMudancaDto.getStatus().equalsIgnoreCase(Enumerados.SituacaoRequisicaoMudanca.Resolvida.getDescricao())){
				this.fechaRelacionamentoMudanca(tc, requisicaoMudancaDto);
			}
			
			
			if(requisicaoMudancaDto.getRegistroexecucao() != null){
				Source source = new Source(requisicaoMudancaDto.getRegistroexecucao());
				requisicaoMudancaDto.setRegistroexecucao(source.getTextExtractor().toString());
			}
			
			ExecucaoMudancaServiceEjb execucaoMudancaService = new ExecucaoMudancaServiceEjb();
			if (requisicaoMudancaDto.getIdTarefa() == null) {
				requisicaoMudancaDao.update(requisicaoMudancaDto);
			} else {

				if (requisicaoMudancaDto.getFase() != null && !requisicaoMudancaDto.getFase().equalsIgnoreCase("")) {
					requisicaoMudancaDao.updateFase(requisicaoMudancaDto.getIdRequisicaoMudanca(), requisicaoMudancaDto.getFase());
					requisicaoMudancaDao.update(requisicaoMudancaDto);
				} else {
						if (tipoMudancaDto != null) {
							requisicaoMudancaDao.update(model);
						} else {
							throw new LogicException(i18n_Message("requisicaoMudanca.categoriaMudancaNaoLocalizada"));
						}
				}
				if(requisicaoMudancaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoMudanca.Cancelada.name()) || requisicaoMudancaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoMudanca.Rejeitada.name())){
					execucaoMudancaService.encerra(requisicaoMudancaDto.getUsuarioDto(), requisicaoMudancaDto, tc);					
				}else{
					execucaoMudancaService.executa(requisicaoMudancaDto, requisicaoMudancaDto.getIdTarefa(), requisicaoMudancaDto.getAcaoFluxo(), tc);
				}	
			
			}
			
			if (requisicaoMudancaDto.getRegistroexecucao() != null && !requisicaoMudancaDto.getRegistroexecucao().trim().equalsIgnoreCase("")){
				OcorrenciaMudancaDao ocorrenciaMudancaDao = new OcorrenciaMudancaDao();
				ocorrenciaMudancaDao.setTransactionControler(tc);
				OcorrenciaMudancaDTO ocorrenciaMudancaDto = new OcorrenciaMudancaDTO();	
				ocorrenciaMudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
				ocorrenciaMudancaDto.setDataregistro(UtilDatas.getDataAtual());
				ocorrenciaMudancaDto.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()));
				ocorrenciaMudancaDto.setTempoGasto(0);
				ocorrenciaMudancaDto.setDescricao(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Execucao.getDescricao());
				ocorrenciaMudancaDto.setDataInicio(UtilDatas.getDataAtual());
				ocorrenciaMudancaDto.setDataFim(UtilDatas.getDataAtual());
				ocorrenciaMudancaDto.setInformacoesContato("não se aplica");
				ocorrenciaMudancaDto.setRegistradopor(requisicaoMudancaDto.getUsuarioDto().getLogin());
				ocorrenciaMudancaDto.setDadosMudanca(new Gson().toJson(requisicaoMudancaDto));
				ocorrenciaMudancaDto.setOrigem(br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia.OUTROS.getSigla().toString());
				ocorrenciaMudancaDto.setCategoria(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Criacao.getSigla());
				ocorrenciaMudancaDto.setOcorrencia(requisicaoMudancaDto.getRegistroexecucao());
				ocorrenciaMudancaDao.create(ocorrenciaMudancaDto);
			}
			
			if(requisicaoMudancaDto.getAcaoFluxo().equalsIgnoreCase("E")){
				if(requisicaoMudancaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoMudanca.Executada.name()) || requisicaoMudancaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoMudanca.Concluida.name())  ){
					this.fechaRelacionamentoMudanca(tc, requisicaoMudancaDto);
				}
			}


			tc.commit();
			tc.close();
			tc = null;
			
			if(requisicaoMudancaDto.getDataHoraInicioAgendada() != null)
				RequisicaoMudanca.salvaGrupoAtvPeriodicaEAgenda(requisicaoMudancaDto);

		} catch (Exception e) {
			rollbackTransaction(tc, e);
			throw new ServiceException(e);
		}
	}

	public RequisicaoMudancaDTO restoreAll(Integer idRequisicaoMudanca) throws Exception {
		return restoreAll(idRequisicaoMudanca, null);
	}

	public RequisicaoMudancaDTO restoreAll(Integer idRequisicaoMudanca, TransactionControler tc) throws Exception {
		RequisicaoMudancaDao requisicaoDao = new RequisicaoMudancaDao();
		if (tc != null)
			requisicaoDao.setTransactionControler(tc);
		RequisicaoMudancaDTO requisicaoDto = new RequisicaoMudancaDTO();
		requisicaoDto.setIdRequisicaoMudanca(idRequisicaoMudanca);
		requisicaoDto = (RequisicaoMudancaDTO) requisicaoDao.restore(requisicaoDto);
		if (requisicaoDto != null && requisicaoDto.getDataHoraInicioAgendada() != null) {
			Timestamp dataHoraTerminoAgendada = requisicaoDto.getDataHoraInicioAgendada();
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			String horaAgendamentoInicialSTR = format.format(dataHoraTerminoAgendada);
			String horaInicial = horaAgendamentoInicialSTR.substring(11, 16);
			requisicaoDto.setHoraAgendamentoInicial(horaInicial.trim());
		}
		if (requisicaoDto != null && requisicaoDto.getDataHoraTermino() != null && requisicaoDto.getDataHoraTerminoStr() != null) {
			Timestamp dataHoraTerminoAgendada = requisicaoDto.getDataHoraTerminoAgendada();
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			if(dataHoraTerminoAgendada!=null){
				String horaAgendamentoFinallSTR = format.format(dataHoraTerminoAgendada);
				String horaFinal = horaAgendamentoFinallSTR.substring(11, 16);
				requisicaoDto.setHoraAgendamentoFinal(horaFinal.trim());
			}
			
		}
		if(requisicaoDto != null && requisicaoDto.getDescricao()!=null){
			Source source = new Source(requisicaoDto.getDescricao());
			requisicaoDto.setDescricao(source.getTextExtractor().toString());
		}
		
		if (requisicaoDto != null) {
			requisicaoDto.setDataHoraTerminoStr(requisicaoDto.getDataHoraTerminoStr());

			EmpregadoDTO empregadoDto = new EmpregadoDao().restoreByIdEmpregado(requisicaoDto.getIdSolicitante());
			if (empregadoDto != null) {
				requisicaoDto.setNomeSolicitante(empregadoDto.getNome());
				requisicaoDto.setEmailSolicitante(empregadoDto.getEmail());
			}

			if(requisicaoDto.getIdProprietario() != null){
				UsuarioDTO usuarioDto = new UsuarioDTO();
				UsuarioDao usuarioDao = new UsuarioDao();
				//usuarioDto.setIdUsuario(requisicaoDto.getIdProprietario());
				//usuarioDto = (UsuarioDTO) usuarioDao.restore(usuarioDto);
				/**
				 * Motivo: Restaura o usuário a partir do idProprietario gravado no banco de dados
				 * Autor: flavio.santana
				 * Data/Hora: 28/11/2013 17:50
				 */
				usuarioDto = (UsuarioDTO) usuarioDao.restoreByIdEmpregado(requisicaoDto.getIdProprietario());
				if(usuarioDto != null){
					requisicaoDto.setResponsavelAtual(usuarioDto.getLogin());
				}
			}
			
			if (requisicaoDto.getIdGrupoAtual() != null) {
				GrupoDao grupoDao = new GrupoDao();
				GrupoDTO grupoDto = new GrupoDTO();
				grupoDto.setIdGrupo(requisicaoDto.getIdGrupoAtual());
				grupoDto = (GrupoDTO) grupoDao.restore(grupoDto);
				if (grupoDto != null)
					requisicaoDto.setNomeGrupoAtual(grupoDto.getSigla());
			}

			if (requisicaoDto.getIdGrupoNivel1() != null) {
				GrupoDao grupoDao = new GrupoDao();
				GrupoDTO grupoDto = new GrupoDTO();
				grupoDto.setIdGrupo(requisicaoDto.getIdGrupoNivel1());
				grupoDto = (GrupoDTO) grupoDao.restore(grupoDto);
				if (grupoDto != null)
					requisicaoDto.setNomeGrupoNivel1(grupoDto.getSigla());
			}
			
			if(requisicaoDto.getIdContatoRequisicaoMudanca()!=null){
				ContatoRequisicaoMudancaDTO contatoRequisicaoMudancaDto = new ContatoRequisicaoMudancaDTO();
				ContatoRequisicaoMudancaDao contatoRequisicaoMudancaDao = new ContatoRequisicaoMudancaDao();
				
				if(CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase("SQLSERVER")){
					if (tc != null)
						contatoRequisicaoMudancaDao.setTransactionControler(tc);
				}
				
				contatoRequisicaoMudancaDto.setIdContatoRequisicaoMudanca(requisicaoDto.getIdContatoRequisicaoMudanca());
				contatoRequisicaoMudancaDto = (ContatoRequisicaoMudancaDTO) contatoRequisicaoMudancaDao.restore(contatoRequisicaoMudancaDto);
				if(contatoRequisicaoMudancaDto!=null){
					requisicaoDto.setNomeContato(contatoRequisicaoMudancaDto.getNomecontato());
			}
			}
			if(requisicaoDto.getIdTipoMudanca()!=null){
				TipoMudancaDTO tipoMudancaDto = new TipoMudancaDTO();
				TipoMudancaDAO tipoMudancaDao = new TipoMudancaDAO();
				
				tipoMudancaDto.setIdTipoMudanca(requisicaoDto.getIdTipoMudanca());
				tipoMudancaDto =  (TipoMudancaDTO) tipoMudancaDao.restore(tipoMudancaDto);
				if(tipoMudancaDto!=null){
					requisicaoDto.setTipo(tipoMudancaDto.getNomeTipoMudanca());
				}
			}
				
			

		}
		return verificaAtraso(requisicaoDto);
	}
	
	public RequisicaoMudancaDTO restoreAllReuniao(Integer idRequisicaoMudanca, Integer idReuniaoRequisicaoMudanca, TransactionControler tc) throws Exception {
		RequisicaoMudancaDao requisicaoDao = new RequisicaoMudancaDao();
		if (tc != null)
			requisicaoDao.setTransactionControler(tc);
		RequisicaoMudancaDTO requisicaoDto = new RequisicaoMudancaDTO();
		requisicaoDto.setIdRequisicaoMudanca(idRequisicaoMudanca);
		requisicaoDto = (RequisicaoMudancaDTO) requisicaoDao.restore(requisicaoDto);
		if(requisicaoDto != null && requisicaoDto.getDescricao()!=null){
			Source source = new Source(requisicaoDto.getDescricao());
			requisicaoDto.setDescricao(source.getTextExtractor().toString());
		}
		
		if (requisicaoDto != null) {
			requisicaoDto.setDataHoraTerminoStr(requisicaoDto.getDataHoraTerminoStr());

			EmpregadoDTO empregadoDto = new EmpregadoDao().restoreByIdEmpregado(requisicaoDto.getIdSolicitante());
			if (empregadoDto != null) {
				requisicaoDto.setNomeSolicitante(empregadoDto.getNome());
				requisicaoDto.setEmailSolicitante(empregadoDto.getEmail());
			}

			if (requisicaoDto.getIdGrupoAtual() != null) {
				GrupoDao grupoDao = new GrupoDao();
				GrupoDTO grupoDto = new GrupoDTO();
				grupoDto.setIdGrupo(requisicaoDto.getIdGrupoAtual());
				grupoDto = (GrupoDTO) grupoDao.restore(grupoDto);
				if (grupoDto != null)
					requisicaoDto.setNomeGrupoAtual(grupoDto.getSigla());
			}

			if (requisicaoDto.getIdGrupoNivel1() != null) {
				GrupoDao grupoDao = new GrupoDao();
				GrupoDTO grupoDto = new GrupoDTO();
				grupoDto.setIdGrupo(requisicaoDto.getIdGrupoNivel1());
				grupoDto = (GrupoDTO) grupoDao.restore(grupoDto);
				if (grupoDto != null)
					requisicaoDto.setNomeGrupoNivel1(grupoDto.getSigla());
			}
			
			if(requisicaoDto.getIdContatoRequisicaoMudanca()!=null){
				ContatoRequisicaoMudancaDTO contatoRequisicaoMudancaDto = new ContatoRequisicaoMudancaDTO();
				ContatoRequisicaoMudancaDao contatoRequisicaoMudancaDao = new ContatoRequisicaoMudancaDao();
				//contatoRequisicaoMudancaDao.setTransactionControler(tc);
				contatoRequisicaoMudancaDto.setIdContatoRequisicaoMudanca(requisicaoDto.getIdContatoRequisicaoMudanca());
				contatoRequisicaoMudancaDto = (ContatoRequisicaoMudancaDTO) contatoRequisicaoMudancaDao.restore(contatoRequisicaoMudancaDto);
				if(contatoRequisicaoMudancaDto!=null){
					requisicaoDto.setNomeContato(contatoRequisicaoMudancaDto.getNomecontato());
			}
			}
			if(requisicaoDto.getIdTipoMudanca()!=null){
				TipoMudancaDTO tipoMudancaDto = new TipoMudancaDTO();
				TipoMudancaDAO tipoMudancaDao = new TipoMudancaDAO();
				
				tipoMudancaDto.setIdTipoMudanca(requisicaoDto.getIdTipoMudanca());
				tipoMudancaDto =  (TipoMudancaDTO) tipoMudancaDao.restore(tipoMudancaDto);
				if(tipoMudancaDto!=null){
					requisicaoDto.setTipo(tipoMudancaDto.getNomeTipoMudanca());
				}
			}
				
			ReuniaoRequisicaoMudancaDAO reuniaoDao = new ReuniaoRequisicaoMudancaDAO();
			ReuniaoRequisicaoMudancaDTO reuniaoDto = new ReuniaoRequisicaoMudancaDTO();
			reuniaoDto.setIdReuniaoRequisicaoMudanca(idReuniaoRequisicaoMudanca);
			reuniaoDto = (ReuniaoRequisicaoMudancaDTO) reuniaoDao.restore(reuniaoDto);
			requisicaoDto.setLocalReuniao(reuniaoDto.getLocalReuniao());
			requisicaoDto.setDataInicio(reuniaoDto.getDataInicio());
			requisicaoDto.setHoraInicio(reuniaoDto.getHoraInicio());
			requisicaoDto.setDuracaoEstimada(reuniaoDto.getDuracaoEstimada());
			requisicaoDto.setDescricao(reuniaoDto.getDescricao());

		}
		return verificaAtraso(requisicaoDto);
	}

	public RequisicaoMudancaDTO verificaAtraso(RequisicaoMudancaDTO requisicaoDto) throws Exception {
		if (requisicaoDto == null)
			return null;

		long atrasoSLA = 0;

		if (requisicaoDto.getDataHoraTermino() != null) {
			Timestamp dataHoraLimite = requisicaoDto.getDataHoraTermino();
			Timestamp dataHoraComparacao = UtilDatas.getDataHoraAtual();
			if (requisicaoDto.encerrada())
				if (requisicaoDto.getDataHoraConclusao() != null) {
					dataHoraComparacao = requisicaoDto.getDataHoraConclusao();
				}
			if (dataHoraComparacao.compareTo(dataHoraLimite) > 0) {
				atrasoSLA = UtilDatas.calculaDiferencaTempoEmMilisegundos(dataHoraComparacao, dataHoraLimite) / 1000;
				requisicaoDto.setPrazoHH(0);
				requisicaoDto.setPrazoMM(0);
			}
		}

		requisicaoDto.setAtraso(atrasoSLA);
		return requisicaoDto;
	}

	public Collection findBySolictacaoServico(RequisicaoMudancaDTO bean) throws ServiceException, LogicException {

		try {
			return getRequisicaoMudancaDao().listProblemaByIdSolicitacao(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<RequisicaoMudancaDTO> obterMudancaCriticos(Integer idItemConfiguracao) {
		try {
			RequisicaoMudancaDao requisicaoMudancaDao = new RequisicaoMudancaDao();
			return requisicaoMudancaDao.listMudancaByIdItemConfiguracao(idItemConfiguracao);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<RequisicaoMudancaDTO> listMudancaByIdItemConfiguracao(Integer idItemConfiguracao) throws Exception {
		RequisicaoMudancaDao requisicaoMudancaDao = new RequisicaoMudancaDao();

		return requisicaoMudancaDao.listMudancaByIdItemConfiguracao(idItemConfiguracao);
	}

	public Collection<RequisicaoMudancaDTO> listaMudancaPorBaseConhecimento(RequisicaoMudancaDTO mudanca) throws Exception {

		Collection<RequisicaoMudancaDTO> listaMudancaPorBaseConhecimento = null;
		RequisicaoMudancaDao mudancaDao = new RequisicaoMudancaDao();
		try {
			listaMudancaPorBaseConhecimento = mudancaDao.listaMudancasPorBaseConhecimento(mudanca);
			if (listaMudancaPorBaseConhecimento != null) {
				for (RequisicaoMudancaDTO mudancaDTO : listaMudancaPorBaseConhecimento) {

					Source source = new Source(mudancaDTO.getDescricao());
					mudancaDTO.setDescricao(source.getTextExtractor().toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaMudancaPorBaseConhecimento;

	}

	@Override
	public Collection<RequisicaoMudancaDTO> quantidadeMudancaPorBaseConhecimento(RequisicaoMudancaDTO mudanca) throws Exception {
		RequisicaoMudancaDao mudancaDao = new RequisicaoMudancaDao();
		return mudancaDao.quantidadeMudancaPorBaseConhecimento(mudanca);
	}

	@Override
	public Collection findByConhecimento(BaseConhecimentoDTO baseConhecimentoDto) throws ServiceException, LogicException {
		RequisicaoMudancaDao requisicaoMudancaDao = new RequisicaoMudancaDao();

		try {
			return requisicaoMudancaDao.findByConhecimento(baseConhecimentoDto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public ServicoContratoDTO findByIdContratoAndIdServico(Integer idContrato,
			Integer idServico) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	//Metodos Adcionados para a nova Gerencia de mudança.
	/*
	public Collection<RequisicaoMudancaDTO> listRequisicaoMudancaByCriterios(RequisicaoMudancaDTO requisicaoMudancaDto) throws Exception {

		Collection<RequisicaoMudancaDTO> listRequisicaoMudancaByCriterios = getRequisicaoMudancaDao().listRequisicaoMudancaByCriterios(requisicaoMudancaDto);

		if (listRequisicaoMudancaByCriterios != null) {
			for (RequisicaoMudancaDTO requisicaoMudanca : listRequisicaoMudancaByCriterios) {

				Source source = new Source(requisicaoMudanca.getDescricao());

				requisicaoMudanca.setDescricao(source.getTextExtractor().toString());
			}
		}

		return listRequisicaoMudancaByCriterios;
	}
	*/
	public Collection<RequisicaoMudancaDTO> listRequisicaoMudancaByCriterios(RequisicaoMudancaDTO requisicaoMudancaDto) throws Exception {
		return null;
	}
	
	public Collection<PesquisaRequisicaoMudancaDTO> listRequisicaoMudancaByCriterios(PesquisaRequisicaoMudancaDTO pesquisaRequisicaoMudancaDto) throws Exception {
		
		Collection<PesquisaRequisicaoMudancaDTO> listRequisicaoMudancaByCriterios = getRequisicaoMudancaDao().listRequisicaoMudancaByCriterios(pesquisaRequisicaoMudancaDto);
		
		if (listRequisicaoMudancaByCriterios != null) {
			for (PesquisaRequisicaoMudancaDTO requisicaoMudanca : listRequisicaoMudancaByCriterios) {
				
				Source source = new Source(requisicaoMudanca.getDescricao());
				
				requisicaoMudanca.setDescricao(source.getTextExtractor().toString());
			}
		}
		
		return listRequisicaoMudancaByCriterios;
	}
	
	@Override
	public boolean verificarSeRequisicaoMudancaPossuiTipoMudanca(
			Integer idTipoMudanca) throws Exception {
		RequisicaoMudancaDao requisicaoMudancaDao = new RequisicaoMudancaDao();
		return requisicaoMudancaDao.verificarSeRequisicaoMudancaPossuiTipoMudanca(idTipoMudanca);
	}
	
	/**
	 * Verifica se o item existe na lista.
	 * 
	 * @param item
	 * @param lista
	 * @return
	 */
	private boolean requisicaoMudancaServicoExisteNaLista(RequisicaoMudancaServicoDTO item, List<RequisicaoMudancaServicoDTO> lista) {
		if (lista == null)
			return false;
		for (RequisicaoMudancaServicoDTO l : lista) {
			if (l.equals(item)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Verifica se o item existe na lista.
	 * 
	 * @param item
	 * @param lista
	 * @return
	 */
	private boolean requisicaoMudancaICExisteNaLista(RequisicaoMudancaItemConfiguracaoDTO item, List<RequisicaoMudancaItemConfiguracaoDTO> lista) {
		if (lista == null)
			return false;
		for (RequisicaoMudancaItemConfiguracaoDTO l : lista) {
			if (l.equals(item)) {
				return true;
			}
		}
		return false;
	}
	
	private String getStatusAtual(Integer id) throws ServiceException, Exception{
		RequisicaoMudancaDTO reqMudanca = new RequisicaoMudancaDTO();
		reqMudanca.setIdRequisicaoMudanca(id);
		reqMudanca = (RequisicaoMudancaDTO) getRequisicaoMudancaDao().restore(reqMudanca);
		String res = reqMudanca.getStatus();
		return res;
		
	}
	
	private RequisicaoMudancaDTO getRequisicaoAtual(Integer id) throws ServiceException, Exception{
		RequisicaoMudancaDTO reqMudanca = new RequisicaoMudancaDTO();
		reqMudanca.setIdRequisicaoMudanca(id);
		reqMudanca = (RequisicaoMudancaDTO) getRequisicaoMudancaDao().restore(reqMudanca);
		return reqMudanca;
		
	}

	@Override
	public void suspende(UsuarioDTO usuarioDto, RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
		TransactionControler tc = new TransactionControlerImpl(getDao().getAliasDB());
		//Reflexao.copyPropertyValues(usuarioDto, usuario);
		this.suspende(usuarioDto, requisicaoMudancaDTO, tc);
		tc.commit();
		tc.close();
		tc = null;
		
	}
	
	public void suspende(UsuarioDTO usuarioDto, RequisicaoMudancaDTO requisicaoMudancaDto, TransactionControler tc) throws Exception {
		RequisicaoMudancaDTO requisicaoMudancaAuxiliarDto = restoreAll(requisicaoMudancaDto.getIdRequisicaoMudanca(), tc);
		requisicaoMudancaAuxiliarDto.setIdJustificativa(requisicaoMudancaDto.getIdJustificativa());
		requisicaoMudancaAuxiliarDto.setComplementoJustificativa(requisicaoMudancaDto.getComplementoJustificativa());
		new ExecucaoMudancaServiceEjb().suspende(usuarioDto, requisicaoMudancaDto, tc);
	}

	@Override
	public void reativa(UsuarioDTO usuarioDto, RequisicaoMudancaDTO requisicaoMudancaDto) throws Exception {
		TransactionControler tc = new TransactionControlerImpl(getDao().getAliasDB());
		reativa(usuarioDto, requisicaoMudancaDto, tc);
		tc.commit();
		tc.close();
		tc = null;
		
	}
	
	public void reativa(UsuarioDTO usuarioDto, RequisicaoMudancaDTO requisicaoMudancaDto, TransactionControler tc) throws Exception {
		RequisicaoMudancaDTO requisicaoMudancaAuxDto = restoreAll(requisicaoMudancaDto.getIdRequisicaoMudanca(), tc);
		new ExecucaoMudancaServiceEjb().reativa(usuarioDto, requisicaoMudancaAuxDto, tc);
	}

	@Override
	public List<RequisicaoMudancaDTO> listMudancaByIdSolicitacao(RequisicaoMudancaDTO bean) throws Exception {
		return getRequisicaoMudancaDao().listMudancaByIdSolicitacao(bean);
	}
	
	public boolean validacaoAvancaFluxo(RequisicaoMudancaDTO requisicaoMudancaDto, TransactionControler tc) throws Exception{
		
		AprovacaoMudancaDTO aprovacaoMudancaDto = new AprovacaoMudancaDTO();
		AprovacaoMudancaDao dao = new AprovacaoMudancaDao();
		dao.setTransactionControler(tc);
		if (requisicaoMudancaDto.getIdRequisicaoMudanca() != null) {
			aprovacaoMudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
			aprovacaoMudancaDto.setVoto("A");
			aprovacaoMudancaDto.setQuantidadeVotoAprovada(dao.quantidadeAprovacaoMudancaPorVotoAprovada(aprovacaoMudancaDto,requisicaoMudancaDto.getIdGrupoComite()));
			aprovacaoMudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
			aprovacaoMudancaDto.setVoto("R");
			aprovacaoMudancaDto.setQuantidadeVotoRejeitada(dao.quantidadeAprovacaoMudancaPorVotoRejeitada(aprovacaoMudancaDto,requisicaoMudancaDto.getIdGrupoComite()));
			aprovacaoMudancaDto.setQuantidadeAprovacaoMudanca(dao.quantidadeAprovacaoMudanca(aprovacaoMudancaDto, requisicaoMudancaDto.getIdGrupoComite()));
			
		}
		if(aprovacaoMudancaDto.getQuantidadeVotoAprovada() > 0){
			
			if(aprovacaoMudancaDto.getQuantidadeAprovacaoMudanca().intValue() == aprovacaoMudancaDto.getQuantidadeVotoAprovada()){
				return true;
			}
			else{
				if(aprovacaoMudancaDto.getQuantidadeVotoAprovada() >= ((aprovacaoMudancaDto.getQuantidadeAprovacaoMudanca()/2) + 1)){
					return true;
				}
			}
		} else if(aprovacaoMudancaDto.getQuantidadeVotoRejeitada() > 0 && aprovacaoMudancaDto.getQuantidadeVotoRejeitada() > (aprovacaoMudancaDto.getQuantidadeAprovacaoMudanca()/2)){
			requisicaoMudancaDto.setStatus("Rejeitada");
			return true;
		}
		
		return false;
		
	}
	
	public boolean validacaoAvancaFluxoProposta(RequisicaoMudancaDTO requisicaoMudancaDto, TransactionControler tc) throws Exception{

		AprovacaoPropostaDTO aprovacaoPropostaDto = new AprovacaoPropostaDTO();
		AprovacaoPropostaDao dao = new AprovacaoPropostaDao();
		dao.setTransactionControler(tc);
		if (requisicaoMudancaDto.getIdRequisicaoMudanca() != null) {
			aprovacaoPropostaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
			aprovacaoPropostaDto.setVoto("A");
			aprovacaoPropostaDto.setQuantidadeVotoAprovada(dao.quantidadeAprovacaoPropostaPorVotoAprovada(aprovacaoPropostaDto,requisicaoMudancaDto.getIdGrupoComite()));
			aprovacaoPropostaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
			aprovacaoPropostaDto.setVoto("R");
			aprovacaoPropostaDto.setQuantidadeVotoRejeitada(dao.quantidadeAprovacaoPropostaPorVotoAprovada(aprovacaoPropostaDto,requisicaoMudancaDto.getIdGrupoComite()));
			aprovacaoPropostaDto.setQuantidadeAprovacaoProposta(dao.quantidadeAprovacaoProposta(aprovacaoPropostaDto, requisicaoMudancaDto.getIdGrupoComite()));

		}
		if(aprovacaoPropostaDto.getQuantidadeVotoAprovada() > 0){

			if(aprovacaoPropostaDto.getQuantidadeAprovacaoProposta().intValue() == aprovacaoPropostaDto.getQuantidadeVotoAprovada()){
				requisicaoMudancaDto.setVotacaoPropostaAprovadaAux("S");
				requisicaoMudancaDto.setFase("Registrada");
				return true;
			}
			else if(aprovacaoPropostaDto.getQuantidadeVotoAprovada() >= ((aprovacaoPropostaDto.getQuantidadeAprovacaoProposta()/2) + 1)){
				requisicaoMudancaDto.setVotacaoPropostaAprovadaAux("S");
				requisicaoMudancaDto.setFase("Registrada");
				return true;
			} 
			
		} else if(aprovacaoPropostaDto.getQuantidadeVotoRejeitada() > 0 && aprovacaoPropostaDto.getQuantidadeVotoRejeitada() >= (aprovacaoPropostaDto.getQuantidadeAprovacaoProposta())/2){
			requisicaoMudancaDto.setVotacaoPropostaAprovadaAux("N");
			requisicaoMudancaDto.setStatus("Rejeitada");
			return true;
		} 
//		} else if(aprovacaoPropostaDto.getQuantidadeVotoAprovada() <= ((aprovacaoPropostaDto.getQuantidadeVotoRejeitada())) && aprovacaoPropostaDto.getQuantidadeVotoRejeitada() > 0){
//			requisicaoMudancaDto.setVotacaoPropostaAprovadaAux("N");
//			return true;
//		}

		return false;

	}
	
	public String verificaAprovacaoProposta(RequisicaoMudancaDTO requisicaoMudancaDto, TransactionControler tc) throws Exception{
		
		String aprovado = "aprovado";
		String reprovado = "reprovado";
		String aquardando = "aquardando";
		AprovacaoPropostaDTO aprovacaoPropostaDto = new AprovacaoPropostaDTO();
		AprovacaoPropostaDao dao = new AprovacaoPropostaDao();
		dao.setTransactionControler(tc);
		if (requisicaoMudancaDto.getIdRequisicaoMudanca() != null) {
			aprovacaoPropostaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
			aprovacaoPropostaDto.setVoto("A");
			aprovacaoPropostaDto.setQuantidadeVotoAprovada(dao.quantidadeAprovacaoPropostaPorVotoAprovada(aprovacaoPropostaDto,requisicaoMudancaDto.getIdGrupoComite()));
			aprovacaoPropostaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
			aprovacaoPropostaDto.setVoto("R");
			aprovacaoPropostaDto.setQuantidadeVotoRejeitada(dao.quantidadeAprovacaoPropostaPorVotoAprovada(aprovacaoPropostaDto,requisicaoMudancaDto.getIdGrupoComite()));
			aprovacaoPropostaDto.setQuantidadeAprovacaoProposta(dao.quantidadeAprovacaoProposta(aprovacaoPropostaDto, requisicaoMudancaDto.getIdGrupoComite()));
			
		}
			
		if(aprovacaoPropostaDto.getQuantidadeVotoAprovada() > 0){
			if(aprovacaoPropostaDto.getQuantidadeAprovacaoProposta().intValue() == aprovacaoPropostaDto.getQuantidadeVotoAprovada()){
				return aprovado;
			}
			
			if(aprovacaoPropostaDto.getQuantidadeVotoAprovada() >= ((aprovacaoPropostaDto.getQuantidadeAprovacaoProposta()/2) + 1)){
				return aprovado;
			}
			
			if(aprovacaoPropostaDto.getQuantidadeVotoRejeitada() > ((aprovacaoPropostaDto.getQuantidadeAprovacaoProposta()/2))){
				return reprovado;
			}
		}
		return aquardando;
		
	}
	
public String verificaAprovacaoMudanca(RequisicaoMudancaDTO requisicaoMudancaDto, TransactionControler tc) throws Exception{
		
		String aprovado = "aprovado";
		String reprovado = "reprovado";
		String aquardando = "aquardando";
		AprovacaoMudancaDTO aprovacaoMudancaDto = new AprovacaoMudancaDTO();
		AprovacaoMudancaDao dao = new AprovacaoMudancaDao();
		dao.setTransactionControler(tc);
		if (requisicaoMudancaDto.getIdRequisicaoMudanca() != null) {
			aprovacaoMudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
			aprovacaoMudancaDto.setVoto("A");
			aprovacaoMudancaDto.setQuantidadeVotoAprovada(dao.quantidadeAprovacaoMudancaPorVotoAprovada(aprovacaoMudancaDto,requisicaoMudancaDto.getIdGrupoComite()));
			aprovacaoMudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
			aprovacaoMudancaDto.setVoto("R");
			aprovacaoMudancaDto.setQuantidadeVotoRejeitada(dao.quantidadeAprovacaoMudancaPorVotoAprovada(aprovacaoMudancaDto,requisicaoMudancaDto.getIdGrupoComite()));
			aprovacaoMudancaDto.setQuantidadeAprovacaoMudanca(dao.quantidadeAprovacaoMudanca(aprovacaoMudancaDto, requisicaoMudancaDto.getIdGrupoComite()));
			
		}
			
		if(aprovacaoMudancaDto.getQuantidadeVotoAprovada() > 0){
			if(aprovacaoMudancaDto.getQuantidadeAprovacaoMudanca().intValue() == aprovacaoMudancaDto.getQuantidadeVotoAprovada()){
				return aprovado;
			}
			
			if(aprovacaoMudancaDto.getQuantidadeVotoAprovada() >= ((aprovacaoMudancaDto.getQuantidadeAprovacaoMudanca()/2) + 1)){
				return aprovado;
			}
			
			if(aprovacaoMudancaDto.getQuantidadeVotoRejeitada() > ((aprovacaoMudancaDto.getQuantidadeAprovacaoMudanca()/2))){
				return reprovado;
				
			}
		}
		return aquardando;
		
	}
	
	/*public void gravaPlanoDeReversaoGED(Collection colArquivosUploadPlanoReversao, RequisicaoMudancaDTO requisicaoMudancaDto, TransactionControler tc) throws Exception {
		// Setando a transaction no GED
		ControleGEDDao controleGEDDao = new ControleGEDDao();
		if (tc != null) {
			controleGEDDao.setTransactionControler(tc);
		}

		String PRONTUARIO_GED_DIRETORIO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedDiretorio,"");
		if (PRONTUARIO_GED_DIRETORIO == null || PRONTUARIO_GED_DIRETORIO.trim().equalsIgnoreCase("")) {
			PRONTUARIO_GED_DIRETORIO = "";
		}

		if (PRONTUARIO_GED_DIRETORIO.equalsIgnoreCase("")) {
			PRONTUARIO_GED_DIRETORIO = Constantes.getValue("DIRETORIO_GED");
		}

		if (PRONTUARIO_GED_DIRETORIO == null || PRONTUARIO_GED_DIRETORIO.equalsIgnoreCase("")) {
			PRONTUARIO_GED_DIRETORIO = "/ged";
		}
		String PRONTUARIO_GED_INTERNO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedInterno, "S");
		if (PRONTUARIO_GED_INTERNO == null) {
			PRONTUARIO_GED_INTERNO = "S";
		}
		String prontuarioGedInternoBancoDados = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedInternoBD, "N");
		if (!UtilStrings.isNotVazio(prontuarioGedInternoBancoDados))
			prontuarioGedInternoBancoDados = "N";
		String pasta = "";
		if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")) {
			pasta = controleGEDDao.getProximaPastaArmazenar();
			File fileDir = new File(PRONTUARIO_GED_DIRETORIO);
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
			fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/" + requisicaoMudancaDto.getIdEmpresa());
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
			fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/" + requisicaoMudancaDto.getIdEmpresa() + "/" + pasta);
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
		}

		for (Iterator it = colArquivosUploadPlanoReversao.iterator(); it.hasNext();) {
			UploadDTO uploadDTO = (UploadDTO) it.next();
			ControleGEDDTO controleGEDDTO = new ControleGEDDTO();
			controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_PLANO_REVERSAO_MUDANCA);
			controleGEDDTO.setId(requisicaoMudancaDto.getIdRequisicaoMudanca());
			controleGEDDTO.setDataHora(UtilDatas.getDataAtual());
			controleGEDDTO.setDescricaoArquivo(uploadDTO.getDescricao());
			controleGEDDTO.setExtensaoArquivo(Util.getFileExtension(uploadDTO.getNameFile()));
			controleGEDDTO.setPasta(pasta);
			controleGEDDTO.setVersao(uploadDTO.getVersao());
			controleGEDDTO.setNomeArquivo(uploadDTO.getNameFile());

			if (!uploadDTO.getTemporario().equalsIgnoreCase("S")) { // Se nao //
																	// for //
																	// temporario
				continue;
			}

			if (PRONTUARIO_GED_INTERNO.trim().equalsIgnoreCase("S") && "S".equalsIgnoreCase(prontuarioGedInternoBancoDados.trim())) { // Se
				// utiliza
				// GED
				// interno e eh BD
				controleGEDDTO.setPathArquivo(uploadDTO.getPath()); // Isso vai
																	// fazer a
																	// gravacao
																	// no BD.
																	// dento do
																	// create
																	// abaixo.
			} else {
				controleGEDDTO.setPathArquivo(null);
			}
			controleGEDDTO = (ControleGEDDTO) controleGEDDao.create(controleGEDDTO);
			uploadDTO.setIdControleGED(controleGEDDTO.getIdControleGED());
			if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && !"S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) { // Se
																															// utiliza
																															// GED
				// interno e nao eh BD
				if (controleGEDDTO != null) {
					try {
						File arquivo = new File(PRONTUARIO_GED_DIRETORIO + "/" + requisicaoMudancaDto.getIdEmpresa() + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + "." + Util.getFileExtension(uploadDTO.getNameFile()));
						CriptoUtils.encryptFile(uploadDTO.getPath(), PRONTUARIO_GED_DIRETORIO + "/" + requisicaoMudancaDto.getIdEmpresa() + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged", System.getProperties().get("user.dir")
								+ Constantes.getValue("CAMINHO_CHAVE_PUBLICA"));
						arquivo.delete();
					} catch (Exception e) {

					}

				}
			}
		}
			/* 
			  else if (!PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")) { // Se // utiliza // GED // externo // FALTA IMPLEMENTAR!!! }
			 
		}
		Collection<ControleGEDDTO> colAnexo = controleGEDDao.listByIdTabelaAndIdBaseConhecimento(ControleGEDDTO.TABELA_REQUISICAOMUDANCA, requisicaoMudancaDto.getIdRequisicaoMudanca());
		if (colAnexo != null) {
			for (ControleGEDDTO dtoGed : colAnexo) {
				boolean b = false;
				for (Iterator it = colArquivosUpload.iterator(); it.hasNext();) {
					UploadDTO uploadDTO = (UploadDTO) it.next();
					if (uploadDTO.getIdControleGED() == null) {
						b = true;
						break;
					}
					if (uploadDTO.getIdControleGED().intValue() == dtoGed.getIdControleGED().intValue()) {
						b = true;
					}
					if (b) {
						break;
					}
				}
				if (!b) {
					controleGEDDao.delete(dtoGed);
				}
			}
		}
	}
}
*/

public void gravaInformacoesGED(RequisicaoMudancaDTO requisicaomudacaDTO, TransactionControler tc, HistoricoMudancaDTO historicoMudancaDTO) throws Exception {
	Collection<UploadDTO> colArquivosUpload = requisicaomudacaDTO.getColArquivosUpload();
	HistoricoGEDDTO historicoGEDDTO = new HistoricoGEDDTO();
	HistoricoGEDDao historicoGEDDao = new HistoricoGEDDao();
	
	// Setando a transaction no GED
	ControleGEDDao controleGEDDao = new ControleGEDDao();
	if (tc != null) {
		controleGEDDao.setTransactionControler(tc);
		historicoGEDDao.setTransactionControler(tc);
	}
	

	String PRONTUARIO_GED_DIRETORIO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedDiretorio,"");
	if (PRONTUARIO_GED_DIRETORIO == null || PRONTUARIO_GED_DIRETORIO.trim().equalsIgnoreCase("")) {
		PRONTUARIO_GED_DIRETORIO = "";
	}

	if (PRONTUARIO_GED_DIRETORIO.equalsIgnoreCase("")) {
		PRONTUARIO_GED_DIRETORIO = Constantes.getValue("DIRETORIO_GED");
	}

	if (PRONTUARIO_GED_DIRETORIO == null || PRONTUARIO_GED_DIRETORIO.equalsIgnoreCase("")) {
		PRONTUARIO_GED_DIRETORIO = "/ged";
	}
	String PRONTUARIO_GED_INTERNO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedInterno, "S");
	if (PRONTUARIO_GED_INTERNO == null) {
		PRONTUARIO_GED_INTERNO = "S";
	}
	String prontuarioGedInternoBancoDados = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedInternoBD, "N");
	if (!UtilStrings.isNotVazio(prontuarioGedInternoBancoDados))
		prontuarioGedInternoBancoDados = "N";
	String pasta = "";
	if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")) {
		pasta = controleGEDDao.getProximaPastaArmazenar();
		File fileDir = new File(PRONTUARIO_GED_DIRETORIO);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/" + requisicaomudacaDTO.getIdEmpresa());
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/" + requisicaomudacaDTO.getIdEmpresa() + "/" + pasta);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
	}
	/**
	 * Grava informações do upload principal.
	 * **/
	
	if(colArquivosUpload != null){
		for (UploadDTO upDto : colArquivosUpload) {
			UploadDTO uploadDTO = (UploadDTO) upDto;
			ControleGEDDTO controleGEDDTO = new ControleGEDDTO();
			
			Integer idControleGed = uploadDTO.getIdControleGED();
			
			historicoGEDDTO.setIdRequisicaoMudanca(requisicaomudacaDTO.getIdRequisicaoMudanca());
			historicoGEDDTO.setIdTabela(ControleGEDDTO.TABELA_REQUISICAOMUDANCA);
			if(historicoMudancaDTO.getIdHistoricoMudanca() != null){
			historicoGEDDTO.setIdHistoricoMudanca(null);
			}else{
				historicoGEDDTO.setIdHistoricoMudanca(-1);
			}
			historicoGEDDao.create(historicoGEDDTO);
			
			controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_REQUISICAOMUDANCA);
			controleGEDDTO.setId(requisicaomudacaDTO.getIdRequisicaoMudanca());
			controleGEDDTO.setDataHora(UtilDatas.getDataAtual());
			controleGEDDTO.setDescricaoArquivo(uploadDTO.getDescricao());
			controleGEDDTO.setExtensaoArquivo(Util.getFileExtension(uploadDTO.getNameFile()));
			controleGEDDTO.setPasta(pasta);
			controleGEDDTO.setNomeArquivo(uploadDTO.getNameFile());
			upDto.setTemporario("S");
			uploadDTO.setTemporario("S");
			if(upDto.getTemporario() != null){
				if (!uploadDTO.getTemporario().equalsIgnoreCase("S")) { // Se nao //
					continue;
				}
			}else{
				continue;
			}
		
			     if (PRONTUARIO_GED_INTERNO != null && PRONTUARIO_GED_INTERNO.trim().equalsIgnoreCase("S") && "S".equalsIgnoreCase(prontuarioGedInternoBancoDados.trim())) { // Se
				// utiliza
				// GED
				// interno e eh BD
				controleGEDDTO.setPathArquivo(uploadDTO.getPath()); // Isso vai
																	// fazer a
																	// gravacao
																	// no BD.
																	// dento do
																	// create
																	// abaixo.
			} else {
				controleGEDDTO.setPathArquivo(null);
			}
			//esse bloco grava a tabela de historicos de anexos:
				
			boolean existe = false;	
			if(idControleGed != null){
				Collection<HistoricoGEDDTO> colAux = historicoGEDDao.listByIdTabelaAndIdLiberacaoEDataFim(ControleGEDDTO.TABELA_REQUISICAOMUDANCA, requisicaomudacaDTO.getIdRequisicaoMudanca());
				if(colAux != null && colAux.size() > 0){
					for (HistoricoGEDDTO historicoGedDTOAux : colAux) {
						if(idControleGed.intValue() == historicoGedDTOAux.getIdControleGed().intValue()){
							idControleGed = historicoGedDTOAux.getIdControleGed();
							existe = true;
							break;
						}
					}
				}
			}

			if(!existe){
				controleGEDDTO = (ControleGEDDTO) controleGEDDao.create(controleGEDDTO);
				controleGEDDTO.setId(historicoGEDDTO.getIdLigacaoHistoricoGed());
				idControleGed = controleGEDDTO.getIdControleGED();
			}
			historicoGEDDTO.setIdControleGed(idControleGed);
			historicoGEDDao.update(historicoGEDDTO);
			
			//uploadDTO.setIdControleGED(controleGEDDTO.getIdControleGED());
			if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && !"S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) { // Se
																															// utiliza
																															// GED
				// interno e nao eh BD
				if (controleGEDDTO != null) {
					try {
						File arquivo = new File(PRONTUARIO_GED_DIRETORIO + "/" + requisicaomudacaDTO.getIdEmpresa() + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + "." + Util.getFileExtension(uploadDTO.getNameFile()));
						CriptoUtils.encryptFile(uploadDTO.getPath(), PRONTUARIO_GED_DIRETORIO + "/" + requisicaomudacaDTO.getIdEmpresa() + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged", System.getProperties().get("user.dir")
								+ Constantes.getValue("CAMINHO_CHAVE_PUBLICA"));
						arquivo.delete();
					} catch (Exception e) {

					}

				}
			} /*
			 * else if (!PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")) { // Se // utiliza // GED // externo // FALTA IMPLEMENTAR!!! }
			 */
		}
	}
	
/*	Collection<ControleGEDDTO> colAnexo = controleGEDDao.listByIdTabelaAndIdBaseConhecimento(ControleGEDDTO.TABELA_REQUISICAOLIBERACAO, requisicaoLiberacaoDTO.getIdRequisicaoLiberacao());
	if (colAnexo != null) {
		for (ControleGEDDTO dtoGed : colAnexo) {
			boolean b = false;
			for (Iterator it = colArquivosUpload.iterator(); it.hasNext();) {
				UploadDTO uploadDTO = (UploadDTO) it.next();
				if (uploadDTO.getIdControleGED() == null) {
					b = true;
					break;
				}
				if (uploadDTO.getIdControleGED().intValue() == dtoGed.getIdControleGED().intValue()) {
					b = true;
				}
				if (b) {
					break;
				}
			}
			if (!b) {
				controleGEDDao.delete(dtoGed);
			}
		}
	}*/

	
}

	public void gravaPlanoDeReversaoGED(RequisicaoMudancaDTO requisicaomudacaDTO, TransactionControler tc, HistoricoMudancaDTO historicoMudancaDTO) throws Exception {
		Collection<UploadDTO> colArquivosUpload = requisicaomudacaDTO.getColUploadPlanoDeReversaoGED();

		// Setando a transaction no GED
		ControleGEDDao controleGEDDao = new ControleGEDDao();
		if (tc != null) {
			controleGEDDao.setTransactionControler(tc);
		}

		String PRONTUARIO_GED_DIRETORIO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedDiretorio, "");
		if (PRONTUARIO_GED_DIRETORIO == null || PRONTUARIO_GED_DIRETORIO.trim().equalsIgnoreCase("")) {
			PRONTUARIO_GED_DIRETORIO = "";
		}

		if (PRONTUARIO_GED_DIRETORIO.equalsIgnoreCase("")) {
			PRONTUARIO_GED_DIRETORIO = Constantes.getValue("DIRETORIO_GED");
		}

		if (PRONTUARIO_GED_DIRETORIO == null || PRONTUARIO_GED_DIRETORIO.equalsIgnoreCase("")) {
			PRONTUARIO_GED_DIRETORIO = "/ged";
		}
		String PRONTUARIO_GED_INTERNO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedInterno, "S");
		if (PRONTUARIO_GED_INTERNO == null) {
			PRONTUARIO_GED_INTERNO = "S";
		}
		String prontuarioGedInternoBancoDados = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedInternoBD, "N");
		if (!UtilStrings.isNotVazio(prontuarioGedInternoBancoDados))
			prontuarioGedInternoBancoDados = "N";
		String pasta = "";
		if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")) {
			pasta = controleGEDDao.getProximaPastaArmazenar();
			File fileDir = new File(PRONTUARIO_GED_DIRETORIO);
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
			fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/" + requisicaomudacaDTO.getIdEmpresa());
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
			fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/" + requisicaomudacaDTO.getIdEmpresa() + "/" + pasta);
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
		}
		/**
		 * Grava informações do upload principal.
		 * **/

		if (colArquivosUpload != null) {
			for (UploadDTO upDto : colArquivosUpload) {
				UploadDTO uploadDTO = (UploadDTO) upDto;
				ControleGEDDTO controleGEDDTO = new ControleGEDDTO();

				Integer idControleGed = uploadDTO.getIdControleGED();
/*
				historicoGEDDTO.setIdRequisicaoMudanca(requisicaomudacaDTO.getIdRequisicaoMudanca());
				historicoGEDDTO.setIdTabela(ControleGEDDTO.TABELA_PLANO_REVERSAO_MUDANCA);
				if (historicoMudancaDTO.getIdHistoricoMudanca() != null) {
					historicoGEDDTO.setIdHistoricoMudanca(null);
				} else {
					historicoGEDDTO.setIdHistoricoMudanca(-1);
				}
				historicoGEDDao.create(historicoGEDDTO);*/

				controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_PLANO_REVERSAO_MUDANCA);
				controleGEDDTO.setId(historicoMudancaDTO.getIdRequisicaoMudanca());
				controleGEDDTO.setDataHora(UtilDatas.getDataAtual());
				controleGEDDTO.setDescricaoArquivo(uploadDTO.getDescricao());
				controleGEDDTO.setExtensaoArquivo(Util.getFileExtension(uploadDTO.getNameFile()));
				controleGEDDTO.setPasta(pasta);
				controleGEDDTO.setVersao(uploadDTO.getVersao());
				controleGEDDTO.setNomeArquivo(uploadDTO.getNameFile());
				upDto.setTemporario("S");
				uploadDTO.setTemporario("S");
				if (upDto.getTemporario() != null) {
					if (!uploadDTO.getTemporario().equalsIgnoreCase("S")) { // Se
																			// nao
																			// //
						continue;
					}
				} else {
					continue;
				}

				if (PRONTUARIO_GED_INTERNO.trim().equalsIgnoreCase("S") && "S".equalsIgnoreCase(prontuarioGedInternoBancoDados.trim())) { // Se
					// utiliza
					// GED
					// interno e eh BD
					controleGEDDTO.setPathArquivo(uploadDTO.getPath()); // Isso
																		// vai
					// fazer a
					// gravacao
					// no BD.
					// dento do
					// create
					// abaixo.
				} else {
					controleGEDDTO.setPathArquivo(null);
				}
				// esse bloco grava a tabela de historicos de anexos:

				boolean existe = false;
				if (idControleGed != null) {
					Collection<ControleGEDDTO> colAux = controleGEDDao.listByIdTabelaAndID(ControleGEDDTO.TABELA_PLANO_REVERSAO_MUDANCA, requisicaomudacaDTO.getIdRequisicaoMudanca());
					if (colAux != null && colAux.size() > 0) {
						for (ControleGEDDTO controleGedDTOAux : colAux) {
							if (idControleGed.intValue() == controleGedDTOAux.getIdControleGED().intValue()) {
								idControleGed = controleGedDTOAux.getIdControleGED();
								existe = true;
								break;
							}
						}
					}
				}

				if (!existe) {
					controleGEDDTO.setId(requisicaomudacaDTO.getIdRequisicaoMudanca());
					controleGEDDTO = (ControleGEDDTO) controleGEDDao.create(controleGEDDTO);
					idControleGed = controleGEDDTO.getIdControleGED();
				}
				// uploadDTO.setIdControleGED(controleGEDDTO.getIdControleGED());
				if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && !"S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) { // Se
					// utiliza
					// GED
					// interno e nao eh BD
					if (controleGEDDTO != null) {
						try {
							File arquivo = new File(PRONTUARIO_GED_DIRETORIO + "/" + requisicaomudacaDTO.getIdEmpresa() + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + "." + Util.getFileExtension(uploadDTO.getNameFile()));
							CriptoUtils.encryptFile(uploadDTO.getPath(), PRONTUARIO_GED_DIRETORIO + "/" + requisicaomudacaDTO.getIdEmpresa() + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged", System.getProperties().get("user.dir") + Constantes.getValue("CAMINHO_CHAVE_PUBLICA"));
							arquivo.delete();
						} catch (Exception e) {

						}

					}
				} /*
				 * else if (!PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")) { //
				 * Se // utiliza // GED // externo // FALTA IMPLEMENTAR!!! }
				 */
			}
		}

		
		Collection<ControleGEDDTO> colAnexo =
				controleGEDDao.listByIdTabelaAndIdBaseConhecimento
				(ControleGEDDTO.TABELA_PLANO_REVERSAO_MUDANCA, requisicaomudacaDTO.getIdRequisicaoMudanca()); 
		if (colAnexo != null) { 
			for (ControleGEDDTO dtoGed : colAnexo) { 
				boolean b = false;
				for (Iterator it = colArquivosUpload.iterator(); it.hasNext();) {
					UploadDTO uploadDTO = (UploadDTO) it.next(); 
					if(uploadDTO.getIdControleGED() == null) { 
						b = true; break; 
					} 
					if(uploadDTO.getIdControleGED().intValue() == dtoGed.getIdControleGED().intValue()) { 
						b = true; 
					} 
					if (b) {
						break;
					}
				} 
				if (!b) { 
					controleGEDDao.delete(dtoGed); 
				} 
			}
		}
		 

	}

	@Override
	public Collection listaQuantidadeMudancaPorImpacto(RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
		RequisicaoMudancaDao requisicaoMudancaDao = new RequisicaoMudancaDao();
		return requisicaoMudancaDao.listaQuantidadeMudancaPorImpacto(requisicaoMudancaDTO);
	}

	@Override
	public Collection listaQuantidadeMudancaPorPeriodo(RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
		RequisicaoMudancaDao requisicaoMudancaDao = new RequisicaoMudancaDao();
		return requisicaoMudancaDao.listaQuantidadeMudancaPorPeriodo(requisicaoMudancaDTO);
	}

	public String negritoHtml(String string) {
		return "<style isBold=\"true\" pdfFontName=\"Helvetica-Bold\">" + string + "</style>";
	}

	@Override
	public Collection listaIdETituloMudancasPorPeriodo(RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
		RequisicaoMudancaDao requisicaoMudancaDao = new RequisicaoMudancaDao();
		return requisicaoMudancaDao.listaIdETituloMudancasPorPeriodo(requisicaoMudancaDTO);
	}

	@Override
	public Collection listaQuantidadeMudancaPorProprietario(RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
		RequisicaoMudancaDao requisicaoMudancaDao = new RequisicaoMudancaDao();
		return requisicaoMudancaDao.listaQuantidadeMudancaPorProprietario(requisicaoMudancaDTO);
	}

	@Override
	public Collection listaQuantidadeMudancaPorSolicitante(RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
		RequisicaoMudancaDao requisicaoMudancaDao = new RequisicaoMudancaDao();
		return requisicaoMudancaDao.listaQuantidadeMudancaPorSolicitante(requisicaoMudancaDTO);
	}

	@Override
	public Collection listaQuantidadeMudancaPorStatus(RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
		RequisicaoMudancaDao requisicaoMudancaDao = new RequisicaoMudancaDao();
		return requisicaoMudancaDao.listaQuantidadeMudancaPorStatus(requisicaoMudancaDTO);
	}

	@Override
	public Collection listaQuantidadeMudancaPorUrgencia(RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
		RequisicaoMudancaDao requisicaoMudancaDao = new RequisicaoMudancaDao();
		return requisicaoMudancaDao.listaQuantidadeMudancaPorUrgencia(requisicaoMudancaDTO);
	}

	@Override
	public Collection listaQuantidadeSemAprovacaoPorPeriodo(RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
		RequisicaoMudancaDao requisicaoMudancaDao = new RequisicaoMudancaDao();
		return requisicaoMudancaDao.listaQuantidadeSemAprovacaoPorPeriodo(requisicaoMudancaDTO);
	}

	public Collection listaQuantidadeERelacionamentos(HttpServletRequest request, RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
		RequisicaoMudancaDao requisicaoMudancaDao = new RequisicaoMudancaDao();

		List<RequisicaoMudancaDTO> listaIdETituloMudancasPorPeriodo = new ArrayList<RequisicaoMudancaDTO>(requisicaoMudancaDao.listaIdETituloMudancasPorPeriodo(requisicaoMudancaDTO));
		Collection<RequisicaoMudancaDTO> listaMudancaIncidente = requisicaoMudancaDao.listaMudancaIncidente(requisicaoMudancaDTO);
		Collection<RequisicaoMudancaDTO> listaMudancaServico = requisicaoMudancaDao.listaMudancaServico(requisicaoMudancaDTO);
		Collection<RequisicaoMudancaDTO> listaMudancaProblema = requisicaoMudancaDao.listaMudancaProblema(requisicaoMudancaDTO);
		Collection<RequisicaoMudancaDTO> listaMudancaGrupo = requisicaoMudancaDao.listaMudancaGrupo(requisicaoMudancaDTO);
		Collection<RequisicaoMudancaDTO> listaMudancaConhecimento = requisicaoMudancaDao.listaMudancaConhecimento(requisicaoMudancaDTO);
		Collection<RequisicaoMudancaDTO> listaMudancaItemConfiguracao = requisicaoMudancaDao.listaMudancaItemConfiguracao(requisicaoMudancaDTO);

		for (int i = 0, j; i < listaIdETituloMudancasPorPeriodo.size(); i++) {
			int tamanhoInicio = listaIdETituloMudancasPorPeriodo.size();
			j = i;
			for (RequisicaoMudancaDTO incidente : listaMudancaIncidente) {
				RequisicaoMudancaDTO aux = listaIdETituloMudancasPorPeriodo.get(j);
				if (aux.getIdRequisicaoMudanca() == incidente.getIdRequisicaoMudanca()) {
					if (aux.getIncidente() == null || aux.getIncidente().isEmpty()) {
						listaIdETituloMudancasPorPeriodo.get(j).setIncidente(incidente.getIncidente());
					} else if (j + 1 == listaIdETituloMudancasPorPeriodo.size() || listaIdETituloMudancasPorPeriodo.get(j + 1).getIdRequisicaoMudanca() != aux.getIdRequisicaoMudanca()) {
						listaIdETituloMudancasPorPeriodo.add(++j, incidente);
					} else {
						listaIdETituloMudancasPorPeriodo.get(++j).setIncidente(incidente.getIncidente());
					}
				}
			}
			j = i;
			for (RequisicaoMudancaDTO servico : listaMudancaServico) {
				RequisicaoMudancaDTO aux = listaIdETituloMudancasPorPeriodo.get(j);
				if (aux.getIdRequisicaoMudanca() == servico.getIdRequisicaoMudanca()) {
					if (aux.getServico() == null || aux.getServico().isEmpty()) {
						listaIdETituloMudancasPorPeriodo.get(j).setServico(servico.getServico());
					} else if (j + 1 == listaIdETituloMudancasPorPeriodo.size() || listaIdETituloMudancasPorPeriodo.get(j + 1).getIdRequisicaoMudanca() != aux.getIdRequisicaoMudanca()) {
						listaIdETituloMudancasPorPeriodo.add(++j, servico);
					} else {
						listaIdETituloMudancasPorPeriodo.get(++j).setServico(servico.getIncidente());
					}
				}
			}
			j = i;
			for (RequisicaoMudancaDTO problema : listaMudancaProblema) {
				RequisicaoMudancaDTO aux = listaIdETituloMudancasPorPeriodo.get(j);
				if (aux.getIdRequisicaoMudanca() == problema.getIdRequisicaoMudanca()) {
					if (aux.getProblema() == null || aux.getProblema().isEmpty()) {
						listaIdETituloMudancasPorPeriodo.get(j).setProblema(problema.getProblema());
					} else if (j + 1 == listaIdETituloMudancasPorPeriodo.size() || listaIdETituloMudancasPorPeriodo.get(j + 1).getIdRequisicaoMudanca() != aux.getIdRequisicaoMudanca()) {
						listaIdETituloMudancasPorPeriodo.add(++j, problema);
					} else {
						listaIdETituloMudancasPorPeriodo.get(++j).setProblema(problema.getIncidente());
					}
				}
			}
			j = i;
			for (RequisicaoMudancaDTO grupo : listaMudancaGrupo) {
				RequisicaoMudancaDTO aux = listaIdETituloMudancasPorPeriodo.get(j);
				if (aux.getIdRequisicaoMudanca() == grupo.getIdRequisicaoMudanca()) {
					if (aux.getGrupoMudanca() == null || aux.getGrupoMudanca().isEmpty()) {
						listaIdETituloMudancasPorPeriodo.get(j).setGrupoMudanca(grupo.getGrupoMudanca());
					} else if (j + 1 == listaIdETituloMudancasPorPeriodo.size() || listaIdETituloMudancasPorPeriodo.get(j + 1).getIdRequisicaoMudanca() != aux.getIdRequisicaoMudanca()) {
						listaIdETituloMudancasPorPeriodo.add(++j, grupo);
					} else {
						listaIdETituloMudancasPorPeriodo.get(++j).setGrupoMudanca(grupo.getIncidente());
					}
				}
			}
			j = i;
			for (RequisicaoMudancaDTO conhecimento : listaMudancaConhecimento) {
				RequisicaoMudancaDTO aux = listaIdETituloMudancasPorPeriodo.get(j);
				if (aux.getIdRequisicaoMudanca() == conhecimento.getIdRequisicaoMudanca()) {
					if (aux.getConhecimento() == null || aux.getConhecimento().isEmpty()) {
						listaIdETituloMudancasPorPeriodo.get(j).setConhecimento(conhecimento.getConhecimento());
					} else if (j + 1 == listaIdETituloMudancasPorPeriodo.size() || listaIdETituloMudancasPorPeriodo.get(j + 1).getIdRequisicaoMudanca() != aux.getIdRequisicaoMudanca()) {
						listaIdETituloMudancasPorPeriodo.add(++j, conhecimento);
					} else {
						listaIdETituloMudancasPorPeriodo.get(++j).setConhecimento(conhecimento.getIncidente());
					}
				}
			}
			j = i;
			for (RequisicaoMudancaDTO itemConfiguracao : listaMudancaItemConfiguracao) {
				RequisicaoMudancaDTO aux = listaIdETituloMudancasPorPeriodo.get(j);
				if (aux.getIdRequisicaoMudanca() == itemConfiguracao.getIdRequisicaoMudanca()) {
					if (aux.getItemConfiguracao() == null || aux.getItemConfiguracao().isEmpty()) {
						listaIdETituloMudancasPorPeriodo.get(j).setItemConfiguracao(itemConfiguracao.getItemConfiguracao());
					} else if (j + 1 == listaIdETituloMudancasPorPeriodo.size() || listaIdETituloMudancasPorPeriodo.get(j + 1).getIdRequisicaoMudanca() != aux.getIdRequisicaoMudanca()) {
						listaIdETituloMudancasPorPeriodo.add(++j, itemConfiguracao);
					} else {
						listaIdETituloMudancasPorPeriodo.get(++j).setItemConfiguracao(itemConfiguracao.getIncidente());
					}
				}
			}

			i += listaIdETituloMudancasPorPeriodo.size() - tamanhoInicio;
		}

		if (!listaIdETituloMudancasPorPeriodo.isEmpty()) {
			Integer idRequisicaoMudancaAnterior = listaIdETituloMudancasPorPeriodo.get(0).getIdRequisicaoMudanca();

			Integer totalIncidente = 0;
			Integer totalServico = 0;
			Integer totalProblema = 0;
			Integer totalConhecimento = 0;
			Integer totalItemConfiguracao = 0;

			for (int i = 0; i < listaIdETituloMudancasPorPeriodo.size(); i++) {
				if (listaIdETituloMudancasPorPeriodo.get(i).getIdRequisicaoMudanca().equals(idRequisicaoMudancaAnterior)) {
					if (listaIdETituloMudancasPorPeriodo.get(i).getIncidente() != null && !listaIdETituloMudancasPorPeriodo.get(i).getIncidente().isEmpty()) {
						totalIncidente++;
					}
					if (listaIdETituloMudancasPorPeriodo.get(i).getServico() != null && !listaIdETituloMudancasPorPeriodo.get(i).getServico().isEmpty()) {
						totalServico++;
					}
					if (listaIdETituloMudancasPorPeriodo.get(i).getProblema() != null && !listaIdETituloMudancasPorPeriodo.get(i).getProblema().isEmpty()) {
						totalProblema++;
					}
					if (listaIdETituloMudancasPorPeriodo.get(i).getConhecimento() != null && !listaIdETituloMudancasPorPeriodo.get(i).getConhecimento().isEmpty()) {
						totalConhecimento++;
					}
					if (listaIdETituloMudancasPorPeriodo.get(i).getItemConfiguracao() != null && !listaIdETituloMudancasPorPeriodo.get(i).getItemConfiguracao().isEmpty()) {
						totalItemConfiguracao++;
					}
				} else {
					RequisicaoMudancaDTO mudancaDTO = new RequisicaoMudancaDTO();
					mudancaDTO.setTitulo(negritoHtml(UtilI18N.internacionaliza(request, "citcorpore.comum.totalMudanca")));
					mudancaDTO.setIncidente(negritoHtml(totalIncidente.toString()));
					mudancaDTO.setServico(negritoHtml(totalServico.toString()));
					mudancaDTO.setProblema(negritoHtml(totalProblema.toString()));
					mudancaDTO.setConhecimento(negritoHtml(totalConhecimento.toString()));
					mudancaDTO.setItemConfiguracao(negritoHtml(totalItemConfiguracao.toString()));

					listaIdETituloMudancasPorPeriodo.add(i++, mudancaDTO);

					totalIncidente = 0;
					totalServico = 0;
					totalProblema = 0;
					totalConhecimento = 0;
					totalItemConfiguracao = 0;

					if (listaIdETituloMudancasPorPeriodo.get(i).getIncidente() != null && !listaIdETituloMudancasPorPeriodo.get(i).getIncidente().isEmpty()) {
						totalIncidente++;
					}
					if (listaIdETituloMudancasPorPeriodo.get(i).getServico() != null && !listaIdETituloMudancasPorPeriodo.get(i).getServico().isEmpty()) {
						totalServico++;
					}
					if (listaIdETituloMudancasPorPeriodo.get(i).getProblema() != null && !listaIdETituloMudancasPorPeriodo.get(i).getProblema().isEmpty()) {
						totalProblema++;
					}
					if (listaIdETituloMudancasPorPeriodo.get(i).getConhecimento() != null && !listaIdETituloMudancasPorPeriodo.get(i).getConhecimento().isEmpty()) {
						totalConhecimento++;
					}
					if (listaIdETituloMudancasPorPeriodo.get(i).getItemConfiguracao() != null && !listaIdETituloMudancasPorPeriodo.get(i).getItemConfiguracao().isEmpty()) {
						totalItemConfiguracao++;
					}

					idRequisicaoMudancaAnterior = listaIdETituloMudancasPorPeriodo.get(i).getIdRequisicaoMudanca();
				}
			}
			RequisicaoMudancaDTO mudancaDTO = new RequisicaoMudancaDTO();
			mudancaDTO.setTitulo(negritoHtml(UtilI18N.internacionaliza(request, "citcorpore.comum.totalMudanca")));
			mudancaDTO.setIncidente(negritoHtml(totalIncidente.toString()));
			mudancaDTO.setServico(negritoHtml(totalServico.toString()));
			mudancaDTO.setProblema(negritoHtml(totalProblema.toString()));
			mudancaDTO.setConhecimento(negritoHtml(totalConhecimento.toString()));
			mudancaDTO.setItemConfiguracao(negritoHtml(totalItemConfiguracao.toString()));

			listaIdETituloMudancasPorPeriodo.add(mudancaDTO);
		}

		return listaIdETituloMudancasPorPeriodo;
	}

	
	public Timestamp MontardataHoraAgendamentoInicial(RequisicaoMudancaDTO requisicaoMudancaDto) {
		Timestamp dataHoraInicio = requisicaoMudancaDto.getDataHoraInicioAgendada();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		String horaAgendamentoInicialSTR = format.format(dataHoraInicio);
		horaAgendamentoInicialSTR = horaAgendamentoInicialSTR.substring(0, 11);
		String horaAgendamentoInicial = requisicaoMudancaDto.getHoraAgendamentoInicial();
		String dia = horaAgendamentoInicialSTR.substring(0, 2);
		String mes = horaAgendamentoInicialSTR.substring(3, 5);
		String ano = horaAgendamentoInicialSTR.substring(6, 10);
		String dataHoraMontada = ano + "-" + mes + "-" + dia + " " + horaAgendamentoInicial+":00.0";
		Timestamp dataHoraInicial = Timestamp.valueOf(dataHoraMontada);
	
		return dataHoraInicial;
	}
	
	public Timestamp MontardataHoraAgendamentoFinal(RequisicaoMudancaDTO requisicaoMudancaDto) {
		Timestamp dataHoraFim = requisicaoMudancaDto.getDataHoraTerminoAgendada();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		String horaAgendamentoFinalSTR = format.format(dataHoraFim);
		String horaAgendamentoFinal = requisicaoMudancaDto.getHoraAgendamentoFinal();
		String dia = horaAgendamentoFinalSTR.substring(0, 2);
		String mes = horaAgendamentoFinalSTR.substring(3, 5);
		String ano = horaAgendamentoFinalSTR.substring(6, 10);
		String dataHoraMontada = ano + "-" + mes + "-" + dia + " " + horaAgendamentoFinal+":00.0";
		Timestamp dataHoraFinal = Timestamp.valueOf(dataHoraMontada);
	
		return dataHoraFinal;
	}
	
	public void calculaTempoAtraso(RequisicaoMudancaDTO requisicaoMudancaDto) throws Exception {
		requisicaoMudancaDto.setPrazoHH(0);
		requisicaoMudancaDto.setPrazoMM(0);
		if (requisicaoMudancaDto.getDataHoraInicioAgendada() != null && requisicaoMudancaDto.getDataHoraTerminoAgendada() != null) {
			Timestamp dataHoraInicioComparacao = requisicaoMudancaDto.getDataHoraInicioAgendada();
			Timestamp dataHoraFinalComparacao = requisicaoMudancaDto.getDataHoraTerminoAgendada();
			if (dataHoraFinalComparacao.compareTo(dataHoraInicioComparacao) > 0) {
				long atrasoSLA = UtilDatas.calculaDiferencaTempoEmMilisegundos(dataHoraFinalComparacao, dataHoraInicioComparacao) / 1000;

				String hora = Util.getHoraStr(new Double(atrasoSLA) / 3600);
				int tam = hora.length();
				requisicaoMudancaDto.setPrazoHH(new Integer(hora.substring(0, tam - 2)));
				requisicaoMudancaDto.setPrazoMM(new Integer(hora.substring(tam - 2, tam)));
			}
		}
	}
	
	public boolean seHoraInicialMenorQAtual(RequisicaoMudancaDTO requisicaoMudancaDto) {
		boolean resultado = false;
		Time horaAtual = UtilDatas.getHoraAtual();
		Date dataAtual = UtilDatas.getDataAtual();
		String horaAtualStr =  horaAtual.toString();
		String dataAtualStr = dataAtual.toString();
		horaAtualStr = horaAtualStr.substring(0, 5);
		Timestamp dataHoraInicio = requisicaoMudancaDto.getDataHoraInicioAgendada();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dataAgendamentoFinalSTR = format.format(dataHoraInicio);
		if (dataAtualStr.equals(dataAgendamentoFinalSTR)) {
			String horaAgendamentoInicial = requisicaoMudancaDto.getHoraAgendamentoInicial();
			horaAgendamentoInicial = horaAgendamentoInicial.replaceAll(":", "");
			horaAtualStr =  horaAtualStr.replaceAll(":", "");
			int horaAtualInt = Integer.parseInt(horaAtualStr);
			int horaAgendamentoInicialInt = Integer.parseInt(horaAgendamentoInicial);
			if (horaAgendamentoInicialInt < horaAtualInt) {
				resultado = true;
			}
		}
		return resultado;
	}
	
	public boolean seHoraFinalMenorQAtual(RequisicaoMudancaDTO requisicaoMudancaDto) {
		boolean resultado = false;
		Time horaAtual = UtilDatas.getHoraAtual();
		Date dataAtual = UtilDatas.getDataAtual();
		String horaAtualStr =  horaAtual.toString();
		String dataAtualStr = dataAtual.toString();
		horaAtualStr = horaAtualStr.substring(0, 5);
		Timestamp dataHoraFim = requisicaoMudancaDto.getDataHoraTerminoAgendada();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dataAgendamentoFinalSTR = format.format(dataHoraFim);
		if (dataAtualStr.equals(dataAgendamentoFinalSTR)) {
			String horaAgendamentoFim = requisicaoMudancaDto.getHoraAgendamentoFinal();
			horaAgendamentoFim = horaAgendamentoFim.replaceAll(":", "");
			horaAtualStr =  horaAtualStr.replaceAll(":", "");
			int horaAtualInt = Integer.parseInt(horaAtualStr);
			int horaAgendamentoFimInt = Integer.parseInt(horaAgendamentoFim);
			if (horaAgendamentoFimInt < horaAtualInt) {
				resultado = true;
			}
		}
		return resultado;
	}
	
	public boolean seHoraFinalMenorQHoraInicial(RequisicaoMudancaDTO requisicaoMudancaDto) {
		boolean resultado = false;
		Time horaAtual = UtilDatas.getHoraAtual();
		Date dataAtual = UtilDatas.getDataAtual();
		String horaAtualStr =  horaAtual.toString();
		String dataAtualStr = dataAtual.toString();
		horaAtualStr = horaAtualStr.substring(0, 5);
		Timestamp dataHoraInicio = requisicaoMudancaDto.getDataHoraInicioAgendada();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dataAgendamentoInicialSTR = format.format(dataHoraInicio);
		Timestamp dataHoraFim = requisicaoMudancaDto.getDataHoraTerminoAgendada();
		String dataAgendamentoFinalSTR = format.format(dataHoraFim);
		String horaAgendamentoFim = requisicaoMudancaDto.getHoraAgendamentoFinal();
		String horaAgendamentoInicial = requisicaoMudancaDto.getHoraAgendamentoInicial();
		horaAgendamentoInicial = horaAgendamentoInicial.replaceAll(":", "");
		horaAgendamentoFim = horaAgendamentoFim.replaceAll(":", "");
		int horaInicioInt = Integer.parseInt(horaAgendamentoInicial);
		int horaFimInt = Integer.parseInt(horaAgendamentoFim);
		if (dataAtualStr.equals(dataAgendamentoFinalSTR) && dataAtualStr.equals(dataAgendamentoInicialSTR)) {
			if (horaInicioInt > horaFimInt) {
				resultado = true;
			}
		}
		if (dataAgendamentoInicialSTR.equals(dataAgendamentoFinalSTR)) {
			if (horaInicioInt > horaFimInt) {
				resultado = true;
			}
		}
		return resultado;
	}
	
	public boolean validacaoGrupoExecutor(RequisicaoMudancaDTO requisicaoMudancaDto) throws Exception {
		boolean resultado = false;

		if (requisicaoMudancaDto != null && requisicaoMudancaDto.getIdGrupoAtual() != null && requisicaoMudancaDto.getIdTipoMudanca() != null) {
			Integer idGrupoExecutor = requisicaoMudancaDto.getIdGrupoAtual();
			Integer idTipoMudanca = requisicaoMudancaDto.getIdTipoMudanca();

			PermissoesFluxoService permissoesFluxoService = (PermissoesFluxoService) ServiceLocator.getInstance().getService(PermissoesFluxoService.class, null);
			
			resultado = permissoesFluxoService.permissaoGrupoExecutor(idTipoMudanca, idGrupoExecutor);
		}
		return resultado;
	}
	
	@Override
	public String getUrlInformacoesComplementares(RequisicaoMudancaDTO requisicaoLiberacaoDTO) throws Exception {
		String url = "";
		TemplateSolicitacaoServicoDao templateDao = new TemplateSolicitacaoServicoDao();
		TemplateSolicitacaoServicoDTO templateDto = null;
		if (templateDto == null) {
        	String idTemplate =ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.TEMPLATE_QUESTIONARIO, "13");
        	if(idTemplate!= null && !idTemplate.equals("")){
        	templateDto = new TemplateSolicitacaoServicoDTO();
            templateDto.setIdTemplate(new Integer(idTemplate));
        	templateDto = (TemplateSolicitacaoServicoDTO) templateDao.restore(templateDto);
        	}
        }
		if (templateDto != null) {
			/*url = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.URL_Sistema, "");*/
			url += templateDto.getUrlRecuperacao();
			url += "?";
			
			url = url.replaceAll("\n", "");
			url = url.replaceAll("\r", "");
			
			String editar = "S";
		if (requisicaoLiberacaoDTO.getIdRequisicaoMudanca() != null && requisicaoLiberacaoDTO.getIdRequisicaoMudanca().intValue() > 0) {
			/*requisicaoLiberacaoDTO = (RequisicaoLiberacaoDTO) getDao().restore(requisicaoLiberacaoDTO);*/
			
				url += "idRequisicao=" + requisicaoLiberacaoDTO.getIdRequisicaoMudanca() + "&";
				url += "IdTipoRequisicao=" + Enumerados.TipoRequisicao.LIBERCAO.getId() + "&";
				if(requisicaoLiberacaoDTO.getIdTipoAba() != null)
					url += "idTipoAba=" + requisicaoLiberacaoDTO.getIdTipoAba() + "&";
				if (requisicaoLiberacaoDTO.getIdTarefa() == null)
					editar = "N";
				else
					url += "idTarefa=" + requisicaoLiberacaoDTO.getIdTarefa() + "&";
			}
			/*url += "&idContrato=" + requisicaoLiberacaoDTO.getIdContrato();*/
			url += "&editar=" + editar;
		}
		return url;
	}
	
	public boolean verificaPermissaoGrupoCancelar(Integer idTipoMudança, Integer idGrupo) throws ServiceException, Exception{
		boolean isOk = false;
		TipoMudancaService tipoMudancaService = (TipoMudancaService) ServiceLocator.getInstance().getService(TipoMudancaService.class, null);
		TipoMudancaDTO tipoMudancaDto = new TipoMudancaDTO();
		PermissoesFluxoDao permissoesDao = new PermissoesFluxoDao();
		
		tipoMudancaDto.setIdTipoMudanca(idTipoMudança);
		tipoMudancaDto = (TipoMudancaDTO) tipoMudancaService.restore(tipoMudancaDto);
		if(tipoMudancaDto != null){
			PermissoesFluxoDTO permissoesDto = permissoesDao.permissaoGrupoCancelar(idGrupo, tipoMudancaDto.getIdTipoFluxo());
			if(permissoesDto != null && permissoesDto.getCancelar() != null && permissoesDto.getCancelar().equalsIgnoreCase("S"))
				isOk = true;
		}
		
		return isOk;
	}
	
	
	
	
	/*################################################# HISTORICO MUDANCA #################################################*/
	
	public HistoricoMudancaDTO createHistoricoMudanca(RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
		HistoricoMudancaDTO historico = new HistoricoMudancaDTO();
		RequisicaoMudancaDTO requisicaoMudancaDTOAux = requisicaoMudancaDTO;
		Integer idExecutormodificacao = requisicaoMudancaDTO.getUsuarioDto().getIdEmpregado();
		RequisicaoMudancaService requisicaoMudancaService =(RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);
		requisicaoMudancaDTOAux = (RequisicaoMudancaDTO) requisicaoMudancaService.restore(requisicaoMudancaDTO);
		requisicaoMudancaDTOAux.setAlterarSituacao(requisicaoMudancaDTO.getAlterarSituacao());
		requisicaoMudancaDTOAux.setAcaoFluxo(requisicaoMudancaDTO.getAcaoFluxo());
		Reflexao.copyPropertyValues(requisicaoMudancaDTOAux, historico);
		historico.setIdExecutorModificacao(idExecutormodificacao);
		historico.setRegistroexecucao(requisicaoMudancaDTO.getRegistroexecucao());
		
		//esse bloco seta as informações de contato.
		ContatoRequisicaoMudancaDTO contatoRequisicaoMudancaDTO = new ContatoRequisicaoMudancaDTO();
		ContatoRequisicaoMudancaService contatoRequisicaoMudancaService = (ContatoRequisicaoMudancaService) ServiceLocator.getInstance().getService(ContatoRequisicaoMudancaService.class, null);
		requisicaoMudancaDTO.setIdContatoRequisicaoMudanca(historico.getIdContatoRequisicaoMudanca());
		contatoRequisicaoMudancaDTO = (ContatoRequisicaoMudancaDTO) contatoRequisicaoMudancaService.restoreContatosById(requisicaoMudancaDTO.getIdContatoRequisicaoMudanca());
		if(contatoRequisicaoMudancaDTO != null){
			historico.setNomeContato(contatoRequisicaoMudancaDTO.getNomecontato());
			historico.setEmailSolicitante(contatoRequisicaoMudancaDTO.getEmailcontato());
			historico.setIdContatoRequisicaoMudanca(contatoRequisicaoMudancaDTO.getIdContatoRequisicaoMudanca());
			historico.setIdLocalidade(contatoRequisicaoMudancaDTO.getIdLocalidade());
			historico.setRamal(contatoRequisicaoMudancaDTO.getRamal());
			historico.setTelefoneContato(contatoRequisicaoMudancaDTO.getTelefonecontato());
			historico.setObservacao(contatoRequisicaoMudancaDTO.getObservacao());
		}
		
		
		HistoricoMudancaDTO ultVersao = new HistoricoMudancaDTO();
		ultVersao = (HistoricoMudancaDTO) this.getHistoricoMudancaDao().maxIdHistorico(requisicaoMudancaDTO);
		if(ultVersao.getIdHistoricoMudanca()!=null) 
		{
			ultVersao = (HistoricoMudancaDTO) this.getHistoricoMudancaDao().restore(ultVersao);
			historico.setHistoricoVersao((ultVersao.getHistoricoVersao() == null ? 1d : + new BigDecimal(ultVersao.getHistoricoVersao() + 0.1f).setScale(1,BigDecimal.ROUND_DOWN).floatValue()));
		}else {
			historico.setHistoricoVersao(1d);
		}
		
		historico.setDataHoraModificacao(UtilDatas.getDataHoraAtual());
		if(historico.getIdExecutorModificacao() == null){
				historico.setIdExecutorModificacao(1);
			}else{
				historico.setIdExecutorModificacao(idExecutormodificacao);
			}
	
		return historico;
	}
	
	public HistoricoMudancaDao getHistoricoMudancaDao() throws ServiceException {
		return (HistoricoMudancaDao) getHistoricoMudDao();
	}
	
	protected CrudDAO getHistoricoMudDao() throws ServiceException {
		return new HistoricoMudancaDao();
	}
	
	public Collection<RequisicaoMudancaResponsavelDTO> listarColResponsaveis(HistoricoMudancaDTO historicoMudancaDTO) throws ServiceException, Exception{
		RequisicaoMudancaResponsavelDao requisicaoMudancaResponsavelDao = new RequisicaoMudancaResponsavelDao();
		Collection<RequisicaoMudancaResponsavelDTO> requisicaoMudancaResponsavelDTOs = requisicaoMudancaResponsavelDao.findByIdMudancaEDataFim(historicoMudancaDTO.getIdRequisicaoMudanca());
		
		return requisicaoMudancaResponsavelDTOs;
	}
	public List<RequisicaoMudancaItemConfiguracaoDTO> listarColItemConfiguracao(HistoricoMudancaDTO historicoMudancaDTO) throws ServiceException, Exception{
		RequisicaoMudancaItemConfiguracaoDao requisicaoMudancaItemConfiguracaoDao = new RequisicaoMudancaItemConfiguracaoDao();
		List<RequisicaoMudancaItemConfiguracaoDTO> requisicaoMudancaItemConfiguracaoDTOs = (List<RequisicaoMudancaItemConfiguracaoDTO>) requisicaoMudancaItemConfiguracaoDao.findByIdMudancaEDataFim(historicoMudancaDTO.getIdRequisicaoMudanca());
		return requisicaoMudancaItemConfiguracaoDTOs;
	}
	public List<RequisicaoMudancaServicoDTO> listarServico(HistoricoMudancaDTO historicoMudancaDTO) throws ServiceException, Exception{
		RequisicaoMudancaServicoDao requisicaoMudancaServicoDao = new RequisicaoMudancaServicoDao();
		List<RequisicaoMudancaServicoDTO> requisicaoMudancaServicoDTOs =  (List<RequisicaoMudancaServicoDTO>) requisicaoMudancaServicoDao.findByIdMudancaEDataFim(historicoMudancaDTO.getIdRequisicaoMudanca());
		return requisicaoMudancaServicoDTOs;
	}
	
	public List<ProblemaMudancaDTO> listarProblema(HistoricoMudancaDTO historicoMudancaDTO) throws ServiceException, Exception{
		
		ProblemaMudancaDAO problemaMudancaDAO = new ProblemaMudancaDAO();
		List<ProblemaMudancaDTO> problemaMudancaDTOs =    (List<ProblemaMudancaDTO>) problemaMudancaDAO.findByIdMudancaEDataFim(historicoMudancaDTO.getIdRequisicaoMudanca());
		return problemaMudancaDTOs;
	}
	
    public List<GrupoRequisicaoMudancaDTO> listarGrupo(HistoricoMudancaDTO historicoMudancaDTO) throws ServiceException, Exception{
		
		GrupoRequisicaoMudancaDao grupoRequisicaoMudancaDAO = new GrupoRequisicaoMudancaDao();
		List<GrupoRequisicaoMudancaDTO> grupoRequisicaoMudancaDTOs =    (List<GrupoRequisicaoMudancaDTO>) grupoRequisicaoMudancaDAO.findByIdMudancaEDataFim(historicoMudancaDTO.getIdRequisicaoMudanca());
		return grupoRequisicaoMudancaDTOs;
	}
	
	public List<RequisicaoMudancaRiscoDTO> listarRiscos(HistoricoMudancaDTO historicoMudancaDTO) throws ServiceException, Exception{
		
		RequisicaoMudancaRiscoDao riscoDao = new RequisicaoMudancaRiscoDao();
		List<RequisicaoMudancaRiscoDTO> problemaMudancaDTOs =    (List<RequisicaoMudancaRiscoDTO>) riscoDao.findByIdRequisicaoMudancaEDataFim(historicoMudancaDTO.getIdRequisicaoMudanca());
		return problemaMudancaDTOs;
	}
	public List<AprovacaoMudancaDTO> listarAprovacoes(HistoricoMudancaDTO historicoMudancaDTO) throws ServiceException, Exception{
		
		AprovacaoMudancaDao aprovacaoDao = new AprovacaoMudancaDao();
		List<AprovacaoMudancaDTO> aprovacaoMudancaDTOs =    (List<AprovacaoMudancaDTO>) aprovacaoDao.listaAprovacaoMudancaPorIdRequisicaoMudancaEHistorico(historicoMudancaDTO.getIdRequisicaoMudanca(), null, null);
		return aprovacaoMudancaDTOs;
	}
	public List<LiberacaoMudancaDTO> listarLiberacoes(HistoricoMudancaDTO historicoMudancaDTO) throws ServiceException, Exception{
		
		LiberacaoMudancaDao liberacaoMudancaDao = new LiberacaoMudancaDao();
		List<LiberacaoMudancaDTO> liberacaoMudancaDTOs =    (List<LiberacaoMudancaDTO>) liberacaoMudancaDao.findByIdRequisicaoMudanca(historicoMudancaDTO.getIdLiberacao(), historicoMudancaDTO.getIdRequisicaoMudanca());
		return liberacaoMudancaDTOs;
	}
	public List<RequisicaoMudancaDTO> listarSolicitacaoServico(HistoricoMudancaDTO historicoMudancaDTO) throws ServiceException, Exception{
		
		RequisicaoMudancaDao mudancaDao = new RequisicaoMudancaDao();
		List<RequisicaoMudancaDTO> solicitacaoMudancaDTOs =    (List<RequisicaoMudancaDTO>) mudancaDao.findByIdRequisicaoMudancaEDataFim( historicoMudancaDTO.getIdRequisicaoMudanca());
		return solicitacaoMudancaDTOs;
	}
	
	public void deleteAdicionaTabelaResponsavel(RequisicaoMudancaDTO requisicaoMudancaDTO, TransactionControler tc) throws Exception{
		Collection<RequisicaoMudancaResponsavelDTO> colResponsavelBanco = new ArrayList<RequisicaoMudancaResponsavelDTO>();
		RequisicaoMudancaResponsavelDao responsavelDao = new RequisicaoMudancaResponsavelDao();
		colResponsavelBanco = responsavelDao.findByIdMudancaEDataFim(requisicaoMudancaDTO.getIdRequisicaoMudanca());
		responsavelDao.setTransactionControler(tc);
		boolean grava = false;
		boolean exclui = false;
		int idResp1 = 0;
		int idResp2 = 0;
		if((colResponsavelBanco == null) || (colResponsavelBanco.size() == 0)){
			for (RequisicaoMudancaResponsavelDTO requisicaoMudancaResponsavelDTO : requisicaoMudancaDTO.getColResponsaveis()) {
				requisicaoMudancaResponsavelDTO.setIdRequisicaoMudanca(requisicaoMudancaDTO.getIdRequisicaoMudanca());
				responsavelDao.create(requisicaoMudancaResponsavelDTO);
			}
		}else{
			if(requisicaoMudancaDTO.getColResponsaveis() != null && requisicaoMudancaDTO.getColResponsaveis().size() > 0){
				//compara o que vem da tela com o que está no banco se o que estiver na tela for diferente do banco
				// então ele grava poruqe o item não existe no banco.
				for (RequisicaoMudancaResponsavelDTO requisicaoMudancaResponsavelDTO : requisicaoMudancaDTO.getColResponsaveis()) {
					for (RequisicaoMudancaResponsavelDTO requisicaoMudancaResponsavelDTO2 : colResponsavelBanco) {
						idResp1 = requisicaoMudancaResponsavelDTO.getIdResponsavel();
						idResp2 = requisicaoMudancaResponsavelDTO2.getIdResponsavel();
						if(idResp1 == idResp2){
							grava = false;
							break;
						}else{
							grava = true;
						}
					}
					if(grava){
						requisicaoMudancaResponsavelDTO.setIdRequisicaoMudanca(requisicaoMudancaDTO.getIdRequisicaoMudanca());
						responsavelDao.create(requisicaoMudancaResponsavelDTO);
					}
				}
				//Compara o que vem do banco com o que está na tela se o que estiver no banco for diferente do que tem na tela
				//então ele seta a data fim para desabilitar no banco.
				if(colResponsavelBanco != null && colResponsavelBanco.size() > 0){
					for (RequisicaoMudancaResponsavelDTO requisicaoMudancaResponsavelDTO : colResponsavelBanco) {
						for (RequisicaoMudancaResponsavelDTO requisicaoMudancaResponsavelDTO2 : requisicaoMudancaDTO.getColResponsaveis()) {
							idResp1 = requisicaoMudancaResponsavelDTO.getIdResponsavel();
							idResp2 = requisicaoMudancaResponsavelDTO2.getIdResponsavel();
							if(idResp1 == idResp2){
								exclui = false;
								break;
							}else{
								exclui = true;
								requisicaoMudancaResponsavelDTO.setDataFim(UtilDatas.getDataAtual());
							}
						}
						if(exclui){
							responsavelDao.update(requisicaoMudancaResponsavelDTO);
						}
					}
				}
			}
		}

	}
	public void deleteAdicionaTabelaProblema(RequisicaoMudancaDTO requisicaoMudancaDTO, TransactionControler tc) throws Exception{
		Collection<ProblemaMudancaDTO> colProblemaBanco = new ArrayList<ProblemaMudancaDTO>();
		ProblemaMudancaDAO problemaDao = new ProblemaMudancaDAO();
		colProblemaBanco = problemaDao.findByIdMudancaEDataFim(requisicaoMudancaDTO.getIdRequisicaoMudanca());
		problemaDao.setTransactionControler(tc);
		boolean grava = false;
		boolean exclui = false;
		int idProblema1 = 0;
		int idProblema2 = 0;
		if((colProblemaBanco == null) || (colProblemaBanco.size() == 0)){
			for (ProblemaMudancaDTO problemamudancaDto : requisicaoMudancaDTO.getListProblemaMudancaDTO()) {
				problemamudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDTO.getIdRequisicaoMudanca());
				problemaDao.create(problemamudancaDto);
			}
		}else{
			if(requisicaoMudancaDTO.getListProblemaMudancaDTO() != null && requisicaoMudancaDTO.getListProblemaMudancaDTO().size() > 0){
				//compara o que vem da tela com o que está no banco se o que estiver na tela for diferente do banco
				// então ele grava poruqe o item não existe no banco.
				for (ProblemaMudancaDTO problemaMudancaDTO : requisicaoMudancaDTO.getListProblemaMudancaDTO()) {
					for (ProblemaMudancaDTO problemaMudancaDTO2 : colProblemaBanco) {
						idProblema1 = problemaMudancaDTO.getIdProblema();
						idProblema2 = problemaMudancaDTO2.getIdProblema();
						if(idProblema1 == idProblema2){
							grava = false;
							break;
						}else{
							grava = true;
						}
					}
					if(grava){
						problemaMudancaDTO.setIdRequisicaoMudanca(requisicaoMudancaDTO.getIdRequisicaoMudanca());
						problemaDao.create(problemaMudancaDTO);
					}
				}
				//Compara o que vem do banco com o que está na tela se o que estiver no banco for diferente do que tem na tela
				//então ele seta a data fim para desabilitar no banco.
				if(colProblemaBanco != null && colProblemaBanco.size() > 0){
					for (ProblemaMudancaDTO problemaMudancaDTO : colProblemaBanco) {
						for (ProblemaMudancaDTO requisicaoMudancaResponsavelDTO2 : requisicaoMudancaDTO.getListProblemaMudancaDTO()) {
							idProblema1 = problemaMudancaDTO.getIdProblema();
							idProblema2 = requisicaoMudancaResponsavelDTO2.getIdProblema();
							if(idProblema1 == idProblema2){
								exclui = false;
								break;
							}else{
								exclui = true;
								problemaMudancaDTO.setDataFim(UtilDatas.getDataAtual());
							}
						}
						if(exclui){
							problemaDao.update(problemaMudancaDTO);
						}
					}
				}
			}
		}
		
	}
	public void deleteAdicionaTabelaRiscos(RequisicaoMudancaDTO requisicaoMudancaDTO, TransactionControler tc) throws Exception{
		Collection<RequisicaoMudancaRiscoDTO> colRiscosBanco = new ArrayList<RequisicaoMudancaRiscoDTO>();
		RequisicaoMudancaRiscoDao riscosDao = new RequisicaoMudancaRiscoDao();
		colRiscosBanco = riscosDao.findByIdRequisicaoMudancaEDataFim(requisicaoMudancaDTO.getIdRequisicaoMudanca());
		riscosDao.setTransactionControler(tc);
		boolean grava = false;
		boolean exclui = false;
		int idRisco1 = 0;
		int idRisco2 = 0;
		if((colRiscosBanco == null) || (colRiscosBanco.size() == 0)){
			for (RequisicaoMudancaRiscoDTO riscoMudancaDto : requisicaoMudancaDTO.getListRequisicaoMudancaRiscoDTO()) {
				riscoMudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDTO.getIdRequisicaoMudanca());
				riscosDao.create(riscoMudancaDto);
			}
		}else{
			if(requisicaoMudancaDTO.getListRequisicaoMudancaRiscoDTO() != null && requisicaoMudancaDTO.getListRequisicaoMudancaRiscoDTO().size() > 0){
				//compara o que vem da tela com o que está no banco se o que estiver na tela for diferente do banco
				// então ele grava poruqe o item não existe no banco.
				for (RequisicaoMudancaRiscoDTO riscoMudancaDTO : requisicaoMudancaDTO.getListRequisicaoMudancaRiscoDTO()) {
					for (RequisicaoMudancaRiscoDTO riscoMudancaDTO2 : colRiscosBanco) {
						idRisco1 = riscoMudancaDTO.getIdRisco();
						idRisco2 = riscoMudancaDTO2.getIdRisco();
						if(idRisco1 == idRisco2){
							grava = false;
							break;
						}else{
							grava = true;
						}
					}
					if(grava){
						riscoMudancaDTO.setIdRequisicaoMudanca(requisicaoMudancaDTO.getIdRequisicaoMudanca());
						riscosDao.create(riscoMudancaDTO);
					}
				}
				//Compara o que vem do banco com o que está na tela se o que estiver no banco for diferente do que tem na tela
				//então ele seta a data fim para desabilitar no banco.
				if(colRiscosBanco != null && colRiscosBanco.size() > 0){
					for (RequisicaoMudancaRiscoDTO riscoMudancaDTO : colRiscosBanco) {
						for (RequisicaoMudancaRiscoDTO riscoMudancaDTO2 : requisicaoMudancaDTO.getListRequisicaoMudancaRiscoDTO()) {
							idRisco1 = riscoMudancaDTO.getIdRisco();
							idRisco2 = riscoMudancaDTO2.getIdRisco();
							if(idRisco1 == idRisco2){
								exclui = false;
								break;
							}else{
								exclui = true;
								riscoMudancaDTO.setDataFim(UtilDatas.getDataAtual());
								riscoMudancaDTO.setIdRequisicaoMudanca(requisicaoMudancaDTO.getIdRequisicaoMudanca());
							}
						}
						if(exclui){
							riscosDao.update(riscoMudancaDTO);
						}
					}
				}
			}
		}
		
	}
	
	public void deleteAdicionaTabelaGrupo(RequisicaoMudancaDTO requisicaoMudancaDTO, TransactionControler tc) throws Exception{
		Collection<GrupoRequisicaoMudancaDTO> colGrupoBanco = new ArrayList<GrupoRequisicaoMudancaDTO>();
		GrupoRequisicaoMudancaDao grupoDao = new GrupoRequisicaoMudancaDao();
		colGrupoBanco = grupoDao.findByIdMudancaEDataFim(requisicaoMudancaDTO.getIdRequisicaoMudanca());
		grupoDao.setTransactionControler(tc);
		boolean grava = false;
		boolean exclui = false;
		int idGrupo1 = 0;
		int idGrupo2 = 0;
		if((colGrupoBanco == null) || (colGrupoBanco.size() == 0)){
			for (GrupoRequisicaoMudancaDTO gruporequisicaomudancaDto : requisicaoMudancaDTO.getListGrupoRequisicaoMudancaDTO()) {
				gruporequisicaomudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDTO.getIdRequisicaoMudanca());
				grupoDao.create(gruporequisicaomudancaDto);
			}
		}else{
			if(requisicaoMudancaDTO.getListGrupoRequisicaoMudancaDTO() != null && requisicaoMudancaDTO.getListGrupoRequisicaoMudancaDTO().size() > 0){
				for (GrupoRequisicaoMudancaDTO gruporequisicaomudancaDto : requisicaoMudancaDTO.getListGrupoRequisicaoMudancaDTO()) {
					for (GrupoRequisicaoMudancaDTO gruporequisicaomudancaDto2 : colGrupoBanco) {
						idGrupo1 = gruporequisicaomudancaDto.getIdGrupo();
						idGrupo2 = gruporequisicaomudancaDto2.getIdGrupo();
						if(idGrupo1 == idGrupo2){
							grava = false;
							break;
						}else{
							grava = true;
						}
					}
					if(grava){
						gruporequisicaomudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDTO.getIdRequisicaoMudanca());
						grupoDao.create(gruporequisicaomudancaDto);
					}
				}

				if(colGrupoBanco != null && colGrupoBanco.size() > 0){
					for (GrupoRequisicaoMudancaDTO gruporequisicaomudancaDto : colGrupoBanco) {
						for (GrupoRequisicaoMudancaDTO gruporequisicaomudancaDto2 : requisicaoMudancaDTO.getListGrupoRequisicaoMudancaDTO()) {
							idGrupo1 = gruporequisicaomudancaDto.getIdGrupo();
							idGrupo2 = gruporequisicaomudancaDto2.getIdGrupo();
							if(idGrupo1 == idGrupo2){
								exclui = false;
								break;
							}else{
								exclui = true;
								gruporequisicaomudancaDto.setDataFim(UtilDatas.getDataAtual());
							}
						}
						if(exclui){
							grupoDao.update(gruporequisicaomudancaDto);
						}
					}
				}
			}
		}
		
	}
	
	private void gravarLiberacaoHistorico(HistoricoMudancaDTO historicoMudancaDTO, TransactionControler tc) throws ServiceException, Exception{
		LiberacaoMudancaDao liberacaoMudancaDao = new LiberacaoMudancaDao();
 
		if (tc != null){
			liberacaoMudancaDao.setTransactionControler(tc);
		}
		
		if (historicoMudancaDTO.getListLiberacaoMudancaDTO() != null) {
			//grava no banco os historicos de liberacao.
			for (LiberacaoMudancaDTO liberacaoMudancaDTO : historicoMudancaDTO.getListLiberacaoMudancaDTO()) {
				
				liberacaoMudancaDTO.setIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca()); 
				liberacaoMudancaDTO.setIdRequisicaoMudanca(historicoMudancaDTO.getIdRequisicaoMudanca());
				liberacaoMudancaDao.create(liberacaoMudancaDTO);
			}
		}
		
	}
	//metodo que grava o historico da grid de incidentes/requisicoes
	private void gravarSolicitacaoServicoHistoricos(HistoricoMudancaDTO historicoMudancaDTO, List<RequisicaoMudancaDTO> listSolicitacaoServicosMudanca, TransactionControler tc) throws ServiceException, Exception{
		SolicitacaoServicoMudancaDao solicitacaoServicoMudancaDao = new SolicitacaoServicoMudancaDao();
		
		if (tc != null){
			solicitacaoServicoMudancaDao.setTransactionControler(tc);
		}
		
		if (listSolicitacaoServicosMudanca != null) {
			//grava no banco os historicos de liberacao.
			for (RequisicaoMudancaDTO solicitacaoMudancaDTO : listSolicitacaoServicosMudanca) {
				SolicitacaoServicoMudancaDTO solicitacaoServicoMudancaDTO = new SolicitacaoServicoMudancaDTO();
				
				solicitacaoServicoMudancaDTO.setIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca()); 
				solicitacaoServicoMudancaDTO.setIdRequisicaoMudanca(historicoMudancaDTO.getIdRequisicaoMudanca());
				solicitacaoServicoMudancaDTO.setIdSolicitacaoServico(solicitacaoMudancaDTO.getIdSolicitacaoServico());
				solicitacaoServicoMudancaDao.create(solicitacaoServicoMudancaDTO);
			}
		}
	}
	
	public void fechaRelacionamentoMudanca(TransactionControler tc , RequisicaoMudancaDTO requisicaoMudancaDto){
	    if(tc != null){
			this.fecharSolicitacaoServicoVinculadaMudanca(tc, requisicaoMudancaDto);
			this.fecharProblemaVinculadoMudanca(requisicaoMudancaDto, tc);
			this.fecharItemConfiguracaoVinculadoMudanca(requisicaoMudancaDto, tc);
	    }
	}
	
	public void fecharSolicitacaoServicoVinculadaMudanca(TransactionControler tc , RequisicaoMudancaDTO requisicaoMudancaDto){
		SolicitacaoServicoMudancaDao solicitacaoServicoMudancaDao = new SolicitacaoServicoMudancaDao();
		try {		
			solicitacaoServicoMudancaDao.setTransactionControler(tc);
			List<SolicitacaoServicoDTO> listSolicitacaoServicoDTO  = (List<SolicitacaoServicoDTO>) solicitacaoServicoMudancaDao.listSolicitacaoByIdMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
			if(listSolicitacaoServicoDTO != null && listSolicitacaoServicoDTO.size() > 0){
				for (SolicitacaoServicoDTO solicitacaoServicoDTO : listSolicitacaoServicoDTO) {
					new SolicitacaoServicoServiceEjb().fechaSolicitacaoServicoVinculadaByProblemaOrMudanca(solicitacaoServicoDTO, tc);
				}
			}
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void fecharProblemaVinculadoMudanca( RequisicaoMudancaDTO requisicaoMudancaDto, TransactionControler tc){		
		ProblemaMudancaDAO problemaMudancaDAO = new ProblemaMudancaDAO();
		try {
			problemaMudancaDAO.setTransactionControler(tc);
			List<ProblemaDTO> listProblemaDto =  problemaMudancaDAO.listProblemaByIdMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
			if(listProblemaDto != null && listProblemaDto.size() > 0){
				for (ProblemaDTO problemaDTO : listProblemaDto) {
					new ProblemaServiceEjb().fechaProblemaERelacionamento(problemaDTO, tc);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void fecharItemConfiguracaoVinculadoMudanca( RequisicaoMudancaDTO requisicaoMudancaDto, TransactionControler tc){
		RequisicaoMudancaItemConfiguracaoDao requisicaoMudancaIcDao = new RequisicaoMudancaItemConfiguracaoDao();
		try {
			requisicaoMudancaIcDao.setTransactionControler(tc);
			List<ItemConfiguracaoDTO> listItemCofiguracao =  requisicaoMudancaIcDao.listItemConfiguracaoByIdMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
			if(listItemCofiguracao != null && listItemCofiguracao.size() > 0){
				for (ItemConfiguracaoDTO itemConfiguracaoDTO : listItemCofiguracao) {
					new ItemConfiguracaoServiceEjb().finalizarItemConfiguracao(itemConfiguracaoDTO, tc);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean verificarItensRelacionados(RequisicaoMudancaDTO requisicaoMudancaDto) throws ServiceException, Exception {
		// TODO Auto-generated method stub
		RequisicaoMudancaItemConfiguracaoDao requisicaoMudancaIcDao = new RequisicaoMudancaItemConfiguracaoDao();
		ProblemaMudancaDAO problemaMudancaDAO = new ProblemaMudancaDAO();
		SolicitacaoServicoMudancaDao solicitacaoServicoMudancaDao = new SolicitacaoServicoMudancaDao();
		
		List<ProblemaDTO> listProblemaDto =  problemaMudancaDAO.listProblemaByIdMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
		List<SolicitacaoServicoDTO> listSolicitacaoServicoDTO  = (List<SolicitacaoServicoDTO>) solicitacaoServicoMudancaDao.listSolicitacaoByIdMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
		List<ItemConfiguracaoDTO> listItemCofiguracao =  requisicaoMudancaIcDao.listItemConfiguracaoByIdMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
		
		if(listProblemaDto != null && listProblemaDto.size() > 0){
			return true;
		}else if(listSolicitacaoServicoDTO != null && listSolicitacaoServicoDTO.size() > 0){
			return true;
		}else if(listItemCofiguracao != null && listItemCofiguracao.size() > 0){
			return true;
		}
		
		return false;
	}

	public void updateTimeAction(Integer idGrupoRedirect, Integer idPrioridadeRedirect, Integer idRequisicaoMudanca) throws ServiceException, LogicException {
		
		ExecucaoMudancaServiceEjb execucaoMudancaService = new ExecucaoMudancaServiceEjb();
		RequisicaoMudancaDao requiscaoMudancaDao = new RequisicaoMudancaDao();
		OcorrenciaMudancaDao ocorrenciaMudancaDao = new OcorrenciaMudancaDao();
		
		TransactionControler tc = new TransactionControlerImpl(requiscaoMudancaDao.getAliasDB());
		
		try {
			
			tc.start();
			
			// Faz validacao, caso exista.
			
			requiscaoMudancaDao.setTransactionControler(tc);
			ocorrenciaMudancaDao.setTransactionControler(tc);
			
			List<RequisicaoMudancaDTO> listaRequisicaoMudanca = new ArrayList<RequisicaoMudancaDTO>();
			
			RequisicaoMudancaDTO mudancaAuxDto = new RequisicaoMudancaDTO();
			mudancaAuxDto.setIdRequisicaoMudanca(idRequisicaoMudanca);
			
			listaRequisicaoMudanca = (List<RequisicaoMudancaDTO>) requiscaoMudancaDao.find(mudancaAuxDto);
			if (listaRequisicaoMudanca != null) {
				mudancaAuxDto = listaRequisicaoMudanca.get(0);
			}
			if(mudancaAuxDto.getIdGrupoAtual() != null){
				//return;
			}
			
			RequisicaoMudancaDTO requisicaoMudancaDto = new RequisicaoMudancaDTO();
			
			requisicaoMudancaDto.setIdGrupoAtual(idGrupoRedirect);
			requisicaoMudancaDto.setPrioridade(idPrioridadeRedirect);
			requisicaoMudancaDto.setIdRequisicaoMudanca(idRequisicaoMudanca);
			
			requiscaoMudancaDao.updateNotNull(requisicaoMudancaDto);
			execucaoMudancaService.direcionaAtendimentoAutomatico(requisicaoMudancaDto, tc);
			
			String strOcorr = "\nEscalação automática.";
			
			// SolicitacaoServicoDTO solicitacaoAuxDto =
			// restoreAll(solicitacaoServicoDto.getIdSolicitacaoServico());
			
			JustificativaRequisicaoMudancaDTO justificativaDto = new JustificativaRequisicaoMudancaDTO();
			justificativaDto.setIdJustificativaMudanca(requisicaoMudancaDto.getIdJustificativa());
			justificativaDto.setDescricaoJustificativa(requisicaoMudancaDto.getComplementoJustificativa());
			
			UsuarioDTO usuarioDTO = new UsuarioDTO();
			usuarioDTO.setLogin("Automático");
			
			OcorrenciaMudancaServiceEjb.create(requisicaoMudancaDto, null, strOcorr, OrigemOcorrencia.OUTROS, CategoriaOcorrencia.Atualizacao, null, CategoriaOcorrencia.Atualizacao.getDescricao(), usuarioDTO, 0, justificativaDto, tc);
			
			tc.commit();
			tc.close();
            tc = null;
            
		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
			throw new ServiceException(e);
		}
	}

	@Override
	public void gravaInformacoesGED(Collection colArquivosUpload,
			int idEmpresa, RequisicaoMudancaDTO requisicaoMudancaDto,
			TransactionControler tc) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	// Thiago Fernandes - 01/11/2013 14:06 - Sol. 121468 - Criação de metodo para validar se foi ninformado o anxo plano de reverção, caso não tenha deve ser aberta a aba de anxo plano de reverção.
	public boolean planoDeReversaoInformado(RequisicaoMudancaDTO requisicaoMudancaDto, HttpServletRequest request) throws Exception {
		boolean planoReversaoInformado = true;
		TipoMudancaDTO tipoMudancaDto = new TipoMudancaDTO();
		AprovacaoMudancaDao aprovacaoMudancaDao = new AprovacaoMudancaDao();
		TipoMudancaDAO tipoMudancaDAO = new TipoMudancaDAO();
		TransactionControler tc = new TransactionControlerImpl(requisicaoMudancaDao.getAliasDB());
		try {
		aprovacaoMudancaDao.setTransactionControler(tc);
		tipoMudancaDAO.setTransactionControler(tc);
		tc.start();
		if(requisicaoMudancaDto.getIdTipoMudanca()!=null){
			tipoMudancaDto.setIdTipoMudanca(requisicaoMudancaDto.getIdTipoMudanca());
			tipoMudancaDto = (TipoMudancaDTO) tipoMudancaDAO.restore(tipoMudancaDto);
		}
		if (requisicaoMudancaDto != null && requisicaoMudancaDto.getDataHoraInicioAgendada() != null && requisicaoMudancaDto.getHoraAgendamentoInicial() != null
				&& requisicaoMudancaDto != null && requisicaoMudancaDto.getDataHoraInicioAgendada() != null && requisicaoMudancaDto.getHoraAgendamentoInicial() != null) {
				boolean resultado = seHoraFinalMenorQHoraInicial(requisicaoMudancaDto);
				if (resultado == true) {
					throw new LogicException(UtilI18N.internacionaliza(request, "requisicaoMudanca.horaFinalMenorQueInicial"));
				}
			}
			
			if (requisicaoMudancaDto != null && requisicaoMudancaDto.getDataHoraInicioAgendada() != null && requisicaoMudancaDto.getHoraAgendamentoInicial() != null) {
				boolean resultado = seHoraInicialMenorQAtual(requisicaoMudancaDto);
				if (resultado == true) {
					throw new LogicException(UtilI18N.internacionaliza(request, "requisicaoMudanca.horaInicialMenorQueAtual"));
				}
			}
			
			if (requisicaoMudancaDto != null && requisicaoMudancaDto.getDataHoraTerminoAgendada() != null && requisicaoMudancaDto.getHoraAgendamentoFinal() != null) {
				boolean resultado = seHoraFinalMenorQAtual(requisicaoMudancaDto);
				if (resultado == true) {
					throw new LogicException(UtilI18N.internacionaliza(request, "requisicaoMudanca.horaFinalMenorQueAtual"));
				}
			}
			
			// gravando a aprovação de mudança
			if(requisicaoMudancaDto.getListAprovacaoMudancaDTO()!=null && !requisicaoMudancaDto.getFase().equalsIgnoreCase("Proposta")){
				aprovacaoMudancaDao.deleteByIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
				for(AprovacaoMudancaDTO aprovacaoMudancaDto : requisicaoMudancaDto.getListAprovacaoMudancaDTO() ){
					aprovacaoMudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
					aprovacaoMudancaDto.setDataHoraInicio(UtilDatas.getDataHoraAtual());
					aprovacaoMudancaDao.create(aprovacaoMudancaDto);
				}
			}

			if(requisicaoMudancaDto.getAcaoFluxo().equalsIgnoreCase("E") && !requisicaoMudancaDto.getFase().equalsIgnoreCase("Proposta")){
				if(!requisicaoMudancaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoMudanca.Cancelada.name())){
					if(tipoMudancaDto.getExigeAprovacao() != null){
						if(!validacaoAvancaFluxo(requisicaoMudancaDto,tc)){
							throw new LogicException(UtilI18N.internacionaliza(request, "requisicaoMudanca.essaSolicitacaoMudancaNaoFoiAprovada"));
						}
					}
					if(!requisicaoMudancaDto.getStatus().equalsIgnoreCase("Rejeitada")){
						Collection<UploadDTO> arquivosUpados = (Collection<UploadDTO>) request.getSession(true).getAttribute("colUploadPlanoDeReversaoGED");
						if (arquivosUpados == null || arquivosUpados.size() == 0) {
							planoReversaoInformado = false;
						}
					}
				}
				
			}
		tc.commit();
		tc.close();
		tc = null;
		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
			throw new ServiceException(e);
		}
		return planoReversaoInformado;
	}
	
	
	
}

