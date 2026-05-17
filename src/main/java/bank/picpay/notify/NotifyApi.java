package bank.picpay.notify;

import bank.picpay.models.usuario.UsuarioEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;

@Component
public class NotifyApi {

    public void postTransactionNotification(UsuarioEntity payerAccount, UsuarioEntity payeeAccount, BigDecimal transactionValue, Instant transactionTime){

    }
}
