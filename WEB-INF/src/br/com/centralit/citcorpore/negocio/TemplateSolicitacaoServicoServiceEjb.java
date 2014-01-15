package br.com.centralit.citcorpore.negocio;
import br.com.centralit.bpm.dto.ElementoFluxoDTO;
import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.integracao.ElementoFluxoDao;
import br.com.centralit.bpm.integracao.TarefaFluxoDao;
import br.com.centralit.bpm.negocio.ItemTrabalho;
import br.com.centralit.bpm.util.Enumerados;
import br.com.centralit.citcorpore.bean.CategoriaProblemaDTO;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.TemplateSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.integracao.CategoriaProblemaDAO;
import br.com.centralit.citcorpore.integracao.ServicoDao;
import br.com.centralit.citcorpore.integracao.TemplateSolicitacaoServicoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilStrings;
public class TemplateSolicitacaoServicoServiceEjb extends CrudServicePojoImpl implements TemplateSolicitacaoServicoService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected CrudDAO getDao() throws ServiceException {
		return new TemplateSolicitacaoServicoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}
	
	private TemplateSolicitacaoServicoDTO recuperaTemplateAcompanhamento(SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
		TemplateSolicitacaoServicoDTO templateDto = null;
        ServicoDTO servicoDto = new ServicoDTO();
        servicoDto.setIdServico(solicitacaoServicoDto.getIdServico());
        servicoDto = (ServicoDTO) new ServicoDao().restore(servicoDto);
        if (servicoDto != null) {
            Integer idTemplate = servicoDto.getIdTemplateSolicitacao();
            if (solicitacaoServicoDto.getIdSolicitacaoServico() != null && servicoDto.getIdTemplateAcompanhamento() != null)
                idTemplate = servicoDto.getIdTemplateAcompanhamento();
            if (idTemplate != null) {
                templateDto = new TemplateSolicitacaoServicoDTO();
                templateDto.setIdTemplate(idTemplate);
                templateDto = (TemplateSolicitacaoServicoDTO) new TemplateSolicitacaoServicoDao().restore(templateDto);
            }
        }
        return templateDto;
	}
	
	@Override
    public TemplateSolicitacaoServicoDTO recuperaTemplateServico(SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
        TemplateSolicitacaoServicoDTO templateDto = null;
        String acao = UtilStrings.nullToVazio(solicitacaoServicoDto.getAcaoFluxo());
    	if (acao.equalsIgnoreCase(Enumerados.ACAO_VISUALIZAR) && solicitacaoServicoDto.getIdSolicitacaoServico() != null && solicitacaoServicoDto.getIdServico() != null && solicitacaoServicoDto.getIdContrato() != null) {
            ServicoDTO servicoDto = new ServicoDTO();
            servicoDto.setIdServico(solicitacaoServicoDto.getIdServico());
            servicoDto = (ServicoDTO) new ServicoDao().restore(servicoDto);
            if (servicoDto != null && servicoDto.getIdTemplateAcompanhamento() != null) {
                templateDto = new TemplateSolicitacaoServicoDTO();
                templateDto.setIdTemplate(servicoDto.getIdTemplateAcompanhamento());
                templateDto = (TemplateSolicitacaoServicoDTO) new TemplateSolicitacaoServicoDao().restore(templateDto);
            }
    	}

    	if (templateDto != null)
    		return templateDto;
    	
		if (solicitacaoServicoDto.getIdTarefa() != null && (solicitacaoServicoDto.getReclassificar() == null || !solicitacaoServicoDto.getReclassificar().equalsIgnoreCase("S"))) {
			TarefaFluxoDTO tarefaFluxoDto = new TarefaFluxoDTO();
			tarefaFluxoDto.setIdItemTrabalho(solicitacaoServicoDto.getIdTarefa());
			tarefaFluxoDto = (TarefaFluxoDTO) new TarefaFluxoDao().restore(tarefaFluxoDto);
			ElementoFluxoDTO elementoDto = new ElementoFluxoDao().restore(tarefaFluxoDto.getIdElemento());
			if (elementoDto.getTemplate() != null)
				templateDto = new TemplateSolicitacaoServicoDao().findByIdentificacao(elementoDto.getTemplate());
		}
        if (templateDto == null && !acao.equalsIgnoreCase(Enumerados.ACAO_VISUALIZAR) && solicitacaoServicoDto.getIdServico() != null && solicitacaoServicoDto.getIdContrato() != null)
        	templateDto = recuperaTemplateAcompanhamento(solicitacaoServicoDto);

        return templateDto;
    }

    @Override
    public TemplateSolicitacaoServicoDTO recuperaTemplateServico(ItemTrabalho itemTrabalho) throws Exception {
        TemplateSolicitacaoServicoDTO templateDto = null;
        if (itemTrabalho != null && itemTrabalho.getIdItemTrabalho() != null) {
            TemplateSolicitacaoServicoDao templateDao = new TemplateSolicitacaoServicoDao();
    		ElementoFluxoDTO elementoDto = itemTrabalho.getElementoFluxoDto();
    		if (elementoDto == null) {
    	        TarefaFluxoDTO tarefaFluxoDto = new TarefaFluxoDTO();
    	        tarefaFluxoDto.setIdItemTrabalho(itemTrabalho.getIdItemTrabalho());
    	        tarefaFluxoDto = (TarefaFluxoDTO) new TarefaFluxoDao().restore(tarefaFluxoDto);
    	        elementoDto = new ElementoFluxoDao().restore(tarefaFluxoDto.getIdElemento());
    		}
            if (elementoDto != null && elementoDto.getTemplate() != null) 
                templateDto = templateDao.findByIdentificacao(elementoDto.getTemplate());
        }
        return templateDto;
    }

    @Override
	public TemplateSolicitacaoServicoDTO recuperaTemplateProblema(ProblemaDTO problemaDto) throws Exception {
		TemplateSolicitacaoServicoDTO templateDto = null;
        TemplateSolicitacaoServicoDao templateDao = new TemplateSolicitacaoServicoDao();
        
       //Verifica se tem template na tarefa 
        if (problemaDto.getIdTarefa() != null) {
            TarefaFluxoDTO tarefaFluxoDto = new TarefaFluxoDTO();
            tarefaFluxoDto.setIdItemTrabalho(problemaDto.getIdTarefa());
            tarefaFluxoDto = (TarefaFluxoDTO) new TarefaFluxoDao().restore(tarefaFluxoDto);
            ElementoFluxoDTO elementoDto = new ElementoFluxoDao().restore(tarefaFluxoDto.getIdElemento());
            if (elementoDto.getTemplate() != null) 
                templateDto = templateDao.findByIdentificacao(elementoDto.getTemplate());
        }
        //Caso nao exista template na tarefa pega a template da categoriaProblema
        if (templateDto == null && problemaDto.getIdCategoriaProblema() != null ) {
            CategoriaProblemaDTO categoriaProblemaDto = new CategoriaProblemaDTO();
            categoriaProblemaDto.setIdCategoriaProblema(problemaDto.getIdCategoriaProblema());
            categoriaProblemaDto = (CategoriaProblemaDTO) new CategoriaProblemaDAO().restore(categoriaProblemaDto);
            if (categoriaProblemaDto != null) {
                Integer idTemplate = categoriaProblemaDto.getIdTemplate();
                if (problemaDto.getIdProblema() != null && categoriaProblemaDto.getIdTemplate() != null)
                    idTemplate = categoriaProblemaDto.getIdTemplate();
                if (idTemplate != null) {
                    templateDto = new TemplateSolicitacaoServicoDTO();
                    templateDto.setIdTemplate(idTemplate);
                    templateDto = (TemplateSolicitacaoServicoDTO) templateDao.restore(templateDto);
                }
            }
        }
        return templateDto;
	}
	
	
	
	@Override
	public TemplateSolicitacaoServicoDTO recuperaTemplateRequisicaoLiberacao(RequisicaoLiberacaoDTO requisicaoLiberacaoDTO) throws Exception {
		TemplateSolicitacaoServicoDTO templateDto = null;
        TemplateSolicitacaoServicoDao templateDao = new TemplateSolicitacaoServicoDao();
                        
       //Verifica se tem template na tarefa 
        if (requisicaoLiberacaoDTO.getIdTarefa() != null) {
            TarefaFluxoDTO tarefaFluxoDto = new TarefaFluxoDTO();
            tarefaFluxoDto.setIdItemTrabalho(requisicaoLiberacaoDTO.getIdTarefa());
            tarefaFluxoDto = (TarefaFluxoDTO) new TarefaFluxoDao().restore(tarefaFluxoDto);
            ElementoFluxoDTO elementoDto = new ElementoFluxoDao().restore(tarefaFluxoDto.getIdElemento());
            if (elementoDto.getTemplate() != null) 
                templateDto = templateDao.findByIdentificacao(elementoDto.getTemplate());
        }

        	
        
        	/*            CategoriaProblemaDTO categoriaProblemaDto = new CategoriaProblemaDTO();
            categoriaProblemaDto.setIdCategoriaProblema(problemaDto.getIdCategoriaProblema());
            categoriaProblemaDto = (CategoriaProblemaDTO) new CategoriaProblemaDAO().restore(categoriaProblemaDto);
            if (categoriaProblemaDto != null) {
                Integer idTemplate = categoriaProblemaDto.getIdTemplate();
                if (problemaDto.getIdProblema() != null && categoriaProblemaDto.getIdTemplate() != null)
                    idTemplate = categoriaProblemaDto.getIdTemplate();
                if (idTemplate != null) {
                    templateDto = new TemplateSolicitacaoServicoDTO();
                    templateDto.setIdTemplate(idTemplate);
                    templateDto = (TemplateSolicitacaoServicoDTO) templateDao.restore(templateDto);
                }
            }
        }*/
        return templateDto;
	}
	

	public TemplateSolicitacaoServicoDTO recuperaTemplateProblema(ProblemaDTO problemaDto, TransactionControler tc) throws Exception {
		TemplateSolicitacaoServicoDTO templateDto = null;
        TemplateSolicitacaoServicoDao templateDao = new TemplateSolicitacaoServicoDao();
        templateDao.setTransactionControler(tc);
        if (problemaDto.getIdTarefa() != null) {
            TarefaFluxoDTO tarefaFluxoDto = new TarefaFluxoDTO();
            tarefaFluxoDto.setIdItemTrabalho(problemaDto.getIdTarefa());
            TarefaFluxoDao tarefaFluxoDao = new TarefaFluxoDao();
            ElementoFluxoDao elementoFluxoDao = new ElementoFluxoDao();
            elementoFluxoDao.setTransactionControler(tc);
            tarefaFluxoDao.setTransactionControler(tc);
            tarefaFluxoDto = (TarefaFluxoDTO) tarefaFluxoDao.restore(tarefaFluxoDto);
            ElementoFluxoDTO elementoDto = elementoFluxoDao.restore(tarefaFluxoDto.getIdElemento());
            if (elementoDto.getTemplate() != null) 
                templateDto = templateDao.findByIdentificacao(elementoDto.getTemplate());
        }
        if (templateDto == null && problemaDto.getIdCategoriaProblema() != null ) {
            CategoriaProblemaDTO categoriaProblemaDto = new CategoriaProblemaDTO();
            categoriaProblemaDto.setIdCategoriaProblema(problemaDto.getIdCategoriaProblema());
            CategoriaProblemaDAO categoriaProblemaDAO =  new CategoriaProblemaDAO();
            categoriaProblemaDAO.setTransactionControler(tc);
            categoriaProblemaDto = (CategoriaProblemaDTO) categoriaProblemaDAO.restore(categoriaProblemaDto);
            if (categoriaProblemaDto != null) {
                Integer idTemplate = categoriaProblemaDto.getIdTemplate();
                if (problemaDto.getIdProblema() != null && categoriaProblemaDto.getIdTemplate() != null)
                    idTemplate = categoriaProblemaDto.getIdTemplate();
                if (idTemplate != null) {
                    templateDto = new TemplateSolicitacaoServicoDTO();
                    templateDto.setIdTemplate(idTemplate);
                    templateDto = (TemplateSolicitacaoServicoDTO) templateDao.restore(templateDto);
                }
            }
        }
        return templateDto;
	}
	
	@Override
	public TemplateSolicitacaoServicoDTO recuperaTemplateRequisicaoMudanca(RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
		TemplateSolicitacaoServicoDTO templateDto = null;
        TemplateSolicitacaoServicoDao templateDao = new TemplateSolicitacaoServicoDao();
        
       //Verifica se tem template na tarefa 
        if (requisicaoMudancaDTO.getIdTarefa() != null) {
            TarefaFluxoDTO tarefaFluxoDto = new TarefaFluxoDTO();
            tarefaFluxoDto.setIdItemTrabalho(requisicaoMudancaDTO.getIdTarefa());
            tarefaFluxoDto = (TarefaFluxoDTO) new TarefaFluxoDao().restore(tarefaFluxoDto);
            ElementoFluxoDTO elementoDto = new ElementoFluxoDao().restore(tarefaFluxoDto.getIdElemento());
            if (elementoDto.getTemplate() != null) 
                templateDto = templateDao.findByIdentificacao(elementoDto.getTemplate());
        }
        //Caso nao exista template na tarefa pega a template da categoriaProblema
        /*if (templateDto == null && requisicaoMudancaDTO.getIdCategoriaProblema() != null ) {
            CategoriaProblemaDTO categoriaProblemaDto = new CategoriaProblemaDTO();
            categoriaProblemaDto.setIdCategoriaProblema(requisicaoMudancaDTO.getIdCategoriaProblema());
            categoriaProblemaDto = (CategoriaProblemaDTO) new CategoriaProblemaDAO().restore(categoriaProblemaDto);
            if (categoriaProblemaDto != null) {
                Integer idTemplate = categoriaProblemaDto.getIdTemplate();
                if (requisicaoMudancaDTO.getIdRequisicaoMudanca() != null && categoriaProblemaDto.getIdTemplate() != null)
                    idTemplate = categoriaProblemaDto.getIdTemplate();
                if (idTemplate != null) {
                    templateDto = new TemplateSolicitacaoServicoDTO();
                    templateDto.setIdTemplate(idTemplate);
                    templateDto = (TemplateSolicitacaoServicoDTO) templateDao.restore(templateDto);
                }
            }
        }*/
        return templateDto;
	}
	
	public TemplateSolicitacaoServicoDTO findByIdentificacao(String identificacao) throws Exception {
		return new TemplateSolicitacaoServicoDao().findByIdentificacao(identificacao);
	}

}
