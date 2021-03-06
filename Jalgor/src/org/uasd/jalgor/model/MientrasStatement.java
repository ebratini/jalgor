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
public class MientrasStatement extends BlockStatement {

    private boolean opened;
    private boolean closed;

    public MientrasStatement() throws AlgorSintaxException {
    }

    public MientrasStatement(Keyword tipoSatement, JalgorInterpreter ji) throws AlgorSintaxException {
        super(tipoSatement, ji);
        parseMe();
    }

    public MientrasStatement(Keyword tipoSatement, JalgorInterpreter ji, int ambito) throws AlgorSintaxException {
        super(tipoSatement, ji, ambito);
        parseMe();
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    private void parseMe() throws AlgorSintaxException {
        AnalizadorLexico al = getJi().getAs().getAl();
        AnalizadorSemantico asem = getJi().getAs().getAsem();

        Token token = al.getNextToken();
        switch (getTipoSatement()) {
            case MIENTRAS:
                if (!(token instanceof VariableId) && !(token instanceof ConstanteAlfanumerica) && !(token instanceof ConstanteNumerica)
                        && !(token instanceof SignoPuntuacion && token.getValue().equals("("))) {
                    String msjError = "(Identificador|Constante (alfa)numerica) esperado";
                    al.getCodeLine().addError(new InterpreterError(msjError));
                    throw new AlgorSintaxException(msjError);
                }
                if (token instanceof VariableId && !asem.variableExiste(token.getValue())) {
                    String msjError = "Variable " + token.getValue() + " no ha sido declarada";
                    al.getCodeLine().addError(new InterpreterError(msjError));
                    throw new AlgorSintaxException(msjError);
                }

                if (!token.getValue().equals("(")) {
                    addTokenStatement(new SignoPuntuacion("("));
                }

                addTokenStatement(token);

                while (al.hasNextToken()) {
                    Token tok = al.getNextToken();
                    if (tok instanceof KeywordToken) {
                        String msjError = "Token invalido: [" + tok.getValue() + "]";
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

                if (!getTokensStatement().getLast().getValue().equals(")")) {
                    addTokenStatement(new SignoPuntuacion(")"));
                }
                addTokenStatement(new SignoPuntuacion("{"));
                setParsedValue(parse());
                break;
            case FIN_MIENTRAS:
                if (token != null) {
                    String msjError = "Token invalido [" + token.getValue() + "]";
                    al.getCodeLine().addError(new InterpreterError(msjError));
                    throw new AlgorSintaxException(msjError);
                }
                setParsedValue(parse());
                break;
        }
    }
}
