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

import java.util.ArrayList;
import java.util.List;
import org.uasd.jalgor.model.ComentarioToken;
import org.uasd.jalgor.model.KeywordToken;
import org.uasd.jalgor.model.OperadorAsignacion;
import org.uasd.jalgor.model.Statement;
import org.uasd.jalgor.model.Token;
import org.uasd.jalgor.model.Variable;
import org.uasd.jalgor.model.VariableId;

/**
 *
 * @author Edwin Bratini <edwin.bratini@gmail.com>
 */
public class AnalizadorSintactico {

    private AnalizadorLexico al = new AnalizadorLexico();
    private List<String> errores = new ArrayList<String>();
    private JalgorInterpreter ji = new JalgorInterpreter();

    public AnalizadorSintactico() {
    }

    public AnalizadorSintactico(AnalizadorLexico al) {
        this.al = al;
    }

    public AnalizadorSintactico(JalgorInterpreter ji, AnalizadorLexico al) {
        this.ji = ji;
        this.al = al;
    }

    public void setAl(AnalizadorLexico al) {
        this.al = al;
    }

    public Statement analize(Token token) {

        Statement statement = null;
        if (token instanceof ComentarioToken) {
            statement = Parser.makeStatement(Statement.Keyword.COMENTARIO, al, ji, token);//new ComentarioStatement(Statement.Keyword.COMENTARIO, al);
        } else if (token instanceof KeywordToken || token instanceof VariableId) {
            if (token instanceof VariableId) {
                if (token.getSiblingToken() instanceof OperadorAsignacion) {
                    if (ji.getVariables().containsKey(token.getValue())) {
                        statement = Parser.makeStatement(Statement.Keyword.ASIGNACION, al, ji, token);//new AsignacionStatement(Statement.Keyword.ASIGNACION, al);
                    } else {
                        errores.add("variable " + token.getValue() + "no declarada");
                    }
                } else {
                    errores.add("[=] esperado");
                }
            } else if (token instanceof KeywordToken) {
                Statement.Keyword tipoKeyword = Statement.getKeywordMatcher().get(token.getValue());
                switch (tipoKeyword) {

                    case PROGRAMA:
                    case FIN_PROGRAMA:
                        statement = Parser.makeStatement(tipoKeyword, al, ji, token);//new ProgramaStatement(tipoKeyword, al);
                        break;
                    case NUM:
                    case ALFA:
                        statement = Parser.makeStatement(tipoKeyword, al, ji, token);//new DeclaracionStatement(tipoKeyword, al);
                        break;
                    case LEE:
                        statement = Parser.makeStatement(tipoKeyword, al, ji, token);//new LeeStatement(tipoKeyword, al);
                        break;
                    case ESCRIBE:
                        statement = Parser.makeStatement(tipoKeyword, al, ji, token);//new EscribeStatement(tipoKeyword, al);
                        break;
                    case SI:
                    case SINO:
                    case FIN_SI:
                        statement = Parser.makeStatement(tipoKeyword, al, ji, token);//new CondicionStatement(tipoKeyword, al);
                        break;
                    case MIENTRAS:
                    case FIN_MIENTRAS:
                        statement = Parser.makeStatement(tipoKeyword, al, ji, token);//new MientrasStatement(tipoKeyword, al);
                        break;
                }
            }
        } else {
            errores.add("mal comienzo de linea de codigo");
        }
        return statement;
    }
}
