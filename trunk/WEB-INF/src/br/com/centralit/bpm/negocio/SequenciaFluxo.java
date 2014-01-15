package br.com.centralit.bpm.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import br.com.centralit.bpm.dto.ItemTrabalhoFluxoDTO;
import br.com.centralit.bpm.dto.SequenciaFluxoDTO;
import br.com.centralit.bpm.integracao.ItemTrabalhoFluxoDao;
import br.com.centralit.bpm.integracao.SequenciaFluxoDao;
import br.com.centralit.bpm.util.UtilScript;

public class SequenciaFluxo extends NegocioBpm{
	
	private InstanciaFluxo instanciaFluxo;
	
	public SequenciaFluxo(InstanciaFluxo instanciaFluxo) {
		this.instanciaFluxo = instanciaFluxo;
		setTransacao(instanciaFluxo.getTransacao());
	}
	
	public List<ItemTrabalho> getOrigens(ItemTrabalho itemTrabalho) throws Exception {
		SequenciaFluxoDao sequenciaFluxoDao = new SequenciaFluxoDao();
		setTransacaoDao(sequenciaFluxoDao);
		List<ItemTrabalho> origens = new ArrayList();
		Collection<SequenciaFluxoDTO> colSequencias = sequenciaFluxoDao.findByIdFluxoAndIdDestino(this.getInstanciaFluxo().getIdFluxo(), itemTrabalho.getIdElemento());
		if (colSequencias != null) {
			ItemTrabalhoFluxoDao itemTrabalhoDao = new ItemTrabalhoFluxoDao();
			setTransacaoDao(itemTrabalhoDao);
			for (SequenciaFluxoDTO sequenciaDto : colSequencias) {
				ItemTrabalhoFluxoDTO itemTrabalhoDto = itemTrabalhoDao.lastByIdInstanciaAndIdElemento(this.getInstanciaFluxo().getIdInstancia(), sequenciaDto.getIdElementoOrigem());
				if (itemTrabalhoDto != null)
					origens.add(ItemTrabalho.getItemTrabalho(getInstanciaFluxo(), itemTrabalhoDto.getIdItemTrabalho()));
			}
		}
		return origens;
	}

	public List<ItemTrabalho> getDestinos(ItemTrabalho elementoOrigem) throws Exception {
		SequenciaFluxoDao sequenciaFluxoDao = new SequenciaFluxoDao();
		setTransacaoDao(sequenciaFluxoDao);
		List<ItemTrabalho> destinos = new ArrayList();
		Collection<SequenciaFluxoDTO> colSequencias = sequenciaFluxoDao.findByIdFluxoAndIdOrigem(this.getInstanciaFluxo().getIdFluxo(), elementoOrigem.getIdElemento());
		if (colSequencias != null) {
			for (SequenciaFluxoDTO sequenciaDto : colSequencias) {
				if (condicaoAtendida(sequenciaDto)) 
					destinos.add(ItemTrabalho.getItemTrabalhoDeElemento(getInstanciaFluxo(), sequenciaDto.getIdElementoDestino()));
			}
		}
		return destinos;
	}

	private boolean condicaoAtendida(SequenciaFluxoDTO sequenciaDto) throws Exception {
		return new Condicao(instanciaFluxo,sequenciaDto.getCondicao()).executa();
	}

	public InstanciaFluxo getInstanciaFluxo() {
		return instanciaFluxo;
	}

	public static void main(String[] args) throws Exception {
		HashMap<String, Object> params = new HashMap();
		params.put("solicitacaoservico.situacao", "RESOLVIDA");
		boolean ret = (Boolean) UtilScript.executaScript("nome","#{solicitacaoservico.situacao}.equalsIgnoreCase('Cancelada') || #{solicitacaoservico.situacao}.equalsIgnoreCase('Resolvida');", params);
		if (ret)
			System.out.println("####### true");
		else
			System.out.println("####### false");
	}

}
