package br.com.cronos.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import base.modelo.Tipo;
import dao.DAOGenerico;




@FacesConverter(value = "converterTipoServidor", forClass = Tipo.class)
public class ConverterTipoServidor implements Converter
{

	 @Override
	    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
	        if (string != null && !string.isEmpty()) {
	        	DAOGenerico dao = new DAOGenerico();
	        	
	            return  dao.buscarPorId(Tipo.class, Long.parseLong(string));
	        }
	        return null;
	    }

	    @Override
	    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
	        if (o != null && (o instanceof Tipo)) {
	            return String.valueOf(((Tipo) o).getId());
	        }

	        return null;
	    }

}