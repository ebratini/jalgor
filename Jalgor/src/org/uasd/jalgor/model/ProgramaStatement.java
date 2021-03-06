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

/**
 *
 * @author Edwin Bratini <edwin.bratini@gmail.com>
 */
public class ProgramaStatement extends BlockStatement {

    private boolean opened;

    public ProgramaStatement() throws AlgorSintaxException {
    }

    public ProgramaStatement(Keyword tipoSatement, JalgorInterpreter ji) throws AlgorSintaxException {
        super(tipoSatement, ji);
        parseMe();
    }

    public ProgramaStatement(Keyword tipoSatement, JalgorInterpreter ji, int ambito) throws AlgorSintaxException {
        super(tipoSatement, ji, ambito);
        parseMe();
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    private void parseMe() throws AlgorSintaxException {
        AnalizadorLexico al = getJi().getAs().getAl();
        Token token = al.getNextToken();
        switch (getTipoSatement()) {
            case PROGRAMA:
                if (!(token instanceof VariableId)) {
                    String msjError = "Identificador esperado";
                    al.getCodeLine().addError(new InterpreterError(msjError));
                    throw new AlgorSintaxException(msjError);
                }
                addTokenStatement(token);

                StringBuilder sbParsedValue = new StringBuilder();
                sbParsedValue.append("// ").append(getOriginalValue()).append(System.getProperty("line.separator"));
                sbParsedValue.append(cppReps.get(getTipoSatement()));
                setParsedValue(sbParsedValue.toString());
                break;
            case FIN_PROGRAMA:
                if (token != null) {
                    String msjError = "Token Invalido: [" + token.getValue() + "]";
                    al.getCodeLine().addError(new InterpreterError(msjError));
                    throw new AlgorSintaxException(msjError);
                }
                setParsedValue(parse());
                break;
        }
    }
}
