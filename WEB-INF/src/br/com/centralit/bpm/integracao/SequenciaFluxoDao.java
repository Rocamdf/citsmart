package br.com.centralit.bpm.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.bpm.dto.SequenciaFluxoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class SequenciaFluxoDao extends CrudDaoDefaultImpl {

	private static final long serialVersionUID = -2416762284308629901L;

	private static final String TABLE_NAME = "bpm_sequenciafluxo";

	public SequenciaFluxoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idElementoOrigem", "idElementoOrigem", true, false, false, false));
		listFields.add(new Field("idElementoDestino", "idElementoDestino", true, false, false, false));
		listFields.add(new Field("idFluxo", "idFluxo", false, false, false, false));
		listFields.add(new Field("condicao", "condicao", false, false, false, false));

		listFields.add(new Field("idConexaoOrigem", "idConexaoOrigem", false, false, false, false));
		listFields.add(new Field("idConexaoDestino", "idConexaoDestino", false, false, false, false));
		listFields.add(new Field("bordaX", "bordaX", false, false, false, false));
		listFields.add(new Field("bordaY", "bordaY", false, false, false, false));
		listFields.add(new Field("posicaoAlterada", "posicaoAlterada", false, false, false, false));
		listFields.add(new Field("nome", "nome", false, false, false, false));
		return listFields;
	}

	public String getTableName() {
		return TABLE_NAME;
	}

	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return SequenciaFluxoDTO.class;
	}

	public Collection find(IDto arg0) throws Exception {
		return null;
	}

	public List<SequenciaFluxoDTO> findByIdFluxo(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idFluxo", "=", parm));
		ordenacao.add(new Order("idElementoOrigem"));
		return (List<SequenciaFluxoDTO>) super.findByCondition(condicao, ordenacao);
	}

	public Collection<SequenciaFluxoDTO> findByIdFluxoAndIdOrigem(Integer idFluxo, Integer idOrigem) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idFluxo", "=", idFluxo));
		condicao.add(new Condition("idElementoOrigem", "=", idOrigem));
		ordenacao.add(new Order("idElementoOrigem"));
		return super.findByCondition(condicao, ordenacao);
	}

	public Collection<SequenciaFluxoDTO> findByIdFluxoAndIdDestino(Integer idFluxo, Integer idDestino) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idFluxo", "=", idFluxo));
		condicao.add(new Condition("idElementoDestino", "=", idDestino));
		ordenacao.add(new Order("idElementoDestino"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByIdFluxo(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idFluxo", "=", parm));
		super.deleteByCondition(condicao);
	}
}
