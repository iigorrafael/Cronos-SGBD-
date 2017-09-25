package sgpb.controle;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ac.modelo.Movimentacao;
import ac.modelo.Permissao;
import base.modelo.Curso;
import dao.DAOGenerico;
import sgpb.modelo.Atividades;
import sgpb.modelo.Evento;
import util.ExibirMensagem;
import util.FecharDialog;
import util.Mensagem;

@ViewScoped
@ManagedBean
public class AtividadesEventoMB {

	private Atividades atividade;
	private List<Atividades> atividades;
	private List<Evento> eventos;
	private DAOGenerico dao;
	private Permissao permissao;
	private Evento evento;

	public AtividadesEventoMB() {
		dao = new DAOGenerico();
		atividades = new ArrayList<>();
		eventos = new ArrayList<>();
		criarNovaAtividade();
		preencherListaAtividade();
		preencherListaEvento();
	}

	public void salvar() {
		try {
			if (atividade.getId() == null) {
				dao.inserir(atividade);
				criarNovaAtividade();
				ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
				preencherListaAtividade();
			} else {
				ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
				dao.alterar(atividade);
				criarNovaAtividade();
				preencherListaAtividade();
			}
		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
		}
		FecharDialog.fecharDialogEvento();
	}
	
	public List<Evento> completarEventos(String str) {
		preencherListaEvento();
		System.out.println(eventos.get(0));
		List<Evento> eventosSelecionados = new ArrayList<>();
		for (Evento eve : eventos) {
			if (eve.getNome().toLowerCase().startsWith(str)) {
				eventosSelecionados.add(eve);
			}
		}
		return eventosSelecionados;
	}
	
	public void preencherListaEvento() {
		eventos = dao.listar(Evento.class);
	}
	
	public void preencherListaAtividade() {
		atividades = dao.listar(Atividades.class);
	}

	public void criarNovaAtividade() {
		atividade = new Atividades();
	}

	public Atividades getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividades atividade) {
		this.atividade = atividade;
	}

	public List<Atividades> getAtividades() {
		return atividades;
	}

	public void setAtividades(List<Atividades> atividades) {
		this.atividades = atividades;
	}

	public DAOGenerico getDao() {
		return dao;
	}

	public void setDao(DAOGenerico dao) {
		this.dao = dao;
	}

	public Permissao getPermissao() {
		return permissao;
	}

	public void setPermissao(Permissao permissao) {
		this.permissao = permissao;
	}

	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}
}
