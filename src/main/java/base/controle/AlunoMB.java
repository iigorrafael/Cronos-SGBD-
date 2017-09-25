package base.controle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.print.attribute.standard.Sides;

import util.AtualizaHorasCertificado;
import util.CriptografiaSenha;
import util.ExibirMensagem;
import util.FecharDialog;
import util.Mensagem;
import util.RecuperarRelacoesProfessor;
import util.ValidacoesGerirUsuarios;
import ac.modelo.AlunoTurma;
import ac.modelo.AtividadeTurma;
import ac.modelo.Movimentacao;
import ac.modelo.Permissao;
import ac.modelo.ProfessorCurso;
import base.modelo.Aluno;
import base.modelo.Tipo;
import base.modelo.Turma;
import dao.DAOGenerico;
import dao.DAOMovimentacaoAluno;

//@ViewScoped
@SessionScoped
@ManagedBean
public class AlunoMB {

	private Aluno aluno;

	private Movimentacao movimentacao;
	private List<Turma> turmas;
	private AlunoTurma alunoTurmaAltera;
	private DAOGenerico dao;
	private DAOMovimentacaoAluno daoMovimentacaoAluno;
	private List<Movimentacao> alunosAtivos;
	private List<Movimentacao> alunosTrancados;
	private Movimentacao auxMovimentacao;
	private AlunoTurma alunoTurma;
	private List<AlunoTurma> listAlunoTurma;
	private ValidacoesGerirUsuarios validacoesGerirUsuarios;
	private List<Turma> turmasProfessor;
	private List<Movimentacao> alunosAtivosProfessor;
	private List<Movimentacao> alunosTrancadosProfessor;
	private RecuperarRelacoesProfessor relacoesProfessor;
	private AtualizaHorasCertificado atualizaHorasCertificado;
	private Permissao permissao;
	private Movimentacao movs ;

	public AlunoMB() {
		criarNovoObjetoAluno();
		dao = new DAOGenerico();
		daoMovimentacaoAluno = new DAOMovimentacaoAluno();
		movimentacao = new Movimentacao();
		turmasProfessor = new ArrayList<>();
		permissao = new Permissao();
		turmas = new ArrayList<>();
		alunosAtivos = new ArrayList<>();
		alunosTrancados = new ArrayList<>();
		alunoTurmaAltera = new AlunoTurma();
		auxMovimentacao = new Movimentacao();
		alunoTurma = new AlunoTurma();
		validacoesGerirUsuarios = new ValidacoesGerirUsuarios();
		alunosAtivosProfessor = new ArrayList<>();
		alunosTrancadosProfessor = new ArrayList<>();
		relacoesProfessor = new RecuperarRelacoesProfessor();
		atualizaHorasCertificado = new AtualizaHorasCertificado();
		listAlunoTurma = new ArrayList<>();
		movs = new Movimentacao();
		atualizarListas();

	}

	public void buscarRaAluno(Movimentacao m) {
		alunoTurma = (AlunoTurma) dao.buscarPorId(AlunoTurma.class, m.getAlunoTurma().getId());
		aluno = (Aluno) dao.buscarPorId(Aluno.class, alunoTurma.getAluno().getId());
	}

	public void adicionarTurma() {

		if (listAlunoTurma.size() == 0) {
			listAlunoTurma.add(alunoTurma);
			alunoTurma = new AlunoTurma();
		} else {

			if (validarRelaciomanetoAluno(alunoTurma)) {
				listAlunoTurma.add(alunoTurma);
				alunoTurma = new AlunoTurma();
			} else {
				ExibirMensagem.exibirMensagem(Mensagem.CURSOSELECIONADO);
			}
		}
	}

	public void tirarCurso(AlunoTurma alunoRemove) {

		listAlunoTurma.remove(alunoRemove);
	}

	public boolean validarRelaciomanetoAluno(AlunoTurma alunoturma) {
		for (AlunoTurma tt : listAlunoTurma) {
			if (tt.getTurma().getCurso().getNome().equals(alunoturma.getTurma().getCurso().getNome())) {
				return false;
			}
		}

		return true;
	}
	
	
	public void abrirCurso(){
			FecharDialog.abrirDialogSalvarAluno();
	
	}

	public void alterar(Movimentacao movimentacao) {
		this.movimentacao = movimentacao;
		listAlunoTurma = dao.listar(AlunoTurma.class,
				" controle = 1 and aluno = " + movimentacao.getAlunoTurma().getAluno().getId());
		
		aluno = (Aluno) dao.buscarPorId(Aluno.class, movimentacao.getAlunoTurma().getAluno().getId());
	

	}


