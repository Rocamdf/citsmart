package br.com.centralit.citcorpore.rh.integracao;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.CertificacaoCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.IdiomaCurriculoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "unchecked", "serial", "rawtypes", "unused" })
public class IdiomaCurriculoDao extends CrudDaoDefaultImpl {
	public IdiomaCurriculoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Collection find(IDto obj) throws Exception {
		List lst = new ArrayList();
		lst.add("idIdioma");
		return super.find(obj, lst);
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();
		
		listFields.add(new Field("idIdioma", "idIdioma", true, false, false, false));
		listFields.add(new Field("idCurriculo" ,"idCurriculo", true, false, false, false));
		listFields.add(new Field("idNivelConversa", "idNivelConversa", false, false, false, false));
		listFields.add(new Field("idNivelEscrita", "idNivelEscrita", false, false, false, false));
		listFields.add(new Field("idNivelLeitura", "idNivelLeitura", false, false, false, false));
		
		return listFields;
	}

	public String getTableName() {
		return "RH_IdiomaCurriculo";
	}

	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return IdiomaCurriculoDTO.class;
	}
	public void deleteByIdCurriculo(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idCurriculo", "=", parm));
		super.deleteByCondition(condicao);
	}
	private List getColunasRestoreAll() {
	    
        List listRetorno = new ArrayList();

        listRetorno.add("idIdioma");
        listRetorno.add("idCurriculo");
        listRetorno.add("idNivelConversa");
        listRetorno.add("idNivelLeitura");
        listRetorno.add("idNivelEscrita"); 
        listRetorno.add("descricaoIdioma"); 
        return listRetorno;
    }	
	public Collection findByIdCurriculo(Integer parm) throws Exception {
		StringBuffer sql = new StringBuffer();
        sql.append("SELECT ic.idIdioma,ic.idCurriculo,ic.idNivelConversa,ic.idNivelLeitura,ic.idNivelEscrita,i.descricao");
        sql.append("  FROM rh_idiomacurriculo ic ");
        sql.append("        INNER JOIN rh_idioma i ON i.ididioma = ic.ididioma");
        sql.append("  WHERE ic.idCurriculo = ? ");
        sql.append(" ORDER BY i.descricao");

        List parametro = new ArrayList();
        
        parametro.add(parm);

        
        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());
        
        return this.engine.listConvertion(getBean(), lista, getColunasRestoreAll());
	}
}
