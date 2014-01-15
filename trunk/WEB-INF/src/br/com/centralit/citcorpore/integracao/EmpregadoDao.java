package br.com.centralit.citcorpore.integracao;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.bean.ClienteDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;

/**
 * @author thays.araujo e daniel
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class EmpregadoDao extends CrudDaoDefaultImpl {

	private static final long serialVersionUID = 1153789196419291108L;

	private static final String TABLE_NAME = "empregados";

	/**
	 * Construtor padr„o
	 */
	public EmpregadoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Class getBean() {
		return EmpregadoDTO.class;
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();

		listFields.add(new Field("idEmpregado", "idEmpregado", true, true, false, false));
		listFields.add(new Field("idUnidade", "idUnidade", false, false, false, false));
		listFields.add(new Field("idCargo", "idCargo", false, false, false, false));
		listFields.add(new Field("Nome", "nome", false, false, false, false));
		listFields.add(new Field("NomeProcura", "nomeProcura", false, false, false, false));
		listFields.add(new Field("DataNascimento", "dataNascimento", false, false, false, false));
		listFields.add(new Field("Sexo", "sexo", false, false, false, false));
		listFields.add(new Field("CPF", "cpf", false, false, false, false));
		listFields.add(new Field("RG", "rg", false, false, false, false));
		listFields.add(new Field("DataEmissaoRG", "dataEmissaoRG", false, false, false, false));
		listFields.add(new Field("OrgExpedidor", "orgExpedidor", false, false, false, false));
		listFields.add(new Field("idUFOrgExpedidor", "idUFOrgExpedidor", false, false, false, false));
		listFields.add(new Field("Pai", "pai", false, false, false, false));
		listFields.add(new Field("Mae", "mae", false, false, false, false));
		listFields.add(new Field("Conjuge", "conjuge", false, false, false, false));
		listFields.add(new Field("Observacoes", "observacoes", false, false, false, false));
		listFields.add(new Field("EstadoCivil", "estadoCivil", false, false, false, false));
		listFields.add(new Field("Email", "email", false, false, false, false));
		listFields.add(new Field("DataCadastro", "dataCadastro", false, false, false, false));
		listFields.add(new Field("Fumante", "fumante", false, false, false, false));
		listFields.add(new Field("CTPSNumero", "ctpsNumero", false, false, false, false));
		listFields.add(new Field("CTPSSerie", "ctpsSerie", false, false, false, false));
		listFields.add(new Field("CTPSIdUf", "ctpsIdUf", false, false, false, false));
		listFields.add(new Field("CTPSDataEmissao", "ctpsDataEmissao", false, false, false, false));
		listFields.add(new Field("NIT", "nit", false, false, false, false));
		listFields.add(new Field("DataAdmissao", "dataAdmissao", false, false, false, false));
		listFields.add(new Field("DataFim", "dataFim", false, false, false, false));
		listFields.add(new Field("idSituacaoFuncional", "idSituacaoFuncional", false, false, false, false));
		listFields.add(new Field("DataDemissao", "dataDemissao", false, false, false, false));
		listFields.add(new Field("tipo", "tipo", false, false, false, false));
		listFields.add(new Field("custoPorHora", "custoPorHora", false, false, false, false));
		listFields.add(new Field("custoTotalMes", "custoTotalMes", false, false, false, false));
		listFields.add(new Field("valorSalario", "valorSalario", false, false, false, false));
		listFields.add(new Field("valorProdutividadeMedia", "valorProdutividadeMedia", false, false, false, false));
		listFields.add(new Field("valorPlanoSaudeMedia", "valorPlanoSaudeMedia", false, false, false, false));
		listFields.add(new Field("valorVTraMedia", "valorVTraMedia", false, false, false, false));
		listFields.add(new Field("valorVRefMedia", "valorVRefMedia", false, false, false, false));
		listFields.add(new Field("agencia", "agencia", false, false, false, false));
		listFields.add(new Field("contaSalario", "contaSalario", false, false, false, false));
		listFields.add(new Field("telefone", "telefone", false, false, false, false));
		listFields.add(new Field("ramal", "ramal", false, false, false, false));
		return listFields;
	}

	@Override
	public void updateNotNull(IDto obj) throws Exception {
		super.updateNotNull(obj);
	}

	public String getTableName() {
		return TABLE_NAME;
	}

	public Collection find(IDto obj) throws Exception {
		return null;
	}

	public Collection list() throws Exception {
		List list = new ArrayList();
		list.add(new Order("nome"));
		return super.list(list);
	}

	/**
	 * Retorna empregado Ativo por id.
	 * 
	 * @param idEmpregado
	 * @return EmpregadoDTO
	 * @throws Exception
	 */
	public EmpregadoDTO restoreEmpregadoAtivoById(Integer idEmpregado) throws Exception {

		ArrayList<Condition> condicoes = new ArrayList<Condition>();

		condicoes.add(new Condition("idEmpregado", "=", idEmpregado));
		condicoes.add(new Condition("dataFim", "is", null));

		List resultados = (List) findByCondition(condicoes, null);

		return (EmpregadoDTO) resultados.get(0);
	}

	/**
	 * @param idGrupo
	 * @return
	 * @throws Exception
	 * @author daniel
	 */
	public Collection<EmpregadoDTO> listEmpregadosByIdGrupo(Integer idGrupo) throws Exception {
		String sql = "select idempregado from gruposempregados where idgrupo = ?";
		List dados = this.execSQL(sql, new Object[] { idGrupo });
		List fields = new ArrayList();
		fields.add("idEmpregado");
		return this.listConvertion(getBean(), dados, fields);
	}

	/**
	 * @param idUnidade
	 * @return
	 * @throws Exception
	 * @author daniel
	 */
	public Collection<EmpregadoDTO> listEmpregadosByIdUnidade(Integer idUnidade) throws Exception {
		String sql = "select idempregado from empregados where idunidade = ?";
		List dados = this.execSQL(sql, new Object[] { idUnidade });
		List fields = new ArrayList();
		fields.add("idEmpregado");
		return this.listConvertion(getBean(), dados, fields);
	}

	public Collection listarIdEmpregados(Integer limit, Integer offset) throws Exception {
		String sql = null;

		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			sql = "  SELECT IDEMPREGADO";
			sql += " FROM   (SELECT ROWNUM RNUM,";
			sql += "               A.*";
			sql += "         FROM   (SELECT *";
			sql += "                 FROM   EMPREGADOS) A";
			sql += "         WHERE  ROWNUM <= " + (offset + limit) + ")";
			sql += " WHERE  RNUM > " + offset;
			sql += " ORDER BY IDEMPREGADO";
		} else {
			sql = "select idempregado from empregados";
			sql += " order by idempregado";
			if (limit != null) {
				sql += " limit " + limit;
			}
			if (offset != null) {
				sql += " offset " + offset;
			}
		}

		List lista = new ArrayList();
		lista = this.execSQL(sql, null);
		List listRetorno = new ArrayList();
		listRetorno.add("idEmpregado");
		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		return result;
	}

	public EmpregadoDTO restoreByIdEmpregado(Integer idEmpregado) throws Exception {
		List ordem = new ArrayList();
		EmpregadoDTO empregadoDto = new EmpregadoDTO();
		empregadoDto.setIdEmpregado(idEmpregado);
		List col = (List) super.find(empregadoDto, ordem);
		if (col == null || col.size() == 0)
			return null;
		return (EmpregadoDTO) col.get(0);
	}

	public Collection<EmpregadoDTO> listEmpregadosGrupo(Integer idEmpregado, Integer idGrupo) throws Exception {
		List parametro = new ArrayList();
		String sql = "SELECT empregados.idempregado,empregados.nome, gruposempregados.enviaemail " + " FROM empregados, gruposempregados "
				+ " WHERE empregados.idempregado = gruposempregados.idempregado " + " AND empregados.idEmpregado = ? " + " AND gruposempregados.idgrupo = ? and empregados.datafim is null";
		parametro.add(idEmpregado);
		parametro.add(idGrupo);
		List lista = this.execSQL(sql.toString(), parametro.toArray());
		List<String> listRetorno = new ArrayList<String>();
		listRetorno.add("idEmpregado");
		listRetorno.add("nome");
		listRetorno.add("enviaEmail");
		List result = this.engine.listConvertion(EmpregadoDTO.class, lista, listRetorno);
		if (result != null) {
			return (Collection<EmpregadoDTO>) result;
		} else {
			return null;
		}
	}

	public Collection<EmpregadoDTO> listEmailContrato(Integer idContrato) throws Exception {
		List parametro = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT e.email ");
		sql.append(" FROM GRUPOSEMPREGADOS ge, EMPREGADOS e, GRUPO g, CONTRATOSGRUPOS cg ");
		sql.append(" WHERE g.idgrupo = ge.idgrupo ");
		sql.append(" AND e.idempregado = ge.idempregado ");
		sql.append(" AND g.idgrupo = cg.idgrupo ");
		sql.append("AND idcontrato = ?");
		parametro.add(idContrato);
		List lista = this.execSQL(sql.toString(), parametro.toArray());
		List<String> listRetorno = new ArrayList<String>();
		listRetorno.add("email");
		List result = this.engine.listConvertion(EmpregadoDTO.class, lista, listRetorno);
		if (result != null) {
			return (Collection<EmpregadoDTO>) result;
		} else {
			return null;
		}
	}

	public Collection<EmpregadoDTO> listEmailContrato() throws Exception {
		List parametro = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT e.email ");
		sql.append(" FROM GRUPOSEMPREGADOS ge, EMPREGADOS e, GRUPO g, CONTRATOSGRUPOS cg ");
		sql.append(" WHERE g.idgrupo = ge.idgrupo ");
		sql.append(" AND e.idempregado = ge.idempregado ");
		sql.append(" AND g.idgrupo = cg.idgrupo ");
		List lista = this.execSQL(sql.toString(), parametro.toArray());
		List<String> listRetorno = new ArrayList<String>();
		listRetorno.add("email");
		List result = this.engine.listConvertion(EmpregadoDTO.class, lista, listRetorno);
		if (result != null) {
			return (Collection<EmpregadoDTO>) result;
		} else {
			return null;
		}
	}

	public EmpregadoDTO listEmpregadoContrato(Integer idContrato, String email) throws Exception {
		List parametro = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ge.idgrupo, ge.idempregado, e.nome, e.email , e.telefone, e.idunidade, cg.idcontrato ");
		sql.append(" FROM GRUPOSEMPREGADOS ge, EMPREGADOS e, GRUPO g, CONTRATOSGRUPOS cg ");
		sql.append(" WHERE g.idgrupo = ge.idgrupo ");
		sql.append(" AND e.idempregado = ge.idempregado ");
		sql.append(" AND g.idgrupo = cg.idgrupo ");
		sql.append("AND idcontrato = ? ");
		sql.append("AND e.email = ?");
		parametro.add(idContrato);
		parametro.add(email);
		List lista = this.execSQL(sql.toString(), parametro.toArray());
		List<String> listRetorno = new ArrayList<String>();
		listRetorno.add("idGrupo");
		listRetorno.add("idEmpregado");
		listRetorno.add("nome");
		listRetorno.add("email");
		listRetorno.add("telefone");
		listRetorno.add("idUnidade");
		listRetorno.add("idContrato");
		List result = this.engine.listConvertion(EmpregadoDTO.class, lista, listRetorno);
		if (result != null && !result.isEmpty()) {
			return (EmpregadoDTO) result.get(0);
		} else {
			return null;
		}
	}

	public EmpregadoDTO listEmpregadoContrato(String email) throws Exception {
		List parametro = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ge.idgrupo, ge.idempregado, e.nome, e.email , e.telefone, e.idunidade, cg.idcontrato ");
		sql.append(" FROM GRUPOSEMPREGADOS ge, EMPREGADOS e, GRUPO g, CONTRATOSGRUPOS cg ");
		sql.append(" WHERE g.idgrupo = ge.idgrupo ");
		sql.append(" AND e.idempregado = ge.idempregado ");
		sql.append(" AND g.idgrupo = cg.idgrupo ");
		sql.append("AND e.email = ?");
		parametro.add(email);
		List lista = this.execSQL(sql.toString(), parametro.toArray());
		List<String> listRetorno = new ArrayList<String>();
		listRetorno.add("idGrupo");
		listRetorno.add("idEmpregado");
		listRetorno.add("nome");
		listRetorno.add("email");
		listRetorno.add("telefone");
		listRetorno.add("idUnidade");
		listRetorno.add("idContrato");
		List result = this.engine.listConvertion(EmpregadoDTO.class, lista, listRetorno);
		if (result != null && !result.isEmpty()) {
			return (EmpregadoDTO) result.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Retorna uma lista de empregados associados a um cargo
	 * 
	 * @param idCargo
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	public boolean verificarSeCargoPossuiEmpregado(Integer idCargo) throws Exception {
		List parametro = new ArrayList();
		String sql = "select idempregado,nome from " + getTableName() + " where idCargo = ? and datafim is null ";
		parametro.add(idCargo);
		List lista = this.execSQL(sql.toString(), parametro.toArray());
		if (lista != null && !lista.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Verifica se idUnidade informado possui empregado.
	 * 
	 * @param idCargo
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	public boolean verificarSeUnidadePossuiEmpregado(Integer idUnidade) throws Exception {
		List parametro = new ArrayList();
		String sql = "select idempregado,nome from " + getTableName() + " where idUnidade = ? and datafim is null ";
		parametro.add(idUnidade);
		List lista = this.execSQL(sql.toString(), parametro.toArray());
		if (lista != null && !lista.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public EmpregadoDTO restoreEmpregadoSeAtivo(EmpregadoDTO empregadoDto) {
		ArrayList<Condition> condicoes = new ArrayList<Condition>();
		condicoes.add(new Condition("idEmpregado", "=", empregadoDto.getIdEmpregado()));
		condicoes.add(new Condition("dataFim", "is", null));
		try {
			List listaEmpregados = (List) this.findByCondition(condicoes, null);
			if (listaEmpregados != null && listaEmpregados.size() > 0) {
				return (EmpregadoDTO) listaEmpregados.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new EmpregadoDTO();
	}

	/**
	 * MÈtodo para consultar Unidade do Empregado
	 * 
	 * @author rodrigo.oliveira
	 * @param idEmpregado
	 * @return Integer - idUnidade
	 * @throws Exception
	 */
	public Integer consultaUnidadeDoEmpregado(Integer idEmpregado) throws Exception {
		ArrayList<Condition> condicoes = new ArrayList<Condition>();
		condicoes.add(new Condition("idEmpregado", "=", idEmpregado));
		condicoes.add(new Condition("dataFim", "is", null));
		List resultados = (List) findByCondition(condicoes, null);
		EmpregadoDTO resp = (EmpregadoDTO) resultados.get(0);
		return resp.getIdUnidade();
	}

	public Collection findByNomeEmpregado(EmpregadoDTO empregadoDTO) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();

		condicao.add(new Condition("nome", "=", empregadoDTO.getNome()));
		ordenacao.add(new Order("nome"));
		condicao.add(new Condition(Condition.AND, "dataFim", "is", null));

		return super.findByCondition(condicao, ordenacao);
	}

	public EmpregadoDTO restoreByNomeEmpregado(EmpregadoDTO empregadoDTO) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();

		condicao.add(new Condition("nome", "=", empregadoDTO.getNome()));
		ordenacao.add(new Order("nome"));
		condicao.add(new Condition(Condition.AND, "dataFim", "is", null));
		List resultados = (List) findByCondition(condicao, ordenacao);
		return (EmpregadoDTO) resultados.get(0);

	}
	
	/**
	 * Restaura o EmpregadoDTO com o Nome cargo a partir do ID Empregado informado.
	 * 
	 * @param idEmpregado
	 * @return EmpregadoDTO com NomeCargo
	 * @author maycon.fernandes
	 */
	
	public EmpregadoDTO restoreEmpregadoAndNomeCargoByIdEmpegado(Integer idEmpregado) throws Exception {
		List parametro = new ArrayList();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" select em.idEmpregado, em.nome, em.telefone, em.email, ca.nomecargo  from ");
		sql.append(" empregados em inner join cargos ca on em.idCargo = ca.idCargo ");
		sql.append(" where em.idempregado = ? ");

		parametro.add(idEmpregado);
		List listaDados = this.execSQL(sql.toString(), parametro.toArray());
		List listRetorno = new ArrayList();
		
		listRetorno.add("idEmpregado");
		listRetorno.add("nome");
		listRetorno.add("telefone");
		listRetorno.add("email");
		listRetorno.add("nomeCargo");

		List result = this.engine.listConvertion(getBean(), listaDados, listRetorno);
		
		return (EmpregadoDTO) result.get(0);

	}

	public Collection listarEmailsNotificacoesPasta(Integer idPasta) throws Exception {
		StringBuffer sql = new StringBuffer();
		List prarametro = new ArrayList();
		sql.append("select email from empregados e where idempregado in(select e.idempregado from pasta p ");
		sql.append("inner join notificacaogrupo ng on p.idnotificacao = ng.idnotificacao and p.idpasta = ? ");
		sql.append("inner join gruposempregados ge on ng.idgrupo = ge.idgrupo and enviaemail = 'S' ");
		sql.append("inner join empregados e on ge.idempregado = e.idempregado) or e.idempregado in(select e.idempregado from pasta p ");
		sql.append("inner join notificacaousuario nu on p.idnotificacao = nu.idnotificacao and p.idpasta = ? ");
		sql.append("inner join usuario u on nu.idusuario = u.idusuario ");
		sql.append("inner join empregados e on u.idempregado = e.idempregado) ");
		sql.append("group by email");
		prarametro.add(idPasta);
		prarametro.add(idPasta);
		List lista = this.execSQL(sql.toString(), prarametro.toArray());
		List listRetorno = new ArrayList();
		listRetorno.add("email");
		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		return result;
	}

	public Collection listarEmailsNotificacoesConhecimento(Integer idConhecimento) throws Exception {
		StringBuffer sql = new StringBuffer();
		List prarametro = new ArrayList();
		sql.append("select email from empregados e where idempregado in(select e.idempregado from baseconhecimento bc ");
		sql.append("inner join notificacaogrupo ng on bc.idnotificacao = ng.idnotificacao and bc.idbaseconhecimento = ? ");
		sql.append("inner join gruposempregados ge on ng.idgrupo = ge.idgrupo and enviaemail = 'S' ");
		sql.append("inner join empregados e on ge.idempregado = e.idempregado) or e.idempregado in (select e.idempregado from baseconhecimento bc ");
		sql.append("inner join notificacaousuario nu on bc.idnotificacao = nu.idnotificacao and bc.idbaseconhecimento = ? ");
		sql.append("inner join usuario u on nu.idusuario = u.idusuario ");
		sql.append("inner join empregados e on u.idempregado = e.idempregado) ");
		sql.append("group by email");
		prarametro.add(idConhecimento);
		prarametro.add(idConhecimento);
		List lista = this.execSQL(sql.toString(), prarametro.toArray());
		List listRetorno = new ArrayList();
		listRetorno.add("email");
		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		return result;
	}

	public Collection listarEmailsNotificacoesServico(Integer idServico) throws Exception {
		StringBuffer sql = new StringBuffer();
		List prarametro = new ArrayList();

		sql.append("select email from empregados where idempregado in ");
		sql.append("(select ge.idempregado from notificacaogrupo ng inner join gruposempregados ge on ng.idgrupo = ge.idgrupo ");
		sql.append("where ng.idnotificacao in (select idnotificacao from notificacaoservico where idservico = ?)) or ");
		sql.append("idempregado in ");
		sql.append("(select u.idempregado from notificacaousuario nu inner join usuario u on nu.idusuario = u.idusuario ");
		sql.append("where nu.idnotificacao in (select idnotificacao from notificacaoservico where idservico = ?)) ");
		sql.append("group by email");
		prarametro.add(idServico);
		prarametro.add(idServico);
		List lista = this.execSQL(sql.toString(), prarametro.toArray());
		List listRetorno = new ArrayList();
		listRetorno.add("email");
		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		return result;
	}

	/**
	 * Retorna verdadeiro para ativo ou falso para inativo de acordo com nome do empregado passado.
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	public boolean verificarEmpregadosAtivos(EmpregadoDTO obj) throws Exception {
		List parametro = new ArrayList();
		List list = new ArrayList();
		String sql = "select idempregado from " + getTableName() + "  where  (nome like ? or nome like ?)  and dataFim is null ";

		if (obj.getIdEmpregado() != null) {
			sql += " and idempregado <> " + obj.getIdEmpregado();
		}

		parametro.add(" " + obj.getNome());
		parametro.add(obj.getNome());
		list = this.execSQL(sql, parametro.toArray());
		if (list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public EmpregadoDTO restoreByEmail(String email) throws Exception {

		List ordem = new ArrayList();

		ordem.add(new Order("email"));

		EmpregadoDTO empregadoDTO = new EmpregadoDTO();

		empregadoDTO.setEmail(email);

		List col = (List) super.find(empregadoDTO, null);

		if (col == null || col.size() == 0)
			return null;

		return (EmpregadoDTO) col.get(0);
	}

	public IDto restauraTodos(IDto obj) throws Exception {
		EmpregadoDTO empregadoDto = (EmpregadoDTO) obj;

		List condicao = new ArrayList();
		// condicao.add(new Condition("dataHoraFim", "IS", null));
		condicao.add(new Condition("idEmpregado", "=", empregadoDto.getIdEmpregado()));
		ArrayList<EmpregadoDTO> p = (ArrayList<EmpregadoDTO>) super.findByCondition(condicao, null);

		if (p != null && p.size() > 0) {
			return p.get(0);
		}

		return null;
	}

	/**
	 * Retorna EmpregadoDTO (idEmpregado e Nome). Esta consulta È a mesma da LOOKUP_SOLICITANTE_CONTRATO.
	 * 
	 * @param nome
	 *            - Nome do Empregado (Campo Nome da tabela Empregados)
	 * @param idContrato
	 *            - Identificador do Contrato.
	 * @return Collection<EmpregadoDTO> - Lista de Empregados com Id e Nome.
	 * @throws Exception
	 * @author valdoilo.damasceno 29.10.2013
	 */
	public Collection<EmpregadoDTO> findSolicitanteByNomeAndIdContrato(String nome, Integer idContrato) throws Exception {

		if (nome == null) {
			nome = "";
		}

		String text = nome;
		text = Normalizer.normalize(text, Normalizer.Form.NFD);
		text = text.replaceAll("[^\\p{ASCII}]", "");
		text = text.replaceAll("·‡„‚ÈÍÌÛÙı˙¸Á¡¿√¬… Õ”‘’⁄‹«¥`^''-+=", "aaaaeeiooouucAAAAEEIOOOUUC");
		nome = text;
		nome = "%" + nome.toUpperCase() + "%";

		List parametros = new ArrayList();

		parametros.add(idContrato);
		parametros.add(nome);

		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT DISTINCT empregados.idEmpregado, trim(empregados.nome) as nome ");
		sql.append(" FROM empregados ");
		sql.append(" INNER JOIN gruposempregados ON empregados.idempregado = gruposempregados.idempregado ");
		sql.append(" INNER JOIN contratosgrupos ON gruposempregados.idgrupo = contratosgrupos.idgrupo ");
		sql.append(" WHERE (empregados.datafim is null) AND (empregados.idsituacaofuncional <> 2) AND (empregados.nome <> 'Administrador') AND contratosgrupos.idcontrato = ? and (UPPER(empregados.nome) LIKE UPPER(?)) ");
		sql.append(" ORDER BY nome ");

		List list = this.execSQL(sql.toString(), parametros.toArray());

		List listRetorno = new ArrayList();
		listRetorno.add("idEmpregado");
		listRetorno.add("nome");

		Collection<EmpregadoDTO> listEmpregadoDto = this.engine.listConvertion(this.getBean(), list, listRetorno);

		return listEmpregadoDto;
	}

	/**
	 * Pesquisa Empregado por Telefone ou Ramal. Retorna o primeiro Empregado encontrado para o Ramal ou Telefone informado. <<< ATEN«√O >> o par‚metro Telefone antes de ser enviado para o mÈtodo,
	 * deve ser tratado com o MÈtodo mascaraProcuraSql() da Classe Utilit·ria br.com.centralit.citcorpore.util.Telefone.java;
	 * 
	 * @param telefone
	 * @return EmpregadoDTO
	 * @author valdoilo.damasceno
	 */
	public EmpregadoDTO findByTelefoneOrRamal(String ramal) {

		EmpregadoDTO empregadoDto = new EmpregadoDTO();
		StringBuilder sql = new StringBuilder();
		List lista = new ArrayList();

		try {
			sql.append(" select " + getNamesFieldsStr());
			sql.append(" from empregados ");
			sql.append(" where (telefone " + ramal + ") or (ramal " + ramal + ") and dataFim is null");

			lista = this.execSQL(sql.toString(), null);

			List listEmpregadoDto = this.engine.listConvertion(getBean(), lista, (List) getFields());

			if (listEmpregadoDto != null && !listEmpregadoDto.isEmpty()) {
				empregadoDto = (EmpregadoDTO) listEmpregadoDto.get(0);
			}

		} catch (PersistenceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return empregadoDto;
	}

	/**
	 * Restaura o EmpregadoDTO com o ID do Contrato Padr„o (Primeiro contrato encontrado para o Empregado) a partir do ID Empregado informado.
	 * 
	 * @param idEmpregado
	 * @return EmpregadoDTO com IDContrato
	 * @author valdoilo.damasceno
	 */
	public EmpregadoDTO restoreEmpregadoWithIdContratoPadraoByIdEmpregado(Integer idEmpregado) {

		if (idEmpregado != null && idEmpregado.intValue() > 0) {

			StringBuilder sql = new StringBuilder();

			List parametros = new ArrayList();

			List listRetorno = new ArrayList();

			sql.append(" select " + getNamesFieldsStr("empregados") + ", contratosgrupos.idcontrato ");
			sql.append(" from empregados inner join gruposempregados on empregados.idempregado = gruposempregados.idempregado ");
			sql.append(" inner join contratosgrupos on gruposempregados.idgrupo = contratosgrupos.idgrupo where empregados.idempregado = ?");

			parametros.add(idEmpregado);

			try {
				List list = this.execSQL(sql.toString(), parametros.toArray());

				List listEmpregadoWithIdContrato = null;

				if (list != null && !list.isEmpty()) {

					listRetorno.addAll(getFields());
					listRetorno.add("idContrato");

					try {
						listEmpregadoWithIdContrato = this.listConvertion(getBean(), list, listRetorno);

						if (listEmpregadoWithIdContrato != null && !listEmpregadoWithIdContrato.isEmpty()) {
							return (EmpregadoDTO) listEmpregadoWithIdContrato.get(0);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (PersistenceException e) {
				e.printStackTrace();
			}
			return null;
		} else {
			return null;
		}
	}
	
	/**
	 * Encontra o Empregado pelo ID
	 * @author euler.ramos
	 * @throws Exception 
	 */
	public List<EmpregadoDTO> findByIdEmpregado(Integer id) throws Exception {
		List resp = new ArrayList();

		Collection fields = getFields();
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		String campos = "";
		for (Iterator it = fields.iterator(); it.hasNext();) {
			Field field = (Field) it.next();
			if (!campos.trim().equalsIgnoreCase("")) {
				campos = campos + ",";
			}
			campos = campos + field.getFieldDB();
			listRetorno.add(field.getFieldClass());
		}

		String sql = "SELECT " + campos + " FROM " + getTableName() + " WHERE idempregado=? ORDER BY idempregado";
		parametro.add(id);
		resp = this.execSQL(sql, parametro.toArray());
		
		List result = this.engine.listConvertion(getBean(), resp, listRetorno);
		return (result == null ? new ArrayList<ClienteDTO>() : result);
	}
	
	
	/**
	 * Encontra o Empregado pelo nome
	 * @author euler.ramos
	 * @throws Exception 
	 */
	public List<EmpregadoDTO> findByNomeEmpregado(String nome) throws Exception {
		List resp = new ArrayList();

		Collection fields = getFields();
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		String campos = "";
		for (Iterator it = fields.iterator(); it.hasNext();) {
			Field field = (Field) it.next();
			if (!campos.trim().equalsIgnoreCase("")) {
				campos = campos + ",";
			}
			campos = campos + field.getFieldDB();
			listRetorno.add(field.getFieldClass());
		}
		String sql = "SELECT " + campos + " FROM " + getTableName() + " WHERE nome=? ORDER BY nome";
		parametro.add(nome);
		resp = this.execSQL(sql, parametro.toArray());
		
		List result = this.engine.listConvertion(getBean(), resp, listRetorno);
		return (result == null ? new ArrayList<ClienteDTO>() : result);
	}
}