package base.controle;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import base.modelo.Tipo;
import util.ExibirMensagem;
import util.FecharDialog;
import util.Mensagem;
import util.ValidacoesGerirUsuarios;
import dao.DAOGenerico;

@ManagedBean

@SessionScoped
public class TipoServidorMB {

	private Tipo tipoServidor;
	private List<Tipo> tipoServidorBusca;
	private DAOGenerico dao;
	private List<Tipo> listTipoServidor;
	private ValidacoesGerirUsuarios validacoesGerirUsuarios;

	public TipoServidorMB() {
		tipoServidor = new Tipo();
		dao = new DAOGenerico();
		listTipoServidor = new ArrayList<>();
		tipoServidorBusca = new ArrayList<>();
		validacoesGerirUsuarios = new ValidacoesGerirUsuarios();
	}

	public void preencherListaTipoServidor(Tipo t) {
		this.tipoServidor = t;

	}

	public void inativarTipoServidor(Tipo t) {
		dao.update(" Tipo set status = false where id = " + t.getId());
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
					dao.inserir(tipoServidor);

				} else {
					tipoServidor.setStatus(true);
					dao.alterar(tipoServidor);
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
		listTipoServidor = dao.listaComStatus(Tipo.class);
	}

	public List<Tipo> getListTipoServidor() {

		listTipoServidor = dao.listaComStatus(Tipo.class);

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
