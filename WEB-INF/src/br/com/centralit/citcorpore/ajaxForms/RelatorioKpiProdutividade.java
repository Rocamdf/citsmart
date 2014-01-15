package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.RelatorioKpiProdutividadeDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.LogoRel;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings({ "rawtypes" })
public class RelatorioKpiProdutividade extends AjaxFormAction {

	private UsuarioDTO usuario;
	private String localeSession = null;

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		this.preencherComboContrato(document, request, response);
	}

	public void imprimirRelatorio(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		usuario = WebUtil.getUsuario(request);
		RelatorioKpiProdutividadeDTO relatorioKpiProdutividadeDTO = (RelatorioKpiProdutividadeDTO) document.getBean();
		HttpSession session = ((HttpServletRequest) request).getSession();
		RelatorioKpiProdutividadeDTO dadosDaTela = new RelatorioKpiProdutividadeDTO();
		dadosDaTela = relatorioKpiProdutividadeDTO;
		UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
		ArrayList<UsuarioDTO> listaUsuarios = new ArrayList<UsuarioDTO>();
		ArrayList<RelatorioKpiProdutividadeDTO> listaParaGerarRelatorio = new ArrayList<RelatorioKpiProdutividadeDTO>();
		Collection<RelatorioKpiProdutividadeDTO> listaParaEnvio = new ArrayList<RelatorioKpiProdutividadeDTO>();
		// Restaura o usuário selecionado
		if (relatorioKpiProdutividadeDTO.getListaUsuarios() != null) {
			String[] listaUsuariosTela;
			listaUsuariosTela = relatorioKpiProdutividadeDTO.getListaUsuarios().split(";");
			if (!listaUsuariosTela.equals("")) {
				for (String i : listaUsuariosTela) {
					if (!i.equals("")) {
						UsuarioDTO usuario = usuarioService.restoreByIdEmpregado(Integer.valueOf(i));
						if(usuario!=null){
							listaUsuarios.add(usuario);
						}
					}
				}
			}
		}

		// ----------------------------------------------------
		SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		ArrayList<SolicitacaoServicoDTO> listaParaArmazenarAsListasDeSolicitacoesAux = new ArrayList<SolicitacaoServicoDTO>();
		double countEstouradasTotalGrupo = 0;
		double countExecutadasTotalGrupo = 0;
		if (listaUsuarios != null) {
			// Busca todas as solicitações feitas pelo usuário
			for (UsuarioDTO usuarios : listaUsuarios) {
				RelatorioKpiProdutividadeDTO novoFuncionario = new RelatorioKpiProdutividadeDTO();
				novoFuncionario.setFuncionario(Integer.toString(usuarios.getIdUsuario()));
				novoFuncionario.setCheckMostrarIncidentes(relatorioKpiProdutividadeDTO.getCheckMostrarIncidentes());
				novoFuncionario.setCheckMostrarRequisicoes(relatorioKpiProdutividadeDTO.getCheckMostrarRequisicoes());
				novoFuncionario.setDataInicio(relatorioKpiProdutividadeDTO.getDataInicio());
				novoFuncionario.setDataFim(relatorioKpiProdutividadeDTO.getDataFim());
				novoFuncionario.setContrato(String.valueOf(relatorioKpiProdutividadeDTO.getIdContrato()));
				novoFuncionario.setGrupo(relatorioKpiProdutividadeDTO.getGrupo());
				novoFuncionario.setFormatoArquivoRelatorio(relatorioKpiProdutividadeDTO.getFormatoArquivoRelatorio());

				double countExecutadasTotal = 0;
				double countEstouradasTotal = 0;
				Collection<SolicitacaoServicoDTO> listaSolicitacoesUsuario = solicitacaoService.listaServicosPorResponsavelNoPeriodo(novoFuncionario);
				novoFuncionario.setFuncionario(usuarios.getNomeUsuario());
				if (listaSolicitacoesUsuario != null && !listaSolicitacoesUsuario.isEmpty()) {

					novoFuncionario.setListaSolicitacoesUsuario(listaSolicitacoesUsuario);
					ArrayList<RelatorioKpiProdutividadeDTO> subLista = new ArrayList<RelatorioKpiProdutividadeDTO>();
					Set<SolicitacaoServicoDTO> listaComItensNaoRepetidos = new LinkedHashSet<SolicitacaoServicoDTO>();
					listaComItensNaoRepetidos.addAll(listaSolicitacoesUsuario);
					// verifica a quantidade de solicitações
					if (novoFuncionario.getListaSolicitacoesUsuario() != null) {
						for (SolicitacaoServicoDTO solicitacaoServicoDTO : listaComItensNaoRepetidos) {
							double countExecutadasPorSolicitacao = 0;
							double countEstouradasPorSolicitacao = 0;
							for (SolicitacaoServicoDTO solicitacaoServicoDTOAux : novoFuncionario.getListaSolicitacoesUsuario()) {
								if (solicitacaoServicoDTO.getIdServico().equals(solicitacaoServicoDTOAux.getIdServico())) {
									countExecutadasPorSolicitacao++;
									if (solicitacaoServicoDTOAux.getDataHoraFim() != null && solicitacaoServicoDTOAux.getDataHoraLimite().after(solicitacaoServicoDTOAux.getDataHoraFim())) {
										countEstouradasPorSolicitacao++;
									}
								}
							}
							countExecutadasTotal+=countExecutadasPorSolicitacao;
							countEstouradasTotal+=countEstouradasPorSolicitacao;

							RelatorioKpiProdutividadeDTO dtoAux = new RelatorioKpiProdutividadeDTO();
							dtoAux.setNumeroSolicitacao(solicitacaoServicoDTO.getIdSolicitacaoServico());
							dtoAux.setNomeServico(solicitacaoServicoDTO.getNomeServico());
							dtoAux.setQtdeExecutada(countExecutadasPorSolicitacao);
							dtoAux.setQtdEstourada(countEstouradasPorSolicitacao);
							dtoAux.setTotalPorExecutante(countExecutadasTotal);
							dtoAux.setTotalPorExecutanteEstouradas(countEstouradasTotal);
							dtoAux.setTotalPorExecutanteEstouradasPorcentagem(String.format( "%.2f", 100*(countEstouradasTotal/countExecutadasTotal) ) + "%");
							dtoAux.setTotalPorExecutantePorcentagem(String.format( "%.2f",100-(100*(countEstouradasTotal/countExecutadasTotal)))+"%");
							dtoAux.setTotalPorServicoPorcentagem(String.format( "%.2f",100-(100*(countEstouradasPorSolicitacao/countExecutadasPorSolicitacao)))+"%");
							subLista.add(dtoAux);
						}

					}
					if (subLista != null && !subLista.isEmpty()) {
						novoFuncionario.setListaGeral(subLista);
						novoFuncionario.setTotalPorExecutante(countExecutadasTotal);
						novoFuncionario.setTotalPorExecutanteEstouradas(countEstouradasTotal);
						novoFuncionario.setTotalPorExecutanteEstouradasPorcentagem(String.valueOf(100*(countEstouradasTotal/countExecutadasTotal))+"%");
						novoFuncionario.setTotalPorExecutantePorcentagem(String.valueOf(100-(100*(countEstouradasTotal/countExecutadasTotal)))+"%");
						listaParaEnvio.add(novoFuncionario);
						countExecutadasTotalGrupo+=countExecutadasTotal;
						countEstouradasTotalGrupo+=countEstouradasTotal;
					}
				}
			}

		}

		if (listaParaEnvio != null && !listaParaEnvio.isEmpty()) {

			String caminhoJasper = CITCorporeUtil.caminho_real_app + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioKpiProdutividade.jasper";
			String diretorioReceita = CITCorporeUtil.caminho_real_app + "/tempFiles";
			String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);

			parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioKpi.titulo")); 

			parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
			parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
			parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
			parametros.put("dataInicio", dadosDaTela.getDataInicio());
			parametros.put("dataFim", dadosDaTela.getDataFim());
			parametros.put("contrato", this.getContrato(dadosDaTela.getIdContrato()));
			parametros.put("grupo", this.getDescricaoGrupo(Integer.parseInt(relatorioKpiProdutividadeDTO.getGrupo())));
			parametros.put("SUBREPORT_DIR", CITCorporeUtil.caminho_real_app + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioKpiProdutividade_subreport1.jasper");
			parametros.put("Logo", LogoRel.getFile());
			parametros.put("titulo", UtilI18N.internacionaliza(request, "citcorpore.comum.titulo"));
			parametros.put("incidentes", dadosDaTela.getCheckMostrarIncidentes()!=null? UtilI18N.internacionaliza(request, "citcorpore.comum.sim"): UtilI18N.internacionaliza(request, "citcorpore.comum.nao"));
			parametros.put("solicitacoes", dadosDaTela.getCheckMostrarRequisicoes()!=null?UtilI18N.internacionaliza(request, "citcorpore.comum.sim"): UtilI18N.internacionaliza(request, "citcorpore.comum.nao"));
			parametros.put("internacionaliza_NumeroSolicitacao", UtilI18N.internacionaliza(request, "gerenciamentoservico.numeroSolicitacao"));
			parametros.put("internacionaliza_NomeServico", UtilI18N.internacionaliza(request, "servico.nome"));
			parametros.put("internacionaliza_QuatidadeExecutada",  UtilI18N.internacionaliza(request, "relatorioKpi.QuantidadeExecutada"));
			parametros.put("internacionaliza_QuantiudadeEstourada",  UtilI18N.internacionaliza(request, "relatorioKpi.QuantidadeEstourada"));
			parametros.put("internacionaliza_TotalExecutado",  UtilI18N.internacionaliza(request, "relatorioKpi.TotalExecutado"));
			parametros.put("internacionaliza_TotalEstourado",  UtilI18N.internacionaliza(request, "relatorioKpi.TotalEstourado"));
			parametros.put("internacionaliza_TotalPorGrupo",  UtilI18N.internacionaliza(request, "relatorioKpi.TotalPorGrupo"));
			parametros.put("internacionaliza_NaoEstrapolada",  UtilI18N.internacionaliza(request, "relatorioKpi.internacionaliza_NaoEstrapolada"));
			parametros.put("ExecutadasTotalPorGrupo", countExecutadasTotalGrupo+"");
			parametros.put("EstouradasTotalPorGrupo", countEstouradasTotalGrupo+"");
			parametros.put("EstouradasTotalPorGrupoPorcentagem", String.format( "%.2f",100*(countEstouradasTotalGrupo/countExecutadasTotalGrupo))+"%");
			parametros.put("ExecutadasTotalPorGrupoPorcentagem",String.format( "%.2f",100-(100*(countEstouradasTotalGrupo/countExecutadasTotalGrupo)))+"%");
			
			

			if (relatorioKpiProdutividadeDTO.getFormatoArquivoRelatorio().equalsIgnoreCase("pdf")) {

				this.gerarRelatorioFormatoPdf(listaParaEnvio, caminhoJasper, parametros, diretorioReceita, document, diretorioRelativoOS, usuario);

			} else {

				this.gerarRelatorioFormatoXls(listaParaEnvio, parametros, diretorioReceita, document, diretorioRelativoOS, usuario);

			}
		} else {
			document.executeScript("reportEmpty();");
		}

		document.executeScript("JANELA_AGUARDE_MENU.hide();");
	}

	@Override
	public Class getBeanClass() {
		return RelatorioKpiProdutividadeDTO.class;
	}

	public void preencherComboContrato(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HTMLSelect comboContrato = document.getSelectById("idContrato");
		comboContrato.removeAllOptions();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		Collection colContrato = contratoService.listAtivos();
		comboContrato.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		comboContrato.addOptions(colContrato, "idContrato", "numero", null);
	}

	public void preencherComboGrupo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, int idContrato) throws Exception {
		HTMLSelect comboGrupo = document.getSelectById("grupo");
		comboGrupo.removeAllOptions();
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		Collection colGrupo = grupoService.listGrupoByIdContrato(idContrato);
		comboGrupo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		comboGrupo.addOptions(colGrupo, "idGrupo", "Nome", null);
	}

	public void preencherComboUsuariosPorGrupo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		RelatorioKpiProdutividadeDTO relatorioKpiProdutividadeDTO = (RelatorioKpiProdutividadeDTO) document.getBean();
		EmpregadoService funcionarioService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		HTMLSelect comboUsuarios = document.getSelectById("segundaLista");

		comboUsuarios.removeAllOptions();
		String comboContrato = relatorioKpiProdutividadeDTO.getGrupo();
		Collection<EmpregadoDTO> colUsuario = funcionarioService.listEmpregadosByIdGrupo(Integer.valueOf(comboContrato));
		ArrayList<EmpregadoDTO> colUsuario2 = new ArrayList<EmpregadoDTO>();

		if (colUsuario != null) {
			for (EmpregadoDTO object : colUsuario) {
				colUsuario2.add(funcionarioService.restoreByIdEmpregado(object.getIdEmpregado()));
			}
		}
		comboUsuarios.addOptions(colUsuario2, "idEmpregado", "nome", null);
	}

	public void preencherComboUsuariosPorContrato(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		RelatorioKpiProdutividadeDTO relatorioKpiProdutividadeDTO = (RelatorioKpiProdutividadeDTO) document.getBean();
		EmpregadoService funcionarioService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		HTMLSelect comboUsuarios = document.getSelectById("segundaLista");
		HTMLSelect comboGrupo = document.getSelectById("grupo");
		int comboContrato = relatorioKpiProdutividadeDTO.getIdContrato();

		comboUsuarios.removeAllOptions();
		comboGrupo.removeAllOptions();

		this.preencherComboGrupo(document, request, response, comboContrato);
		Collection<EmpregadoDTO> colUsuario = funcionarioService.listEmpregadosByIdGrupo(comboContrato);
		ArrayList<EmpregadoDTO> colUsuario2 = new ArrayList<EmpregadoDTO>();

		if (colUsuario != null) {
			for (EmpregadoDTO object : colUsuario) {
				colUsuario2.add(funcionarioService.restoreByIdEmpregado(object.getIdEmpregado()));
			}
		}
		comboUsuarios.addOptions(colUsuario2, "idEmpregado", "nome", null);
	}

	public void gerarRelatorioFormatoPdf(Collection<RelatorioKpiProdutividadeDTO> listaRelatorioMudancaItemConfiguracao, String caminhoJasper, Map<String, Object> parametros, String diretorioReceita,
			DocumentHTML document, String diretorioRelativoOS, UsuarioDTO usuario) throws Exception {

		JRDataSource dataSource = new JRBeanCollectionDataSource(listaRelatorioMudancaItemConfiguracao);

		JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);

		JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioKpiProdutividade" + "_" + usuario.getIdUsuario() + ".pdf");

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
				+ "/RelatorioKpiProdutividade" + "_" + usuario.getIdUsuario() + ".pdf')");
	}

	public void gerarRelatorioFormatoXls(Collection<RelatorioKpiProdutividadeDTO> listaRelatorioMudancaItemConfiguracao, Map<String, Object> parametros, String diretorioReceita,
			DocumentHTML document, String diretorioRelativoOS, UsuarioDTO usuario) throws Exception {

		JRDataSource dataSource = new JRBeanCollectionDataSource(listaRelatorioMudancaItemConfiguracao);

		JasperDesign desenho = JRXmlLoader.load(CITCorporeUtil.caminho_real_app + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioKpiProdutividade.jrxml");

		JasperReport relatorio = JasperCompileManager.compileReport(desenho);

		JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, dataSource);

		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, impressao);
		exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, diretorioReceita + "/RelatorioKpiProdutividade" + "_" + usuario.getIdUsuario() + ".xls");

		exporter.exportReport();

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
				+ "/RelatorioKpiProdutividade" + "_" + usuario.getIdUsuario() + ".xls')");

	}

	private String getContrato(Integer id) throws ServiceException, Exception {

		ContratoDTO contrato = new ContratoDTO();
		contrato.setIdContrato(id);
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		contrato = (ContratoDTO) contratoService.restore(contrato);
		if (contrato != null) {
			return contrato.getNumero();
		}

		return null;
	}

	private String getDescricaoGrupo(int idGrupo) throws ServiceException, Exception {

		GrupoDTO dto = new GrupoDTO();
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		dto = grupoService.listGrupoById(idGrupo);
		return dto.getDescricao();

	}

}
