package elecciones2021.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import elecciones2021.model.Mesa;


public interface MesaRepository extends JpaRepository<Mesa, Long> {

	@Query("select c from Mesa c where mesa = ?1")
	Mesa findMesa(int mesa);
	
	@Query("select c from Mesa c")
	List<Mesa> findAll();
	
	@Query(nativeQuery=true,value="select * from elecciones.mesa where fecha_de_carga is not null")
	List<Mesa> findLoaded();
	
	@Query(nativeQuery=true,value="select count(*) from elecciones.mesa")
	long getTotalMesas();
	
	@Query(nativeQuery=true,value="select count(*) from elecciones.mesa where fecha_de_carga is not null")
	long getTotalMesasCargadas();
	
	@Query(nativeQuery=true,value="select nombre, count(*) as totaltelegramas, sum(cargado) as totalcargados from (select c.id_circuito, c.nombre, case when m.fecha_de_carga is not null then 1 else 0 end as cargado from elecciones.circuitos c inner join elecciones.lugares_votacion as l on c.id_circuito = l.id_circuito inner join elecciones.mesa m on m.lugar_votacion_id = l.id_lugar_votacion) as p group by id_circuito, p.nombre")
	List<Map<String, Object>> getTotalesXCircuito();
	
	@Query(nativeQuery=true,value="select li.texto_formateado, li.color, p.porcentaje as valor from elecciones.lista_interna li inner join elecciones.porcentajesxlistanac() as p on li.id_lista_interna = p.id_lista_interna")
	List<Map<String, Object>> getTotalesDiputadosNacionales();
	
	
	@Query(nativeQuery=true,value="select li.texto_formateado, li.color, p.porcentaje as valor from elecciones.lista_interna li inner join elecciones.porcentajesxlistaprov() as p on li.id_lista_interna = p.id_lista_interna")
	List<Map<String, Object>> getTotalesLegisladoresProvinciales();
	
	@Query(nativeQuery=true,value="select li.texto_formateado, li.color, p.porcentaje as valor from elecciones.lista_interna li inner join elecciones.porcentajesxlistampal() as p on li.id_lista_interna = p.id_lista_interna")
	List<Map<String, Object>> getTotalesConcejales();
	
}