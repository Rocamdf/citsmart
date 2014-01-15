package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.CidadesDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CidadesDao extends CrudDaoDefaultImpl{
		private static final long serialVersionUID = 1L;

		public CidadesDao() {
			super(Constantes.getValue("DATABASE_ALIAS"), null);
		}

		public Collection find(IDto arg0) throws Exception {
			
			return null;
		}

		
		public Collection getFields() {
			Collection listFields = new ArrayList();
			listFields.add(new Field("idCidade", "idCidade", true, true, false, false));
			listFields.add(new Field("nomeCidade", "nomeCidade", false, false, false, true));
			listFields.add(new Field("idUf", "idUf", false, false, false, false));
			
			return listFields;
		}

		public String getTableName() {
			
			return "CIDADES";
		}

		public Collection listByIdUf(Integer idUf) throws Exception{
			Condition cond = new Condition("idUf", "=", idUf);
			List lstCond = new ArrayList();
			lstCond.add(cond);
			
			List list = new ArrayList();				
			return super.findByCondition(lstCond, list);
		}
		
		
		@Override
		public Collection list() throws Exception {
			List list = new ArrayList();
			list.add(new Order("nomeCidade"));
			return super.list(list);
		}
		public Class getBean() {
			
			return CidadesDTO.class;

		}
		
	    public CidadesDTO findByCodigoIBGE(String codigoIBGE) throws Exception {
	        Object[] objs = new Object[] {codigoIBGE};	        
	        List lista = this.execSQL("SELECT idCidade, nomeCidade, idUf, codigoIBGE FROM CIDADES WHERE codigoIBGE = ?", objs);
	        
	        List listRetorno = new ArrayList();
	        listRetorno.add("idCidade");
	        listRetorno.add("nomeCidade");
	        listRetorno.add("idUf");
            listRetorno.add("codigoIBGE");
	        
	        List result = this.engine.listConvertion(getBean(), lista, listRetorno);
	        if (result != null && result.size() > 0) {
	            return (CidadesDTO) result.get(0);
	        }else{
	            return null;
	        }
	    } 	

	public Collection<CidadesDTO> listByIdCidades(CidadesDTO obj)
			throws Exception {
		List list = new ArrayList();
		List fields = new ArrayList();
		String sql = "select idcidade,nomecidade from " + getTableName()+ " where iduf = ? ";
		fields.add("idCidade");
		fields.add("nomeCidade");
		list = this.execSQL(sql, new Object[] {obj.getIdUf()});
		
		Collection<CidadesDTO> result = this.engine.listConvertion(getBean(),
				list, fields);
		if (result != null && result.size() > 0) {
			return result;
        }else{
            return null;
        }
		
	}
	    
	
}