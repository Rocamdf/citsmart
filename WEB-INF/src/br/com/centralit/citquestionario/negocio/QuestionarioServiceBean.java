package br.com.centralit.citquestionario.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.integracao.ControleGEDDao;
import br.com.centralit.citquestionario.bean.GrupoQuestionarioDTO;
import br.com.centralit.citquestionario.bean.OpcaoRespostaQuestionarioDTO;
import br.com.centralit.citquestionario.bean.QuestaoQuestionarioDTO;
import br.com.centralit.citquestionario.bean.QuestionarioDTO;
import br.com.centralit.citquestionario.bean.RespostaItemQuestionarioAnexosDTO;
import br.com.centralit.citquestionario.bean.RespostaItemQuestionarioDTO;
import br.com.centralit.citquestionario.integracao.GrupoQuestionarioDao;
import br.com.centralit.citquestionario.integracao.OpcaoRespostaQuestionarioDao;
import br.com.centralit.citquestionario.integracao.QuestaoQuestionarioDao;
import br.com.centralit.citquestionario.integracao.QuestionarioDao;
import br.com.centralit.citquestionario.integracao.RespostaItemQuestionarioAnexosDao;
import br.com.centralit.citquestionario.integracao.RespostaItemQuestionarioDao;
import br.com.centralit.citquestionario.integracao.RespostaItemQuestionarioOpcoesDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.WebUtil;

