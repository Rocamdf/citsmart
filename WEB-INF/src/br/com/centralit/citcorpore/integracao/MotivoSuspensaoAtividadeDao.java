package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.MotivoSuspensaoAtividadeDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class MotivoSuspensaoAtividadeDao extends CrudDaoDefaultImpl {

	private static final long serialVersionUID = 1L;

	public MotivoSuspensaoAtividadeDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idMotivo", "idMotivo", true, true, false, true));
		listFields.add(new Field("descricao", "descricao", false, false, false, false));
		listFields.add(new Field("dataFim", "dataFim", false, false, false, false));
		return listFields;
	}

	public String getTableName() {
		return this.getOwner() + "MotivoSuspensaoAtivid";
	}

	public Collection list() throws Exception {
		return super.list("descricao");
	}

	public Class getBean() {
		return MotivoSuspensaoAtividadeDTO.class;
	}

	public Collection find(IDto arg0) throws Exception {
		return null;
	}

	/**
	 * Verifica se o registro informado já consta gravado no BD. Considera apenas registros ativos.
	 * 
	 * @param motivoSuspensaoAtividadeDTO
	 * @return boolean
	 * @throws Exception
	 */
	public boolean jaExisteRegistroComMesmoNome(MotivoSuspensaoAtividadeDTO motivoSuspensaoAtividadeDTO) throws Exception {
		ArrayList<Condition> condicoes = new ArrayList<Condition>();
		condicoes.add(new Condition("descricao", "=", motivoSuspensaoAtividadeDTO.getDescricao()));
		condicoes.add(new Condition("dataFim", "is", null));
		Collection retorno = null;
		retorno = super.findByCondition(condicoes, null);
		if (retorno != null) {
			if (retorno.size() > 0) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public Collection listarMotivosSuspensaoAtividadeAtivos() throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("dataFim", "is", null));
		ordenacao.add(new Order("descricao"));
		return super.findByCondition(condicao, ordenacao);
	}
}
