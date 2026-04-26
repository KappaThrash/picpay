package bank.picpay.auth;

import bank.picpay.exceptions.custom_exceptions.BusinessException;
import bank.picpay.models.responses.auth.AuthorizationResponseDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthorizeApi {


    public boolean getAuth(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<AuthorizationResponseDTO> response = restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", AuthorizationResponseDTO.class);

        if(!response.getStatusCode().is2xxSuccessful()) {
            throw new BusinessException("Sistema de autorização retornando erro");
        }

        AuthorizationResponseDTO responseBody = response.getBody();
        if(responseBody != null){
            return responseBody.getData().isAuthorization();
        }else{
            throw new BusinessException("Sistema de autorização retornando response null");
        }
    }
}
