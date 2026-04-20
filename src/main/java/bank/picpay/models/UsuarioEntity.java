package bank.picpay.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID usuarioCPF_id;

    String nome;

    @Enumerated(EnumType.STRING)
    TipoUsuario tipo;

    String documento;

    String email;

    String senha;

}
