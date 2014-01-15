package br.com.citframework.auditoria;

import br.com.citframework.util.Constantes;

public class RegistroAuditoriaFactory {
	
	
	public static RegistroAuditoria getRegistroAuditoria() throws Exception{
		String valor= "CLASSE_AUDITORIA";
		
		Object obj =  Class.forName(Constantes.getValue(valor));
		
		if(obj instanceof RegistroAuditoria){
			
			return (RegistroAuditoria)obj;
			
		}else{
			throw new Exception("Classe configurada em CLASSE_AUDITORIA não corresponde a um RegistroAuditoria");
			
		}
		
	}

}
