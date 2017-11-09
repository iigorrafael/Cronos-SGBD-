package base.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ac.services.AlunoService;
import base.modelo.Tipo;
import base.service.TipoService;
import util.ExibirMensagem;
import util.FecharDialog;
import util.Mensagem;
import util.ValidacoesGerirUsuarios; 
import dao.GenericDAO;


@ViewScoped
@Named("tipoServidorMB")
public class TipoServidorMB implements Serializable{
	
	private static final long serialVersionUID = 1L;


	private Tipo tipoServidor;
	private List<Tipo> tipoServidorBusca;
	private List<Tipo> listTipoServidor;
	
	@Inject
	private ValidacoesGerirUsuarios validacoesGerirUsuarios;

	
	@Inject
	private GenericDAO<Tipo> daoTipo; //faz as buscas
	
	@Inject
	private TipoService tipoService; // inserir no banco
	
	
	@PostConstruct
	public void inicializar() {
	
		tipoServidor = new Tipo();
	
		listTipoServidor = new ArrayList<>();
		listTipoServidor = daoTipo.listaComStatus(Tipo.class);
		tipoServidorBusca = new ArrayList<>();
		
	}

	public void preencherListaTipoServidor(Tipo t) {
		this.tipoServidor = t;

	}

	public void inativarTipoServidor(Tipo t) {
		tipoService.update(" Tipo set status = false where id = " + t.getId());
		criarNovoObjeto();
		ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
		carregarLista();
	}

	public void salvar() {

		try {
			
			if (validacoesGerirUsuarios.buscarPermissao(tipoServidor) == true) {
				ExibirMensagem.exibirMensagem(Mensagem.TIPOCADASTRO);
			} else {

				if (tipoServidor.getId() == null) {
					tipoServidor.setStatus(true);
					tipoService.inserirAlterar(tipoServidor);

				} else {
					tipoServidor.setStatus(true);
					tipoService.inserirAlterar(tipoServidor);
				}

				criarNovoObjeto();
				ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
				FecharDialog.fecharDialogTipoServidor();
				carregarLista();
			}
		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
			e.printStackTrace();
		}

	}

	public void criarNovoObjeto() {
		tipoServidor = new Tipo();
	}

	public void carregarLista() {
		listTipoServidor = daoTipo.listaComStatus(Tipo.class);
	}

	public List<Tipo> getListTipoServidor() {

		//listTipoServidor = daoTipo.listaComStatus(Tipo.class);

		return listTipoServidor;
	}

	public void setListTipoServidor(List<Tipo> listTipoServidor) {
		this.listTipoServidor = listTipoServidor;
	}

	public Tipo getTipoServidor() {
		return tipoServidor;
	}

	public void setTipoServidor(Tipo tipoServidor) {
		this.tipoServidor = tipoServidor;
	}

	public List<Tipo> getTipoServidorBusca() {
		return tipoServidorBusca;
	}

	public void setTipoServidorBusca(List<Tipo> tipoServidorBusca) {
		this.tipoServidorBusca = tipoServidorBusca;
	}
}

