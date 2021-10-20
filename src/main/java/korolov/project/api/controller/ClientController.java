package korolov.project.api.controller;

import korolov.project.api.dto.ClientDTO;
import korolov.project.business.ClientService;
import korolov.project.business.EntityStateException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }
    //CREATE createClient /*new client*/ POST
    @PostMapping("/users")
    ClientDTO create(@RequestBody ClientDTO clientDTO) {
        try {
            clientService.create();
        } catch (EntityStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists");
        }
        return getOne(clientDTO.getEmail());
    }

    //READ showClient showAllClients GET

    //UPDATE editClient /*edit client info*/ PUT

    //DELETE deleteClient DELETE

    //TODO implement controller
}
