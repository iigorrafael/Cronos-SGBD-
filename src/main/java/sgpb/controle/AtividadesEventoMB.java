package sgpb.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ac.modelo.AlunoTurma;
import ac.modelo.Pessoa;
import dao.GenericDAO;
import sgpb.modelo.AtividadeEvento;
import sgpb.modelo.Evento;
import sgpb.modelo.ReceberLista;
import sgpb.service.AtividadesEventoService;
import sgpb.service.ReceberListaService;
import util.ExibirMensagem;
import util.FecharDialog;
import util.Mensagem;

@ViewScoped
@Named("atividadesEventoMB")
public class AtividadesEventoMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private AtividadeEvento atividade;
	private List<AtividadeEvento> atividades;
	private List<Evento> eventos;
	private List<Pessoa> pessoas;
	private List<ReceberLista> receberListas;
	private Evento evento;
	private String email;
	private Pessoa usuario;
	private ReceberLista receberLista;

	@Inject
	private GenericDAO<AtividadeEvento> daoAtividades; // faz as buscas

	@Inject
	private GenericDAO<Pessoa> daoPessoa; // faz as buscas

	@Inject
	private GenericDAO<ReceberLista> daoReceberLista; // faz as buscas

	@Inject
	private GenericDAO<Evento> daoEventos; // faz as buscas

	@Inject
	private AtividadesEventoService atividadesService; // inserir no banco

	@Inject
	private ReceberListaService receberListaService; // inserir no banco

	@PostConstruct
	public void inicializar() {
		atividades = new ArrayList<>();
		eventos = new ArrayList<>();
		receberListas = new ArrayList<>();
		criarNovaReceberLista();
		criarNovaAtividade();
		preencherListaAtividade();
		preencherListaEvento();
	}

	public void EventoAtividade(Evento evento) {
		atividade.setEvento(evento);
	}

	public void salvar() {
		try {
			if (atividade.getId() == null) {
				atividadesService.inserirAlterar(atividade);
				criarNovaAtividade();
				ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
				preencherListaAtividade();
			} else {
				atividadesService.inserirAlterar(atividade);
				ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
				criarNovaAtividade();
				preencherListaAtividade();
			}
		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
		}
		FecharDialog.fecharDialogAtividades();
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

	public List<Pessoa> completarPessoas(String str) {
		preencherListaPessoas();
		System.out.println(pessoas.get(0).getNome());
		List<Pessoa> pessoasSelecionados = new ArrayList<>();
		for (Pessoa user : pessoas) {
			if (user.getNome().toLowerCase().startsWith(str)) {
				pessoasSelecionados.add(user);
			}
		}
		return pessoasSelecionados;
	}

	public void addListaEmailUsuario() {
		try {
			if (usuario.getId() != null) {
				if (receberLista.getId() == null) {
					receberLista.setNome(usuario.getNome());
					receberLista.setPessoa(usuario);
					receberLista.setEmail(usuario.getUsuario());
					receberLista.setAtividades(atividade);
					receberListaService.inserirAlterar(receberLista);
					criarNovaReceberLista();
					preencherListaReceberListas();
					ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);					

				} else {
					atividadesService.inserirAlterar(atividade);
					ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
					criarNovaReceberLista();
					preencherListaReceberListas();
				}
			} else {

				criarNovaReceberLista();
			}
		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
		}
		FecharDialog.fecharDialogEmail();
	}

	public void addListaEmail() {
		try {
			if (receberLista.getEmail() != null) {
				if (receberLista.getId() == null) {
					receberLista.setAtividades(atividade);
					receberListaService.inserirAlterar(receberLista);
					criarNovaReceberLista();
					ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
				} else {
					atividadesService.inserirAlterar(atividade);
					ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
					criarNovaReceberLista();
					preencherListaAtividade();
				}
			} else {
				receberListaService.inserirAlterar(receberLista);
				ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
				criarNovaReceberLista();
				preencherListaAtividade();
			}
		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
		}
		FecharDialog.fecharDialogEmail();
	}

	public void preencherListaEvento() {
		eventos = daoEventos.listarSemStatus(Evento.class);
	}

	public void preencherListaPessoas() {
		pessoas = daoPessoa.listarSemStatus(Pessoa.class);
	}

	public void preencherListaReceberListas(AjaxBehaviorEvent event) {
		receberListas = daoReceberLista.listarSemStatus(ReceberLista.class, " atividades = " + atividade.getId());
	}
	
	public void preencherListaReceberListas() {
		receberListas = daoReceberLista.listarSemStatus(ReceberLista.class, " atividades = " + atividade.getId());
	}

	public void preencherListaAtividade() {
		atividades = daoAtividades.listarSemStatus(AtividadeEvento.class);
	}

	public void criarNovaAtividade() {
		atividade = new AtividadeEvento();
	}

	public void criarNovaReceberLista() {
		receberLista = new ReceberLista();
	}

	public AtividadeEvento getAtividade() {
		return atividade;
	}

	public void setAtividade(AtividadeEvento atividade) {
		this.atividade = atividade;
	}

	public List<AtividadeEvento> getAtividades() {
		return atividades;
	}

	public void setAtividades(List<AtividadeEvento> atividades) {
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

	public GenericDAO<AtividadeEvento> getDaoAtividades() {
		return daoAtividades;
	}

	public void setDaoAtividades(GenericDAO<AtividadeEvento> daoAtividades) {
		this.daoAtividades = daoAtividades;
	}

	public GenericDAO<Evento> getDaoEventos() {
		return daoEventos;
	}

	public void setDaoEventos(GenericDAO<Evento> daoEventos) {
		this.daoEventos = daoEventos;
	}

	public AtividadesEventoService getAtividadesService() {
		return atividadesService;
	}

	public void setAtividadesService(AtividadesEventoService atividadesService) {
		this.atividadesService = atividadesService;
	}

	/**
	 * @return the pessoas
	 */
	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	/**
	 * @param pessoas
	 *            the pessoas to set
	 */
	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	/**
	 * @return the receberListas
	 */
	public List<ReceberLista> getReceberListas() {
		return receberListas;
	}

	/**
	 * @param receberListas
	 *            the receberListas to set
	 */
	public void setReceberListas(List<ReceberLista> receberListas) {
		this.receberListas = receberListas;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the usuario
	 */
	public Pessoa getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(Pessoa usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the receberLista
	 */
	public ReceberLista getReceberLista() {
		return receberLista;
	}

	/**
	 * @param receberLista
	 *            the receberLista to set
	 */
	public void setReceberLista(ReceberLista receberLista) {
		this.receberLista = receberLista;
	}

	/**
	 * @return the daoPessoa
	 */
	public GenericDAO<Pessoa> getDaoPessoa() {
		return daoPessoa;
	}

	/**
	 * @param daoPessoa
	 *            the daoPessoa to set
	 */
	public void setDaoPessoa(GenericDAO<Pessoa> daoPessoa) {
		this.daoPessoa = daoPessoa;
	}

	/**
	 * @return the daoReceberLista
	 */
	public GenericDAO<ReceberLista> getDaoReceberLista() {
		return daoReceberLista;
	}

	/**
	 * @param daoReceberLista
	 *            the daoReceberLista to set
	 */
	public void setDaoReceberLista(GenericDAO<ReceberLista> daoReceberLista) {
		this.daoReceberLista = daoReceberLista;
	}

}
