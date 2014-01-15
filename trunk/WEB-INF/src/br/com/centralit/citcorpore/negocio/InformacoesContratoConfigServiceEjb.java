package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import br.com.centralit.citcorpore.bean.InformacoesContratoConfigDTO;
import br.com.centralit.citcorpore.bean.InformacoesContratoPerfSegDTO;
import br.com.centralit.citcorpore.integracao.InformacoesContratoConfigDao;
import br.com.centralit.citcorpore.integracao.InformacoesContratoPerfSegDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;

public class InformacoesContratoConfigServiceEjb extends CrudServicePojoImpl implements InformacoesContratoConfigService{

	
	private static final long serialVersionUID = 1L;

	protected CrudDAO getDao() throws ServiceException {
		
		return new InformacoesContratoConfigDao();
	}
	protected void validaCreate(Object arg0) throws Exception {
		
	}
	protected void validaDelete(Object arg0) throws Exception {
		
	}
	protected void validaFind(Object arg0) throws Exception {
		
	}
	protected void validaUpdate(Object arg0) throws Exception {
		
	}
	public Collection getAtivos() throws Exception {
		InformacoesContratoConfigDao informacoesdao = new InformacoesContratoConfigDao();
		return informacoesdao.getAtivos();
	}
	
    @Override
    public IDto create(IDto model) throws ServiceException, LogicException {
        CrudDAO dao = getDao();
        InformacoesContratoPerfSegDao daoPEPS = new InformacoesContratoPerfSegDao();
        TransactionControler tc = new TransactionControlerImpl(dao.getAliasDB());
        try{
            //Faz validacao, caso exista.
            validaCreate(model);
            
            //Seta o TransactionController para os DAOs
            dao.setTransactionControler(tc);
            daoPEPS.setTransactionControler(tc);
            
            //Inicia transacao
            tc.start();
            
            //Executa operacoes pertinentes ao negocio.
            InformacoesContratoConfigDTO contrato = (InformacoesContratoConfigDTO)model;
            contrato.setNome(contrato.getNome().toUpperCase());
            contrato = (InformacoesContratoConfigDTO)super.create(model);
            if(contrato.getPerfilSelecionado() != null && contrato.getPerfilSelecionado().length > 0){
                InformacoesContratoPerfSegDTO prontuarioSeg = new InformacoesContratoPerfSegDTO();
                prontuarioSeg.setIdInformacoesContratoConfig(contrato.getIdInformacoesContratoConfig());
                for(int i = 0; i < contrato.getPerfilSelecionado().length; i++){
                    prontuarioSeg.setIdPerfilSeguranca(contrato.getPerfilSelecionado()[i]);
                    daoPEPS.create(prontuarioSeg);
                }
            }
            
            //Faz commit e fecha a transacao.
            tc.commit();
            tc.close();
            tc = null;
            return model;
        }catch(Exception e){
            this.rollbackTransaction(tc, e);
            throw new ServiceException(e);
        }
    }
    
    @Override
    public void update(IDto model) throws ServiceException, LogicException {
        CrudDAO dao = getDao();
        InformacoesContratoConfigDTO contrato = (InformacoesContratoConfigDTO)model;
        InformacoesContratoPerfSegDao daoPEPS = new InformacoesContratoPerfSegDao();
        TransactionControler tc = new TransactionControlerImpl(dao.getAliasDB());
        try{
            //Faz validacao, caso exista.
            validaCreate(model);
            
            //Seta o TransactionController para os DAOs
            dao.setTransactionControler(tc);
            daoPEPS.setTransactionControler(tc);
            
            //Inicia transacao
            tc.start();
            
            //Executa operacoes pertinentes ao negocio.
            contrato.setNome(contrato.getNome().toUpperCase());            
            dao.update(contrato);
            daoPEPS.deleteAllByIdInformacoesContratoConfig(contrato);
            if(contrato.getPerfilSelecionado() != null && contrato.getPerfilSelecionado().length > 0){
                InformacoesContratoPerfSegDTO prontuarioSeg = new InformacoesContratoPerfSegDTO();
                prontuarioSeg.setIdInformacoesContratoConfig(contrato.getIdInformacoesContratoConfig());
                for(int i = 0; i < contrato.getPerfilSelecionado().length; i++){
                    prontuarioSeg.setIdPerfilSeguranca(contrato.getPerfilSelecionado()[i]);
                    daoPEPS.create(prontuarioSeg);
                }
            }
            
            //Faz commit e fecha a transação.
            tc.commit();
            tc.close();
            tc = null;
        }catch(Exception e){
            this.rollbackTransaction(tc, e);
            throw new ServiceException(e);
        }
    }
    
