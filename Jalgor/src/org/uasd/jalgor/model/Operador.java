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

/**
 *
 * @author Edwin Bratini <edwin.bratini@gmail.com>
 */
public abstract class Operador extends Token {

    public enum TipoOperador {

        SUMA, RESTA, MULT, DIV,
        EQ, NE, GT, GE, LT, LE,
        AND, OR, XOR, ASIG
    };
    protected TipoOperador tipoOperador;
    protected HashMap<String, TipoOperador> opNames = new HashMap<String, TipoOperador>() {{
        put("-", TipoOperador.RESTA);
        put("+", TipoOperador.SUMA);
        put("*", TipoOperador.MULT);
        put("/", TipoOperador.DIV);

        put("==", TipoOperador.EQ);
        put("!=", TipoOperador.NE);
        put(">", TipoOperador.GT);
        put("<", TipoOperador.LT);
        put(">=", TipoOperador.GE);
        put("<=", TipoOperador.LE);

        put("&", TipoOperador.AND);
        put("|", TipoOperador.OR);
        put("^", TipoOperador.XOR);
        put("=", TipoOperador.ASIG);
    }};

    public Operador() {
        super(Token.TipoToken.OPERADOR);
    }

    public Operador(TipoOperador tipoOperador) {
        super(Token.TipoToken.OPERADOR);
        this.tipoOperador = tipoOperador;
    }
    
    public TipoOperador getTipoOperador() {
        return tipoOperador;
    }

    public void setTipoOperador(TipoOperador tipoOperador) {
        this.tipoOperador = tipoOperador;
    }

    public HashMap<String, TipoOperador> getOpNames() {
        return opNames;
    }

    public abstract String getValue();
}
