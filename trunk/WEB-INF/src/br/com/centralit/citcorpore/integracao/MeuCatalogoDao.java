package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.MeuCatalogoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
@SuppressWarnings({ "unchecked", "rawtypes" })
public class MeuCatalogoDao extends CrudDaoDefaultImpl  {

	public MeuCatalogoDao() 
	{
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Override
	public Collection getFields() 
	{
		Collection listFields = new ArrayList();
		
		listFields.add(new Field("idServico", "idServico", true, false, false, false));
		listFields.add(new Field("idUsuario", "idUsuario", true, false, false, false));
	
		return listFields;
	}

	@Override
	public String getTableName() 
	{
		return "MEUCATALOGO";
	}
	public Collection list() throws Exception 
	{
		List list = new ArrayList();
		list.add(new Order("idservico"));
		return super.list(list);
	}
	
	public Collection findByCondition(Integer id) throws Exception 
	{
		List list1 = new ArrayList();
		List list2 = new ArrayList();
		list1.add(new Condition("idUsuario", "=", id));
		list2.add(new Order("idServico"));
		return super.findByCondition(list1, list2);
	}
	
	public Collection findByIdServicoAndIdUsuario(Integer idServico, Integer idUsuario) throws Exception 
	{
		List list1 = new ArrayList();
		List list2 = new ArrayList();
		list1.add(new Condition("idUsuario", "=", idUsuario));
		list1.add(new Condition("idServico", "=", idServico));
		list2.add(new Order("idServico"));
		return super.findByCondition(list1, list2);
	}
	    

	@Override
	public void delete(IDto obj) throws Exception {
    	MeuCatalogoDTO dto = (MeuCatalogoDTO) obj;
    	List param = new ArrayList();
    	param.add(dto.getIdUsuario());
    	param.add(dto.getIdServico());    	
		String str = "DELETE FROM " +getTableName()+ " WHERE idUsuario = ? AND idServico = ?";
		super.execUpdate(str, param.toArray());
	}

	public Collection find(IDto obj) throws Exception 
    {
		List ordem = new ArrayList();
		ordem.add(new Order("idServico"));
		return super.find(obj, ordem);
    }

	@Override
	public Class getBean() {
		return MeuCatalogoDTO.class;
	}

}
