package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.AcaoPlanoMelhoriaDTO;
import br.com.centralit.citcorpore.integracao.AcaoPlanoMelhoriaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

@SuppressWarnings({"serial","rawtypes"})
public class AcaoPlanoMelhoriaServiceEjb extends CrudServicePojoImpl implements AcaoPlanoMelhoriaService {
	protected CrudDAO getDao() throws ServiceException {
		return new AcaoPlanoMelhoriaDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdPlanoMelhoria(Integer parm) throws Exception{
		AcaoPlanoMelhoriaDao dao = new AcaoPlanoMelhoriaDao();
		try{
			return dao.findByIdPlanoMelhoria(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdPlanoMelhoria(Integer parm) throws Exception{
		AcaoPlanoMelhoriaDao dao = new AcaoPlanoMelhoriaDao();
		try{
			dao.deleteByIdPlanoMelhoria(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByIdObjetivoPlanoMelhoria(Integer parm) throws Exception{
		AcaoPlanoMelhoriaDao dao = new AcaoPlanoMelhoriaDao();
		try{
			return dao.findByIdObjetivoPlanoMelhoria(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdObjetivoPlanoMelhoria(Integer parm) throws Exception{
		AcaoPlanoMelhoriaDao dao = new AcaoPlanoMelhoriaDao();
		try{
			dao.deleteByIdObjetivoPlanoMelhoria(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<AcaoPlanoMelhoriaDTO> listAcaoPlanoMelhoria(AcaoPlanoMelhoriaDTO acaoPlanoMelhoriaDto) throws Exception {
		AcaoPlanoMelhoriaDao dao = new AcaoPlanoMelhoriaDao();
		try{
			return dao.listAcaoPlanoMelhoria(acaoPlanoMelhoriaDto);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
