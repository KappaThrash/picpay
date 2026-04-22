package bank.picpay.service;

import bank.picpay.exceptions.custom_exceptions.CarteiraNotFoundException;
import bank.picpay.exceptions.custom_exceptions.UserNotFoundException;
import bank.picpay.models.carteira.CarteiraDTO;
import bank.picpay.models.carteira.CarteiraEntity;
import bank.picpay.repository.CarteiraRepository;
import bank.picpay.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class CarteiraService {
    private final CarteiraRepository carteiraRepository;
    private final UsuarioRepository usuarioRepository;

    CarteiraService(CarteiraRepository carteiraRepository, UsuarioRepository usuarioRepository){
        this.carteiraRepository = carteiraRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public ResponseEntity<CarteiraEntity> criarCarteira(CarteiraDTO dto){
        var UsuarioEntity = usuarioRepository.findById(dto.getUser_id())
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        var Carteira = new CarteiraEntity();
        Carteira.setUser_id(UsuarioEntity);
        Carteira.setBalance(BigDecimal.ZERO);

        return ResponseEntity.status(HttpStatus.CREATED).body(carteiraRepository.save(Carteira));
    }

    public ResponseEntity<CarteiraEntity> getCarteira(UUID id) {
        var carteiraEntity = carteiraRepository.findById(id)
                .orElseThrow(() -> new CarteiraNotFoundException("Carteira não encontrada"));

        return ResponseEntity.status(HttpStatus.CREATED).body(carteiraEntity);
    }
}
