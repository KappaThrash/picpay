package bank.picpay.service;

import bank.picpay.exceptions.custom_exceptions.BusinessException;
import bank.picpay.exceptions.custom_exceptions.CarteiraNotFoundException;
import bank.picpay.models.responses.auth.AuthorizationResponseDTO;
import bank.picpay.models.transacao.TransacaoDTO;
import bank.picpay.models.transacao.TransacaoEntity;
import bank.picpay.models.usuario.TipoUsuario;
import bank.picpay.repository.CarteiraRepository;
import bank.picpay.repository.TransacaoRepository;
import bank.picpay.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.Instant;

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
    public ResponseEntity<TransacaoEntity> actTransacao(TransacaoDTO dto){

        var PayerCarteira = carteiraRepository.findById(dto.getPayer())
                .orElseThrow(() -> new CarteiraNotFoundException("Carteira do Payer não encontrada"));

        var PayeeCarteira = carteiraRepository.findById(dto.getPayee())
                .orElseThrow(() -> new CarteiraNotFoundException("Carteira do Payee não encontrada"));

        var PayerAccount = PayerCarteira.getUser_id();
        var PayeeAccount = PayeeCarteira.getUser_id();

        BigDecimal TransactionValue = dto.getAmount();

        if(PayerAccount.getTipo() == TipoUsuario.LOJISTA){
            throw new BusinessException("Usuarios do tipo LOJISTA não podem efetuar transferencias");
        }

        if(PayerCarteira.getBalance().compareTo(TransactionValue) < 0){
            throw new BusinessException("Saldo insuficiente");
        }

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<AuthorizationResponseDTO> response = restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", AuthorizationResponseDTO.class);

        if(!response.getStatusCode().is2xxSuccessful()) {
            throw new BusinessException("Sistema de autorização retornando erro");
        }

        AuthorizationResponseDTO responseBody = response.getBody();
        if(responseBody != null){
            boolean isAuthorized = responseBody.getData().isAuthorization();
            if(!isAuthorized){
                throw new BusinessException("Sistema de autorização recusou a transferencia");
            }
        }else{
            throw new BusinessException("Sistema de autorização retornando response null");
        }

        PayerCarteira.debit(TransactionValue);
        PayeeCarteira.credit(TransactionValue);

        var SavingTransacaoEntity = new TransacaoEntity();
        SavingTransacaoEntity.setAmount(dto.getAmount());
        SavingTransacaoEntity.setPayer(PayerCarteira);
        SavingTransacaoEntity.setPayee(PayeeCarteira);
        transacaoRepository.save(SavingTransacaoEntity);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