	public void salvarTurma() {

		try {

			if (validarRelaciomanetoAluno(alunoTurma)) {
				listAlunoTurma.add(alunoTurma);
				alunoTurma = new AlunoTurma();
				for (AlunoTurma a : listAlunoTurma) {

					if (validarRelacionarCursosAluno(a)) {

						a.setAluno(aluno);
						a.setControle(1);
						a.setStatus(true);
						a.setPermiteCadastroCertificado(1); // coloquei aqui
						a.setMomentoMudanca(new Date());
						dao.inserir(a);

						atualizaHorasCertificado.alterarHoras(a);

						movimentacao = new Movimentacao();

						movimentacao.setDataMovimentacao(a.getDataMudanca());
						movimentacao.setSituacao(1);
						movimentacao.setAlunoTurma(a);
						movimentacao.setStatus(true);
						movimentacao.setControle(true);

						dao.inserir(movimentacao);
					}
				}
				ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);

			} else {
				ExibirMensagem.exibirMensagem(Mensagem.CURSOSELECIONADO);
			}

		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
			e.printStackTrace();
		}
	}

	public void fecharDialog() {
		FecharDialog.fecharDialogAlunoCursoEditar();
		FecharDialog.fecharDialogEditarAluno();
		ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
	}

	public void salvar() {
		try {
			
			if(listAlunoTurma.size() == 0){
				ExibirMensagem.exibirMensagem(Mensagem.CURSOCADASTRO);
			}else{
			
			if (validacoesGerirUsuarios.buscarUsuarios(aluno)) {
				ExibirMensagem.exibirMensagem(Mensagem.USUARIO);
			} else if (validacoesGerirUsuarios.buscarRA(alunoTurma)) {
				ExibirMensagem.exibirMensagem(Mensagem.RA);
			} else {

				if (aluno.getSenha().isEmpty()) {
					aluno.setSenha("123");
				}

				aluno.setDataCadastro(new Date());
				aluno.setStatus(true);
				aluno.setPerfilAluno("aluno");
				aluno.setSenha(CriptografiaSenha.criptografar(aluno.getSenha()));
				dao.inserir(aluno);

				for (AlunoTurma a : listAlunoTurma) {

					a.setAluno(aluno);
					a.setControle(1);
					a.setStatus(true);
					a.setPermiteCadastroCertificado(1); // coloquei aqui
					dao.inserir(a);

					movimentacao = new Movimentacao();

					movimentacao.setDataMovimentacao(a.getDataMudanca());
					movimentacao.setSituacao(1);
					movimentacao.setAlunoTurma(a);
					movimentacao.setStatus(true);
					movimentacao.setControle(true);

					dao.inserir(movimentacao);

				}
				FecharDialog.fecharDialogAlunoCurso();
				FecharDialog.fecharDialogAluno();
				ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
				criarNovoObjetoAluno();
				atualizarListas();
			}
			
			}
			
		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
			e.printStackTrace();
		}
	}

	private Boolean validarRelacionarCursosAluno(AlunoTurma alTurma) {
		List<AlunoTurma> alunoBusca = new ArrayList<>();
		try {
			alunoBusca = dao.listar(AlunoTurma.class,
					" turma = " + alTurma.getTurma().getId() + " and status = true and aluno = " + aluno.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (alunoBusca.isEmpty())
			return true;
		return false;
	}

	public void salvarEditar() {
		try {
			if ((validacoesGerirUsuarios.buscarUsuarios(aluno))
					&& (validacoesGerirUsuarios.buscarUsuarioAlterar(aluno))) {
				ExibirMensagem.exibirMensagem(Mensagem.USUARIO);
			} else if ((validacoesGerirUsuarios.buscarRA(alunoTurma))
					&& (validacoesGerirUsuarios.buscarRaAlterar(alunoTurma))) {
				ExibirMensagem.exibirMensagem(Mensagem.RA);
			} else {
				
				dao.alterar(aluno);

				FecharDialog.fecharDialogEditarAluno();
				ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
				atualizarListas();
			}

		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
			e.printStackTrace();
		}
	}
	
	
	public void salvarIrCurso() {
		try {
			if ((validacoesGerirUsuarios.buscarUsuarios(aluno))
					&& (validacoesGerirUsuarios.buscarUsuarioAlterar(aluno))) {
				ExibirMensagem.exibirMensagem(Mensagem.USUARIO);
			} else if ((validacoesGerirUsuarios.buscarRA(alunoTurma))
					&& (validacoesGerirUsuarios.buscarRaAlterar(alunoTurma))) {
				ExibirMensagem.exibirMensagem(Mensagem.RA);
			} else {
				dao.alterar(aluno);

				FecharDialog.abrirDialogEditarAluno();
				atualizarListas();
			}

		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
			e.printStackTrace();
		}
	}
	
	public void salvarSenha() {
		try {
			
				aluno.setSenha(CriptografiaSenha.criptografar(aluno.getSenha()));
				aluno.setPerfilAluno("aluno");
				dao.alterar(aluno);

				FecharDialog.fecharDialogEditarSenha();
				ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
				atualizarListas();
			

		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
			e.printStackTrace();
		}
	}

	public void inativar(Movimentacao movimentacao) {
		try {
			
			
			List<AlunoTurma> listAlunoTurmas = new ArrayList<>();
			listAlunoTurmas = dao.listar(AlunoTurma.class, " aluno.id ="+movimentacao.getAlunoTurma().getAluno().getId());
			List<Movimentacao> m = new ArrayList<>();
			
			
			for(AlunoTurma a : listAlunoTurmas){
				inavitarMovimentacao(a);
			}
			
		
		
		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
		}
		criarNovoObjetoAluno();
	}
	
	
	public void inavitarMovimentacao(AlunoTurma aluno){
		
		List<Movimentacao> listMov = new ArrayList<>();
		listMov = dao.listar(Movimentacao.class, " alunoTurma.id = "+aluno.getId());
		for(Movimentacao m : listMov){
	    	
	    	
	    	m.setSituacao(0);
			m.setControle(true); // excluir
			m.setDataMovimentacao(new Date());
			m.setStatus(false);
			m.getAlunoTurma().getAluno().setStatus(false);
			dao.inserir(m);
			dao.alterar(m.getAlunoTurma().getAluno());

			
			inativarCertificados(m.getAlunoTurma().getId());
			inativarMovimentacoes(m.getAlunoTurma().getId());
			inativarAlunoTurma(m.getAlunoTurma().getAluno().getId());
			
			
	    	
		}
		ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
		atualizarListas();
	}

	// trancamentos, cancelamentos e abandonos
	public void inativarMovimentacoes() {

		try {

			if (((auxMovimentacao.getDataMovimentacao())
					.before(permitirCadastrarMovimentacao(movimentacao.getAlunoTurma()).getDataMovimentacao()))
					|| (auxMovimentacao.getDataMovimentacao()).equals(
							permitirCadastrarMovimentacao(movimentacao.getAlunoTurma()).getDataMovimentacao())
					) {
				ExibirMensagem.exibirMensagem(Mensagem.MOVIMENTACOES_ANTERIORES);
			} else {

				Date dataFinal = auxMovimentacao.getDataMovimentacao();
				Calendar calendarData = Calendar.getInstance();
				calendarData.setTime(dataFinal);
				calendarData.add(Calendar.DATE, -1);
				Date dataInicial = calendarData.getTime();

				Movimentacao mov = new Movimentacao();
				mov = (Movimentacao) daoMovimentacaoAluno.listarTodos(Movimentacao.class,
						" a.dataMovimentacao = (select max(b.dataMovimentacao) "
								+ " from Movimentacao b where b.alunoTurma = a.alunoTurma) "
								+ " and a.status is true and a.alunoTurma.status = true and dataMovimentacaoFim = null and a.alunoTurma = "
								+ movimentacao.getAlunoTurma().getId())
						.get(0);

				mov.setControle(false);
				mov.setDataMovimentacaoFim(dataInicial);
				dao.alterar(mov);

				AlunoTurma aluno = new AlunoTurma();
				aluno = (AlunoTurma) dao.buscarPorId(AlunoTurma.class, movimentacao.getAlunoTurma().getId());
				aluno.setControle(0);
				dao.alterar(aluno);

				auxMovimentacao.setAlunoTurma(movimentacao.getAlunoTurma());
				auxMovimentacao.setStatus(true);
				auxMovimentacao.setControle(false);

				dao.inserir(auxMovimentacao);

				FecharDialog.fecharDialogAlunoCursoEditar();
				FecharDialog.fecharDialogAlunoEditarCurso();
				FecharDialog.fecharDialogAlunoTrancamento();
				alterar(movimentacao);

				ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
				auxMovimentacao = new Movimentacao();

				dao.update(" AlunoTurma set permiteCadastroCertificado = 2 where id = "
						+ movimentacao.getAlunoTurma().getId());

			
				criarNovoObjetoAluno();
				atualizarListas();

			}
		} catch (Exception e) {
			e.printStackTrace();
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
		}

	}

	public void ativar() {
		try {

			if (((auxMovimentacao.getDataMovimentacao())
					.before(permitirCadastrarMovimentacao(movimentacao.getAlunoTurma()).getDataMovimentacao()))
					|| (auxMovimentacao.getDataMovimentacao()).equals(
							permitirCadastrarMovimentacao(movimentacao.getAlunoTurma()).getDataMovimentacao())
					) {
				ExibirMensagem.exibirMensagem(Mensagem.MOVIMENTACOES_ANTERIORES);
			} else {

				Date dataFinal = movimentacao.getDataMovimentacao();
				Calendar calendarData = Calendar.getInstance();
				calendarData.setTime(dataFinal);
				calendarData.add(Calendar.DATE, -1);
				Date dataInicial = calendarData.getTime();

				Movimentacao mov = new Movimentacao();
				mov = (Movimentacao) daoMovimentacaoAluno.listarTodos(Movimentacao.class,
						" a.dataMovimentacao = (select max(b.dataMovimentacao) "
								+ " from Movimentacao b where b.alunoTurma = a.alunoTurma) "
								+ " and a.status is true and a.alunoTurma.status = true and dataMovimentacaoFim = null and a.alunoTurma = "
								+ movimentacao.getAlunoTurma().getId())
						.get(0);

				mov.setDataMovimentacaoFim(dataInicial);
				mov.setControle(true);
				dao.alterar(mov);

				AlunoTurma aluno = new AlunoTurma();
				aluno = (AlunoTurma) dao.buscarPorId(AlunoTurma.class, movimentacao.getAlunoTurma().getId());
				aluno.setControle(1);
				dao.alterar(aluno);

				auxMovimentacao.setAlunoTurma(movimentacao.getAlunoTurma());
				auxMovimentacao.setSituacao(1);
				auxMovimentacao.setStatus(true);
				auxMovimentacao.setControle(true);
				dao.inserir(auxMovimentacao);

				dao.update(" AlunoTurma set permiteCadastroCertificado = 1 where id = "
						+ movimentacao.getAlunoTurma().getId());

				FecharDialog.fecharDialogAlunoDestrancar();
				ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
				auxMovimentacao = new Movimentacao();
				atualizarListas();
				criarNovoObjetoAluno();
			}
		} catch (Exception e) {
			System.err.println("Erro em destrancar");
			e.printStackTrace();

			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
		}

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

	public void criarNovoObjetoAluno() {
		aluno = new Aluno();
		movimentacao = new Movimentacao();
		alunoTurma = new AlunoTurma();
		listAlunoTurma = new ArrayList<>();
	}

	public void preencherListaAlunos() {
		alunosAtivos = new ArrayList<>();
		alunosAtivos = daoMovimentacaoAluno.buscarAtivo();
	}

	public void preencherListaTrancados() {
		alunosTrancados = new ArrayList<>();
		alunosTrancados = daoMovimentacaoAluno.buscarTrancado();
	}

	public void preencherListaTurma() {
		turmas = new ArrayList<>();
		turmas = dao.listaComStatus(Turma.class);
	}

	public void atualizarListas() {
		preencherListaAlunos();
		preencherListaTrancados();
		preencherListaAlunosAtivosProfessor();
		preencherListaAlunosTrancadosProfessor();
	}

	public void inativarCertificados(Long id) {
		try {
			dao.update(" Certificado set status = false where alunoTurma = " + id);
			ExibirMensagem.exibirMensagem(Mensagem.INATIVAR_CERTIFICADOS);
		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO_INATIVAR_CERTIFICADOS);
		}

	}

	public void inativarMovimentacoes(Long id) {

		try {
			dao.update(" Movimentacao set status = false where alunoTurma = " + id);

			ExibirMensagem.exibirMensagem(Mensagem.INATIVAR_MOVIMENTACOES);
		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO_INATIVAR_MOVIMENTACOES);
		}

	}

	public void inativarAlunoTurma(Long id) {

		try {
			dao.update(" AlunoTurma set status = false where aluno = " + id);

			ExibirMensagem.exibirMensagem(Mensagem.INATIVAR_ALUNO_TURMA);
		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO_INATIVAR_ALUNO_TURMA);
		}

	}

	public Turma validarDataTurmaComAluno() {
		Turma turma = new Turma();
		turma = (Turma) dao.listar(Turma.class, " id = " + alunoTurma.getTurma().getId()).get(0);
		return turma;
	}

	public AlunoTurma permitirCadastrarAlunoTurma(Aluno aluno) {
		AlunoTurma alunoTurma = new AlunoTurma();

	
		alunoTurma = (AlunoTurma) daoMovimentacaoAluno.buscarMaiorAlunoTurma(aluno.getId()).get(0);
		return alunoTurma;
	}

	public Movimentacao permitirCadastrarMovimentacao(AlunoTurma aluno) {
		Movimentacao mov = new Movimentacao();

		mov = (Movimentacao) daoMovimentacaoAluno.buscarMaiorMovimentacao(aluno.getId()).get(0);
		return mov;
	}

	public List<Turma> completarTurmaProfessor(String str) {
		preencherListaTurmaProfessor();
		List<Turma> turmasSelecionadas = new ArrayList<>();
		for (Turma t : turmasProfessor) {
			if (t.getDescricao().startsWith(str)) {
				turmasSelecionadas.add(t);
			}
		}
		return turmasSelecionadas;
	}

	public void preencherListaTurmaProfessor() {
		relacoesProfessor = new RecuperarRelacoesProfessor();
		turmasProfessor = relacoesProfessor.recuperarTurmasProfessor();
	}

	public void preencherListaAlunosAtivosProfessor() {
		alunosAtivosProfessor = new ArrayList<>();
		alunosAtivosProfessor = relacoesProfessor.recuperarAlunoMovimentacaoAtivo();

		
	}

	public void preencherListaAlunosTrancadosProfessor() {
		alunosTrancadosProfessor = new ArrayList<>();
		alunosTrancadosProfessor = relacoesProfessor.recuperarAlunoMovimentacaoTrancados();

	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Movimentacao getMovimentacao() {
		return movimentacao;
	}

	public void setMovimentacao(Movimentacao movimentacao) {
		this.movimentacao = movimentacao;
	}

	public List<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}

	public List<Movimentacao> getAlunosAtivos() {
		return alunosAtivos;
	}

	public void setAlunosAtivos(List<Movimentacao> alunosAtivos) {
		this.alunosAtivos = alunosAtivos;
	}

	public List<Movimentacao> getAlunosTrancados() {
		return alunosTrancados;
	}

	public void setAlunosTrancados(List<Movimentacao> alunosTrancados) {
		this.alunosTrancados = alunosTrancados;
	}

	public AlunoTurma getAlunoTurma() {
		return alunoTurma;
	}

	public void setAlunoTurma(AlunoTurma alunoTurma) {
		this.alunoTurma = alunoTurma;
	}

	public Movimentacao getAuxMovimentacao() {
		return auxMovimentacao;
	}

	public void setAuxMovimentacao(Movimentacao auxMovimentacao) {
		this.auxMovimentacao = auxMovimentacao;
	}

	public List<Turma> getTurmasProfessor() {
		return turmasProfessor;
	}

	public void setTurmasProfessor(List<Turma> turmasProfessor) {
		this.turmasProfessor = turmasProfessor;
	}

	public List<Movimentacao> getAlunosAtivosProfessor() {
		return alunosAtivosProfessor;
	}

	public void setAlunosAtivosProfessor(List<Movimentacao> alunosAtivosProfessor) {
		this.alunosAtivosProfessor = alunosAtivosProfessor;
	}

	public AlunoTurma getAlunoTurmaAltera() {
		return alunoTurmaAltera;
	}

	public void setAlunoTurmaAltera(AlunoTurma alunoTurmaAltera) {
		this.alunoTurmaAltera = alunoTurmaAltera;
	}

	public List<AlunoTurma> getListAlunoTurma() {
		return listAlunoTurma;
	}

	public void setListAlunoTurma(List<AlunoTurma> listAlunoTurma) {
		this.listAlunoTurma = listAlunoTurma;
	}

	public List<Movimentacao> getAlunosTrancadosProfessor() {
		return alunosTrancadosProfessor;
	}

	public void setAlunosTrancadosProfessor(List<Movimentacao> alunosTrancadosProfessor) {
		this.alunosTrancadosProfessor = alunosTrancadosProfessor;
	}

}
