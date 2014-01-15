package br.com.centralit.citcorpore.batch;

import java.io.File;

import br.com.centralit.citcorpore.negocio.MenuService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoMenuService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;

public class ThreadCarregaMenuXML extends Thread {
	
	@Override
	public void run() {
		try {
			sleep(3000);
		} catch (InterruptedException e2) {
			e2.printStackTrace();
		}
		
		try {
			String mostraBotoes = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.LER_ARQUIVO_PADRAO_XML_MENUS, "S");
			if(mostraBotoes.trim().equalsIgnoreCase("S")){
				System.out.println("CITSMART - INICIANDO CARGA DO MENU PELO ARQUIVO XML PADRÃO.");
				carregaXMLMenuPadrao();
			}
		} catch (Exception e) {
			System.out.println("CITSMART - ERRO AO LER PARÂMETRO ID:" + Enumerados.ParametroSistema.LER_ARQUIVO_PADRAO_XML_MENUS + " DO SISTEMA.");
			e.printStackTrace();
		}
		
	}
	
	private synchronized void carregaXMLMenuPadrao(){
		String diretorio = "";
		try {
			MenuService menuService = null;
			try {
				menuService = (MenuService) ServiceLocator.getInstance().getService(MenuService.class, null);
			} catch (ServiceException e1) {
				e1.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			String separator = System.getProperty("file.separator");
			String diretorioReceita = CITCorporeUtil.caminho_real_app + "XMLs"  + separator;
			
			File file = new File(diretorioReceita + "menu.xml");
			
			if(menuService != null){
				menuService.gerarCarga(file);
				menuService.deletaMenusSemReferencia();
			
				menuService.gerarCarga(file);
				menuService.deletaMenusSemReferencia();
			}
			
			PerfilAcessoMenuService perfilAcessoMenuService = (PerfilAcessoMenuService) ServiceLocator.getInstance().getService(PerfilAcessoMenuService.class, null);
			perfilAcessoMenuService.atualizaPerfis();
			
			//menuService.gerarCargaNova(file);
			System.out.println("CITSMART - MENUS CARREGADOS COM SUCESSO A PARTIR DO ARQUIVO XML PADRÃO.");
			
		} catch (Exception e) {
			System.out.println("CITSMART - ERRO AO CARREGAR O ARQUIVO XML PADRÃO DE MENU DO DIRETÓRIO " + diretorio);
			e.printStackTrace();
		}
	}
	
}
