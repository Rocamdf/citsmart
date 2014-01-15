package br.com.citframework.integracao;

import java.util.List;

import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.log.Log;
import br.com.citframework.log.LogFactory;
import br.com.citframework.util.Reflexao;

@SuppressWarnings("rawtypes")
public class RegistraLog implements Runnable {

	private Object obj;
	private String operacao;
	private PersistenceUtil persistenceUtil;
	private UsuarioDTO usuarioSessao;
	private String tableName;

	public RegistraLog(Object obj, String operacao, PersistenceUtil persistenceUtil, UsuarioDTO usuarioSessao, String tableName) {
		super();
		this.obj = obj;
		this.operacao = operacao;
		this.persistenceUtil = persistenceUtil;
		this.usuarioSessao = usuarioSessao;
		this.tableName = tableName;
	}

	@Override
	public void run() {
		try {
			if (usuarioSessao != null) {
				List lista = Reflexao.getAllProperties(obj);
				String dados = "";
				for (int i = 0; i < lista.size(); i++) {
					String campo = lista.get(i).toString();
					Object valor = Reflexao.getPropertyValue(obj, campo);
					if (valor != null) {
						try {
							campo = persistenceUtil.getCampoDB(campo);
							if (dados.trim().length() > 0)
								dados += ";";
							dados += campo + " = " + valor;
						} catch (Exception e) {

						}
					}
				}
				if (dados.trim().length() > 0) {

					String msg = tableName + Log.SEPARADOR + operacao + Log.SEPARADOR + dados + Log.SEPARADOR + usuarioSessao.getIdUsuario() + Log.SEPARADOR + usuarioSessao.getNomeUsuario() + Log.SEPARADOR
							+ usuarioSessao.getStatus();
					Log log = LogFactory.getLog();
					log.registraLog(msg, this.getClass(), Log.INFO);
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}

	}

}
