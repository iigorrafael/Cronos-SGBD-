package ac.controle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.HashMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import ac.modelo.AlunoTurma;
import ac.modelo.Certificado;
import ac.modelo.GrupoTurma;
import base.modelo.Aluno;
import dao.DAOFiltros;
import dao.DAOGenerico;
import util.ChamarRelatorio;
import util.ExibirMensagem;
import util.Mensagem;

@ViewScoped
@ManagedBean
public class GraficoCertificadoAlunoMB {

	private AlunoTurma alunoTurmaSelecionada;
	private List<AlunoTurma> listAlunoTurmas;

	private BarChartModel grafico;
	private AlunoTurma alunoTurma;
	private DAOFiltros daoFiltros;
	private UsuarioSessaoMB usuarioSessao;
	private List<Certificado> certificados;
	private List<Long> idGrupos;
	private Set<Long> idGruposSelecionados;
	private DAOGenerico dao;
	private ChartSeries totalGrupo;
	private ChartSeries totalCertificado;
	private List<Long> gruposTurmas;

	public GraficoCertificadoAlunoMB() {

		alunoTurmaSelecionada = new AlunoTurma();
		listAlunoTurmas = new ArrayList<>();

		grafico = new BarChartModel();
		alunoTurma = new AlunoTurma();
		daoFiltros = new DAOFiltros();
		usuarioSessao = new UsuarioSessaoMB();
		certificados = new ArrayList<>();
		idGrupos = new ArrayList<>();
		idGruposSelecionados = new HashSet<>();
		dao = new DAOGenerico();
		totalGrupo = new ChartSeries();
		totalCertificado = new ChartSeries();
		gruposTurmas = new ArrayList<>();
		calcularHoras();
	}

	public void limpa() {

		grafico.clear();
		calcularHoras();

	}

	public Long recuperarTurmaAluno() {
		alunoTurma = (AlunoTurma) daoFiltros.buscarTurmaAluno(usuarioSessao.recuperarAluno().getId()).get(0);
		return alunoTurma.getTurma().getId();
	}

	public void recuperarCertificados() {

		if (alunoTurmaSelecionada.getId() == null) {

			certificados = daoFiltros.certificadosAlunos(usuarioSessao.recuperarAluno().getId(), 3);
		} else {

			certificados = daoFiltros.certificadosAlunosImprimir(alunoTurmaSelecionada.getId(), 3);
		}
	}

	public void ordenarIdGrupoTurma() {
		recuperarCertificados();
		idGrupos.clear();
		for (Certificado c : certificados) {
			idGrupos.add(c.getIdGrupoTurma());
		}
		Collections.sort(idGrupos);

	}

	public void escolherGruposTurmas() {
		ordenarIdGrupoTurma();
		idGruposSelecionados.clear();
		for (Long i : idGrupos) {
			idGruposSelecionados.add(i);
		}

	}

	public void calcularHoras() {
		Double somaCertificados = 0.0;
		escolherGruposTurmas();
		GrupoTurma grupoTurma = new GrupoTurma();
		GrupoTurma auxGrupoTurma = new GrupoTurma();

		gruposTurmas = new ArrayList<>();
		gruposTurmas.addAll(idGruposSelecionados);
		certificados = new ArrayList<>();
		for (int i = 0; i <= gruposTurmas.size() - 1; i++) {
			if (alunoTurmaSelecionada.getId() == null) {

				certificados = dao.listar(Certificado.class, " situacao = 3 and aluno = "
						+ usuarioSessao.recuperarAluno().getId() + " and idGrupoTurma = " + gruposTurmas.get(i));
			} else {
				certificados = dao.listar(Certificado.class, " situacao = 3 and alunoTurma = "
						+ alunoTurmaSelecionada.getId() + " and idGrupoTurma = " + gruposTurmas.get(i));
			}
			for (Certificado c : certificados) {
				somaCertificados = somaCertificados + c.getHoraComputada();
				grupoTurma = (GrupoTurma) dao.listar(GrupoTurma.class, " id = " + c.getIdGrupoTurma()).get(0);
			}
			if (somaCertificados > 0.0) {
				criarGrafico(somaCertificados, grupoTurma);
				somaCertificados = 0.0;
			}
			grupoTurma = new GrupoTurma();
			certificados = new ArrayList<>();
		}
	}

	public void criarGrafico(Double somaCertificado, GrupoTurma grupoTurma) {
		totalGrupo.setLabel("Horas do Grupo " + grupoTurma.getGrupo().getDescricao());
		totalGrupo.set("Grupos", grupoTurma.getMaximoHoras());

		totalCertificado.setLabel("Horas dos Certificados " + grupoTurma.getGrupo().getDescricao());
		totalCertificado.set("Grupos", somaCertificado);

		grafico.addSeries(totalGrupo);
		grafico.addSeries(totalCertificado);

		grafico.setLegendPosition("ne");
		grafico.setAnimate(true);

		Axis yAxis = grafico.getAxis(AxisType.Y);
		yAxis.setLabel("Horas");

		totalGrupo = new ChartSeries();
		totalCertificado = new ChartSeries();

	}

	public void imprimirCertificadoGrupo() {

		try {

			if (alunoTurmaSelecionada.getId() == null) {
				ExibirMensagem.exibirMensagem(Mensagem.TURMAGRUPO);
			} else {

				List<Certificado> certificados = dao.listar(Certificado.class,
						" alunoTurma = " + alunoTurmaSelecionada.getId() + " and atividadeTurma.turma = "
								+ alunoTurmaSelecionada.getTurma().getId() + " and situacao = 3");

				if (!certificados.isEmpty()) {
					Certificado cs = certificados.get(0);

					ChamarRelatorio ch = new ChamarRelatorio();
					HashMap parametro = new HashMap<>();
					parametro.put("ALUNOTURMA", alunoTurmaSelecionada.getId());

					ch.imprimeRelatorio("grupoTurmaAlunoOutro2.jasper", parametro, "Relatório situação nos grupos");
				} else {
					ExibirMensagem.exibirMensagem(Mensagem.NADA_ENCONTRADO);
				}

			}
		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
		}
	}

	public BarChartModel getGrafico() {
		return grafico;
	}

	public void setGrafico(BarChartModel grafico) {
		this.grafico = grafico;
	}

	public AlunoTurma getAlunoTurmaSelecionada() {
		return alunoTurmaSelecionada;
	}

	public void setAlunoTurmaSelecionada(AlunoTurma alunoTurmaSelecionada) {
		this.alunoTurmaSelecionada = alunoTurmaSelecionada;
	}

	public List<AlunoTurma> getListAlunoTurmas() {

		Aluno aluno = (Aluno) dao.buscarPorId(Aluno.class, usuarioSessao.recuperarAluno().getId());
		listAlunoTurmas = dao.listar(AlunoTurma.class, " controle = 1 and aluno  = " + aluno.getId());

		return listAlunoTurmas;
	}

	public void setListAlunoTurmas(List<AlunoTurma> listAlunoTurmas) {
		this.listAlunoTurmas = listAlunoTurmas;
	}

}