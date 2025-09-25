package es.nextdigital.demo.atm.service;

import es.nextdigital.demo.account.repository.AccountRepository;
import es.nextdigital.demo.atm.dto.WithdrawalRequestDTO;
import es.nextdigital.demo.atm.dto.WithdrawalResponseDTO;
import es.nextdigital.demo.atm.exception.ATMNotFoundException;
import es.nextdigital.demo.atm.exception.CardNotFoundException;
import es.nextdigital.demo.atm.exception.InsufficientFundsException;
import es.nextdigital.demo.atm.model.ATM;
import es.nextdigital.demo.atm.repository.ATMRepository;
import es.nextdigital.demo.card.model.Card;
import es.nextdigital.demo.card.repository.CardRepository;
import es.nextdigital.demo.movement.model.Movement;
import es.nextdigital.demo.movement.model.Type;
import es.nextdigital.demo.movement.repository.MovementRepository;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class ATMService {

  private final ATMRepository atmRepository;
  private final CardRepository cardRepository;
  private final AccountRepository accountRepository;
  private final MovementRepository movementRepository;

  public ATMService(
      ATMRepository atmRepository,
      CardRepository cardRepository,
      AccountRepository accountRepository,
      MovementRepository movementRepository) {
    this.atmRepository = atmRepository;
    this.cardRepository = cardRepository;
    this.accountRepository = accountRepository;
    this.movementRepository = movementRepository;
  }

  public WithdrawalResponseDTO withdrawMoney(WithdrawalRequestDTO request) {
    if (request.getAtmId() == null || request.getAtmId() <= 0) {
      throw new IllegalArgumentException("ATM ID must be a positive number");
    }
    if (request.getCardNumber() == null || request.getCardNumber().trim().isEmpty()) {
      throw new IllegalArgumentException("Card number cannot be empty");
    }
    if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Amount must be positive");
    }

    ATM atm =
        atmRepository
            .findById(request.getAtmId())
            .orElseThrow(() -> new ATMNotFoundException("ATM not found"));

    Card card =
        cardRepository
            .findByCardNumber(request.getCardNumber())
            .orElseThrow(() -> new CardNotFoundException("Card not found"));

    // Check if ATM has enough cash
    if (atm.getAvailableCash().compareTo(request.getAmount()) < 0) {
      throw new InsufficientFundsException("ATM does not have enough cash");
    }

    // Calculate fee and total amount
    BigDecimal fee = atm.calculateFee(card.getBankName());
    BigDecimal totalAmount = request.getAmount().add(fee);

    // Check if account has sufficient funds
    if (card.getBankAccount().getAccountAmount().compareTo(totalAmount) < 0) {
      throw new InsufficientFundsException("Insufficient balance in account");
    }

    // Process withdrawal
    card.getBankAccount()
        .setAccountAmount(card.getBankAccount().getAccountAmount().subtract(totalAmount));
    atm.setAvailableCash(atm.getAvailableCash().subtract(request.getAmount()));

    // Save changes
    accountRepository.save(card.getBankAccount());
    atmRepository.save(atm);

    // Record transaction
    Movement withdrawalMovement = new Movement();
    withdrawalMovement.setBankAccount(card.getBankAccount());
    withdrawalMovement.setMovementAmount(request.getAmount().negate());
    withdrawalMovement.setType(Type.CASH);
    movementRepository.save(withdrawalMovement);

    if (fee.compareTo(BigDecimal.ZERO) > 0) {
      Movement feeMovement = new Movement();
      feeMovement.setBankAccount(card.getBankAccount());
      feeMovement.setMovementAmount(fee.negate());
      feeMovement.setType(Type.FEE);
      movementRepository.save(feeMovement);
    }

    return WithdrawalResponseDTO.success(
        request.getAmount(), fee, card.getBankAccount().getAccountAmount(), atm.getLocation());
  }
}
