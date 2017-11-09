package sgpb.service;

import java.io.Serializable;

import javax.inject.Inject;

import dao.GenericDAO;
import sgpb.modelo.AtividadeEvento;
import util.Transacional;


public class AtividadesEventoService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<AtividadeEvento> daoAtividades;

	@Transacional
	public void inserirAlterar(AtividadeEvento atividade){
		if(atividade.getId()==null){
			daoAtividades.inserir(atividade);
		}else{
			daoAtividades.alterar(atividade);
		}
	}
	
	@Transacional
	public void update(String valor){
		daoAtividades.update(valor);
	}

}