    @Override
    public IDto restore(IDto model) throws ServiceException, LogicException {
        try{
            InformacoesContratoConfigDTO contrato = (InformacoesContratoConfigDTO)super.restore(model);
            InformacoesContratoPerfSegDao daoPEPS = new InformacoesContratoPerfSegDao();
            InformacoesContratoPerfSegDTO contratoSeg = new InformacoesContratoPerfSegDTO();
            contratoSeg.setIdInformacoesContratoConfig(contrato.getIdInformacoesContratoConfig());
            Collection perfis = daoPEPS.find(contratoSeg);
            if(perfis != null && !perfis.isEmpty()){
                contratoSeg = null;
                contrato.setPerfilSelecionado(new Integer[perfis.size()]);
                int i = 0;
                for(Iterator it = perfis.iterator(); it.hasNext(); i++){
                    contratoSeg = (InformacoesContratoPerfSegDTO)it.next();
                    contrato.getPerfilSelecionado()[i] = contratoSeg.getIdPerfilSeguranca();
                }
            }
            return contrato;
        }catch(Exception e){
            throw new ServiceException(e);
        }
    }
    
	public Collection findByPai(Integer idCentroCustoPai) throws Exception{
		InformacoesContratoConfigDao dao = (InformacoesContratoConfigDao)getDao();
		return dao.findByPai(idCentroCustoPai);		
	}
	
	public Collection findByNome(String nome) throws Exception {
		InformacoesContratoConfigDao dao = (InformacoesContratoConfigDao)getDao();
		return dao.findByNome(nome);		
	}
	
	public Collection findSemPai(Integer idEmpresa) throws Exception{
		InformacoesContratoConfigDao dao = (InformacoesContratoConfigDao)getDao();
		return dao.findSemPai(idEmpresa);			
	}
	
	public Collection getCollectonHierarquica(Integer idEmpresa, boolean acrescentarInativos) throws Exception{
		Collection colSemPai = this.findSemPai(idEmpresa);
		if (colSemPai == null) return null;
		
		Collection colRetorno = new ArrayList();
		InformacoesContratoConfigDTO undDto;
		boolean bAcrescenta;
		for(Iterator it = colSemPai.iterator(); it.hasNext();){
			undDto = (InformacoesContratoConfigDTO)it.next();
			bAcrescenta = true;
			if (!acrescentarInativos){
				if (undDto.getSituacao()==null || undDto.getSituacao().equalsIgnoreCase("A")){
					bAcrescenta = false;
				}
			}
			if (bAcrescenta){
				undDto.setNivel(new Integer(0));
				colRetorno.add(undDto);
			
				Collection colFilhos = carregaFilhos(undDto.getIdInformacoesContratoConfig(), 0, acrescentarInativos);
				if (colFilhos != null){
					colRetorno.addAll(colFilhos);
				}
			}
		}
		return colRetorno;
	}
	
	private Collection carregaFilhos(Integer idPai, int nivel, boolean acrescentarInativos) throws Exception{
		Collection colFilhos = this.findByPai(idPai);
		if (colFilhos == null) return null;
		
		Collection colRetorno = new ArrayList();
		
		InformacoesContratoConfigDTO undDto;
		boolean bAcrescenta;
		for(Iterator it = colFilhos.iterator(); it.hasNext();){
			undDto = (InformacoesContratoConfigDTO)it.next();
			bAcrescenta = true;
			if (!acrescentarInativos){
				if (undDto.getSituacao()==null || undDto.getSituacao().equalsIgnoreCase("A")){
					bAcrescenta = false;
				}
			}		
			if (bAcrescenta){
				undDto.setNivel(new Integer(nivel + 1));
				colRetorno.add(undDto);
				
				Collection colFilhosFilhos = carregaFilhos(undDto.getIdInformacoesContratoConfig(), nivel + 1, acrescentarInativos);
				if (colFilhosFilhos != null){
					colRetorno.addAll(colFilhosFilhos);
				}
			}
		}		
		return colRetorno;
	}
   
	
}
