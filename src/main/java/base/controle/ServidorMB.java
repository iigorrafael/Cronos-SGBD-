package base.controle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import util.CriptografiaSenha;
import util.ExibirMensagem;
import util.FecharDialog;
import util.Mensagem;
import util.ValidacoesGerirUsuarios;
import ac.controle.UsuarioSessaoMB;
import ac.modelo.Permissao;
import ac.modelo.Pessoa;
import ac.modelo.ProfessorCurso;
import base.modelo.Curso;
import base.modelo.Servidor;
import base.modelo.Tipo;
import dao.DAOGenerico;
import dao.DAOUsuario;

@ManagedBean

@SessionScoped
public class ServidorMB {

	private DAOGenerico dao;
	private List<Tipo> listTipoSelecionado;
	private List<Tipo> tipoSalvar;
	private Tipo permissaoAdicionada;
	private UsuarioSessaoMB usuarioSessao;
	private List<Curso> listCursoSelecionado;
	private List<Curso> listCursoCompletar;
	private Curso cursoAdicionado;
	private List<Curso> listCursoSalvar;
	private List<Servidor> listServidor;
	private boolean controle = true;
	private Servidor servidor;
	private Permissao permissao;
	private ProfessorCurso professorCurso;
	private boolean controleDesativar = true;
	private List<ProfessorCurso> professorCursoRemove;
	private List<Permissao> permissaoRemove;
	private Permissao removerPermissao;
	private Tipo permissaoParaRemove;
	private List<Permissao> permissaoRemoveSelecionada;
	private List<ProfessorCurso> professorCursoSelecionado;
	private ValidacoesGerirUsuarios validacoesGerirUsuarios;
	private DAOUsuario daoUsuarios;
	private boolean controleRadio = true;
	int flag = 0;
	private boolean controleSenha;
	private boolean controleAddTipo = true;
	private boolean controleAddCurso = true;

	public ServidorMB() {

		professorCursoSelecionado = new ArrayList<>();
		dao = new DAOGenerico();
		listTipoSelecionado = new ArrayList<>();
		tipoSalvar = new ArrayList<>();
		servidor = new Servidor();
		permissao = new Permissao();
		professorCurso = new ProfessorCurso();
		listServidor = new ArrayList<>();
		professorCursoRemove = new ArrayList<>();
		validacoesGerirUsuarios = new ValidacoesGerirUsuarios();
		usuarioSessao = new UsuarioSessaoMB();
		permissaoRemove = new ArrayList<>();
		permissaoRemoveSelecionada = new ArrayList<>();
		daoUsuarios = new DAOUsuario();
		listCursoSelecionado = new ArrayList<>();
		listCursoSalvar = new ArrayList<>();
		permissaoAdicionada = new Tipo();
		cursoAdicionado = new Curso();
		listCursoCompletar = new ArrayList<>();
		permissaoParaRemove = new Tipo();
		removerPermissao = new Permissao();

	}

	public void preencherListaServidor(Servidor servid) {
		this.servidor = new Servidor();
		this.servidor = servid;
		controleSenha = false; 
		controleAddTipo = true;
		controleRadio = true;

		tipoSalvar.clear();
		for (Permissao p : daoUsuarios.buscarPermissaoServidor(" status = true and servidor = " + servidor.getId())) {
			tipoSalvar.add(p.getTipo());
		}
		for (Tipo t : tipoSalvar) {

			if (t.getDescricao().equalsIgnoreCase("professor")) {
				controle = false;
				controleAddCurso = false;
				listCursoSelecionado.clear();
				for (ProfessorCurso c : daoUsuarios
						.buscarCursosProfessor(" status = true and professor = " + servidor.getId())) {
					listCursoSelecionado.add(c.getCurso());
				}
			} else {
				controleAddCurso = true;
				controle = true;
				listCursoSelecionado = new ArrayList<>();
			}
			
			if(t.getDescricao().equalsIgnoreCase("secretaria")){
				controleRadio = false;
			}
		}

	}
	
	
	public void controle(){
		controleAddTipo = false;
		
	}
	public void cancelar(){
		controleAddTipo = true;
		FecharDialog.fecharDialogServidor();
	}

	public void adicionaPermissao() {
		
		
		permissaoAdicionada = (Tipo) dao.buscarPorId(Tipo.class, permissao.getTipo().getId());

		if (tipoSalvar.size() == 0) {
			tipoSalvar.add(permissaoAdicionada);
			
			controlar();
			controleChefe();
		} else {

			if (validarRelaciomanetoPermissao(permissaoAdicionada)) {
				tipoSalvar.add(permissaoAdicionada);
			} else {
				ExibirMensagem.exibirMensagem(Mensagem.PERMISSAOADICIONADA);
			}
		
		controlar();
		controleChefe();
		}
	}

