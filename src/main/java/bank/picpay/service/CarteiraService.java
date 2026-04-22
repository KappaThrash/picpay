package bank.picpay.service;

import bank.picpay.exceptions.custom_exceptions.UserNotFoundException;
import bank.picpay.models.carteira.CarteiraDTO;
import bank.picpay.models.carteira.CarteiraEntity;
import bank.picpay.repository.CarteiraRepository;
import bank.picpay.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CarteiraService {
    private final CarteiraRepository carteiraRepository;
    private final UsuarioRepository usuarioRepository;

    CarteiraService(CarteiraRepository carteiraRepository, UsuarioRepository usuarioRepository){
        this.carteiraRepository = carteiraRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public ResponseEntity<?> criarCarteira(CarteiraDTO dto){
        var UsuarioEntity = usuarioRepository.findById(dto.getUser_id())
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        var Carteira = new CarteiraEntity();
        Carteira.setUser_id(UsuarioEntity);
        Carteira.setBalance(BigDecimal.ZERO);

        return ResponseEntity.status(HttpStatus.CREATED).body(carteiraRepository.save(Carteira));
    }
}
