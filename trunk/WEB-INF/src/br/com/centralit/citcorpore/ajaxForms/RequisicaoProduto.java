package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.bean.CategoriaProdutoDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.ItemRequisicaoProdutoDTO;
import br.com.centralit.citcorpore.bean.ProdutoDTO;
import br.com.centralit.citcorpore.bean.RelacionamentoProdutoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoProdutoDTO;
import br.com.centralit.citcorpore.bean.TipoProdutoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CategoriaProdutoService;
import br.com.centralit.citcorpore.negocio.CentroResultadoService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.EnderecoService;
import br.com.centralit.citcorpore.negocio.ItemRequisicaoProdutoService;
import br.com.centralit.citcorpore.negocio.ProdutoService;
import br.com.centralit.citcorpore.negocio.ProjetoService;
import br.com.centralit.citcorpore.negocio.RelacionamentoProdutoService;
import br.com.centralit.citcorpore.negocio.RequisicaoProdutoService;
import br.com.centralit.citcorpore.negocio.TipoProdutoService;
import br.com.centralit.citcorpore.negocio.UnidadeMedidaService;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilFormatacao;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class RequisicaoProduto extends AjaxFormAction {

	@Override
	public Class getBeanClass() {
		return RequisicaoProdutoDTO.class;
	}

	protected String getAcao() {
	    return RequisicaoProdutoDTO.ACAO_CRIACAO;
	}
	
	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

        RequisicaoProdutoDTO requisicaoProdutoDto = (RequisicaoProdutoDTO) document.getBean();
        
		HTMLSelect finalidade = (HTMLSelect) document.getSelectById("finalidade");
		finalidade.removeAllOptions();
		finalidade.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
        finalidade.addOption("I", UtilI18N.internacionaliza(request, "requisicaoProduto.finalidade.usoInterno"));
		finalidade.addOption("C", UtilI18N.internacionaliza(request, "requisicaoProduto.finalidade.atendimentoCliente"));

        UnidadeMedidaService unidadeMedidaService = (UnidadeMedidaService) ServiceLocator.getInstance().getService(UnidadeMedidaService.class, WebUtil.getUsuarioSistema(request));
        HTMLSelect idUnidadeMedida = (HTMLSelect) document.getSelectById("item#idUnidadeMedida");
        idUnidadeMedida.removeAllOptions();
        idUnidadeMedida.addOption("", "---");
        Collection colUnidades = unidadeMedidaService.list();
        if(colUnidades != null && !colUnidades.isEmpty())
            idUnidadeMedida.addOptions(colUnidades, "idUnidadeMedida", "siglaUnidadeMedida", null);

        CentroResultadoService centroResultadoService = (CentroResultadoService) ServiceLocator.getInstance().getService(CentroResultadoService.class, WebUtil.getUsuarioSistema(request));
        HTMLSelect idCentroCusto = (HTMLSelect) document.getSelectById("idCentroCusto");
        idCentroCusto.removeAllOptions();
        idCentroCusto.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
        Collection colCCusto = centroResultadoService.listPermiteRequisicaoProduto();
        if(colCCusto != null && !colCCusto.isEmpty())
            idCentroCusto.addOptions(colCCusto, "idCentroResultado", "nomeHierarquizado", null);
                
        HTMLSelect idProjeto = (HTMLSelect) document.getSelectById("idProjeto");
        idProjeto.removeAllOptions();
        idProjeto.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
        if (requisicaoProdutoDto != null && requisicaoProdutoDto.getIdContrato() != null) {
            ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, WebUtil.getUsuarioSistema(request));
            ContratoDTO contratoDto = new ContratoDTO();
            contratoDto.setIdContrato(requisicaoProdutoDto.getIdContrato());
            contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
            if (contratoDto != null) {
                ProjetoService projetoService = (ProjetoService) ServiceLocator.getInstance().getService(ProjetoService.class, WebUtil.getUsuarioSistema(request));
                Collection colProjetos = projetoService.listHierarquia(contratoDto.getIdCliente(), true);
                if(colProjetos != null && !colProjetos.isEmpty()) 
                    idProjeto.addOptions(colProjetos, "idProjeto", "nomeHierarquizado", null);
            }
        }
        
        EnderecoService enderecoService = (EnderecoService) ServiceLocator.getInstance().getService(EnderecoService.class, WebUtil.getUsuarioSistema(request));
        HTMLSelect idEnderecoEntrega = (HTMLSelect) document.getSelectById("idEnderecoEntrega");
        idEnderecoEntrega.removeAllOptions();
        idEnderecoEntrega.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
        Collection colEnderecos = enderecoService.recuperaEnderecosEntregaProduto();
        if(colEnderecos != null && !colEnderecos.isEmpty())
            idEnderecoEntrega.addOptions(colEnderecos, "idEndereco", "enderecoStr", null);
        
        ProdutoService produtoService = (ProdutoService) ServiceLocator.getInstance().getService(ProdutoService.class, WebUtil.getUsuarioSistema(request));
        Collection<ProdutoDTO> colProdutos = produtoService.findByIdCategoriaAndAceitaRequisicao(null,"S");
        request.setAttribute("colProdutos", colProdutos);
        
        montaHierarquiaCategoria(document, request, response);

        if (requisicaoProdutoDto != null && requisicaoProdutoDto.getIdSolicitacaoServico() != null) {
			restore(document, request, response);
		} else {
			preparaTelaInclusao(document, request, response);
		}

	}

	private void preparaTelaInclusao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

        RequisicaoProdutoDTO requisicaoProdutoDto = (RequisicaoProdutoDTO) document.getBean();
        String editar = requisicaoProdutoDto.getEditar();
		RequisicaoProdutoService requisicaoProdutoService = (RequisicaoProdutoService) ServiceLocator.getInstance().getService(RequisicaoProdutoService.class, WebUtil.getUsuarioSistema(request));

		requisicaoProdutoDto = (RequisicaoProdutoDTO) requisicaoProdutoService.restore(requisicaoProdutoDto);
		if (requisicaoProdutoDto == null) 
			return;

		requisicaoProdutoDto.setEditar(editar);
        if (requisicaoProdutoDto.getEditar() == null)
            requisicaoProdutoDto.setEditar("S");
        
        requisicaoProdutoDto.setRejeitada("N");
        
        HTMLTable tblItensRequisicao = document.getTableById("tblItensRequisicao");
        tblItensRequisicao.deleteAllRows();
        
        ItemRequisicaoProdutoService itemRequisicaoProdutoService = (ItemRequisicaoProdutoService) ServiceLocator.getInstance().getService(ItemRequisicaoProdutoService.class, WebUtil.getUsuarioSistema(request));
        Collection<ItemRequisicaoProdutoDTO> itensRequisicao = itemRequisicaoProdutoService.findByIdSolicitacaoServico(requisicaoProdutoDto.getIdSolicitacaoServico());
        if (itensRequisicao != null) {
            tblItensRequisicao.addRowsByCollection(itensRequisicao, 
                                                new String[] {"","descricaoItem","quantidade","siglaUnidadeMedida","marcaPreferencial","descrSituacao"}, 
                                                null, 
                                                "", 
                                                new String[] {"gerarImgAlterar"}, 
                                                "editarItem", 
                                                null);  
        }
        
        requisicaoProdutoDto.setAcao(getAcao());
        HTMLForm form = document.getForm("form");
        form.clear();   
        form.setValues(requisicaoProdutoDto);
        
        document.getElementById("finalidade").setDisabled(true);
	}
	
    public void montaHierarquiaCategoria(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CategoriaProdutoService categoriaProdutoService = (CategoriaProdutoService) ServiceLocator.getInstance().getService(CategoriaProdutoService.class, WebUtil.getUsuarioSistema(request));
        
        Collection<CategoriaProdutoDTO> colCategorias = categoriaProdutoService.listAtivas();
        HTMLSelect idCategoria = (HTMLSelect) document.getSelectById("item#idCategoriaProduto");
        idCategoria.removeAllOptions();
        idCategoria.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
        if(colCategorias != null && !colCategorias.isEmpty())
        	idCategoria.addOptions(colCategorias, "idCategoria", "nomeHierarquizado", null);
    }   

    public void preparaTelaItemRequisicao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }

        RequisicaoProdutoDTO requisicaoProdutoDto = (RequisicaoProdutoDTO) document.getBean();
        if (requisicaoProdutoDto.getTipoIdentificacaoItem() != null && requisicaoProdutoDto.getTipoIdentificacaoItem().equals("S"))
            exibeProduto(document, request, response);
        else
            document.executeScript("prepararTelaDigitacaoProduto()");
        document.executeScript("$(\"#POPUP_ITEM_REQUISICAO\").dialog(\"open\")");    
    }

    public void exibeProduto(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }

        document.executeScript("document.getElementById('divDigitacao').style.display = 'none'");
        document.executeScript("document.getElementById('divProduto').style.display = 'none'");
        
        RequisicaoProdutoDTO requisicaoProdutoDto = (RequisicaoProdutoDTO) document.getBean();
        if (requisicaoProdutoDto.getIdProduto() == null) 
            return;

        document.executeScript("document.getElementById('divBotoesItem').style.display = 'block';");
        String img = "";
        String detalhes = "";
        String acessorios = "";
        ProdutoService produtoService = (ProdutoService) ServiceLocator.getInstance().getService(ProdutoService.class, WebUtil.getUsuarioSistema(request));
        ProdutoDTO produtoDto = new ProdutoDTO();
        produtoDto.setIdProduto(requisicaoProdutoDto.getIdProduto());
        produtoDto = (ProdutoDTO) produtoService.restore(produtoDto);
        if (produtoDto != null) {     
            document.executeScript("document.getElementById('divProduto').style.display = 'block';");
            document.executeScript("document.getElementById('descricaoItem').disabled = -1;");
            produtoService.recuperaImagem(produtoDto);
            if (produtoDto.getImagem() != null) 
                img = "<img src=\""+produtoDto.getImagem()+"\" style='align:center'/>";
            
            if (produtoDto.getDetalhes() != null) 
                detalhes += "<b>"+UtilI18N.internacionaliza(request, "itemRequisicaoProduto.especificacoes")+":</b><br>"+produtoDto.getDetalhes()+"<br><br>";
            
            if (produtoDto.getPrecoMercado() != null) 
                detalhes += "<b>"+UtilI18N.internacionaliza(request, "itemRequisicaoProduto.preco")+":</b>&nbsp;"+UtilI18N.internacionaliza(request, "citcorpore.comum.simboloMonetario")+UtilFormatacao.formatDouble(produtoDto.getPrecoMercado(), 2);
            
            RelacionamentoProdutoService relacionamentoService = (RelacionamentoProdutoService) ServiceLocator.getInstance().getService(RelacionamentoProdutoService.class, WebUtil.getUsuarioSistema(request));
            Collection<RelacionamentoProdutoDTO> colRelacionados = relacionamentoService.findByIdTipoProduto(produtoDto.getIdTipoProduto());
            if (colRelacionados != null && !colRelacionados.isEmpty()) {
                TipoProdutoService tipoProdutoService = (TipoProdutoService) ServiceLocator.getInstance().getService(TipoProdutoService.class, WebUtil.getUsuarioSistema(request));
                HashMap<String, CategoriaProdutoDTO> mapAcessorios = new HashMap();
                for (RelacionamentoProdutoDTO acessorioDto : colRelacionados) {
                    TipoProdutoDTO produtoAuxDto = new TipoProdutoDTO();
                    produtoAuxDto.setIdTipoProduto(acessorioDto.getIdTipoProdutoRelacionado());
                    produtoAuxDto = (TipoProdutoDTO) tipoProdutoService.restore(produtoAuxDto);
                    if (produtoAuxDto != null) {
                        CategoriaProdutoDTO categoriaDto = mapAcessorios.get(""+produtoAuxDto.getIdCategoria());
                        if (categoriaDto == null) {
                            List<TipoProdutoDTO> list = new ArrayList();
                            list.add(produtoAuxDto);
                            categoriaDto = new CategoriaProdutoDTO();
                            categoriaDto.setIdCategoria(produtoAuxDto.getIdCategoria());
                            categoriaDto.setNomeCategoria(produtoAuxDto.getNomeCategoria());
                            categoriaDto.setColProdutos(list);
                            mapAcessorios.put(""+produtoAuxDto.getIdCategoria(),categoriaDto);
                        }else{
                            categoriaDto.getColProdutos().add(produtoAuxDto);
                        }
                    }
                }                
                
                CategoriaProdutoService categoriaProdutoService = (CategoriaProdutoService) ServiceLocator.getInstance().getService(CategoriaProdutoService.class, WebUtil.getUsuarioSistema(request));
                int i = 0;
                acessorios = "<table class='table1' border='0'><tr><th colspan='3'>"+UtilI18N.internacionaliza(request, "itemRequisicaoProduto.produtosAssociados")+"</th></tr>";                
                for (String id: mapAcessorios.keySet()) {  
                    CategoriaProdutoDTO categoriaDto = mapAcessorios.get(id);
                    categoriaProdutoService.recuperaImagem(categoriaDto);
                    String path = categoriaDto.getImagem();
                    if (path == null || path.equals(""))
                        path = Constantes.getValue("CONTEXTO_APLICACAO")+"/imagens/folder.png";
                    acessorios += "<tr><td><img style='width:16px;height:16px' src=\""+path+"\" /></td><td colspan='2'><b>"+categoriaDto.getNomeCategoria()+"</b></td></tr>";
                    for (TipoProdutoDTO produtoAuxDto: categoriaDto.getColProdutos()) {
                        acessorios += "<tr><td>&nbsp;</td><td>&nbsp;</td><td>"+produtoAuxDto.getNomeProduto()+"</td></tr>";
                    }
                }
                acessorios += "</table>";
                
                /*int i = 0;
                acessorios = "<table><tr>";
                for (AcessorioProdutoDTO acessorioDto : colRelacionados) {
                    ProdutoDTO produtoAuxDto = new ProdutoDTO();
                    produtoAuxDto.setIdProduto(acessorioDto.getIdAcessorio());
                    produtoAuxDto = (ProdutoDTO) produtoService.restore(produtoAuxDto);
                    if (produtoAuxDto != null) {
                        String preco = "";
                        if (produtoAuxDto.getPrecoMercado() != null) 
                            preco = "&nbsp;&nbsp;"+UtilI18N.internacionaliza(request, "citcorpore.comum.simboloMonetario")+UtilFormatacao.formatDouble(produtoAuxDto.getPrecoMercado(), 2);
                        if (i > 0 && i % 2 == 0) {
                            acessorios += "</tr>";
                            acessorios += "<tr>";
                        }
                        acessorios += "<td><label style='cursor:pointer'>"+produtoAuxDto.getNomeCategoria()+" -> " +produtoAuxDto.getNomeProduto()+preco+"</label>&nbsp;&nbsp;&nbsp;</td>";
//                        acessorios += "<td><label style='cursor:pointer'><input type='checkbox' value='"+acessorioDto.getIdAcessorio()+"' name='idAcessorio' id='idAcessorio"+acessorioDto.getIdAcessorio()+"' />"+
//                        "<input id='qtdeAcessorio_"+acessorioDto.getIdAcessorio()+"' name='qtdeAcessorio_"+acessorioDto.getIdAcessorio()+"' type='text'  maxlength='3' lenght='3' class='Format[Moeda]'/>" +
//                        produtoAuxDto.getNomeProduto()+preco+"</label>&nbsp;&nbsp;&nbsp;</td>";
                        i++;
                    }
                }
                acessorios += "<tr></table>";*/
            }
            HTMLForm form = document.getForm("formItemRequisicao");
            form.setValues(produtoDto);
            document.executeScript("posicionarCategoria("+produtoDto.getIdCategoria().intValue()+");");
        }
        document.getElementById("divImagemProduto").setInnerHTML(img);
        document.getElementById("divDetalhesProduto").setInnerHTML(detalhes);
        document.getElementById("divAcessorios").setInnerHTML(acessorios);
    }
}
