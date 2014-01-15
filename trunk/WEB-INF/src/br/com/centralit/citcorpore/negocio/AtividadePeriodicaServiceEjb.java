package br.com.centralit.citcorpore.negocio;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.AgendaAtvPeriodicasDTO;
import br.com.centralit.citcorpore.bean.AtividadePeriodicaDTO;
import br.com.centralit.citcorpore.bean.OSAtividadePeriodicaDTO;
import br.com.centralit.citcorpore.bean.OSDTO;
import br.com.centralit.citcorpore.bean.ProgramacaoAtividadeDTO;
import br.com.centralit.citcorpore.integracao.AtividadePeriodicaDao;
import br.com.centralit.citcorpore.integracao.OSAtividadePeriodicaDao;
import br.com.centralit.citcorpore.integracao.ProgramacaoAtividadeDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
@SuppressWarnings({ "rawtypes", "serial" })
public class AtividadePeriodicaServiceEjb extends CrudServicePojoImpl implements AtividadePeriodicaService {
	protected CrudDAO getDao() throws ServiceException {
		return new AtividadePeriodicaDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public IDto create(IDto model) throws ServiceException, LogicException {
		//Instancia Objeto controlador de transacao
		CrudDAO crudDao = getDao();
		ProgramacaoAtividadeDao programacaoAtividadeDao = new ProgramacaoAtividadeDao();
		OSAtividadePeriodicaDao oSAtividadePeriodicaDao = new OSAtividadePeriodicaDao(); 
		TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
		try{
			//Faz validacao, caso exista.
			validaCreate(model);
			AtividadePeriodicaDTO atividadePeriodicaDTO = (AtividadePeriodicaDTO)model;
			//Instancia ou obtem os DAOs necessarios.
			
			
			//Seta o TransactionController para os DAOs
			crudDao.setTransactionControler(tc);
			programacaoAtividadeDao.setTransactionControler(tc);
			oSAtividadePeriodicaDao.setTransactionControler(tc);
			
			//Inicia transacao
			tc.start();
			
			//Executa operacoes pertinentes ao negocio.
			model = crudDao.create(model);
			if (atividadePeriodicaDTO.getColItens() != null){
				for(Iterator it = atividadePeriodicaDTO.getColItens().iterator(); it.hasNext();){
					ProgramacaoAtividadeDTO programacaoAtividadeDTO = (ProgramacaoAtividadeDTO)it.next();
					programacaoAtividadeDTO.setIdAtividadePeriodica(atividadePeriodicaDTO.getIdAtividadePeriodica());
                    programacaoAtividadeDTO.setHoraInicio(programacaoAtividadeDTO.getHoraInicio().replaceAll(":",""));
                    programacaoAtividadeDTO.setHoraFim(programacaoAtividadeDTO.getHoraFim().replaceAll(":",""));
					programacaoAtividadeDao.create(programacaoAtividadeDTO);
				}
			}
			
			if(atividadePeriodicaDTO.getColItensOS() != null){
				for(Iterator it = atividadePeriodicaDTO.getColItensOS().iterator(); it.hasNext();){
					OSDTO oSDTO = (OSDTO)it.next();
					OSAtividadePeriodicaDTO oSAtividadePeriodicadto = new OSAtividadePeriodicaDTO();
					oSAtividadePeriodicadto.setIdAtividadePeriodica(atividadePeriodicaDTO.getIdAtividadePeriodica());
					oSAtividadePeriodicadto.setIdOs(oSDTO.getIdOS());
					oSAtividadePeriodicaDao.create(oSAtividadePeriodicadto);
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

	public void update(IDto model) throws ServiceException, LogicException {
		//Instancia Objeto controlador de transacao
		CrudDAO crudDao = getDao();
		ProgramacaoAtividadeDao programacaoAtividadeDao = new ProgramacaoAtividadeDao();
		OSAtividadePeriodicaDao oSAtividadePeriodicaDao = new OSAtividadePeriodicaDao();
		TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
		try{
			//Faz validacao, caso exista.
			validaUpdate(model);
			AtividadePeriodicaDTO atividadePeriodicaDTO = (AtividadePeriodicaDTO)model;

			//Seta o TransactionController para os DAOs
			crudDao.setTransactionControler(tc);
			programacaoAtividadeDao.setTransactionControler(tc);
			oSAtividadePeriodicaDao.setTransactionControler(tc);
			
			//Inicia transacao
			tc.start();
			
			//Executa operacoes pertinentes ao negocio.
			crudDao.update(model);
			programacaoAtividadeDao.deleteByIdAtividadePeriodica(atividadePeriodicaDTO.getIdAtividadePeriodica());
			if (atividadePeriodicaDTO.getColItens() != null){
				for(Iterator it = atividadePeriodicaDTO.getColItens().iterator(); it.hasNext();){
					ProgramacaoAtividadeDTO programacaoAtividadeDTO = (ProgramacaoAtividadeDTO)it.next();
					programacaoAtividadeDTO.setIdAtividadePeriodica(atividadePeriodicaDTO.getIdAtividadePeriodica());
					programacaoAtividadeDTO.setHoraInicio(programacaoAtividadeDTO.getHoraInicio().replaceAll(":",""));
					programacaoAtividadeDTO.setHoraFim(programacaoAtividadeDTO.getHoraFim().replaceAll(":",""));
			        programacaoAtividadeDao.create(programacaoAtividadeDTO);
				}
			}
			
			oSAtividadePeriodicaDao.deleteByIdAtividadePeriodica(atividadePeriodicaDTO.getIdAtividadePeriodica());
			if(atividadePeriodicaDTO.getColItensOS() != null){
				for(Iterator it = atividadePeriodicaDTO.getColItensOS().iterator(); it.hasNext();){
					OSDTO oSDTO = (OSDTO)it.next();
					OSAtividadePeriodicaDTO oSAtividadePeriodicadto = new OSAtividadePeriodicaDTO();
					oSAtividadePeriodicadto.setIdAtividadePeriodica(atividadePeriodicaDTO.getIdAtividadePeriodica());
					oSAtividadePeriodicadto.setIdOs(oSDTO.getIdOS());
					oSAtividadePeriodicaDao.create(oSAtividadePeriodicadto);
				}
			}
			
			
			//Faz commit e fecha a transacao.
			tc.commit();
			tc.close();
		}catch(Exception e){
			this.rollbackTransaction(tc, e);
		}
	}	
	public Collection findByIdSolicitacaoServico(Integer parm) throws Exception{
		AtividadePeriodicaDao dao = new AtividadePeriodicaDao();
		try{
			return dao.findByIdSolicitacaoServico(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}	
	public Collection findByIdContrato(Integer parm) throws Exception{
		AtividadePeriodicaDao dao = new AtividadePeriodicaDao();
		try{
			return dao.findByIdContrato(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdContrato(Integer parm) throws Exception{
		AtividadePeriodicaDao dao = new AtividadePeriodicaDao();
		try{
			dao.deleteByIdContrato(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByIdProcedimentoTecnico(Integer parm) throws Exception{
		AtividadePeriodicaDao dao = new AtividadePeriodicaDao();
		try{
			return dao.findByIdProcedimentoTecnico(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdProcedimentoTecnico(Integer parm) throws Exception{
		AtividadePeriodicaDao dao = new AtividadePeriodicaDao();
		try{
			dao.deleteByIdProcedimentoTecnico(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	public Collection findByIdGrupoAtvPeriodica(AgendaAtvPeriodicasDTO agendaAtvPeriodicasDTO) throws Exception{
		AtividadePeriodicaDao dao = new AtividadePeriodicaDao();
		Collection<AtividadePeriodicaDTO> colAgendamentoVinculados = new ArrayList<AtividadePeriodicaDTO>();
		Collection<AtividadePeriodicaDTO> colAgendamentoSemVinculacao = new ArrayList<AtividadePeriodicaDTO>();
		Collection<AtividadePeriodicaDTO> colAgendamentoMudanca = new ArrayList<AtividadePeriodicaDTO>();
		Collection<AtividadePeriodicaDTO> colRetorno = new ArrayList<AtividadePeriodicaDTO>();
		try{
			//Só vai entrar caso tenha selecionado algum item do Grupo de Atividades.
			if (agendaAtvPeriodicasDTO != null && agendaAtvPeriodicasDTO.getIdGrupoAtvPeriodica() != 0 && agendaAtvPeriodicasDTO.getIdGrupoPesquisa() != null) {
				//Só vai entrar caso tenha selecioando a opção Gerência Mudança do Grupo Pesquisa.
				if (agendaAtvPeriodicasDTO != null && agendaAtvPeriodicasDTO.getIdGrupoPesquisa() != null && agendaAtvPeriodicasDTO.getIdGrupoPesquisa() == 2) {
					colAgendamentoMudanca = dao.listSomenteReqMudanca(agendaAtvPeriodicasDTO);
					if (colAgendamentoMudanca != null) {
						colRetorno.addAll(colAgendamentoMudanca);
					}
				}else{
					if (agendaAtvPeriodicasDTO != null && agendaAtvPeriodicasDTO.getIdGrupoPesquisa() != null && agendaAtvPeriodicasDTO.getIdGrupoPesquisa() != 0) {
						colAgendamentoVinculados = dao.listAgendamentoVinculados(agendaAtvPeriodicasDTO);
						if (colAgendamentoVinculados != null) {
							colRetorno.addAll(colAgendamentoVinculados);
						}
					}
				}
				//Caso não tenha selecionado nenhuma opção do Grupo Pesquisa, ai vou trazer todos.
				if (agendaAtvPeriodicasDTO != null && agendaAtvPeriodicasDTO.getIdGrupoPesquisa() != null && agendaAtvPeriodicasDTO.getIdGrupoPesquisa() == 0) {
					colAgendamentoSemVinculacao = dao.listAgendamentoSemVinculacao(agendaAtvPeriodicasDTO);
					colAgendamentoMudanca = dao.listSomenteReqMudanca(agendaAtvPeriodicasDTO);
					if (colAgendamentoSemVinculacao != null) {
						colRetorno.addAll(colAgendamentoSemVinculacao);
					}
					if (colAgendamentoMudanca != null) {
						colRetorno.addAll(colAgendamentoMudanca);
					}
				}else{
					colAgendamentoSemVinculacao = dao.listAgendamentoSemVinculacao(agendaAtvPeriodicasDTO);
					if (colAgendamentoSemVinculacao != null) {
						colRetorno.addAll(colAgendamentoSemVinculacao);
					}
				}
			}
			//return dao.findByIdGrupoAtvPeriodica(agendaAtvPeriodicasDTO);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return colRetorno;
	}
	
	public void deleteByIdGrupoAtvPeriodica(Integer parm) throws Exception{
		AtividadePeriodicaDao dao = new AtividadePeriodicaDao();
		try{
			dao.deleteByIdGrupoAtvPeriodica(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByTituloAtividade(Integer parm) throws Exception{
		AtividadePeriodicaDao dao = new AtividadePeriodicaDao();
		try{
			return dao.findByTituloAtividade(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByTituloAtividade(Integer parm) throws Exception{
		AtividadePeriodicaDao dao = new AtividadePeriodicaDao();
		try{
			dao.deleteByTituloAtividade(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	/**
	 * Metodo que verifica se existe um registro com os mesmos dados na base de dados.
	 * 
	 * @param nomeAtividade - nome da atividade periodica.
	 * @return Retorna 'true' se existir um registro igual e 'false' caso contrario.
	 * @throws Exception
	 */
	public boolean existeDuplicacao(String tituloAtividade, Integer idAtividade) throws Exception {
		AtividadePeriodicaDao dao = new AtividadePeriodicaDao();
		return dao.existeDuplicacao(tituloAtividade, idAtividade);
	}

	@Override
	public Collection findByIdRequisicaoMudanca(Integer parm) throws Exception {
		AtividadePeriodicaDao dao = new AtividadePeriodicaDao();
		try{
			return dao.findByIdRequisicaoMudanca(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public Collection findByIdProblema(Integer parm) throws Exception {
		AtividadePeriodicaDao dao = new AtividadePeriodicaDao();
		try{
			return dao.findByIdProblema(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public Collection findByIdRequisicaoLiberacao(Integer parm) throws Exception {
		AtividadePeriodicaDao dao = new AtividadePeriodicaDao();
		try{
//			return dao.findByIdRequisicaoMudanca(parm);
			return dao.findByIdRequisicaoLiberacao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
		

}
