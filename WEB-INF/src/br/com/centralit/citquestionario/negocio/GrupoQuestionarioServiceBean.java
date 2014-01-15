package br.com.centralit.citquestionario.negocio;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.negocio.ContratoQuestionariosService;
import br.com.centralit.citquestionario.bean.LinhaSpoolQuestionario;
import br.com.centralit.citquestionario.bean.ListagemDTO;
import br.com.centralit.citquestionario.bean.ListagemLinhaDTO;
import br.com.centralit.citquestionario.bean.OpcaoRespostaQuestionarioDTO;
import br.com.centralit.citquestionario.bean.QuestaoQuestionarioDTO;
import br.com.centralit.citquestionario.bean.QuestionarioDTO;
import br.com.centralit.citquestionario.bean.RespostaItemQuestionarioAnexosDTO;
import br.com.centralit.citquestionario.bean.RespostaItemQuestionarioDTO;
import br.com.centralit.citquestionario.bean.RespostaItemQuestionarioOpcoesDTO;
import br.com.centralit.citquestionario.integracao.GrupoQuestionarioDao;
import br.com.centralit.citquestionario.util.ListagemConfig;
import br.com.centralit.citquestionario.util.RenderDynamicForm;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilHTML;

public class GrupoQuestionarioServiceBean extends CrudServicePojoImpl implements GrupoQuestionarioService{
    String HTML_TABLE_START = 
        "<table width=\"100%\" style='border:1px solid black'>" +
        "    <tr>" +
        "        <td>" +
        "            <b>Data de Início</b>" +
        "        </td>" +
        "        <td>" +
        "            <b>Data Fim</b>" +
        "        </td>" +
        "        <td>" +
        "            <b>Qtde Dias</b>" +
        "        </td>" +
        "    </tr>";
    String HTML_TABLE_LINE = 
        "    <tr>" +
        "        <td style='text-align:center'>{0}</td>" +
        "        <td style='text-align:center'>{1}</td>" +
        "        <td style='text-align:center'>{2}</td>" +
        "    </tr>";
    String HTML_TABLE_END = 
        "</table>";
    
    String HTML_TABLE_START_ENCAMINHAMENTO = 
    	"<table width=\"100%\" style='border:1px solid black'>";

    String HTML_TABLE_LINE_ENCAMINHAMENTO = 
    	"    <tr>" +
    	"        <td>{0}</td>" +
    	"    </tr>";
    String HTML_TABLE_END_ENCAMINHAMENTO = 
    	"</table>";
	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;

	protected CrudDAO getDao() throws ServiceException {
		
		return new GrupoQuestionarioDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
		
	}

	protected void validaDelete(Object arg0) throws Exception {
		
	}

	protected void validaFind(Object arg0) throws Exception {
		
	}

	protected void validaUpdate(Object arg0) throws Exception {
		
	}
	
	public Collection listByIdQuestionario(Integer idQuestionario) throws Exception{
		GrupoQuestionarioDao grupoQuestionarioDao = new GrupoQuestionarioDao();
		return grupoQuestionarioDao.listByIdQuestionario(idQuestionario);
	}
	
