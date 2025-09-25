package es.nextdigital.demo.movement.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.nextdigital.demo.movement.dto.MovementDTO;
import es.nextdigital.demo.movement.model.Movement;

@Mapper(componentModel = "spring")
public interface MovementMapper {
  @Mapping(target = "bankAccount", ignore = true)
  List<MovementDTO> toDtoList(List<Movement> orders);
}
