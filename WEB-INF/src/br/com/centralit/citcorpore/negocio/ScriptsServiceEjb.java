package br.com.centralit.citcorpore.negocio;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.bean.ScriptsDTO;
import br.com.centralit.citcorpore.bean.VersaoDTO;
import br.com.centralit.citcorpore.integracao.ScriptsDao;
import br.com.centralit.citcorpore.integracao.VersaoDao;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.ConnectionProvider;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings("serial")
public class ScriptsServiceEjb extends CrudServicePojoImpl implements ScriptsService {

	private HashMap<String, String> chaves;

	@Override
	public ScriptsDTO buscaScriptPorId(Integer id) throws Exception {
		ScriptsDao scriptsDao = (ScriptsDao) getDao();
		return scriptsDao.buscaScriptPorId(id);
	}

	@Override
	public ScriptsDTO consultarScript(String nomeScript) throws Exception {
		ScriptsDao scriptsDao = (ScriptsDao) getDao();
		return scriptsDao.consultarScript(nomeScript);
	}

	@Override
	public void deletarScript(IDto model, DocumentHTML document) throws Exception {
		ScriptsDTO scriptsDto = (ScriptsDTO) model;
		TransactionControler tc = new TransactionControlerImpl(getDao().getAliasDB());
		ScriptsDao scriptsDao = new ScriptsDao();

		try {
			validaUpdate(scriptsDto);
			scriptsDao.setTransactionControler(tc);
			tc.start();
			scriptsDto.setDataFim(UtilDatas.getDataAtual());
			scriptsDao.update(scriptsDto);
			document.alert(i18n_Message("MSG07"));
			tc.commit();
			tc.close();

		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
		}
	}

