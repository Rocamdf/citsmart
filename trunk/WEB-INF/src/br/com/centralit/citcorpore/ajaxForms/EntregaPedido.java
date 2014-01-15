package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.bean.CotacaoDTO;
import br.com.centralit.citcorpore.bean.CriterioAvaliacaoDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.InspecaoPedidoCompraDTO;
import br.com.centralit.citcorpore.bean.PedidoCompraDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CotacaoService;
import br.com.centralit.citcorpore.negocio.CriterioAvaliacaoService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.negocio.InspecaoPedidoCompraService;
import br.com.centralit.citcorpore.negocio.PedidoCompraService;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoCotacao;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoPedidoCompra;
import br.com.citframework.service.ServiceLocator;

public class EntregaPedido extends AjaxFormAction {
    
    @SuppressWarnings("rawtypes")
    public Class getBeanClass() {
        return PedidoCompraDTO.class;
    }

    private void configuraBotoes(DocumentHTML document, HttpServletRequest request, PedidoCompraDTO pedidoCompraBean) throws Exception {
        if (pedidoCompraBean.getIdCotacao() == null)
            return;
        
        document.executeScript("document.getElementById('btnGravar').style.display = 'none'");
        
        if (pedidoCompraBean.getSituacao() != null && pedidoCompraBean.getSituacao().equals(SituacaoPedidoCompra.Entregue.name())) {
            document.executeScript("desabilitarTela()");
            return;
        }
        
        document.executeScript("habilitarTela()");
        CotacaoDTO cotacaoDto = new CotacaoDTO();
        cotacaoDto.setIdCotacao(pedidoCompraBean.getIdCotacao());
        CotacaoService cotacaoService = (CotacaoService) ServiceLocator.getInstance().getService(CotacaoService.class, null);
        cotacaoDto = (CotacaoDTO) cotacaoService.restore(cotacaoDto);
        if (cotacaoDto == null)
            return;
 
        SituacaoCotacao situacao = SituacaoCotacao.valueOf(cotacaoDto.getSituacao());
        if (situacao.equals(SituacaoCotacao.Pedido) || situacao.equals(SituacaoCotacao.Entrega)) {
            Collection colItens = cotacaoService.findItensPendentesAprovacao(cotacaoDto);
            if (colItens == null || colItens.size() == 0) {
                document.executeScript("document.getElementById('btnGravar').style.display = 'block'");
            }
        }
    }
    
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try{
            UsuarioDTO usuario = WebUtil.getUsuario(request);
            if (usuario == null) {
                document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
                document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
                return;
            }
            
            CriterioAvaliacaoService criterioAvaliacaoService = (CriterioAvaliacaoService) ServiceLocator.getInstance().getService(CriterioAvaliacaoService.class, WebUtil.getUsuarioSistema(request));
            Collection<CriterioAvaliacaoDTO> colCriterios = criterioAvaliacaoService.findByAplicavelAvaliacaoComprador();
            request.setAttribute("colCriterios", colCriterios);
            
            PedidoCompraDTO pedidoCompraDto = (PedidoCompraDTO) document.getBean();
            
            request.setAttribute("idCotacao", ""+pedidoCompraDto.getIdCotacao()); 
            exibePedidosCompra(document,request,pedidoCompraDto);
            restore(document,request,response);
            
        }finally{
            document.executeScript("parent.escondeJanelaAguarde()");
        }
    }


    private void exibePedidosCompra(DocumentHTML document, HttpServletRequest request, PedidoCompraDTO pedidoCompraDto) throws Exception {
        HTMLTable tblPedidos = document.getTableById("tblPedidos");
        tblPedidos.deleteAllRows();

        PedidoCompraService pedidoCompraService = (PedidoCompraService) ServiceLocator.getInstance().getService(PedidoCompraService.class, null);
        Collection<PedidoCompraDTO> colPedidos = pedidoCompraService.findByIdCotacao(pedidoCompraDto.getIdCotacao());
        if (colPedidos != null && !colPedidos.isEmpty()) {
            FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
            for (PedidoCompraDTO pedidoDto : colPedidos) {
                FornecedorDTO fornecedorDto = new FornecedorDTO();
                fornecedorDto.setIdFornecedor(pedidoDto.getIdFornecedor());
                fornecedorDto = (FornecedorDTO) fornecedorService.restore(fornecedorDto);
                if (fornecedorDto != null)
                    pedidoDto.setNomeFornecedor(fornecedorDto.getNomeFantasia());
            }
            tblPedidos.addRowsByCollection(colPedidos, 
                                                new String[] {"","numeroPedido","nomeFornecedor","descrSituacao"}, 
                                                null, 
                                                null,
                                                new String[] {"gerarImgAlteracao"}, 
                                                "exibirPedido", 
                                                null);
        }    
    }    
    
    public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PedidoCompraDTO pedidoCompraDto = (PedidoCompraDTO) document.getBean();
        
        HTMLForm form = document.getForm("form");
        form.clear();
        if (pedidoCompraDto.getIdPedido() != null) {
            Integer idCotacao = pedidoCompraDto.getIdCotacao();
            PedidoCompraService pedidoCompraService = (PedidoCompraService) ServiceLocator.getInstance().getService(PedidoCompraService.class, null);
            pedidoCompraDto = (PedidoCompraDTO) pedidoCompraService.restore(pedidoCompraDto);
            pedidoCompraDto.setIdCotacao(idCotacao);

            FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
            FornecedorDTO fornecedorDto = new FornecedorDTO();
            fornecedorDto.setIdFornecedor(pedidoCompraDto.getIdFornecedor());
            fornecedorDto = (FornecedorDTO) fornecedorService.restore(fornecedorDto);
            pedidoCompraDto.setNomeFornecedor(fornecedorDto.getNomeFantasia());
            
            InspecaoPedidoCompraService inspecaoPedidoService = (InspecaoPedidoCompraService) ServiceLocator.getInstance().getService(InspecaoPedidoCompraService.class, null);
            CriterioAvaliacaoService criterioAvaliacaoService = (CriterioAvaliacaoService) ServiceLocator.getInstance().getService(CriterioAvaliacaoService.class, null);
            Collection<InspecaoPedidoCompraDTO> colInspecao = inspecaoPedidoService.findByIdPedido(pedidoCompraDto.getIdPedido());
            document.executeScript("GRID_CRITERIOS.deleteAllRows();");
            if (colInspecao == null) {
                Collection<CriterioAvaliacaoDTO> colCriterios = criterioAvaliacaoService.findByAplicavelAvaliacaoComprador();
                if (colCriterios != null) {
                    colInspecao = new ArrayList();
                    for (CriterioAvaliacaoDTO criterioDto : colCriterios) {
                        InspecaoPedidoCompraDTO inspecaoDto = new InspecaoPedidoCompraDTO();
                        inspecaoDto.setIdCriterio(criterioDto.getIdCriterio());
                        inspecaoDto.setTipoAvaliacao(criterioDto.getTipoAvaliacao());
                        colInspecao.add(inspecaoDto);
                    }
                }
            }else{
                for (InspecaoPedidoCompraDTO inspecaoDto : colInspecao) {
                    CriterioAvaliacaoDTO criterioDto = new CriterioAvaliacaoDTO();
                    criterioDto.setIdCriterio(inspecaoDto.getIdCriterio());
                    criterioDto = (CriterioAvaliacaoDTO) criterioAvaliacaoService.restore(criterioDto);
                    if (criterioDto == null)
                        continue;
                    inspecaoDto.setTipoAvaliacao(criterioDto.getTipoAvaliacao());
                }    
            }
            if (colInspecao != null) {
                int i = 0;
                for (InspecaoPedidoCompraDTO inspecaoDto : colInspecao) {
                    i++;
                    document.executeScript("GRID_CRITERIOS.addRow()");
                    inspecaoDto.setSequencia(i);
                    document.executeScript("seqCriterio = NumberUtil.zerosAEsquerda("+i+",5)");
                    document.executeScript("exibeCriterio('" + br.com.citframework.util.WebUtil.serializeObject(inspecaoDto) + "')");
                }    
            }
        }
        configuraBotoes(document, request, pedidoCompraDto);
        form.setValues(pedidoCompraDto);
    }
    
    public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }
        
        PedidoCompraDTO pedidoCompraDto = (PedidoCompraDTO) document.getBean();
        if (pedidoCompraDto.getIdPedido() == null) 
            return;
        
        pedidoCompraDto.setUsuarioDto(usuario);
        Collection<InspecaoPedidoCompraDTO> colInspecao = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(InspecaoPedidoCompraDTO.class, "colCriterios_Serialize", request);
        pedidoCompraDto.setColInspecao(colInspecao);
        pedidoCompraDto.setUsuarioDto(usuario);
        
        PedidoCompraService pedidoCompraService = (PedidoCompraService) ServiceLocator.getInstance().getService(PedidoCompraService.class, null);
        pedidoCompraService.atualizaEntrega(pedidoCompraDto);
        exibePedidosCompra(document,request,pedidoCompraDto);
        document.alert(UtilI18N.internacionaliza(request, "pedidoCompra.mensagemAtualizacao"));
        document.executeScript("limpar();");
        document.executeScript("parent.atualiza();");
    }

}
