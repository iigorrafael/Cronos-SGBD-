package sgpb.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "tab_organizadores")
public class Organizadores implements Serializable{
	
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_organizadores")
    private Long id;
    
    @NotNull(message = "O campo grupo não pode ser nulo")
    @Column(name = "receber_lista_email", nullable = false, columnDefinition = "BOOLEAN")
	private boolean receberListaEmail;
    
    @ManyToOne
    private Evento evento;
    
    @ManyToOne
    private Evento servidor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isReceberListaEmail() {
		return receberListaEmail;
	}

	public void setReceberListaEmail(boolean receberListaEmail) {
		this.receberListaEmail = receberListaEmail;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Evento getServidor() {
		return servidor;
	}

	public void setServidor(Evento servidor) {
		this.servidor = servidor;
	}
	
	
	

}
