package br.com.cronos.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import ac.modelo.Permissao;
import dao.DAOGenerico;





@FacesConverter(value = "converterPermissao", forClass = Permissao.class)
public class ConverterPermissao implements Converter
{

	 @Override
	    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
	        if (string != null && !string.isEmpty()) {
	        	DAOGenerico dao = new DAOGenerico();
	        	
	            return  dao.buscarPorId(Permissao.class, Long.parseLong(string));
	        }
	        return null;
	    }

	    @Override
	    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
	        if (o != null && (o instanceof Permissao)) {
	            return String.valueOf(((Permissao) o).getId());
	        }

	        return null;
	    }

}