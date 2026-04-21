package bank.picpay.controller;

import bank.picpay.models.TransacaoEntity;
import bank.picpay.service.TransacaoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {
    private final TransacaoService service;

    public TransacaoController(TransacaoService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> transacaoPost(@RequestBody @Valid TransacaoEntity entity){
        return service.actTransacao(entity);
    }
}
