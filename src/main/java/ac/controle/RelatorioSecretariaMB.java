package ac.controle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import util.ChamarRelatorio;
import util.ExibirMensagem;
import util.Mensagem;
import util.RecuperarRelacoesProfessor;
import util.VerificaSituacaoTurma;
import ac.modelo.AlunoTurma;
import ac.modelo.Certificado;
import ac.modelo.Movimentacao;
import base.modelo.Turma;
import dao.DAOGenerico;
import dao.DAOMovimentacaoAluno;

@ViewScoped
@ManagedBean
public class RelatorioSecretariaMB {
	private DAOGenerico dao;
	private Turma turma;
	private DAOMovimentacaoAluno daoMovimentacaoAluno;
	private List<Turma> turmas;

	public RelatorioSecretariaMB() {
		dao = new DAOGenerico();
		daoMovimentacaoAluno = new DAOMovimentacaoAluno();
		turma = new Turma();
		preencherListaTurma();
		
		
	}

	public void imprimirCertificadoSituacao() {
		VerificaSituacaoTurma verificaSituacaoTurma = new VerificaSituacaoTurma();
		try {
			verificaSituacaoTurma.recuperarCertificados(turma);
			List<AlunoTurma> alunoTurmas = dao.listar(AlunoTurma.class, " aluno.id is not null ");
			if (!alunoTurmas.isEmpty()) {

				ChamarRelatorio ch = new ChamarRelatorio();
				HashMap parametro = new HashMap<>();
				parametro.put("TURMA", getTurma().getId());

				ch.imprimeRelatorio("situacao.jasper", parametro,
						"Relatório de situação da turma " + getTurma().getDescricao());
			} else {
				ExibirMensagem.exibirMensagem(Mensagem.NADA_ENCONTRADO);
			}
		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
		}
	}

	public void preencherListaTurma() {
		turmas = dao.listaComStatus(Turma.class);
	}

	public List<Turma> completarTurma(String str) {
		preencherListaTurma();
		List<Turma> turmasSelecionadas = new ArrayList<>();
		for (Turma t : turmas) {
			if (t.getDescricao().startsWith(str)) {
				turmasSelecionadas.add(t);
			}
		}
		return turmasSelecionadas;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}
}
