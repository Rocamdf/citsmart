package br.com.centralit.citcorpore.bpm.negocio;

import java.util.Collection;
import java.util.Map;

import br.com.centralit.bpm.dto.EventoFluxoDTO;
import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.negocio.InstanciaFluxo;
import br.com.centralit.bpm.negocio.Tarefa;
import br.com.centralit.citcorpore.bean.AlcadaDTO;
import br.com.centralit.citcorpore.bean.CentroResultadoDTO;
import br.com.centralit.citcorpore.bean.CotacaoItemRequisicaoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.EntregaItemRequisicaoDTO;
import br.com.centralit.citcorpore.bean.ItemRequisicaoProdutoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoProdutoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.CentroResultadoDao;
import br.com.centralit.citcorpore.integracao.CotacaoDao;
import br.com.centralit.citcorpore.integracao.CotacaoItemRequisicaoDao;
import br.com.centralit.citcorpore.integracao.EntregaItemRequisicaoDao;
import br.com.centralit.citcorpore.integracao.ItemCotacaoDao;
import br.com.centralit.citcorpore.integracao.ItemRequisicaoProdutoDao;
import br.com.centralit.citcorpore.integracao.RequisicaoProdutoDao;
import br.com.centralit.citcorpore.integracao.UsuarioDao;
import br.com.centralit.citcorpore.negocio.ItemRequisicaoProdutoServiceEjb;
import br.com.centralit.citcorpore.negocio.alcada.AlcadaCompras;
import br.com.centralit.citcorpore.util.Enumerados.AcaoItemRequisicaoProduto;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoEntregaItemRequisicao;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoItemRequisicaoProduto;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilStrings;


@SuppressWarnings({ "unused", "unchecked" })
public class ExecucaoRequisicaoProduto extends ExecucaoSolicitacao {  

    public ExecucaoRequisicaoProduto() {
        super();
    }
    
    @Override
    public void validaEncerramento() throws Exception { 
		SolicitacaoServicoDTO solicitacaoServicoDto = getSolicitacaoServicoDto();
		if (solicitacaoServicoDto == null)
			throw new Exception("Solicitação de serviço não encontrada");

    	ItemRequisicaoProdutoDao itemRequisicaoProdutoDao = new ItemRequisicaoProdutoDao();
        Collection<ItemRequisicaoProdutoDTO> colItens = itemRequisicaoProdutoDao.findByIdSolicitacaoServico(getSolicitacaoServicoDto().getIdSolicitacaoServico());
        if (colItens == null || colItens.isEmpty())
            return;
        
        int qtdeItens = 0;
        boolean bEncerramentoPermitido = true;
        ItemCotacaoDao itemCotacaoDao = new ItemCotacaoDao();
        CotacaoDao cotacaoDao = new CotacaoDao();
        EntregaItemRequisicaoDao entregaItemDao = new EntregaItemRequisicaoDao();            
        for (ItemRequisicaoProdutoDTO itemDto : colItens) {
            if (!itemDto.getTipoAtendimento().equalsIgnoreCase("C"))
                continue;
            
            if (itemDto.getIdItemCotacao() == null)
                continue;
            
            qtdeItens ++;
    		if (solicitacaoServicoDto.getSituacao().equalsIgnoreCase(SituacaoSolicitacaoServico.Cancelada.name())) {
    			bEncerramentoPermitido = false;
    			break;
    		}

            Collection<EntregaItemRequisicaoDTO> colEntregas = entregaItemDao.findByIdItemRequisicaoProduto(itemDto.getIdItemRequisicaoProduto());
            if (colEntregas == null || colEntregas.isEmpty()) {
                bEncerramentoPermitido = false;
                break;
            }

            double qtde = 0;
            for (EntregaItemRequisicaoDTO entregaDto : colEntregas) {
                if (!entregaDto.getSituacao().equals(SituacaoEntregaItemRequisicao.Aprovada.name())) {
                    bEncerramentoPermitido = false;
                    break;
                }
                qtde += entregaDto.getQuantidadeEntregue().doubleValue();
            }
            if (!bEncerramentoPermitido)
                break;
            
            if (itemDto.getQtdeAprovada().doubleValue() != qtde) {
                bEncerramentoPermitido = false;
                break;
            }
        }
        
		if (bEncerramentoPermitido && solicitacaoServicoDto.atendida() && qtdeItens > 0) 
			bEncerramentoPermitido = false;
		
        if (!bEncerramentoPermitido)
            throw new LogicException("Encerramento da requisição "+getSolicitacaoServicoDto().getIdSolicitacaoServico()+" não permitido. Existe pelo menos um item em processo de cotação.");
       
        setTransacaoDao(itemRequisicaoProdutoDao);
        ItemRequisicaoProdutoServiceEjb itemRequisicaoService = new ItemRequisicaoProdutoServiceEjb();
        for (ItemRequisicaoProdutoDTO itemDto : colItens) {
        	if (itemDto.getSituacao().equals(SituacaoItemRequisicaoProduto.Cancelado.name()) || itemDto.getSituacao().equals(SituacaoItemRequisicaoProduto.Finalizado.name()))
        		continue;
        	itemDto.setSituacao(SituacaoItemRequisicaoProduto.Cancelado.name());
        	itemRequisicaoProdutoDao.updateNotNull(itemDto);
        	itemRequisicaoService.geraHistorico(getTransacao(), null, itemDto, AcaoItemRequisicaoProduto.Cancelamento, null, SituacaoItemRequisicaoProduto.Cancelado);
        }
    }
    
