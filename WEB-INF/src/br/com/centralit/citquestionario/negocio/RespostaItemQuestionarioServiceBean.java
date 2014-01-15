package br.com.centralit.citquestionario.negocio;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citquestionario.bean.QuestaoQuestionarioDTO;
import br.com.centralit.citquestionario.bean.RespostaItemAuxiliarDTO;
import br.com.centralit.citquestionario.bean.RespostaItemQuestionarioAnexosDTO;
import br.com.centralit.citquestionario.bean.RespostaItemQuestionarioDTO;
import br.com.centralit.citquestionario.bean.RespostaItemQuestionarioOpcoesDTO;
import br.com.centralit.citquestionario.integracao.QuestaoQuestionarioDao;
import br.com.centralit.citquestionario.integracao.RespostaItemQuestionarioAnexosDao;
import br.com.centralit.citquestionario.integracao.RespostaItemQuestionarioDao;
import br.com.centralit.citquestionario.integracao.RespostaItemQuestionarioOpcoesDao;
import br.com.centralit.citquestionario.util.ConstantesQuestionario;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;

public class RespostaItemQuestionarioServiceBean extends CrudServicePojoImpl implements RespostaItemQuestionarioService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected CrudDAO getDao() throws ServiceException {
		
		return new RespostaItemQuestionarioDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
		
	}

	protected void validaDelete(Object arg0) throws Exception {
		
	}

	protected void validaFind(Object arg0) throws Exception {
		
	}

	protected void validaUpdate(Object arg0) throws Exception {
		
	}
	/**
	 * Pega o ID que se encontra na colllection.
	 * @param col
	 * @return
	 */
	private Integer getIdInCollection(Collection col, String textoBusca ){
		if (col != null){
			for(Iterator it = col.iterator(); it.hasNext();){
				RespostaItemAuxiliarDTO respItem = (RespostaItemAuxiliarDTO)it.next();
				if (respItem.getFieldName().trim().equalsIgnoreCase(textoBusca)){
					if (respItem.getFieldValue() != null && !respItem.getFieldValue().trim().equalsIgnoreCase("")){
						return new Integer(Integer.parseInt(respItem.getFieldValue()));
					}
				}
			}
		}
		return null;
	}
	/**
	 * Pega o Texto que se encontra na colllection.
	 * @param col
	 * @return
	 */
	private String getTextoInCollection(Collection col, String textoBusca){
		if (col != null){
			for(Iterator it = col.iterator(); it.hasNext();){
				RespostaItemAuxiliarDTO respItem = (RespostaItemAuxiliarDTO)it.next();
				if (respItem.getFieldName().trim().equalsIgnoreCase(textoBusca)){
					if (respItem.getFieldValue() != null && !respItem.getFieldValue().trim().equalsIgnoreCase("")){
						return respItem.getFieldValue();
					}
				}
			}
		}
		return null;
	}
	/**
	 * Recebe uma Collection de RespostaItemAuxiliarDTO e processa.
	 * @param tc
	 * @param col
	 * @param idIdentificadorResposta
	 * @throws Exception
	 */
	public Collection processCollection(TransactionControler tc, Collection col, Collection colAnexos, Integer idIdentificadorResposta, HttpServletRequest request) throws Exception{
		QuestaoQuestionarioDTO questaoQuestionarioDto;
		RespostaItemQuestionarioDTO respostaItemQuestionarioDto;
		RespostaItemQuestionarioOpcoesDTO respostaItemQuestionarioOpcoesDto;
		
		QuestaoQuestionarioDao questaoDao = new QuestaoQuestionarioDao();
		RespostaItemQuestionarioDao respostaItemDao = new RespostaItemQuestionarioDao();
		RespostaItemQuestionarioOpcoesDao respItemOpcoesDao = new RespostaItemQuestionarioOpcoesDao();
		RespostaItemQuestionarioAnexosDao respostaItemQuestionarioAnexosDao = new RespostaItemQuestionarioAnexosDao();
		
		questaoDao.setTransactionControler(tc);
		respostaItemDao.setTransactionControler(tc);
		respItemOpcoesDao.setTransactionControler(tc);
		respostaItemQuestionarioAnexosDao.setTransactionControler(tc);
		
		Collection result = new ArrayList(); //Esta collection apenas armazena os CIDS. Para verificacao entre outro passo sobre vigilancia epidemiologica.
		
		if (colAnexos != null && colAnexos.size() > 0){
			boolean bPrimVez = true;
			RespostaItemQuestionarioDTO respostaItemQuestionarioDtoAux = new RespostaItemQuestionarioDTO();
			for(Iterator it = colAnexos.iterator(); it.hasNext();){
				RespostaItemQuestionarioAnexosDTO respItemAnexo = (RespostaItemQuestionarioAnexosDTO)it.next();
				if (bPrimVez){
					respostaItemQuestionarioDtoAux.setIdQuestaoQuestionario(respItemAnexo.getIdQuestaoQuestionario());
					respostaItemQuestionarioDtoAux.setIdIdentificadorResposta(idIdentificadorResposta);
					respostaItemQuestionarioDtoAux = (RespostaItemQuestionarioDTO) respostaItemDao.create(respostaItemQuestionarioDtoAux);
				}
				bPrimVez = false;
				
				respItemAnexo.setIdRespostaItemQuestionario(respostaItemQuestionarioDtoAux.getIdRespostaItemQuestionario());
				respostaItemQuestionarioAnexosDao.create(respItemAnexo);
			}
		}
		if (col != null){
			for(Iterator it = col.iterator(); it.hasNext();){
				RespostaItemAuxiliarDTO respItem = (RespostaItemAuxiliarDTO)it.next();
				if (respItem.getFieldName().length()<9) continue;
				if (respItem.getFieldName().substring(0, 9).equalsIgnoreCase("campoDyn_")){
					String idQuestaoStr = respItem.getFieldName().substring(9);
					if (idQuestaoStr.indexOf(" ") != -1){
					    idQuestaoStr = idQuestaoStr.substring(0,idQuestaoStr.indexOf(" "));
					}
					String sequencialResposta = "";
					if (idQuestaoStr.indexOf("#") != -1){
					    int p = idQuestaoStr.indexOf("#"); 
					    sequencialResposta = idQuestaoStr.substring(0, p);
					    idQuestaoStr = idQuestaoStr.substring(p+1);
					}
					questaoQuestionarioDto = new QuestaoQuestionarioDTO();
					questaoQuestionarioDto.setIdQuestaoQuestionario(new Integer(Integer.parseInt(idQuestaoStr)));
					
					questaoQuestionarioDto = (QuestaoQuestionarioDTO) questaoDao.restore(questaoQuestionarioDto);
					
					respostaItemQuestionarioDto = new RespostaItemQuestionarioDTO();
					respostaItemQuestionarioDto.setIdIdentificadorResposta(idIdentificadorResposta);
					respostaItemQuestionarioDto.setIdQuestaoQuestionario(new Integer(Integer.parseInt(idQuestaoStr)));
					
					
					
					if (questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("T") || 
							questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("L") ||
							questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("A") ||
							questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("9")){
							respostaItemQuestionarioDto.setRespostaTextual(respItem.getFieldValue());
							if(questaoQuestionarioDto.getObrigatoria().equalsIgnoreCase("S")){
								if(respostaItemQuestionarioDto.getRespostaTextual().equalsIgnoreCase("")){
									throw new LogicException(UtilI18N.internacionaliza(request, "questionario.camposObrigatoriosNaoPreenchidos"));
								}
							}
					}
					if (questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("N")){
						if (respItem.getFieldValue()!=null && !respItem.getFieldValue().trim().equalsIgnoreCase("")){
							String aux = respItem.getFieldValue().replaceAll("\\.", "");
							aux = aux.replaceAll("\\,", "\\.");							
							try{
								respostaItemQuestionarioDto.setRespostaNumero(Double.valueOf(aux));
							}catch (Exception e) {
								e.printStackTrace();
								respostaItemQuestionarioDto.setRespostaNumero(new Double(0));
							}
							if(respostaItemQuestionarioDto.getRespostaNumero() == null && questaoQuestionarioDto.getObrigatoria().equalsIgnoreCase("S")){
								throw new LogicException(UtilI18N.internacionaliza(request, "questionario.camposObrigatoriosNaoPreenchidos"));
							}
						}
					}
					if (questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("V")){
						if (respItem.getFieldValue()!=null && !respItem.getFieldValue().trim().equalsIgnoreCase("")){
							String aux = respItem.getFieldValue().replaceAll("\\.", "");
							aux = aux.replaceAll("\\,", "\\.");
							try{
								respostaItemQuestionarioDto.setRespostaValor(Double.valueOf(aux));
							}catch (Exception e) {
								e.printStackTrace();
								respostaItemQuestionarioDto.setRespostaValor(new Double(0));
							}
							if(respostaItemQuestionarioDto.getRespostaValor() == null && questaoQuestionarioDto.getObrigatoria().equalsIgnoreCase("S")){
								throw new LogicException(UtilI18N.internacionaliza(request, "questionario.camposObrigatoriosNaoPreenchidos"));
							}
						}
					}
					if (questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("%")){
						if (respItem.getFieldValue()!=null && !respItem.getFieldValue().trim().equalsIgnoreCase("")){
							String aux = respItem.getFieldValue().replaceAll("\\.", "");;
							aux = aux.replaceAll("\\,", "\\.");
							try{
								respostaItemQuestionarioDto.setRespostaPercentual(Double.valueOf(aux));
							}catch (Exception e) {
								e.printStackTrace();
								respostaItemQuestionarioDto.setRespostaPercentual(new Double(0));
							}	
							
							if(respostaItemQuestionarioDto.getRespostaPercentual() == null && questaoQuestionarioDto.getObrigatoria().equalsIgnoreCase("S")){
								throw new LogicException(UtilI18N.internacionaliza(request, "questionario.camposObrigatoriosNaoPreenchidos"));
							}
						}
					}
					if (questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("D")){
						if (respItem.getFieldValue()!=null && !respItem.getFieldValue().trim().equalsIgnoreCase("")){
							try{
								respostaItemQuestionarioDto.setRespostaData(UtilDatas.strToSQLDate(respItem.getFieldValue()));
							}catch (Exception e) {
								e.printStackTrace();
								respostaItemQuestionarioDto.setRespostaData(null);
							}
							if(respostaItemQuestionarioDto.getRespostaData() == null && questaoQuestionarioDto.getObrigatoria().equalsIgnoreCase("S")){
								throw new LogicException(UtilI18N.internacionaliza(request, "questionario.camposObrigatoriosNaoPreenchidos"));
							}
						}
					}	
					if (questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("H")){
						if (respItem.getFieldValue()!=null && !respItem.getFieldValue().trim().equalsIgnoreCase("")){
							respostaItemQuestionarioDto.setRespostaHora(respItem.getFieldValue().replaceAll(":", ""));
						}
					}
					if (questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("Q")){ //Frequencia Cardiaca
						if (respItem.getFieldValue()!=null && !respItem.getFieldValue().trim().equalsIgnoreCase("")){
							String[] resp = respItem.getFieldValue().split(ConstantesQuestionario.CARACTER_SEPARADOR);
							if (resp != null){
								if (resp[0] != null && !resp[0].trim().equalsIgnoreCase("")){
									try{
										respostaItemQuestionarioDto.setRespostaValor(new Double(resp[0]));		
									}catch (Exception e) {
										e.printStackTrace();
										respostaItemQuestionarioDto.setRespostaValor(new Double(0));	
									}
								}
							}
							if(respostaItemQuestionarioDto.getRespostaValor() == null && questaoQuestionarioDto.getObrigatoria().equalsIgnoreCase("S")){
								throw new LogicException(UtilI18N.internacionaliza(request, "questionario.camposObrigatoriosNaoPreenchidos"));
							}
						}
					}	
					if (questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("G")){ //Mes e Ano
						if (respItem.getFieldValue()!=null && !respItem.getFieldValue().trim().equalsIgnoreCase("")){
							String[] resp = respItem.getFieldValue().split(ConstantesQuestionario.CARACTER_SEPARADOR);
							if (resp != null){
								if (resp[0] != null && !resp[0].trim().equalsIgnoreCase("")){
									respostaItemQuestionarioDto.setRespostaMes(new Integer(resp[0]));
								}
								if (resp.length > 1){
									if (resp[1] != null && !resp[1].trim().equalsIgnoreCase("")){
										respostaItemQuestionarioDto.setRespostaAno(new Integer(resp[1]));
									}	
								}
							}
						}
					}
					if (questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("*")){ //% e Valor
						if (respItem.getFieldValue()!=null && !respItem.getFieldValue().trim().equalsIgnoreCase("")){
							String[] resp = respItem.getFieldValue().split(ConstantesQuestionario.CARACTER_SEPARADOR);
							if (resp != null){
								if (resp[0] != null && !resp[0].trim().equalsIgnoreCase("")){
									resp[0] = resp[0].replaceAll("\\.", "");
									resp[0] = resp[0].replaceAll("\\,", "\\.");
									respostaItemQuestionarioDto.setRespostaPercentual(new Double(resp[0]));
								}
								if (resp.length > 1){
									if (resp[1] != null && !resp[1].trim().equalsIgnoreCase("")){
										resp[1] = resp[1].replaceAll("\\.", "");
										resp[1] = resp[1].replaceAll("\\,", "\\.");
										respostaItemQuestionarioDto.setRespostaValor(new Double(resp[1]));
									}
								}
							}
						}
					}
					if (questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("1")){ //Faixa de Valores Numeros
						if (respItem.getFieldValue()!=null && !respItem.getFieldValue().trim().equalsIgnoreCase("")){
							String[] resp = respItem.getFieldValue().split(ConstantesQuestionario.CARACTER_SEPARADOR);
							if (resp != null){
								if (resp[0] != null && !resp[0].trim().equalsIgnoreCase("")){
									resp[0] = resp[0].replaceAll("\\.", "");
									resp[0] = resp[0].replaceAll("\\,", "\\.");
									respostaItemQuestionarioDto.setRespostaNumero(new Double(resp[0]));
								}
								if (resp.length > 1){
									if (resp[1] != null && !resp[1].trim().equalsIgnoreCase("")){
										resp[1] = resp[1].replaceAll("\\.", "");
										resp[1] = resp[1].replaceAll("\\,", "\\.");
										respostaItemQuestionarioDto.setRespostaNumero2(new Double(resp[1]));
									}	
								}
							}
							if(respostaItemQuestionarioDto.getRespostaNumero2() == null && questaoQuestionarioDto.getObrigatoria().equalsIgnoreCase("S")){
								throw new LogicException(UtilI18N.internacionaliza(request, "questionario.camposObrigatoriosNaoPreenchidos"));
							}
						}							
					}
					if (questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("2")){ //Faixa de Valores Decimais
						if (respItem.getFieldValue()!=null && !respItem.getFieldValue().trim().equalsIgnoreCase("")){
							String[] resp = respItem.getFieldValue().split(ConstantesQuestionario.CARACTER_SEPARADOR);
							if (resp != null){
								if (resp[0] != null && !resp[0].trim().equalsIgnoreCase("")){
									resp[0] = resp[0].replaceAll("\\.", "");
									resp[0] = resp[0].replaceAll("\\,", "\\.");
									respostaItemQuestionarioDto.setRespostaValor(new Double(resp[0]));
								}
								if (resp.length > 1){
									if (resp[1] != null && !resp[1].trim().equalsIgnoreCase("")){
										resp[1] = resp[1].replaceAll("\\.", "");
										resp[1] = resp[1].replaceAll("\\,", "\\.");
										respostaItemQuestionarioDto.setRespostaValor2(new Double(resp[1]));
									}	
								}
							}
							if(respostaItemQuestionarioDto.getRespostaValor2() == null && questaoQuestionarioDto.getObrigatoria().equalsIgnoreCase("S")){
								throw new LogicException(UtilI18N.internacionaliza(request, "questionario.camposObrigatoriosNaoPreenchidos"));
							}
						}							
					}						
                    if (questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("8")){ //Listagem
                        if (respItem.getFieldValue()!=null && !respItem.getFieldValue().trim().equalsIgnoreCase("")){
                            respostaItemQuestionarioDto.setRespostaIdListagem(respItem.getFieldValue().trim());
                        }                           
                    }                       
					respostaItemQuestionarioDto = (RespostaItemQuestionarioDTO) respostaItemDao.create(respostaItemQuestionarioDto);
					
					
					
						if (!respItem.isMultiple()){
							if (questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("R") 
									|| questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("C")
									|| questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("X")){
								respostaItemQuestionarioOpcoesDto = new RespostaItemQuestionarioOpcoesDTO();
								respostaItemQuestionarioOpcoesDto.setIdOpcaoRespostaQuestionario(new Integer(Integer.parseInt(respItem.getFieldValue())));
								respostaItemQuestionarioOpcoesDto.setIdRespostaItemQuestionario(respostaItemQuestionarioDto.getIdRespostaItemQuestionario());
								respItemOpcoesDao.create(respostaItemQuestionarioOpcoesDto);
							}
						}else{
							if (questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("R") 
											|| questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("C")
											|| questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("X")){
								respItem.setFieldValue(respItem.getFieldValue() + ConstantesQuestionario.CARACTER_SEPARADOR);
								String[] resp = respItem.getFieldValue().split(ConstantesQuestionario.CARACTER_SEPARADOR);
								if (resp != null){
									for(int i = 0; i < resp.length; i++){
										if (resp[i] != null && !resp[i].trim().equalsIgnoreCase("")){
											respostaItemQuestionarioOpcoesDto = new RespostaItemQuestionarioOpcoesDTO();
											try{
												respostaItemQuestionarioOpcoesDto.setIdOpcaoRespostaQuestionario(new Integer(Integer.parseInt(resp[i])));
												respostaItemQuestionarioOpcoesDto.setIdRespostaItemQuestionario(respostaItemQuestionarioDto.getIdRespostaItemQuestionario());
												respItemOpcoesDao.create(respostaItemQuestionarioOpcoesDto);
											}catch (Exception e) {
												e.printStackTrace();
											}
										}
									}
								}
							}
						}
					}
				}
			}
		return result;
	}
	

	public void deleteByIdQuestaoAndIdentificadorResposta(Integer idQuestaoQuestionario, Integer idIdentificadorResposta) throws Exception {
        RespostaItemQuestionarioDao respostaItemDao = new RespostaItemQuestionarioDao();
        respostaItemDao.deleteByIdQuestaoAndIdentificadorResposta(idQuestaoQuestionario, idIdentificadorResposta);          
	}
	
	public Collection listByIdIdentificadorAndIdQuestao(Integer idIdentificadorResposta, Integer idQuestaoQuestionario) throws Exception {
        RespostaItemQuestionarioDao respostaItemDao = new RespostaItemQuestionarioDao();
        return respostaItemDao.listByIdIdentificadorAndIdQuestao(idIdentificadorResposta, idQuestaoQuestionario);          
	}
	
    public Collection getRespostasOpcoesByIdRespostaItemQuestionario(Integer idRespostaItemQuestionario) throws Exception {
    	RespostaItemQuestionarioOpcoesDao respostaItemQuestionarioOpcoesDao = new RespostaItemQuestionarioOpcoesDao();
        return respostaItemQuestionarioOpcoesDao.getRespostasOpcoesByIdRespostaItemQuestionario(idRespostaItemQuestionario);
    }
    
    public void deleteByIdIdentificadorResposta(
            final RespostaItemQuestionarioDTO resposta) 
    throws ServiceException, LogicException
    {
        RespostaItemQuestionarioDao dao = (RespostaItemQuestionarioDao)getDao();
        TransactionControler tc = new TransactionControlerImpl(dao.
                getAliasDB());
        try{
            deleteByIdIdentificadorResposta(resposta, tc);
            tc.commit();
            tc.close();
        }catch(Exception e){
            e.printStackTrace();
            this.rollbackTransaction(tc, e);
        }
    }
    
    public void deleteByIdIdentificadorResposta(
            final RespostaItemQuestionarioDTO resposta, 
            final TransactionControler tc) 
    throws Exception
    {
        validaDelete(resposta);
        RespostaItemQuestionarioDao dao = (RespostaItemQuestionarioDao)getDao();
        RespostaItemQuestionarioAnexosDao riqaDao = new RespostaItemQuestionarioAnexosDao();
        RespostaItemQuestionarioOpcoesDao riqoDao = new RespostaItemQuestionarioOpcoesDao();
        dao.setTransactionControler(tc);
        riqaDao.setTransactionControler(tc);
        riqoDao.setTransactionControler(tc);
        tc.start();
        
        List where = new ArrayList();
        where.add(new Condition("idIdentificadorResposta", "=", 
                resposta.getIdIdentificadorResposta()));
        Collection respostas = dao.findByCondition(where, new ArrayList());
        
        if(respostas != null && !respostas.isEmpty()){
            RespostaItemQuestionarioDTO resp = null;
            for(Iterator it = respostas.iterator(); it.hasNext();){
                resp = (RespostaItemQuestionarioDTO)it.next();
                riqoDao.deleteByIdRespostaItemQuestionario(
                        resp.getIdRespostaItemQuestionario());
                riqaDao.deleteByIdRespostaItemQuestionario(
                        resp.getIdRespostaItemQuestionario());
            }
            dao.deleteByIdIdentificadorResposta(resposta.
                    getIdIdentificadorResposta());
        }
    }

}