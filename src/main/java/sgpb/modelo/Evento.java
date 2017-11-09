package sgpb.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tab_eventos")
public class Evento implements Serializable {

	public Evento() {
		super();
	}

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_evento")
	private Long id;

	@NotNull(message = "O campo grupo não pode ser nulo")
	@Column(name = "nome_evento", nullable = false)
	private String nome;

	@NotNull(message = "O campo grupo não pode ser nulo")
	@Column(name = "data_evento", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataEvento;

	@Column(name = "data_evento_fim")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataEventoFim;

	@NotNull(message = "O campo grupo não pode ser nulo")
	@Column(name = "local_evento", nullable = false)
	private String local;

	@Lob
	@NotNull(message = "O campo grupo não pode ser nulo")
	@Column(name = "detalhes_evento", nullable = false, columnDefinition = "TEXT")
	private String detalhes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataEvento() {
		return dataEvento;
	}

	public void setDataEvento(Date dataEvento) {
		this.dataEvento = dataEvento;
	}

	public Date getDataEventoFim() {
		return dataEventoFim;
	}

	public void setDataEventoFim(Date dataEventoFim) {
		this.dataEventoFim = dataEventoFim;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getDetalhes() {
		return detalhes;
	}

	public void setDetalhes(String detalhes) {
		this.detalhes = detalhes;
	}

}
