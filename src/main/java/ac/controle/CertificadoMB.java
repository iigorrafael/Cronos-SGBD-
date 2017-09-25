package ac.controle;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import util.CalculoEquivalencia;
import util.ExibirMensagem;
import util.FecharDialog;
import util.GeradorSenhas;
import util.Mensagem;
import util.ValidaPeriodoIncricao;
import util.ValidaTopoAtividade;
import util.ValidaTopoGrupo;
import ac.modelo.AlunoTurma;
import ac.modelo.AtividadeTurma;
import ac.modelo.Certificado;
import ac.modelo.GrupoTurma;
import base.modelo.Aluno;
import base.modelo.Curso;
import dao.DAOFiltros;
import dao.DAOGenerico;

@SessionScoped
@ManagedBean
public class CertificadoMB {

	private Certificado certificado;
	private UsuarioSessaoMB usuarioSessao;
	private List<Certificado> certificadosAlunos;
	private List<AtividadeTurma> atividadeTurmas;
	private DAOGenerico dao;
	private DAOFiltros daoFiltros;
	private AlunoTurma alunoTurma;
	private Aluno aluno;
	private CalculoEquivalencia calculoEquivalencia;
	private ValidaTopoGrupo validaTopoGrupo;
	private ValidaTopoAtividade validaTopoAtividade;
	private ValidaPeriodoIncricao validaPeriodoIncricao;
	private List<AlunoTurma> cursos;
	private Boolean permitePDF;
	private List<AlunoTurma> listAlunoTurma;
	private boolean controle = true;
	private boolean controleAltera = false;

	public CertificadoMB() {
		criarNovoObjetoCertificado();
		permitePDF = false;
		usuarioSessao = new UsuarioSessaoMB();
		certificadosAlunos = new ArrayList<>();
		atividadeTurmas = new ArrayList<>();
		dao = new DAOGenerico();
		calculoEquivalencia = new CalculoEquivalencia();
		alunoTurma = new AlunoTurma();
		daoFiltros = new DAOFiltros();
		preencherListaCertificadoAlunos();
		validaTopoGrupo = new ValidaTopoGrupo();
		validaTopoAtividade = new ValidaTopoAtividade();
		validaPeriodoIncricao = new ValidaPeriodoIncricao();
		cursos = new ArrayList<>();
		listAlunoTurma = new ArrayList<>();
		aluno = new Aluno();
		
	}
	
	public void controle(){
			controle = false;
	}
	public void controleCancela(){
		controleAltera = false;
	}

