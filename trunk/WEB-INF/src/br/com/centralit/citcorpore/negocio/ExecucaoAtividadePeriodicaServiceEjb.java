package br.com.centralit.citcorpore.negocio;
import java.io.File;
import java.sql.Date;
import java.util.Collection;
import java.util.Iterator;

import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.bean.ExecucaoAtividadePeriodicaDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.integracao.AnexoDao;
import br.com.centralit.citcorpore.integracao.ExecucaoAtividadePeriodicaDao;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.integracao.ControleGEDDao;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;
public class ExecucaoAtividadePeriodicaServiceEjb extends CrudServicePojoImpl implements ExecucaoAtividadePeriodicaService {
	protected CrudDAO getDao() throws ServiceException {
		return new ExecucaoAtividadePeriodicaDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
	    ExecucaoAtividadePeriodicaDTO execucaoAtividadePeriodicaDto = (ExecucaoAtividadePeriodicaDTO) arg0;
        if (!execucaoAtividadePeriodicaDto.getSituacao().equals("S")) {
            execucaoAtividadePeriodicaDto.setIdMotivoSuspensao(null);
            execucaoAtividadePeriodicaDto.setComplementoMotivoSuspensao(null);
        }
	}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {
	    validaCreate(arg0);
	}
	
	public Collection findByIdAtividadePeriodica(Integer idAtividadePeriodicaParm) throws Exception {
		ExecucaoAtividadePeriodicaDao dao = new ExecucaoAtividadePeriodicaDao();
		try{
			return dao.findByIdAtividadePeriodica(idAtividadePeriodicaParm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}	    
	}

	public void deleteByIdAtividadePeriodica(Integer parm) throws Exception{
		ExecucaoAtividadePeriodicaDao dao = new ExecucaoAtividadePeriodicaDao();
		try{
			dao.deleteByIdAtividadePeriodica(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdEmpregado(Integer parm) throws Exception{
		ExecucaoAtividadePeriodicaDao dao = new ExecucaoAtividadePeriodicaDao();
		try{
			dao.deleteByIdEmpregado(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
    public IDto create( IDto model) throws ServiceException, LogicException{
        //Instancia Objeto controlador de transacao
        CrudDAO crudDao = getDao();
        AnexoDao anexoDao = new AnexoDao();
        TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        try{
            //Faz validacao, caso exista.
            validaCreate(model);
            
            //Instancia ou obtem os DAOs necessarios.
            
            
            //Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);
            anexoDao.setTransactionControler(tc);
            
            //Inicia transacao
            tc.start();
            
            //Executa operacoes pertinentes ao negocio.
            model = crudDao.create(model);
            ExecucaoAtividadePeriodicaDTO execucaoAtividadeDto = (ExecucaoAtividadePeriodicaDTO)model;
            
            gravaInformacoesGED(execucaoAtividadeDto.getColArquivosUpload(), 1, execucaoAtividadeDto);
            
            //Faz commit e fecha a transacao.
            tc.commit();
            tc.close();
            

            return model;
        }catch(Exception e){
            this.rollbackTransaction(tc, e);
        }
        return model;
    }
    
    public void update( IDto model) throws ServiceException, LogicException{
        //Instancia Objeto controlador de transacao
        CrudDAO crudDao = getDao();
        AnexoDao anexoDao = new AnexoDao();
        TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        try{
            //Faz validacao, caso exista.
            validaUpdate(model);

            //Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);
            anexoDao.setTransactionControler(tc);
            
            //Inicia transacao
            tc.start();
            
            //Executa operacoes pertinentes ao negocio.
            crudDao.update(model);
            ExecucaoAtividadePeriodicaDTO execucaoAtividadeDto = (ExecucaoAtividadePeriodicaDTO)model;
            gravaInformacoesGED(execucaoAtividadeDto.getColArquivosUpload(), 1, execucaoAtividadeDto);
            
            //Faz commit e fecha a transacao.
            tc.commit();
            tc.close();
            

        }catch(Exception e){
            this.rollbackTransaction(tc, e);
        }       
    }
    
    public IDto restore(IDto model) throws ServiceException, LogicException{
        try{
            IDto obj = getDao().restore(model);
            ExecucaoAtividadePeriodicaDTO execucaoAtividadeDto = (ExecucaoAtividadePeriodicaDTO)obj;
            ControleGEDDao controleGedDao = new ControleGEDDao();
            Collection col = controleGedDao.listByIdTabelaAndID(ControleGEDDTO.TABELA_EXECUCAOATIVIDADE, execucaoAtividadeDto.getIdExecucaoAtividadePeriodica());
            execucaoAtividadeDto.setColArquivosUpload(col);
            return obj;
        }catch(LogicException e){
            throw new ServiceException(e);
        }catch(Exception e){
            throw new ServiceException(e);
        }       
    }       
	public void gravaInformacoesGED(Collection colArquivosUpload, int idEmpresa, ExecucaoAtividadePeriodicaDTO execucaoAtividadeDto) throws Exception {
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
		ControleGEDService controleGEDService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
		String pasta = "";
		if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")) {
			pasta = controleGEDService.getProximaPastaArmazenar();
			File fileDir = new File(PRONTUARIO_GED_DIRETORIO);
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
			fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa);
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
			fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta);
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
		}
		for (Iterator it = colArquivosUpload.iterator(); it.hasNext();) {
			UploadDTO uploadDTO = (UploadDTO) it.next();
			if (!uploadDTO.getTemporario().equalsIgnoreCase("S")) { // Se nao
																	// for
																	// temporario
				continue;
			}
			ControleGEDDTO controleGEDDTO = new ControleGEDDTO();
			controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_EXECUCAOATIVIDADE);
			controleGEDDTO.setId(execucaoAtividadeDto.getIdExecucaoAtividadePeriodica());
			controleGEDDTO.setDataHora(UtilDatas.getDataAtual());
			controleGEDDTO.setDescricaoArquivo(uploadDTO.getDescricao());
			controleGEDDTO.setExtensaoArquivo(Util.getFileExtension(uploadDTO.getNameFile()));
			controleGEDDTO.setPasta(pasta);
			controleGEDDTO.setNomeArquivo(uploadDTO.getNameFile());

			if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && "S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) { // Se
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

			controleGEDDTO = (ControleGEDDTO) controleGEDService.create(controleGEDDTO);
			if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && !"S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) { // Se
																															// utiliza
																															// GED
				// interno e nao eh BD
				if (controleGEDDTO != null) {
					File arquivo = new File(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + "." + Util.getFileExtension(uploadDTO.getNameFile()));
					CriptoUtils.encryptFile(uploadDTO.getPath(), PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged", System.getProperties()
							.get("user.dir") + Constantes.getValue("CAMINHO_CHAVE_PUBLICA"));

					arquivo.delete();
				}
			} /*else if (!PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")) { // Se
																		// utiliza
																		// GED
																		// externo
				// FALTA IMPLEMENTAR!!!
			}*/
		}
	}
	public Collection findBlackoutByIdMudancaAndPeriodo(Integer idMudanca, Date dataInicio, Date dataFim) throws Exception{
		ExecucaoAtividadePeriodicaDao dao = new ExecucaoAtividadePeriodicaDao();
		return dao.findBlackoutByIdMudancaAndPeriodo(idMudanca, dataInicio, dataFim);
	}
}
