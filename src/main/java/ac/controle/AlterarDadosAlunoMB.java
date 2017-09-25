package ac.controle;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import util.CriptografiaSenha;
import util.ExibirMensagem;
import util.Mensagem;
import util.ValidacoesGerirUsuarios;
import ac.modelo.AlunoTurma;
import base.modelo.Aluno;
import dao.DAOGenerico;

@SessionScoped
@ManagedBean
public class AlterarDadosAlunoMB {

	private base.modelo.Aluno aluno;
	private DAOGenerico dao;
	private ValidacoesGerirUsuarios validacoesGerirUsuarios;
	private UsuarioSessaoMB usuarioSessao;
	private String senha;
	private String senha2;
	private List<AlunoTurma> alunoTurmaAluno;
	private AlunoTurma retornaAlunoTurma;

	public AlterarDadosAlunoMB() {
		aluno = new Aluno();
		dao = new DAOGenerico();
		validacoesGerirUsuarios = new ValidacoesGerirUsuarios();
		usuarioSessao = new UsuarioSessaoMB();
		senha = "";
		senha2 = "";
		alunoTurmaAluno = new ArrayList<>();
		retornaAlunoTurma = new AlunoTurma();
		preencherAluno();
	}

	public void alterarAluno() {
		try {
			aluno.setUsuario(aluno.getUsuario().replace("'", "").replace("=", "").replace("<", "").replace("&", ""));
			if ((validacoesGerirUsuarios.buscarUsuarios(aluno))
					&& (validacoesGerirUsuarios.buscarUsuarioAlterar(aluno))) {
				ExibirMensagem.exibirMensagem(Mensagem.USUARIO);
			} else {
				if (senha != "") {
					aluno.setSenha(senha);
					aluno.setSenha(CriptografiaSenha.criptografar(aluno.getSenha()));
				}
				dao.alterar(aluno);
				ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
				preencherAluno();
			}
		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
			System.err.println("Erro alterarAluno");
			e.printStackTrace();
		}
	}

	public void preencherAluno() {
		try {
			aluno = (Aluno) usuarioSessao.recuperarAluno();
			
			
			alunoTurmaAluno = dao.listar(AlunoTurma.class, " aluno = " + aluno.getId());
			for(AlunoTurma a : alunoTurmaAluno){

				retornaAlunoTurma = (AlunoTurma) dao.buscarPorId(AlunoTurma.class, a.getId());
				
			}
		} catch (Exception e) {
			System.err.println("Erro preencherAluno");
			e.printStackTrace();
		}
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}



	public List<AlunoTurma> getAlunoTurmaAluno() {
		return alunoTurmaAluno;
	}

	public void setAlunoTurmaAluno(List<AlunoTurma> alunoTurmaAluno) {
		this.alunoTurmaAluno = alunoTurmaAluno;
	}

	public AlunoTurma getRetornaAlunoTurma() {
		return retornaAlunoTurma;
	}

	public void setRetornaAlunoTurma(AlunoTurma retornaAlunoTurma) {
		this.retornaAlunoTurma = retornaAlunoTurma;
	}

	public String getSenha2() {
		return senha2;
	}

	public void setSenha2(String senha2) {
		this.senha2 = senha2;
	}
}
