package br.com.citframework.auditoria;

import java.io.Serializable;

import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.IDaoTransact;

public interface RegistroAuditoria extends Serializable{
	public static final int TIPO_OPERACAO_INSERT = 1;
	public static final int TIPO_OPERACAO_UPDATE = 2;
	public static final int TIPO_OPERACAO_DELETE = 3;
	
	public void registraAcao(IDaoTransact dao, IDto dto, int tipoOperacao)throws Exception;

}
