package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import com.gargoylesoftware.htmlunit.util.StringUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.bean.CidadesDTO;
import br.com.centralit.citcorpore.bean.ClienteDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.ContratosUnidadesDTO;
import br.com.centralit.citcorpore.bean.EnderecoDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.LocalidadeDTO;
import br.com.centralit.citcorpore.bean.LocalidadeUnidadeDTO;
import br.com.centralit.citcorpore.bean.PaisDTO;
import br.com.centralit.citcorpore.bean.TipoUnidadeDTO;
import br.com.centralit.citcorpore.bean.UfDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UnidadesAccServicosDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CidadesService;
import br.com.centralit.citcorpore.negocio.ClienteService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.ContratosUnidadesService;
import br.com.centralit.citcorpore.negocio.EnderecoService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.negocio.LocalidadeService;
import br.com.centralit.citcorpore.negocio.LocalidadeUnidadeService;
import br.com.centralit.citcorpore.negocio.PaisServico;
import br.com.centralit.citcorpore.negocio.TipoUnidadeService;
import br.com.centralit.citcorpore.negocio.UfService;
import br.com.centralit.citcorpore.negocio.UnidadeService;
import br.com.centralit.citcorpore.negocio.UnidadesAccServicosService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
public class Unidade extends AjaxFormAction {

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		preencherComboTipoUnidade(document, request, response);
		preencherComboUnidadePai(document, request, response);

