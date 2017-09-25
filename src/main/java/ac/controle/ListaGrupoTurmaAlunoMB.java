package ac.controle;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ac.modelo.AlunoTurma;
import ac.modelo.AtividadeTurma;
import ac.modelo.GrupoTurma;
import base.modelo.Aluno;
import base.modelo.Tipo;
import base.modelo.Turma;
import dao.DAOFiltros;
import dao.DAOGenerico;

@ViewScoped
@ManagedBean
public class ListaGrupoTurmaAlunoMB {

	private List<AtividadeTurma> atividadesTurmas;
	private List<GrupoTurma> gruposTurmas;
	private DAOFiltros daoFiltros;
	private UsuarioSessaoMB usuarioSessao;
	private AlunoTurma alunoTurma;
	private List<AlunoTurma> listAlunoTurmas;
	private GrupoTurma grupoTurma;
	private AtividadeTurma atividadeTurma;

	private DAOGenerico dao ;
	private AlunoTurma alunoTurmaSelecionada;
	
	public ListaGrupoTurmaAlunoMB() {
		daoFiltros = new DAOFiltros();
		alunoTurma = new AlunoTurma();
		atividadesTurmas = new ArrayList<>();
		gruposTurmas = new ArrayList<>();
		usuarioSessao = new UsuarioSessaoMB();
		grupoTurma = new GrupoTurma();
		listAlunoTurmas = new ArrayList<>();
		alunoTurmaSelecionada = new AlunoTurma();
		atividadeTurma = new AtividadeTurma();
		dao = new DAOGenerico();
	
	}

	private Long recuperarTurmaAluno() {
		alunoTurma = (AlunoTurma) daoFiltros.buscarTurmaAluno(usuarioSessao.recuperarAluno().getId()).get(0);
		return alunoTurma.getId();
	}

	public void preencherGruposTurmas() {
		ListaAtividadesTurmaAlunosMB atividade = new ListaAtividadesTurmaAlunosMB();
		
		gruposTurmas = daoFiltros.gruposTurmaAluno(alunoTurmaSelecionada.getTurma().getId());	
		atividadesTurmas = daoFiltros.atividadesTurmaAluno(alunoTurmaSelecionada.getTurma().getId());

	}
	
	public List<GrupoTurma> getGruposTurmas() {
		return gruposTurmas;
	}
	public void setGruposTurmas(List<GrupoTurma> gruposTurmas) {
		this.gruposTurmas = gruposTurmas;
	}
	public GrupoTurma getGrupoTurma() {
		return grupoTurma;
	}
	public void setGrupoTurma(GrupoTurma grupoTurma) {
		this.grupoTurma = grupoTurma;
	}

	public AlunoTurma getAlunoTurmaSelecionada() {
		return alunoTurmaSelecionada;
	}

	public void setAlunoTurmaSelecionada(AlunoTurma alunoTurmaSelecionada) {
		this.alunoTurmaSelecionada = alunoTurmaSelecionada;
	}

	public List<AlunoTurma> getListAlunoTurmas() {
		
		Aluno  aluno = (Aluno) dao.buscarPorId(Aluno.class, usuarioSessao.recuperarAluno().getId());
		listAlunoTurmas = dao.listar(AlunoTurma.class, " controle = 1 and aluno  = "+aluno.getId());
		
		return listAlunoTurmas;
	} 

	public void setListAlunoTurmas(List<AlunoTurma> listAlunoTurmas) {
		this.listAlunoTurmas = listAlunoTurmas;
	}

	public List<AtividadeTurma> getAtividadesTurmas() {
		return atividadesTurmas;
	}

	public void setAtividadesTurmas(List<AtividadeTurma> atividadesTurmas) {
		this.atividadesTurmas = atividadesTurmas;
	}

	public AtividadeTurma getAtividadeTurma() {
		return atividadeTurma;
	}

	public void setAtividadeTurma(AtividadeTurma atividadeTurma) {
		this.atividadeTurma = atividadeTurma;
	}

	
	
}
