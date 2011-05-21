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

/**
 *
 * @author Edwin Bratini <edwin.bratini@gmail.com>
 */
public class Expresion extends Token {

    private Expresion exp1;
    private Expresion exp2;
    private Operador oper;

    public Expresion() {
    }

    public Expresion(Expresion exp1, Expresion exp2, Operador oper) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.oper = oper;
    }

    public Expresion getExp1() {
        return exp1;
    }

    public void setExp1(Expresion exp1) {
        this.exp1 = exp1;
    }

    public Expresion getExp2() {
        return exp2;
    }

    public void setExp2(Expresion exp2) {
        this.exp2 = exp2;
    }

    public Operador getOper() {
        return oper;
    }

    public void setOper(Operador oper) {
        this.oper = oper;
    }
}
