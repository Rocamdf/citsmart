package br.com.centralit.citcorpore.ajaxForms;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.ItemControleFinanceiroViagemDTO;
import br.com.centralit.citcorpore.bean.ItemPrestacaoContasViagemDTO;
import br.com.centralit.citcorpore.bean.PrestacaoContasViagemDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.PrestacaoContasViagemDao;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.ItemControleFinanceiroViagemService;
import br.com.centralit.citcorpore.negocio.ItemPrestacaoContasViagemService;
import br.com.centralit.citcorpore.negocio.JustificativaParecerService;
import br.com.centralit.citcorpore.negocio.PrestacaoContasViagemService;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;

@SuppressWarnings({"rawtypes"})
public class ConferenciaViagem extends AjaxFormAction{

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		
		document.executeScript("$('#divJustificativa').hide();");
		
		PrestacaoContasViagemDTO prestacaoContasViagemDto = (PrestacaoContasViagemDTO) document.getBean();
		prestacaoContasViagemDto.setIdEmpregado(this.recuperaEmpregado(prestacaoContasViagemDto));
		
		PrestacaoContasViagemDTO prestacaoAux = new PrestacaoContasViagemDTO();
		
		prestacaoAux = this.recuperaPrestacaoContas(prestacaoContasViagemDto);
		
		if(prestacaoAux != null) 
			prestacaoContasViagemDto.setIdPrestacaoContasViagem(prestacaoAux.getIdPrestacaoContasViagem());
			
