package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.SolucaoContornoDTO;
import br.com.centralit.citcorpore.bean.SolucaoDefinitivaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({"rawtypes" , "unchecked"})
public class SolucaoDefinitivaDAO extends CrudDaoDefaultImpl {


	public SolucaoDefinitivaDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;

	@Override
	public Collection find(IDto obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection getFields() {
		Collection listFields = new ArrayList();

		listFields.add(new Field("IDSOLUCAODEFINITIVA", "idSolucaoDefinitiva", true, true, false, false));
		listFields.add(new Field("TITULO", "titulo", false, false, false, false));
		listFields.add(new Field("DESCRICAO", "descricao", false, false, false, false));
		listFields.add(new Field("DATAHORACRIACAO", "dataHoraCriacao", false, false, false, false));
		listFields.add(new Field("idproblema", "idProblema", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "solucaodefinitiva";
	}

	@Override
	public Collection list() throws Exception {
		List list = new ArrayList();
		list.add(new Order("titulo"));
		return super.list(list);
	}

	@Override
	public Class getBean() {
		return SolucaoDefinitivaDTO.class;
	}
	
	public IDto restore(IDto obj) throws Exception{
		SolucaoDefinitivaDTO solucaoDefinitivaDTO = (SolucaoDefinitivaDTO) obj;
		
		List condicao = new ArrayList();
		condicao.add(new Condition("idSolucaoDefinitiva", "=", solucaoDefinitivaDTO.getIdSolucaoDefinitiva()));
		
		ArrayList<SolucaoDefinitivaDTO> res = (ArrayList<SolucaoDefinitivaDTO>) super.findByCondition(condicao, null);
		
		if(res != null){
			return res.get(0);
		}else{
			return null;
		}
	}
	
	public IDto findByIdProblema(IDto obj) throws Exception{
		SolucaoDefinitivaDTO solucaoDefinitivaDTO = (SolucaoDefinitivaDTO) obj;
		
		List condicao = new ArrayList();
		condicao.add(new Condition("idProblema", "=", solucaoDefinitivaDTO.getIdProblema()));
		
		ArrayList<SolucaoDefinitivaDTO> res = (ArrayList<SolucaoDefinitivaDTO>) super.findByCondition(condicao, null);
		
		if(res != null){
			return res.get(0);
		}else{
			return null;
		}
	}

}
