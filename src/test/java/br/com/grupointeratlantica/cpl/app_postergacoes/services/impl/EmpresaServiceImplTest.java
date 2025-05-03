package br.com.grupointeratlantica.cpl.app_postergacoes.services.impl;

import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.empresa.EmpresaAtualizacaoDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.models.Empresa;
import br.com.grupointeratlantica.cpl.app_postergacoes.repositories.EmpresaRepository;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.exceptions.EmpresaInexistenteException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class EmpresaServiceImplTest {

    @Mock
    private EmpresaRepository empresaRepository;

    @InjectMocks
    private EmpresaServiceImpl empresaServiceImpl;

    @Nested
    class DeletarEmpresaPorId{
        @Test
        void testDeletarEmpresaPorId(){
            // Arrange
            Empresa empresa = new Empresa(null, 1, "ITM Matriz", "matriz@gmail.com");

            given(empresaRepository.save(empresa)).willAnswer(invocationOnMock -> {
                empresa.setId(1);
                return empresa;
            });

            Empresa novaEmpresa = empresaRepository.save(empresa);

            given(empresaRepository.existsById(novaEmpresa.getId())).willReturn(true);

            willDoNothing().given(empresaRepository).deleteById(novaEmpresa.getId());

            // Act
            empresaServiceImpl.deletarEmpresaPorId(empresa.getId());

            // Assert
            verify(empresaRepository, times(1)).deleteById(novaEmpresa.getId());
        }

        @Test
        void testDeletarEmpresaPorId_Falha() {
            // Arrange
            Empresa empresa = new Empresa(null, 1, "ITM Matriz", "matriz@gmail.com");

            given(empresaRepository.existsById(empresa.getId())).willReturn(false);

            // Act e Assert
            Exception exception = assertThrows(EmpresaInexistenteException.class, () -> {
                empresaServiceImpl.deletarEmpresaPorId(empresa.getId());
            });

            assertEquals("Empresa inexistente.", exception.getMessage());

            verify(empresaRepository, times(0)).deleteById(empresa.getId());
        }
    }

    @Nested
    class AtualizarEmpresa{
        @Test
        void testAtualizarEmpresa(){
            EmpresaAtualizacaoDTO dto = new EmpresaAtualizacaoDTO(1, 1, "Alterado", "alterado@gmail.com");

            Empresa empresa = new Empresa(1, 1, "ITM Matriz", "matriz@gmail.com");

            given(empresaRepository.findById(empresa.getId())).willReturn(Optional.of(empresa));

            given(empresaRepository.save(empresa)).willReturn(empresa);

            empresaRepository.save(empresa);

            empresaServiceImpl.atualizarEmpresa(dto);

            assertEquals("Alterado", empresa.getNome());
        }

        @Test
        void testAtualizarEmpresa_Falha(){
            EmpresaAtualizacaoDTO dto = new EmpresaAtualizacaoDTO(1, 1, "Alterado", "alterado@gmail.com");

            given(empresaRepository.findById(anyInt())).willReturn(Optional.empty());

            assertThrows(EmpresaInexistenteException.class, () -> {
                empresaServiceImpl.atualizarEmpresa(dto);
            });
        }
    }

}