package bank.picpay.service;

import bank.picpay.auth.AuthorizeApi;
import bank.picpay.exceptions.custom_exceptions.BusinessException;
import bank.picpay.exceptions.custom_exceptions.CarteiraNotFoundException;
import bank.picpay.models.transacao.TransacaoDTO;
import bank.picpay.models.transacao.TransacaoEntity;
import bank.picpay.notify.NotifyApi;
import bank.picpay.repository.CarteiraRepository;
import bank.picpay.repository.TransacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final CarteiraRepository carteiraRepository;
    private final AuthorizeApi authorizeApi;

    public TransacaoService(TransacaoRepository transacaoRepository, CarteiraRepository carteiraRepository, AuthorizeApi authorizeApi) {
        this.transacaoRepository = transacaoRepository;
        this.carteiraRepository = carteiraRepository;
        this.authorizeApi = authorizeApi;
    }

    @Transactional
    public ResponseEntity<TransacaoEntity> actTransacao(TransacaoDTO dto){

        var PayerCarteira = carteiraRepository.findById(dto.getPayer())
                .orElseThrow(() -> new CarteiraNotFoundException("Carteira do Payer não encontrada"));

        var PayeeCarteira = carteiraRepository.findById(dto.getPayee())
                .orElseThrow(() -> new CarteiraNotFoundException("Carteira do Payee não encontrada"));

        var PayerAccount = PayerCarteira.getUser_id();
        var PayeeAccount = PayeeCarteira.getUser_id();

        BigDecimal TransactionValue = dto.getAmount();

        if(PayerAccount.isLOJISTA()){
            throw new BusinessException("Usuarios do tipo LOJISTA não podem efetuar transferencias");
        }

        if(PayerCarteira.getBalance().compareTo(TransactionValue) < 0){
            throw new BusinessException("Saldo insuficiente");
        }

        if(!authorizeApi.getAuth()){
           throw new BusinessException("Não autorizado");
        }

        PayerCarteira.debit(TransactionValue);
        PayeeCarteira.credit(TransactionValue);


        var SavingTransacaoEntity = new TransacaoEntity();
        SavingTransacaoEntity.mapDTOToEntity(dto, PayerCarteira, PayeeCarteira);
        transacaoRepository.save(SavingTransacaoEntity);

        new NotifyApi().postTransactionNotification(PayerAccount, PayeeAccount, TransactionValue, SavingTransacaoEntity.getCreated_at());

        return ResponseEntity.status(HttpStatus.CREATED).body(SavingTransacaoEntity);
    }

}
