package bank.picpay.controller;

import bank.picpay.models.UsuarioEntity;
import bank.picpay.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cadastro")
public class UsuarioController {
    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    public ResponseEntity<?> postUsuario(@RequestBody UsuarioEntity){
        return service.
    }
}
