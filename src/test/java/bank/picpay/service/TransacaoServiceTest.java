package bank.picpay.service;

import bank.picpay.auth.AuthorizeApi;
import bank.picpay.exceptions.custom_exceptions.BusinessException;
import bank.picpay.models.carteira.CarteiraEntity;
import bank.picpay.models.transacao.TransacaoDTO;
import bank.picpay.models.usuario.TipoUsuario;
import bank.picpay.models.usuario.UsuarioEntity;
import bank.picpay.repository.CarteiraRepository;
import bank.picpay.repository.TransacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {

    @Mock
    AuthorizeApi authorizeApi;

    @Mock
    TransacaoRepository transacaoRepository;

    @Mock
    CarteiraRepository carteiraRepository;

    @InjectMocks
    TransacaoService transacaoService;

    TransacaoDTO dto;
    UsuarioEntity PayerAccount;
    CarteiraEntity PayerCarteira;
    UsuarioEntity PayeeAccount;
    CarteiraEntity PayeeCarteira;

    @BeforeEach
    void setup(){
        dto = new TransacaoDTO(new BigDecimal(10),null,null);

        PayerAccount = new UsuarioEntity(UUID.randomUUID(), "a", TipoUsuario.USUARIO, "84.132.415/0001-09",
                "daniel@gmail.com","abc");
        PayerCarteira = new CarteiraEntity(UUID.randomUUID(),PayerAccount, new BigDecimal(1000));

        PayeeAccount = new UsuarioEntity(UUID.randomUUID(), "ab", TipoUsuario.LOJISTA, "850.987.415-80",
                "danielq@gmail.com","abc");
        PayeeCarteira = new CarteiraEntity(UUID.randomUUID(),PayeeAccount, new BigDecimal(1000));
    }


    @Test
    void actTransacaoShouldThrowBusinessExceptionLojistaBecausePayerAccountIsTipoLojista() {

        PayerAccount.setTipo(TipoUsuario.LOJISTA);

        when(carteiraRepository.findById(dto.getPayer()))
                .thenReturn(Optional.of(PayerCarteira));

        when(carteiraRepository.findById(dto.getPayee()))
                .thenReturn(Optional.of(PayeeCarteira));

        assertThrows(BusinessException.class, () -> transacaoService.actTransacao(dto));
    }

    @Test
    void actTransacaoShouldThrowBusinessExceptionLojistaBecausePayerBalanceIsNotEnough() {

        PayerCarteira.setBalance(BigDecimal.ONE);

        when(carteiraRepository.findById(dto.getPayer()))
                .thenReturn(Optional.of(PayerCarteira));

        when(carteiraRepository.findById(dto.getPayee()))
                .thenReturn(Optional.of(PayeeCarteira));

        assertThrows(BusinessException.class, () -> transacaoService.actTransacao(dto));
    }
}