	@SuppressWarnings("resource")
	@Override
	public String executaRotinaScripts() throws Exception {
		String erro = null;

		// LEITURA PROGRESSIVA DO XML

		String separator = System.getProperty("file.separator");
		String diretorio = CITCorporeUtil.caminho_real_app + "XMLs" + separator;
		File file = new File(diretorio + "historicoDeVersoes.xml");
		SAXBuilder sb = new SAXBuilder();
		Document doc = sb.build(file);
		Element historicoDeVersoes = doc.getRootElement();

		List<Element> versoes = historicoDeVersoes.getChildren();

		TransactionControlerImpl tc = null;
		Connection connection = null;
		ScriptsDao scriptsDao = new ScriptsDao();
		VersaoDao versaoDao = new VersaoDao();
		try {
			tc = new TransactionControlerImpl(Constantes.getValue("DATABASE_ALIAS"));
			scriptsDao.setTransactionControler(tc);
			versaoDao.setTransactionControler(tc);
			connection = (Connection) tc.getTransactionObject();
			connection.setAutoCommit(true);
			if (!tc.isStarted())
				tc.start();
			if (versoes != null & !versoes.isEmpty()) {
				for (Element versao : versoes) {
					// limpa o HashMap de chaves
					setChaves(null);
					VersaoDTO versaoDTO = versaoDao.buscaVersaoPorNome(versao.getText());
					if (versaoDTO == null) {
						versaoDTO = new VersaoDTO();
						versaoDTO.setNomeVersao(versao.getText());
						versaoDTO = (VersaoDTO) versaoDao.create(versaoDTO);
						tc.commit();
						if (!tc.isStarted())
							tc.start();
					}

					diretorio = CITCorporeUtil.caminho_real_app + "scripts_deploy" + separator;
					String nomeArquivo = "deploy_versao_" + versaoDTO.getNomeVersao() + "_" + CITCorporeUtil.SGBD_PRINCIPAL + ".sql";

					file = new File(diretorio + nomeArquivo);
					Scanner scanner = null;
					try {
						scanner = new Scanner(file, "ISO-8859-1");
						scanner = scanner.useDelimiter("\n");
					} catch (FileNotFoundException e) {
						// VERSAO NÃO ENCONTROU SCRIPT
						continue;
					}

					// TIRA COMENTARIOS
					String sqlQuery = new String();
					boolean comentario = false;
					while (scanner.hasNext()) {
						String linha = scanner.next();
						if (comentario && linha.contains("*/")) {
							linha = linha.substring(linha.indexOf("*/") + 2);
							comentario = false;
						}
						if (linha.contains("/*")) {
							String str = linha;
							linha = str.substring(0, str.indexOf("/*"));
							if (linha.contains("*/")) {
								linha += str.substring(str.indexOf("*/") + 2);
							} else {
								comentario = true;
								sqlQuery += " " + linha + "\n";
							}
						}
						if (!comentario && !linha.startsWith("--") && !linha.startsWith("//")) {
							sqlQuery += " " + linha + "\n";
						}
					}
					scanner.close();

					// DIVIDE O SCRIPT USANDO ";" SEGUIDO DE QUEBRA DE LINHA
					scanner = new Scanner(sqlQuery).useDelimiter(";(\r)?\n");
					while (scanner.hasNext()) {
						String sql = scanner.next();
						sql = sql.replaceAll("(\r\n|\n\r|\r|\n)", " ");
						sql = sql.trim();
						if (!sql.isEmpty()) {
							String nomeScript = nomeArquivo + "#" + sql.hashCode();
							ScriptsDTO scriptsDTO = consultarScript(nomeScript);
							if (scriptsDTO == null) {
								sql = preencheChavesPrimarias(sql);
								scriptsDTO = new ScriptsDTO();
								scriptsDTO.setNome(nomeScript);
								scriptsDTO.setDataInicio(UtilDatas.getDataAtual());
								scriptsDTO.setSqlQuery(sql);
								scriptsDTO.setTipo(ScriptsDTO.TIPO_UPDATE);
								scriptsDTO.setHistorico("INSTRUÇÃO EXECUTADA PELA ROTINA DO SISTEMA AO INICIALIZAR A APLICAÇÃO. DATA / HORA: " + UtilDatas.getDataHoraAtual());
								scriptsDTO.setIdVersao(versaoDTO.getIdVersao());
								try {
									System.out.println("VERSÃO -> " + versaoDTO.getNomeVersao() + " SQL -> " + scriptsDTO.getSqlQuery());
									connection = (Connection) tc.getTransactionObject();
									int quantidadeRegistrosAfetados = connection.createStatement().executeUpdate(sql);
									scriptsDTO.setDescricao("SUCESSO: " + quantidadeRegistrosAfetados + " REGISTROS AFETADOS!");
								} catch (SQLException e) {
									scriptsDTO.setDescricao("ERRO: " + e.getLocalizedMessage());
								} finally {
									try {
										tc.commit();
									} catch (Exception e2) {
										e2.printStackTrace();
									}

									System.out.println("RESULTADO: " + scriptsDTO.getDescricao());

									if (!tc.isStarted())
										tc.start();
									if (scriptsDTO.getIdScript() == null) {
										scriptsDao.create(scriptsDTO);
									} else {
										scriptsDao.update(scriptsDTO);
									}
									tc.commit();
									if (!tc.isStarted()) {
										tc.start();
									}
								}
							}
						}
					}
					scanner.close();
				}
				connection.close();
				connection = null;
				tc.close();
				tc = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			erro = e.getLocalizedMessage();
			try {
				connection.close();
				connection = null;
			} catch (Exception e2) {
			}
			try {
				tc.close();
				tc = null;
			} catch (Exception e2) {
			}
		}
		return erro;
	}

	@Override
	public List<String[]> executarScriptConsulta(ScriptsDTO script) throws Exception {
		List<String[]> retorno = null;
		Connection connection = ConnectionProvider.getConnection(Constantes.getValue("DATABASE_ALIAS"));
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection.setAutoCommit(false);
			statement = connection.createStatement();
			statement.setMaxRows(1024);
			retorno = new ArrayList<String[]>();
			String sql = script.getSqlQuery();
			sql = sql.trim();
			if (sql.endsWith(";")) {
				sql = sql.substring(0, sql.length() - 1);
			}
			resultSet = statement.executeQuery(sql);
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int quantidadeColunas = rsmd.getColumnCount();
			boolean primeiro = true;
			while (resultSet.next()) {
				if (primeiro) {
					primeiro = false;
					String colunas[] = new String[quantidadeColunas];
					for (int i = 1; i <= quantidadeColunas; i++) {
						colunas[i - 1] = rsmd.getColumnName(i);
					}
					retorno.add(colunas);
				}
				String dados[] = new String[quantidadeColunas];
				for (int i = 1; i <= quantidadeColunas; i++) {
					dados[i - 1] = resultSet.getString(i);
				}
				retorno.add(dados);
			}

			connection.commit();
			statement.close();
			connection.close();
			resultSet.close();
			statement = null;
			connection = null;
			resultSet = null;
		} catch (Exception e) {
			try {
				connection.rollback();
				connection.close();
				connection = null;
			} catch (Exception e2) {
				e2.getMessage();
			}
			try {
				if (statement != null) {
					statement.close();
				}
				statement = null;
			} catch (Exception e2) {
			}
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				resultSet = null;
			} catch (Exception e2) {
			}
			throw e;
		}
		return retorno;
	}

	@SuppressWarnings("resource")
	@Override
	public String executarScriptUpdate(ScriptsDTO script) throws Exception {
		String retorno = null;
		Connection connection = ConnectionProvider.getConnection(Constantes.getValue("DATABASE_ALIAS"));
		Statement statement = null;
		try {
			connection.setAutoCommit(false);
			statement = connection.createStatement();

			Scanner scanner = new Scanner(script.getSqlQuery()).useDelimiter(";(\r)?\n");
			while (scanner.hasNext()) {
				String sql = scanner.next();
				sql = sql.replaceAll("(\r\n|\n\r|\r|\n)", " ");
				sql = sql.trim();
				if (sql.endsWith(";")) {
					sql = sql.substring(0, sql.length() - 1);
				}
				if (!sql.isEmpty()) {
					statement.executeUpdate(sql);
				}
			}
			scanner.close();

			connection.commit();
			statement.close();
			connection.close();
			statement = null;
			connection = null;
		} catch (Exception e) {
			retorno = e.getLocalizedMessage();
			try {
				connection.rollback();
				connection.close();
				connection = null;
			} catch (Exception e2) {
				e2.getMessage();
			}
			try {
				if (statement != null) {
					statement.close();
				}
				statement = null;
			} catch (Exception e2) {
			}
		}
		return retorno;
	}

	public HashMap<String, String> getChaves() {
		if (chaves == null) {
			chaves = new HashMap<String, String>();
		}
		return chaves;
	}

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new ScriptsDao();
	}

	@Override
	public boolean haScriptDeVersaoComErro() throws Exception {
		ScriptsDao scriptsDao = (ScriptsDao) getDao();
		return scriptsDao.haScriptDeVersaoComErro();
	}

	@Override
	public void marcaErrosScriptsComoCorrigidos() throws Exception {
		ScriptsDao scriptsDao = (ScriptsDao) getDao();

		TransactionControler tc = new TransactionControlerImpl(Constantes.getValue("DATABASE_ALIAS"));

		scriptsDao.setTransactionControler(tc);

		try {

			tc.start();

			scriptsDao.marcaErrosScriptsComoCorrigidos();

			tc.commit();
			tc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String obterColunaCorrespondenteAChave(String sql, String chave) {
		String coluna = "";
		// pega o conteudo dos parenteses
		Pattern pattern = Pattern.compile("\\(([^\\)]+)\\)");
		Matcher matcher = pattern.matcher(sql);
		// se existem dois grupos de parenteses. (colunas e valores)
		if (matcher.find()) {
			String colunasString = matcher.group();
			if (matcher.find()) {
				String valoresString = matcher.group();

				// remove os parenteses
				colunasString = colunasString.replaceAll("\\(|\\)", "");
				valoresString = valoresString.replaceAll("\\(|\\)", "");
				List<String> colunas = Arrays.asList(colunasString.split(","));
				List<String> valores = Arrays.asList(valoresString.split(","));

				int indice = 0;
				for (String valor : valores) {
					if (chave.trim().equalsIgnoreCase(valor.trim())) {
						break;
					}
					indice++;
				}
				coluna = colunas.get(indice);
				coluna = coluna.trim();
			}
		}
		return coluna;
	}

	/**
	 * Retorna o nome da tabela de acordo com o sql(insert) informado
	 * 
	 * @author Murilo Gabriel Rodrigues
	 */
	public String obterNomeDaTabela(String sql) {
		List<String> palavras = Arrays.asList(sql.split(" |\n|\r"));
		boolean insert = false;
		boolean into = false;
		for (String palavra : palavras) {
			if (insert & into) {
				if (palavra.contains(".")) {
					palavra = palavra.substring(palavra.lastIndexOf(".") + 1);
				}
				return palavra.trim();
			}
			if (palavra.trim().equalsIgnoreCase("insert")) {
				insert = true;
			}
			if (insert && palavra.trim().equalsIgnoreCase("into")) {
				into = true;
			}
		}
		return null;
	}

	/**
	 * Faz a leitura da SQL substituindo as chaves pelos próximos valores disponíveis no banco de dados. Essas chaves tem o padrão $id_[texto] Ex.: $id_idusuario01, $id_idusuario20,
	 * $id_texto_qualquer...
	 * 
	 * @author Murilo Gabriel Rodrigues
	 */
	public String preencheChavesPrimarias(String sql) throws Exception {
		ScriptsDao scriptsDao = (ScriptsDao) getDao();

		Pattern pattern = Pattern.compile("(\\$id_\\w+)\\b");
		Matcher matcher = pattern.matcher(sql);

		while (matcher.find()) {
			String chave = matcher.group();
			if (chave != null && !chave.trim().isEmpty()) {
				String indice = getChaves().get(chave);
				// se a chave ainda não foi obtida
				if (indice == null || indice.trim().isEmpty()) {
					String coluna = obterColunaCorrespondenteAChave(sql, chave);
					String tabela = obterNomeDaTabela(sql);
					if (tabela != null && !tabela.isEmpty() && coluna != null && !coluna.isEmpty()) {
						Integer nextKey = scriptsDao.getNextKey(tabela, coluna);
						if (nextKey != null && nextKey.intValue() != 0) {
							indice = nextKey.toString();
							getChaves().put(chave, indice);
						}
					}
				}
				if (indice != null && !indice.trim().isEmpty()) {
					sql = sql.replaceAll("\\" + chave, indice);
				}
			}
		}
		return sql;
	}

	public void setChaves(HashMap<String, String> chaves) {
		this.chaves = chaves;
	}

	public boolean temScriptsAtivos(ScriptsDTO script) throws Exception {
		ScriptsDao scriptsDao = (ScriptsDao) getDao();
		return scriptsDao.temScriptsAtivos(script);
	}

	@Override
	protected void validaCreate(Object obj) throws Exception {

	}

	@Override
	protected void validaDelete(Object obj) throws Exception {

	}

	@Override
	protected void validaFind(Object obj) throws Exception {

	}

	@Override
	protected void validaUpdate(Object obj) throws Exception {

	}

	public String verificaPermissoesUsuarioBanco(HttpServletRequest request) throws ServiceException {
		ScriptsDao scriptsDao = (ScriptsDao) getDao();

		String resultadoTesteCriacaoTabela = scriptsDao.testaPermissaoCriacaoTabela();
		String resultadoTesteInsercaoRegistroTabela = scriptsDao.testaPermissaoInsercaoRegistroTabela();
		String resultadoTesteConsultaTabela = scriptsDao.testaPermissaoConsultaTabela();
		String resultadoTesteExclusaoRegistroTabela = scriptsDao.testaPermissaoExclusaoRegistroTabela();
		String resultadoTesteCriacaoColuna = scriptsDao.testaPermissaoCriacaoColuna();
		String resultadoTesteAlteracaoColuna = scriptsDao.testaPermissaoAlteracaoColuna();
		String resultadoTesteExclusaoColuna = scriptsDao.testaPermissaoExclusaoColuna();
		String resultadoTesteExclusaoTabela = scriptsDao.testaPermissaoExclusaoTabela();

		List<String> acoesSemPermissao = new ArrayList<String>();

		if (resultadoNegativoPermissoes(resultadoTesteCriacaoTabela)) {
			acoesSemPermissao.add(UtilI18N.internacionaliza(request, "citcorpore.comum.criacaoTabelas"));
		}
		if (resultadoNegativoPermissoes(resultadoTesteInsercaoRegistroTabela)) {
			acoesSemPermissao.add(UtilI18N.internacionaliza(request, "citcorpore.comum.incersaoRegistrosTabelas"));
		}
		if (resultadoNegativoPermissoes(resultadoTesteConsultaTabela)) {
			acoesSemPermissao.add(UtilI18N.internacionaliza(request, "citcorpore.comum.consultaTabelas"));
		}
		if (resultadoNegativoPermissoes(resultadoTesteExclusaoRegistroTabela)) {
			acoesSemPermissao.add(UtilI18N.internacionaliza(request, "citcorpore.comum.exclusaoRegistrosTabelas"));
		}
		if (resultadoNegativoPermissoes(resultadoTesteCriacaoColuna)) {
			acoesSemPermissao.add(UtilI18N.internacionaliza(request, "citcorpore.comum.criacaoColunasTabelas"));
		}
		if (resultadoNegativoPermissoes(resultadoTesteAlteracaoColuna)) {
			acoesSemPermissao.add(UtilI18N.internacionaliza(request, "citcorpore.comum.alteracaoColunasTabelas"));
		}
		if (resultadoNegativoPermissoes(resultadoTesteExclusaoColuna)) {
			acoesSemPermissao.add(UtilI18N.internacionaliza(request, "citcorpore.comum.exclusaoColunasTabelas"));
		}
		if (resultadoNegativoPermissoes(resultadoTesteExclusaoTabela)) {
			acoesSemPermissao.add(UtilI18N.internacionaliza(request, "citcorpore.comum.ExclusaoTabelas"));
		}

		StringBuilder retorno = new StringBuilder();

		if (acoesSemPermissao.isEmpty()) {
			return "sucesso";
		} else {
			retorno.append(UtilI18N.internacionaliza(request, "citcorpore.comum.encontradaFaltaPermissao") + "<br>");
			for (String acao : acoesSemPermissao) {
				retorno.append("&nbsp;&nbsp;&nbsp;<b>").append(acao).append("</b>,<br>");
			}
			return retorno.substring(0, retorno.lastIndexOf(",")) + ".";
		}
	}

	boolean resultadoNegativoPermissoes(String resultadoTestePermissoes) {
		return resultadoTestePermissoes != null
				&& !resultadoTestePermissoes.trim().equalsIgnoreCase("sucesso")
				&& (resultadoTestePermissoes.toLowerCase().contains("command denied")
						|| resultadoTestePermissoes.toLowerCase().contains("permission denied")
						|| resultadoTestePermissoes.toLowerCase().contains("permissão negada")
						|| resultadoTestePermissoes.toLowerCase().contains("must be owner")
						|| resultadoTestePermissoes.toLowerCase().contains("deve ser o dono")
						|| resultadoTestePermissoes.toLowerCase().contains("insufficient privileges")
						|| resultadoTestePermissoes.toLowerCase().contains("no privileges"));
	}

	public List<ScriptsDTO> pesquisaScriptsComFaltaPermissao() throws Exception {
		List<ScriptsDTO> scriptsNaoExecutadosManualmente = new ArrayList<ScriptsDTO>();

		ScriptsDao scriptsDao = (ScriptsDao) getDao();
		List<ScriptsDTO> scriptsComFaltaPermissao = scriptsDao.pesquisaScriptsComFaltaPermissao();
		if (scriptsComFaltaPermissao != null && !scriptsComFaltaPermissao.isEmpty()) {
			for (ScriptsDTO script : scriptsComFaltaPermissao) {
				if (!scriptFoiExecutado(script)) {
					scriptsNaoExecutadosManualmente.add(script);
				}
			}
		}

		return scriptsNaoExecutadosManualmente;
	}

	boolean scriptFoiExecutado(ScriptsDTO scriptsDTO) throws Exception {
		ScriptsDao scriptsDao = (ScriptsDao) getDao();
		boolean scriptFoiExecutado = false;

		String sqlLowerCase = scriptsDTO.getSqlQuery().toLowerCase();
		Matcher matcher = null;
		switch (obtemTipoSQL(scriptsDTO)) {
		case ScriptsDTO.TIPO_CRIAR_TABELA:
			matcher = Pattern.compile("create table").matcher(sqlLowerCase);
			if (matcher.find()) {
				String nomeTabela = sqlLowerCase.substring(matcher.end()).trim().split(" ", 2)[0];
				scriptFoiExecutado = scriptsDao.verificaExistenciaTabela(nomeTabela);
			}
			break;
		case ScriptsDTO.TIPO_INSERIR_REGISTRO:
			// verificacao para esse caso atualmente não é viável
			scriptFoiExecutado = true;
			break;
		case ScriptsDTO.TIPO_DELETAR_REGISTRO:
			// verificacao para esse caso atualmente não é viável
			scriptFoiExecutado = true;
			break;
		case ScriptsDTO.TIPO_ADICIONAR_COLUNA:
			String nomeTabela = null;
			String nomeColuna = null;
			matcher = Pattern.compile("alter table").matcher(sqlLowerCase);
			if (matcher.find()) {
				nomeTabela = sqlLowerCase.substring(matcher.end()).trim().split(" ", 2)[0];
			}
			matcher = Pattern.compile("add column").matcher(sqlLowerCase);
			if (matcher.find()) {
				nomeColuna = sqlLowerCase.substring(matcher.end()).trim().split(" ", 2)[0];
			}
			if (nomeTabela != null && nomeColuna != null) {
				scriptFoiExecutado = scriptsDao.verificaExistenciaColuna(nomeTabela, nomeColuna);
			}
			break;
		case ScriptsDTO.TIPO_ADICIONAR_CONSTRAINT:
			// verificacao para esse caso atualmente não é viável
			scriptFoiExecutado = true;
			break;
		case ScriptsDTO.TIPO_ALTERAR_COLUNA:
			// verificacao para esse caso atualmente não é viável
			scriptFoiExecutado = true;
			break;
		case ScriptsDTO.TIPO_DELETAR_COLUNA:
			// verificacao para esse caso atualmente não é viável
			scriptFoiExecutado = true;
			break;
		case ScriptsDTO.TIPO_DELETAR_TABELA:
			// verificacao para esse caso atualmente não é viável
			scriptFoiExecutado = true;
			break;
		}

		return scriptFoiExecutado;
	}

	int obtemTipoSQL(ScriptsDTO scriptsDTO) {
		int tipo = 0;
		if (scriptsDTO != null && scriptsDTO.getSqlQuery() != null) {
			if (scriptsDTO.getSqlQuery().trim().toUpperCase().startsWith("CREATE TABLE")) {
				tipo = ScriptsDTO.TIPO_CRIAR_TABELA;
			} else if (scriptsDTO.getSqlQuery().trim().toUpperCase().startsWith("INSERT INTO")) {
				tipo = ScriptsDTO.TIPO_INSERIR_REGISTRO;
			} else if (scriptsDTO.getSqlQuery().trim().toUpperCase().startsWith("DELETE FROM")) {
				tipo = ScriptsDTO.TIPO_DELETAR_REGISTRO;
			} else if (scriptsDTO.getSqlQuery().trim().toUpperCase().startsWith("ALTER TABLE")) {
				if (scriptsDTO.getSqlQuery().trim().toUpperCase().contains(" ADD CONSTRAINT ")) {
					tipo = ScriptsDTO.TIPO_ADICIONAR_CONSTRAINT;
				} else if (scriptsDTO.getSqlQuery().trim().toUpperCase().contains(" ADD ")) {
					tipo = ScriptsDTO.TIPO_ADICIONAR_COLUNA;
				}
			} else if (scriptsDTO.getSqlQuery().trim().toUpperCase().startsWith("ALTER TABLE")
					&& (scriptsDTO.getSqlQuery().trim().toUpperCase().contains("CHANGE COLUMN") || scriptsDTO.getSqlQuery().trim().toUpperCase().contains("RENAME COLUMN"))) {
				tipo = ScriptsDTO.TIPO_ALTERAR_COLUNA;
			} else if (scriptsDTO.getSqlQuery().trim().toUpperCase().startsWith("ALTER TABLE") && scriptsDTO.getSqlQuery().trim().toUpperCase().contains("DROP COLUMN")) {
				tipo = ScriptsDTO.TIPO_DELETAR_COLUNA;
			} else if (scriptsDTO.getSqlQuery().trim().toUpperCase().startsWith("DROP TABLE")) {
				tipo = ScriptsDTO.TIPO_DELETAR_TABELA;
			}
		}

		return tipo;
	}

}
