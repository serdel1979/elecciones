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
@Table(name="circuitos", schema="elecciones")
public class Circuito implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	@Column(name = "id_circuito")
	private int idCircuito;
	
	private String nombre;
	
	private Double porcentaje_pondera_nacion;
	
	private Double porcentaje_pondera_provincia_muni;
	
	@OneToMany( fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name="id_circuito", referencedColumnName="id_circuito")
	private Set<CircuitoMesa> lugaresdevotacion;
	

	
	public int getIdCircuito() {
		return idCircuito;
	}

	public String getNombre() {
		return nombre;
	}



	public void setIdCircuito(int idCircuito) {
		this.idCircuito = idCircuito;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public Set<CircuitoMesa> getLugaresdevotacion() {
		return lugaresdevotacion;
	}

	public void setLugaresdevotacion(Set<CircuitoMesa> lugaresdevotacion) {
		this.lugaresdevotacion = lugaresdevotacion;
	}

	public Double getPorcentaje_pondera_nacion() {
		return porcentaje_pondera_nacion;
	}

	public void setPorcentaje_pondera_nacion(Double porcentaje_pondera_nacion) {
		this.porcentaje_pondera_nacion = porcentaje_pondera_nacion;
	}

	public Double getPorcentaje_pondera_provincia_muni() {
		return porcentaje_pondera_provincia_muni;
	}

	public void setPorcentaje_pondera_provincia_muni(Double porcentaje_pondera_provincia_muni) {
		this.porcentaje_pondera_provincia_muni = porcentaje_pondera_provincia_muni;
	}
}
