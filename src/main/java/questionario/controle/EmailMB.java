package questionario.controle;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LegendPlacement;
import org.primefaces.model.chart.PieChartModel;

import ac.controle.UsuarioSessaoMB;
import ac.modelo.AlunoTurma;
import ac.modelo.Pessoa;
import ac.services.AlunoService;
import base.modelo.Aluno;
import base.modelo.Curso;
import base.modelo.Turma;
import base.service.AlunoTurmaService;
import dao.GenericDAO;
import questionario.modelo.Email;
import questionario.modelo.Formulario;
import questionario.service.EmailService;
import questionario.service.FormularioService;
import util.EnviarEmail;
import util.ExibirMensagem;
import util.Mensagem;

@ViewScoped
@Named("EmailMB")
public class EmailMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Aluno aluno;
	private Curso curso;
	private Turma turma;
	private List<Curso> cursos;
	private Email email;
	private List<Email> listEmail;
	private List<Formulario> listFormulario;
	private String nome;
	private List<Turma> turmas;
	private List<Email> listSelecionados;

	private List<Formulario> lista;

	@Inject
	private GenericDAO<Aluno> daoAluno;

	@Inject
	private GenericDAO<Email> daoEmail;

	@Inject
	private GenericDAO<Turma> daoTurma;

	@Inject
	private GenericDAO<Formulario> daoFormulario;

	@Inject
	private GenericDAO<Curso> daoCurso;

	@Inject
	private EmailService emailService;

	@Inject
	private AlunoService alunoService;

	@Inject
	private AlunoTurmaService alunoTurmaService;

	@PostConstruct
	public void inicializar() {
		
		cursos = new ArrayList<>();
		turmas = new ArrayList<>();
		curso = new Curso();
		turma = new Turma();
		email = new Email();
		listEmail = new ArrayList<>();
		listFormulario = new ArrayList<>();
		listSelecionados = new ArrayList<>();
		preencherEmail();

		lista = daoFormulario.listaComStatus(Formulario.class);
	}

	public void preencherEmail() {
		listEmail = daoEmail.listaComStatus(Email.class);
	}

	public void libe() {
		for (Email e : listSelecionados) {
			System.out.println("emails : " + e.getAlunoTurma().getAluno().getNome());
		}
	}
 
	public void enviar() {
		
		
		
		AlunoTurma alunoTurmaLibera = new AlunoTurma();
		DateFormat df3 = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		String data = df3.format(cal.getTime());

		if (listSelecionados.size() > 0) {

			for (Email e : listSelecionados) {

				if (e.getAlunoTurma().getAluno().getUsuario() == null) {
					ExibirMensagem.exibirMensagem(
							"Aluno " + e.getAlunoTurma().getAluno().getNome() + " n�o possui e-mail cadastrado");
				} else {

					if (EnviarEmail.enviarEmail(e.getAlunoTurma().getAluno().getUsuario(),
							"Cronos - Question�rio Egressos", "Ol� " + e.getAlunoTurma().getAluno().getNome()
									+ " informamos que voc� est� liberado para responder o question�ro de egressos")) {
						
						email = e;
						email.setEnviado(true);
						email.setDataEnvio(data);
						emailService.inserirAlterar(email);

						alunoTurmaLibera = e.getAlunoTurma();
						alunoTurmaLibera.setLiberado(true);
						alunoTurmaService.inserirAlterar(alunoTurmaLibera);
						email = new Email();
						alunoTurmaLibera = new AlunoTurma();

					} else {
						ExibirMensagem.exibirMensagem(Mensagem.ERROEMAIL);
					}

				}
			}

			ExibirMensagem.exibirMensagem(Mensagem.EMAIL);
		} else {
			ExibirMensagem.exibirMensagem(Mensagem.SELECIONAALUNO);
		}

	}







	public BarChartModel getGraficoPizzaPesquisa() {

		BarChartModel model = new BarChartModel(); 
		ChartSeries chart = new ChartSeries();
        chart.setLabel("Voc� voltaria a estudar no IFPR para fazer outros cursos?");

		int sim = 0;
		int nao = 0;
		for (Formulario jps : lista) {
			if (jps.getIadc5().equals("sim")) {
				sim += 1;
				chart.set("Sim", sim);
			 } else {
				nao += 1;
				chart.set("N�o", nao);
			}

		}
 
		model.setTitle("Voc� voltaria a estudar no IFPR para fazer outros cursos?"); 
		model.setLegendPosition("ne");
	    Axis axis = model.getAxis(AxisType.X); 
	    Axis axisY = model.getAxis(AxisType.Y);
	    axisY.setLabel("Quantidade Pessoas");
	    model.addSeries(chart);

		return model;

	}

	public BarChartModel getGraficoP2() {

		BarChartModel model = new BarChartModel(); 
		ChartSeries chart = new ChartSeries();
        chart.setLabel("Voc� teria interesse em cursar uma P�s-Gradua��o ou cursos de qualifica��o profissional ofertados pelo IFPR?");

		int sim = 0;
		int nao = 0;
		for (Formulario jps : lista) {
			if (jps.getIadc6().equals("sim")) {
				sim += 1;
				chart.set("Sim", sim);
			} else {
				nao += 1;
				chart.set("N�o", nao);
			}

		}
 
		model.setTitle("Voc� teria interesse em cursar uma P�s-Gradua��o ou cursos de qualifica��o profissional ofertados pelo IFPR?");
		model.setLegendPosition("ne");
		model.setSeriesColors("207C2B");
	    Axis axis = model.getAxis(AxisType.X); 
	    Axis axisY = model.getAxis(AxisType.Y);
	    axisY.setLabel("Quantidade Pessoas");
	    model.addSeries(chart);

		return model;

	}

	public BarChartModel getGraficoP3() {

		BarChartModel model = new BarChartModel(); 
		ChartSeries chart = new ChartSeries();
        chart.setLabel("Voc� est� trabalhando na �rea atualmente?");

		int sim = 0;
		int nao = 0;
		for (Formulario jps : lista) {
			if (jps.getIp3().equals("sim")) {
				sim += 1;
				chart.set("Sim", sim);
			} else {
				nao += 1;
				chart.set("N�o", nao);
			}

		}
		model.setTitle("Voc� est� trabalhando na �rea atualmente?"); 
		model.setLegendPosition("ne");
	    Axis axis = model.getAxis(AxisType.X); 
	    Axis axisY = model.getAxis(AxisType.Y);
	    axisY.setLabel("Quantidade Pessoas");
	    model.addSeries(chart);

		return model;

	}

	public BarChartModel getGraficoP4() {

		BarChartModel model = new BarChartModel(); 
		ChartSeries chart = new ChartSeries();
        chart.setLabel("Na contrata��o de um egresso de sua �rea, o que � relevante no processo de sele��o?");


		int r1 = 0;
		int r2 = 0;
		int r3 = 0;
		int r4 = 0;
		int r5 = 0;
		for (Formulario jps : lista) {
			if (jps.getIadc2().equals("O nome da Institui��o de Ensino onde estudou")) {
				r1 += 1;
				chart.set("O nome da Institui��o de Ensino onde estudou", r1);
			} else if (jps.getIadc2().equals("As respostas ao teste de sele��o, ao qual foi submetido")) {
				r2 += 1;
				chart.set("As respostas ao teste de sele��o, ao qual foi submetido", r2);
			} else if (jps.getIadc2().equals("A forma��o te�rica")) {
				r3 += 1;
				chart.set("A forma��o te�rica", r3);
			} else if (jps.getIadc2().equals("A experi�ncia pr�tica")) {
				r4 += 1;
				chart.set("A experi�ncia pr�tica", r4);
			} else if (jps.getIadc2().equals("Vis�o sist�mica")) {
				r5 += 1;
				chart.set("Vis�o sist�mica", r5);
			}

		}
		model.setTitle("Na contrata��o de um egresso de sua �rea, o que � relevante no processo de sele��o?");
		model.setSeriesColors("207C2B");
		model.setLegendPosition("ne");
	    Axis axis = model.getAxis(AxisType.X); 
	    Axis axisY = model.getAxis(AxisType.Y);
	    axisY.setLabel("Quantidade Pessoas");
	    model.addSeries(chart);

		return model; 

	}

	public BarChartModel getGraficoP5() {

		BarChartModel model = new BarChartModel(); 
		ChartSeries chart = new ChartSeries();
        chart.setLabel("O que tem faltado aos rec�m-formados em sua �rea?");

		int r1 = 0;
		int r2 = 0;
		int r3 = 0;
		int r4 = 0;
		int r5 = 0;
		int r6 = 0;
		for (Formulario jps : lista) {
			if (jps.getIadc3().equals("Maior embasamento conceitual")) {
				r1 += 1;
				chart.set("Maior embasamento conceitual", r1);
			} else if (jps.getIadc3().equals("Maior embasamento t�cnico")) {
				r2 += 1;
				chart.set("Maior embasamento t�cnico", r2);
			} else if (jps.getIadc3().equals("Maior embasamento pr�tico")) {
				r3 += 1;
				chart.set("Maior embasamento pr�tico", r3);
			} else if (jps.getIadc3().equals("Maior aproxima��o com as necessidades da ind�sstria")) {
				r4 += 1;
				chart.set("Maior aproxima��o com as necessidades da ind�sstria", r4);
			} else if (jps.getIadc3().equals("Maior capacidade de lideran�a")) {
				r5 += 1;
				chart.set("Maior capacidade de lideran�a", r5);
			} else if (jps.getIadc3().equals("Maior vis�o sist�mica")) {
				r6 += 1;
				chart.set("Maior vis�o sist�mica", r6);
			}

		}
		model.setTitle("O que tem faltado aos rec�m-formados em sua �rea?");
		model.setLegendPosition("ne");
	    Axis axis = model.getAxis(AxisType.X); 
	    Axis axisY = model.getAxis(AxisType.Y);
	    axisY.setLabel("Quantidade Pessoas");
	    model.addSeries(chart);

		return model; 
	}

	public BarChartModel getGraficoP6() {

		BarChartModel model = new BarChartModel(); 
		ChartSeries chart = new ChartSeries();
        chart.setLabel("Os conhecimentos adquiridos durante o curso foram importantes para forma��o profissional?");

		int r1 = 0;
		int r2 = 0;
		int r3 = 0;
		int r4 = 0;
		for (Formulario jps : lista) {
			if (jps.getIc7().equals("muito satisfatorio")) {
				r1 += 1;
				chart.set("Muito Satisfat�rio", r1);
			} else if (jps.getIc7().equals("satisfatorio")) {
				r2 += 1;
				chart.set("Satisfat�rio", r2);
			} else if (jps.getIc7().equals("insatisfatario")) {
				r3 += 1;
				chart.set("Insatisfat�rio", r3);
			} else if (jps.getIc7().equals("nao sei responder")) {
				r4 += 1;
				chart.set("N�o sei Responder", r4);
			}

		}
		model.setTitle("Os conhecimentos adquiridos durante o curso foram importantes para forma��o profissional?");
		model.setSeriesColors("207C2B");
		model.setLegendPosition("ne");
	    Axis axis = model.getAxis(AxisType.X); 
	    Axis axisY = model.getAxis(AxisType.Y);
	    axisY.setLabel("Quantidade Pessoas");
	    model.addSeries(chart);

		return model;

	}

	public BarChartModel getGraficoP7() {

		BarChartModel model = new BarChartModel(); 
		ChartSeries chart = new ChartSeries();
        chart.setLabel("Quais foram suas principais dificuldades logo ap�s a conclus�o do curso?");

		int r1 = 0;
		int r2 = 0;
		int r3 = 0;
		int r4 = 0;
		for (Formulario jps : lista) {
			if (jps.getIc10().equals("encontrar emprego na �rea")) {
				r1 += 1;
				chart.set("Encontrar emprego na �rea", r1);
			} else if (jps.getIc10().equals("adequa��o salarial")) {
				r2 += 1;
				chart.set("Adequa��o salarial", r2);
			} else if (jps.getIc10().equals("ser promovido")) {
				r3 += 1;
				chart.set("ser promovido", r3);
			} else if (jps.getIc10().equals("tempo para se dedicar a uma qualifica��o")) {
				r4 += 1;
				chart.set("Tempo para se dedicar a uma qualifica��o", r4);
			}

		}
		model.setTitle("Quais foram suas principais dificuldades logo ap�s a conclus�o do curso?"); 
		model.setLegendPosition("ne");
	    Axis axis = model.getAxis(AxisType.X); 
	    Axis axisY = model.getAxis(AxisType.Y);
	    axisY.setLabel("Quantidade Pessoas");
	    model.addSeries(chart);

		return model;

	}
	
	
	public BarChartModel getGraficoP8() {

		BarChartModel model = new BarChartModel(); 
		ChartSeries chart = new ChartSeries();
        chart.setLabel("A dura��o do curso foi suficiente?");

		int r1 = 0;
		int r2 = 0;
		int r3 = 0; 
		for (Formulario jps : lista) {
			if (jps.getIc4().equals("sim")) {
				r1 += 1;
				chart.set("Sim", r1);
			} else if (jps.getIc4().equals("nao")) {
				r2 += 1;
				chart.set("N�o", r2);
			} else if (jps.getIc4().equals("nao sei responder")) {
				r3 += 1;
				chart.set("N�o sei responder", r3);
			}  

		} 
		model.setTitle("A dura��o do curso foi suficiente?");
		model.setSeriesColors("207C2B");
		model.setLegendPosition("ne");
	    Axis axis = model.getAxis(AxisType.X); 
	    Axis axisY = model.getAxis(AxisType.Y);
	    axisY.setLabel("Quantidade Pessoas");
	    model.addSeries(chart);

		return model;

	}
	
	public BarChartModel getGraficoP9() {

		BarChartModel model = new BarChartModel(); 
		ChartSeries chart = new ChartSeries();
        chart.setLabel("Como foi a parte te�rica do curso?");

		int r1 = 0;
		int r2 = 0;
		int r3 = 0; 
		int r4 = 0; 
		for (Formulario jps : lista) {
			if (jps.getIc6().equals("muito satisfatorio")) {
				r1 += 1;
				chart.set("Muito satisfat�rio", r1);
			} else if (jps.getIc6().equals("satisfatorio")) {
				r2 += 1;
				chart.set("Satisfat�rio", r2);
			} else if (jps.getIc6().equals("insatisfatario")) {
				r3 += 1;
				chart.set("Insatisfat�rio", r3);
			}  
			else if (jps.getIc6().equals("nao sei responder")) {
				r4 += 1;
				chart.set("N�o sei responder", r4);
			}  

		}
		model.setTitle("Como foi a parte te�rica do curso?");
		model.setLegendPosition("ne");
	    Axis axis = model.getAxis(AxisType.X); 
	    Axis axisY = model.getAxis(AxisType.Y);
	    axisY.setLabel("Quantidade Pessoas");
	    model.addSeries(chart);

		return model;

	}
	
	public BarChartModel getGraficoP10() {

		BarChartModel model = new BarChartModel(); 
		ChartSeries chart = new ChartSeries();
        chart.setLabel("Como voc� avalia o nivel de exig�ncia do curso?");

		int r1 = 0;
		int r2 = 0;
		int r3 = 0; 
		for (Formulario jps : lista) {
			if (jps.getIc8().equals("deveria ter exigido muito mais de mim")) {
				r1 += 1;
				chart.set("Deveria ter exigido muito mais de mim", r1);
			} else if (jps.getIc8().equals("deveria ter exigido um pouco mais de mim")) {
				r2 += 1;
				chart.set("Deveria ter exigido um pouco mais de mim", r2);
			} else if (jps.getIc8().equals("deveria ter exigido menos de mim")) {
				r3 += 1;
				chart.set("Deveria ter exigido menos de mim", r3);
			}  
			 

		}
		model.setTitle("Como voc� avalia o nivel de exig�ncia do curso?");
		model.setSeriesColors("207C2B");
		model.setLegendPosition("ne");
	    Axis axis = model.getAxis(AxisType.X); 
	    Axis axisY = model.getAxis(AxisType.Y);
	    axisY.setLabel("Quantidade Pessoas");
	    model.addSeries(chart);

		return model;

	}
	
	public BarChartModel getGraficoP11() {

		BarChartModel model = new BarChartModel(); 
		ChartSeries chart = new ChartSeries();
        chart.setLabel("Qual foi a principal contribui��o do curso?");
        
		int r1 = 0;
		int r2 = 0;
		int r3 = 0; 
		int r4 = 0; 
		for (Formulario jps : lista) {
			if (jps.getIc9().equals("a obten��o de diploma de n�vel t�cnico")) {
				r1 += 1;
				chart.set("A obten��o de diploma de n�vel t�cnico", r1);
			} else if (jps.getIc9().equals("a aquisi��o de cultura geral")) {
				r2 += 1;
				chart.set("A aquisi��o de cultura geral", r2);
			} else if (jps.getIc9().equals("a aquisi�aoo de forma��o profissional e te�rica")) {
				r3 += 1;
				chart.set("A aquisi�aoo de forma��o profissional e te�rica", r3);
			}  else if (jps.getIc9().equals("melhores perspectivas de ganhos materiais")) {
				r4 += 1;
				chart.set("Melhores perspectivas de ganhos materiais", r4);
			}  
			 

		}
		model.setTitle("Qual foi a principal contribui��o do curso?"); 
		model.setLegendPosition("ne");
	    Axis axis = model.getAxis(AxisType.X); 
	    Axis axisY = model.getAxis(AxisType.Y);
	    axisY.setLabel("Quantidade Pessoas");
	    model.addSeries(chart);

		return model;

	}
	
	
	public BarChartModel getGraficoP12() {

		BarChartModel model = new BarChartModel(); 
		ChartSeries chart = new ChartSeries();
        chart.setLabel("De modo geral, os professores tinham dom�nio do conte�do das disciplinas que deram?");

		int r1 = 0;
		int r2 = 0;
		int r3 = 0; 
		int r4 = 0; 
		for (Formulario jps : lista) {
			if (jps.getIcd1().equals("muito satisfatori")) {
				r1 += 1;
				chart.set("Muito satisfat�ri", r1);
			} else if (jps.getIcd1().equals("satisfatorio")) {
				r2 += 1;
				chart.set("satisfat�rio", r2);
			} else if (jps.getIcd1().equals("insatisfatario")) {
				r3 += 1;
				chart.set("Insatisfat�rio", r3);
			}  else if (jps.getIcd1().equals("nao sei responder")) {
				r4 += 1;
				chart.set("N�o sei responder", r4);
			}  
			 

		} 
		model.setTitle("De modo geral, os professores tinham dom�nio do conte�do das disciplinas que deram?");
		model.setSeriesColors("207C2B");
		model.setLegendPosition("ne");
	    Axis axis = model.getAxis(AxisType.X); 
	    Axis axisY = model.getAxis(AxisType.Y);
	    axisY.setLabel("Quantidade Pessoas"); 
	    model.addSeries(chart);

		return model;

	}
	
	public BarChartModel getGraficoP13() {

		BarChartModel model = new BarChartModel(); 
		ChartSeries chart = new ChartSeries();
        chart.setLabel("De modo geral, como foram os recursos utilizados pelos professores durante as aulas?");

		int r1 = 0;
		int r2 = 0;
		int r3 = 0; 
		int r4 = 0; 
		for (Formulario jps : lista) {
			if (jps.getIcd2().equals("muito satisfatori")) {
				r1 += 1;
				chart.set("Muito satisfat�ri", r1);
			} else if (jps.getIcd2().equals("satisfatorio")) {
				r2 += 1;
				chart.set("satisfat�rio", r2);
			} else if (jps.getIcd2().equals("insatisfatario")) {
				r3 += 1;
				chart.set("Insatisfat�rio", r3);
			}  else if (jps.getIcd2().equals("nao sei responder")) {
				r4 += 1;
				chart.set("N�o sei responder", r4);
			}  
			 

		}
 
		model.setTitle("De modo geral, como foram os recursos utilizados pelos professores durante as aulas?"); 
		model.setLegendPosition("ne");
	    Axis axis = model.getAxis(AxisType.X); 
	    Axis axisY = model.getAxis(AxisType.Y);
	    axisY.setLabel("Quantidade Pessoas");
	    model.addSeries(chart);

		return model;
	}
	
	public BarChartModel getGraficoP14() {

		BarChartModel model = new BarChartModel(); 
		ChartSeries chart = new ChartSeries();
        chart.setLabel("De Modo geral, como foi a rela��o do professor com os alunos?");


		int r1 = 0;
		int r2 = 0;
		int r3 = 0; 
		int r4 = 0; 
		for (Formulario jps : lista) {
			if (jps.getIcd3().equals("muito satisfatori")) {
				r1 += 1;
				chart.set("Muito satisfat�ri", r1);
			} else if (jps.getIcd3().equals("satisfatorio")) {
				r2 += 1;
				chart.set("satisfat�rio", r2);
			} else if (jps.getIcd3().equals("insatisfatario")) {
				r3 += 1;
				chart.set("Insatisfat�rio", r3);
			}  else if (jps.getIcd3().equals("nao sei responder")) {
				r4 += 1;
				chart.set("N�o sei responder", r4);
			}  
			 

		} 
		model.setTitle("De Modo geral, como foi a rela��o do professor com os alunos?");
		model.setSeriesColors("207C2B");
		model.setLegendPosition("ne");
	    Axis axis = model.getAxis(AxisType.X); 
	    Axis axisY = model.getAxis(AxisType.Y);
	    axisY.setLabel("Quantidade Pessoas");
	    model.addSeries(chart);

		return model;
	  

	}
	
	public BarChartModel getGraficoP15() {

		BarChartModel model = new BarChartModel(); 
		ChartSeries chart = new ChartSeries();
        chart.setLabel("Voc� j� tinha uma profiss�o antes de fazer o curso?");

		int sim = 0;
		int nao = 0;
		for (Formulario jps : lista) {
			if (jps.getIp1().equals("sim")) {
				sim += 1;
				chart.set("Sim", sim);
			} else {
				nao += 1;
				chart.set("N�o", nao);
			}

		}
		model.setTitle("Voc� j� tinha uma profiss�o antes de fazer o curso?"); 
		model.setLegendPosition("ne");
	    Axis axis = model.getAxis(AxisType.X); 
	    Axis axisY = model.getAxis(AxisType.Y);
	    axisY.setLabel("Quantidade Pessoas");
	    model.addSeries(chart);

		return model;

	}
	
	public BarChartModel getGraficoP16() {

		BarChartModel model = new BarChartModel(); 
		ChartSeries chart = new ChartSeries();
        chart.setLabel("Voc� estava trabalhando na �rea durante o curso?");

		int sim = 0;
		int nao = 0;
		for (Formulario jps : lista) {
			if (jps.getIp2().equals("sim")) {
				sim += 1;
				chart.set("Sim", sim);
			} else {
				nao += 1;
				chart.set("N�o", nao);
			}

		} 
		model.setTitle("Voc� estava trabalhando na �rea durante o curso?");
		model.setSeriesColors("207C2B");
		model.setLegendPosition("ne");
	    Axis axis = model.getAxis(AxisType.X); 
	    Axis axisY = model.getAxis(AxisType.Y);
	    axisY.setLabel("Quantidade Pessoas");
	    model.addSeries(chart);

		return model;

	}
	
	public BarChartModel getGraficoP17() {

		BarChartModel model = new BarChartModel(); 
		ChartSeries chart = new ChartSeries();
        chart.setLabel("Voc� est� trabalhando na �rea atualmente?");

		int sim = 0;
		int nao = 0;
		for (Formulario jps : lista) {
			if (jps.getIp3().equals("sim")) {
				sim += 1;
				chart.set("Sim", sim);
			} else {
				nao += 1;
				chart.set("N�o", nao);
			}

		}
 
		model.setTitle("Voc� est� trabalhando na �rea atualmente?"); 
		model.setLegendPosition("ne");
	    Axis axis = model.getAxis(AxisType.X); 
	    Axis axisY = model.getAxis(AxisType.Y);
	    axisY.setLabel("Quantidade Pessoas");
	    model.addSeries(chart);

		return model;

	}
	
	public BarChartModel getGraficoP18() {

		BarChartModel model = new BarChartModel(); 
		ChartSeries chart = new ChartSeries();
        chart.setLabel("Na sua opini�o um estudante rec�m-formado em sua �rea que tenha dedicado todo o tempo de estudo somente �s atividades acad�micas, leva mais tempo para se adaptar ao mercado do que um outro que j� trabalhava na �rea durante o dia e estudava a noite?");

		int sim = 0;
		int nao = 0;
		for (Formulario jps : lista) {
			if (jps.getIadc1().equals("Sim, pois o contato com a pr�tica � um grande diferencial")) {
				sim += 1;
				chart.set("Sim, pois o contato com a pr�tica � um grande diferencial", sim);
			//	graficoP2.set("Sim, pois o contato com a pr�tica � um grande diferencial", sim);
			} else if (jps.getIadc1().equals("N�o, pois os conhecimentos adquiridos com as atividades acad�micas s�o suficientes para atuar no mercado")) {
				nao += 1;
				chart.set("N�o, pois os conhecimentos adquiridos com as atividades acad�micas s�o suficientes para atuar no mercado", nao);
				//graficoP2.set("N�o, pois os conhecimentos adquiridos com as atividades acad�micas s�o suficientes para atuar no mercado", nao);
				     
			} 

		}
		model.setTitle("Na sua opini�o um estudante rec�m-formado em sua �rea que tenha dedicado todo o tempo de estudo somente �s atividades acad�micas, leva mais tempo para se adaptar ao mercado do que um outro que j� trabalhava na �rea durante o dia e estudava a noite?");
		model.setSeriesColors("207C2B");
		model.setLegendPosition("ne");
	    Axis axis = model.getAxis(AxisType.X); 
	    Axis axisY = model.getAxis(AxisType.Y);
	    axisY.setLabel("Quantidade Pessoas");
	    model.addSeries(chart);
		

		return model;

	}
	
	 
	public BarChartModel getGraficoP19() {

		BarChartModel model = new BarChartModel(); 
		ChartSeries chart = new ChartSeries();
        chart.setLabel("Em sua opini�o, quanto tempo leva um egresso, rec�m-formado em sua area, para tornar-se altamente produtivo, ap�s ser contratado por sua empresa?");

		int r1 = 0;
		int r2 = 0;
		int r3 = 0;
		int r4 = 0;
		int r5 = 0;
		for (Formulario jps : lista) {
			if (jps.getIadc4().equals("Imediatamente ap�s a contrata��o")) {
				r1 += 1;
				chart.set("Imediatamente ap�s a contrata��o", r1);
			} else if (jps.getIadc4().equals("Algumas semanas")) {
				r2 += 1;
				chart.set("Algumas semanas", r2);
			} else if (jps.getIadc4().equals("Alguns meses")) {
				r3 += 1;
				chart.set("Alguns meses", r3);
			} else if (jps.getIadc4().equals("Entre 1 e 2 anos")) {
				r4 += 1;
				chart.set("Entre 1 e 2 anos", r4);
			} else if (jps.getIadc4().equals("Mais de 2 anos")) {
				r5 += 1;
				chart.set("Mais de 2 anos", r5);
			}

		}
		    model.setTitle("Em sua opini�o, quanto tempo leva um egresso, rec�m-formado em sua area, para tornar-se altamente produtivo, ap�s ser contratado por sua empresa?");
	        model.setLegendPosition("ne");
	        Axis axis = model.getAxis(AxisType.X); 
	        Axis axisY = model.getAxis(AxisType.Y);
	        axisY.setLabel("Quantidade Pessoas");
	        model.addSeries(chart);

		return model;

	}
	
	
	
	
	

	
	
	
	
	
	
	
	
	

	public List<Curso> completarCursos(String str) {
		cursos = daoCurso.listaComStatus(Curso.class);
		List<Curso> cursosSelecionados = new ArrayList<>();
		for (Curso cur : cursos) {
			if (cur.getNome().toLowerCase().startsWith(str)) {
				cursosSelecionados.add(cur);
			}
		}
		return cursosSelecionados;
	}

	public void buscarGraficoTurma() throws ParseException {
		
		lista = daoFormulario.listar(Formulario.class, "  alunoTurma.turma.id= " + turma.getId());
		if (lista.size() == 0) {

			FacesContext context = FacesContext.getCurrentInstance();
			Application application = context.getApplication();
			ViewHandler viewHandler = application.getViewHandler();
			UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
			context.setViewRoot(viewRoot);

			ExibirMensagem.exibirMensagem(Mensagem.GRAFICO);
		}

	}
	
	
	public void buscarGraficoCurso() throws ParseException {
		
		lista = daoFormulario.listar(Formulario.class, " alunoTurma.turma.curso.id= " + curso.getId());
		if (lista.size() == 0) {

			FacesContext context = FacesContext.getCurrentInstance();
			Application application = context.getApplication();
			ViewHandler viewHandler = application.getViewHandler();
			UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
			context.setViewRoot(viewRoot);

			ExibirMensagem.exibirMensagem(Mensagem.GRAFICO);
		}

	}

	public void buscarTurma() {
		listEmail = daoEmail.listar(Email.class, " alunoTurma.turma.id= " + turma.getId());
	}

	public void buscar() {
		listEmail = daoEmail.listar(Email.class, " alunoTurma.turma.curso.id= " + curso.getId());
	}

	public List<Turma> completarTurma(String str) {
		turmas = daoTurma.listaComStatus(Turma.class);
		List<Turma> AlunoSelecionados = new ArrayList<>();
		for (Turma cur : turmas) {
			if (cur.getDescricao().toLowerCase().startsWith(str)) {
				AlunoSelecionados.add(cur);
			}
		}
		return AlunoSelecionados;
	}

	public List<Email> getListEmail() {
		return listEmail;
	}

	public void setListEmail(List<Email> listEmail) {
		this.listEmail = listEmail;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public List<Email> getListSelecionados() {
		return listSelecionados;
	}

	public void setListSelecionados(List<Email> listSelecionados) {
		this.listSelecionados = listSelecionados;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

	public Email getEmail() {
		return email;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public void setEmail(Email email) {
		this.email = email;
	}
	

}
