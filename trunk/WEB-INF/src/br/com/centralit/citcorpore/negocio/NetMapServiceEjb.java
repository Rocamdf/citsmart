package br.com.centralit.citcorpore.negocio;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.NetMapDTO;
import br.com.centralit.citcorpore.integracao.NetMapDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class NetMapServiceEjb extends CrudServicePojoImpl implements NetMapService {
	
	protected CrudDAO getDao() throws ServiceException {
		return new NetMapDao();
    }

    protected void validaCreate(Object arg0) throws Exception {
    }

    protected void validaDelete(Object arg0) throws Exception {
    }

    protected void validaFind(Object arg0) throws Exception {
    }

    protected void validaUpdate(Object arg0) throws Exception {
    }

    @SuppressWarnings("unchecked")
    public List<NetMapDTO> verificarExistenciaIp(NetMapDTO netMapDTO) throws ServiceException, LogicException, Exception {
		NetMapDao dao = new NetMapDao();
		try {
		    return (List<NetMapDTO>) dao.verificarExistenciaIp(netMapDTO);
		} catch (Exception e) {
			System.out.println();
		    e.getStackTrace();
		}
		return (List<NetMapDTO>) dao.verificarExistenciaIp(netMapDTO);
    }

    @SuppressWarnings("unchecked")
    public List<NetMapDTO> listIpByDataInventario(Date dataInventario) throws ServiceException, LogicException, Exception {
		NetMapDao netMapDao = new NetMapDao();
	
		return (List<NetMapDTO>) netMapDao.listIpByDataInventario(dataInventario);
    }

	@Override
	public List<NetMapDTO> listIp() throws Exception {
		NetMapDao netMapDao = new NetMapDao();
		return (List<NetMapDTO>) netMapDao.listIp();
	}
	
	public Collection existeByNome(Date dataInventario, String nome) throws Exception{
		NetMapDao netMapDao = new NetMapDao();
		return netMapDao.existeByNome(dataInventario, nome);		
	}
}