package bank.picpay.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

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
