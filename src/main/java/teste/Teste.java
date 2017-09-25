package teste;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

import ac.modelo.AlunoTurma;
import ac.modelo.AtividadeTurma;
import ac.modelo.Certificado;
import base.modelo.Aluno;
import dao.ConexaoBanco;
import dao.DAOGenerico;
import sgpb.modelo.Evento;

public class Teste {
	private static EntityManager entityManager;
	
	public static void main(String args []){ 
    Evento evento = new Evento();
//    Date dataEvento = 24/05/2018;
	entityManager =  ConexaoBanco.getConexao().getEm();
	evento.setNome("Porra");
	evento.setDetalhes("detalhes");
//	evento.setTipo("Tipo");
//	evento.setArea("area");
//	evento.setData(dataEvento);
	
	
	
//	AlunoTurma a = new AlunoTurma();
//	Certificado c = new Certificado();
	
//	AtividadeTurma a = new AtividadeTurma();
	
		
//	DAOGenerico dao = new DAOGenerico();
//	
//	Aluno aluno = (Aluno) dao.buscarPorId(Aluno.class, 1L);
//	
//	AlunoTurma turma = (AlunoTurma) dao.buscarCondicao(AlunoTurma.class, "id_pessoa" ,1L);
//	System.out.println(turma.getRa());
	
	
	
	}
	
}
