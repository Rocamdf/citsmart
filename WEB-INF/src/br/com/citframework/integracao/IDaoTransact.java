package br.com.citframework.integracao;

import java.util.Collection;

import br.com.citframework.dto.Usuario;


public interface IDaoTransact extends IDao{
	
	
    public TransactionControler getTransactionControler() throws Exception;
    public void setTransactionControler(TransactionControler tc) throws Exception;
    public String getAliasDB();
    
    // Necessario ao engine de persistencia e auditoria
    public abstract String getTableName();
	public abstract Collection getFields();
	public Usuario getUsuario();
	


}
