package br.com.centralit.citcorpore.negocio;
import java.util.Collection;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.TarefaLinhaBaseProjetoDTO;
import br.com.centralit.citcorpore.integracao.TarefaLinhaBaseProjetoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServicePojoImpl;
public class TarefaLinhaBaseProjetoServiceEjb extends CrudServicePojoImpl implements TarefaLinhaBaseProjetoService {
	protected CrudDAO getDao() throws ServiceException {
		return new TarefaLinhaBaseProjetoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdLinhaBaseProjeto(Integer parm) throws Exception{
		TarefaLinhaBaseProjetoDao dao = new TarefaLinhaBaseProjetoDao();
		try{
			return dao.findByIdLinhaBaseProjeto(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdLinhaBaseProjeto(Integer parm) throws Exception{
		TarefaLinhaBaseProjetoDao dao = new TarefaLinhaBaseProjetoDao();
		try{
			dao.deleteByIdLinhaBaseProjeto(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findCarteiraByIdEmpregado(Integer idEmpregado) throws Exception {
		TarefaLinhaBaseProjetoDao dao = new TarefaLinhaBaseProjetoDao();
		try{
			return dao.findCarteiraByIdEmpregado(idEmpregado);
		} catch (Exception e) {
			throw new ServiceException(e);
		}		
	}
	public Collection findByIdTarefaLinhaBaseProjetoMigr(Integer idTarefaLinhaBaseProjetoMigr) throws Exception {
		TarefaLinhaBaseProjetoDao dao = new TarefaLinhaBaseProjetoDao();
		try{
			return dao.findByIdTarefaLinhaBaseProjetoMigr(idTarefaLinhaBaseProjetoMigr);
		} catch (Exception e) {
			throw new ServiceException(e);
		}			
	}
	public Collection findByIdTarefaLinhaBaseProjetoPai(Integer idTarefaLinhaBaseProjetoPai) throws Exception {
		TarefaLinhaBaseProjetoDao dao = new TarefaLinhaBaseProjetoDao();
		try{
			return dao.findByIdTarefaLinhaBaseProjetoPai(idTarefaLinhaBaseProjetoPai);
		} catch (Exception e) {
			throw new ServiceException(e);
		}			
	}	
	public void atualizaInfoProporcionais(TransactionControler tc, Integer idTarefaBase) throws Exception{
		//--Faz o calculo do proporcional das tarefas acima.
		TarefaLinhaBaseProjetoDao tarefaLinhaBaseProjetoDao = new TarefaLinhaBaseProjetoDao();
		tarefaLinhaBaseProjetoDao.setTransactionControler(tc);
		//
		TarefaLinhaBaseProjetoDTO tarefaLinhaBaseProjetoDTO = new TarefaLinhaBaseProjetoDTO();
		tarefaLinhaBaseProjetoDTO.setIdTarefaLinhaBaseProjeto(idTarefaBase);
		tarefaLinhaBaseProjetoDTO = (TarefaLinhaBaseProjetoDTO) tarefaLinhaBaseProjetoDao.restore(tarefaLinhaBaseProjetoDTO);	
		if (tarefaLinhaBaseProjetoDTO != null && tarefaLinhaBaseProjetoDTO.getIdTarefaLinhaBaseProjetoPai() != null){
			Collection colTarefas = tarefaLinhaBaseProjetoDao.calculaValoresTarefasFilhas(tarefaLinhaBaseProjetoDTO.getIdTarefaLinhaBaseProjetoPai());
			if (colTarefas != null){
				for (Iterator it = colTarefas.iterator(); it.hasNext();){
					TarefaLinhaBaseProjetoDTO tarefaLinhaBaseProjetoAux = (TarefaLinhaBaseProjetoDTO)it.next();
					TarefaLinhaBaseProjetoDTO tarefaLinhaBaseProjetoAtu = new TarefaLinhaBaseProjetoDTO();
					tarefaLinhaBaseProjetoAtu.setIdTarefaLinhaBaseProjeto(tarefaLinhaBaseProjetoDTO.getIdTarefaLinhaBaseProjetoPai());
					tarefaLinhaBaseProjetoAtu.setCusto(tarefaLinhaBaseProjetoAux.getCusto());
					tarefaLinhaBaseProjetoAtu.setCustoPerfil(tarefaLinhaBaseProjetoAux.getCustoPerfil());
					tarefaLinhaBaseProjetoAtu.setTempoTotAlocMinutos(tarefaLinhaBaseProjetoAux.getTempoTotAlocMinutos());
					tarefaLinhaBaseProjetoAtu.setProgresso(tarefaLinhaBaseProjetoAux.getProgresso());
					if (tarefaLinhaBaseProjetoAtu.getProgresso() != null){
						tarefaLinhaBaseProjetoAtu.setProgresso(tarefaLinhaBaseProjetoAtu.getProgresso().doubleValue() * 100);
					}else{
						tarefaLinhaBaseProjetoAtu.setProgresso(new Double(0));
					}
					if (tarefaLinhaBaseProjetoAtu.getProgresso().doubleValue() > 100){
						tarefaLinhaBaseProjetoAtu.setProgresso(new Double(100));
					}
					if (tarefaLinhaBaseProjetoAtu.getProgresso().doubleValue() >= 100){
						tarefaLinhaBaseProjetoAtu.setSituacao(TarefaLinhaBaseProjetoDTO.PRONTO);
					}
					tarefaLinhaBaseProjetoDao.updateNotNull(tarefaLinhaBaseProjetoAtu);
				}
			}
			if (tarefaLinhaBaseProjetoDTO.getIdTarefaLinhaBaseProjetoPai() != null){
				atualizaInfoProporcionais(tc, tarefaLinhaBaseProjetoDTO.getIdTarefaLinhaBaseProjetoPai());
			}
		}		
	}
}
