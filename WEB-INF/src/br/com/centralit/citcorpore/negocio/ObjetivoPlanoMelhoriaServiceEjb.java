package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ObjetivoPlanoMelhoriaDTO;
import br.com.centralit.citcorpore.integracao.ObjetivoPlanoMelhoriaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

@SuppressWarnings({ "rawtypes", "serial" })
public class ObjetivoPlanoMelhoriaServiceEjb extends CrudServicePojoImpl implements ObjetivoPlanoMelhoriaService {
	protected CrudDAO getDao() throws ServiceException {
		return new ObjetivoPlanoMelhoriaDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaFind(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	public Collection findByIdPlanoMelhoria(Integer parm) throws Exception {
		ObjetivoPlanoMelhoriaDao dao = new ObjetivoPlanoMelhoriaDao();
		try {
			return dao.findByIdPlanoMelhoria(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByIdPlanoMelhoria(Integer parm) throws Exception {
		ObjetivoPlanoMelhoriaDao dao = new ObjetivoPlanoMelhoriaDao();
		try {
			dao.deleteByIdPlanoMelhoria(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<ObjetivoPlanoMelhoriaDTO> listObjetivosPlanoMelhoria(ObjetivoPlanoMelhoriaDTO obj) throws Exception {
		ObjetivoPlanoMelhoriaDao dao = new ObjetivoPlanoMelhoriaDao();
		try {
			return dao.listObjetivosPlanoMelhoria(obj);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
