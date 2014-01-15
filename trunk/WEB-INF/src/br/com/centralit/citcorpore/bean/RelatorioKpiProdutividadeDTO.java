package br.com.centralit.citcorpore.bean;

//Criado por Bruno.Aquino

import java.sql.Date;
import java.util.Collection;

import br.com.citframework.dto.IDto;

public class RelatorioKpiProdutividadeDTO implements IDto {

	private static final long serialVersionUID = 1L;
	private Date dataInicio;
	private Date dataFim;
	private String formatoArquivoRelatorio;
	private Integer idContrato;
	private String contrato;
	private String funcionario;
	private String grupo;
	private String listaUsuarios;
	private String checkMostrarRequisicoes;
	private String checkMostrarIncidentes;
	private Collection<SolicitacaoServicoDTO> listaSolicitacoesUsuario;
	private Collection<RelatorioKpiProdutividadeDTO> listaGeral;
	private double qtdeExecutada;
	private double qtdEstourada;
	private int numeroSolicitacao;
	private String NomeServico;
	private double totalGrupoEstouradas;
	private double totalGrupoExecutadas;
	private double totalPorExecutante;
	private double totalPorExecutanteEstouradas;
	private String totalPorExecutanteEstouradasPorcentagem;
	private String totalPorExecutantePorcentagem;
	private String totalPorServicoPorcentagem;

	public String getTotalPorServicoPorcentagem() {
		return totalPorServicoPorcentagem;
	}

	public void setTotalPorServicoPorcentagem(String totalPorServicoPorcentagem) {
		this.totalPorServicoPorcentagem = totalPorServicoPorcentagem;
	}

	public String getTotalPorExecutantePorcentagem() {
		return totalPorExecutantePorcentagem;
	}

	public void setTotalPorExecutantePorcentagem(String totalPorExecutantePorcentagem) {
		this.totalPorExecutantePorcentagem = totalPorExecutantePorcentagem;
	}

	public String getTotalPorExecutanteEstouradasPorcentagem() {
		return totalPorExecutanteEstouradasPorcentagem;
	}

	public void setTotalPorExecutanteEstouradasPorcentagem(String totalPorExecutanteEstouradasPorcentagem) {
		this.totalPorExecutanteEstouradasPorcentagem = totalPorExecutanteEstouradasPorcentagem;
	}

	public double getTotalGrupoEstouradas() {
		return totalGrupoEstouradas;
	}

	public void setTotalGrupoEstouradas(double totalGrupoEstouradas) {
		this.totalGrupoEstouradas = totalGrupoEstouradas;
	}

	public double getTotalGrupoExecutadas() {
		return totalGrupoExecutadas;
	}

	public void setTotalGrupoExecutadas(double totalGrupoExecutadas) {
		this.totalGrupoExecutadas = totalGrupoExecutadas;
	}

	public double getTotalPorExecutante() {
		return totalPorExecutante;
	}

	public void setTotalPorExecutante(double totalPorExecutante) {
		this.totalPorExecutante = totalPorExecutante;
	}

	public double getTotalPorExecutanteEstouradas() {
		return totalPorExecutanteEstouradas;
	}

	public void setTotalPorExecutanteEstouradas(double totalPorExecutanteEstouradas) {
		this.totalPorExecutanteEstouradas = totalPorExecutanteEstouradas;
	}

	public String getNomeServico() {
		return NomeServico;
	}

	public void setNomeServico(String nomeServico) {
		NomeServico = nomeServico;
	}

	public int getNumeroSolicitacao() {
		return numeroSolicitacao;
	}

	public void setNumeroSolicitacao(int numeroSolicitacao) {
		this.numeroSolicitacao = numeroSolicitacao;
	}

	public Collection<SolicitacaoServicoDTO> getListaSolicitacoesUsuario() {
		return listaSolicitacoesUsuario;
	}

	public void setListaSolicitacoesUsuario(Collection<SolicitacaoServicoDTO> listaSolicitacoesUsuario) {
		this.listaSolicitacoesUsuario = listaSolicitacoesUsuario;
	}

	public Collection<RelatorioKpiProdutividadeDTO> getListaGeral() {
		return listaGeral;
	}

	public void setListaGeral(Collection<RelatorioKpiProdutividadeDTO> listaGeral) {
		this.listaGeral = listaGeral;
	}

	public double getQtdeExecutada() {
		return qtdeExecutada;
	}

	public void setQtdeExecutada(double qtdeExecutada) {
		this.qtdeExecutada = qtdeExecutada;
	}

	public double getQtdEstourada() {
		return qtdEstourada;
	}

	public void setQtdEstourada(double qtdEstourada) {
		this.qtdEstourada = qtdEstourada;
	}

	public String getCheckMostrarRequisicoes() {
		return checkMostrarRequisicoes;
	}

	public void setCheckMostrarRequisicoes(String checkMostrarRequisicoes) {
		this.checkMostrarRequisicoes = checkMostrarRequisicoes;
	}

	public String getCheckMostrarIncidentes() {
		return checkMostrarIncidentes;
	}

	public void setCheckMostrarIncidentes(String checkMostrarIncidentes) {
		this.checkMostrarIncidentes = checkMostrarIncidentes;
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

	public String getFormatoArquivoRelatorio() {
		return formatoArquivoRelatorio;
	}

	public void setFormatoArquivoRelatorio(String formatoArquivoRelatorio) {
		this.formatoArquivoRelatorio = formatoArquivoRelatorio;
	}

	public Integer getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}

	public String getContrato() {
		return contrato;
	}

	public void setContrato(String contrato) {
		this.contrato = contrato;
	}

	public String getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(String funcionario) {
		this.funcionario = funcionario;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(String listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
