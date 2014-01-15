/**
 * 
 */
package br.com.centralit.citcorpore.integracao;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.PesquisaSatisfacaoDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.SQLConfig;

/**
 * @author valdoilo
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class PesquisaSatisfacaoDAO extends CrudDaoDefaultImpl {

	private static final long serialVersionUID = -7363485540488248699L;
	private EmpregadoDTO pesquisaSatisfacaoDTO;

	public PesquisaSatisfacaoDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto arg0) throws Exception {
		return null;
	}

	@Override
	public Collection getFields() {
		Collection<Field> listFields = new ArrayList();

		listFields.add(new Field("idpesquisasatisfacao", "idPesquisaSatisfacao", true, true, false, false));
		listFields.add(new Field("idsolicitacaoservico", "idSolicitacaoServico", false, false, false, false));
		listFields.add(new Field("nota", "nota", false, false, false, false));
		listFields.add(new Field("comentario", "comentario", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "pesquisasatisfacao";
	}

	@Override
	public Collection list() throws Exception {
		List ordenacao = new ArrayList();
		ordenacao.add(new Order("idAvaliacaoSatisfacao"));
		return super.list(ordenacao);
	}

	@Override
	public Class getBean() {
		return PesquisaSatisfacaoDTO.class;
	}

	public Collection<PesquisaSatisfacaoDTO> getPesquisaByIdSolicitacao(int idServico) {
		ArrayList<Condition> condicoes = new ArrayList<Condition>();
		condicoes.add(new Condition("idSolicitacaoServico", "=", idServico));
		Collection c = null;
		try {
			c = this.findByCondition(condicoes, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}

	public Collection<PesquisaSatisfacaoDTO> relatorioPesquisaSatisfacao(PesquisaSatisfacaoDTO pesquisaSatisfacaoDTO) throws Exception {
		
		List parametro = new ArrayList();
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT ps.idpesquisasatisfacao, ps.idsolicitacaoservico, ps.nota, ps.comentario, ");
		sql.append("ss.idsolicitante, e.nome, sc.idcontrato, c.numero, u.nome, ss.dataHoraInicio, ss.dataHoraFim ");
		sql.append("FROM PESQUISASATISFACAO ps, SOLICITACAOSERVICO ss, EMPREGADOS e, SERVICOCONTRATO sc, CONTRATOS c, USUARIO u ");
		sql.append("WHERE ss.idsolicitacaoservico = ps.idsolicitacaoservico ");
		sql.append("AND ss.idsolicitante = e.idempregado ");
		sql.append("AND sc.idservicocontrato = ss.idservicocontrato ");
		sql.append("AND c.idcontrato = sc.idcontrato ");
		sql.append("AND u.idusuario = ss.idresponsavel ");
		sql.append("AND (c.deleted IS NULL OR upper(c.deleted) = 'N') ");
		
		if (pesquisaSatisfacaoDTO.getIdSolicitacaoServico() != null) {
			sql.append("AND   ps.idsolicitacaoservico = ? ");
			parametro.add(pesquisaSatisfacaoDTO.getIdSolicitacaoServico());
		} else {
			if (pesquisaSatisfacaoDTO.getDataInicio() != null && pesquisaSatisfacaoDTO.getDataFim() != null) {
				sql.append("AND ss.datahorasolicitacao BETWEEN ? AND ? ");
				parametro.add(pesquisaSatisfacaoDTO.getDataInicio());
				if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
					parametro.add(pesquisaSatisfacaoDTO.getDataFim());
				} else {
					parametro.add(transformaHoraFinal(pesquisaSatisfacaoDTO.getDataFim()));
				}
			}
			if (pesquisaSatisfacaoDTO.getIdContrato() != null) {
				sql.append("AND   sc.idcontrato = ? ");
				parametro.add(pesquisaSatisfacaoDTO.getIdContrato());
			}
			if (pesquisaSatisfacaoDTO.getIdSolicitante() != null) {
				sql.append("AND   ss.idsolicitante = ? ");
				parametro.add(pesquisaSatisfacaoDTO.getIdSolicitante());
			}
			if (pesquisaSatisfacaoDTO.getNota() != null) {
				sql.append("AND   ps.nota = ? ");
				parametro.add(pesquisaSatisfacaoDTO.getNota());
			}
		}
		
		List lista = this.execSQL(sql.toString(), parametro.toArray());
		List<String> listRetorno = new ArrayList<String>();
		listRetorno.add("idPesquisaSatisfacao");
		listRetorno.add("idSolicitacaoServico");
		listRetorno.add("nota");
		listRetorno.add("comentario");
		listRetorno.add("idSolicitante");
		listRetorno.add("nomeSolicitante");
		listRetorno.add("idContrato");
		listRetorno.add("contrato");
		listRetorno.add("operador");
		listRetorno.add("dataHoraInicio");
		listRetorno.add("dataHoraFim");
		List result = this.engine.listConvertion(PesquisaSatisfacaoDTO.class, lista, listRetorno);
		if (result != null) {
			return (Collection<PesquisaSatisfacaoDTO>) result;
		} else {
			return null;
		}

	}

	private Timestamp transformaHoraFinal(Date data) throws ParseException {
		String dataHora = data + " 23:59:59";
		String pattern = "yyyy-MM-dd hh:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		java.util.Date d = sdf.parse(dataHora);
		java.sql.Timestamp sqlDate = new java.sql.Timestamp(d.getTime());
		return sqlDate;
	}

}
