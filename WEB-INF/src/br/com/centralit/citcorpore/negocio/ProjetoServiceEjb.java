package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AssinaturaAprovacaoProjetoDTO;
import br.com.centralit.citcorpore.bean.OSDTO;
import br.com.centralit.citcorpore.bean.ProjetoDTO;
import br.com.centralit.citcorpore.bean.RecursoProjetoDTO;
import br.com.centralit.citcorpore.integracao.AssinaturaAprovacaoProjetoDao;
import br.com.centralit.citcorpore.integracao.OSDao;
import br.com.centralit.citcorpore.integracao.ProjetoDao;
import br.com.centralit.citcorpore.integracao.RecursoProjetoDao;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings({"rawtypes","unchecked"})
public class ProjetoServiceEjb extends CrudServicePojoImpl implements ProjetoService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4101073129183404628L;

	protected CrudDAO getDao() throws ServiceException {
		return new ProjetoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	protected void validaFind(Object obj) throws Exception {
	}

	public Collection list(List ordenacao) throws LogicException, RemoteException, ServiceException {
		return null;
	}

	public Collection list(String ordenacao) throws LogicException, RemoteException, ServiceException {
		return null;
	}
	
	public Collection findByIdCliente(Integer parm) throws Exception {
	    return new ProjetoDao().findByIdCliente(parm);
	}
	
    public Collection listHierarquia(Integer idCliente, boolean acrescentarInativos) throws Exception{
        ProjetoDao projetoDao = new ProjetoDao();
        Collection<ProjetoDTO> colSemPai = projetoDao.findSemPai(idCliente);
        if (colSemPai == null) return null;
        
        Collection colRetorno = new ArrayList();
        boolean bAcrescenta;
        for (ProjetoDTO projetoDto : colSemPai) {
            bAcrescenta = true;
            if (!acrescentarInativos && !projetoDto.getSituacao().equalsIgnoreCase("A"))
               bAcrescenta = false;
            if (bAcrescenta){
                projetoDto.setNivel(new Integer(0));
                colRetorno.add(projetoDto);
            
                Collection colFilhos = carregaFilhos(projetoDto.getIdProjeto(), 0, idCliente, acrescentarInativos);
                if (colFilhos != null)
                    colRetorno.addAll(colFilhos);
            }
        }
        return colRetorno;
    }
    
    private Collection carregaFilhos(Integer idPai, int nivel, Integer idCliente, boolean acrescentarInativos) throws Exception{
        ProjetoDao projetoDao = new ProjetoDao();
        Collection<ProjetoDTO> colFilhos = projetoDao.findByIdPai(idPai, idCliente);
        if (colFilhos == null) return null;
        
        Collection colRetorno = new ArrayList();
        
        boolean bAcrescenta;
        for (ProjetoDTO projetoDto : colFilhos) {
            bAcrescenta = true;
            if (!acrescentarInativos && !projetoDto.getSituacao().equalsIgnoreCase("A"))
                bAcrescenta = false; 
            if (bAcrescenta){
                projetoDto.setNivel(new Integer(nivel + 1));
                colRetorno.add(projetoDto);
                
                Collection colFilhosFilhos = carregaFilhos(projetoDto.getIdProjeto(), nivel + 1, idCliente, acrescentarInativos);
                if (colFilhosFilhos != null)
                    colRetorno.addAll(colFilhosFilhos);
            }
        }       
        return colRetorno;
    }

	@Override
	public IDto create(IDto model) throws ServiceException, LogicException {
		//Instancia Objeto controlador de transacao
		ProjetoDao crudDao = (ProjetoDao)getDao();
		RecursoProjetoDao recursoProjetoDao = new RecursoProjetoDao();
		AssinaturaAprovacaoProjetoDao assinaturaAprovacaoProjetoDao = new AssinaturaAprovacaoProjetoDao();
		OSDao osDao = new OSDao();
		TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
		try{
			//Faz validacao, caso exista.
			validaCreate(model);
			
			//Instancia ou obtem os DAOs necessarios.
			
			
			//Seta o TransactionController para os DAOs
			crudDao.setTransactionControler(tc);
			recursoProjetoDao.setTransactionControler(tc);
			assinaturaAprovacaoProjetoDao.setTransactionControler(tc);
			osDao.setTransactionControler(tc);
			
			//Inicia transacao
			tc.start();
			
			//Executa operacoes pertinentes ao negocio.
			model = crudDao.create(model);
			ProjetoDTO projetoDTO = (ProjetoDTO)model;
			
			if(projetoDTO.getVinculoOS() != null && projetoDTO.getVinculoOS().equalsIgnoreCase("S"))
				this.validaObrigatoriedade(projetoDTO);
			
			if (projetoDTO.getColRecursos() != null){
				for (Iterator it = projetoDTO.getColRecursos().iterator(); it.hasNext();){
					RecursoProjetoDTO recursoProjetoDTO = (RecursoProjetoDTO)it.next();
					recursoProjetoDTO.setIdProjeto(projetoDTO.getIdProjeto());
					if (recursoProjetoDTO.getCustoHora() == null){
						recursoProjetoDTO.setCustoHora(new Double(0));
					}
					recursoProjetoDao.create(recursoProjetoDTO);
				}
			}
			
			if (projetoDTO.getColAssinaturasAprovacoes() != null){
				for (Iterator it = projetoDTO.getColAssinaturasAprovacoes().iterator(); it.hasNext();){
					AssinaturaAprovacaoProjetoDTO assinaturaAprovacaoProjetoDTO = (AssinaturaAprovacaoProjetoDTO)it.next();
					assinaturaAprovacaoProjetoDTO.setIdProjeto(projetoDTO.getIdProjeto());
					if (assinaturaAprovacaoProjetoDTO.getPapel() == null){
						assinaturaAprovacaoProjetoDTO.setPapel(" ");
					}
					if (assinaturaAprovacaoProjetoDTO.getOrdem() == null){
						assinaturaAprovacaoProjetoDTO.setOrdem(" ");
					}
					assinaturaAprovacaoProjetoDao.create(assinaturaAprovacaoProjetoDTO);
				}
			}
			
			if (projetoDTO.getVinculoOS() != null && projetoDTO.getVinculoOS().equalsIgnoreCase("S")){
				OSDTO osDto = new OSDTO();
				osDto.setIdContrato(projetoDTO.getIdContrato());
				osDto.setNumero(projetoDTO.getNumero());
				osDto.setIdServicoContrato(projetoDTO.getIdServicoContrato());
				osDto.setAno(projetoDTO.getAno());
				osDto.setNomeAreaRequisitante(projetoDTO.getNomeAreaRequisitante());
				osDto.setDemanda(projetoDTO.getDemanda());
				osDto.setObjetivo(projetoDTO.getObjetivo());
				osDto.setSituacaoOS(OSDTO.EM_CRIACAO);
				osDto.setDataInicio(UtilDatas.getDataAtual());
				osDto.setDataFim(UtilDatas.getDataAtual());
				osDto.setDataEmissao(projetoDTO.getDataEmissao());
				osDto = (OSDTO) osDao.create(osDto);
				
				ProjetoDTO projetoAux = new ProjetoDTO();
				projetoAux = (ProjetoDTO) crudDao.restore(model);
				projetoAux.setIdProjeto(projetoDTO.getIdProjeto());
				projetoAux.setIdOs(osDto.getIdOS());
				crudDao.updateNotNull(projetoAux);
			}			
			
			//Faz commit e fecha a transacao.
			tc.commit();
			tc.close();
			tc = null;

			return model;
		}catch(Exception e){
			this.rollbackTransaction(tc, e);
		}
		return model;
	}

	@Override
	public void update(IDto model) throws ServiceException, LogicException {
		//Instancia Objeto controlador de transacao
		ProjetoDao crudDao = (ProjetoDao)getDao();
		RecursoProjetoDao recursoProjetoDao = new RecursoProjetoDao();
		AssinaturaAprovacaoProjetoDao assinaturaAprovacaoProjetoDao = new AssinaturaAprovacaoProjetoDao();
		OSDao osDao = new OSDao();
		TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
		try{
			//Faz validacao, caso exista.
			validaUpdate(model);

			//Seta o TransactionController para os DAOs
			crudDao.setTransactionControler(tc);
			recursoProjetoDao.setTransactionControler(tc);
			assinaturaAprovacaoProjetoDao.setTransactionControler(tc);
			osDao.setTransactionControler(tc);
			
			//Inicia transacao
			tc.start();
			
			//Executa operacoes pertinentes ao negocio.
			ProjetoDTO projetoDTO = (ProjetoDTO)model;
			
			if(projetoDTO.getVinculoOS() != null && projetoDTO.getVinculoOS().equalsIgnoreCase("S"))
				this.validaObrigatoriedade(projetoDTO);
			
			Integer idOs = null;
			if (projetoDTO.getIdOs() != null){
				idOs = new Integer(projetoDTO.getIdOs());
			}
			if (projetoDTO.getVinculoOS() == null || !projetoDTO.getVinculoOS().equalsIgnoreCase("S")){
				projetoDTO.setIdOs(null);
			}
			crudDao.update(model);
			recursoProjetoDao.deleteByIdProjeto(projetoDTO.getIdProjeto());
			if (projetoDTO.getColRecursos() != null){
				for (Iterator it = projetoDTO.getColRecursos().iterator(); it.hasNext();){
					RecursoProjetoDTO recursoProjetoDTO = (RecursoProjetoDTO)it.next();
					recursoProjetoDTO.setIdProjeto(projetoDTO.getIdProjeto());
					if (recursoProjetoDTO.getCustoHora() == null){
						recursoProjetoDTO.setCustoHora(new Double(0));
					}
					recursoProjetoDao.create(recursoProjetoDTO);
				}
			}
			
			assinaturaAprovacaoProjetoDao.deleteByIdProjeto(projetoDTO.getIdProjeto());
			if (projetoDTO.getColAssinaturasAprovacoes() != null){
				for (Iterator it = projetoDTO.getColAssinaturasAprovacoes().iterator(); it.hasNext();){
					AssinaturaAprovacaoProjetoDTO assinaturaAprovacaoProjetoDTO = (AssinaturaAprovacaoProjetoDTO)it.next();
					assinaturaAprovacaoProjetoDTO.setIdProjeto(projetoDTO.getIdProjeto());
					if (assinaturaAprovacaoProjetoDTO.getPapel() == null){
						assinaturaAprovacaoProjetoDTO.setPapel(" ");
					}
					if (assinaturaAprovacaoProjetoDTO.getOrdem() == null){
						assinaturaAprovacaoProjetoDTO.setOrdem(" ");
					}
					assinaturaAprovacaoProjetoDao.create(assinaturaAprovacaoProjetoDTO);
				}
			}
			
			if (projetoDTO.getVinculoOS() != null && projetoDTO.getVinculoOS().equalsIgnoreCase("S")){
				if (projetoDTO.getIdOs() == null){
					OSDTO osDto = new OSDTO();
					osDto.setIdContrato(projetoDTO.getIdContrato());
					osDto.setNumero(projetoDTO.getNumero());
					osDto.setIdServicoContrato(projetoDTO.getIdServicoContrato());
					osDto.setAno(projetoDTO.getAno());
					osDto.setNomeAreaRequisitante(projetoDTO.getNomeAreaRequisitante());
					osDto.setDemanda(projetoDTO.getDemanda());
					osDto.setObjetivo(projetoDTO.getObjetivo());
					osDto.setSituacaoOS(OSDTO.EM_CRIACAO);
					osDto.setDataEmissao(projetoDTO.getDataEmissao());
					osDto.setDataInicio(UtilDatas.getDataAtual());
					osDto.setDataFim(UtilDatas.getDataAtual());
					osDto = (OSDTO) osDao.create(osDto);
					
					ProjetoDTO projetoAux = new ProjetoDTO();
					projetoAux = (ProjetoDTO) crudDao.restore(model);
					projetoAux.setIdProjeto(projetoDTO.getIdProjeto());
					projetoAux.setIdOs(osDto.getIdOS());
					crudDao.updateNotNull(projetoAux);
				}else{
					OSDTO osDto = new OSDTO();
					osDto.setIdOS(projetoDTO.getIdOs());
					osDto = (OSDTO) osDao.restore(osDto);
					if (osDto != null){
						osDto.setIdContrato(projetoDTO.getIdContrato());
						osDto.setNumero(projetoDTO.getNumero());
						osDto.setIdServicoContrato(projetoDTO.getIdServicoContrato());
						osDto.setAno(projetoDTO.getAno());
						osDto.setNomeAreaRequisitante(projetoDTO.getNomeAreaRequisitante());
						osDto.setDemanda(projetoDTO.getDemanda());
						osDto.setObjetivo(projetoDTO.getObjetivo());
						osDto.setDataEmissao(projetoDTO.getDataEmissao());
						osDao.update(osDto);
					}
				}
			}else{
				if (idOs != null){
					OSDTO osDto = new OSDTO();
					osDto.setIdOS(idOs);					
					osDao.delete(osDto);
				}
			}
			
			//Faz commit e fecha a transacao.
			tc.commit();
			tc.close();
			tc = null;

		}catch(Exception e){
			this.rollbackTransaction(tc, e);
		}	
	}
    
	private void validaObrigatoriedade(ProjetoDTO projetoDTO) throws LogicException {
		if(projetoDTO.getIdServicoContrato() == null){
			throw new LogicException(i18n_Message("projeto.obrigatorioServico"));
		}
		if(projetoDTO.getNumero() == null || projetoDTO.getNumero().equals("")){
			throw new LogicException(i18n_Message("projeto.obrigatorioNumero"));
		}
		if(projetoDTO.getNomeAreaRequisitante() == null || projetoDTO.getNomeAreaRequisitante().equals("")){
			throw new LogicException(i18n_Message("projeto.obrigatorioRequisitante"));
		}
	}

}
