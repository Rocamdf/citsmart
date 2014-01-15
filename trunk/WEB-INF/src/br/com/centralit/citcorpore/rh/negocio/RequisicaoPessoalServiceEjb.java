package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.centralit.bpm.negocio.ItemTrabalho;
import br.com.centralit.citcorpore.bean.CargosDTO;
import br.com.centralit.citcorpore.bean.CidadesDTO;
import br.com.centralit.citcorpore.bean.ParecerDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UfDTO;
import br.com.centralit.citcorpore.exception.LogicException;
import br.com.centralit.citcorpore.integracao.CidadesDao;
import br.com.centralit.citcorpore.integracao.UfDao;
import br.com.centralit.citcorpore.negocio.CargosService;
import br.com.centralit.citcorpore.negocio.ParecerServiceEjb;
import br.com.centralit.citcorpore.rh.bean.AtitudeIndividualDTO;
import br.com.centralit.citcorpore.rh.bean.CertificacaoDTO;
import br.com.centralit.citcorpore.rh.bean.ConhecimentoDTO;
import br.com.centralit.citcorpore.rh.bean.CursoDTO;
import br.com.centralit.citcorpore.rh.bean.DescricaoCargoDTO;
import br.com.centralit.citcorpore.rh.bean.EntrevistaCandidatoDTO;
import br.com.centralit.citcorpore.rh.bean.ExperienciaInformaticaDTO;
import br.com.centralit.citcorpore.rh.bean.FormacaoAcademicaDTO;
import br.com.centralit.citcorpore.rh.bean.HabilidadeDTO;
import br.com.centralit.citcorpore.rh.bean.IdiomaDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoAtitudeIndividualDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoCertificacaoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoConhecimentoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoCursoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoExperienciaAnteriorDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoExperienciaInformaticaDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoFormacaoAcademicaDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoHabilidadeDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoIdiomaDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoPessoalDTO;
import br.com.centralit.citcorpore.rh.bean.TriagemRequisicaoPessoalDTO;
import br.com.centralit.citcorpore.rh.integracao.AtitudeIndividualDao;
import br.com.centralit.citcorpore.rh.integracao.CertificacaoDao;
import br.com.centralit.citcorpore.rh.integracao.ConhecimentoDao;
import br.com.centralit.citcorpore.rh.integracao.CursoDao;
import br.com.centralit.citcorpore.rh.integracao.EntrevistaCandidatoDao;
import br.com.centralit.citcorpore.rh.integracao.ExperienciaInformaticaDao;
import br.com.centralit.citcorpore.rh.integracao.FormacaoAcademicaDao;
import br.com.centralit.citcorpore.rh.integracao.HabilidadeDao;
import br.com.centralit.citcorpore.rh.integracao.IdiomaDao;
import br.com.centralit.citcorpore.rh.integracao.RequisicaoAtitudeIndividualDao;
import br.com.centralit.citcorpore.rh.integracao.RequisicaoCertificacaoDao;
import br.com.centralit.citcorpore.rh.integracao.RequisicaoConhecimentoDao;
import br.com.centralit.citcorpore.rh.integracao.RequisicaoCursoDao;
import br.com.centralit.citcorpore.rh.integracao.RequisicaoExperienciaAnteriorDao;
import br.com.centralit.citcorpore.rh.integracao.RequisicaoExperienciaInformaticaDao;
import br.com.centralit.citcorpore.rh.integracao.RequisicaoFormacaoAcademicaDao;
import br.com.centralit.citcorpore.rh.integracao.RequisicaoHabilidadeDao;
import br.com.centralit.citcorpore.rh.integracao.RequisicaoIdiomaDao;
import br.com.centralit.citcorpore.rh.integracao.RequisicaoPessoalDao;
import br.com.centralit.citcorpore.rh.integracao.TriagemRequisicaoPessoalDao;
import br.com.centralit.citcorpore.util.Enumerados.TipoEntrevista;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilStrings;
import br.com.citframework.util.WebUtil;

@SuppressWarnings({"unchecked","rawtypes"})
public class RequisicaoPessoalServiceEjb extends CrudServicePojoImpl implements RequisicaoPessoalService {

	private static final long serialVersionUID = 1L;

	protected CrudDAO getDao() throws ServiceException{ 
		return new RequisicaoPessoalDao(); 
	}
	
	protected void validaCreate(Object arg0) throws Exception{
	}
	
	protected void validaDelete(Object arg0) throws Exception{
	}
	
	protected void validaFind(Object arg0) throws Exception{
	}
	
	protected void validaUpdate(Object arg0) throws Exception{
	}
	
