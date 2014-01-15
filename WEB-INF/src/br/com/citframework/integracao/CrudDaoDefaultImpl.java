package br.com.citframework.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.InvalidTransactionControler;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.Reflexao;

public abstract class CrudDaoDefaultImpl extends DaoTransactDefaultImpl implements CrudDAO{
	

	
    

	/**
	 * 
	 */
	private static final long serialVersionUID = 7175945542202010026L;

	public CrudDaoDefaultImpl(String aliasDB, Usuario usuario){
		super(aliasDB,usuario);
		
	}

	public CrudDaoDefaultImpl(TransactionControler tc, Usuario usuario) throws InvalidTransactionControler{
		super(tc,usuario);
	}
	
    /**
     * Metodos Publicos. 
     * Esses são os métodos padrão de um CRUD e dependendo da necessidade podem ser sobrescritos
     */
	
	public IDto create(IDto obj) throws Exception {
		return (IDto)engine.create(obj);
	}
	
	public IDto createWithID(IDto obj) throws Exception {
		return (IDto)engine.createWithID(obj);
	}

	
	
	public void delete(IDto obj) throws Exception {
		engine.delete(obj);
	}


	public IDto restore(IDto obj) throws Exception {
		return (IDto)engine.restore(obj);
	}

	public void update(IDto obj) throws Exception {
		engine.update(obj);
	}

	
    /**
     * Metodos protegidos. 
     * Esses são os métodos auxiliares previamente implementados para suporte. 
     * DEVEM SOMENTE SER USADO INTERNAMENTE NOS DAOS
     */
	

	
	protected int deleteByCondition(List condicao) throws Exception {
		return engine.deleteByCondition(condicao);
	}	


	
	protected void updateNotNull(IDto obj) throws Exception {
		engine.updateNotNull(obj);
	}
	
	protected int updateByCondition(IDto obj,List condicao) throws Exception {
		return engine.updateByCondition(obj, condicao);
	}	
	
	protected int updateNotNullByCondition(IDto obj,List condicao) throws Exception {
		return engine.updateNotNullByCondition(obj, condicao);
	}
		
	/**
	 * ## IMPORTANTE: ##
	 * O 1.o parametro eh uma lista de String com o nome
	 * dos campos na classe que devem ser ordenados. 
	 * 		Exemplo: 
	 * 			List lst = new ArrayList();
	 * 			lst.add("nomeUf");
	 * 			lst.add("siglaUf");
	 * 
	 * 			list(lst); 
	 * 
	 * @param ordenacao
	 * @return
	 * @throws Exception
	 */
	
	protected Collection list(List ordenacao) throws Exception {
		return engine.list(ordenacao);
	}

	/**
	 * Passa o campo na classe a qual o retorno deve ser ordenado.
	 * @param ordenacao
	 * @return
	 * @throws Exception
	 */

	protected Collection list(String ordenacao) throws Exception {
		List lstOrder = new ArrayList();
		lstOrder.add(new Order(ordenacao));
		
		return engine.list(lstOrder);
	}	
	


	/**
	 * Faz a busca dos dados atraves dos campos que estiverem preenchidos no bean, ou seja
	 * que nao estejam nulos. O segundo parametro permite ordenacao.
	 *  
	 * ## IMPORTANTE: ##
	 * O segundo parametro eh uma lista de String com o nome
	 * dos campos na classe que devem ser ordenados. 
	 * 		Exemplo: 
	 * 			List lst = new ArrayList();
	 * 			lst.add("nomeUf");
	 * 			lst.add("siglaUf");
	 * 
	 * 			find(obj, lst); 
	 */

	protected Collection find(IDto obj, List ordenacao) throws Exception{
		return engine.findNotNull(obj, ordenacao);
	}
	
	public Collection findByCondition(List condicao,List ordenacao) throws Exception{
		return engine.findByCondition(condicao, ordenacao);
	}		
	

	public abstract Collection find(IDto obj) throws Exception;

	public abstract Collection getFields(); 
	
	public String getNamesFieldsStr(){
		Collection col = getFields();
		String camposStr = "";
		if (col != null){
			for (Iterator it = col.iterator(); it.hasNext();){
				Field field = (Field)it.next();
				if (!camposStr.trim().equalsIgnoreCase("")){
					camposStr = camposStr + ",";
				}
				camposStr = camposStr + field.getFieldDB();
			}
		}
		return camposStr;
	}
	
	public String getNamesFieldsStr(String prefix){
		Collection col = getFields();
		String camposStr = "";
		if (col != null){
			for (Iterator it = col.iterator(); it.hasNext();){
				Field field = (Field)it.next();
				if (!camposStr.trim().equalsIgnoreCase("")){
					camposStr = camposStr + ",";
				}
				camposStr = camposStr + prefix + "." + field.getFieldDB();
			}
		}
		return camposStr;
	}	
	
	public List getListNamesFieldClass(){
		Collection col = getFields();
		List lstRetorno =  new ArrayList();
		if (col != null){
			for (Iterator it = col.iterator(); it.hasNext();){
				Field field = (Field)it.next();
				lstRetorno.add(field.getFieldClass());
			}
		}
		return lstRetorno;
	}	

	public abstract String getTableName();
	
  	public abstract Collection list() throws Exception;
  	
  	public String getOwner(){
  		String valor = Constantes.getValue("OWNER_DB_" + this.getClass().getName());
  		if (valor == null){
  			//Se nao encontrar especificao para a Classe, entao pega o Geral.
  			valor = Constantes.getValue("OWNER_DB");
  		}
  		if (valor == null){
  			return "";
  		}
  		return valor;
  	}

}
