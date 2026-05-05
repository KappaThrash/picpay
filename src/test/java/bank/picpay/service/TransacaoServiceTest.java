package bank.picpay.service;

import bank.picpay.auth.AuthorizeApi;
import bank.picpay.models.carteira.CarteiraEntity;
import bank.picpay.models.usuario.UsuarioEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {

    @Mock
    AuthorizeApi authorizeApi;


//    @Test
//    void actTransacaoBusinessExceptionLOJISTA() {
//        var PayerAccount = new UsuarioEntity(UUID.randomUUID(),);
//
//        var Payercarteira = new CarteiraEntity(UUID.randomUUID(),);
//    }
}