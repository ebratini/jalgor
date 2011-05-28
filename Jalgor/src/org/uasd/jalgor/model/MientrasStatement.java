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

import java.util.ArrayList;
import java.util.List;
import org.uasd.jalgor.business.AlgorSintaxException;
import org.uasd.jalgor.business.AnalizadorLexico;
import org.uasd.jalgor.business.InterpreterError;
import org.uasd.jalgor.business.JalgorInterpreter;

/**
 *
 * @author Edwin Bratini <edwin.bratini@gmail.com>
 */
public class MientrasStatement extends Statement {

    private int ambitoSeqId = -1;
    private List<Statement> blockStatements = new ArrayList<Statement>();

    public MientrasStatement(Keyword tipoSatement, AnalizadorLexico al) throws AlgorSintaxException {
        super(tipoSatement, al);
        parseMe();
    }

    public MientrasStatement(Keyword tipoSatement, AnalizadorLexico al, int ambito) throws AlgorSintaxException {
        super(tipoSatement, al);
        this.ambitoSeqId = ambito;
        parseMe();
    }

    public MientrasStatement() throws AlgorSintaxException {
    }

    public List<Statement> getBlockStatements() {
        return blockStatements;
    }

    public void setBlockStatements(List<Statement> blockStatements) {
        this.blockStatements = blockStatements;
    }

    public void addBlockStatement(Statement statement) {
        this.blockStatements.add(statement);
    }

    public int getAmbitoSeqId() {
        return ambitoSeqId;
    }

    private void parseMe() throws AlgorSintaxException {
        Token token = getAl().getNextToken();
        switch (getTipoSatement()) {
            case MIENTRAS:
                if (!(token instanceof VariableId) && !(token instanceof ConstanteAlfanumerica) && !(token instanceof ConstanteNumerica)) {
                    String msjError = "[Identificador|Constante (alfa)numerica] esperado";
                    getAl().getCodeLine().addError(new InterpreterError(msjError));
                    throw new AlgorSintaxException(msjError);
                }
                if (token instanceof VariableId && !JalgorInterpreter.getVariables().containsKey(token.getValue())) {
                    String msjError = "Variable " + token.getValue() + " no ha sido declarada";
                    getAl().getCodeLine().addError(new InterpreterError(msjError));
                    throw new AlgorSintaxException(msjError);
                }

                addTokenStatement(token);

                while (getAl().hasNextToken()) {
                    Token tok = getAl().getNextToken();
                    if (token instanceof KeywordToken) {
                        String msjError = "Token invalido: " + tok.getValue();
                        getAl().getCodeLine().addError(new InterpreterError(msjError));
                        throw new AlgorSintaxException(msjError);
                    }
                    if (tok instanceof VariableId && !JalgorInterpreter.getVariables().containsKey(tok.getValue())) {
                        String msjError = "Variable " + tok.getValue() + " no ha sido declarada";
                        getAl().getCodeLine().addError(new InterpreterError(msjError));
                        throw new AlgorSintaxException(msjError);
                    }
                    addTokenStatement(tok);
                }

                // TODO: el parse para estos dos tipos de statement es diferente
                setParsedValue(parse());
                break;
            case FIN_MIENTRAS:
                if (token != null) {
                    String msjError = "Token invalido " + token.getValue();
                    getAl().getCodeLine().addError(new InterpreterError(msjError));
                    throw new AlgorSintaxException(msjError);
                }

                // TODO: el parse para estos dos tipos de statement es diferente
                setParsedValue(parse());
                break;
        }
    }
}
