package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.bean.AcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.AcordoNivelServicoHistoricoDTO;
import br.com.centralit.citcorpore.bean.PrioridadeAcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.PrioridadeServicoUnidadeDTO;
import br.com.centralit.citcorpore.bean.PrioridadeServicoUsuarioDTO;
import br.com.centralit.citcorpore.bean.ResultadosEsperadosDTO;
import br.com.centralit.citcorpore.bean.RevisarSlaDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.SlaRequisitoSlaDTO;
import br.com.centralit.citcorpore.bean.TempoAcordoNivelServicoDTO;
import br.com.centralit.citcorpore.integracao.AcordoNivelServicoDao;
import br.com.centralit.citcorpore.integracao.AcordoNivelServicoHistoricoDao;
import br.com.centralit.citcorpore.integracao.PrioridadeAcordoNivelServicoDao;
import br.com.centralit.citcorpore.integracao.PrioridadeServicoUnidadeDao;
import br.com.centralit.citcorpore.integracao.PrioridadeServicoUsuarioDao;
import br.com.centralit.citcorpore.integracao.ResultadosEsperadosDAO;
import br.com.centralit.citcorpore.integracao.RevisarSlaDao;
import br.com.centralit.citcorpore.integracao.SlaRequisitoSLADao;
import br.com.centralit.citcorpore.integracao.TempoAcordoNivelServicoDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;

public class AcordoNivelServicoServiceEjb extends CrudServicePojoImpl implements AcordoNivelServicoService {
	
	protected CrudDAO getDao() throws ServiceException {
		return new AcordoNivelServicoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}
		
	public AcordoNivelServicoDTO findAtivoByIdServicoContrato(Integer idServicoContrato, String tipo) throws Exception {
	    AcordoNivelServicoDao acordoNivelServicoDao = new AcordoNivelServicoDao();
	    return acordoNivelServicoDao.findAtivoByIdServicoContrato(idServicoContrato, tipo);
	}
	
	public void copiarSLA(Integer idAcordoNivelServico, Integer idServicoContratoOrigem, Integer[] idServicoCopiarPara) throws Exception{
    	AcordoNivelServicoDao acordoNivelServicoDao = new AcordoNivelServicoDao();
    	TempoAcordoNivelServicoDao tempoAcordoNivelServicoDao = new TempoAcordoNivelServicoDao();
    	PrioridadeServicoUnidadeDao prioridadeServicoUnidadeDao = new PrioridadeServicoUnidadeDao();
		AcordoNivelServicoDTO acordoNivelServicoDTO = new AcordoNivelServicoDTO();
		acordoNivelServicoDTO.setIdAcordoNivelServico(idAcordoNivelServico);
		acordoNivelServicoDTO = (AcordoNivelServicoDTO) acordoNivelServicoDao.restore(acordoNivelServicoDTO);	
		
		TransactionControler tc = new TransactionControlerImpl(acordoNivelServicoDao.getAliasDB());
		
		acordoNivelServicoDao.setTransactionControler(tc);
		tempoAcordoNivelServicoDao.setTransactionControler(tc);
		prioridadeServicoUnidadeDao.setTransactionControler(tc);
		Collection colPrioridadesUnidades = prioridadeServicoUnidadeDao.findByIdServicoContrato(idServicoContratoOrigem);
		try{
    		tc.start();
    		for (int i = 0; i < idServicoCopiarPara.length; i++){
    		    acordoNivelServicoDTO.setIdAcordoNivelServico(null);
    		    acordoNivelServicoDTO.setIdServicoContrato(idServicoCopiarPara[i]);
    		    
    		    acordoNivelServicoDTO = (AcordoNivelServicoDTO) acordoNivelServicoDao.create(acordoNivelServicoDTO);
    		    if (acordoNivelServicoDTO.getTipo().equalsIgnoreCase("T")){ //TEMPO
    			Collection colTempos = tempoAcordoNivelServicoDao.findByIdAcordo(idAcordoNivelServico);
    			if (colTempos != null){
    			    for (Iterator it = colTempos.iterator(); it.hasNext();){
    				TempoAcordoNivelServicoDTO tempoAcordoNivelServicoDTO = (TempoAcordoNivelServicoDTO)it.next();
    				tempoAcordoNivelServicoDTO.setIdAcordoNivelServico(acordoNivelServicoDTO.getIdAcordoNivelServico());
    				try{
    				    tempoAcordoNivelServicoDao.create(tempoAcordoNivelServicoDTO);
    				}catch (Exception e) {
    				    e.printStackTrace();
				}
    			    }
    			}
    			if (colPrioridadesUnidades != null){
    			    for (Iterator it = colPrioridadesUnidades.iterator(); it.hasNext();){
    				PrioridadeServicoUnidadeDTO prioridadeServicoUnidadeDTO = (PrioridadeServicoUnidadeDTO)it.next();
    				prioridadeServicoUnidadeDTO.setIdServicoContrato(idServicoCopiarPara[i]);
    				try{
    				    prioridadeServicoUnidadeDao.delete(prioridadeServicoUnidadeDTO);
    				}catch (Exception e) {
    				    e.printStackTrace(); //Deixa passar o erro, pos não influencia.
				}        				
    				try{
    				    prioridadeServicoUnidadeDao.create(prioridadeServicoUnidadeDTO);
    				}catch (Exception e) {
    				    e.printStackTrace(); //Deixa passar o erro, pos não influencia.
				}
    			    }
    			}
    		    }
    		}
    		tc.commit();
		}catch (Exception e) {
		    this.rollbackTransaction(tc, e);
		}finally {
		    tc.close();
		}
		tc = null;
	}

