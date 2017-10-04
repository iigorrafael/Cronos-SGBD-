package sgpb.service;

import java.io.Serializable;
import javax.inject.Inject;
import javax.transaction.Transactional;

import dao.GenericDAO;
import sgpb.modelo.Evento;

public class EventoService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<Evento> daoEvento;

	@Transactional
	public void inserirAlterar(Evento evento){
		if(evento.getId()==null){
			daoEvento.inserir(evento);
		}else{
			daoEvento.alterar(evento);
		}
	}
	
	@Transactional
	public void update(String valor){
		daoEvento.update(valor);
	}
	
}
