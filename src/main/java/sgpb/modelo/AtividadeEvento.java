package sgpb.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "tab_atividades_evento")
public class AtividadeEvento implements Serializable {

	public AtividadeEvento() {
		super();
	}

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_atividade")
	private Long id;

	@Column(name = "data_atividade",nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataAtividade;
	
	@Column(name = "inicio_atividade",nullable = false)
	@Temporal(TemporalType.DATE)
	private Date horarioInicio;
	
	@Column(name = "fim_atividade",nullable = false)
	@Temporal(TemporalType.DATE)
	private Date horarioFim;
	
	@Column(name = "tempo_antes_inicio_atividade",nullable = false)
	private Integer tempoAntesInicio;
	
	@Column(name = "tempo_apos_inicio_atividade",nullable = false)
	private Integer tempoAposInicio;
	
	@Column(name = "tempo_antes_fim_atividade")
	private Integer tempoAntesFim;
	
	@Column(name = "autenticar_fim_atividade", columnDefinition = "BOOLEAN")
	private boolean autenticarFim;
	
	@Column(name = "descricao_atividade",nullable = false)
	private String descricao;
	
	@NotNull(message = "O campo evento não pode ser nulo")
	@JoinColumn(name = "id_evento_atividade", nullable = false)
	@ManyToOne
	private Evento evento;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the dataAtividade
	 */
	public Date getDataAtividade() {
		return dataAtividade;
	}

	/**
	 * @param dataAtividade the dataAtividade to set
	 */
	public void setDataAtividade(Date dataAtividade) {
		this.dataAtividade = dataAtividade;
	}

	/**
	 * @return the horarioInicio
	 */
	public Date getHorarioInicio() {
		return horarioInicio;
	}

	/**
	 * @param horarioInicio the horarioInicio to set
	 */
	public void setHorarioInicio(Date horarioInicio) {
		this.horarioInicio = horarioInicio;
	}

	/**
	 * @return the horarioFim
	 */
	public Date getHorarioFim() {
		return horarioFim;
	}

	/**
	 * @param horarioFim the horarioFim to set
	 */
	public void setHorarioFim(Date horarioFim) {
		this.horarioFim = horarioFim;
	}

	/**
	 * @return the tempoAntesInicio
	 */
	public Integer getTempoAntesInicio() {
		return tempoAntesInicio;
	}

	/**
	 * @param tempoAntesInicio the tempoAntesInicio to set
	 */
	public void setTempoAntesInicio(Integer tempoAntesInicio) {
		this.tempoAntesInicio = tempoAntesInicio;
	}

	/**
	 * @return the tempoAposInicio
	 */
	public Integer getTempoAposInicio() {
		return tempoAposInicio;
	}

	/**
	 * @param tempoAposInicio the tempoAposInicio to set
	 */
	public void setTempoAposInicio(Integer tempoAposInicio) {
		this.tempoAposInicio = tempoAposInicio;
	}

	/**
	 * @return the tempoAntesFim
	 */
	public Integer getTempoAntesFim() {
		return tempoAntesFim;
	}

	/**
	 * @param tempoAntesFim the tempoAntesFim to set
	 */
	public void setTempoAntesFim(Integer tempoAntesFim) {
		this.tempoAntesFim = tempoAntesFim;
	}

	/**
	 * @return the autenticarFim
	 */
	public boolean isAutenticarFim() {
		return autenticarFim;
	}

	/**
	 * @param autenticarFim the autenticarFim to set
	 */
	public void setAutenticarFim(boolean autenticarFim) {
		this.autenticarFim = autenticarFim;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the evento
	 */
	public Evento getEvento() {
		return evento;
	}

	/**
	 * @param evento the evento to set
	 */
	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	


}