	@Override
	public IDto deserializaObjeto(String serialize) throws Exception {
	    RequisicaoPessoalDTO requisicaoPessoalDto = null;
	    
        if (serialize != null) {
            requisicaoPessoalDto = (RequisicaoPessoalDTO) WebUtil.deserializeObject(RequisicaoPessoalDTO.class, serialize);
            if (requisicaoPessoalDto != null && requisicaoPessoalDto.getSerializeFormacaoAcademica() != null)
                requisicaoPessoalDto.setColFormacaoAcademica(WebUtil.deserializeCollectionFromString(RequisicaoFormacaoAcademicaDTO.class, requisicaoPessoalDto.getSerializeFormacaoAcademica()));
            if (requisicaoPessoalDto != null && requisicaoPessoalDto.getSerializeCertificacao() != null)
                requisicaoPessoalDto.setColCertificacao(WebUtil.deserializeCollectionFromString(RequisicaoCertificacaoDTO.class, requisicaoPessoalDto.getSerializeCertificacao()));
            if (requisicaoPessoalDto != null && requisicaoPessoalDto.getSerializeCurso() != null)
                requisicaoPessoalDto.setColCurso(WebUtil.deserializeCollectionFromString(RequisicaoCursoDTO.class, requisicaoPessoalDto.getSerializeCurso()));
            if (requisicaoPessoalDto != null && requisicaoPessoalDto.getSerializeExperienciaInformatica() != null)
                requisicaoPessoalDto.setColExperienciaInformatica(WebUtil.deserializeCollectionFromString(RequisicaoExperienciaInformaticaDTO.class, requisicaoPessoalDto.getSerializeExperienciaInformatica()));
            if (requisicaoPessoalDto != null && requisicaoPessoalDto.getSerializeIdioma() != null)
                requisicaoPessoalDto.setColIdioma(WebUtil.deserializeCollectionFromString(RequisicaoIdiomaDTO.class, requisicaoPessoalDto.getSerializeIdioma()));
            if (requisicaoPessoalDto != null && requisicaoPessoalDto.getSerializeExperienciaAnterior() != null)
                requisicaoPessoalDto.setColExperienciaAnterior(WebUtil.deserializeCollectionFromString(RequisicaoExperienciaAnteriorDTO.class, requisicaoPessoalDto.getSerializeExperienciaAnterior()));
            if (requisicaoPessoalDto != null && requisicaoPessoalDto.getSerializeHabilidade() != null)
                requisicaoPessoalDto.setColHabilidade(WebUtil.deserializeCollectionFromString(RequisicaoHabilidadeDTO.class, requisicaoPessoalDto.getSerializeHabilidade()));
            if (requisicaoPessoalDto != null && requisicaoPessoalDto.getSerializeAtitudeIndividual() != null)
                requisicaoPessoalDto.setColAtitudeIndividual(WebUtil.deserializeCollectionFromString(RequisicaoAtitudeIndividualDTO.class, requisicaoPessoalDto.getSerializeAtitudeIndividual()));
            if (requisicaoPessoalDto != null && requisicaoPessoalDto.getSerializeConhecimento() != null)
            	requisicaoPessoalDto.setColConhecimento(WebUtil.deserializeCollectionFromString(RequisicaoConhecimentoDTO.class, requisicaoPessoalDto.getSerializeConhecimento()));            
            if (requisicaoPessoalDto != null && requisicaoPessoalDto.getSerializeTriagem() != null)
                requisicaoPessoalDto.setColTriagem(WebUtil.deserializeCollectionFromString(TriagemRequisicaoPessoalDTO.class, requisicaoPessoalDto.getSerializeTriagem()));
        }
        
        return requisicaoPessoalDto;
	}

	@Override
	public void validaCreate(SolicitacaoServicoDTO solicitacaoServicoDto,IDto model) throws Exception {
		RequisicaoPessoalDTO requisicaoPessoalDto = (RequisicaoPessoalDTO) model;
		validaAtualizacao(solicitacaoServicoDto, requisicaoPessoalDto);
	}

	private void validaAtualizacao(SolicitacaoServicoDTO solicitacaoServicoDto, RequisicaoPessoalDTO requisicaoPessoalDto) throws Exception {
		String acao = UtilStrings.nullToVazio(requisicaoPessoalDto.getAcao());
		requisicaoPessoalDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
        if (acao.equalsIgnoreCase(RequisicaoPessoalDTO.ACAO_ANALISE)) {
            if (requisicaoPessoalDto.getRejeitada() != null && requisicaoPessoalDto.getRejeitada().equalsIgnoreCase("S") && requisicaoPessoalDto.getIdJustificativaValidacao() == null)
                throw new LogicException("Justificativa não informada");
        }
        if (acao.equalsIgnoreCase(RequisicaoPessoalDTO.ACAO_TRIAGEM)) {
            if (requisicaoPessoalDto.getColTriagem() == null || requisicaoPessoalDto.getColTriagem().size() == 0)
                throw new LogicException("Currículos não informados");
        }
        if (acao.equalsIgnoreCase(RequisicaoPessoalDTO.ACAO_ENTREVISTA_RH) || acao.equalsIgnoreCase(RequisicaoPessoalDTO.ACAO_ENTREVISTA_GESTOR)) {
        	Collection<TriagemRequisicaoPessoalDTO> colTriagens = new TriagemRequisicaoPessoalServiceEjb().findByIdSolicitacaoServicoAndIdTarefa(solicitacaoServicoDto.getIdSolicitacaoServico(), solicitacaoServicoDto.getIdTarefa());
        	if (colTriagens != null) {
        		String tipo = TipoEntrevista.RH.name();
        		if (acao.equalsIgnoreCase(RequisicaoPessoalDTO.ACAO_ENTREVISTA_GESTOR))
        			tipo = TipoEntrevista.Gestor.name();
        		EntrevistaCandidatoDao entrevistaDao = new EntrevistaCandidatoDao();
        		for (TriagemRequisicaoPessoalDTO triagemDto : colTriagens) {
					EntrevistaCandidatoDTO entrevistaDto = entrevistaDao.findByIdTriagemAndIdCurriculo(triagemDto.getIdTriagem(), triagemDto.getIdCurriculo());
					if (requisicaoPessoalDto.getAcaoManterGravarTarefa().equalsIgnoreCase("E")) {
						if (entrevistaDto == null)
			                throw new LogicException("Entrevista(s) não realizada(s)");
					}
				}
        	}
        }
	}

