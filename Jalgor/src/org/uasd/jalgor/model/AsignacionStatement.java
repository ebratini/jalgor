/*
 *  The MIT License
 * 
 *  Copyright 2011 Edwin Bratini <edwin.bratini@gmail.com>.
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 * 
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 * 
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package org.uasd.jalgor.model;

import org.uasd.jalgor.business.AlgorSintaxException;
import org.uasd.jalgor.business.AnalizadorLexico;

/**
 *
 * @author Edwin Bratini <edwin.bratini@gmail.com>
 */
public class AsignacionStatement extends Statement {

    private Token idVariable;
    private Variable.TipoVariable tipoVariable;

    public AsignacionStatement(Keyword tipoSatement, AnalizadorLexico al, VariableId idVariable, Variable.TipoVariable tipoVariable) throws AlgorSintaxException {
        super(tipoSatement, al);
        this.idVariable = idVariable;
        this.tipoVariable = tipoVariable;
        setOriginalValue(String.valueOf(al.getCodeLine()));
        parseMe();
    }

    public AsignacionStatement() {
    }

    // TODO: think abt this
    private void parseMe() throws AlgorSintaxException {
        this.addTokenStatement(idVariable);
        this.addTokenStatement(getAl().getNextToken());
        switch (tipoVariable) {
            case ALFA:
                while (getAl().hasNextToken()) {
                    Token tok = getAl().getNextToken();
                    if (tok instanceof ConstanteAlfanumerica || tok instanceof ExpresionCadena || (tok instanceof SignoPuntuacion && tok.getValue().equals(";"))) {
                        if (tok instanceof ConstanteAlfanumerica
                                && !(tok.getSiblingToken() instanceof SignoPuntuacion && tok.getSiblingToken().getValue().equals(";"))) {
                            throw new AlgorSintaxException("Token invalido: " + tok.getValue());
                        } else if (tok instanceof ExpresionCadena
                                && !(tok.getSiblingToken() instanceof SignoPuntuacion && tok.getSiblingToken().getValue().equals(";"))) {
                            throw new AlgorSintaxException("Token invalido: " + tok.getValue());
                        }
                        this.addTokenStatement(tok);
                        if (tok instanceof SignoPuntuacion && tok.getValue().equals(";")) {
                            break;
                        }
                    } else {
                        throw new AlgorSintaxException("Token invalido: " + tok.getValue());
                    }
                }
                break;
            case NUM:
                while (getAl().hasNextToken()) {
                    Token tok = getAl().getNextToken();
                    if (tok instanceof ConstanteNumerica || tok instanceof ExpresionNumerica || (tok instanceof SignoPuntuacion && tok.getValue().equals(";"))) {
                        if (tok instanceof ConstanteNumerica
                                && !(tok.getSiblingToken() instanceof SignoPuntuacion && tok.getSiblingToken().getValue().equals(";"))) {
                            throw new AlgorSintaxException("Token invalido: " + tok.getValue());
                        } else if (tok instanceof ExpresionNumerica
                                && !(tok.getSiblingToken() instanceof SignoPuntuacion && tok.getSiblingToken().getValue().equals(";"))) {
                            throw new AlgorSintaxException("Token invalido: " + tok.getValue());
                        }
                        this.addTokenStatement(tok);
                        if (tok instanceof SignoPuntuacion && tok.getValue().equals(";")) {
                            break;
                        }
                    } else {
                        throw new AlgorSintaxException("Token invalido: " + tok.getValue());
                    }
                }
                break;
        }
    }

    @Override
    public String toString() {
        StringBuilder parsedValue = new StringBuilder();

        for (Token tok : this.getTokensStatement()) {
            parsedValue.append(tok.toString()).append(" ");
        }
        return parsedValue.toString();
    }
}
