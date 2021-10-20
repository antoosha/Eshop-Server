package korolov.project.api.controller;

import korolov.project.business.ClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }
    //CREATE createClient /*new client*/
    //READ showClient showAllClients
    //UPDATE editClient /*edit client info*/
    //DELETE deleteClient
}
