package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.CargosDTO;
import br.com.centralit.citcorpore.bean.ParecerDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.integracao.CargosDao;
import br.com.centralit.citcorpore.negocio.CargosService;
import br.com.centralit.citcorpore.negocio.ComplemInfSolicitacaoServicoServiceEjb;
import br.com.centralit.citcorpore.negocio.ParecerServiceEjb;
import br.com.centralit.citcorpore.rh.bean.AtitudeIndividualDTO;
import br.com.centralit.citcorpore.rh.bean.CargoAtitudeIndividualDTO;
import br.com.centralit.citcorpore.rh.bean.CargoCertificacaoDTO;
import br.com.centralit.citcorpore.rh.bean.CargoConhecimentoDTO;
import br.com.centralit.citcorpore.rh.bean.CargoCursoDTO;
import br.com.centralit.citcorpore.rh.bean.CargoExperienciaAnteriorDTO;
import br.com.centralit.citcorpore.rh.bean.CargoExperienciaInformaticaDTO;
import br.com.centralit.citcorpore.rh.bean.CargoFormacaoAcademicaDTO;
import br.com.centralit.citcorpore.rh.bean.CargoHabilidadeDTO;
import br.com.centralit.citcorpore.rh.bean.CargoIdiomaDTO;
import br.com.centralit.citcorpore.rh.bean.CertificacaoDTO;
import br.com.centralit.citcorpore.rh.bean.ConhecimentoDTO;
import br.com.centralit.citcorpore.rh.bean.CursoDTO;
import br.com.centralit.citcorpore.rh.bean.DescricaoCargoDTO;
import br.com.centralit.citcorpore.rh.bean.ExperienciaInformaticaDTO;
import br.com.centralit.citcorpore.rh.bean.FormacaoAcademicaDTO;
import br.com.centralit.citcorpore.rh.bean.HabilidadeDTO;
import br.com.centralit.citcorpore.rh.bean.IdiomaDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoPessoalDTO;
import br.com.centralit.citcorpore.rh.integracao.AtitudeIndividualDao;
import br.com.centralit.citcorpore.rh.integracao.CargoAtitudeIndividualDao;
import br.com.centralit.citcorpore.rh.integracao.CargoCertificacaoDao;
import br.com.centralit.citcorpore.rh.integracao.CargoConhecimentoDao;
import br.com.centralit.citcorpore.rh.integracao.CargoCursoDao;
import br.com.centralit.citcorpore.rh.integracao.CargoExperienciaAnteriorDao;
import br.com.centralit.citcorpore.rh.integracao.CargoExperienciaInformaticaDao;
import br.com.centralit.citcorpore.rh.integracao.CargoFormacaoAcademicaDao;
import br.com.centralit.citcorpore.rh.integracao.CargoHabilidadeDao;
import br.com.centralit.citcorpore.rh.integracao.CargoIdiomaDao;
import br.com.centralit.citcorpore.rh.integracao.CertificacaoDao;
import br.com.centralit.citcorpore.rh.integracao.ConhecimentoDao;
import br.com.centralit.citcorpore.rh.integracao.CursoDao;
import br.com.centralit.citcorpore.rh.integracao.DescricaoCargoDao;
import br.com.centralit.citcorpore.rh.integracao.ExperienciaInformaticaDao;
import br.com.centralit.citcorpore.rh.integracao.FormacaoAcademicaDao;
import br.com.centralit.citcorpore.rh.integracao.HabilidadeDao;
import br.com.centralit.citcorpore.rh.integracao.IdiomaDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;
import br.com.citframework.util.WebUtil;

@SuppressWarnings({"unchecked"})
public class DescricaoCargoServiceEjb extends ComplemInfSolicitacaoServicoServiceEjb implements DescricaoCargoService {

	private static final long serialVersionUID = 1L;

	protected CrudDAO getDao() throws ServiceException{ 
		return new DescricaoCargoDao(); 
	}
	
	protected void validaCreate(Object arg0) throws Exception{
	   validaColecoes((DescricaoCargoDTO) arg0);
	}
	
	protected void validaDelete(Object arg0) throws Exception{}
	
	protected void validaFind(Object arg0) throws Exception{}
	
	protected void validaUpdate(Object arg0) throws Exception{
		validaColecoes((DescricaoCargoDTO) arg0);
	}
	