	public void adicionaProfessorCurso() {
		
		cursoAdicionado = (Curso) dao.buscarPorId(Curso.class, professorCurso.getCurso().getId());

		if (listCursoSelecionado.size() == 0) {
			listCursoSelecionado.add(cursoAdicionado);
		} else {

			if (validarRelaciomanetoCurso(cursoAdicionado)) {
				listCursoSelecionado.add(cursoAdicionado);
			} else {
				ExibirMensagem.exibirMensagem(Mensagem.CURSOJAADICIONADO);
			}

		}
	}

	public boolean validarRelaciomanetoPermissao(Tipo tipo) {

		for (Tipo tt : tipoSalvar) {
			if (tt.getDescricao().equals(tipo.getDescricao())) {
				return false;
			}
		}

		return true;
	}

	public boolean validarRelaciomanetoCurso(Curso curso) {

		for (Curso tt : listCursoSelecionado) {
			if (tt.getNome().equals(curso.getNome())) {
				return false;
			}
		}
		return true;
	}

	public void removerPermissao(Tipo tipo) {
		if (servidor.getId() == null) {
			permissaoAdicionada = (Tipo) dao.buscarPorId(Tipo.class, tipo.getId());
			tipoSalvar.remove(permissaoAdicionada);
			controlar();
			controleChefe();
			
		} else {
			
		
			permissaoParaRemove = (Tipo) dao.buscarPorId(Tipo.class, tipo.getId());
			permissaoRemove = dao.listar(Permissao.class,
					" servidor = " + servidor.getId() + " and tipo = " + permissaoParaRemove.getId());

			if (permissaoRemove.size() > 0) {

				for (Permissao p : permissaoRemove) {
					p.setStatus(false);
					dao.alterar(p);
				}

				int flag = 1;
				ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
				preencherListaServidor(servidor);

			} else {
				
				
				tipoSalvar.remove(permissaoParaRemove);
			}

		}

	}

	public void removerCurso(Curso curso) {

		if (servidor.getId() == null) {
			cursoAdicionado = (Curso) dao.buscarPorId(Curso.class, curso.getId());
			listCursoSelecionado.remove(cursoAdicionado);
		} else {

			List<ProfessorCurso> cursoRemov = new ArrayList<>();

			cursoRemov = dao.listar(ProfessorCurso.class,
					" professor = " + servidor.getId() + " and status = true and curso = " + curso.getId());

			for (ProfessorCurso p : cursoRemov) {
				p.setStatus(false);
				dao.alterar(p);
			}

			int flag = 1;
			ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
			preencherListaServidor(servidor);

		}
	}

	public List<Tipo> completaPermissao(String str) {
		listTipoSelecionado = dao.listaComStatus(Tipo.class);
		List<Tipo> listTipos = new ArrayList<>();
		for (Tipo cur : listTipoSelecionado) {
			if (cur.getDescricao().toLowerCase().startsWith(str)) {
				listTipos.add(cur);
			}
		}
		return listTipos;
	}

	public List<Curso> completaCurso(String str) {
		listCursoCompletar = dao.listaComStatus(Curso.class);
		List<Curso> listTipos = new ArrayList<>();
		for (Curso cur : listCursoCompletar) {
			if (cur.getNome().toLowerCase().startsWith(str)) {
				listTipos.add(cur);
			}
		}
		return listTipos;
	}

	public void controlar() {

		for (Tipo l : tipoSalvar) {
			
		

			if (l.getDescricao().equals("professor") || l.getDescricao().equals("professores")) {
				controle = false;
				controleAddCurso = false;

				
				break;
				
			} else {

				controle = true;
				controleAddCurso = true;

				listCursoSelecionado = new ArrayList<>();

			}

		}
	}

	public void controleChefe() {
		for (Tipo l : tipoSalvar) {
			if (l.getDescricao().equals("secretaria")) {
				controleRadio = false;
				break;
			} else {
				controleRadio = true;
			}
		}
	}

