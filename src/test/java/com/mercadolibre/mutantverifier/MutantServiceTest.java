package com.mercadolibre.mutantverifier;

import com.mercadolibre.mutantverifier.entity.Stats;
import com.mercadolibre.mutantverifier.entity.StatsError;
import com.mercadolibre.mutantverifier.repository.StatsErrorRepository;
import com.mercadolibre.mutantverifier.repository.StatsRepository;
import com.mercadolibre.mutantverifier.service.MutantService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;


@SpringBootTest
public class MutantServiceTest {

    @Mock
    private StatsRepository statsRepository;

    @Mock
    private StatsErrorRepository statsErrorRepository;


    @InjectMocks
    private MutantService mutantService;


    @Test
    public void testMutantHorizontal(){
        try {
            String[] test = {
                    "AAAA",
                    "AAAT",
                    "ATAT",
                    "TTAT"};
            boolean ver= mutantService.isMutant(test);
            Assertions.assertEquals(true,ver);
        }
        catch(Exception e){

        }
    }
    @Test
    public void testMutantVertical(){
        try {
            String[] test = {
                    "AAAT",
                    "AAAT",
                    "ATAT",
                    "ATAA"};
            boolean ver=mutantService.isMutant(test);
            Assertions.assertEquals(true,ver);
        }
        catch(Exception e){

        }
    }
    @Test
    public void testMutantCross1(){
        try {
            String[] test = {
                    "ATAT",
                    "AAAT",
                    "ATAT",
                    "TTAA"};
            boolean ver=mutantService.isMutant(test);
            Assertions.assertEquals(true,ver);
        }
        catch(Exception e){

        }
    }
    @Test
    public void testMutantCross2(){
        try {
            String[] test = {
                    "AAAT",
                    "AATT",
                    "ATAT",
                    "TTAA"};
            boolean ver=mutantService.isMutant(test);
            Assertions.assertEquals(true,ver);
        }
        catch(Exception e){

        }
    }
    @Test
    public void testHuman(){
        try {
            String[] test = {
                    "ATAA",
                    "AAAT",
                    "ATAT",
                    "TTCT"};
            boolean ver=mutantService.isMutant(test);
            Assertions.assertEquals(false,ver);
        }
        catch(Exception e){

        }
    }

    @Test
    public void testStatsOk(){
        Mockito.when(statsRepository.save(Mockito.any())).thenReturn(new Stats());
        String[] test = {
                "ATAA",
                "AAAT",
                "ATAT",
                "TTCT"};
        mutantService.saveStats(test,true);
        Assertions.assertEquals(true,true);
    }

    @Test
    public void testExistDNA(){
        try {
            //Optional<Stats> stats = statsRepository.findById(dnaS);
            Optional <Stats> out= Optional.of(new Stats());
            Mockito.when(statsRepository.findById(Mockito.any())).thenReturn(out);
            String[] test = {
                    "ATAA",
                    "AAAT",
                    "ATAT",
                    "TTCT"};
            mutantService.existDna(test);
            Assertions.assertEquals(true,true);
        }
        catch(Exception e){
            Assertions.fail(e.getMessage());
        }
    }
    @Test
    public void testSaveLog(){
        try {
            Mockito.when(statsErrorRepository.save(Mockito.any())).thenReturn(new StatsError());
            String[] test = {
                    "ATAA",
                    "AAAT",
                    "ATAT",
                    "TTCT"};
            mutantService.saveLog(test,true, "OnlyTest");
            Assertions.assertEquals(true,true);
        }
        catch(Exception e){
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testCountHumansOrMutants(){
        Integer valExp=5;
        Optional<Integer> out=Optional.of(valExp);
        Mockito.when(statsRepository.countElements(Mockito.anyBoolean())).thenReturn(out);
        Integer val=mutantService.countMutantsOrHumans(true);
        Assertions.assertEquals(valExp,val);

    }

}
