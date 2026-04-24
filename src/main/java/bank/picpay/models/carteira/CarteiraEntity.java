package bank.picpay.models.carteira;

import bank.picpay.exceptions.custom_exceptions.BusinessException;
import bank.picpay.models.usuario.UsuarioEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "carteira")
public class CarteiraEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UsuarioEntity user_id;

    @Column(nullable = false)
    private BigDecimal balance;

    public void debit(BigDecimal value){
        if(this.getBalance().compareTo(value) < 0){
            throw new BusinessException("Saldo insuficiente");
        }
        this.balance = this.balance.subtract(value);
    }

    public void credit(BigDecimal value){
        if(value.compareTo(BigDecimal.ZERO) < 0){
            throw new BusinessException("Creditando valor negativo");
        }
        this.balance = this.balance.add(value);
    }
}
