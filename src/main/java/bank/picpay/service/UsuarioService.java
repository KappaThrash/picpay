package bank.picpay.service;

import bank.picpay.models.usuario.TipoUsuario;
import bank.picpay.models.usuario.UsuarioEntity;
import bank.picpay.models.usuario.cnpjDTO;
import bank.picpay.models.usuario.cpfDTO;
import bank.picpay.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<?> validarUsuario(cpfDTO dto){
        var Entity = new UsuarioEntity();
        Entity.setNome(dto.getNome());

        Entity.setTipo(TipoUsuario.USUARIO);

        Entity.setDocumento(dto.getCpf());
        Entity.setEmail(dto.getEmail());
        Entity.setSenha(dto.getSenha());

        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(Entity));
    }

    public ResponseEntity<?> validarLojista(cnpjDTO dto){
        var Entity = new UsuarioEntity();
        Entity.setNome(dto.getNome());

        Entity.setTipo(TipoUsuario.LOJISTA);

        Entity.setDocumento(dto.getCnpj());
        Entity.setEmail(dto.getEmail());
        Entity.setSenha(dto.getSenha());

        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(Entity));
    }
}
