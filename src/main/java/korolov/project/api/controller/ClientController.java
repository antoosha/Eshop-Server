package korolov.project.api.controller;

import korolov.project.api.dto.ClientDTO;
import korolov.project.business.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
public class ClientController {

    //TODO normal implementation controller

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    //CREATE createClient /*new client*/ POST
    @PostMapping("/clients")
    ClientDTO create(@RequestBody ClientDTO clientDTO) {
        return new ClientDTO("", "", "");
    }

    //READ showAllClients GET
    @GetMapping("/clients")
    Collection<ClientDTO> getAll() {
        return Collections.emptyList();
    }

    //READ showClient GET
    @GetMapping("/clients/{id}")
    ClientDTO getOne(@PathVariable String id) {
        return new ClientDTO("", "", "");
    }

    //UPDATE editClient /*edit client info*/ PUT
    @PutMapping("/clients/{id}")
    void update(@PathVariable String id, @RequestBody ClientDTO clientDTO) {}

    //DELETE deleteClient DELETE
    @DeleteMapping("/clients/{id}")
    void delete(@PathVariable String id) {}

}
