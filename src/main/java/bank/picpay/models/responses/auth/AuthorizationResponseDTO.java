package bank.picpay.models.responses.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthorizationResponseDTO {
    String status;
    DataResponseAuth data;
}
