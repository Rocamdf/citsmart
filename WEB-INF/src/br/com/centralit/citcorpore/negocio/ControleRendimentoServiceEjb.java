package br.com.centralit.citcorpore.negocio;

import java.sql.Date;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.ControleRendimentoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.integracao.ControleRendimentoDao;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class ControleRendimentoServiceEjb extends CrudServicePojoImpl implements ControleRendimentoService{

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new ControleRendimentoDao();
	}

	@Override
	protected void validaCreate(Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validaUpdate(Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validaDelete(Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validaFind(Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Collection<ControleRendimentoDTO> findByMesAno(String mes, String ano, Integer idGrupo) throws Exception{
		ControleRendimentoDao controleDao = new ControleRendimentoDao();
		try{
			return controleDao.findByMesAno(mes, ano, idGrupo);
		} catch (Exception e){
			throw new ServiceException(e);
		}
	}
	
	@Override
	public Collection<ControleRendimentoDTO> findPontuacaoRendimento(String mes, String ano, Integer idGrupo) throws Exception{
		ControleRendimentoDao controleDao = new ControleRendimentoDao();
		try{
			return controleDao.findPontuacaoRendimento(mes, ano, idGrupo);
		} catch (Exception e){
			throw new ServiceException(e);
		}
	}

}
