package es.nextdigital.demo.client.service;

import es.nextdigital.demo.client.exception.MovementsNotFoundException;
import es.nextdigital.demo.client.repository.ClientRepository;
import es.nextdigital.demo.movement.dto.MovementDTO;
import es.nextdigital.demo.movement.mapper.MovementMapper;
import es.nextdigital.demo.movement.model.Movement;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

  private final ClientRepository clientRepository;
  private final MovementMapper movementMapper;

  public ClientService(ClientRepository clientRepository, MovementMapper movementMapper) {
    this.clientRepository = clientRepository;
    this.movementMapper = movementMapper;
  }

  public List<MovementDTO> getMovementsByAccountId(Long bankAccountId) {
    List<Movement> movements = clientRepository.findMovementsByBankAccountId(bankAccountId);
    if (movements.isEmpty()) {
      throw new MovementsNotFoundException(bankAccountId);
    }
    return movementMapper.toDtoList(movements);
  }
}
