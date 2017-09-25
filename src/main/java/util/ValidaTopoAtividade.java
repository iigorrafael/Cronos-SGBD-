package util;

import java.util.ArrayList;
import java.util.List;

import ac.modelo.Certificado;
import base.modelo.Aluno;
import dao.DAOFiltros;
import dao.DAOGenerico;

public class ValidaTopoAtividade {
	private DAOFiltros daoFiltros;
	private List<Certificado> certificados;
	private Double somaCertificado;

	public ValidaTopoAtividade() {
		daoFiltros = new DAOFiltros();
		certificados = new ArrayList<>();
		somaCertificado = 0.0;
	}

	public Boolean calcularTotalAtividade(Certificado certificado) {
		try {
			daoFiltros = new DAOFiltros();
			certificados = new ArrayList<>();
			
			certificados = daoFiltros.certificadosAlunosComAtividade(certificado.getAlunoTurma().getId(), 3,
					certificado.getAtividadeTurma().getId());
			somaCertificado = 0.0;
			for (Certificado c : certificados) {
				somaCertificado = somaCertificado + c.getHoraComputada();
			}

			if ((certificado.getAtividadeTurma().getHoraUnica())
					&& (certificado.getAtividadeTurma().getQuantidadeHoraUnica() != null)) {
				if (somaCertificado > certificado.getAtividadeTurma().getQuantidadeHoraUnica()) {
					return false;
				}
			} else if (certificado.getAtividadeTurma().getMaximoHoras() != null) {
				if (somaCertificado >= certificado.getAtividadeTurma().getMaximoHoras()) {
					return false;
				}
			}
		} catch (Exception e) {
			System.err.println("Erro calcularTotalAtividade");
			e.printStackTrace();
		}
		return true;

	}
}