    public void associaItemTrabalhoAprovacao(Tarefa tarefa) throws Exception{
        CotacaoItemRequisicaoDao cotacaoItemRequisicaoDao = new CotacaoItemRequisicaoDao();
        setTransacaoDao(cotacaoItemRequisicaoDao);
        Collection<CotacaoItemRequisicaoDTO> colItens = cotacaoItemRequisicaoDao.findDisponiveisAprovacaoByIdSolicitacaoServico(getSolicitacaoServicoDto().getIdSolicitacaoServico());
        if (colItens != null) {
            for (CotacaoItemRequisicaoDTO cotacaoItemRequisicaoDto : colItens) {
                cotacaoItemRequisicaoDto.setIdItemTrabalho(tarefa.getIdItemTrabalho());
                cotacaoItemRequisicaoDao.update(cotacaoItemRequisicaoDto);
            }
        }
    }
    
    public void associaItemTrabalhoInspecao(Tarefa tarefa) throws Exception{
        EntregaItemRequisicaoDao entregaItemRequisicaoDao = new EntregaItemRequisicaoDao();
        setTransacaoDao(entregaItemRequisicaoDao);
        Collection<EntregaItemRequisicaoDTO> colItens = new EntregaItemRequisicaoDao().findDisponiveisInspecaoByIdSolicitacaoServico(getSolicitacaoServicoDto().getIdSolicitacaoServico());
        if (colItens != null) {
            for (EntregaItemRequisicaoDTO inspecaoDto : colItens) {
                inspecaoDto.setIdItemTrabalho(tarefa.getIdItemTrabalho());
                entregaItemRequisicaoDao.update(inspecaoDto);
            }
        }
    }   
    
    public void associaItemTrabalhoGarantia(Tarefa tarefa) throws Exception{
        EntregaItemRequisicaoDao entregaItemRequisicaoDao = new EntregaItemRequisicaoDao();
        setTransacaoDao(entregaItemRequisicaoDao);
        Collection<EntregaItemRequisicaoDTO> colItens = new EntregaItemRequisicaoDao().findNaoAprovadasEDisponiveisByIdSolicitacaoServico(getSolicitacaoServicoDto().getIdSolicitacaoServico());
        if (colItens != null) {
            for (EntregaItemRequisicaoDTO inspecaoDto : colItens) {
                inspecaoDto.setIdItemTrabalho(tarefa.getIdItemTrabalho());
                entregaItemRequisicaoDao.update(inspecaoDto);
            }
        }
    }  
    
    public ExecucaoRequisicaoProduto(RequisicaoProdutoDTO requisicaoProdutoDto, TransactionControler tc) {
        super(requisicaoProdutoDto, tc);
    }
    
    public ExecucaoRequisicaoProduto(TransactionControler tc) {
        super(tc);
    }

    public boolean existeAprovacaoPendente() throws Exception{
        Collection<CotacaoItemRequisicaoDTO> colItens = new CotacaoItemRequisicaoDao().findDisponiveisAprovacaoByIdSolicitacaoServico(getSolicitacaoServicoDto().getIdSolicitacaoServico());
        return colItens != null && colItens.size() > 0;
    }
    
    public boolean existeEntregaPendenteInspecao()  throws Exception{
        Collection<EntregaItemRequisicaoDTO> colItens = new EntregaItemRequisicaoDao().findDisponiveisInspecaoByIdSolicitacaoServico(getSolicitacaoServicoDto().getIdSolicitacaoServico());
        return colItens != null && colItens.size() > 0;
    }
    
