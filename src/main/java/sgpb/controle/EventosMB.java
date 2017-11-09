package sgpb.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.GenericDAO;
import sgpb.modelo.Evento;
import sgpb.service.EventosService;
import util.ExibirMensagem;
import util.FecharDialog;
import util.Mensagem;


@ViewScoped
@Named("eventosMB")
public class EventosMB implements Serializable{
	
	private static final long serialVersionUID = 1L;
 
	
	private Evento evento;
	private List<Evento> eventos;	 
 
 
	@Inject
	private GenericDAO<Evento> daoEvento; // faz as buscas
	
	@Inject
	private EventosService eventoService; // inserir no banco	
	
	
	@PostConstruct
	public void inicializar() {
		criarNovoEvento();
		eventos = new ArrayList<>();		
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
		eventos = daoEvento.listarSemStatus(Evento.class);
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
	
	 
}

