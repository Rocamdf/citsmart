package br.com.centralit.citcorpore.batch;

import java.io.File;

public class LimpaTemporarios {
	public void executar() throws Exception{
		System.out.println("CITSAUDE -> INICIANDO PROCESSO DE LIMPEZA DE ARQUIVOS TEMPORARIOS...");
		
		String userDir = System.getProperty("user.dir");
		File dir = new File(userDir + "/tempReceitas");
		if (dir.exists() && dir.isDirectory()) {
			apagarDoDiretorio(dir.listFiles());
		}
		dir = new File(userDir + "/tempRelatorio");
		if (dir.exists() && dir.isDirectory()) {
			apagarDoDiretorio(dir.listFiles());
		}
		dir = new File(userDir + "/tempUpload");
		if (dir.exists() && dir.isDirectory()) {
			apagarDoDiretorio(dir.listFiles());
		}
		dir = new File(userDir + "/tempUploadAutoCadastro");
		if (dir.exists() && dir.isDirectory()) {
			apagarDoDiretorio(dir.listFiles());
		}
		dir = new File(userDir + "/temporario");
		if (dir.exists() && dir.isDirectory()) {
			apagarDoDiretorio(dir.listFiles());
		}
		dir = new File(userDir + "/tempInventario");
		if (dir.exists() && dir.isDirectory()) {
			apagarDoDiretorio(dir.listFiles());
		}		
		
		System.out.println("CITSAUDE -> FINALIZANDO PROCESSO DE LIMPEZA DE ARQUIVOS TEMPORARIOS...");
	}
	private void apagarDoDiretorio(File[] files){
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (file.exists() && file.isDirectory()) {
				apagarDoDiretorio(file.listFiles());
			}else{
				String name = file.getName();
				System.out.println("CITSAUDE -> Preparando para apagar arquivo temporario '" + name + "'...");
				try {
					file.delete();
					System.out.println("CITSAUDE -> arquivo temporario '" + name + "' apagado!");
				} catch (Exception e) {
					System.out.println("CITSAUDE -> Problemas ao apagar o arquivo temporario '" + name + "' !");
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		LimpaTemporarios l = new LimpaTemporarios();
		l.executar();
	}
}
