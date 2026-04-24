package bank.picpay.models.transacao;

import bank.picpay.models.carteira.CarteiraEntity;
import bank.picpay.models.usuario.UsuarioEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transacoes")
public class TransacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "payer_id")
    private CarteiraEntity payer;

    @ManyToOne
    @JoinColumn(name = "payee_id")
    private CarteiraEntity payee;

    private Instant created_at;

}
