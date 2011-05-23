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

import org.uasd.jalgor.model.ComentarioToken;
import org.uasd.jalgor.model.ConstanteAlfanumerica;
import org.uasd.jalgor.model.ConstanteNumerica;
import org.uasd.jalgor.model.KeywordToken;
import org.uasd.jalgor.model.Operador;
import org.uasd.jalgor.model.OperadorAritmetico;
import org.uasd.jalgor.model.OperadorAsignacion;
import org.uasd.jalgor.model.OperadorBooleano;
import org.uasd.jalgor.model.OperadorRelacional;
import org.uasd.jalgor.model.SignoPuntuacion;
import org.uasd.jalgor.model.Statement;
import org.uasd.jalgor.model.Token;
import org.uasd.jalgor.model.VariableId;

/**
 *
 * @author Edwin Bratini <edwin.bratini@gmail.com>
 */
public class AnalizadorLexico {

    private int currPos;
    private char[] codeLine;
    //private CodeLine lineCode;

    public Token getNextToken() {
        Token token = null;
        if (currPos == (codeLine.length - 1)) {
            return null;
        }
        switch (codeLine[currPos]) {
            case '-':
            case '+':
            case '*':
            case '/':
                if (codeLine[currPos + 1] == '/') {
                    token = new ComentarioToken();
                    currPos += 2;
                } else {
                    token = new OperadorAritmetico(Operador.getOpNames().get(String.valueOf(codeLine[currPos])));
                    currPos++;
                }
                break;
            case '&':
            case '|':
            case '^':
                token = new OperadorBooleano(Operador.getOpNames().get(String.valueOf(codeLine[currPos])));
                currPos++;
                break;
            case '<':
            case '>':
            case '!':
                if (codeLine[currPos + 1] == '=') {
                    token = new OperadorRelacional(Operador.getOpNames().get(String.valueOf(codeLine[currPos] + codeLine[currPos + 1])));
                    currPos += 2;
                } else {
                    token = new OperadorRelacional(Operador.getOpNames().get(String.valueOf(codeLine[currPos])));
                    currPos++;
                }
                break;
            case '=':
                if (codeLine[currPos + 1] == '=') {
                    token = new OperadorRelacional(Operador.getOpNames().get(String.valueOf(codeLine[currPos] + codeLine[currPos + 1])));
                    currPos += 2;
                } else {
                    token = new OperadorAsignacion(Operador.getOpNames().get(String.valueOf(codeLine[currPos])));
                    currPos++;
                }
                break;
            case ';':
            case '(':
            case ')':
            case ',':
                token = new SignoPuntuacion(codeLine[currPos]);
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
                while (Character.isDigit(codeLine[currPos++])) {
                    num.append(codeLine[currPos]);
                }
                token = new ConstanteNumerica(num.toString());
                currPos++;
                break;
            case '"':
                StringBuilder str = new StringBuilder();
                while (codeLine[currPos++] != '"') {
                    str.append(codeLine[currPos]);
                }
                token = new ConstanteAlfanumerica(str.toString());
                currPos += 2;
                break;
            default:
                StringBuilder var = new StringBuilder();
                char currChar = codeLine[currPos];
                while (Character.isLetterOrDigit(currChar) || currChar == '_') {
                    var.append(codeLine[currPos]);
                    currPos++;
                    currChar = codeLine[currPos];
                }
                if (Statement.keywordMatcher.containsKey(var.toString().toUpperCase())) {
                    token = new KeywordToken(var.toString());
                } else {
                    token = new VariableId(var.toString());
                }
                currPos++;
                break;
        }

        token.setSiblingToken(getNextToken());
        return token;
    }

    public char[] getCodeLine() {
        return codeLine;
    }

    public boolean hasNextToken() {
        boolean hasIt = false;
        currPos += 1;

        if (getNextToken() != null) {
            hasIt = true;
        }

        currPos -= 1;
        return hasIt;
    }

    public void resetAnalizador(char[] codeLine) {
        this.codeLine = codeLine;
        this.currPos = 0;
    }
}
