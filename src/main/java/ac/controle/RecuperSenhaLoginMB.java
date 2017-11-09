package ac.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import util.CriptografiaSenha;
import util.EnviarEmail;
import util.GeradorSenhas;
import ac.modelo.Pessoa;
import ac.services.PessoaService;
import dao.GenericDAO;

@ViewScoped
@Named("recuperSenhaLoginMB")
public class RecuperSenhaLoginMB implements Serializable{

	private static final long serialVersionUID = 1L;

	private String email;
	
	private List<Pessoa> lista;
	private String mensagem;
	
	@Inject
	private GenericDAO<Pessoa> daoPessoa;
	
	@Inject
	private PessoaService pessoaService;
	
	@PostConstruct
	public void inicializa() {
		lista = new ArrayList<>();
		mensagem = "";
		email = "";
	}
	public Boolean buscarEmail() {
		lista = daoPessoa.listaComStatus(Pessoa.class);
		return lista.stream().anyMatch(p -> p.getUsuario().equals(email));
	}

	public void recuperarSenhaLogin() {
		String senha;
		if (buscarEmail()) {
			senha = GeradorSenhas.gerarSenha();
			pessoaService.updateSenha(CriptografiaSenha.criptografar(senha), email);
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