	public Collection findByIdServicoContrato(Integer parm) throws Exception{
		AcordoNivelServicoDao dao = new AcordoNivelServicoDao();
		try{
			return dao.findByIdServicoContrato(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	public Collection consultaPorIdServicoContrato(Integer parm) throws Exception{
		ResultadosEsperadosDAO dao = new ResultadosEsperadosDAO();
		Collection colRetorno = new ArrayList();
		try{
			Collection col = dao.findByIdServicoContrato(parm);
			if (col != null && col.size() > 0){
			    for (Iterator it = col.iterator(); it.hasNext();){
					ResultadosEsperadosDTO resultados = (ResultadosEsperadosDTO)it.next();
					if (resultados.getDeleted() == null || resultados.getDeleted().equalsIgnoreCase("N") || resultados.getDeleted().trim().equals("")){
					    if(!consultaAcordoNivelServicoAtivo(resultados)){
					    	colRetorno.add(resultados);
					    }
					}
			    }
			}
			return colRetorno;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	private boolean consultaAcordoNivelServicoAtivo(ResultadosEsperadosDTO resultadosEsperadosDTO) throws ServiceException{
		AcordoNivelServicoDao dao = new AcordoNivelServicoDao();
		AcordoNivelServicoDTO acordoNivelServicoContratoDTO = new AcordoNivelServicoDTO();
		acordoNivelServicoContratoDTO.setIdAcordoNivelServico(resultadosEsperadosDTO.getIdAcordoNivelServico());
		try{
			acordoNivelServicoContratoDTO = (AcordoNivelServicoDTO) dao.restore(acordoNivelServicoContratoDTO);
			String situacao = acordoNivelServicoContratoDTO.getSituacao();
			if(!situacao.equalsIgnoreCase("A")){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}
	
	public void deleteByIdServicoContrato(Integer parm) throws Exception{
		AcordoNivelServicoDao dao = new AcordoNivelServicoDao();
		try{
			dao.deleteByIdServicoContrato(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByIdPrioridadePadrao(Integer parm) throws Exception{
		AcordoNivelServicoDao dao = new AcordoNivelServicoDao();
		try{
			return dao.findByIdPrioridadePadrao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdPrioridadePadrao(Integer parm) throws Exception{
		AcordoNivelServicoDao dao = new AcordoNivelServicoDao();
		try{
			dao.deleteByIdPrioridadePadrao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	/**
	 * Método para retornar os serviços que possuem o SLA selecionado já copiado, para ser tratado evitando duplicação de SLA.
	 * 
	 * @param titulo do SLA selecionado
	 * @return retorna os serviços que possuem o SLA selecionado
	 * @throws Exception
	 * @author rodrigo.oliveira
	 */
	public List<ServicoContratoDTO> buscaServicosComContrato(String tituloSla) throws Exception{
		AcordoNivelServicoDao dao = new AcordoNivelServicoDao();
		try{
			return dao.buscaServicosComContrato(tituloSla);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	/**
	 * Método para verificar se existe cadastrado um cadastro o mesmo nome.
	 * 
	 * @param HashMap mapFields
	 * @return true se o nome existir e false se não existir
	 * @throws Exception
	 * @author rodrigo.oliveira
	 */
	public boolean verificaSeNomeExiste(HashMap mapFields) throws Exception{
		String tituloSLA = (String) mapFields.get("TITULOSLA");
		AcordoNivelServicoDao dao = new AcordoNivelServicoDao();
		try{
			return dao.verificaSeNomeExiste(tituloSLA);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public List<AcordoNivelServicoDTO> findAcordosSemVinculacaoDireta() throws Exception{
		AcordoNivelServicoDao dao = new AcordoNivelServicoDao();
		try{
			return dao.findAcordosSemVinculacaoDireta();
		} catch (Exception e) {
			throw new ServiceException(e);
		}		
	}
	
	public AcordoNivelServicoDTO create(AcordoNivelServicoDTO acordoNivelServicoDTO, AcordoNivelServicoHistoricoDTO acordoNivelServicoHistoricoDTO) throws ServiceException, LogicException{
		
		TransactionControler transactionControler = new TransactionControlerImpl(this.getAcordoNivelServicoDao().getAliasDB());
		
		AcordoNivelServicoDao acordoNivelServicoDao = new AcordoNivelServicoDao();
		TempoAcordoNivelServicoDao tempoAcordoNivelServicoDao = new TempoAcordoNivelServicoDao();
		PrioridadeAcordoNivelServicoDao prioridadeAcordoNivelServicoDao = new PrioridadeAcordoNivelServicoDao();
		PrioridadeServicoUsuarioDao prioridadeServicoUsuarioDao = new PrioridadeServicoUsuarioDao();
		SlaRequisitoSLADao slaRequisitoSlaDao = new SlaRequisitoSLADao();
		AcordoNivelServicoHistoricoDao acordoNivelServicoHistoricoDao = new AcordoNivelServicoHistoricoDao();
		RevisarSlaDao revisarSlaDao = new RevisarSlaDao();
		
		Integer idAcordoNivelServico = 0;
		
		try{
			acordoNivelServicoDao.setTransactionControler(transactionControler);
			tempoAcordoNivelServicoDao.setTransactionControler(transactionControler);	
			prioridadeAcordoNivelServicoDao.setTransactionControler(transactionControler);
			prioridadeServicoUsuarioDao.setTransactionControler(transactionControler);
			slaRequisitoSlaDao.setTransactionControler(transactionControler);
			acordoNivelServicoHistoricoDao.setTransactionControler(transactionControler);
			revisarSlaDao.setTransactionControler(transactionControler);
			
			transactionControler.start();
			
			acordoNivelServicoDTO = (AcordoNivelServicoDTO) acordoNivelServicoDao.create(acordoNivelServicoDTO);
			acordoNivelServicoDTO.setIdAcordoNivelServico(acordoNivelServicoDTO.getIdAcordoNivelServico());
			
			if(acordoNivelServicoDTO.getIdAcordoNivelServico() != null){
				idAcordoNivelServico = acordoNivelServicoDTO.getIdAcordoNivelServico();
			}
			
    		for(int i = 1; i <= 5; i++){
    			TempoAcordoNivelServicoDTO tempoAcordoNivelServicoDTO = new TempoAcordoNivelServicoDTO();
    			tempoAcordoNivelServicoDTO.setIdAcordoNivelServico(idAcordoNivelServico);
    			tempoAcordoNivelServicoDTO.setIdFase(1);
    			tempoAcordoNivelServicoDTO.setIdPrioridade(i);
    			tempoAcordoNivelServicoDTO.setTempoHH(acordoNivelServicoDTO.getHhCaptura()[i - 1]);
    			tempoAcordoNivelServicoDTO.setTempoMM(acordoNivelServicoDTO.getMmCaptura()[i - 1]);
    			tempoAcordoNivelServicoDao.create(tempoAcordoNivelServicoDTO);
    		}
    		for(int i = 1; i <= 5; i++){
    			TempoAcordoNivelServicoDTO tempoAcordoNivelServicoDTO = new TempoAcordoNivelServicoDTO();
    			tempoAcordoNivelServicoDTO.setIdAcordoNivelServico(idAcordoNivelServico);
    			tempoAcordoNivelServicoDTO.setIdFase(2);
    			tempoAcordoNivelServicoDTO.setIdPrioridade(i);
    			tempoAcordoNivelServicoDTO.setTempoHH(acordoNivelServicoDTO.getHhResolucao()[i - 1]);
    			tempoAcordoNivelServicoDTO.setTempoMM(acordoNivelServicoDTO.getMmResolucao()[i - 1]);
    			tempoAcordoNivelServicoDao.create(tempoAcordoNivelServicoDTO);
    		}
    		
			List<PrioridadeAcordoNivelServicoDTO> colUnidades = acordoNivelServicoDTO.getListaPrioridadeUnidade();
			if (colUnidades != null && colUnidades.size() > 0){
				for (PrioridadeAcordoNivelServicoDTO prioridadeAcordoNivelServicoDTO : colUnidades) {
					prioridadeAcordoNivelServicoDTO.setIdAcordoNivelServico(idAcordoNivelServico);
					prioridadeAcordoNivelServicoDTO.setDataInicio(UtilDatas.getDataAtual());
					prioridadeAcordoNivelServicoDao.create(prioridadeAcordoNivelServicoDTO);
				}
			}
			
			List<PrioridadeServicoUsuarioDTO> colUsuarios = acordoNivelServicoDTO.getListaPrioridadeUsuario();
			if (colUsuarios != null && colUsuarios.size() > 0){
				for (PrioridadeServicoUsuarioDTO prioridadeServicoUsuarioDTO : colUsuarios) {
					prioridadeServicoUsuarioDTO.setIdAcordoNivelServico(idAcordoNivelServico);
					prioridadeServicoUsuarioDTO.setDataInicio(UtilDatas.getDataAtual());
					prioridadeServicoUsuarioDao.create(prioridadeServicoUsuarioDTO);
				}
			}
			
			List<SlaRequisitoSlaDTO> colSlaRequisitoSla = acordoNivelServicoDTO.getListaSlaRequisitoSlaDTO();
			if (colSlaRequisitoSla != null && colSlaRequisitoSla.size() > 0){
				for (SlaRequisitoSlaDTO slaRequisitoSlaDTO : colSlaRequisitoSla) {
					slaRequisitoSlaDTO.setIdAcordoNivelServico(idAcordoNivelServico);
					slaRequisitoSlaDTO.setDataUltModificacao(UtilDatas.getDataAtual());
					slaRequisitoSlaDao.create(slaRequisitoSlaDTO);
				}
			}
			
			List<RevisarSlaDTO> colRevisarSla = acordoNivelServicoDTO.getListaRevisarSlaDTO();
			if (colRevisarSla != null && colRevisarSla.size() > 0){
				for (RevisarSlaDTO revisarSlaDTO : colRevisarSla) {
					revisarSlaDTO.setIdAcordoNivelServico(idAcordoNivelServico);
					revisarSlaDao.create(revisarSlaDTO);
				}
			}
			
			AcordoNivelServicoDTO acordoNivelServicoAux = new AcordoNivelServicoDTO(); 
			
			acordoNivelServicoAux.setTempoAuto(acordoNivelServicoDTO.getTempoAuto());
			acordoNivelServicoAux.setIdPrioridadeAuto1(acordoNivelServicoDTO.getIdPrioridadeAuto1());
			acordoNivelServicoAux.setIdGrupo1(acordoNivelServicoDTO.getIdGrupo1());
			acordoNivelServicoAux.setIdAcordoNivelServico(idAcordoNivelServico);
			acordoNivelServicoDao.updateTemposAcoes(acordoNivelServicoAux);
			
			acordoNivelServicoHistoricoDTO.setIdAcordoNivelServico(idAcordoNivelServico);
			acordoNivelServicoHistoricoDao.create(acordoNivelServicoHistoricoDTO);
			
			transactionControler.commit();
			transactionControler.close();
			
		}catch(Exception e){
			e.printStackTrace();
			this.rollbackTransaction(transactionControler, e);
			throw new ServiceException(e.getMessage());
		}
		
		return acordoNivelServicoDTO;
		
	}
	
	public AcordoNivelServicoDTO update(AcordoNivelServicoDTO acordoNivelServicoDTO, AcordoNivelServicoHistoricoDTO acordoNivelServicoHistoricoDTO) throws ServiceException, LogicException {
		
		TransactionControler transactionControler = new TransactionControlerImpl(this.getAcordoNivelServicoDao().getAliasDB());
		
		AcordoNivelServicoDao acordoNivelServicoDao = new AcordoNivelServicoDao();
		TempoAcordoNivelServicoDao tempoAcordoNivelServicoDao = new TempoAcordoNivelServicoDao();
		PrioridadeAcordoNivelServicoDao prioridadeAcordoNivelServicoDao = new PrioridadeAcordoNivelServicoDao();
		PrioridadeServicoUsuarioDao prioridadeServicoUsuarioDao = new PrioridadeServicoUsuarioDao();
		SlaRequisitoSLADao slaRequisitoSlaDao = new SlaRequisitoSLADao();
		AcordoNivelServicoHistoricoDao acordoNivelServicoHistoricoDao = new AcordoNivelServicoHistoricoDao();
		RevisarSlaDao revisarSlaDao = new RevisarSlaDao();
		
		Integer idAcordoNivelServico = acordoNivelServicoDTO.getIdAcordoNivelServico();
		
		try{
			acordoNivelServicoDao.setTransactionControler(transactionControler);
			tempoAcordoNivelServicoDao.setTransactionControler(transactionControler);	
			prioridadeAcordoNivelServicoDao.setTransactionControler(transactionControler);
			prioridadeServicoUsuarioDao.setTransactionControler(transactionControler);
			slaRequisitoSlaDao.setTransactionControler(transactionControler);
			acordoNivelServicoHistoricoDao.setTransactionControler(transactionControler);
			revisarSlaDao.setTransactionControler(transactionControler);
			
			transactionControler.start();
			
			acordoNivelServicoDao.updateNotNull(acordoNivelServicoDTO);
			
			tempoAcordoNivelServicoDao.deleteByIdAcordo(idAcordoNivelServico);
			
			for(int i = 1; i <= 5; i++){
				TempoAcordoNivelServicoDTO tempoAcordoNivelServicoDTO = new TempoAcordoNivelServicoDTO();
				tempoAcordoNivelServicoDTO.setIdAcordoNivelServico(idAcordoNivelServico);
				tempoAcordoNivelServicoDTO.setIdFase(1);
				tempoAcordoNivelServicoDTO.setIdPrioridade(i);
				tempoAcordoNivelServicoDTO.setTempoHH(acordoNivelServicoDTO.getHhCaptura()[i - 1]);
				tempoAcordoNivelServicoDTO.setTempoMM(acordoNivelServicoDTO.getMmCaptura()[i - 1]);
				tempoAcordoNivelServicoDao.create(tempoAcordoNivelServicoDTO);
			}
			for(int i = 1; i <= 5; i++){
				TempoAcordoNivelServicoDTO tempoAcordoNivelServicoDTO = new TempoAcordoNivelServicoDTO();
				tempoAcordoNivelServicoDTO.setIdAcordoNivelServico(idAcordoNivelServico);
				tempoAcordoNivelServicoDTO.setIdFase(2);
				tempoAcordoNivelServicoDTO.setIdPrioridade(i);
				tempoAcordoNivelServicoDTO.setTempoHH(acordoNivelServicoDTO.getHhResolucao()[i - 1]);
				tempoAcordoNivelServicoDTO.setTempoMM(acordoNivelServicoDTO.getMmResolucao()[i - 1]);
				tempoAcordoNivelServicoDao.create(tempoAcordoNivelServicoDTO);
			}
			
			//Atualiza prioridade unidades
			List<PrioridadeAcordoNivelServicoDTO> listaPrioridadeUnidadeAtual = (List<PrioridadeAcordoNivelServicoDTO>) prioridadeAcordoNivelServicoDao.findByIdAcordoNivelServico(idAcordoNivelServico);
			if(listaPrioridadeUnidadeAtual != null && listaPrioridadeUnidadeAtual.size() > 0){
				for (PrioridadeAcordoNivelServicoDTO prioridadeAcordoNivelServicoDTO : listaPrioridadeUnidadeAtual) {
					if (prioridadeAcordoNivelServicoDTO.getDataFim() == null){
						prioridadeAcordoNivelServicoDTO.setDataFim(UtilDatas.getDataAtual());
						prioridadeAcordoNivelServicoDao.update(prioridadeAcordoNivelServicoDTO);
					}
				}
			}
			List<PrioridadeAcordoNivelServicoDTO> colUnidades = acordoNivelServicoDTO.getListaPrioridadeUnidade();
			if(colUnidades != null && colUnidades.size() > 0){
				for (PrioridadeAcordoNivelServicoDTO prioridadeAcordoNivelServicoDTO : colUnidades) {
					prioridadeAcordoNivelServicoDTO.setIdAcordoNivelServico(idAcordoNivelServico);
					prioridadeAcordoNivelServicoDTO.setDataInicio(UtilDatas.getDataAtual());
					prioridadeAcordoNivelServicoDTO.setDataFim(null);
					PrioridadeAcordoNivelServicoDTO prioridadeAcordoNivelServicoAux = (PrioridadeAcordoNivelServicoDTO) prioridadeAcordoNivelServicoDao.restore(prioridadeAcordoNivelServicoDTO);
					if (prioridadeAcordoNivelServicoAux != null){
						prioridadeAcordoNivelServicoDao.update(prioridadeAcordoNivelServicoDTO);        				
					}else{
						prioridadeAcordoNivelServicoDao.create(prioridadeAcordoNivelServicoDTO);
					}
				}
			}
			
			//Atualiza prioridade usuários
			List<PrioridadeServicoUsuarioDTO> listaPrioridadeServicoUsuarioAtual = (List<PrioridadeServicoUsuarioDTO>) prioridadeServicoUsuarioDao.findByIdAcordoNivelServico(idAcordoNivelServico);
			if(listaPrioridadeServicoUsuarioAtual != null && listaPrioridadeServicoUsuarioAtual.size() > 0){
				for (PrioridadeServicoUsuarioDTO prioridadeServicoUsuarioDTO : listaPrioridadeServicoUsuarioAtual) {
					if(prioridadeServicoUsuarioDTO.getDataFim() == null){
						prioridadeServicoUsuarioDTO.setDataFim(UtilDatas.getDataAtual());
						prioridadeServicoUsuarioDao.update(prioridadeServicoUsuarioDTO);
					}
				}
			}
			List<PrioridadeServicoUsuarioDTO> colUsuarios = acordoNivelServicoDTO.getListaPrioridadeUsuario();
			if(colUsuarios != null && colUsuarios.size() > 0){
				for (PrioridadeServicoUsuarioDTO prioridadeServicoUsuarioDTO : colUsuarios) {
					prioridadeServicoUsuarioDTO.setIdAcordoNivelServico(idAcordoNivelServico);
					prioridadeServicoUsuarioDTO.setDataInicio(UtilDatas.getDataAtual());
					prioridadeServicoUsuarioDTO.setDataFim(null);
					PrioridadeServicoUsuarioDTO prioridadeServicoUsuarioAux = (PrioridadeServicoUsuarioDTO) prioridadeServicoUsuarioDao.restore(prioridadeServicoUsuarioDTO);
					if(prioridadeServicoUsuarioAux != null){
						prioridadeServicoUsuarioDao.update(prioridadeServicoUsuarioDTO);
					}else{
						prioridadeServicoUsuarioDao.create(prioridadeServicoUsuarioDTO);
					}
				}
			}
			
			//Atualiza requisitoSla
			slaRequisitoSlaDao.deleteByIdAcordoNivelServico(idAcordoNivelServico);
			List<SlaRequisitoSlaDTO> colSlaRequisitoSla = acordoNivelServicoDTO.getListaSlaRequisitoSlaDTO();
			if (colSlaRequisitoSla != null && colSlaRequisitoSla.size() > 0){
				for (SlaRequisitoSlaDTO slaRequisitoSlaDTO : colSlaRequisitoSla) {
					slaRequisitoSlaDTO.setIdAcordoNivelServico(idAcordoNivelServico);
					slaRequisitoSlaDTO.setDataUltModificacao(UtilDatas.getDataAtual());
					slaRequisitoSlaDao.create(slaRequisitoSlaDTO);
				}
			}
			
			//Atualiza revisarSla
			revisarSlaDao.deleteByIdAcordoNivelServico(idAcordoNivelServico);
			List<RevisarSlaDTO> colRevisarSla = acordoNivelServicoDTO.getListaRevisarSlaDTO();
			if (colRevisarSla != null && colRevisarSla.size() > 0){
				for (RevisarSlaDTO revisarSlaDTO : colRevisarSla) {
					revisarSlaDTO.setIdAcordoNivelServico(idAcordoNivelServico);
					revisarSlaDao.create(revisarSlaDTO);
				}
			}
			
			AcordoNivelServicoDTO acordoNivelServicoAux = new AcordoNivelServicoDTO(); 
			
			acordoNivelServicoAux.setTempoAuto(acordoNivelServicoDTO.getTempoAuto());
			acordoNivelServicoAux.setIdPrioridadeAuto1(acordoNivelServicoDTO.getIdPrioridadeAuto1());
			acordoNivelServicoAux.setIdGrupo1(acordoNivelServicoDTO.getIdGrupo1());
			acordoNivelServicoAux.setIdAcordoNivelServico(idAcordoNivelServico);
			
			acordoNivelServicoDao.updateTemposAcoes(acordoNivelServicoAux);
			
			acordoNivelServicoHistoricoDTO.setIdAcordoNivelServico(idAcordoNivelServico);
			acordoNivelServicoHistoricoDao.create(acordoNivelServicoHistoricoDTO);
			
			transactionControler.commit();
			transactionControler.close();
			
		}catch(Exception e){
			e.printStackTrace();
			this.rollbackTransaction(transactionControler, e);
			throw new ServiceException(e.getMessage());
		}
		
		return acordoNivelServicoDTO;
	}

	public void excluir(AcordoNivelServicoDTO acordoNivelServicoDTO) throws Exception {
		
		TransactionControler transactionControler = new TransactionControlerImpl(this.getAcordoNivelServicoDao().getAliasDB());
		
		AcordoNivelServicoDao acordoNivelServicoDao = new AcordoNivelServicoDao();
		TempoAcordoNivelServicoDao tempoAcordoNivelServicoDao = new TempoAcordoNivelServicoDao();
		PrioridadeAcordoNivelServicoDao prioridadeAcordoNivelServicoDao = new PrioridadeAcordoNivelServicoDao();
		PrioridadeServicoUsuarioDao prioridadeServicoUsuarioDao = new PrioridadeServicoUsuarioDao();
		SlaRequisitoSLADao slaRequisitoSlaDao = new SlaRequisitoSLADao();
		AcordoNivelServicoHistoricoDao acordoNivelServicoHistoricoDao = new AcordoNivelServicoHistoricoDao();
		RevisarSlaDao revisarSlaDao = new RevisarSlaDao();
		
		Integer idAcordoNivelServico = acordoNivelServicoDTO.getIdAcordoNivelServico();
		
		try{
			acordoNivelServicoDao.setTransactionControler(transactionControler);
			tempoAcordoNivelServicoDao.setTransactionControler(transactionControler);	
			prioridadeAcordoNivelServicoDao.setTransactionControler(transactionControler);
			prioridadeServicoUsuarioDao.setTransactionControler(transactionControler);
			slaRequisitoSlaDao.setTransactionControler(transactionControler);
			acordoNivelServicoHistoricoDao.setTransactionControler(transactionControler);
			revisarSlaDao.setTransactionControler(transactionControler);
			
			transactionControler.start();
			
			acordoNivelServicoDTO.setDeleted("y");
			acordoNivelServicoDao.update(acordoNivelServicoDTO);
			
			tempoAcordoNivelServicoDao.deleteByIdAcordo(idAcordoNivelServico);
			
			List<PrioridadeAcordoNivelServicoDTO> listaPrioridadeUnidadeAtual = (List<PrioridadeAcordoNivelServicoDTO>) prioridadeAcordoNivelServicoDao.findByIdAcordoNivelServico(idAcordoNivelServico);
			if(listaPrioridadeUnidadeAtual != null && listaPrioridadeUnidadeAtual.size() > 0){
				for (PrioridadeAcordoNivelServicoDTO prioridadeAcordoNivelServicoDTO : listaPrioridadeUnidadeAtual) {
					if (prioridadeAcordoNivelServicoDTO.getDataFim() == null){
						prioridadeAcordoNivelServicoDTO.setDataFim(UtilDatas.getDataAtual());
						prioridadeAcordoNivelServicoDao.update(prioridadeAcordoNivelServicoDTO);
					}
				}
			}
			
			List<PrioridadeServicoUsuarioDTO> listaPrioridadeServicoUsuarioAtual = (List<PrioridadeServicoUsuarioDTO>) prioridadeServicoUsuarioDao.findByIdAcordoNivelServico(idAcordoNivelServico);
			if(listaPrioridadeServicoUsuarioAtual != null && listaPrioridadeServicoUsuarioAtual.size() > 0){
				for (PrioridadeServicoUsuarioDTO prioridadeServicoUsuarioDTO : listaPrioridadeServicoUsuarioAtual) {
					if(prioridadeServicoUsuarioDTO.getDataFim() == null){
						prioridadeServicoUsuarioDTO.setDataFim(UtilDatas.getDataAtual());
						prioridadeServicoUsuarioDao.update(prioridadeServicoUsuarioDTO);
					}
				}
			}
			
			slaRequisitoSlaDao.deleteByIdAcordoNivelServico(idAcordoNivelServico);
			
			revisarSlaDao.deleteByIdAcordoNivelServico(idAcordoNivelServico);
			
			transactionControler.commit();
			transactionControler.close();
			
		}catch(Exception e){
			e.printStackTrace();
			this.rollbackTransaction(transactionControler, e);
			throw new ServiceException(e.getMessage());
		}
		
	}
			
	/**
	 * Retorna DAO de AcordoNivelServico.
	 * 
	 * @return AcordoNivelServicoDao
	 * @throws ServiceException
	 */
	public AcordoNivelServicoDao getAcordoNivelServicoDao() throws ServiceException {
		return (AcordoNivelServicoDao) this.getDao();
	}
	
	public List<AcordoNivelServicoDTO> findIdEmailByIdSolicitacaoServico(Integer idSolicitacaoServico) throws Exception{
		AcordoNivelServicoDao dao = new AcordoNivelServicoDao();
		try{
			return dao.findIdEmailByIdSolicitacaoServico(idSolicitacaoServico);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public String verificaIdAcordoNivelServico(HashMap mapFields) throws Exception {
		AcordoNivelServicoDao acordoNivelServicoDao = new AcordoNivelServicoDao();
		List<AcordoNivelServicoDTO> listaAcordoNivelServicoDTO = null;
		String id = mapFields.get("IDACORDONIVELSERVICO").toString().trim();
		if ((id==null)||(id.equals(""))){
			id="0";
		}
		if (UtilStrings.soContemNumeros(id)) {
			Integer idAcordoNivelServico = (Integer) Integer.parseInt(id);
			listaAcordoNivelServicoDTO = acordoNivelServicoDao.findByIdAcordoSemVinculacaoDireta(idAcordoNivelServico);
		} else {
			listaAcordoNivelServicoDTO = acordoNivelServicoDao.findByTituloSLA(id);
		}
		if((listaAcordoNivelServicoDTO != null)&&(listaAcordoNivelServicoDTO.size()>0)){
			return String.valueOf(listaAcordoNivelServicoDTO.get(0).getIdAcordoNivelServico());
		}else{
			return "0";
		}
	}

}