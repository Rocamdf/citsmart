package br.com.centralit.citcorpore.ajaxForms;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.AtividadesOSDTO;
import br.com.centralit.citcorpore.bean.AtividadesServicoContratoDTO;
import br.com.centralit.citcorpore.bean.CalendarioDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.JornadaTrabalhoDTO;
import br.com.centralit.citcorpore.bean.OSDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoGrupoDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.ContratoDao;
import br.com.centralit.citcorpore.negocio.AtividadesOSService;
import br.com.centralit.citcorpore.negocio.AtividadesServicoContratoService;
import br.com.centralit.citcorpore.negocio.CalendarioServiceEjb;
import br.com.centralit.citcorpore.negocio.GrupoEmpregadoService;
import br.com.centralit.citcorpore.negocio.OSService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoGrupoService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoSituacaoOSService;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.negocio.VinculaOsIncidenteService;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.comparacao.ObjectSimpleComparator;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;

@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class Os extends AjaxFormAction {

	public Class getBeanClass() {
		return OSDTO.class;
	}

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert("Sessão expirada! Favor efetuar logon novamente!");
			return;
		}
		OSDTO os = (OSDTO) document.getBean();

		HTMLSelect idContrato = (HTMLSelect) document.getSelectById("idServicoContrato");
		ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
		Collection colServicosDoContrato = servicoContratoService.findByIdContrato(os.getIdContrato());
		idContrato.removeAllOptions();
		idContrato.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecioneServico"));
		List colFinal = new ArrayList();
		if (colServicosDoContrato != null) {
			for (Iterator it = colServicosDoContrato.iterator(); it.hasNext();) {
				ServicoContratoDTO servicoContratoDTO = (ServicoContratoDTO) it.next();
				if (servicoContratoDTO.getDeleted() == null || servicoContratoDTO.getDeleted().equalsIgnoreCase("n")) {
					if (servicoContratoDTO.getDataFim() == null || servicoContratoDTO.getDataFim().after(UtilDatas.getDataAtual())) {
						ServicoDTO servicoDto = new ServicoDTO();
						servicoDto.setIdServico(servicoContratoDTO.getIdServico());
						servicoDto = (ServicoDTO) servicoService.restore(servicoDto);
						if (servicoDto != null) {
							String sigla = servicoDto.getSiglaAbrev();
							String nomeServico = servicoDto.getNomeServico();
							if (sigla != null && nomeServico != null) {
								sigla = sigla.trim();
								nomeServico = nomeServico.trim();
								if (!sigla.equals("")) {
									servicoContratoDTO.setNomeServico(sigla + " - " + nomeServico);
								} else {
									servicoContratoDTO.setNomeServico(nomeServico);
								}
							} else {
								servicoContratoDTO.setNomeServico(nomeServico);
							}
							// idContrato.addOption("" + servicoContratoDTO.getIdServicoContrato(), servicoDto.getNomeServico());
							if (servicoDto.getIdTipoDemandaServico().intValue() == 2) {
								colFinal.add(servicoContratoDTO);
							}
						}
					}
				}
			}
		}

		Collections.sort(colFinal, new ObjectSimpleComparator("getNomeServico", ObjectSimpleComparator.ASC));
		for (Iterator it = colFinal.iterator(); it.hasNext();) {
			ServicoContratoDTO servicoContratoDTO = (ServicoContratoDTO) it.next();
			idContrato.addOption("" + servicoContratoDTO.getIdServicoContrato(), servicoContratoDTO.getNomeServico());
		}
		
		PerfilAcessoSituacaoOSService perfilAcessoSituacaoOSService = (PerfilAcessoSituacaoOSService) ServiceLocator.getInstance().getService(PerfilAcessoSituacaoOSService.class, null);
		GrupoEmpregadoService grupoEmpregadoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);
		PerfilAcessoGrupoService perfilAcessoGrupoService = (PerfilAcessoGrupoService) ServiceLocator.getInstance().getService(PerfilAcessoGrupoService.class, null);
		
		Collection colSituacoesPermitidasFinal = new ArrayList();
		Collection<GrupoEmpregadoDTO> colGruposUsuario = null;
		
		if(usuario != null){
			//Retorna as permissões do usuário
			colSituacoesPermitidasFinal = perfilAcessoSituacaoOSService.getSituacoesOSPermitidasByUsuario(usuario);
			
			//Retorna os grupos do usuário
			colGruposUsuario = grupoEmpregadoService.findByIdEmpregado(usuario.getIdEmpregado());
			if(colGruposUsuario != null){
				Collection<Integer> colSituacoesPermitidasTemp = null;
				for (GrupoEmpregadoDTO grupoEmpregadoDTO : colGruposUsuario) {
					PerfilAcessoGrupoDTO perfilAcessoGrupoDTO = new PerfilAcessoGrupoDTO();
					perfilAcessoGrupoDTO.setIdGrupo(grupoEmpregadoDTO.getIdGrupo());
					perfilAcessoGrupoDTO = perfilAcessoGrupoService.listByIdGrupo(perfilAcessoGrupoDTO);
					colSituacoesPermitidasTemp = perfilAcessoSituacaoOSService.getSituacoesOSPermitidasByGrupo(perfilAcessoGrupoDTO);
					//Percorre temporário e verifica se já existe na lista final, se não existir adiciona
					if(colSituacoesPermitidasTemp != null){
						for (Integer object : colSituacoesPermitidasTemp) {
							if(colSituacoesPermitidasFinal == null){
								colSituacoesPermitidasFinal = new ArrayList();
								colSituacoesPermitidasFinal.add(object);
							}else if(!colSituacoesPermitidasFinal.contains(object)){
								colSituacoesPermitidasFinal.add(object);
							}
						}
					}
				}
			}
		}
		
		generateComboSituacoes(document, request, colSituacoesPermitidasFinal);
		
		lockUnlockFields(document);
		
		document.focusInFirstActivateField(null);
		
		if (os.getIdOS() != null) {
			restore(document, request, response);
		}
		
		if (colFinal.isEmpty()) {
			document.alert(UtilI18N.internacionaliza(request, "os.naoHaServicosTipoDemandaOrdemServico"));
		}
		
	}

	private void lockAllFields(DocumentHTML document) throws Exception {

		document.getElementById("dataInicio").setReadonly(true);
		document.getElementById("dataFim").setReadonly(true);
		document.getElementById("idServicoContrato").setReadonly(true);
		document.getElementById("numero").setReadonly(true);
		document.getElementById("ano").setReadonly(true);
		document.getElementById("nomeAreaRequisitante").setReadonly(true);

		document.getSelectById("situacaoOS").setDisabled(false);
		document.getElementById("btnGravar").setDisabled(false);

	}

	private void lockUnlockFields(DocumentHTML document) throws Exception {
		document.getForm("form").lockForm();

		document.getSelectById("situacaoOS").setDisabled(false);
		document.getElementById("btnGravar").setDisabled(false);
		document.getElementById("idOS").setDisabled(false);
		document.getElementById("idContrato").setDisabled(false);
		document.getElementById("idServicoContrato").setDisabled(false);
		document.getElementById("colItens_Serialize").setDisabled(false);
		document.getElementById("dataInicio").setDisabled(false);
		document.getElementById("dataFim").setDisabled(false);
		document.getElementById("numero").setDisabled(false);
		document.getElementById("ano").setDisabled(false);
		document.getElementById("nomeAreaRequisitante").setDisabled(false);
	}

	public void generateComboSituacoes(DocumentHTML document, HttpServletRequest request, Collection colSituacoesPermitidas) throws Exception {
		OSDTO os = (OSDTO) document.getBean();
		OSService osService = (OSService) ServiceLocator.getInstance().getService(OSService.class, null);

		OSDTO oSDTO = null;
		if (os.getIdOS() != null) {
			oSDTO = (OSDTO) osService.restore(os);
		}

		document.getSelectById("situacaoOS").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

		if (colSituacoesPermitidas == null) {
			return;
		}

		for (Iterator it = colSituacoesPermitidas.iterator(); it.hasNext();) {
			Integer situacao = (Integer) it.next();

			if (oSDTO != null) {
				if (oSDTO.getIdOSPai() != null) {
					this.setaComboSituacaoExecucao(document, situacao, request);
				} else {
					this.setaComboSituacaoCriacao(document, situacao, request);
				}
			} else {
				this.setaComboSituacaoCriacao(document, situacao, request);
			}
		}
	}

	public void setaComboSituacaoCriacao(DocumentHTML document, Integer situacao, HttpServletRequest request) throws Exception {
		OSDTO osDto = new OSDTO();
		switch (situacao) {
		case 1:
			osDto.setSituacaoOS(situacao);
			document.getSelectById("situacaoOS").addOption("" + situacao.intValue(), UtilI18N.internacionaliza(request, "perfil.criacao"));
			break;
		case 2:
			osDto.setSituacaoOS(situacao);
			document.getSelectById("situacaoOS").addOption("" + situacao.intValue(), UtilI18N.internacionaliza(request, "perfil.solicitada"));
			break;
		case 3:
			osDto.setSituacaoOS(situacao);
			document.getSelectById("situacaoOS").addOption("" + situacao.intValue(), UtilI18N.internacionaliza(request, "perfil.autorizada"));
			break;
		case 4:
			osDto.setSituacaoOS(situacao);
			document.getSelectById("situacaoOS").addOption("" + situacao.intValue(), UtilI18N.internacionaliza(request, "perfil.aprovada"));
			break;
		case 5:
			break;
		case 6:
			break;
		case 7:
			osDto.setSituacaoOS(situacao);
			document.getSelectById("situacaoOS").addOption("" + situacao.intValue(), UtilI18N.internacionaliza(request, "perfil.cancelada"));
			break;
		case 8:
			break;
//		default:
//			osDto.setSituacaoOS(situacao);
//			document.getSelectById("situacaoOS").addOption("" + situacao.intValue(), osDto.getDescricaoSituacaoOS());
		}
	}

	public void setaComboSituacaoExecucao(DocumentHTML document, Integer situacao, HttpServletRequest request) throws Exception {
		OSDTO osDto = new OSDTO();
		if (situacao > 4 && situacao < 7) {
			if(situacao == 5){
				osDto.setSituacaoOS(situacao);
				document.getSelectById("situacaoOS").addOption("" + situacao.intValue(), UtilI18N.internacionaliza(request, "perfil.execucao"));
			} else if(situacao == 6){
				osDto.setSituacaoOS(situacao);
				document.getSelectById("situacaoOS").addOption("" + situacao.intValue(), UtilI18N.internacionaliza(request, "perfil.executada"));
			}
		}
	}

	public void atualizaGridOS(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		OSDTO os = (OSDTO) document.getBean();
		OSService osService = (OSService) ServiceLocator.getInstance().getService(OSService.class, null);
		OSDTO oSDTO = (OSDTO) osService.restore(os);
		HTMLTable tblOS = document.getTableById("tblOS");

		if (os.getSequenciaOS() == null) {
			tblOS.addRow(oSDTO, new String[] { "", "", "numero", "nomeAreaRequisitante", "demanda" }, new String[] { "idOS" }, "O.S já cadastrada!!", new String[] { "exibeIconesOS" }, null, null);
		} else {
			tblOS.updateRow(oSDTO, new String[] { "", "", "numero", "nomeAreaRequisitante", "demanda" }, new String[] { "idOS" }, "O.S já cadastrada!!", new String[] { "exibeIconesOS" }, null, null,
					os.getSequenciaOS());
		}
		document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblOS', 'tblOS');");
		document.executeScript("fecharOS();");
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		OSDTO os = (OSDTO) document.getBean();
		OSService osService = (OSService) ServiceLocator.getInstance().getService(OSService.class, null);

		Collection colItens = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(AtividadesOSDTO.class, "colItens_Serialize", request);

		Integer idContrato = -1;

		try {
			idContrato = Integer.parseInt((String) request.getSession(true).getAttribute("NUMERO_CONTRATO_EDICAO"));
		} catch (Exception e) {
		}
		if (idContrato == -1) {
			document.alert("Não foi possível identificar o contrato. Por favor, feche esta tela e faça logon novamente!");
			return;
		}

		os.setIdContrato(idContrato);
		os.setColItens(colItens);
		boolean bAlterar = false;

		if (os.getIdOS() == null || os.getIdOS().intValue() == 0) {
			osService.create(os);
		} else {
			// Se OS está sendo cancelada, verifica se existe RAs e os cancela se houver
			if (os.getSituacaoOS().equals(OSDTO.CANCELADA)) {
				document.executeScript("confirmaCancelamento();");
				return;
			}
			osService.update(os);
			bAlterar = true;
		}

		HTMLForm form = document.getForm("form");
		form.clear();

		document.executeScript("GRID_ITENS.deleteAllRows()");

		document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.OSGravadaSucesso"));
		document.executeScript("parent.atualizaOSs()");
		document.executeScript("parent.fecharVisao()");

	}

	public void cancelaOSeRAs(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		OSDTO os = (OSDTO) document.getBean();

		OSService osService = (OSService) ServiceLocator.getInstance().getService(OSService.class, null);
		VinculaOsIncidenteService vinculaOsIncidenteService = (VinculaOsIncidenteService) ServiceLocator.getInstance().getService(VinculaOsIncidenteService.class, null);
		
		osService.cancelaOsFilhas(os);
		osService.update(os);
		
		//Desfaz Vínculo de Incidente
		Collection<OSDTO> listaOsFilhas = osService.retornaSeExisteOSFilha(os);
		if(listaOsFilhas != null){
			for (OSDTO osdto : listaOsFilhas) {
				vinculaOsIncidenteService.deleteByIdOs(osdto.getIdOS());
			}
		}
		
		HTMLForm form = document.getForm("form");
		form.clear();

		document.executeScript("GRID_ITENS.deleteAllRows()");

		document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.OSGravadaSucesso"));
		document.executeScript("parent.atualizaOSs()");
		document.executeScript("parent.fecharVisao()");

	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		OSDTO os = (OSDTO) document.getBean();
		OSService osService = (OSService) ServiceLocator.getInstance().getService(OSService.class, null);
		AtividadesOSService atividadesOSService = (AtividadesOSService) ServiceLocator.getInstance().getService(AtividadesOSService.class, null);

		os = (OSDTO) osService.restore(os);

		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(os);

		document.executeScript("GRID_ITENS.deleteAllRows()");

		Collection col = null;

		double custoTotal = 0;

		if (os != null) {
			int i = 0;
			col = atividadesOSService.findByIdOS(os.getIdOS());
			if (col != null) {
				for (Iterator it = col.iterator(); it.hasNext();) {
					i++;
					AtividadesOSDTO atividadesOSDTO = (AtividadesOSDTO) it.next();
					document.executeScript("GRID_ITENS.addRow()");
					document.executeScript("seqSelecionada = NumberUtil.zerosAEsquerda(GRID_ITENS.getMaxIndex(),5)");
					String strQtde = "";
					String strFormula = atividadesOSDTO.getFormula();
					
					if (atividadesOSDTO.getCustoAtividade() != null) {
						strQtde = UtilFormatacao.formatDouble(atividadesOSDTO.getCustoAtividade(), 2);
					}
					String strId = "";
					if (atividadesOSDTO.getIdAtividadeServicoContrato() != null) {
						strId = atividadesOSDTO.getIdAtividadeServicoContrato().toString();
					}
					
					if (strQtde == null) {
						strQtde = "";
					}
					if (strId == null) {
						strId = "";
					}
					if (strFormula == null) {
						strFormula = "";
					}
					
					String strDet = atividadesOSDTO.getDescricaoAtividade();
					String strObs = atividadesOSDTO.getObsAtividade();
					if (strDet != null) {
						strDet = strDet.replaceAll("'", "");
					}else{
						strDet = "";
					}
					if (strObs != null) {
						strObs = strObs.replaceAll("'", "");
					}else{
						strObs = "";
					}
					if (atividadesOSDTO.getCustoAtividade() != null) {
						custoTotal = custoTotal + atividadesOSDTO.getCustoAtividade().doubleValue();
					}
					String contabilizar = atividadesOSDTO.getContabilizar();
					Integer idServicoContratoContabilInt = atividadesOSDTO.getIdServicoContratoContabil();
					String idServicoContratoContabil = "";
					
					if(contabilizar == null){
						contabilizar = "";
					}
					if(idServicoContratoContabilInt != null){
						idServicoContratoContabil = idServicoContratoContabilInt.toString();
					}
					if(idServicoContratoContabil == null){
						idServicoContratoContabil = "";
					}
					
					document.executeScript("setaRestoreItem('" + atividadesOSDTO.getComplexidade() + "'," + "'" + br.com.citframework.util.WebUtil.codificaEnter(strDet) + "'," + "'"
							+ br.com.citframework.util.WebUtil.codificaEnter(strObs) + "'," + "'" + strQtde + "'," + "'" + strFormula + "'," + "'" + strId + "'," + 
							"'" + contabilizar + "'," + "'" + idServicoContratoContabil + "'" + ")");
				}
			}
		}
		
		document.getElementById("custoTotal").setInnerHTML("<b>" + UtilFormatacao.formatDouble(custoTotal, 2) + "</b>");
		document.executeScript("preencheNumeracaoItens()");
		lockUnlockFields(document);
		
		if (os != null) {
			if (os.getSituacaoOS() != null && os.getSituacaoOS().equals(OSDTO.EM_CRIACAO)) {
				int temp = 0;
				if (col != null) {
					for (Iterator ite = col.iterator(); ite.hasNext();) {
						temp++;
						AtividadesOSDTO atividades = (AtividadesOSDTO) ite.next();
						String objHtml = "document.form.obs" + UtilFormatacao.formatInt(temp, "00000");
						document.executeScript(objHtml + ".onkeydown = true");
					}
				}
			} else {
				lockAllFields(document);
			}
		}
	}
	
	public void gravarRegistroExecucao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		OSDTO osDto = (OSDTO) document.getBean();
		document.executeScript("aguarde();");
		OSService osService = (OSService) ServiceLocator.getInstance().getService(OSService.class, null);
		OSDTO beanOs = new OSDTO();

		if (osDto != null) {
			if (osDto.getQuantidade().intValue() < 1) {
				document.alert("Quantidade não é um valor válido!");
				document.executeScript("fechar_aguarde();");
				return;
			}

			beanOs.setIdOS(osDto.getIdOSPai());
			beanOs = (OSDTO) osService.restore(beanOs);

			beanOs.setIdOS(null);
			beanOs.setDataFim(osDto.getDataFimExecucao());
			beanOs.setDataInicio(osDto.getDataInicioExecucao());
			beanOs.setQuantidade(osDto.getQuantidade());
			beanOs.setIdOSPai(osDto.getIdOSPai());
			beanOs.setSituacaoOS(new Integer(5));

			if (beanOs.getIdOSPai() != null) {
				osService.retornaAtividadeCadastradaByPai(beanOs);
			}

			try {
				osService.create(beanOs);
				document.executeScript("parent.atualizaOSs()");
				document.alert("R.A. gerado com sucesso!");
				document.executeScript("fechar_aguarde();");
				document.executeScript("$('#POPUP_REGISTRO_EXECUCAO').dialog('close');");
			} catch (Exception e) {
				document.alert(" Ocorreu uma erro ao gravar: Gerar R.A!");
				document.executeScript("fechar_aguarde();");
				document.executeScript("$('#POPUP_REGISTRO_EXECUCAO').dialog('close');");
			}
		}

	}

	public void restoreInfoServicoContrato(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		OSDTO os = (OSDTO) document.getBean();
		if (os.getIdServicoContrato() == null) {
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
		AtividadesServicoContratoService atividadesServicoContratoService = (AtividadesServicoContratoService) ServiceLocator.getInstance().getService(AtividadesServicoContratoService.class, null);

		ServicoContratoDTO servicoContratoDTO = new ServicoContratoDTO();
		servicoContratoDTO.setIdServicoContrato(os.getIdServicoContrato());
		servicoContratoDTO = (ServicoContratoDTO) servicoContratoService.restore(servicoContratoDTO);

		ServicoDTO servicoDto = new ServicoDTO();
		servicoDto.setIdServico(servicoContratoDTO.getIdServico());
		servicoDto = (ServicoDTO) servicoService.restore(servicoDto);

		HTMLForm form = document.getForm("form");
		form.clear();

		os.setAno(UtilDatas.getYear(UtilDatas.getDataAtual()));
		os.setObjetivo(servicoDto.getObjetivo());
		os.setDemanda(servicoDto.getNomeServico());
		os.setNomeAreaRequisitante(servicoContratoDTO.getAreaRequisitante());
		form.setValues(os);

		document.executeScript("GRID_ITENS.deleteAllRows()");
		Collection col = null;
		double custoTotal = 0;
		if (os != null) {
			int i = 0;
			col = atividadesServicoContratoService.obterAtividadesAtivasPorIdServicoContrato(os.getIdServicoContrato());
			if (col != null) {
				for (Iterator it = col.iterator(); it.hasNext();) {
					i++;
					AtividadesServicoContratoDTO atividadesServicoContratoDTO = (AtividadesServicoContratoDTO) it.next();
					document.executeScript("GRID_ITENS.addRow()");
					document.executeScript("seqSelecionada = NumberUtil.zerosAEsquerda(GRID_ITENS.getMaxIndex(),5)");
					
					String strDet = atividadesServicoContratoDTO.getDescricaoAtividade();
					String strObs = atividadesServicoContratoDTO.getObsAtividade();
					String strFormula = atividadesServicoContratoDTO.getFormula();
					String contabilizar = atividadesServicoContratoDTO.getContabilizar();
					Integer idServicoContratoContabilInt = atividadesServicoContratoDTO.getIdServicoContratoContabil();
					String strQtde = "";
					String idServicoContratoContabil = "";
					
					if(strDet == null){
						strDet = "";
					}
					
					if(strObs == null){
						strObs = "";
					}
					
					// Calcula custo atividade caso o período seja Dias úteis
					if (atividadesServicoContratoDTO.getPeriodo() != null && atividadesServicoContratoDTO.getPeriodo().trim().equals("4")) {
						Double custo = calculaCustoAtividade(atividadesServicoContratoDTO, os, servicoContratoDTO.getIdCalendario());
						atividadesServicoContratoDTO.setCustoAtividade(custo);
						strFormula = atividadesServicoContratoDTO.getFormula();
					}
					// Calcula custo atividade caso o período seja Dias corridos
					else if (atividadesServicoContratoDTO.getPeriodo() != null && atividadesServicoContratoDTO.getPeriodo().trim().equals("5")) {
						Double custo = calculaCustoAtividade(atividadesServicoContratoDTO, os);
						atividadesServicoContratoDTO.setCustoAtividade(custo);
						strFormula = atividadesServicoContratoDTO.getFormula();
					} 
					else {
						atividadesServicoContratoDTO.setCustoAtividade(atividadesServicoContratoService.calculaFormula(atividadesServicoContratoDTO));
					}
					
					
					if (atividadesServicoContratoDTO.getCustoAtividade() != null) {
						strQtde = UtilFormatacao.formatDouble(atividadesServicoContratoDTO.getCustoAtividade(), 2);
					}
					
					String strId = "";
					if (atividadesServicoContratoDTO.getIdAtividadeServicoContrato() != null) {
						strId = atividadesServicoContratoDTO.getIdAtividadeServicoContrato().toString();
					}
					
					if (strDet != null) {
						strDet = strDet.replaceAll("'", "");
					}else{
						strDet = "";
					}
					if (strObs != null) {
						strObs = strObs.replaceAll("'", "");
					}else{
						strObs = "";
					}
					if (strQtde == null) {
						strQtde = "";
					}
					if (strFormula == null) {
						strFormula = "";
					}
					if (strId == null) {
						strId = "";
					}
					if(contabilizar == null){
						contabilizar = "";
					}
					if(idServicoContratoContabilInt != null){
						idServicoContratoContabil = idServicoContratoContabilInt.toString();
					}
					if(idServicoContratoContabil == null){
						idServicoContratoContabil = "";
					}
					
					if (atividadesServicoContratoDTO.getCustoAtividade() != null) {
						custoTotal = custoTotal + atividadesServicoContratoDTO.getCustoAtividade().doubleValue();
					}

					document.executeScript("setaRestoreItem('" + atividadesServicoContratoDTO.getComplexidade().trim() + "'," + 
															"'" + br.com.citframework.util.WebUtil.codificaEnter(strDet) + "'," + 
															"'"	+ br.com.citframework.util.WebUtil.codificaEnter(strObs) + "'," + 
															"'" + strQtde + "'," + "'" + strFormula + "'," + "'" + strId + "'," + 
															"'" + contabilizar + "'," + "'" + idServicoContratoContabil + "'" +")");
				}

				document.executeScript("preencheNumeracaoItens()");

			}
		}

		lockUnlockFields(document);

		document.getElementById("custoTotal").setInnerHTML("<b>" + UtilFormatacao.formatDouble(custoTotal, 2) + "</b>");

		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();

		if (os.getSituacaoOS() == null) {
			int temp = 0;
			if (col != null) {
				for (Iterator ite = col.iterator(); ite.hasNext();) {
					temp++;
					AtividadesServicoContratoDTO atividades = (AtividadesServicoContratoDTO) ite.next();
					String objHtml = "document.form.obs" + UtilFormatacao.formatInt(temp, "00000");
					document.executeScript(objHtml + ".onkeydown = true");
				}
			}
		}
	}

	private Double calculaCustoAtividade(AtividadesServicoContratoDTO atividadesServicoContratoDTO, OSDTO os) throws Exception {

		Date dataInicio = os.getDataInicio();
		Date dataFim = os.getDataFim();
		Double hora = atividadesServicoContratoDTO.getHora();

		Integer difEmDias = UtilDatas.dataDiff(dataInicio, dataFim);

		String complexidade = atividadesServicoContratoDTO.getComplexidade().trim();
		Integer idServicoContrato = os.getIdServicoContrato();
		Double result = null;

		// Consultar valor da complexidade
		if (idServicoContrato != null && hora != null && complexidade != null && difEmDias != null) {
			ContratoDao contratoDao = new ContratoDao();
			Double valorComplex = contratoDao.consultaComplexidade(idServicoContrato, complexidade);
			// Calcula custo
			result = hora * valorComplex * difEmDias;

			// Preenche fórmula
			DecimalFormat formatacao = new DecimalFormat("0.00");
			String formula = "" + formatacao.format(atividadesServicoContratoDTO.getHora()) + " x " + atividadesServicoContratoDTO.getComplexidade() + " x " + difEmDias + " Dias corridos";
			atividadesServicoContratoDTO.setFormula(formula);
		} else {
			result = 0.0;
		}

		return result;
	}

	private Double calculaCustoAtividade(AtividadesServicoContratoDTO atividadesServicoContratoDTO, OSDTO os, Integer idCalendario) throws Exception {

		Date dataInicio = os.getDataInicio();
		Date dataFim = os.getDataFim();
		Double hora = atividadesServicoContratoDTO.getHora();

		Integer qntDiasUteis = contaDiasUteis(dataInicio, dataFim, idCalendario);

		String complexidade = atividadesServicoContratoDTO.getComplexidade().trim();
		Integer idServicoContrato = os.getIdServicoContrato();
		Double result = null;

		// Consultar valor da complexidade
		if (idServicoContrato != null && hora != null && complexidade != null && qntDiasUteis != null) {
			ContratoDao contratoDao = new ContratoDao();
			Double valorComplex = contratoDao.consultaComplexidade(idServicoContrato, complexidade);
			// Calcula custo
			result = hora * valorComplex * qntDiasUteis;

			// Preenche fórmula
			DecimalFormat formatacao = new DecimalFormat("0.00");
			String formula = "" + formatacao.format(atividadesServicoContratoDTO.getHora()) + " x " + atividadesServicoContratoDTO.getComplexidade() + " x " + qntDiasUteis + " Dias úteis";
			atividadesServicoContratoDTO.setFormula(formula);
		} else {
			result = 0.0;
		}

		return result;
	}

	private Integer contaDiasUteis(Date dataInicio, Date dataFim, Integer idCalendario) throws Exception {

		Integer totalDiasUteis = 0;
		Integer difEmDias = UtilDatas.dataDiff(dataInicio, dataFim);

		int index = 0;
		Date dtTemp = dataInicio;
		do {
			// Verifica se é dia útil (segunda a sexta)
			if (UtilDatas.verificaDiaUtil(dtTemp)) {
				// Se for dia útil verifica se o dia é ou não feriado
				CalendarioServiceEjb calendarioService = new CalendarioServiceEjb();

				CalendarioDTO calendarioDto = calendarioService.recuperaCalendario(idCalendario);
				JornadaTrabalhoDTO jornada = calendarioService.recuperaJornada(calendarioDto, new java.sql.Date(dtTemp.getTime()));

				if (jornada != null) {
					// Se não for feridao, incrementa quantidade de Dias úteis
					totalDiasUteis++;
				}
			}
			// Incrementa um dia na data
			dtTemp = UtilDatas.incrementaDiasEmData(dtTemp, 1);

			index++;

		} while (difEmDias >= index);

		return totalDiasUteis;
	}

}
