package br.com.leosalema.controller;

import br.com.leosalema.dto.EnderecoDTO;

import br.com.leosalema.service.EnderecoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EnderecoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    EnderecoService enderecoService;

    @Nested
    class RetornaTodosEnderecosController {
        @Test
        @DisplayName("deve retornar todos enderecos com sucesso")
        void deveRetornarTodosEnderecosComSucesso() throws Exception {
            var endereco = new EnderecoDTO();
            endereco.setId(1L);
            endereco.setNumero(12);

            when(enderecoService.listarTodos())
                    .thenReturn(List.of(endereco));

            mockMvc.perform(get("/endereco"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json("[{id: 1,cep: null,logradouro: null,numero: 12,cidade: null,estado: null,principal: false}]"));
        }
    }

    @Nested
    class DeveCriarUmEnderecoController {
        @Test
        @DisplayName("Deve Criar um endereco com sucesso")
        void DeveCriarUmEnderecoComSucesso() throws Exception {
            EnderecoDTO endereco = new EnderecoDTO();
            endereco.setId(1L);
            endereco.setNumero(12);
            endereco.setEstado("sp");


           when(enderecoService.inserir(any())).thenReturn(endereco);
            mockMvc.perform(post("/endereco")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"id\":1,\"cep\":null,\"logradouro\":null,\"numero\":12,\"cidade\":null,\"estado\":null,\"principal\":false}")
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json("{\"id\":1,\"cep\":null,\"logradouro\":null,\"numero\":12,\"cidade\":null,\"estado\":null,\"principal\":false}"));
        }
    }

    @Nested
    class AtualizaEnderecoController {
        @Test
        @DisplayName("Deve atualizar o endereco com sucesso")
        void deveAtualizarEnderecoComSucesso() throws Exception {

            doNothing().when(enderecoService).alterar(any(), any());
            mockMvc.perform(put("/endereco")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"id\":1,\"cep\":null,\"logradouro\":null,\"numero\":12,\"cidade\":null,\"estado\":null,\"principal\":false}")
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andDo(print())
                    .andExpect(status().isOk());
        }
    }

    @Nested
    class RemoveEnderecoController {
        @Test
        @DisplayName("Deve remover um endereco com sucesso")
        void deveRemoverEnderecoComSucesso() throws Exception {
            doNothing().when(enderecoService).excluir(1L);

            mockMvc.perform(delete("/endereco/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"id\":1,\"cep\":null,\"logradouro\":null,\"numero\":12,\"cidade\":null,\"estado\":null,\"principal\":false}")
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andDo(print())
                    .andExpect(status().isOk());
        }
    }


}
