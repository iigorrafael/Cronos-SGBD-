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
public class AlterarDadosSecretariaMB {

	private Servidor secretaria;
	private DAOGenerico dao;
	private ValidacoesGerirUsuarios validacoesGerirUsuarios;
	private UsuarioSessaoMB usuarioSessao;
	private String senha;
	private String senha2;

	public AlterarDadosSecretariaMB() {
		secretaria = new Servidor();
		dao = new DAOGenerico();
		validacoesGerirUsuarios = new ValidacoesGerirUsuarios();
		usuarioSessao = new UsuarioSessaoMB();
		senha = "";
		senha2 = "";
		preencherSecretaria();
	}

	public void alterarSecretaria() {
		try {
			if ((validacoesGerirUsuarios.buscarUsuarios(secretaria))
					&& (validacoesGerirUsuarios.buscarUsuarioAlterar(secretaria))) {
				ExibirMensagem.exibirMensagem(Mensagem.USUARIO);
			} else {
				if (senha != "") {
					secretaria.setSenha(senha);
					secretaria.setSenha(CriptografiaSenha.criptografar(secretaria.getSenha()));
				}
				dao.alterar(secretaria);
				ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
				preencherSecretaria();

			}
		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
			System.err.println("Erro alterarSecretaria");
			e.printStackTrace();
		}
	}

	public void preencherSecretaria() {
		secretaria = (Servidor) usuarioSessao.recuperarSecretaria();
	}



	public Servidor getSecretaria() {
		return secretaria;
	}

	public void setSecretaria(Servidor secretaria) {
		this.secretaria = secretaria;
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
