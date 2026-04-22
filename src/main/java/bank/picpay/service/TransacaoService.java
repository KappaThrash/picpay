package bank.picpay.service;

import bank.picpay.models.transacao.TransacaoEntity;
import bank.picpay.repository.TransacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TransacaoService {

    private final TransacaoRepository repository;

    public TransacaoService(TransacaoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ResponseEntity<?> actTransacao(TransacaoEntity entity){

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
