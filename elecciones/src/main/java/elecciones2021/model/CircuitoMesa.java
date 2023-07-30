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
@Table(name="lugares_votacion", schema="elecciones")
public class CircuitoMesa implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	@Column(name = "id_lugar_votacion")
	private int idLugarVotacion;
	@Column(length=50)
	private String seccion;
	@Column(length=50)
	private String circuito;
	@Column(length=50)
	private String nombre;
	@Column(length=50)
	private String direccion;
    // @Column(name = "id",length=50, unique = true, nullable = false, insertable = false, updatable = false)
	@Column(name = "mesa_desde")
	private int mesaDesde;
	@Column(name="mesa_hasta")
	private int mesaHasta;
	
    @OneToMany( fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name="lugar_votacion_id", referencedColumnName="id_lugar_votacion")
	private Set<Mesa> mesas;
	
	public int getIdLugarVotacion() {
		return idLugarVotacion;
	}
	public void setIdLugarVotacion(int idLugarVotacion) {
		this.idLugarVotacion = idLugarVotacion;
	}
	public String getSeccion() {
		return seccion;
	}
	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}
	public String getCircuito() {
		return circuito;
	}
	public void setCircuito(String circuito) {
		this.circuito = circuito;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public int getMesaDesde() {
		return mesaDesde;
	}
	public void setMesaDesde(int mesaDesde) {
		this.mesaDesde = mesaDesde;
	}
	public int getMesaHasta() {
		return mesaHasta;
	}
	public void setMesaHasta(int mesaHasta) {
		this.mesaHasta = mesaHasta;
	}
}

