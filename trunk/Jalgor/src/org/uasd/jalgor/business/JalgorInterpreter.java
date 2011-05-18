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
package org.uasd.jalgor.business;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.uasd.jalgor.model.FileManager;
import org.uasd.jalgor.model.Statement;
import org.uasd.jalgor.model.Variable;

/**
 *
 * @author Edwin Bratini <edwin.bratini@gmail.com>
 */
public class JalgorInterpreter {

    private String sourceFilePath;
    private String outFilePath;
    private StringBuilder sbCodeLines = FileManager.loadFile(new File(sourceFilePath));
    
    private HashMap<Integer, String> codeLines = new HashMap<Integer, String>();
    private List<Statement> statements = new ArrayList<Statement>();
    private List<Variable> variables = new ArrayList<Variable>();
    private List<InterpreterError> errores = new ArrayList<InterpreterError>();

    public JalgorInterpreter() {
    }

    public JalgorInterpreter(String sourceFilePath, String outFilePath) throws InvalidFileNameException {
        validateSourceFileName(sourceFilePath);
        validateOutFileName(outFilePath);
        this.sourceFilePath = sourceFilePath;
        this.outFilePath = outFilePath;
        initCodeLines();
    }

    public String getOutFilePath() {
        return outFilePath;
    }

    public String getSourceFilePath() {
        return sourceFilePath;
    }

    public HashMap<Integer, String> getCodeLines() {
        return codeLines;
    }

    public StringBuilder getSbCodeLines() {
        return sbCodeLines;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public List<Variable> getVariables() {
        return variables;
    }
    
    private void initCodeLines() {
        String[] lines = sbCodeLines.toString().split(System.getProperty("line.separator"));
        int i = 0;
        for (String line : lines) {
            codeLines.put(i++, line);
        }
    }

    private void validateSourceFileName(String sourceFname) throws InvalidFileNameException {
        if (!sourceFname.endsWith(".algor")) {
            throw new InvalidSourceFileNameException("El nombre del archivo fuente esta mal formado");
        }
    }

    private void validateOutFileName(String outFName) throws InvalidFileNameException {
        if (!outFName.endsWith(".cpp")) {
            throw new InvalidOutFileNameException("El nombre del archivo salida esta mal formado");
        }
    }

    public void start() {
        if (sbCodeLines.length() < 1) {
            errores.add(new EmptyFileError("El archivo fuente esta vacio"));
            return;
        }
        // TODO: pending implementation
    }
}
