package br.com.centralit.citcorpore.batch;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.CategoriaGaleriaImagemDTO;
import br.com.centralit.citcorpore.bean.GaleriaImagensDTO;
import br.com.centralit.citcorpore.negocio.CategoriaGaleriaImagemService;
import br.com.centralit.citcorpore.negocio.GaleriaImagensService;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;

public class ThreadIniciaGaleriaImagens extends Thread {
	public void run() {
		CategoriaGaleriaImagemService categoriaGaleriaImagemService = null;;
		try {
			categoriaGaleriaImagemService = (CategoriaGaleriaImagemService) ServiceLocator.getInstance().getService(CategoriaGaleriaImagemService.class, null);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Collection colG = null;
		try {
			if(categoriaGaleriaImagemService != null){
				colG = categoriaGaleriaImagemService.list();
			}
		} catch (LogicException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		GaleriaImagensService galeriaImagensService = null;
		try {
			galeriaImagensService = (GaleriaImagensService) ServiceLocator.getInstance().getService(GaleriaImagensService.class, null);
		} catch (ServiceException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		ControleGEDService controleGEDService = null;
		try {
			controleGEDService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
		} catch (ServiceException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		    String PRONTUARIO_GED_DIRETORIO = null;
		    try {
			PRONTUARIO_GED_DIRETORIO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedDiretorio,"");
		    } catch (Exception e2) {
			e2.printStackTrace();
		    }
		    if (PRONTUARIO_GED_DIRETORIO == null || PRONTUARIO_GED_DIRETORIO.trim().equalsIgnoreCase("")) {
			PRONTUARIO_GED_DIRETORIO = "";
		    }

		    if (PRONTUARIO_GED_DIRETORIO.equalsIgnoreCase("")) {
			PRONTUARIO_GED_DIRETORIO = Constantes.getValue("DIRETORIO_GED");
		    }

		    if (PRONTUARIO_GED_DIRETORIO == null || PRONTUARIO_GED_DIRETORIO.equalsIgnoreCase("")) {
			PRONTUARIO_GED_DIRETORIO = "/ged";
		    }		
		
		Integer idEmpresa = 1;
		if (colG != null && colG.size() >0){
			for(Iterator it1 = colG.iterator(); it1.hasNext();){
				CategoriaGaleriaImagemDTO categDto = (CategoriaGaleriaImagemDTO)it1.next();
				Collection col = null;
				try {
					col = galeriaImagensService.findByCategoria(categDto.getIdCategoriaGaleriaImagem());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				int iContador = 0;
				if (col != null){
					for(Iterator it = col.iterator(); it.hasNext();){
						GaleriaImagensDTO galeriaImagensAux = (GaleriaImagensDTO)it.next();
						
						ControleGEDDTO controleGEDDTO = new ControleGEDDTO();
						controleGEDDTO.setIdControleGED(new Integer(galeriaImagensAux.getNomeImagem()));
						try {
							controleGEDDTO = (ControleGEDDTO) controleGEDService.restore(controleGEDDTO);
						} catch (LogicException e1) {
							e1.printStackTrace();
						} catch (ServiceException e1) {
							e1.printStackTrace();
						}
						if (controleGEDDTO != null){
					        String urlSistema = "";
                            try {
                                urlSistema = ParametroUtil.getValor(ParametroSistema.URL_Sistema, null, "");
                            } catch (Exception e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
					        
							File fileDir2 = new File(urlSistema + "/galeriaImagens");
							if (!fileDir2.exists()){
								fileDir2.mkdirs();
							}
							fileDir2 = new File(urlSistema + "/galeriaImagens/" + idEmpresa);
							if (!fileDir2.exists()){
								fileDir2.mkdirs();
							}
							fileDir2 = new File(urlSistema + "/galeriaImagens/" + idEmpresa + "/" + galeriaImagensAux.getIdCategoriaGaleriaImagem());
							if (!fileDir2.exists()){
								fileDir2.mkdirs();
							}
							
							try{
							    File arquivo = new File(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + controleGEDDTO.getPasta() + "/" + controleGEDDTO.getIdControleGED() + ".ged");
							    if (arquivo.exists()){
									CriptoUtils.decryptFile(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + controleGEDDTO.getPasta() + "/" + controleGEDDTO.getIdControleGED() + ".ged", 
								            urlSistema + "/galeriaImagens/" + idEmpresa + "/" + galeriaImagensAux.getIdCategoriaGaleriaImagem() + "/" + controleGEDDTO.getIdControleGED() + "." + galeriaImagensAux.getExtensao(), 
					                        System.getProperties().get("user.dir") + Constantes.getValue("CAMINHO_CHAVE_PRIVADA"));
							    } else {
							    	System.out.println("Arquivo : "+PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + controleGEDDTO.getPasta() + "/" + controleGEDDTO.getIdControleGED() + ".ged"+" Não Encontrado!");
							    }
								
							}catch (Exception e) {
								e.printStackTrace();
							}
							
						}
						
						iContador++;				
					}
				}
				categDto = null;
				
			}
		}
	}
}
