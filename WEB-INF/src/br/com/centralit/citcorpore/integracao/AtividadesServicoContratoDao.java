/**
 * CentralIT - CITSmart
 *
 */
package br.com.centralit.citcorpore.integracao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import br.com.centralit.citcorpore.bean.AtividadesServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

;

@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
public class AtividadesServicoContratoDao extends CrudDaoDefaultImpl {
	public AtividadesServicoContratoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idAtividadeServicoContrato", "idAtividadeServicoContrato", true, true, false, false));
		listFields.add(new Field("idServicoContrato", "idServicoContrato", false, false, false, false));
		listFields.add(new Field("descricaoAtividade", "descricaoAtividade", false, false, false, false));
		listFields.add(new Field("obsAtividade", "obsAtividade", false, false, false, false));
		listFields.add(new Field("custoAtividade", "custoAtividade", false, false, false, false));
		listFields.add(new Field("complexidade", "complexidade", false, false, false, false));
		listFields.add(new Field("hora", "hora", false, false, false, false));
		listFields.add(new Field("quantidade", "quantidade", false, false, false, false));
		listFields.add(new Field("periodo", "periodo", false, false, false, false));
		listFields.add(new Field("formula", "formula", false, false, false, false));
		listFields.add(new Field("contabilizar", "contabilizar", false, false, false, false));
		listFields.add(new Field("idServicoContratoContabil", "idServicoContratoContabil", false, false, false, false));
		listFields.add(new Field("deleted", "deleted", false, false, false, false));
		listFields.add(new Field("tipoCusto", "tipoCusto", false, false, false, false));
		return listFields;
	}

	public String getTableName() {
		return this.getOwner() + "AtividadesServicoContrato";
	}

	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return AtividadesServicoContratoDTO.class;
	}

	public Collection find(IDto arg0) throws Exception {
		return null;
	}

	public Collection findByIdServicoContrato(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idServicoContrato", "=", parm));
		ordenacao.add(new Order("idAtividadeServicoContrato"));
		return super.findByCondition(condicao, ordenacao);
	}

	/**
	 * Retorna Lita de Atividades Servico Contrato Ativas pelo
	 * idServicoContrato.
	 * 
	 * @param idServicoContrato
	 * @return atividadesServicoContrato
	 * @throws Exception
	 */
	public Collection obterAtividadesAtivasPorIdServicoContrato(Integer idServicoContrato) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idServicoContrato", "=", idServicoContrato));
		ordenacao.add(new Order("idAtividadeServicoContrato"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByIdServicoContrato(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idServicoContrato", "=", parm));
		super.deleteByCondition(condicao);
	}
	
	/**
	 * @param idServicoContrato
	 * @throws PersistenceException
	 * @author cledson.junior
	 */
	public void updateAtividadesServicoContrato(Integer idServicoContrato) throws PersistenceException {
		List parametros = new ArrayList();
		parametros.add("y");
		parametros.add(idServicoContrato);
		String sql = "UPDATE " + getTableName() + " SET deleted = ? WHERE idServicoContrato = ?";
		execUpdate(sql, parametros.toArray());
	}
}
