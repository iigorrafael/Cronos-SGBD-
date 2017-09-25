package ac.controle;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ac.modelo.Certificado;
import dao.DAOFiltros;
import dao.DAOGenerico;

@ViewScoped
@ManagedBean
public class ListaCertificadosAlunosMB {
	private List<Certificado> certificadosValidados;
	private List<Certificado> certificadosAutenticados;
	private List<Certificado> certificadosInvalidados;
	private List<Certificado> certificadosNaoAuntenticados;
	private Certificado certificado;
	private Certificado certificadoBuscado;
	private DAOFiltros daoFiltros;
	private UsuarioSessaoMB usuarioSessao;
	private DAOGenerico dao ;

	public ListaCertificadosAlunosMB() {
		usuarioSessao = new UsuarioSessaoMB();
		daoFiltros = new DAOFiltros();
		certificadosValidados = new ArrayList<>();
		certificadosAutenticados = new ArrayList<>();
		certificadosInvalidados = new ArrayList<>();
		certificadosNaoAuntenticados = new ArrayList<>();
		certificado = new Certificado();
		certificadoBuscado = new Certificado();
		dao = new DAOGenerico();
		preencherListaCertificadoValidados();
		preencherListaCertificadoAutenticados();
		preencherListaCertificadoInvalidados();
		preencherListaCertificadoNaoAutenticados();
	}

	public void busca(Certificado cet){
		certificadoBuscado = (Certificado) dao.buscarPorId(Certificado.class, cet.getId());
	
	}
	public void preencherListaCertificadoValidados() {
		certificadosValidados = daoFiltros.certificadosAlunos(usuarioSessao.recuperarAluno().getId(), 3);
	}

	public void preencherListaCertificadoAutenticados() {
		certificadosAutenticados = daoFiltros.certificadosAlunos(usuarioSessao.recuperarAluno().getId(), 1);
	}

	public void preencherListaCertificadoInvalidados() {
		certificadosInvalidados = daoFiltros.certificadosAlunos(usuarioSessao.recuperarAluno().getId(), 4);
	}

	public void preencherListaCertificadoNaoAutenticados() {
		certificadosNaoAuntenticados = daoFiltros.certificadosAlunos(usuarioSessao.recuperarAluno().getId(), 2);
	}

	public List<Certificado> getCertificadosValidados() {
		return certificadosValidados;
	}

	public void setCertificadosValidados(List<Certificado> certificadosValidados) {
		this.certificadosValidados = certificadosValidados;
	}

	public List<Certificado> getCertificadosAutenticados() {
		return certificadosAutenticados;
	}

	public void setCertificadosAutenticados(List<Certificado> certificadosAutenticados) {
		this.certificadosAutenticados = certificadosAutenticados;
	}

	public List<Certificado> getCertificadosInvalidados() {
		return certificadosInvalidados;
	}

	public void setCertificadosInvalidados(List<Certificado> certificadosInvalidados) {
		this.certificadosInvalidados = certificadosInvalidados;
	}

	public List<Certificado> getCertificadosNaoAuntenticados() {
		return certificadosNaoAuntenticados;
	}

	public void setCertificadosNaoAuntenticados(List<Certificado> certificadosNaoAuntenticados) {
		this.certificadosNaoAuntenticados = certificadosNaoAuntenticados;
	}

	public Certificado getCertificado() {
		return certificado;
	}

	public void setCertificado(Certificado certificado) {
		this.certificado = certificado;
	}

	public Certificado getCertificadoBuscado() {
		return certificadoBuscado;
	}

	public void setCertificadoBuscado(Certificado certificadoBuscado) {
		this.certificadoBuscado = certificadoBuscado;
	}
	
}
