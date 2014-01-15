package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import br.com.citframework.dto.IDto;
import br.com.citframework.util.DateAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RequisicaoMudancaServico") 
public class RequisicaoMudancaServicoDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer idRequisicaoMudancaServico;
	private Integer idRequisicaoMudanca;
	private Integer idServico;

	// campos para mera visualização
	private String nome;
	private String descricao;

	@XmlElement(name = "dataFim")
	@XmlJavaTypeAdapter(DateAdapter.class)	
	private Date dataFim;

	@Override
	public boolean equals(Object obj) {
		RequisicaoMudancaServicoDTO objComparacao = null;

		if (obj instanceof RequisicaoMudancaServicoDTO) {
			objComparacao = (RequisicaoMudancaServicoDTO) obj;
			if (objComparacao.getIdServico().equals(this.getIdServico()) && objComparacao.getIdRequisicaoMudanca().equals(this.getIdRequisicaoMudanca())) {
				return true;
			}
		}

		return false;
	}

	public Integer getIdRequisicaoMudancaServico() {
		return idRequisicaoMudancaServico;
	}

	public void setIdRequisicaoMudancaServico(Integer idRequisicaoMudancaServico) {
		this.idRequisicaoMudancaServico = idRequisicaoMudancaServico;
	}

	public Integer getIdRequisicaoMudanca() {
		return idRequisicaoMudanca;
	}

	public void setIdRequisicaoMudanca(Integer idRequisicaoMudanca) {
		this.idRequisicaoMudanca = idRequisicaoMudanca;
	}

	public Integer getIdServico() {
		return idServico;
	}

	public void setIdServico(Integer idServico) {
		this.idServico = idServico;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	

}
