
package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ColetaPrecoDTO;
import br.com.centralit.citcorpore.rh.bean.AtitudeCandidatoDTO;
import br.com.centralit.citcorpore.rh.bean.EntrevistaCandidatoDTO;
import br.com.centralit.citcorpore.rh.bean.HistoricoCandidatoDTO;
import br.com.centralit.citcorpore.rh.bean.TriagemRequisicaoPessoalDTO;
import br.com.centralit.citcorpore.rh.integracao.AtitudeCandidatoDao;
import br.com.centralit.citcorpore.rh.integracao.EntrevistaCandidatoDao;
import br.com.centralit.citcorpore.util.Enumerados.TipoEntrevista;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDServiceBean;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.service.ServiceLocator;

public class EntrevistaCandidatoServiceEjb extends CrudServicePojoImpl implements EntrevistaCandidatoService {
	protected CrudDAO getDao() throws ServiceException{ 
		return new EntrevistaCandidatoDao(); 
	}
	
	protected void validaCreate(Object arg0) throws Exception{
		validaAtualizacao(arg0);
	}
	
	private void validaAtualizacao(Object arg0) throws Exception {
		
		EntrevistaCandidatoDTO entrevistaCandidatoDto = (EntrevistaCandidatoDTO)arg0;
		
		if (entrevistaCandidatoDto.getTipoEntrevista().equalsIgnoreCase(TipoEntrevista.RH.name())) {
			
    		if (entrevistaCandidatoDto.getPlanoCarreira() == null || entrevistaCandidatoDto.getPlanoCarreira().trim().equals(""))
    			throw new LogicException("Plano de carreira não informado");
    		
    		if (entrevistaCandidatoDto.getCaracteristicas() == null || entrevistaCandidatoDto.getCaracteristicas().trim().equals(""))
    			throw new LogicException("Características não informadas");
    		
    		if (entrevistaCandidatoDto.getTrabalhoEmEquipe() == null || entrevistaCandidatoDto.getTrabalhoEmEquipe().trim().equals(""))
    			throw new LogicException("Trabalho em Equipe não informado");
    		
    		if (entrevistaCandidatoDto.getPossuiOutraAtividade() == null)
    			throw new LogicException("Indicador se possui outra atividade não informado");
    		
    		if (entrevistaCandidatoDto.getPossuiOutraAtividade().equalsIgnoreCase("S") && (entrevistaCandidatoDto.getOutraAtividade() == null || entrevistaCandidatoDto.getOutraAtividade().trim().equals("")))
    			throw new LogicException("Outra atividade não informada");
    		
//    		if (entrevistaCandidatoDto.getConcordaExclusividade() == null)
//    			throw new LogicException("Indicador se concorda com exclusividade não informado");
    		
    		if (entrevistaCandidatoDto.getSalarioAtual() == null || entrevistaCandidatoDto.getSalarioAtual().doubleValue() == 0)	    		
    			throw new LogicException("Salário atual não informado");
    		
    		if (entrevistaCandidatoDto.getPretensaoSalarial() == null || entrevistaCandidatoDto.getPretensaoSalarial().doubleValue() == 0)	  
    			throw new LogicException("Pretensão salarial não informada");
    		
    		if (entrevistaCandidatoDto.getDataDisponibilidade() == null)
    			throw new LogicException("Data da disponibilidade não informada");
    		
    		if (entrevistaCandidatoDto.getCompetencias() == null || entrevistaCandidatoDto.getCompetencias().trim().equals(""))
    			throw new LogicException("Competências não informadas");
    		
    		if (entrevistaCandidatoDto.getColAtitudes() == null || entrevistaCandidatoDto.getColAtitudes().isEmpty()) {
    			
    			throw new LogicException("Atitudes não informadas");
    		} else {
    			
    			for (AtitudeCandidatoDTO atitudeCandidatoDto : entrevistaCandidatoDto.getColAtitudes()) {
    				
    				if (atitudeCandidatoDto.getAvaliacao() == null || atitudeCandidatoDto.getAvaliacao().equalsIgnoreCase("")) {
    					
    					throw new LogicException("Informe a avaliação para a atitude \"" + atitudeCandidatoDto.getDescricao() + "\"");
    				}
    			}
    		}
		} else {
    		if (entrevistaCandidatoDto.getResultado() == null) {
    			
    			throw new LogicException("Resultado não informado");
    		}
		}
	}

	protected void validaDelete(Object arg0) throws Exception{}
	
	protected void validaFind(Object arg0) throws Exception{}
	
	protected void validaUpdate(Object arg0) throws Exception{
		validaAtualizacao(arg0);
	}

