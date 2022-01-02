package korolov.project.business;

import korolov.project.api.exceptions.EntityStateException;
import korolov.project.api.exceptions.HasRelationException;
import korolov.project.dao.ClientJpaRepository;
import korolov.project.domain.Client;
import org.aspectj.lang.annotation.Before;
import org.hibernate.criterion.Example;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTests {

    @InjectMocks
    ClientService clientService;

    @Mock
    ClientJpaRepository clientJpaRepository;

    @Test
    public void testReadAll() {
        Client client1 = new Client("Anton", "Korolov", "akorol6969@gmail.com");
        Client client2 = new Client("Artur", "Pirozhkov", "pirozhkov@gmail.com");

        List<Client> clients = List.of(client1, client2);

        Mockito.when(clientJpaRepository.findAll()).thenReturn(clients);

        Collection<Client> returnedClients = clientService.readAll();

        assertEquals(2, returnedClients.size());
        verify(clientJpaRepository, times(1)).findAll();
    }

    @Test
    public void testCreate() throws EntityStateException {
        Client client = new Client("Anton", "Korolov", "akorol6969@gmail.com");
        clientService.create(client);
        verify(clientJpaRepository, times(1)).save(any());
        verify(clientJpaRepository, times(1)).save(any(Client.class));
        verify(clientJpaRepository, times(1)).save(client);
    }

    @Test
    public void testReadOne(){
        Client client = new Client("Anton", "Korolov", "akorol6969@gmail.com");

        Mockito.when(clientJpaRepository.findById("akorol6969@gmail.com")).thenReturn(Optional.of(client));

        Client returnedClient = clientService.readById("akorol6969@gmail.com").get();

        assertEquals(client.getEmail(), returnedClient.getEmail());
        assertEquals(client.getName(), returnedClient.getName());
        assertEquals(client.getSurname(), returnedClient.getSurname());
        verify(clientJpaRepository, times(1)).findById("akorol6969@gmail.com");
    }

    @Test
    public void testDelete() throws HasRelationException {
        clientService.deleteById("akorol6969@gmail.com");
        verify(clientJpaRepository, times(1)).deleteById("akorol6969@gmail.com");
    }

    @Test
    public void testExists(){
        Client client = new Client("Anton", "Korolov", "akorol6969@gmail.com");
        Mockito.when(clientJpaRepository.existsById("akorol6969@gmail.com")).thenReturn(true);
        assertTrue(clientService.exists(client));
        verify(clientJpaRepository, times(1)).existsById("akorol6969@gmail.com");
    }

    @Test
    public void testUpdate() throws EntityStateException {
        Client client = new Client("Anton", "Korolov", "akorol6969@gmail.com");
        Mockito.when(clientService.exists(client)).thenReturn(true);
        clientService.update(client);

        verify(clientJpaRepository, times(1)).save(any());
        verify(clientJpaRepository, times(1)).save(any(Client.class));
        verify(clientJpaRepository, times(1)).save(client);

        ArgumentCaptor<Client> argumentCaptor = ArgumentCaptor.forClass(Client.class);
        verify(clientJpaRepository, Mockito.times(1)).save(argumentCaptor.capture());
        Client clientProvidedToService = argumentCaptor.getValue();
        assertEquals("akorol6969@gmail.com", clientProvidedToService.getEmail());
        assertEquals("Anton", clientProvidedToService.getName());
        assertEquals("Korolov", clientProvidedToService.getSurname());
    }
}
