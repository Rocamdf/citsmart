package br.com.centralit.bpm.dto;

import java.util.List;

public class PropriedadeElementoDTO {
	private String id;
	private String nome;
	private String tipo;
	private Integer tamanho;
	private Integer tamanhoMaximo;
	private String valorDefault; 
	
	private List<OpcaoPropriedadeDTO> opcoes;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Integer getTamanho() {
		return tamanho;
	}
	public void setTamanho(Integer tamanho) {
		this.tamanho = tamanho;
	}
	public Integer getTamanhoMaximo() {
		return tamanhoMaximo;
	}
	public void setTamanhoMaximo(Integer tamanhoMaximo) {
		this.tamanhoMaximo = tamanhoMaximo;
	}
	public List<OpcaoPropriedadeDTO> getOpcoes() {
		return opcoes;
	}
	public void setOpcoes(List<OpcaoPropriedadeDTO> opcoes) {
		this.opcoes = opcoes;
	}
    public String getValorDefault() {
        return valorDefault;
    }
    public void setValorDefault(String valorDefault) {
        this.valorDefault = valorDefault;
    }

}
