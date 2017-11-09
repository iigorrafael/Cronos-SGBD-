package br.com.cronos.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

import dao.GenericDAO;
import sgpb.modelo.Evento;
import util.Mensagem;

@Named("converterEvento")
public class ConverterEvento implements Converter{

	@Inject
	private GenericDAO<Evento> dao;

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				
			 return dao.buscarPorId(Evento.class, Long.parseLong(value));
			 
			} catch (Exception e) {
				e.printStackTrace();
				throw new ConverterException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, Mensagem.ERRO_CONVERTER, ""));
			}
		} else {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (!(value instanceof Evento)) return null;
		    return  String.valueOf(((Evento) value).getId());
		
		
	}
}


//@FacesConverter("converterEvento")
//public class ConverterEvento implements Converter {
//
//	@Override
//	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
//		if (value != null && value.trim().length() > 0) {
//			try {
//				Genericd dao = new DAOGenerico();
//				Evento evento = (Evento) dao.buscarPorId(Evento.class, Long.parseLong(value));
//				return evento;
//			} catch (Exception e) {
//				e.printStackTrace();
//				throw new ConverterException(
//						new FacesMessage(FacesMessage.SEVERITY_ERROR, Mensagem.ERRO_CONVERTER, ""));
//			}
//		} else {
//			return null;
//		}
//	}
//
//	@Override
//	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
//		if (object instanceof Evento && object != null) {
//			Evento eve = (Evento) object;
//			return String.valueOf(eve.getId());
//		} else {
//			return null;
//		}
//	}

