package util;

import java.util.ArrayList;
import java.util.List;

import ac.modelo.AlunoTurma;
import ac.modelo.Pessoa;


import base.modelo.Aluno;
import base.modelo.Servidor;
import base.modelo.Tipo;
import dao.DAOGenerico;
import dao.DAOUsuario;

public class ValidacoesGerirUsuarios {
	private DAOGenerico dao;

	public ValidacoesGerirUsuarios() {
		dao = new DAOGenerico();
	}

	public Boolean buscarUsuarios(Pessoa pessoa) {
		List<Pessoa> pessoas = new ArrayList<>();
		try {
			pessoas = dao.listarCadastro(Pessoa.class, " usuario = '" + pessoa.getUsuario() + "'");
			if (pessoas.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarUsuarios");
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean buscarPermissao(Tipo tipo) {
		
		
		List<Tipo> tipos = new ArrayList<>();
		try {
			tipos = dao.listar(Tipo.class, " descricao = '" + tipo.getDescricao() + "'");
			if (tipos.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarUsuarios");
			e.printStackTrace();
		}
		return false;
	}
	

	public Boolean buscarUsuarioAlterar(Pessoa pessoa) {
		
		List<Pessoa> pessoas = new ArrayList<>();
		pessoas = dao.listar(Pessoa.class,
				" usuario = '" + pessoa.getUsuario() + "' and id = '" + pessoa.getId() + "'");
		if (pessoas.size() > 0) {
			return false;
		}
		return true;
	}

	public Boolean buscarSiape(Servidor professor) {
		System.out.println("erro no metodo buscarSiape  classe validacoesGerirUsuarios");
		List<Servidor> professores = new ArrayList<>();
		List<Servidor> secretarias = new ArrayList<>();
		try {
			professores = dao.listar(Servidor.class, " siape = '" + professor.getSiape() + "'");
			secretarias = dao.listar(Servidor.class, " siape = '" + professor.getSiape() + "'");
			if (!professores.isEmpty() || !secretarias.isEmpty()) {
				return true;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarSiape ");
			e.printStackTrace();
		}
		return false;
	}

	public Boolean buscarSiapeAlterar(Servidor professor) {
		System.out.println("erro no metodo buscarSiapeAlterar classe validacoesGerirUsuarios");
		List<Servidor> professores = new ArrayList<>();
		try {
			professores = dao.listar(Servidor.class,
					" siape = '" + professor.getSiape() + "' and id = '" + professor.getId() + "'");
			if (professores.size() > 0) {
				return false;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarSiapeAlterar ");
			e.printStackTrace();
		}
		return true;
	}

	public Boolean buscarRA(AlunoTurma alunoTurma) {
		

	    List<AlunoTurma> alunoTurmaCond = new ArrayList<>();
		
		try {
			alunoTurmaCond = dao.listar(AlunoTurma.class, " ra = " + alunoTurma.getRa());
			if (alunoTurmaCond.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarRA");
			e.printStackTrace();
		}
		return false;
	}

	public Boolean buscarRaAlterar(AlunoTurma aluno) {
	
		List<AlunoTurma> alunos = new ArrayList<>();
		try {
			alunos = dao.listar(AlunoTurma.class, " ra = '" + aluno.getRa() + "' and id = '" + aluno.getId() + "'");
			if (alunos.size() > 0) {
				return false;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarRaAlterar");
			e.printStackTrace();
		}
		return true;
	}
/*
	public Boolean buscarSiape(Secretaria secretaria) {
		System.out.println("erro no metodo buscarSiape classe validacoesGerirUsuarios ");
		
		List<Secretaria> secretarias = new ArrayList<>();
		List<Professor> professores = new ArrayList<>();
		try {
			secretarias = dao.listar(Secretaria.class, " siape = '" + secretaria.getSiape() + "'");
			professores = dao.listar(Professor.class, " siape = '" + secretaria.getSiape() + "'");
			if ((!secretarias.isEmpty()) || (!professores.isEmpty())) {
				return true;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarSiape Secretaria");
			e.printStackTrace();
		}
		return false;
	}

	public Boolean buscarSiapeAlterar(Secretaria secretaria) {
		System.out.println("erro no metodo buscarSiapeAlterar classe validacoesGerirUsuarios");
		List<Secretaria> secretarias = new ArrayList<>();
		try {
			secretarias = dao.listar(Secretaria.class,
					" siape = '" + secretaria.getSiape() + "' and id = '" + secretaria.getId() + "'");
			if (secretarias.size() > 0) {
				return false;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarSiapeAlterar Secretaria");
			e.printStackTrace();
		}
		return true;
	}*/

}