	public void removerCurso(Servidor servidor) {
		this.servidor = servidor;
		List<Permissao> permissao = new ArrayList<>();
		permissao = dao.listar(Permissao.class,
				" servidor = " + servidor.getId() + " and status = true and tipo.descricao = 'professor'");

		if (permissao.size() > 0) {
			professorCursoRemove = dao.listar(ProfessorCurso.class,
					" professor = " + servidor.getId() + " and status = true");

		} else {
			FecharDialog.fecharDialogServidorCruso();
			ExibirMensagem.exibirMensagem(Mensagem.SERVIDOR);
		}

	}

	public void buscarPermissao() {
		List<Permissao> permissao = new ArrayList<>();
		permissao = dao.listar(Permissao.class, " servidor = " + servidor.getId());

		for (Permissao pp : permissao) {

			if (pp.getTipo().getDescricao().equals("professor")) {
				removerCurso(servidor);
				inativarCursoProfessorTodos();
			}

		}
	}

	public void inativarCursoProfessorTodos() {
		for (ProfessorCurso p : professorCursoSelecionado) {
			p.setStatus(false);
			dao.alterar(p);

			carregaLista();
			ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
			FecharDialog.fecharDialogServidorCruso();
		}
	}

	public void inativarCursoProfessor() {

		if (professorCursoRemove.size() == 1) {
			ExibirMensagem.exibirMensagem(Mensagem.CURSOPROFESSOR);
		} else {

			for (ProfessorCurso p : professorCursoSelecionado) {
				p.setStatus(false);
				dao.alterar(p);
			}
			carregaLista();

			ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
			FecharDialog.fecharDialogServidorCruso();
		}
	}
	
	public void salvarSenha() {
		try {
			
				servidor.setSenha(CriptografiaSenha.criptografar(servidor.getSenha()));
				dao.alterar(servidor);

				FecharDialog.fecharDialogEditarSenhaServidor();
				ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
				carregaLista();
			

		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
			e.printStackTrace();
		}
	}
	
	
	
public Boolean verificaProfessor(){
	for (Tipo t : tipoSalvar) {
		if (t.getDescricao().equalsIgnoreCase("professor")) {	
					if(listCursoSelecionado.size() == 0){
						return false;
					}			
		}
	}
	return true;
}
	public void salvar() {

		if (flag == 1) {
			criarNovoObjeto();
			carregaLista();
			ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
			FecharDialog.fecharDialogServidor();
			flag = 0;
		} else {
			try {
				
					
			if(verificaProfessor() == false){
				ExibirMensagem.exibirMensagem(Mensagem.SELECIONEPROFESSOR);
			}else{

				if (servidor.getId() == null) {

					if (tipoSalvar.size() > 0) {

						if (validacoesGerirUsuarios.buscarUsuarios(servidor)) {
							ExibirMensagem.exibirMensagem(Mensagem.USUARIO);
						} else if (validacoesGerirUsuarios.buscarSiape(servidor)) {
							ExibirMensagem.exibirMensagem(Mensagem.SIAPE);
						} else {
							if (servidor.getSenha().isEmpty()) {
								servidor.setSenha("123");
							}

							if (servidor.getChefe() == null) {
								servidor.setChefe(false);
							}
							servidor.setDataCadastro(new Date());
							servidor.setStatus(true);
							servidor.setSenha(CriptografiaSenha.criptografar(servidor.getSenha()));
							dao.inserir(servidor);
							for (Tipo t : tipoSalvar) {
								permissao.setDataInclusao(new Date());
								permissao.setServidor(servidor);
								permissao.setStatus(true);
								permissao.setTipo(t);
								dao.inserir(permissao);

								permissao = new Permissao();
							}
							for (Tipo t : tipoSalvar) {
								if (t.getDescricao().equals("professor")) {
									
									
									for (Curso c : listCursoSelecionado) {
										
										professorCurso.setStatus(true);
										professorCurso.setCurso(c);
										professorCurso.setProfessor(servidor);
										dao.inserir(professorCurso);

										professorCurso = new ProfessorCurso();
									}
								}
							}
							controleAddCurso = true;
							criarNovoObjeto();
							carregaLista();
							ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
							FecharDialog.fecharDialogServidor();

						}
					} else {

						ExibirMensagem.exibirMensagem(Mensagem.ERROPERMISSAO);
					}

				} else {
					if ((validacoesGerirUsuarios.buscarUsuarios(servidor))
							&& (validacoesGerirUsuarios.buscarUsuarioAlterar(servidor))) {
						ExibirMensagem.exibirMensagem(Mensagem.USUARIO);
					} else if ((validacoesGerirUsuarios.buscarSiape(servidor))
							&& (validacoesGerirUsuarios.buscarSiapeAlterar(servidor))) {
						ExibirMensagem.exibirMensagem(Mensagem.SIAPE);
					} else {
						if (servidor.getSenha().isEmpty()) {
							servidor.setSenha("123");
						}

						if (servidor.getChefe() == null) {
							servidor.setChefe(false);
						}
						dao.alterar(servidor);
						for (Tipo t : tipoSalvar) {
							permissao.setDataInclusao(new Date());
							permissao.setServidor(servidor);
							permissao.setStatus(true);
							permissao.setTipo(t);
							if (validarRelacionamentoPermissao(permissao)) {
								dao.inserir(permissao);
							}
							permissao = new Permissao();
						}
						for (Tipo t : tipoSalvar) {
							if (t.getDescricao().equalsIgnoreCase("professor")) {
								
								
								
								for (Curso c : listCursoSelecionado) {
									professorCurso.setStatus(true);
									professorCurso.setCurso(c);
									professorCurso.setProfessor(servidor);
									if (validarRelacionamentoCursoProfessor(professorCurso)) {
										dao.inserir(professorCurso);
									}
									professorCurso = new ProfessorCurso();
								}
							}
						}
						controleAddCurso = true;
						criarNovoObjeto();
						carregaLista();
						ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
						FecharDialog.fecharDialogServidor();
					}
				}
			}
			} catch (Exception e) {
				System.out.println("erro" + e);
			}
		}
	}

