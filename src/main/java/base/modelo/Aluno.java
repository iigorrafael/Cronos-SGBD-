package base.modelo;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import ac.modelo.Pessoa;

/**
 * Entity implementation class for Entity: Aluno
 *
 */
@Entity
@Table(name = "tab_aluno")
@PrimaryKeyJoinColumn(name = "id_pessoa")
public class Aluno extends Pessoa implements Serializable {

	public Aluno() {
		super();
	}

	private static final long serialVersionUID = 1L;
	

	private String situacao;
	// 1 para sim 2 para não
	
	private String nomeResponsavel;
	
	
	private String telefoneResponsavel;
	
//	
//	private boolean liberado;
	
	
	private Integer qtdRespostas;

	@Column(name = "permite_cadastro_certificado")
	private Integer permiteCadastroCertificado;


	public Integer getQtdRespostas() {
		return qtdRespostas;
	}

	
//	public boolean isLiberado() {
//		return liberado;
//	}
//
//
//	public void setLiberado(boolean liberado) {
//		this.liberado = liberado;
//	}


	public void setQtdRespostas(Integer qtdRespostas) {
		this.qtdRespostas = qtdRespostas;
	}

	public String getNomeResponsavel() {
		return nomeResponsavel;
	}

	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}

	public String getTelefoneResponsavel() {
		return telefoneResponsavel;
	}

	public void setTelefoneResponsavel(String telefoneResponsavel) {
		this.telefoneResponsavel = telefoneResponsavel;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Integer getPermiteCadastroCertificado() {
		return permiteCadastroCertificado;
	}

	public void setPermiteCadastroCertificado(Integer permiteCadastroCertificado) {
		this.permiteCadastroCertificado = permiteCadastroCertificado;
	}
}
