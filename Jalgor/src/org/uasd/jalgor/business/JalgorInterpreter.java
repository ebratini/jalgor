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
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.uasd.jalgor.model.*;
import org.uasd.jalgor.model.Variable.TipoVariable;

/**
 *
 * @author Edwin Bratini <edwin.bratini@gmail.com>
 */
public class JalgorInterpreter {

    private String sourceFilePath;
    private String outFilePath;
    private StringBuilder sbCodeLines;
    private List<CodeLine> codeLines = new ArrayList<CodeLine>();
    private List<Statement> statements = new ArrayList<Statement>();
    private List<Variable> variables = new ArrayList<Variable>();
    private List<InterpreterError> errores = new ArrayList<InterpreterError>();
    private AnalizadorSintactico as = new AnalizadorSintactico();

    public JalgorInterpreter() {
    }

    public JalgorInterpreter(String sourceFilePath, String outFilePath) throws InvalidFileNameException {
        validateSourceFileName(sourceFilePath);
        validateOutFileName(outFilePath);
        this.sourceFilePath = sourceFilePath;
        this.outFilePath = outFilePath;
        this.sbCodeLines = FileManager.loadFile(new File(sourceFilePath));
    }

    public String getOutFilePath() {
        return outFilePath;
    }

    public String getSourceFilePath() {
        return sourceFilePath;
    }

    public AnalizadorSintactico getAs() {
        return as;
    }

