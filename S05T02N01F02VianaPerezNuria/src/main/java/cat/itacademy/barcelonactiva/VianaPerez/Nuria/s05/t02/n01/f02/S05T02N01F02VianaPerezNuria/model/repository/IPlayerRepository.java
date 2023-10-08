package cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f02.S05T02N01F02VianaPerezNuria.model.repository;

import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f02.S05T02N01F02VianaPerezNuria.model.domain.Player;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPlayerRepository extends MongoRepository<Player, ObjectId> {
    Boolean existsByName(String name);


}
