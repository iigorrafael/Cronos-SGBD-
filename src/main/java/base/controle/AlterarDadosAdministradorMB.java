package base.controle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import util.CriptografiaSenha;
import util.ExibirMensagem;
import util.Mensagem;
import util.ValidacoesGerirUsuarios;
import ac.controle.UsuarioSessaoMB;

import base.modelo.Servidor;
import dao.DAOGenerico;

@ViewScoped
@ManagedBean
public class AlterarDadosAdministradorMB {

	private Servidor administrador;
	private DAOGenerico dao;
	private ValidacoesGerirUsuarios validacoesGerirUsuarios;
	private UsuarioSessaoMB usuarioSessao;
	private String senha;
	private String senha2;

	public AlterarDadosAdministradorMB() {
		administrador = new Servidor();
		dao = new DAOGenerico();
		validacoesGerirUsuarios = new ValidacoesGerirUsuarios();
		usuarioSessao = new UsuarioSessaoMB();
		senha = "";
		senha2 = "";
		preencherAdministrador();
	}

	public void alterarAdministrador() {
		try {
			if ((validacoesGerirUsuarios.buscarUsuarios(administrador))
					&& (validacoesGerirUsuarios.buscarUsuarioAlterar(administrador))) {
				ExibirMensagem.exibirMensagem(Mensagem.USUARIO);
			} else {
				if (senha != "") {
					administrador.setSenha(senha);
					administrador.setSenha(CriptografiaSenha.criptografar(administrador.getSenha()));
				}

				dao.alterar(administrador);
				ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
				preencherAdministrador();
			}
		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
			System.err.println("Erro alterarAdministrador");
			e.printStackTrace();
		}
	}

	public void preencherAdministrador() {
		try {
			usuarioSessao = new UsuarioSessaoMB();
			administrador = (Servidor) usuarioSessao.recuperarServidor();
		} catch (Exception e) {
			System.err.println("preencherAdministrador");
			e.printStackTrace();
		}
	}


	

	public Servidor getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Servidor administrador) {
		this.administrador = administrador;
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
