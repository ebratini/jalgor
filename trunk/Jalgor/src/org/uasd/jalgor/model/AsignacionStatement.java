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
import org.uasd.jalgor.business.InterpreterError;
import org.uasd.jalgor.business.JalgorInterpreter;
import org.uasd.jalgor.business.JalgorInterpreter.AnalizadorLexico;
import org.uasd.jalgor.business.JalgorInterpreter.AnalizadorSemantico;

/**
 *
 * @author Edwin Bratini <edwin.bratini@gmail.com>
 */
public class AsignacionStatement extends Statement {

    private Token idVariable;
    private Variable.TipoVariable tipoVariable;

    public AsignacionStatement() throws AlgorSintaxException {
    }

    public AsignacionStatement(Keyword tipoSatement, JalgorInterpreter ji) throws AlgorSintaxException {
        super(tipoSatement, ji);
        parseMe();
    }

    public AsignacionStatement(Keyword tipoSatement, JalgorInterpreter ji, VariableId idVariable, Variable.TipoVariable tipoVariable) throws AlgorSintaxException {
        super(tipoSatement, ji);
        this.idVariable = idVariable;
        this.tipoVariable = tipoVariable;
        parseMe();
    }

    private void parseMe() throws AlgorSintaxException {
        AnalizadorLexico al = getJi().getAs().getAl();
        AnalizadorSemantico asem = getJi().getAs().getAsem();

        addTokenStatement(idVariable);
        addTokenStatement(new OperadorAsignacion(Operador.TipoOperador.ASIG, "="));
        switch (tipoVariable) {
            case ALFA:
                while (al.hasNextToken()) {
                    Token tok = al.getNextToken();
                    if (tok instanceof ConstanteAlfanumerica || tok instanceof VariableId
                            || (tok instanceof OperadorAritmetico && ((OperadorAritmetico) tok).getTipoOperador().equals(Operador.TipoOperador.SUMA))
                            || (tok instanceof SignoPuntuacion && tok.getValue().equals(";"))) {
                        this.addTokenStatement(tok);
                    } else {
                        String msjError = String.format("Token invalido: [%s]", tok.getValue());
                        al.getCodeLine().addError(new InterpreterError(msjError));
                        throw new AlgorSintaxException(msjError);
                    }
                }
                if (!getTokensStatement().getLast().getValue().equals(";")) {
                    String msjError = String.format("Token invalido al final de linea: [%s]", getTokensStatement().getLast().getValue());
                    al.getCodeLine().addError(new InterpreterError(msjError));
                    throw new AlgorSintaxException(msjError);
                }
                break;
            case NUM:
                while (al.hasNextToken()) {
                    Token tok = al.getNextToken();
                    if (tok instanceof ConstanteNumerica
                            || (tok instanceof VariableId && asem.variableExiste(tok.getValue()) && asem.searchVariable(tok.getValue()).getTipoVariable().equals(tipoVariable.NUM))
                            || tok instanceof OperadorAritmetico || (tok instanceof SignoPuntuacion && tok.getValue().equals(";"))) {
                        addTokenStatement(tok);
                    } else {
                        String msjError = String.format("Token invalido: [%s]", tok.getValue());
                        if (tok instanceof VariableId) {
                            if (!asem.variableExiste(tok.getValue())) {
                                msjError = "Variable [" + tok.getValue() + "] no existe";
                            } else {
                                msjError = "Tipos de variable incompatibles. Numero requerido. Alfabetico encontrado";
                            }
                        }
                        al.getCodeLine().addError(new InterpreterError(msjError));
                        throw new AlgorSintaxException(msjError);
                    }
                }
                if (!getTokensStatement().getLast().getValue().equals(";")) {
                    String msjError = String.format("Token invalido al final de linea: [%s]", getTokensStatement().getLast().getValue());
                    al.getCodeLine().addError(new InterpreterError(msjError));
                    throw new AlgorSintaxException(msjError);
                }
                break;
        }

        setParsedValue(parse());
        if (getParsedValue().indexOf(';') != getParsedValue().lastIndexOf(';')) {
            String msjError = "Fin de linea invalido. Mas de un identificador de fin de linea encontrado.";
            al.getCodeLine().addError(new InterpreterError(msjError));
            throw new AlgorSintaxException(msjError);
        }
    }
}
