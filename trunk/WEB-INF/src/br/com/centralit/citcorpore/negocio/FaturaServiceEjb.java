package br.com.centralit.citcorpore.negocio;
import java.sql.Date;
import java.util.Collection;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.FaturaApuracaoANSDTO;
import br.com.centralit.citcorpore.bean.FaturaDTO;
import br.com.centralit.citcorpore.bean.FaturaOSDTO;
import br.com.centralit.citcorpore.bean.GlosaOSDTO;
import br.com.centralit.citcorpore.bean.OSDTO;
import br.com.centralit.citcorpore.integracao.FaturaApuracaoANSDao;
import br.com.centralit.citcorpore.integracao.FaturaDao;
import br.com.centralit.citcorpore.integracao.FaturaOSDao;
import br.com.centralit.citcorpore.integracao.GlosaOSDao;
import br.com.centralit.citcorpore.integracao.OSDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;
public class FaturaServiceEjb extends CrudServicePojoImpl implements FaturaService {
	protected CrudDAO getDao() throws ServiceException {
		return new FaturaDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdContrato(Integer parm) throws Exception{
		FaturaDao dao = new FaturaDao();
		try{
			return dao.findByIdContrato(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdContrato(Integer parm) throws Exception{
		FaturaDao dao = new FaturaDao();
		try{
			dao.deleteByIdContrato(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	public Collection findByIdContratoAndPeriodoAndSituacao(Integer idContrato, Date dataInicio, Date dataFim, String[] situacao) throws Exception {
		FaturaDao dao = new FaturaDao();
		return dao.findByIdContratoAndPeriodoAndSituacao(idContrato, dataInicio, dataFim, situacao);
	}

	@Override
	public IDto create(IDto model) throws ServiceException, LogicException {
		//Instancia Objeto controlador de transacao
		CrudDAO crudDao = getDao();
		FaturaApuracaoANSDao faturaApuracaoANSDao = new FaturaApuracaoANSDao();
		FaturaOSDao faturaOSDao = new FaturaOSDao();
		TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
		try{
			//Faz validacao, caso exista.
			validaCreate(model);
			FaturaDTO faturaDTO = (FaturaDTO)model;
			//Instancia ou obtem os DAOs necessarios.
			
			//Seta o TransactionController para os DAOs
			crudDao.setTransactionControler(tc);
			faturaApuracaoANSDao.setTransactionControler(tc);
			faturaOSDao.setTransactionControler(tc);
			
			//Inicia transacao
			tc.start();
			
			double valorGlosado = 0;
			
			//Executa operacoes pertinentes ao negocio.
			model = crudDao.create(model);
			if (faturaDTO.getColItens() != null){
				int i = 0;
				for(Iterator it = faturaDTO.getColItens().iterator(); it.hasNext();){
					i++;
					FaturaApuracaoANSDTO faturaApuracaoANSDTO = (FaturaApuracaoANSDTO)it.next();
					faturaApuracaoANSDTO.setIdFatura(faturaDTO.getIdFatura());
					faturaApuracaoANSDTO.setDataApuracao(UtilDatas.getDataAtual());
					
					if (faturaApuracaoANSDTO.getValorGlosa() != null){
					    valorGlosado = valorGlosado + faturaApuracaoANSDTO.getValorGlosa().doubleValue();
					}
					
					faturaApuracaoANSDao.create(faturaApuracaoANSDTO);
				}
			}
			
			if (faturaDTO.getIdOSFatura() != null){
				for (int i = 0; i < faturaDTO.getIdOSFatura().length; i++){
					FaturaOSDTO faturaOSDTO = new FaturaOSDTO();
					faturaOSDTO.setIdFatura(faturaDTO.getIdFatura());
					faturaOSDTO.setIdOs(faturaDTO.getIdOSFatura()[i]);
					faturaOSDao.create(faturaOSDTO);
				}
			}
			
			OSDao osSDao = new OSDao();
			GlosaOSDao glosaOSDao = new GlosaOSDao();
			Collection colOSs = osSDao.listOSByIds(faturaDTO.getIdContrato(), faturaDTO.getIdOSFatura());
			double valorPrevistoOS = 0;
			double valorExecutadoOS = 0;
			if (colOSs != null){
			    for(Iterator itOs = colOSs.iterator(); itOs.hasNext();){
				OSDTO osDto = (OSDTO)itOs.next();
//				if (osDto.getGlosaOS() != null){
//				    valorGlosado = valorGlosado + osDto.getGlosaOS().doubleValue();
//				}
				if (osDto.getCustoOS() != null){
				    valorPrevistoOS = valorPrevistoOS + osDto.getCustoOS().doubleValue();
				}
				if (osDto.getExecutadoOS() != null){
				    valorExecutadoOS = valorExecutadoOS + osDto.getExecutadoOS().doubleValue();
				}
				Collection colGlosasOS = glosaOSDao.findByIdOs(osDto.getIdOS());
				if (colGlosasOS != null){
				    for (Iterator it = colGlosasOS.iterator(); it.hasNext();){
					GlosaOSDTO glosaOSDTO = (GlosaOSDTO)it.next();
					if (glosaOSDTO.getCustoGlosa() != null){
					    valorGlosado = valorGlosado + glosaOSDTO.getCustoGlosa().doubleValue();
					}
				    }
				}
			    }
			}
			
			faturaDTO.setValorSomaGlosasOS(valorGlosado);
			faturaDTO.setValorPrevistoSomaOS(valorPrevistoOS);
			faturaDTO.setValorExecutadoSomaOS(valorExecutadoOS);
			crudDao.update(faturaDTO);
			
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
		CrudDAO crudDao = getDao();
		FaturaApuracaoANSDao faturaApuracaoANSDao = new FaturaApuracaoANSDao();
		FaturaOSDao faturaOSDao = new FaturaOSDao();
		TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
		try{
			//Faz validacao, caso exista.
			validaCreate(model);
			FaturaDTO faturaDTO = (FaturaDTO)model;
			//Instancia ou obtem os DAOs necessarios.
			
			//Seta o TransactionController para os DAOs
			crudDao.setTransactionControler(tc);
			faturaApuracaoANSDao.setTransactionControler(tc);
			faturaOSDao.setTransactionControler(tc);
			
			//Inicia transacao
			tc.start();
			
			FaturaDTO faturaAux = new FaturaDTO();
			faturaAux.setIdFatura(faturaDTO.getIdFatura());
			faturaAux = (FaturaDTO) crudDao.restore(faturaAux);
			if (faturaAux != null){
				faturaDTO.setDataCriacao(faturaAux.getDataCriacao());
			}
			
			double valorGlosado = 0;
			
			//Executa operacoes pertinentes ao negocio.
			crudDao.update(model);
			faturaApuracaoANSDao.deleteByIdFatura(faturaDTO.getIdFatura());
			if (faturaDTO.getColItens() != null){
				int i = 0;
				for(Iterator it = faturaDTO.getColItens().iterator(); it.hasNext();){
					i++;
					FaturaApuracaoANSDTO faturaApuracaoANSDTO = (FaturaApuracaoANSDTO)it.next();
					faturaApuracaoANSDTO.setIdFatura(faturaDTO.getIdFatura());
					faturaApuracaoANSDTO.setDataApuracao(UtilDatas.getDataAtual());
					
					if (faturaApuracaoANSDTO.getValorGlosa() != null){
					    valorGlosado = valorGlosado + faturaApuracaoANSDTO.getValorGlosa().doubleValue();
					}
					
					faturaApuracaoANSDao.create(faturaApuracaoANSDTO);
				}
			}
			
			faturaOSDao.deleteByIdFatura(faturaDTO.getIdFatura());
			if (faturaDTO.getIdOSFatura() != null){
				for (int i = 0; i < faturaDTO.getIdOSFatura().length; i++){
					FaturaOSDTO faturaOSDTO = new FaturaOSDTO();
					faturaOSDTO.setIdFatura(faturaDTO.getIdFatura());
					faturaOSDTO.setIdOs(faturaDTO.getIdOSFatura()[i]);
					faturaOSDao.create(faturaOSDTO);
				}
			}
			
			OSDao osSDao = new OSDao();
			GlosaOSDao glosaOSDao = new GlosaOSDao();
			Collection colOSs = osSDao.listOSByIds(faturaDTO.getIdContrato(), faturaDTO.getIdOSFatura());
			double valorPrevistoOS = 0;
			double valorExecutadoOS = 0;
			if (colOSs != null){
			    for(Iterator itOs = colOSs.iterator(); itOs.hasNext();){
				OSDTO osDto = (OSDTO)itOs.next();
//				if (osDto.getGlosaOS() != null){
//				    valorGlosado = valorGlosado + osDto.getGlosaOS().doubleValue();
//				}
				if (osDto.getCustoOS() != null){
				    valorPrevistoOS = valorPrevistoOS + osDto.getCustoOS().doubleValue();
				}
				if (osDto.getExecutadoOS() != null){
				    valorExecutadoOS = valorExecutadoOS + osDto.getExecutadoOS().doubleValue();
				}				
				Collection colGlosasOS = glosaOSDao.findByIdOs(osDto.getIdOS());
				if (colGlosasOS != null){
				    for (Iterator it = colGlosasOS.iterator(); it.hasNext();){
					GlosaOSDTO glosaOSDTO = (GlosaOSDTO)it.next();
					if (glosaOSDTO.getCustoGlosa() != null){
					    valorGlosado = valorGlosado + glosaOSDTO.getCustoGlosa().doubleValue();
					}
				    }
				}
			    }
			}
			
			faturaDTO.setValorSomaGlosasOS(valorGlosado);
			faturaDTO.setValorPrevistoSomaOS(valorPrevistoOS);
			faturaDTO.setValorExecutadoSomaOS(valorExecutadoOS);	
			crudDao.update(faturaDTO);
			
			//Faz commit e fecha a transacao.
			tc.commit();
			tc.close();
		}catch(Exception e){
			this.rollbackTransaction(tc, e);
		}
	}
	
	public void updateSituacao(Integer idFatura, String situacao, String aprovacaoGestor, String aprovacaoFiscal) throws Exception {
	    FaturaDao faturaDao = new FaturaDao();
	    faturaDao.updateSituacao(idFatura, situacao, aprovacaoGestor, aprovacaoFiscal);
	}
	
}
