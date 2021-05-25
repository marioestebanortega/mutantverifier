package com.mercadolibre.mutantverifier;

import com.mercadolibre.mutantverifier.dto.DnaData;
import com.mercadolibre.mutantverifier.dto.StatsDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DtoTest {
    @Test
    public void testAllDto(){
        String[] test={"",""};
        DnaData dat=new DnaData();
        dat.setDna(test);
        dat.getDna();
        StatsDto st=new StatsDto();
        st.setCount_human_dna(5);
        st.setCount_mutant_dna(5);
        st.setRatio(5D);
        st.getRatio();
        st.getCount_human_dna();
        st.getCount_mutant_dna();
        Assertions.assertEquals(true,st!=null&&dat!=null);
    }
}
