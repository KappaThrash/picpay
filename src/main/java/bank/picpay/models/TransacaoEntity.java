package bank.picpay.models;

import bank.picpay.models.usuario.UsuarioEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "transacoes")
public class TransacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    BigDecimal value;

    @ManyToOne
    @JoinColumn(name = "payer_id")
    private UsuarioEntity payer;

    @ManyToOne
    @JoinColumn(name = "payee_id")
    private UsuarioEntity payee;
}