	public boolean verificarEmailCadastrado() {

		Pessoa pessoa = new Pessoa();

		pessoa = (Pessoa) dao.listar(Pessoa.class, " usuario = " + servidor.getUsuario());

		if (pessoa.getUsuario().equals(servidor.getUsuario())) {
			return true;
		} else {
			return false;
		}
	}

	public boolean validarRelacionamentoPermissao(Permissao permissao) {
		List<Permissao> listPermissaoServidor = new ArrayList<>();

		try {

			listPermissaoServidor = dao.listar(Permissao.class,
					" tipo = " + permissao.getTipo().getId() + " and servidor = " + permissao.getServidor().getId());

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (listPermissaoServidor.isEmpty())
			return true;
		return false;

	}

	public boolean validarRelacionamentoCursoProfessor(ProfessorCurso servidorCurso) {
		List<ProfessorCurso> listServidorCurso = new ArrayList<>();

		try {

			listServidorCurso = dao.listar(ProfessorCurso.class, " professor = " + servidorCurso.getProfessor().getId()
					+ " and curso = " + servidorCurso.getCurso().getId());

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (listServidorCurso.isEmpty())
			return true;
		return false;

	}

	public void inativarServidor(Servidor servidor) {

		try {
			servidor.setStatus(false);
			dao.alterar(servidor);
			carregaLista();
			ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
		}

	}

	public void carregaLista() {
		listServidor = dao.listaComStatus(Servidor.class);
	}

	public void carregaPermissao() {

		listTipoSelecionado = dao.listaComStatus(Tipo.class);
		listCursoSalvar = dao.listaComStatus(Curso.class);
	}

	public void criarNovoObjeto() {

		controleAddTipo = true;
		servidor = new Servidor();
		permissao = new Permissao();
		listCursoSelecionado = new ArrayList<>();
		tipoSalvar = new ArrayList<>();
		
		controleSenha = true;
		controle = true;
		controleAddCurso = true;
		


	}

	public void reiniciarObjeto() {

		servidor = new Servidor();

		FecharDialog.fecharDialogServidor();

	}

	public List<Tipo> getListTipoSelecionado() {
		return listTipoSelecionado;
	}

	public void setListTipoSelecionado(List<Tipo> listTipoSelecionado) {
		this.listTipoSelecionado = listTipoSelecionado;
	}

	public boolean isControle() {
		return controle;
	}

	public void setControle(boolean controle) {
		this.controle = controle;
	}

	public Servidor getServidor() {
		return servidor;
	}

	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
	}

	public Permissao getPermissao() {
		return permissao;
	}

	public void setPermissao(Permissao permissao) {
		this.permissao = permissao;
	}

	public List<Curso> getListCursoSelecionado() {
		return listCursoSelecionado;
	}

	public void setListCursoSelecionado(List<Curso> listCursoSelecionado) {
		this.listCursoSelecionado = listCursoSelecionado;
	}

	public List<Curso> getListCursoSalvar() {
		listCursoSalvar = dao.listaComStatus(Curso.class);
		return listCursoSalvar;
	}

	public void setListCursoSalvar(List<Curso> listCursoSalvar) {
		this.listCursoSalvar = listCursoSalvar;
	}

	public ProfessorCurso getProfessorCurso() {
		return professorCurso;
	}

	public void setProfessorCurso(ProfessorCurso professorCurso) {
		this.professorCurso = professorCurso;
	}

	public List<Servidor> getListServidor() {
		listServidor = dao.listaComStatus(Servidor.class);

		return listServidor;
	}

	public boolean verificaCurso(Servidor servidor) {

		List<Permissao> listPermissao = new ArrayList<>();
		listPermissao = dao.listar(Permissao.class,
				" servidor = " + servidor.getId() + " and tipo.descricao = 'professor'");
		if (listPermissao.size() > 0) {
		
			return true;
		} else {
	
			return false;
		}
	}

	public boolean isControleDesativar() {
		return controleDesativar;
	}

	public List<ProfessorCurso> getProfessorCursoSelecionado() {
		return professorCursoSelecionado;
	}

	public void setProfessorCursoSelecionado(List<ProfessorCurso> professorCursoSelecionado) {
		this.professorCursoSelecionado = professorCursoSelecionado;
	}

	public UsuarioSessaoMB getUsuarioSessao() {
		return usuarioSessao;
	}

	public void setUsuarioSessao(UsuarioSessaoMB usuarioSessao) {
		this.usuarioSessao = usuarioSessao;
	}

	public void setControleDesativar(boolean controleDesativar) {
		this.controleDesativar = controleDesativar;
	}

	public void setListServidor(List<Servidor> listServidor) {
		this.listServidor = listServidor;
	}

	public boolean isControleRadio() {
		return controleRadio;
	}

	public void setControleRadio(boolean controleRadio) {
		this.controleRadio = controleRadio;
	}

	public Curso getCursoAdicionado() {
		return cursoAdicionado;
	}

	public void setCursoAdicionado(Curso cursoAdicionado) {
		this.cursoAdicionado = cursoAdicionado;
	}

	public List<ProfessorCurso> getProfessorCursoRemove() {
		return professorCursoRemove;
	}

	public void setProfessorCursoRemove(List<ProfessorCurso> professorCursoRemove) {
		this.professorCursoRemove = professorCursoRemove;
	}

	public List<Permissao> getPermissaoRemove() {
		return permissaoRemove;
	}

	public void setPermissaoRemove(List<Permissao> permissaoRemove) {
		this.permissaoRemove = permissaoRemove;
	}

	public Tipo getPermissaoAdicionada() {
		return permissaoAdicionada;
	}

	public void setPermissaoAdicionada(Tipo permissaoAdicionada) {
		this.permissaoAdicionada = permissaoAdicionada;
	}

	public List<Permissao> getPermissaoRemoveSelecionada() {
		return permissaoRemoveSelecionada;
	}

	public List<Curso> getListCursoCompletar() {
		return listCursoCompletar;
	}

	public void setListCursoCompletar(List<Curso> listCursoCompletar) {
		this.listCursoCompletar = listCursoCompletar;
	}

	public void setPermissaoRemoveSelecionada(List<Permissao> permissaoRemoveSelecionada) {
		this.permissaoRemoveSelecionada = permissaoRemoveSelecionada;
	}

	public List<Tipo> getTipoSalvar() {
		return tipoSalvar;
	}

	public void setTipoSalvar(List<Tipo> tipoSalvar) {
		this.tipoSalvar = tipoSalvar;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Tipo getPermissaoParaRemove() {
		return permissaoParaRemove;
	}

	public Permissao getRemoverPermissao() {
		return removerPermissao;
	}

	public void setRemoverPermissao(Permissao removerPermissao) {
		this.removerPermissao = removerPermissao;
	}

	public void setPermissaoParaRemove(Tipo permissaoParaRemove) {
		this.permissaoParaRemove = permissaoParaRemove;
	}

	public boolean isControleSenha() {
		return controleSenha;
	}

	public void setControleSenha(boolean controleSenha) {
		this.controleSenha = controleSenha;
	}

	public boolean isControleAddTipo() {
		return controleAddTipo;
	}

	public void setControleAddTipo(boolean controleAddTipo) {
		this.controleAddTipo = controleAddTipo;
	}

	public boolean isControleAddCurso() {
		return controleAddCurso;
	}

	public void setControleAddCurso(boolean controleAddCurso) {
		this.controleAddCurso = controleAddCurso;
	}

	
}
