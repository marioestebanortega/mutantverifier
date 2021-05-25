package com.mercadolibre.mutantverifier;

import com.mercadolibre.mutantverifier.util.CalcsUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CalcUtilsTest {
    @Test
    public void testMutantHorizontal(){
        try {
            String[] test = {
                    "AAAA",
                    "AAAT",
                    "ATAT",
                    "TTAT"};
            boolean ver= CalcsUtil.isMutant(test);
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
            boolean ver=CalcsUtil.isMutant(test);
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
            boolean ver=CalcsUtil.isMutant(test);
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
            boolean ver=CalcsUtil.isMutant(test);
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
            boolean ver=CalcsUtil.isMutant(test);
            Assertions.assertEquals(false,ver);
        }
        catch(Exception e){

        }
    }
}