    public boolean existeEntregaNaoAprovada() throws Exception{
        Collection<EntregaItemRequisicaoDTO> colItens = new EntregaItemRequisicaoDao().findNaoAprovadasEDisponiveisByIdSolicitacaoServico(getSolicitacaoServicoDto().getIdSolicitacaoServico());
        return colItens != null && colItens.size() > 0;
    }
    
    public boolean entregaFinalizada()  throws Exception{
        Collection<ItemRequisicaoProdutoDTO> colItens = new ItemRequisicaoProdutoDao().findByIdSolicitacaoServico(getSolicitacaoServicoDto().getIdSolicitacaoServico());
        if (colItens == null || colItens.isEmpty())
            return false;
        
        for (ItemRequisicaoProdutoDTO itemDto : colItens) {
            if (itemDto.getIdSolicitacaoServico().intValue() == 76047) {
                int i = 0;
            }
            if (!itemDto.getTipoAtendimento().equalsIgnoreCase("C"))
                continue;
            if (itemDto.getSituacao().equals(SituacaoItemRequisicaoProduto.Inviabilizado.name()))
                continue;
            if (itemDto.getSituacao().equals(SituacaoItemRequisicaoProduto.Cancelado.name()))
                continue;
            if (!itemDto.getSituacao().equals(SituacaoItemRequisicaoProduto.Finalizado.name()))
                return false;
        }
        return true;
    }
    
    public boolean requisicaoRejeitada() throws Exception{
        RequisicaoProdutoDTO requisicaoDto = recuperaRequisicaoProduto();
        return requisicaoDto.getRejeitada() != null && requisicaoDto.getRejeitada().equalsIgnoreCase("S");
    }
    
    public boolean exigeAutorizacao() throws Exception{
        RequisicaoProdutoDTO requisicaoDto = recuperaRequisicaoProduto();
        return exigeAutorizacao(requisicaoDto);
    }
    
    public boolean exigeAutorizacao(RequisicaoProdutoDTO requisicaoProdutoDto) throws Exception{
        RequisicaoProdutoDTO requisicaoAuxDto = recuperaRequisicaoProduto();
        AlcadaDTO alcadaDto = recuperaAlcada(requisicaoAuxDto);
        if (alcadaDto != null) {
            boolean result = false;
            if (alcadaDto.getColResponsaveis() != null) {
                result = true;
                for (EmpregadoDTO empregadoDto: alcadaDto.getColResponsaveis()) {
                    if (getSolicitacaoServicoDto().getIdSolicitante().intValue() == empregadoDto.getIdEmpregado().intValue()) {
                        result = false;
                        break;
                    }
                }
            }
            return result;
        }else
            return false;
    }
    
    public StringBuffer recuperaLoginAutorizadores() throws Exception{
        RequisicaoProdutoDTO requisicaoDto = recuperaRequisicaoProduto();
        return recuperaLoginAutorizadores(requisicaoDto);
    }

    public StringBuffer recuperaLoginAutorizadores(RequisicaoProdutoDTO requisicaoProdutoDto) throws Exception{
        StringBuffer result = new StringBuffer();
        AlcadaDTO alcadaDto = recuperaAlcada(requisicaoProdutoDto);
        if (alcadaDto != null && alcadaDto.getColResponsaveis() != null) {
            int i = 0;
            UsuarioDao usuarioDao = new UsuarioDao();
            for (EmpregadoDTO empregadoDto: alcadaDto.getColResponsaveis()) {
                UsuarioDTO usuarioDto = usuarioDao.restoreAtivoByIdEmpregado(empregadoDto.getIdEmpregado());
                if (usuarioDto != null) {
                    if (i > 0)
                        result.append(";");
                    result.append(usuarioDto.getLogin());
                    i++;
                }
            }
        }   
        if (result.length() == 0)
        	throw new LogicException("Não foi encontrado nenhum autorizador da requisição");
        return result;
    }
    
    public AlcadaDTO recuperaAlcada(RequisicaoProdutoDTO requisicaoProdutoDto) throws Exception {
        return new AlcadaCompras().determinaAlcada(requisicaoProdutoDto, recuperaCentroCusto(requisicaoProdutoDto), getTransacao());
    }
    