	@Override
	public void validaDelete(SolicitacaoServicoDTO solicitacaoServicoDto,IDto model) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void validaUpdate(SolicitacaoServicoDTO solicitacaoServicoDto,IDto model) throws Exception {
		RequisicaoPessoalDTO requisicaoPessoalDto = (RequisicaoPessoalDTO) model;
		if (solicitacaoServicoDto != null && solicitacaoServicoDto.getAcaoFluxo() != null) {
			requisicaoPessoalDto.setAcaoManterGravarTarefa(solicitacaoServicoDto.getAcaoFluxo());
		}
		validaAtualizacao(solicitacaoServicoDto, requisicaoPessoalDto);
	}

	@Override
	public IDto create(TransactionControler tc,SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		validaCreate(solicitacaoServicoDto, model);
		
		//Instancia Objeto controlador de transacao
		RequisicaoPessoalDao requisicaoPessoalDao = new RequisicaoPessoalDao();
		RequisicaoFormacaoAcademicaDao requisicaoFormacaoAcademicaDao = new RequisicaoFormacaoAcademicaDao();
		RequisicaoCertificacaoDao requisicaoCertificacaoDao = new RequisicaoCertificacaoDao();
		RequisicaoCursoDao requisicaoCursoDao = new RequisicaoCursoDao();
		RequisicaoExperienciaInformaticaDao requisicaoExperienciaInformaticaDao = new RequisicaoExperienciaInformaticaDao();
		RequisicaoIdiomaDao requisicaoIdiomaDao = new RequisicaoIdiomaDao();
		RequisicaoExperienciaAnteriorDao requisicaoExperienciaAnteriorDao = new RequisicaoExperienciaAnteriorDao();
		RequisicaoConhecimentoDao requisicaoConhecimentoDao = new RequisicaoConhecimentoDao();
		RequisicaoHabilidadeDao requisicaoHabilidadeDao = new RequisicaoHabilidadeDao();
		RequisicaoAtitudeIndividualDao requisicaoAtitudeIndividualDao = new RequisicaoAtitudeIndividualDao();
		
		try{
			
			//Seta o TransactionController para os DAOs
			requisicaoPessoalDao.setTransactionControler(tc);
			requisicaoFormacaoAcademicaDao.setTransactionControler(tc);
			requisicaoCertificacaoDao.setTransactionControler(tc);
			requisicaoCursoDao.setTransactionControler(tc);
			requisicaoExperienciaInformaticaDao.setTransactionControler(tc);
			requisicaoIdiomaDao.setTransactionControler(tc);
			requisicaoExperienciaAnteriorDao.setTransactionControler(tc);
			requisicaoConhecimentoDao.setTransactionControler(tc);
			requisicaoHabilidadeDao.setTransactionControler(tc);
			requisicaoAtitudeIndividualDao.setTransactionControler(tc);
			
			//Inicia transacao
			tc.start();
			
			//Executa operacoes pertinentes ao negocio.
			model = requisicaoPessoalDao.create(model);
			RequisicaoPessoalDTO requisicaoPessoalDto = (RequisicaoPessoalDTO)model;

			if (requisicaoPessoalDto.getColFormacaoAcademica() !=null){
				for (RequisicaoFormacaoAcademicaDTO requisicaoFormacaoAcademicaDto : requisicaoPessoalDto.getColFormacaoAcademica()) {
					requisicaoFormacaoAcademicaDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
					requisicaoFormacaoAcademicaDao.create(requisicaoFormacaoAcademicaDto);
				}
			}	
			
			if (requisicaoPessoalDto.getColCertificacao() !=null){
				for (RequisicaoCertificacaoDTO requisicaoCertificacaoDto : requisicaoPessoalDto.getColCertificacao()) {
					requisicaoCertificacaoDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
					requisicaoCertificacaoDao.create(requisicaoCertificacaoDto);
				}
			}	
			
			if (requisicaoPessoalDto.getColCurso() !=null){
				for (RequisicaoCursoDTO requisicaoCursoDto : requisicaoPessoalDto.getColCurso()) {
					requisicaoCursoDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
					requisicaoCursoDao.create(requisicaoCursoDto);
				}
			}	
            
			if (requisicaoPessoalDto.getColExperienciaInformatica() !=null){
				for (RequisicaoExperienciaInformaticaDTO requisicaoExperienciaInformaticaDto : requisicaoPessoalDto.getColExperienciaInformatica()) {
					requisicaoExperienciaInformaticaDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
					requisicaoExperienciaInformaticaDao.create(requisicaoExperienciaInformaticaDto);
				}
			}	
			
			if (requisicaoPessoalDto.getColIdioma() !=null){
				for (RequisicaoIdiomaDTO requisicaoIdiomaDto : requisicaoPessoalDto.getColIdioma()) {
					requisicaoIdiomaDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
					requisicaoIdiomaDao.create(requisicaoIdiomaDto);
				}
			}
			
			if (requisicaoPessoalDto.getColExperienciaAnterior() !=null){
				for (RequisicaoExperienciaAnteriorDTO requisicaoExperienciaAnteriorDto : requisicaoPessoalDto.getColExperienciaAnterior()) {
					requisicaoExperienciaAnteriorDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
					requisicaoExperienciaAnteriorDao.create(requisicaoExperienciaAnteriorDto);
				}
			}
			
			if (requisicaoPessoalDto.getColConhecimento() !=null){
				for (RequisicaoConhecimentoDTO requisicaoConhecimentoDto : requisicaoPessoalDto.getColConhecimento()) {
					requisicaoConhecimentoDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
					requisicaoConhecimentoDao.create(requisicaoConhecimentoDto);
				}
			}
			
			if (requisicaoPessoalDto.getColHabilidade() !=null){
				for (RequisicaoHabilidadeDTO requisicaoHabilidadeDto : requisicaoPessoalDto.getColHabilidade()) {
					requisicaoHabilidadeDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
					requisicaoHabilidadeDao.create(requisicaoHabilidadeDto);
				}
			}
			
			if (requisicaoPessoalDto.getColAtitudeIndividual() !=null){
				for (RequisicaoAtitudeIndividualDTO requisicaoAtitudeIndividualDto : requisicaoPessoalDto.getColAtitudeIndividual()) {
					requisicaoAtitudeIndividualDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
					requisicaoAtitudeIndividualDao.create(requisicaoAtitudeIndividualDto);
				}
			}
			//Faz commit e fecha a transacao.
			tc.commit();
			tc.close();

			return model;
		}catch(Exception e){
			this.rollbackTransaction(tc, e);
		}
		return model;
	}

