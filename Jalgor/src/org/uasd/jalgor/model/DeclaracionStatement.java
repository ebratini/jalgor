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
import org.uasd.jalgor.business.InterpreterError;

/**
 *
 * @author Edwin Bratini <edwin.bratini@gmail.com>
 */
public class DeclaracionStatement extends Statement {

    public DeclaracionStatement() throws AlgorSintaxException {
    }

    public DeclaracionStatement(Keyword tipoSatement, AnalizadorLexico al) throws AlgorSintaxException {
        super(tipoSatement, al);
        setOriginalValue(getAl().getCodeLine().getOrigValue());

    }

    private void parseMe() throws AlgorSintaxException {
        Token token = getAl().getNextToken();
        if (!(token instanceof VariableId)) {
            String msjError = "Identificador esperado";
            getAl().getCodeLine().addError(new InterpreterError(msjError));
            throw new AlgorSintaxException(msjError);
        }
        Token nxtToken = token.getSiblingToken();
        if (!(nxtToken instanceof OperadorAsignacion) && !(nxtToken instanceof SignoPuntuacion)
                || (nxtToken instanceof SignoPuntuacion
                && (((SignoPuntuacion) nxtToken).getValue().equals(",") || ((SignoPuntuacion) nxtToken).getValue().equals(";")))) {

            String msjError = "Token invalido" + nxtToken.getValue();
            getAl().getCodeLine().addError(new InterpreterError(msjError));
            throw new AlgorSintaxException(msjError);
        }
        switch (getTipoSatement()) {
            case ALFA:

                break;
            case NUM:
                break;
        }
    }
}