    public RequisicaoProdutoDTO recuperaRequisicaoProduto() throws Exception{
        RequisicaoProdutoDao requisicaoDao = new RequisicaoProdutoDao();
        setTransacaoDao(requisicaoDao);
        SolicitacaoServicoDTO solicitacaoDto = getSolicitacaoServicoDto();
        RequisicaoProdutoDTO requisicaoDto = new RequisicaoProdutoDTO();
        requisicaoDto.setIdSolicitacaoServico(solicitacaoDto.getIdSolicitacaoServico());
        requisicaoDto = (RequisicaoProdutoDTO) requisicaoDao.restore(requisicaoDto);
        Reflexao.copyPropertyValues(solicitacaoDto, requisicaoDto);
        return requisicaoDto;
    }
    
    public CentroResultadoDTO recuperaCentroCusto(RequisicaoProdutoDTO requisicaoProdutoDto) throws Exception {
        CentroResultadoDTO centroCustoDto = new CentroResultadoDTO();
        centroCustoDto.setIdCentroResultado(requisicaoProdutoDto.getIdCentroCusto());
        return (CentroResultadoDTO) new CentroResultadoDao().restore(centroCustoDto);
    }
    
	@Override
	public InstanciaFluxo inicia() throws Exception {
		return super.inicia();
	}
	
    @Override
    public InstanciaFluxo inicia(FluxoDTO fluxoDto, Integer idFase) throws Exception {
        String idGrupo = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_REQ_PRODUTOS, null);
        if (idGrupo == null || idGrupo.trim().equals(""))
            throw new Exception("Grupo padrão de requisição de produtos não parametrizado");
        getSolicitacaoServicoDto().setIdGrupoAtual(new Integer(idGrupo.trim()));
        return super.inicia(fluxoDto,idFase);
    }
    
    @Override
    public void mapObjetoNegocio(Map<String, Object> map) throws Exception {
        super.mapObjetoNegocio(map);
    }    
    
    @Override
    public void executaEvento(EventoFluxoDTO eventoFluxoDto) throws Exception {
        super.executaEvento(eventoFluxoDto);
    }
    
    @Override    
    public void complementaInformacoesEmail(SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
        super.complementaInformacoesEmail(solicitacaoServicoDto);
        
        StringBuffer strItens = new StringBuffer();
        RequisicaoProdutoDTO requisicaoDto = recuperaRequisicaoProduto();
        if (requisicaoDto.getFinalidade().equals("C"))
            strItens.append("<b>Finalidade: </b>Atendimento ao cliente<br>");
        else
            strItens.append("<b>Finalidade: </b>Uso interno<br>");
        strItens.append("<b>Centro de custo: </b>"+requisicaoDto.getCentroCusto()+"<br>");
        strItens.append("<b>Projeto: </b>"+requisicaoDto.getProjeto()+"<br>");
        ItemRequisicaoProdutoDao itemRequisicaoProdutoDao = new ItemRequisicaoProdutoDao();
        setTransacaoDao(itemRequisicaoProdutoDao);
        Collection<ItemRequisicaoProdutoDTO> colItens = itemRequisicaoProdutoDao.findByIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
        if (colItens != null) {
            strItens.append("<br>");
            strItens.append("<table width='100%'>");
            strItens.append("   <tr>");
            strItens.append("       <th><b>ITENS</b></th>");
            strItens.append("   </tr>");
            strItens.append("</table>");
            strItens.append("<table width='100%' border='1' style='padding: 5px 5px 5px 5px'>");
            strItens.append("   <tr>");
            strItens.append("       <th width='30%'><b>Descrição</b></th>");
            strItens.append("       <th><b>Especificações</b></th>");
            strItens.append("       <th><b>Qtde</b></th>");
            strItens.append("       <th width='15%'><b>Situação atual</b></th>");
            strItens.append("   </tr>");
            for (ItemRequisicaoProdutoDTO itemDto : colItens) {
                strItens.append("   <tr>");
                strItens.append("       <td>"+itemDto.getDescricaoItem()+"</td>");
                strItens.append("       <td>"+UtilStrings.nullToVazio(itemDto.getEspecificacoes())+"</td>");
                strItens.append("       <td>"+UtilFormatacao.formatDouble(itemDto.getQtdeAprovada(),2)+"</td>");
                strItens.append("       <td>"+itemDto.getDescrSituacao()+"</td>");
                strItens.append("   </tr>");
            }
            strItens.append("</table>");
        }
        solicitacaoServicoDto.setInformacoesComplementaresHTML(strItens.toString());
    }

}
