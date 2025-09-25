package es.nextdigital.demo.client.controller;

import es.nextdigital.demo.client.service.ClientService;
import es.nextdigital.demo.movement.dto.MovementDTO;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients")
public class ClientController {

  private final ClientService clientService;

  public ClientController(ClientService clientService) {
    this.clientService = clientService;
  }

  @GetMapping("/{id}")
  public List<MovementDTO> getMovementsByAccount(@PathVariable Long id) {
    return clientService.getMovementsByAccountId(id);
  }
}
