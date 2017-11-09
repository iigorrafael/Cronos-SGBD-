package sgpb.service;

import java.io.Serializable;
import javax.inject.Inject;

import dao.GenericDAO;
import sgpb.modelo.ReceberLista;
import util.Transacional;

public class ReceberListaService implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<ReceberLista> daoReceberLista;

	@Transacional
	public void inserirAlterar(ReceberLista tipo) {
		if (tipo.getId() == null) {
			daoReceberLista.inserir(tipo);
		} else {
			daoReceberLista.alterar(tipo);
		}
	}

	@Transacional
	public void update(String valor) {
		daoReceberLista.update(valor);
	}

}
