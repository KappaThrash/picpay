package bank.picpay.models.usuario;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID usuario_id;

    String nome;

    @Enumerated(EnumType.STRING)
    TipoUsuario tipo;

    @Column(unique = true)
    String documento;

    @Column(unique = true)
    String email;

    String senha;


    public boolean isLOJISTA(){
        return this.tipo == TipoUsuario.LOJISTA;
    }
}
