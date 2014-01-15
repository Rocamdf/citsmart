/**
 * 
 */
package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.bean.EventoMonitConhecimentoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;

/**
 * @author Vadoilo Damasceno
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class EventoMonitConhecimentoDAO extends CrudDaoDefaultImpl {

	private static final long serialVersionUID = 4791986551932029622L;

	public EventoMonitConhecimentoDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto obj) throws Exception {
		return null;
	}

	@Override
	public Collection getFields() {
		Collection listFields = new ArrayList();

		listFields.add(new Field("IDEVENTOMONITORAMENTO", "idEventoMonitoramento", true, false, false, false));
		listFields.add(new Field("IDBASECONHECIMENTO", "idBaseConhecimento", true, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "EVENTOMONITCONHECIMENTO";
	}

	@Override
	public Collection list() throws Exception {
		List ordenacao = new ArrayList();
		ordenacao.add(new Order("idEventoMonitoramento"));
		return super.list(ordenacao);
	}

	@Override
	public Class getBean() {
		return EventoMonitConhecimentoDTO.class;
	}

	/**
	 * Deleta EventoMonitConhecimento pelo id da Base de Conhecimento.
	 * 
	 * @param idBaseConhecimento
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public void deleteByIdConhecimento(Integer idBaseConhecimento) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idBaseConhecimento", "=", idBaseConhecimento));
		this.deleteByCondition(condicao);
	}

	/**
	 * Lista EventoMonitConhecimentoDTO por idBaseConhecimento.
	 * 
	 * @param idBaseConhecimento
	 * @return Collection<EventoMonitConhecimentoDTO>
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public Collection<EventoMonitConhecimentoDTO> listByIdBaseConhecimento(Integer idBaseConhecimento) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();

		condicao.add(new Condition("idBaseConhecimento", "=", idBaseConhecimento));
		ordenacao.add(new Order("idBaseConhecimento", "ASC"));

		return findByCondition(condicao, ordenacao);
	}
	
	public Collection<EventoMonitConhecimentoDTO> listByIdEventoMonitoramento(Integer idEventoMonitoramento) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();

		condicao.add(new Condition("idEventoMonitoramento", "=", idEventoMonitoramento));
		ordenacao.add(new Order("idBaseConhecimento", "ASC"));

		return findByCondition(condicao, ordenacao);
	}	

	/**
	 * Retorna true ou false caso evento Monitoramento tenha algum relacionamento com base de conhecimento
	 * 
	 * @param idEventoMonitoramento
	 * @return boolena
	 * @throws Exception
	 * @author thays.araujo
	 */
	public boolean verificarEventoMonitoramentoComConhecimento(Integer idEventoMonitoramento) throws Exception {
		List parametro = new ArrayList();
		List list = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select ideventoMonitoramento From " + getTableName()+" ");
		if (idEventoMonitoramento != null) {
			sql.append("where  ideventoMonitoramento = ? ");
			parametro.add(idEventoMonitoramento);
		}

		list = this.execSQL(sql.toString(), parametro.toArray());
		if (list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

}
