package br.com.citframework.log;

import java.sql.Timestamp;

import java.util.StringTokenizer;


import br.com.citframework.dto.LogDados;
import br.com.citframework.dto.LogTabela;
import br.com.citframework.service.LogDadosService;
import br.com.citframework.service.LogDadosServiceBean;
import br.com.citframework.service.LogTabelaService;
import br.com.citframework.service.LogTabelaServiceBean;
//import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;


public class DbLog implements Log{

	public void registraLog(String mensagem, Class classe, String tipoMensagem) throws Exception {
		try {
			if(mensagem.indexOf(Log.SEPARADOR)>0){
				StringTokenizer stok = new StringTokenizer(mensagem,SEPARADOR);
				int i = 0;
				String nomeTabela ="";
				String operacao ="";
				String dados ="";
				String idUsuario ="";
				String login ="";
				String ipOrigem ="";
				
				while(stok.hasMoreTokens()){
					String tok =  stok.nextToken();
					if(i==0){
						nomeTabela = tok;
					}else if(i==1){
						operacao = tok;
					}else if(i==2){
						dados = tok;
					}else if(i==3){
						idUsuario = tok;	
					}else if(i==4){
						login = tok;
					}else if(i==5){
						ipOrigem = tok;
					}
					i++;
				}
				
				if(nomeTabela.equalsIgnoreCase("logdados")){
					return;
				}
				LogDados ld = new LogDados();
				ld.setDados(dados);
				Timestamp dataAtual = UtilDatas.getDataHoraAtual();
				ld.setDtAtualizacao(dataAtual);
				ld.setDataLog(dataAtual);
				ld.setIdUsuario(new Integer(idUsuario));
				ld.setLocalOrigem(ipOrigem);
				ld.setOperacao(operacao);
				ld.setNomeTabela(nomeTabela);
				ld.setNomeUsuario(login);
				LogDadosService lds =  new LogDadosServiceBean();
				lds.create(ld);
				
				DbLogArquivo dbLogArquivo = new DbLogArquivo();
				String ldString = dbLogArquivo.formatarStringLogDados(ld);
				dbLogArquivo.registraLog(ldString, classe, tipoMensagem);
				
			}			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void registraLog(Exception e, Class classe, String tipoMensagem)
			throws Exception {
		
	}

}
