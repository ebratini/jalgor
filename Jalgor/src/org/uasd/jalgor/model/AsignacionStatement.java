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
import org.uasd.jalgor.business.JalgorInterpreter;

/**
 *
 * @author Edwin Bratini <edwin.bratini@gmail.com>
 */
public class AsignacionStatement extends Statement {

    private Token idVariable;
    private Variable.TipoVariable tipoVariable;

    public AsignacionStatement(Keyword tipoSatement, AnalizadorLexico al, VariableId idVariable, Variable.TipoVariable tipoVariable)
            throws AlgorSintaxException {
        super(tipoSatement, al);
        this.idVariable = idVariable;
        this.tipoVariable = tipoVariable;
        parseMe();
    }

    public AsignacionStatement() throws AlgorSintaxException {
    }

    // TODO: think abt this
    private void parseMe() throws AlgorSintaxException {
        this.addTokenStatement(idVariable);
        Token token = getAl().getNextToken();
        if (!(token instanceof OperadorAsignacion)) {
            String msjError = "Token invalido: " + token.getValue() + "; [=] esperado";
            getAl().getCodeLine().addError(new InterpreterError(msjError));
            throw new AlgorSintaxException(msjError);
        }
        this.addTokenStatement(token);
        switch (tipoVariable) {
            case ALFA:
                while (getAl().hasNextToken()) {
                    Token tok = getAl().getNextToken();
                    if (tok instanceof ConstanteAlfanumerica || tok instanceof VariableId
                            || (tok instanceof OperadorAritmetico && ((OperadorAritmetico) tok).getTipoOperador().equals(Operador.TipoOperador.SUMA))
                            || (tok instanceof SignoPuntuacion && tok.getValue().equals(";"))) {
                        this.addTokenStatement(tok);
                    } else {
                        String msjError = "Token invalido: " + tok.getValue();
                        getAl().getCodeLine().addError(new InterpreterError(msjError));
                        throw new AlgorSintaxException(msjError);
                    }
                }
                if (!getTokensStatement().get(getTokensStatement().size() - 1).getValue().equals(";")) {
                    String msjError = "Token invalido al final de linea: " + getTokensStatement().get(getTokensStatement().size() - 1).getValue();
                    getAl().getCodeLine().addError(new InterpreterError(msjError));
                    throw new AlgorSintaxException(msjError);
                }
                break;
            case NUM:
                while (getAl().hasNextToken()) {
                    Token tok = getAl().getNextToken();
                    if (tok instanceof ConstanteNumerica || (tok instanceof VariableId && JalgorInterpreter.getVariables().containsKey(tok.getValue()))
                            || tok instanceof OperadorAritmetico || (tok instanceof SignoPuntuacion && tok.getValue().equals(";"))) {
                        this.addTokenStatement(tok);
                    } else {
                        String msjError = "Token invalido: " + tok.getValue();
                        getAl().getCodeLine().addError(new InterpreterError(msjError));
                        throw new AlgorSintaxException(msjError);
                    }
                }
                if (!getTokensStatement().get(getTokensStatement().size() - 1).getValue().equals(";")) {
                    String msjError = "Token invalido al final de linea: " + getTokensStatement().get(getTokensStatement().size() - 1).getValue();
                    getAl().getCodeLine().addError(new InterpreterError(msjError));
                    throw new AlgorSintaxException(msjError);
                }
                break;
        }

        setParsedValue(parse());
    }

    @Override
    public String toString() {
        return getParsedValue();
    }
}
