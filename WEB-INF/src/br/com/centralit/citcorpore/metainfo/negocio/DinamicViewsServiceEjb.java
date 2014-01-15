package br.com.centralit.citcorpore.metainfo.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import br.com.centralit.citajax.exception.LogicException;
import br.com.centralit.citcorpore.bean.MatrizVisaoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.MatrizVisaoDao;
import br.com.centralit.citcorpore.metainfo.bean.CamposObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.DinamicViewsDTO;
import br.com.centralit.citcorpore.metainfo.bean.GrupoVisaoCamposNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.GrupoVisaoDTO;
import br.com.centralit.citcorpore.metainfo.bean.LookupDTO;
import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.ReturnLookupDTO;
import br.com.centralit.citcorpore.metainfo.bean.ScriptsVisaoDTO;
import br.com.centralit.citcorpore.metainfo.bean.VinculoVisaoDTO;
import br.com.centralit.citcorpore.metainfo.bean.VisaoDTO;
import br.com.centralit.citcorpore.metainfo.bean.VisaoRelacionadaDTO;
import br.com.centralit.citcorpore.metainfo.integracao.CamposObjetoNegocioDao;
import br.com.centralit.citcorpore.metainfo.integracao.GrupoVisaoCamposNegocioDao;
import br.com.centralit.citcorpore.metainfo.integracao.GrupoVisaoDao;
import br.com.centralit.citcorpore.metainfo.integracao.ObjetoNegocioDao;
import br.com.centralit.citcorpore.metainfo.integracao.ScriptsVisaoDao;
import br.com.centralit.citcorpore.metainfo.integracao.VinculoVisaoDao;
import br.com.centralit.citcorpore.metainfo.integracao.VisaoDao;
import br.com.centralit.citcorpore.metainfo.integracao.VisaoRelacionadaDao;
import br.com.centralit.citcorpore.metainfo.script.ScriptRhinoJSExecute;
import br.com.centralit.citcorpore.metainfo.util.HashMapUtil;
import br.com.centralit.citcorpore.metainfo.util.JSONUtil;
import br.com.centralit.citcorpore.metainfo.util.MetaUtil;
import br.com.centralit.citcorpore.metainfo.util.RuntimeScript;
import br.com.centralit.citcorpore.negocio.ExecucaoSolicitacaoServiceEjb;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.RegistraLogDinamicView;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({"serial","rawtypes","unchecked","unused"})
public class DinamicViewsServiceEjb extends CrudServicePojoImpl implements DinamicViewsService {

