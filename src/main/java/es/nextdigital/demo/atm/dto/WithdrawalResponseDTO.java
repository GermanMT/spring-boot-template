package es.nextdigital.demo.atm.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawalResponseDTO {
  private BigDecimal amountWithdrawn;
  private BigDecimal fee;
  private BigDecimal totalDeducted;
  private BigDecimal remainingBalance;
  private LocalDateTime timestamp;
  private String atmLocation;

  public static WithdrawalResponseDTO success(
      BigDecimal amount, BigDecimal fee, BigDecimal remainingBalance, String atmLocation) {
    WithdrawalResponseDTO response = new WithdrawalResponseDTO();
    response.setAmountWithdrawn(amount);
    response.setFee(fee);
    response.setTotalDeducted(amount.add(fee));
    response.setRemainingBalance(remainingBalance);
    response.setTimestamp(LocalDateTime.now());
    response.setAtmLocation(atmLocation);
    return response;
  }
}
