package br.com.centralit.citcorpore.ajaxForms;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.DataManagerDTO;
import br.com.centralit.citcorpore.bean.DataManagerFKRelatedDTO;
import br.com.centralit.citcorpore.bean.TabFederacaoDadosDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.TabFederacaoDadosDao;
import br.com.centralit.citcorpore.metainfo.bean.CamposObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.integracao.VisaoDao;
import br.com.centralit.citcorpore.metainfo.negocio.CamposObjetoNegocioService;
import br.com.centralit.citcorpore.metainfo.negocio.ObjetoNegocioService;
import br.com.centralit.citcorpore.metainfo.util.DataBaseMetaDadosUtil;
import br.com.centralit.citcorpore.metainfo.util.MetaUtil;
import br.com.centralit.citcorpore.negocio.DataBaseMetaDadosService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ImportInfoField;
import br.com.centralit.citcorpore.util.ImportInfoRecord;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.UtilImportData;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.dto.LogDados;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.JdbcEngine;
import br.com.citframework.service.LogDadosService;
import br.com.citframework.service.LogDadosServiceBean;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;
import br.com.citframework.util.UtilTratamentoArquivos;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DataManager extends AjaxFormAction {

	private ObjetoNegocioService objetoNegocioService;
	
	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.getSession(true).setAttribute("colUploadsGED", null);

		document.executeScript("uploadAnexos.refresh()");

	}

	@Override
	public Class getBeanClass() {
		return DataManagerDTO.class;
	}

	public void trataExport(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataManagerDTO dataManagerDTO = (DataManagerDTO) document.getBean();
		CamposObjetoNegocioService camposObjetoNegocioService = (CamposObjetoNegocioService) ServiceLocator.getInstance().getService(CamposObjetoNegocioService.class, null);
		if (dataManagerDTO.getIdObjetoNegocio() == null) {
			return;
		}

		Collection col = camposObjetoNegocioService.findByIdObjetoNegocio(dataManagerDTO.getIdObjetoNegocio());
		String table = "";
		table += "<table><tr><td style='background-color:yellow'><input type='checkbox' value='S' name='excluirAoExportar' onclick='validaExcl(this)'>"
				+ UtilI18N.internacionaliza(request, "dataManager.excluir") + "</td><td><input type='checkbox' value='S' name='exportarVinculos'>"
				+ UtilI18N.internacionaliza(request, "dataManager.exportarvinculos") + "</td><td><input type='checkbox' value='S' name='exportarSql'>" 
				+ UtilI18N.internacionaliza(request, "dataManager.exportarSql") + "</td></tr></table><br>";
		table += "<table cellspacing='1' cellpadding='1' width='100%' border='1'>";
		table += "<tr>";
		table += "<td>";
		table += UtilI18N.internacionaliza(request, "dataManager.chave");
		table += "</td>";
		table += "<td>";
		table += UtilI18N.internacionaliza(request, "dataManager.campo");
		table += "</td>";
		table += "<td>";
		table += UtilI18N.internacionaliza(request, "dataManager.condicao");
		table += "</td>";
		table += "<td>";
		table += UtilI18N.internacionaliza(request, "dataManager.valor");
		table += "</td>";
		table += "</tr>";
		if (col != null) {
			for (Iterator it = col.iterator(); it.hasNext();) {
				CamposObjetoNegocioDTO camposObjetoNegocioDto = (CamposObjetoNegocioDTO) it.next();
				// String strTam = "";
				// String chave = "";
				table += "<tr>";
				table += "<td>";
				if (camposObjetoNegocioDto.getPk() != null && camposObjetoNegocioDto.getPk().equalsIgnoreCase("S")) {
					table += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/ok.png' border='0'/>";
				} else {
					table += "&nbsp;";
				}
				table += "</td>";
				table += "<td>";
				table += (camposObjetoNegocioDto.getDescricao() == null ? camposObjetoNegocioDto.getNome() : camposObjetoNegocioDto.getDescricao()) + " (" + camposObjetoNegocioDto.getTipoDB() + ")";
				table += "</td>";
				table += "<td>";
				table += "<select name='cond_" + camposObjetoNegocioDto.getIdCamposObjetoNegocio() + "' id='cond_" + camposObjetoNegocioDto.getIdCamposObjetoNegocio() + "'>";
				boolean isStringType = MetaUtil.isStringType(camposObjetoNegocioDto.getTipoDB());
				boolean isDateType = MetaUtil.isDateType(camposObjetoNegocioDto.getTipoDB());
				table += "<option value=''>-- " + UtilI18N.internacionaliza(request, "dataManager.semFiltro") + " --</option>";
				table += "<option value='='>=</option>";
				table += "<option value='1'>&lt;&gt;</option>";
				table += "<option value='2'>&gt;</option>";
				table += "<option value='3'>&lt;</option>";
				table += "<option value='IN'>IN</option>";
				table += "<option value='NOT IN'>NOT IN</option>";
				if (isDateType) {
					table += "<option value='BETWEEN'>" + UtilI18N.internacionaliza(request, "dataManager.entre") + "</option>";
				}
				table += "<option value='IS NULL'>IS NULL</option>";
				if (isStringType) {
					table += "<option value='LIKE'>" + UtilI18N.internacionaliza(request, "dataManager.queContenha") + "</option>";
				}
				table += "</select>";
				table += "</td>";
				table += "<td>";
				table += "<input type='text' name='valor_" + camposObjetoNegocioDto.getIdCamposObjetoNegocio() + "' id='valor_" + camposObjetoNegocioDto.getIdCamposObjetoNegocio() + "' size='30'/>";
				table += "</td>";
				table += "</tr>";
			}
		}
		table += "</table>";
		document.getElementById("divExport").setInnerHTML(table);

		document.executeScript("geraPopup()");
	}

	public void importar(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// DataManagerDTO dataManagerDTO = (DataManagerDTO) document.getBean();
		Collection colUploadsGED = (Collection) request.getSession(true).getAttribute("colUploadsGED");
		if (colUploadsGED == null || colUploadsGED.size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "dataManager.naoHaArquivosImportar"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		String ORIGEM_SISTEMA = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ORIGEM_SISTEMA, " ");
		if (ORIGEM_SISTEMA == null || ORIGEM_SISTEMA.trim().equalsIgnoreCase("")) {
			document.alert(UtilI18N.internacionaliza(request, "dataManager.infoorigemdados"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		List colRecordsGeral = new ArrayList();
		for (Iterator it = colUploadsGED.iterator(); it.hasNext();) {
			UploadDTO uploadDTO = (UploadDTO) it.next();
			Collection colRecords = UtilImportData.readXMLFile(uploadDTO.getPath());
			if (colRecords != null) {
				colRecordsGeral.addAll(colRecords);
			}
		}
		importarDados(document, request, response, colRecordsGeral, true, ORIGEM_SISTEMA);
	}

	public void importarDados(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, List colRecordsGeral, boolean federarDados, String ORIGEM_SISTEMA) throws Exception {
		long qtdRegInsert = 0;
		long qtdRegUpdate = 0;
		int count = 0;
		DataBaseMetaDadosUtil dataBaseMetaDadosUtil = new DataBaseMetaDadosUtil();
		TabFederacaoDadosDao tabFederacaoDadosDao = new TabFederacaoDadosDao();
		for (Iterator itRecords = colRecordsGeral.iterator(); itRecords.hasNext();) {

			ImportInfoRecord importInfoRecord = (ImportInfoRecord) itRecords.next();

			System.out.println("\n TABELA " + importInfoRecord.getTableName() + " --- COUNT " + count);

			if (ORIGEM_SISTEMA.equalsIgnoreCase(importInfoRecord.getOrigem())) {
				federarDados = false;
			}
			String sqlWhere = "";
			List lstParmsInsert = new ArrayList();
			List lstParmsUpdate = new ArrayList();
			List lstParmsWhere = new ArrayList();
			String sqlInsert = "";
			String sqlInsertValues = "";
			String sqlUpdate = "";
			String sqlSelect = "";
			String nomePrimeiroCampo = "";

			String chaveOrigem = "";
			String chaveFinal = "";

			for (Iterator it = importInfoRecord.getColFields().iterator(); it.hasNext();) {
				ImportInfoField importInfoField = (ImportInfoField) it.next();

				System.out.println(" " + importInfoField.getNameField() + " = " + importInfoField.getValueField());

				if (nomePrimeiroCampo.trim().equalsIgnoreCase("")) {
					nomePrimeiroCampo = importInfoField.getNameField();
				}
				if (!sqlInsert.trim().equalsIgnoreCase("")) {
					sqlInsert = sqlInsert + ",";
					sqlInsertValues = sqlInsertValues + ",";
				}

				sqlInsert = sqlInsert + importInfoField.getNameField();
				sqlInsertValues = sqlInsertValues + "?";
				String strVal = "";
				if (importInfoField.isKey()) {
					if (!sqlWhere.trim().equalsIgnoreCase("")) {
						sqlWhere = sqlWhere + " AND ";
					}
					sqlWhere = sqlWhere + " " + importInfoField.getNameField() + " = ?";
					if (federarDados) {
						if (importInfoField.isSequence()) {
							strVal = localizaOrigemTabelaChaveOrig(importInfoRecord.getOrigem(), importInfoRecord.getTableName(), importInfoField.getValueField());
							if (strVal == null) {
								strVal = generateChaveFederada(importInfoRecord.getTableName(), importInfoField.getNameField());
							}
							lstParmsWhere.add(MetaUtil.convertType(importInfoField.getType(), strVal));

							if (!chaveFinal.trim().equalsIgnoreCase("")) {
								chaveFinal = chaveOrigem + "+";
							}
							chaveFinal = chaveFinal + strVal;
						} else {
							if (!chaveFinal.trim().equalsIgnoreCase("")) {
								chaveFinal = chaveOrigem + "+";
							}
							// Tem que localizar a chave referenciada na base federada.
							String tabelaPai = dataBaseMetaDadosUtil.getTabelaPaiByTableAndField(importInfoRecord.getTableName(), importInfoField.getNameField(), true);
							if (tabelaPai != null && !tabelaPai.trim().equalsIgnoreCase("")) {
								strVal = localizaOrigemTabelaChaveOrig(importInfoRecord.getOrigem(), tabelaPai, importInfoField.getValueField());
								if (strVal != null) {
									chaveFinal = chaveFinal + strVal;
								} else {
									chaveFinal = chaveFinal + importInfoField.getValueField();
									strVal = importInfoField.getValueField();
								}
								lstParmsWhere.add(MetaUtil.convertType(importInfoField.getType(), strVal));
							} else {
								chaveFinal = chaveFinal + importInfoField.getValueField();
								lstParmsWhere.add(MetaUtil.convertType(importInfoField.getType(), importInfoField.getValueField()));
							}
						}
					} else {
						strVal = importInfoField.getValueField();
						lstParmsWhere.add(MetaUtil.convertType(importInfoField.getType(), strVal));
					}
					if (!chaveOrigem.trim().equalsIgnoreCase("")) {
						chaveOrigem = chaveOrigem + "+";
					}
					chaveOrigem = chaveOrigem + importInfoField.getValueField();
				} else {
					if (!sqlUpdate.trim().equalsIgnoreCase("")) {
						sqlUpdate = sqlUpdate + ",";
					}
					sqlUpdate = sqlUpdate + importInfoField.getNameField() + " = ?";
					if (federarDados) {
						// Tem que localizar a chave referenciada na base federada.
						String tabelaPai = dataBaseMetaDadosUtil.getTabelaPaiByTableAndField(importInfoRecord.getTableName(), importInfoField.getNameField(), true);
						if (tabelaPai != null && !tabelaPai.trim().equalsIgnoreCase("")) {
							strVal = localizaOrigemTabelaChaveOrig(importInfoRecord.getOrigem(), tabelaPai, importInfoField.getValueField());
							if (strVal == null) {
								strVal = importInfoField.getValueField();
							}
							lstParmsUpdate.add(MetaUtil.convertType(importInfoField.getType(), strVal));
						} else {
							strVal = importInfoField.getValueField();
							if (importInfoField.getValueField().trim().equalsIgnoreCase("null")) {
								lstParmsUpdate.add(null);
							} else {
								lstParmsUpdate.add(MetaUtil.convertType(importInfoField.getType(), strVal));
							}
						}
					} else {
						strVal = importInfoField.getValueField();
						if (importInfoField.getValueField().trim().equalsIgnoreCase("null")) {
							lstParmsUpdate.add(null);
						} else {
							lstParmsUpdate.add(MetaUtil.convertType(importInfoField.getType(), strVal));
						}
					}
				}
				if (importInfoField.getValueField().trim().equalsIgnoreCase("null")) {
					lstParmsInsert.add(null);
				} else {
					lstParmsInsert.add(MetaUtil.convertType(importInfoField.getType(), strVal));
				}
			}
			if (federarDados) {
				TabFederacaoDadosDTO tabFederacaoDadosDto = new TabFederacaoDadosDTO();
				tabFederacaoDadosDto.setOrigem(importInfoRecord.getOrigem());
				tabFederacaoDadosDto.setChaveOriginal(chaveOrigem);
				tabFederacaoDadosDto.setChaveFinal(chaveFinal);
				tabFederacaoDadosDto.setNomeTabela(importInfoRecord.getTableName());
				tabFederacaoDadosDto.setCriacao(UtilDatas.getDataHoraAtual());
				tabFederacaoDadosDto.setUltAtualiz(UtilDatas.getDataHoraAtual());
				TabFederacaoDadosDTO tabFederacaoDadosAux = (TabFederacaoDadosDTO) tabFederacaoDadosDao.restore(tabFederacaoDadosDto);
				if (tabFederacaoDadosAux != null) {
					tabFederacaoDadosDto.setCriacao(null);
					tabFederacaoDadosDao.updateNotNull(tabFederacaoDadosDto);
				} else {
					tabFederacaoDadosDao.create(tabFederacaoDadosDto);
				}
			}

			lstParmsUpdate.addAll(lstParmsWhere);
			sqlInsert = "INSERT INTO " + importInfoRecord.getTableName() + "(" + sqlInsert + ") VALUES (" + sqlInsertValues + ")";
			sqlUpdate = "UPDATE " + importInfoRecord.getTableName() + " SET " + sqlUpdate + " WHERE " + sqlWhere;
			sqlSelect = "SELECT " + nomePrimeiroCampo + " FROM " + importInfoRecord.getTableName() + " WHERE " + sqlWhere;

			JdbcEngine jdbcEngine = new JdbcEngine(Constantes.getValue("DATABASE_ALIAS"), null);
			try {
				List lst = jdbcEngine.execSQL(sqlSelect, lstParmsWhere.toArray(), 0);
				if (lst == null || lst.size() == 0) {
					jdbcEngine.execUpdate(sqlInsert, lstParmsInsert.toArray());
					qtdRegInsert++;
				} else {
					jdbcEngine.execUpdate(sqlUpdate, lstParmsUpdate.toArray());
					qtdRegUpdate++;
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("O ERRO OCORREU NO REGISTRO " + count);
				System.err.println(sqlInsert + " - " + lstParmsInsert);
				System.err.println(sqlUpdate + " - " + lstParmsUpdate);
				System.err.println(sqlSelect + " - " + lstParmsWhere);
			}

			count++;
		}
		document.alert("Registros Inseridos: " + qtdRegInsert + ". Atualizados: " + qtdRegUpdate);
		request.getSession(true).setAttribute("colUploadsGED", null);
		document.executeScript("$('#divImport').dialog('close')");
		document.executeScript("$('#POPUP_EXPORTAR').dialog('close')");
		document.executeScript("limpar_upload()");
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	}

	public String generateChaveFederada(String tableName, String nomeField) throws PersistenceException {
		JdbcEngine jdbcEngine = new JdbcEngine(Constantes.getValue("DATABASE_ALIAS"), null);
		String sql = "SELECT MAX(" + nomeField + ") FROM " + tableName + "";
		List lst = jdbcEngine.execSQL(sql, null, 0);
		if (lst == null || lst.size() == 0) {
			return "1";
		}
		Object[] obj = (Object[]) lst.get(0);
		long l = 0;
		try {
			l = Long.parseLong(obj[0].toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		l = l + 1;
		return "" + l;
	}

	public String localizaOrigemTabelaChaveOrig(String origem, String tableName, String chaveOrig) throws Exception {
		TabFederacaoDadosDao tabFederadaDao = new TabFederacaoDadosDao();
		Collection col = tabFederadaDao.findByOrigemTabelaChaveOriginal(origem, tableName, chaveOrig);
		if (col != null && col.size() > 0) {
			TabFederacaoDadosDTO tabFederacaoDadosDTO = (TabFederacaoDadosDTO) ((List) col).get(0);
			return tabFederacaoDadosDTO.getChaveFinal();
		}
		return null;
	}

	public void exportar(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataManagerDTO dataManagerDTO = (DataManagerDTO) document.getBean();
		if (dataManagerDTO.getIdObjetoNegocio() == null) {
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);
		if (usuarioDto == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		String ORIGEM_SISTEMA = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ORIGEM_SISTEMA, " ");
		if (ORIGEM_SISTEMA == null || ORIGEM_SISTEMA.trim().equalsIgnoreCase("")) {
			document.alert(UtilI18N.internacionaliza(request, "dataManager.infoorigemdados"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		String sqlDelete = "";
		String nomeTabela = "";
		HashMap hashValores = getFormFields(request);
		String excluirAoExportar = (String) hashValores.get("excluirAoExportar".toUpperCase());
		String exportarVinculos = (String) hashValores.get("exportarVinculos".toUpperCase());
		String exportarSql = (String) hashValores.get("exportarSql".toUpperCase());
		StringBuffer strAux = null;
		if ((excluirAoExportar != null && excluirAoExportar.equalsIgnoreCase("S")) || (exportarVinculos != null && exportarVinculos.equalsIgnoreCase("S"))) {
			if(exportarSql != null && exportarSql.equalsIgnoreCase("S"))
				strAux = geraRecursiveExportObjetoNegocioSql(hashValores, dataManagerDTO.getIdObjetoNegocio(), sqlDelete, nomeTabela, "", "");
			else
				strAux = geraRecursiveExportObjetoNegocio(hashValores, dataManagerDTO.getIdObjetoNegocio(), sqlDelete, nomeTabela, "", "");
		} else {
			if(exportarSql != null && exportarSql.equalsIgnoreCase("S"))
				strAux = geraExportObjetoNegocioSql(hashValores, dataManagerDTO.getIdObjetoNegocio(), sqlDelete, nomeTabela, "", "");
			else
				strAux = geraExportObjetoNegocio(hashValores, dataManagerDTO.getIdObjetoNegocio(), sqlDelete, nomeTabela, "", "");
		}
		nomeTabela = (String) hashValores.get("NOMETABELADB");
		sqlDelete = (String) hashValores.get("COMMANDDELETE");
		String sqlExport = (String) hashValores.get("COMMAND");
		
		String strDateTime = (new java.util.Date()).toString();
		strDateTime = strDateTime.replaceAll(" ", "_");
		strDateTime = strDateTime.replaceAll(":", "_");
		String str = "";
		
		if(exportarSql != null && exportarSql.equalsIgnoreCase("S")){
			str += "\n-- origem: " + ORIGEM_SISTEMA;
			str += "\n-- Data: " + UtilDatas.dateToSTR(UtilDatas.getDataAtual(), "dd/MM/yyyy hh:MM:ss") + "\n"; 
			str += "\n" + strAux.toString();
			str += "\n-- END SQL"; 
			UtilTratamentoArquivos.geraFileTxtFromString(CITCorporeUtil.caminho_real_app + "/exportSQL/export_data_" + strDateTime + ".sql", str);
		}else{
			str = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<tables origem='" + ORIGEM_SISTEMA + "'>\n" + strAux.toString();
			str = "" + str + "\n</tables>";
			UtilTratamentoArquivos.geraFileTxtFromString(CITCorporeUtil.caminho_real_app + "/exportXML/export_data_" + strDateTime + ".smart", str);
		}

		JdbcEngine jdbcEngine = new JdbcEngine(Constantes.getValue("DATABASE_ALIAS"), null);
		LogDados logDados = new LogDados();
		logDados.setDtAtualizacao(UtilDatas.getDataHoraAtual());
		logDados.setIdUsuario(usuarioDto.getIdUsuario());
		logDados.setDataInicio(UtilDatas.getDataAtual());
		logDados.setDataLog(UtilDatas.getDataHoraAtual());
		logDados.setNomeTabela(nomeTabela);
		logDados.setOperacao("Export");
		logDados.setLocalOrigem(usuarioDto.getNomeUsuario());
		logDados.setDados("Execute sql export... " + sqlExport);

		LogDadosService lds = new LogDadosServiceBean();

		try {
			logDados = (LogDados) lds.create(logDados);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (excluirAoExportar != null && excluirAoExportar.equalsIgnoreCase("S")) {
			logDados.setOperacao("Delete after export");
			sqlDelete = sqlDelete + ";";
			String[] strDel = sqlDelete.split(";");
			if (strDel != null) {
				for (int i = 0; i < strDel.length; i++) {
					logDados.setDados("Execute sql delete... " + strDel[i]);
					try {
						logDados = (LogDados) lds.create(logDados);
						try {
							System.out.println("Executando sql... " + strDel[i]);
							jdbcEngine.execUpdate(strDel[i], null); // Antes de executar a operacao de exclusao, faz a gravacao do LOG.
						} catch (Exception e) {
							e.printStackTrace();
							logDados.setDtAtualizacao(UtilDatas.getDataHoraAtual()); // Se falhar a execucao da operacao delete, gera informando que falhou!
							logDados.setDataLog(UtilDatas.getDataHoraAtual());
							logDados.setDados("FAILED! [Original Id Log: " + logDados.getIdlog() + "] Execute sql delete... " + strDel[i]);
							lds.create(logDados);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		document.alert(UtilI18N.internacionaliza(request, "dataManager.arquivoExportado"));
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
		if(exportarSql != null && exportarSql.equalsIgnoreCase("S"))
			document.executeScript("getFile('" + CITCorporeUtil.caminho_real_app + "/exportSQL/export_data_" + strDateTime + ".sql', 'export_data_" + strDateTime + ".sql')");
		else
			document.executeScript("getFile('" + CITCorporeUtil.caminho_real_app + "/exportXML/export_data_" + strDateTime + ".smart', 'export_data_" + strDateTime + ".smart')");
	}

	public StringBuffer geraRecursiveExportObjetoNegocio(HashMap hashValores, Integer idObjetoNegocio, String sqlDelete, String nomeTabela, String filterAditional, String order)
			throws ServiceException, Exception {
		StringBuffer strAux = geraExportObjetoNegocio(hashValores, idObjetoNegocio, sqlDelete, nomeTabela, filterAditional, order);
		nomeTabela = (String) hashValores.get("NOMETABELADB");
		String tabelasTratadas = (String) hashValores.get("TABELASTRATADAS");
		String keysProcessed = (String) hashValores.get("KEYS");
		Collection colRelateds = getFks(nomeTabela);
		System.out.println("Tratando FKS da Tabelas : " + nomeTabela);
		if (colRelateds != null && colRelateds.size() > 0) {
			ObjetoNegocioService objetoNegocioService = (ObjetoNegocioService) ServiceLocator.getInstance().getService(ObjetoNegocioService.class, null);
			for (Iterator it = colRelateds.iterator(); it.hasNext();) {
				DataManagerFKRelatedDTO dataManagerFKRelatedDTO = (DataManagerFKRelatedDTO) it.next();
				System.out.println("		Tabela FK : " + dataManagerFKRelatedDTO.getNomeTabelaRelacionada());

				ObjetoNegocioDTO objNegocioDto = (ObjetoNegocioDTO) objetoNegocioService.getByNomeTabelaDB(dataManagerFKRelatedDTO.getNomeTabelaRelacionada());
				if (objNegocioDto != null) {
					if (tabelasTratadas.indexOf(("'" + objNegocioDto.getNomeTabelaDB().trim() + "'").toUpperCase()) > -1) {
						System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Tabela ja processada: " + objNegocioDto.getNomeTabelaDB());
						continue;
					}
					String filter = "";
					if (keysProcessed != null && !keysProcessed.trim().equalsIgnoreCase("")) {
						filter = dataManagerFKRelatedDTO.getPartChild() + " IN (" + keysProcessed + ")";
						System.out.println("		Tabela FK : " + dataManagerFKRelatedDTO.getNomeTabelaRelacionada() + " Processando... " + objNegocioDto.getNomeTabelaDB() + " Filtro: " + filter);
						StringBuffer strAux2 = geraRecursiveExportObjetoNegocio(hashValores, objNegocioDto.getIdObjetoNegocio(), sqlDelete, dataManagerFKRelatedDTO.getNomeTabelaRelacionada(), filter,
								"");
						strAux.append("\n");
						strAux.append(strAux2);
					}
				}
			}
		}
		return strAux;
	}

	public Collection getFks(String nomeTabela) throws Exception {
		DataBaseMetaDadosUtil dataBaseMetaDadosUtil = new DataBaseMetaDadosUtil();
		VisaoDao visaoDao = new VisaoDao();
		Connection con = (Connection) visaoDao.getTransactionControler().getTransactionObject();
		String DB_SCHEMA = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.DB_SCHEMA, " ");
		if (DB_SCHEMA == null || DB_SCHEMA.trim().equalsIgnoreCase("")) {
			DB_SCHEMA = "citsmart";
		}
		Collection colRelateds = dataBaseMetaDadosUtil.readFK(con, DB_SCHEMA, DB_SCHEMA, nomeTabela);
		con.close();
		con = null;
		return colRelateds;
	}

	public void exportarTudo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// DataManagerDTO dataManagerDTO = (DataManagerDTO) document.getBean();
		ObjetoNegocioService objetoNegocioService = (ObjetoNegocioService) ServiceLocator.getInstance().getService(ObjetoNegocioService.class, null);
		HashMap hashValores = getFormFields(request);
		StringBuffer str = new StringBuffer();

		String ORIGEM_SISTEMA = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ORIGEM_SISTEMA, " ");
		if (ORIGEM_SISTEMA == null || ORIGEM_SISTEMA.trim().equalsIgnoreCase("")) {
			document.alert(UtilI18N.internacionaliza(request, "dataManager.infoorigemdados"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		String nomeTabela = "";
		Collection colObjsNeg = objetoNegocioService.listAtivos();
		if (colObjsNeg != null) {
			// boolean bPrim = true;
			for (Iterator it = colObjsNeg.iterator(); it.hasNext();) {
				ObjetoNegocioDTO objetoNegocioDTO = (ObjetoNegocioDTO) it.next();
				String sqlDelete = "";
				StringBuffer strAux = geraExportObjetoNegocio(hashValores, objetoNegocioDTO.getIdObjetoNegocio(), sqlDelete, nomeTabela, "", "");
				if (strAux != null) {
					str.append(strAux);
				}
			}
		}

		String strAux = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<tables origem='" + ORIGEM_SISTEMA + "'>\n" + str.toString();
		strAux = "" + strAux + "\n</tables>";

		String strDateTime = (new java.util.Date()).toString();
		strDateTime = strDateTime.replaceAll(" ", "_");
		strDateTime = strDateTime.replaceAll(":", "_");
		UtilTratamentoArquivos.geraFileTxtFromString(CITCorporeUtil.caminho_real_app + "/exportXML/export_data_ALL_" + strDateTime + ".smart", strAux.toString());

		document.alert(UtilI18N.internacionaliza(request, "dataManager.arquivoExportado"));
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
		document.executeScript("getFile('" + CITCorporeUtil.caminho_real_app + "/exportXML/export_data_ALL_" + strDateTime + ".smart', 'export_data_ALL_" + strDateTime + ".smart')");
	}

	public StringBuffer geraExportObjetoNegocio(HashMap hashValores, Integer idObjetoNegocio, String sqlDelete, String nomeTabela, String filterAditional, String order) throws ServiceException,
			Exception {
		CamposObjetoNegocioService camposObjetoNegocioService = (CamposObjetoNegocioService) ServiceLocator.getInstance().getService(CamposObjetoNegocioService.class, null);
		ObjetoNegocioService objetoNegocioService = (ObjetoNegocioService) ServiceLocator.getInstance().getService(ObjetoNegocioService.class, null);
		ObjetoNegocioDTO objetoNegocioDTO = new ObjetoNegocioDTO();
		objetoNegocioDTO.setIdObjetoNegocio(idObjetoNegocio);
		objetoNegocioDTO = (ObjetoNegocioDTO) objetoNegocioService.restore(objetoNegocioDTO);
		Collection col = camposObjetoNegocioService.findByIdObjetoNegocio(idObjetoNegocio);
		String sqlCondicao = "";
		String sqlCampos = "";

		String excluirAoExportar = (String) hashValores.get("excluirAoExportar".toUpperCase());

		// Antes de fazer a exportacao, faz o sincronismo com o DB, pois pode estar desatualizado!
		DataBaseMetaDadosUtil dataBaseMetaDadosUtil = new DataBaseMetaDadosUtil();
		dataBaseMetaDadosUtil.sincronizaObjNegDB(objetoNegocioDTO.getNomeTabelaDB(), false);
		System.out.println("Sincronizando tabela: " + objetoNegocioDTO.getNomeTabelaDB());
		Thread.sleep(1000); // Da uma sleep pra liberar cursores presos e dar um tempo pro GC passar.
		System.gc();

		hashValores.put("NOMETABELADB", objetoNegocioDTO.getNomeTabelaDB());
		String tabelasTratadas = (String) hashValores.get("TABELASTRATADAS");
		tabelasTratadas = UtilStrings.nullToVazio(tabelasTratadas);
		tabelasTratadas = tabelasTratadas + "'" + objetoNegocioDTO.getNomeTabelaDB() + "'";
		hashValores.put("TABELASTRATADAS", tabelasTratadas);
		// nomeTabela = objetoNegocioDTO.getNomeTabelaDB();
		if (col != null) {
			for (Iterator it = col.iterator(); it.hasNext();) {
				CamposObjetoNegocioDTO camposObjetoNegocioDto = (CamposObjetoNegocioDTO) it.next();
				if (!sqlCampos.trim().equalsIgnoreCase("")) {
					sqlCampos += ",";
				}
				sqlCampos = sqlCampos + camposObjetoNegocioDto.getNomeDB();
				String cond = (String) hashValores.get("COND_" + camposObjetoNegocioDto.getIdCamposObjetoNegocio());
				String valor = (String) hashValores.get("VALOR_" + camposObjetoNegocioDto.getIdCamposObjetoNegocio());
				if (!UtilStrings.nullToVazio(cond).trim().equalsIgnoreCase("") && !UtilStrings.nullToVazio(valor).trim().equalsIgnoreCase("")) {
					sqlCondicao = sqlCondicao + " " + camposObjetoNegocioDto.getNomeDB();
					if (cond != null && cond.equalsIgnoreCase("1")) {
						sqlCondicao = sqlCondicao + " <> ";
					} else if (cond != null && cond.equalsIgnoreCase("2")) {
						sqlCondicao = sqlCondicao + " > ";
					} else if (cond != null && cond.equalsIgnoreCase("3")) {
						sqlCondicao = sqlCondicao + " < ";
					} else {
						sqlCondicao = sqlCondicao + " " + cond + " ";
					}
					boolean isStringType = MetaUtil.isStringType(camposObjetoNegocioDto.getTipoDB());
					if (isStringType) {
						if (cond.equalsIgnoreCase("=") || cond.equalsIgnoreCase("1") || cond.equalsIgnoreCase("2") || cond.equalsIgnoreCase("3")) {
							valor = valor.replaceAll("'", "");
							valor = "'" + valor + "'";
						}
					}
					if (cond != null && !cond.trim().equalsIgnoreCase("IS NULL")) {
						sqlCondicao = sqlCondicao + valor;
					}
				}
			}
		}
		String sqlFinal = "SELECT " + sqlCampos + " FROM " + objetoNegocioDTO.getNomeTabelaDB();
		sqlDelete = "DELETE FROM " + objetoNegocioDTO.getNomeTabelaDB();
		if (!sqlCondicao.trim().equalsIgnoreCase("")) {
			if (filterAditional != null && !filterAditional.trim().equalsIgnoreCase("")) {
				sqlFinal = sqlFinal + " WHERE " + sqlCondicao + " AND (" + filterAditional + ")";
				sqlDelete = sqlDelete + " WHERE " + sqlCondicao + " AND (" + filterAditional + ")";
			} else {
				sqlFinal = sqlFinal + " WHERE " + sqlCondicao;
				sqlDelete = sqlDelete + " WHERE " + sqlCondicao;
			}
		} else {
			if (filterAditional != null && !filterAditional.trim().equalsIgnoreCase("")) {
				sqlFinal = sqlFinal + " WHERE (" + filterAditional + ")";
				sqlDelete = sqlDelete + " WHERE (" + filterAditional + ")";
			}
		}
		if (order != null && !order.trim().equalsIgnoreCase(""))
			sqlFinal += " ORDER BY " + order;

		String sqlDeleteAux = (String) hashValores.get("COMMANDDELETE");
		String sqlExportAux = (String) hashValores.get("COMMAND");

		if (!UtilStrings.nullToVazio(sqlDeleteAux).trim().equalsIgnoreCase("")) {
			sqlDeleteAux = sqlDelete + "; " + UtilStrings.nullToVazio(sqlDeleteAux);
		} else {
			sqlDeleteAux = sqlDelete;
		}
		if (!UtilStrings.nullToVazio(sqlExportAux).trim().equalsIgnoreCase("")) {
			sqlExportAux = sqlFinal + "; " + UtilStrings.nullToVazio(sqlExportAux);
		} else {
			sqlExportAux = sqlFinal;
		}

		hashValores.put("COMMANDDELETE", sqlDeleteAux);
		hashValores.put("COMMAND", sqlExportAux);
		JdbcEngine jdbcEngine = new JdbcEngine(Constantes.getValue("DATABASE_ALIAS"), null);
		List lst = null;
		try {
			lst = jdbcEngine.execSQL(sqlFinal, null, 0);
		} catch (Exception e) {
			e.printStackTrace();
			return new StringBuffer("OCORREU ERRO NA GERACAO DOS DADOS!" + e.getMessage());
		}
		StringBuffer strXML = new StringBuffer();
		strXML.append("<table name='" + objetoNegocioDTO.getNomeTabelaDB() + "'>\n");
		strXML.append("<command><![CDATA[" + sqlFinal + "]]></command>\n");
		if (excluirAoExportar != null && excluirAoExportar.equalsIgnoreCase("S")) {
			strXML.append("<commandDelete><![CDATA[" + sqlDelete + "]]></commandDelete>\n");
		} else {
			strXML.append("<commandDelete>NONE</commandDelete>\n");
		}
		String keysProcessed = "";
		if (lst != null) {
			int j = 0;
			for (Iterator itDados = lst.iterator(); itDados.hasNext();) {
				Object[] obj = (Object[]) itDados.next();
				int i = 0;
				j++;
				strXML.append("<record number='" + j + "'>\n");
				for (Iterator it = col.iterator(); it.hasNext();) {
					CamposObjetoNegocioDTO camposObjetoNegocioDto = (CamposObjetoNegocioDTO) it.next();
					String key = "n";
					boolean isStringType = MetaUtil.isStringType(camposObjetoNegocioDto.getTipoDB());
					if (camposObjetoNegocioDto.getPk() != null && camposObjetoNegocioDto.getPk().equalsIgnoreCase("S")) {
						key = "y";
						if (!keysProcessed.trim().equalsIgnoreCase("")) {
							keysProcessed = keysProcessed + ",";
						}
						if (isStringType) {
							keysProcessed = keysProcessed + "'" + obj[i] + "'";
						} else {
							keysProcessed = keysProcessed + "" + obj[i] + "";
						}
					}
					String sequence = "n";
					if (camposObjetoNegocioDto.getSequence() != null && camposObjetoNegocioDto.getSequence().equalsIgnoreCase("S")) {
						sequence = "y";
					}
					strXML.append("<field name='" + camposObjetoNegocioDto.getNomeDB() + "' key='" + key + "' sequence='" + sequence + "' type='" + camposObjetoNegocioDto.getTipoDB().trim() + "'>");
					if (isStringType) {
						strXML.append("<![CDATA[");
					}
					strXML.append(obj[i]);
					if (isStringType) {
						strXML.append("]]>");
					}
					strXML.append("</field>\n");
					i++;
				}
				strXML.append("</record>\n");
			}
		}
		strXML.append("</table>\n");
		hashValores.put("KEYS", keysProcessed);

		return strXML;
	}

	public void limparUpload(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession(true).setAttribute("colUploadsGED", null);
		document.alert(UtilI18N.internacionaliza(request, "dataManager.limparuploadsucesso"));
	}

	private HashMap getFormFields(HttpServletRequest req) {
		try {
			req.setCharacterEncoding("ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			System.out.println("PROBLEMA COM CODIFICACAO DE CARACTERES!!! [AjaxProcessEvent.getFormFields()]");
			e.printStackTrace();
		}
		HashMap formFields = new HashMap();
		Enumeration en = req.getParameterNames();
		String[] strValores;
		while (en.hasMoreElements()) {
			String nomeCampo = (String) en.nextElement();
			strValores = req.getParameterValues(nomeCampo);
			if (strValores.length == 0) {
				formFields.put(nomeCampo.toUpperCase(), UtilStrings.decodeCaracteresEspeciais(req.getParameter(nomeCampo)));
			} else {
				if (strValores.length == 1) {
					formFields.put(nomeCampo.toUpperCase(), UtilStrings.decodeCaracteresEspeciais(strValores[0]));
				} else {
					formFields.put(nomeCampo.toUpperCase(), strValores);
				}
			}
		}
		return formFields;
	}

	public void debugValuesFromRequest(HashMap hashValores) {
		Set set = hashValores.entrySet();
		Iterator i = set.iterator();

		System.out.print("------- VALORES DO REQUEST: -------");
		while (i.hasNext()) {
			Map.Entry me = (Map.Entry) i.next();
			System.out.print("-------------> [" + me.getKey() + "]: [" + me.getValue() + "]");
		}
	}
	
	public StringBuffer geraExportObjetoNegocioSql(HashMap hashValores, Integer idObjetoNegocio, String sqlDelete, String nomeTabela, String filterAditional, String order) throws ServiceException,
	Exception{
		CamposObjetoNegocioService camposObjetoNegocioService = (CamposObjetoNegocioService) ServiceLocator.getInstance().getService(CamposObjetoNegocioService.class, null);
		ObjetoNegocioService objetoNegocioService = (ObjetoNegocioService) ServiceLocator.getInstance().getService(ObjetoNegocioService.class, null);
		ObjetoNegocioDTO objetoNegocioDTO = new ObjetoNegocioDTO();
		objetoNegocioDTO.setIdObjetoNegocio(idObjetoNegocio);
		objetoNegocioDTO = (ObjetoNegocioDTO) objetoNegocioService.restore(objetoNegocioDTO);
		Collection<CamposObjetoNegocioDTO> col = camposObjetoNegocioService.findByIdObjetoNegocio(idObjetoNegocio);
		String sqlCondicao = "";
		String sqlCampos = "";
		String sqlInsert = "";
		String camposInsert = "";

		//String excluirAoExportar = (String) hashValores.get("excluirAoExportar".toUpperCase());

		// Antes de fazer a exportacao, faz o sincronismo com o DB, pois pode estar desatualizado!
		DataBaseMetaDadosUtil dataBaseMetaDadosUtil = new DataBaseMetaDadosUtil();
		dataBaseMetaDadosUtil.sincronizaObjNegDB(objetoNegocioDTO.getNomeTabelaDB(), false);
		System.out.println("Sincronizando tabela: " + objetoNegocioDTO.getNomeTabelaDB());
		Thread.sleep(1000); // Da uma sleep pra liberar cursores presos e dar um tempo pro GC passar.
		System.gc();

		hashValores.put("NOMETABELADB", objetoNegocioDTO.getNomeTabelaDB());
		String tabelasTratadas = (String) hashValores.get("TABELASTRATADAS");
		tabelasTratadas = UtilStrings.nullToVazio(tabelasTratadas);
		tabelasTratadas = tabelasTratadas + "'" + objetoNegocioDTO.getNomeTabelaDB() + "'";
		hashValores.put("TABELASTRATADAS", tabelasTratadas);
		if (col != null) {
			for(CamposObjetoNegocioDTO camposObjeto : col){
				if (!sqlCampos.trim().equalsIgnoreCase("")) {
					sqlCampos += ",";
					camposInsert += ","; 
				}
				sqlCampos = sqlCampos + camposObjeto.getNomeDB();
				camposInsert += "'" + camposObjeto.getNomeDB() + "'"; 
				String cond = (String) hashValores.get("COND_" + camposObjeto.getIdCamposObjetoNegocio());
				String valor = (String) hashValores.get("VALOR_" + camposObjeto.getIdCamposObjetoNegocio());
				if (!UtilStrings.nullToVazio(cond).trim().equalsIgnoreCase("") && !UtilStrings.nullToVazio(valor).trim().equalsIgnoreCase("")) {
					sqlCondicao = sqlCondicao + " " + camposObjeto.getNomeDB();
					if (cond != null && cond.equalsIgnoreCase("1")) {
						sqlCondicao = sqlCondicao + " <> ";
					} else if (cond != null && cond.equalsIgnoreCase("2")) {
						sqlCondicao = sqlCondicao + " > ";
					} else if (cond != null && cond.equalsIgnoreCase("3")) {
						sqlCondicao = sqlCondicao + " < ";
					} else {
						sqlCondicao = sqlCondicao + " " + cond + " ";
					}
					boolean isStringType = MetaUtil.isStringType(camposObjeto.getTipoDB());
					if (isStringType) {
						if (cond.equalsIgnoreCase("=") || cond.equalsIgnoreCase("1") || cond.equalsIgnoreCase("2") || cond.equalsIgnoreCase("3")) {
							valor = valor.replaceAll("'", "");
							valor = "'" + valor + "'";
						}
					}
					if (cond != null && !cond.trim().equalsIgnoreCase("IS NULL")) {
						sqlCondicao = sqlCondicao + valor;
					}
				}
			}
		}
		String sqlFinal = "SELECT " + sqlCampos + " FROM " + objetoNegocioDTO.getNomeTabelaDB();
		sqlDelete = "DELETE FROM " + objetoNegocioDTO.getNomeTabelaDB();
		if (!sqlCondicao.trim().equalsIgnoreCase("")) {
			if (filterAditional != null && !filterAditional.trim().equalsIgnoreCase("")) {
				sqlFinal = sqlFinal + " WHERE " + sqlCondicao + " AND (" + filterAditional + ")";
				sqlDelete = sqlDelete + " WHERE " + sqlCondicao + " AND (" + filterAditional + ")";
			} else {
				sqlFinal = sqlFinal + " WHERE " + sqlCondicao;
				sqlDelete = sqlDelete + " WHERE " + sqlCondicao;
			}
		} else {
			if (filterAditional != null && !filterAditional.trim().equalsIgnoreCase("")) {
				sqlFinal = sqlFinal + " WHERE (" + filterAditional + ")";
				sqlDelete = sqlDelete + " WHERE (" + filterAditional + ")";
			}
		}
		if (order != null && !order.trim().equalsIgnoreCase(""))
			sqlFinal += " ORDER BY " + order;

		String sqlDeleteAux = (String) hashValores.get("COMMANDDELETE");
		String sqlExportAux = (String) hashValores.get("COMMAND");

		if (!UtilStrings.nullToVazio(sqlDeleteAux).trim().equalsIgnoreCase("")) {
			sqlDeleteAux = sqlDelete + "; " + UtilStrings.nullToVazio(sqlDeleteAux);
		} else {
			sqlDeleteAux = sqlDelete;
		}
		if (!UtilStrings.nullToVazio(sqlExportAux).trim().equalsIgnoreCase("")) {
			sqlExportAux = sqlFinal + "; " + UtilStrings.nullToVazio(sqlExportAux);
		} else {
			sqlExportAux = sqlFinal;
		}

		hashValores.put("COMMANDDELETE", sqlDeleteAux);
		hashValores.put("COMMAND", sqlExportAux);
		JdbcEngine jdbcEngine = new JdbcEngine(Constantes.getValue("DATABASE_ALIAS"), null);
		List lst = null;
		try {
			lst = jdbcEngine.execSQL(sqlFinal, null, 0);
		} catch (Exception e) {
			e.printStackTrace();
			return new StringBuffer("OCORREU ERRO NA GERACAO DOS DADOS!" + e.getMessage());
		}
		
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("");
	
		sqlInsert = "INSERT INTO '" + objetoNegocioDTO.getNomeTabelaDB() + "' (" + camposInsert + ") VALUES (";
		
		String keysProcessed = "";
		if (lst != null) {
			for (Iterator itDados = lst.iterator(); itDados.hasNext();) {
				strSQL.append(sqlInsert);
				Object[] obj = (Object[]) itDados.next();
				int i = 0;
				for (Iterator it = col.iterator(); it.hasNext();) {
					CamposObjetoNegocioDTO camposObjetoNegocioDto = (CamposObjetoNegocioDTO) it.next();
					boolean isStringType = MetaUtil.isStringType(camposObjetoNegocioDto.getTipoDB());
					if (camposObjetoNegocioDto.getPk() != null && camposObjetoNegocioDto.getPk().equalsIgnoreCase("S")) {
						if (!keysProcessed.trim().equalsIgnoreCase("")) {
							keysProcessed = keysProcessed + ",";
						}
						if (isStringType) {
							keysProcessed = keysProcessed + "'" + obj[i] + "'";
						} else {
							keysProcessed = keysProcessed + "" + obj[i] + "";
						}
					}
					if(i != 0)
						strSQL.append(",");
					if (isStringType && obj[i] != null) {
						strSQL.append("'");
					}
					strSQL.append(obj[i]);
					if (isStringType && obj[i] != null) {
						strSQL.append("'");
					}
					i++;
				}
				strSQL.append(");\n");
			}
		}
		
		hashValores.put("KEYS", keysProcessed);

		return strSQL;
	}
	
	public StringBuffer geraRecursiveExportObjetoNegocioSql(HashMap hashValores, Integer idObjetoNegocio, String sqlDelete, String nomeTabela, String filterAditional, String order)
			throws ServiceException, Exception {
		StringBuffer strAux = geraExportObjetoNegocioSql(hashValores, idObjetoNegocio, sqlDelete, nomeTabela, filterAditional, order);
		nomeTabela = (String) hashValores.get("NOMETABELADB");
		String tabelasTratadas = (String) hashValores.get("TABELASTRATADAS");
		String keysProcessed = (String) hashValores.get("KEYS");
		Collection colRelateds = getFks(nomeTabela);
		System.out.println("Tratando FKS da Tabelas : " + nomeTabela);
		if (colRelateds != null && colRelateds.size() > 0) {
			ObjetoNegocioService objetoNegocioService = (ObjetoNegocioService) ServiceLocator.getInstance().getService(ObjetoNegocioService.class, null);
			for (Iterator it = colRelateds.iterator(); it.hasNext();) {
				DataManagerFKRelatedDTO dataManagerFKRelatedDTO = (DataManagerFKRelatedDTO) it.next();
				System.out.println("		Tabela FK : " + dataManagerFKRelatedDTO.getNomeTabelaRelacionada());

				ObjetoNegocioDTO objNegocioDto = (ObjetoNegocioDTO) objetoNegocioService.getByNomeTabelaDB(dataManagerFKRelatedDTO.getNomeTabelaRelacionada());
				if (objNegocioDto != null) {
					if (tabelasTratadas.indexOf(("'" + objNegocioDto.getNomeTabelaDB().trim() + "'").toUpperCase()) > -1) {
						System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Tabela ja processada: " + objNegocioDto.getNomeTabelaDB());
						continue;
					}
					String filter = "";
					if (keysProcessed != null && !keysProcessed.trim().equalsIgnoreCase("")) {
						filter = dataManagerFKRelatedDTO.getPartChild() + " IN (" + keysProcessed + ")";
						System.out.println("		Tabela FK : " + dataManagerFKRelatedDTO.getNomeTabelaRelacionada() + " Processando... " + objNegocioDto.getNomeTabelaDB() + " Filtro: " + filter);
						StringBuffer strAux2 = geraRecursiveExportObjetoNegocioSql(hashValores, objNegocioDto.getIdObjetoNegocio(), sqlDelete, dataManagerFKRelatedDTO.getNomeTabelaRelacionada(), filter,
								"");
						strAux.append("\n");
						strAux.append(strAux2);
					}
				}
			}
		}
		return strAux;
	}
	
	public void exportarTudoSql(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// DataManagerDTO dataManagerDTO = (DataManagerDTO) document.getBean();
		ObjetoNegocioService objetoNegocioService = (ObjetoNegocioService) ServiceLocator.getInstance().getService(ObjetoNegocioService.class, null);
		HashMap hashValores = getFormFields(request);
		StringBuffer str = new StringBuffer();

		String ORIGEM_SISTEMA = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ORIGEM_SISTEMA, " ");
		if (ORIGEM_SISTEMA == null || ORIGEM_SISTEMA.trim().equalsIgnoreCase("")) {
			document.alert(UtilI18N.internacionaliza(request, "dataManager.infoorigemdados"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		String nomeTabela = "";
		Collection colObjsNeg = objetoNegocioService.listAtivos();
		if (colObjsNeg != null) {
			// boolean bPrim = true;
			for (Iterator it = colObjsNeg.iterator(); it.hasNext();) {
				ObjetoNegocioDTO objetoNegocioDTO = (ObjetoNegocioDTO) it.next();
				String sqlDelete = "";
				StringBuffer strAux = geraExportObjetoNegocioSql(hashValores, objetoNegocioDTO.getIdObjetoNegocio(), sqlDelete, nomeTabela, "", "");
				if (strAux != null) {
					str.append(strAux);
				}
			}
		}

		String strAux = "";
		strAux += "\n-- origem: " + ORIGEM_SISTEMA;
		strAux += "\n-- Data: " + UtilDatas.dateToSTR(UtilDatas.getDataAtual(), "dd/MM/yyyy hh:MM:ss") + "\n"; 
		strAux += "\n" + str.toString();
		strAux += "\n-- END SQL"; 
		

		String strDateTime = (new java.util.Date()).toString();
		strDateTime = strDateTime.replaceAll(" ", "_");
		strDateTime = strDateTime.replaceAll(":", "_");
		UtilTratamentoArquivos.geraFileTxtFromString(CITCorporeUtil.caminho_real_app + "/exportSQL/export_data_ALL_" + strDateTime + ".sql", strAux.toString());
		
		document.alert(UtilI18N.internacionaliza(request, "dataManager.arquivoExportado"));
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
		document.executeScript("getFile('" + CITCorporeUtil.caminho_real_app + "/exportXML/export_data_ALL_" + strDateTime + ".sql', 'export_data_ALL_" + strDateTime + ".sql')");
	}
	
	public void carregaMetaDados(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataBaseMetaDadosService data = (DataBaseMetaDadosService) ServiceLocator.getInstance().getService(DataBaseMetaDadosService.class, null);
		DataBaseMetaDadosUtil dataBaseMetaDadosUtil = new DataBaseMetaDadosUtil();
		VisaoDao visaoDao = new VisaoDao();
		Connection con = (Connection) visaoDao.getTransactionControler().getTransactionObject();
		String DB_SCHEMA = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.DB_SCHEMA,"");
		if (DB_SCHEMA == null || DB_SCHEMA.trim().equalsIgnoreCase("")){
		    DB_SCHEMA = "citsmart";
		}
		
		//Desabilitando as tabelas para garantir que as que não existam mais não fiquem ativas
		desabilitaTabelas();
		
		Collection colObsNegocio = dataBaseMetaDadosUtil.readTables(con, DB_SCHEMA, DB_SCHEMA, null, true);		
		con.close();
		con = null;
		
		String carregados = "";
		
		for (Iterator it = colObsNegocio.iterator(); it.hasNext();){
			ObjetoNegocioDTO objetoNegocioDTO = (ObjetoNegocioDTO)it.next();
			
			System.out.println("-----: Objeto de Negocio: " + objetoNegocioDTO.getNomeTabelaDB());
			carregados += objetoNegocioDTO.getNomeTabelaDB() + "<br>";
			
			Collection colObjs = getObjetoNegocioService().findByNomeTabelaDB(objetoNegocioDTO.getNomeTabelaDB());
			if (colObjs == null || colObjs.size() == 0){
				System.out.println("----------: Criando....  " + objetoNegocioDTO.getNomeTabelaDB());
				getObjetoNegocioService().create(objetoNegocioDTO); 
			}else{
				ObjetoNegocioDTO objetoNegocioAux = (ObjetoNegocioDTO)((List)colObjs).get(0);
				objetoNegocioDTO.setIdObjetoNegocio(objetoNegocioAux.getIdObjetoNegocio());
				System.out.println("----------: Atualizando....  " + objetoNegocioDTO.getNomeTabelaDB() + "    Id Interno: " + objetoNegocioAux.getIdObjetoNegocio());
				getObjetoNegocioService().update(objetoNegocioDTO);
			}
		}
		
		data.corrigeTabelaComplexidade();
		data.corrigeTabelaSla();
		data.corrigeTabelaFluxoServico();
		
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
		load(document, request, response);
	}
	
	private ObjetoNegocioService getObjetoNegocioService() throws ServiceException, Exception{
		if(objetoNegocioService == null){
			return (ObjetoNegocioService) ServiceLocator.getInstance().getService(ObjetoNegocioService.class, null);
		}else{
			return objetoNegocioService;
		}
	}
	
	private void desabilitaTabelas() throws LogicException, ServiceException, Exception{
		
		List<ObjetoNegocioDTO> listObjetoNegocio = (List<ObjetoNegocioDTO>) getObjetoNegocioService().list();
		
		for (ObjetoNegocioDTO objetoNegocioDTO : listObjetoNegocio) {
			objetoNegocioDTO.setSituacao("I");
			getObjetoNegocioService().updateDisable(objetoNegocioDTO);
		}
		
	}
}
