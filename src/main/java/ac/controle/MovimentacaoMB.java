package ac.controle;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import util.RecuperarRelacoesProfessor;
import ac.modelo.AlunoTurma;
import ac.modelo.Movimentacao;
import base.modelo.Aluno;
import base.modelo.Servidor;
import dao.DAOGenerico;

@ViewScoped
@ManagedBean
public class MovimentacaoMB {

	private List<Movimentacao> movimentacaosSecretaria;
	private List<Movimentacao> movimentacaosAluno;
	private List<Movimentacao> movimentacaosProfessor;
	private UsuarioSessaoMB usuarioSessao;
	private Aluno aluno;
	private DAOGenerico dao;

	public MovimentacaoMB() {
		movimentacaosSecretaria = new ArrayList<>();
		movimentacaosAluno = new ArrayList<>();
		movimentacaosProfessor = new ArrayList<>();
		usuarioSessao = new UsuarioSessaoMB();
		dao = new DAOGenerico();
	}

	public void preencherMovimentacaoSecretaria() {
		movimentacaosSecretaria = dao.listaComStatus(Movimentacao.class);
	}

	public void preencherMovimentacaoAluno() {
		aluno = new Aluno();
		aluno = (Aluno) usuarioSessao.recuperarAluno();
		movimentacaosAluno = dao.listar(Movimentacao.class, " alunoTurma.aluno = " + aluno.getId());
		
	
	}

	public void preencherMovimentacaoProfessor() {		
		RecuperarRelacoesProfessor recuperarRelacoesProfessor = new RecuperarRelacoesProfessor();
		movimentacaosProfessor = new ArrayList<>();
		movimentacaosProfessor = recuperarRelacoesProfessor.recuperarTodasMovimentacoesAtivas();
		
		
	}

	public List<Movimentacao> getMovimentacaosSecretaria() {
		return movimentacaosSecretaria;
	}

	public void setMovimentacaosSecretaria(List<Movimentacao> movimentacaosSecretaria) {
		this.movimentacaosSecretaria = movimentacaosSecretaria;
	}

	public List<Movimentacao> getMovimentacaosAluno() {
		return movimentacaosAluno;
	}

	public void setMovimentacaosAluno(List<Movimentacao> movimentacaosAluno) {
		this.movimentacaosAluno = movimentacaosAluno;
	}

	public List<Movimentacao> getMovimentacaosProfessor() {
		return movimentacaosProfessor;
	}

	public void setMovimentacaosProfessor(List<Movimentacao> movimentacaosProfessor) {
		this.movimentacaosProfessor = movimentacaosProfessor;
	}

}
