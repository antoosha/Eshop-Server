package korolov.project.dao;

import korolov.project.domain.Client;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClientRepositoryTests {

    @Autowired
    ClientJpaRepository clientJpaRepository;

    @Test
    public void testCreateReadDelete() {
        Client client = new Client("Anton", "Korolov","akorol6969@gmail.com");

        clientJpaRepository.save(client);
        List<Client> clients = clientJpaRepository.findAll();
        Assertions.assertThat(clients).extracting(Client::getEmail).containsOnly("akorol6969@gmail.com");

        clientJpaRepository.deleteAll();
        Assertions.assertThat(clientJpaRepository.findAll()).isEmpty();
    }
}
