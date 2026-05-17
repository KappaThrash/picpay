package bank.picpay.notify;

import bank.picpay.models.responses.auth.notification.EmailDTO;
import bank.picpay.models.usuario.UsuarioEntity;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;

@Component
public class NotificationProducer {

    private final RabbitTemplate rabbitTemplate;

    public NotificationProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${broker.queue.email.name}")
    String routingKey;

    public void postTransactionNotification(UsuarioEntity payerAccount, UsuarioEntity payeeAccount, BigDecimal transactionValue, Instant transactionTime){
        EmailDTO emailDTO = new EmailDTO(payerAccount.getNome(),payeeAccount.getNome(),payerAccount.getEmail(),
                payeeAccount.getEmail(),transactionValue,transactionTime);


        rabbitTemplate.convertAndSend(routingKey, emailDTO);
    }
}
