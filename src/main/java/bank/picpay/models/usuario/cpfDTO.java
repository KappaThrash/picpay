package bank.picpay.models.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class cpfDTO {
    String nome;

    TipoUsuario tipo;

    @CPF
    String cpf;

    String email;
    String senha;
}