		if(prestacaoContasViagemDto.getIdEmpregado() != null){		
			this.geraGridItemsAdiantados(document, request, response, prestacaoContasViagemDto);
			this.geraGridItemsPrestacaoContas(document, request, response, prestacaoContasViagemDto);
			
			restore(document, request, response, prestacaoContasViagemDto);
			
		}else{
			document.alert("Nenhum Integrante Encontrado nessa Solicitação");
		}
	}
	
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, PrestacaoContasViagemDTO prestacaoContasViagemDto) throws Exception {
		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		EmpregadoDTO empregadoDto = new EmpregadoDTO();
		
		empregadoDto.setIdEmpregado(prestacaoContasViagemDto.getIdEmpregado());
		empregadoDto = (EmpregadoDTO) empregadoService.restore(empregadoDto);
		
		String nome = "<h2 style='padding-left: 20px; margin-top:10px;'>" + empregadoDto.getNome() + "</h2>";
		document.getElementById("divNome").setInnerHTML(nome);	
		
		
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(prestacaoContasViagemDto);
	}
	
	public void geraGridItemsAdiantados(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, PrestacaoContasViagemDTO prestacaoContasViagemDto) throws ServiceException, Exception{
		ItemControleFinanceiroViagemService itemControleService = (ItemControleFinanceiroViagemService) ServiceLocator.getInstance().getService(ItemControleFinanceiroViagemService.class, null);
		Collection<ItemControleFinanceiroViagemDTO> colItens = itemControleService.listaItensAdiantamento(prestacaoContasViagemDto.getIdSolicitacaoServico(), prestacaoContasViagemDto.getIdEmpregado());
		double valorTotal = 0;
		DecimalFormat df = new DecimalFormat("#,#00.00");
		if(colItens != null){
			HTMLTable tblItens;
			tblItens = document.getTableById("tblItensAdiantados");
			tblItens.deleteAllRows();
			
			tblItens.addRowsByCollection(colItens, new String[]{"idItemControleFinanceiroViagem","nomeTipoMovimFinanceira","nomeFornecedor", "valorAdiantamento"}, null, null, null, null, null);
			
			for(ItemControleFinanceiroViagemDTO item : colItens){
				valorTotal += item.getValorAdiantamento();
			}
			String res = "<h2 style=\"text-align: right;\">Valor Total Adiantado: R$ " + df.format(valorTotal) +" </h2>";
			document.getElementById("divTotal").setInnerHTML(res);
		}
	}
	
	public void geraGridItemsPrestacaoContas(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, PrestacaoContasViagemDTO prestacaoContasViagemDto) throws ServiceException, Exception{
		ItemPrestacaoContasViagemService itemService = (ItemPrestacaoContasViagemService) ServiceLocator.getInstance().getService(ItemPrestacaoContasViagemService.class, null);
		Collection<ItemPrestacaoContasViagemDTO> colItens = itemService.recuperaItensPrestacao(prestacaoContasViagemDto);
		if(colItens != null){
			HTMLTable tabelaItemPrestacaoContasViagem;
			tabelaItemPrestacaoContasViagem = document.getTableById("tabelaItemPrestacaoContasViagem");
			tabelaItemPrestacaoContasViagem.deleteAllRows();
			tabelaItemPrestacaoContasViagem.addRowsByCollection(colItens, new String[]{"idItemDespesaViagem","nomeFornecedor","numeroDocumento","data","valor","descricao"}, null, null, null, null, null);
			
			tabelaItemPrestacaoContasViagem.setVisible(true);
			
		}
	}
	
	private PrestacaoContasViagemDTO recuperaPrestacaoContas(PrestacaoContasViagemDTO prestacaoContasViagemDto2) throws ServiceException, Exception{
		PrestacaoContasViagemDTO prestacaoContasViagemDto = new PrestacaoContasViagemDTO();
		PrestacaoContasViagemService prestacaoContasViagemService = (PrestacaoContasViagemService) ServiceLocator.getInstance().getService(PrestacaoContasViagemService.class, null);
		
		prestacaoContasViagemDto.setIdPrestacaoContasViagem(prestacaoContasViagemService.recuperaIdPrestacaoSeExistir(prestacaoContasViagemDto2.getIdSolicitacaoServico(), prestacaoContasViagemDto2.getIdEmpregado()));
		prestacaoContasViagemDto = (PrestacaoContasViagemDTO) prestacaoContasViagemService.restore(prestacaoContasViagemDto);
		return prestacaoContasViagemDto;
	}
	
	private Integer recuperaEmpregado(PrestacaoContasViagemDTO prestacaoContasViagemDto) throws Exception{
		PrestacaoContasViagemDao dao = new PrestacaoContasViagemDao();
		PrestacaoContasViagemDTO prestacaoContasAux = new PrestacaoContasViagemDTO();
		//verifica se ja existe um prestação de contas para associada a essa tarefa
		prestacaoContasAux = dao.findByTarefaAndSolicitacao(prestacaoContasViagemDto.getIdTarefa(), prestacaoContasViagemDto.getIdSolicitacaoServico());
		if(prestacaoContasAux != null){
			return prestacaoContasAux.getIdEmpregado();
		}
		//se nao existir uma prestação, busca uma sem associação e associa a tarefa atual
		List list = dao.findBySolicitacao(prestacaoContasViagemDto.getIdSolicitacaoServico());
		if(list != null){
			prestacaoContasAux = (PrestacaoContasViagemDTO) list.get(0);
			if(prestacaoContasAux != null && prestacaoContasAux.getIdEmpregado() != null){
				prestacaoContasAux.setIdItemTrabalho(prestacaoContasViagemDto.getIdTarefa());
				dao.update(prestacaoContasAux);
				return prestacaoContasAux.getIdEmpregado();
			}		
		}

		return null;
	}

	@Override
	public Class getBeanClass() {
		return PrestacaoContasViagemDTO.class;
	}
	
	public void preencherComboJustificativaAutorizacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JustificativaParecerService justificativaService = (JustificativaParecerService) ServiceLocator.getInstance().getService(JustificativaParecerService.class, WebUtil.getUsuarioSistema(request));
		
	    Collection colJustificativas = justificativaService.listAplicaveisRequisicao();
	    
		HTMLSelect comboJustificativaAutorizacao = (HTMLSelect) document.getSelectById("idJustificativaAutorizacao");
		
		document.getSelectById("idJustificativaAutorizacao").removeAllOptions();
		
		comboJustificativaAutorizacao.removeAllOptions();
		comboJustificativaAutorizacao.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		
		if (colJustificativas != null){
			comboJustificativaAutorizacao.addOptions(colJustificativas, "idJustificativa", "descricaoJustificativa", null);
		}
	}

}
