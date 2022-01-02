package korolov.project.api.controller;

import korolov.project.api.controller.ClientController;
import korolov.project.api.converter.ClientConverter;
import korolov.project.api.exceptions.EntityStateException;
import korolov.project.business.ClientService;
import korolov.project.domain.Client;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
public class ClientControllerTests {
    @MockBean
    ClientService clientService;

    @Autowired
    MockMvc mockMvc;

    //GET ONE
    @Test
    public void testGetOne() throws Exception {
        Client client = new Client("Anton", "Korolov","akorol6969@gmail.com");
        //For client with id "akorol6969@gmail.com"
        Mockito.when(clientService.readById("akorol6969@gmail.com")).thenReturn(Optional.of(client));
        //For existing client
        mockMvc.perform(get("/clients/akorol6969@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("Anton")))
                .andExpect(jsonPath("$.surname", Matchers.is("Korolov")))
                .andExpect(jsonPath("$.email", Matchers.is("akorol6969@gmail.com")));


        //For anything else then "akorol6969@gmail.com"
        Mockito.when(clientService.readById(not(eq("akorol6969@gmail.com")))).thenReturn(Optional.empty());
        //For request to client, that does not exist
        mockMvc.perform(get("/users/honza@gmail.com"))
                .andExpect(status().isNotFound());
    }

    //GET ALL
    @Test
    public void testGetAll() throws Exception{
        Client client1 = new Client("Honza", "Honzik","honza@gmail.com");
        Client client2 = new Client("Anton", "Korolov","akorol6969@gmail.com");
        List<Client> clients = List.of(client1, client2);

        Mockito.when(clientService.readAll()).thenReturn(clients);

        mockMvc.perform(get("/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].email", Matchers.is("honza@gmail.com")))
                .andExpect(jsonPath("$[1].email", Matchers.is("akorol6969@gmail.com")));
    }

    //DELETE
    @Test
    public void testDelete() throws Exception {
        Client client = new Client("Anton", "Korolov","akorol6969@gmail.com");

        //Mock method readById, because it used to verify existence of clientbefore delete.
        Mockito.when(clientService.readById(not(eq("akorol6969@gmail.com")))).thenReturn(Optional.empty());
        Mockito.when(clientService.readById("akorol6969@gmail.com")).thenReturn(Optional.of(client));

        //If try to delete client who does not exist returns HTTP status Not found...
        mockMvc.perform(get("/clients/aaa"))
                .andExpect(status().isNotFound());
        //... and deleteById should not be called
        verify(clientService, never()).deleteById(any());

        //Try to delete exising user, should be OK
        mockMvc.perform(delete("/clients/akorol6969@gmail.com"))
                .andExpect(status().isOk());
        //should be called deleteById
        verify(clientService, times(1)).deleteById("akorol6969@gmail.com");
    }

    //CREATE EXISTING
    @Test
    public void testCreateExisting() throws Exception {
        //When calling create with any client, throw exception.
        doThrow(new EntityStateException()).when(clientService).create(any(Client.class));

        //Try to create client, who already exists, return HTTP status Conflict.
        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Anton\",\"surname\":\"Korolov\",\"email\":\"akorol6969@gmail.com\"}"))
                .andExpect(status().isConflict());
    }

    //CREATE NEW
    @Test
    public void testCreate() throws Exception {
        //Create client, which should be returned after creating.
        Client client = new Client("Anton", "Korolov","akorol6969@gmail.com");

        Mockito.when(clientService.create(client)).thenReturn(client);

        //If try to create client, return HTTP OK and client's data
        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Anton\",\"surname\":\"Korolov\",\"email\":\"akorol6969@gmail.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("Anton")))
                .andExpect(jsonPath("$.surname", Matchers.is("Korolov")))
                .andExpect(jsonPath("$.email", Matchers.is("akorol6969@gmail.com")));


        //Verify using ArgumentCaptor, that create has been called 1x.
        ArgumentCaptor<Client> argumentCaptor = ArgumentCaptor.forClass(Client.class);
        Mockito.verify(clientService, Mockito.times(1)).create(argumentCaptor.capture());
        Client clientProvidedToService = argumentCaptor.getValue();
        assertEquals("akorol6969@gmail.com", clientProvidedToService.getEmail());
        assertEquals("Anton", clientProvidedToService.getName());
        assertEquals("Korolov", clientProvidedToService.getSurname());
        }

    //UPDATE
    @Test
    public void testUpdateNotExisting() throws Exception {
        //When calling create with any client, throw exception.
        doThrow(new EntityStateException()).when(clientService).update(any(Client.class));

        //Try to update not existing client, return HTTP status NOT FOUND.
        mockMvc.perform(put("/clients/akorol6969@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Anton\",\"surname\":\"Korolov\",\"email\":\"akorol6969@gmail.com\"}"))
                .andExpect(status().isNotFound());
    }

    //UPDATE
    @Test
    public void testUpdate() throws Exception {
        //Create client, which should be returned after creating.
        Client client = new Client("Anton", "Koro","akorol6969@gmail.com");

        Mockito.when(clientService.update(client)).thenReturn(client);

        //If try to update client, return HTTP OK and client's data
        mockMvc.perform(put("/clients/akorol6969@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Anton\",\"surname\":\"Koro\",\"email\":\"akorol6969@gmail.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("Anton")))
                .andExpect(jsonPath("$.surname", Matchers.is("Koro")))
                .andExpect(jsonPath("$.email", Matchers.is("akorol6969@gmail.com")));

        Mockito.when(clientService.update(client)).thenReturn(client);

        //Verify using ArgumentCaptor, that create has been called 1x.
        ArgumentCaptor<Client> argumentCaptor = ArgumentCaptor.forClass(Client.class);
        Mockito.verify(clientService, Mockito.times(1)).update(argumentCaptor.capture());
        Client clientProvidedToService = argumentCaptor.getValue();
        assertEquals("akorol6969@gmail.com", clientProvidedToService.getEmail());
        assertEquals("Anton", clientProvidedToService.getName());
        assertEquals("Koro", clientProvidedToService.getSurname());
    }
}
