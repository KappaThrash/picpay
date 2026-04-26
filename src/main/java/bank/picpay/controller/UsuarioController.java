package bank.picpay.controller;

import bank.picpay.models.usuario.cnpjDTO;
import bank.picpay.models.usuario.cpfDTO;
import bank.picpay.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class UsuarioController {
    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("/usuario")
    public ResponseEntity<?> postUsuario(@RequestBody @Valid cpfDTO dto){
        return service.validarUsuario(dto);
    }

    @PostMapping("/lojista")
    public ResponseEntity<?> postUsuario(@RequestBody @Valid cnpjDTO dto){
        return service.validarLojista(dto);
    }

}
