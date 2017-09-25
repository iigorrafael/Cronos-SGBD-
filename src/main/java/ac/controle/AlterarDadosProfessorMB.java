package ac.controle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import util.CriptografiaSenha;
import util.ExibirMensagem;
import util.Mensagem;
import util.ValidacoesGerirUsuarios;

import base.modelo.Servidor;
import dao.DAOGenerico;

@SessionScoped
@ManagedBean
public class AlterarDadosProfessorMB {

	private Servidor professor;
	private DAOGenerico dao;
	private ValidacoesGerirUsuarios validacoesGerirUsuarios;
	private UsuarioSessaoMB usuarioSessao;
	private String senha;
	private String senha2;

	public AlterarDadosProfessorMB() {
		professor = new Servidor();
		dao = new DAOGenerico();
		validacoesGerirUsuarios = new ValidacoesGerirUsuarios();
		usuarioSessao = new UsuarioSessaoMB();
		senha = "";
		senha2 = "";
		preencherProfessor();
	}

	public void alterarProfessor() {
		try {
			if ((validacoesGerirUsuarios.buscarUsuarios(professor))
					&& (validacoesGerirUsuarios.buscarUsuarioAlterar(professor))) {
				ExibirMensagem.exibirMensagem(Mensagem.USUARIO);
			} else {
				if (senha != "") {
					professor.setSenha(senha);
					professor.setSenha(CriptografiaSenha.criptografar(professor.getSenha()));
				}
				dao.alterar(professor);
				ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
				preencherProfessor();
			}
		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
			System.err.println("Erro alterarProfessor");
			e.printStackTrace();
		}
	}

	public void preencherProfessor() {
		try {
			professor = (Servidor) usuarioSessao.recuperarProfessor();
		} catch (Exception e) {
			System.err.println("Erro preencherProfessor");
			e.printStackTrace();
		}
	}



	public Servidor getProfessor() {
		return professor;
	}

	public void setProfessor(Servidor professor) {
		this.professor = professor;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSenha2() {
		return senha2;
	}

	public void setSenha2(String senha2) {
		this.senha2 = senha2;
	}
}
