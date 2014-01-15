package br.com.centralit.bpm.integracao;

import java.util.ArrayList;
import java.util.List;

import br.com.centralit.bpm.dto.ElementoFluxoDTO;
import br.com.centralit.bpm.dto.ElementoFluxoInicioDTO;
import br.com.centralit.bpm.util.Enumerados;
import br.com.centralit.bpm.util.Enumerados.TipoElementoFluxo;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.Order;

public class ElementoFluxoInicioDao extends ElementoFluxoDao {
	public Class getBean() {
		return ElementoFluxoInicioDTO.class;
	}
	@Override
	protected TipoElementoFluxo getTipoElemento() {
		return Enumerados.TipoElementoFluxo.Inicio;
	}	
	public ElementoFluxoInicioDTO restoreByIdFluxo(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idFluxo", "=", parm)); 
		condicao.add(new Condition("tipoElemento", "=", getTipoElemento().name())); 
		ordenacao.add(new Order("idElemento"));
		List list = (List<ElementoFluxoDTO>) super.findByCondition(condicao, ordenacao);
		if (list != null && !list.isEmpty())
			return (ElementoFluxoInicioDTO) list.get(0);
		else
			return null;
	}	
}
