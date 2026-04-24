package bank.picpay.models.responses.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorizationResponseDTO {
    String status;
    DataResponseAuth data;
}
