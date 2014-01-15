package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.TipoUnidadeDTO;
import br.com.centralit.citcorpore.integracao.TipoUnidadeDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;
@SuppressWarnings("rawtypes")
public class TipoUnidadeServiceEjb extends CrudServicePojoImpl implements TipoUnidadeService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4101073129183404628L;

	protected CrudDAO getDao() throws ServiceException {
		
		return new TipoUnidadeDao();
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
	
	
	@Override
	public IDto create(IDto model) throws ServiceException, LogicException {
		TipoUnidadeDTO tipoUnidade = (TipoUnidadeDTO)model;
		
		tipoUnidade.setDataInicio(UtilDatas.getDataAtual());
		
		return super.create(tipoUnidade);
	}
	
	public boolean jaExisteUnidadeComMesmoNome(String nome) {
		ArrayList<Condition> condicoes = new ArrayList<Condition>();
		condicoes.add(new Condition("nomeTipoUnidade", "=", nome));
		condicoes.add(new Condition("dataFim", "is", null));
		Collection retorno = null;
		try {
			retorno = ((TipoUnidadeDao) getDao()).findByCondition(condicoes, null);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno == null ? false : true;
	}
	
}
