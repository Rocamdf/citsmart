package br.com.citframework.log;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.centralit.citcorpore.negocio.ParametroCorporeService;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTratamentoArquivos;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class CitLog implements Log{
	
	private ParametroCorporeService parametroService;
	private String pathLog;
	private String fileLog;
	private String extLog;
	
	/**
	 * @author breno.guimaraes
	 */
	public CitLog(){
		inicializarParametros();
	}
	
	private void inicializarParametros(){
		try {
			
			pathLog = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.PATH_LOG, "C:/Program Files/jboss/server/default/deploy/CitCorpore.war/tempFiles");
			fileLog = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.FILE_LOG, "/log");
			extLog = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.EXT_LOG, "txt");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void registraLog(String mensagem, Class classe, String tipoMensagem) throws Exception {
				
		Date dataAtual = UtilDatas.getDataAtual();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nomeArquivo = pathLog + fileLog + "_" + sdf.format(dataAtual) + "." + extLog;
		synchronized (nomeArquivo) {
			List lista = new ArrayList();
			sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			lista.add("["+tipoMensagem+"] - "+sdf.format(dataAtual)+" - "+classe.getName()+" - "+mensagem);
			UtilTratamentoArquivos.geraFileTXT(nomeArquivo, lista, true);
		}
	}

	public void registraLog(Exception e, Class classe, String tipoMensagem)
			throws Exception {
		Date dataAtual = UtilDatas.getDataAtual();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nomeArquivo = pathLog + fileLog + "_" + sdf.format(dataAtual) + "." + extLog;
		List lista = new ArrayList();
		sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		lista.add("["+tipoMensagem+"] - "+sdf.format(dataAtual)+" - "+classe.getName()+" - Exception:");
		synchronized (nomeArquivo) {
			FileOutputStream fos = new FileOutputStream(nomeArquivo, true);
			PrintStream out = new PrintStream(fos);
			UtilTratamentoArquivos.geraFileTXT(nomeArquivo, lista, out);		
			e.printStackTrace(out);
			try{
				fos.close();
			}catch(Exception e1){
				System.out.println("Erro ao fechar arquivo de log: "+nomeArquivo); 
				e1.printStackTrace();
			}
		}
	}
	

}
