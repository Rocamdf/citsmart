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
@XmlRootElement(name = "RequisicaoMudancaItemConfiguracao") 
public class RequisicaoMudancaItemConfiguracaoDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer idRequisicaoMudancaItemConfiguracao;
	private Integer idRequisicaoMudanca;
	private Integer idItemConfiguracao;
	private String descricao;

	private String nomeItemConfiguracao;

	@XmlElement(name = "dataFim")
	@XmlJavaTypeAdapter(DateAdapter.class)	
	private Date dataFim;

	@Override
	public boolean equals(Object obj) {
		RequisicaoMudancaItemConfiguracaoDTO objComparacao = null;

		if (obj instanceof RequisicaoMudancaItemConfiguracaoDTO) {
			objComparacao = (RequisicaoMudancaItemConfiguracaoDTO) obj;
			if (objComparacao.getIdItemConfiguracao().equals(this.getIdItemConfiguracao()) && objComparacao.getIdRequisicaoMudanca().equals(this.getIdRequisicaoMudanca())) {
				return true;
			}
		}

		return false;
	}
	
	public String getNomeItemConfiguracao() {
		return nomeItemConfiguracao;
	}

	public void setNomeItemConfiguracao(String nomeItemConfiguracao) {
		this.nomeItemConfiguracao = nomeItemConfiguracao;
	}

	public Integer getIdRequisicaoMudancaItemConfiguracao() {
		return idRequisicaoMudancaItemConfiguracao;
	}

	public void setIdRequisicaoMudancaItemConfiguracao(Integer idRequisicaoMudancaItemConfiguracao) {
		this.idRequisicaoMudancaItemConfiguracao = idRequisicaoMudancaItemConfiguracao;
	}

	public Integer getIdRequisicaoMudanca() {
		return idRequisicaoMudanca;
	}

	public void setIdRequisicaoMudanca(Integer idRequisicaoMudanca) {
		this.idRequisicaoMudanca = idRequisicaoMudanca;
	}

	public Integer getIdItemConfiguracao() {
		return idItemConfiguracao;
	}

	public void setIdItemConfiguracao(Integer idItemConfiguracao) {
		this.idItemConfiguracao = idItemConfiguracao;
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
