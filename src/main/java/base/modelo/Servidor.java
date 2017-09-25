package base.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import ac.modelo.Pessoa;


@Entity
@Table(name = "tab_servidor")
@PrimaryKeyJoinColumn(name = "id_pessoa")
public class Servidor extends Pessoa implements Serializable{
	
	public Servidor() {
		super();
	}

	private static final long serialVersionUID = 1L;
	@Column(nullable=false, length=50)
	private String siape;
	
	
	private String observacao;
	
	private Boolean chefe;

	public String getSiape() {
		return siape;
	}

	public void setSiape(String siape) {
		this.siape = siape;
	}

	public Boolean getChefe() {
		return chefe;
	}

	public void setChefe(Boolean chefe) {
		this.chefe = chefe;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

}
