package br.com.leosalema.controller;

import br.com.leosalema.dto.PessoaDTO;
import br.com.leosalema.service.EnderecoService;
import br.com.leosalema.service.PessoaService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PessoaontrollerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PessoaService pessoaService;

    @MockBean
    EnderecoService enderecoService;

    @Nested
    class RetornaTodasPessoasController {
        @Test
        @DisplayName("deve retornar todas as pessoas cadastradas")
        void deveRetornarTodasPessoasCadastradas() throws Exception {
            var pessoa = new PessoaDTO();
            pessoa.setId(1L);
            pessoa.setNomeCompleto("Leonardo");

            when(pessoaService.listarTodos())
                    .thenReturn(List.of(pessoa));


            mockMvc.perform(get("/pessoa"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json("[{\"id\":1,\"nomeCompleto\":\"Leonardo\",\"dataNascimento\":null,\"listaEnderecos\":[]}]"));
        }
    }
    @Nested
    class CriaPessoaController {
        @Test
        @DisplayName("deve criar uma pessoa com sucesso")
        void deveCriarUmaPessoaComSucesso() throws Exception {
            PessoaDTO pessoa = new PessoaDTO();
            pessoa.setId(1L);
            pessoa.setNomeCompleto("Leonardo");

            when(pessoaService.inserir(any())).thenReturn(pessoa);

            mockMvc.perform(post("/pessoa")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"id\":1,\"nomeCompleto\":\"Leonardo\",\"dataNascimento\":null,\"listaEnderecos\":[]}")
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(content().json("{\"id\":1,\"nomeCompleto\":\"Leonardo\",\"dataNascimento\":null,\"listaEnderecos\":[]}"));

        }
    }

    @Nested
    class AtualizaPessoaController {
        @Test
        @DisplayName("deve atualizar uma pessoa com sucesso")
        void deveAtualizarUmaPessoaComSucesso() throws Exception {

            doNothing().when(pessoaService).alterar(any(), any());

            mockMvc.perform(put("/pessoa")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"id\":1,\"cep\":null,\"logradouro\":null,\"numero\":12,\"cidade\":null,\"estado\":null,\"principal\":false}")
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andDo(print())
                    .andExpect(status().isOk());
        }
    }

    @Nested
    class RemovePessoaController {
        @Test
        @DisplayName("deve remover uma pessoa com sucesso")
        void deveRemoverUmaPessoaComSucesso() throws Exception {
            doNothing().when(enderecoService).excluir(1L);

            mockMvc.perform(delete("/pessoa/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"id\":1,\"cep\":null,\"logradouro\":null,\"numero\":12,\"cidade\":null,\"estado\":null,\"principal\":false}")
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andDo(print())
                    .andExpect(status().isOk());
        }
    }
}
