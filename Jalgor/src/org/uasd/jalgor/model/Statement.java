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
import java.util.HashMap;
import java.util.List;
import org.uasd.jalgor.business.AnalizadorLexico;
import org.uasd.jalgor.business.InterpreterError;

/**
 *
 * @author Edwin Bratini <edwin.bratini@gmail.com>
 */
public class Statement {

    public enum Keyword {

        COMENTARIO, ASIGNACION, PROGRAMA, FIN_PROGRAMA, NUM, ALFA,
        LEE, ESCRIBE, SI, ENTONCES, SINO,
        FIN_SI, MIENTRAS, FIN_MIENTRAS
    };
    private Keyword tipoSatement;
    private HashMap<Keyword, String> cppReps = new HashMap<Keyword, String>() {

        {
            put(Keyword.NUM, "double");
            put(Keyword.ALFA, "string");
            put(Keyword.LEE, "cin>>");
            put(Keyword.ESCRIBE, "cout<<");
            put(Keyword.SI, "if");
            put(Keyword.ENTONCES, "{");
            put(Keyword.SINO, "else");
            put(Keyword.FIN_SI, "}");
            put(Keyword.MIENTRAS, "while");
            put(Keyword.FIN_MIENTRAS, "}");
        }
    };
    public static HashMap<String, Keyword> keywordMatcher = new HashMap<String, Keyword>() {

        {
            for (Keyword k : Keyword.values()) {
                if (k.name().equalsIgnoreCase("comentario") || k.name().equalsIgnoreCase("asignacion")) {
                    continue;
                }
                put(k.name(), k);
            }
        }
    };
    private AnalizadorLexico al;
    private String originalValue;
    private String parsedValue;
    private boolean correcta;
    private List<InterpreterError> statementErrors = new ArrayList<InterpreterError>();

    public Statement() {
    }

    public Statement(Keyword tipoSatement) {
        this.tipoSatement = tipoSatement;
    }

    public Statement(Keyword tipoSatement, AnalizadorLexico al) {
        this.tipoSatement = tipoSatement;
        this.al = al;
    }

    public HashMap<Keyword, String> getCppReps() {
        return cppReps;
    }

    public void setCppReps(HashMap<Keyword, String> cppReps) {
        this.cppReps = cppReps;
    }

    public Keyword getTipoSatement() {
        return tipoSatement;
    }

    public void setTipoSatement(Keyword tipoSatement) {
        this.tipoSatement = tipoSatement;
    }

    public AnalizadorLexico getAl() {
        return al;
    }

    public void setAl(AnalizadorLexico al) {
        this.al = al;
    }

    public static HashMap<String, Keyword> getKeywordMatcher() {
        return keywordMatcher;
    }

    public boolean isCorrecta() {
        return correcta;
    }

    public void setCorrecta(boolean correcta) {
        this.correcta = correcta;
    }

    public String getOriginalValue() {
        return originalValue;
    }

    public void setOriginalValue(String originalValue) {
        this.originalValue = originalValue;
    }

    public String getParsedValue() {
        return parsedValue;
    }

    public void setParsedValue(String parsedValue) {
        this.parsedValue = parsedValue;
    }

    public List<InterpreterError> getStatementErrors() {
        return statementErrors;
    }

    public String parse(Keyword statement) {
        return cppReps.get(statement);
    }
}
