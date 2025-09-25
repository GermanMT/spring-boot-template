package es.nextdigital.demo.atm.controller;

import es.nextdigital.demo.atm.dto.WithdrawalRequestDTO;
import es.nextdigital.demo.atm.dto.WithdrawalResponseDTO;
import es.nextdigital.demo.atm.service.ATMService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/atm")
public class ATMController {

  private final ATMService atmService;

  public ATMController(ATMService atmService) {
    this.atmService = atmService;
  }

  @PostMapping("/withdraw")
  public ResponseEntity<WithdrawalResponseDTO> withdrawMoney(@Valid @RequestBody WithdrawalRequestDTO request) {
    WithdrawalResponseDTO response = atmService.withdrawMoney(request);
    return ResponseEntity.ok(response);
  }
}