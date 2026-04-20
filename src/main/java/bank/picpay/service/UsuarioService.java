package bank.picpay.service;

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
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<?> validarLojista(cnpjDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
