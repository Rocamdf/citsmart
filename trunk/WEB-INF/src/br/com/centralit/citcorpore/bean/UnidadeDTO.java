package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import br.com.citframework.dto.IDto;
@SuppressWarnings("rawtypes")
public class UnidadeDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 638687400065001805L;

	private Integer idUnidade;

	private Integer idGrupo;

	private Integer idUnidadePai;

	private Integer idTipoUnidade;

	private Integer idEmpresa;

	private String nome;

	private Date dataInicio;

	private Date dataFim;

	private String descricao;

	private String email;

	private Integer idEndereco;

	private String aceitaEntregaProduto;

	private List servicos;

	private Integer[] idContrato;

	private int nivel;

	private String localidadesSerializadas;

	private ArrayList<LocalidadeUnidadeDTO> listaDeLocalidade;

	private String logradouro;

	private String numero;

	private String complemento;

	private String bairro;

	private Integer idCidade;

	private Integer idPais;

	private String cep;
	
	private Integer idUf;
	
	

	public Integer getIdUnidade() {
		return idUnidade;
	}

	public void setIdUnidade(Integer idUnidade) {
		this.idUnidade = idUnidade;
	}

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	public Integer getIdUnidadePai() {
		return idUnidadePai;
	}

	public void setIdUnidadePai(Integer idUnidadePai) {
		this.idUnidadePai = idUnidadePai;
	}

	public Integer getIdTipoUnidade() {
		return idTipoUnidade;
	}

	public void setIdTipoUnidade(Integer idTipoUnidade) {
		this.idTipoUnidade = idTipoUnidade;
	}

	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getNome() {
		return nome;
	}

	public String getNomeNivel() {
		if (this.getNome() == null) {
			return this.nome;
		}
		String str = "";
		for (int i = 0; i < this.getNivel(); i++) {
			str += "....";
		}
		return str + this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public List getServicos() {
		return servicos;
	}

	public void setServicos(List servicos) {
		this.servicos = servicos;
	}

	public Integer[] getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Integer[] idContrato) {
		this.idContrato = idContrato;
	}

	/**
	 * @return the localidadesSerializadas
	 */
	public String getLocalidadesSerializadas() {
		return localidadesSerializadas;
	}

	/**
	 * @param localidadesSerializadas
	 *            the localidadesSerializadas to set
	 */
	public void setLocalidadesSerializadas(String localidadesSerializadas) {
		this.localidadesSerializadas = localidadesSerializadas;
	}

	/**
	 * @return the listaDeLocalidade
	 */
	public ArrayList<LocalidadeUnidadeDTO> getListaDeLocalidade() {
		return listaDeLocalidade;
	}

	/**
	 * @param listaDeLocalidade
	 *            the listaDeLocalidade to set
	 */
	public void setListaDeLocalidade(ArrayList<LocalidadeUnidadeDTO> listaDeLocalidade) {
		this.listaDeLocalidade = listaDeLocalidade;
	}

	public Integer getIdEndereco() {
		return idEndereco;
	}

	public void setIdEndereco(Integer idEndereco) {
		this.idEndereco = idEndereco;
	}

	public String getAceitaEntregaProduto() {
		return aceitaEntregaProduto;
	}

	public void setAceitaEntregaProduto(String aceitaEntregaProduto) {
		this.aceitaEntregaProduto = aceitaEntregaProduto;
	}

	/**
	 * @return the logradouro
	 */
	public String getLogradouro() {
		return logradouro;
	}

	/**
	 * @param logradouro
	 *            the logradouro to set
	 */
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	/**
	 * @return the numero
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * @param numero
	 *            the numero to set
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * @return the complemento
	 */
	public String getComplemento() {
		return complemento;
	}

	/**
	 * @param complemento
	 *            the complemento to set
	 */
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	/**
	 * @return the bairro
	 */
	public String getBairro() {
		return bairro;
	}

	/**
	 * @param bairro
	 *            the bairro to set
	 */
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	/**
	 * @return the idCidade
	 */
	public Integer getIdCidade() {
		return idCidade;
	}

	/**
	 * @param idCidade
	 *            the idCidade to set
	 */
	public void setIdCidade(Integer idCidade) {
		this.idCidade = idCidade;
	}

	/**
	 * @return the idPais
	 */
	public Integer getIdPais() {
		return idPais;
	}

	/**
	 * @param idPais
	 *            the idPais to set
	 */
	public void setIdPais(Integer idPais) {
		this.idPais = idPais;
	}

	/**
	 * @return the cep
	 */
	public String getCep() {
		return cep;
	}

	/**
	 * @param cep
	 *            the cep to set
	 */
	public void setCep(String cep) {
		this.cep = cep;
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
