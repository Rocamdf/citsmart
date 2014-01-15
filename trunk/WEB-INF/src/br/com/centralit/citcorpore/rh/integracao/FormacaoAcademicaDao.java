package br.com.centralit.citcorpore.rh.integracao;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.FormacaoAcademicaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

//Substituir NomeDaClasseDao
public class FormacaoAcademicaDao extends CrudDaoDefaultImpl {
	
	private static final String SQL_NOME = " select idFormacaoAcademica, descricao from RH_FormacaoAcademica " +
			" where upper(descricao) like upper(?)";
	
	public FormacaoAcademicaDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection find(IDto arg0) throws Exception {
		return null;
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();
	    

		listFields.add(new Field("idFormacaoAcademica", "idFormacaoAcademica", true, true, false, false));
		listFields.add(new Field("descricao", "descricao", false, false, false, false));
		listFields.add(new Field("detalhe", "detalhe", false, false, false, false));
		
		return listFields;
	}
	
	// Substituir NomeDaTabela
	public String getTableName() {
		return "RH_FormacaoAcademica";
	}

	
	// Substituir NomeDoDTO
	public Class getBean() {
		
		return FormacaoAcademicaDTO.class;
	}


    public Collection list() throws Exception {
        List list = new ArrayList();
        list.add(new Order("descricao"));
        return super.list(list);
    }
    
    public Collection findByNome(String nome) throws Exception{
		if(nome == null)
			nome = "";
		String text = nome.trim().replaceAll(" ", "");
		text = Normalizer.normalize(text, Normalizer.Form.NFD);
		text = text.replaceAll("[^\\p{ASCII}]", "");
		text = text.replaceAll("·‡„‚ÈÍÌÛÙı˙¸Á¡¿√¬… Õ”‘’⁄‹«¥`^''-+=", "aaaaeeiooouucAAAAEEIOOOUUC ");
		nome = text;		
		nome = "%"+nome.toUpperCase()+"%";
		Object[] objs = new Object[] {nome}; 
	    List list = this.execSQL(SQL_NOME, objs);
	  
	    List listRetorno = new ArrayList();
	    listRetorno.add("idFormacaoAcademica");
		listRetorno.add("descricao");

		
		List result = this.engine.listConvertion(getBean(), list, listRetorno);
		
		return result;
	}

}
