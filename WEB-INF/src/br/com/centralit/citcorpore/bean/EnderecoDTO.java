package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

@SuppressWarnings("serial")
public class EnderecoDTO implements IDto {
	private Integer idEndereco;
	
    private String logradouro;
    
    private String numero;
    
    private String complemento;
    
    private String bairro;
    
    private Integer idCidade;
    
    private Integer idPais;
    
    private String cep;
    
    private String identificacao;
    
    private String nomeCidade;
    
    private String siglaUf;
    
    private String enderecoStr;
    
    private Integer idUf;
    
    

    
    public Integer getIdEndereco() {
        return idEndereco;
    }
    public void setIdEndereco(Integer idEndereco) {
        this.idEndereco = idEndereco;
    }
    public String getLogradouro() {
        return logradouro;
    }
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }
    public String getIdentificacao() {
        return identificacao;
    }
    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public String getComplemento() {
        return complemento;
    }
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
    public String getBairro() {
        return bairro;
    }
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    public Integer getIdCidade() {
        return idCidade;
    }
    public void setIdCidade(Integer idCidade) {
        this.idCidade = idCidade;
    }
    public Integer getIdPais() {
        return idPais;
    }
    public void setIdPais(Integer idPais) {
        this.idPais = idPais;
    }
    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }
    public String getNomeCidade() {
        return nomeCidade;
    }
    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }
    public String getSiglaUf() {
        return siglaUf;
    }
    public void setSiglaUf(String siglaUf) {
        this.siglaUf = siglaUf;
    }
    public void setEnderecoStr(String enderecoStr) {
        this.enderecoStr = enderecoStr;
    }
    public String getEnderecoStr() {
        enderecoStr = "";
        if (this.identificacao != null)
            enderecoStr += this.identificacao + " - ";
        if (this.logradouro != null)
            enderecoStr += this.logradouro;
        if (this.numero != null) {
            if (enderecoStr.length() > 0)
                enderecoStr += ", ";
            enderecoStr += this.numero;
        }
        if (this.complemento != null) {
            if (enderecoStr.length() > 0)
                enderecoStr += ", ";
            enderecoStr += this.complemento;
        }
        if (this.bairro != null) {
            if (enderecoStr.length() > 0)
                enderecoStr += ", ";
            enderecoStr += this.bairro;
        }
        if (this.nomeCidade != null) {
            if (enderecoStr.length() > 0)
                enderecoStr += " - ";
            enderecoStr += this.nomeCidade;
        }
        if (this.siglaUf != null) {
            if (enderecoStr.length() > 0)
                enderecoStr += " - ";
            enderecoStr += this.siglaUf;
        }
        return enderecoStr;
    }
	/**
	 * @return the idUf
	 */
	public Integer getIdUf() {
		return idUf;
	}
	/**
	 * @param idUf the idUf to set
	 */
	public void setIdUf(Integer idUf) {
		this.idUf = idUf;
	}
    
  
}
