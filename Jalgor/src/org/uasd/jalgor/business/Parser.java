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

import java.util.logging.Level;
import java.util.logging.Logger;
import org.uasd.jalgor.model.AsignacionStatement;
import org.uasd.jalgor.model.ComentarioStatement;
import org.uasd.jalgor.model.ConstanteAlfanumerica;
import org.uasd.jalgor.model.OperadorAsignacion;
import org.uasd.jalgor.model.SignoPuntuacion;
import org.uasd.jalgor.model.Statement;
import org.uasd.jalgor.model.Statement.Keyword;
import org.uasd.jalgor.model.Token;
import org.uasd.jalgor.model.Variable;
import org.uasd.jalgor.model.Variable.TipoVariable;
import org.uasd.jalgor.model.VariableId;

/**
 *
 * @author Edwin Bratini <edwin.bratini@gmail.com>
 */
public class Parser {

    public static Statement makeStatement(Keyword tipoSatement, AnalizadorLexico al, JalgorInterpreter ji, Token token) {
        Statement statement = null;
        try {
            switch (tipoSatement) {
                case COMENTARIO:
                    statement = new ComentarioStatement(tipoSatement, al);
                    break;
                case ASIGNACION:
                    Token nxtToken = al.getNextToken();
                    if (nxtToken instanceof OperadorAsignacion) {
                        nxtToken = al.getNextToken();
                        Variable var = AnalizadorSemantico.searchVariable(token.getValue(), AnalizadorSintactico.getAmbitoStatements());
                        switch (var.getTipoVariable()) {
                            case ALFA:
                                statement = new AsignacionStatement(tipoSatement, al, (VariableId) token, TipoVariable.ALFA);
                                while (al.hasNextToken()) {
                                    Token tok = al.getNextToken();
                                    if (tok instanceof ConstanteAlfanumerica || (tok instanceof SignoPuntuacion && tok.getValue().equals(";"))) {
                                        statement.addTokenStatement(al.getNextToken());
                                    }
                                }
                                break;
                            case NUM:
                                break;
                        }
                    }
            }

        } catch (AlgorSintaxException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }

        return statement;
    }
}
