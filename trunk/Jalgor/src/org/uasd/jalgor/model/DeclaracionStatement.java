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

import java.util.List;
import org.uasd.jalgor.business.AlgorSintaxException;
import org.uasd.jalgor.business.InterpreterError;
import org.uasd.jalgor.business.JalgorInterpreter;
import org.uasd.jalgor.business.JalgorInterpreter.AnalizadorLexico;
import org.uasd.jalgor.business.JalgorInterpreter.AnalizadorSemantico;

/**
 *
 * @author Edwin Bratini <edwin.bratini@gmail.com>
 */
public class DeclaracionStatement extends Statement {

    public DeclaracionStatement() throws AlgorSintaxException {
    }

    public DeclaracionStatement(Keyword tipoSatement, AnalizadorLexico al, List<Variable> variables) throws AlgorSintaxException {
        super(tipoSatement, al);
        parseMe();
    }

    public DeclaracionStatement(Keyword tipoSatement, JalgorInterpreter ji) throws AlgorSintaxException {
        super(tipoSatement, ji);
        parseMe();
    }

    private void parseMe() throws AlgorSintaxException {
        JalgorInterpreter ji = getJi();
        AnalizadorLexico al = ji.getAs().getAl();
        AnalizadorSemantico asem = ji.getAs().getAsem();

        Token token = al.getNextToken();//getAl().getNextToken();
        Token nxtToken = al.getNextToken();//token.getSiblingToken();
        if (!(token instanceof VariableId)) {
            String msjError = "Identificador esperado";
            al.getCodeLine().addError(new InterpreterError(msjError));
            throw new AlgorSintaxException(msjError);
        }
        if ((!(nxtToken instanceof OperadorAsignacion) && !(nxtToken instanceof SignoPuntuacion))
                || (nxtToken instanceof SignoPuntuacion
                && (!((SignoPuntuacion) nxtToken).getValue().equals(",") && !((SignoPuntuacion) nxtToken).getValue().equals(";")))) {

            String msjError = "Token invalido " + nxtToken.getValue() + "; [;|,|=] esperado";
            al.getCodeLine().addError(new InterpreterError(msjError));
            throw new AlgorSintaxException(msjError);
        }
        if (asem.variableExiste(token.getValue())) {
            String msjError = "Variable " + token.getValue() + " ya ha sido declarada";
            al.getCodeLine().addError(new InterpreterError(msjError));
            throw new AlgorSintaxException(msjError);
        }

        addTokenStatement(token);
        addTokenStatement(nxtToken);
        ji.addVariable(new Variable(Variable.TipoVariable.valueOf(getTipoSatement().toString()), token.getValue()));

        if (!(nxtToken instanceof OperadorAsignacion)) {
            while (al.hasNextToken()) {
                Token tok = al.getNextToken();
                if (tok instanceof VariableId) {
                    if (asem.variableExiste(tok.getValue())) {
                        String msjError = "Variable " + tok.getValue() + " ya ha sido declarada";
                        al.getCodeLine().addError(new InterpreterError(msjError));
                        throw new AlgorSintaxException(msjError);
                    }
                    ji.addVariable(new Variable(Variable.TipoVariable.valueOf(getTipoSatement().toString()), token.getValue()));
                }
                addTokenStatement(tok);
            }
            if (!getTokensStatement().getLast().getValue().equals(";")) {
                String msjError = "Token invalido al final de linea: " + getTokensStatement().get(getTokensStatement().size() - 1).getValue();
                msjError += "; [;] esperado";
                al.getCodeLine().addError(new InterpreterError(msjError));
                throw new AlgorSintaxException(msjError);
            }
            setParsedValue(parse());
        } else if (nxtToken instanceof OperadorAsignacion) {
            Statement asigStatement = new AsignacionStatement(getTipoSatement(), al, (VariableId) token,
                    Variable.TipoVariable.valueOf(getTipoSatement().toString()));

            setParsedValue(parse() + " " + asigStatement.getParsedValue());
        }
    }
}
