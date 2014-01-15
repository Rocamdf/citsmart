package br.com.centralit.citcorpore.bean;

import java.util.Collection;

import br.com.citframework.dto.IDto;

@SuppressWarnings("serial")
public class DicionarioDTO implements IDto {

	private Integer idDicionario;

	private Integer idLingua;
	
	private String sigla;

	private String nome;

	private String valor;

	private Collection<DicionarioDTO> listDicionario;

	/**
	 * @return the idDicionario
	 */
	public Integer getIdDicionario() {
		return idDicionario;
	}

	/**
	 * @param idDicionario
	 *            the idDicionario to set
	 */
	public void setIdDicionario(Integer idDicionario) {
		this.idDicionario = idDicionario;
	}

	/**
	 * @return the idLingua
	 */
	public Integer getIdLingua() {
		return idLingua;
	}

	/**
	 * @param idLingua
	 *            the idLingua to set
	 */
	public void setIdLingua(Integer idLingua) {
		this.idLingua = idLingua;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor
	 *            the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * @return the listDicionario
	 */
	public Collection<DicionarioDTO> getListDicionario() {
		return listDicionario;
	}

	/**
	 * @param listDicionario
	 *            the listDicionario to set
	 */
	public void setListDicionario(Collection<DicionarioDTO> listDicionario) {
		this.listDicionario = listDicionario;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

}