	@Override
	public IDto create(IDto model) throws ServiceException, LogicException {
		//Instancia Objeto controlador de transacao
		EntrevistaCandidatoDao entrevistaCandidatoDao = new EntrevistaCandidatoDao();
		AtitudeCandidatoDao atitudeCandidatoDao = new AtitudeCandidatoDao();
		TransactionControler tc = new TransactionControlerImpl(atitudeCandidatoDao.getAliasDB());
		
		try{
			
			validaCreate(model);
			
			//Seta o TransactionController para os DAOs
			entrevistaCandidatoDao.setTransactionControler(tc);
			atitudeCandidatoDao.setTransactionControler(tc);
			
			//Inicia transacao
			tc.start();
			
			//Executa operacoes pertinentes ao negocio.
			EntrevistaCandidatoDTO entrevistaCandidatoDto = (EntrevistaCandidatoDTO)model;
			if (entrevistaCandidatoDto.getTipoEntrevista().equalsIgnoreCase(TipoEntrevista.RH.name()))
				entrevistaCandidatoDto.setResultado("N");
			
			model = entrevistaCandidatoDao.create(model);
			atualizaAnexos(entrevistaCandidatoDto, tc);
			
			//grava historico do candidato
			if(entrevistaCandidatoDto.getResultado() != null && !entrevistaCandidatoDto.getResultado().equals("N")){
				this.gravaHistoricoCandidato(entrevistaCandidatoDto, tc);
			}

			if (entrevistaCandidatoDto.getTipoEntrevista().equalsIgnoreCase(TipoEntrevista.RH.name()) && entrevistaCandidatoDto.getColAtitudes() !=null){
				for (AtitudeCandidatoDTO atitudeCandidatoDto : entrevistaCandidatoDto.getColAtitudes()) {
					atitudeCandidatoDto.setIdEntrevista(entrevistaCandidatoDto.getIdEntrevista());
					atitudeCandidatoDao.create(atitudeCandidatoDto);
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
	public void update(IDto model) throws ServiceException, LogicException {

		//Instancia Objeto controlador de transacao
		EntrevistaCandidatoDao entrevistaCandidatoDao = new EntrevistaCandidatoDao();
		AtitudeCandidatoDao atitudeCandidatoDao = new AtitudeCandidatoDao();
		TransactionControler tc = new TransactionControlerImpl(atitudeCandidatoDao.getAliasDB());
		
		try{
			
			validaUpdate(model);

			//Seta o TransactionController para os DAOs
			EntrevistaCandidatoDTO entrevistaCandidatoDto = (EntrevistaCandidatoDTO)model;
			entrevistaCandidatoDao.setTransactionControler(tc);
			atitudeCandidatoDao.setTransactionControler(tc);
			
			entrevistaCandidatoDao.updateNotNull(entrevistaCandidatoDto);
			
			atualizaAnexos(entrevistaCandidatoDto, tc);
			
			//grava historico do candidato
			if(entrevistaCandidatoDto.getResultado() != null && !entrevistaCandidatoDto.getResultado().equals("")){
				this.gravaHistoricoCandidato(entrevistaCandidatoDto, tc);
			}
			
			if (entrevistaCandidatoDto.getTipoEntrevista().equalsIgnoreCase(TipoEntrevista.RH.name())){
				atitudeCandidatoDao.deleteByIdEntrevista(entrevistaCandidatoDto.getIdEntrevista());
				if (entrevistaCandidatoDto.getColAtitudes() !=null){
					for (AtitudeCandidatoDTO atitudeCandidatoDto : entrevistaCandidatoDto.getColAtitudes()) {
						atitudeCandidatoDto.setIdEntrevista(entrevistaCandidatoDto.getIdEntrevista());
						atitudeCandidatoDao.create(atitudeCandidatoDto);
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
	
	
	public EntrevistaCandidatoDTO findByIdTriagemAndIdCurriculo(Integer idTriagem, Integer idCurriculo) throws Exception {
		return new EntrevistaCandidatoDao().findByIdTriagemAndIdCurriculo(idTriagem, idCurriculo);
	}
	
	public void gravaHistoricoCandidato(EntrevistaCandidatoDTO entrevistaCandidatoDto, TransactionControler tc) throws ServiceException, Exception {
		HistoricoCandidatoDTO historicoCandidatoDTO = new HistoricoCandidatoDTO();
		historicoCandidatoDTO.setIdCurriculo(entrevistaCandidatoDto.getIdCurriculo());
		historicoCandidatoDTO.setIdEntrevista(entrevistaCandidatoDto.getIdEntrevista());
		historicoCandidatoDTO.setResultado(entrevistaCandidatoDto.getResultado());
		historicoCandidatoDTO.setIdSolicitacaoServico(entrevistaCandidatoDto.getIdSolicitacaoServico());
		
		HistoricoCandidatoService historicoCandidatoService = (HistoricoCandidatoService) ServiceLocator.getInstance().getService(HistoricoCandidatoService.class, null);
		historicoCandidatoService.create(historicoCandidatoDTO);
		
	}
	
	private void atualizaAnexos(EntrevistaCandidatoDTO entrevistaCandidatoDto, TransactionControler tc) throws Exception {
        new ControleGEDServiceBean().atualizaAnexos(entrevistaCandidatoDto.getAnexos(), ControleGEDDTO.TABELA_COLETAPRECOS, entrevistaCandidatoDto.getIdEntrevista(), tc);
	}

	@Override
	public Collection listCurriculosAprovadosPorOrdemMaiorNota(Integer idTriagem) throws Exception {
		EntrevistaCandidatoDao candidatoDao = new EntrevistaCandidatoDao();
		return candidatoDao.listCurriculosAprovadosPorOrdemMaiorNota(idTriagem);
	}
	
	@Override
	public Boolean seCandidatoAprovado(TriagemRequisicaoPessoalDTO triagemRequisicaoPessoalDTO) throws Exception {
		EntrevistaCandidatoDao candidatoDao = new EntrevistaCandidatoDao();
		return candidatoDao.seCandidatoAprovado(triagemRequisicaoPessoalDTO);
	}
	
}
