package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.EnderecoCurriculoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class EnderecoCurriculoDao extends CrudDaoDefaultImpl {
	
	public EnderecoCurriculoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Collection find(IDto obj) throws Exception {
		return null;
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();
		
		listFields.add(new Field("idEndereco" ,"idEndereco", true, true, false, true));
		listFields.add(new Field("logradouro" ,"logradouro", false, false, false, false));
		listFields.add(new Field("cep" ,"cep", false, false, false, false));
		listFields.add(new Field("complemento" ,"complemento", false, false, false, false));
		listFields.add(new Field("idTipoEndereco" ,"idTipoEndereco", false, false, false, false));
		listFields.add(new Field("correspondencia" ,"correspondencia", false, false, false, false));
		listFields.add(new Field("nomeCidade" ,"nomeCidade", false, false, false, false));
		listFields.add(new Field("nomeBairro" ,"nomeBairro", false, false, false, false));
		listFields.add(new Field("idUF" ,"enderecoIdUF", false, false, false, false));
		listFields.add(new Field("idCurriculo" ,"idCurriculo", false, false, false, false));
		
		return listFields;
	}

	public String getTableName() {
		return "RH_EnderecoCurriculo";
	}

	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return EnderecoCurriculoDTO.class;
	}
	private List getColunasRestoreAll() {
	    
        List listRetorno = new ArrayList();

        listRetorno.add("idEndereco");
        listRetorno.add("logradouro");
        listRetorno.add("cep");
        listRetorno.add("complemento");
        listRetorno.add("idTipoEndereco");
        listRetorno.add("correspondencia");
        listRetorno.add("nomeCidade");
        listRetorno.add("nomeBairro");
        listRetorno.add("enderecoIdUF");
        listRetorno.add("idCurriculo");
        listRetorno.add("siglaUf");        
        return listRetorno;
    }	
	public Collection findByIdCurriculo(Integer parm) throws Exception {
		StringBuffer sql = new StringBuffer();
        sql.append("SELECT end.idEndereco,end.logradouro,end.cep,end.complemento,end.idTipoEndereco," );
        sql.append("       end.correspondencia,end.nomeCidade,end.nomeBairro,end.idUf,end.idCurriculo,uf.siglaUf");
        sql.append("  FROM rh_enderecoCurriculo end ");
        sql.append("        LEFT JOIN ufs uf ON uf.idUf = end.idUf");
        sql.append("  WHERE end.idCurriculo = ? ");
        sql.append(" ORDER BY end.idEndereco");

        List parametro = new ArrayList();
        
        parametro.add(parm);

        
        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());
        
        return this.engine.listConvertion(getBean(), lista, getColunasRestoreAll());
	}
	public void deleteByIdCurriculo(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idCurriculo", "=", parm));
		super.deleteByCondition(condicao);
	}
}
