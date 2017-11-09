package cope.controle;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FlowEvent;

import cope.modelo.Participante;
import cope.modelo.Projeto;

@ManagedBean
@ViewScoped
public class ProjetoMB {

	private Projeto projeto;
	private List<Participante> ListaParticipanteProjeto;

	public ProjetoMB() {
		projeto = new Projeto();
		ListaParticipanteProjeto = new ArrayList<>();
	}

	public String navegacaoNovoExtencao(FlowEvent event) {
		/*
		 * if (event.getOldStep().equals("projeto")) { if
		 * (projeto.getTitulo().length() == 0) { return event.getOldStep(); } }
		 */
		System.out.println("Nevegando");
		return event.getNewStep();
	}

	// GET SET

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public List<Participante> getListaParticipanteProjeto() {
		return ListaParticipanteProjeto;
	}

	public void setListaParticipanteProjeto(List<Participante> listaParticipanteProjeto) {
		ListaParticipanteProjeto = listaParticipanteProjeto;
	}

}
