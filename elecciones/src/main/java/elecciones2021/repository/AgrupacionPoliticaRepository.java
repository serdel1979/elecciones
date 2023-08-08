package elecciones2021.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import elecciones2021.model.AgrupacionPolitica;


public interface AgrupacionPoliticaRepository extends JpaRepository<AgrupacionPolitica, Long> {
	//List<AgrupacionPolitica> findAll(); //List<AgrupacionPolitica> findAllByOrderByNombreAsc();
	 //List<AgrupacionPolitica> findAllByOrderByNroOrdenAsc();
	List<AgrupacionPolitica> findAllByOrderByNroOrdenAsc();
}