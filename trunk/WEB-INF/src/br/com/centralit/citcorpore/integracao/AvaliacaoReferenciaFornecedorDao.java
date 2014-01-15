package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.AvaliacaoReferenciaFornecedorDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
public class AvaliacaoReferenciaFornecedorDao extends CrudDaoDefaultImpl {
	public AvaliacaoReferenciaFornecedorDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idAvaliacaoFornecedor", "idAvaliacaoFornecedor", true, false, false, false));
		listFields.add(new Field("idEmpregado", "idEmpregado", true, false, false, false));
		listFields.add(new Field("decisao", "decisao", false, false, false, false));
		listFields.add(new Field("observacoes", "observacoes", false, false, false, false));
		return listFields;
	}

	public String getTableName() {
		return this.getOwner() + "avaliacaoReferenciaFornecedor";
	}

	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return AvaliacaoReferenciaFornecedorDTO.class;
	}

	public Collection find(IDto arg0) throws Exception {
		return null;
	}

	public void deleteByIdAvaliacaoFornecedor(Integer idAvaliacaoFornecedor) throws Exception {
		List lstCondicao = new ArrayList();
		lstCondicao.add(new Condition("idAvaliacaoFornecedor", "=", idAvaliacaoFornecedor));
		super.deleteByCondition(lstCondicao);
	}

	/**
	 * Lista AvaliacaoReferenciaFornecedorDTO por idAvaliacaoFornecedor.
	 * 
	 * @param idBaseConhecimento
	 * @return Collection<AvaliacaoReferenciaFornecedorDTO>
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<AvaliacaoReferenciaFornecedorDTO> listByIdAvaliacaoFornecedor(Integer idAvaliacaoFornecedor) throws Exception {
		List parametros = new ArrayList();
		List listRetorno = new ArrayList();

		List list = new ArrayList();

		StringBuffer sql = new StringBuffer();

		sql.append("select empregados.idempregado,avaliacaoreferenciafornecedor.idavaliacaofornecedor,empregados.nome,empregados.telefone,avaliacaoreferenciafornecedor.decisao,avaliacaoreferenciafornecedor.observacoes ");
		sql.append("from avaliacaoreferenciafornecedor ");
		sql.append("inner join empregados on empregados.idempregado = avaliacaoreferenciafornecedor.idempregado ");

		if (idAvaliacaoFornecedor != null) {
			sql.append("where idavaliacaofornecedor = ?");
			parametros.add(idAvaliacaoFornecedor);
		}

		list = this.execSQL(sql.toString(), parametros.toArray());
		listRetorno.add("idEmpregado");
		listRetorno.add("idAvaliacaoFornecedor");
		listRetorno.add("nome");
		listRetorno.add("telefone");
		listRetorno.add("decisao");
		listRetorno.add("observacoes");

		if (list != null && !list.isEmpty()) {
			Collection<AvaliacaoReferenciaFornecedorDTO> listAvaliacaoReferenciaFornecedor = this.listConvertion(AvaliacaoReferenciaFornecedorDTO.class, list, listRetorno);
			return listAvaliacaoReferenciaFornecedor;
		}

		return null;
	}

}
