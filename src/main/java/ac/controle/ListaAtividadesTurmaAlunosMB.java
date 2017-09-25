package ac.controle;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ac.modelo.AlunoTurma;
import ac.modelo.AtividadeTurma;
import dao.DAOFiltros;

@ViewScoped
@ManagedBean
public class ListaAtividadesTurmaAlunosMB {
	
	private DAOFiltros daoFiltros;
	private UsuarioSessaoMB usuarioSessao;
	private AlunoTurma alunoTurma;
	


	public ListaAtividadesTurmaAlunosMB() {
		daoFiltros = new DAOFiltros();
		alunoTurma = new AlunoTurma();
	
		usuarioSessao = new UsuarioSessaoMB();

	}

	private Long recuperarTurmaAluno() {
		alunoTurma = (AlunoTurma) daoFiltros.buscarTurmaAluno(usuarioSessao.recuperarAluno().getId()).get(0);
		return alunoTurma.getTurma().getId();
	}
	
	public void preencherAtividadesTurmas(Long idTurma) {
	
	}

}
