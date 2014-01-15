package br.com.centralit.bpm.dto;

import br.com.citframework.dto.IDto;

public class SequenciaFluxoDTO implements IDto {
	private Integer idFluxo;
	private Integer idElementoOrigem;
	private Integer idElementoDestino;
	private Integer idConexaoOrigem;
	private Integer idConexaoDestino;
	private String nome;
	private String condicao;

	private Integer idElemento;
	private String idDesenhoOrigem;
	private String idDesenhoDestino;
	private Double bordaX;
	private Double bordaY;
	private String posicaoAlterada;

	public Integer getIdFluxo() {
		return idFluxo;
	}

	public void setIdFluxo(Integer idFluxo) {
		this.idFluxo = idFluxo;
	}

	public String getCondicao() {
		return condicao;
	}

	public void setCondicao(String condicao) {
		this.condicao = condicao;
	}

	public Integer getIdElementoOrigem() {
		return idElementoOrigem;
	}

	public void setIdElementoOrigem(Integer idElementoOrigem) {
		this.idElementoOrigem = idElementoOrigem;
	}

	public Integer getIdElementoDestino() {
		return idElementoDestino;
	}

	public void setIdElementoDestino(Integer idElementoDestino) {
		this.idElementoDestino = idElementoDestino;
	}

	public String getIdDesenhoOrigem() {
		return idDesenhoOrigem;
	}

	public void setIdDesenhoOrigem(String idDesenhoOrigem) {
		this.idDesenhoOrigem = idDesenhoOrigem;
	}

	public String getIdDesenhoDestino() {
		return idDesenhoDestino;
	}

	public void setIdDesenhoDestino(String idDesenhoDestino) {
		this.idDesenhoDestino = idDesenhoDestino;
	}

	public Integer getIdConexaoOrigem() {
		return idConexaoOrigem;
	}

	public void setIdConexaoOrigem(Integer idConexaoOrigem) {
		this.idConexaoOrigem = idConexaoOrigem;
	}

	public Integer getIdConexaoDestino() {
		return idConexaoDestino;
	}

	public void setIdConexaoDestino(Integer idConexaoDestino) {
		this.idConexaoDestino = idConexaoDestino;
	}
	public Integer getIdElemento() {
		return idElemento;
	}

	public void setIdElemento(Integer idElemento) {
		this.idElemento = idElemento;
	}

	public Double getBordaX() {
		return bordaX;
	}

	public void setBordaX(Double bordaX) {
		this.bordaX = bordaX;
	}

	public Double getBordaY() {
		return bordaY;
	}

	public void setBordaY(Double bordaY) {
		this.bordaY = bordaY;
	}

	public String getPosicaoAlterada() {
		return posicaoAlterada;
	}

	public void setPosicaoAlterada(String posicaoAlterada) {
		this.posicaoAlterada = posicaoAlterada;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
