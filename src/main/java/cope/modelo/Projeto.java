package cope.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tab_projeto")
public class Projeto implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_projeto")
	private Long id;
	private String titulo;
	private boolean fluxoContinuo;
	private Date dataInicio;
	private Date dataTermino;
	private String localExecucao;
	@ManyToOne
	private Texto resumoProposta;
	private String caracterizacaoProposta;
	private String areaTematica;
	private boolean acaoVinculadaPrograma;
	private Projeto programa;
	private String periodicidade;
	private String abrangencia;
	private String localRealizado;
	private boolean limite;
	private boolean inscricoes;
	private String publicoAlvo;
	private Integer qtdPessoasImpactadas;
	private Texto parcerias;
	private Texto referencialTeorico;
	private Texto justificativa;
	private Texto impactoTransformacao;
	private Texto objetivo;
	private Texto metodologia;
	// PROGRAMACAO
	private Texto recursoDisponivel;
	private Texto recursoNecessario;
	private Texto participacaoDiscente;
	private Texto bibliografica;
	private String grandeAreaConhecimento;
	private String areaConhecimento;
	private String areaConhecimentoCodigo;
	private String subAreaConhecimento;
	private String subAreaConhecimentoCodigo;
	private boolean comiteEtica;
	private boolean aprovacaoComiteEtica;
	private Texto funcamentacaoProposta;
	private Texto objetivosSeremAlcancados;
	private Texto recursos;
	private String recursosFinanceiros;
	private Integer situacao;

	public Projeto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo.trim();
	}

	public boolean isFluxoContinuo() {
		return fluxoContinuo;
	}

	public void setFluxoContinuo(boolean fluxoContinuo) {
		this.fluxoContinuo = fluxoContinuo;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}

	public String getLocalExecucao() {
		return localExecucao;
	}

	public void setLocalExecucao(String localExecucao) {
		this.localExecucao = localExecucao.trim();
	}

	public Texto getResumoProposta() {
		return resumoProposta;
	}

	public void setResumoProposta(Texto resumoProposta) {
		this.resumoProposta = resumoProposta;
	}

	public String getCaracterizacaoProposta() {
		return caracterizacaoProposta;
	}

	public void setCaracterizacaoProposta(String caracterizacaoProposta) {
		this.caracterizacaoProposta = caracterizacaoProposta.trim();
	}

	public String getAreaTematica() {
		return areaTematica;
	}

	public void setAreaTematica(String areaTematica) {
		this.areaTematica = areaTematica.trim();
	}

	public boolean isAcaoVinculadaPrograma() {
		return acaoVinculadaPrograma;
	}

	public void setAcaoVinculadaPrograma(boolean acaoVinculadaPrograma) {
		this.acaoVinculadaPrograma = acaoVinculadaPrograma;
	}

	public Projeto getPrograma() {
		return programa;
	}

	public void setPrograma(Projeto programa) {
		this.programa = programa;
	}

	public String getPeriodicidade() {
		return periodicidade;
	}

	public void setPeriodicidade(String periodicidade) {
		this.periodicidade = periodicidade.trim();
	}

	public String getAbrangencia() {
		return abrangencia;
	}

	public void setAbrangencia(String abrangencia) {
		this.abrangencia = abrangencia.trim();
	}

	public String getLocalRealizado() {
		return localRealizado;
	}

	public void setLocalRealizado(String localRealizado) {
		this.localRealizado = localRealizado.trim();
	}

	public boolean isLimite() {
		return limite;
	}

	public void setLimite(boolean limite) {
		this.limite = limite;
	}

	public boolean isInscricoes() {
		return inscricoes;
	}

	public void setInscricoes(boolean inscricoes) {
		this.inscricoes = inscricoes;
	}

	public String getPublicoAlvo() {
		return publicoAlvo;
	}

	public void setPublicoAlvo(String publicoAlvo) {
		this.publicoAlvo = publicoAlvo.trim();
	}

	public Integer getQtdPessoasImpactadas() {
		return qtdPessoasImpactadas;
	}

	public void setQtdPessoasImpactadas(Integer qtdPessoasImpactadas) {
		this.qtdPessoasImpactadas = qtdPessoasImpactadas;
	}

	public Texto getParcerias() {
		return parcerias;
	}

	public void setParcerias(Texto parcerias) {
		this.parcerias = parcerias;
	}

	public Texto getReferencialTeorico() {
		return referencialTeorico;
	}

	public void setReferencialTeorico(Texto referencialTeorico) {
		this.referencialTeorico = referencialTeorico;
	}

	public Texto getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(Texto justificativa) {
		this.justificativa = justificativa;
	}

	public Texto getImpactoTransformacao() {
		return impactoTransformacao;
	}

	public void setImpactoTransformacao(Texto impactoTransformacao) {
		this.impactoTransformacao = impactoTransformacao;
	}

	public Texto getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(Texto objetivo) {
		this.objetivo = objetivo;
	}

	public Texto getMetodologia() {
		return metodologia;
	}

	public void setMetodologia(Texto metodologia) {
		this.metodologia = metodologia;
	}

	public Texto getRecursoDisponivel() {
		return recursoDisponivel;
	}

	public void setRecursoDisponivel(Texto recursoDisponivel) {
		this.recursoDisponivel = recursoDisponivel;
	}

	public Texto getRecursoNecessario() {
		return recursoNecessario;
	}

	public void setRecursoNecessario(Texto recursoNecessario) {
		this.recursoNecessario = recursoNecessario;
	}

	public Texto getParticipacaoDiscente() {
		return participacaoDiscente;
	}

	public void setParticipacaoDiscente(Texto participacaoDiscente) {
		this.participacaoDiscente = participacaoDiscente;
	}

	public Texto getBibliografica() {
		return bibliografica;
	}

	public void setBibliografica(Texto bibliografica) {
		this.bibliografica = bibliografica;
	}

	public String getGrandeAreaConhecimento() {
		return grandeAreaConhecimento;
	}

	public void setGrandeAreaConhecimento(String grandeAreaConhecimento) {
		this.grandeAreaConhecimento = grandeAreaConhecimento.trim();
	}

	public String getAreaConhecimento() {
		return areaConhecimento;
	}

	public void setAreaConhecimento(String areaConhecimento) {
		this.areaConhecimento = areaConhecimento.trim();
	}

	public String getSubAreaConhecimento() {
		return subAreaConhecimento;
	}

	public void setSubAreaConhecimento(String subAreaConhecimento) {
		this.subAreaConhecimento = subAreaConhecimento.trim();
	}

	public boolean isComiteEtica() {
		return comiteEtica;
	}

	public void setComiteEtica(boolean comiteEtica) {
		this.comiteEtica = comiteEtica;
	}

	public boolean isAprovacaoComiteEtica() {
		return aprovacaoComiteEtica;
	}

	public void setAprovacaoComiteEtica(boolean aprovacaoComiteEtica) {
		this.aprovacaoComiteEtica = aprovacaoComiteEtica;
	}

	public Texto getFuncamentacaoProposta() {
		return funcamentacaoProposta;
	}

	public void setFuncamentacaoProposta(Texto funcamentacaoProposta) {
		this.funcamentacaoProposta = funcamentacaoProposta;
	}

	public Texto getObjetivosSeremAlcancados() {
		return objetivosSeremAlcancados;
	}

	public void setObjetivosSeremAlcancados(Texto objetivosSeremAlcancados) {
		this.objetivosSeremAlcancados = objetivosSeremAlcancados;
	}

	public Texto getRecursos() {
		return recursos;
	}

	public void setRecursos(Texto recursos) {
		this.recursos = recursos;
	}

	public String getRecursosFinanceiros() {
		return recursosFinanceiros;
	}

	public void setRecursosFinanceiros(String recursosFinanceiros) {
		this.recursosFinanceiros = recursosFinanceiros.trim();
	}

	public Integer getSituacao() {
		return situacao;
	}

	public void setSituacao(Integer situacao) {
		this.situacao = situacao;
	}

	public String getAreaConhecimentoCodigo() {
		return areaConhecimentoCodigo;
	}

	public void setAreaConhecimentoCodigo(String areaConhecimentoCodigo) {
		this.areaConhecimentoCodigo = areaConhecimentoCodigo;
	}

	public String getSubAreaConhecimentoCodigo() {
		return subAreaConhecimentoCodigo;
	}

	public void setSubAreaConhecimentoCodigo(String subAreaConhecimentoCodigo) {
		this.subAreaConhecimentoCodigo = subAreaConhecimentoCodigo;
	}
}
