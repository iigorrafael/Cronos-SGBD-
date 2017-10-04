package sgpb.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ac.modelo.Permissao;
import dao.GenericDAO;
import sgpb.modelo.Evento;
import sgpb.service.EventoService;
import util.ExibirMensagem;
import util.FecharDialog;
import util.Mensagem;

@ViewScoped
@Named("eventoMB")
public class EventoMB implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Evento evento;
	private List<Evento> eventos;
	private Permissao permissao;
	
	@Inject
	private GenericDAO<Evento> daoEvento; // faz as buscas
	
	@Inject
	private EventoService eventoService; // inserir no banco

	@PostConstruct
	public void inicializar() {
		eventos = new ArrayList<>();
		criarNovoEvento();
		preencherListaEvento();
	}

	public void salvar() {
		try {
			if (evento.getId() == null) {
				eventoService.inserirAlterar(evento);
				criarNovoEvento();
				ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
				preencherListaEvento();
			} else {				
				eventoService.inserirAlterar(evento);
				criarNovoEvento();
				ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
				preencherListaEvento();
			}
		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
		}
		FecharDialog.fecharDialogEvento();
	}
	
	public void preencherListaEvento() {
		eventos = daoEvento.listaComStatus(Evento.class);
	}

	public void criarNovoEvento() {
		evento = new Evento();
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public Permissao getPermissao() {
		return permissao;
	}

	public void setPermissao(Permissao permissao) {
		this.permissao = permissao;
	}

	public GenericDAO<Evento> getDaoEvento() {
		return daoEvento;
	}

	public void setDaoEvento(GenericDAO<Evento> daoEvento) {
		this.daoEvento = daoEvento;
	}

	public EventoService getEventoService() {
		return eventoService;
	}

	public void setEventoService(EventoService eventoService) {
		this.eventoService = eventoService;
	}
	
	

}
