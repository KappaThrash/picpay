package bank.picpay.repository;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "usuarios")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String nome;
    String cpf;
    String email;
    String senha;

}
