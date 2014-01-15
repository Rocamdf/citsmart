package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.IntegranteViagemDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;
@SuppressWarnings({ "rawtypes", "unchecked" })
public class IntegranteViagemDao extends CrudDaoDefaultImpl {

	public IntegranteViagemDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Collection find(IDto obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public Collection getFields() {
		Collection listFields = new ArrayList();

		listFields.add(new Field("idsolicitacaoservico", "idSolicitacaoServico", false, false, false, false));
		listFields.add(new Field("idempregado", "idEmpregado", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "integranteviagem";
	}

	@Override
	public Collection list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class getBean() {
		// TODO Auto-generated method stub
		return IntegranteViagemDTO.class;
	}
	
	public Collection<IntegranteViagemDTO> findAllByIdSolicitacao(Integer idSolicitacao) throws Exception{
		List condicao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacao));
		return super.findByCondition(condicao, null);
	}
	
	public boolean verificarSeIntegranteViagemExiste(Integer idsolicitacaoServico,Integer idEmpregado) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idsolicitacaoServico));
		condicao.add(new Condition("idEmpregado", "=", idEmpregado));
		List lista = (List) super.findByCondition(condicao, null);
		if(lista!=null && !lista.isEmpty()){
			return true;
		}else{
			return false;
		}
	}
	
	public Integer retornaQtdeIntegrantes(Integer idSolicitacao) throws Exception{
		return (Integer)this.findAllByIdSolicitacao(idSolicitacao).size();
	}

}
