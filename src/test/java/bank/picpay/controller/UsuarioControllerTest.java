package bank.picpay.controller;

import bank.picpay.models.usuario.UsuarioEntity;
import bank.picpay.models.usuario.cpfDTO;
import bank.picpay.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UsuarioService usuarioService;

    @Test
    void postUsuarioCPF() throws Exception {
        ResponseEntity<UsuarioEntity> usuarioEntity = new ResponseEntity<>(new UsuarioEntity(), HttpStatus.CREATED);
        cpfDTO cpfDTO = new cpfDTO();

        when(usuarioService.validarUsuario(any(cpfDTO.class)))
                .thenReturn(usuarioEntity);

        mockMvc.perform(post("/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cpfDTO))
        ).andExpect(status().isCreated());
    }

    @Test
    void postUsuarioCNPJ() {
    }
}