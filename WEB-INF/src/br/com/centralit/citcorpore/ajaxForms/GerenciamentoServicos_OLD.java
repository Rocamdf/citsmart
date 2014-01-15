package br.com.centralit.citcorpore.ajaxForms;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.htmlparser.jericho.Source;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DialShape;
import org.jfree.chart.plot.MeterInterval;
import org.jfree.chart.plot.MeterPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.ui.TextAnchor;

import br.com.centralit.bpm.dto.ElementoFluxoDTO;
import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.integracao.ElementoFluxoDao;
import br.com.centralit.bpm.util.Enumerados;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.bean.ContratosGruposDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.GerenciamentoServicosDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.UnidadeDao;
import br.com.centralit.citcorpore.negocio.ContratosGruposService;
import br.com.centralit.citcorpore.negocio.EmpregadoServiceEjb;
import br.com.centralit.citcorpore.negocio.ExecucaoSolicitacaoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.TipoDemandaServicoService;
import br.com.centralit.citcorpore.negocio.UsuarioServiceEjb;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.citframework.comparacao.ObjectSimpleComparator;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class GerenciamentoServicos_OLD extends AjaxFormAction {

	@Override
	public Class getBeanClass() {
		return GerenciamentoServicosDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		exibeTarefas(document, request, response);
	}

	public void exibeTarefas(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession().setAttribute("dadosEstaGerenServ", null);
		ArrayList<SolicitacaoServicoDTO> resultSolicitacoes = new ArrayList<SolicitacaoServicoDTO>();

		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		GerenciamentoServicosDTO gerenciamentoBean = (GerenciamentoServicosDTO) document.getBean();

		ExecucaoSolicitacaoService execucaoSolicitacaoService = (ExecucaoSolicitacaoService) ServiceLocator.getInstance().getService(ExecucaoSolicitacaoService.class, null);
		ContratosGruposService contratosGruposService = (ContratosGruposService) ServiceLocator.getInstance().getService(ContratosGruposService.class, null);

		List<TarefaFluxoDTO> colTarefas = null;
		colTarefas = execucaoSolicitacaoService.recuperaTarefas(usuario.getLogin());

		if (colTarefas == null)
			return;

		String COLABORADORES_VINC_CONTRATOS = ParametroUtil.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.COLABORADORES_VINC_CONTRATOS, "N");
		if (COLABORADORES_VINC_CONTRATOS == null) {
			COLABORADORES_VINC_CONTRATOS = "N";
		}
		Collection colContratosColab = null;
		if (COLABORADORES_VINC_CONTRATOS.equalsIgnoreCase("S")) {
			colContratosColab = contratosGruposService.findByIdEmpregado(usuario.getIdEmpregado());
		}

		boolean bFiltroPorContrato = gerenciamentoBean.getNumeroContratoSel() != null && gerenciamentoBean.getNumeroContratoSel().length() > 0;
		boolean bFiltroPorSolicitacao = gerenciamentoBean.getIdSolicitacaoSel() != null && gerenciamentoBean.getIdSolicitacaoSel().length() > 0;
		boolean bDescricao = gerenciamentoBean.getDescricaoSolicitacao() != null && gerenciamentoBean.getDescricaoSolicitacao().trim().length() > 0;
		boolean bTipoDemandaServico = gerenciamentoBean.getIdTipoDemandaServico() != null && gerenciamentoBean.getIdTipoDemandaServico().trim().length() > 0;

		List<TarefaFluxoDTO> colTarefasFiltradas = new ArrayList();
		if (!bFiltroPorContrato && !bFiltroPorSolicitacao && !bDescricao && !bTipoDemandaServico)

			colTarefasFiltradas.addAll(colTarefas);

		else {
			for (TarefaFluxoDTO tarefaDto : colTarefas) {

				boolean bAdicionar = false;
				boolean bAuxiliar = true;
				String contrato = UtilStrings.nullToVazio(((SolicitacaoServicoDTO) tarefaDto.getSolicitacaoDto()).getContrato());
				String idSolicitacao = "" + ((SolicitacaoServicoDTO) tarefaDto.getSolicitacaoDto()).getIdSolicitacaoServico();

				String idTipoDemandaServico = "" + ((SolicitacaoServicoDTO) tarefaDto.getSolicitacaoDto()).getIdTipoDemandaServico();

				Source source = new Source(UtilStrings.nullToVazio(((SolicitacaoServicoDTO) tarefaDto.getSolicitacaoDto()).getDescricao()));

				String descricao = UtilStrings.nullToVazio(source.getTextExtractor().toString().toLowerCase());

				if (bFiltroPorContrato && bDescricao && bFiltroPorSolicitacao && bTipoDemandaServico) {

					bAdicionar = contrato.indexOf(gerenciamentoBean.getNumeroContratoSel()) >= 0 && idSolicitacao.indexOf(gerenciamentoBean.getIdSolicitacaoSel()) >= 0
							&& idTipoDemandaServico.indexOf(gerenciamentoBean.getIdTipoDemandaServico()) >= 0
							&& StringUtils.contains(descricao, gerenciamentoBean.getDescricaoSolicitacao().toLowerCase());

				} else {

					if (bFiltroPorContrato) {

						bAdicionar = contrato.indexOf(gerenciamentoBean.getNumeroContratoSel()) >= 0;
						if (!bAdicionar) {
							bAuxiliar = false;
						}
					}

					if (bDescricao) {

						bAdicionar = StringUtils.contains(descricao, gerenciamentoBean.getDescricaoSolicitacao().toLowerCase());
						if (!bAdicionar) {
							bAuxiliar = false;
						}
					}
					if (bFiltroPorSolicitacao) {

						bAdicionar = idSolicitacao.indexOf(gerenciamentoBean.getIdSolicitacaoSel()) >= 0;
						if (!bAdicionar) {
							bAuxiliar = false;
						}

					}
					if (bTipoDemandaServico) {
						bAdicionar = idTipoDemandaServico.indexOf(gerenciamentoBean.getIdTipoDemandaServico()) >= 0;
						if (!bAdicionar) {
							bAuxiliar = false;
						}
					}

				}

				if (bAdicionar && bAuxiliar) {

					colTarefasFiltradas.add(tarefaDto);
				}

			}
		}
		List colTarefasFiltradasFinal = new ArrayList();

		HashMap mapResponsavelAtual = new HashMap();
		mapResponsavelAtual.put(UtilI18N.internacionaliza(request, "citcorpore.comum.sematribuicao"), UtilI18N.internacionaliza(request, "citcorpore.comum.sematribuicao"));

		HashMap mapGrupoAtual = new HashMap();
		mapGrupoAtual.put(UtilI18N.internacionaliza(request, "citcorpore.comum.sematribuicao"), UtilI18N.internacionaliza(request, "citcorpore.comum.sematribuicao"));

		HashMap mapSolicitanteUnidade = new HashMap();

		boolean asc = true;
		if (gerenciamentoBean.getOrdenacaoAsc() != null) {
			asc = gerenciamentoBean.getOrdenacaoAsc().equalsIgnoreCase("false") ? false : true;
		}

		for (TarefaFluxoDTO tarefaDto : colTarefasFiltradas) {
			SolicitacaoServicoDTO dtoSol = (SolicitacaoServicoDTO) tarefaDto.getSolicitacaoDto();
			if (COLABORADORES_VINC_CONTRATOS.equalsIgnoreCase("S")) { // Se parametro de colaboradores por contrato ativo, entao filtra.
				if (colContratosColab == null) {
					continue;
				}
				if (!isContratoInList(dtoSol.getIdContrato(), colContratosColab)) {
					continue;
				}
			}
			dtoSol.setDataHoraLimiteToString(""); // Apenas forca atualizacao
			dtoSol.setDataHoraSolicitacaoToString(""); // Apenas forca atualizacao
			dtoSol.setDescricaoSemFormatacao("");
			dtoSol.setDescricaoForTitle("");
			dtoSol.setDescricao("");
			dtoSol.setResposta("");
			dtoSol.setDetalhamentoCausa("");
			if (dtoSol.getSlaACombinar() == null) {
				dtoSol.setSlaACombinar("N");
			}
			int prazoHH = 0;
			int prazoMM = 0;
			if (dtoSol.getPrazoHH() != null) {
				prazoHH = dtoSol.getPrazoHH();
			}
			if (dtoSol.getPrazoMM() != null) {
				prazoMM = dtoSol.getPrazoMM();
			}
			if (prazoHH == 0 && prazoMM == 0) {
				dtoSol.setSlaACombinar("S");
				dtoSol.setAtrasoSLA(0);
				dtoSol.setAtrasoSLAStr("");
				dtoSol.setDataHoraLimiteStr("");
			}
			if (dtoSol.getSlaACombinar().equalsIgnoreCase("S")) {
				dtoSol.setDataHoraLimite(null);
				dtoSol.setAtrasoSLA(0);
				dtoSol.setAtrasoSLAStr("");
				if (asc) {
					tarefaDto.setDataHoraLimite(new Timestamp(UtilDatas.alteraData(UtilDatas.getDataAtual(), 10, Calendar.YEAR).getTime()));
				} else {
					tarefaDto.setDataHoraLimite(new Timestamp(UtilDatas.alteraData(UtilDatas.getDataAtual(), -10, Calendar.YEAR).getTime()));
				}
			}
			if (dtoSol.getSituacao().equalsIgnoreCase(br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico.Suspensa.name())) {
				dtoSol.setDataHoraLimite(null);
				dtoSol.setAtrasoSLA(0);
				dtoSol.setAtrasoSLAStr("");
				if (asc) {
					tarefaDto.setDataHoraLimite(new Timestamp(UtilDatas.alteraData(UtilDatas.getDataAtual(), 10, Calendar.YEAR).getTime()));
				} else {
					tarefaDto.setDataHoraLimite(new Timestamp(UtilDatas.alteraData(UtilDatas.getDataAtual(), -10, Calendar.YEAR).getTime()));
				}
			}
			if (dtoSol.getSolicitanteUnidade() != null && !dtoSol.getSolicitanteUnidade().trim().equalsIgnoreCase("")) {
				dtoSol.setSolicitante(""); // pra nao enviar no JSON
			} else {
				dtoSol.setSolicitanteUnidade(StringEscapeUtils.escapeJavaScript(dtoSol.getSolicitante()));
			}

			// Preenche COMBO de Responsáveis
			if (tarefaDto.getResponsavelAtual() != null && StringUtils.isNotBlank(tarefaDto.getResponsavelAtual())) {
				if (!mapResponsavelAtual.containsKey(tarefaDto.getResponsavelAtual())) {
					mapResponsavelAtual.put(StringEscapeUtils.escapeJavaScript(tarefaDto.getResponsavelAtual().trim()), StringEscapeUtils.escapeJavaScript(tarefaDto.getResponsavelAtual()));
				}
			}

			// Preenche COMBO de Grupo Executor
			if (dtoSol.getGrupoAtual() != null && StringUtils.isNotBlank(dtoSol.getGrupoAtual())) {
				if (!mapGrupoAtual.containsKey(dtoSol.getGrupoAtual())) {
					mapGrupoAtual.put(StringEscapeUtils.escapeJavaScript(dtoSol.getGrupoAtual().trim()), StringEscapeUtils.escapeJavaScript(dtoSol.getGrupoAtual()));
				}
			}

			// Preenche COMBO de Solicitante
			if (dtoSol.getSolicitanteUnidade() != null && StringUtils.isNotBlank(dtoSol.getSolicitanteUnidade())) {
				if (!mapSolicitanteUnidade.containsKey(dtoSol.getSolicitanteUnidade())) {
					mapSolicitanteUnidade.put(StringEscapeUtils.escapeJavaScript(dtoSol.getSolicitanteUnidade().trim()), StringEscapeUtils.escapeJavaScript(dtoSol.getSolicitanteUnidade()));
				}
			}

			SolicitacaoServicoDTO sol = (SolicitacaoServicoDTO) tarefaDto.getSolicitacaoDto();
			sol.setContrato(StringEscapeUtils.escapeJavaScript(sol.getContrato()));
			sol.setDescricao(StringEscapeUtils.escapeJavaScript(sol.getDescricao()));
			sol.setDemanda(StringEscapeUtils.escapeJavaScript(sol.getDemanda()));
			sol.setDetalhamentoCausa(StringEscapeUtils.escapeJavaScript(sol.getDetalhamentoCausa()));
			sol.setEmailcontato(StringEscapeUtils.escapeJavaScript(sol.getEmailcontato()));
			sol.setGrupoNivel1(StringEscapeUtils.escapeJavaScript(sol.getGrupoNivel1()));
			sol.setNomecontato(StringEscapeUtils.escapeJavaScript(sol.getNomecontato()));
			sol.setNomeServico(StringEscapeUtils.escapeJavaScript(sol.getNomeServico()));
			sol.setNomeSolicitante(StringEscapeUtils.escapeJavaScript(sol.getNomeSolicitante()));
			sol.setNomeTarefa(StringEscapeUtils.escapeJavaScript(sol.getNomeTarefa()));
			sol.setNomeUnidadeResponsavel(StringEscapeUtils.escapeJavaScript(sol.getNomeUnidadeResponsavel()));
			sol.setNomeUnidadeSolicitante(StringEscapeUtils.escapeJavaScript(sol.getNomeUnidadeSolicitante()));
			sol.setObservacao(null);
			sol.setOrigem(StringEscapeUtils.escapeJavaScript(sol.getOrigem()));
			sol.setResponsavel(StringEscapeUtils.escapeJavaScript(sol.getResponsavel()));
			sol.setResposta(StringEscapeUtils.escapeJavaScript(sol.getResposta()));
			sol.setServico(StringEscapeUtils.escapeJavaScript(sol.getServico()));
			sol.setSolicitante(StringEscapeUtils.escapeJavaScript(sol.getSolicitante()));
			sol.setSolicitanteUnidade(StringEscapeUtils.escapeJavaScript(sol.getSolicitanteUnidade()));
			sol.setTelefonecontato(StringEscapeUtils.escapeJavaScript(sol.getTelefonecontato()));
			if (sol.getGrupoAtual() == null || sol.getGrupoAtual().isEmpty()) {
				sol.setGrupoAtual(" ");
			}

			tarefaDto.setSolicitacaoDto(sol);

			// FILTROS DA BARRA DE SOLICITAÇÕES

			// NENHUM ITEM FOI SELECIONADO
			if (((gerenciamentoBean.getResponsavelAtual() == null || StringUtils.isBlank(gerenciamentoBean.getResponsavelAtual()))
					&& (gerenciamentoBean.getGrupoAtual() == null || StringUtils.isBlank(gerenciamentoBean.getGrupoAtual())) && (gerenciamentoBean.getSolicitanteUnidade() == null || StringUtils
					.isBlank(gerenciamentoBean.getSolicitanteUnidade())))) {

				colTarefasFiltradasFinal.add(tarefaDto);
				resultSolicitacoes.add((SolicitacaoServicoDTO) tarefaDto.getSolicitacaoDto());

			} else {

				// SOMENTE RESPONSÁVEL SELECIONADO
				if ((gerenciamentoBean.getResponsavelAtual() != null && StringUtils.isNotBlank(gerenciamentoBean.getResponsavelAtual()))
						&& (gerenciamentoBean.getGrupoAtual() == null || StringUtils.isBlank(gerenciamentoBean.getGrupoAtual()))
						&& (gerenciamentoBean.getSolicitanteUnidade() == null || StringUtils.isBlank(gerenciamentoBean.getSolicitanteUnidade()))) {

					// SE RESPONSÁVEL SEM ATRIBUIÇÃO - OK
					if (this.isSemAtribuicao(request, gerenciamentoBean.getResponsavelAtual()) && (tarefaDto.getResponsavelAtual() == null || StringUtils.isBlank(tarefaDto.getResponsavelAtual()))) {

						colTarefasFiltradasFinal.add(tarefaDto);
						resultSolicitacoes.add((SolicitacaoServicoDTO) tarefaDto.getSolicitacaoDto());

					} else {

						// SE RESPONSÁVEL SELECIONADO - OK
						if (gerenciamentoBean.getResponsavelAtual() != null && tarefaDto.getResponsavelAtual() != null
								&& gerenciamentoBean.getResponsavelAtual().trim().equalsIgnoreCase(StringEscapeUtils.unescapeJavaScript(tarefaDto.getResponsavelAtual().trim()))) {
							colTarefasFiltradasFinal.add(tarefaDto);
							resultSolicitacoes.add((SolicitacaoServicoDTO) tarefaDto.getSolicitacaoDto());
						}
					}

					continue;
				}

				// RESPONSÁVEL && GRUPO SELECIONADO
				if (gerenciamentoBean.getResponsavelAtual() != null && StringUtils.isNotBlank(gerenciamentoBean.getResponsavelAtual()) && gerenciamentoBean.getGrupoAtual() != null
						&& StringUtils.isNotBlank(gerenciamentoBean.getGrupoAtual())
						&& (gerenciamentoBean.getSolicitanteUnidade() == null || StringUtils.isBlank(gerenciamentoBean.getSolicitanteUnidade()))) {

					// RESPONSÁVEL && GRUPO SEM ATRIBUIÇÃO
					if (this.isSemAtribuicao(request, gerenciamentoBean.getResponsavelAtual()) && this.isSemAtribuicao(request, gerenciamentoBean.getGrupoAtual())
							&& (tarefaDto.getResponsavelAtual() == null || StringUtils.isBlank(tarefaDto.getResponsavelAtual()))
							&& (dtoSol.getGrupoAtual() == null || StringUtils.isBlank(dtoSol.getGrupoAtual()))) {

						colTarefasFiltradasFinal.add(tarefaDto);
						resultSolicitacoes.add((SolicitacaoServicoDTO) tarefaDto.getSolicitacaoDto());

					} else {

						// RESPONSÁVEL SEM ATRIBUIÇÃO - GRUPO SELECIONADO - OK
						if (this.isSemAtribuicao(request, gerenciamentoBean.getResponsavelAtual()) && (tarefaDto.getResponsavelAtual() == null || StringUtils.isBlank(tarefaDto.getResponsavelAtual()))
								&& (dtoSol.getGrupoAtual() != null && gerenciamentoBean.getGrupoAtual().trim().equalsIgnoreCase(StringEscapeUtils.unescapeJavaScript(dtoSol.getGrupoAtual().trim())))) {

							colTarefasFiltradasFinal.add(tarefaDto);
							resultSolicitacoes.add((SolicitacaoServicoDTO) tarefaDto.getSolicitacaoDto());

						} else {

							// RESPONSÁVEL && GRUPO SELECIONADO - OK
							if (tarefaDto.getResponsavelAtual() != null && StringUtils.equalsIgnoreCase(tarefaDto.getResponsavelAtual().trim(), gerenciamentoBean.getResponsavelAtual().trim())
									&& gerenciamentoBean.getGrupoAtual().equalsIgnoreCase(StringEscapeUtils.unescapeJavaScript(dtoSol.getGrupoAtual()))) {

								colTarefasFiltradasFinal.add(tarefaDto);
								resultSolicitacoes.add((SolicitacaoServicoDTO) tarefaDto.getSolicitacaoDto());
							}
						}
					}
					continue;
				}

				// SE RESPONSÁVEL && SOLICITANTE SELECIONADO
				if (gerenciamentoBean.getResponsavelAtual() != null && StringUtils.isNotBlank(gerenciamentoBean.getResponsavelAtual()) && gerenciamentoBean.getSolicitanteUnidade() != null
						&& StringUtils.isNotBlank(gerenciamentoBean.getSolicitanteUnidade()) && (gerenciamentoBean.getGrupoAtual() == null || StringUtils.isBlank(gerenciamentoBean.getGrupoAtual()))) {

					// RESPONSÁVEL SEM ATRIBUIÇÃO && SOLICITANTE SELECIONADO - OK
					if (this.isSemAtribuicao(request, gerenciamentoBean.getResponsavelAtual())
							&& (tarefaDto.getResponsavelAtual() == null || StringUtils.isBlank(tarefaDto.getResponsavelAtual()))
							&& (dtoSol.getSolicitanteUnidade() != null && gerenciamentoBean.getSolicitanteUnidade().trim()
									.equalsIgnoreCase(StringEscapeUtils.unescapeJavaScript(dtoSol.getSolicitanteUnidade().trim())))) {

						colTarefasFiltradasFinal.add(tarefaDto);
						resultSolicitacoes.add((SolicitacaoServicoDTO) tarefaDto.getSolicitacaoDto());

					} else {

						// SE RESPONSÁVEL && SOLICITANTE SELECIONADO - OK
						if (tarefaDto.getResponsavelAtual() != null && StringUtils.equalsIgnoreCase(tarefaDto.getResponsavelAtual().trim(), gerenciamentoBean.getResponsavelAtual())
								&& dtoSol.getSolicitanteUnidade() != null
								&& gerenciamentoBean.getSolicitanteUnidade().trim().equalsIgnoreCase(StringEscapeUtils.unescapeJavaScript(dtoSol.getSolicitanteUnidade().trim()))) {

							colTarefasFiltradasFinal.add(tarefaDto);
							resultSolicitacoes.add((SolicitacaoServicoDTO) tarefaDto.getSolicitacaoDto());

						}

					}

					continue;
				}

				// RESPONSÁVEL && GRUPO EXECUTOR && SOLICITANTE -- SELECIONADOS - OK
				if ((gerenciamentoBean.getResponsavelAtual() != null && StringUtils.isNotBlank(gerenciamentoBean.getResponsavelAtual()) && gerenciamentoBean.getGrupoAtual() != null
						&& StringUtils.isNotBlank(gerenciamentoBean.getGrupoAtual()) && gerenciamentoBean.getSolicitanteUnidade() != null && StringUtils.isNotBlank(gerenciamentoBean
						.getSolicitanteUnidade()))) {

					// RESPONSÁVEL SEM ATRIBUIÇÃO && GRUPO EXECUTOR SEM ATRIBUIÇÃO && SOLICITANTE SELECIONADO - OK
					if ((this.isSemAtribuicao(request, gerenciamentoBean.getResponsavelAtual()) && (tarefaDto.getResponsavelAtual() == null || StringUtils.isBlank(tarefaDto.getResponsavelAtual())))
							&& (this.isSemAtribuicao(request, gerenciamentoBean.getGrupoAtual()) && (dtoSol.getGrupoAtual() == null || StringUtils.isBlank(dtoSol.getGrupoAtual())))
							&& (dtoSol.getSolicitanteUnidade() != null && gerenciamentoBean.getSolicitanteUnidade().trim()
									.equalsIgnoreCase(StringEscapeUtils.unescapeJavaScript(dtoSol.getSolicitanteUnidade().trim())))) {

						colTarefasFiltradasFinal.add(tarefaDto);
						resultSolicitacoes.add((SolicitacaoServicoDTO) tarefaDto.getSolicitacaoDto());

						continue;

					} else {
						// RESPONSÁVEL SEM ATRIBUIÇÃO && GRUPO EXECUTOR SELECIONADO && SOLICITANTE SELECIONADO - OK

						if ((this.isSemAtribuicao(request, gerenciamentoBean.getResponsavelAtual()) && (tarefaDto.getResponsavelAtual() == null || StringUtils.isBlank(tarefaDto.getResponsavelAtual())))
								&& dtoSol.getGrupoAtual() != null
								&& StringUtils.equalsIgnoreCase(dtoSol.getGrupoAtual().trim(), gerenciamentoBean.getGrupoAtual().trim())
								&& (dtoSol.getSolicitanteUnidade() != null && gerenciamentoBean.getSolicitanteUnidade().trim()
										.equalsIgnoreCase(StringEscapeUtils.unescapeJavaScript(dtoSol.getSolicitanteUnidade().trim())))) {

							colTarefasFiltradasFinal.add(tarefaDto);
							resultSolicitacoes.add((SolicitacaoServicoDTO) tarefaDto.getSolicitacaoDto());

							continue;

						}

						// RESPONSÁVEL && GRUPO EXECUTOR && SOLICITANTE -- SELECIONADOS - OK
						if (tarefaDto.getResponsavelAtual() != null && StringUtils.equalsIgnoreCase(tarefaDto.getResponsavelAtual().trim(), gerenciamentoBean.getResponsavelAtual().trim())
								&& dtoSol.getGrupoAtual() != null && StringUtils.equalsIgnoreCase(dtoSol.getGrupoAtual().trim(), gerenciamentoBean.getGrupoAtual().trim())
								&& dtoSol.getSolicitanteUnidade() != null
								&& gerenciamentoBean.getSolicitanteUnidade().trim().equalsIgnoreCase(StringEscapeUtils.unescapeJavaScript(dtoSol.getSolicitanteUnidade().trim()))) {

							colTarefasFiltradasFinal.add(tarefaDto);
							resultSolicitacoes.add((SolicitacaoServicoDTO) tarefaDto.getSolicitacaoDto());

							continue;
						}
					}
				}

				// SE SOMENTE GRUPO FOI SELECIONADO
				if ((gerenciamentoBean.getGrupoAtual() != null && StringUtils.isNotBlank(gerenciamentoBean.getGrupoAtual()))
						&& (gerenciamentoBean.getResponsavelAtual() == null || StringUtils.isBlank(gerenciamentoBean.getResponsavelAtual()))
						&& (gerenciamentoBean.getSolicitanteUnidade() == null || StringUtils.isBlank(gerenciamentoBean.getSolicitanteUnidade()))) {

					// SE GRUPO SEM ATRIBUIÇÃO - OK
					if (this.isSemAtribuicao(request, gerenciamentoBean.getGrupoAtual()) && (tarefaDto.getResponsavelAtual() == null || StringUtils.isBlank(tarefaDto.getResponsavelAtual()))) {

						colTarefasFiltradasFinal.add(tarefaDto);
						resultSolicitacoes.add((SolicitacaoServicoDTO) tarefaDto.getSolicitacaoDto());

					} else {

						// SE GRUPO SELECIONADO - OK
						if (gerenciamentoBean.getGrupoAtual() != null && dtoSol.getGrupoAtual() != null && gerenciamentoBean.getGrupoAtual().trim().equalsIgnoreCase(dtoSol.getGrupoAtual().trim())) {
							colTarefasFiltradasFinal.add(tarefaDto);
							resultSolicitacoes.add((SolicitacaoServicoDTO) tarefaDto.getSolicitacaoDto());
						}
					}

					continue;
				}

				// SE GRUPO && SOLICITANTE SELECIONADO - OK
				if ((gerenciamentoBean.getResponsavelAtual() == null || StringUtils.isBlank(gerenciamentoBean.getResponsavelAtual()))
						&& (gerenciamentoBean.getGrupoAtual() != null && dtoSol.getGrupoAtual() != null && gerenciamentoBean.getGrupoAtual().trim().equalsIgnoreCase(dtoSol.getGrupoAtual().trim()) && !this
								.isSemAtribuicao(request, gerenciamentoBean.getGrupoAtual()))
						&& (gerenciamentoBean.getSolicitanteUnidade() != null && dtoSol.getSolicitanteUnidade() != null
								&& gerenciamentoBean.getSolicitanteUnidade().trim().equalsIgnoreCase(StringEscapeUtils.unescapeJavaScript(dtoSol.getSolicitanteUnidade().trim())) && !this
									.isSemAtribuicao(request, dtoSol.getSolicitanteUnidade()))) {

					colTarefasFiltradasFinal.add(tarefaDto);
					resultSolicitacoes.add((SolicitacaoServicoDTO) tarefaDto.getSolicitacaoDto());

					continue;
				}

				// SE SOMENTE SOLICITANTE FOI SELECIONADO - OK
				if ((gerenciamentoBean.getSolicitanteUnidade() != null && dtoSol.getSolicitanteUnidade() != null
						&& gerenciamentoBean.getSolicitanteUnidade().trim().equalsIgnoreCase(StringEscapeUtils.unescapeJavaScript(dtoSol.getSolicitanteUnidade().trim())) && !this.isSemAtribuicao(
						request, dtoSol.getSolicitanteUnidade()))
						&& ((gerenciamentoBean.getResponsavelAtual() == null || StringUtils.isBlank(gerenciamentoBean.getResponsavelAtual())) && (gerenciamentoBean.getGrupoAtual() == null || StringUtils
								.isBlank(gerenciamentoBean.getGrupoAtual())))) {

					colTarefasFiltradasFinal.add(tarefaDto);
					resultSolicitacoes.add((SolicitacaoServicoDTO) tarefaDto.getSolicitacaoDto());

					continue;
				}

			}

			// VERIFICA SE A SOLICITAÇÃO POSSUI ANEXO
			ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
			Collection colAnexos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.TABELA_SOLICITACAOSERVICO, dtoSol.getIdSolicitacaoServico());

			if (colAnexos != null) {
				dtoSol.setPossuiAnexo("S");
			} else {
				dtoSol.setPossuiAnexo("N");
			}

			dtoSol = null;
		}

		if (gerenciamentoBean.getNomeCampoOrdenacao() != null && !gerenciamentoBean.getNomeCampoOrdenacao().trim().equalsIgnoreCase("")
				&& !gerenciamentoBean.getNomeCampoOrdenacao().trim().equalsIgnoreCase("dataHoraLimite")) {
			Collections.sort(resultSolicitacoes, new ObjectSimpleComparator(getWithGet(gerenciamentoBean.getNomeCampoOrdenacao()), (asc ? ObjectSimpleComparator.ASC : ObjectSimpleComparator.DESC)));
			colTarefasFiltradasFinal = (List<TarefaFluxoDTO>) reordena((ArrayList<TarefaFluxoDTO>) colTarefasFiltradasFinal, resultSolicitacoes);
		} else {
			if (asc) {
				Collections.sort(colTarefasFiltradasFinal, new ObjectSimpleComparator("getDataHoraLimite", ObjectSimpleComparator.ASC));
			} else {
				Collections.sort(colTarefasFiltradasFinal, new ObjectSimpleComparator("getDataHoraLimite", ObjectSimpleComparator.DESC));
			}
		}

		String tarefasStr = serializaTarefas(colTarefasFiltradasFinal);
		/*
		 * String tarefasStr = new Gson().toJson(colTarefasFiltradasFinal); tarefasStr = tarefasStr.replaceAll("\n", " "); tarefasStr = tarefasStr.replaceAll("\r", " "); tarefasStr =
		 * tarefasStr.replaceAll("\\\\n", " "); tarefasStr = tarefasStr.replaceAll("\\\\", "{{BARRA}}");
		 */

		document.executeScript("exibirTarefas('" + tarefasStr + "');");

		document.getSelectById("responsavelAtual").removeAllOptions();
		document.getSelectById("responsavelAtual").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");

		Map<String, String> treeMapResponsavelAtual = new TreeMap<String, String>(mapResponsavelAtual);

		for (Iterator it = treeMapResponsavelAtual.values().iterator(); it.hasNext();) {
			String responsavelAtual = (String) it.next();
			document.getSelectById("responsavelAtual").addOption(responsavelAtual, responsavelAtual);
		}

		document.getSelectById("responsavelAtual").setValue(gerenciamentoBean.getResponsavelAtual());

		document.getSelectById("grupoAtual").removeAllOptions();
		document.getSelectById("grupoAtual").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");

		Map<String, String> treeMapGrupoAtual = new TreeMap<String, String>(mapGrupoAtual);

		for (Iterator it = treeMapGrupoAtual.values().iterator(); it.hasNext();) {
			String grupoAtual = (String) it.next();
			document.getSelectById("grupoAtual").addOption(grupoAtual, grupoAtual);
		}

		document.getSelectById("grupoAtual").setValue(gerenciamentoBean.getGrupoAtual());

		document.getSelectById("solicitanteUnidade").removeAllOptions();
		document.getSelectById("solicitanteUnidade").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");

		Map<String, String> treeMapSolicitanteUnidade = new TreeMap<String, String>(mapSolicitanteUnidade);

		for (Iterator it = treeMapSolicitanteUnidade.values().iterator(); it.hasNext();) {
			String solicitanteUnidade = (String) it.next();
			document.getSelectById("solicitanteUnidade").addOption(solicitanteUnidade, solicitanteUnidade);
		}

		document.getSelectById("solicitanteUnidade").setValue(StringEscapeUtils.escapeJavaScript(gerenciamentoBean.getSolicitanteUnidade()));

		TipoDemandaServicoService tipoDemandaService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, null);
		HTMLSelect idTipoDemandaServico = (HTMLSelect) document.getSelectById("idTipoDemandaServico");
		idTipoDemandaServico.removeAllOptions();
		idTipoDemandaServico.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		Collection col = tipoDemandaService.listSolicitacoes();
		if (col != null) {
			idTipoDemandaServico.addOptions(col, "idTipoDemandaServico", "nomeTipoDemandaServico", null);
		}
		document.getSelectById("idTipoDemandaServico").setValue(gerenciamentoBean.getIdTipoDemandaServico());

		// inverte asc
		gerenciamentoBean.setOrdenacaoAsc(gerenciamentoBean.getOrdenacaoAsc() != null && gerenciamentoBean.getOrdenacaoAsc().equalsIgnoreCase("true") ? "false" : "true");
		document.executeScript("document.formPesquisa.ordenacaoAsc.value = " + gerenciamentoBean.getOrdenacaoAsc());
		document.executeScript("setinha()");

		request.getSession().setAttribute("dadosEstaGerenServ", colTarefasFiltradasFinal);

		gerenciamentoBean = null;

	}

	public void mostraEstatAdicional(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		Collection colItens = (Collection) request.getSession().getAttribute("dadosEstaGerenServ");
		DefaultCategoryDataset dataSetTempoFaltante = new DefaultCategoryDataset();
		DefaultCategoryDataset dataSetData = new DefaultCategoryDataset();
		DefaultCategoryDataset dataSetAtrib = new DefaultCategoryDataset();
		DefaultCategoryDataset dataSetTecnico = new DefaultCategoryDataset();
		DefaultCategoryDataset dataSetTarefa = new DefaultCategoryDataset();
		DefaultCategoryDataset dataSetUnidade = new DefaultCategoryDataset();
		UsuarioServiceEjb usuarioService = new UsuarioServiceEjb();
		EmpregadoServiceEjb empregadoService = new EmpregadoServiceEjb();
		UnidadeDao unidadeDao = new UnidadeDao();
		ElementoFluxoDao elementoFluxoDao = new ElementoFluxoDao();
		StringBuilder strBuilderTableEst = new StringBuilder("Não há dados!");
		Date dAmanha = UtilDatas.incrementaDiasEmData(UtilDatas.getDataAtual(), 1);
		Date dOntem = UtilDatas.incrementaDiasEmData(UtilDatas.getDataAtual(), -1);
		if (colItens != null) {
			int atrasada = 0;
			int aux30min = 0;
			int aux60min = 0;
			int aux90min = 0;
			int aux120min = 0;
			int auxOk = 0;

			int hoje = 0;
			int depois = 0;
			int antes = 0;

			int qtdeAtrib = 0;
			int qtdeSemAtrib = 0;
			HashMap hashTecnico = new HashMap();
			HashMap hashTarefa = new HashMap();
			HashMap hashUnidade = new HashMap();
			for (Iterator it = colItens.iterator(); it.hasNext();) {
				TarefaFluxoDTO tarefaFluxoDTO = (TarefaFluxoDTO) it.next();
				SolicitacaoServicoDTO solicitacaoServicoDTO = (SolicitacaoServicoDTO) tarefaFluxoDTO.getSolicitacaoDto();
				if (solicitacaoServicoDTO.getAtrasada()) {
					atrasada++;
				} else {
					if (solicitacaoServicoDTO.getTempoFaltante() != null && solicitacaoServicoDTO.getTempoFaltante().equalsIgnoreCase("30")) {
						aux30min++;
					}
					if (solicitacaoServicoDTO.getTempoFaltante() != null && solicitacaoServicoDTO.getTempoFaltante().equalsIgnoreCase("60")) {
						aux60min++;
					}
					if (solicitacaoServicoDTO.getTempoFaltante() != null && solicitacaoServicoDTO.getTempoFaltante().equalsIgnoreCase("90")) {
						aux90min++;
					}
					if (solicitacaoServicoDTO.getTempoFaltante() != null && solicitacaoServicoDTO.getTempoFaltante().equalsIgnoreCase("120")) {
						aux120min++;
					}
					if (solicitacaoServicoDTO.getTempoFaltante() != null && solicitacaoServicoDTO.getTempoFaltante().equalsIgnoreCase("0") || solicitacaoServicoDTO.getTempoFaltante() != null
							&& solicitacaoServicoDTO.getTempoFaltante().equalsIgnoreCase("*")) {
						auxOk++;
					}
				}
				if (solicitacaoServicoDTO.getDataHoraLimite() != null) {
					if (solicitacaoServicoDTO.getDataHoraLimite().after(dAmanha)) {
						depois++;
					} else if (solicitacaoServicoDTO.getDataHoraLimite().before(dOntem)) {
						antes++;
					} else {
						hoje++;
					}
				}
				if (tarefaFluxoDTO.getIdResponsavelAtual() == null) {
					qtdeSemAtrib++;
				} else {
					qtdeAtrib++;
				}
				String qtdeResp = (String) hashTecnico.get("" + tarefaFluxoDTO.getIdResponsavelAtual());
				if (!hashTecnico.containsKey("" + tarefaFluxoDTO.getIdResponsavelAtual())) {
					hashTecnico.put("" + tarefaFluxoDTO.getIdResponsavelAtual(), "1");
				} else {
					int qtde = 0;
					try {
						qtde = Integer.parseInt(qtdeResp);
					} catch (Exception e) {
						e.printStackTrace();
					}
					qtde++;
					hashTecnico.put("" + tarefaFluxoDTO.getIdResponsavelAtual(), "" + qtde);
				}
				String qtdeTarefa = (String) hashTarefa.get("" + tarefaFluxoDTO.getIdElemento());
				if (!hashTarefa.containsKey("" + tarefaFluxoDTO.getIdElemento())) {
					hashTarefa.put("" + tarefaFluxoDTO.getIdElemento(), "1");
				} else {
					int qtde = 0;
					try {
						qtde = Integer.parseInt(qtdeTarefa);
					} catch (Exception e) {
						e.printStackTrace();
					}
					qtde++;
					hashTarefa.put("" + tarefaFluxoDTO.getIdElemento(), "" + qtde);
				}
				String qtdeUnidade = (String) hashUnidade.get("" + solicitacaoServicoDTO.getIdUnidade());
				if (!hashUnidade.containsKey("" + solicitacaoServicoDTO.getIdUnidade())) {
					hashUnidade.put("" + solicitacaoServicoDTO.getIdUnidade(), "1");
				} else {
					int qtde = 0;
					try {
						qtde = Integer.parseInt(qtdeUnidade);
					} catch (Exception e) {
						e.printStackTrace();
					}
					qtde++;
					hashUnidade.put("" + solicitacaoServicoDTO.getIdUnidade(), "" + qtde);
				}
			}
			dataSetTempoFaltante.addValue(atrasada, "TEMPO", "Atrasada");
			dataSetTempoFaltante.addValue(aux30min, "TEMPO", "<=30 min");
			dataSetTempoFaltante.addValue(aux60min, "TEMPO", "<=60 min");
			dataSetTempoFaltante.addValue(aux90min, "TEMPO", "<=90 min");
			dataSetTempoFaltante.addValue(aux120min, "TEMPO", "<=120 min");
			dataSetTempoFaltante.addValue(auxOk, "TEMPO", ">120 min");

			String dir = CITCorporeUtil.caminho_real_app + "/tempFiles/" + usuario.getIdUsuario();
			String arq1 = CITCorporeUtil.caminho_real_app + "/tempFiles/" + usuario.getIdUsuario() + "/" + "IMG_GRF_ESTAT001_" + usuario.getIdUsuario() + ".png";
			File arquivo = new File(dir);
			if (!arquivo.exists())
				arquivo.mkdirs();

			arquivo = new File(arq1);
			if (arquivo.exists()) {
				arquivo.delete();
			}
			JFreeChart chart = ChartFactory.createBarChart("Controle Tempo", "", "", dataSetTempoFaltante, PlotOrientation.HORIZONTAL, false, true, true);
			atribuiInfoGrafico(chart);
			try {
				ChartUtilities.saveChartAsPNG(arquivo, chart, 240, 120);
			} catch (IOException e) {
				e.printStackTrace();
			}

			// ----------------
			dataSetData.addValue(antes, "TEMPO", "Anterior a hoje");
			dataSetData.addValue(hoje, "TEMPO", "Hoje");
			dataSetData.addValue(depois, "TEMPO", "Amanhã ou depois");

			String arq0 = CITCorporeUtil.caminho_real_app + "/tempFiles/" + usuario.getIdUsuario() + "/" + "IMG_GRF_ESTAT000_" + usuario.getIdUsuario() + ".png";

			arquivo = new File(arq0);
			if (arquivo.exists()) {
				arquivo.delete();
			}
			JFreeChart chart0 = ChartFactory.createBarChart("Controle por Data", "", "", dataSetData, PlotOrientation.HORIZONTAL, false, true, true);
			atribuiInfoGrafico(chart0);
			try {
				ChartUtilities.saveChartAsPNG(arquivo, chart0, 240, 120);
			} catch (IOException e) {
				e.printStackTrace();
			}

			// ----------------
			for (Iterator iter = hashTecnico.entrySet().iterator(); iter.hasNext();) {
				Map.Entry chave = (Entry) iter.next();
				if (chave != null) {
					String qtdeResp = (String) hashTecnico.get(chave.getKey());
					int qtde = 0;
					try {
						qtde = Integer.parseInt(qtdeResp);
					} catch (Exception e) {
						e.printStackTrace();
					}
					int idResp = 0;
					try {
						idResp = Integer.parseInt(chave.getKey().toString());
					} catch (Exception e) {
						e.printStackTrace();
					}
					String resp = chave.getKey().toString();
					if (resp == null || resp.trim().equalsIgnoreCase("null")) {
						resp = "Sem atribuição";
					} else if (idResp != 0) {
						UsuarioDTO usuarioDto = new UsuarioDTO();
						usuarioDto.setIdUsuario(idResp);
						try {
							usuarioDto = (UsuarioDTO) usuarioService.restore(usuarioDto);
							if (usuarioDto != null) {
								EmpregadoDTO empDto = new EmpregadoDTO();
								empDto.setIdEmpregado(usuarioDto.getIdEmpregado());
								empDto = (EmpregadoDTO) empregadoService.restore(empDto);
								if (empDto != null) {
									resp = empDto.getNome();
								}
							}
						} catch (ServiceException e) {
							e.printStackTrace();
						} catch (LogicException e) {
							e.printStackTrace();
						}
					}
					dataSetTecnico.setValue(qtde, resp, resp);
				}
			}
			String arq2 = CITCorporeUtil.caminho_real_app + "/tempFiles/" + usuario.getIdUsuario() + "/" + "IMG_GRF_ESTAT002_" + usuario.getIdUsuario() + ".png";

			arquivo = new File(arq2);
			if (arquivo.exists()) {
				arquivo.delete();
			}
			JFreeChart chart2 = ChartFactory.createBarChart("Atribuições por técnico", "", "", dataSetTecnico, PlotOrientation.HORIZONTAL, false, true, false);
			atribuiInfoGrafico(chart2);
			try {
				ChartUtilities.saveChartAsPNG(arquivo, chart2, 520, 240);
			} catch (IOException e) {
				e.printStackTrace();
			}

			// ----------------
			for (Iterator iter = hashTarefa.entrySet().iterator(); iter.hasNext();) {
				Map.Entry chave = (Entry) iter.next();
				if (chave != null) {
					String qtdeResp = (String) hashTarefa.get(chave.getKey());
					int qtde = 0;
					try {
						qtde = Integer.parseInt(qtdeResp);
					} catch (Exception e) {
						e.printStackTrace();
					}
					int idElemento = 0;
					try {
						idElemento = Integer.parseInt(chave.getKey().toString());
					} catch (Exception e) {
						e.printStackTrace();
					}
					String resp = chave.getKey().toString();
					if (resp == null || resp.trim().equalsIgnoreCase("null")) {
						resp = "Sem atribuição";
					} else if (idElemento != 0) {
						ElementoFluxoDTO elementoFluxoDTO = new ElementoFluxoDTO();
						elementoFluxoDTO.setIdElemento(idElemento);
						try {
							elementoFluxoDTO = (ElementoFluxoDTO) elementoFluxoDao.restore(elementoFluxoDTO);
							if (elementoFluxoDTO != null) {
								resp = elementoFluxoDTO.getNome();
							}
						} catch (ServiceException e) {
							e.printStackTrace();
						} catch (LogicException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					dataSetTarefa.setValue(qtde, resp, resp);
				}
			}
			String arq3 = CITCorporeUtil.caminho_real_app + "/tempFiles/" + usuario.getIdUsuario() + "/" + "IMG_GRF_ESTAT003_" + usuario.getIdUsuario() + ".png";

			arquivo = new File(arq3);
			if (arquivo.exists()) {
				arquivo.delete();
			}
			JFreeChart chart3 = ChartFactory.createBarChart("Alocação por tarefa", "", "", dataSetTarefa, PlotOrientation.HORIZONTAL, false, true, false);
			atribuiInfoGrafico(chart3);
			try {
				ChartUtilities.saveChartAsPNG(arquivo, chart3, 520, 240);
			} catch (IOException e) {
				e.printStackTrace();
			}

			// ----------------
			for (Iterator iter = hashUnidade.entrySet().iterator(); iter.hasNext();) {
				Map.Entry chave = (Entry) iter.next();
				if (chave != null) {
					String qtdeResp = (String) hashUnidade.get(chave.getKey());
					int qtde = 0;
					try {
						qtde = Integer.parseInt(qtdeResp);
					} catch (Exception e) {
						e.printStackTrace();
					}
					int idUnidade = 0;
					try {
						idUnidade = Integer.parseInt(chave.getKey().toString());
					} catch (Exception e) {
						e.printStackTrace();
					}
					String resp = chave.getKey().toString();
					if (resp == null || resp.trim().equalsIgnoreCase("null")) {
						resp = "Sem atribuição";
					} else if (idUnidade != 0) {
						UnidadeDTO unidadeDTO = new UnidadeDTO();
						unidadeDTO.setIdUnidade(idUnidade);
						try {
							unidadeDTO = (UnidadeDTO) unidadeDao.restore(unidadeDTO);
							if (unidadeDTO != null) {
								resp = unidadeDTO.getNome();
							}
						} catch (ServiceException e) {
							e.printStackTrace();
						} catch (LogicException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					dataSetUnidade.setValue(qtde, resp, resp);
				}
			}
			String arq4 = CITCorporeUtil.caminho_real_app + "/tempFiles/" + usuario.getIdUsuario() + "/" + "IMG_GRF_ESTAT004_" + usuario.getIdUsuario() + ".png";

			arquivo = new File(arq4);
			if (arquivo.exists()) {
				arquivo.delete();
			}
			JFreeChart chart4 = ChartFactory.createBarChart("Alocação por unidade", "", "", dataSetUnidade, PlotOrientation.HORIZONTAL, false, true, false);
			atribuiInfoGrafico(chart4);
			try {
				ChartUtilities.saveChartAsPNG(arquivo, chart4, 520, 240);
			} catch (IOException e) {
				e.printStackTrace();
			}

			// ----------------
			dataSetAtrib.addValue(qtdeSemAtrib, "ATRIB", "Não há");
			dataSetAtrib.addValue(qtdeAtrib, "ATRIB", "Atribuída");

			String arq5 = CITCorporeUtil.caminho_real_app + "/tempFiles/" + usuario.getIdUsuario() + "/" + "IMG_GRF_ESTAT005_" + usuario.getIdUsuario() + ".png";

			arquivo = new File(arq5);
			if (arquivo.exists()) {
				arquivo.delete();
			}
			JFreeChart chart5 = ChartFactory.createBarChart("Req./Incidentes com Responsável", "", "", dataSetAtrib, PlotOrientation.HORIZONTAL, false, true, true);
			atribuiInfoGrafico(chart5);
			try {
				ChartUtilities.saveChartAsPNG(arquivo, chart5, 240, 120);
			} catch (IOException e) {
				e.printStackTrace();
			}

			strBuilderTableEst.delete(0, strBuilderTableEst.length());
			strBuilderTableEst.append("<table>");
			strBuilderTableEst.append("<tr>");
			strBuilderTableEst.append("<td colspan='2'>");
			strBuilderTableEst
					.append("<span style='text-color:red'>Atenção! Os dados abaixo representam os dados que estão sendo apresentados na listagem. Levam em consideração as permissões e o filtro aplicadao na listagem.</span>");
			strBuilderTableEst.append("</td>");
			strBuilderTableEst.append("</tr>");
			strBuilderTableEst.append("<tr>");
			strBuilderTableEst.append("<td>");
			strBuilderTableEst.append("<img src='" + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles/" + usuario.getIdUsuario() + "/IMG_GRF_ESTAT001_" + usuario.getIdUsuario()
					+ ".png' border='0'>");
			strBuilderTableEst.append("</td>");
			strBuilderTableEst.append("<td>");
			strBuilderTableEst.append("<img src='" + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles/" + usuario.getIdUsuario() + "/IMG_GRF_ESTAT002_" + usuario.getIdUsuario()
					+ ".png' border='0'>");
			strBuilderTableEst.append("</td>");
			strBuilderTableEst.append("</tr>");
			strBuilderTableEst.append("<tr>");
			strBuilderTableEst.append("<td>");
			strBuilderTableEst.append("<img src='" + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles/" + usuario.getIdUsuario() + "/IMG_GRF_ESTAT000_" + usuario.getIdUsuario()
					+ ".png' border='0'>");
			strBuilderTableEst.append("</td>");
			strBuilderTableEst.append("<td>");
			strBuilderTableEst.append("<img src='" + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles/" + usuario.getIdUsuario() + "/IMG_GRF_ESTAT003_" + usuario.getIdUsuario()
					+ ".png' border='0'>");
			strBuilderTableEst.append("</td>");
			strBuilderTableEst.append("</tr>");
			strBuilderTableEst.append("<tr>");
			strBuilderTableEst.append("<td>");
			strBuilderTableEst.append("<img src='" + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles/" + usuario.getIdUsuario() + "/IMG_GRF_ESTAT005_" + usuario.getIdUsuario()
					+ ".png' border='0'>");
			strBuilderTableEst.append("</td>");
			strBuilderTableEst.append("<td>");
			strBuilderTableEst.append("<img src='" + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles/" + usuario.getIdUsuario() + "/IMG_GRF_ESTAT004_" + usuario.getIdUsuario()
					+ ".png' border='0'>");
			strBuilderTableEst.append("</td>");
			strBuilderTableEst.append("</tr>");
			strBuilderTableEst.append("</table>");
		}

		document.getElementById("divInterna_divDemaisEstat").setInnerHTML(strBuilderTableEst.toString());
	}

	private void atribuiInfoGrafico(JFreeChart chart) {
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
		plot.getRenderer().setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE1, TextAnchor.CENTER));
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setUpperMargin(0.20);
		rangeAxis.setAxisLineVisible(true);
		rangeAxis.setTickLabelsVisible(true);
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		// Formatando cores de fundo, fonte do titulo, etc...
		// chart.setBackgroundPaint(COR_FUNDO); // Cor do fundo do grafico
		// chart.getTitle().setPaint(COR_TITULO); // Cor do titulo
		chart.getTitle().setFont(new java.awt.Font("arial", Font.BOLD, 10)); // Fonte do
		chart.getPlot().setBackgroundPaint(new Color(221, 227, 213));// Cor de
		chart.setBorderVisible(false); // Visibilidade da borda do grafico
		BarRenderer rend = (BarRenderer) plot.getRenderer();
		// CategoryItemRenderer rend = (CategoryItemRenderer) plot.getRenderer();
		rend.setSeriesOutlinePaint(0, Color.BLACK); // Cor da borda das barras do
		rend.setBaseItemLabelFont(new java.awt.Font("SansSerif", Font.BOLD, 10));

		// rend.setSeriesPaint(0, new Color(70 ,130 ,180)); // Cor das barras do
		// rend.setItemMargin(0.10); // Margem entre o eixo Y e a primeira barra do
		rend.setBaseItemLabelsVisible(true);
		// rend.setBaseItemLabelGenerator(new CustomLabelGenerator());
		rend.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		rend.setSeriesItemLabelsVisible(0, new Boolean(true));
		rend.setSeriesItemLabelGenerator(0, new StandardCategoryItemLabelGenerator());
		// rend.setSeriesPositiveItemLabelPosition(1, new ItemLabelPosition(ItemLabelAnchor.OUTSIDE11, TextAnchor.BASELINE_CENTER, TextAnchor.BASELINE_CENTER, 50.0));
		rend.setPositiveItemLabelPositionFallback(new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.BASELINE_CENTER, TextAnchor.CENTER, 0.0));
	}

	/**
	 * Verifica se valor é -- Sem Atribuição --
	 * 
	 * @param request
	 * @param valor
	 * @return true ou false
	 * @author valdoilo.damasceno
	 */
	private boolean isSemAtribuicao(HttpServletRequest request, String valor) {
		if (valor != null && valor.trim().equalsIgnoreCase(UtilI18N.internacionaliza(request, "citcorpore.comum.sematribuicao"))) {
			return true;
		} else {
			return false;
		}
	}

	private String serializaTarefas(List<TarefaFluxoDTO> colTarefas) throws Exception {
		if (colTarefas == null)
			return null;
		for (TarefaFluxoDTO tarefaDto : colTarefas) {
			tarefaDto.setElementoFluxo_serialize(br.com.citframework.util.WebUtil.serializeObject(tarefaDto.getElementoFluxoDto()));
			tarefaDto.setSolicitacao_serialize(br.com.citframework.util.WebUtil.serializeObject(tarefaDto.getSolicitacaoDto()));
		}
		return br.com.citframework.util.WebUtil.serializeObjects(colTarefas);
	}

	private ArrayList<TarefaFluxoDTO> reordena(ArrayList<TarefaFluxoDTO> tarefas, ArrayList<SolicitacaoServicoDTO> solicitacoes) {
		ArrayList<TarefaFluxoDTO> novaLista = new ArrayList<TarefaFluxoDTO>();
		for (SolicitacaoServicoDTO s : solicitacoes) {
			loopTarefas: for (TarefaFluxoDTO t : tarefas) {
				if (((SolicitacaoServicoDTO) t.getSolicitacaoDto()).getIdSolicitacaoServico().equals(s.getIdSolicitacaoServico())) {
					novaLista.add(t);
					break loopTarefas;
				}
			}
		}

		return novaLista;
	}

	/**
	 * Insere 'get' antes do elemento para se adequar à reflexão.
	 * 
	 * @param palavra
	 * @return
	 * @author breno.guimaraes
	 */
	private String getWithGet(String palavra) {
		return "get" + palavra.substring(0, 1).toUpperCase() + palavra.substring(1);
	}

	private boolean isContratoInList(Integer idContrato, Collection colContratosColab) {
		if (idContrato == null) {
			return false;
		}
		if (colContratosColab != null) {
			for (Iterator it = colContratosColab.iterator(); it.hasNext();) {
				ContratosGruposDTO contratosGruposDTO = (ContratosGruposDTO) it.next();
				if (contratosGruposDTO.getIdContrato() == null) {
					contratosGruposDTO.setIdContrato(new Integer(0));
				}
				if (contratosGruposDTO.getIdContrato().intValue() == idContrato.intValue()) {
					return true;
				}
			}
		}
		return false;
	}

	public void preparaExecucaoTarefa(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		GerenciamentoServicosDTO gerenciamentoBean = (GerenciamentoServicosDTO) document.getBean();
		if (gerenciamentoBean.getIdTarefa() == null)
			return;

		ExecucaoSolicitacaoService execucaoSolicitacaoService = (ExecucaoSolicitacaoService) ServiceLocator.getInstance().getService(ExecucaoSolicitacaoService.class, null);
		TarefaFluxoDTO tarefaDto = execucaoSolicitacaoService.recuperaTarefa(usuario.getLogin(), gerenciamentoBean.getIdTarefa());
		if (tarefaDto == null || tarefaDto.getElementoFluxoDto() == null || !tarefaDto.getExecutar().equals("S") || tarefaDto.getElementoFluxoDto().getTipoInteracao() == null)
			return;

		if (tarefaDto.getElementoFluxoDto().getTipoInteracao().equals(Enumerados.INTERACAO_VISAO)) {
			if (tarefaDto.getIdVisao() != null) {
				document.executeScript("exibirVisao('" + UtilI18N.internacionaliza(request, "gerenciaservico.executartarefa") + " " + tarefaDto.getElementoFluxoDto().getDocumentacao() + "','"
						+ tarefaDto.getIdVisao() + "','" + tarefaDto.getElementoFluxoDto().getIdFluxo() + "','" + tarefaDto.getIdItemTrabalho() + "','" + gerenciamentoBean.getAcaoFluxo() + "');");
			} else {
				document.alert(UtilI18N.internacionaliza(request, "gerenciaservico.visaotarefa") + " \"" + tarefaDto.getElementoFluxoDto().getDocumentacao() + "\" "
						+ UtilI18N.internacionaliza(request, "gerenciaservico.naoencontrado"));
			}
		} else {
			String caracterParmURL = "?";
			if (tarefaDto.getElementoFluxoDto().getUrl().indexOf("?") > -1) { // Se na URL jÃ¡ conter ?, entao colocar &
				caracterParmURL = "&";
			}
			document.executeScript("exibirUrl('" + UtilI18N.internacionaliza(request, "gerenciaservico.executartarefa") + " " + tarefaDto.getElementoFluxoDto().getDocumentacao() + "','"
					+ tarefaDto.getElementoFluxoDto().getUrl() + caracterParmURL + "idSolicitacaoServico=" + ((SolicitacaoServicoDTO) tarefaDto.getSolicitacaoDto()).getIdSolicitacaoServico()
					+ "&idTarefa=" + tarefaDto.getIdItemTrabalho() + "&acaoFluxo=" + gerenciamentoBean.getAcaoFluxo() + "');");
		}

		tarefaDto = null;
	}

	public void reativaSolicitacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		GerenciamentoServicosDTO gerenciamentoBean = (GerenciamentoServicosDTO) document.getBean();
		if (gerenciamentoBean.getIdSolicitacao() == null)
			return;

		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		SolicitacaoServicoDTO solicitacaoServicoDto = solicitacaoServicoService.restoreAll(gerenciamentoBean.getIdSolicitacao());
		solicitacaoServicoService.reativa(usuario, solicitacaoServicoDto);
		exibeTarefas(document, request, response);

		gerenciamentoBean = null;
		solicitacaoServicoDto = null;
	}

	public void capturaTarefa(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		GerenciamentoServicosDTO gerenciamentoBean = (GerenciamentoServicosDTO) document.getBean();
		if (gerenciamentoBean.getIdTarefa() == null)
			return;

		ExecucaoSolicitacaoService execucaoSolicitacaoService = (ExecucaoSolicitacaoService) ServiceLocator.getInstance().getService(ExecucaoSolicitacaoService.class, null);
		execucaoSolicitacaoService.executa(usuario, gerenciamentoBean.getIdTarefa(), Enumerados.ACAO_INICIAR);
		exibeTarefas(document, request, response);

		gerenciamentoBean = null;
	}

	public void imprimeErroGrid(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GerenciamentoServicosDTO gerenciamentoBean = (GerenciamentoServicosDTO) document.getBean();
		System.err.println("\n\nErro ao renderizar grid solicitações: " + gerenciamentoBean.getErroGrid());
		System.out.println("\n\nErro ao renderizar grid solicitações: " + gerenciamentoBean.getErroGrid());
	}

	public void exibeGraficoVeloc(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		GerenciamentoServicosDTO gerenciamentoBean = (GerenciamentoServicosDTO) document.getBean();
		ArrayList colMeters = new ArrayList();

		MeterInterval meterInterval = new MeterInterval(UtilI18N.internacionaliza(request, "gerenciaservico.atrasadas"), new Range(0, gerenciamentoBean.getQuantidadeAtrasadas()), Color.RED, null,
				Color.RED);
		colMeters.add(meterInterval);
		meterInterval = new MeterInterval(UtilI18N.internacionaliza(request, "citcorpore.comum.emandamento"), new Range(gerenciamentoBean.getQuantidadeAtrasadas(),
				gerenciamentoBean.getQuantidadeTotal()), Color.GREEN, null, Color.GREEN);
		colMeters.add(meterInterval);

		Range intervaloTotal = new Range(0, gerenciamentoBean.getQuantidadeTotal());

		DefaultValueDataset dataset = new DefaultValueDataset();
		dataset.setValue(new Double(gerenciamentoBean.getQuantidadeAtrasadas().intValue()));
		gerarRelatorioVelocimetro(dataset, intervaloTotal, colMeters, "", CITCorporeUtil.caminho_real_app + "/tempFiles/" + usuario.getIdUsuario() + "/", "IMG_GRF_INC_VC_" + usuario.getIdUsuario()
				+ ".png", 190, false);
		document.getElementById("divGrafico4").setInnerHTML(
				"<table><tr><td><img src='" + request.getContextPath() + "/tempFiles/" + usuario.getIdUsuario() + "/IMG_GRF_INC_VC_" + usuario.getIdUsuario() + ".png' border='0'/></td></tr></table>");
	}

	public static void gerarRelatorioVelocimetro(DefaultValueDataset dataset, Range intervaloTotal, ArrayList intervalos, String titulo, String caminho, String nomeArquivo, int angulo,
			boolean legendaGrafico) {
		int width = 180;
		int height = 180;

		MeterPlot plot1 = new MeterPlot();
		// units
		plot1.setUnits("");
		// range
		plot1.setRange(intervaloTotal);
		for (int i = 0; i < intervalos.size(); i++) {
			plot1.addInterval((MeterInterval) intervalos.get(i));
		}
		plot1.setDialShape(DialShape.CHORD);
		// dial background paint
		plot1.setDialBackgroundPaint(Color.white);
		// needle paint
		plot1.setNeedlePaint(Color.black);
		// value font
		plot1.setValueFont(new Font("Serif", Font.PLAIN, 12));
		// value paint
		plot1.setValuePaint(Color.black);
		// tick label type
		// plot1.setTickLabelType(MeterPlot.NO_LABELS);
		// tick label font
		plot1.setTickLabelFont(new Font("Serif", Font.PLAIN, 12));
		// tick label format
		plot1.setTickLabelFormat(new DecimalFormat("0"));
		// meter angle
		plot1.setMeterAngle(angulo);

		// DefaultValueDataset ds;
		// ds = new DefaultValueDataset();
		// ds.setValue(dado);
		plot1.setDataset(dataset);
		JFreeChart chart = new JFreeChart(titulo, plot1);
		File arquivo = new File(caminho);
		if (!arquivo.exists())
			arquivo.mkdirs();

		arquivo = new File(caminho + nomeArquivo);
		if (arquivo.exists()) {
			arquivo.delete();
		}

		try {
			ChartUtilities.saveChartAsPNG(arquivo, chart, width, height);
			// System.out.println("Gráfico Pizza gerado com sucesso em: \n\t" + caminho + nomeArquivo);

		} catch (Exception e) {
			System.err.println("Problemas durante a criação do Gráfico XY: " + e.getMessage());
		}
	}

	public void restoreSolicitacaoServico(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		HTMLForm form = document.getForm("form");
		form.clear();
		GerenciamentoServicosDTO gerenciamentoServicosDTO = (GerenciamentoServicosDTO) document.getBean();
		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		SolicitacaoServicoDTO solicitacaoServicoDto = new SolicitacaoServicoDTO();
		solicitacaoServicoDto = solicitacaoServicoService.restoreAll(gerenciamentoServicosDTO.getIdSolicitacaoServicoDescricao());
		gerenciamentoServicosDTO.setDescricaoSolicitacaoVisualizar(solicitacaoServicoDto.getDescricaoSemFormatacao());
		form.setValues(gerenciamentoServicosDTO);
	}
}