	private void validaColecoes(DescricaoCargoDTO descricaoCargoDto) throws Exception{
		if (descricaoCargoDto.getColFormacaoAcademica() == null || descricaoCargoDto.getColFormacaoAcademica().isEmpty()) {
			throw new LogicException("É necessário ao menos uma formação acadêmica");
		}
		if (descricaoCargoDto.getColConhecimento() == null || descricaoCargoDto.getColConhecimento().isEmpty()) {
			throw new LogicException("É necessário ao menos um conhecimento");
		}
		if (descricaoCargoDto.getColHabilidade() == null || descricaoCargoDto.getColHabilidade().isEmpty()) {
			throw new LogicException("É necessário ao menos uma habilidade");
		}
		if (descricaoCargoDto.getColAtitudeIndividual() == null || descricaoCargoDto.getColAtitudeIndividual().isEmpty()) {
			throw new LogicException("É necessário ao menos uma atitude Individual");
		}
	}

	@Override
	public IDto restore(IDto model) throws ServiceException, LogicException {
		DescricaoCargoDTO descricaoCargoDto = (DescricaoCargoDTO) super.restore(model);
		recuperaRelacionamentos(descricaoCargoDto);
		return descricaoCargoDto;
	}	
	
	/**
	 * Desenvolvedor: David Rodrigues - Data: 26/11/2013 - Horário: 16:08 - ID Citsmart: 0 
	 * 
	 * Motivo/Comentário: Tratamento de NullPointerException
	 *  	
	 */
	
