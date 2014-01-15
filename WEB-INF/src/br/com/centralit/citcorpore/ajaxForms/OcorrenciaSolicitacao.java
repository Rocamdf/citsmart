/**
 * @author breno.guimaraes
 *
 */

package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.bpm.util.UtilScript;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.bean.AprovacaoSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.CategoriaOcorrenciaDTO;
import br.com.centralit.citcorpore.bean.ContatoSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.JustificativaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.OrigemOcorrenciaDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.CategoriaOcorrenciaDAO;
import br.com.centralit.citcorpore.integracao.OrigemOcorrenciaDAO;
import br.com.centralit.citcorpore.negocio.AprovacaoSolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.JustificativaSolicitacaoService;
import br.com.centralit.citcorpore.negocio.OcorrenciaSolicitacaoService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilHTML;
import br.com.citframework.util.UtilStrings;

import com.google.gson.Gson;
//import br.com.centralit.bpm.negocio.ItemTrabalho;
//import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;


@SuppressWarnings("rawtypes")
public class OcorrenciaSolicitacao  extends AjaxFormAction {    
   
	@Override
    public Class getBeanClass() {
    	return OcorrenciaSolicitacaoDTO.class;
    }
    
    private OcorrenciaSolicitacaoService getService() throws ServiceException, Exception {	
    	OcorrenciaSolicitacaoService ocorrenciaService = (OcorrenciaSolicitacaoService) ServiceLocator.getInstance().getService(OcorrenciaSolicitacaoService.class, null);
    	return ocorrenciaService;
    }
    
    @Override
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	UsuarioDTO usuario = WebUtil.getUsuario(request);
    	
    	if (usuario == null) {
    		document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada") );
    		document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
    		return;
    	}
    	
