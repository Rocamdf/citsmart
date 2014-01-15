package br.com.centralit.citged.integracao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.AnexoBaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ControleGEDDao extends CrudDaoDefaultImpl {
	private static final long serialVersionUID = 1L;

	private static final String SQL_NEXT_KEY = "SELECT MAX(idcontroleged) + 1 FROM controleged";

	private static final String SQL_INSERT = "INSERT INTO controleged (IDCONTROLEGED, IDTABELA, ID, NOMEARQUIVO, " + "DESCRICAOARQUIVO, EXTENSAOARQUIVO, DATAHORA, PASTA, "
			+ "CONTEUDOARQUIVO, VERSAO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String SQL_RESTORE = "SELECT IDCONTROLEGED, IDTABELA, ID, NOMEARQUIVO, DESCRICAOARQUIVO, " + "EXTENSAOARQUIVO, DATAHORA, PASTA, CONTEUDOARQUIVO, VERSAO FROM controleged "
			+ "WHERE IDCONTROLEGED = ?";

	public ControleGEDDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection find(IDto obj) throws Exception {
		return null;
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();

		listFields.add(new Field("idControleGED", "idControleGED", true, true, false, false));
		listFields.add(new Field("idTabela", "idTabela", false, false, false, false));
		listFields.add(new Field("id", "id", false, false, false, false));
		listFields.add(new Field("nomeArquivo", "nomeArquivo", false, false, false, false));
		listFields.add(new Field("descricaoArquivo", "descricaoArquivo", false, false, false, false));
		listFields.add(new Field("extensaoArquivo", "extensaoArquivo", false, false, false, false));
		listFields.add(new Field("dataHora", "dataHora", false, false, false, false));
		listFields.add(new Field("pasta", "pasta", false, false, false, false));
		listFields.add(new Field("versao", "versao", false, false, false, false));

		return listFields;
	}

	public String getTableName() {
		return "controleged";
	}

	public Collection list() throws Exception {
		return null;
	}

	
	public Collection listByIdTabelaAndID(Integer idTabela, Integer id) throws Exception {
		List list = new ArrayList();
		list.add(new Order("dataHora", Order.DESC));
		ControleGEDDTO obj = new ControleGEDDTO();
		obj.setIdTabela(idTabela);
		obj.setId(id);
		return super.find(obj, list);
	}

	/**
	 * Pesquisa utilizada somente para arquivos anexados na Base de Conhecimento. idTabela = 4.
	 * 
	 * @param idTabela
	 * @param idBasePai
	 * @param idBaseFilho
	 * @return
	 * @throws Exception
	 */
	public Collection listByIdTabelaAndIdBaseConhecimentoPaiEFilho(Integer idTabela, Integer idBasePai, Integer idBaseFilho) throws Exception {
		List parametros = new ArrayList();

		StringBuffer sql = new StringBuffer();

		sql.append("SELECT IDCONTROLEGED, IDTABELA, ID, NOMEARQUIVO, DESCRICAOARQUIVO, EXTENSAOARQUIVO, DATAHORA, PASTA, CONTEUDOARQUIVO FROM controleged WHERE idtabela = ? AND (id = ? or id = ?)");
		parametros.add(idTabela);
		parametros.add(idBasePai);
		parametros.add(idBaseFilho);

		List list = this.execSQL(sql.toString(), parametros.toArray());

		return this.engine.listConvertion(this.getBean(), list, (List) getFields());
	}

	/**
	 * Pesquisa GEDs da Base de Conhecimento.
	 * 
	 * @param idTabela
	 * @param idBaseConhecimento
	 * @return Collection
	 * @throws Exception
	 */
	public Collection listByIdTabelaAndIdBaseConhecimento(Integer idTabela, Integer idBaseConhecimento) throws Exception {
		List parametros = new ArrayList();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT IDCONTROLEGED, IDTABELA, ID, NOMEARQUIVO, DESCRICAOARQUIVO, EXTENSAOARQUIVO, DATAHORA, PASTA, CONTEUDOARQUIVO FROM controleged WHERE idtabela = ? ");
		parametros.add(idTabela);
		if(idBaseConhecimento != null){
			sql.append("AND id = ?");
			parametros.add(idBaseConhecimento);
		}
		List list = this.execSQL(sql.toString(), parametros.toArray());

		return this.engine.listConvertion(this.getBean(), list, (List) getFields());
	}
	
 	public Class getBean() {
		return ControleGEDDTO.class;
	}

	public String getProximaPastaArmazenar() throws Exception {
		String sql = "SELECT PASTA, COUNT(*) FROM controleged GROUP BY PASTA ORDER BY 2";
		List lista = this.execSQL(sql, null);

		List listRetorno = new ArrayList();
		listRetorno.add("pasta");
		listRetorno.add("qtdeObjetos");

		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		if (result == null || result.size() == 0) {
			return "A";
		}
		ControleGEDDTO controleGEDDTO = (ControleGEDDTO) result.get(0);
		if (controleGEDDTO == null) {
			return "A";
		}
		if (controleGEDDTO.getQtdeObjetos().intValue() > 14999) {
			return generateProximaPasta(controleGEDDTO.getPasta());
		}
		return "A";
	}

	private String generateProximaPasta(String pasta) throws Exception {
		if (pasta.length() == 1) {
			if (pasta.equalsIgnoreCase("Z")) {
				return "AA";
			} else {
				int x = pasta.charAt(0);
				x++;
				return new String(((char) x) + "");
			}
		} else {
			int x = pasta.charAt(pasta.length() - 1);
			char aux = (char) x;
			if (aux == 'Z') {
				x = pasta.charAt(pasta.length() - 2);
				aux = (char) x;
				if (aux == 'Z') {
					return pasta + "A";
				} else {
					String ret = "";
					if (pasta.length() > 2) {
						ret = pasta.substring(0, pasta.length() - 2);
						x++;
						ret += ((char) x) + "A";
					} else {
						x++;
						ret = ((char) x) + "A";
					}
					return ret;
				}
			} else {
				String ret = pasta.substring(0, pasta.length() - 1);
				int y = aux;
				y++;
				return ret + ((char) y);
			}
		}
	}

	private Integer nextKey(final Connection conn) throws Exception {
		Integer key = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(SQL_NEXT_KEY);
			rs = ps.executeQuery();
			if (rs.next())
				key = new Integer(rs.getInt(1));
		} finally {
			if (rs != null)
				try {
					rs.close();
					rs = null;
				} catch (SQLException sqle) {
				}
			if (ps != null)
				try {
					ps.close();
					ps = null;
				} catch (SQLException sqle) {
				}
		}
		return key != null ? key : new Integer(1);
	}

	@Override
	public IDto create(IDto obj) throws Exception {
		File f = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ControleGEDDTO ged = (ControleGEDDTO) obj;
		try {
			conn = super.getDataSource().getConnection();
			ged.setIdControleGED(nextKey(conn));
			ps = conn.prepareStatement(SQL_INSERT);
			ps.setInt(2, ged.getIdTabela());
			ps.setInt(3, ged.getId());
			ps.setString(4, ged.getNomeArquivo());
			ps.setString(5, ged.getDescricaoArquivo());
			ps.setString(6, ged.getExtensaoArquivo());
			ps.setDate(7, ged.getDataHora());
			ps.setString(8, ged.getPasta());
			if (UtilStrings.isNotVazio(ged.getPathArquivo())) {
				f = new File(ged.getPathArquivo());
				InputStream is = new FileInputStream(f);
				if (is.available() > 0)
					ps.setBinaryStream(9, is, is.available());
				else
					ps.setNull(9, Types.BLOB);
			} else
				ps.setNull(9, Types.BLOB);
			ps.setString(10, ged.getVersao());
			ps.setInt(1, ged.getIdControleGED());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ps != null)
				try {
					ps.close();
					ps = null;
				} catch (SQLException sqle) {
				}
			if (conn != null)
				try {
					conn.close();
					conn = null;
				} catch (SQLException sqle) {
				}
			if (f != null)
				f.delete();
		}
		return ged;
	}

	public IDto create(IDto obj, String x) throws Exception {
		return super.create(obj);
	}

	public IDto restore(IDto obj, String x) throws Exception {
		return super.restore(obj);
	}

	@Override
	public IDto restore(IDto obj) throws Exception {
		File f = null;
		ResultSet rs = null;
		Connection conn = null;
		ControleGEDDTO ret = null;
		PreparedStatement ps = null;
		ControleGEDDTO ged = (ControleGEDDTO) obj;
		try {
			conn = super.getDataSource().getConnection();
			ps = conn.prepareStatement(SQL_RESTORE);
			ps.setInt(1, ged.getIdControleGED());
			rs = ps.executeQuery();
			if (rs.next()) {
				ret = new ControleGEDDTO();
				ret.setIdControleGED(rs.getInt(1));
				ret.setIdTabela(rs.getInt(2));
				ret.setId(rs.getInt(3));
				ret.setNomeArquivo(rs.getString(4));
				ret.setDescricaoArquivo(rs.getString(5));
				ret.setExtensaoArquivo(rs.getString(6));
				ret.setDataHora(rs.getDate(7));
				ret.setPasta(rs.getString(8));
				InputStream is = null;
				try {
					is = rs.getBinaryStream(9);
				} catch (Exception e) {
				}

				if (is != null) {
					int qtd = 0;
					byte[] b = null;
					f = new File(Constantes.getValue("DIRETORIO_GED") + "/" + Constantes.getValue("ID_EMPRESA_PROC_BATCH") + "/" + ret.getPasta());
					f.mkdirs();
					f = new File(f.getAbsolutePath() + "/" + ret.getIdControleGED() + ".ged");
					f.createNewFile();
					ret.setPathArquivo(f.getAbsolutePath());
					OutputStream os = new FileOutputStream(f);
					try {
						do {
							b = new byte[1024];
							qtd = is.read(b);
							if (qtd >= 0) {
								os.write(b, 0, qtd);
								os.flush();
							}
						} while (qtd >= 0);
					} catch (Exception e) {
					}
					os.close();
				}
			}
		} finally {
			if (rs != null)
				try {
					rs.close();
					rs = null;
				} catch (SQLException sqle) {
				}
			if (ps != null)
				try {
					ps.close();
					ps = null;
				} catch (SQLException sqle) {
				}
			if (conn != null)
				try {
					conn.close();
					conn = null;
				} catch (SQLException sqle) {
				}
		}
		return ret;
	}
	
	public void deleteByIdRequisicaoLiberacao(Integer idRequisicao, Integer idTabela) throws Exception {
		String sql = "DELETE FROM " + this.getTableName() + " WHERE id = ? AND idTabela = ?";
		super.execUpdate(sql, new Object[] { idRequisicao, idTabela});
	}
	
	public Collection<UploadDTO> listByIdTabelaAndIdHistorico(Integer idTabela, Integer id) throws Exception {
		List lstRetorno = new ArrayList();
		List list = new ArrayList();
		StringBuffer sql = new StringBuffer();
		List parametro = new ArrayList();
		
		parametro.add(idTabela);
		parametro.add(id);
		sql.append("select ged.IDCONTROLEGED, ged.NOMEARQUIVO, ged.DESCRICAOARQUIVO ");
		sql.append("from ligacao_historico_ged lig ");
		sql.append("inner join controleged ged on lig.idcontroleged = ged.idcontroleged  ");
		sql.append("where ged.idtabela = ? AND lig.idhistoricoliberacao = ? ");

		list = this.execSQL(sql.toString(),  parametro.toArray());

		lstRetorno.add("idControleGED");
		lstRetorno.add("nameFile");
		lstRetorno.add("descricao");
		

		if (list != null && !list.isEmpty()) {

			return (List<UploadDTO>) this.listConvertion(UploadDTO.class, list, lstRetorno);

		} else {

			return null;
		}
	}
	
	/**
	 * Pesquisa GEDs da Requisicao de Liberação.
	 * 
	 * @param idTabela
	 * @param idRequisicaoLiberacao
	 * @return Collection
	 * @throws Exception
	 */
	public Collection listByIdTabelaAndIdLiberacao(Integer idTabela, Integer idLiberacao) throws Exception {
		List parametros = new ArrayList();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT IDCONTROLEGED, IDTABELA, ID, NOMEARQUIVO, DESCRICAOARQUIVO, EXTENSAOARQUIVO, DATAHORA, PASTA, CONTEUDOARQUIVO FROM controleged WHERE idtabela = ? ");
		parametros.add(idTabela);
		if(idLiberacao != null){
			sql.append("AND id = ?");
			parametros.add(idLiberacao);
		}
		List list = this.execSQL(sql.toString(), parametros.toArray());

		return this.engine.listConvertion(this.getBean(), list, (List) getFields());
	}
	
	public Collection listByIdTabelaAndIdLiberacaoAndLigacao(Integer idTabela, Integer idRequisicaoLiberacao) throws Exception {
		List parametros = new ArrayList();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ged.IDCONTROLEGED, ged.IDTABELA, ged.ID, ged.NOMEARQUIVO, ged.DESCRICAOARQUIVO, ged.EXTENSAOARQUIVO, ged.DATAHORA, ged.PASTA, ged.VERSAO ");
		sql.append("FROM controleged  ged ");
		sql.append("WHERE ged.idtabela = ? ");
		parametros.add(idTabela);
		if(idRequisicaoLiberacao != null){
			sql.append("AND ged.id = ?");
			parametros.add(idRequisicaoLiberacao);
		}
		
		List list = this.execSQL(sql.toString(), parametros.toArray());

		return this.engine.listConvertion(this.getBean(), list, (List) getFields());
	}

	public ControleGEDDTO getControleGED(AnexoBaseConhecimentoDTO anexoBaseConhecimento) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idTabela", "=", ControleGEDDTO.TABELA_BASECONHECIMENTO));
		condicao.add(new Condition("id", "=", anexoBaseConhecimento.getIdBaseConhecimento()));
		condicao.add(new Condition("nomeArquivo", "=", anexoBaseConhecimento.getNomeAnexo()+"."+anexoBaseConhecimento.getExtensao()));
		ArrayList<ControleGEDDTO> resultado = (ArrayList<ControleGEDDTO>) super.findByCondition(condicao, null);
		return (resultado == null ? new ControleGEDDTO() : resultado.get(0));
	}

}