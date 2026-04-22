package bank.picpay.service;

import bank.picpay.models.transacao.TransacaoDTO;
import bank.picpay.models.transacao.TransacaoEntity;
import bank.picpay.repository.CarteiraRepository;
import bank.picpay.repository.TransacaoRepository;
import bank.picpay.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CarteiraRepository carteiraRepository;

    public TransacaoService(TransacaoRepository transacaoRepository, UsuarioRepository usuarioRepository, CarteiraRepository carteiraRepository) {
        this.transacaoRepository = transacaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.carteiraRepository = carteiraRepository;
    }

    @Transactional
    public ResponseEntity<?> actTransacao(TransacaoDTO dto){

        var PayerCarteira = carteiraRepository.findById(dto.getPayer());

        var PayeeCarteira = carteiraRepository.findById(dto.getPayee()).els;

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
