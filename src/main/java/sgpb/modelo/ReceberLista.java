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

import ac.modelo.Pessoa;


@Entity
@Table(name = "tab_receber_lista")
public class ReceberLista implements Serializable{
	
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_receber_lista")
    private Long id;
    
    @NotNull(message = "O campo nome não pode ser nulo")
    @Column(name = "nome_receber_lista", nullable = false)
	private String nome;
    
    
    @NotNull(message = "O campo e-mail não pode ser nulo")
    @Column(name = "email_receber_lista", nullable = false)
	private String email;
    
    @ManyToOne
    private AtividadeEvento atividades;
    
    @ManyToOne
    private Pessoa pessoa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AtividadeEvento getAtividades() {
		return atividades;
	}

	public void setAtividades(AtividadeEvento atividades) {
		this.atividades = atividades;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	
}