	@Override
	public void update(TransactionControler tc,SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		validaUpdate(solicitacaoServicoDto, model);
		

		//Instancia Objeto controlador de transacao
		RequisicaoPessoalDao requisicaoPessoalDao = new RequisicaoPessoalDao();
		RequisicaoFormacaoAcademicaDao requisicaoFormacaoAcademicaDao = new RequisicaoFormacaoAcademicaDao();
		RequisicaoCertificacaoDao requisicaoCertificacaoDao = new RequisicaoCertificacaoDao();
		RequisicaoCursoDao requisicaoCursoDao = new RequisicaoCursoDao();
		RequisicaoExperienciaInformaticaDao requisicaoExperienciaInformaticaDao = new RequisicaoExperienciaInformaticaDao();
		RequisicaoIdiomaDao requisicaoIdiomaDao = new RequisicaoIdiomaDao();
		RequisicaoExperienciaAnteriorDao requisicaoExperienciaAnteriorDao = new RequisicaoExperienciaAnteriorDao();
		RequisicaoConhecimentoDao requisicaoConhecimentoDao = new RequisicaoConhecimentoDao();
		RequisicaoHabilidadeDao requisicaoHabilidadeDao = new RequisicaoHabilidadeDao();
		RequisicaoAtitudeIndividualDao requisicaoAtitudeIndividualDao = new RequisicaoAtitudeIndividualDao();
		TriagemRequisicaoPessoalDao triagemRequisicaoPessoalDao = new TriagemRequisicaoPessoalDao();
		RequisicaoPessoalService requisicaoPessoalService = (RequisicaoPessoalService) ServiceLocator.getInstance().getService(RequisicaoPessoalService.class, null);
		
		try{
		
			//Seta o TransactionController para os DAOs
			requisicaoPessoalDao.setTransactionControler(tc);
			requisicaoPessoalDao.setTransactionControler(tc);
			requisicaoFormacaoAcademicaDao.setTransactionControler(tc);
			requisicaoCertificacaoDao.setTransactionControler(tc);
			requisicaoCursoDao.setTransactionControler(tc);
			requisicaoExperienciaInformaticaDao.setTransactionControler(tc);
			requisicaoIdiomaDao.setTransactionControler(tc);
			requisicaoExperienciaAnteriorDao.setTransactionControler(tc);
			requisicaoConhecimentoDao.setTransactionControler(tc);
			requisicaoHabilidadeDao.setTransactionControler(tc);
			requisicaoAtitudeIndividualDao.setTransactionControler(tc);
			triagemRequisicaoPessoalDao.setTransactionControler(tc);
			
			//Inicia transacao
			tc.start();
			
			//Executa operacoes pertinentes ao negocio.
			RequisicaoPessoalDTO requisicaoPessoalDto = (RequisicaoPessoalDTO)model;
			
//			if(requisicaoPessoalDto.getPreRequisitoEntrevistaGestor() == null || requisicaoPessoalDto.getPreRequisitoEntrevistaGestor().equals("")){
//				requisicaoPessoalDto = (RequisicaoPessoalDTO) requisicaoPessoalService.restore(requisicaoPessoalDto);
//			}
			
			if (requisicaoPessoalDto != null && requisicaoPessoalDto.getIdSolicitacaoServico() != null && requisicaoPessoalDto.getIdCargo() == null) {
				requisicaoPessoalDto = (RequisicaoPessoalDTO) requisicaoPessoalService.restore(requisicaoPessoalDto);
				model = requisicaoPessoalDto;
			}

			if (requisicaoPessoalDto != null && requisicaoPessoalDto.getAcao() != null && requisicaoPessoalDto.getAcao().equals(RequisicaoPessoalDTO.ACAO_ANALISE)) {
				
		        ParecerServiceEjb parecerService = new ParecerServiceEjb();
		        
		        String aprovado = "S";
		        
		        if (requisicaoPessoalDto.getRejeitada().equalsIgnoreCase("S")) {
		        	
		        	aprovado = "N";
		        }
		        
		        /*
		         * Se está marcando como aprovado
		         * então limpa uma possível justificativa de Não aprovada.
		         * uelen.pereira
		         */
		        if (aprovado.equalsIgnoreCase("S")) {
		        	
		        	requisicaoPessoalDto.setIdJustificativaValidacao(null);
		        	requisicaoPessoalDto.setComplemJustificativaValidacao("");
		        }
		        
	            ParecerDTO parecerDto = parecerService.createOrUpdate(tc, requisicaoPessoalDto.getIdParecerValidacao(), solicitacaoServicoDto.getUsuarioDto(), requisicaoPessoalDto.getIdJustificativaValidacao(), requisicaoPessoalDto.getComplemJustificativaValidacao(), aprovado);
	            requisicaoPessoalDto.setIdParecerValidacao(parecerDto.getIdParecer());
	            
			} else if (requisicaoPessoalDto != null && requisicaoPessoalDto.getAcao() != null && requisicaoPessoalDto.getAcao().equals(RequisicaoPessoalDTO.ACAO_CRIACAO)) {
				
				requisicaoFormacaoAcademicaDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
				
				if (requisicaoPessoalDto.getColFormacaoAcademica() !=null){
					for (RequisicaoFormacaoAcademicaDTO requisicaoFormacaoAcademicaDto : requisicaoPessoalDto.getColFormacaoAcademica()) {
						requisicaoFormacaoAcademicaDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
						requisicaoFormacaoAcademicaDao.create(requisicaoFormacaoAcademicaDto);
					}
				}	
	
				requisicaoCertificacaoDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
				if (requisicaoPessoalDto.getColCertificacao() !=null){
					for (RequisicaoCertificacaoDTO requisicaoCertificacaoDto : requisicaoPessoalDto.getColCertificacao()) {
						requisicaoCertificacaoDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
						requisicaoCertificacaoDao.create(requisicaoCertificacaoDto);
					}
				}	
				
				requisicaoCursoDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
				if (requisicaoPessoalDto.getColCurso() !=null){
					for (RequisicaoCursoDTO requisicaoCursoDto : requisicaoPessoalDto.getColCurso()) {
						requisicaoCursoDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
						requisicaoCursoDao.create(requisicaoCursoDto);
					}
				}
				
				requisicaoExperienciaInformaticaDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
				if (requisicaoPessoalDto.getColExperienciaInformatica() !=null){
					for (RequisicaoExperienciaInformaticaDTO requisicaoExperienciaInformaticaDto : requisicaoPessoalDto.getColExperienciaInformatica()) {
						requisicaoExperienciaInformaticaDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
						requisicaoExperienciaInformaticaDao.create(requisicaoExperienciaInformaticaDto);
					}
				}	
				
				requisicaoIdiomaDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
				if (requisicaoPessoalDto.getColIdioma() !=null){
					for (RequisicaoIdiomaDTO requisicaoIdiomaDto : requisicaoPessoalDto.getColIdioma()) {
						requisicaoIdiomaDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
						requisicaoIdiomaDao.create(requisicaoIdiomaDto);
					}
				}	
				
				requisicaoExperienciaAnteriorDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
				if (requisicaoPessoalDto.getColExperienciaAnterior() !=null){
					for (RequisicaoExperienciaAnteriorDTO requisicaoExperienciaAnteriorDto : requisicaoPessoalDto.getColExperienciaAnterior()) {
						requisicaoExperienciaAnteriorDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
						requisicaoExperienciaAnteriorDao.create(requisicaoExperienciaAnteriorDto);
					}
				}
				
				requisicaoConhecimentoDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
				if (requisicaoPessoalDto.getColConhecimento() !=null){
					for (RequisicaoConhecimentoDTO requisicaoConhecimentoDto : requisicaoPessoalDto.getColConhecimento()) {
						requisicaoConhecimentoDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
						requisicaoConhecimentoDao.create(requisicaoConhecimentoDto);
					}
				}	
				
				requisicaoHabilidadeDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
				if (requisicaoPessoalDto.getColHabilidade() !=null){
					for (RequisicaoHabilidadeDTO requisicaoHabilidadeDto : requisicaoPessoalDto.getColHabilidade()) {
						requisicaoHabilidadeDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
						requisicaoHabilidadeDao.create(requisicaoHabilidadeDto);
					}
				}	
				
				requisicaoAtitudeIndividualDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
				if (requisicaoPessoalDto.getColAtitudeIndividual() !=null){
					for (RequisicaoAtitudeIndividualDTO requisicaoAtitudeIndividualDto : requisicaoPessoalDto.getColAtitudeIndividual()) {
						requisicaoAtitudeIndividualDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
						requisicaoAtitudeIndividualDao.create(requisicaoAtitudeIndividualDto);
					}
				}	
			}else if (requisicaoPessoalDto != null && requisicaoPessoalDto.getAcao() != null && requisicaoPessoalDto.getAcao().equals(RequisicaoPessoalDTO.ACAO_TRIAGEM)) {
				triagemRequisicaoPessoalDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
				if (requisicaoPessoalDto.getColTriagem() !=null){
					for (TriagemRequisicaoPessoalDTO triagemRequisicaoPessoalDto : requisicaoPessoalDto.getColTriagem()) {
						triagemRequisicaoPessoalDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
						triagemRequisicaoPessoalDao.create(triagemRequisicaoPessoalDto);
					}
				}	
			}
			requisicaoPessoalDao.update(model);
			
			//Faz commit e fecha a transacao.
			tc.commit();
			tc.close();
		}catch(Exception e){
			this.rollbackTransaction(tc, e);
		}
		
	}

