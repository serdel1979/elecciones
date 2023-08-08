package elecciones2021.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="lista_interna", schema="elecciones")

public class ListaInterna implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	@Column(name = "id_lista_interna")
	private int idListaInterna;
	
	@Column(name = "descripcion")
	private String descripcion;

	
	@Column(name = "nro_lista")
	private String nro_lista;



	@Column(name = "permite_diputados_nacionales", columnDefinition = "boolean default true")
	private boolean allowDipNac;
	

	@Column(name = "permite_legisladores_provinciales", columnDefinition = "boolean default true")
	private boolean allowLegProv;
	
	@Column(name = "permite_parlamentarios_mercosur_reg", columnDefinition = "boolean default true")
	private boolean allowParlamentariosMercosurReg;
	
	@Column(name = "permite_senadores_nacionales", columnDefinition = "boolean default true")
	private boolean allowSenadores;
	
	
	@Column(name = "permite_intendente", columnDefinition = "boolean default true")
	private boolean allowIntendente;

	
	@Column(name = "permite_parlamentarios_mercosur", columnDefinition = "boolean default true")
	private boolean allowParlamentarios;
	
	@Column(name = "permite_gobernador", columnDefinition = "boolean default true")
	private boolean allowGobernador;

	@Column(name = "permite_presidente", columnDefinition = "boolean default true")
	private boolean allowPresidente;


	
    /*@OneToMany( fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name="lista_interna_id", referencedColumnName="id_lista_interna")
    @JsonIgnore
	private Set<Escrutinio> escrutinios;*/


	@Column(name = "color")
	private String color;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getTexto_formateado() {
		return texto_formateado;
	}

	public void setTexto_formateado(String texto_formateado) {
		this.texto_formateado = texto_formateado;
	}

	@Column(name = "texto_formateado")
	private String texto_formateado;

    @Column(name = "nro_orden", columnDefinition = "bigint")
    private Long nroOrden;

	public int getIdListaInterna() {
		return idListaInterna;
	}

	public String getDescripcion() {
		return descripcion;
	}



	/*public Set<Escrutinio> getEscrutinios() {
		return escrutinios;
	}*/

	public void setIdListaInterna(int idListaInterna) {
		this.idListaInterna = idListaInterna;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	public boolean isAllowDipNac() {
		return allowDipNac;
	}

	public void setAllowDipNac(boolean allowDipNac) {
		this.allowDipNac = allowDipNac;
	}

	public boolean isAllowLegProv() {
		return allowLegProv;
	}

	public void setAllowLegProv(boolean allowLegProv) {
		this.allowLegProv = allowLegProv;
	}

	public boolean isAllowParlamentariosMercosurReg() {
		return allowParlamentariosMercosurReg;
	}

	public void setAllowParlamentariosMercosurReg(boolean allowConcejales) {
		this.allowParlamentariosMercosurReg = allowConcejales;
	}

	public String getNro_lista() {
		return nro_lista;
	}

	public void setNro_lista(String nro_lista) {
		this.nro_lista = nro_lista;
	}

	public boolean isAllowSenadores() {
		return allowSenadores;
	}

	public void setAllowSenadores(boolean allowSenadores) {
		this.allowSenadores = allowSenadores;
	}

	public boolean isAllowIntendente() {
		return allowIntendente;
	}

	public void setAllowIntendente(boolean allowIntendente) {
		this.allowIntendente = allowIntendente;
	}

	public boolean isAllowParlamentarios() {
		return allowParlamentarios;
	}

	public void setAllowParlamentarios(boolean allowParlamentarios) {
		this.allowParlamentarios = allowParlamentarios;
	}

	public boolean isAllowGobernador() {
		return allowGobernador;
	}

	public void setAllowGobernador(boolean allowGobernador) {
		this.allowGobernador = allowGobernador;
	}

	public boolean isAllowPresidente() {
		return allowPresidente;
	}

	public void setAllowPresidente(boolean allowPresidente) {
		this.allowPresidente = allowPresidente;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getNro_orden() {
		return nroOrden;
	}

	public void setNro_orden(Long nro_orden) {
		this.nroOrden = nro_orden;
	}




	/*public void setEscrutinios(Set<Escrutinio> escrutinios) {
		this.escrutinios = escrutinios;
	}*/
}
