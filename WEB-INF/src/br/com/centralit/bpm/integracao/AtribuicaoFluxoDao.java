package br.com.centralit.bpm.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.bpm.dto.AtribuicaoFluxoDTO;
import br.com.centralit.bpm.dto.GrupoBpmDTO;
import br.com.centralit.bpm.util.Enumerados;
import br.com.centralit.bpm.util.Enumerados.TipoAtribuicao;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class AtribuicaoFluxoDao extends CrudDaoDefaultImpl {

	private static final String TABLE_NAME = "bpm_atribuicaofluxo";

	private static final long serialVersionUID = -4960358021849486192L;

	private static final String SQL_RESTORE = "SELECT A.idAtribuicao, A.idItemTrabalho, A.tipo, A.idUsuario, A.idGrupo, A.dataHora "
			+ "  FROM Bpm_AtribuicaoFluxo A INNER JOIN Bpm_ItemTrabalhoFluxo I ON A.idItemTrabalho = I.idItemTrabalho ";

	public AtribuicaoFluxoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	private List getListaDeCampos() {
		List listRetorno = new ArrayList();
		listRetorno.add("idAtribuicao");
		listRetorno.add("idItemTrabalho");
		listRetorno.add("tipo");
		listRetorno.add("idUsuario");
		listRetorno.add("idGrupo");
		listRetorno.add("dataHora");

		return listRetorno;
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idAtribuicao", "idAtribuicao", true, true, false, false));
		listFields.add(new Field("idItemTrabalho", "idItemTrabalho", false, false, false, false));
		listFields.add(new Field("tipo", "tipo", false, false, false, false));
		listFields.add(new Field("idUsuario", "idUsuario", false, false, false, false));
		listFields.add(new Field("idGrupo", "idGrupo", false, false, false, false));
		listFields.add(new Field("dataHora", "dataHora", false, false, false, false));
		return listFields;
	}

	public String getTableName() {
		return TABLE_NAME;
	}

	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return AtribuicaoFluxoDTO.class;
	}

	public Collection find(IDto arg0) throws Exception {
		return null;
	}

	public Collection<AtribuicaoFluxoDTO> findDisponiveis(Integer idUsuario, Collection<GrupoBpmDTO> grupos) throws Exception {
		String sql = SQL_RESTORE + " WHERE I.situacao <> ? AND I.situacao <> ? AND (A.idUsuario = ? ";
		if (grupos != null && !grupos.isEmpty()) {
			sql += " OR A.idGrupo IN (";
			int i = 0;
			for (GrupoBpmDTO grupoBpmDto : grupos) {
				if (i > 0)
					sql += ",";
				sql += grupoBpmDto.getIdGrupo();
				i++;
			}
			sql += ") ";
		}
		sql += " OR I.idResponsavelAtual = ? )";

		List lista = this.execSQL(sql, new Object[] { Enumerados.SituacaoItemTrabalho.Executado.name(), Enumerados.SituacaoItemTrabalho.Cancelado.name(), idUsuario, idUsuario });

		return this.engine.listConvertion(getBean(), lista, getListaDeCampos());
	}

	public Collection<AtribuicaoFluxoDTO> findDisponiveisByIdUsuario(Integer idUsuario) throws Exception {
		String sql = SQL_RESTORE + " WHERE I.situacao <> ? AND I.situacao <> ? AND A.idUsuario = ? ORDER BY A.dataHora";

		List lista = this.execSQL(sql, new Object[] { Enumerados.SituacaoItemTrabalho.Executado.name(), Enumerados.SituacaoItemTrabalho.Cancelado.name(), idUsuario });

		return this.engine.listConvertion(getBean(), lista, getListaDeCampos());
	}

	public Collection<AtribuicaoFluxoDTO> findDisponiveisByIdGrupo(Integer idGrupo) throws Exception {
		String sql = SQL_RESTORE + " WHERE I.situacao <> ? AND I.situacao <> ? " + "   AND idGrupo = ? ORDER BY A.dataHora";

		List lista = this.execSQL(sql, new Object[] { Enumerados.SituacaoItemTrabalho.Executado.name(), Enumerados.SituacaoItemTrabalho.Cancelado.name(), idGrupo });

		return this.engine.listConvertion(getBean(), lista, getListaDeCampos());
	}

	public Collection<AtribuicaoFluxoDTO> findByDisponiveisByIdInstancia(Integer idInstancia) throws Exception {
		String sql = SQL_RESTORE + " WHERE I.situacao <> ? AND I.situacao <> ? AND I.idInstancia = ? ORDER BY A.dataHora";

		List lista = this.execSQL(sql, new Object[] { Enumerados.SituacaoItemTrabalho.Executado.name(), Enumerados.SituacaoItemTrabalho.Cancelado.name(), idInstancia });

		return this.engine.listConvertion(getBean(), lista, getListaDeCampos());
	}

	public Collection<AtribuicaoFluxoDTO> findByDisponiveisByIdItemTrabalho(Integer idItemTrabalho) throws Exception {
		String sql = SQL_RESTORE + " WHERE I.situacao <> ? AND I.situacao <> ? AND I.idItemTrabalho = ? ORDER BY A.dataHora";

		List lista = this.execSQL(sql, new Object[] { Enumerados.SituacaoItemTrabalho.Executado.name(), Enumerados.SituacaoItemTrabalho.Cancelado.name(), idItemTrabalho });

		return this.engine.listConvertion(getBean(), lista, getListaDeCampos());
	}

	public Collection<AtribuicaoFluxoDTO> findByIdUsuario(Integer idUsuario) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idUsuario", "=", idUsuario));
		ordenacao.add(new Order("idAtribuicao"));
		return super.findByCondition(condicao, ordenacao);
	}

	public Collection<AtribuicaoFluxoDTO> findByIdGrupo(Integer idGrupo) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idGrupo", "=", idGrupo));
		ordenacao.add(new Order("idAtribuicao"));
		return super.findByCondition(condicao, ordenacao);
	}

	public Collection<AtribuicaoFluxoDTO> findByIdItemTrabalhoAndTipo(Integer idItemTrabalho, String tipo) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idItemTrabalho", "=", idItemTrabalho));
		condicao.add(new Condition("tipo", "=", tipo));
		ordenacao.add(new Order("idAtribuicao"));
		return super.findByCondition(condicao, ordenacao);
	}

	public Collection<AtribuicaoFluxoDTO> findByIdItemTrabalhoAndIdUsuario(Integer idItemTrabalho, Integer idUsuario, TipoAtribuicao tipo) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idItemTrabalho", "=", idItemTrabalho));
		condicao.add(new Condition("tipo", "=", tipo.name()));
		condicao.add(new Condition("idUsuario", "=", idUsuario));
		ordenacao.add(new Order("idAtribuicao"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteDelegacao(Integer idItemTrabalho) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idItemTrabalho", "=", idItemTrabalho));
		condicao.add(new Condition("tipo", "=", Enumerados.TipoAtribuicao.Delegacao.name()));
		super.deleteByCondition(condicao);
	}
}
