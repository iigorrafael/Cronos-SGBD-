package ac.controle;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import util.CriptografiaSenha;
import util.EnviarEmail;
import util.GeradorSenhas;
import ac.modelo.Pessoa;
import dao.DAOGenerico;

@ViewScoped
@ManagedBean
public class RecuperSenhaLoginMB {

	private String email;
	private DAOGenerico dao;
	private List<Pessoa> lista;
	private String mensagem;
	
	public RecuperSenhaLoginMB() {
		dao = new DAOGenerico();
		lista = new ArrayList<>();
		mensagem = "";
		email = "";
	}
	public Boolean buscarEmail() {
		lista = dao.listaComStatus(Pessoa.class);
		return lista.stream().anyMatch(p -> p.getUsuario().equals(email));
	}

	public void recuperarSenhaLogin() {
		String senha;
		if (buscarEmail()) {
			senha = GeradorSenhas.gerarSenha();
			dao.updateSenha(CriptografiaSenha.criptografar(senha), email);
			if (EnviarEmail.enviarEmail(email, "Cronos, Recuperação de senha ",
					"Olá, " + "\n" + "Sua nova senha: " + senha)) {
				mensagem = "E-mail enviado";
			} else {
				mensagem = "Erros ao enviar e-mail";
			}
		} else {
			mensagem = "E-mail não encontrado";
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
