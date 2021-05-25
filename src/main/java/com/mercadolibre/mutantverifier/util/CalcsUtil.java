package com.mercadolibre.mutantverifier.util;

import com.mercadolibre.mutantverifier.dto.DnaData;

public class CalcsUtil {

    /**
     * Verificacion de la cadena transformandola en Matriz NxX
     * Verificacion Horizontal, Vertical y cruzada
     * @param dna
     * @return
     * @throws Exception
     */
    public static boolean isMutant(String[] dna) throws Exception {
        int i = 0;
        int sizeV = dna.length;
        int validation[][] = new int[sizeV][sizeV];
        int cantDna = 0;
        //Cantidad de numero de secuncia de caracteres para indentificar si una persona es mutante
        int CANT_M = 4;
        //Esta variable ayuda a finalizar el proceso si encuentra al menos una secuencia de ADN
        //Si se requiere revisar todas las cadenas se deja esta variable en falso
        boolean FINISH_IMMEDIATE = true;


        if (dna == null || dna.length == 0) {
            throw new Exception("La cadena enviada esta vacia o nula");
        }

        String[][] out = new String[sizeV][sizeV];

        if (dna[0].length() != dna.length) {
            throw new Exception("No se envió una cadena NxN correcta, revise la tabla enviada");
        }
        //Ciclo para realizar validacion de carcateres
        //Ciclo para convertire el parametro de entrada en una matriz
        for (String s : dna) {
            int j = 0;
            for (j = 0; j < sizeV; j++) {
                String letter = String.valueOf(s.charAt(j));
                if (!AdnLettersEnum.contains(letter)) {
                    throw new Exception("El caracter:" + letter + " no corresponde aun valor válido de ADN");
                }
                out[i][j] = letter;

            }
            i++;
        }

        //Revisión horizontal de la matriz
        for (int i2 = 0; i2 < sizeV; i2++) {
            int cantOk = 1;
            for (int j = 0; j < sizeV; j++) {
                int acty = j;
                int actx = i2;
                int nexty = acty + 1;
                int nextx = actx;

                //Se valida si el elemnto siguiente es igual al anterior para identificar la secuncia valida
                //e ir contando hata verificar que la cantidad sea igual a CANT_M
                if (actx >= 0
                        && actx < sizeV
                        && acty >= 0
                        && acty < sizeV
                        && validation[actx][acty] == 0) {
                    String let = out[actx][acty];
                    if ((nexty >= 0) && (nexty < sizeV) && nextx >= 0 && nextx < sizeV
                            && out[nextx][nexty].equals(let)) {
                        cantOk++;
                    } else {
                        cantOk = 1;
                        continue;
                    }
                    if (cantOk == CANT_M) {
                        cantDna++;
                        //Si la verificacion es correcta y la bandera FINISH_IMMEDIATE este en true
                        //se finaliza el proceso validando que la persona si es mutante
                        if (FINISH_IMMEDIATE) {
                            return true;
                        }
                        //Esta matriz fue creada en caso que aun se quiera seguir validando mas cadenas de ADN
                        for (int y = 0; y < CANT_M; y++) {
                            validation[actx][nexty - y] = 1;
                        }
                        cantOk = 1;
                        continue;
                    }
                } else {
                    cantOk = 1;
                }
            }
        }
        //Revisión vertical de la matriz
        for (int i2 = 0; i2 < sizeV; i2++) {
            int cantOk = 1;
            for (int j = 0; j < sizeV; j++) {
                int acty = i2;
                int actx = j;
                int nexty = acty;
                int nextx = actx + 1;
                if (actx >= 0
                        && actx < sizeV
                        && acty >= 0
                        && acty < sizeV
                        && validation[actx][acty] == 0) {
                    String let = out[actx][acty];
                    if ((nexty >= 0) && (nexty < sizeV) && nextx >= 0 && nextx < sizeV
                            && out[nextx][nexty].equals(let)) {
                        cantOk++;
                    } else {
                        cantOk = 1;
                        continue;
                    }
                    if (cantOk == CANT_M) {
                        //Si la verificacion es correcta y la bandera FINISH_IMMEDIATE este en true
                        //se finaliza el proceso validando que la persona si es mutante
                        cantDna++;
                        if (FINISH_IMMEDIATE) {
                            return true;
                        }
                        //Esta matriz fue creada en caso que aun se quiera seguir validando mas cadenas de ADN
                        for (int y = 0; y < CANT_M; y++) {
                            validation[nextx - 1][acty] = 1;
                        }
                        cantOk = 1;
                        continue;
                    }
                } else {
                    cantOk = 1;
                }
            }
        }
        //Revisión cruzada 1
        for (int i2 = 0; i2 < sizeV; i2++) {
            int cantOk = 1;
            for (int j = 0; j < sizeV; j++) {
                for (int k = 0; k < sizeV; k++) {
                    int acty = j + k;
                    int actx = i2 + k;
                    int nexty = acty + 1;
                    int nextx = actx + 1;
                    if (actx >= 0
                            && actx < sizeV
                            && acty >= 0
                            && acty < sizeV
                            && validation[actx][acty] == 0) {
                        String let = out[actx][acty];
                        if ((nexty >= 0) && (nexty < sizeV) && nextx >= 0 && nextx < sizeV
                                && out[nextx][nexty].equals(let)) {
                            cantOk++;
                        } else {
                            cantOk = 1;
                            continue;
                        }
                        if (cantOk == CANT_M) {
                            //Si la verificacion es correcta y la bandera FINISH_IMMEDIATE este en true
                            //se finaliza el proceso validando que la persona si es mutante
                            cantDna++;
                            if (FINISH_IMMEDIATE) {
                                return true;
                            }
                            //Esta matriz fue creada en caso que aun se quiera seguir validando mas cadenas de ADN
                            for (int y = 0; y < CANT_M; y++) {
                                validation[nextx - y][nexty - y] = 1;
                            }
                            cantOk = 1;
                            continue;
                        }
                    } else {
                        cantOk = 1;
                    }
                }
            }
        }
        //Revisión cruzada2
        for (int i2 = 0; i2 < sizeV; i2++) {
            int cantOk = 1;
            for (int j = 0; j < sizeV; j++) {
                for (int k = 0; k < sizeV; k++) {
                    int acty = j - k;
                    int actx = i2 + k;
                    int nexty = acty - 1;
                    int nextx = actx + 1;
                    if (actx >= 0
                            && actx < sizeV
                            && acty >= 0
                            && acty < sizeV
                            && validation[actx][acty] == 0) {
                        String let = out[actx][acty];
                        if ((nexty >= 0) && (nexty < sizeV) && nextx >= 0 && nextx < sizeV
                                && out[nextx][nexty].equals(let)) {
                            cantOk++;
                        } else {
                            cantOk = 1;
                            continue;
                        }
                        if (cantOk == CANT_M) {
                            //Si la verificacion es correcta y la bandera FINISH_IMMEDIATE este en true
                            //se finaliza el proceso validando que la persona si es mutante
                            cantDna++;
                            if (FINISH_IMMEDIATE) {
                                return true;
                            }
                            //Esta matriz fue creada en caso que aun se quiera seguir validando mas cadenas de ADN
                            for (int y = 0; y < CANT_M; y++) {
                                validation[nextx - y][nexty + y] = 1;
                            }
                            cantOk = 1;
                            continue;
                        }
                    } else {
                        cantOk = 1;
                    }
                }


            }
        }
        return cantDna>0?true:false;
    }



    public static String toStringDna(String[] dna){
        String dnaS="";
        for (String dnaC:dna
        ) {
            dnaS=dnaS+dnaC;
        }
        return dnaS;
    }

}
