package bank.picpay.models.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class cnpjDTO {
    String nome;

    TipoUsuario tipo;

    @CNPJ
    String cnpj;

    String email;
    String senha;
}
