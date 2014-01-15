package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.CertificacaoCurriculoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "unchecked", "serial", "rawtypes", "unused" })
public class CertificacaoCurriculoDao extends CrudDaoDefaultImpl {
	public CertificacaoCurriculoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Collection find(IDto obj) throws Exception {
		List lst = new ArrayList();
		lst.add("idCertificacao");
		return super.find(obj, lst);
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();
		
		listFields.add(new Field("idCertificacao", "idCertificacao", true, true, false, false));
		listFields.add(new Field("versao", "versao", false, false, false, false));
		listFields.add(new Field("validade", "validade", false, false, false, false));
		listFields.add(new Field("idCurriculo" ,"idCurriculo", false, false, false, false));
		listFields.add(new Field("descricao" ,"descricao", false, false, false, false));
		
		return listFields;
	}

	public String getTableName() {
		return "RH_CertificacaoCurriculo";
	}

	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return CertificacaoCurriculoDTO.class;
	}
	
	public Collection findByIdCurriculo(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idCurriculo", "=", parm)); 
		ordenacao.add(new Order("idCertificacao"));
		List<CertificacaoCurriculoDTO> result = (List<CertificacaoCurriculoDTO>) super.findByCondition(condicao, ordenacao);
		
		return result;
	}
	public void deleteByIdCurriculo(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idCurriculo", "=", parm));
		super.deleteByCondition(condicao);
	}
}
