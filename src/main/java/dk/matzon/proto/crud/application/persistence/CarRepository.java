package dk.matzon.proto.crud.application.persistence;

import dk.matzon.proto.crud.model.Car;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, String> {
}
