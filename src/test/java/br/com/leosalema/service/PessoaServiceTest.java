package br.com.leosalema.service;

import br.com.leosalema.dto.PessoaDTO;
import br.com.leosalema.entity.EnderecoEntity;
import br.com.leosalema.entity.PessoaEntity;
import br.com.leosalema.repository.PessoaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PessoaServiceTest {

    @InjectMocks
    PessoaService service;

    @Mock
    PessoaRepository pessoaRepository;

    @Captor
    private ArgumentCaptor<PessoaEntity> useArgumentCaptor;
    @Captor
    private ArgumentCaptor<Long> idArgumentCaptor;

    @Nested
    class RetornaTodasPessoasService {
        @Test
        @DisplayName("Deve retornar todas pessoas com sucesso")
        void deveRetornarTodasPessoasComSucesso() {
            PessoaEntity pessoa = new PessoaEntity();

            var pessoasList = List.of(pessoa);
            doReturn(List.of(pessoa))
                    .when(pessoaRepository)
                    .findAll();

            var output = service.listarTodos();

            assertNotNull(output);
            assertEquals(pessoasList.size(), output.size());
        }

        @Test
        @DisplayName("Nao deve retornar pessoas")
        void naoDeveRetornarPessoas() {
            doReturn(null)
                    .when(pessoaRepository)
                    .findAll();

            assertThrows(NullPointerException.class, () -> service.listarTodos() );

        }
    }

    @Nested
    class CriaPessoaService {
        @Test
        @DisplayName("Deve criar uma pessoa com sucesso")
        void deveCriarUmaPessoaComSucesso() {
            PessoaEntity pessoa = new PessoaEntity();
            pessoa.setNomeCompleto("Leo");
            pessoa.setDataNascimento("12/06/2002");
            pessoa.setListaEndereco(new EnderecoEntity());

            doReturn(pessoa)
                    .when(pessoaRepository)
                    .save(useArgumentCaptor.capture());

            var input = new PessoaDTO();
            input.setNomeCompleto("Leo");
            input.setDataNascimento("12/06/2002");
            pessoa.setListaEndereco(new EnderecoEntity());

            var output = service.inserir(input);

            assertNotNull(output);
            var userCaptured = useArgumentCaptor.getValue();

            assertEquals(input.getNomeCompleto(), userCaptured.getNomeCompleto());
            assertEquals(input.getDataNascimento(), userCaptured.getDataNascimento());
            assertEquals(input.getListaEnderecos(), userCaptured.getListaEndereco());
        }

        @Test
        @DisplayName("Nao deve criar uma pessoa se ocorrer um erro")
        void naoDeveCriarUmaPessoaSeOcorrerUmErro() {

            doThrow(new RuntimeException())
                    .when(pessoaRepository)
                    .save(any());

            var input = new PessoaDTO(
                    1L, "Leonardo","12/06/2002", Collections.singletonList(new EnderecoEntity())
            );

            assertThrows(RuntimeException.class, () -> service.inserir(input));
        }
    }

    @Nested
    class AtualizaPessoaService {
        @Test
        @DisplayName("Deve atualizar uma pessoa se ela existir")
        void deveAtualizarPessoaSeElaExistir() {
            var pessoaAtualizada = new PessoaEntity();
            pessoaAtualizada.setId(1L);
            pessoaAtualizada.setNomeCompleto("Leo patrick");
            pessoaAtualizada.setDataNascimento("12/06/2002");
            pessoaAtualizada.setListaEndereco(new EnderecoEntity());

            PessoaEntity pessoa = new PessoaEntity();
            pessoa.setId(1L);
            pessoa.setNomeCompleto("Leo");

            doReturn(Optional.of(pessoa))
                    .when(pessoaRepository)
                    .findById(idArgumentCaptor.capture());

            doReturn(pessoaAtualizada)
                    .when(pessoaRepository)
                    .save(useArgumentCaptor.capture());

            service.alterar(pessoaAtualizada.getId(), new PessoaDTO(pessoaAtualizada));

            assertEquals(pessoaAtualizada.getId(), idArgumentCaptor.getValue());

            var pessoaCapturada = useArgumentCaptor.getValue();

            assertEquals(pessoaAtualizada.getNomeCompleto(), pessoaCapturada.getNomeCompleto());
            assertEquals(pessoaAtualizada.getDataNascimento(), pessoaCapturada.getDataNascimento());
            assertEquals(pessoaAtualizada.getListaEndereco(), pessoaCapturada.getListaEndereco());

            verify(pessoaRepository, times(1)).findById(idArgumentCaptor.getValue());
            verify(pessoaRepository, times(1)).save(useArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Nao deve atualizar uma pessoa se ela nao existir")
        void naoDeveAtualizarPessoaSeElaNaoExistir() {

            var updatePessoa = new PessoaEntity();
            updatePessoa.setId(1L);
            updatePessoa.setNomeCompleto("Leo patrick");
            updatePessoa.setDataNascimento("12/06/2002");
            updatePessoa.setListaEndereco(new EnderecoEntity());

            var pessoaId = 1L;

            doReturn(Optional.empty())
                    .when(pessoaRepository)
                    .findById(idArgumentCaptor.capture());

            service.alterar(pessoaId, new PessoaDTO(updatePessoa));

            assertEquals(updatePessoa.getId(), idArgumentCaptor.getValue());

            verify(pessoaRepository, times(1)).findById(idArgumentCaptor.getValue());
            verify(pessoaRepository, times(0)).save(any());
        }
    }

    @Nested
    class RemovePessoaService {
        @Test
        @DisplayName("Deve remover uma pessoa com sucesso se ela existir")
        void deveRemoverUmaPessoaComSucessoSeElaExistir() {
            doReturn(true)
                    .when(pessoaRepository)
                    .existsById(idArgumentCaptor.capture());

            doNothing()
                    .when(pessoaRepository)
                    .deleteById(idArgumentCaptor.capture());

            var pessoaID = 1L;

            service.excluir(pessoaID);

            var idList = idArgumentCaptor.getAllValues();
            assertEquals(pessoaID, idList.get(0));
            assertEquals(pessoaID, idList.get(1));

            verify(pessoaRepository, times(1)).existsById(idList.get(0));
            verify(pessoaRepository, times(1)).deleteById(idList.get(1));

        }

        @Test
        @DisplayName("Nao deve remover uma pessoa se ela nao existir")
        void naoDeveRemoverUmaPessoaSeElaNaoExistir() {

            doReturn(false)
                    .when(pessoaRepository)
                    .existsById(idArgumentCaptor.capture());

            var pessoaID = 1L;

            service.excluir(pessoaID);

            assertEquals(pessoaID, idArgumentCaptor.getValue());

            verify(pessoaRepository, times(1)).existsById(idArgumentCaptor.getValue());
            verify(pessoaRepository, times(0)).deleteById(any());

        }
    }


}