		document.getElementById("divListaContratos").setVisible(false);
		String UNIDADE_VINC_CONTRATOS = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.UNIDADE_VINC_CONTRATOS, "N");
		if (UNIDADE_VINC_CONTRATOS == null) {
			UNIDADE_VINC_CONTRATOS = "N";
		}
		if (UNIDADE_VINC_CONTRATOS.equalsIgnoreCase("S")) {
			ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
			ClienteService clienteService = (ClienteService) ServiceLocator.getInstance().getService(ClienteService.class, null);
			FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
			Collection colContratos = contratoService.list();
			String bufferContratos = "<b>" + UtilI18N.internacionaliza(request, "citcorpore.comum.acessoAosContratos") + ":</b> <br><table>";
			if (colContratos != null) {
				for (Iterator it = colContratos.iterator(); it.hasNext();) {
					ContratoDTO contratoDto = (ContratoDTO) it.next();
					if (contratoDto.getDeleted() == null || !contratoDto.getDeleted().equalsIgnoreCase("y")) {
						String nomeCliente = "";
						String nomeForn = "";
						ClienteDTO clienteDto = new ClienteDTO();
						clienteDto.setIdCliente(contratoDto.getIdCliente());
						clienteDto = (ClienteDTO) clienteService.restore(clienteDto);
						if (clienteDto != null) {
							nomeCliente = clienteDto.getNomeRazaoSocial();
						}
						FornecedorDTO fornecedorDto = new FornecedorDTO();
						fornecedorDto.setIdFornecedor(contratoDto.getIdFornecedor());
						fornecedorDto = (FornecedorDTO) fornecedorService.restore(fornecedorDto);
						if (fornecedorDto != null) {
							nomeForn = fornecedorDto.getRazaoSocial();
						}

						String situacao = "";
						if (contratoDto.getSituacao().equalsIgnoreCase("A")) {
							situacao = UtilI18N.internacionaliza(request, "citcorpore.comum.situacaoAtiva");
						}
						if (contratoDto.getSituacao().equalsIgnoreCase("C")) {
							situacao = UtilI18N.internacionaliza(request, "citcorpore.comum.situacaoCancelado");
						}
						if (contratoDto.getSituacao().equalsIgnoreCase("F")) {
							situacao = UtilI18N.internacionaliza(request, "citcorpore.comum.situacaoFinalizado");
						}
						if (contratoDto.getSituacao().equalsIgnoreCase("P")) {
							situacao = UtilI18N.internacionaliza(request, "citcorpore.comum.situacaoParalisado");
						}

						String checked = "";
						// if (this.getEmpregadoBean().getIdContrato() != null){
						// for(int i = 0; i < this.getEmpregadoBean().getIdContrato().length; i++){
						// if (this.getEmpregadoBean().getIdContrato()[i].intValue() == contratoDto.getIdContrato().intValue()){
						// checked = " checked=checked ";
						// break;
						// }
						// }
						// }

						bufferContratos += "<tr>";
						bufferContratos += "<td>";
						bufferContratos += "<input type='checkbox' name='idContrato' id='idContrato_" + contratoDto.getIdContrato() + "' value='0" + contratoDto.getIdContrato() + "' " + checked
								+ "/> Número: " + contratoDto.getNumero() + " de " + UtilDatas.dateToSTR(contratoDto.getDataContrato()) + " (" + nomeCliente + " - " + nomeForn + ") - " + situacao;
						bufferContratos += "</td>";
						bufferContratos += "</tr>";
					}
				}
			}
			bufferContratos += "</table>";
			document.getElementById("fldListaContratos").setInnerHTML(bufferContratos);
			document.getElementById("divListaContratos").setVisible(true);
		}

		document.focusInFirstActivateField(null);

		this.preencherComboPais(document, request, response);
	}

	/**
	 * Preenche a combo de 'TipoUnidade' do formulário HTML com base na lista recuperada do banco. Obs.: Este preenchimento disconsidera itens com data final, ou seja, inativos. DEVE haver uma combo
	 * com id='idTipoUnidade' no documento HTML. Esse elemento será recuperado pelo framework e o tratamento começa a partir dai.
	 */
	public void preencherComboTipoUnidade(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		TipoUnidadeService tipoUnidadeService = (TipoUnidadeService) ServiceLocator.getInstance().getService(TipoUnidadeService.class, null);
		HTMLSelect comboTipoUnidade = (HTMLSelect) document.getSelectById("idTipoUnidade");
		ArrayList<TipoUnidadeDTO> tipos = (ArrayList) tipoUnidadeService.list();

		inicializaCombo(comboTipoUnidade, request);
		for (TipoUnidadeDTO tipo : tipos)
			if (tipo.getDataFim() == null)
				comboTipoUnidade.addOption(tipo.getIdTipoUnidade().toString(), Util.tratarAspasSimples(Util.retiraBarraInvertida(tipo.getNomeTipoUnidade().trim())));
	}

	/**
	 * Preenche a combo de 'UnidadePai' do formulário HTML com base na lista recuperada do banco. Obs.: Este preenchimento disconsidera itens com data final, ou seja, inativos. Obs2.: Ele recupera o
	 * getBean() para verificar se há um item ativo, se houver significa que houve restore, nesse caso, ao preencher a lista, ele disconsidera o item trazido do banco que for igual ao item ativo para
	 * evitar que o usuário possa cadastrar uma unidade sendo unidadePai dela mesma. DEVE haver uma combo com id='idTipoUnidade' no documento HTML. Esse elemento será recuperado pelo framework e o
	 * tratamento começa a partir dai.
	 */
	public void preencherComboUnidadePai(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
		HTMLSelect comboUnidadePai = (HTMLSelect) document.getSelectById("idUnidadePai");
		ArrayList<UnidadeDTO> unidades = (ArrayList) unidadeService.listHierarquia();

		inicializaCombo(comboUnidadePai, request);

		// getBean para saber se já existe um bean carregado (em caso de
		// restore)
		UnidadeDTO unidadeRestore = (UnidadeDTO) document.getBean();

		for (UnidadeDTO unidade : unidades) {
			// se existir dataFim, está inativo, então não entra na combo.
			if (unidade.getDataFim() == null) {
				/*
				 * se houver um bean carregado(restore), comparo se o item da lista do banco Ã© igual a esse bean, se for ele não pode ser adicionado Ã  lista, se não o usuário poderá colocar a
				 * unidade como pai dela mesma.
				 */
				if (unidadeRestore.getIdUnidade() == null || unidadeRestore.getIdUnidade().compareTo(unidade.getIdUnidade()) != 0) {
					comboUnidadePai.addOption(unidade.getIdUnidade().toString(), StringEscapeUtils.escapeJavaScript(unidade.getNomeNivel()));
				}
			}

		}

	}

	/**
	 * Executa uma inicialização padrão para as combos. Basicamente deleta todas as opçÃµes, caso haja, e insere aprimeira linha com o valor "-- Selecione --".
	 * 
	 * @param componenteCombo
	 *            Componente o qual deseja inicializar com as configuraçÃµes citadas acima.
	 */
	private void inicializaCombo(HTMLSelect componenteCombo, HttpServletRequest request) {
		componenteCombo.removeAllOptions();
		componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// Obtendo o objeto contendo os dados informados no formulários.
		UnidadeDTO unidadeDTO = (UnidadeDTO) document.getBean();

		// Obtendo o serviço.
		UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);

		// Verificando se o DTO e o serviço existem.
		if (unidadeDTO != null && unidadeService != null) {

			unidadeDTO.setServicos(getUnidadesAccServicos().deserealizaObjetosDoRequest(request));

			unidadeDTO.setListaDeLocalidade((ArrayList<LocalidadeUnidadeDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(LocalidadeUnidadeDTO.class, "localidadesSerializadas",
					request));

			// Verificando se a unidade já existe.
			if (unidadeService.jaExisteUnidadeComMesmoNome(unidadeDTO)) {

				// Notificando o usuário que a unidade já existe.
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));

				return;
			}

			// Inserindo.
			if (unidadeDTO.getIdUnidade() == null) {

				unidadeDTO.setIdEmpresa(WebUtil.getIdEmpresa(request));

				unidadeService.create(unidadeDTO);

				document.alert(UtilI18N.internacionaliza(request, "MSG05"));

			} else { // Atualizando.

				unidadeService.update(unidadeDTO);

				document.alert(UtilI18N.internacionaliza(request, "MSG06"));
			}

			CITCorporeUtil.limparFormulario(document);

			document.executeScript("ocultaGrid()");

			document.executeScript("ocultaGridLocalidade()");
		}
		
		document.executeScript("deleteAllRowsLocalidade()");
		document.executeScript("limpar_LOOKUP_UNIDADE()");
	}

	public void listaContrato(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UnidadeDTO unidadeDTO = (UnidadeDTO) document.getBean();
		String UNIDADE_VINC_CONTRATOS = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.UNIDADE_VINC_CONTRATOS, "N");
		if (UNIDADE_VINC_CONTRATOS == null) {
			UNIDADE_VINC_CONTRATOS = "N";
		} else if (UNIDADE_VINC_CONTRATOS.equalsIgnoreCase("S")) {
			if (unidadeDTO.getIdUnidadePai() != null) {
				document.getElementById("divListaContratos").setVisible(false);
			} else {
				document.getElementById("divListaContratos").setVisible(true);
			}
		} else
			return;
	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		UnidadeDTO unidade = (UnidadeDTO) document.getBean();
		UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
		LocalidadeDTO localidadeDTO = new LocalidadeDTO();
		EnderecoDTO enderecoDto = new EnderecoDTO();
		LocalidadeService localidadeService = (LocalidadeService) ServiceLocator.getInstance().getService(LocalidadeService.class, null);
		LocalidadeUnidadeService localidadeUnidadeService = (LocalidadeUnidadeService) ServiceLocator.getInstance().getService(LocalidadeUnidadeService.class, null);
		EnderecoService enderecoService = (EnderecoService) ServiceLocator.getInstance().getService(EnderecoService.class, null);

		unidade = (UnidadeDTO) unidadeService.restore(unidade);

		Collection<LocalidadeUnidadeDTO> listaIdlocalidade = new ArrayList<LocalidadeUnidadeDTO>();

		if (unidade != null) {

			if (unidade.getIdEndereco() != null) {
				enderecoDto.setIdEndereco(unidade.getIdEndereco());
				enderecoDto = (EnderecoDTO) enderecoService.restore(enderecoDto);
			}

			listaIdlocalidade = localidadeUnidadeService.listaIdLocalidades(unidade.getIdUnidade());
		}

		this.preencherComboTipoUnidade(document, request, response);
		this.preencherComboUnidadePai(document, request, response);
		this.preencherComboPais(document, request, response);
		this.preencherComboUfs(document, request, response);
		this.preencherComboCidade(document, request, response);

		document.executeScript("deleteAllRows()");
		document.executeScript("deleteAllRowsLocalidade()");
		HTMLForm form = CITCorporeUtil.limparFormulario(document);
		document.getForm("form").setValues(enderecoDto);
		if(unidade != null){
			form.setValues(unidade);
		}

		if (unidade != null) {

			unidadeService.restaurarGridServicos(document, getUnidadesAccServicos().consultarServicosAtivosPorUnidade(unidade.getIdUnidade()));

			if (unidade.getIdUnidadePai() == null) {
				String UNIDADE_VINC_CONTRATOS = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.UNIDADE_VINC_CONTRATOS, "N");
				if (UNIDADE_VINC_CONTRATOS == null) {
					UNIDADE_VINC_CONTRATOS = "N";
				}
				if (UNIDADE_VINC_CONTRATOS.equalsIgnoreCase("S")) {
					ContratosUnidadesService contratosUnidadesService = (ContratosUnidadesService) ServiceLocator.getInstance().getService(ContratosUnidadesService.class, null);
					Collection col = contratosUnidadesService.findByIdUnidade(unidade.getIdUnidade());

					if (col != null && col.size() > 0) {
						for (Iterator it = col.iterator(); it.hasNext();) {

							ContratosUnidadesDTO contratosUnidadesDTO = (ContratosUnidadesDTO) it.next();
							document.getCheckboxById("idContrato_" + contratosUnidadesDTO.getIdContrato()).setValue("0" + contratosUnidadesDTO.getIdContrato());
						}
					}
				}
				document.getElementById("divListaContratos").setVisible(true);
			} else {
				document.getElementById("divListaContratos").setVisible(false);
			}

		}

		if (listaIdlocalidade != null && !listaIdlocalidade.isEmpty()) {
			for (LocalidadeUnidadeDTO localidadeUnidadeDto : listaIdlocalidade) {
				if (localidadeUnidadeDto.getIdLocalidade() != null) {
					localidadeDTO.setIdLocalidade(localidadeUnidadeDto.getIdLocalidade());
					localidadeDTO = (LocalidadeDTO) localidadeService.restore(localidadeDTO);
					document.executeScript("addLinhaTabelaLocalidade(" + localidadeDTO.getIdLocalidade() + ", '" + StringEscapeUtils.escapeJavaScript(localidadeDTO.getNomeLocalidade()) + "', " + false + ");");
				}
				document.executeScript("exibeGridLocalidade()");
			}

		}
	}

	/**
	 * Metodo para exclusão lógica de Unidade
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UnidadeDTO unidade = (UnidadeDTO) document.getBean();
		UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, WebUtil.getUsuarioSistema(request));
		unidade.setListaDeLocalidade((ArrayList<LocalidadeUnidadeDTO>) br.com.citframework.util.WebUtil
				.deserializeCollectionFromRequest(LocalidadeUnidadeDTO.class, "localidadesSerializadas", request));
		if (unidade.getIdUnidade() != null && unidade.getIdUnidade() != 0) {
			unidadeService.deletarUnidade(unidade, document, request);
			document.executeScript("deleteAllRows()");
			document.executeScript("deleteAllRowsLocalidade()");
			HTMLForm form = document.getForm("form");
			form.clear();
		}

		document.executeScript("limpar_LOOKUP_UNIDADE()");
	}

	public Class getBeanClass() {
		return UnidadeDTO.class;
	}

	/**
	 * Método para excluir uma associação de unidade com serviços
	 * 
	 * @author rodrigo.oliveira
	 * 
	 */
	public void excluirAssociacaoServico(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		UnidadeDTO unidadeDTO = (UnidadeDTO) document.getBean();

		Integer idServico = !request.getParameter("servicoSerializado").isEmpty() ? Integer.parseInt(request.getParameter("servicoSerializado")) : null;

		UnidadesAccServicosDTO unidadeAccServicosDTO = new UnidadesAccServicosDTO();

		unidadeAccServicosDTO.setIdServico(idServico);

		// unidadeAccServicosDTO = (UnidadesAccServicosDTO) this.getUnidadesAccServicos().restore(unidadeAccServicosDTO);

		if (idServico != null && idServico != 0) {
			this.getUnidadesAccServicos().excluirAssociacaoServicosUnidade(unidadeDTO.getIdUnidade(), idServico);
			document.alert(UtilI18N.internacionaliza(request, "MSG07"));
		}

	}

	/**
	 * Retorna o service para criar a relação de unidade com serviços
	 * 
	 * @author rodrigo.oliveira
	 */
	public UnidadesAccServicosService getUnidadesAccServicos() throws ServiceException, Exception {

		return (UnidadesAccServicosService) ServiceLocator.getInstance().getService(UnidadesAccServicosService.class, null);

	}

	/**
	 * Preenche combo de Pais.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void preencherComboPais(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		UnidadeDTO unidadeDTO = (UnidadeDTO) document.getBean();

		PaisServico paisServico = (PaisServico) ServiceLocator.getInstance().getService(PaisServico.class, null);

		HTMLSelect comboPais = (HTMLSelect) document.getSelectById("idPais");

		ArrayList<PaisDTO> listPais = (ArrayList) paisServico.list();

		this.inicializaCombo(comboPais, request);

		if (listPais != null) {
			for (PaisDTO paisDto : listPais) {

				comboPais.addOption(paisDto.getIdPais().toString(), Util.tratarAspasSimples(Util.retiraBarraInvertida(paisDto.getNomePais())));

			}

		}

	}

	/**
	 * Preenche combo de Ufs.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void preencherComboUfs(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		UnidadeDTO unidadeDTO = (UnidadeDTO) document.getBean();

		EnderecoDTO enderecoDto = new EnderecoDTO();

		UnidadeDTO unidade = new UnidadeDTO();

		UfDTO ufDto = new UfDTO();

		UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);

		EnderecoService enderecoService = (EnderecoService) ServiceLocator.getInstance().getService(EnderecoService.class, null);

		UfService ufService = (UfService) ServiceLocator.getInstance().getService(UfService.class, null);

		if (unidadeDTO.getIdUnidade() != null) {

			unidade = (UnidadeDTO) unidadeService.restore(unidadeDTO);

		}

		if (unidade.getIdEndereco() != null) {
			enderecoDto.setIdEndereco(unidade.getIdEndereco());
			enderecoDto = (EnderecoDTO) enderecoService.restore(enderecoDto);
		}

		if (unidadeDTO.getIdPais() != null) {
			ufDto.setIdPais(unidadeDTO.getIdPais());
		} else {
			if (enderecoDto.getIdPais() != null) {
				ufDto.setIdPais(enderecoDto.getIdPais());
			}
		}

		HTMLSelect comboUfs = (HTMLSelect) document.getSelectById("idUf");

		ArrayList<UfDTO> listUfs = (ArrayList) ufService.listByIdPais(ufDto);

		this.inicializaCombo(comboUfs, request);

		if (listUfs != null) {
			for (UfDTO uf : listUfs) {

				comboUfs.addOption(uf.getIdUf().toString(), uf.getNomeUf());

			}
		}

	}

	/**
	 * Preenche combo de Cidade.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void preencherComboCidade(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		UnidadeDTO unidadeDTO = (UnidadeDTO) document.getBean();

		EnderecoDTO enderecoDto = new EnderecoDTO();

		UnidadeDTO unidade = new UnidadeDTO();

		CidadesDTO cidadeDto = new CidadesDTO();

		CidadesService cidadesService = (CidadesService) ServiceLocator.getInstance().getService(CidadesService.class, null);

		EnderecoService enderecoService = (EnderecoService) ServiceLocator.getInstance().getService(EnderecoService.class, null);

		UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);

		if (unidadeDTO.getIdUnidade() != null) {

			unidade = (UnidadeDTO) unidadeService.restore(unidadeDTO);

		}

		if (unidade.getIdEndereco() != null) {
			enderecoDto.setIdEndereco(unidade.getIdEndereco());
			enderecoDto = (EnderecoDTO) enderecoService.restore(enderecoDto);
		}

		if (unidadeDTO.getIdUf() != null) {
			cidadeDto.setIdUf(unidadeDTO.getIdUf());
		} else {
			if (enderecoDto.getIdUf() != null) {
				cidadeDto.setIdUf(enderecoDto.getIdUf());
			}
		}
		HTMLSelect comboCidade = (HTMLSelect) document.getSelectById("idCidade");

		ArrayList<CidadesDTO> listCidade = (ArrayList) cidadesService.listByIdCidades(cidadeDto);

		this.inicializaCombo(comboCidade, request);
		if (listCidade != null) {
			for (CidadesDTO cidade : listCidade) {

				comboCidade.addOption(cidade.getIdCidade().toString(), cidade.getNomeCidade());

			}
		}

	}

}
