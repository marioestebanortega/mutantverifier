package com.mercadolibre.mutantverifier;

import com.mercadolibre.mutantverifier.controller.MutantController;
import com.mercadolibre.mutantverifier.dto.DnaData;
import com.mercadolibre.mutantverifier.service.MutantService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class MutantControllerTest {


    @Mock
    private MutantService mutantService;

    @InjectMocks
    private MutantController mutantController;


    @Test
    public void testVerifierException1() {
        try {
            Mockito.when(mutantService.isMutant(Mockito.any())).thenThrow(new Exception());
            Mockito.when(mutantService.existDna(Mockito.any())).thenReturn(false);
            Mockito.doNothing().when(mutantService).saveStats(Mockito.any(), Mockito.anyBoolean());

            String[] test = {
                    "AAAA",
                    "AAAT",
                    "ATAT",
                    "TTAT"};
            DnaData dna = new DnaData();
            dna.setDna(test);
            ResponseEntity<String> out = mutantController.verifier(dna);
            Integer code = out.getStatusCodeValue();
            Assertions.assertEquals(HttpStatus.SERVICE_UNAVAILABLE.value(), code);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testVerifierException2() {
        try {
            Mockito.when(mutantService.isMutant(Mockito.any())).thenReturn(true);
            Mockito.when(mutantService.existDna(Mockito.any())).thenThrow(new Exception());
            Mockito.doNothing().when(mutantService).saveStats(Mockito.any(), Mockito.anyBoolean());
            Mockito.doNothing().when(mutantService).saveLog(Mockito.any(), Mockito.anyBoolean(), Mockito.anyString());


            String[] test = {
                    "AAAA",
                    "AAAT",
                    "ATAT",
                    "TTAT"};
            DnaData dna = new DnaData();
            dna.setDna(test);
            ResponseEntity<String> out = mutantController.verifier(dna);
            Integer code = out.getStatusCodeValue();
            Assertions.assertEquals(HttpStatus.OK.value(), code);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testVerifierOk() {
        try {
            Mockito.when(mutantService.isMutant(Mockito.any())).thenReturn(true);
            Mockito.when(mutantService.existDna(Mockito.any())).thenReturn(false);
            Mockito.doNothing().when(mutantService).saveStats(Mockito.any(), Mockito.anyBoolean());

            String[] test = {
                    "AAAA",
                    "AAAT",
                    "ATAT",
                    "TTAT"};
            DnaData dna = new DnaData();
            dna.setDna(test);

            ResponseEntity<String> out = mutantController.verifier(dna);

            Integer code = out.getStatusCodeValue();

            Assertions.assertEquals(HttpStatus.OK.value(), code);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }

    }

    @Test
    public void testVerifierOkWithExistDna() {
        try {
            Mockito.when(mutantService.isMutant(Mockito.any())).thenReturn(true);
            Mockito.when(mutantService.existDna(Mockito.any())).thenReturn(true);

            String[] test = {
                    "AAAA",
                    "AAAT",
                    "ATAT",
                    "TTAT"};
            DnaData dna = new DnaData();
            dna.setDna(test);

            ResponseEntity<String> out = mutantController.verifier(dna);

            Integer code = out.getStatusCodeValue();

            Assertions.assertEquals(HttpStatus.OK.value(), code);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }

    }

    @Test
    public void verifyStatsOk() {
        Mockito.when(mutantService.countMutantsOrHumans(Mockito.any())).thenReturn(5);
        ResponseEntity re = mutantController.getStats();
        Assertions.assertEquals(HttpStatus.OK.value(), re.getStatusCodeValue());
    }

}
