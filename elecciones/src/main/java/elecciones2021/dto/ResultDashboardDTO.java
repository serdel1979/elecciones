package elecciones2021.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultDashboardDTO {
    private static ResultDashboardDTO resultDashboardDTO = null;
    long totalTables;
    long totalTelegramsLoaded;

	List<Map<String, Object>> totalesxCircuito;
	
	List<Map<String, Object>> totalesxDiputadosNacionales;
	
	List<Map<String, Object>> totalesxLegisladoresProvinciales;
	
	List<Map<String, Object>> totalesxConcejales;
	
	List<Map<String, Object>> totalesxPresidente;
	
	List<Map<String, Object>> totalesxGobernador;
	
	List<Map<String, Object>> totalesxIntendente;
	
	List<Map<String, Object>> totalesxParlamentarios;
	

    public int getNroTable() {
        return nroTable;
    }

    public void setNroTable(int nroTable) {
        this.nroTable = nroTable;
    }

    int nroTable;
    public  static ResultDashboardDTO getResultDashboardDTO() {

        if (resultDashboardDTO==null) {
            resultDashboardDTO = new ResultDashboardDTO();
        }
        return resultDashboardDTO;
    }
    
    public ResultDashboardDTO()
    {
    	this.totalesxCircuito = new ArrayList<Map<String, Object>>();
    	
    	this.totalesxDiputadosNacionales = new ArrayList<Map<String, Object>>();
    	
    	this.totalesxLegisladoresProvinciales = new ArrayList<Map<String, Object>>();
    	
    	this.totalesxConcejales = new ArrayList<Map<String, Object>>();
    	
    	this.totalesxPresidente = new  ArrayList<Map<String, Object>>();
    	
    	this.totalesxParlamentarios = new ArrayList<Map<String, Object>>();
    	
    	this.totalesxIntendente = new ArrayList<Map<String, Object>>();
    	
    	this.totalesxGobernador = new  ArrayList<Map<String, Object>>();
    	
    }

    public long getTotalTables() {
        return totalTables;
    }

    public void setTotalTables(long totalTables) {
        this.totalTables = totalTables;
    }

    public long getTotalTelegramsLoaded() {
        return totalTelegramsLoaded;
    }

    public void setTotalTelegramsLoaded(long totalTelegramsLoaded) {
        this.totalTelegramsLoaded = totalTelegramsLoaded;
    }

    public List<Map<String, Object>> getTotalesxCircuito() {
		return totalesxCircuito;
	}

	public void setTotalesxCircuito(List<Map<String, Object>> totalesxCircuito) {
		this.totalesxCircuito = totalesxCircuito;
	}

	public List<Map<String, Object>> getTotalesxDiputadosNacionales() {
		return totalesxDiputadosNacionales;
	}

	public List<Map<String, Object>> getTotalesxLegisladoresProvinciales() {
		return totalesxLegisladoresProvinciales;
	}

	public List<Map<String, Object>> getTotalesxConcejales() {
		return totalesxConcejales;
	}

	public void setTotalesxDiputadosNacionales(List<Map<String, Object>> totalesxDiputadosNacionales) {
		this.totalesxDiputadosNacionales = totalesxDiputadosNacionales;
	}

	public void setTotalesxLegisladoresProvinciales(List<Map<String, Object>> totalesxLegisladoresProvinciales) {
		this.totalesxLegisladoresProvinciales = totalesxLegisladoresProvinciales;
	}

	public void setTotalesxConcejales(List<Map<String, Object>> totalesxConcejales) {
		this.totalesxConcejales = totalesxConcejales;
	}

	public List<Map<String, Object>> getTotalesxPresidente() {
		return totalesxPresidente;
	}

	public void setTotalesxPresidente(List<Map<String, Object>> totalesxPresidente) {
		this.totalesxPresidente = totalesxPresidente;
	}

	public List<Map<String, Object>> getTotalesxGobernador() {
		return totalesxGobernador;
	}

	public void setTotalesxGobernador(List<Map<String, Object>> totalesxGobernador) {
		this.totalesxGobernador = totalesxGobernador;
	}

	public List<Map<String, Object>> getTotalesxIntendente() {
		return totalesxIntendente;
	}

	public void setTotalesxIntendente(List<Map<String, Object>> totalesxIntendente) {
		this.totalesxIntendente = totalesxIntendente;
	}

	public List<Map<String, Object>> getTotalesxParlamentarios() {
		return totalesxParlamentarios;
	}

	public void setTotalesxParlamentarios(List<Map<String, Object>> totalesxParlamentarios) {
		this.totalesxParlamentarios = totalesxParlamentarios;
	}

	public static void setResultDashboardDTO(ResultDashboardDTO resultDashboardDTO) {
		ResultDashboardDTO.resultDashboardDTO = resultDashboardDTO;
	}


}
