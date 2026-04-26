package bank.picpay.service;

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
        Entity.mapCPFDTOoEntity(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(Entity));
    }

    public ResponseEntity<?> validarLojista(cnpjDTO dto){
        var Entity = new UsuarioEntity();
        Entity.mapCPNPJDTOoEntity(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(Entity));
    }
}