	protected CrudDAO getDao() throws ServiceException {
		return new VisaoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	/**
	 * TRATAMENTO DE MATRIZ
	 * Este metodo trata do motor do sistema dinamico de gravacao de dados de visoes (montadas dinamicamente)
	 */
	public void saveMatriz(UsuarioDTO usuarioDto, DinamicViewsDTO dinamicViewDto, HashMap map) throws Exception {
		String jsonMatriz = dinamicViewDto.getJsonMatriz();
		HashMap<String, Object> mapMatriz = null;
		try{
			mapMatriz = JSONUtil.convertJsonToMap(jsonMatriz, true);
		}catch (Exception e) {
			System.out.println("jsonMatriz: " + jsonMatriz);
			e.printStackTrace();
			throw e;
		}	
		Collection colCamposPKPrincipal = new ArrayList();
		Collection colCamposTodosPrincipal = new ArrayList();		
		ArrayList colMatrizTratada = (ArrayList) mapMatriz.get("MATRIZ");
		if (colMatrizTratada != null){
			Integer idVisao = dinamicViewDto.getDinamicViewsIdVisao();
			setInfoSave(idVisao, colCamposPKPrincipal, colCamposTodosPrincipal);
			CamposObjetoNegocioDTO camposObjetoNegocioDTO = null;
			if (colCamposPKPrincipal != null && colCamposPKPrincipal.size() > 0){
				camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) colCamposPKPrincipal.iterator().next();
			}
			if (camposObjetoNegocioDTO == null){
				throw new Exception("Problema ao obter a chave da VISAO!");
			}
			for (Iterator it = colMatrizTratada.iterator(); it.hasNext();){
				HashMap mapItem = (HashMap)it.next();
				mapItem.put(camposObjetoNegocioDTO.getNomeDB(), mapItem.get("FLD_0"));
				System.out.println("Processando... " + mapItem);
				save(usuarioDto, dinamicViewDto, mapItem);
			}
		}
	}
	/**
	 * Este metodo trata do motor do sistema dinamico de gravacao de dados de visoes (montadas dinamicamente)
	 */
	public void save(UsuarioDTO usuarioDto, DinamicViewsDTO dinamicViewDto, HashMap map) throws Exception {
		VisaoRelacionadaDao visaoRelacionadaDao = new VisaoRelacionadaDao();
		GrupoVisaoDao grupoVisaoDao = new GrupoVisaoDao();
		GrupoVisaoCamposNegocioDao grupoVisaoCamposNegocioDao = new GrupoVisaoCamposNegocioDao();
		CamposObjetoNegocioDao camposObjetoNegocioDao = new CamposObjetoNegocioDao();
		VinculoVisaoDao vinculoVisaoDao = new VinculoVisaoDao();
		ScriptsVisaoDao scriptsVisaoDao = new ScriptsVisaoDao();
		MatrizVisaoDao matrizVisaoDao = new MatrizVisaoDao();
		VisaoDao visaoDao = new VisaoDao();
		TransactionControler tc = visaoDao.getTransactionControler();
		
		visaoRelacionadaDao.setTransactionControler(tc);
		grupoVisaoDao.setTransactionControler(tc);
		grupoVisaoCamposNegocioDao.setTransactionControler(tc);
		camposObjetoNegocioDao.setTransactionControler(tc);
		vinculoVisaoDao.setTransactionControler(tc);
		scriptsVisaoDao.setTransactionControler(tc);
		matrizVisaoDao.setTransactionControler(tc);
		
		tc.start();		
		Integer idVisao = dinamicViewDto.getDinamicViewsIdVisao();
		Collection colScripts = scriptsVisaoDao.findByIdVisao(idVisao);
		HashMap mapScritps = new HashMap();
		if (colScripts != null){
			for (Iterator it = colScripts.iterator(); it.hasNext();){
				ScriptsVisaoDTO scriptsVisaoDTO = (ScriptsVisaoDTO)it.next();
				mapScritps.put(scriptsVisaoDTO.getTypeExecute() + "#" + scriptsVisaoDTO.getScryptType().trim(), scriptsVisaoDTO.getScript());
			}
		}		
		
		Collection colCamposPKPrincipal = new ArrayList();
		Collection colCamposTodosPrincipal = new ArrayList();
		Collection colCamposTodosVinc = null;
		CamposObjetoNegocioDTO camposObjetoNegocioChaveMatriz = new CamposObjetoNegocioDTO();
		
		setInfoSave(idVisao, colCamposPKPrincipal, colCamposTodosPrincipal);
		
		Collection colVisoesRelacionadas = visaoRelacionadaDao.findByIdVisaoPaiAtivos(idVisao);
		
		try{
			if (isPKExists(colCamposPKPrincipal, map)){
		   		String strScript = (String)mapScritps.get(ScriptsVisaoDTO.SCRIPT_EXECUTE_SERVER + "#" + ScriptsVisaoDTO.SCRIPT_ONUPDATE.getName());
		   		if (strScript != null && !strScript.trim().equalsIgnoreCase("")){
		   			ScriptRhinoJSExecute scriptExecute = new ScriptRhinoJSExecute();
		   			RuntimeScript runtimeScript = new RuntimeScript();
		   			Context cx = Context.enter();
		   			Scriptable scope = cx.initStandardObjects();	
		   			scope.put("mapFields", scope, map);
		   			String action = "UPDATE";
		   			scope.put("ACTION", scope, action);
		   			scope.put("userLogged", scope, usuarioDto);
		   			scope.put("transactionControler", scope, tc);
		   			scope.put("dinamicViewDto", scope, dinamicViewDto);
		   			scope.put("RuntimeScript", scope, runtimeScript);
		   			Object retorno = scriptExecute.processScript(cx, scope, strScript, VisaoServiceEjb.class.getName() + "_" + ScriptsVisaoDTO.SCRIPT_ONUPDATE.getName());
		   		}	
		   		if (!dinamicViewDto.isAbortFuncaoPrincipal()){
		   		    updateFromMap(map, colCamposTodosPrincipal, usuarioDto, visaoDao);
        		   		strScript = (String)mapScritps.get(ScriptsVisaoDTO.SCRIPT_EXECUTE_SERVER + "#" + ScriptsVisaoDTO.SCRIPT_AFTERUPDATE.getName());
        		   		if (strScript != null && !strScript.trim().equalsIgnoreCase("")){
        		   			ScriptRhinoJSExecute scriptExecute = new ScriptRhinoJSExecute();
        		   			RuntimeScript runtimeScript = new RuntimeScript();
        		   			Context cx = Context.enter();
        		   			Scriptable scope = cx.initStandardObjects();	
        		   			scope.put("mapFields", scope, map);
        		   			String action = "UPDATE";
        		   			scope.put("ACTION", scope, action);
        		   			scope.put("userLogged", scope, usuarioDto);
        		   			scope.put("transactionControler", scope, tc);
        		   			scope.put("dinamicViewDto", scope, dinamicViewDto);
        		   			scope.put("RuntimeScript", scope, runtimeScript);
        		   			Object retorno = scriptExecute.processScript(cx, scope, strScript, VisaoServiceEjb.class.getName() + "_" + ScriptsVisaoDTO.SCRIPT_AFTERUPDATE.getName());
        		   		}				
		   		}
			}else{
		   		String strScript = (String)mapScritps.get(ScriptsVisaoDTO.SCRIPT_EXECUTE_SERVER + "#" + ScriptsVisaoDTO.SCRIPT_ONCREATE.getName());
		   		if (strScript != null && !strScript.trim().equalsIgnoreCase("")){
		   			ScriptRhinoJSExecute scriptExecute = new ScriptRhinoJSExecute();
		   			RuntimeScript runtimeScript = new RuntimeScript();
		   			Context cx = Context.enter();
		   			Scriptable scope = cx.initStandardObjects();	
		   			scope.put("mapFields", scope, map);
		   			String action = "CREATE";
		   			scope.put("ACTION", scope, action);		   			
		   			scope.put("userLogged", scope, usuarioDto);
		   			scope.put("transactionControler", scope, tc);
		   			scope.put("dinamicViewDto", scope, dinamicViewDto);
		   			scope.put("RuntimeScript", scope, runtimeScript);
		   			Object retorno = scriptExecute.processScript(cx, scope, strScript, VisaoServiceEjb.class.getName() + "_" + ScriptsVisaoDTO.SCRIPT_ONCREATE.getName());
		   		}			
		   		if (!dinamicViewDto.isAbortFuncaoPrincipal()){
		   		    createFromMap(map, colCamposTodosPrincipal, usuarioDto, visaoDao);
        		   		strScript = (String)mapScritps.get(ScriptsVisaoDTO.SCRIPT_EXECUTE_SERVER + "#" + ScriptsVisaoDTO.SCRIPT_AFTERCREATE.getName());
        		   		if (strScript != null && !strScript.trim().equalsIgnoreCase("")){
        		   			ScriptRhinoJSExecute scriptExecute = new ScriptRhinoJSExecute();
        		   			RuntimeScript runtimeScript = new RuntimeScript();
        		   			Context cx = Context.enter();
        		   			Scriptable scope = cx.initStandardObjects();	
        		   			scope.put("mapFields", scope, map);
        		   			String action = "CREATE";
        		   			scope.put("ACTION", scope, action);		   			
        		   			scope.put("userLogged", scope, usuarioDto);
        		   			scope.put("transactionControler", scope, tc);
        		   			scope.put("dinamicViewDto", scope, dinamicViewDto);
        		   			scope.put("RuntimeScript", scope, runtimeScript);
        		   			Object retorno = scriptExecute.processScript(cx, scope, strScript, VisaoServiceEjb.class.getName() + "_" + ScriptsVisaoDTO.SCRIPT_AFTERCREATE.getName());
        		   		}				
		   		}
			}
			if (colVisoesRelacionadas != null){
				for(Iterator it = colVisoesRelacionadas.iterator(); it.hasNext();){
					VisaoRelacionadaDTO visaoRelacionadaDto = (VisaoRelacionadaDTO)it.next();
					Collection colVinculos = vinculoVisaoDao.findByIdVisaoRelacionada(visaoRelacionadaDto.getIdVisaoRelacionada());//****
					
					Object objFromHash = (Object) map.get(VisaoRelacionadaDTO.PREFIXO_SISTEMA_TABELA_VINCULADA + visaoRelacionadaDto.getIdVisaoFilha());
					VisaoDTO visaoDtoAux = new VisaoDTO();
					visaoDtoAux.setIdVisao(visaoRelacionadaDto.getIdVisaoFilha());
					visaoDtoAux = (VisaoDTO) visaoDao.restore(visaoDtoAux);//*****
					MatrizVisaoDTO matrizVisaoDTO = new MatrizVisaoDTO();
					boolean ehMatriz = false;
					if (visaoDtoAux != null){
						if (visaoDtoAux.getTipoVisao().equalsIgnoreCase(VisaoDTO.MATRIZ)){
							ehMatriz = true;
							matrizVisaoDTO.setIdVisao(visaoDtoAux.getIdVisao());
							Collection colMatriz = matrizVisaoDao.findByIdVisao(visaoDtoAux.getIdVisao());
							if (colMatriz != null && colMatriz.size() > 0){
								matrizVisaoDTO = (MatrizVisaoDTO) colMatriz.iterator().next();
								camposObjetoNegocioChaveMatriz.setIdCamposObjetoNegocio(matrizVisaoDTO.getIdCamposObjetoNegocio1());
								camposObjetoNegocioChaveMatriz.setIdObjetoNegocio(matrizVisaoDTO.getIdObjetoNegocio());
								camposObjetoNegocioChaveMatriz = (CamposObjetoNegocioDTO) camposObjetoNegocioDao.restore(camposObjetoNegocioChaveMatriz);
							}
						}
					}
					
					if (HashMap.class.isInstance(objFromHash)){
						HashMap mapVinc = (HashMap)objFromHash;
						if (mapVinc != null){ //Se existir dados recebidos.
							Collection colCamposPKVinc = new ArrayList();
							colCamposTodosVinc = new ArrayList();							
							setInfoSave(visaoRelacionadaDto.getIdVisaoFilha(), colCamposPKVinc, colCamposTodosVinc);
							//Grava os dados de informacoes vinculadas.
							if (isPKExists(colCamposPKVinc, mapVinc)){
								updateFromMap(mapVinc, colCamposTodosVinc, usuarioDto, visaoDao);
							}else{
								createFromMap(mapVinc, colCamposTodosVinc, usuarioDto, visaoDao);
							}
						}
					}else if (Collection.class.isInstance(objFromHash)){
						Collection colVinc = (Collection)objFromHash;
						if (colVinc != null){
							for (Iterator it2 = colVinc.iterator(); it2.hasNext();){
								HashMap mapVinc = (HashMap)it2.next();
								if (mapVinc != null){ //Se existir dados recebidos.
									Collection colCamposPKVinc = new ArrayList();
									colCamposTodosVinc = new ArrayList();									
									setInfoSave(visaoRelacionadaDto.getIdVisaoFilha(), colCamposPKVinc, colCamposTodosVinc);//*****
									String tipoVinc = "";
									if (colVinculos != null && colVinculos.size() > 0){
										VinculoVisaoDTO vinculoVisaoDTO = (VinculoVisaoDTO) ((List)(colVinculos)).get(0);
										tipoVinc = vinculoVisaoDTO.getTipoVinculo();
									}
									if (ehMatriz){
										if (camposObjetoNegocioChaveMatriz != null){
											mapVinc.put(camposObjetoNegocioChaveMatriz.getNomeDB(), mapVinc.get("FLD_0"));
										}
										CamposObjetoNegocioDTO camposObjetoNegocioDTO = null;
										if (colCamposPKVinc != null && colCamposPKVinc.size() > 0){
											for (Iterator itVinc = colCamposPKVinc.iterator(); itVinc.hasNext();){
												camposObjetoNegocioDTO = (CamposObjetoNegocioDTO)itVinc.next();
												if (!camposObjetoNegocioDTO.getNomeDB().trim().equalsIgnoreCase(camposObjetoNegocioChaveMatriz.getNomeDB().trim())){
													mapVinc.put(camposObjetoNegocioDTO.getNomeDB(), map.get(camposObjetoNegocioDTO.getNomeDB()));
												}
											}
										}		
										if (tipoVinc == null || tipoVinc.equalsIgnoreCase("")){
											tipoVinc = VinculoVisaoDTO.VINCULO_1_TO_N;
										}
									}
									if (tipoVinc.equalsIgnoreCase(VinculoVisaoDTO.VINCULO_N_TO_N)){
										//Grava os dados de informacoes vinculadas.
										if (isPKExists(colCamposPKVinc, mapVinc)){
											updateFromMap(mapVinc, colCamposTodosVinc, usuarioDto, visaoDao);
										}else{
											createFromMap(mapVinc, colCamposTodosVinc, usuarioDto, visaoDao);
										}									
										processCreateVinc(visaoRelacionadaDto, colVinculos, map, mapVinc, usuarioDto);
									}
									if (tipoVinc.equalsIgnoreCase(VinculoVisaoDTO.VINCULO_1_TO_N)){
										mapVinc = createUniqueMap(map, mapVinc);//******
										//Grava os dados de informacoes vinculadas.
										if (isPKExists(colCamposPKVinc, mapVinc)){
											updateFromMap(mapVinc, colCamposTodosVinc, usuarioDto, visaoDao);
										}else{
											createFromMap(mapVinc, colCamposTodosVinc, usuarioDto, visaoDao);
										}										
									}
								}						
							}
						}
					}
				}
			}		
			
			if (dinamicViewDto.getIdFluxo() != null || dinamicViewDto.getIdTarefa() != null) 
				new ExecucaoSolicitacaoServiceEjb().executa(usuarioDto, tc, dinamicViewDto.getIdFluxo(), dinamicViewDto.getIdTarefa(), dinamicViewDto.getAcaoFluxo(), map, colCamposTodosPrincipal, colCamposTodosVinc);
			
			tc.commit();
			tc.close();

		}catch (Exception e) {
		    e.printStackTrace();
			this.rollbackTransaction(tc, e);
		}finally {
			tc.close();
			tc = null;
		}
	}
	private HashMap createUniqueMap(HashMap mapSrc, HashMap mapDest){
		Set set = mapSrc.entrySet();
		Iterator i = set.iterator();
		while(i.hasNext()) { 
			Map.Entry me = (Map.Entry)i.next();
			String str1 = (String) me.getKey();
			if (!str1.equalsIgnoreCase("REMOVED")){ //Mantem a opcao REMOVED para a visao original.
				Object r = mapDest.get(me.getKey());
				mapDest.put(me.getKey(), me.getValue());
			}
		}
		return mapDest;
	}
	public void processCreateVinc(VisaoRelacionadaDTO visaoRelacionadaDto, Collection colVinculos, HashMap mapPai, HashMap mapVinc, UsuarioDTO usuarioDto) throws Exception{
		CamposObjetoNegocioDao camposObjetoNegocioDao = new CamposObjetoNegocioDao();
		VisaoDao visaoDao = new VisaoDao();
		if (colVinculos != null){
			Collection colCamposVinc = new ArrayList();
			HashMap mapNew = new HashMap();
			for(Iterator itVinculos = colVinculos.iterator(); itVinculos.hasNext();){
				VinculoVisaoDTO vinculoVisaoDTO = (VinculoVisaoDTO)itVinculos.next();
				if (vinculoVisaoDTO.getIdCamposObjetoNegocioPaiNN() != null){
					CamposObjetoNegocioDTO camposObjetoNegocioDTOPai = new CamposObjetoNegocioDTO();
					camposObjetoNegocioDTOPai.setIdCamposObjetoNegocio(vinculoVisaoDTO.getIdCamposObjetoNegocioPaiNN());
					camposObjetoNegocioDTOPai = (CamposObjetoNegocioDTO) camposObjetoNegocioDao.restore(camposObjetoNegocioDTOPai);
					String valuePai = (String)mapPai.get(camposObjetoNegocioDTOPai.getNomeDB().trim().toUpperCase());
					mapNew.put(camposObjetoNegocioDTOPai.getNomeDB().trim().toUpperCase(), valuePai);
					camposObjetoNegocioDTOPai.setSequence("N");
					colCamposVinc.add(camposObjetoNegocioDTOPai);
				}					
				
				if (vinculoVisaoDTO.getIdCamposObjetoNegocioFilhoNN() != null){
					CamposObjetoNegocioDTO camposObjetoNegocioDTOFilho = new CamposObjetoNegocioDTO();
					camposObjetoNegocioDTOFilho.setIdCamposObjetoNegocio(vinculoVisaoDTO.getIdCamposObjetoNegocioFilhoNN());
					camposObjetoNegocioDTOFilho = (CamposObjetoNegocioDTO) camposObjetoNegocioDao.restore(camposObjetoNegocioDTOFilho);		
					
					String valueFilho = (String)mapVinc.get(camposObjetoNegocioDTOFilho.getNomeDB().trim().toUpperCase());
					mapNew.put(camposObjetoNegocioDTOFilho.getNomeDB().trim().toUpperCase(), valueFilho);
					camposObjetoNegocioDTOFilho.setSequence("N");
					colCamposVinc.add(camposObjetoNegocioDTOFilho);
				}					
			}
			if (colCamposVinc != null && colCamposVinc.size() > 0){
				if (!isPKExists(colCamposVinc, mapNew)){
					createFromMap(mapNew, colCamposVinc, usuarioDto, visaoDao);
				}
			}
		}
	}
	/**
	 * Seta informacoes importantes para a gravacao dados.
	 * As informacoes serao preenchidas nas collections: colCamposPK, colCamposTodos
	 * @param colGrupos
	 * @param colCamposPK
	 * @param colCamposTodos
	 * @throws Exception 
	 */
	public void setInfoSave(Integer idVisao, Collection colCamposPK, Collection colCamposTodos) throws Exception{
		GrupoVisaoCamposNegocioDao grupoVisaoCamposNegocioDao = new GrupoVisaoCamposNegocioDao();
		CamposObjetoNegocioDao camposObjetoNegocioDao = new CamposObjetoNegocioDao();
		GrupoVisaoDao grupoVisaoDao = new GrupoVisaoDao();
		
		Collection colGrupos = grupoVisaoDao.findByIdVisaoAtivos(idVisao);
		if (colGrupos != null){
			for(Iterator it = colGrupos.iterator(); it.hasNext();){
				GrupoVisaoDTO grupoVisaoDTO = (GrupoVisaoDTO)it.next();
				grupoVisaoDTO.setColCamposVisao(grupoVisaoCamposNegocioDao.findByIdGrupoVisaoAtivos(grupoVisaoDTO.getIdGrupoVisao()));
				
				if (grupoVisaoDTO.getColCamposVisao() != null){
					for(Iterator it2 = grupoVisaoDTO.getColCamposVisao().iterator(); it2.hasNext();){
						GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO)it2.next();
						
						CamposObjetoNegocioDTO camposObjetoNegocioDTO = new CamposObjetoNegocioDTO();
						camposObjetoNegocioDTO.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioDTO.getIdCamposObjetoNegocio());
						camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) camposObjetoNegocioDao.restore(camposObjetoNegocioDTO);
						
						if (camposObjetoNegocioDTO != null){
							camposObjetoNegocioDTO.setFormula(grupoVisaoCamposNegocioDTO.getFormula());
							camposObjetoNegocioDTO.setTipoNegocio(grupoVisaoCamposNegocioDTO.getTipoNegocio());
							if (camposObjetoNegocioDTO.getPk().equalsIgnoreCase("S")){
								colCamposPK.add(camposObjetoNegocioDTO);
							}
							colCamposTodos.add(camposObjetoNegocioDTO);
						}
					}
				}
			}
		}		
	}
	
	public HashMap createFromMap(HashMap map, Collection colCampos, UsuarioDTO usuarioDto, VisaoDao visaoDao) throws Exception{
		//VisaoDao visaoDao = new VisaoDao();
		List lstParms = new ArrayList();
		String strValues = "";
		String strFields = "";
		
		if(colCampos == null){
			System.err.println("DinamicViewsServiceEjb - colCampos é null ");
		}
		String strTable = generateFrom(colCampos);
		String sql = "INSERT INTO " + strTable + " ";
		if(colCampos != null){
			for(Iterator it = colCampos.iterator(); it.hasNext();){
				CamposObjetoNegocioDTO camposObjetoNegocioDTO = (CamposObjetoNegocioDTO)it.next();
				
				String strVal = "";
				if (camposObjetoNegocioDTO != null && camposObjetoNegocioDTO.getFormula() != null && !camposObjetoNegocioDTO.getFormula().trim().equalsIgnoreCase("") && !camposObjetoNegocioDTO.getTipoNegocio().trim().equalsIgnoreCase(MetaUtil.CLASS_AND_METHOD)){
					strVal = executeFormula(camposObjetoNegocioDTO.getFormula(), map, camposObjetoNegocioDTO);
				}else{
					if(camposObjetoNegocioDTO != null){
						strVal = (String) map.get(camposObjetoNegocioDTO.getNomeDB().trim().toUpperCase());
					}
				}
				
				if (camposObjetoNegocioDTO != null && camposObjetoNegocioDTO.getSequence().equalsIgnoreCase("S")){
					int val = getNextKey(strTable, camposObjetoNegocioDTO.getNomeDB().trim());
					if (!strValues.equalsIgnoreCase("")){
						strValues += ",";
					}
					strValues += "?";
					
					if (!strFields.equalsIgnoreCase("")){
						strFields += ",";
					}
					strFields += "" + camposObjetoNegocioDTO.getNomeDB().trim();
					
					map.put(camposObjetoNegocioDTO.getNomeDB().trim().toUpperCase(), "" + val);
					
					lstParms.add(val);				
				}else{
					//Se o campo for obrigatório e o valor for null ou vazio, não pode continuar.
					if (camposObjetoNegocioDTO != null && camposObjetoNegocioDTO.getObrigatorio() != null && camposObjetoNegocioDTO.getObrigatorio().equalsIgnoreCase("S")) {
						if (strVal == null || strVal.trim().equals("")) {
							return map;
						}
					} else {
						/* Desenvolvedor: euler.ramos e thiago.oliveira Data: 08/11/2013 Horário: 16h00min ID Citsmart: 123627 Motivo/Comentário: As telas dinamic view não estavam listando os registros que estavam com o campo deleted igual a null */
						if (camposObjetoNegocioDTO != null && camposObjetoNegocioDTO.getNome().equalsIgnoreCase("deleted")){
							if ((strVal==null)||(strVal.equals(""))){
								strVal="n";
							}
						}
						//Se o campo não for obrigatório mas o valor for null, recebe vazio para garantir que não de erro de sql (para campos tipo not null)
						if (strVal == null) {
							strVal = UtilStrings.nullToVazio(strVal);
						}
					}
					
					if (!strValues.equalsIgnoreCase("")){
						strValues += ",";
					}
					strValues += "?";
					
					if (!strFields.equalsIgnoreCase("")){
						strFields += ",";
					}
					if(camposObjetoNegocioDTO != null){
						strFields += "" + camposObjetoNegocioDTO.getNomeDB().trim();
						lstParms.add(MetaUtil.convertType(camposObjetoNegocioDTO.getTipoDB().trim(), strVal, camposObjetoNegocioDTO.getPrecisionDB()));
					}
				}
			}
		}
		sql += "(" + strFields + ") VALUES (" + strValues + ")";
		//System.err.println(sql);
		visaoDao.execUpdate(sql, lstParms.toArray());
		
		if (UtilStrings.nullToVazio(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.USE_LOG, "false")).equalsIgnoreCase("true")){
			new Thread(new RegistraLogDinamicView(lstParms.toArray(), "I", sql, usuarioDto, strTable)).start();
		}
		
		return map;
	}
	
	public void updateFromMap(HashMap map, Collection colCampos, UsuarioDTO usuarioDto, VisaoDao visaoDao) throws Exception{
		List lstParms = new ArrayList();
		List lstWhere = new ArrayList();
		String strFields = "";
		String strWhere = "";
		String strTable = generateFrom(colCampos);
		String sql = "UPDATE " + strTable + " ";
		for(Iterator it = colCampos.iterator(); it.hasNext();){
			CamposObjetoNegocioDTO camposObjetoNegocioDTO = (CamposObjetoNegocioDTO)it.next();
			
			String strVal = "";
			if (camposObjetoNegocioDTO.getFormula() != null && !camposObjetoNegocioDTO.getFormula().trim().equalsIgnoreCase("") && !camposObjetoNegocioDTO.getTipoNegocio().trim().equalsIgnoreCase(MetaUtil.CLASS_AND_METHOD)){
				strVal = executeFormula(camposObjetoNegocioDTO.getFormula(), map, camposObjetoNegocioDTO);
			}else{
				strVal = (String) map.get(camposObjetoNegocioDTO.getNomeDB().trim().toUpperCase());
			}			
			if (!camposObjetoNegocioDTO.getPk().equalsIgnoreCase("S")){
				if (strVal != null){
					if (!strFields.equalsIgnoreCase("")){
						strFields += ",";
					}
					strFields += "" + camposObjetoNegocioDTO.getNomeDB().trim() + " = ?";
					lstParms.add(MetaUtil.convertType(camposObjetoNegocioDTO.getTipoDB().trim(), strVal, camposObjetoNegocioDTO.getPrecisionDB()));
				}
			}else{
				if (strVal != null){		
					if (!strWhere.equalsIgnoreCase("")){
						strWhere += " AND ";
					}					
					strWhere += "" + camposObjetoNegocioDTO.getNomeDB() + " = ?";
					lstWhere.add(MetaUtil.convertType(camposObjetoNegocioDTO.getTipoDB().trim(), strVal, camposObjetoNegocioDTO.getPrecisionDB()));
				}
			}
		}
		sql += " SET " + strFields + " WHERE " + strWhere;
		
		lstParms.addAll(lstWhere);
		
		if (strFields != null && !strFields.trim().equalsIgnoreCase("")){
			visaoDao.execUpdate(sql, lstParms.toArray());
			if (UtilStrings.nullToVazio(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.USE_LOG, "false")).equalsIgnoreCase("true")){
				new Thread(new RegistraLogDinamicView(lstParms.toArray(), "U", sql, usuarioDto, strTable)).start();
			}
		}
		
		if (!map.containsKey("REMOVED")){
			map.put("REMOVED", "false");
		}
		String removed = (String)map.get("REMOVED");
		if (removed == null){
			removed = "false";
		}
		if (removed.equalsIgnoreCase("X")){ //A nova arquitetura coloca esta informacao.
			removed = "true";
		}
		String sqlUltAtualizAndLogicDelete = "";
		if (removed.equalsIgnoreCase("true")){
			sqlUltAtualizAndLogicDelete = "UPDATE " + strTable + " SET DELETED = 'Y' WHERE " + strWhere;
			try{
				visaoDao.execUpdate(sqlUltAtualizAndLogicDelete, lstWhere.toArray());
				if (UtilStrings.nullToVazio(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.USE_LOG, "false")).equalsIgnoreCase("true")){
					new Thread(new RegistraLogDinamicView(lstWhere.toArray(), "D", sqlUltAtualizAndLogicDelete, usuarioDto, strTable)).start();
				}
			}catch (Exception e) {
				System.out.println(sqlUltAtualizAndLogicDelete);
				throw new LogicException("Não foi possí­vel realizar a exclusão do registro! Verifique se possui o Campo 'DELETED' do tipo CHAR(1) no Banco de dados!");
			}			
		//}else{
			//ISTO NAO EH NECESSARIO. RETIRADO POR EMAURI. 23/10/2012
			//sqlUltAtualizAndLogicDelete = "UPDATE " + strTable + " SET DELETED = 'N' WHERE " + strWhere;
			//sqlUltAtualizAndLogicDelete = "UPDATE " + strTable + " WHERE " + strWhere;
		}
	}	
	
	@SuppressWarnings("rawtypes")
	public Collection restoreVisao(Integer idVisao, Collection colFilter) throws Exception {
		GrupoVisaoDao grupoVisaoDao = new GrupoVisaoDao();
		GrupoVisaoCamposNegocioDao grupoVisaoCamposNegocioDao = new GrupoVisaoCamposNegocioDao();
		CamposObjetoNegocioDao camposObjetoNegocioDao = new CamposObjetoNegocioDao();
		Collection colGrupos = grupoVisaoDao.findByIdVisaoAtivos(idVisao);
		
		LookupServiceEjb lookupService = new LookupServiceEjb();
		
		Collection colCamposTodos = new ArrayList();
		if (colGrupos != null){
			for(Iterator it = colGrupos.iterator(); it.hasNext();){
				GrupoVisaoDTO grupoVisaoDTO = (GrupoVisaoDTO)it.next();
				grupoVisaoDTO.setColCamposVisao(grupoVisaoCamposNegocioDao.findByIdGrupoVisaoAtivos(grupoVisaoDTO.getIdGrupoVisao()));
				
				if (grupoVisaoDTO.getColCamposVisao() != null){
					for(Iterator it2 = grupoVisaoDTO.getColCamposVisao().iterator(); it2.hasNext();){
						GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO)it2.next();
						
						CamposObjetoNegocioDTO camposObjetoNegocioDTO = new CamposObjetoNegocioDTO();
						camposObjetoNegocioDTO.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioDTO.getIdCamposObjetoNegocio());
						camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) camposObjetoNegocioDao.restore(camposObjetoNegocioDTO);
						
						if (camposObjetoNegocioDTO != null){
							grupoVisaoCamposNegocioDTO.setCamposObjetoNegocioDto(camposObjetoNegocioDTO);
							camposObjetoNegocioDTO.setFormula(grupoVisaoCamposNegocioDTO.getFormula());
							colCamposTodos.add(grupoVisaoCamposNegocioDTO);
						}
					}
				}
			}
		}
		
		String sql = generateSQLRestore(colCamposTodos, colFilter);
		
		VisaoDao visaoDao = new VisaoDao();
		Collection colRetorno = visaoDao.execSQL(sql, null);	
		if (colRetorno != null){
			for(Iterator it = colRetorno.iterator(); it.hasNext();){
				String lineLabel = "";
				String lineValue = "";
				Object[] objs = (Object[])it.next();
				int i = 0;
				for (Iterator it2 = colCamposTodos.iterator(); it2.hasNext();){
					GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO)it2.next();
					CamposObjetoNegocioDTO camposObjetoNegocioDTO = grupoVisaoCamposNegocioDTO.getCamposObjetoNegocioDto();					
					camposObjetoNegocioDTO.setValue(objs[i]);
					
					if (grupoVisaoCamposNegocioDTO.getTipoNegocio().equalsIgnoreCase(MetaUtil.RELATION)){
						if (grupoVisaoCamposNegocioDTO.getTipoLigacao() == null){
							grupoVisaoCamposNegocioDTO.setTipoLigacao(GrupoVisaoCamposNegocioDTO.RELATION_SIMPLE);
						}
						if (grupoVisaoCamposNegocioDTO.getTipoLigacao().equalsIgnoreCase(GrupoVisaoCamposNegocioDTO.RELATION_SIMPLE)){
							LookupDTO lookupDto = new LookupDTO();
							lookupDto.setTermoPesquisa("");
							if (objs[i] != null){
								lookupDto.setTermoPesquisa(objs[i].toString());
								lookupDto.setIdGrupoVisao(grupoVisaoCamposNegocioDTO.getIdGrupoVisao());
								lookupDto.setIdCamposObjetoNegocio(camposObjetoNegocioDTO.getIdCamposObjetoNegocio());
								
								ReturnLookupDTO returnLookupAux = lookupService.restoreSimple(lookupDto);
								camposObjetoNegocioDTO.setReturnLookupDTO(returnLookupAux);
							}else{
							    camposObjetoNegocioDTO.setReturnLookupDTO(null);
							}
						}
					}
					
					i++;
				}
				break; //Como é restore, pega só o 1.o
			}
		}
		return colCamposTodos;
	}
	
	private String generateSQLRestore(Collection colPresentation, Collection colFilter) throws Exception{
		String sql = "SELECT ";
		
		sql += generateFields(colPresentation);
		sql += " FROM ";
		sql += generateFromWithRelatios(colPresentation, colFilter);
		
		String strFilter = generateFilter(colFilter);
		if (!strFilter.equalsIgnoreCase("")){
			sql += " WHERE " + strFilter;
		}
		
		return sql;
	}
	
	private String generateFields(Collection colPresentation) throws Exception{
		ObjetoNegocioDao objetoNegocioDao = new ObjetoNegocioDao();
		String sqlFields = "";
		if (colPresentation != null){
			int i = 1;
			for(Iterator it = colPresentation.iterator(); it.hasNext();){
				GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO)it.next();
				CamposObjetoNegocioDTO camposObjetoNegocioDTO = grupoVisaoCamposNegocioDTO.getCamposObjetoNegocioDto();
				ObjetoNegocioDTO objetoNegocioDTO = new ObjetoNegocioDTO();
				
				objetoNegocioDTO.setIdObjetoNegocio(camposObjetoNegocioDTO.getIdObjetoNegocio());
				objetoNegocioDTO = (ObjetoNegocioDTO) objetoNegocioDao.restore(objetoNegocioDTO);
				
				if (objetoNegocioDTO != null){
					if (!sqlFields.equalsIgnoreCase("")){
						sqlFields += ", ";
					}
					sqlFields += objetoNegocioDTO.getNomeTabelaDB() + "." + camposObjetoNegocioDTO.getNomeDB() + " Fld_" + i;
				}
				i++;
			}
		}
		return sqlFields;
	}	
	
	private String generateFromWithRelatios(Collection colPresentation, Collection colFilter) throws Exception{
		ObjetoNegocioDao objetoNegocioDao = new ObjetoNegocioDao();
		HashMap map = new HashMap();
		
		Collection colGeral = new ArrayList();
		if (colPresentation != null){
			colGeral.addAll(colPresentation);
		}
		if (colFilter != null){
			colGeral.addAll(colFilter);
		}
		
		if (colGeral != null){
			for(Iterator it = colGeral.iterator(); it.hasNext();){
				GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO)it.next();
				CamposObjetoNegocioDTO camposObjetoNegocioDTO = grupoVisaoCamposNegocioDTO.getCamposObjetoNegocioDto();				
				ObjetoNegocioDTO objetoNegocioDTO = new ObjetoNegocioDTO();
				
				objetoNegocioDTO.setIdObjetoNegocio(camposObjetoNegocioDTO.getIdObjetoNegocio());
				objetoNegocioDTO = (ObjetoNegocioDTO) objetoNegocioDao.restore(objetoNegocioDTO);
				
				if (objetoNegocioDTO != null){
					if (!map.containsKey(objetoNegocioDTO.getNomeTabelaDB())){
						map.put(objetoNegocioDTO.getNomeTabelaDB(), objetoNegocioDTO.getNomeTabelaDB());
					}
				}
			}
		}
		
		Set set = map.entrySet(); 
		Iterator i = set.iterator(); 
		
		String fromSql = "";
		while(i.hasNext()) { 
			Map.Entry me = (Map.Entry)i.next(); 
			if (!fromSql.equalsIgnoreCase("")){
				fromSql += ",";
			}
			fromSql += me.getKey();
		}
		
		return fromSql;
	}
	
	private String generateFilter(Collection colFilter) throws Exception{
		ObjetoNegocioDao objetoNegocioDao = new ObjetoNegocioDao();
		String sqlFilter = "";
		if (colFilter != null){
			for(Iterator it = colFilter.iterator(); it.hasNext();){
				GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO)it.next();
				CamposObjetoNegocioDTO camposObjetoNegocioDTO = grupoVisaoCamposNegocioDTO.getCamposObjetoNegocioDto();				
				ObjetoNegocioDTO objetoNegocioDTO = new ObjetoNegocioDTO();
				
				objetoNegocioDTO.setIdObjetoNegocio(camposObjetoNegocioDTO.getIdObjetoNegocio());
				objetoNegocioDTO = (ObjetoNegocioDTO) objetoNegocioDao.restore(objetoNegocioDTO);
				
				if (objetoNegocioDTO != null){
					if (!sqlFilter.equalsIgnoreCase("")){
						sqlFilter += " AND ";
					}
					String pref = "";
					String suf = "";
					String comp = "=";
					String value = "" + camposObjetoNegocioDTO.getValue();
					if (MetaUtil.isStringType(camposObjetoNegocioDTO.getTipoDB())){
						pref = "'%";
						suf = "%'";		
						comp = "LIKE";
					}else{
						if (MetaUtil.isNumericType(camposObjetoNegocioDTO.getTipoDB())){
							value = getNumber(value);
						}
					}
					sqlFilter += objetoNegocioDTO.getNomeTabelaDB() + "." + camposObjetoNegocioDTO.getNomeDB() + " " + comp + " " +
								pref + value + suf;
				}
			}
		}
		return sqlFilter;
	}	
	public String getNumber(String value){
		if (value == null){
			return null;
		}
		if (value.indexOf("[") > -1){
			String aux = value.substring(value.indexOf("["));
			return UtilStrings.apenasNumeros(aux);
		}
		value = value.replaceAll(",00", "");
		value = value.replaceAll(",000", "");
		value = value.replaceAll("\\,", ".");
		return value;
	}
	public String executeFormula(String formula, HashMap map, CamposObjetoNegocioDTO camposObjetoNegocioDTO){
		if (formula == null){
			return null;
		}
		if (camposObjetoNegocioDTO.getTipoNegocio() != null && camposObjetoNegocioDTO.getTipoNegocio().equalsIgnoreCase("CLASS")){ //SE FOR EXECUCAO DE CLASSE, CAI FORA! SERVE PARA CARREGAR COMBOS.
			return null;
		}
		org.mozilla.javascript.Context cx = org.mozilla.javascript.Context.enter();
		org.mozilla.javascript.Scriptable scope = cx.initStandardObjects();
		
		String sourceName = this.getClass().getName() + "_Formula";
		
		br.com.centralit.citcorpore.metainfo.util.HashMapUtil.map = map;
		
		String retorno = "";
		formula = formula.replaceAll("TEXTSEARCH", "utilStrings.generateNomeBusca");
		formula = formula.replaceAll("GETFIELD", "hashMapUtil.getFieldInHash");
		formula = "retorno = " + formula;
		
		String compl = "var importNames = JavaImporter();\n";

		compl += "importNames.importPackage(Packages.br.com.citframework.util);\n";
		compl += "importNames.importPackage(Packages.br.com.centralit.citcorpore.metainfo.util);\n";
		
		formula = compl + "\n" + formula;
		
		scope.put("retorno", scope, retorno);
		scope.put("utilStrings", scope, new UtilStrings());
		scope.put("hashMapUtil", scope, new HashMapUtil());
		
		Object result = cx.evaluateString(scope, formula, sourceName, 1, null);
		
		//System.out.println(cx.toString(result));
		
		return cx.toString(result);
	}
	
	public boolean isPKExists(Collection colCamposPK, HashMap hashValores) throws Exception{
		ObjetoNegocioDao objetoNegocioDao = new ObjetoNegocioDao();
		VisaoDao visaoDao = new VisaoDao();
		String sql = "SELECT ";
		String sqlFields = "";
		String sqlFilter = "";
		int i = 1;
		if (colCamposPK == null || colCamposPK.size() == 0){
			return false;
		}
		for(Iterator it = colCamposPK.iterator(); it.hasNext();){
			CamposObjetoNegocioDTO camposObjetoNegocioDTO = (CamposObjetoNegocioDTO)it.next();
			/*
			if (!sqlFields.equalsIgnoreCase("")){
				sqlFields += ", ";
			}
			*/
			ObjetoNegocioDTO objetoNegocioDTO = new ObjetoNegocioDTO();
			
			objetoNegocioDTO.setIdObjetoNegocio(camposObjetoNegocioDTO.getIdObjetoNegocio());
			objetoNegocioDTO = (ObjetoNegocioDTO) objetoNegocioDao.restore(objetoNegocioDTO);
			
			if (objetoNegocioDTO != null){
				if (!sqlFields.equalsIgnoreCase("")){
					sqlFields += ", ";
				}			
				sqlFields += objetoNegocioDTO.getNomeTabelaDB() + "." + camposObjetoNegocioDTO.getNomeDB() + " Val_" + i;	
			}
			if (!sqlFilter.equalsIgnoreCase("")){
				sqlFilter += " AND ";
			}
			String pref = "";
			String suf = "";
			String comp = "=";
			if (MetaUtil.isStringType(camposObjetoNegocioDTO.getTipoDB().trim())){
				pref = "'";
				suf = "'";		
			}
			String strVal = (String) hashValores.get(camposObjetoNegocioDTO.getNomeDB().toUpperCase());
			if (strVal != null){
				if (strVal.trim().equalsIgnoreCase("")){
					//Se nao existir valor para a PK, eh que nao existe!
					return false;
				}
				if (MetaUtil.isNumericType(camposObjetoNegocioDTO.getTipoDB().trim())){
 					int x = strVal.indexOf("["); //Vai ate este ponto, pois o codigo fica entre [x]
					if (x > -1){
						strVal = strVal.substring(x);
					}
					strVal = UtilStrings.apenasNumeros(strVal);
				}
				strVal = strVal.replaceAll(",00", "");
				strVal = strVal.replaceAll("\\,", ".");
				sqlFilter += objetoNegocioDTO.getNomeTabelaDB() + "." + camposObjetoNegocioDTO.getNomeDB() + " " + comp + " " + pref + strVal + suf;				
			}else{
				//Se nao existir valor para a PK, eh que nao existe!
				return false;
			}
			i++;
		}	
		
		sql += sqlFields + " FROM " + generateFrom(colCamposPK) + " WHERE " + sqlFilter;
		
		Collection colRet = visaoDao.execSQL(sql, null);
		if (colRet == null){
			return false;
		}
		if (colRet.size() > 0){
			return true;
		}
		return false;
	}

	public String generateFrom(Collection colGeral) throws Exception{
		ObjetoNegocioDao objetoNegocioDao = new ObjetoNegocioDao();
		HashMap map = new HashMap();
		
		if (colGeral != null){
			for(Iterator it = colGeral.iterator(); it.hasNext();){
				CamposObjetoNegocioDTO camposObjetoNegocioDTO = (CamposObjetoNegocioDTO)it.next();
				ObjetoNegocioDTO objetoNegocioDTO = new ObjetoNegocioDTO();
				
				objetoNegocioDTO.setIdObjetoNegocio(camposObjetoNegocioDTO.getIdObjetoNegocio());
				objetoNegocioDTO = (ObjetoNegocioDTO) objetoNegocioDao.restore(objetoNegocioDTO);
				
				if (objetoNegocioDTO != null){
					if (!map.containsKey(objetoNegocioDTO.getNomeTabelaDB())){
						map.put(objetoNegocioDTO.getNomeTabelaDB(), objetoNegocioDTO.getNomeTabelaDB());
					}
				}
			}
		}
		
		Set set = map.entrySet(); 
		Iterator i = set.iterator(); 
		
		String fromSql = "";
		while(i.hasNext()) { 
			Map.Entry me = (Map.Entry)i.next(); 
			if (!fromSql.equalsIgnoreCase("")){
				fromSql += ",";
			}
			fromSql += me.getKey();
		}
		
		return fromSql;
	}
	
	private int getNextKey(String nomeTabela, String nomeCampo) throws Exception {
		VisaoDao visaoDao = new VisaoDao();
		String sql = Constantes.getValue("FUNC_NEXT_KEY");
		sql=sql.replaceAll("<FIELD>", nomeCampo).replaceAll("<TABLE>", nomeTabela);
		try {
			List lista = visaoDao.execSQL(sql, null);
			if (lista == null || lista.size() == 0)
				return 1;
			if (((Object[]) lista.get(0))[0] == null) {
				return 1;
			}
			Integer key = new Integer(((Object[]) lista.get(0))[0].toString());
			return key.intValue() + 1;
		} catch (Exception e) {
			
			throw new Exception("Erro ao recuperar Next Key : sql - " + sql, e);
		}

	}	
	
	public String internacionalizaScript(String script, Locale locale) throws Exception {
		Pattern pattern = Pattern.compile("\\$\\b[a-zA-Z_0-9.]+\\b");
		Matcher matcher = pattern.matcher(script);
		while (matcher.find()) {
			String chave = matcher.group();
			String chaveInternacionalizada = UtilI18N.internacionaliza(locale.getLanguage(), chave.substring(1));
			if (chaveInternacionalizada != null && !chaveInternacionalizada.isEmpty()) {
				script = script.replaceAll("\\" + chave, chaveInternacionalizada);
			}
		}
		return script;
	}
}
