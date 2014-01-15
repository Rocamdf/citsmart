package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.AcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.AcordoServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.integracao.AcordoNivelServicoDao;
import br.com.centralit.citcorpore.integracao.AcordoServicoContratoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings("rawtypes")
public class AcordoServicoContratoServiceEjb extends CrudServicePojoImpl implements AcordoServicoContratoService {

	private static final long serialVersionUID = -2451582844101521914L;

	protected CrudDAO getDao() throws ServiceException {
		return new AcordoServicoContratoDao();
	}
	
	private AcordoServicoContratoDao getAcordoServicoContratoDao() throws ServiceException {
		return (AcordoServicoContratoDao) this.getDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaFind(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	public Collection findByIdAcordoNivelServico(Integer parm) throws Exception {
		AcordoServicoContratoDao dao = new AcordoServicoContratoDao();
		try {
			return dao.findByIdAcordoNivelServico(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByIdAcordoNivelServicoAndContrato(Integer idAcordoNivelServico, Integer idContrato) throws Exception {
		AcordoServicoContratoDao dao = new AcordoServicoContratoDao();
		try {
			 List<AcordoServicoContratoDTO> lista = dao.findBylistByIdAcordoNivelServicoAndContrato(idAcordoNivelServico, idContrato);
			 if(lista!=null) {
				 for (AcordoServicoContratoDTO acordoServicoContratoDTO : lista) {					
					 dao.deleteByIdAcordoServicoContrato(acordoServicoContratoDTO.getIdAcordoServicoContrato());
				}
			 }
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection findByIdServicoContrato(Integer parm) throws Exception {
		AcordoServicoContratoDao dao = new AcordoServicoContratoDao();
		try {
			return dao.findByIdServicoContrato(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByIdServicoContrato(Integer parm) throws Exception {
		AcordoServicoContratoDao dao = new AcordoServicoContratoDao();
		try {
			dao.deleteByIdServicoContrato(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public IDto create(IDto model) throws ServiceException, LogicException {
		
		AcordoServicoContratoDTO acordoServicoContratoDTO = (AcordoServicoContratoDTO) model;
		AcordoNivelServicoDTO acordoNivelServico = new AcordoNivelServicoDTO();
		acordoNivelServico.setIdAcordoNivelServico(acordoServicoContratoDTO.getIdAcordoNivelServico());
		TransactionControler tc = new TransactionControlerImpl(getDao().getAliasDB());
		
		AcordoNivelServicoDao acordoNivelServicoDao = new AcordoNivelServicoDao();
		try{
		    tc.start();
		    
		    acordoNivelServicoDao.setTransactionControler(tc);
			
		    if (acordoServicoContratoDTO.getListaServicoContrato() != null) {
		    	acordoNivelServico = (AcordoNivelServicoDTO) acordoNivelServicoDao.restore(acordoNivelServico);
		    	
				for (ServicoContratoDTO servicoContratoDTO : acordoServicoContratoDTO.getListaServicoContrato()) {
					
					if(acordoServicoContratoDTO.getHabilitado()!=null && acordoNivelServico.getTipo() != null && acordoNivelServico.getTipo().equalsIgnoreCase("T")) {
						List<AcordoServicoContratoDTO> acordoServicoContratoDTOs = this.getAcordoServicoContratoDao().listAtivoByIdServicoContrato(servicoContratoDTO.getIdServicoContrato(), "T");
						if(acordoServicoContratoDTOs != null) {
							for (AcordoServicoContratoDTO acordo : acordoServicoContratoDTOs) {
								acordo.setHabilitado("N");
								this.getAcordoServicoContratoDao().updateNotNull(acordo);
							}
						}		    		
					}
					
					AcordoServicoContratoDTO obj = new AcordoServicoContratoDTO();
					obj.setIdServicoContrato(servicoContratoDTO.getIdServicoContrato());
					obj.setIdAcordoNivelServico(acordoServicoContratoDTO.getIdAcordoNivelServico());
					if(acordoServicoContratoDTO.getHabilitado() != null && acordoNivelServico.getTipo() != null && acordoNivelServico.getTipo().equalsIgnoreCase("T")) {
						obj.setHabilitado("S");
					}
					obj.setDataCriacao(UtilDatas.getDataAtual());
					obj.setDataInicio(UtilDatas.getDataAtual());
					super.create(obj);
				}
			}
		    
    		tc.commit();
            tc.close();
            tc = null;
        } catch (Exception e) {
            this.rollbackTransaction(tc, e);
            throw new ServiceException(e);
		}
		
		return acordoServicoContratoDTO;		
	}
	
	@Override
	public void update(IDto model) throws ServiceException, LogicException {
		AcordoServicoContratoDTO acordoServicoContratoDTO = (AcordoServicoContratoDTO) model;
		AcordoNivelServicoDTO acordoNivelServico = new AcordoNivelServicoDTO();
		acordoNivelServico.setIdAcordoNivelServico(acordoServicoContratoDTO.getIdAcordoNivelServico());
		TransactionControler tc = new TransactionControlerImpl(getDao().getAliasDB());
		
		AcordoNivelServicoDao acordoNivelServicoDao = new AcordoNivelServicoDao();
		try{
		    tc.start();
		    
		    acordoNivelServicoDao.setTransactionControler(tc);
			
		    this.deleteByIdAcordoNivelServicoAndContrato(acordoServicoContratoDTO.getIdAcordoNivelServico(), acordoServicoContratoDTO.getIdContrato());
		    if (acordoServicoContratoDTO.getListaServicoContrato() != null) {
		    	acordoNivelServico = (AcordoNivelServicoDTO) acordoNivelServicoDao.restore(acordoNivelServico);

				for (ServicoContratoDTO servicoContratoDTO : acordoServicoContratoDTO.getListaServicoContrato()) {
					
					if(acordoServicoContratoDTO.getHabilitado() != null && acordoNivelServico.getTipo() != null && acordoNivelServico.getTipo().equalsIgnoreCase("T")) {
						List<AcordoServicoContratoDTO> acordoServicoContratoDTOs = this.getAcordoServicoContratoDao().listAtivoByIdServicoContrato(servicoContratoDTO.getIdServicoContrato(), "T");
						if(acordoServicoContratoDTOs != null) {
							for (AcordoServicoContratoDTO acordo : acordoServicoContratoDTOs) {
								acordo.setHabilitado("N");
								this.getAcordoServicoContratoDao().updateNotNull(acordo);
							}
						}		    		
					}
					
					AcordoServicoContratoDTO obj = new AcordoServicoContratoDTO();
					obj.setIdServicoContrato(servicoContratoDTO.getIdServicoContrato());
					obj.setIdAcordoNivelServico(acordoServicoContratoDTO.getIdAcordoNivelServico());
					if(acordoServicoContratoDTO.getHabilitado() != null && acordoNivelServico.getTipo() != null && acordoNivelServico.getTipo().equalsIgnoreCase("T")) {
						obj.setHabilitado("S");
					}
					obj.setDataCriacao(UtilDatas.getDataAtual());
					obj.setDataInicio(UtilDatas.getDataAtual());
					super.create(obj);
				}
			}
		    
    		tc.commit();
            tc.close();
            tc = null;
        } catch (Exception e) {
            this.rollbackTransaction(tc, e);
            throw new ServiceException(e);
		}
	}
	
	public AcordoServicoContratoDTO findAtivoByIdServicoContrato(Integer idServicoContrato, String tipo) throws Exception {
		AcordoServicoContratoDao dao = new AcordoServicoContratoDao();
		try {
			return dao.findAtivoByIdServicoContrato(idServicoContrato, tipo);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public boolean existeAcordoServicoContrato(Integer idAcordoNivelServico, Integer idContrato) throws Exception {
		AcordoServicoContratoDao dao = new AcordoServicoContratoDao();
		try {
			return dao.existeAcordoServicoContrato(idAcordoNivelServico, idContrato);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public Collection<AcordoServicoContratoDTO> findByIdAcordoNivelServicoIdServicoContrato(Integer idAcordoNivelServico, Integer idServicoContrato) throws Exception {
		AcordoServicoContratoDao dao = new AcordoServicoContratoDao();
		try {
			return dao.findByIdAcordoNivelServicoIdServicoContrato(idAcordoNivelServico, idServicoContrato);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void updateNotNull(IDto obj) throws Exception {
		AcordoServicoContratoDTO acordoServicoContratoDTO = (AcordoServicoContratoDTO) obj;
		AcordoServicoContratoDao dao = new AcordoServicoContratoDao();
		try {
			if (acordoServicoContratoDTO.getHabilitado().equalsIgnoreCase("S")) {
				List<AcordoServicoContratoDTO> lista = dao.listAtivoByIdServicoContrato(acordoServicoContratoDTO.getIdAcordoServicoContrato(), acordoServicoContratoDTO.getIdServicoContrato(), "T");
				if(lista != null) {
					for (AcordoServicoContratoDTO acordo : lista) {
						acordo.setHabilitado("N");
						dao.updateNotNull(acordo);
					}
				}
			}			
			dao.updateNotNull(obj);			
			
		} catch (Exception e) {
			throw new ServiceException(e);
		}		
	}
	
	public List<AcordoServicoContratoDTO> listAtivoByIdServicoContrato(Integer idAcordoServicoContrato, Integer idServicoContrato, String tipo) throws Exception {
		AcordoServicoContratoDao dao = new AcordoServicoContratoDao();
		try {
			return dao.listAtivoByIdServicoContrato(idAcordoServicoContrato, idServicoContrato, tipo);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
}
