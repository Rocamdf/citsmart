
package br.com.centralit.citcorpore.rh.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.bean.CargosDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CargosService;
import br.com.centralit.citcorpore.rh.bean.CurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.DescricaoCargoDTO;
import br.com.centralit.citcorpore.rh.bean.EntrevistaCandidatoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoPessoalDTO;
import br.com.centralit.citcorpore.rh.bean.TriagemRequisicaoPessoalDTO;
import br.com.centralit.citcorpore.rh.negocio.CurriculoService;
import br.com.centralit.citcorpore.rh.negocio.DescricaoCargoService;
import br.com.centralit.citcorpore.rh.negocio.EntrevistaCandidatoService;
import br.com.centralit.citcorpore.rh.negocio.RequisicaoPessoalService;
import br.com.centralit.citcorpore.rh.negocio.TriagemRequisicaoPessoalService;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.citframework.service.ServiceLocator;

public class TriagemRequisicaoPessoal extends RequisicaoPessoal {

	public String getAcao() {
		 return RequisicaoPessoalDTO.ACAO_TRIAGEM; 
	}

    public void sugereCurriculos(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }
        RequisicaoPessoalDTO requisicaoPessoalDto = (RequisicaoPessoalDTO) document.getBean();
        String certificacao = (requisicaoPessoalDto.getChkCertificacao()!=null)?requisicaoPessoalDto.getChkCertificacao():"";
        String formacao = (requisicaoPessoalDto.getChkFormacao()!=null)?requisicaoPessoalDto.getChkFormacao():"";
        String idioma = (requisicaoPessoalDto.getChkIdioma()!=null)?requisicaoPessoalDto.getChkIdioma():"";
        
        if (requisicaoPessoalDto.getIdSolicitacaoServico() != null) {
	            RequisicaoPessoalService requisicaoPessoalService = (RequisicaoPessoalService) ServiceLocator.getInstance().getService(RequisicaoPessoalService.class, null);
	            requisicaoPessoalDto = (RequisicaoPessoalDTO) requisicaoPessoalService.restore(requisicaoPessoalDto);
        }
        
        requisicaoPessoalDto.setChkCertificacao(certificacao);
        requisicaoPessoalDto.setChkFormacao(formacao);
        requisicaoPessoalDto.setChkIdioma(idioma);
        CargosDTO cargosDto = new CargosDTO();
        cargosDto.setIdCargo(requisicaoPessoalDto.getIdCargo());
        
        CargosService cargosService = (CargosService) ServiceLocator.getInstance().getService(CargosService.class, null);
        cargosDto = (CargosDTO) cargosService.restore(cargosDto);
        
        
        
        DescricaoCargoDTO descricaoCargoDto = new DescricaoCargoDTO();
        descricaoCargoDto.setIdDescricaoCargo(cargosDto.getIdDescricaoCargo());
        
        DescricaoCargoService descricaoCargoService = (DescricaoCargoService) ServiceLocator.getInstance().getService(DescricaoCargoService.class, null);
        descricaoCargoDto = (DescricaoCargoDTO) descricaoCargoService.restore(descricaoCargoDto);
        HTMLTable tblCurriculos = document.getTableById("tblCurriculos");
        tblCurriculos.deleteAllRows();
        
        TriagemRequisicaoPessoalService triagemRequisicaoPessoalService = (TriagemRequisicaoPessoalService) ServiceLocator.getInstance().getService(TriagemRequisicaoPessoalService.class, WebUtil.getUsuarioSistema(request));
        Collection<CurriculoDTO> curriculos = triagemRequisicaoPessoalService.sugereCurriculos(requisicaoPessoalDto,descricaoCargoDto);
        
        TemplatePesquisaCurriculo template = new TemplatePesquisaCurriculo();
        template.adicionarCurriculosPorColecao(document, request, response, curriculos);
        