	@Override
	public void delete(TransactionControler tc,SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		
		RequisicaoPessoalDao requisicaoPessoalDao = new RequisicaoPessoalDao();
		RequisicaoFormacaoAcademicaDao requisicaoFormacaoAcademicaDao = new RequisicaoFormacaoAcademicaDao();
		RequisicaoCertificacaoDao requisicaoCertificacaoDao = new RequisicaoCertificacaoDao();
		RequisicaoCursoDao requisicaoCursoDao = new RequisicaoCursoDao();
		RequisicaoExperienciaInformaticaDao requisicaoExperienciaInformaticaDao = new RequisicaoExperienciaInformaticaDao();
		RequisicaoIdiomaDao requisicaoIdiomaDao = new RequisicaoIdiomaDao();
		RequisicaoExperienciaAnteriorDao requisicaoExperienciaAnteriorDao = new RequisicaoExperienciaAnteriorDao();
		RequisicaoConhecimentoDao requisicaoConhecimentoDao = new RequisicaoConhecimentoDao();
		RequisicaoHabilidadeDao requisicaoHabilidadeDao = new RequisicaoHabilidadeDao();
		RequisicaoAtitudeIndividualDao requisicaoAtitudeIndividualDao = new RequisicaoAtitudeIndividualDao();
		
		try{
			//Faz validacao, caso exista.
			validaDelete(solicitacaoServicoDto, model);
			
			//Instancia ou obtem os DAOs necessarios.
			
			
			//Seta o TransactionController para os DAOs
			requisicaoPessoalDao.setTransactionControler(tc);
			requisicaoFormacaoAcademicaDao.setTransactionControler(tc);
			requisicaoCertificacaoDao.setTransactionControler(tc);
			requisicaoCursoDao.setTransactionControler(tc);
			requisicaoExperienciaInformaticaDao.setTransactionControler(tc);
			requisicaoIdiomaDao.setTransactionControler(tc);
			requisicaoExperienciaAnteriorDao.setTransactionControler(tc);
			requisicaoConhecimentoDao.setTransactionControler(tc);
			requisicaoHabilidadeDao.setTransactionControler(tc);
			requisicaoAtitudeIndividualDao.setTransactionControler(tc);
			
			//Inicia transacao
			tc.start();
			
			RequisicaoPessoalDTO requisicaoPessoalDto = (RequisicaoPessoalDTO)model;

			requisicaoFormacaoAcademicaDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
			requisicaoCertificacaoDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
			requisicaoCursoDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
			requisicaoExperienciaInformaticaDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
			requisicaoIdiomaDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
			requisicaoExperienciaAnteriorDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
			requisicaoConhecimentoDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
			requisicaoHabilidadeDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
			requisicaoAtitudeIndividualDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
			requisicaoPessoalDao.delete(requisicaoPessoalDto);
			
			//Faz commit e fecha a transacao.
			tc.commit();
			tc.close();
		}catch(Exception e){
			this.rollbackTransaction(tc, e);
		}
	}
	
