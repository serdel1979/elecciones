package elecciones2021.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import elecciones2021.model.CircuitoMesa;



public interface CircuitoMesaRepository extends JpaRepository<CircuitoMesa, Long> {

	@Query("select c from CircuitoMesa c where circuito = ?1 and ?2 between mesaDesde and mesaHasta")
	CircuitoMesa findCircuitoMesa(String circuito, int mesa);
}