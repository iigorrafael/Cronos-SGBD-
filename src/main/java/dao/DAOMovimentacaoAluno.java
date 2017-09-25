package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import ac.modelo.AlunoTurma;
import ac.modelo.Movimentacao;


public class DAOMovimentacaoAluno {
	private static EntityManager entityManager;

	public List buscarAtivo() {
	
		entityManager = ConexaoBanco.getConexao().getEm();
		Query q = null;
		List<Movimentacao> lista = new ArrayList<>();
		try {
			if (!entityManager.getTransaction().isActive())
				entityManager.getTransaction().begin();
			q = entityManager
					.createQuery("from Movimentacao a where a.dataMovimentacao = (select max(b.dataMovimentacao)"
							+ " from Movimentacao b where b.alunoTurma = a.alunoTurma) "
							+ " and a.situacao = 1 and a.status is true and a.controle = true and a.dataMovimentacaoFim = null"); // coloquei um and dataMovi.... = null
			entityManager.getTransaction().commit();
			lista = q.getResultList();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		}
		return lista;
	}

	public List buscarTrancado() {
	
		entityManager = ConexaoBanco.getConexao().getEm();
		Query q = null;
		List<Movimentacao> lista = new ArrayList<>();
		try {
			if (!entityManager.getTransaction().isActive())
				entityManager.getTransaction().begin();
			q = entityManager.createQuery(
					"from Movimentacao a where a.dataMovimentacao = (select max(b.dataMovimentacao) from Movimentacao b where b.alunoTurma = a.alunoTurma) "
							+ " and a.situacao <> 1 and a.status is true and a.alunoTurma.status = true and a.controle = false"); // coloquei um and dataMovi.... = null
			entityManager.getTransaction().commit();
			lista = q.getResultList();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		}
		return lista;

	}

	public List listarMaioresAlunoTurma() {
	
		entityManager = ConexaoBanco.getConexao().getEm();
		Query q = null;
		List<AlunoTurma> lista = new ArrayList<>();
		try {
			if (!entityManager.getTransaction().isActive())
				entityManager.getTransaction().begin();
			q = entityManager.createQuery("from AlunoTurma a where a.dataMudanca = (select max(b.dataMudanca)"
					+ " from AlunoTurma b where b.aluno = a.aluno) " + " and a.status is true");
			entityManager.getTransaction().commit();
			lista = q.getResultList();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		}
		return lista;
	}

	public List buscarMaiorAlunoTurma(Long aluno) {
		
		entityManager = ConexaoBanco.getConexao().getEm();
		Query q = null;
		List<AlunoTurma> lista = new ArrayList<>();
		try {
			if (!entityManager.getTransaction().isActive())
				entityManager.getTransaction().begin();
			q = entityManager.createQuery(" from AlunoTurma a where a.momentoMudanca  = (select max(b.momentoMudanca) "
					+ " from AlunoTurma b where b.aluno = a.aluno) and a.status is true and a.aluno = " + aluno);
			entityManager.getTransaction().commit();
			lista = q.getResultList();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		}
		return lista;
	}

	public List buscarMaiorMovimentacao(Long aluno) {
		entityManager = ConexaoBanco.getConexao().getEm();
		Query q = null;
		List<Movimentacao> lista = new ArrayList<>();
		try {
				if (!entityManager.getTransaction().isActive()){
				entityManager.getTransaction().begin();
			}
			
			q = entityManager
					.createQuery(" from Movimentacao a where a.dataMovimentacao  = (select max(b.dataMovimentacao) "
							+ " from Movimentacao b where b.alunoTurma = a.alunoTurma) and a.status is true and a.alunoTurma = "
							+ aluno);
			entityManager.getTransaction().commit();
			lista = q.getResultList();
			
			for(Movimentacao a : lista){
				System.out.print("lista " +a.getAlunoTurma().getAluno().getNome());
				if(a.getDataMovimentacao() == null)
				{
					int i = 10;
				}
				
			}
			
			
			
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		}
		return lista;
	}

	public List listarTodos(Class classe, String condicao) {
	    Query query = null;
		try {
			query = entityManager
					.createQuery("from " + classe.getSimpleName() + " a where a.status is true and " + condicao);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return query.getResultList();
	}
	
	
	public Object buscarCond(Class classe, String condicao) {
	    Query query = null;
		try {
			query = entityManager
					.createQuery("from " + classe.getSimpleName() + " a where a.status is true and " + condicao);
			if(query.getResultList().size() > 0){
			}else{
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return query.getResultList();
	}
}
