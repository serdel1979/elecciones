package elecciones2021.model;

import java.io.Serializable;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.OneToMany;
import javax.persistence.Table;




@Entity
@Table(name="agrupacion_politica", schema="elecciones")

public class AgrupacionPolitica implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	@Column(name = "id_agrupacion_politica")
	private int idAgrupacionPolitica;

	@Column(name = "descripcion")
	private String descripcion;

	

	
    @OneToMany( fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name="agrupacion_politica_id", referencedColumnName="id_agrupacion_politica")
	private Set<ListaInterna> listaInterna;



	public int getIdAgrupacionPolitica() {
		return idAgrupacionPolitica;
	}



	public String getDescripcion() {
		return descripcion;
	}



	public Set<ListaInterna> getListaInterna() {
		return listaInterna;
	}



	public void setIdAgrupacionPolitica(int idAgrupacionPolitica) {
		this.idAgrupacionPolitica = idAgrupacionPolitica;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	public void setListaInterna(Set<ListaInterna> listaInterna) {
		this.listaInterna = listaInterna;
	}


}
