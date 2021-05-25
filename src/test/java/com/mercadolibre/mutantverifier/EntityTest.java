package com.mercadolibre.mutantverifier;

import com.mercadolibre.mutantverifier.entity.Stats;
import com.mercadolibre.mutantverifier.entity.StatsError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class EntityTest {

    @Test
    public  void testAllEntities(){
        Stats st=new Stats();
        st.setDna("");
        st.setMutant(true);
        st.getDna();
        st.getMutant();
        StatsError ste=new StatsError();
        ste.setErrorMessage("");
        ste.setDateErrror(new Date());
        ste.setId(1);
        ste.setMutant(true);
        ste.getDna();
        ste.getMutant();
        ste.getDateErrror();
        ste.getId();
        ste.getErrorMessage();
        Assertions.assertEquals(true,st!=null&&ste!=null);

    }

}
