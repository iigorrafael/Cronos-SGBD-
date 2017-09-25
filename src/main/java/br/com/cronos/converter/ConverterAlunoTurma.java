package br.com.cronos.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import dao.DAOGenerico;
import ac.modelo.AlunoTurma;
import util.Mensagem;

@FacesConverter("converterAlunoTurma")
public class ConverterAlunoTurma implements Converter {

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				DAOGenerico dao = new DAOGenerico();
				AlunoTurma alunoTurma = (AlunoTurma) dao.buscarPorId(AlunoTurma.class, Long.parseLong(value));
				return alunoTurma;
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
	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			AlunoTurma cur = (AlunoTurma) object;
			return String.valueOf(cur.getId());
		} else {
			return null;
		}
	}
}
