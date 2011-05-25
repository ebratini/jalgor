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
import org.uasd.jalgor.business.InterpreterError;

/**
 *
 * @author Edwin Bratini <edwin.bratini@gmail.com>
 */
public class CodeLine {

    private int lineNumber;
    private String origValue;
    private String parsedValue;
    private List<InterpreterError> errores = new ArrayList<InterpreterError>();

    public CodeLine() {
    }

    public CodeLine(int lineNumber, String origValue) {
        this.lineNumber = lineNumber;
        this.origValue = origValue;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getOrigValue() {
        return origValue;
    }

    public void setOrigValue(String origValue) {
        this.origValue = origValue;
    }

    public String getParsedValue() {
        return parsedValue;
    }

    public void setParsedValue(String parsedValue) {
        this.parsedValue = parsedValue;
    }

    public List<InterpreterError> getErrores() {
        return errores;
    }

    public void setErrores(List<InterpreterError> errores) {
        this.errores = errores;
    }

    public void addError(InterpreterError error) {
        this.errores.add(error);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CodeLine other = (CodeLine) obj;
        if (this.lineNumber != other.lineNumber) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + this.lineNumber;
        return hash;
    }
}
