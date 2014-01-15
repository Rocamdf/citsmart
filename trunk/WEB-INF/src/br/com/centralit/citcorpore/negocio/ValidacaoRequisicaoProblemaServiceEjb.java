package br.com.centralit.citcorpore.negocio;

import br.com.centralit.bpm.dto.ElementoFluxoDTO;
import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.integracao.ElementoFluxoDao;
import br.com.centralit.bpm.integracao.TarefaFluxoDao;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.ValidacaoRequisicaoProblemaDTO;
import br.com.centralit.citcorpore.integracao.ServicoDao;
import br.com.centralit.citcorpore.integracao.ValidacaoRequisicaoProblemaDAO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.WebUtil;


/**
 * 
 * @author geber.costa
 *
 */

@SuppressWarnings({"serial"})
public class ValidacaoRequisicaoProblemaServiceEjb extends CrudServicePojoImpl implements ValidacaoRequisicaoProblemaService {
	
	@Override
	public ValidacaoRequisicaoProblemaDTO recuperaTemplateRequisicaoProblema(ProblemaDTO problemaDto) throws Exception {

		ValidacaoRequisicaoProblemaDTO templateValidacaoDto = null;
		ValidacaoRequisicaoProblemaDAO templateValidacaoDao = new ValidacaoRequisicaoProblemaDAO();
		if (problemaDto.getIdTarefa() != null) {
			TarefaFluxoDTO tarefaFluxoDto = new TarefaFluxoDTO();
			tarefaFluxoDto.setIdItemTrabalho(problemaDto.getIdTarefa());
			tarefaFluxoDto = (TarefaFluxoDTO) new TarefaFluxoDao().restore(tarefaFluxoDto);
			ElementoFluxoDTO elementoDto = new ElementoFluxoDao().restore(tarefaFluxoDto.getIdElemento());
			if (elementoDto.getTemplate() != null)
				templateValidacaoDto = templateValidacaoDao.findByObservacaoProblema(elementoDto.getTemplate());
		}
		
		if(templateValidacaoDto == null && problemaDto.getIdServico()!= null && problemaDto.getIdContrato() != null ){
			ServicoDTO servicoDto = new ServicoDTO();
			servicoDto.setIdServico(problemaDto.getIdServico());
			servicoDto = (ServicoDTO) new ServicoDao().restore(servicoDto);
			if(servicoDto != null){
				Integer idTemplate = servicoDto.getIdTemplateSolicitacao();
				if (problemaDto.getIdSolicitacaoServico() != null && servicoDto.getIdTemplateAcompanhamento() != null)
					idTemplate = servicoDto.getIdTemplateAcompanhamento();
				if (idTemplate != null) {
					templateValidacaoDto = new ValidacaoRequisicaoProblemaDTO();
					templateValidacaoDto.setIdValidacaoRequisicaoProblema(idTemplate);
					templateValidacaoDto = (ValidacaoRequisicaoProblemaDTO)  templateValidacaoDao.restore(templateValidacaoDto);
				}
			}
			
		}
		
		return templateValidacaoDto;

	}


	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new ValidacaoRequisicaoProblemaDAO();
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
	public ValidacaoRequisicaoProblemaDTO findByIdProblema(Integer parm) throws Exception {
		ValidacaoRequisicaoProblemaDAO dao = new ValidacaoRequisicaoProblemaDAO();
		try{
			return dao.findByIdProblema(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}


	@Override
	public IDto create(TransactionControler tc, ProblemaDTO problemaDto, IDto model) throws Exception {
		ValidacaoRequisicaoProblemaDTO validacaoRequisicaoProblemaDto = (ValidacaoRequisicaoProblemaDTO) model;
		ValidacaoRequisicaoProblemaDAO validacaoRequisicaoProblemaDao =    new ValidacaoRequisicaoProblemaDAO();
		
		validacaoRequisicaoProblemaDao.setTransactionControler(tc);
		
		if(problemaDto.getIdProblema() !=null){
			
			validacaoRequisicaoProblemaDto.setIdProblema(problemaDto.getIdProblema());
		}
		
		validacaoRequisicaoProblemaDto.setDataInicio(UtilDatas.getDataAtual());
		
		validacaoRequisicaoProblemaDto = (ValidacaoRequisicaoProblemaDTO) validacaoRequisicaoProblemaDao.create(validacaoRequisicaoProblemaDto);
		
		
		return validacaoRequisicaoProblemaDto;
	}


	@Override
	public void update(TransactionControler tc, ProblemaDTO problemaDto, IDto model) throws Exception {
		ValidacaoRequisicaoProblemaDTO validacaoRequisicaoProblemaDto = (ValidacaoRequisicaoProblemaDTO) model;
		ValidacaoRequisicaoProblemaDAO validacaoRequisicaoProblemaDao =    new ValidacaoRequisicaoProblemaDAO();
		
		validacaoRequisicaoProblemaDao.setTransactionControler(tc);
		
		if(problemaDto.getIdProblema() !=null){
			
			validacaoRequisicaoProblemaDto.setIdProblema(problemaDto.getIdProblema());
		}
		if(validacaoRequisicaoProblemaDto.getIdValidacaoRequisicaoProblema()!=null){
			validacaoRequisicaoProblemaDao.update(validacaoRequisicaoProblemaDto);
		}
		
		 
	}


	@Override
	public IDto deserializaObjeto(String serialize) throws Exception {
		 ValidacaoRequisicaoProblemaDTO validacaoRequisicaoProblemaDto = null;
		    
	        if (serialize != null) {
	        	
	        	validacaoRequisicaoProblemaDto = (ValidacaoRequisicaoProblemaDTO) WebUtil.deserializeObject(ValidacaoRequisicaoProblemaDTO.class, serialize);
	           
	        }
	        return validacaoRequisicaoProblemaDto;
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
}