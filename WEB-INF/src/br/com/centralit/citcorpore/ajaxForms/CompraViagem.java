package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.bean.CidadesDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.ControleFinanceiroViagemDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.IntegranteViagemDTO;
import br.com.centralit.citcorpore.bean.ItemControleFinanceiroViagemDTO;
import br.com.centralit.citcorpore.bean.JustificativaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoViagemDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CentroResultadoService;
import br.com.centralit.citcorpore.negocio.CidadesService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.ControleFinanceiroViagemService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.ItemControleFinanceiroViagemService;
import br.com.centralit.citcorpore.negocio.JustificativaSolicitacaoService;
import br.com.centralit.citcorpore.negocio.MoedaService;
import br.com.centralit.citcorpore.negocio.ProjetoService;
import br.com.centralit.citcorpore.negocio.RequisicaoViagemService;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;

@SuppressWarnings({"rawtypes","unchecked"})
public class CompraViagem extends AjaxFormAction{

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		
		ControleFinanceiroViagemDTO controleFinanceiroViagemDto = (ControleFinanceiroViagemDTO) document.getBean();
		
		RequisicaoViagemDTO requisicaoViagemDto = new RequisicaoViagemDTO();
		
		if(controleFinanceiroViagemDto.getIdSolicitacaoServico()!=null){
			requisicaoViagemDto.setIdSolicitacaoServico(controleFinanceiroViagemDto.getIdSolicitacaoServico());
			requisicaoViagemDto.setIdContrato(controleFinanceiroViagemDto.getIdContrato());
		}
		
		this.preencherComboCentroResultado(document, request, response);
		this.preencherComboProjeto(document, request, response, requisicaoViagemDto);
		//this.preencherComboCidades(document, request, response);
		this.preencherComboJustificativa(document, request, response);
		this.preencherComboMoeda(document, request, response);
		
		document.executeScript("$('#divItens').hide()");
		document.executeScript("$('#divMotivo').hide()");
		
