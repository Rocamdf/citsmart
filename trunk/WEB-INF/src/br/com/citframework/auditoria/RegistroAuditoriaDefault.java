package br.com.citframework.auditoria;

import java.util.Collection;
import java.util.Iterator;

import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.IDaoTransact;
import br.com.citframework.log.Log;
import br.com.citframework.log.LogFactory;
import br.com.citframework.util.Reflexao;

public class RegistroAuditoriaDefault implements RegistroAuditoria{

	/**
	 * @author ney
	 * Classe padrão para geração de trilha de auditoria
	 * Registra as ações do sistema num arquivo de log
	 */
	private static final long serialVersionUID = 3785229625499684733L;

	public void registraAcao(IDaoTransact dao, IDto dto, int tipoOperacao)throws Exception {
		String acao = "";
		
		if(tipoOperacao == RegistroAuditoria.TIPO_OPERACAO_INSERT){
			acao = "inserção";
		}else if(tipoOperacao == RegistroAuditoria.TIPO_OPERACAO_DELETE){
			acao = "exclusão";
		}else if(tipoOperacao == RegistroAuditoria.TIPO_OPERACAO_UPDATE){
			acao = "atualização";
		}
		
		String msg = "O(A) usuário(a) "+dao.getUsuario().getNomeUsuario()+" efetuou uma "+acao+" na tabela "+dao.getTableName()+" com os seguintes parâmetros:\n";
		msg+=getParametros(dto, dao.getFields());
		Log log =LogFactory.getLog();
		log.registraLog(msg, dao.getClass(), Log.INFO);
		
		
	}
	
	
	private String getParametros(IDto dto, Collection fields ) throws Exception{
		String parametros = "";
		
		if(fields!=null && fields.size()>0){
			
			Iterator it = fields.iterator();
			while(it.hasNext()){
				Field field = (Field)it.next();
				Object valor = Reflexao.getPropertyValue(dto, field.getFieldClass());
				if(valor!=null){
					
					parametros+="\n		"+field.getFieldDB()+" = "+valor+";";
				}
				
				
			}
			
		}
		
		return parametros;
		
		
		
	}
	
	

}
