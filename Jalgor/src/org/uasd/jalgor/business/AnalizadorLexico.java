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

import org.uasd.jalgor.model.*;

/**
 *
 * @author Edwin Bratini <edwin.bratini@gmail.com>
 */
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
                    //currPos++;
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
                    token = getNextToken();
                    break;
                }
                token = new OperadorAritmetico(Operador.getOpNames().get(String.valueOf(currChar)));
                //currPos++;
                break;
            case '&':
            case '|':
            case '^':
            case '~':
                token = new OperadorBooleano(Operador.getOpNames().get(String.valueOf(currChar)));
                //currPos++;
                break;
            case '<':
            case '>':
            case '!':
                if (chrCodeLine[currPos] == '=') {
                    token = new OperadorRelacional(Operador.getOpNames().get(String.valueOf(currChar + chrCodeLine[currPos + 1])));
                    currPos += 2;
                } else {
                    token = new OperadorRelacional(Operador.getOpNames().get(String.valueOf(currChar)));
                    //currPos++;
                }
                break;
            case '=':
                if (chrCodeLine[currPos + 1] == '=') {
                    token = new OperadorRelacional(Operador.getOpNames().get(String.valueOf(currChar + chrCodeLine[currPos + 1])));
                    currPos += 2;
                } else {
                    token = new OperadorAsignacion(Operador.getOpNames().get(String.valueOf(currChar)));
                    //currPos++;
                }
                break;
            case ';':
            case '(':
            case ')':
            case ',':
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
                currPos++;
                break;
            case '"':
                StringBuilder str = new StringBuilder();
                currChar = getNextChar();
                while (hasNextChar() && currChar != '"') {
                    str.append(currChar);
                    currChar =  getNextChar();
                }
                token = new ConstanteAlfanumerica(str.toString());
                //currPos += 2;
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
                if (Statement.keywordMatcher.containsKey(var.toString().toLowerCase())) {
                    token = new KeywordToken(var.toString());
                } else {
                    token = new VariableId(var.toString());
                }
                //currPos++;
                break;
        }

        /*if (token != null) {
            int prevPos = currPos;
            token.setSiblingToken(getNextToken());
            currPos = prevPos;
        }*/
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
