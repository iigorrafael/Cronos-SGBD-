package sgpb.service;

import java.io.Serializable;

import javax.inject.Inject;
import javax.transaction.Transactional;

import dao.GenericDAO;
import sgpb.modelo.Atividades;

public class AtividadesEventoService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<Atividades> daoAtividades;

	@Transactional
	public void inserirAlterar(Atividades atividade){
		if(atividade.getId()==null){
			daoAtividades.inserir(atividade);
		}else{
			daoAtividades.alterar(atividade);
		}
	}
	
	@Transactional
	public void update(String valor){
		daoAtividades.update(valor);
	}

}
