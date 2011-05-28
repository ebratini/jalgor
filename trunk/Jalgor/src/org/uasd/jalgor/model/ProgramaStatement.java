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
public class ProgramaStatement extends Statement {

    private int ambitoSeqId = -1;
    private List<Statement> blockStatements = new ArrayList<Statement>();

    public ProgramaStatement() throws AlgorSintaxException {
    }

    public ProgramaStatement(Keyword tipoSatement) {
        super(tipoSatement);
    }

    public ProgramaStatement(Keyword tipoSatement, AnalizadorLexico al) throws AlgorSintaxException {
        super(tipoSatement, al);
        parseMe();
    }

    public ProgramaStatement(Keyword tipoSatement, AnalizadorLexico al, int ambito) throws AlgorSintaxException {
        super(tipoSatement, al);
        this.ambitoSeqId = ambito;
        parseMe();
    }

    private void parseMe() throws AlgorSintaxException {
        Token token = getAl().getNextToken();
        switch (getTipoSatement()) {
            case PROGRAMA:
                if (!(token instanceof VariableId)) {
                    String msjError = "Identificador esperado";
                    getAl().getCodeLine().addError(new InterpreterError(msjError));
                    throw new AlgorSintaxException(msjError);
                }
                if (!(token.getSiblingToken() instanceof SignoPuntuacion)
                        || (token.getSiblingToken() instanceof SignoPuntuacion && !((SignoPuntuacion) token.getSiblingToken()).getValue().equals(";"))) {
                    String msjError = "; esperado";
                    getAl().getCodeLine().addError(new InterpreterError(msjError));
                    throw new AlgorSintaxException(msjError);
                }
                addTokenStatement(token);
                addTokenStatement(token.getSiblingToken());
                break;
            case FIN_PROGRAMA:
                if (!(token instanceof SignoPuntuacion)
                        || (token instanceof SignoPuntuacion && !((SignoPuntuacion) token).getValue().equals(";"))) {
                    String msjError = "; esperado";
                    getAl().getCodeLine().addError(new InterpreterError(msjError));
                    throw new AlgorSintaxException(msjError);
                }
                addTokenStatement(token);
                break;
        }
        setParsedValue(parse());
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
}
