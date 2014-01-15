package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;


public class ExperienciaProfissionalCurriculoDTO implements IDto {
	private Integer idExperienciaProfissional;
	private Integer idTipoPeriodo;
	private String periodo;
	private String funcao;
	
	private String descricaoTipoPeriodo;
	private String descricaoEmpresa;
	private Integer idCurriculo;
	private String localidade;
	
	
	
	public Integer getIdCurriculo() {
		return idCurriculo;
	}
	public void setIdCurriculo(Integer idCurriculo) {
		this.idCurriculo = idCurriculo;
	}
	
	public Integer getIdExperienciaProfissional() {
		return idExperienciaProfissional;
	}
	public void setIdExperienciaProfissional(Integer idExperienciaProfissional) {
		this.idExperienciaProfissional = idExperienciaProfissional;
	}
	public Integer getIdTipoPeriodo() {
		return idTipoPeriodo;
	}
	public void setIdTipoPeriodo(Integer idTipoPeriodo) {
		this.idTipoPeriodo = idTipoPeriodo;
	}
	
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	public String getDescricaoTipoPeriodo() {
		return descricaoTipoPeriodo;
	}
	public void setDescricaoTipoPeriodo(String descricaoTipoPeriodo) {
		this.descricaoTipoPeriodo = descricaoTipoPeriodo;
	}
	public String getDescricaoEmpresa() {
		return descricaoEmpresa;
	}
	public void setDescricaoEmpresa(String descricaoEmpresa) {
		this.descricaoEmpresa = descricaoEmpresa;
	}
	public String getLocalidade() {
		return localidade;
	}
	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}
	public String getFuncao() {
		return funcao;
	}
	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}
	
}