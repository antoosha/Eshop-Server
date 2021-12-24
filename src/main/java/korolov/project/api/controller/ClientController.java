package korolov.project.api.controller;

import korolov.project.api.converter.ClientConverter;
import korolov.project.api.dto.ClientDTO;
import korolov.project.api.exceptions.EntityStateException;
import korolov.project.api.exceptions.HasRelationException;
import korolov.project.business.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    //CREATE createClient /*new client*/ POST
    @PostMapping("/clients")
    ClientDTO create(@RequestBody ClientDTO clientDTO) {
        try {
            clientDTO = ClientConverter.fromModel(clientService.create(ClientConverter.toModel(clientDTO)));
        } catch (EntityStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client already exists");
        }
        return clientDTO;
    }

    //READ showAllClients GET
    @GetMapping("/clients")
    List<ClientDTO> getAll() {
        return ClientConverter.fromModels(clientService.readAll());
    }

    //READ showClient GET
    @GetMapping("/clients/{id}")
    ClientDTO getOne(@PathVariable String id) {
        return ClientConverter.fromModel(clientService.readById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found")
        ));
    }

    //UPDATE editClient /*edit client info*/ PUT
    @PutMapping("/clients/{id}")
    ClientDTO update(@PathVariable String id, @RequestBody ClientDTO clientDTO) {
        if (!clientDTO.getEmail().equals(id))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client ids do not match");
        try {
            clientDTO = ClientConverter.fromModel(clientService.update(ClientConverter.toModel(clientDTO)));
        } catch (EntityStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client not found");
        }
        return clientDTO;
    }

    //DELETE deleteClient DELETE
    @DeleteMapping("/clients/{id}")
    void delete(@PathVariable String id) {
        if (clientService.readById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client to delete not found");
        }
        try {
            clientService.deleteById(id);
        } catch (HasRelationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Could not to delete client");
        }
    }

}