	public void salvar() {
		try {

			if (certificado.getAlunoTurma().getPermiteCadastroCertificado() == 2) {
				ExibirMensagem.exibirMensagem(Mensagem.NAO_ATIVO);
			} else {
				if (certificado.getQuantidadeMaximaHora() <= 0) {
					ExibirMensagem.exibirMensagem(Mensagem.QUANTIDADE_HORAS);
				} else {
					if (!certificado.getDataInicio().after(certificado.getDataFinalizado())) {
						try {
							 if
							 (validaPeriodoIncricao.permitirCadastroCertificado(certificado))
							 {
							 ExibirMensagem.exibirMensagem(Mensagem.PERIODO_CERTIFICADO_INVALIDO);
							 } else {
							certificado.setDescricao(certificado.getDescricao().replace("'", "").replace("=", "")
									.replace("<", "").replace("&", ""));
							certificado.setInstituicao(certificado.getInstituicao().replace("'", "").replace("=", "")
									.replace("<", "").replace("&", ""));

							if (certificado.getId() == null) {

								

								certificado.setDataCadastro(new Date());
								certificado.setAluno((Aluno) usuarioSessao.recuperarAluno());
								certificado.setStatus(true);
								certificado.setSituacao(0);
								certificado.setAtualizado(true);

								if (!validaTopoAtividade.calcularTotalAtividade(certificado)) {
									ExibirMensagem.exibirMensagem(Mensagem.HORA_ATIVIDADE_MAXIMA);
								} else {
									if (!validaTopoGrupo.calcularTotalGrupo(certificado)) {
										ExibirMensagem.exibirMensagem(Mensagem.HORA_GRUPO_MAXIMO);
									} else {
										certificado.setSituacao(0);
										dao.inserir(certificado);
									
										certificado.setHoraComputada(
												calculoEquivalencia.calcularHorasCertificado(certificado));
										certificado.setIdGrupoTurma(
												recuperarGrupoTurma(certificado.getAtividadeTurma()).getId());
										dao.alterar(certificado);

										
										if (certificado.getCaminhoCertificado() != null) {


											String nomeCertificado = gerarNomeCertificado()
													+ GeradorSenhas.gerarSenha();
											Path origem = Paths.get(certificado.getCaminhoCertificado());
											String path = FacesContext.getCurrentInstance().getExternalContext()
													.getRealPath("");

											File diretorio = new File(path + "/certificadoUpload");

											if (!diretorio.exists()) {
												diretorio.mkdirs();
											}
											Path destino = Paths.get(
													diretorio.getCanonicalFile() + "//" + nomeCertificado + ".pdf");

											Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);
											certificado.setCaminhoCertificado(nomeCertificado);
											certificado.setSituacao(0);
											dao.alterar(certificado);
										}
										ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
										FecharDialog.fecharDialogCertificado();
										criarNovoObjetoCertificado();
										controle = true;
									}
								}
							} else {
								if (!validaTopoAtividade.calcularTotalAtividade(certificado)) {
									
									ExibirMensagem.exibirMensagem(Mensagem.HORA_ATIVIDADE_MAXIMA);
								} else {
									if (!validaTopoGrupo.calcularTotalGrupo(certificado)) {
										FecharDialog.fecharDialogCertificado();
										ExibirMensagem.exibirMensagem(Mensagem.HORA_GRUPO_MAXIMO);
									} else {
										
										certificado.setHoraComputada(
												calculoEquivalencia.calcularHorasCertificado(certificado));
										certificado.setIdGrupoTurma(
												recuperarGrupoTurma(certificado.getAtividadeTurma()).getId());
										certificado.setAtualizado(true);
										dao.alterar(certificado);
								
										ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
										criarNovoObjetoCertificado();
										FecharDialog.fecharDialogCertificado();
										controle = true;
										controleAltera= false;
									}
								}
							}
							 }
						} catch (Exception e) {
							ExibirMensagem.exibirMensagem(Mensagem.ERRO);
							e.printStackTrace();
						}
					} else {
						ExibirMensagem.exibirMensagem(Mensagem.DATA_FINALIZAÇÃO);
					}
				}
				
			}
		} catch (Exception e) {
			System.err.println("salvar() - CertificadoMB");
			e.printStackTrace();
		}
		preencherListaCertificadoAlunos();
	}
	
	public List<AlunoTurma> completarCursos(String str) {
		cursos = dao.listar(AlunoTurma.class, " aluno = " + usuarioSessao.recuperarAluno().getId());
		List<AlunoTurma> cursosAtivos = new ArrayList<>();
		for (AlunoTurma cur : cursos) {
			if (cur.getTurma().getDescricao().toLowerCase().startsWith(str)) {
				cursosAtivos.add(cur);
			}
		}
		return cursosAtivos;
	}

	public void inativar(Certificado certificado) {
		try {
			certificado.setStatus(false);
			dao.alterar(certificado);
			ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
		}
		preencherListaCertificadoAlunos();
		criarNovoObjetoCertificado();
	}

	public static String gerarNomeCertificado() {
		SimpleDateFormat momento = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return momento.format(new Date());
	}

	public List<AtividadeTurma> completarAtividadeTurma(String str) {
		
		preencherListaAtividadeTurma();
		List<AtividadeTurma> atividadeTurmaSelecionados = new ArrayList<>();
		for (AtividadeTurma at : atividadeTurmas) {
			if (at.getAtividade().getDescricao().toLowerCase().startsWith(str)) {
				atividadeTurmaSelecionados.add(at);
			}
		}
		return atividadeTurmaSelecionados;
	}

	public void upload(FileUploadEvent evento) {
		try {
			UploadedFile arquivoUpload = evento.getFile();
			if (!arquivoUpload.getFileName().isEmpty()) {
				Path arquivoTemp = Files.createTempFile(null, null);
				Files.copy(arquivoUpload.getInputstream(), arquivoTemp, StandardCopyOption.REPLACE_EXISTING);
				certificado.setCaminhoCertificado(arquivoTemp.toString());
				permitePDF = true;
			}
		} catch (Exception e) {
			System.err.println("Erro em: upload");
			e.printStackTrace();
		}
	}

	public void criarNovoObjetoCertificado() {
		permitePDF = false;
		certificado = new Certificado();
	}

	public void preencherListaCertificadoAlunos() {
		try {
			
			certificadosAlunos = daoFiltros.certificadosAlunos(usuarioSessao.recuperarAluno().getId(), 0);
			RequestContext.getCurrentInstance().update("frmTabela");
	
		} catch (Exception e) {
			System.err.println("preencherListaCertificadoAlunos");
			e.printStackTrace();
		}
		
	}

	private Long recuperarTurmaAluno() {
		alunoTurma = (AlunoTurma) daoFiltros.buscarTurmaAluno(usuarioSessao.recuperarAluno().getId()).get(0);
		return alunoTurma.getTurma().getId();
	}

	public void preencherListaAtividadeTurma() {
		try {
			atividadeTurmas = daoFiltros.atividadesTurmaAluno(certificado.getAlunoTurma().getTurma().getId());
		} catch (Exception e) {
			System.err.println("preencherListaAtividadeTurma");
			e.printStackTrace();
		}
	}

	public GrupoTurma recuperarGrupoTurma(AtividadeTurma atividadeTurma) {
		GrupoTurma grupoTurma = new GrupoTurma();
		try {
			grupoTurma = (GrupoTurma) dao
					.listar(GrupoTurma.class, " grupo = " + atividadeTurma.getAtividade().getGrupo().getId()
							+ " and turma = " + atividadeTurma.getTurma().getId())
					.get(0);
		} catch (Exception e) {
			System.err.println("recuperarGrupoTurma");
			e.printStackTrace();
		}
		return grupoTurma;
	}

	public void permitirPDF(Certificado certificado) {
		controleAltera = true;
		permitePDF = false;
		try {
			if ((certificado.getCaminhoCertificado() == null) || (certificado.getCaminhoCertificado().equals(""))) {
				permitePDF = false;
			} else {
				permitePDF = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Certificado getCertificado() {
		return certificado;
	}

	public void setCertificado(Certificado certificado) {
		this.certificado = certificado;
	}

	public List<AtividadeTurma> getAtividadeTurmas() {
		return atividadeTurmas;
	}

	public void setAtividadeTurmas(List<AtividadeTurma> atividadeTurmas) {
		this.atividadeTurmas = atividadeTurmas;
	}

	public List<Certificado> getCertificadosAlunos() {


		return certificadosAlunos;
	}

	public void setCertificadosAlunos(List<Certificado> certificadosAlunos) {
		this.certificadosAlunos = certificadosAlunos;
	}

	public Boolean getPermitePDF() {
		return permitePDF;
	}

	public List<AlunoTurma> getListAlunoTurma() {
		
		aluno = (Aluno) dao.buscarPorId(Aluno.class, usuarioSessao.recuperarAluno().getId());
		
		listAlunoTurma = dao.listar(AlunoTurma.class, " controle = 1 and aluno  = "+aluno.getId());
		return listAlunoTurma;
	}

	public void setListAlunoTurma(List<AlunoTurma> listAlunoTurma) {
		this.listAlunoTurma = listAlunoTurma;
	}

	public void setPermitePDF(Boolean permitePDF) {
		this.permitePDF = permitePDF;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public boolean isControle() {
		return controle;
	}

	public void setControle(boolean controle) {
		this.controle = controle;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public List<AlunoTurma> getCursos() {
		return cursos;
	}

	public void setCursos(List<AlunoTurma> cursos) {
		this.cursos = cursos;
	}

	public boolean isControleAltera() {
		return controleAltera;
	}

	public void setControleAltera(boolean controleAltera) {
		this.controleAltera = controleAltera;
	}
	

}
