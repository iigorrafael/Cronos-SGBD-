package protocolo.modelo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tab_setor")
public class Setor {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	
	private String descricao;
	
	private Date dataCadastro;
	
	private boolean status;
	
	
	

	public boolean isStatus() {
		return status;
	}




	public void setStatus(boolean status) {
		this.status = status;
	}




	public Long getId() {
		return id;
	}
	
	


	public Date getDataCadastro() {
		return dataCadastro;
	}




	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}




	public void setId(Long id) {
		this.id = id;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