	public Collection geraImpressao(Collection colQuestoes){
		LinhaSpoolQuestionario linhaSpoolQuestionario = new LinhaSpoolQuestionario();
		String avanco = "     ";
		Collection colLinhas = new ArrayList(); 
		if (colQuestoes != null){
			for (Iterator itQuest = colQuestoes.iterator(); itQuest.hasNext();){
				QuestaoQuestionarioDTO questaoDto = (QuestaoQuestionarioDTO)itQuest.next();
				if (questaoDto.getImprime() != null && !questaoDto.getImprime().trim().equalsIgnoreCase("S")) {
				    continue;
				}
				
				if (questaoDto.getTextoInicial() != null && !questaoDto.getTextoInicial().trim().equalsIgnoreCase("")){
					linhaSpoolQuestionario = new LinhaSpoolQuestionario(avanco + questaoDto.getTextoInicial());
					colLinhas.add(linhaSpoolQuestionario);
				}
				
				String value = "";
				String value2 = "";
				String value3 = "";
				
				String bufferQuestao = "";
				String dadosAdicionais = "";
				
				String infoObr = "";
				
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("T") || questaoDto.getTipoQuestao().equalsIgnoreCase("A")){ //Texto
					if (questaoDto.getRespostaItemDto()!=null){ 
						value = questaoDto.getRespostaItemDto().getRespostaTextual();
					}						
				}else if (questaoDto.getTipoQuestao().equalsIgnoreCase("D")){ //Data
					if (questaoDto.getRespostaItemDto()!=null){
						if (questaoDto.getRespostaItemDto().getRespostaData() != null){
							value = UtilDatas.dateToSTR(questaoDto.getRespostaItemDto().getRespostaData());
						}
					}						
				}else if (questaoDto.getTipoQuestao().equalsIgnoreCase("H")){ //Data
					if (questaoDto.getRespostaItemDto()!=null){
						if (questaoDto.getRespostaItemDto().getRespostaHora() != null){
							value = UtilDatas.formatHoraStr(questaoDto.getRespostaItemDto().getRespostaHora());
						}
					}						
				}else if (questaoDto.getTipoQuestao().equalsIgnoreCase("N")){ //Número (sem casas decimais)
					if (questaoDto.getRespostaItemDto()!=null){
						if (questaoDto.getRespostaItemDto().getRespostaNumero() != null){
							value = UtilFormatacao.formatDouble(questaoDto.getRespostaItemDto().getRespostaNumero(), 0);
						}
					}						
				}else if (questaoDto.getTipoQuestao().equalsIgnoreCase("V") || questaoDto.getTipoQuestao().equalsIgnoreCase("%")){ //Valor Decimal e %
					if (questaoDto.getRespostaItemDto()!=null){
						int qtdeDecimais = 0;
						if (questaoDto.getDecimais()!=null){
							qtdeDecimais = questaoDto.getDecimais().intValue();
						}
						if (questaoDto.getRespostaItemDto().getRespostaValor() != null){
							value = UtilFormatacao.formatDouble(questaoDto.getRespostaItemDto().getRespostaValor(), qtdeDecimais);
						}
					}						
				}
				
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("1")){ //Faixa de Números (sem casas decimais)
					if (questaoDto.getRespostaItemDto()!=null){
						value = UtilFormatacao.formatDouble(questaoDto.getRespostaItemDto().getRespostaValor(), 0);
						value2 = UtilFormatacao.formatDouble(questaoDto.getRespostaItemDto().getRespostaValor2(), 0);
					}						
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("2")){ //Faixa de Valores (com casas decimais)
					if (questaoDto.getRespostaItemDto()!=null){
						int qtdeDecimais = 0;
						if (questaoDto.getDecimais()!=null){
							qtdeDecimais = questaoDto.getDecimais().intValue();
						}
						value = UtilFormatacao.formatDouble(questaoDto.getRespostaItemDto().getRespostaValor(), qtdeDecimais);
						value2 = UtilFormatacao.formatDouble(questaoDto.getRespostaItemDto().getRespostaValor2(), qtdeDecimais);
					}						
				}

				if (questaoDto.getTipoQuestao().equalsIgnoreCase("G")){ //Mes/Ano
					if (questaoDto.getRespostaItemDto()!=null){
						if (questaoDto.getRespostaItemDto().getRespostaMes() != null){
							value = UtilFormatacao.formatInt(questaoDto.getRespostaItemDto().getRespostaMes().intValue(), "00");
						}
						if (questaoDto.getRespostaItemDto().getRespostaAno() != null){
							value2 = UtilFormatacao.formatInt(questaoDto.getRespostaItemDto().getRespostaAno().intValue(), "0000");
						}
					}						
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("*")){ //Percentual e Valor
					if (questaoDto.getRespostaItemDto()!=null){
						if (questaoDto.getRespostaItemDto().getRespostaPercentual() != null){
							value = UtilFormatacao.formatDouble(questaoDto.getRespostaItemDto().getRespostaPercentual(), 2);
						}
						if (questaoDto.getRespostaItemDto().getRespostaValor() != null){
							value = UtilFormatacao.formatDouble(questaoDto.getRespostaItemDto().getRespostaValor(), 2);
						}
					}						
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("L")){ //Campo Longo - Editor
					if (questaoDto.getRespostaItemDto()!=null){
						value = questaoDto.getRespostaItemDto().getRespostaTextual();
					}		 				
				}
				
				String tituloQuestao = "";
				if (questaoDto.getTituloQuestaoQuestionario() != null) {
    				tituloQuestao = questaoDto.getTituloQuestaoQuestionario();
    				tituloQuestao = tituloQuestao.trim();
    				tituloQuestao = tituloQuestao.replaceAll("<p>", "");
    				tituloQuestao = tituloQuestao.replaceAll("</p>", "");
    				
    				if (tituloQuestao.length() > 5){
    					if (tituloQuestao.substring(tituloQuestao.length()-5, tituloQuestao.length()).equalsIgnoreCase("</br>")){
    						tituloQuestao = tituloQuestao.substring(0, tituloQuestao.length()-5) 
    										+ infoObr;
    										//+ ":";
    					}else{
    						tituloQuestao = tituloQuestao + infoObr; // + ":";
    					}
    				}else{
    					tituloQuestao = tituloQuestao + infoObr; // + ":";
    				}
				}
				
				if (questaoDto.getTipo() != null && (questaoDto.getTipo().equalsIgnoreCase("T") || questaoDto.getTipoQuestao().equalsIgnoreCase("F"))){ //Texto Fixo
					//bufferQuestao += avanco + tituloQuestao;
					bufferQuestao = ""; //NAO SERA IMPRESSO O TEXTO FIXO.
				}else{
					if ("B".equalsIgnoreCase(questaoDto.getInfoResposta())){ //Em baixo da pergunta
						bufferQuestao += avanco + tituloQuestao;
						
						bufferQuestao += avanco;
					}else{
						bufferQuestao += avanco + tituloQuestao;
					}					
					if (questaoDto.getUnidade()!=null && !questaoDto.getUnidade().equalsIgnoreCase("")){
						dadosAdicionais += " " + questaoDto.getUnidade();
					}
					if (questaoDto.getValoresReferencia()!=null && !questaoDto.getValoresReferencia().equalsIgnoreCase("")){
						dadosAdicionais += questaoDto.getValoresReferencia() + "";
					}					
				}
				
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("T")){ //Texto Curto (Tamanho delimitado)
					bufferQuestao += value + dadosAdicionais;				
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("A")){ //Texto Longo (Campo de observações, etc.)
					bufferQuestao += value + dadosAdicionais;									
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("L")){ //Texto Longo com Editor (Campo de Laudos, etc.)
					bufferQuestao += value + dadosAdicionais;
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("R") ||
						questaoDto.getTipoQuestao().equalsIgnoreCase("C") ||
						questaoDto.getTipoQuestao().equalsIgnoreCase("X")){ 
					if (questaoDto.getColOpcoesResposta()!=null){
						boolean b = false;
						String aux = "";
						/*
						if ("B".equalsIgnoreCase(questaoDto.getInfoResposta())){ //Em baixo da pergunta
							aux = "\n";
						}	
						*/						
						String bufferOut = aux;
						for(Iterator itOpcResp = questaoDto.getColOpcoesResposta().iterator(); itOpcResp.hasNext();){
							OpcaoRespostaQuestionarioDTO opcRespDto =  (OpcaoRespostaQuestionarioDTO)itOpcResp.next();
							
							if (questaoDto.getRespostaItemDto()!=null){
								if (isInCollection(opcRespDto.getIdOpcaoRespostaQuestionario(), questaoDto.getRespostaItemDto().getColOpcoesResposta())){
									if (b){
										bufferOut += ", ";
									}
									bufferOut += opcRespDto.getTitulo().replaceAll("<br>","");	
									b = true;
								}
							}
						}
						bufferOut += "" + dadosAdicionais;
						bufferOut = bufferOut.replaceAll("\n", "");
						
						bufferQuestao += bufferOut;																					
					}
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("N")){ //Número (sem casas decimais)
					bufferQuestao += value + dadosAdicionais;										
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("V")){ //Valor Decimal
					bufferQuestao += value + dadosAdicionais;										
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("%")){ //% (Percentual)
					bufferQuestao += value + dadosAdicionais;															
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("*")){ //% (Percentual)  +   Valor Absoluto
					bufferQuestao += value + " " + value2 + dadosAdicionais;														
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("1")){ //Faixa de Valores (Números)
					bufferQuestao += value + " " + value2 + dadosAdicionais;																			
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("2")){ //Faixa de Valores (Valores decimais)
					bufferQuestao += value + " " + value2 + dadosAdicionais;																									
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("I")){ //Galeria de Imagens
																														
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("M")){ //Galeria Multimídia (Videos,etc.)			}
																																		
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("D")){ //Data
					bufferQuestao += value + dadosAdicionais;				
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("H")){ //Hora
					bufferQuestao += value + dadosAdicionais;				
				}	
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("P")){ //Pressao Arterial
					bufferQuestao += value + "/" + value2 + dadosAdicionais;																			
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("!")){ //Peso, Altura e IMC
					bufferQuestao += value + " " + value2 + " " + value3 + dadosAdicionais;
				}	
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("G")){ //Mes/Ano
					bufferQuestao += value + "/" + value2 + dadosAdicionais;																				
				}
				
				linhaSpoolQuestionario = new LinhaSpoolQuestionario(UtilHTML.decodeHTML(UtilHTML.retiraFormatacaoHTML(bufferQuestao)));
				colLinhas.add(linhaSpoolQuestionario);				
			}
		}
		return colLinhas;
	}
	private String montaLinhaQuestao(QuestaoQuestionarioDTO questaoDto){
		Collection colQ = new ArrayList();
		colQ.add(questaoDto);
		Collection colSaida = geraImpressaoFormatadaHTML(colQ, null, null, null);
		String saida = "";
		if (colSaida != null){
			for(Iterator it = colSaida.iterator(); it.hasNext();){
				LinhaSpoolQuestionario linhaSpoolQuestionario =  (LinhaSpoolQuestionario)it.next();
				saida += linhaSpoolQuestionario.getLinha();
			}
		}
		return saida;
	}
    private String montaTituloQuestao(QuestaoQuestionarioDTO questaoDto){
        String tituloQuestao = "";
        if (questaoDto.getTituloQuestaoQuestionario() != null) {
            tituloQuestao = questaoDto.getTituloQuestaoQuestionario();
            if (!tituloQuestao.equalsIgnoreCase("")){
    
                String infoObr = "";
                
                tituloQuestao = tituloQuestao.trim();
                tituloQuestao = tituloQuestao.replaceAll("<p>", "");
                tituloQuestao = tituloQuestao.replaceAll("</p>", "</br>");
                
                if (tituloQuestao.length() > 5 && tituloQuestao.substring(tituloQuestao.length()-5, tituloQuestao.length()).equalsIgnoreCase("</br>")){
                    tituloQuestao = tituloQuestao.substring(0, tituloQuestao.length()-5) 
                                    + infoObr
                                    + "</br>";
                }else{
                    tituloQuestao = tituloQuestao + infoObr;
                }
            }
        }    
        return tituloQuestao;
    }
	public Collection geraImpressaoFormatadaHTML(Collection colQuestoes,
			Date dataQuestionario, 
			Integer idContrato, 
			Integer idProfissional){
		ListagemService listagemService = null;
		try {
			listagemService = (ListagemService) ServiceLocator.getInstance().getService(ListagemService.class, null);
		} catch (ServiceException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		QuestionarioService questionarioService = null;
		try {
			questionarioService = (QuestionarioService) ServiceLocator.getInstance().getService(QuestionarioService.class, null);
		} catch (ServiceException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		ContratoQuestionariosService contratoQuestionariosService = null;
		try {
			contratoQuestionariosService = (ContratoQuestionariosService) ServiceLocator.getInstance().getService(ContratoQuestionariosService.class, null);
		} catch (ServiceException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		GrupoQuestionarioService grupoQuestionarioService = null;
		try {
			grupoQuestionarioService = (GrupoQuestionarioService) ServiceLocator.getInstance().getService(GrupoQuestionarioService.class, null);
		} catch (ServiceException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		LinhaSpoolQuestionario linhaSpoolQuestionario = new LinhaSpoolQuestionario();
		String avanco = "     ";
		Collection colLinhas = new ArrayList(); 
		if (colQuestoes != null){
			for (Iterator itQuest = colQuestoes.iterator(); itQuest.hasNext();){
				QuestaoQuestionarioDTO questaoDto = (QuestaoQuestionarioDTO)itQuest.next();
                if (questaoDto.getImprime() != null && !questaoDto.getImprime().trim().equalsIgnoreCase("S")) {
                    continue;
                }
				
				if (questaoDto.getTextoInicial() != null && !questaoDto.getTextoInicial().trim().equalsIgnoreCase("")){
					linhaSpoolQuestionario = new LinhaSpoolQuestionario(avanco + questaoDto.getTextoInicial());
					colLinhas.add(linhaSpoolQuestionario);
				}
				
				String value = "";
				String value2 = "";
				String value3 = "";
				String value4 = "";
				String value5 = "";
				String value6 = "";
				
				String bufferQuestao = "";
				String dadosAdicionais = "";
				
				String infoObr = "";
				
				//Matriz !!!!!!!!!!!!!
				if (questaoDto.getTipo().equalsIgnoreCase("M")){
				    int qtdeRespostas = 0;
				    for(Iterator it = questaoDto.getColQuestoesAgrupadas().iterator(); it.hasNext();){
				        QuestaoQuestionarioDTO questaoAgrup = (QuestaoQuestionarioDTO)it.next();
				        RespostaItemQuestionarioDTO resposta = questaoAgrup.obtemRespostaItemDto(questaoAgrup.getSequencialResposta());
				        if (resposta != null && (resposta.existeResposta() || (resposta.getColOpcoesResposta() != null && resposta.getColOpcoesResposta().size() > 0))) {
				            qtdeRespostas++;
				        }
				    }
				    if (qtdeRespostas == 0) continue;
				    bufferQuestao = "<tr><td colspan='20'><b>" + montaTituloQuestao(questaoDto) + "</b>";
				    //bufferQuestao += "<div id='divQuestoesMatriz_" + questaoDto.getIdQuestaoQuestionario() + "' style='width: 720px; height: "+altura+"px; overflow: auto;'>";
				    bufferQuestao += "<div id='divQuestoesMatriz_" + questaoDto.getIdQuestaoQuestionario() + "' style='width: 100%; height: 100%; overflow: auto;'>";
				    bufferQuestao += "<table id='tblQuestoesMatriz_" + questaoDto.getIdQuestaoQuestionario() + "' cellpadding='0' cellspacing='0' width='100%' height='100%' border = '1'>";
		            if (questaoDto.getCabecalhoColunas().equalsIgnoreCase("S")){
		                bufferQuestao += "<tr>";
		                for(Iterator itCol = questaoDto.getColCabecalhosColuna().iterator(); itCol.hasNext();){
		                    QuestaoQuestionarioDTO questaoAgrup = (QuestaoQuestionarioDTO)itCol.next();
                            bufferQuestao += "<td bgcolor='#20b2aa'>"+questaoAgrup.getTituloQuestaoQuestionarioSemFmt()+"</td>";         
		                }
		                bufferQuestao += "</tr>";
		            }

		            Iterator itLin = questaoDto.getColCabecalhosLinha().iterator();
                    Iterator itCel = questaoDto.getColQuestoesAgrupadas().iterator();			            
		            for(Integer linha = 1; linha <= questaoDto.getQtdeLinhas(); linha++){
                        bufferQuestao += "<tr>";			                
		                if (questaoDto.getCabecalhoLinhas().equalsIgnoreCase("S")){
		                    QuestaoQuestionarioDTO questaoAgrupada = (QuestaoQuestionarioDTO) itLin.next();
		                    bufferQuestao += "<td bgcolor='#20b2aa'>"+questaoAgrupada.getTituloQuestaoQuestionarioSemFmt()+"</td>";
		                }
		                for(Integer coluna = 1; coluna <= questaoDto.getQtdeColunas(); coluna++){ 
                            QuestaoQuestionarioDTO questaoAgrupada = (QuestaoQuestionarioDTO) itCel.next();
                            questaoAgrupada.setAgrupada(true);
                            bufferQuestao += "<td>"+montaLinhaQuestao(questaoAgrupada)+"</td>";
		                }
                        bufferQuestao += "</tr>";			                
		            }
		            bufferQuestao += "</table></div></td>";
		            
					linhaSpoolQuestionario = new LinhaSpoolQuestionario(bufferQuestao);
					linhaSpoolQuestionario.setGenerateTR(false);
					colLinhas.add(linhaSpoolQuestionario);		
					
					continue;
				}
				
				//TABELA !!!!!!!!!!!!!!!!
				if (questaoDto.getTipo().equalsIgnoreCase("L")){
                    bufferQuestao = "<tr><td colspan='20'>" + montaTituloQuestao(questaoDto) + ""; 
                    bufferQuestao += "<div style='width: 100%; height: 100%; overflow: auto;'>";
                    bufferQuestao += "<table id='tblRespostasTabela_" + questaoDto.getIdQuestaoQuestionario() + "' cellpadding='0' cellspacing='0' width='100%' border = '1'>";
                    bufferQuestao += "<tr>";
                    for (Iterator itCel = questaoDto.getColQuestoesAgrupadas().iterator(); itCel.hasNext();){
                        QuestaoQuestionarioDTO questaoAgrupada = (QuestaoQuestionarioDTO) itCel.next();
                        bufferQuestao += "<td bgcolor='#20b2aa'>"+montaTituloQuestao(questaoAgrupada)+"</td>";
                    }
                    bufferQuestao += "</tr>";
                    int ultimoSequencial = 0;                        
                    if (questaoDto.getColRespostas() != null && questaoDto.getColRespostas().size() > 0) {
                        int sequencial = 0;
                        for (Iterator itResp = questaoDto.getColRespostas().iterator(); itResp.hasNext();){
                            RespostaItemQuestionarioDTO resp = (RespostaItemQuestionarioDTO) itResp.next();
                            if (resp.getSequencialResposta() != null && resp.getSequencialResposta().intValue() != sequencial) { 
                                if (sequencial > 0){
                                    bufferQuestao += "</tr>";
                                } 
                                sequencial = resp.getSequencialResposta();
                                if (sequencial > ultimoSequencial){
                                    ultimoSequencial = sequencial;
                                }                                    
                                bufferQuestao += "<tr>";
                                for (Iterator itCel = questaoDto.getColQuestoesAgrupadas().iterator(); itCel.hasNext();){
                                    QuestaoQuestionarioDTO questaoTabela = (QuestaoQuestionarioDTO) itCel.next();
                                    questaoTabela.setAgrupada(true);
                                    questaoTabela.setSequencialResposta(new Integer(sequencial));
                                    questaoTabela.setTituloQuestaoQuestionario("");
                                    bufferQuestao += "<td>"+montaLinhaQuestao(questaoTabela)+"</td>";
                                }
                            } 
                        }
                        bufferQuestao += "</tr>";
                    }    
                    bufferQuestao += "</table>";
                    bufferQuestao += "</div></td></tr>";
                    linhaSpoolQuestionario = new LinhaSpoolQuestionario(bufferQuestao);
                    linhaSpoolQuestionario.setGenerateTR(false);
                    colLinhas.add(linhaSpoolQuestionario);
                    
                    continue;
				}
				
				RespostaItemQuestionarioDTO resposta = questaoDto.obtemRespostaItemDto(questaoDto.getSequencialResposta());
				if (resposta == null) {
				    continue;
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("T") || questaoDto.getTipoQuestao().equalsIgnoreCase("A")){ //Texto
					if (resposta.getRespostaTextual()!=null){ 
						value = resposta.getRespostaTextual();
					}						
				}else if (questaoDto.getTipoQuestao().equalsIgnoreCase("D")){ //Data
					if (resposta!=null){
						if (resposta.getRespostaData() != null){
							value = UtilDatas.dateToSTR(resposta.getRespostaData());
						}
					}						
				}else if (questaoDto.getTipoQuestao().equalsIgnoreCase("H")){ //Data
					if (resposta!=null){
						if(resposta.getRespostaHora() != null){
						    if(resposta.getRespostaHora() != null){
			                    if(resposta.getRespostaHora().length() == 4)
			                        value = UtilDatas.formatHoraStr(resposta.getRespostaHora());
			                    else if(resposta.getRespostaHora().length() == 3)
			                        value = resposta.getRespostaHora().substring(0, 2) + ":" 
			                              + resposta.getRespostaHora().substring(2);
			                    else value = resposta.getRespostaHora();
			                }
						}
					}						
				}else if (questaoDto.getTipoQuestao().equalsIgnoreCase("N")){ //Número (sem casas decimais)
					if (resposta!=null){
						if (resposta.getRespostaNumero() != null){
							value = UtilFormatacao.formatDouble(resposta.getRespostaNumero(), 0);
						}
					}						
				}else if (questaoDto.getTipoQuestao().equalsIgnoreCase("V") || questaoDto.getTipoQuestao().equalsIgnoreCase("%")){ //Valor Decimal e %
					if (resposta!=null){
						int qtdeDecimais = 0;
						if (questaoDto.getDecimais()!=null){
							qtdeDecimais = questaoDto.getDecimais().intValue();
						}
						if (resposta.getRespostaValor() != null){
							value = UtilFormatacao.formatDouble(resposta.getRespostaValor(), qtdeDecimais);
						}
					}						
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("1")){ //Faixa de Números (sem casas decimais)
					if (resposta!=null){
						value = UtilFormatacao.formatDouble(resposta.getRespostaValor(), 0);
						value2 = UtilFormatacao.formatDouble(resposta.getRespostaValor2(), 0);
					}						
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("2")){ //Faixa de Valores (com casas decimais)
					if (resposta!=null){
						int qtdeDecimais = 0;
						if (questaoDto.getDecimais()!=null){
							qtdeDecimais = questaoDto.getDecimais().intValue();
						}
						value = UtilFormatacao.formatDouble(resposta.getRespostaValor(), qtdeDecimais);
						value2 = UtilFormatacao.formatDouble(resposta.getRespostaValor2(), qtdeDecimais);
					}						
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("G")){ //Mes/Ano
					if (resposta!=null){
						if (resposta.getRespostaMes() != null){
							value = UtilFormatacao.formatInt(resposta.getRespostaMes().intValue(), "00");
						}
						if (resposta.getRespostaAno() != null){
							value2 = UtilFormatacao.formatInt(resposta.getRespostaAno().intValue(), "0000");
						}
					}						
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("%")){ //Percentual
					if (resposta!=null){
						if (resposta.getRespostaPercentual() != null){
							value = UtilFormatacao.formatDouble(resposta.getRespostaPercentual(), 2);
						}
					}						
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("*")){ //Percentual e Valor
					if (resposta!=null){
						if (resposta.getRespostaPercentual() != null){
							value = UtilFormatacao.formatDouble(resposta.getRespostaPercentual(), 2);
						}
						if (resposta.getRespostaValor() != null){
							value2 = UtilFormatacao.formatDouble(resposta.getRespostaValor(), 2);
						}
					}						
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("L")){ //Campo Longo - Editor
					if (resposta!=null){
						value = resposta.getRespostaTextual();
					}						
				}
		        if (questaoDto.getTipoQuestao().equalsIgnoreCase("8")){ //Listagem
		            ListagemDTO listagem = null;
					try {
						listagem = ListagemConfig.getInstance().find(questaoDto.getNomeListagem());
					} catch (Exception e) {
						e.printStackTrace();
						value = "";
					}
		            if (listagem != null){
		                try {
		                	if (listagemService != null){
		                		listagem = (ListagemDTO) listagemService.restore(listagem);
		                	}
						} catch (LogicException e) {
							e.printStackTrace();
							value = "";
						} catch (ServiceException e) {
							e.printStackTrace();
							value = "";
						}
		                
		                for(Iterator it = listagem.getLinhas().iterator(); it.hasNext();){ 
		                    ListagemLinhaDTO linha =  (ListagemLinhaDTO)it.next();
		                    
		                    if (resposta != null && resposta.getRespostaIdListagem() != null){
		                        if (linha.getId().trim().equalsIgnoreCase(resposta.getRespostaIdListagem().trim())){
		                        	value += linha.getDescricao();
		                        }
		                    }
		                }
		            }
		        }
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("M")){ //Galeria Multimídia (Videos,etc.)
					if (resposta != null){
						Collection colAnexos = resposta.getColRelacaoAnexos();
						if (colAnexos != null){
			            	for(Iterator it = colAnexos.iterator(); it.hasNext();){
			            		RespostaItemQuestionarioAnexosDTO respAnexoDto = (RespostaItemQuestionarioAnexosDTO)it.next();
			            		value += respAnexoDto.getNomeArquivo();
			            		if (respAnexoDto.getObservacao() != null){
			            			if (!respAnexoDto.getObservacao().trim().equalsIgnoreCase("")){
			            				value += " <b>Obs.:</b> " + respAnexoDto.getObservacao();
			            			}
			            		}
			            		value += "<br>";
			            	}
			            }	
					}
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("R") ||
						questaoDto.getTipoQuestao().equalsIgnoreCase("C") ||
						questaoDto.getTipoQuestao().equalsIgnoreCase("X")){ 
					if (questaoDto.getColOpcoesResposta()!=null){
						boolean b = false;
						for(Iterator itOpcResp = questaoDto.getColOpcoesResposta().iterator(); itOpcResp.hasNext();){
							OpcaoRespostaQuestionarioDTO opcRespDto =  (OpcaoRespostaQuestionarioDTO)itOpcResp.next();
							
							if (resposta!=null){
								if (isInCollection(opcRespDto.getIdOpcaoRespostaQuestionario(), resposta.getColOpcoesResposta())){
									if (b){
										value += ", ";
									}
									value += opcRespDto.getTitulo().replaceAll("<br>","");
									b = true;
								}
							}
						}																				
					}
				}				
				
				String tituloQuestao = "";
				if (questaoDto.getTituloQuestaoQuestionario() != null) {
    				tituloQuestao = questaoDto.getTituloQuestaoQuestionario();
    				tituloQuestao = tituloQuestao.trim();
    				tituloQuestao = tituloQuestao.replaceAll("<p>", "");
    				tituloQuestao = tituloQuestao.replaceAll("</p>", "");
    				tituloQuestao = tituloQuestao.replaceAll("&nbsp;", "");
				}
				
				if (tituloQuestao.length() > 5){
					if (tituloQuestao.substring(tituloQuestao.length()-5, tituloQuestao.length()).equalsIgnoreCase("</br>")){
						tituloQuestao = tituloQuestao.substring(0, tituloQuestao.length()-5) 
						+ infoObr;
						//+ ":";
					}else{
						tituloQuestao = tituloQuestao + infoObr; // + ":";
					}
				}else{
					tituloQuestao = tituloQuestao + infoObr; // + ":";
				}
				if (tituloQuestao.trim().equalsIgnoreCase(":")){
					tituloQuestao = "";
				}
				
				if (value == null) {
				    value = "";
				}
				if (!questaoDto.isAgrupada()){
					if (!value.equalsIgnoreCase("") || !value2.equalsIgnoreCase("") || !value3.equalsIgnoreCase("")){
						if (!tituloQuestao.trim().equalsIgnoreCase("")){
							bufferQuestao += "<td width='30%' border='1px solid black'>";
						}
					}
				}
				if (questaoDto.getTipo() != null && (questaoDto.getTipo().equalsIgnoreCase("T") || questaoDto.getTipoQuestao().equalsIgnoreCase("F"))){ //Texto Fixo
					//bufferQuestao += avanco + tituloQuestao;
					bufferQuestao += ""; //NAO SERA IMPRESSO O TEXTO FIXO.
				}else{
					if (!value.equalsIgnoreCase("") || !value2.equalsIgnoreCase("") || !value3.equalsIgnoreCase("")){
						if ("B".equalsIgnoreCase(questaoDto.getInfoResposta())){ //Em baixo da pergunta
							bufferQuestao += avanco + tituloQuestao;
							
							bufferQuestao += avanco;
						}else{
							bufferQuestao += avanco + tituloQuestao;
						}	
						if (questaoDto.getUnidade()!=null && !questaoDto.getUnidade().equalsIgnoreCase("")){
							dadosAdicionais += " " + questaoDto.getUnidade();
						}
						if (questaoDto.getValoresReferencia()!=null && !questaoDto.getValoresReferencia().equalsIgnoreCase("")){
							dadosAdicionais += questaoDto.getValoresReferencia() + "";
						}					
					}
				}
				if (!questaoDto.isAgrupada()){
					if (!value.equalsIgnoreCase("") || !value2.equalsIgnoreCase("") || !value3.equalsIgnoreCase("")){
						if (!tituloQuestao.trim().equalsIgnoreCase("")){
							bufferQuestao += "</td>";
							bufferQuestao += "<td border='1px solid black'>";
						}else{
							bufferQuestao += "<td border='1px solid black' colspan='2'>";
						}
					}
				}
				
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("T")){ //Texto Curto (Tamanho delimitado)
					if (!value.equalsIgnoreCase("") || !value2.equalsIgnoreCase("") || !value3.equalsIgnoreCase("")){
						bufferQuestao += value + dadosAdicionais;				
					}
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("A")){ //Texto Longo (Campo de observações, etc.)
					if (!value.equalsIgnoreCase("") || !value2.equalsIgnoreCase("") || !value3.equalsIgnoreCase("")){
						String valueAux = value;
						if (valueAux == null){
							valueAux = "";
						}
						valueAux = valueAux.replaceAll("\r\n", "<br/>");
						valueAux = valueAux.replaceAll("\r", "<br/>");
						valueAux = valueAux.replaceAll("\n", "<br/>");
						bufferQuestao += valueAux + dadosAdicionais;									
					}
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("L")){ //Texto Longo com Editor (Campo de Laudos, etc.)
					if (!value.equalsIgnoreCase("") || !value2.equalsIgnoreCase("") || !value3.equalsIgnoreCase("")){
						bufferQuestao += value + dadosAdicionais;
					}
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("R") ||
						questaoDto.getTipoQuestao().equalsIgnoreCase("C") ||
						questaoDto.getTipoQuestao().equalsIgnoreCase("X")){ 
					if (questaoDto.getColOpcoesResposta()!=null){
						String bufferOut = value;
						bufferOut += "" + dadosAdicionais;
						bufferOut = bufferOut.replaceAll("\n", "");
						
						bufferQuestao += bufferOut;																					
					}
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("N")){ //Número (sem casas decimais)
					bufferQuestao += value + dadosAdicionais;										
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("V")){ //Valor Decimal
					bufferQuestao += value + dadosAdicionais;										
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("%")){ //% (Percentual)
					bufferQuestao += value + dadosAdicionais;															
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("*")){ //% (Percentual)  +   Valor Absoluto
					bufferQuestao += value + "% - " + value2 + dadosAdicionais;														
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("1")){ //Faixa de Valores (Números)
					bufferQuestao += value + " " + value2 + dadosAdicionais;																			
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("2")){ //Faixa de Valores (Valores decimais)
					bufferQuestao += value + " " + value2 + dadosAdicionais;																									
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("I")){ //Galeria de Imagens
					
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("M")){ //Galeria Multimídia (Videos,etc.)			}
					bufferQuestao += value + dadosAdicionais;				
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("D")){ //Data
					bufferQuestao += value + dadosAdicionais;				
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("H")){ //Hora
					bufferQuestao += value + dadosAdicionais;				
				}	
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("G")){ //Mes/Ano
					bufferQuestao += value + "/" + value2 + dadosAdicionais;																				
				}
				if (questaoDto.getTipoQuestao().equalsIgnoreCase("8")){ //Listagem
					bufferQuestao += value + dadosAdicionais;		
				}
				if (!questaoDto.isAgrupada()){
					if (!value.equalsIgnoreCase("") || !value2.equalsIgnoreCase("") || !value3.equalsIgnoreCase("")){					
						bufferQuestao += "</td>";
					}
				}
				
				linhaSpoolQuestionario = new LinhaSpoolQuestionario(bufferQuestao);
				linhaSpoolQuestionario.setGenerateTR(true);
				colLinhas.add(linhaSpoolQuestionario);				
			}
		}
		return colLinhas;
	}
	
	public Collection geraImpressaoFormatadaHTMLQuestao(QuestaoQuestionarioDTO questaoDto){
		ListagemService listagemService = null;
		try {
			listagemService = (ListagemService) ServiceLocator.getInstance().getService(ListagemService.class, null);
		} catch (ServiceException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		LinhaSpoolQuestionario linhaSpoolQuestionario = new LinhaSpoolQuestionario();
		String avanco = "     ";
		Collection colLinhas = new ArrayList(); 

        if (questaoDto.getImprime() != null && !questaoDto.getImprime().trim().equalsIgnoreCase("S")){
            linhaSpoolQuestionario = new LinhaSpoolQuestionario(avanco);
            colLinhas.add(linhaSpoolQuestionario);
            return colLinhas;
        }
		
		if (questaoDto.getTextoInicial() != null && !questaoDto.getTextoInicial().trim().equalsIgnoreCase("")){
			linhaSpoolQuestionario = new LinhaSpoolQuestionario(avanco + questaoDto.getTextoInicial());
			colLinhas.add(linhaSpoolQuestionario);
		}
		
		String value = "";
		String value2 = "";
		String value3 = "";
		String value4 = "";
		String value5 = "";
		String value6 = "";
		
		String bufferQuestao = "";
		String dadosAdicionais = "";
		
		String infoObr = "";
		
		//Matriz !!!!!!!!!!!!!
		if (questaoDto.getTipo().equalsIgnoreCase("M")){
		    bufferQuestao = "<tr><td colspan='20'>" + montaTituloQuestao(questaoDto) + "";
		    //bufferQuestao += "<div id='divQuestoesMatriz_" + questaoDto.getIdQuestaoQuestionario() + "' style='width: 720px; height: "+altura+"px; overflow: auto;'>";
		    bufferQuestao += "<div id='divQuestoesMatriz_" + questaoDto.getIdQuestaoQuestionario() + "' style='width: 100%; height: 100%; overflow: auto;'>";
		    bufferQuestao += "<table id='tblQuestoesMatriz_" + questaoDto.getIdQuestaoQuestionario() + "' cellpadding='0' cellspacing='0' width='100%' height='100%' border = '1'>";
            if (questaoDto.getCabecalhoColunas().equalsIgnoreCase("S")){
                bufferQuestao += "<tr>";
                for(Iterator itCol = questaoDto.getColCabecalhosColuna().iterator(); itCol.hasNext();){
                    QuestaoQuestionarioDTO questaoAgrup = (QuestaoQuestionarioDTO)itCol.next();
                    bufferQuestao += "<td bgcolor='#20b2aa'>"+questaoAgrup.getTituloQuestaoQuestionarioSemFmt()+"</td>";         
                }
                bufferQuestao += "</tr>";
            }

            Iterator itLin = questaoDto.getColCabecalhosLinha().iterator();
            Iterator itCel = questaoDto.getColQuestoesAgrupadas().iterator();			            
            for(Integer linha = 1; linha <= questaoDto.getQtdeLinhas(); linha++){
                bufferQuestao += "<tr>";			                
                if (questaoDto.getCabecalhoLinhas().equalsIgnoreCase("S")){
                    QuestaoQuestionarioDTO questaoAgrupada = (QuestaoQuestionarioDTO) itLin.next();
                    bufferQuestao += "<td bgcolor='#20b2aa'>"+questaoAgrupada.getTituloQuestaoQuestionarioSemFmt()+"</td>";
                }
                for(Integer coluna = 1; coluna <= questaoDto.getQtdeColunas(); coluna++){ 
                    QuestaoQuestionarioDTO questaoAgrupada = (QuestaoQuestionarioDTO) itCel.next();
                    questaoAgrupada.setAgrupada(true);
                    bufferQuestao += "<td>"+montaLinhaQuestao(questaoAgrupada)+"</td>";
                }
                bufferQuestao += "</tr>";			                
            }
            bufferQuestao += "</table></div></td>";
            
			linhaSpoolQuestionario = new LinhaSpoolQuestionario(bufferQuestao);
			linhaSpoolQuestionario.setGenerateTR(false);
			colLinhas.add(linhaSpoolQuestionario);		
			
			return colLinhas;
		}
		
		//TABELA !!!!!!!!!!!!!!!!
		if (questaoDto.getTipo().equalsIgnoreCase("L")){
            bufferQuestao = "<tr><td colspan='20'>" + montaTituloQuestao(questaoDto) + ""; 
            bufferQuestao += "<div style='width: 100%; height: 100%; overflow: auto;'>";
            bufferQuestao += "<table id='tblRespostasTabela_" + questaoDto.getIdQuestaoQuestionario() + "' cellpadding='0' cellspacing='0' width='100%' border = '1'>";
            bufferQuestao += "<tr>";
            for (Iterator itCel = questaoDto.getColQuestoesAgrupadas().iterator(); itCel.hasNext();){
                QuestaoQuestionarioDTO questaoAgrupada = (QuestaoQuestionarioDTO) itCel.next();
                bufferQuestao += "<td bgcolor='#20b2aa'>"+montaTituloQuestao(questaoAgrupada)+"</td>";
            }
            bufferQuestao += "</tr>";
            int ultimoSequencial = 0;                        
            if (questaoDto.getColRespostas() != null && questaoDto.getColRespostas().size() > 0) {
                int sequencial = 0;
                for (Iterator itResp = questaoDto.getColRespostas().iterator(); itResp.hasNext();){
                    RespostaItemQuestionarioDTO resp = (RespostaItemQuestionarioDTO) itResp.next();
                    if (resp.getSequencialResposta() != null && resp.getSequencialResposta().intValue() != sequencial) { 
                        if (sequencial > 0){
                            bufferQuestao += "</tr>";
                        } 
                        sequencial = resp.getSequencialResposta();
                        if (sequencial > ultimoSequencial){
                            ultimoSequencial = sequencial;
                        }                                    
                        bufferQuestao += "<tr>";
                        for (Iterator itCel = questaoDto.getColQuestoesAgrupadas().iterator(); itCel.hasNext();){
                            QuestaoQuestionarioDTO questaoTabela = (QuestaoQuestionarioDTO) itCel.next();
                            questaoTabela.setAgrupada(true);
                            questaoTabela.setSequencialResposta(new Integer(sequencial));
                            questaoTabela.setTituloQuestaoQuestionario("");
                            bufferQuestao += "<td>"+montaLinhaQuestao(questaoTabela)+"</td>";
                        }
                    } 
                }
                bufferQuestao += "</tr>";
            }    
            bufferQuestao += "</table>";
            bufferQuestao += "</div></td></tr>";
            linhaSpoolQuestionario = new LinhaSpoolQuestionario(bufferQuestao);
            linhaSpoolQuestionario.setGenerateTR(false);
            colLinhas.add(linhaSpoolQuestionario);
            
            return colLinhas;
		}
		
		RespostaItemQuestionarioDTO resposta = questaoDto.obtemRespostaItemDto(questaoDto.getSequencialResposta());
		if (questaoDto.getTipoQuestao().equalsIgnoreCase("T") || questaoDto.getTipoQuestao().equalsIgnoreCase("A")){ //Texto
			if (resposta.getRespostaTextual()!=null){ 
				value = resposta.getRespostaTextual();
			}						
		}else if (questaoDto.getTipoQuestao().equalsIgnoreCase("D")){ //Data
			if (resposta!=null){
				if (resposta.getRespostaData() != null){
					value = UtilDatas.dateToSTR(resposta.getRespostaData());
				}
			}						
		}else if (questaoDto.getTipoQuestao().equalsIgnoreCase("H")){ //Data
			if (resposta!=null){
				if (resposta.getRespostaHora() != null){
					value = UtilDatas.formatHoraStr(resposta.getRespostaHora());
				}
			}						
		}else if (questaoDto.getTipoQuestao().equalsIgnoreCase("N")){ //Número (sem casas decimais)
			if (resposta!=null){
				if (resposta.getRespostaNumero() != null){
					value = UtilFormatacao.formatDouble(resposta.getRespostaNumero(), 0);
				}
			}						
		}else if (questaoDto.getTipoQuestao().equalsIgnoreCase("V") || questaoDto.getTipoQuestao().equalsIgnoreCase("%")){ //Valor Decimal e %
			if (resposta!=null){
				int qtdeDecimais = 0;
				if (questaoDto.getDecimais()!=null){
					qtdeDecimais = questaoDto.getDecimais().intValue();
				}
				if (resposta.getRespostaValor() != null){
					value = UtilFormatacao.formatDouble(resposta.getRespostaValor(), qtdeDecimais);
				}
			}						
		}
		
		if (questaoDto.getTipoQuestao().equalsIgnoreCase("1")){ //Faixa de Números (sem casas decimais)
			if (resposta!=null){
				value = UtilFormatacao.formatDouble(resposta.getRespostaValor(), 0);
				value2 = UtilFormatacao.formatDouble(resposta.getRespostaValor2(), 0);
			}						
		}
		if (questaoDto.getTipoQuestao().equalsIgnoreCase("2")){ //Faixa de Valores (com casas decimais)
			if (resposta!=null){
				int qtdeDecimais = 0;
				if (questaoDto.getDecimais()!=null){
					qtdeDecimais = questaoDto.getDecimais().intValue();
				}
				value = UtilFormatacao.formatDouble(resposta.getRespostaValor(), qtdeDecimais);
				value2 = UtilFormatacao.formatDouble(resposta.getRespostaValor2(), qtdeDecimais);
			}						
		}
		if (questaoDto.getTipoQuestao().equalsIgnoreCase("G")){ //Mes/Ano
			if (resposta!=null){
				if (resposta.getRespostaMes() != null){
					value = UtilFormatacao.formatInt(resposta.getRespostaMes().intValue(), "00");
				}
				if (resposta.getRespostaAno() != null){
					value2 = UtilFormatacao.formatInt(resposta.getRespostaAno().intValue(), "0000");
				}
			}						
		}
		if (questaoDto.getTipoQuestao().equalsIgnoreCase("*")){ //Percentual e Valor
			if (resposta!=null){
				if (resposta.getRespostaPercentual() != null){
					value = UtilFormatacao.formatDouble(resposta.getRespostaPercentual(), 2);
				}
				if (resposta.getRespostaValor() != null){
					value2 = UtilFormatacao.formatDouble(resposta.getRespostaValor(), 2);
				}
			}						
		}
		if (questaoDto.getTipoQuestao().equalsIgnoreCase("L")){ //Campo Longo - Editor
			if (resposta!=null){
				value = resposta.getRespostaTextual();
			}						
		}
        if (questaoDto.getTipoQuestao().equalsIgnoreCase("8")){ //Listagem
            ListagemDTO listagem = null;
			try {
				listagem = ListagemConfig.getInstance().find(questaoDto.getNomeListagem());
			} catch (Exception e) {
				e.printStackTrace();
				value = "";
			}
            if (listagem != null){
                try {
                	if (listagemService != null){
                		listagem = (ListagemDTO) listagemService.restore(listagem);
                	}
				} catch (LogicException e) {
					e.printStackTrace();
					value = "";
				} catch (ServiceException e) {
					e.printStackTrace();
					value = "";
				}
                for(Iterator it = listagem.getLinhas().iterator(); it.hasNext();){ 
                    ListagemLinhaDTO linha =  (ListagemLinhaDTO)it.next();
                    
                    if (resposta != null && resposta.getRespostaIdListagem() != null){
                        if (linha.getId().trim().equalsIgnoreCase(resposta.getRespostaIdListagem().trim())){
                        	value += linha.getDescricao();
                        }
                    }
                }
            }
        }	
		if (questaoDto.getTipoQuestao().equalsIgnoreCase("M")){ //Galeria Multimídia (Videos,etc.)
			if (resposta != null){
				Collection colAnexos = resposta.getColRelacaoAnexos();
				if (colAnexos != null){
	            	for(Iterator it = colAnexos.iterator(); it.hasNext();){
	            		RespostaItemQuestionarioAnexosDTO respAnexoDto = (RespostaItemQuestionarioAnexosDTO)it.next();
	            		value += respAnexoDto.getNomeArquivo();
	            		if (respAnexoDto.getObservacao() != null){
	            			if (!respAnexoDto.getObservacao().trim().equalsIgnoreCase("")){
	            				value += " <b>Obs.:</b> " + respAnexoDto.getObservacao();
	            			}
	            		}
	            		value += "<br>";
	            	}
	            }	
			}
		}
		if (questaoDto.getTipoQuestao().equalsIgnoreCase("R") ||
				questaoDto.getTipoQuestao().equalsIgnoreCase("C") ||
				questaoDto.getTipoQuestao().equalsIgnoreCase("X")){ 
			if (questaoDto.getColOpcoesResposta()!=null){
				boolean b = false;
				for(Iterator itOpcResp = questaoDto.getColOpcoesResposta().iterator(); itOpcResp.hasNext();){
					OpcaoRespostaQuestionarioDTO opcRespDto =  (OpcaoRespostaQuestionarioDTO)itOpcResp.next();
					
					if (resposta!=null){
						if (isInCollection(opcRespDto.getIdOpcaoRespostaQuestionario(), resposta.getColOpcoesResposta())){
							if (b){
								value += ", ";
							}
							value += opcRespDto.getTitulo().replaceAll("<br>","");
							b = true;
						}
					}
				}																				
			}
		}				
		
		String tituloQuestao = "";
		if (questaoDto.getTituloQuestaoQuestionario() != null) {
			tituloQuestao = questaoDto.getTituloQuestaoQuestionario();
			tituloQuestao = tituloQuestao.trim();
			tituloQuestao = tituloQuestao.replaceAll("<p>", "");
			tituloQuestao = tituloQuestao.replaceAll("</p>", "");
			tituloQuestao = tituloQuestao.replaceAll("&nbsp;", "");
		}
		
		if (tituloQuestao.length() > 5){
			if (tituloQuestao.substring(tituloQuestao.length()-5, tituloQuestao.length()).equalsIgnoreCase("</br>")){
				tituloQuestao = tituloQuestao.substring(0, tituloQuestao.length()-5) 
				+ infoObr;
				//+ ":";
			}else{
				tituloQuestao = tituloQuestao + infoObr;// + ":";
			}
		}else{
			tituloQuestao = tituloQuestao + infoObr;// + ":";
		}
		if (tituloQuestao.trim().equalsIgnoreCase(":")){
			tituloQuestao = "";
		}
		
		if (value == null) {
		    value = "";
		}
		if (!questaoDto.isAgrupada()){
			if (!value.equalsIgnoreCase("") || !value2.equalsIgnoreCase("") || !value3.equalsIgnoreCase("")){
				if (!tituloQuestao.trim().equalsIgnoreCase("")){
					bufferQuestao += "<td width='30%' border='1px solid black'>";
				}
			}
		}
		if (questaoDto.getTipo() != null && (questaoDto.getTipo().equalsIgnoreCase("T") || questaoDto.getTipoQuestao().equalsIgnoreCase("F"))){ //Texto Fixo
			//bufferQuestao += avanco + tituloQuestao;
			bufferQuestao += ""; //NAO SERA IMPRESSO O TEXTO FIXO.
		}else{
			if (!value.equalsIgnoreCase("") || !value2.equalsIgnoreCase("") || !value3.equalsIgnoreCase("")){
				if ("B".equalsIgnoreCase(questaoDto.getInfoResposta())){ //Em baixo da pergunta
					bufferQuestao += avanco + tituloQuestao;
					
					bufferQuestao += avanco;
				}else{
					bufferQuestao += avanco + tituloQuestao;
				}	
				if (questaoDto.getUnidade()!=null && !questaoDto.getUnidade().equalsIgnoreCase("")){
					dadosAdicionais += " " + questaoDto.getUnidade();
				}
				if (questaoDto.getValoresReferencia()!=null && !questaoDto.getValoresReferencia().equalsIgnoreCase("")){
					dadosAdicionais += questaoDto.getValoresReferencia() + "";
				}					
			}
		}
		if (!questaoDto.isAgrupada()){
			if (!value.equalsIgnoreCase("") || !value2.equalsIgnoreCase("") || !value3.equalsIgnoreCase("")){
				if (!tituloQuestao.trim().equalsIgnoreCase("")){
					bufferQuestao += "</td>";
					bufferQuestao += "<td border='1px solid black'>";
				}else{
					bufferQuestao += "<td border='1px solid black' colspan='2'>";
				}
			}
		}
		
		if (questaoDto.getTipoQuestao().equalsIgnoreCase("T")){ //Texto Curto (Tamanho delimitado)
			if (!value.equalsIgnoreCase("") || !value2.equalsIgnoreCase("") || !value3.equalsIgnoreCase("")){
				bufferQuestao += value + dadosAdicionais;				
			}
		}
		if (questaoDto.getTipoQuestao().equalsIgnoreCase("A")){ //Texto Longo (Campo de observações, etc.)
			if (!value.equalsIgnoreCase("") || !value2.equalsIgnoreCase("") || !value3.equalsIgnoreCase("")){
				bufferQuestao += value + dadosAdicionais;									
			}
		}
		if (questaoDto.getTipoQuestao().equalsIgnoreCase("L")){ //Texto Longo com Editor (Campo de Laudos, etc.)
			if (!value.equalsIgnoreCase("") || !value2.equalsIgnoreCase("") || !value3.equalsIgnoreCase("")){
				bufferQuestao += value + dadosAdicionais;
			}
		}
		if (questaoDto.getTipoQuestao().equalsIgnoreCase("R") ||
				questaoDto.getTipoQuestao().equalsIgnoreCase("C") ||
				questaoDto.getTipoQuestao().equalsIgnoreCase("X")){ 
			if (questaoDto.getColOpcoesResposta()!=null){
				String bufferOut = value;
				bufferOut += "" + dadosAdicionais;
				bufferOut = bufferOut.replaceAll("\n", "");
				
				bufferQuestao += bufferOut;																					
			}
		}
		if (questaoDto.getTipoQuestao().equalsIgnoreCase("N")){ //Número (sem casas decimais)
			bufferQuestao += value + dadosAdicionais;										
		}
		if (questaoDto.getTipoQuestao().equalsIgnoreCase("V")){ //Valor Decimal
			bufferQuestao += value + dadosAdicionais;										
		}
		if (questaoDto.getTipoQuestao().equalsIgnoreCase("%")){ //% (Percentual)
			bufferQuestao += value + dadosAdicionais;															
		}
		if (questaoDto.getTipoQuestao().equalsIgnoreCase("*")){ //% (Percentual)  +   Valor Absoluto
			bufferQuestao += value + " " + value2 + dadosAdicionais;														
		}
		if (questaoDto.getTipoQuestao().equalsIgnoreCase("1")){ //Faixa de Valores (Números)
			bufferQuestao += value + " " + value2 + dadosAdicionais;																			
		}
		if (questaoDto.getTipoQuestao().equalsIgnoreCase("2")){ //Faixa de Valores (Valores decimais)
			bufferQuestao += value + " " + value2 + dadosAdicionais;																									
		}
		if (questaoDto.getTipoQuestao().equalsIgnoreCase("I")){ //Galeria de Imagens
			
		}
		if (questaoDto.getTipoQuestao().equalsIgnoreCase("M")){ //Galeria Multimídia (Videos,etc.)			}
			bufferQuestao += value + dadosAdicionais;				
		}
		if (questaoDto.getTipoQuestao().equalsIgnoreCase("D")){ //Data
			bufferQuestao += value + dadosAdicionais;				
		}
		if (questaoDto.getTipoQuestao().equalsIgnoreCase("H")){ //Hora
			bufferQuestao += value + dadosAdicionais;				
		}
		if (questaoDto.getTipoQuestao().equalsIgnoreCase("G")){ //Mes/Ano
			bufferQuestao += value + "/" + value2 + dadosAdicionais;																				
		}	
		if (questaoDto.getTipoQuestao().equalsIgnoreCase("8")){ //Listagem
			bufferQuestao += value + dadosAdicionais;		
		}
		if (!questaoDto.isAgrupada()){
			if (!value.equalsIgnoreCase("") || !value2.equalsIgnoreCase("") || !value3.equalsIgnoreCase("")){					
				bufferQuestao += "</td>";
			}
		}
		
		linhaSpoolQuestionario = new LinhaSpoolQuestionario(bufferQuestao);
		linhaSpoolQuestionario.setGenerateTR(true);
		colLinhas.add(linhaSpoolQuestionario);				

		return colLinhas;
	}
	
	private boolean isInCollection(Integer idValor, Collection colVerificar){
		if (colVerificar == null) return false;
		for(Iterator it = colVerificar.iterator(); it.hasNext();){
			RespostaItemQuestionarioOpcoesDTO respItemQuestDto = (RespostaItemQuestionarioOpcoesDTO)it.next();
			if (respItemQuestDto.getIdOpcaoRespostaQuestionario().intValue() == idValor.intValue()){
				return true;
			}
		}
		return false;
	}
}
