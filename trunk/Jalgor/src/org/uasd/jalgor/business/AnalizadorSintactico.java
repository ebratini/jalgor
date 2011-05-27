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
import org.uasd.jalgor.model.AsignacionStatement;
import org.uasd.jalgor.model.CodeLine;
import org.uasd.jalgor.model.ComentarioStatement;
import org.uasd.jalgor.model.ComentarioToken;
import org.uasd.jalgor.model.CondicionStatement;
import org.uasd.jalgor.model.DeclaracionStatement;
import org.uasd.jalgor.model.EscribeStatement;
import org.uasd.jalgor.model.KeywordToken;
import org.uasd.jalgor.model.LeeStatement;
import org.uasd.jalgor.model.MientrasStatement;
import org.uasd.jalgor.model.OperadorAsignacion;
import org.uasd.jalgor.model.ProgramaStatement;
import org.uasd.jalgor.model.Statement;
import org.uasd.jalgor.model.Token;
import org.uasd.jalgor.model.Variable.TipoVariable;
import org.uasd.jalgor.model.VariableId;

/**
 *
 * @author Edwin Bratini <edwin.bratini@gmail.com>
 */
public class AnalizadorSintactico {

    // TODO: crear metodo que construye expresiones y arbol binario
    private AnalizadorLexico al = new AnalizadorLexico();
    private List<String> errores = new ArrayList<String>();

    // TODO: no se necesitara objeto ji por que los miembros que necesito los hare estaticos
    private JalgorInterpreter ji = new JalgorInterpreter();
    private int currLinePos = 1;

    public AnalizadorSintactico() {
    }

    public AnalizadorSintactico(AnalizadorLexico al) {
        this.al = al;
    }

    // TODO: eliminar este constructor
    public AnalizadorSintactico(JalgorInterpreter ji, AnalizadorLexico al) {
        this.ji = ji;
        this.al = al;
    }

    public void setAl(AnalizadorLexico al) {
        this.al = al;
    }

    // TODO: Eliminar
    public JalgorInterpreter getJi() {
        return ji;
    }

    public void setJi(JalgorInterpreter ji) {
        this.ji = ji;
    }

    public AnalizadorLexico getAl() {
        return al;
    }

    public int getCurrLinePos() {
        return currLinePos;
    }

    public List<String> getErrores() {
        return errores;
    }
    
    public void go() {
        while (hasNextCodeLine()) {
            ji.getStatements().add(analizeCodeLine());
        }
    }

    private boolean hasNextCodeLine() {
        int prevPos = currLinePos;
        boolean hasIt = (getNextCodeLine() != null);
        currLinePos = prevPos;
        return hasIt;
    }

    private CodeLine getNextCodeLine() {
        CodeLine codeLine = null;
        // TODO: sustituir ji por nombre de clase (static access)
        if (currLinePos < ji.getCodeLines().size()) {
            codeLine = ji.getCodeLines().get(currLinePos);
        }
        currLinePos++;
        return codeLine;
    }

    public Statement analizeCodeLine() {
        al.resetCodeLine(getNextCodeLine());
        Token token = al.getNextToken();
        Statement statement = null;
        try {
            if (token instanceof ComentarioToken) {
                statement = new ComentarioStatement(Statement.Keyword.COMENTARIO, al);
            } else if (token instanceof KeywordToken || token instanceof VariableId) {
                if (token instanceof VariableId) {
                    if (token.getSiblingToken() instanceof OperadorAsignacion) {
                        if (JalgorInterpreter.getVariables().containsKey(token.getValue())) {
                            TipoVariable tipoVariable = JalgorInterpreter.getVariables().get(token.getValue()).getTipoVariable();
                            statement = new AsignacionStatement(Statement.Keyword.ASIGNACION, al, (VariableId) token, tipoVariable);
                        } else {
                            String msjError = "variable " + token.getValue() + "no declarada";
                            errores.add(msjError);
                            al.getCodeLine().addError(new InterpreterError(msjError));
                        }
                    } else {
                        String msjError = "[=] esperado";
                        errores.add(msjError);
                        al.getCodeLine().addError(new InterpreterError(msjError));
                    }
                } else if (token instanceof KeywordToken) {
                    Statement.Keyword tipoKeyword = Statement.getKeywordMatcher().get(token.getValue());
                    switch (tipoKeyword) {

                        case PROGRAMA:
                        case FIN_PROGRAMA:
                            statement = new ProgramaStatement(tipoKeyword, al);
                            break;
                        case NUM:
                        case ALFA:
                            statement = new DeclaracionStatement(tipoKeyword, al);
                            break;
                        case LEE:
                            statement = new LeeStatement(tipoKeyword, al);
                            break;
                        case ESCRIBE:
                            statement = new EscribeStatement(tipoKeyword, al);
                            break;
                        case SI:
                            statement = new CondicionStatement(tipoKeyword, al);
                            Statement condSt = analizeCodeLine();
                            do {
                                ((CondicionStatement) statement).addBlockStatement(condSt);
                            } while(hasNextCodeLine() && (!(condSt instanceof CondicionStatement)) && condSt.getTipoSatement().equals(Statement.Keyword.FIN_SI));
                            break;
                        case SINO:
                            statement = new CondicionStatement(tipoKeyword, al);
                            break;
                        case FIN_SI:
                            statement = new CondicionStatement(tipoKeyword, al);
                            break;
                        case MIENTRAS:
                            statement = new MientrasStatement(tipoKeyword, al);
                            Statement bucleSt = analizeCodeLine();
                            do {
                                ((MientrasStatement) statement).addBlockStatement(bucleSt);
                            } while(hasNextCodeLine() && (!(bucleSt instanceof MientrasStatement)) && bucleSt.getTipoSatement().equals(Statement.Keyword.FIN_PROGRAMA));
                            break;
                        case FIN_MIENTRAS:
                            statement = new MientrasStatement(tipoKeyword, al);
                            break;
                    }
                }
            } else {
                String msjError = "mal comienzo de linea de codigo";
                errores.add(msjError);
                al.getCodeLine().addError(new InterpreterError(msjError));
            }
        } catch (AlgorSintaxException ase) {
        }
        return statement;
    }
}
