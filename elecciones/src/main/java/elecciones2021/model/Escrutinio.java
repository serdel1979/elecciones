package elecciones2021.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="escrutinio", schema="elecciones")

public class Escrutinio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	@Column(name = "id_escrutinio")
	private int idEscrutinio;

	@Column(name = "id_listainterna")
	private int idListaInterna;
	
	@Column(name = "total_votos_diputados_nacionales")
	private int totalVotosDiputadosNacionales;
	
	@Column(name = "total_votos_legisladores_provinciales")
	private int totalVotosLegisladoresProvinciales;

	@Column(name = "total_votos_parlamentarios_mercosur_reg")
	private int totalVotosParlamentariosRercosurReg;
	
	
	@Column(name = "total_votos_senadores")
	private int totalVotosSenadores;

	@Column(name = "total_votos_intendente")
	private int totalVotosIntendente;
	
	@Column(name = "total_votos_gobernador")
	private int totalVotosGobernador;
	
	@Column(name = "total_votos_parlamentarios_mercosur")
	private int totalVotosParlamentariosMercosur;
	
	@Column(name = "total_votos_presidente")
	private int totalVotosPresidente;
	
	
	public int getTotalVotosSenadores() {
		return totalVotosSenadores;
	}

	public void setTotalVotosSenadores(int totalVotosSenadores) {
		this.totalVotosSenadores = totalVotosSenadores;
	}

	public int getTotalVotosIntendente() {
		return totalVotosIntendente;
	}

	public void setTotalVotosIntendente(int totalVotosIntendente) {
		this.totalVotosIntendente = totalVotosIntendente;
	}

	public int getTotalVotosGobernador() {
		return totalVotosGobernador;
	}

	public void setTotalVotosGobernador(int totalVotosGobernador) {
		this.totalVotosGobernador = totalVotosGobernador;
	}

	public int getTotalVotosParlamentariosMercosur() {
		return totalVotosParlamentariosMercosur;
	}

	public void setTotalVotosParlamentariosMercosur(int totalVotosParlamentariosMercosur) {
		this.totalVotosParlamentariosMercosur = totalVotosParlamentariosMercosur;
	}

	public int getTotalVotosPresidente() {
		return totalVotosPresidente;
	}

	public void setTotalVotosPresidente(int totalVotosPresidente) {
		this.totalVotosPresidente = totalVotosPresidente;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getIdEscrutinio() {
		return idEscrutinio;
	}

	public void setIdEscrutinio(int idEscrutinio) {
		this.idEscrutinio = idEscrutinio;
	}

	public int getIdListaInterna() {
		return idListaInterna;
	}

	public void setIdListaInterna(int idListaInterna) {
		this.idListaInterna = idListaInterna;
	}

	public int getTotalVotosDiputadosNacionales() {
		return totalVotosDiputadosNacionales;
	}

	public int getTotalVotosLegisladoresProvinciales() {
		return totalVotosLegisladoresProvinciales;
	}

	public int getTotalVotosParlamentariosMercosurReg() {
		return totalVotosParlamentariosRercosurReg;
	}

	public void setTotalVotosDiputadosNacionales(int totalVotosDiputadosNacionales) {
		this.totalVotosDiputadosNacionales = totalVotosDiputadosNacionales;
	}

	public void setTotalVotosLegisladoresProvinciales(int totalVotosLegisladoresProvinciales) {
		this.totalVotosLegisladoresProvinciales = totalVotosLegisladoresProvinciales;
	}

	public void setTotalVotosParlamentariosMercosurReg(int totalVotosConcejales) {
		this.totalVotosParlamentariosRercosurReg = totalVotosConcejales;
	}



}