        if (curriculos != null && !curriculos.isEmpty()) {
            tblCurriculos.addRowsByCollection(curriculos, 
                    new String[] {"","","nome", "",""}, 
                    null, 
                    null,
                    new String[] {"gerarSelecaoCurriculo"}, 
                    null, 
                    null);     
        }
//        document.executeScript("$(\"#POPUP_SUGESTAO_CURRICULOS\").dialog(\"open\");");
    }
    public void triagemManual(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }
        
        RequisicaoPessoalDTO requisicaoPessoalDTO = (RequisicaoPessoalDTO) document.getBean();

        HTMLTable tblCurriculos = document.getTableById("tblCurriculosManuais");
        tblCurriculos.deleteAllRows();
        
        TriagemRequisicaoPessoalService triagemRequisicaoPessoalService = (TriagemRequisicaoPessoalService) ServiceLocator.getInstance().getService(TriagemRequisicaoPessoalService.class, WebUtil.getUsuarioSistema(request));
        Collection<CurriculoDTO> curriculos = triagemRequisicaoPessoalService.triagemManualPorCriterios(requisicaoPessoalDTO);
        CurriculoService curriculoService = (CurriculoService) ServiceLocator.getInstance().getService(CurriculoService.class, null);
        
        Collection<CurriculoDTO> curriculosTriados = new ArrayList<CurriculoDTO>();
        
        if(requisicaoPessoalDTO.getPesquisa_chave() != null && !requisicaoPessoalDTO.getPesquisa_chave().equalsIgnoreCase("")){

        	Gson gson = new Gson();

        	for (CurriculoDTO curriculoDTO : curriculos) {
        		curriculoDTO = (CurriculoDTO) curriculoService.restore(curriculoDTO);
        		String curriculo = gson.toJson(curriculoDTO);
        		String termo = requisicaoPessoalDTO.getPesquisa_chave().toString();
        		boolean valido = StringUtils.contains(curriculo.toLowerCase(), termo.toLowerCase());
        		if(valido){
        			curriculosTriados.add(curriculoDTO);
        		}
        	}
        }else{
        	if(curriculos != null && curriculos.size() > 0){
        		curriculosTriados.addAll(curriculos);
        	}

        }
        
        TemplatePesquisaCurriculo template = new TemplatePesquisaCurriculo();
        template.adicionarCurriculosPorColecao(document, request, response, curriculosTriados);
        
        if (curriculos != null && !curriculos.isEmpty()) {
            tblCurriculos.addRowsByCollection(curriculos, 
                                                new String[] {"","","nome",""}, 
                                                null, 
                                                "",
                                                new String[] {"gerarColecao"}, 
                                                null, 
                                                null);       
        }
       /* document.executeScript("$(\"#POPUP_TRIAGEM_MANUAL\").dialog(\"open\");");*/
    }
    public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null){
              document.alert("Sessão expirada! Favor efetuar logon novamente!");
              return;
        }
        
        List<CurriculoDTO> lstCurriculoAux = new ArrayList<CurriculoDTO>();
        CurriculoService curriculoService = (CurriculoService) ServiceLocator.getInstance().getService(CurriculoService.class, null);
        EntrevistaCandidatoService entrevistaCandidatoService = (EntrevistaCandidatoService) ServiceLocator.getInstance().getService(EntrevistaCandidatoService.class, null);
        EntrevistaCandidatoDTO entrevistaCandidatoDTO = new EntrevistaCandidatoDTO();
        Boolean aprovado = false;
        
        RequisicaoPessoalDTO requisicaoPessoalDto = (RequisicaoPessoalDTO) document.getBean();
        
        if (requisicaoPessoalDto.getIdSolicitacaoServico() != null) {
        	
            RequisicaoPessoalService requisicaoPessoalService = (RequisicaoPessoalService) ServiceLocator.getInstance().getService(RequisicaoPessoalService.class, null);
            requisicaoPessoalDto = (RequisicaoPessoalDTO) requisicaoPessoalService.restore(requisicaoPessoalDto);
            if(requisicaoPessoalDto.getColTriagem() != null ){
            	for (TriagemRequisicaoPessoalDTO triagemRequisicaoPessoalDTO : requisicaoPessoalDto.getColTriagem()) {
            		entrevistaCandidatoDTO.setIdTriagem(triagemRequisicaoPessoalDTO.getIdTriagem());
            		aprovado = entrevistaCandidatoService.seCandidatoAprovado(triagemRequisicaoPessoalDTO);
            		if (aprovado != null && aprovado == false) {
            			CurriculoDTO curriculoDTO = new CurriculoDTO();
	            		curriculoDTO.setIdCurriculo(triagemRequisicaoPessoalDTO.getIdCurriculo());
	            		curriculoDTO = (CurriculoDTO) curriculoService.restore(curriculoDTO);
						lstCurriculoAux.add(curriculoDTO);
            		}
				}
            }
            
            this.preencherComboUfs(document, request, requisicaoPessoalDto);
            this.preencherComboCidade(document, request, requisicaoPessoalDto);
            if(lstCurriculoAux != null ){
            	//Restore de curriculos já triados e adiciona na table caso tenha selecionado gravar e manter tarefa atual
            	this.adicionaCurriculos(document, request, lstCurriculoAux, "restore");
            }
        }
        
        requisicaoPessoalDto.setAcao(getAcao());
        HTMLForm form = document.getForm("form");
        form.setValues(requisicaoPessoalDto);
        
   } 
    public void tratarColecaoTriagem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }

        Collection<CurriculoDTO> colCurriculos = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(CurriculoDTO.class, "curriculos_serialize", request);
        //Adiciona os curriculos através da Pesquisa de curriculos
        adicionaCurriculos(document,request,colCurriculos, "pesquisa");
     }     

    public void adicionaCurriculos(DocumentHTML document, HttpServletRequest request, Collection<CurriculoDTO> colCurriculos, String acao) throws Exception {
        if (colCurriculos != null) {
    		ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
            CurriculoService curriculoService = (CurriculoService) ServiceLocator.getInstance().getService(CurriculoService.class, null);
        	for (CurriculoDTO curriculoDto : colCurriculos) {
        		curriculoDto = (CurriculoDTO) curriculoService.restore(curriculoDto);
        		if (curriculoDto != null) {
        			Collection<ControleGEDDTO> colAnexos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.FOTO_CURRICULO, curriculoDto.getIdCurriculo());
        			String caminhoFoto = "";
        			if (colAnexos != null && colAnexos.size() > 0) {
        				List<UploadDTO> colAnexosUploadDTO = (List<UploadDTO>) controleGedService.convertListControleGEDToUploadDTO(colAnexos);
        				caminhoFoto = colAnexosUploadDTO.get(0).getCaminhoRelativo();
        			}
        			curriculoDto.setCaminhoFoto(caminhoFoto);
        			//Apenas inclui o curriculo se ele não estiver na lista negra
        			if(curriculoDto.getListaNegra() != null && curriculoDto.getListaNegra().equalsIgnoreCase("N") || curriculoDto.getListaNegra() == null){
	        			if(acao.equalsIgnoreCase("pesquisa")){
	        				document.executeScript("parent.incluirCurriculo('" + br.com.citframework.util.WebUtil.serializeObject(curriculoDto) + "');");
	        			}else{
	        				document.executeScript("incluirCurriculo('" + br.com.citframework.util.WebUtil.serializeObject(curriculoDto) + "');");
	        			}
        			}
        		}
			}
        }
     }  
    
}
