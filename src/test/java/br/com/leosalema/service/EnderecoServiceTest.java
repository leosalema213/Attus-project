package br.com.leosalema.service;

import br.com.leosalema.dto.EnderecoDTO;
import br.com.leosalema.dto.PessoaDTO;
import br.com.leosalema.entity.EnderecoEntity;
import br.com.leosalema.repository.EnderecoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EnderecoServiceTest {

    @InjectMocks
    EnderecoService service;

    @Mock
    EnderecoRepository enderecoRepository;

    @Captor
    private ArgumentCaptor<EnderecoEntity> useArgumentCaptor;
    @Captor
    private ArgumentCaptor<Long> idArgumentCaptor;

    @Nested
    class RetornaTodosEnderecosService {
        @Test
        @DisplayName("Deve retornar todos enderecos com sucesso")
        void deveRetornarTodosEnderecoComSucesso() {
            EnderecoEntity endereco = new EnderecoEntity();

            var endercoList = List.of(endereco);
            doReturn(List.of(endereco))
                    .when(enderecoRepository)
                    .findAll();

            var output = service.listarTodos();

            assertNotNull(output);
            assertEquals(endercoList.size(), output.size());
        }

        @Test
        @DisplayName("Nao deve retornar enderecos")
        void naoDeveRetornarEnderecos() {
            doReturn(null)
                    .when(enderecoRepository)
                    .findAll();

            assertThrows(NullPointerException.class, () -> service.listarTodos() );

        }
    }

    @Nested
    class CriaEnderecoService {
        @Test
        @DisplayName("Deve criar um endereco com sucesso")
        void deveCriarUmEnderecoComSucesso() {
            EnderecoEntity endereco = new EnderecoEntity();
            endereco.setId(1L);
            endereco.setCep(15806345L);
            endereco.setEstado("SP");
            endereco.setCidade("Catanduva");
            endereco.setLogradouro("Rua das flores");
            endereco.setNumero(24);

            doReturn(endereco)
                    .when(enderecoRepository)
                    .save(useArgumentCaptor.capture());

            var input = new EnderecoDTO();
            input.setId(1L);
            input.setCep(15806345L);
            input.setEstado("SP");
            input.setCidade("Catanduva");
            input.setLogradouro("Rua das flores");
            input.setNumero(24);

            service.inserir(input);

            var userCaptured = useArgumentCaptor.getValue();

            assertEquals(input.getId(), userCaptured.getId());
            assertEquals(input.getCep(), userCaptured.getCep());
            assertEquals(input.getEstado(), userCaptured.getEstado());
            assertEquals(input.getCidade(), userCaptured.getCidade());
            assertEquals(input.getLogradouro(), userCaptured.getLogradouro());
            assertEquals(input.getNumero(), userCaptured.getNumero());

            verify(enderecoRepository, times(1)).save(useArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Nao deve criar um endereco se ocorrer um erro")
        void naoDeveCriarUmEnderecoSeOcorrerUmErro() {

            doThrow(new RuntimeException())
                    .when(enderecoRepository)
                    .save(any());


            var input = new EnderecoDTO();

            assertThrows(RuntimeException.class, () -> service.inserir(input));
        }
    }

    @Nested
    class AtualizaEnderecoService {
        @Test
        @DisplayName("Deve atualizar um endereco se ele existir")
        void deveAtualizarEnderecoSeEleExistir() {
            EnderecoEntity enderecoAtualizado = new EnderecoEntity();
            enderecoAtualizado.setId(1L);
            enderecoAtualizado.setCep(15806345L);
            enderecoAtualizado.setCidade("catanduva");
            enderecoAtualizado.setLogradouro("rua das beterrabas");

            EnderecoEntity endereco = new EnderecoEntity();
            endereco.setId(1L);
            endereco.setCep(15806345L);
            endereco.setCidade("catanduva");
            endereco.setLogradouro("rua das flores");

            doReturn(Optional.of(endereco))
                    .when(enderecoRepository)
                    .findById(idArgumentCaptor.capture());

            doReturn(enderecoAtualizado)
                    .when(enderecoRepository)
                    .save(useArgumentCaptor.capture());

            service.alterar(enderecoAtualizado.getId(), new EnderecoDTO(enderecoAtualizado));

            assertEquals(enderecoAtualizado.getId(), idArgumentCaptor.getValue());

            var peopleCaptured = useArgumentCaptor.getValue();

            assertEquals(enderecoAtualizado.getCidade(), peopleCaptured.getCidade());
            assertEquals(enderecoAtualizado.getEstado(), peopleCaptured.getEstado());
            assertEquals(enderecoAtualizado.getLogradouro(), peopleCaptured.getLogradouro());

            verify(enderecoRepository, times(1)).findById(idArgumentCaptor.getValue());
            verify(enderecoRepository, times(1)).save(useArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Nao deve atualizar um endereco se ele nao existir")
        void naoDeveAtualizarEnderecoSeEleNaoExistir() {

            var updateAdress = new EnderecoEntity();
            updateAdress.setId(1L);
            updateAdress.setCep(15806345L);
            updateAdress.setCidade("catanduva");
            updateAdress.setLogradouro("rua das beterrabas");

            var AdressId = 1L;

            doReturn(Optional.empty())
                    .when(enderecoRepository)
                    .findById(idArgumentCaptor.capture());

            service.alterar(AdressId, new EnderecoDTO(updateAdress));

            assertEquals(updateAdress.getId(), idArgumentCaptor.getValue());

            verify(enderecoRepository, times(1)).findById(idArgumentCaptor.getValue());
            verify(enderecoRepository, times(0)).save(any());
        }

    }


    @Nested
    class RemoveEnderecoService {
        @Test
        @DisplayName("Deve remover um endereco com sucesso se ele existir")
        void deveRemoverUmEnderecoComSucessoSeEleExistir() {
            doReturn(true)
                    .when(enderecoRepository)
                    .existsById(idArgumentCaptor.capture());

            doNothing()
                    .when(enderecoRepository)
                    .deleteById(idArgumentCaptor.capture());

            var AdressId = 1L;

            service.excluir(AdressId);

            var idList = idArgumentCaptor.getAllValues();
            assertEquals(AdressId, idList.get(0));
            assertEquals(AdressId, idList.get(1));

            verify(enderecoRepository, times(1)).existsById(idList.get(0));
            verify(enderecoRepository, times(1)).deleteById(idList.get(1));

        }

        @Test
        @DisplayName("Nao deve remover um endereco se ele nao existir")
        void naoDeveRemoverUmEnderecoSeEleNaoExistir() {

            doReturn(false)
                    .when(enderecoRepository)
                    .existsById(idArgumentCaptor.capture());

            var AdressId = 1L;

            service.excluir(AdressId);

            assertEquals(AdressId, idArgumentCaptor.getValue());

            verify(enderecoRepository, times(1)).existsById(idArgumentCaptor.getValue());
            verify(enderecoRepository, times(0)).deleteById(any());

        }
    }

}
