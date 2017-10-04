package sgpb.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.GenericDAO;
import sgpb.modelo.Atividades;
import sgpb.modelo.Evento;
import sgpb.service.AtividadesEventoService;
import util.ExibirMensagem;
import util.FecharDialog;
import util.Mensagem;

@ViewScoped
@Named("atividadesEventoMB")
public class AtividadesEventoMB implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Atividades atividade;
	private List<Atividades> atividades;
	private List<Evento> eventos;
	private Evento evento;
	
	@Inject
	private GenericDAO<Atividades> daoAtividades; // faz as buscas
	
	@Inject
	private GenericDAO<Evento> daoEventos; // faz as buscas
	
	@Inject
	private AtividadesEventoService AtividadesService; // inserir no banco

	@PostConstruct
	public void inicializar() {
		atividades = new ArrayList<>();
		eventos = new ArrayList<>();
		criarNovaAtividade();
		preencherListaAtividade();
		preencherListaEvento();
	}
	
	public void EventoAtividade(Evento evento){
		atividade.setEvento(evento);
	}

	public void salvar() {
		try {
			if (atividade.getId() == null) {
				AtividadesService.inserirAlterar(atividade);
				criarNovaAtividade();
				ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
				preencherListaAtividade();
			} else {				
				AtividadesService.inserirAlterar(atividade);
				ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
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
		System.out.println(eventos.get(0).getNome());
		List<Evento> eventosSelecionados = new ArrayList<>();
		for (Evento eve : eventos) {
			if (eve.getNome().toLowerCase().startsWith(str)) {
				eventosSelecionados.add(eve);
			}
		}
		return eventosSelecionados;
	}
	
	public void preencherListaEvento() {
		eventos = daoEventos.listaComStatus(Evento.class);
	}
	
	public void preencherListaAtividade() {
		atividades = daoAtividades.listaComStatus(Atividades.class);
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

	public GenericDAO<Atividades> getDaoAtividades() {
		return daoAtividades;
	}

	public void setDaoAtividades(GenericDAO<Atividades> daoAtividades) {
		this.daoAtividades = daoAtividades;
	}

	public GenericDAO<Evento> getDaoEventos() {
		return daoEventos;
	}

	public void setDaoEventos(GenericDAO<Evento> daoEventos) {
		this.daoEventos = daoEventos;
	}

	public AtividadesEventoService getAtividadesService() {
		return AtividadesService;
	}

	public void setAtividadesService(AtividadesEventoService atividadesService) {
		AtividadesService = atividadesService;
	}
	
	
}
