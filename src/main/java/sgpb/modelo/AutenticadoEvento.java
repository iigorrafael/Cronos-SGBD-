package sgpb.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import ac.modelo.Pessoa;

/**
 * @author igor
 *
 */
@Entity
@Table(name = "tab_autenticado_atividade")
public class AutenticadoEvento implements Serializable {
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_autenticado_atividade")
    private Long id;
    
    @NotNull(message = "O campo grupo não pode ser nulo")
    @Column(name = "data_hora", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHora;    
    
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

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	
	public AtividadeEvento getAtividades() {
		return atividades;
	}

	public void setAtividades(AtividadeEvento atividades) {
		this.atividades = atividades;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

}