	@Override
	public IDto restore(IDto model) throws ServiceException {
		RequisicaoPessoalDTO requisicaoPessoalDto = null;
		try {
			requisicaoPessoalDto = (RequisicaoPessoalDTO) super.restore(model);
			CargosDTO cargosDto = new CargosDTO();
			CargosService cargosService = (CargosService) ServiceLocator.getInstance().getService(CargosService.class, null);
			if(requisicaoPessoalDto != null){
				cargosDto.setIdCargo(requisicaoPessoalDto.getIdCargo());
				cargosDto = (CargosDTO) cargosService.restore(cargosDto);
			
				DescricaoCargoDTO descricaoCargoDto = new DescricaoCargoDTO();
				descricaoCargoDto.setIdDescricaoCargo(cargosDto.getIdDescricaoCargo());
	          
				DescricaoCargoService descricaoCargoService = (DescricaoCargoService) ServiceLocator.getInstance().getService(DescricaoCargoService.class, null);
				if(descricaoCargoDto.getIdDescricaoCargo() != null){
		        	descricaoCargoDto = (DescricaoCargoDTO) descricaoCargoService.restore(descricaoCargoDto);
		        	 requisicaoPessoalDto.setAtividades(descricaoCargoDto.getAtividades());
		 	        requisicaoPessoalDto.setObservacoes(descricaoCargoDto.getObservacoes());
				}
	        
				if (requisicaoPessoalDto.getSalario() == null || requisicaoPessoalDto.getSalario().doubleValue() == 0)
					requisicaoPessoalDto.setSalarioACombinar("S");
	        
				recuperaRelacionamentos(requisicaoPessoalDto);

			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
		return requisicaoPessoalDto;
	}	
	
	/**
	 * Desenvolvedor: David Rodrigues - Data: 26/11/2013 - Horário: 15:58 - ID Citsmart: 0 
	 * 
	 * Motivo/Comentário: Tratamento de NullPointerException
	 *  	
	 */

	public void recuperaRelacionamentos(RequisicaoPessoalDTO requisicaoPessoalDto) throws ServiceException, LogicException {
		try {
			Collection<RequisicaoFormacaoAcademicaDTO> colFormacaoAcademica = new RequisicaoFormacaoAcademicaDao().findByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
			if (colFormacaoAcademica != null) {
				FormacaoAcademicaDao formacaoAcademicaDao = new FormacaoAcademicaDao();
				for (RequisicaoFormacaoAcademicaDTO requisicaoFormacaoAcademicaDto : colFormacaoAcademica) {
					FormacaoAcademicaDTO formacaoAcademicaDto = new FormacaoAcademicaDTO();
					formacaoAcademicaDto.setIdFormacaoAcademica(requisicaoFormacaoAcademicaDto.getIdFormacaoAcademica());
					formacaoAcademicaDto = (FormacaoAcademicaDTO) formacaoAcademicaDao.restore(formacaoAcademicaDto);
					if(formacaoAcademicaDto != null){
						requisicaoFormacaoAcademicaDto.setDescricao(formacaoAcademicaDto.getDescricao());
						requisicaoFormacaoAcademicaDto.setDetalhe(formacaoAcademicaDto.getDetalhe());
					}
				}
				requisicaoPessoalDto.setColFormacaoAcademica(colFormacaoAcademica);
			}
			
			Collection<RequisicaoCertificacaoDTO> colCertificacao = new RequisicaoCertificacaoDao().findByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
			if (colCertificacao != null) {
				CertificacaoDao certificacaoDao = new CertificacaoDao();
				for (RequisicaoCertificacaoDTO requisicaoCertificacaoDto : colCertificacao) {
					CertificacaoDTO certificacaoDto = new CertificacaoDTO();
					certificacaoDto.setIdCertificacao(requisicaoCertificacaoDto.getIdCertificacao());
					certificacaoDto = (CertificacaoDTO) certificacaoDao.restore(certificacaoDto);
					if(certificacaoDto != null){
						requisicaoCertificacaoDto.setDescricao(certificacaoDto.getDescricao());
						requisicaoCertificacaoDto.setDetalhe(certificacaoDto.getDetalhe());
					}
				}
				requisicaoPessoalDto.setColCertificacao(colCertificacao);
			}
			
			Collection<RequisicaoCursoDTO> colCurso = new RequisicaoCursoDao().findByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
			if (colCurso != null) {
				CursoDao cursoDao = new CursoDao();
				for (RequisicaoCursoDTO requisicaoCursoDto : colCurso) {
					CursoDTO cursoDto = new CursoDTO();
					cursoDto.setIdCurso(requisicaoCursoDto.getIdCurso());
					cursoDto = (CursoDTO) cursoDao.restore(cursoDto);
					if(cursoDto != null){
						requisicaoCursoDto.setDescricao(cursoDto.getDescricao());
						requisicaoCursoDto.setDetalhe(cursoDto.getDetalhe());
					}
				}
				requisicaoPessoalDto.setColCurso(colCurso);
			}
			
			Collection<RequisicaoExperienciaInformaticaDTO> colExperienciaInformatica = new RequisicaoExperienciaInformaticaDao().findByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
			if (colExperienciaInformatica != null) {
				ExperienciaInformaticaDao experienciaInformaticaDao = new ExperienciaInformaticaDao();
				for (RequisicaoExperienciaInformaticaDTO requisicaoExperienciaInformaticaDto : colExperienciaInformatica) {
					ExperienciaInformaticaDTO experienciaInformaticaDto = new ExperienciaInformaticaDTO();
					experienciaInformaticaDto.setIdExperienciaInformatica(requisicaoExperienciaInformaticaDto.getIdExperienciaInformatica());
					experienciaInformaticaDto = (ExperienciaInformaticaDTO) experienciaInformaticaDao.restore(experienciaInformaticaDto);
					if(experienciaInformaticaDto != null){
						requisicaoExperienciaInformaticaDto.setDescricao(experienciaInformaticaDto.getDescricao());
						requisicaoExperienciaInformaticaDto.setDetalhe(experienciaInformaticaDto.getDetalhe());
					}
				}
				requisicaoPessoalDto.setColExperienciaInformatica(colExperienciaInformatica);
			}
			
			Collection<RequisicaoIdiomaDTO> colIdioma = new RequisicaoIdiomaDao().findByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
			if (colIdioma != null) {
				IdiomaDao idiomaDao = new IdiomaDao();
				for (RequisicaoIdiomaDTO requisicaoIdiomaDto : colIdioma) {
					IdiomaDTO idiomaDto = new IdiomaDTO();
					idiomaDto.setIdIdioma(requisicaoIdiomaDto.getIdIdioma());
					idiomaDto = (IdiomaDTO) idiomaDao.restore(idiomaDto);
					if(idiomaDto != null){
						requisicaoIdiomaDto.setDescricao(idiomaDto.getDescricao());
						requisicaoIdiomaDto.setDetalhe(idiomaDto.getDetalhe());
					}
				}
				requisicaoPessoalDto.setColIdioma(colIdioma);
			}
			
			Collection<RequisicaoExperienciaAnteriorDTO> colExperienciaAnterior = new RequisicaoExperienciaAnteriorDao().findByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
			if (colExperienciaAnterior != null) {
				ConhecimentoDao conhecimentoDao = new ConhecimentoDao();
				for (RequisicaoExperienciaAnteriorDTO requisicaoExperienciaAnteriorDto : colExperienciaAnterior) {
					ConhecimentoDTO conhecimentoDto = new ConhecimentoDTO();
					conhecimentoDto.setIdConhecimento(requisicaoExperienciaAnteriorDto.getIdConhecimento());
					conhecimentoDto = (ConhecimentoDTO) conhecimentoDao.restore(conhecimentoDto);
					if(conhecimentoDto != null){
						requisicaoExperienciaAnteriorDto.setDescricao(conhecimentoDto.getDescricao());
						requisicaoExperienciaAnteriorDto.setDetalhe(conhecimentoDto.getDetalhe());
					}
				}
				requisicaoPessoalDto.setColExperienciaAnterior(colExperienciaAnterior);
			}
			
			Collection<RequisicaoConhecimentoDTO> colConhecimento = new RequisicaoConhecimentoDao().findByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
			if (colConhecimento != null) {
				ConhecimentoDao conhecimentoDao = new ConhecimentoDao();
				for (RequisicaoConhecimentoDTO requisicaoConhecimentoDto : colConhecimento) {
					ConhecimentoDTO conhecimentoDto = new ConhecimentoDTO();
					conhecimentoDto.setIdConhecimento(requisicaoConhecimentoDto.getIdConhecimento());
					conhecimentoDto = (ConhecimentoDTO) conhecimentoDao.restore(conhecimentoDto);
					if(requisicaoConhecimentoDto != null){
						requisicaoConhecimentoDto.setDescricao(conhecimentoDto.getDescricao());
						requisicaoConhecimentoDto.setDetalhe(conhecimentoDto.getDetalhe());
					}
				}
				requisicaoPessoalDto.setColConhecimento(colConhecimento);
			}
			
			Collection<RequisicaoHabilidadeDTO> colHabilidade = new RequisicaoHabilidadeDao().findByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
			if (colHabilidade != null) {
				HabilidadeDao habilidadeDao = new HabilidadeDao();
				for (RequisicaoHabilidadeDTO requisicaoHabilidadeDto : colHabilidade) {
					HabilidadeDTO habilidadeDto = new HabilidadeDTO();
					habilidadeDto.setIdHabilidade(requisicaoHabilidadeDto.getIdHabilidade());
					habilidadeDto = (HabilidadeDTO) habilidadeDao.restore(habilidadeDto);
					if(habilidadeDto != null){
						requisicaoHabilidadeDto.setDescricao(habilidadeDto.getDescricao());
						requisicaoHabilidadeDto.setDetalhe(habilidadeDto.getDetalhe());
					}
				}
				requisicaoPessoalDto.setColHabilidade(colHabilidade);
			}
			
			Collection<RequisicaoAtitudeIndividualDTO> colAtitudeIndividual = new RequisicaoAtitudeIndividualDao().findByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
			if (colAtitudeIndividual != null) {
				AtitudeIndividualDao atitudeIndividualDao = new AtitudeIndividualDao();
				for (RequisicaoAtitudeIndividualDTO requisicaoAtitudeIndividualDto : colAtitudeIndividual) {
					AtitudeIndividualDTO atitudeIndividualDto = new AtitudeIndividualDTO();
					atitudeIndividualDto.setIdAtitudeIndividual(requisicaoAtitudeIndividualDto.getIdAtitudeIndividual());
					atitudeIndividualDto = (AtitudeIndividualDTO) atitudeIndividualDao.restore(atitudeIndividualDto);
					if(atitudeIndividualDto != null){
						requisicaoAtitudeIndividualDto.setDescricao(atitudeIndividualDto.getDescricao());
						requisicaoAtitudeIndividualDto.setDetalhe(atitudeIndividualDto.getDetalhe());
					}
				}
				requisicaoPessoalDto.setColAtitudeIndividual(colAtitudeIndividual);
			}
			
			if (requisicaoPessoalDto.getIdCidade() != null) {
				CidadesDTO cidadeDto = new CidadesDTO();
				cidadeDto.setIdCidade(requisicaoPessoalDto.getIdCidade());
				cidadeDto = (CidadesDTO) new CidadesDao().restore(cidadeDto);
				if (cidadeDto != null && cidadeDto.getIdUf() != null) {
					requisicaoPessoalDto.setIdUf(cidadeDto.getIdUf());
					UfDTO ufDto = new UfDao().findByIdUf(cidadeDto.getIdUf());
					if (ufDto != null)
						requisicaoPessoalDto.setIdPais(ufDto.getIdPais());
					else
						requisicaoPessoalDto.setIdPais(new Integer(1)); // retirar
				}
			}
			
			 
	            Collection colTriados = new TriagemRequisicaoPessoalDao().findByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
	            if(colTriados != null){
	            	requisicaoPessoalDto.setColTriagem(colTriados);
	            }
	            
		} catch (Exception e) {
			e.printStackTrace();
			throw new LogicException(e);
		}
	}
	
	@Override
	public void preparaSolicitacaoParaAprovacao(SolicitacaoServicoDTO solicitacaoDto, ItemTrabalho itemTrabalho, String aprovacao, Integer idJustificativa, String observacoes) throws Exception {

	}	
	
	@Override
	public String getInformacoesComplementaresFmtTexto(SolicitacaoServicoDTO solicitacaoDto, ItemTrabalho itemTrabalho) throws Exception {
		return solicitacaoDto.getDescricaoSemFormatacao();
	}	
}
