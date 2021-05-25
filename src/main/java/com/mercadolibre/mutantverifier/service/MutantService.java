package com.mercadolibre.mutantverifier.service;

import com.mercadolibre.mutantverifier.entity.Stats;
import com.mercadolibre.mutantverifier.entity.StatsError;
import com.mercadolibre.mutantverifier.repository.StatsErrorRepository;
import com.mercadolibre.mutantverifier.repository.StatsRepository;
import com.mercadolibre.mutantverifier.util.CalcsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * Servicio de calculo de mutantes
 */
@Service
public class MutantService {

    @Autowired
    private StatsRepository statsRepository;

    @Autowired
    private StatsErrorRepository statsErrorRepository;

    /**
     * Metodo encargado de verificar si una persona
     * es mutante o humano basadao en su cadena de ADN
     * @param di
     * @return
     * @throws Exception
     */
    public boolean isMutant(String[] di) throws Exception {
        return CalcsUtil.isMutant(di);
    }



    public void saveStats(String[] dna,boolean verific){
        String dnaS="";
        Stats stats=new Stats();
        stats.setMutant(verific);

        dnaS=CalcsUtil.toStringDna(dna);
        stats.setDna(dnaS);
        stats.setMutant(verific);
        statsRepository.save(stats);
    }

    public boolean existDna(String[] dna)  throws Exception {
        try {
            String dnaS = CalcsUtil.toStringDna(dna);
            Optional<Stats> stats = statsRepository.findById(dnaS);
            return stats.isPresent();
        }
        catch(Exception e){
            throw new Exception("General error:"+e.getMessage());
        }
    }

    public void saveLog(String[] dna, boolean verific, String message){
        String dnaS=CalcsUtil.toStringDna(dna);
        StatsError se=new StatsError();
        se.setMutant(verific);
        se.setDna(dnaS);
        se.setDateErrror(new Date());
        se.setErrorMessage(message);
        statsErrorRepository.save(se);
    }

    public Integer countMutantsOrHumans(Boolean type) {
        Optional<Integer> elements=statsRepository.countElements(type);
        return elements.get();
    }
}
