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
@Table(name = "tab_receber_lista")
public class ReceberLista implements Serializable{
	
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_receber_lista")
    private Long id;
    
    @Column(name = "receber_lista_email", nullable = false, columnDefinition = "BOOLEAN")
	private boolean receberListaEmail;
    
    @NotNull(message = "O campo grupo não pode ser nulo")
    @Column(name = "email", nullable = false, columnDefinition = "BOOLEAN")
	private String email;
    
    @ManyToOne
    private Atividades atividades;

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

	public Atividades getAtividades() {
		return atividades;
	}

	public void setAtividades(Atividades atividades) {
		this.atividades = atividades;
	}

}
