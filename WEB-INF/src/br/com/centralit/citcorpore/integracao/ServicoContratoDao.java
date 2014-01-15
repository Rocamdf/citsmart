package br.com.centralit.citcorpore.integracao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
public class ServicoContratoDao extends CrudDaoDefaultImpl {
	public ServicoContratoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public static String strSGBDPrincipal = null;

	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idServicoContrato", "idServicoContrato", true, true, false, false));
		listFields.add(new Field("idServico", "idServico", false, false, false, false));
		listFields.add(new Field("idContrato", "idContrato", false, false, false, false));
		listFields.add(new Field("idCondicaoOperacao", "idCondicaoOperacao", false, false, false, false));
		listFields.add(new Field("dataInicio", "dataInicio", false, false, false, false));
		listFields.add(new Field("dataFim", "dataFim", false, false, false, false));
		listFields.add(new Field("observacao", "observacao", false, false, false, false));
		listFields.add(new Field("custo", "custo", false, false, false, false));
		listFields.add(new Field("restricoesPressup", "restricoesPressup", false, false, false, false));
		listFields.add(new Field("objetivo", "objetivo", false, false, false, false));
		listFields.add(new Field("permiteSLANoCadInc", "permiteSLANoCadInc", false, false, false, false));
		listFields.add(new Field("linkProcesso", "linkProcesso", false, false, false, false));
		listFields.add(new Field("descricaoProcesso", "descricaoProcesso", false, false, false, false));
		listFields.add(new Field("tipoDescProcess", "tipoDescProcess", false, false, false, false));
		listFields.add(new Field("areaRequisitante", "areaRequisitante", false, false, false, false));
		listFields.add(new Field("idModeloEmailCriacao", "idModeloEmailCriacao", false, false, false, false));
		listFields.add(new Field("idModeloEmailFinalizacao", "idModeloEmailFinalizacao", false, false, false, false));
		listFields.add(new Field("idModeloEmailAcoes", "idModeloEmailAcoes", false, false, false, false));
		listFields.add(new Field("idGrupoNivel1", "idGrupoNivel1", false, false, false, false));
		listFields.add(new Field("idGrupoExecutor", "idGrupoExecutor", false, false, false, false));
		listFields.add(new Field("idGrupoAprovador", "idGrupoAprovador", false, false, false, false));
		listFields.add(new Field("idCalendario", "idCalendario", false, false, false, false));
		listFields.add(new Field("permSLATempoACombinar", "permSLATempoACombinar", false, false, false, false));
		listFields.add(new Field("permMudancaSLA", "permMudancaSLA", false, false, false, false));
		listFields.add(new Field("permMudancaCalendario", "permMudancaCalendario", false, false, false, false));
		listFields.add(new Field("deleted", "deleted", false, false, false, false));
		return listFields;
	}

	public String getTableName() {
		return this.getOwner() + "ServicoContrato";
	}

	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return ServicoContratoDTO.class;
	}

	public Collection find(IDto arg0) throws Exception {
		return null;
	}

	public Collection findByIdServico(Integer parm) throws Exception {
		StringBuilder sql = new StringBuilder();
		List parametro = new ArrayList();
		List list = new ArrayList();
		sql.append("SELECT idServico, idContrato, deleted, dataInicio, dataFim ");
		sql.append("FROM " + getTableName() + " ");
		sql.append("WHERE idservico = " + parm + " ");
		sql.append("AND (datafim is null or datafim > ? )");
		parametro.add(UtilDatas.getDataAtual());
		list = this.execSQL(sql.toString(), parametro.toArray());
		List listRetorno = new ArrayList();
		listRetorno.add("idServico");
		listRetorno.add("idContrato");
		listRetorno.add("deleted");
		listRetorno.add("dataInicio");
		listRetorno.add("dataFim");

		List result = this.engine.listConvertion(getBean(), list, listRetorno);
		if (result == null || result.size() == 0) {
			return null;
		} else {
			return result;
		}
	}

	public void deleteByIdServico(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idServico", "=", parm));
		super.deleteByCondition(condicao);
	}

	public Collection findByIdContrato(Integer parm) throws Exception {
		StringBuilder sql = new StringBuilder();
		List parametro = new ArrayList();
		List list = new ArrayList();
		sql.append("SELECT " + this.getNamesFieldsStr() + " ");
		sql.append("FROM " + getTableName() + " ");
		sql.append("WHERE idContrato = ? ");
		sql.append("AND (deleted is null or deleted <> 'y')");

		parametro.add(parm);

		list = this.execSQL(sql.toString(), parametro.toArray());

		List listRetorno = this.getListNamesFieldClass();

		List result = new ArrayList();
		result = this.engine.listConvertion(getBean(), list, listRetorno);

		return result;

	}

	public Collection findByIdContratoDistinct(Integer idContrato) throws Exception {
		StringBuilder sql = new StringBuilder();
		List parametro = new ArrayList();
		List list = new ArrayList();
		sql.append("SELECT distinct servicocontrato.idServico ");
		sql.append("FROM servicocontrato ");
		sql.append("INNER JOIN servico ON servico.idservico = servicocontrato.idservico ");
		sql.append("WHERE servicocontrato.idcontrato = " + idContrato);
		list = this.execSQL(sql.toString(), parametro.toArray());
		List listRetorno = new ArrayList();
		listRetorno.add("idServico");

		List result = this.engine.listConvertion(getBean(), list, listRetorno);
		if (result == null || result.size() == 0) {
			return null;
		} else {
			return result;
		}
	}

	public Collection findServicoContratoByIdContrato(Integer idContrato) throws Exception {
		StringBuilder sql = new StringBuilder();
		List parametro = new ArrayList();
		List list = new ArrayList();
		sql.append("SELECT servicocontrato.idServicoContrato, servico.nomeServico, servico.idServico ");
		sql.append("FROM servicocontrato servicocontrato ");
		sql.append("INNER JOIN servico servico ON servicocontrato.idservico = servico.idservico ");
		sql.append("WHERE servicocontrato.idContrato = " + idContrato);
		sql.append(" AND (servicocontrato.deleted is null or servicocontrato.deleted <> 'y') ");
		sql.append("order by servico.nomeservico ");
		list = this.execSQL(sql.toString(), parametro.toArray());
		List listRetorno = new ArrayList();
		listRetorno.add("idServicoContrato");
		listRetorno.add("nomeServico");
		listRetorno.add("idServico");

		List result = this.engine.listConvertion(getBean(), list, listRetorno);
		if (result == null || result.size() == 0) {
			return null;
		} else {
			return result;
		}
	}

	/**
	 * Retorna ServicoContrato Ativo de acordo com o idContrato e idServico informado.
	 * 
	 * @param idContrato
	 *            - Identificador do Contrato.
	 * @param idServico
	 *            - Identificador do Serviço.
	 * @return ServicoContratoDTO
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public ServicoContratoDTO findByIdContratoAndIdServico(Integer idContrato, Integer idServico) throws Exception {

		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT ");
		sql.append(this.getNamesFieldsStr());
		sql.append(" FROM servicocontrato ");
		sql.append(" WHERE idcontrato = ? AND idservico = ? AND (datafim is null OR datafim > ? ) AND (deleted <> 'y' OR deleted <> 'Y' OR deleted is null) ");

		List parametros = new ArrayList();

		parametros.add(idContrato);
		parametros.add(idServico);
		parametros.add(UtilDatas.getDataAtual());

		List list = this.execSQL(sql.toString(), parametros.toArray());

		List<ServicoContratoDTO> listServicoContratoDto = this.listConvertion(this.getBean(), list, this.getListNamesFieldClass());

		if (listServicoContratoDto != null && !listServicoContratoDto.isEmpty()) {
			return listServicoContratoDto.get(0);
		} else {
			return null;
		}

	}

	public void deleteByIdContrato(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idContrato", "=", parm));
		super.deleteByCondition(condicao);
	}

	public void setDataFim(Integer idServicoContrato) throws Exception {

		ServicoContratoDTO servicoContratoDto = new ServicoContratoDTO();

		servicoContratoDto.setIdServicoContrato(idServicoContrato);
		servicoContratoDto.setDataFim(UtilDatas.getDataAtual());

		super.updateNotNull(servicoContratoDto);
	}

	public Collection findByIdFornecedor(Integer idFornecedor) throws Exception {
		StringBuilder sql = new StringBuilder();
		List parametro = new ArrayList();
		List list = new ArrayList();

		Collection fields = getFields();
		List listaNomes = new ArrayList();
		sql.append("SELECT ");
		int i = 0;
		for (Iterator it = fields.iterator(); it.hasNext();) {
			Field field = (Field) it.next();
			if (i > 0) {
				sql.append(", ");
			}
			sql.append("sc." + field.getFieldDB());
			listaNomes.add(field.getFieldClass());
			i++;
		}

		sql.append(" FROM " + getTableName());
		sql.append(" sc INNER JOIN contratos c ON c.idcontrato = sc.idcontrato WHERE c.idfornecedor = ? ");
		// sql.append("order by nomeservico ");

		parametro.add(idFornecedor);

		list = this.execSQL(sql.toString(), parametro.toArray());

		List result = this.engine.listConvertion(getBean(), list, listaNomes);
		if (result == null || result.size() == 0) {
			return null;
		} else {
			return result;
		}
	}

	public boolean validaServicoContrato(Integer idContrato, Integer idServico) throws Exception {

		StringBuilder sql = new StringBuilder();
		List parametro = new ArrayList();

		List list = new ArrayList();
		sql.append("SELECT idServicoContrato ");
		sql.append("FROM " + getTableName() + " ");
		sql.append("WHERE idContrato = ? ");
		sql.append("AND idServico = ? ");
		sql.append("AND deleted IS NULL ");

		parametro.add(idContrato);
		parametro.add(idServico);

		list = this.execSQL(sql.toString(), parametro.toArray());
		List listRetorno = new ArrayList();
		listRetorno.add("idServicoContrato");

		List result = this.engine.listConvertion(getBean(), list, listRetorno);
		if (result == null || result.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param idServicoContrato
	 * @param data
	 * @throws PersistenceException
	 * @author cledson.junioro
	 */
	public void updateServicoContrato(Integer idServicoContrato, Date data) throws PersistenceException {
		List parametros = new ArrayList();
		if (data != null) {
			parametros.add(data);
		} else {
			parametros.add(null);
		}
		parametros.add("y");
		parametros.add(idServicoContrato);
		String sql = "UPDATE " + getTableName() + " SET datafim = ?, deleted = ? WHERE idServicoContrato = ?";
		execUpdate(sql, parametros.toArray());
	}

	/**
	 * @param servicoContratoDTO
	 * @param paginacao
	 * @param pagAtual
	 * @param pagAtualAux
	 * @param totalPag
	 * @param quantidadePaginator
	 * @param campoPesquisa
	 * @return
	 * @throws Exception
	 * @author cledson.junioro
	 * 
	 *         Paginação da tela Administração de Contratos
	 */
	public Collection findByIdContratoPaginada(ServicoContratoDTO servicoContratoDTO, String paginacao, Integer pagAtual, Integer pagAtualAux, Integer totalPag, Integer quantidadePaginator,
			String campoPesquisa) throws Exception {
		if (strSGBDPrincipal == null) {
			strSGBDPrincipal = CITCorporeUtil.SGBD_PRINCIPAL;
			strSGBDPrincipal = UtilStrings.nullToVazio(strSGBDPrincipal).trim();
		}
		StringBuilder sql = new StringBuilder();
		String trim = "";
		String sql2 = "";

		sql.append("SELECT sc.idServicoContrato,sc.idServico,sc.idContrato,sc.idCondicaoOperacao,sc.dataInicio,sc.dataFim,sc.observacao,");
		sql.append("sc.custo,sc.restricoesPressup,sc.objetivo,sc.permiteSLANoCadInc,sc.linkProcesso,sc.descricaoProcesso,sc.tipoDescProcess,");
		sql.append("sc.areaRequisitante,sc.idModeloEmailCriacao,sc.idModeloEmailFinalizacao,sc.idModeloEmailAcoes,sc.idGrupoNivel1,sc.idGrupoExecutor,");
		sql.append("sc.idGrupoAprovador,sc.idCalendario,sc.permSLATempoACombinar,sc.permMudancaSLA,sc.permMudancaCalendario,sc.deleted");
		sql.append(" FROM " + getTableName() + " sc INNER JOIN servico s ON sc.idServico = s.idServico");

		if (strSGBDPrincipal.equalsIgnoreCase("SQLSERVER")) {
			trim = "";
		} else {
			trim = " order by trim(s.nomeservico)";
		}

		sql.append(" WHERE sc.idContrato = " + servicoContratoDTO.getIdContrato() + " AND (upper(s.nomeservico) like '%" + campoPesquisa.toUpperCase() + "%' OR upper(s.siglaabrev) like '%"
				+ campoPesquisa.toUpperCase() + "%') AND (sc.deleted is null or sc.deleted <> 'y')" + trim);
		List listaTotal = this.execSQL(sql.toString(), null);
		if (quantidadePaginator != null) {
			if (strSGBDPrincipal.equalsIgnoreCase("POSTGRESQL") || strSGBDPrincipal.equalsIgnoreCase("POSTGRES")) {
				sql.append(" LIMIT " + quantidadePaginator + " OFFSET " + pagAtual);
			} else if (strSGBDPrincipal.equalsIgnoreCase("MYSQL")) {
				sql.append(" LIMIT " + pagAtual + ", " + quantidadePaginator);
			} else if (strSGBDPrincipal.equalsIgnoreCase("ORACLE")) {
				Integer quantidadePaginator2 = new Integer(0);
				quantidadePaginator2 = quantidadePaginator + pagAtual;
				sql2 = sql.toString();
				sql.delete(0, sql.length());
				sql.append("SELECT sc.idServicoContrato,sc.idServico,sc.idContrato,sc.idCondicaoOperacao,sc.dataInicio,sc.dataFim,sc.observacao,");
				sql.append("sc.custo,sc.restricoesPressup,sc.objetivo,sc.permiteSLANoCadInc,sc.linkProcesso,sc.descricaoProcesso,sc.tipoDescProcess,");
				sql.append("sc.areaRequisitante,sc.idModeloEmailCriacao,sc.idModeloEmailFinalizacao,sc.idModeloEmailAcoes,sc.idGrupoNivel1,");
				sql.append("sc.idGrupoExecutor,sc.idGrupoAprovador,sc.idCalendario,sc.permSLATempoACombinar,sc.permMudancaSLA,sc.permMudancaCalendario,sc.deleted");
				sql.append(" FROM " + getTableName() + " sc INNER JOIN servico s ON sc.idServico = s.idServico ");
				sql.append(" WHERE sc.idContrato = " + servicoContratoDTO.getIdContrato() + " AND ( upper(s.nomeservico) like '%" + campoPesquisa.toUpperCase() + "%' OR upper(s.siglaabrev)  like '%"
						+ campoPesquisa.toUpperCase() + "%')");
				sql.append(" AND (sc.deleted is null or sc.deleted <> 'y') and IDSERVICOCONTRATO in ");
				sql.append("(select IDSERVICOCONTRATO from (select table_.*, rownum rownum_ from (select count(*) over() as totalRowCount,");
				sql.append(sql2.substring(6, sql2.length()));
				sql.append(") table_ where rownum <= " + quantidadePaginator2 + " ) as SERVICOCONTRATO where rownum_ > " + pagAtual + ")");
			} else if (strSGBDPrincipal.equalsIgnoreCase("SQLSERVER")) {
				Integer quantidadePaginator2 = new Integer(0);
				quantidadePaginator2 = quantidadePaginator + pagAtual;
				if (pagAtual != 1) {
					pagAtual++;
				}
				sql2 = sql.toString();
				sql.delete(0, sql.length());
				sql.append("SELECT idServicoContrato,idServico,idContrato,idCondicaoOperacao,dataInicio,dataFim,observacao,");
				sql.append("custo,restricoesPressup,objetivo,permiteSLANoCadInc,linkProcesso,descricaoProcesso,tipoDescProcess,");
				sql.append("areaRequisitante,idModeloEmailCriacao,idModeloEmailFinalizacao,idModeloEmailAcoes,idGrupoNivel1,");
				sql.append("idGrupoExecutor,idGrupoAprovador,idCalendario,permSLATempoACombinar,permMudancaSLA,permMudancaCalendario,deleted");
				sql.append(" FROM" + "(select ROW_NUMBER() OVER( order by s.nomeservico) as rownum_, ");
				sql.append(sql2.substring(6, sql2.length()) + ")  as table_ where table_.rownum_ between " + pagAtual + " and " + quantidadePaginator2);
			}
		}

		if (listaTotal != null) {
			servicoContratoDTO.setTotalItens(listaTotal.size());
			if (listaTotal.size() > quantidadePaginator) {
				totalPag = ((listaTotal.size() / quantidadePaginator));
				if (listaTotal.size() % quantidadePaginator != 0) {
					totalPag = totalPag + 1;
				}
			} else {
				totalPag = 1;
			}
		}
		servicoContratoDTO.setTotalPagina(totalPag);
		List lista = execSQL(sql.toString(), null);
		if (lista == null || lista.size() == 0) {
			TransactionControler tc = this.getTransactionControler();
			if (tc != null) {
				tc.close();
			}
			tc = null;
			return null;
		}

		List result = new ArrayList();
		if (lista == null || lista.size() == 0) {
			TransactionControler tc = this.getTransactionControler();
			if (tc != null) {
				tc.close();
			}
			tc = null;
			return result;
		}

		TransactionControler tc = this.getTransactionControler();
		if (tc != null) {
			tc.close();
		}
		tc = null;
		List listRetorno = this.getListNamesFieldClass();

		result = this.engine.listConvertion(getBean(), lista, listRetorno);

		return result;
	}

	/**
	 * 
	 * @param idServicoContrato
	 *            metodo para buscar por inner join informações sobre o servico pelo idServicoContrato para exibir no carrinho de serviços(Portal)
	 * @return ServicoDTO
	 * @throws Exception
	 */
	public ServicoContratoDTO findByIdServicoContrato(Integer idServico, Integer idContrato) throws Exception {
		List parametro = new ArrayList();
		List fields = new ArrayList();
		List list = new ArrayList();
		String sql = "select servicocontrato.idservicocontrato, valorservicocontrato.valorservico, servico.nomeservico, categoriaservico.nomecategoriaservico "
				+ "from servicocontrato servicocontrato " + "left join valorservicocontrato valorservicocontrato on valorservicocontrato.idservicocontrato =  servicocontrato.idservicocontrato "
				+ "inner join servico servico on servicocontrato.idservico = servico.idservico "
				+ "inner join categoriaservico categoriaservico on servico.idcategoriaservico = categoriaservico.idcategoriaservico " + "where valorservicocontrato.datafim is null "
				+ "and servicocontrato.datafim is null and servicocontrato.idservico = ? and servicocontrato.idcontrato = ?";
		parametro.add(idServico);
		parametro.add(idContrato);
		list = this.execSQL(sql, parametro.toArray());
		fields.add("idServicoContrato");
		fields.add("valorServico");
		fields.add("nomeServico");
		fields.add("nomeCategoriaServico");
		if (list != null && !list.isEmpty()) {
			return (ServicoContratoDTO) this.listConvertion(getBean(), list, fields).get(0);
		} else {
			return null;
		}
	}

	public Collection<ServicoContratoDTO> findAtivosByIdGrupo(Integer idGrupo) throws Exception {
		List parametro = new ArrayList();
		List fields = new ArrayList();
		List list = new ArrayList();
		String sql = "select servicocontrato.idservicocontrato " + "from servicocontrato servicocontrato "
				+ "where (servicocontrato.idgruponivel1 = ? or servicocontrato.idgrupoexecutor = ? or servicocontrato.idgrupoaprovador = ?) " + "and servicocontrato.datafim is null";

		parametro.add(idGrupo);
		parametro.add(idGrupo);
		parametro.add(idGrupo);
		fields.add("idServicoContrato");
		list = this.execSQL(sql, parametro.toArray());

		if (list != null && !list.isEmpty()) {
			return (Collection<ServicoContratoDTO>) this.listConvertion(getBean(), list, fields);
		} else {
			return null;
		}
	}

	/**
	 * @author euler.ramos
	 * @param idCalendario
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ServicoContratoDTO> findByIdCalendario(Integer idCalendario) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idCalendario", "=", idCalendario)); 
		ordenacao.add(new Order("idServico"));
		ArrayList<ServicoContratoDTO> result = (ArrayList<ServicoContratoDTO>) super.findByCondition(condicao, ordenacao);
		return (result == null ? new ArrayList<ServicoContratoDTO>() : result);
	}

}
