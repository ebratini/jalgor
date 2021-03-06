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
public class Variable extends Token implements Comparable<Variable> {

    private int ambito;

    public enum TipoVariable {

        NUM, ALFA
    };
    private TipoVariable tipoVariable;
    private String id;

    // TODO: agregar miembro ambito
    public Variable() {
    }

    public Variable(String id) {
        this.id = id;
    }

    public Variable(TipoVariable tipoVariable) {
        this.tipoVariable = tipoVariable;
    }

    public Variable(TipoVariable tipoVariable, String id) {
        this(tipoVariable);
        this.id = id;
    }

    public Variable(String id, int ambito) {
        this(id);
        this.ambito = ambito;
    }

    public Variable(TipoVariable tipoVariable, String id, int ambito) {
        this(tipoVariable, id);
        this.ambito = ambito;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TipoVariable getTipoVariable() {
        return tipoVariable;
    }

    public void setTipoVariable(TipoVariable tipoVariable) {
        this.tipoVariable = tipoVariable;
    }

    public int getAmbito() {
        return ambito;
    }

    public void setAmbito(int ambito) {
        this.ambito = ambito;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Variable other = (Variable) obj;
        if (this.ambito != other.ambito) {
            return false;
        }
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.ambito;
        hash = 41 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public int compareTo(Variable o) {
        return this.id.compareTo(o.getId());
    }
}
