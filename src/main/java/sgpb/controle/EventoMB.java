package sgpb.controle;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ac.modelo.Movimentacao;
import ac.modelo.Permissao;
import dao.DAOGenerico;
import sgpb.modelo.Evento;
import util.ExibirMensagem;
import util.FecharDialog;
import util.Mensagem;

@ViewScoped
@ManagedBean(name="eventoMB")
public class EventoMB {

	private Evento evento;
	private List<Evento> eventos;
	private DAOGenerico dao;
	private Permissao permissao;
	private Movimentacao movs;

	public EventoMB() {
		dao = new DAOGenerico();
		eventos = new ArrayList<>();
		criarNovoEvento();
		preencherListaEvento();
	}

	public void salvar() {
		try {
			if (evento.getId() == null) {
				dao.inserir(evento);
				criarNovoEvento();
				ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
				preencherListaEvento();
			} else {
				ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
				dao.alterar(evento);
				criarNovoEvento();
				preencherListaEvento();
			}
		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
		}
		FecharDialog.fecharDialogEvento();
	}
	
//	public void inativar(Evento evento) {
//		try {
//						
//			List<AlunoTurma> listAlunoTurmas = new ArrayList<>();
//			listAlunoTurmas = dao.listar(AlunoTurma.class, " aluno.id ="+movimentacao.getAlunoTurma().getAluno().getId());
//			List<Movimentacao> m = new ArrayList<>();
//			
//			
//			for(AlunoTurma a : listAlunoTurmas){
//				inavitarMovimentacao(a);
//			}
//			
//		
//		
//		} catch (Exception e) {
//			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
//		}
//		criarNovoObjetoAluno();
//	}
	
	public void preencherListaEvento() {
		eventos = dao.listar(Evento.class);
	}

	public void criarNovoEvento() {
		evento = new Evento();
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public DAOGenerico getDao() {
		return dao;
	}

	public void setDao(DAOGenerico dao) {
		this.dao = dao;
	}

	public Permissao getPermissao() {
		return permissao;
	}

	public void setPermissao(Permissao permissao) {
		this.permissao = permissao;
	}

	public Movimentacao getMovs() {
		return movs;
	}

	public void setMovs(Movimentacao movs) {
		this.movs = movs;
	}

	/*
	 * public void inativar(Atividade atividade) { permiteInativar = new
	 * PermiteInativar(); try { if
	 * (permiteInativar.verificarAtividadeComAtividadeTurma(atividade.getId()))
	 * { atividade.setStatus(false); dao.alterar(atividade);
	 * preencherListaAtividade();
	 * ExibirMensagem.exibirMensagem(Mensagem.SUCESSO); } else {
	 * ExibirMensagem.exibirMensagem(Mensagem.ATIVIDADE_COM_ATIVIDADE_TURMA); }
	 * 
	 * } catch (Exception e) { ExibirMensagem.exibirMensagem(Mensagem.ERRO); } }
	 */

}
