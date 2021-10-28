package korolov.project.api.converter;

import korolov.project.api.dto.ClientDTO;
import korolov.project.domain.Client;

import java.util.Collection;

public class ClientConverter {
    public static Client toModel(ClientDTO clientDTO) {
        return new Client(clientDTO.getName(), clientDTO.getSurname(), clientDTO.getEmail());
    }
    public static ClientDTO fromModel(Client client) {
        return new ClientDTO(client.getName(), client.getSurname(), client.getEmail());
    }
    public static Collection<Client> toModels(Collection<ClientDTO> clientDTOs) {
        return clientDTOs.stream().map(ClientConverter::toModel).toList();
    }
    public static Collection<ClientDTO> fromModels(Collection<Client> clients) {
        return clients.stream().map(ClientConverter::fromModel).toList();
    }
}
