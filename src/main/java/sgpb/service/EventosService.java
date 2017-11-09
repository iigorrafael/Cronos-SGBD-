package sgpb.service;

import java.io.Serializable;

import javax.inject.Inject;
 
import dao.GenericDAO;
import sgpb.modelo.Evento;
import util.Transacional;

public class EventosService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<Evento> daoTipo;
	
	@Transacional
	public void inserirAlterar(Evento tipo){
		if(tipo.getId()==null){
			daoTipo.inserir(tipo);
		}else{
			daoTipo.alterar(tipo);
		}
	}
	
	@Transacional
	public void update(String valor){
		daoTipo.update(valor);
	}

}
