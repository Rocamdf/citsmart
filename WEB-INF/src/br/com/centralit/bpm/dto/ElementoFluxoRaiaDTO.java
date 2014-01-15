package br.com.centralit.bpm.dto;

import java.util.List;

public class ElementoFluxoRaiaDTO extends ElementoFluxoDTO {
	
	private String siglaGrupo;
	private List<String> idElementosAgrupados;

	public List<String> getIdElementosAgrupados() {
		return idElementosAgrupados;
	}

	public void setIdElementosAgrupados(List<String> idElementosAgrupados) {
		this.idElementosAgrupados = idElementosAgrupados;
	}

	public String getSiglaGrupo() {
		return siglaGrupo;
	}

	public void setSiglaGrupo(String siglaGrupo) {
		this.siglaGrupo = siglaGrupo;
	}
	
}
