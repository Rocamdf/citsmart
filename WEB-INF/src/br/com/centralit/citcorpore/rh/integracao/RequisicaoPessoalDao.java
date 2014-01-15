package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.RequisicaoPessoalDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

//Substituir NomeDaClasseDao
public class RequisicaoPessoalDao extends CrudDaoDefaultImpl {
	
	public RequisicaoPessoalDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection find(IDto arg0) throws Exception {
		return null;
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();
		
		listFields.add(new Field("idSolicitacaoServico", "idSolicitacaoServico", true, false, false, false));
		listFields.add(new Field("idCargo", "idCargo", false, false, false, false));
		listFields.add(new Field("vagas", "vagas", false, false, false, false));
		listFields.add(new Field("tipoContratacao", "tipoContratacao", false, false, false, false));
		listFields.add(new Field("motivoContratacao", "motivoContratacao", false, false, false, false));
		listFields.add(new Field("salario", "salario", false, false, false, false));
		listFields.add(new Field("idCentroCusto", "idCentroCusto", false, false, false, false));
		listFields.add(new Field("idProjeto", "idProjeto", false, false, false, false));
		listFields.add(new Field("idParecerValidacao", "idParecerValidacao", false, false, false, false));
		listFields.add(new Field("rejeitada", "rejeitada", false, false, false, false));
		listFields.add(new Field("confidencial", "confidencial", false, false, false, false));
		listFields.add(new Field("beneficios", "beneficios", false, false, false, false));
		listFields.add(new Field("folgas", "folgas", false, false, false, false));
		listFields.add(new Field("idJornada", "idJornada", false, false, false, false));
		listFields.add(new Field("idCidade", "idCidade", false, false, false, false));
		listFields.add(new Field("idUnidade", "idUnidade", false, false, false, false));
		listFields.add(new Field("preRequisitoEntrevistaGestor", "preRequisitoEntrevistaGestor", false, false, false, false));
		listFields.add(new Field("idUf", "idUf", false, false, false, false));
		listFields.add(new Field("idPais", "idPais", false, false, false, false));
		listFields.add(new Field("qtdCandidatosAprovados", "qtdCandidatosAprovados", false, false, false, false));
		
		return listFields;
	}
	
	// Substituir NomeDaTabela
	public String getTableName() {
		return "RH_RequisicaoPessoal";
	}

	
	// Substituir NomeDoDTO
	public Class getBean() {
		
		return RequisicaoPessoalDTO.class;
	}


    public Collection list() throws Exception {
        List list = new ArrayList();
        list.add(new Order("idCargo"));
        return super.list(list);
    }

}
