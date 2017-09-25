package ac.controle;

import ac.modelo.Pessoa;


import base.modelo.Aluno;
import base.modelo.Servidor;
import dao.DAOUsuario;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

@SessionScoped
@ManagedBean
public class UsuarioSessaoMB {
	private Pessoa pessoa;
	private Servidor servidor;
	private Aluno usuarioAluno;
	private Servidor secretaria;
	private Servidor administrador;

	private DAOUsuario daoUsuario;

	public UsuarioSessaoMB() {
		pessoa = new Pessoa();
		SecurityContext context = SecurityContextHolder.getContext();
		if (context instanceof SecurityContext) {
			Authentication authentication = context.getAuthentication();
			if (authentication instanceof Authentication) {
				pessoa.setUsuario(((User) authentication.getPrincipal()).getUsername());
			}
		}
	}

	public Pessoa recuperarAluno() {
		usuarioAluno = new Aluno();
		daoUsuario = new DAOUsuario();
		try {
			usuarioAluno = (Aluno) daoUsuario.retornarLogado(Aluno.class, pessoa.getUsuario()).get(0);
		} catch (Exception e) {
		}
		return usuarioAluno;
	}
	
	public Pessoa recuperarServidor() {
		servidor = new Servidor();
		daoUsuario = new DAOUsuario();
		try {
			servidor = (Servidor) daoUsuario.retornarLogado(Servidor.class, pessoa.getUsuario()).get(0);
		} catch (Exception e) {
		}
		return servidor;
	}	
	
	
	
	public Servidor recuperarCadastroServidor() {
	
		servidor = new Servidor();
		daoUsuario = new DAOUsuario();
		try {
			servidor = (Servidor) daoUsuario.retornarLogado(Servidor.class, pessoa.getUsuario()).get(0);
		} catch (Exception e) {
		}
		return servidor;
	}
	
	

	public Servidor recuperarSecretaria() {

		
		secretaria = new Servidor();
		daoUsuario = new DAOUsuario();
		try {
			secretaria = (Servidor) daoUsuario.retornarLogado(Servidor.class, pessoa.getUsuario()).get(0);
			
		} catch (Exception e) {
		}
		return secretaria;
		
	}

	public Servidor recuperarProfessor() { //public Professor recuperarProfessor() {
		
		servidor = new Servidor();// professor = new Professor();
		daoUsuario = new DAOUsuario();
		try {
			servidor = (Servidor) daoUsuario.retornarLogado(Servidor.class, pessoa.getUsuario()).get(0);
		} catch (Exception e) {
		}
		return servidor;
	}

	public Servidor recuperarAdmisnitrador() {


		administrador = new Servidor();
		daoUsuario = new DAOUsuario();
		try {
			administrador = (Servidor) daoUsuario.retornarLogado(Servidor.class, pessoa.getUsuario()).get(0);
		} catch (Exception e) {
		}
		return servidor;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

}