	public void recuperaRelacionamentos(DescricaoCargoDTO descricaoCargoDto) throws ServiceException, LogicException {
		try {
			Collection<CargoFormacaoAcademicaDTO> colFormacaoAcademica = new CargoFormacaoAcademicaDao().findByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
			if (colFormacaoAcademica != null) {
				FormacaoAcademicaDao formacaoAcademicaDao = new FormacaoAcademicaDao();
				for (CargoFormacaoAcademicaDTO cargoFormacaoAcademicaDto : colFormacaoAcademica) {
					FormacaoAcademicaDTO formacaoAcademicaDto = new FormacaoAcademicaDTO();
					formacaoAcademicaDto.setIdFormacaoAcademica(cargoFormacaoAcademicaDto.getIdFormacaoAcademica());
					formacaoAcademicaDto = (FormacaoAcademicaDTO) formacaoAcademicaDao.restore(formacaoAcademicaDto);
					if(formacaoAcademicaDto != null){
						cargoFormacaoAcademicaDto.setDescricao(formacaoAcademicaDto.getDescricao());
						cargoFormacaoAcademicaDto.setDetalhe(formacaoAcademicaDto.getDetalhe());
					}
				}
				descricaoCargoDto.setColFormacaoAcademica(colFormacaoAcademica);
			}
			
			Collection<CargoCertificacaoDTO> colCertificacao = new CargoCertificacaoDao().findByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
			if (colCertificacao != null) {
				CertificacaoDao certificacaoDao = new CertificacaoDao();
				for (CargoCertificacaoDTO cargoCertificacaoDto : colCertificacao) {
					CertificacaoDTO certificacaoDto = new CertificacaoDTO();
					certificacaoDto.setIdCertificacao(cargoCertificacaoDto.getIdCertificacao());
					certificacaoDto = (CertificacaoDTO) certificacaoDao.restore(certificacaoDto);
					if(certificacaoDto != null){
						cargoCertificacaoDto.setDescricao(certificacaoDto.getDescricao());
						cargoCertificacaoDto.setDetalhe(certificacaoDto.getDetalhe());
					}
				}
				descricaoCargoDto.setColCertificacao(colCertificacao);
			}
			
			Collection<CargoCursoDTO> colCurso = new CargoCursoDao().findByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
			if (colCurso != null) {
				CursoDao cursoDao = new CursoDao();
				for (CargoCursoDTO cargoCursoDto : colCurso) {
					CursoDTO cursoDto = new CursoDTO();
					cursoDto.setIdCurso(cargoCursoDto.getIdCurso());
					cursoDto = (CursoDTO) cursoDao.restore(cursoDto);
					if(cursoDto != null){
						cargoCursoDto.setDescricao(cursoDto.getDescricao());
						cargoCursoDto.setDetalhe(cursoDto.getDetalhe());
					}
				}
				descricaoCargoDto.setColCurso(colCurso);
			}
			
			Collection<CargoExperienciaInformaticaDTO> colExperienciaInformatica = new CargoExperienciaInformaticaDao().findByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
			if (colExperienciaInformatica != null) {
				ExperienciaInformaticaDao experienciaInformaticaDao = new ExperienciaInformaticaDao();
				for (CargoExperienciaInformaticaDTO cargoExperienciaInformaticaDto : colExperienciaInformatica) {
					ExperienciaInformaticaDTO experienciaInformaticaDto = new ExperienciaInformaticaDTO();
					experienciaInformaticaDto.setIdExperienciaInformatica(cargoExperienciaInformaticaDto.getIdExperienciaInformatica());
					experienciaInformaticaDto = (ExperienciaInformaticaDTO) experienciaInformaticaDao.restore(experienciaInformaticaDto);
					if(experienciaInformaticaDto != null){
						cargoExperienciaInformaticaDto.setDescricao(experienciaInformaticaDto.getDescricao());
						cargoExperienciaInformaticaDto.setDetalhe(experienciaInformaticaDto.getDetalhe());
					}
				}
				descricaoCargoDto.setColExperienciaInformatica(colExperienciaInformatica);
			}
			
			Collection<CargoIdiomaDTO> colIdioma = new CargoIdiomaDao().findByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
			if (colIdioma != null) {
				IdiomaDao idiomaDao = new IdiomaDao();
				for (CargoIdiomaDTO cargoIdiomaDto : colIdioma) {
					IdiomaDTO idiomaDto = new IdiomaDTO();
					idiomaDto.setIdIdioma(cargoIdiomaDto.getIdIdioma());
					idiomaDto = (IdiomaDTO) idiomaDao.restore(idiomaDto);
					if(idiomaDto != null){
						cargoIdiomaDto.setDescricao(idiomaDto.getDescricao());
						cargoIdiomaDto.setDetalhe(idiomaDto.getDetalhe());
					}
				}
				descricaoCargoDto.setColIdioma(colIdioma);
			}
			
			Collection<CargoExperienciaAnteriorDTO> colExperienciaAnterior = new CargoExperienciaAnteriorDao().findByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
			if (colExperienciaAnterior != null) {
				ConhecimentoDao conhecimentoDao = new ConhecimentoDao();
				for (CargoExperienciaAnteriorDTO cargoExperienciaAnteriorDto : colExperienciaAnterior) {
					ConhecimentoDTO conhecimentoDto = new ConhecimentoDTO();
					conhecimentoDto.setIdConhecimento(cargoExperienciaAnteriorDto.getIdConhecimento());
					conhecimentoDto = (ConhecimentoDTO) conhecimentoDao.restore(conhecimentoDto);
					if(conhecimentoDto != null){
						cargoExperienciaAnteriorDto.setDescricao(conhecimentoDto.getDescricao());
						cargoExperienciaAnteriorDto.setDetalhe(conhecimentoDto.getDetalhe());
					}
				}
				descricaoCargoDto.setColExperienciaAnterior(colExperienciaAnterior);
			}
			
			Collection<CargoConhecimentoDTO> colConhecimento = new CargoConhecimentoDao().findByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
			if (colConhecimento != null) {
				ConhecimentoDao conhecimentoDao = new ConhecimentoDao();
				for (CargoConhecimentoDTO cargoConhecimentoDto : colConhecimento) {
					ConhecimentoDTO conhecimentoDto = new ConhecimentoDTO();
					conhecimentoDto.setIdConhecimento(cargoConhecimentoDto.getIdConhecimento());
					conhecimentoDto = (ConhecimentoDTO) conhecimentoDao.restore(conhecimentoDto);
					if(conhecimentoDto != null){
						cargoConhecimentoDto.setDescricao(conhecimentoDto.getDescricao());
						cargoConhecimentoDto.setDetalhe(conhecimentoDto.getDetalhe());
					}
				}
				descricaoCargoDto.setColConhecimento(colConhecimento);
			}
			
			Collection<CargoHabilidadeDTO> colHabilidade = new CargoHabilidadeDao().findByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
			if (colHabilidade != null) {
				HabilidadeDao habilidadeDao = new HabilidadeDao();
				for (CargoHabilidadeDTO cargoHabilidadeDto : colHabilidade) {
					HabilidadeDTO habilidadeDto = new HabilidadeDTO();
					habilidadeDto.setIdHabilidade(cargoHabilidadeDto.getIdHabilidade());
					habilidadeDto = (HabilidadeDTO) habilidadeDao.restore(habilidadeDto);
					if(cargoHabilidadeDto != null){
						cargoHabilidadeDto.setDescricao(habilidadeDto.getDescricao());
						cargoHabilidadeDto.setDetalhe(habilidadeDto.getDetalhe());
					}
				}
				descricaoCargoDto.setColHabilidade(colHabilidade);
			}
			
			Collection<CargoAtitudeIndividualDTO> colAtitudeIndividual = new CargoAtitudeIndividualDao().findByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
			if (colAtitudeIndividual != null) {
				AtitudeIndividualDao atitudeIndividualDao = new AtitudeIndividualDao();
				for (CargoAtitudeIndividualDTO cargoAtitudeIndividualDto : colAtitudeIndividual) {
					AtitudeIndividualDTO atitudeIndividualDto = new AtitudeIndividualDTO();
					atitudeIndividualDto.setIdAtitudeIndividual(cargoAtitudeIndividualDto.getIdAtitudeIndividual());
					atitudeIndividualDto = (AtitudeIndividualDTO) atitudeIndividualDao.restore(atitudeIndividualDto);
					if (atitudeIndividualDto != null){
						cargoAtitudeIndividualDto.setDescricao(atitudeIndividualDto.getDescricao());
						cargoAtitudeIndividualDto.setDetalhe(atitudeIndividualDto.getDetalhe());
					}
				}
				descricaoCargoDto.setColAtitudeIndividual(colAtitudeIndividual);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new LogicException(e);
		}
	}
		@Override
	public IDto deserializaObjeto(String serialize) throws Exception {
	    DescricaoCargoDTO descricaoCargoDto = null;
	    
        if (serialize != null) {
            descricaoCargoDto = (DescricaoCargoDTO) WebUtil.deserializeObject(DescricaoCargoDTO.class, serialize);
            if (descricaoCargoDto != null && descricaoCargoDto.getSerializeFormacaoAcademica() != null)
                descricaoCargoDto.setColFormacaoAcademica(WebUtil.deserializeCollectionFromString(CargoFormacaoAcademicaDTO.class, descricaoCargoDto.getSerializeFormacaoAcademica()));
            if (descricaoCargoDto != null && descricaoCargoDto.getSerializeCertificacao() != null)
                descricaoCargoDto.setColCertificacao(WebUtil.deserializeCollectionFromString(CargoCertificacaoDTO.class, descricaoCargoDto.getSerializeCertificacao()));
            if (descricaoCargoDto != null && descricaoCargoDto.getSerializeCurso() != null)
                descricaoCargoDto.setColCurso(WebUtil.deserializeCollectionFromString(CargoCursoDTO.class, descricaoCargoDto.getSerializeCurso()));
            if (descricaoCargoDto != null && descricaoCargoDto.getSerializeExperienciaInformatica() != null)
                descricaoCargoDto.setColExperienciaInformatica(WebUtil.deserializeCollectionFromString(CargoExperienciaInformaticaDTO.class, descricaoCargoDto.getSerializeExperienciaInformatica()));
            if (descricaoCargoDto != null && descricaoCargoDto.getSerializeIdioma() != null)
                descricaoCargoDto.setColIdioma(WebUtil.deserializeCollectionFromString(CargoIdiomaDTO.class, descricaoCargoDto.getSerializeIdioma()));
            if (descricaoCargoDto != null && descricaoCargoDto.getSerializeExperienciaAnterior() != null)
                descricaoCargoDto.setColExperienciaAnterior(WebUtil.deserializeCollectionFromString(CargoExperienciaAnteriorDTO.class, descricaoCargoDto.getSerializeExperienciaAnterior()));
            if (descricaoCargoDto != null && descricaoCargoDto.getSerializeHabilidade() != null)
                descricaoCargoDto.setColHabilidade(WebUtil.deserializeCollectionFromString(CargoHabilidadeDTO.class, descricaoCargoDto.getSerializeHabilidade()));
            if (descricaoCargoDto != null && descricaoCargoDto.getSerializeAtitudeIndividual() != null)
                descricaoCargoDto.setColAtitudeIndividual(WebUtil.deserializeCollectionFromString(CargoAtitudeIndividualDTO.class, descricaoCargoDto.getSerializeAtitudeIndividual()));
            if (descricaoCargoDto != null && descricaoCargoDto.getSerializeConhecimento() != null)
                descricaoCargoDto.setColConhecimento(WebUtil.deserializeCollectionFromString(CargoConhecimentoDTO.class, descricaoCargoDto.getSerializeConhecimento()));
        }
        
        return descricaoCargoDto;
	}

	@Override
	public DescricaoCargoDTO findByIdSolicitacaoServico(Integer parm) throws Exception {
		DescricaoCargoDTO descricaoCargoDto = new DescricaoCargoDao().findByIdSolicitacaoServico(parm);
		if (descricaoCargoDto != null)
			recuperaRelacionamentos(descricaoCargoDto);
		return descricaoCargoDto;
	}

	@Override
	public void validaCreate(SolicitacaoServicoDTO solicitacaoServicoDto,IDto model) throws Exception {
		validaCreate(model);
		DescricaoCargoDTO descricaoCargoDto = (DescricaoCargoDTO) model;
		validaAtualizacao(solicitacaoServicoDto, descricaoCargoDto);
	}

	private void validaAtualizacao(SolicitacaoServicoDTO solicitacaoServicoDto, DescricaoCargoDTO descricaoCargoDto) throws Exception {
		descricaoCargoDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
		if (UtilStrings.nullToVazio(descricaoCargoDto.getAcao()).equalsIgnoreCase(RequisicaoPessoalDTO.ACAO_ANALISE)) {
            if (descricaoCargoDto.getSituacao() == null || descricaoCargoDto.getSituacao().trim().length() == 0)
                throw new LogicException("Parecer não informado");
            if (descricaoCargoDto.getSituacao().equalsIgnoreCase("X") && descricaoCargoDto.getIdJustificativaValidacao() == null)
                throw new LogicException("Justificativa não informada");
        }
	}

	@Override
	public void validaDelete(SolicitacaoServicoDTO solicitacaoServicoDto,IDto model) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void validaUpdate(SolicitacaoServicoDTO solicitacaoServicoDto,IDto model) throws Exception {
		DescricaoCargoDTO descricaoCargoDto = (DescricaoCargoDTO) model;
		validaAtualizacao(solicitacaoServicoDto, descricaoCargoDto);
	}

	@Override
	public IDto create(TransactionControler tc,SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		validaCreate(solicitacaoServicoDto, model);
		
		//Instancia Objeto controlador de transacao
		DescricaoCargoDao descricaoCargoDao = new DescricaoCargoDao();
		CargoFormacaoAcademicaDao cargoFormacaoAcademicaDao = new CargoFormacaoAcademicaDao();
		CargoCertificacaoDao cargoCertificacaoDao = new CargoCertificacaoDao();
		CargoCursoDao cargoCursoDao = new CargoCursoDao();
		CargoExperienciaInformaticaDao cargoExperienciaInformaticaDao = new CargoExperienciaInformaticaDao();
		CargoIdiomaDao cargoIdiomaDao = new CargoIdiomaDao();
		CargoExperienciaAnteriorDao cargoExperienciaAnteriorDao = new CargoExperienciaAnteriorDao();
		CargoConhecimentoDao cargoConhecimentoDao = new CargoConhecimentoDao();
		CargoHabilidadeDao cargoHabilidadeDao = new CargoHabilidadeDao();
		CargoAtitudeIndividualDao cargoAtitudeIndividualDao = new CargoAtitudeIndividualDao();
		
		try{
			
			//Seta o TransactionController para os DAOs
			descricaoCargoDao.setTransactionControler(tc);
			cargoFormacaoAcademicaDao.setTransactionControler(tc);
			cargoCertificacaoDao.setTransactionControler(tc);
			cargoCursoDao.setTransactionControler(tc);
			cargoExperienciaInformaticaDao.setTransactionControler(tc);
			cargoIdiomaDao.setTransactionControler(tc);
			cargoExperienciaAnteriorDao.setTransactionControler(tc);
			cargoConhecimentoDao.setTransactionControler(tc);
			cargoHabilidadeDao.setTransactionControler(tc);
			cargoAtitudeIndividualDao.setTransactionControler(tc);
			
			//Inicia transacao
			tc.start();
			
			//Executa operacoes pertinentes ao negocio.
			model = descricaoCargoDao.create(model);
			DescricaoCargoDTO descricaoCargoDto = (DescricaoCargoDTO)model;

			if (descricaoCargoDto.getColFormacaoAcademica() !=null){
				for (CargoFormacaoAcademicaDTO cargoFormacaoAcademicaDto : descricaoCargoDto.getColFormacaoAcademica()) {
					cargoFormacaoAcademicaDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
					cargoFormacaoAcademicaDao.create(cargoFormacaoAcademicaDto);
				}
			}	
			
			if (descricaoCargoDto.getColCertificacao() !=null){
				for (CargoCertificacaoDTO cargoCertificacaoDto : descricaoCargoDto.getColCertificacao()) {
					cargoCertificacaoDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
					cargoCertificacaoDao.create(cargoCertificacaoDto);
				}
			}	
			
			if (descricaoCargoDto.getColCurso() !=null){
				for (CargoCursoDTO cargoCursoDto : descricaoCargoDto.getColCurso()) {
					cargoCursoDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
					cargoCursoDao.create(cargoCursoDto);
				}
			}	
            
			if (descricaoCargoDto.getColExperienciaInformatica() !=null){
				for (CargoExperienciaInformaticaDTO cargoExperienciaInformaticaDto : descricaoCargoDto.getColExperienciaInformatica()) {
					cargoExperienciaInformaticaDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
					cargoExperienciaInformaticaDao.create(cargoExperienciaInformaticaDto);
				}
			}	
			
			if (descricaoCargoDto.getColIdioma() !=null){
				for (CargoIdiomaDTO cargoIdiomaDto : descricaoCargoDto.getColIdioma()) {
					cargoIdiomaDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
					cargoIdiomaDao.create(cargoIdiomaDto);
				}
			}
			
			if (descricaoCargoDto.getColExperienciaAnterior() !=null){
				for (CargoExperienciaAnteriorDTO cargoExperienciaAnteriorDto : descricaoCargoDto.getColExperienciaAnterior()) {
					cargoExperienciaAnteriorDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
					cargoExperienciaAnteriorDao.create(cargoExperienciaAnteriorDto);
				}
			}
			
			if (descricaoCargoDto.getColConhecimento() !=null){
				for (CargoConhecimentoDTO cargoConhecimentoDto : descricaoCargoDto.getColConhecimento()) {
					cargoConhecimentoDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
					cargoConhecimentoDao.create(cargoConhecimentoDto);
				}
			}
			
			if (descricaoCargoDto.getColHabilidade() !=null){
				for (CargoHabilidadeDTO cargoHabilidadeDto : descricaoCargoDto.getColHabilidade()) {
					cargoHabilidadeDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
					cargoHabilidadeDao.create(cargoHabilidadeDto);
				}
			}
			
			if (descricaoCargoDto.getColAtitudeIndividual() !=null){
				for (CargoAtitudeIndividualDTO cargoAtitudeIndividualDto : descricaoCargoDto.getColAtitudeIndividual()) {
					cargoAtitudeIndividualDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
					cargoAtitudeIndividualDao.create(cargoAtitudeIndividualDto);
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
		DescricaoCargoDao descricaoCargoDao = new DescricaoCargoDao();
		CargoFormacaoAcademicaDao cargoFormacaoAcademicaDao = new CargoFormacaoAcademicaDao();
		CargoCertificacaoDao cargoCertificacaoDao = new CargoCertificacaoDao();
		CargoCursoDao cargoCursoDao = new CargoCursoDao();
		CargoExperienciaInformaticaDao cargoExperienciaInformaticaDao = new CargoExperienciaInformaticaDao();
		CargoIdiomaDao cargoIdiomaDao = new CargoIdiomaDao();
		CargoExperienciaAnteriorDao cargoExperienciaAnteriorDao = new CargoExperienciaAnteriorDao();
		CargoConhecimentoDao cargoConhecimentoDao = new CargoConhecimentoDao();
		CargoHabilidadeDao cargoHabilidadeDao = new CargoHabilidadeDao();
		CargoAtitudeIndividualDao cargoAtitudeIndividualDao = new CargoAtitudeIndividualDao();
		CargosDao cargosDao = new CargosDao();
		
		try{
		
			//Seta o TransactionController para os DAOs
			descricaoCargoDao.setTransactionControler(tc);
			cargoFormacaoAcademicaDao.setTransactionControler(tc);
			cargoCertificacaoDao.setTransactionControler(tc);
			cargoCursoDao.setTransactionControler(tc);
			cargoExperienciaInformaticaDao.setTransactionControler(tc);
			cargoIdiomaDao.setTransactionControler(tc);
			cargoExperienciaAnteriorDao.setTransactionControler(tc);
			cargoConhecimentoDao.setTransactionControler(tc);
			cargoHabilidadeDao.setTransactionControler(tc);
			cargoAtitudeIndividualDao.setTransactionControler(tc);
			cargosDao.setTransactionControler(tc);
			
			//Inicia transacao
			tc.start();
			
			//Executa operacoes pertinentes ao negocio.
			descricaoCargoDao.update(model);
			DescricaoCargoDTO descricaoCargoDto = (DescricaoCargoDTO)model;
			
			if (descricaoCargoDto.getAcao().equals(RequisicaoPessoalDTO.ACAO_ANALISE)) {
				String aprovado = "N";
				if (descricaoCargoDto.getSituacao().equalsIgnoreCase("A"))
					aprovado = "S";
		        ParecerServiceEjb parecerService = new ParecerServiceEjb();
	            ParecerDTO parecerDto = parecerService.createOrUpdate(tc, descricaoCargoDto.getIdParecerValidacao(), solicitacaoServicoDto.getUsuarioDto(), descricaoCargoDto.getIdJustificativaValidacao(), descricaoCargoDto.getComplemJustificativaValidacao(), aprovado);
	            descricaoCargoDto.setIdParecerValidacao(parecerDto.getIdParecer());
	            if (descricaoCargoDto.getSituacao().equalsIgnoreCase("A")) {
	            	CargosDTO cargoDto = new CargosDTO();
	            	cargoDto.setNomeCargo(descricaoCargoDto.getNomeCargo());
	            	CargosService cargosService = (CargosService) ServiceLocator.getInstance().getService(CargosService.class, null);
	            	Collection<CargosDTO> col = cargosService.seCargoJaCadastrado(cargoDto);
		            	if (col == null || col.size() <= 0) {
		            	cargoDto.setDataInicio(UtilDatas.getDataAtual());
		            	cargoDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
		            	cargosDao.create(cargoDto);
	            	}
	            }
			}else if (descricaoCargoDto.getAcao().equals(RequisicaoPessoalDTO.ACAO_CRIACAO)) {
				cargoFormacaoAcademicaDao.deleteByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
				if (descricaoCargoDto.getColFormacaoAcademica() !=null){
					for (CargoFormacaoAcademicaDTO cargoFormacaoAcademicaDto : descricaoCargoDto.getColFormacaoAcademica()) {
						cargoFormacaoAcademicaDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
						cargoFormacaoAcademicaDao.create(cargoFormacaoAcademicaDto);
					}
				}	
	
				cargoCertificacaoDao.deleteByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
				if (descricaoCargoDto.getColCertificacao() !=null){
					for (CargoCertificacaoDTO cargoCertificacaoDto : descricaoCargoDto.getColCertificacao()) {
						cargoCertificacaoDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
						cargoCertificacaoDao.create(cargoCertificacaoDto);
					}
				}	
				
				cargoCursoDao.deleteByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
				if (descricaoCargoDto.getColCurso() !=null){
					for (CargoCursoDTO cargoCursoDto : descricaoCargoDto.getColCurso()) {
						cargoCursoDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
						cargoCursoDao.create(cargoCursoDto);
					}
				}
				
				cargoExperienciaInformaticaDao.deleteByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
				if (descricaoCargoDto.getColExperienciaInformatica() !=null){
					for (CargoExperienciaInformaticaDTO cargoExperienciaInformaticaDto : descricaoCargoDto.getColExperienciaInformatica()) {
						cargoExperienciaInformaticaDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
						cargoExperienciaInformaticaDao.create(cargoExperienciaInformaticaDto);
					}
				}	
				
				cargoIdiomaDao.deleteByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
				if (descricaoCargoDto.getColIdioma() !=null){
					for (CargoIdiomaDTO cargoIdiomaDto : descricaoCargoDto.getColIdioma()) {
						cargoIdiomaDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
						cargoIdiomaDao.create(cargoIdiomaDto);
					}
				}	
				
				cargoExperienciaAnteriorDao.deleteByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
				if (descricaoCargoDto.getColExperienciaAnterior() !=null){
					for (CargoExperienciaAnteriorDTO cargoExperienciaAnteriorDto : descricaoCargoDto.getColExperienciaAnterior()) {
						cargoExperienciaAnteriorDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
						cargoExperienciaAnteriorDao.create(cargoExperienciaAnteriorDto);
					}
				}
				
				cargoConhecimentoDao.deleteByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
				if (descricaoCargoDto.getColConhecimento() !=null){
					for (CargoConhecimentoDTO cargoConhecimentoDto : descricaoCargoDto.getColConhecimento()) {
						cargoConhecimentoDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
						cargoConhecimentoDao.create(cargoConhecimentoDto);
					}
				}	
				
				cargoHabilidadeDao.deleteByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
				if (descricaoCargoDto.getColHabilidade() !=null){
					for (CargoHabilidadeDTO cargoHabilidadeDto : descricaoCargoDto.getColHabilidade()) {
						cargoHabilidadeDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
						cargoHabilidadeDao.create(cargoHabilidadeDto);
					}
				}	
				
				cargoAtitudeIndividualDao.deleteByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
				if (descricaoCargoDto.getColAtitudeIndividual() !=null){
					for (CargoAtitudeIndividualDTO cargoAtitudeIndividualDto : descricaoCargoDto.getColAtitudeIndividual()) {
						cargoAtitudeIndividualDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
						cargoAtitudeIndividualDao.create(cargoAtitudeIndividualDto);
					}
				}
			}
			//Faz commit e fecha a transacao.
			tc.commit();
			tc.close();
		}catch(Exception e){
			this.rollbackTransaction(tc, e);
		}
		
	}

	@Override
	public void delete(TransactionControler tc,SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {

		DescricaoCargoDao descricaoCargoDao = new DescricaoCargoDao();		
		CargoFormacaoAcademicaDao cargoFormacaoAcademicaDao = new CargoFormacaoAcademicaDao();
		CargoCertificacaoDao cargoCertificacaoDao = new CargoCertificacaoDao();
		CargoCursoDao cargoCursoDao = new CargoCursoDao();
		CargoExperienciaInformaticaDao cargoExperienciaInformaticaDao = new CargoExperienciaInformaticaDao();
		CargoIdiomaDao cargoIdiomaDao = new CargoIdiomaDao();
		CargoExperienciaAnteriorDao cargoExperienciaAnteriorDao = new CargoExperienciaAnteriorDao();
		CargoConhecimentoDao cargoConhecimentoDao = new CargoConhecimentoDao();
		CargoHabilidadeDao cargoHabilidadeDao = new CargoHabilidadeDao();
		CargoAtitudeIndividualDao cargoAtitudeIndividualDao = new CargoAtitudeIndividualDao();
		
		try{
			//Faz validacao, caso exista.
			validaDelete(solicitacaoServicoDto, model);
			
			//Instancia ou obtem os DAOs necessarios.
			
			
			//Seta o TransactionController para os DAOs
			descricaoCargoDao.setTransactionControler(tc);
			cargoFormacaoAcademicaDao.setTransactionControler(tc);
			cargoCertificacaoDao.setTransactionControler(tc);
			cargoCursoDao.setTransactionControler(tc);
			cargoExperienciaInformaticaDao.setTransactionControler(tc);
			cargoIdiomaDao.setTransactionControler(tc);
			cargoExperienciaAnteriorDao.setTransactionControler(tc);
			cargoConhecimentoDao.setTransactionControler(tc);
			cargoHabilidadeDao.setTransactionControler(tc);
			cargoAtitudeIndividualDao.setTransactionControler(tc);
			
			//Inicia transacao
			tc.start();
			
			DescricaoCargoDTO descricaoCargoDto = (DescricaoCargoDTO)model;

			cargoFormacaoAcademicaDao.deleteByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
			cargoCertificacaoDao.deleteByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
			cargoCursoDao.deleteByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
			cargoExperienciaInformaticaDao.deleteByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
			cargoIdiomaDao.deleteByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
			cargoExperienciaAnteriorDao.deleteByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
			cargoConhecimentoDao.deleteByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
			cargoHabilidadeDao.deleteByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
			cargoAtitudeIndividualDao.deleteByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
			descricaoCargoDao.delete(descricaoCargoDto);
			
			//Faz commit e fecha a transacao.
			tc.commit();
			tc.close();
		}catch(Exception e){
			this.rollbackTransaction(tc, e);
		}
	}
	
}
