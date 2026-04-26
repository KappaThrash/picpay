package bank.picpay.service;

import bank.picpay.auth.AuthorizeApi;
import bank.picpay.exceptions.custom_exceptions.BusinessException;
import bank.picpay.exceptions.custom_exceptions.CarteiraNotFoundException;
import bank.picpay.models.transacao.TransacaoDTO;
import bank.picpay.models.transacao.TransacaoEntity;
import bank.picpay.notify.NotifyApi;
import bank.picpay.repository.CarteiraRepository;
import bank.picpay.repository.TransacaoRepository;
import bank.picpay.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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

        if(PayerAccount.isLOJISTA()){
            throw new BusinessException("Usuarios do tipo LOJISTA não podem efetuar transferencias");
        }

        if(PayerCarteira.getBalance().compareTo(TransactionValue) < 0){
            throw new BusinessException("Saldo insuficiente");
        }

        AuthorizeApi AuthorizeApi = new AuthorizeApi();
        if(!AuthorizeApi.getAuth()){
           throw new BusinessException("Não autorizado");
        }

        PayerCarteira.debit(TransactionValue);
        PayeeCarteira.credit(TransactionValue);

        //A api simplesmente retorna um 204 No Content ou um 504 Gateway Timeout simulando uma falha
        // como não é pedido para enviar algo e nem tem documentação para oque enviar deixarei
        // apenas a classe com o metodo vazio aqui
        new NotifyApi().postNotifcation();


        var SavingTransacaoEntity = new TransacaoEntity();
        SavingTransacaoEntity.mapDTOToEntity(dto, PayerCarteira, PayeeCarteira);
        transacaoRepository.save(SavingTransacaoEntity);

        return ResponseEntity.status(HttpStatus.OK).body(SavingTransacaoEntity);
    }

}
