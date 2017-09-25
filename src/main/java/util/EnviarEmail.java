package util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class EnviarEmail {
	public static Boolean enviarEmail(String destinatario, String titulo, String mensagem) {
		SimpleEmail email = new SimpleEmail();
		Boolean flag = false;
		try {
			email.setDebug(true);
			email.setHostName("smtp.gmail.com");
			email.setAuthentication("cronos.software21@gmail.com", "cronossoftware2016");
			email.setSSL(true);
			email.setSmtpPort(465);
			email.addTo(destinatario);
			email.setFrom("cronos.software21@gmail.com");
			email.setSubject(titulo);
			email.setMsg(mensagem);
			email.send();
			flag = true;
			
			ExibirMensagem.exibirMensagem("valor da flag "+flag);
			ExibirMensagem.exibirMensagem(Mensagem.ERRO_EMAILTESTE);
		} catch (EmailException e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO_EMAIL);
		}
		if (flag) {
			ExibirMensagem.exibirMensagem("falg true");
			return true;
		} else {
			ExibirMensagem.exibirMensagem("falg false");
			return false;
		}
	}
}
