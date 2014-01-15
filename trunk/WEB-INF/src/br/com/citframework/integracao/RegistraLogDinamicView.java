package br.com.citframework.integracao;

import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ParametroCorporeService;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.dto.LogDados;
import br.com.citframework.service.LogDadosService;
import br.com.citframework.service.LogDadosServiceBean;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;

public class RegistraLogDinamicView implements Runnable {

	private Object[] obj;
	private String operacao;
	private String sqlExec;
	private UsuarioDTO usuarioSessao;
	private String tableName;
	private ParametroCorporeService parametroService;

	public RegistraLogDinamicView(Object[] obj, String operacao, String sqlExec, UsuarioDTO usuarioSessao, String tableName) {
		super();
		this.obj = obj;
		this.operacao = operacao;
		this.sqlExec = sqlExec;
		this.usuarioSessao = usuarioSessao;
		this.tableName = tableName;
	}

	@Override
	public void run() {
		try {
			if (UtilStrings.nullToVazio(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.USE_LOG, "false")).equalsIgnoreCase("true")) {
				if (usuarioSessao != null) {
					String dados = "";
					if (obj != null){
						try{
							for (int i = 0; i < obj.length; i++){
								if (!dados.trim().equalsIgnoreCase("")){
									dados += ", ";
								}
								dados += "[" + obj[i].toString() + "]";
							}
						}catch(Exception e){}
					}
					LogDados logDados = new LogDados();
					logDados.setDtAtualizacao(UtilDatas.getDataHoraAtual());
					logDados.setIdUsuario(usuarioSessao.getIdUsuario());
					logDados.setDataInicio(UtilDatas.getDataAtual());
					logDados.setDataLog(UtilDatas.getDataHoraAtual());
					logDados.setNomeTabela(tableName);
					logDados.setOperacao(operacao);
					logDados.setLocalOrigem(usuarioSessao.getNomeUsuario());
					logDados.setDados("Execute ... {" + sqlExec + "} Data: {" + dados + "}");
					
					LogDadosService lds =  new LogDadosServiceBean();
					
					try{
						logDados = (LogDados) lds.create(logDados);	
					}catch(Exception e){}						
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