    public void setAs(AnalizadorSintactico as) {
        this.as = as;
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

    public void addVariable(Variable var) {
        this.variables.add(var);
    }

    public List<InterpreterError> getErrores() {
        return errores;
    }

    public List<CodeLine> getCodeLines() {
        return codeLines;
    }

    public void setCodeLines(List<CodeLine> codeLines) {
        this.codeLines = codeLines;
    }

    private void initCodeLines() {
        String[] lines = sbCodeLines.toString().split(System.getProperty("line.separator"));
        int i = 1;
        for (String line : lines) {
            codeLines.add(new CodeLine(i++, line));
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

    public boolean hayErrorEnLineaCodigo() {
        boolean hayError = false;
        for (CodeLine cl : codeLines) {
            if (cl.getErrores().size() > 0) {
                hayError = true;
                break;
            }
        }
        return hayError;
    }

    public void printCodeLineErrors() {
        System.out.println(getCodeLineErrors().toString());
    }

    public StringBuilder getCodeLineErrors() {
        StringBuilder sbCodeLneErrors = new StringBuilder();
        for (CodeLine cl : codeLines) {
            for (InterpreterError ie : cl.getErrores()) {
                sbCodeLneErrors.append(String.format("Linea: %-3d %s\n", cl.getLineNumber(), ie.getMensaje()));
            }
        }
        return sbCodeLneErrors;
    }

    private StringBuilder getDefaultHeader() {
        StringBuilder sbDefaultParsed = new StringBuilder();

        // lo constante
        sbDefaultParsed.append("#include <iostream>").append(System.getProperty("line.separator"));
        sbDefaultParsed.append("using namespace std;").append(System.getProperty("line.separator"));
        sbDefaultParsed.append(System.getProperty("line.separator"));

        return sbDefaultParsed;
    }

    private StringBuilder getParsedStm(Statement stm) {
        StringBuilder sbParsedStatement = new StringBuilder();
        if (stm instanceof BlockStatement) {
            sbParsedStatement.append(stm.getParsedValue()).append(System.getProperty("line.separator"));
            for (Statement innerStm : ((BlockStatement) stm).getBlockStatements()) {
                sbParsedStatement.append(getParsedStm(innerStm));
            }
        } else {
            sbParsedStatement.append(stm.getParsedValue()).append(System.getProperty("line.separator"));
        }
        return sbParsedStatement;
    }

    public StringBuilder getParsedStatements() {
        StringBuilder sbParsedStatements = getDefaultHeader();
        for (Statement stm : statements) {
            sbParsedStatements.append(getParsedStm(stm));
        }
        return sbParsedStatements;
    }

    public void start() {
        if (sbCodeLines.length() < 1) {
            errores.add(new EmptyFileError("El archivo fuente esta vacio"));
            printErrores();
            return;
        }
        initCodeLines();
        statements.addAll(as.collectStatements());
        if (!statements.contains(null) && !hayErrorEnLineaCodigo()) {
            // imprime archivo
            StringBuilder fileContent = getParsedStatements();
            FileManager.writeToFile(fileContent, new File(outFilePath), false);
            System.out.println("Success!");
        } else {
            // imprime errores
            if (errores.size() > 0) {
                printErrores();
            }
            printCodeLineErrors();
        }
    }

    // Analizador Sintactico
    public class AnalizadorSintactico {

        // TODO: crear metodo que construye expresiones y arbol binario
        private AnalizadorLexico al = new AnalizadorLexico();
        private AnalizadorSemantico asem = new AnalizadorSemantico();
        private int currLinePos = 0;
        private int ambitoStatementSeq;
        private LinkedList<Integer> ambitoStatements = new LinkedList<Integer>();
        private boolean isPrgStmSet = false;
        private boolean isFinPrgStmSet = false;
        private boolean ifOpened = false;
        private boolean ifClosed = false;

        public AnalizadorSintactico() {
        }

        public AnalizadorSintactico(AnalizadorLexico al) {
            this.al = al;
        }

        public void setAl(AnalizadorLexico al) {
            this.al = al;
        }

        public AnalizadorLexico getAl() {
            return al;
        }

        public AnalizadorSemantico getAsem() {
            return asem;
        }

        public void setAsem(AnalizadorSemantico asem) {
            this.asem = asem;
        }

        public int getCurrLinePos() {
            return currLinePos;
        }

        public List<Statement> collectStatements() {
            List<Statement> statements = new ArrayList<Statement>();
            while (hasNextCodeLine()) {
                statements.add(analizeCodeLine());
            }
            return statements;
        }

        private boolean hasNextCodeLine() {
            int prevPos = currLinePos;
            boolean hasIt = (getNextCodeLine() != null);
            currLinePos = prevPos;
            return hasIt;
        }

        private CodeLine getNextCodeLine() {
            CodeLine codeLine = null;
            if (currLinePos < codeLines.size()) {
                codeLine = codeLines.get(currLinePos);
                while (codeLine.getOrigValue().trim().isEmpty()) {
                    currLinePos++;
                    codeLine = getNextCodeLine();
                }
            }
            //currLinePos++;
            return codeLine;
        }

        private int getNextAmbitoStmSeq() {
            return ambitoStatementSeq++;
        }

        public LinkedList<Integer> getAmbitoStatements() {
            return ambitoStatements;
        }

        // metodo para hallar la sentencia programa
        private ProgramaStatement searchProgramaStatement(List<Statement> statements) {
            ProgramaStatement ps = null;
            for (Statement stm : statements) {
                if (stm.getTipoSatement().equals(Statement.Keyword.PROGRAMA)) {
                    ps = (ProgramaStatement) stm;
                    break;
                }
            }
            return ps;
        }

        // este metodo sirve para obtener las sentencias por linea de codigo
        public Statement analizeCodeLine() {
            al.resetCodeLine(getNextCodeLine());
            currLinePos++;
            Token token = al.getNextToken();
            Statement statement = null;
            try {
                if (token instanceof ComentarioToken) {
                    statement = new ComentarioStatement(Statement.Keyword.COMENTARIO, al);
                } else if (token instanceof KeywordToken || token instanceof VariableId) {

                    // verificando si el ambito del token es el correcto (entre sentencias programa y fin_programa)
                    // si no esta, se lanza una excepcion
                    if (!isPrgStmSet && !token.getValue().equalsIgnoreCase("programa")) {
                        String msjError = "Token: " + token.getValue() + " invalido. ";
                        msjError += "[programa] esperado";
                        al.getCodeLine().addError(new InterpreterError(msjError));
                        throw new AlgorSintaxException(msjError);
                    }
                    if (token instanceof VariableId) {
                        Token nxtToken = al.getNextToken();
                        if (nxtToken instanceof OperadorAsignacion) {

                            /* si la sentencia es de asignacion (variable seguida de op de asignacion)
                             * reviso si ya ha sido declarada, sino se lanza una excepcion
                             */
                            Variable var = asem.searchVariable(token.getValue());
                            if (var != null) {
                                TipoVariable tipoVariable = var.getTipoVariable(); // JalgorInterpreter.getVariables().get(token.getValue()).getTipoVariable();
                                statement = new AsignacionStatement(Statement.Keyword.ASIGNACION, JalgorInterpreter.this, (VariableId) token, tipoVariable);
                            } else {
                                String msjError = "variable " + token.getValue() + " no declarada";
                                al.getCodeLine().addError(new InterpreterError(msjError));
                                throw new AlgorSintaxException(msjError);
                            }
                        } else {
                            String msjError = "[=] esperado";
                            al.getCodeLine().addError(new InterpreterError(msjError));
                            throw new AlgorSintaxException(msjError);
                        }
                    } else if (token instanceof KeywordToken) {
                        Statement.Keyword tipoKeyword = Statement.getKeywordMatcher().get(token.getValue().toLowerCase());
                        switch (tipoKeyword) {

                            case PROGRAMA:
                                // ver si ya existe la sentencia fin_programa en la lista de sentencias de JI
                                if (isPrgStmSet) {
                                    String msjError = "Token: " + token.getValue() + " invalido.";
                                    al.getCodeLine().addError(new InterpreterError(msjError));
                                    throw new AlgorSintaxException(msjError);
                                }

                                ambitoStatements.offer(getNextAmbitoStmSeq());
                                isPrgStmSet = true;

                                statement = new ProgramaStatement(tipoKeyword, JalgorInterpreter.this);
                                Statement prgStm = null;
                                while (hasNextCodeLine() && !(prgStm instanceof ProgramaStatement && prgStm.getTipoSatement().equals(Statement.Keyword.FIN_PROGRAMA))) {
                                    prgStm = analizeCodeLine();
                                    ((ProgramaStatement) statement).addBlockStatement(prgStm);
                                }
                                // si llego hasta aqui es porque es un fin de sentencia,
                                // para manejar el ambito, hacer un poll a la pila de ambito
                                ambitoStatements.pollLast();

                                // validar que/ se halla salido del bucle por sentencia fin_programa y no por fin de archivo
                                if (((ProgramaStatement) statement).getBlockStatements().getLast().getTipoSatement() != Statement.Keyword.FIN_PROGRAMA) {
                                    String msjError = "Sentencia [fin_programa] esperado";
                                    al.getCodeLine().addError(new InterpreterError(msjError));
                                    throw new AlgorSintaxException(msjError);
                                }
                                break;
                            case FIN_PROGRAMA:
                                if (!isPrgStmSet) {
                                    String msjError = "Token: " + token.getValue() + " invalido. ";
                                    msjError += "[programa] esperado";
                                    al.getCodeLine().addError(new InterpreterError(msjError));
                                    throw new AlgorSintaxException(msjError);
                                }
                                if (isFinPrgStmSet) {
                                    String msjError = "Token: " + token.getValue() + " invalido.";
                                    al.getCodeLine().addError(new InterpreterError(msjError));
                                    throw new AlgorSintaxException(msjError);
                                }
                                statement = new ProgramaStatement(tipoKeyword, JalgorInterpreter.this);
                                isFinPrgStmSet = true;
                                break;
                            case NUM:
                            case ALFA: // TODO: lo de ambito de las variables no esta funcionando, arreglar
                                statement = new DeclaracionStatement(tipoKeyword, JalgorInterpreter.this);//new DeclaracionStatement(tipoKeyword, al);
                                break;
                            case LEE:
                                statement = new LeeStatement(tipoKeyword, JalgorInterpreter.this);
                                break;
                            case ESCRIBE:
                                statement = new EscribeStatement(tipoKeyword, JalgorInterpreter.this);
                                break;
                            case SI:
                                ifOpened = true;
                                ifClosed = false;
                                ambitoStatements.offer(getNextAmbitoStmSeq());

                                statement = new CondicionStatement(tipoKeyword, JalgorInterpreter.this);
                                Statement ifCondSt = null;
                                while (hasNextCodeLine() && !(ifCondSt instanceof CondicionStatement && (ifCondSt.getTipoSatement().equals(Statement.Keyword.SINO) || ifCondSt.getTipoSatement().equals(Statement.Keyword.FIN_SI)))) {
                                    ifCondSt = analizeCodeLine();
                                    ((CondicionStatement) statement).addBlockStatement(ifCondSt);
                                }
                                // si llego hasta aqui es porque es un fin de sentencia,
                                // para manejar el ambito, hacer un poll a la pila de ambito
                                ambitoStatements.pollLast();
                                break;
                            case SINO:
                                if (!ifOpened) {
                                    String msjError = "Token: " + token.getValue() + " invalido. ";
                                    msjError += "[si] esperado";
                                    al.getCodeLine().addError(new InterpreterError(msjError));
                                    throw new AlgorSintaxException(msjError);
                                }
                                ambitoStatements.offer(getNextAmbitoStmSeq());

                                statement = new CondicionStatement(tipoKeyword, JalgorInterpreter.this);
                                Statement elseCondSt = null;
                                while (hasNextCodeLine() && !(elseCondSt instanceof CondicionStatement && elseCondSt.getTipoSatement().equals(Statement.Keyword.FIN_SI))) {
                                    elseCondSt = analizeCodeLine();
                                    ((CondicionStatement) statement).addBlockStatement(elseCondSt);
                                }
                                // si llego hasta aqui es porque es un fin de sentencia,
                                // para manejar el ambito, hacer un poll a la pila de ambito
                                ambitoStatements.pollLast();
                                // validar que se halla salido del bucle por sentencia fin_si y no por fin de archivo
                                break;
                            case FIN_SI:
                                if (!ifOpened) {
                                    String msjError = "Token: " + token.getValue() + " invalido. ";
                                    msjError += "[si] esperado";
                                    al.getCodeLine().addError(new InterpreterError(msjError));
                                    throw new AlgorSintaxException(msjError);
                                }
                                statement = new CondicionStatement(tipoKeyword, JalgorInterpreter.this);
                                ifClosed = true;
                                //ifOpened = false;
                                break;
                            case MIENTRAS:
                                ambitoStatements.offer(getNextAmbitoStmSeq());
                                statement = new MientrasStatement(tipoKeyword, JalgorInterpreter.this);
                                Statement bucleSt = null;
                                while (hasNextCodeLine() && !(bucleSt instanceof MientrasStatement && bucleSt.getTipoSatement().equals(Statement.Keyword.FIN_MIENTRAS))) {
                                    bucleSt = analizeCodeLine();
                                    ((MientrasStatement) statement).addBlockStatement(bucleSt);
                                }
                                ambitoStatements.pollLast();
                                break;
                            case FIN_MIENTRAS:
                                statement = new MientrasStatement(tipoKeyword, JalgorInterpreter.this);
                                break;
                            default:
                                String msjError = "Mal comienzo de linea de codigo";
                                al.getCodeLine().addError(new InterpreterError(msjError));
                                throw new AlgorSintaxException(msjError);
                        }
                    }
                } else if (token != null) {
                    String msjError = "Mal comienzo de linea de codigo";
                    al.getCodeLine().addError(new InterpreterError(msjError));
                    throw new AlgorSintaxException(msjError);
                }
            } catch (AlgorSintaxException ase) {
            }
            return statement;
        }
    }

    // Analizador Lexico
    public class AnalizadorLexico {

        private int currPos;
        private CodeLine codeLine;
        private char[] chrCodeLine;

        public AnalizadorLexico() {
        }

        public AnalizadorLexico(CodeLine codeLine) {
            this.codeLine = codeLine;
            initChrCodeLine();
        }

        private void initChrCodeLine() {
            if (codeLine != null) {
                chrCodeLine = codeLine.getOrigValue().toCharArray();
            }
        }

        public Token getNextToken() {
            Token token = null;
            if (!hasNextChar()) {
                return null;
            }
            char currChar = chrCodeLine[currPos];
            switch (currChar) {
                // TODO: posibilidad de crear un token espacio para mantener formato igual al fuente original
                case ' ':
                case '\t':
                    // mover el indice hasta que el char sea diferente de espacio o tab
                    while (currChar == ' ' || currChar == '\t') {
                        currChar = getNextChar();
                    }
                    token = getNextToken();
                    break;
                case '-':
                case '+':
                case '*':
                case '/':
                    if (currChar == '-' && (hasNextChar()
                            && (chrCodeLine[currPos + 1] == '.' || Character.isDigit(chrCodeLine[currPos + 1])))) {
                        currChar = getNextChar();
                        token = getNextToken();
                        break;
                    }
                    token = new OperadorAritmetico(Operador.getOpNames().get(String.valueOf(currChar)), String.valueOf(currChar));
                    currPos++;
                    break;
                case '&':
                case '|':
                case '^':
                case '~':
                    token = new OperadorBooleano(Operador.getOpNames().get(String.valueOf(currChar)), String.valueOf(currChar));
                    currPos++;
                    break;
                case '<':
                case '>':
                case '!':
                    if (chrCodeLine[currPos] == '=') {
                        token = new OperadorRelacional(Operador.getOpNames().get(String.valueOf(currChar) + String.valueOf(chrCodeLine[currPos + 1])),
                                String.valueOf(currChar) + String.valueOf(chrCodeLine[currPos + 1]));
                        currPos += 2;
                    } else {
                        token = new OperadorRelacional(Operador.getOpNames().get(String.valueOf(currChar)), String.valueOf(currChar));
                        currPos++;
                    }
                    break;
                case '=':
                    if (chrCodeLine[currPos + 1] == '=') {
                        token = new OperadorRelacional(Operador.getOpNames().get(String.valueOf(currChar) + String.valueOf(chrCodeLine[currPos + 1])),
                                String.valueOf(currChar) + String.valueOf(chrCodeLine[currPos + 1]));
                        currPos += 2;
                    } else {
                        token = new OperadorAsignacion(Operador.getOpNames().get(String.valueOf(currChar)), String.valueOf(currChar));
                        currPos++;
                    }
                    break;
                case ';':
                case '(':
                case ')':
                case ',':
                case '{':
                case '}':
                    token = new SignoPuntuacion(String.valueOf(currChar));
                    currPos++;
                    break;
                case '.':
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    StringBuilder num = new StringBuilder();
                    // si es un numero negativo
                    if (chrCodeLine[currPos - 1] == '-') {
                        num.append("-");
                    }
                    if (currChar == '.') {
                        num.append(".");
                        currChar = getNextChar();
                    }
                    while (hasNextChar() && Character.isDigit(currChar)) {
                        num.append(currChar);
                        currChar = getNextChar();
                    }
                    token = new ConstanteNumerica(num.toString());
                    break;
                case '"':
                    StringBuilder str = new StringBuilder();
                    str.append(currChar);
                    currChar = getNextChar();
                    while (hasNextChar() && currChar != '"') {
                        str.append(currChar);
                        currChar = getNextChar();
                    }
                    str.append(currChar);
                    token = new ConstanteAlfanumerica(str.toString());
                    currPos++;
                    break;
                default:
                    StringBuilder var = new StringBuilder();
                    //currChar = chrCodeLine[currPos];
                    while ((Character.isLetterOrDigit(currChar) || currChar == '_') && currChar != '\0') {
                        var.append(currChar);
                        //currPos++;
                        currChar = getNextChar();
                    }
                    if (var.toString().equals("com")) {
                        token = new ComentarioToken();
                        break;
                    }
                    if (Statement.keywordMatcher.containsKey(var.toString())) {
                        token = new KeywordToken(var.toString());
                    } else {
                        token = new VariableId(var.toString());
                    }
                    //currPos++;
                    break;
            }
            return token;
        }

        public char[] getChrCodeLine() {
            return chrCodeLine;
        }

        public CodeLine getCodeLine() {
            return codeLine;
        }

        private boolean hasNextChar() {
            if (currPos < chrCodeLine.length) {
                return true;
            }
            return false;
        }

        private char getNextChar() {
            char nxtChar = '\0';
            currPos++;
            if (hasNextChar()) {
                nxtChar = chrCodeLine[currPos];
            }
            return nxtChar;
        }

        public boolean hasNextToken() {
            int prevPos = currPos;
            boolean hasIt = (getNextToken() != null);
            currPos = prevPos;
            return hasIt;
        }

        public void resetCodeLine(CodeLine codeLine) {
            this.codeLine = codeLine;
            currPos = 0;
            initChrCodeLine();
        }
    }

    // Analizador Semantico
    public class AnalizadorSemantico {

        public boolean variableExiste(String variableId) {
            boolean existe = false;
            Variable var = new Variable(variableId, -1);
            for (Integer ambito : as.getAmbitoStatements()) {
                var.setAmbito(ambito);
                if (variables.contains(var)) {
                    existe = true;
                    break;
                }
            }
            return existe;
        }

        public Variable searchVariable(String variableId) {
            Variable variable = null;
            Variable searchedVar = null;
            for (Integer ambito : as.getAmbitoStatements()) {
                searchedVar = new Variable(variableId, ambito);
                int idx = Collections.binarySearch(variables, searchedVar);

                if (idx >= 0) {
                    variable = variables.get(idx);
                    break;
                }
            }
            return variable;
        }
    }
}
