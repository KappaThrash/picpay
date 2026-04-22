package bank.picpay.controller;

import bank.picpay.models.carteira.CarteiraDTO;
import bank.picpay.service.CarteiraService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carteira")
public class CarteiraController {
    private final CarteiraService service;

    CarteiraController(CarteiraService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> cadastrarCarteira(@RequestBody @Valid CarteiraDTO dto){
        return service.criarCarteira(dto);
    }
}
