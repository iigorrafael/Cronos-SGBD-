package br.com.cronos.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import ac.modelo.AlunoTurma;
import dao.DAOGenerico;



@FacesConverter(value = "alunoTurmaConverte", forClass = AlunoTurma.class)
public class ConverterCertificadoAlunoTurma implements Converter
{

	 @Override
	    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
	        if (string != null && !string.isEmpty()) {
	        	DAOGenerico dao = new DAOGenerico();
	        	
	            return  dao.buscarPorId(AlunoTurma.class, Long.parseLong(string));
	        }
	        return null;
	    }

	    @Override
	    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
	        if (o != null && (o instanceof AlunoTurma)) {
	            return String.valueOf(((AlunoTurma) o).getId());
	        }

	        return null;
	    }

}