package bank.picpay.controller;

import bank.picpay.models.usuario.UsuarioEntity;
import bank.picpay.models.usuario.cnpjDTO;
import bank.picpay.models.usuario.cpfDTO;
import bank.picpay.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cadastro")
public class UsuarioController {
    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("/usuario")
    public ResponseEntity<?> postUsuario(@RequestBody cpfDTO dto){
        return service.validarUsuario(dto);
    }

    @PostMapping("/lojista")
    public ResponseEntity<?> postUsuario(@RequestBody cnpjDTO dto){
        return service.validarLojista(dto);
    }

}
