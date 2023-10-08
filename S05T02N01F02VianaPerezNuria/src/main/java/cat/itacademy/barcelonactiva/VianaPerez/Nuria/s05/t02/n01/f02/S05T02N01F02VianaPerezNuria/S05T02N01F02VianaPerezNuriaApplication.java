package cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f02.S05T02N01F02VianaPerezNuria;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(//change de the project name and version in swagger
		info = @Info(title = "DiceGame CRUD with MongoDB", version = "1.0.0"),
		//ex: work with two servers in cloud, one for Windows, other for IOS
		servers = {@Server(url = "http://localhost:9004"), @Server(url = "http://localhost:8080")},
		tags = @Tag(name = "Sprint 5.2.1.F2", description = "main CRUD methods with Open Api Documentation")
)

public class S05T02N01F02VianaPerezNuriaApplication {

	public static void main(String[] args) {
		SpringApplication.run(S05T02N01F02VianaPerezNuriaApplication.class, args);
	}

}
