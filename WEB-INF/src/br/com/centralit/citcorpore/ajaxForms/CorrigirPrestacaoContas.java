package br.com.centralit.citcorpore.ajaxForms;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.ItemControleFinanceiroViagemDTO;
import br.com.centralit.citcorpore.bean.ItemPrestacaoContasViagemDTO;
import br.com.centralit.citcorpore.bean.ParecerDTO;
import br.com.centralit.citcorpore.bean.PrestacaoContasViagemDTO;
import br.com.centralit.citcorpore.bean.RequisicaoViagemDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.ParecerDao;
import br.com.centralit.citcorpore.integracao.PrestacaoContasViagemDao;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.ItemControleFinanceiroViagemService;
import br.com.centralit.citcorpore.negocio.ItemPrestacaoContasViagemService;
import br.com.centralit.citcorpore.negocio.PrestacaoContasViagemService;
import br.com.centralit.citcorpore.negocio.RequisicaoViagemService;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;

@SuppressWarnings({"rawtypes", "unchecked" })
public class CorrigirPrestacaoContas extends AjaxFormAction{
	public Class getBeanClass() {
		return PrestacaoContasViagemDTO.class;
	}

	/**
	 * Inicializa os dados ao carregar a tela.
	 * @author ronnie.lopes
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		
		document.executeScript("$('#divCorrecao').hide()");

		PrestacaoContasViagemDTO prestacaoContasViagemDto = (PrestacaoContasViagemDTO) document.getBean();
		RequisicaoViagemDTO requisicaoViagemDto = this.recuperaRequisicaoViagem(prestacaoContasViagemDto.getIdSolicitacaoServico());
		
		
		if(requisicaoViagemDto != null){
			requisicaoViagemDto.setUsuarioDto(usuario);
			requisicaoViagemDto.setIdTarefa(prestacaoContasViagemDto.getIdTarefa());
			PrestacaoContasViagemDTO prestacaoContasViagemAux = this.recuperaPrestacaoContasCorrecao(requisicaoViagemDto);
			if(prestacaoContasViagemAux != null){
				requisicaoViagemDto.setIdTarefa(prestacaoContasViagemDto.getIdTarefa());
				prestacaoContasViagemDto = this.carregaInformaçoesCorrecao(document, request, response, prestacaoContasViagemAux);
				document.executeScript("$('#divCorrecao').show()");
			}else{
				prestacaoContasViagemDto.setIdEmpregado(usuario.getIdEmpregado());
				if(this.recuperaIdPrestacaoSeExistir(prestacaoContasViagemDto.getIdSolicitacaoServico(), prestacaoContasViagemDto.getIdEmpregado()) != null)
					prestacaoContasViagemDto.setIdPrestacaoContasViagem(this.recuperaIdPrestacaoSeExistir(prestacaoContasViagemDto.getIdSolicitacaoServico(), prestacaoContasViagemDto.getIdEmpregado()));
			}
		}

		this.geraGridItensAdiantados(document, request, response, prestacaoContasViagemDto);
		if(prestacaoContasViagemDto.getIdPrestacaoContasViagem() != null){
			restore(document, request, response, prestacaoContasViagemDto);
		}
	
	}

	/**
	 * Inclui registro.
	 * @author ronnie.lopes
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrestacaoContasViagemDTO prestacaoContasViagemDto = (PrestacaoContasViagemDTO) document.getBean();
		PrestacaoContasViagemService prestacaoContasViagemService = (PrestacaoContasViagemService) ServiceLocator.getInstance().getService(PrestacaoContasViagemService.class, null);
		
		prestacaoContasViagemDto.setListaItemPrestacaoContasViagemDTO((ArrayList<ItemPrestacaoContasViagemDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(ItemPrestacaoContasViagemDTO.class, "listItens", request));
		
		if (prestacaoContasViagemDto.getIdPrestacaoContasViagem() == null || prestacaoContasViagemDto.getIdPrestacaoContasViagem().intValue() == 0) {
			prestacaoContasViagemService.create(prestacaoContasViagemDto);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else {
			prestacaoContasViagemService.update(prestacaoContasViagemDto);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}
		HTMLForm form = document.getForm("form");
		form.clear();
		document.executeScript("deleteAllRows()");
	}

	/**
	 * Restaura os dados ao clicar em um registro.
	 * @author ronnie.lopes
	 * @param document
	 * @param request
	 * @param response
	 * @param prestacaoContasViagemDto 
	 * @throws Exception
	 */
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, PrestacaoContasViagemDTO prestacaoContasViagemDto) throws Exception {
		PrestacaoContasViagemService prestacaoContasViagemService = (PrestacaoContasViagemService) ServiceLocator.getInstance().getService(PrestacaoContasViagemService.class, null);
		PrestacaoContasViagemDTO prestacaoContasAux = null;
		prestacaoContasAux = (PrestacaoContasViagemDTO) prestacaoContasViagemService.restore(prestacaoContasViagemDto);
		
		this.recuperaGridItensPrestacaoContas(document, request, response, prestacaoContasViagemDto);

		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(prestacaoContasViagemDto);

	}
	
	
	private void geraGridItensAdiantados(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, PrestacaoContasViagemDTO prestacaoContasViagemDto) throws Exception{
		ItemControleFinanceiroViagemService itemControleService = (ItemControleFinanceiroViagemService) ServiceLocator.getInstance().getService(ItemControleFinanceiroViagemService.class, null);
		Collection<ItemControleFinanceiroViagemDTO> colItens = itemControleService.listaItensAdiantamento(prestacaoContasViagemDto.getIdSolicitacaoServico(), prestacaoContasViagemDto.getIdEmpregado());
		double valorTotal = 0;
		DecimalFormat df = new DecimalFormat("#,#00.00");
		if(colItens != null){
			HTMLTable tblItens;
			tblItens = document.getTableById("tblItensAdiantados");
			tblItens.deleteAllRows();
			
			tblItens.addRowsByCollection(colItens, new String[]{"","idItemControleFinanceiroViagem","nomeTipoMovimFinanceira", "valorAdiantamento"}, null, null, new String[]{"gerarImgTbl"}, "addItemPrestacaoContas", null);
			
			for(ItemControleFinanceiroViagemDTO item : colItens){
				valorTotal += item.getValorAdiantamento();
			}
			String res = "<h4 style=\"text-align: right;\">Valor Total Adiantado: R$ " + df.format(valorTotal) +" </h4>";
			document.getElementById("divTotal").setInnerHTML(res);
		}
	}
	
	private void recuperaGridItensPrestacaoContas(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, PrestacaoContasViagemDTO prestacaoContasViagemDto) throws Exception{
		ItemPrestacaoContasViagemService itemService = (ItemPrestacaoContasViagemService) ServiceLocator.getInstance().getService(ItemPrestacaoContasViagemService.class, null);
		Collection<ItemPrestacaoContasViagemDTO> colItens = itemService.recuperaItensPrestacao(prestacaoContasViagemDto);
		if(colItens != null){
			HTMLTable tabelaItemPrestacaoContasViagem;
			tabelaItemPrestacaoContasViagem = document.getTableById("tabelaItemPrestacaoContasViagem");
			tabelaItemPrestacaoContasViagem.deleteAllRows();
			tabelaItemPrestacaoContasViagem.addRowsByCollection(colItens, new String[]{"", "idItemDespesaViagem","nomeFornecedor","numeroDocumento","data","valor","descricao"}, null, null, new String[]{"gerarButtonDelete"}, null, null);
			
			tabelaItemPrestacaoContasViagem.setVisible(true);
		}
	}
	
	public Integer recuperaIdPrestacaoSeExistir(Integer idSolicitacaoServico, Integer idEmpregado) throws ServiceException, Exception{
		PrestacaoContasViagemService prestacaoContasViagemService = (PrestacaoContasViagemService) ServiceLocator.getInstance().getService(PrestacaoContasViagemService.class, null);
		return prestacaoContasViagemService.recuperaIdPrestacaoSeExistir(idSolicitacaoServico, idEmpregado);
	}
	
	public PrestacaoContasViagemDTO recuperaPrestacaoContas(RequisicaoViagemDTO requisicaoViagemDto) throws Exception{
		PrestacaoContasViagemDao dao = new PrestacaoContasViagemDao();
		PrestacaoContasViagemDTO prestacaoContasAux = new PrestacaoContasViagemDTO();
		//verifica se ja existe um prestação de contas para associada a essa tarefa
		prestacaoContasAux = dao.findByTarefaAndSolicitacao(requisicaoViagemDto.getIdTarefa(), requisicaoViagemDto.getIdSolicitacaoServico());
		if(prestacaoContasAux != null){
			return prestacaoContasAux;
		}
		//se nao existir uma prestação, busca uma sem associação e associa a tarefa atual
		List list = dao.findBySolicitacao(requisicaoViagemDto.getIdSolicitacaoServico());
		if(list != null){
			prestacaoContasAux = (PrestacaoContasViagemDTO) list.get(0);
			if(prestacaoContasAux != null){
				prestacaoContasAux.setIdItemTrabalho(requisicaoViagemDto.getIdTarefa());
				dao.update(prestacaoContasAux);
				return prestacaoContasAux;
			}		
		} 
		
		/*PrestacaoContasViagemDao dao = new PrestacaoContasViagemDao();
		PrestacaoContasViagemDTO prestacaoDto = new PrestacaoContasViagemDTO();
		
		prestacaoDto = dao.findBySolicitacaoAndTarefa(requisicaoViagemDto.getIdSolicitacaoServico(),requisicaoViagemDto.getIdTarefa());*/
		
		return null;
	}
	
	private RequisicaoViagemDTO recuperaRequisicaoViagem(Integer idSolicitacao) throws ServiceException, Exception{
		RequisicaoViagemDTO requisicaoViagemDTO = new RequisicaoViagemDTO();
		RequisicaoViagemService reqViagemService = (RequisicaoViagemService) ServiceLocator.getInstance().getService(RequisicaoViagemService.class, null);
		
		requisicaoViagemDTO.setIdSolicitacaoServico(idSolicitacao);
		requisicaoViagemDTO = (RequisicaoViagemDTO) reqViagemService.restore(requisicaoViagemDTO);
		if(requisicaoViagemDTO != null)
			return requisicaoViagemDTO;
		
		return null;
	}
	

	private PrestacaoContasViagemDTO carregaInformaçoesCorrecao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, PrestacaoContasViagemDTO prestacaoContasViagemDto) throws Exception {
		
		if(prestacaoContasViagemDto != null){
			EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
			EmpregadoDTO empregadoDto = new EmpregadoDTO();
			
			empregadoDto.setIdEmpregado(prestacaoContasViagemDto.getIdEmpregado());
			empregadoDto = (EmpregadoDTO) empregadoService.restore(empregadoDto);
			
			String divNome = "<h2>" + empregadoDto.getNome() + "</h2>";
			document.getElementById("divNome").setInnerHTML(divNome);
			
			ParecerDTO parecerDto = recuperaParecer(prestacaoContasViagemDto);
			if(parecerDto != null){
				prestacaoContasViagemDto.setCorrigir(parecerDto.getComplementoJustificativa());
			}
		}
		return prestacaoContasViagemDto;
	}

	private ParecerDTO recuperaParecer(PrestacaoContasViagemDTO prestacaoContasViagemDto) throws Exception {
		ParecerDTO parecerDto = new ParecerDTO();
		if (prestacaoContasViagemDto.getIdAprovacao() != null) {
			parecerDto.setIdParecer(prestacaoContasViagemDto.getIdAprovacao());
			return (ParecerDTO) new ParecerDao().restore(parecerDto);

		}
		return null;
	}
	
	private PrestacaoContasViagemDTO recuperaPrestacaoContasCorrecao(RequisicaoViagemDTO requisicaoViagemDto) throws ServiceException, Exception{
		PrestacaoContasViagemService contasViagemService = (PrestacaoContasViagemService)ServiceLocator.getInstance().getService(PrestacaoContasViagemService.class, null);
		return contasViagemService.recuperaCorrecao(requisicaoViagemDto);
	}
}
