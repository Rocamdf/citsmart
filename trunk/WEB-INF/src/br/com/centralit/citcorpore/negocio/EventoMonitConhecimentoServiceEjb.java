/**
 * 
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.EventoMonitConhecimentoDTO;
import br.com.centralit.citcorpore.integracao.EventoMonitConhecimentoDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServicePojoImpl;

/**
 * @author Vadoilo Damasceno
 * 
 */
public class EventoMonitConhecimentoServiceEjb extends CrudServicePojoImpl implements EventoMonitConhecimentoService {

	private static final long serialVersionUID = 5589108536069729171L;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new EventoMonitConhecimentoDAO();
	}

	@Override
	protected void validaCreate(Object obj) throws Exception {

	}

	@Override
	protected void validaUpdate(Object obj) throws Exception {

	}

	@Override
	protected void validaDelete(Object obj) throws Exception {

	}

	@Override
	protected void validaFind(Object obj) throws Exception {

	}

	@Override
	public void deleteByIdConhecimento(Integer idBaseConhecimento, TransactionControler transactionControler) throws Exception {

		EventoMonitConhecimentoDAO eventoMonitConhecimentoDao = new EventoMonitConhecimentoDAO();

		eventoMonitConhecimentoDao.setTransactionControler(transactionControler);

		eventoMonitConhecimentoDao.deleteByIdConhecimento(idBaseConhecimento);

	}

	@Override
	public void create(EventoMonitConhecimentoDTO eventoMonitConhecimentoDto, TransactionControler transactionControler) throws Exception {

		EventoMonitConhecimentoDAO eventoMonitConhecimentoDao = new EventoMonitConhecimentoDAO();

		eventoMonitConhecimentoDao.setTransactionControler(transactionControler);

		eventoMonitConhecimentoDao.create(eventoMonitConhecimentoDto);

	}

	@Override
	public Collection<EventoMonitConhecimentoDTO> listByIdBaseConhecimento(Integer idBaseConhecimento) throws Exception {

		EventoMonitConhecimentoDAO eventoMonitConhecimentoDao = new EventoMonitConhecimentoDAO();

		return eventoMonitConhecimentoDao.listByIdBaseConhecimento(idBaseConhecimento);
	}
	
	public Collection<EventoMonitConhecimentoDTO> listByIdEventoMonitoramento(Integer idEventoMonitoramento) throws Exception {
		EventoMonitConhecimentoDAO eventoMonitConhecimentoDao = new EventoMonitConhecimentoDAO();

		return eventoMonitConhecimentoDao.listByIdEventoMonitoramento(idEventoMonitoramento);		
	}

	@Override
	public boolean verificarEventoMonitoramentoComConhecimento(Integer idEventoMonitoramento) throws Exception {
		EventoMonitConhecimentoDAO eventoMonitConhecimentoDao = new EventoMonitConhecimentoDAO();
		return eventoMonitConhecimentoDao.verificarEventoMonitoramentoComConhecimento(idEventoMonitoramento);
	}

}
