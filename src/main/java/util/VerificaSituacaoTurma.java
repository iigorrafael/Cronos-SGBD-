package util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ac.modelo.AlunoTurma;
import ac.modelo.Certificado;
import ac.modelo.GrupoTurma;
import ac.services.AlunoService;
import base.modelo.Aluno;
import base.modelo.Turma; 
import dao.FiltrosDAO;
import dao.GenericDAO;

public class VerificaSituacaoTurma implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private List<GrupoTurma> grupoTurmas;
	private List<Certificado> certificados;
	private List<AlunoTurma> alunosTurmas;
	
	
	@Inject
	private GenericDAO<GrupoTurma> daoGrupoTurma;
	
	@Inject
	private GenericDAO<Certificado> daoCertificado;
	
	@Inject
	private AlunoService alunoService;
	
	@Inject
	private  FiltrosDAO daoFiltros;
	
	public  VerificaSituacaoTurma() {
		grupoTurmas = new ArrayList<>();
		alunosTurmas = new ArrayList<>();
		certificados = new ArrayList<>();

	}

	public void recuperarGrupoTurma(Turma turma) {
		grupoTurmas = daoGrupoTurma.listar(GrupoTurma.class, " matriz = " + turma.getMatriz().getId());
	}

	public void recuperarAlunoTurmas(Turma turma) {
		alunosTurmas = daoFiltros.buscarAlunoTurma(turma.getId());

	}

	public void recuperarCertificados(Turma turma) {

		recuperarAlunoTurmas(turma);
	
		
		
		for (AlunoTurma a : alunosTurmas) {

			certificados.addAll(daoCertificado.listar(Certificado.class, " alunoTurma = " + a.getId() + " and situacao = 3"));
			calcularSituacao(turma, a);
			certificados = new ArrayList<>();
			
			
		}
		
	
		
	}

	public void calcularSituacao(Turma turma, AlunoTurma alunoTurma) {
		recuperarGrupoTurma(turma);
		List<Boolean> situacoes = new ArrayList<>();
		Boolean situacaoAux = true;
		Double soma = 0.0;
		for (GrupoTurma g : grupoTurmas) {
			for (Certificado c : certificados) {
				if (c.getIdGrupoTurma().equals(g.getId())) {
					if (c.getHoraComputada() != null) {
						soma = c.getHoraComputada() + soma;
					}
				}
			}
			if (soma >= g.getMaximoHoras() && soma >= g.getMinimoHoras()) {
				situacoes.add(true);
			} else {
				situacoes.add(false);
			}
			soma = 0.0;
		}
		for (int i = 0; i <= situacoes.size() - 1; i++) {
			if (situacoes.get(i) == false) {
				situacaoAux = false;
			}
		}

		alterarSituacao(alunoTurma, situacaoAux);
	}

	public void alterarSituacao(AlunoTurma alunoTurma, Boolean situacao) {
		if (situacao) {
			alunoService.update(" Aluno set situacao = 'Completou' where id = " + alunoTurma.getAluno().getId());
		} else {
			alunoService.update(" Aluno set situacao = 'Não Completou' where id = " + alunoTurma.getAluno().getId());
		}
	}
}