		if(requisicaoViagemDto.getIdSolicitacaoServico() != null ){
			this.restore(document, request, response,requisicaoViagemDto);
		}
		
	}
	
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response,RequisicaoViagemDTO requisicaoViagemDTO) throws ServiceException, Exception{
		
		RequisicaoViagemService reqViagemService = (RequisicaoViagemService) ServiceLocator.getInstance().getService(RequisicaoViagemService.class, null);
		ControleFinanceiroViagemService controleFinanceiroService = (ControleFinanceiroViagemService) ServiceLocator.getInstance().getService(ControleFinanceiroViagemService.class, null);
		ControleFinanceiroViagemDTO controleFinanceiroDto = null;
		
		if(requisicaoViagemDTO.getIdSolicitacaoServico()!=null){
			controleFinanceiroDto = controleFinanceiroService.buscarControleFinanceiroViagemPorIdSolicitacao(requisicaoViagemDTO.getIdSolicitacaoServico());
			requisicaoViagemDTO = (RequisicaoViagemDTO) reqViagemService.restore(requisicaoViagemDTO);
			if(requisicaoViagemDTO!=null){
				this.geraGridIntegrantes(document, request, response,requisicaoViagemDTO);
			}
			if(requisicaoViagemDTO != null){
				requisicaoViagemDTO.setNomeCidadeOrigem(this.recuperaCidade(requisicaoViagemDTO.getIdCidadeOrigem()));
				requisicaoViagemDTO.setNomeCidadeDestino(this.recuperaCidade(requisicaoViagemDTO.getIdCidadeDestino()));
			}
			if(controleFinanceiroDto != null){
				requisicaoViagemDTO.setObservacoes(controleFinanceiroDto.getObservacoes());
				requisicaoViagemDTO.setIdMoeda(controleFinanceiroDto.getIdMoeda());
				requisicaoViagemDTO.setIdControleFinanceiroViagem(controleFinanceiroDto.getIdControleFinanceiroViagem());
			}
		}
		HTMLForm form = document.getForm("form");
        form.clear();   
        form.setValues(requisicaoViagemDTO);
        
        document.getElementById("nomeCidadeOrigem").setDisabled(true);
        document.getElementById("nomeCidadeDestino").setDisabled(true);
        document.getElementById("dataInicioViagem").setDisabled(true);
        document.getElementById("dataFimViagem").setDisabled(true);
        document.getElementById("qtdeDias").setDisabled(true);
        document.getElementById("idCentroCusto").setDisabled(true);
        document.getElementById("idProjeto").setDisabled(true);
        document.getElementById("idMotivoViagem").setDisabled(true);
        document.getElementById("descricaoMotivo").setDisabled(true);
        document.getElementById("descricaoMotivo").setDisabled(true);
        document.getElementById("idMoeda").setDisabled(true);
		
	}
	
	public void geraGridIntegrantes(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, RequisicaoViagemDTO requisicaoViagemDTO) throws ServiceException, Exception{
		RequisicaoViagemService reqViagemService = (RequisicaoViagemService) ServiceLocator.getInstance().getService(RequisicaoViagemService.class, null);
		
		Collection<IntegranteViagemDTO> colIntegrantes =  reqViagemService.recuperaIntegrantesViagemBySolicitacao(requisicaoViagemDTO.getIdSolicitacaoServico());
		if(colIntegrantes != null){
			HTMLTable tblControleFinaceiro;
			tblControleFinaceiro = document.getTableById("tblControleFinaceiro");
			tblControleFinaceiro.deleteAllRows();
			tblControleFinaceiro.addRowsByCollection(colIntegrantes, new String[]{"","nome"}, null, null, new String[]{"gerarImg"}, "carregaItens", null);			
		}
		
	}
	
	@Override
	public Class getBeanClass() {
		return ControleFinanceiroViagemDTO.class;
	}
	
	
	/**
	 * Preenche a combo de 'Centro Resultado' do formulário HTML
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void preencherComboCentroResultado(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		CentroResultadoService centroResultadoService = (CentroResultadoService) ServiceLocator.getInstance().getService(CentroResultadoService.class, WebUtil.getUsuarioSistema(request));
        HTMLSelect idCentroCusto = (HTMLSelect) document.getSelectById("idCentroCusto");
        idCentroCusto.removeAllOptions();
        idCentroCusto.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
        Collection colCCusto = centroResultadoService.listPermiteRequisicaoProduto();
        if(colCCusto != null && !colCCusto.isEmpty()){
        	 idCentroCusto.addOptions(colCCusto, "idCentroResultado", "nomeHierarquizado", null);
        }
           
	}
	
	/**
	 * Preenche a combo de 'Projeto' do formulário HTML
	 * @param document
	 * @param request
	 * @param response
	 * @param requisicaoViagemDto
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void preencherComboProjeto(DocumentHTML document, HttpServletRequest request, HttpServletResponse response,RequisicaoViagemDTO requisicaoViagemDto) throws Exception{
		 HTMLSelect idProjeto = (HTMLSelect) document.getSelectById("idProjeto");
	        idProjeto.removeAllOptions();
	        idProjeto.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
	        if (requisicaoViagemDto.getIdContrato() != null) {
	            ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, WebUtil.getUsuarioSistema(request));
	            ContratoDTO contratoDto = new ContratoDTO();
	            contratoDto.setIdContrato(requisicaoViagemDto.getIdContrato());
	            contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
	            if (contratoDto != null) {
	                ProjetoService projetoService = (ProjetoService) ServiceLocator.getInstance().getService(ProjetoService.class, WebUtil.getUsuarioSistema(request));
	                Collection colProjetos = projetoService.listHierarquia(contratoDto.getIdCliente(), true);
	                if(colProjetos != null && !colProjetos.isEmpty()) 
	                    idProjeto.addOptions(colProjetos, "idProjeto", "nomeHierarquizado", null);
	            }
	        }
	}
	
	/**
	 * Preenche combo de 'Cidade Origem'.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void preencherComboCidades(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CidadesService cidadesService = (CidadesService) ServiceLocator.getInstance().getService(CidadesService.class, null);
		
		HTMLSelect comboCidadeOrigem = (HTMLSelect) document.getSelectById("idCidadeOrigem");
		HTMLSelect comboCidadeDestino = (HTMLSelect) document.getSelectById("idCidadeDestino");

		ArrayList<CidadesDTO> listCidade = (ArrayList) cidadesService.list();

		this.inicializaCombo(comboCidadeOrigem, request);
		this.inicializaCombo(comboCidadeDestino, request);
		if (listCidade != null) {
			comboCidadeOrigem.addOptions(listCidade, "idCidade", "nomeCidade", null);
			comboCidadeDestino.addOptions(listCidade, "idCidade", "nomeCidade", null);
		}
	}
	
	
	/**
	 * Preenche combo de 'justificativa solicitação'.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void preencherComboJustificativa(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JustificativaSolicitacaoService justificativaSolicitacaoService = (JustificativaSolicitacaoService)ServiceLocator.getInstance().getService(JustificativaSolicitacaoService.class, null);
		
		Collection<JustificativaSolicitacaoDTO> colJustificativas = justificativaSolicitacaoService.listAtivasParaViagem();
		
		HTMLSelect comboJustificativa = (HTMLSelect) document.getSelectById("idMotivoViagem");
		document.getSelectById("idMotivoViagem").removeAllOptions();
		inicializaCombo(comboJustificativa, request);
		if (colJustificativas != null){
			comboJustificativa.addOptions(colJustificativas, "idJustificativa", "descricaoJustificativa", null);
		}
	}
	
	
	/**
	 * Executa uma inicialização padrão para as combos. Basicamente deleta todas as opções, caso haja, e insere aprimeira linha com o valor "-- Selecione --".
	 * @param componenteCombo
	 * @param request
	 */
	private void inicializaCombo(HTMLSelect componenteCombo, HttpServletRequest request) {
		componenteCombo.removeAllOptions();
		componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
	}
	
	
	public void preencherComboMoeda(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		MoedaService moedaService = (MoedaService) ServiceLocator.getInstance().getService(MoedaService.class, null);
        HTMLSelect idMoeda = (HTMLSelect) document.getSelectById("idMoeda");
        idMoeda.removeAllOptions();
        idMoeda.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
        Collection colMoedas = moedaService.findAllAtivos();
        if(colMoedas != null && !colMoedas.isEmpty()){
        	idMoeda.addOptions(colMoedas, "idMoeda", "nomeMoeda", null);
        }
           
	}
	
	/**
	 * @param idCidade
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	public String recuperaCidade(Integer idCidade) throws Exception {
		CidadesDTO cidadeDto  = new CidadesDTO();
		CidadesService cidadesService = (CidadesService) ServiceLocator.getInstance().getService(CidadesService.class, null);
		if(idCidade !=null){
			cidadeDto.setIdCidade(idCidade);
			cidadeDto = (CidadesDTO) cidadesService.restore(cidadeDto);
			return cidadeDto.getNomeCidade();
		}
		return null;
	}
	
	
	/**
	 * Recupera itens com atributo 'adiantamento = n', e monta uma grid por usuario.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 */
	public void carregaTblItens(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception{
		document.executeScript("$('#divItens').hide()");
		ControleFinanceiroViagemDTO controleDto = (ControleFinanceiroViagemDTO) document.getBean();
		ItemControleFinanceiroViagemDTO itemControleFinanceiroViagemDto = new ItemControleFinanceiroViagemDTO();
		
		itemControleFinanceiroViagemDto.setIdSolicitacaoServico(controleDto.getIdSolicitacaoServico());
		itemControleFinanceiroViagemDto.setIdEmpregado(controleDto.getIdIntegrante());
		
		ItemControleFinanceiroViagemService itemService = (ItemControleFinanceiroViagemService) ServiceLocator.getInstance().getService(ItemControleFinanceiroViagemService.class, null);
		Collection<ItemControleFinanceiroViagemDTO> colItens = itemService.listaItensCompra(itemControleFinanceiroViagemDto.getIdSolicitacaoServico(), itemControleFinanceiroViagemDto.getIdEmpregado());
		
		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		EmpregadoDTO empregadoDto = new EmpregadoDTO();
		
		empregadoDto.setIdEmpregado(controleDto.getIdIntegrante());
		empregadoDto = (EmpregadoDTO) empregadoService.restore(empregadoDto);
		
		String nome = "<h2 style='padding-left: 20px'>" + empregadoDto.getNome() + "</h2>";
		document.getElementById("divNome").setInnerHTML(nome);		
		
		HTMLTable tblItens;
		tblItens = document.getTableById("tblItens");
		tblItens.deleteAllRows();
		
		if(colItens != null){
			tblItens.addRowsByCollection(colItens, new String[]{"","nomeTipoMovimFinanceira","nomeFornecedor","quantidade","valorUnitario","valorAdiantamento"}, null, null, new String[]{"gerarImgEdit"}, "editItem", null);
		}
		
		document.executeScript("$('#divItens').show()");
		
	}
	
	

}
