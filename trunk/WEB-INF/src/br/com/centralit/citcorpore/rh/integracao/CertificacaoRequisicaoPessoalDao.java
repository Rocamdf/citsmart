package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.CertificacaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

//Substituir NomeDaClasseDao
public class CertificacaoRequisicaoPessoalDao extends CrudDaoDefaultImpl {
	
	public CertificacaoRequisicaoPessoalDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection find(IDto arg0) throws Exception {
		return null;
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();
	    

		listFields.add(new Field("idCertificacao", "idCertificacao", true, false, false, false));
		listFields.add(new Field("versaoCertificacao", "versaoCertificacao", true, false, false, false));
		listFields.add(new Field("validadeCertificacao", "validadeCertificacao", true, false, false, false));
		listFields.add(new Field("descricaoCertificacao", "descricaoCertificacao", true, false, false, false));
		listFields.add(new Field("idCurriculo" ,"idCurriculo", false, false, false, false));
		
		return listFields;
	}
	
	// Substituir NomeDaTabela
	public String getTableName() {
		return "RH_CertificacaoRequisicaoPessoal";
	}

	
	// Substituir NomeDoDTO
	public Class getBean() {
		
		return CertificacaoDTO.class;
	}


    public Collection list() throws Exception {
        List list = new ArrayList();
        list.add(new Order("descricao"));
        return super.list(list);
    }
    
    public Collection findByIdCurriculo(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idCurriculo", "=", parm)); 
		ordenacao.add(new Order("idCertificacao"));
		return super.findByCondition(condicao, ordenacao);
	}
}