public class QuestionarioServiceBean extends CrudServicePojoImpl implements QuestionarioService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 
	protected CrudDAO getDao() throws ServiceException {
		
		return new QuestionarioDao();
	}

    private boolean existeIdQuestaoCompartihada(List lista, String id) {
        boolean result = false;
        for(Integer i = 0; i <= lista.size()-1; i++){
            if (((String) lista.get(i)).trim().equalsIgnoreCase(id.trim())) {
                result = true;
                break;
            }
        }
        return result;
    }

    private void verificaAtributosQuestao(QuestaoQuestionarioDTO questaoDto) throws Exception {
        if (questaoDto.getSigla() != null && questaoDto.getSigla().trim().equalsIgnoreCase("")) {
            questaoDto.setSigla(null);
        }
        if (questaoDto.getObrigatoria() == null || questaoDto.getObrigatoria().trim().equalsIgnoreCase("")){
            questaoDto.setObrigatoria("N");
        }
        if (questaoDto.getInfoResposta() == null || questaoDto.getInfoResposta().trim().equalsIgnoreCase("")){
            questaoDto.setInfoResposta("L");
        }
        if (questaoDto.getTipo() == null || questaoDto.getTipo().trim().equalsIgnoreCase("")){
            questaoDto.setTipo("Q");
        }
        if (questaoDto.getTituloQuestaoQuestionario() == null || questaoDto.getTituloQuestaoQuestionario().trim().equalsIgnoreCase("")){
            questaoDto.setTituloQuestaoQuestionario("  ");
        }       
        if (questaoDto.getImprime() == null || questaoDto.getImprime().trim().equalsIgnoreCase("")){
            questaoDto.setImprime("S");
        }   
        if (questaoDto.getCalculada() == null || questaoDto.getCalculada().trim().equalsIgnoreCase("")){
            questaoDto.setCalculada("N");
        }                            
        if (questaoDto.getEditavel() == null || questaoDto.getEditavel().trim().equalsIgnoreCase("")){
            questaoDto.setEditavel("N");
        }          
        if (questaoDto.getUltimoValor() == null || questaoDto.getUltimoValor().trim().equalsIgnoreCase("")){
            questaoDto.setUltimoValor("N");
        }                            
    }
    
	private void validaQuestoes(Object arg0) throws Exception {
	    QuestaoQuestionarioDao questaoDao = new QuestaoQuestionarioDao();
        List lista = new ArrayList();
        QuestionarioDTO questionarioDto = (QuestionarioDTO) arg0;
        Iterator itGrupo = questionarioDto.getColGrupos().iterator();
        for(;itGrupo.hasNext();){
            GrupoQuestionarioDTO grupoDto = (GrupoQuestionarioDTO)itGrupo.next();
            
            if (grupoDto.getColQuestoes() != null){
                Iterator itQuestao = grupoDto.getColQuestoes().iterator();
                for(;itQuestao.hasNext();){
                    QuestaoQuestionarioDTO questaoDto = (QuestaoQuestionarioDTO)itQuestao.next();
                    if (questaoDto.getSigla() != null && !questaoDto.getSigla().trim().equalsIgnoreCase("")) {
                        Integer idQuestao = questaoDto.getIdQuestaoQuestionario();
                        if (idQuestao == null) {
                            idQuestao = 0;
                        }
                        Integer idQuestaoOrigem = questaoDto.getIdQuestaoOrigem();
                        if (idQuestaoOrigem == null) {
                            idQuestaoOrigem = 0;
                        }
                        Collection colQuestoesSigla = questaoDao.listBySiglaAndIdQuestao(questaoDto.getSigla(), idQuestaoOrigem, idQuestao);
                        if (colQuestoesSigla.size() > 0) {
                            //throw new LogicException("A sigla "+questaoDto.getSigla()+" já está sendo utilizada por outra questão.");
                        }
                    }   
                }
            }
        }
    }       
    
	protected void validaCreate(Object arg0) throws Exception {
	    validaQuestoes(arg0);
	}

	protected void validaDelete(Object arg0) throws Exception {

	}

	protected void validaFind(Object arg0) throws Exception {
		
	}

	protected void validaUpdate(Object arg0) throws Exception {
        validaQuestoes(arg0);		
	}

	public IDto create(IDto model) throws ServiceException, LogicException {
		//Instancia Objeto controlador de transacao
		CrudDAO crudDao = getDao();
		QuestaoQuestionarioDao questaoDao = new QuestaoQuestionarioDao();
		GrupoQuestionarioDao grupoDao = new GrupoQuestionarioDao();
		OpcaoRespostaQuestionarioDao opcaoRespostaDao = new OpcaoRespostaQuestionarioDao();
		TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
		QuestionarioDTO questionarioDto = (QuestionarioDTO)model;
		try{
			//Faz validacao, caso exista.
			validaCreate(questionarioDto);
			
			//Seta o TransactionController para os DAOs
			crudDao.setTransactionControler(tc);
			questaoDao.setTransactionControler(tc);
			grupoDao.setTransactionControler(tc);
			opcaoRespostaDao.setTransactionControler(tc);
			
			//Inicia transacao
			tc.start();
			
			//Executa operacoes pertinentes ao negocio.
			((QuestionarioDTO)model).setAtivo("S");
			model = crudDao.create(model);
			((QuestionarioDTO)model).setIdQuestionarioOrigem(((QuestionarioDTO)model).getIdQuestionario());
			crudDao.update(model);
			
			Iterator itGrupo = questionarioDto.getColGrupos().iterator();
			int i = 1;
			for(;itGrupo.hasNext();){
				GrupoQuestionarioDTO grupoDto = (GrupoQuestionarioDTO)itGrupo.next();
				grupoDto.setIdQuestionario(questionarioDto.getIdQuestionario());
				grupoDto.setOrdem(new Integer(i));
				grupoDto = (GrupoQuestionarioDTO) grupoDao.create(grupoDto);
				i++;
				
				if (grupoDto.getColQuestoes() != null){
					Iterator itQuestao = grupoDto.getColQuestoes().iterator();
					int sequenciaQuestaoAux = 1;
					for(;itQuestao.hasNext();){
						QuestaoQuestionarioDTO questaoDto = (QuestaoQuestionarioDTO)itQuestao.next();
						questaoDto.setSequenciaQuestao(new Integer(sequenciaQuestaoAux));
						sequenciaQuestaoAux++;
						questaoDto.setIdGrupoQuestionario(grupoDto.getIdGrupoQuestionario());
						
						if (questaoDto.getTituloQuestaoQuestionario()!=null){
						    questaoDto.setIdQuestaoQuestionario(null);
						    verificaAtributosQuestao(questaoDto);
						    
							questaoDto = (QuestaoQuestionarioDTO) questaoDao.create(questaoDto);
							
							if (questaoDto.getColOpcoesResposta() != null){
								for(Iterator itOpcoes =  questaoDto.getColOpcoesResposta().iterator(); itOpcoes.hasNext();){
									OpcaoRespostaQuestionarioDTO opcaoRespostaDto = (OpcaoRespostaQuestionarioDTO)itOpcoes.next();
									opcaoRespostaDto.setIdQuestaoQuestionario(questaoDto.getIdQuestaoQuestionario());
									if (opcaoRespostaDto.getExibeComplemento().equalsIgnoreCase("S") && opcaoRespostaDto.getQuestaoComplementoDto() != null) {
									    QuestaoQuestionarioDTO questaoComplementoDto = opcaoRespostaDto.getQuestaoComplementoDto();
									    questaoComplementoDto.setIdQuestaoQuestionario(null);
									    questaoComplementoDto.setIdGrupoQuestionario(null);
									    questaoComplementoDto.setIdQuestaoAgrupadora(null);
									    questaoComplementoDto.setIdQuestaoOrigem(null);
									    questaoComplementoDto.setSequenciaQuestao(1);
									    questaoComplementoDto.setUltimoValor(questaoDto.getUltimoValor());
									    verificaAtributosQuestao(questaoComplementoDto);
									    
									    questaoComplementoDto = (QuestaoQuestionarioDTO) questaoDao.create(questaoComplementoDto);
									    opcaoRespostaDto.setIdQuestaoComplemento(questaoComplementoDto.getIdQuestaoQuestionario());
									}
									opcaoRespostaDao.create(opcaoRespostaDto);
								}
							}
                            if (questaoDto.getColQuestoesAgrupadas() != null){
                                Iterator itQuestaoAgrupada = questaoDto.getColQuestoesAgrupadas().iterator();
                                int sequenciaQuestaoAgrupada = 1;
                                for(;itQuestaoAgrupada.hasNext();){
                                    QuestaoQuestionarioDTO questaoAgrupadaDto = (QuestaoQuestionarioDTO)itQuestaoAgrupada.next();
                                    questaoAgrupadaDto.setSequenciaQuestao(new Integer(sequenciaQuestaoAgrupada));
                                    sequenciaQuestaoAgrupada++;
                                    questaoAgrupadaDto.setIdGrupoQuestionario(grupoDto.getIdGrupoQuestionario());
                                    questaoAgrupadaDto.setIdQuestaoAgrupadora(questaoDto.getIdQuestaoQuestionario());
                                    questaoAgrupadaDto.setUltimoValor(questaoDto.getUltimoValor());
                                    
                                    if (questaoAgrupadaDto.getTituloQuestaoQuestionario()!=null){
                                        questaoAgrupadaDto.setIdQuestaoQuestionario(null);
                                        verificaAtributosQuestao(questaoAgrupadaDto);

                                        questaoAgrupadaDto = (QuestaoQuestionarioDTO) questaoDao.create(questaoAgrupadaDto);
                                        
                                        if (questaoAgrupadaDto.getColOpcoesResposta() != null){
                                            for(Iterator itOpcoes =  questaoAgrupadaDto.getColOpcoesResposta().iterator(); itOpcoes.hasNext();){
                                                OpcaoRespostaQuestionarioDTO opcaoRespostaDto = (OpcaoRespostaQuestionarioDTO)itOpcoes.next();
                                                opcaoRespostaDto.setIdQuestaoQuestionario(questaoAgrupadaDto.getIdQuestaoQuestionario());
                                                
                                                opcaoRespostaDao.create(opcaoRespostaDto);
                                            }
                                        }
                                    }
                                }  
                            }
                            if (questaoDto.getColCabecalhosLinha() != null){
                                Iterator itCabecalho = questaoDto.getColCabecalhosLinha().iterator();
                                int sequenciaCabecalho = 1;
                                for(;itCabecalho.hasNext();){
                                    QuestaoQuestionarioDTO cabecalho = (QuestaoQuestionarioDTO)itCabecalho.next();
                                    cabecalho.setSequenciaQuestao(new Integer(sequenciaCabecalho));
                                    sequenciaCabecalho++;
                                    cabecalho.setIdGrupoQuestionario(grupoDto.getIdGrupoQuestionario());
                                    cabecalho.setIdQuestaoAgrupadora(questaoDto.getIdQuestaoQuestionario());
                                    
                                    if (cabecalho.getTituloQuestaoQuestionario()!=null){
                                        cabecalho.setIdQuestaoQuestionario(null);
                                        verificaAtributosQuestao(cabecalho);
                                                
                                        cabecalho = (QuestaoQuestionarioDTO) questaoDao.create(cabecalho);
                                    }
                                }  
                            }
                            if (questaoDto.getColCabecalhosColuna() != null){
                                Iterator itCabecalho = questaoDto.getColCabecalhosColuna().iterator();
                                int sequenciaCabecalho = 1;
                                for(;itCabecalho.hasNext();){
                                    QuestaoQuestionarioDTO cabecalho = (QuestaoQuestionarioDTO)itCabecalho.next();
                                    cabecalho.setSequenciaQuestao(new Integer(sequenciaCabecalho));
                                    sequenciaCabecalho++;
                                    cabecalho.setIdGrupoQuestionario(grupoDto.getIdGrupoQuestionario());
                                    cabecalho.setIdQuestaoAgrupadora(questaoDto.getIdQuestaoQuestionario());
                                    
                                    if (cabecalho.getTituloQuestaoQuestionario()!=null){
                                        cabecalho.setIdQuestaoQuestionario(null);
                                        verificaAtributosQuestao(cabecalho);

                                        cabecalho = (QuestaoQuestionarioDTO) questaoDao.create(cabecalho);
                                    }
                                }  
                            }
						}
					}
				}
			}
			
			//Faz commit e fecha a transacao.
			tc.commit();
			tc.close();
			
			return model;
		}catch(Exception e){
			this.rollbackTransaction(tc, e);
		}
		return model;
	}

	public void update(IDto model) throws ServiceException, LogicException {
		//Instancia Objeto controlador de transacao
		QuestionarioDao crudDao = new QuestionarioDao();
		QuestaoQuestionarioDao questaoDao = new QuestaoQuestionarioDao();
		GrupoQuestionarioDao grupoDao = new GrupoQuestionarioDao();
		OpcaoRespostaQuestionarioDao opcaoRespostaDao = new OpcaoRespostaQuestionarioDao();
		TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
		QuestionarioDTO questionarioDto = (QuestionarioDTO)model;
		try{
			//Faz validacao, caso exista.
			validaUpdate(model);
			
			//Instancia ou obtem os DAOs necessarios.
			
			
			//Seta o TransactionController para os DAOs
			crudDao.setTransactionControler(tc);
			questaoDao.setTransactionControler(tc);
			grupoDao.setTransactionControler(tc);
			opcaoRespostaDao.setTransactionControler(tc);
			
			//Inicia transacao
			tc.start();
			
			if (existeResposta(questionarioDto.getIdQuestionario())){
			    questionarioDto.setAtivo("N");
			    crudDao.updateNotNull(questionarioDto);
			    questionarioDto.setIdQuestionarioOrigem(questionarioDto.getIdQuestionarioOrigem());
			    questionarioDto.setIdQuestionario(null);
			    questionarioDto.setAtivo("S");
			    questionarioDto = (QuestionarioDTO) crudDao.create(questionarioDto);
			}else{
			    questionarioDto.setAtivo("S");
			    crudDao.update(questionarioDto);
    			Collection colGruposRecuperado = grupoDao.listByIdQuestionario(questionarioDto.getIdQuestionario());
    			Iterator itGrupoRecuperado = colGruposRecuperado.iterator();
    			for(;itGrupoRecuperado.hasNext();){
    				GrupoQuestionarioDTO grupoDto = (GrupoQuestionarioDTO)itGrupoRecuperado.next();
    				questaoDao.deleteByIdGrupoQuestionario(grupoDto.getIdGrupoQuestionario());
    				grupoDao.delete(grupoDto);
    			} 
			}
			
			int i = 1;
            Iterator itGrupo = questionarioDto.getColGrupos().iterator();
            for(;itGrupo.hasNext();){
                GrupoQuestionarioDTO grupoDto = (GrupoQuestionarioDTO)itGrupo.next();
                grupoDto.setIdQuestionario(questionarioDto.getIdQuestionario());
                grupoDto.setOrdem(new Integer(i));
                grupoDto = (GrupoQuestionarioDTO) grupoDao.create(grupoDto);
                i++;
                
                if (grupoDto.getColQuestoes() != null){
                    Iterator itQuestao = grupoDto.getColQuestoes().iterator();
                    int sequenciaQuestaoAux = 1;
                    for(;itQuestao.hasNext();){
                        QuestaoQuestionarioDTO questaoDto = (QuestaoQuestionarioDTO)itQuestao.next();
                        questaoDto.setSequenciaQuestao(new Integer(sequenciaQuestaoAux));
                        sequenciaQuestaoAux++;
                        questaoDto.setIdGrupoQuestionario(grupoDto.getIdGrupoQuestionario());
                        
                        if (questaoDto.getTituloQuestaoQuestionario()!=null){
                            questaoDto.setIdQuestaoQuestionario(null);
                            verificaAtributosQuestao(questaoDto);

                            questaoDto = (QuestaoQuestionarioDTO) questaoDao.create(questaoDto);
                            
                            if (questaoDto.getColOpcoesResposta() != null){
                                for(Iterator itOpcoes =  questaoDto.getColOpcoesResposta().iterator(); itOpcoes.hasNext();){
                                    OpcaoRespostaQuestionarioDTO opcaoRespostaDto = (OpcaoRespostaQuestionarioDTO)itOpcoes.next();
                                    opcaoRespostaDto.setIdQuestaoQuestionario(questaoDto.getIdQuestaoQuestionario());
                                    if (opcaoRespostaDto.getExibeComplemento().equalsIgnoreCase("S") && opcaoRespostaDto.getQuestaoComplementoDto() != null) {
                                        QuestaoQuestionarioDTO questaoComplementoDto = opcaoRespostaDto.getQuestaoComplementoDto();
                                        questaoComplementoDto.setIdQuestaoQuestionario(null);
                                        questaoComplementoDto.setIdGrupoQuestionario(null);
                                        questaoComplementoDto.setIdQuestaoAgrupadora(null);
                                        questaoComplementoDto.setIdQuestaoOrigem(null);
                                        questaoComplementoDto.setSequenciaQuestao(1);
                                        questaoComplementoDto.setUltimoValor(questaoDto.getUltimoValor());
                                        verificaAtributosQuestao(questaoComplementoDto);

                                        questaoComplementoDto = (QuestaoQuestionarioDTO) questaoDao.create(questaoComplementoDto);
                                        if (questaoComplementoDto.getColOpcoesResposta() != null){
                                            for(Iterator itOpcoesComp =  questaoComplementoDto.getColOpcoesResposta().iterator(); itOpcoesComp.hasNext();){
                                                OpcaoRespostaQuestionarioDTO opcaoRespostaCompDto = (OpcaoRespostaQuestionarioDTO)itOpcoesComp.next();
                                                opcaoRespostaCompDto.setIdQuestaoQuestionario(questaoComplementoDto.getIdQuestaoQuestionario());
                                                opcaoRespostaDao.create(opcaoRespostaCompDto);
                                            }    
                                        }
                                        opcaoRespostaDto.setIdQuestaoComplemento(questaoComplementoDto.getIdQuestaoQuestionario());
                                    }
                                    opcaoRespostaDto.setIdOpcaoRespostaQuestionario(null);
                                    opcaoRespostaDao.create(opcaoRespostaDto);
                                }
                            }
                            if (questaoDto.getColQuestoesAgrupadas() != null){
                                Iterator itQuestaoAgrupada = questaoDto.getColQuestoesAgrupadas().iterator();
                                int sequenciaQuestaoAgrupada = 1;
                                for(;itQuestaoAgrupada.hasNext();){
                                    QuestaoQuestionarioDTO questaoAgrupadaDto = (QuestaoQuestionarioDTO)itQuestaoAgrupada.next();
                                    questaoAgrupadaDto.setSequenciaQuestao(new Integer(sequenciaQuestaoAgrupada));
                                    sequenciaQuestaoAgrupada++;
                                    questaoAgrupadaDto.setIdGrupoQuestionario(grupoDto.getIdGrupoQuestionario());
                                    questaoAgrupadaDto.setIdQuestaoAgrupadora(questaoDto.getIdQuestaoQuestionario());
                                    questaoAgrupadaDto.setUltimoValor(questaoDto.getUltimoValor());
                                    
                                    if (questaoAgrupadaDto.getTituloQuestaoQuestionario()!=null){
                                        questaoAgrupadaDto.setIdQuestaoQuestionario(null);
                                        verificaAtributosQuestao(questaoAgrupadaDto);
                                        
                                        questaoAgrupadaDto = (QuestaoQuestionarioDTO) questaoDao.create(questaoAgrupadaDto);

                                        if (questaoAgrupadaDto.getColOpcoesResposta() != null){
                                            for(Iterator itOpcoes =  questaoAgrupadaDto.getColOpcoesResposta().iterator(); itOpcoes.hasNext();){
                                                OpcaoRespostaQuestionarioDTO opcaoRespostaDto = (OpcaoRespostaQuestionarioDTO)itOpcoes.next();
                                                opcaoRespostaDto.setIdQuestaoQuestionario(questaoAgrupadaDto.getIdQuestaoQuestionario());
                                                
                                                opcaoRespostaDao.create(opcaoRespostaDto);
                                            }
                                        }
                                    }
                                }  
                            }
                            if (questaoDto.getColCabecalhosLinha() != null){
                                Iterator itCabecalho = questaoDto.getColCabecalhosLinha().iterator();
                                int sequenciaCabecalho = 1;
                                for(;itCabecalho.hasNext();){
                                    QuestaoQuestionarioDTO cabecalho = (QuestaoQuestionarioDTO)itCabecalho.next();
                                    cabecalho.setSequenciaQuestao(new Integer(sequenciaCabecalho));
                                    sequenciaCabecalho++;
                                    cabecalho.setIdGrupoQuestionario(grupoDto.getIdGrupoQuestionario());
                                    cabecalho.setIdQuestaoAgrupadora(questaoDto.getIdQuestaoQuestionario());
                                    
                                    if (cabecalho.getTituloQuestaoQuestionario()!=null){
                                        cabecalho.setIdQuestaoQuestionario(null);
                                        verificaAtributosQuestao(cabecalho);

                                        cabecalho = (QuestaoQuestionarioDTO) questaoDao.create(cabecalho);
                                    }
                                }  
                            }
                            if (questaoDto.getColCabecalhosColuna() != null){
                                Iterator itCabecalho = questaoDto.getColCabecalhosColuna().iterator();
                                int sequenciaCabecalho = 1;
                                for(;itCabecalho.hasNext();){
                                    QuestaoQuestionarioDTO cabecalho = (QuestaoQuestionarioDTO)itCabecalho.next();
                                    cabecalho.setSequenciaQuestao(new Integer(sequenciaCabecalho));
                                    sequenciaCabecalho++;
                                    cabecalho.setIdGrupoQuestionario(grupoDto.getIdGrupoQuestionario());
                                    cabecalho.setIdQuestaoAgrupadora(questaoDto.getIdQuestaoQuestionario());
                                    
                                    if (cabecalho.getTituloQuestaoQuestionario()!=null){
                                        cabecalho.setIdQuestaoQuestionario(null);
                                        verificaAtributosQuestao(cabecalho);

                                        cabecalho = (QuestaoQuestionarioDTO) questaoDao.create(cabecalho);
                                    }
                                }  
                            }
                        }
                    }
                }
            }
			
			//Faz commit e fecha a transacao.
			tc.commit();
			tc.close();
		}catch(Exception e){
			this.rollbackTransaction(tc, e);
		}
	}
	
	public void updateOrdemGrupos(IDto model) throws ServiceException, LogicException {
		//Instancia Objeto controlador de transacao
		CrudDAO crudDao = getDao();
		GrupoQuestionarioDao grupoDao = new GrupoQuestionarioDao();
		TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
		QuestionarioDTO questionarioDto = (QuestionarioDTO)model;
		try{
			//Seta o TransactionController para os DAOs
			grupoDao.setTransactionControler(tc);
			
			//Inicia transacao
			tc.start();
            Iterator itGrupo = questionarioDto.getColGrupos().iterator();
            int i = 1;
            for(;itGrupo.hasNext();){
                GrupoQuestionarioDTO grupoDto = (GrupoQuestionarioDTO)itGrupo.next();
                grupoDto.setIdQuestionario(questionarioDto.getIdQuestionario());
                grupoDto.setNomeGrupoQuestionario(null);
                grupoDto.setOrdem(new Integer(i));
                
                grupoDao.updateOrdem(grupoDto);
                
                i++;
            }
            
			//Faz commit e fecha a transacao.
			tc.commit();
			tc.close();
		}catch(Exception e){
			this.rollbackTransaction(tc, e);
		}
	}
	
	public void updateNomeGrupo(IDto model) throws ServiceException, LogicException {
		//Instancia Objeto controlador de transacao
		CrudDAO crudDao = getDao();
		GrupoQuestionarioDao grupoDao = new GrupoQuestionarioDao();
		TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
		QuestionarioDTO questionarioDto = (QuestionarioDTO)model;
		try{
			//Seta o TransactionController para os DAOs
			grupoDao.setTransactionControler(tc);
			
			//Inicia transacao
			tc.start();

			GrupoQuestionarioDTO grupoDto = new GrupoQuestionarioDTO();
			grupoDto.setIdGrupoQuestionario(questionarioDto.getIdGrupoQuestionario());
			grupoDto.setNomeGrupoQuestionario(questionarioDto.getNomeGrupoQuestionario());
			grupoDto.setIdQuestionario(null);
			grupoDto.setOrdem(null);
			
			grupoDao.updateNome(grupoDto);
			
			//Faz commit e fecha a transacao.
			tc.commit();
			tc.close();
		}catch(Exception e){
			this.rollbackTransaction(tc, e);
		}
	}

    private void recuperaOpcoesResposta(QuestaoQuestionarioDTO questDto, Collection colRespostas) throws ServiceException, LogicException {
        RespostaItemQuestionarioOpcoesDao respItemQuestOpDao = new RespostaItemQuestionarioOpcoesDao();
        RespostaItemQuestionarioAnexosDao respostaItemQuestionarioAnexosDao = new RespostaItemQuestionarioAnexosDao();
        
        try {
            if (colRespostas != null){
                if (colRespostas.size()>0){
                    for (Iterator itAux = colRespostas.iterator(); itAux.hasNext();){ 
                        RespostaItemQuestionarioDTO respostaItemDto = (RespostaItemQuestionarioDTO)itAux.next();
                        if (colRespostas.size() == 1) {
                            questDto.setRespostaItemDto(respostaItemDto);
                        }    
                        Collection colOpcoesResposta = respItemQuestOpDao.getRespostasOpcoesByIdRespostaItemQuestionario(respostaItemDto.getIdRespostaItemQuestionario());
                        respostaItemDto.setColOpcoesResposta(colOpcoesResposta);
                        
                        if (questDto.getTipoQuestao().equalsIgnoreCase("M")){ //Galeria Multimidia
                            Collection colRelacaoAnexos = respostaItemQuestionarioAnexosDao.listByIdRespostaItemQuestionario(respostaItemDto.getIdRespostaItemQuestionario());
                            if (colRelacaoAnexos != null){
                                ControleGEDDao controleGedDao = new ControleGEDDao();
                                for (Iterator itAnexos = colRelacaoAnexos.iterator(); itAnexos.hasNext();){
                                    RespostaItemQuestionarioAnexosDTO respAnexoDto = (RespostaItemQuestionarioAnexosDTO)itAnexos.next();
                                    ControleGEDDTO controleGedDto = new ControleGEDDTO();
                                    String idControleGEDStr = respAnexoDto.getCaminhoAnexo();
                                    if (idControleGEDStr != null){
                                        Integer idControleGED;
                                        try{
                                            idControleGED = new Integer(idControleGEDStr);
                                        }catch (Exception e) {
                                            idControleGED = new Integer(0);
                                        }
                                        controleGedDto.setIdControleGED(idControleGED);
                                        controleGedDto = (ControleGEDDTO) controleGedDao.restore(controleGedDto);
                                        if (controleGedDto != null){
                                            respAnexoDto.setNomeArquivo(controleGedDto.getNomeArquivo());
                                            respAnexoDto.setIdControleGED(controleGedDto.getIdControleGED());
                                        }
                                    }
                                }
                            }
                            respostaItemDto.setColRelacaoAnexos(colRelacaoAnexos);
                        }                                        
                    }
                    questDto.setSerializeRespostas(WebUtil.serializeObjects(colRespostas));
                    questDto.setColRespostas(colRespostas);                                           
                }
            }
        }catch(LogicException e){
            throw new ServiceException(e);
        }catch(Exception e){
            throw new ServiceException(e);
        }
    }
	
    private void obtemRespostaQuestao(QuestaoQuestionarioDTO questDto, Integer idIdentificadorResposta) throws ServiceException, LogicException {
        RespostaItemQuestionarioDao respostaDao = new RespostaItemQuestionarioDao();
        
        try {
            Collection colRespostas = null;
            if (questDto.getTipo().equalsIgnoreCase("L")) {
                colRespostas = respostaDao.listByIdIdentificadorAndIdTabela(idIdentificadorResposta, questDto.getIdQuestaoQuestionario());
            }else{
                colRespostas = respostaDao.listByIdIdentificadorAndIdQuestao(idIdentificadorResposta, questDto.getIdQuestaoQuestionario());
            }  
            recuperaOpcoesResposta(questDto, colRespostas);
        }catch(LogicException e){
            throw new ServiceException(e);
        }catch(Exception e){
            throw new ServiceException(e);
        }
    }

    private void obtemUltimaResposta(QuestaoQuestionarioDTO questDto, Integer idSolicitacaoServico) throws ServiceException, LogicException {
/*        if (questDto.getUltimoValor().equalsIgnoreCase("N")) {
            return;
        }
        
        RespostaItemQuestionarioDao respostaDao = new RespostaItemQuestionarioDao();
        QuestaoQuestionarioDao questaoDao = new QuestaoQuestionarioDao();
        
        try {
            Collection colRespostas = new ArrayList();
            if (questDto.getTipo().equalsIgnoreCase("L") || questDto.getTipo().equalsIgnoreCase("M")) {
                if (questDto.getColQuestoesAgrupadas() != null && questDto.getColQuestoesAgrupadas().size() > 0) {
                    // recupera a última questao respondida da matriz ou tabela
                    RespostaItemQuestionarioDTO respostaItemDto = respostaDao.getUltimaRespostaBySiglaQuestaoAgrupadora(questDto.getSigla(), idPessoa);
                    
                    if (respostaItemDto != null) {
                        Collection colRespQuestaoAgrupada = null;
                        QuestaoQuestionarioDTO questaoAuxDto = new QuestaoQuestionarioDTO();
                        
                        // recupera as respostas da ultima matriz ou tabela  
                        questaoAuxDto.setIdQuestaoQuestionario(respostaItemDto.getIdQuestaoQuestionario());
                        questaoAuxDto = (QuestaoQuestionarioDTO) questaoDao.restore(questaoAuxDto);
                        colRespQuestaoAgrupada = respostaDao.listByIdIdentificadorAndIdTabela(respostaItemDto.getIdIdentificadorResposta(), questaoAuxDto.getIdQuestaoAgrupadora());
                        
                        if (colRespQuestaoAgrupada != null && colRespQuestaoAgrupada.size() > 0) {
                            for (Iterator itResp = colRespQuestaoAgrupada.iterator(); itResp.hasNext();){
                                RespostaItemQuestionarioDTO respQuestaoDto = (RespostaItemQuestionarioDTO) itResp.next();
                                questaoAuxDto.setIdQuestaoQuestionario(respQuestaoDto.getIdQuestaoQuestionario());
                                questaoAuxDto = (QuestaoQuestionarioDTO) questaoDao.restore(questaoAuxDto);
                                if (questaoAuxDto.getSigla() != null) {
                                    // localiza a questão com a mesma sigla
                                    for (Iterator itQuest = questDto.getColQuestoesAgrupadas().iterator(); itQuest.hasNext();){
                                        QuestaoQuestionarioDTO questaoAgrupadaDto = (QuestaoQuestionarioDTO) itQuest.next();
                                        if (questaoAgrupadaDto.getSigla() != null && questaoAgrupadaDto.getSigla().equalsIgnoreCase(questaoAuxDto.getSigla())) {
                                            respQuestaoDto.setIdQuestaoQuestionario(questaoAgrupadaDto.getIdQuestaoQuestionario());
                                            if (questaoAgrupadaDto.getColRespostas() == null) {
                                                questaoAgrupadaDto.setColRespostas(new ArrayList());
                                            }
                                            questaoAgrupadaDto.getColRespostas().add(respQuestaoDto);
                                            if (questDto.getTipo().equalsIgnoreCase("M")) {
                                                questaoAgrupadaDto.setRespostaItemDto(respQuestaoDto);
                                            }
                                            colRespostas.add(respQuestaoDto);
                                            break;
                                        }
                                    }    
                                }
                            }
                        }
                    }    
                }    
            }else{
                // recupera a última resposta dos outros tipos de questão
                RespostaItemQuestionarioDTO respostaItemDto = null;
                if (questDto.getUltimoValor().equalsIgnoreCase("S")) {
                    respostaItemDto = respostaDao.getUltimaRespostaByIdQuestao(questDto.getIdQuestaoOrigem(), idPessoa);
                }else if (questDto.getSigla() != null){
                    respostaItemDto = respostaDao.getUltimaRespostaBySiglaQuestao(questDto.getSigla(), idPessoa);
                }
                if (respostaItemDto != null) {
                    // associa a resposta à questão
                    respostaItemDto.setIdQuestaoQuestionario(questDto.getIdQuestaoQuestionario());
                    colRespostas.add(respostaItemDto);
                }    
            }    
            
            // monta as opções de resposta
            recuperaOpcoesResposta(questDto, colRespostas);
            
            // limpa o identificador e id da resposta
            for (Iterator itAux = colRespostas.iterator(); itAux.hasNext();){ 
                RespostaItemQuestionarioDTO respostaItemDto = (RespostaItemQuestionarioDTO)itAux.next();
                respostaItemDto.setIdIdentificadorResposta(null);
                respostaItemDto.setIdRespostaItemQuestionario(null);
            }    
        }catch(LogicException e){
            throw new ServiceException(e);
        }catch(Exception e){
            throw new ServiceException(e);
        }*/
    } 
    
	public IDto restore(IDto model) throws ServiceException, LogicException {
		GrupoQuestionarioDao grupoDao = new GrupoQuestionarioDao();
		QuestaoQuestionarioDao questaoDao = new QuestaoQuestionarioDao();
		OpcaoRespostaQuestionarioDao opcaoRespostaDao = new OpcaoRespostaQuestionarioDao();
		RespostaItemQuestionarioDao respostaDao = new RespostaItemQuestionarioDao();
		RespostaItemQuestionarioOpcoesDao respItemQuestOpDao = new RespostaItemQuestionarioOpcoesDao();
		QuestionarioDTO questionarioDto = null;
		
		QuestionarioDTO questionarioDtoParm = (QuestionarioDTO)model;
		try{
		    if (questionarioDtoParm.getIdIdentificadorResposta() == null && questionarioDtoParm.getIdQuestionarioOrigem() != null)
		    	questionarioDto = restoreByIdOrigem(questionarioDtoParm.getIdQuestionarioOrigem());
		    
	        if (questionarioDto == null)
	            questionarioDto = (QuestionarioDTO) getDao().restore(model);  

		    if (questionarioDto == null)
		    	return null;
		    
			Collection colGruposRecuperado = grupoDao.listByIdQuestionario(questionarioDto.getIdQuestionario());
			questionarioDto.setColGrupos(colGruposRecuperado);
			
			Iterator it = colGruposRecuperado.iterator();
			for(;it.hasNext();){
				GrupoQuestionarioDTO grupoDto = (GrupoQuestionarioDTO)it.next();
				
				Collection colQuestoes = questaoDao.listByIdGrupoQuestionario(grupoDto.getIdGrupoQuestionario());
				
				if (colQuestoes != null){
					Iterator itQuestoes = colQuestoes.iterator();					
					for(;itQuestoes.hasNext();){
						QuestaoQuestionarioDTO questDto = (QuestaoQuestionarioDTO)itQuestoes.next();
						if (questDto.getValorPermitido1() == null){
							questDto.setValorPermitido1(new Double(0));
						}
						if (questDto.getValorPermitido2() == null){
							questDto.setValorPermitido2(new Double(0));
						}
						  
						Collection colOpcoesResposta = opcaoRespostaDao.listByIdQuestaoQuestionario(questDto.getIdQuestaoQuestionario());
						if (colOpcoesResposta != null) {
                            for(Iterator itOpcao = colOpcoesResposta.iterator(); itOpcao.hasNext();){
                                OpcaoRespostaQuestionarioDTO opcaoResposta = (OpcaoRespostaQuestionarioDTO)itOpcao.next();
                                if (opcaoResposta.getExibeComplemento() != null && opcaoResposta.getExibeComplemento().equalsIgnoreCase("S") && opcaoResposta.getIdQuestaoComplemento() != null){
                                    QuestaoQuestionarioDTO questaoComplementoDto = new QuestaoQuestionarioDTO();
                                    questaoComplementoDto.setIdQuestaoQuestionario(opcaoResposta.getIdQuestaoComplemento());
                                    questaoComplementoDto = (QuestaoQuestionarioDTO) questaoDao.restore(questaoComplementoDto);
                                    if (questionarioDtoParm.getIdIdentificadorResposta()!=null) {
                                        obtemRespostaQuestao(questaoComplementoDto, questionarioDtoParm.getIdIdentificadorResposta());
                                    }else if (questaoComplementoDto.getUltimoValor() != null && !questaoComplementoDto.getUltimoValor().equalsIgnoreCase("N") && 
                                              questionarioDtoParm.getModo() != null && questionarioDtoParm.getModo().equalsIgnoreCase(QuestionarioDTO.MODO_VISUALIZACAO)) {
                                        obtemUltimaResposta(questaoComplementoDto, questionarioDtoParm.getIdSolicitacaoServico());  
                                    }
                                    Collection colOpcoesRespostaComplemento = opcaoRespostaDao.listByIdQuestaoQuestionario(questaoComplementoDto.getIdQuestaoQuestionario());
                                    questaoComplementoDto.setSerializeOpcoesResposta(WebUtil.serializeObjects(colOpcoesRespostaComplemento));
                                    questaoComplementoDto.setColOpcoesResposta(colOpcoesRespostaComplemento);
                                    opcaoResposta.setSerializeQuestaoComplemento(WebUtil.serializeObject(questaoComplementoDto));  
                                    opcaoResposta.setQuestaoComplementoDto(questaoComplementoDto);
                                }
                            }
						}
                        questDto.setSerializeOpcoesResposta(WebUtil.serializeObjects(colOpcoesResposta));						
                        questDto.setColOpcoesResposta(colOpcoesResposta);
                        
		                Collection colQuestoesAgrupadas = questaoDao.listByIdQuestaoAgrupadora(questDto.getIdQuestaoQuestionario());
		                questDto.setColQuestoesAgrupadas(colQuestoesAgrupadas);
		                if (colQuestoesAgrupadas != null){
		                    Iterator itQuestoesAgrup = colQuestoesAgrupadas.iterator();                   
		                    for(;itQuestoesAgrup.hasNext();){
		                        QuestaoQuestionarioDTO questAgrup = (QuestaoQuestionarioDTO)itQuestoesAgrup.next();
		                        
		                        if (questionarioDtoParm.getIdIdentificadorResposta()!=null){
		                            Collection colRespostas = null;
		                            if (questAgrup.getTipo().equalsIgnoreCase("L")) {
		                                colRespostas = respostaDao.listByIdIdentificadorAndIdTabela(questionarioDtoParm.getIdIdentificadorResposta(),questAgrup.getIdQuestaoQuestionario());
		                            }else{
		                                colRespostas = respostaDao.listByIdIdentificadorAndIdQuestao(questionarioDtoParm.getIdIdentificadorResposta(),questAgrup.getIdQuestaoQuestionario());
		                            }    
		                            if (colRespostas != null){
		                                if (colRespostas.size()>0){
		                                    for (Iterator itAux = colRespostas.iterator(); itAux.hasNext();){ 
		                                        RespostaItemQuestionarioDTO respostaItemDto = (RespostaItemQuestionarioDTO)itAux.next();
		                                        if (colRespostas.size() == 1) {
		                                            questAgrup.setRespostaItemDto(respostaItemDto);
		                                        }    
	                                            if (questAgrup.getTipoQuestao().equalsIgnoreCase("R") || questAgrup.getTipoQuestao().equalsIgnoreCase("C")
	                                                    || questAgrup.getTipoQuestao().equalsIgnoreCase("X")){
	                                                Collection colOpcoesRespostaAgrup = respItemQuestOpDao.getRespostasOpcoesByIdRespostaItemQuestionario(respostaItemDto.getIdRespostaItemQuestionario());
	                                                respostaItemDto.setColOpcoesResposta(colOpcoesRespostaAgrup);
	                                            }
		                                    }
		                                    questAgrup.setSerializeRespostas(WebUtil.serializeObjects(colRespostas));
		                                    questAgrup.setColRespostas(colRespostas);		                                    
		                                }
		                            } 
		                        }
		                        
		                        Collection colOpcoesRespostaAgrup = opcaoRespostaDao.listByIdQuestaoQuestionario(questAgrup.getIdQuestaoQuestionario());
		                        questAgrup.setSerializeOpcoesResposta(WebUtil.serializeObjects(colOpcoesRespostaAgrup));
		                        questAgrup.setColOpcoesResposta(colOpcoesRespostaAgrup);
		                    }
		                }	
		                
                        if (questionarioDtoParm.getIdIdentificadorResposta()!=null) {
                            obtemRespostaQuestao(questDto, questionarioDtoParm.getIdIdentificadorResposta());
                        }else if (questDto.getUltimoValor() != null && !questDto.getUltimoValor().equalsIgnoreCase("N") && 
                                  questionarioDtoParm.getModo() != null && questionarioDtoParm.getModo().equalsIgnoreCase(QuestionarioDTO.MODO_VISUALIZACAO)) {
                            obtemUltimaResposta(questDto, questionarioDtoParm.getIdSolicitacaoServico());  
                        }

		                Collection colCabecalhosLinha = questaoDao.listCabecalhosLinha(questDto.getIdQuestaoQuestionario());
                        questDto.setSerializeCabecalhosLinha(WebUtil.serializeObjects(colCabecalhosLinha));
                        questDto.setColCabecalhosLinha(colCabecalhosLinha);	
                        Collection colCabecalhosColuna = questaoDao.listCabecalhosColuna(questDto.getIdQuestaoQuestionario());
                        questDto.setSerializeCabecalhosColuna(WebUtil.serializeObjects(colCabecalhosColuna));
                        questDto.setColCabecalhosColuna(colCabecalhosColuna);                             
					}
					
				}
                grupoDto.setColQuestoes(colQuestoes);
			}
			
			return questionarioDto;
		}catch(LogicException e){
			throw new ServiceException(e);
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

	public Collection listByIdEmpresa(Integer idEmpresa) throws Exception{
		QuestionarioDao questDao = new QuestionarioDao();
		return questDao.listByIdEmpresa(idEmpresa);
	}

    public Collection listByIdEmpresaAndAplicacao(Integer idEmpresa, String aplicacao) throws Exception{
        QuestionarioDao questDao = new QuestionarioDao();
        return questDao.listByIdEmpresaAndAplicacao(idEmpresa, aplicacao);
    }
	
    public boolean existeResposta(Integer idQuestionario) throws Exception{
        QuestionarioDao questDao = new QuestionarioDao();
        return questDao.existeResposta(idQuestionario);           
    }
    
    public QuestionarioDTO restoreByIdOrigem(Integer idQuestionarioOrigem) throws Exception {
        QuestionarioDao questDao = new QuestionarioDao();
        return questDao.restoreByIdOrigem(idQuestionarioOrigem);           
    }
    
	public void copyGroup(IDto model) throws ServiceException, LogicException {
		//Instancia Objeto controlador de transacao
		CrudDAO crudDao = getDao();
		QuestaoQuestionarioDao questaoDao = new QuestaoQuestionarioDao();
		GrupoQuestionarioDao grupoDao = new GrupoQuestionarioDao();
		OpcaoRespostaQuestionarioDao opcaoRespostaDao = new OpcaoRespostaQuestionarioDao();
		TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
		QuestionarioDTO questionarioDto = (QuestionarioDTO)model;
		try{
			//Seta o TransactionController para os DAOs
			questaoDao.setTransactionControler(tc);
			grupoDao.setTransactionControler(tc);
			opcaoRespostaDao.setTransactionControler(tc);
			
			//Inicia transacao
			tc.start();
			
			Collection colQuestoes = questaoDao.listByIdGrupoQuestionario(questionarioDto.getIdGrupoQuestionario());
			
			GrupoQuestionarioDTO grupoDto = new GrupoQuestionarioDTO();
			grupoDto.setIdQuestionario(questionarioDto.getIdQuestionarioCopiar());
			grupoDto.setNomeGrupoQuestionario(questionarioDto.getNomeGrupoQuestionario());
			grupoDto = (GrupoQuestionarioDTO) grupoDao.create(grupoDto);			
			
			Iterator itQuestoes = colQuestoes.iterator();
			for(;itQuestoes.hasNext();){
				QuestaoQuestionarioDTO questaoQuestionarioDto = (QuestaoQuestionarioDTO)itQuestoes.next();
				questaoQuestionarioDto.setIdGrupoQuestionario(grupoDto.getIdGrupoQuestionario());
				
				Collection colOpcoesResposta = opcaoRespostaDao.listByIdQuestaoQuestionario(questaoQuestionarioDto.getIdQuestaoQuestionario());
				
				questaoQuestionarioDto = (QuestaoQuestionarioDTO) questaoDao.create(questaoQuestionarioDto);
				
				if (colOpcoesResposta != null){
					for(Iterator itOp = colOpcoesResposta.iterator(); itOp.hasNext();){
						OpcaoRespostaQuestionarioDTO opRespDto = (OpcaoRespostaQuestionarioDTO)itOp.next();
						opRespDto.setIdQuestaoQuestionario(questaoQuestionarioDto.getIdQuestaoQuestionario());
						
						opcaoRespostaDao.create(opRespDto);
					}
				}
			}
						
			//Faz commit e fecha a transacao.
			tc.commit();
			tc.close();
		}catch(Exception e){
			this.rollbackTransaction(tc, e);
		}
	}

	public Collection listOpcoesRespostaItemQuestionarioOpcoes(Integer idRespostaItemQuestionario) throws Exception{
		RespostaItemQuestionarioOpcoesDao dao = new RespostaItemQuestionarioOpcoesDao();
		return dao.getRespostasOpcoesByIdRespostaItemQuestionario(idRespostaItemQuestionario);
	}
   
}
