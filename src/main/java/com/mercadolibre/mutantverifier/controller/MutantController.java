package com.mercadolibre.mutantverifier.controller;

import com.mercadolibre.mutantverifier.dto.DnaData;
import com.mercadolibre.mutantverifier.dto.StatsDto;
import com.mercadolibre.mutantverifier.service.MutantService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/")
public class MutantController {

    @Autowired
    private MutantService mutantService;

    private static final Logger logger =  LogManager.getLogger(MutantController.class);

    @GetMapping("/stats")
    public ResponseEntity<StatsDto> getStats() {
        try {
            StatsDto out = new StatsDto();
            Integer countMutant = mutantService.countMutantsOrHumans(true);
            Integer countHumans = mutantService.countMutantsOrHumans(false);
            if (countHumans == 0) {
                throw new Exception("No se puede realizar el calculo de ratio debido a que la cantidad de humanos es 0");
            }

            Double ratio = Double.valueOf(countMutant) / Double.valueOf(countHumans);

            out.setRatio(ratio);
            out.setCount_mutant_dna(countMutant);
            out.setCount_human_dna(countHumans);
            return new ResponseEntity<StatsDto>(out, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<StatsDto>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    /**
     * Controller para recibir cadena ADN para validar si es mutante una persona
     *
     * @param di
     * @return
     */
    @PostMapping(value = "/mutant", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> verifier(@RequestBody DnaData di) {
        try {
            boolean isMutant = mutantService.isMutant(di.getDna());

            try {
                //En esta varte se gurda la verificacion del DNA
                //en la tabla de estadisiticas siempre y cuando
                //el registro de DNA sea nuevo
                if (!mutantService.existDna(di.getDna())) {
                    mutantService.saveStats(di.getDna(), isMutant);
                }
            } catch (Exception e) {
                try {
                    //Si hay un error guardando la estadisticas no deberia interrumpirse el servicio
                    //Se propone guardar el problema en la siguiente table de errores
                    mutantService.saveLog(di.getDna(), isMutant, e.getMessage());
                    logger.error(e.getMessage());
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
                } catch (Exception ex) {
                    //Es posible que sea por eerores de conexion a base de datos o de red
                    //Esta exception deberia ser controladoa en otro tipo de log
                    logger.error(e.getMessage());
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
                }
            }

            if (isMutant) {
                return new ResponseEntity<>("Este ADN corresponde a un mutante", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Este ADN NO corresponde a un mutante", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
