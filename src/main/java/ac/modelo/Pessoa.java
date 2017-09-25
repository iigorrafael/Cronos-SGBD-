 package ac.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Entity implementation class for Entity: Pessoa
 *
 */
@Entity
@Table(name = "tab_pessoa")
@Inheritance(strategy = InheritanceType.JOINED)
public class Pessoa implements Serializable {

	public Pessoa() {
		super();
	}

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_pessoa")
	private Long id;

	@NotNull(message = "O campo nome não pode ser nulo")
	@Column(nullable = false)
	private String nome;

	@Pattern(regexp = ".+@.+\\.[a-z]+", message = "E-mail inválido")
	@NotNull(message = "O campo e-mail não pode ser nulo")
	@Column(nullable = false)
	private String usuario;

	private String senha;
	
	
	private String perfilAluno;

	//perfil foi retirado

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date dataCadastro;

	@Column(nullable = false)
	private Boolean status;
	
	@Column(name = "biometria1", nullable = false)
    private String biometria1;
	
	@Column(name = "biometria2", nullable = false)
    private String biometria2;
	
	@Column(name = "biometria3", nullable = false)
    private String biometria3;
	
	@Column(name = "biometria4", nullable = false)
    private String biometria4;

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}


	public String getPerfilAluno() {
		return perfilAluno;
	}

	public void setPerfilAluno(String perfilAluno) {
		this.perfilAluno = perfilAluno;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getBiometria1() {
		return biometria1;
	}

	public void setBiometria1(String biometria1) {
		this.biometria1 = biometria1;
	}

	public String getBiometria2() {
		return biometria2;
	}

	public void setBiometria2(String biometria2) {
		this.biometria2 = biometria2;
	}

	public String getBiometria3() {
		return biometria3;
	}

	public void setBiometria3(String biometria3) {
		this.biometria3 = biometria3;
	}

	public String getBiometria4() {
		return biometria4;
	}

	public void setBiometria4(String biometria4) {
		this.biometria4 = biometria4;
	}

}
