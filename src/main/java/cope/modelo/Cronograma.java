package cope.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tab_cronograma")
public class Cronograma implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_cronograma")
	private Long id;
	@ManyToOne
	private Projeto projeto;
	@ManyToOne
	private ItemCronograma itemCronograma;

	public Cronograma() {
	}

	public Cronograma(Projeto projeto, ItemCronograma itemCronograma) {
		super();
		this.projeto = projeto;
		this.itemCronograma = itemCronograma;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public ItemCronograma getItemCronograma() {
		return itemCronograma;
	}

	public void setItemCronograma(ItemCronograma itemCronograma) {
		this.itemCronograma = itemCronograma;
	}
}
