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
public class EscribeStatement extends Statement {

    public EscribeStatement() throws AlgorSintaxException {
        super(Keyword.ESCRIBE);
    }

    public EscribeStatement(Keyword tipoSatement, JalgorInterpreter ji) throws AlgorSintaxException {
        super(tipoSatement, ji);
        parseMe();
    }

    private void parseMe() throws AlgorSintaxException {
        AnalizadorLexico al = getJi().getAs().getAl();
        AnalizadorSemantico asem = getJi().getAs().getAsem();
        
        Token token = al.getNextToken();
        if (!(token instanceof VariableId) && !(token instanceof ConstanteAlfanumerica) && !(token instanceof ConstanteNumerica)) {
            String msjError = "(Identificador|Constante (alfa)numerica) esperado";
            al.getCodeLine().addError(new InterpreterError(msjError));
            throw new AlgorSintaxException(msjError);
        }
        if (token instanceof VariableId && !asem.variableExiste(token.getValue())) {
            String msjError = "Variable [" + token.getValue() + "] no ha sido declarada";
            al.getCodeLine().addError(new InterpreterError(msjError));
            throw new AlgorSintaxException(msjError);
        }

        addTokenStatement(token);

        while (al.hasNextToken()) {
            Token tok = al.getNextToken();
            if (token instanceof KeywordToken) {
                String msjError = String.format("Token invalido: [%s]", tok.getValue());
                al.getCodeLine().addError(new InterpreterError(msjError));
                throw new AlgorSintaxException(msjError);
            }
            if (tok instanceof VariableId && !asem.variableExiste(tok.getValue())) {
                String msjError = "Variable [" + tok.getValue() + "] no ha sido declarada";
                al.getCodeLine().addError(new InterpreterError(msjError));
                throw new AlgorSintaxException(msjError);
            }
            addTokenStatement(tok);
        }
        if (!getTokensStatement().getLast().getValue().equals(";")) {
            String msjError = String.format("Token invalido al final de linea: %s. (;) esperado", getTokensStatement().getLast().getValue());
            al.getCodeLine().addError(new InterpreterError(msjError));
            throw new AlgorSintaxException(msjError);
        }

        setParsedValue(parse());
        if (getParsedValue().indexOf(';') != getParsedValue().lastIndexOf(';')) {
            String msjError = "Fin de linea invalido. Mas de un identificador de fin de linea encontrado.";
            al.getCodeLine().addError(new InterpreterError(msjError));
            throw new AlgorSintaxException(msjError);
        }
    }
}
