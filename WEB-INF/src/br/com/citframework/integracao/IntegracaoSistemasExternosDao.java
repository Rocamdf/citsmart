package br.com.citframework.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.IntegracaoSistemasExternosDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class IntegracaoSistemasExternosDao extends CrudDaoDefaultImpl {
	public IntegracaoSistemasExternosDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idIntegracao" ,"idIntegracao", true, true, false, false));
		listFields.add(new Field("dataHora" ,"dataHora", false, false, false, false));
		listFields.add(new Field("processo" ,"processo", false, false, false, false));
		listFields.add(new Field("identificador" ,"identificador", false, false, false, false));
		listFields.add(new Field("idObjeto" ,"idObjeto", false, false, false, false));
		listFields.add(new Field("situacao" ,"situacao", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "integracaosistemasexternos";
	}
	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return IntegracaoSistemasExternosDTO.class;
	}
	public Collection find(IDto arg0) throws Exception {
		return null;
	}
	public IntegracaoSistemasExternosDTO findByIdProcessoAndIdentificador(String processo, String identificador) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("processo", "=", processo)); 
		condicao.add(new Condition("identificador", "=", identificador)); 
		ordenacao.add(new Order("idIntegracao"));
		List<IntegracaoSistemasExternosDTO> result = (List<IntegracaoSistemasExternosDTO>) super.findByCondition(condicao, ordenacao);
		if (result != null)
			return result.get(0);
		else
			return null;
	}
	public IntegracaoSistemasExternosDTO findByIdProcessoAndIdObjeto(String processo, String idObjeto, String situacao) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("processo", "=", processo)); 
		condicao.add(new Condition("idObjeto", "=", idObjeto)); 
		if (situacao != null)
			condicao.add(new Condition("situacao", "=", situacao)); 
		ordenacao.add(new Order("idIntegracao"));
		List<IntegracaoSistemasExternosDTO> result = (List<IntegracaoSistemasExternosDTO>) super.findByCondition(condicao, ordenacao);
		if (result != null)
			return result.get(0);
		else
			return null;
	}
	@Override
	public void updateNotNull(IDto obj) throws Exception {
		super.updateNotNull(obj);
	}
}
