package ac.controle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import util.ExibirMensagem;
import util.FecharDialog;
import util.Mensagem;
import util.PermiteInativar;
import util.RecuperarRelacoesProfessor;
import ac.modelo.Atividade;
import ac.modelo.AtividadeTurma;
import ac.modelo.GrupoTurma;
import base.modelo.Turma;
import dao.DAOGenerico;

@SessionScoped
@ManagedBean
public class AtividadeTurmaMB {
	private AtividadeTurma atividadeTurma;
	private List<AtividadeTurma> atividadesTurmas;
	private List<Atividade> atividades;
	private List<GrupoTurma> grupoTurmas;
	private List<Turma> turmas;
	private RecuperarRelacoesProfessor relacoesProfessor;
	private DAOGenerico dao;
	private PermiteInativar permiteInativar;

	public AtividadeTurmaMB() {
		dao = new DAOGenerico();
		criarNovoObjetoAtividadeTurma();
		atividadesTurmas = new ArrayList<>();
		grupoTurmas = new ArrayList<>();
		atividades = new ArrayList<>();
		turmas = new ArrayList<>();
		preencherListaAtividadeTurma();
	}

	public void salvar() {
		try {

			if (atividadeTurma.getId() == null) {
				if (verificarAtividadeTurmaIguais(atividadeTurma)) {
					ExibirMensagem.exibirMensagem(Mensagem.ATIVIDADE_TURMA_CADASTRADO);
				} else {
					if (atividadeTurma.getMinimoHoras() <= atividadeTurma.getMaximoHoras()) {
						atividadeTurma.setDataCadastro(new Date());
						atividadeTurma.setStatus(true);
						dao.inserir(atividadeTurma);
						criarNovoObjetoAtividadeTurma();
						ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
						FecharDialog.fecharDialogAtividadeTurma();
						preencherListaAtividadeTurma();
					} else {
						ExibirMensagem.exibirMensagem(Mensagem.MAXIMO_DE_HORAS);
					}
				}
			} else {
				if ((verificarAtividadeTurmaIguais(atividadeTurma))
						&& (verificarAtividadeTurmaIguaisAlterar(atividadeTurma))) {
					ExibirMensagem.exibirMensagem(Mensagem.ATIVIDADE_TURMA_CADASTRADO);
				} else {
					if (atividadeTurma.getMinimoHoras() <= atividadeTurma.getMaximoHoras()) {
						if (atividadeTurma.getHoraUnica()) {
							atividadeTurma.setEquivalencia(null);
							atividadeTurma.setEquivalenciaHora(null);
						} else {
							atividadeTurma.setQuantidadeHoraUnica(null);
						}
						dao.alterar(atividadeTurma);
						criarNovoObjetoAtividadeTurma();
						ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
						FecharDialog.fecharDialogAtividadeTurma();
						preencherListaAtividadeTurma();
					}
				}
			}

		} catch (

		Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
			e.printStackTrace();
		}

	}
	

	public void inativar(AtividadeTurma atividadeTurma) {
		permiteInativar = new PermiteInativar();
		try {
			if (permiteInativar.verificarAtividadeTurmaComCertificado(atividadeTurma.getId())) {
				atividadeTurma.setStatus(false);
				dao.alterar(atividadeTurma);
				preencherListaAtividadeTurma();
				ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
			} else {
				ExibirMensagem.exibirMensagem(Mensagem.ATIVIDADE_TURMA_COM_CERTIFICADO);
			}
		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
		}
	}

	public void criarNovoObjetoAtividadeTurma() {
		atividadeTurma = new AtividadeTurma();
	}

	public void preencherListaAtividadeTurma() {
		relacoesProfessor = new RecuperarRelacoesProfessor();
		atividadesTurmas = relacoesProfessor.recuperarAtividadeTurmasProfessor();
	}

	public void preencherListaTurma() {
		relacoesProfessor = new RecuperarRelacoesProfessor();
		turmas = relacoesProfessor.recuperarTurmasProfessor();
	}

	public void preencherListaAtividade() {
		
		atividades = dao.listar(Atividade.class, " grupo.id = "+atividadeTurma.getGrupoTurma().getGrupo().getId());
		
	
		
	}
	public void preencherListaGrupoTurmas() {
		relacoesProfessor = new RecuperarRelacoesProfessor();
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

	public List<Atividade> completarAtividade(String str) {
	//	preencherListaAtividade();
		List<Atividade> atividadesSelecionadas = new ArrayList<>();
		for (Atividade ati : atividades) {
			if (ati.getDescricao().toLowerCase().startsWith(str)) {
				atividadesSelecionadas.add(ati);
			}
		}
		return atividadesSelecionadas;
	}

	
	public List<GrupoTurma> completarGrupoTurma(String str) {
		preencherListaGrupoTurmas();
		List<GrupoTurma> grupoSelecionadas = new ArrayList<>();
		for (GrupoTurma ati : grupoTurmas) {
			if (ati.getGrupo().getDescricao().toLowerCase().startsWith(str)) {
				grupoSelecionadas.add(ati);
			}
		}
		return grupoSelecionadas;
	}

	public Boolean verificarAtividadeTurmaIguais(AtividadeTurma atividadeTurma) {
		try {
			List<AtividadeTurma> verificador = new ArrayList<>();
			verificador = dao.listar(AtividadeTurma.class, " turma = " + atividadeTurma.getTurma().getId()
					+ " and atividade = " + atividadeTurma.getAtividade().getId());
			if (verificador.isEmpty())
				return false;
		} catch (Exception e) {
			System.err.println("Erro no metodo verificarAtividadeTurmaIguais");
			e.printStackTrace();
		}
		return true;
	}

	public Boolean verificarAtividadeTurmaIguaisAlterar(AtividadeTurma atividadeTurma) {
		try {
			List<AtividadeTurma> verificador = new ArrayList<>();
			verificador = dao.listar(AtividadeTurma.class,
					" turma = " + atividadeTurma.getTurma().getId() + " and atividade = "
							+ atividadeTurma.getAtividade().getId() + " and id = " + atividadeTurma.getId());
			if (verificador.isEmpty())
				return true;
		} catch (Exception e) {
			System.err.println("Erro no metodo verificarAtividadeTurmaIguais");
			e.printStackTrace();
		}
		return false;
	}

	public AtividadeTurma getAtividadeTurma() {
		return atividadeTurma;
	}

	public void setAtividadeTurma(AtividadeTurma atividadeTurma) {
		this.atividadeTurma = atividadeTurma;
	}

	public List<AtividadeTurma> getAtividadesTurmas() {
		return atividadesTurmas;
	}

	public void setAtividadesTurmas(List<AtividadeTurma> atividadesTurmas) {
		this.atividadesTurmas = atividadesTurmas;
	}

	public List<Atividade> getAtividades() {
		return atividades;
	}

	public void setAtividades(List<Atividade> atividades) {
		this.atividades = atividades;
	}

	public List<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}

	public List<GrupoTurma> getGrupoTurmas() {
		return grupoTurmas;
	}

	public void setGrupoTurmas(List<GrupoTurma> grupoTurmas) {
		this.grupoTurmas = grupoTurmas;
	}

}
