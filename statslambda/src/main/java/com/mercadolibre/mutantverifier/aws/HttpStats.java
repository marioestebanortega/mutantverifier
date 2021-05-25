package com.mercadolibre.mutantverifier.aws;


import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.mutantverifier.dto.StatsDto;
import com.mercadolibre.mutantverifier.service.MutantService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.function.Function;
@Component
public class HttpStats
        implements Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private static final Logger logger =  LogManager.getLogger(HttpStats.class);

    @Autowired
    private MutantService mutantService;

    private APIGatewayProxyResponseEvent generateResponse(String message,HttpStatus var){
        APIGatewayProxyResponseEvent res=new APIGatewayProxyResponseEvent();
        res.setStatusCode(var.value());
        res.setBody(message);
        return res;
    };

    @Override
    public APIGatewayProxyResponseEvent apply(APIGatewayProxyRequestEvent req) {

        try {
            StatsDto out = new StatsDto();
            ObjectMapper objectMapper = new ObjectMapper();
            Integer countMutant = mutantService.countMutantsOrHumans(true);
            Integer countHumans = mutantService.countMutantsOrHumans(false);
            if (countHumans == 0) {
                throw new Exception("No se puede realizar el calculo de ratio debido a que la cantidad de humanos es 0");
            }

            Double ratio = Double.valueOf(countMutant) / Double.valueOf(countHumans);

            out.setRatio(ratio);
            out.setCount_mutant_dna(countMutant);
            out.setCount_human_dna(countHumans);
            String outS = objectMapper.writeValueAsString(out);
            return generateResponse(outS,HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return generateResponse(e.getMessage(),HttpStatus.SERVICE_UNAVAILABLE);

        }

    }


}
