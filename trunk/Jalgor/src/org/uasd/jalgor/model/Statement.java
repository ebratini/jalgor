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

import java.util.HashMap;
import java.util.LinkedList;
import org.uasd.jalgor.business.JalgorInterpreter;
import org.uasd.jalgor.business.JalgorInterpreter.AnalizadorLexico;
import org.uasd.jalgor.business.JalgorInterpreter.AnalizadorSemantico;

/**
 *
 * @author Edwin Bratini <edwin.bratini@gmail.com>
 */
public abstract class Statement {

    public enum Keyword {

        COMENTARIO, ASIGNACION, PROGRAMA, FIN_PROGRAMA, NUM, ALFA,
        LEE, ESCRIBE, SI, ENTONCES, SINO, FIN_SI, MIENTRAS, FIN_MIENTRAS,
        VERDADERO, FALSO
    };
    private Keyword tipoSatement;
    public static HashMap<Keyword, String> cppReps = new HashMap<Keyword, String>() {

        {
            put(Keyword.COMENTARIO, "//");
            put(Keyword.ASIGNACION, "");
            put(Keyword.PROGRAMA, "int main() {");
            put(Keyword.FIN_PROGRAMA, "}");
            put(Keyword.NUM, "float");
            put(Keyword.ALFA, "string");
            put(Keyword.LEE, "cin >>");
            put(Keyword.ESCRIBE, "cout <<");
            put(Keyword.SI, "if");
            put(Keyword.ENTONCES, "{");
            put(Keyword.SINO, "}else{");
            put(Keyword.FIN_SI, "}");
            put(Keyword.MIENTRAS, "while");
            put(Keyword.FIN_MIENTRAS, "}");
            put(Keyword.VERDADERO, "true");
            put(Keyword.FALSO, "false");
        }
    };
    public static HashMap<String, Keyword> keywordMatcher = new HashMap<String, Keyword>() {

        {
            for (Keyword k : Keyword.values()) {
                if (k.toString().toLowerCase().equalsIgnoreCase("comentario")
                        || k.toString().toLowerCase().equalsIgnoreCase("asignacion")) {
                    continue;
                }
                put(k.toString().toLowerCase(), k);
            }
        }
    };
    private JalgorInterpreter ji;
    private AnalizadorLexico al;
    private AnalizadorSemantico as;
    private String originalValue;
    private String parsedValue;
    private LinkedList<Token> tokensStatement = new LinkedList<Token>();

    public Statement() {
    }

    public Statement(Keyword tipoSatement) {
        this.tipoSatement = tipoSatement;
    }

    public Statement(Keyword tipoSatement, JalgorInterpreter jalgorInterpreter) {
        this.tipoSatement = tipoSatement;
        this.ji = jalgorInterpreter;
        this.originalValue = ji.getAs().getAl().getCodeLine().getOrigValue();
    }

    public Statement(Keyword tipoSatement, AnalizadorLexico al) {
        this.tipoSatement = tipoSatement;
        this.al = al;
        this.originalValue = al.getCodeLine().getOrigValue();
    }

    public Statement(Keyword tipoSatement, AnalizadorLexico al, AnalizadorSemantico as) {
        this(tipoSatement, al);
        this.as = as;
    }

    public JalgorInterpreter getJi() {
        return ji;
    }

    public void setJi(JalgorInterpreter ji) {
        this.ji = ji;
    }

    public static HashMap<Keyword, String> getCppReps() {
        return cppReps;
    }

    public static void setCppReps(HashMap<Keyword, String> cppReps) {
        Statement.cppReps = cppReps;
    }

    public AnalizadorSemantico getAs() {
        return as;
    }

    public void setAs(AnalizadorSemantico as) {
        this.as = as;
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

    public LinkedList<Token> getTokensStatement() {
        return tokensStatement;
    }

    public void setTokensStatement(LinkedList<Token> tokensStatement) {
        this.tokensStatement = tokensStatement;
    }

    public void addTokenStatement(Token token) {
        this.tokensStatement.offer(token);
    }

    protected String parse() {
        StringBuilder sbParsedValue = new StringBuilder();
        sbParsedValue.append(String.format("%s ", cppReps.get(tipoSatement)));

        for (Token tok : this.getTokensStatement()) {
            sbParsedValue.append(tok.toString());
        }
        return sbParsedValue.toString().trim();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Statement other = (Statement) obj;
        if (this.tipoSatement != other.tipoSatement) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + (this.tipoSatement != null ? this.tipoSatement.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return this.parsedValue;
    }
}
