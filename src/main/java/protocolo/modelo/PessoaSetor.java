package protocolo.modelo;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ac.modelo.Pessoa;
import base.modelo.Servidor;

@Entity
@Table(name = "tab_pessoa_setor")
public class PessoaSetor {

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private boolean responsavel;
	
	private boolean visualizaProtocolo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable=false)
	private Servidor servidor;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable=false)
	private Setor setor;
	
	private boolean status;

	public Long getId() {
		return id;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isResponsavel() {
		return responsavel;
	}

	public void setResponsavel(boolean responsavel) {
		this.responsavel = responsavel;
	}
	
	public Servidor getServidor() {
		return servidor;
	}

	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public boolean isVisualizaProtocolo() {
		return visualizaProtocolo;
	}

	public void setVisualizaProtocolo(boolean visualizaProtocolo) {
		this.visualizaProtocolo = visualizaProtocolo;
	}
	
	
	
}
