package ac.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.hibernate.Session;

import util.ChamarRelatorio;
import util.ExibirMensagem;
import util.Mensagem;
import util.RecuperarRelacoesProfessor;
import ac.modelo.AtividadeTurma;
import ac.modelo.Certificado;
import ac.modelo.GrupoTurma;
import ac.modelo.Movimentacao;
import base.modelo.Turma; 
import dao.GenericDAO;

@ViewScoped
@Named("relatorioProfessorMB")
public class RelatorioProfessorMB implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	
	private Movimentacao aluno;
	private Turma turma;
	private List<Movimentacao> alunosAtivosProfessor;

	private List<Turma> turmas;
	private List<GrupoTurma> grupoTurmas;
	private GrupoTurma grupoTurma;
	
	@Inject
	private GenericDAO<Certificado> daoCertificado;
	
	@Inject
	private GenericDAO<AtividadeTurma> daoAtividadesTurma;
	
	@Inject
	private RecuperarRelacoesProfessor relacoesProfessor;
	
	@Inject
	private EntityManager manager;

	@PostConstruct
	public void inicializar() {
	
		turma = new Turma();
		grupoTurma = new GrupoTurma();
		turmas = new ArrayList<>();
		grupoTurmas = new ArrayList<>();
		aluno = new Movimentacao();
	}

	public void imprimirCertificadoGrupo() {
		try {
			List<Certificado> certificados = daoCertificado.listar(Certificado.class,
					" situacao = 3 and aluno = " + getAluno().getAlunoTurma().getAluno().getId());
			
			
			if (!certificados.isEmpty()) {
				Certificado cs = certificados.get(0);
				
				HashMap parametro = new HashMap<>();
				parametro.put("ALUNO", getAluno().getAlunoTurma().getAluno().getId());
				ChamarRelatorio ch = new ChamarRelatorio("grupo.jasper", parametro, "certificado_" +"Relat�rio por grupo do aluno " + aluno.getAlunoTurma().getAluno().getNome());
				Session sessions = manager.unwrap(Session.class);
				sessions.doWork(ch);
				
				
				
				
				
			} else {
				ExibirMensagem.exibirMensagem(Mensagem.NADA_ENCONTRADO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
		}
	}
	
	
	
	public void imprimirCertificadoHorasGrupo() { 
		try {
			List<Certificado> certificados = daoCertificado.listar(Certificado.class,
					" situacao = 3 ");
			
			
			if (!certificados.isEmpty()) {
				Certificado cs = certificados.get(0);
				
				HashMap parametro = new HashMap<>();
				parametro.put("TURMA", turma.getId());
				ChamarRelatorio ch = new ChamarRelatorio("situacaoHorasAluno.jasper", parametro, "certificado_" +"Relat�rio Horas aluno " + turma.getAbreviacaoTurma());
				Session sessions = manager.unwrap(Session.class);
				sessions.doWork(ch);
				
				
				
				
				
			} else {
				ExibirMensagem.exibirMensagem(Mensagem.NADA_ENCONTRADO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
		}
	}

	public void imprimirCertificadoGrupoTurma() {
		
		
		try {
			List<Certificado> certificados = daoCertificado.listar(Certificado.class, " situacao = 3 and idGrupoTurma = "
					+ getGrupoTurma().getId() + " and atividadeTurma.matriz.id = " + turma.getMatriz().getId());
			

			
			
			if (!certificados.isEmpty()) {
				Certificado cs = certificados.get(0);

				
				HashMap parametro = new HashMap<>();
				parametro.put("GRUPO", grupoTurma.getId());
				parametro.put("TURMA", turma.getId());
				ChamarRelatorio ch = new ChamarRelatorio("detalhado-turma.jasper", parametro, "certificado_" +"Relat�rio detalhado turma "
						+ grupoTurma.getGrupo().getDescricao() + " " + turma.getDescricao());
				Session sessions = manager.unwrap(Session.class);
				sessions.doWork(ch);
				 
			} else {
				ExibirMensagem.exibirMensagem(Mensagem.NADA_ENCONTRADO);
			}
		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
		}
	}
	
	public void imprimirCertificadoGrupoTurmaAtividade() {
		
		try {
			List<AtividadeTurma> atividades = daoAtividadesTurma.listar(AtividadeTurma.class, "  matriz = "+ turma.getMatriz().getId());
			
			
		
			
			
			if (!atividades.isEmpty()) {
			
				HashMap parametro = new HashMap<>();
				parametro.put("TURMA", turma.getMatriz().getId());
				ChamarRelatorio ch = new ChamarRelatorio("grupoTurma.jasper", parametro, "atividades_" +"Relat�rio atividades turma "
						 + turma.getDescricao());
				Session sessions = manager.unwrap(Session.class);
				sessions.doWork(ch);
				 
			} else {
				ExibirMensagem.exibirMensagem(Mensagem.NADA_ENCONTRADO);
			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
		}
	}

	public void imprimirGrupoAluno() {
		try {
			List<Certificado> certificados = daoCertificado.listar(Certificado.class, " situacao = 3 and aluno = "
					+ getAluno().getAlunoTurma().getAluno().getId() + " and  idGrupoTurma = " + grupoTurma.getId());

			if (!certificados.isEmpty()) {
				Certificado cs = certificados.get(0);
				
				
				HashMap parametro = new HashMap<>();
				parametro.put("ALUNO_GRUPO", getAluno().getAlunoTurma().getAluno().getId());
				parametro.put("GRUPO_ALUNO", grupoTurma.getId());
				ChamarRelatorio ch = new ChamarRelatorio("detalhado-aluno.jasper", parametro, "Relat�rio por grupo do aluno " + getAluno().getAlunoTurma().getAluno().getNome());
				Session sessions = manager.unwrap(Session.class);
				sessions.doWork(ch);
				
				  
			} else {
				ExibirMensagem.exibirMensagem(Mensagem.NADA_ENCONTRADO);
			}
		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
			e.printStackTrace();
		}
	}

	public void preencherListaAlunosProfessor() {
	
		alunosAtivosProfessor = new ArrayList<>();
		alunosAtivosProfessor = relacoesProfessor.recuperarAlunoMovimentacaoAtivo();
	}

	public List<Movimentacao> completarMovimentacaoProfessor(String str) {
	
		preencherListaAlunosProfessor();
		List<Movimentacao> alunoSelecionado = new ArrayList<>();
		for (Movimentacao a : alunosAtivosProfessor) {
			if (a.getAlunoTurma().getAluno().getNome().startsWith(str)) {
				alunoSelecionado.add(a);
			}
		}
		return alunoSelecionado;
	}

	public void preencherListaTurma() {	
		turmas = relacoesProfessor.recuperarTurmasProfessor();
	}

	public void preencherListaGrupoTurma() {
		
		grupoTurmas = relacoesProfessor.recuperarGrupoTurmasProfessor();
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

	public List<GrupoTurma> completarGrupoTurma(String str) {
		preencherListaGrupoTurma();
		List<GrupoTurma> grupoTurmaSelecionado = new ArrayList<>();
		for (GrupoTurma g : grupoTurmas) {
			if (g.getGrupo().getDescricao().toLowerCase().startsWith(str)) {
				grupoTurmaSelecionado.add(g);
			}
		}
		return grupoTurmaSelecionado;
	}

	public Movimentacao getAluno() {
		return aluno;
	}

	public void setAluno(Movimentacao aluno) {
		this.aluno = aluno;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public GrupoTurma getGrupoTurma() {
		return grupoTurma;
	}

	public void setGrupoTurma(GrupoTurma grupoTurma) {
		this.grupoTurma = grupoTurma;
	}

}
