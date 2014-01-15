package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class CidadesDTO implements IDto{
    private static final long serialVersionUID = 1L;
    
    private Integer idCidade;
	private Integer IdUf;
	private String nomeCidade;
	
	
	/**
	 * @return the nomeCidade
	 */
	public String getNomeCidade() {
		return nomeCidade;
	}
	/**
	 * @param nomeCidade the nomeCidade to set
	 */
	public void setNomeCidade(String nomeCidade) {
		this.nomeCidade = nomeCidade;
	}
	/**
	 * @return the idCidade
	 */
	public Integer getIdCidade() {
		return idCidade;
	}
	/**
	 * @param idCidade the idCidade to set
	 */
	public void setIdCidade(Integer idCidade) {
		this.idCidade = idCidade;
	}
	/**
	 * @return the idUf
	 */
	public Integer getIdUf() {
		return IdUf;
	}
	/**
	 * @param idUf the idUf to set
	 */
	public void setIdUf(Integer idUf) {
		IdUf = idUf;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
   
    
}