    	geraComboCategoria(document, request);
    	geraComboOrigem(document, request);
    	document.getElementById("registradopor").setValue(usuario.getNomeUsuario() );
    }
    
    public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	UsuarioDTO usuario = WebUtil.getUsuario(request);
    	
    	if (usuario == null) {
    		document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada")	);
    		document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
    		return;
    	}
    	
    	OcorrenciaSolicitacaoDTO ocorrenciaSolicitacaoDTO = (OcorrenciaSolicitacaoDTO) document.getBean();
    	
    	if (ocorrenciaSolicitacaoDTO.getIdCategoriaOcorrencia() == null) {
    		document.alert(UtilI18N.internacionaliza(request, "MSE03") );
    		return;
    	}
    	
    	if (ocorrenciaSolicitacaoDTO.getIdOrigemOcorrencia() == null) {
    		document.alert(UtilI18N.internacionaliza(request, "MSE04") );
    		return;
    	}
    	
    	ocorrenciaSolicitacaoDTO.setDataInicio(UtilDatas.getDataAtual() ); 	
    	ocorrenciaSolicitacaoDTO.setRegistradopor(usuario.getNomeUsuario() );
    	ocorrenciaSolicitacaoDTO.setDataregistro(UtilDatas.getDataAtual() );
    	ocorrenciaSolicitacaoDTO.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual() ) );
    	ocorrenciaSolicitacaoDTO.setIdSolicitacaoServico(ocorrenciaSolicitacaoDTO.getIdSolicitacaoOcorrencia() );
    	getService().create(ocorrenciaSolicitacaoDTO);
    	document.alert(UtilI18N.internacionaliza(request, "MSG05") );
    	listOcorrenciasSituacao(document, request, response);
    	document.executeScript("$('#POPUP_menuOcorrencias').dialog('close');");
    }
    
    public void listOcorrenciasSituacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {    	
    	UsuarioDTO usuario = WebUtil.getUsuario(request);
    	
    	if (usuario == null) {
    		document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada") );
    		document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
    		return;
    	}
    	
    	CategoriaOcorrenciaDAO categoriaOcorrenciaDAO = new CategoriaOcorrenciaDAO();
    	OrigemOcorrenciaDAO origemOcorrenciaDAO = new OrigemOcorrenciaDAO();
    	
    	CategoriaOcorrenciaDTO categoriaOcorrenciaDTO = (CategoriaOcorrenciaDTO) categoriaOcorrenciaDAO.getBean().newInstance();
    	OrigemOcorrenciaDTO origemOcorrenciaDTO = (OrigemOcorrenciaDTO) origemOcorrenciaDAO.getBean().newInstance();
    	
    	OcorrenciaSolicitacaoDTO bean = (OcorrenciaSolicitacaoDTO)document.getBean();
    	OcorrenciaSolicitacaoService ocorrenciaService = getService();
    	Collection col = ocorrenciaService.findByIdSolicitacaoServico(bean.getIdSolicitacaoOcorrencia() );
    	
    	StringBuilder stringBuilder = new StringBuilder();
    	
    	stringBuilder.append("<table class='dynamicTable table table-striped table-bordered table-condensed dataTable' style='table-layout: fixed;'>");
    	stringBuilder.append("<tr>");
    	stringBuilder.append("<td class=''>");
    	stringBuilder.append(UtilI18N.internacionaliza(request, "ocorrenciaSolicitacao.codigoocorrencia"));
    	stringBuilder.append("</td>");
    	stringBuilder.append("<td class=''>");
    	stringBuilder.append(UtilI18N.internacionaliza(request, "ocorrenciaSolicitacao.informacaoocorrencia"));
    	stringBuilder.append("</td>");
    	stringBuilder.append("<td class=''>");
    	stringBuilder.append(UtilI18N.internacionaliza(request, "citcorpore.comum.categoria"));
    	stringBuilder.append("</td>");
    	stringBuilder.append("<td class=''>");
    	stringBuilder.append(UtilI18N.internacionaliza(request, "citcorpore.comum.origem"));
    	stringBuilder.append("</td>");   
    	stringBuilder.append("<td class=''>");
    	stringBuilder.append(UtilI18N.internacionaliza(request, "ocorrenciaSolicitacao.tempoGasto"));
    	stringBuilder.append("</td>");
    	stringBuilder.append("</tr>");
		
		UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
				
		if (col != null && col.size() > 0) {			
			JustificativaSolicitacaoService justificativaService = (JustificativaSolicitacaoService) ServiceLocator.getInstance().getService(JustificativaSolicitacaoService.class, null);
            AprovacaoSolicitacaoServicoService aprovacaoService = (AprovacaoSolicitacaoServicoService) ServiceLocator.getInstance().getService(AprovacaoSolicitacaoServicoService.class, null);
            EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);

            for (Iterator it = col.iterator(); it.hasNext(); ) {
				OcorrenciaSolicitacaoDTO ocorrenciaSolicitacaoAux = (OcorrenciaSolicitacaoDTO) it.next();				
				String ocorrencia = UtilStrings.nullToVazio(ocorrenciaSolicitacaoAux.getOcorrencia() );
		
				if (ocorrenciaSolicitacaoAux.getIdJustificativa() != null) {
					JustificativaSolicitacaoDTO justificativaDto = new JustificativaSolicitacaoDTO();
					justificativaDto.setIdJustificativa(ocorrenciaSolicitacaoAux.getIdJustificativa() );
					justificativaDto = (JustificativaSolicitacaoDTO) justificativaService.restore(justificativaDto);
					if (justificativaDto != null) 
						ocorrencia += "<br>" + UtilI18N.internacionaliza(request, "citcorpore.comum.justificativa") + ": " + justificativaDto.getDescricaoJustificativa() + "<br>";
				}
				
				if (ocorrenciaSolicitacaoAux.getComplementoJustificativa() != null) 
					ocorrencia += "<br>" + UtilI18N.internacionaliza(request, "gerenciaservico.mudarsla.complementojustificativa") 
					+ ": <b>" + ocorrenciaSolicitacaoAux.getComplementoJustificativa() + "<br>";
		
				String dadosSolicitacao = UtilStrings.nullToVazio(ocorrenciaSolicitacaoAux.getDadosSolicitacao() );
		
				SolicitacaoServicoDTO solicitacaoDto = null;
				if (dadosSolicitacao.length() > 0) {					
					try {
						solicitacaoDto = new Gson().fromJson(dadosSolicitacao,SolicitacaoServicoDTO.class);
						
						if (solicitacaoDto != null)
							dadosSolicitacao = solicitacaoDto.getDadosStr();
					} catch (Exception e) {
						dadosSolicitacao = "";
					}
				}
				
				String descricao = UtilStrings.nullToVazio(ocorrenciaSolicitacaoAux.getDescricao() ) ;
				String informacoesContato = UtilStrings.nullToVazio(ocorrenciaSolicitacaoAux.getInformacoesContato() );
		
				if (informacoesContato.length() > 0) {
					try {
						ContatoSolicitacaoServicoDTO contatoDto = new Gson().fromJson(informacoesContato, ContatoSolicitacaoServicoDTO.class);
						if (contatoDto != null)
							informacoesContato = contatoDto.getDadosStr();
					} catch (Exception e) {
						informacoesContato = "";
					}	
				}
				
				String aprovacao = "";
				if (solicitacaoDto != null && solicitacaoDto.getIdUltimaAprovacao() != null && ocorrenciaSolicitacaoAux.getIdItemTrabalho() != null) {
				    AprovacaoSolicitacaoServicoDTO aprovacaoDto = new AprovacaoSolicitacaoServicoDTO();
				    aprovacaoDto.setIdAprovacaoSolicitacaoServico(solicitacaoDto.getIdUltimaAprovacao());
				    aprovacaoDto = (AprovacaoSolicitacaoServicoDTO) aprovacaoService.restore(aprovacaoDto);
				    if (aprovacaoDto.getIdTarefa() != null && aprovacaoDto.getIdTarefa().intValue() == ocorrenciaSolicitacaoAux.getIdItemTrabalho().intValue()) {
				        EmpregadoDTO empregadoDto = empregadoService.restoreByIdEmpregado(aprovacaoDto.getIdResponsavel());
				        if (empregadoDto != null)
	                        aprovacao += UtilI18N.internacionaliza(request, "citcorpore.comum.aprovador") + ": " + empregadoDto.getNome() + "<br>";
				        aprovacao += UtilI18N.internacionaliza(request, "citcorpore.comum.aprovada") + ": ";
				        if (aprovacaoDto.getAprovacao().equalsIgnoreCase("A"))
				            aprovacao += "Sim";
				        else
				            aprovacao += "Não";
				        if (aprovacaoDto.getIdJustificativa() != null) {
		                    JustificativaSolicitacaoDTO justificativaDto = new JustificativaSolicitacaoDTO();
		                    justificativaDto.setIdJustificativa(aprovacaoDto.getIdJustificativa() );
		                    justificativaDto = (JustificativaSolicitacaoDTO) justificativaService.restore(justificativaDto);
		                    if (justificativaDto != null) 
		                        aprovacao += "<br>" + UtilI18N.internacionaliza(request, "citcorpore.comum.justificativa") + ": " + justificativaDto.getDescricaoJustificativa();
				        }
		                if (aprovacaoDto.getComplementoJustificativa() != null && aprovacaoDto.getComplementoJustificativa().trim().length() > 0) {
		                    aprovacao += "<br>" + UtilI18N.internacionaliza(request, "gerenciaservico.mudarsla.complementojustificativa") 
		                    + ": " + aprovacaoDto.getComplementoJustificativa();
		                }
                        if (aprovacaoDto.getObservacoes() != null && aprovacaoDto.getObservacoes().trim().length() > 0) {
                            aprovacao += "<br>" + UtilI18N.internacionaliza(request, "citcorpore.comum.observacoes") 
                            + ": " + StringEscapeUtils.unescapeJavaScript(aprovacaoDto.getObservacoes());
                        }
				    }
				}
				
				ocorrencia = ocorrencia.replaceAll("\"", "");
				descricao = descricao.replaceAll("\"", "");
				informacoesContato = informacoesContato.replaceAll("\"", "");
				ocorrencia = ocorrencia.replaceAll("\n", "<br>");
				descricao = descricao.replaceAll("\n", "<br>");
				informacoesContato = informacoesContato.replaceAll("\n", "<br>");
				dadosSolicitacao = dadosSolicitacao.replaceAll("\n", "<br>");		
				ocorrencia = UtilHTML.encodeHTML(ocorrencia.replaceAll("\'", "") );
				descricao = UtilHTML.encodeHTML(descricao.replaceAll("\'", "") );				
				informacoesContato = UtilHTML.encodeHTML(informacoesContato.replaceAll("\'", "") );
				
				stringBuilder.append("<tr>");
				stringBuilder.append("<td rowspan='4' >");
				stringBuilder.append("<b>" + ocorrenciaSolicitacaoAux.getIdOcorrencia() + "</b>");
				stringBuilder.append("</td>");
				stringBuilder.append("<td >");
				stringBuilder.append("<b>" + UtilDatas.dateToSTR(ocorrenciaSolicitacaoAux.getDataregistro() ) + " - " + ocorrenciaSolicitacaoAux.getHoraregistro());
	        	String strRegPor = ocorrenciaSolicitacaoAux.getRegistradopor();
	        	try{
	        		if (ocorrenciaSolicitacaoAux.getRegistradopor() != null && !ocorrenciaSolicitacaoAux.getRegistradopor().trim().equalsIgnoreCase("Automático")){
		        		UsuarioDTO usuarioDto = usuarioService.restoreByLogin(ocorrenciaSolicitacaoAux.getRegistradopor());
		        		if (usuarioDto != null){
		        			EmpregadoDTO empregadoDto = empregadoService.restoreByIdEmpregado(usuarioDto.getIdEmpregado());
		        			strRegPor = strRegPor + " - " + empregadoDto.getNome();
		        		}
	        		}
	        	}catch(Exception e){}
	        	if (ocorrenciaSolicitacaoAux.getRegistradopor() != null)
	        		stringBuilder.append( " - </b>" + UtilI18N.internacionaliza(request, "ocorrenciaSolicitacao.registradopor") + ": <b>" + strRegPor + "</b>");
	        	stringBuilder.append("</td>");
	        	
	        	// Categoria Ocorrência
	        	stringBuilder.append("<td >");
	        	if (ocorrenciaSolicitacaoAux.getIdCategoriaOcorrencia() != null && ocorrenciaSolicitacaoAux.getIdCategoriaOcorrencia() != 0) {
	        		categoriaOcorrenciaDTO.setIdCategoriaOcorrencia(ocorrenciaSolicitacaoAux.getIdCategoriaOcorrencia() );
	        		categoriaOcorrenciaDTO = (CategoriaOcorrenciaDTO) categoriaOcorrenciaDAO.restore(categoriaOcorrenciaDTO);
	        		stringBuilder.append(UtilI18N.internacionaliza(request, "citcorpore.comum.categoria") + ": <b>" + categoriaOcorrenciaDTO.getNome() + "</b>");
	        	} else {	        		
	        		stringBuilder.append(UtilI18N.internacionaliza(request, "citcorpore.comum.categoria") + ": ");
	        	}				
	        	stringBuilder.append("</td>");
				
	        	// Origem Ocorrência
	        	stringBuilder.append( "<td >");
				if (ocorrenciaSolicitacaoAux.getIdOrigemOcorrencia() != null && ocorrenciaSolicitacaoAux.getIdOrigemOcorrencia() != 0) {
					origemOcorrenciaDTO.setIdOrigemOcorrencia(ocorrenciaSolicitacaoAux.getIdOrigemOcorrencia() );
					origemOcorrenciaDTO = (OrigemOcorrenciaDTO)	origemOcorrenciaDAO.restore(origemOcorrenciaDTO);
					stringBuilder.append( UtilI18N.internacionaliza(request, "origemAtendimento.origem") + ": <b>" + origemOcorrenciaDTO.getNome() + "</b>");
				} else {
					stringBuilder.append( UtilI18N.internacionaliza(request, "origemAtendimento.origem") + ": ");
				}
				stringBuilder.append("</td>");
				
				stringBuilder.append("<td >");
				stringBuilder.append( UtilI18N.internacionaliza(request, "ocorrenciaSolicitacao.tempoGasto") + ": <b><br />" + (ocorrenciaSolicitacaoAux.getTempoGasto() != null ? "" 
				+ ocorrenciaSolicitacaoAux.getTempoGasto() + " min" : "--") + "</b>");
				stringBuilder.append("</td>");			
				stringBuilder.append("</tr>");
		
				if (dadosSolicitacao == null || dadosSolicitacao.trim().equalsIgnoreCase("") ) {
					stringBuilder.append("<tr>");
					stringBuilder.append("<td colspan='4' style='word-wrap: break-word;overflow:hidden;' >");
					stringBuilder.append(UtilI18N.internacionaliza(request, "citcorpore.comum.descricao") + ": <b>" +  StringEscapeUtils.unescapeJavaScript(descricao) + "<br><br></b>");
					stringBuilder.append("</td>");		
					stringBuilder.append("</tr>");
				} else {
					stringBuilder.append("<tr>");
					stringBuilder.append("<td colspan='4' style='word-wrap: break-word;overflow:hidden;' >");
    				
					stringBuilder.append(UtilI18N.internacionaliza(request, "citcorpore.comum.descricao") + ": <b>" + StringEscapeUtils.unescapeJavaScript(descricao) + "<br><br></b>");

                    if (dadosSolicitacao != null && !dadosSolicitacao.trim().equalsIgnoreCase("") ) {
                    	stringBuilder.append(UtilI18N.internacionaliza(request, "ocorrenciaSolicitacao.dadosolicitacao") + ": <b><br>" + StringEscapeUtils.unescapeJavaScript(dadosSolicitacao) + "<br><br></b>");
    				}
    				
                    stringBuilder.append("</td>");
                    stringBuilder.append("</tr>");
				}
			
			
				if (ocorrencia.length() > 0) {
					stringBuilder.append("<tr>");
					stringBuilder.append("<td colspan='4' style='word-wrap: break-word;overflow:hidden;' >");
					stringBuilder.append(UtilI18N.internacionaliza(request, "citcorpore.comum.ocorrencia") + ": <br><b>" + StringEscapeUtils.unescapeJavaScript(ocorrencia) + "<br><br></b>");
					if (aprovacao.length() > 0)
						stringBuilder.append("<b>" + aprovacao + "<br><br></b>");
						stringBuilder.append("</td>");		
						stringBuilder.append("</tr>");
				}else{
					stringBuilder.append("<tr>");
					stringBuilder.append("<td colspan='4' style='word-wrap: break-word;overflow:hidden;' >");
					stringBuilder.append("&nbsp;");
					stringBuilder.append("</td>");       
					stringBuilder.append("</tr>");
				}

                if (informacoesContato == null || informacoesContato.trim().equalsIgnoreCase("") ) {
                	stringBuilder.append("<tr>");
                	stringBuilder.append("<td colspan='4' style='word-wrap: break-word;overflow:hidden;' >");
        			if (ocorrenciaSolicitacaoAux.getInformacoesContato() != null && ocorrenciaSolicitacaoAux.getInformacoesContato().length() > 0) 
        				stringBuilder.append(UtilI18N.internacionaliza(request, "citcorpore.comum.informacaoContato") + ": <br><b>" + StringEscapeUtils.unescapeJavaScript(ocorrenciaSolicitacaoAux.getInformacoesContato()) + "<br><br></b>");
        			else
        				stringBuilder.append("&nbsp;");
        				stringBuilder.append("</td>");
        				stringBuilder.append("</tr>");
				} else {
					stringBuilder.append("<tr>");
					stringBuilder.append("<td colspan='4' style='word-wrap: break-word;overflow:hidden;' >");
        			if (informacoesContato.length() > 0) 
        				stringBuilder.append( UtilI18N.internacionaliza(request, "citcorpore.comum.informacaoContato") + ": <br><b>" + StringEscapeUtils.unescapeJavaScript(informacoesContato) + "<br><br></b>");
        			else
        				stringBuilder.append("&nbsp;");
        				stringBuilder.append("</td>");
        				stringBuilder.append("</tr>");
				}
			}
		} else {
			stringBuilder.append("<table width='100%' class='dynamicTable table table-striped table-bordered table-condensed dataTable' >");
			stringBuilder.append("<tr>");
			stringBuilder.append("<td colspan='4' >");
			stringBuilder.append("<b>" + UtilI18N.internacionaliza(request, "citcorpore.comum.naoinformacao") + "</b>");
			stringBuilder.append("</td>");		
			stringBuilder.append("</tr>");	    
		}
		stringBuilder.append( "</table>");	
		document.getElementById("divRelacaoOcorrencias").setInnerHTML(stringBuilder.toString());
    }
    
    public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	
	OcorrenciaSolicitacaoDTO ocorrencia = (OcorrenciaSolicitacaoDTO) document.getBean();
	ocorrencia = (OcorrenciaSolicitacaoDTO) getService().restore(ocorrencia);
	HTMLForm form = document.getForm("formOcorrenciaSolicitacao");
	form.clear();
	form.setValues(ocorrencia);
    }
    
    private void geraComboCategoria(DocumentHTML document, HttpServletRequest request) throws Exception {
    	HTMLSelect comboTipoDemanda = (HTMLSelect) document.getSelectById("categoria");
    	comboTipoDemanda.removeAllOptions();
    	comboTipoDemanda.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione") );
    	
    	for (Enumerados.CategoriaOcorrencia c : Enumerados.CategoriaOcorrencia.values() ) {
    		if (!c.getSigla().toString().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.MudancaSLA.getSigla().toString() ) && 
    				!c.getSigla().toString().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.Reclassificacao.getSigla().toString() ) &&
    				!c.getSigla().toString().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.Agendamento.getSigla().toString() ) &&
    				!c.getSigla().toString().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.Suspensao.getSigla().toString() ) &&
    				!c.getSigla().toString().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.Reativacao.getSigla().toString() ) &&
    				!c.getSigla().toString().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.Encerramento.getSigla().toString() ) &&
    				!c.getSigla().toString().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.Reabertura.getSigla().toString() ) &&
    				!c.getSigla().toString().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.Direcionamento.getSigla().toString() ) &&
    				!c.getSigla().toString().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.Compartilhamento.getSigla().toString() ) &&
    				!c.getSigla().toString().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.Criacao.getSigla().toString() ) ) {
    			comboTipoDemanda.addOption(c.getSigla().toString(), c.getDescricao());
    		}
    	}
    }
    
    private void geraComboOrigem(DocumentHTML document, HttpServletRequest request) throws Exception {
    	HTMLSelect comboTipoDemanda = (HTMLSelect) document.getSelectById("origem");
    	comboTipoDemanda.removeAllOptions();
    	comboTipoDemanda.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione") );
	
    	for(Enumerados.OrigemOcorrencia c : Enumerados.OrigemOcorrencia.values() ) {
    		comboTipoDemanda.addOption(c.getSigla().toString(), c.getDescricao() );
    	}
    }
}
