package com.mercadolibre.mutantverifier.aws;


import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.mutantverifier.dto.DnaData;
import com.mercadolibre.mutantverifier.service.MutantService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class HttpPostMutant implements Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private static final Logger logger =  LogManager.getLogger(HttpPostMutant.class);

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
            ObjectMapper mapper = new ObjectMapper();
            String jsonInString = req.getBody();
            //JSON from String to Object
            DnaData di = mapper.readValue(jsonInString, DnaData.class);

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
                    return generateResponse(e.getMessage(), HttpStatus.OK);
                } catch (Exception ex) {
                    //Es posible que sea por eerores de conexion a base de datos o de red
                    //Esta exception deberia ser controladoa en otro tipo de log
                    logger.error(e.getMessage());
                    return generateResponse(e.getMessage(), HttpStatus.OK);
                }
            }
            if (isMutant) {
                return generateResponse("Este ADN corresponde a un mutante", HttpStatus.OK);
            } else {
                return generateResponse("Este ADN NO corresponde a un mutante", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return generateResponse(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }


}
