/**
 * 
 */
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

/**
 * @author igor
 *
 */
@Entity
@Table(name = "tab_evento")
public class Evento implements Serializable {

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
    
    @Column(name = "inscricao_previa", nullable = false, columnDefinition = "TEXT")
    private boolean inscricaoPrevia;
 

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

	public boolean isInscricaoPrevia() {
		return inscricaoPrevia;
	}

	public void setInscricaoPrevia(boolean inscricaoPrevia) {
		this.inscricaoPrevia = inscricaoPrevia;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataEvento == null) ? 0 : dataEvento.hashCode());
		result = prime * result + ((dataEventoFim == null) ? 0 : dataEventoFim.hashCode());
		result = prime * result + ((detalhes == null) ? 0 : detalhes.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (inscricaoPrevia ? 1231 : 1237);
		result = prime * result + ((local == null) ? 0 : local.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Evento)) {
			return false;
		}
		Evento other = (Evento) obj;
		if (dataEvento == null) {
			if (other.dataEvento != null) {
				return false;
			}
		} else if (!dataEvento.equals(other.dataEvento)) {
			return false;
		}
		if (dataEventoFim == null) {
			if (other.dataEventoFim != null) {
				return false;
			}
		} else if (!dataEventoFim.equals(other.dataEventoFim)) {
			return false;
		}
		if (detalhes == null) {
			if (other.detalhes != null) {
				return false;
			}
		} else if (!detalhes.equals(other.detalhes)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (inscricaoPrevia != other.inscricaoPrevia) {
			return false;
		}
		if (local == null) {
			if (other.local != null) {
				return false;
			}
		} else if (!local.equals(other.local)) {
			return false;
		}
		if (nome == null) {
			if (other.nome != null) {
				return false;
			}
		} else if (!nome.equals(other.nome)) {
			return false;
		}
		return true;
	}
	
	
    
}

 