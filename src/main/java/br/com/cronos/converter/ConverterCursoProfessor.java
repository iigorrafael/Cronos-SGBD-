package br.com.cronos.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import ac.modelo.ProfessorCurso;
import dao.DAOGenerico;





@FacesConverter(value = "converterProfessorCurso", forClass = ProfessorCurso.class)
public class ConverterCursoProfessor implements Converter
{

	 @Override
	    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
	        if (string != null && !string.isEmpty()) {
	        	DAOGenerico dao = new DAOGenerico();
	        	
	            return  dao.buscarPorId(ProfessorCurso.class, Long.parseLong(string));
	        }
	        return null;
	    }

	    @Override
	    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
	        if (o != null && (o instanceof ProfessorCurso)) {
	            return String.valueOf(((ProfessorCurso) o).getId());
	        }

	        return null;
	    }

}