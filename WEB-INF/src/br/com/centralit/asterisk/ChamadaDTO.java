package br.com.centralit.asterisk;

public class ChamadaDTO {
	String numeroOrigem;
	String numeroDestino;
	
	public String getNumeroOrigem() {
		return numeroOrigem;
	}
	public void setNumeroOrigem(String numeroOrigem) {
		this.numeroOrigem = numeroOrigem;
	}
	public String getNumeroDestino() {
		return numeroDestino;
	}
	public void setNumeroDestino(String numeroDestino) {
		this.numeroDestino = numeroDestino;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj!=null){
			ChamadaDTO objeto = (ChamadaDTO) obj;
			return ((this.getNumeroOrigem().equals(objeto.getNumeroOrigem()))&&(this.getNumeroDestino().equals(objeto.getNumeroDestino())));
		} else {
			return false;
		}
	}
}
