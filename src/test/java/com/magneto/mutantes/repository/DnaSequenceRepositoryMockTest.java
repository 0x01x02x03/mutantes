package com.magneto.mutantes.repository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.magneto.mutantes.model.DnaSequence;

@ExtendWith(MockitoExtension.class) // Habilita las anotaciones de Mockito para JUnit 5
public class DnaSequenceRepositoryMockTest { // Cambiado el nombre para diferenciar del test de integración

    @Mock // Esto crea un mock de tu interfaz DnaSequenceRepository
    private DnaSequenceRepository dnaSequenceRepository;

    @BeforeEach
    public void setUp() {
        // Reiniciar el mock antes de cada test para asegurar un estado limpio
        // Esto es crucial para que cada test tenga sus propios "when" y "verify"
        reset(dnaSequenceRepository);
    }

    @Test
    void testFindById_found() {
        String testHash = "mockedHash1";
        DnaSequence mockedDna = new DnaSequence();
        mockedDna.setDnaHash(testHash);
        mockedDna.setMutant(true);

        // Configura el comportamiento del mock:
        // Cuando se llame a findById con "mockedHash1", devuelve un Optional que contiene mockedDna.
        when(dnaSequenceRepository.findById(testHash)).thenReturn(Optional.of(mockedDna));

        // Llama al método del mock
        Optional<DnaSequence> result = dnaSequenceRepository.findById(testHash);

        // Verifica el resultado esperado
        assertThat(result).isPresent();
        assertThat(result.get().getDnaHash()).isEqualTo(testHash);
        assertThat(result.get().isMutant()).isTrue();

        // Verifica que el método findById fue llamado exactamente una vez con el argumento correcto
        verify(dnaSequenceRepository, times(1)).findById(testHash);
        // Verifica que no se llamaron otros métodos no esperados
        verifyNoMoreInteractions(dnaSequenceRepository);
    }

    @Test
    void testFindById_notFound() {
        String testHash = "nonExistentHash";

        // Configura el comportamiento del mock:
        // Cuando se llame a findById con "nonExistentHash", devuelve Optional.empty().
        when(dnaSequenceRepository.findById(testHash)).thenReturn(Optional.empty());

        // Llama al método del mock
        Optional<DnaSequence> result = dnaSequenceRepository.findById(testHash);

        // Verifica el resultado esperado
        assertThat(result).isNotPresent();

        // Verifica que el método findById fue llamado exactamente una vez con el argumento correcto
        verify(dnaSequenceRepository, times(1)).findById(testHash);
        verifyNoMoreInteractions(dnaSequenceRepository);
    }

    @Test
    void testSave() {
        DnaSequence dnaToSave = new DnaSequence();
        dnaToSave.setDnaHash("newHashToSave");
        dnaToSave.setMutant(false);

        // Configura el comportamiento del mock:
        // Cuando se llame a save con cualquier DnaSequence, devuelve el mismo objeto que se le pasó.
        // Esto simula la persistencia sin realmente guardarlo.
        when(dnaSequenceRepository.save(any(DnaSequence.class))).thenReturn(dnaToSave);

        // Llama al método del mock
        DnaSequence savedDna = dnaSequenceRepository.save(dnaToSave);

        // Verifica el resultado esperado
        assertThat(savedDna).isNotNull();
        assertThat(savedDna.getDnaHash()).isEqualTo("newHashToSave");
        assertThat(savedDna.isMutant()).isFalse();

        // Verifica que el método save fue llamado exactamente una vez con el argumento correcto
        verify(dnaSequenceRepository, times(1)).save(dnaToSave);
        verifyNoMoreInteractions(dnaSequenceRepository);
    }

    @Test
    void testCountByIsMutant_true() {
        // Configura el comportamiento del mock:
        // Cuando se llame a countByIsMutant(true), devuelve 5L.
        when(dnaSequenceRepository.countByIsMutant(true)).thenReturn(5L);

        // Llama al método del mock
        long mutantCount = dnaSequenceRepository.countByIsMutant(true);

        // Verifica el resultado esperado
        assertThat(mutantCount).isEqualTo(5L);

        // Verifica que el método countByIsMutant fue llamado exactamente una vez con el argumento true
        verify(dnaSequenceRepository, times(1)).countByIsMutant(true);
        verifyNoMoreInteractions(dnaSequenceRepository);
    }

    @Test
    void testCountByIsMutant_false() {
        // Configura el comportamiento del mock:
        // Cuando se llame a countByIsMutant(false), devuelve 10L.
        when(dnaSequenceRepository.countByIsMutant(false)).thenReturn(10L);

        // Llama al método del mock
        long humanCount = dnaSequenceRepository.countByIsMutant(false);

        // Verifica el resultado esperado
        assertThat(humanCount).isEqualTo(10L);

        // Verifica que el método countByIsMutant fue llamado exactamente una vez con el argumento false
        verify(dnaSequenceRepository, times(1)).countByIsMutant(false);
        verifyNoMoreInteractions(dnaSequenceRepository);
    }

    @Test
    void testExistsById_true() {
        String testHash = "existingRecordHash";
        // Configura el mock para que exista
        when(dnaSequenceRepository.existsById(testHash)).thenReturn(true);

        boolean exists = dnaSequenceRepository.existsById(testHash);

        assertThat(exists).isTrue();
        verify(dnaSequenceRepository, times(1)).existsById(testHash);
        verifyNoMoreInteractions(dnaSequenceRepository);
    }

    @Test
    void testExistsById_false() {
        String testHash = "nonExistingRecordHash";
        // Configura el mock para que no exista
        when(dnaSequenceRepository.existsById(testHash)).thenReturn(false);

        boolean exists = dnaSequenceRepository.existsById(testHash);

        assertThat(exists).isFalse();
        verify(dnaSequenceRepository, times(1)).existsById(testHash);
        verifyNoMoreInteractions(dnaSequenceRepository);
    }
}
