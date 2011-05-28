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
import org.uasd.jalgor.model.CodeLine;
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
    private static StringBuilder sbCodeLines;
    private static List<CodeLine> codeLines = new ArrayList<CodeLine>();
    private static List<Statement> statements = new ArrayList<Statement>();
    private static HashMap<String, Variable> variables = new HashMap<String, Variable>();
    private static List<InterpreterError> errores = new ArrayList<InterpreterError>();
    private AnalizadorSintactico as = new AnalizadorSintactico(new AnalizadorLexico());

    private static int ambitoStatementSeq;

    public JalgorInterpreter() {
    }

    public JalgorInterpreter(String sourceFilePath, String outFilePath) throws InvalidFileNameException {
        validateSourceFileName(sourceFilePath);
        validateOutFileName(outFilePath);
        this.sourceFilePath = sourceFilePath;
        this.outFilePath = outFilePath;
        JalgorInterpreter.sbCodeLines = FileManager.loadFile(new File(sourceFilePath));
        initCodeLines();
    }

    public String getOutFilePath() {
        return outFilePath;
    }

    public String getSourceFilePath() {
        return sourceFilePath;
    }

    public static StringBuilder getSbCodeLines() {
        return sbCodeLines;
    }

    public static List<Statement> getStatements() {
        return statements;
    }

    public static HashMap<String, Variable> getVariables() {
        return variables;
    }

    public static List<InterpreterError> getErrores() {
        return errores;
    }

    public static List<CodeLine> getCodeLines() {
        return codeLines;
    }

    public static void setCodeLines(List<CodeLine> codeLines) {
        JalgorInterpreter.codeLines = codeLines;
    }
    
    private void initCodeLines() {
        String[] lines = sbCodeLines.toString().split(System.getProperty("line.separator"));
        int i = 1;
        for (String line : lines) {
            if (line.length() > 0 && !line.equals("")) {
                codeLines.add(new CodeLine(i++, line));
            }
        }
    }

    private void validateSourceFileName(String sourceFname) throws InvalidFileNameException {
        if (!sourceFname.toLowerCase().endsWith(".algor")) {
            throw new InvalidSourceFileNameException("El nombre del archivo fuente esta mal formado");
        }
    }

    private void validateOutFileName(String outFName) throws InvalidFileNameException {
        if (!outFName.toLowerCase().endsWith(".cpp")) {
            throw new InvalidOutFileNameException("El nombre del archivo salida esta mal formado");
        }
    }

    private void printErrores() {
        for (InterpreterError ie : errores) {
            System.out.println(ie.getMensaje());
        }
    }

    private boolean hayErrorEnLineaCodigo() {
        boolean hayError = false;
        for (CodeLine cl : codeLines) {
            if (cl.getErrores().size() > 0) {
                hayError = true;
            }
        }

        return hayError;
    }

    private void printCodeLineErrors() {
        for (CodeLine cl : codeLines) {
            for (InterpreterError ie : cl.getErrores()) {
                System.out.format("%d %s", cl.getLineNumber(), ie.getMensaje());
            }
        }
    }

    public static int getNextAmbitoStmSeq() {
        return ambitoStatementSeq++;
    }

    public void start() {
        if (sbCodeLines.length() < 1) {
            errores.add(new EmptyFileError("El archivo fuente esta vacio"));
            printErrores();
            return;
        }

        initCodeLines();
        as.go();

        if (!getStatements().contains(null) && !hayErrorEnLineaCodigo()) {
            // imprime archivo
            StringBuilder fileContent = new StringBuilder();
            for (Statement stm : statements) {
                fileContent.append(stm.toString()).append(System.getProperty("line.separator"));
            }
            FileManager.writeToFile(fileContent, new File(outFilePath), false);
        } else {
            // imprime errores
            if (errores.size() > 0) {
                printErrores();
            }
            printCodeLineErrors();
        }
    }
}
