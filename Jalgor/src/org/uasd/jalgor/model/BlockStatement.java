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

import java.util.LinkedList;
import org.uasd.jalgor.business.JalgorInterpreter;

/**
 *
 * @author Edwin Bratini <edwin.bratini@gmail.com>
 */
public class BlockStatement extends Statement {

    private int ambitoSeqId = -1;
    private LinkedList<Statement> blockStatements = new LinkedList<Statement>();

    public BlockStatement() {
    }

    public BlockStatement(Keyword tipoSatement, JalgorInterpreter jalgorInterpreter) {
        super(tipoSatement, jalgorInterpreter);
    }

    public BlockStatement(Keyword tipoSatement, JalgorInterpreter jalgorInterpreter, int ambito) {
        this(tipoSatement, jalgorInterpreter);
        this.ambitoSeqId = ambito;
    }

    public LinkedList<Statement> getBlockStatements() {
        return blockStatements;
    }

    public void setBlockStatements(LinkedList<Statement> blockStatements) {
        this.blockStatements = blockStatements;
    }

    public void addBlockStatement(Statement statement) {
        this.blockStatements.offer(statement);
    }

    public int getAmbitoSeqId() {
        return ambitoSeqId;
    }
}
