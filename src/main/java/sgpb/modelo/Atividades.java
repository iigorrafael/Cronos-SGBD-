package sgpb.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * @author igor
 *
 */
@Entity
@Table(name = "tab_atividades_evento")
public class Atividades implements Serializable {
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_atividades")
    private Long id;
    
    @Column(name = "data_autenticacao", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataAutenticacao;
    
    @NotNull(message = "O campo harario de inicio não pode ser nulo")
    @Column(name = "horario_inicio", nullable = false)
    @Temporal(TemporalType.TIME)
    private Date horarioInicio;
	
    @NotNull(message = "O campo horario final não pode ser nulo")
    @Column(name = "horario_fim", nullable = false)
    @Temporal(TemporalType.TIME)
    private Date horarioFim;
    
    @Column(name = "tolerancia_antes_inicio", nullable = false)
    private Integer toleranciaAntesInicio;
    
    @Column(name = "tolerancia_apos_inicio", nullable = false)
    private Integer toleranciaAposInicio;
    
    @Column(name = "tolerancia_fim")
    private Integer toleranciaFim;
 
    @Column(name = "autenticar_inicio", columnDefinition = "BOOLEAN")
    private boolean autenticarInicio;
    
    @Column(name = "autenticar_fim", columnDefinition = "BOOLEAN")
    private boolean autenticarFim;
    
    @NotNull(message = "O campo grupo não pode ser nulo")
    @Column(name = "descricao_atividades", nullable = false)
    private String descricao;
    
    @ManyToOne
    @JoinColumn(name="Evento_ID")
    private Evento evento;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataAutenticacao() {
		return dataAutenticacao;
	}

	public void setDataAutenticacao(Date dataAutenticacao) {
		this.dataAutenticacao = dataAutenticacao;
	}

	public Date getHorarioInicio() {
		return horarioInicio;
	}

	public void setHorarioInicio(Date horarioInicio) {
		this.horarioInicio = horarioInicio;
	}

	public Date getHorarioFim() {
		return horarioFim;
	}

	public void setHorarioFim(Date horarioFim) {
		this.horarioFim = horarioFim;
	}

	public Integer getToleranciaAntesInicio() {
		return toleranciaAntesInicio;
	}

	public void setToleranciaAntesInicio(Integer toleranciaAntesInicio) {
		this.toleranciaAntesInicio = toleranciaAntesInicio;
	}

	public Integer getToleranciaAposInicio() {
		return toleranciaAposInicio;
	}

	public void setToleranciaAposInicio(Integer toleranciaAposInicio) {
		this.toleranciaAposInicio = toleranciaAposInicio;
	}

	public Integer getToleranciaFim() {
		return toleranciaFim;
	}

	public void setToleranciaFim(Integer toleranciaFim) {
		this.toleranciaFim = toleranciaFim;
	}

	public boolean isAutenticarInicio() {
		return autenticarInicio;
	}

	public void setAutenticarInicio(boolean autenticarInicio) {
		this.autenticarInicio = autenticarInicio;
	}

	public boolean isAutenticarFim() {
		return autenticarFim;
	}

	public void setAutenticarFim(boolean autenticarFim) {
		this.autenticarFim = autenticarFim;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}
    
}
