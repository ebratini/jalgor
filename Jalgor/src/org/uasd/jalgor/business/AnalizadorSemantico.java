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

import java.util.Collections;
import java.util.LinkedList;
import org.uasd.jalgor.model.Variable;

/**
 *
 * @author Edwin Bratini <edwin.bratini@gmail.com>
 */
public class AnalizadorSemantico {

    public static boolean variableExiste(String variableId) {
        boolean existe = false;
        Variable var = new Variable(variableId, -1);
        for (Integer ambito : AnalizadorSintactico.getAmbitoStatements()) {
            var.setAmbito(ambito);
            if (JalgorInterpreter.getVariables().contains(var)) {
                existe = true;
                break;
            }
        }
        return existe;
    }
    
    public static Variable searchVariable(String variableId) {
        Variable variable = null;
        Variable searchedVar = null;
        for (Integer ambito : AnalizadorSintactico.getAmbitoStatements()) {
            searchedVar = new Variable(variableId, ambito);
            int idx = Collections.binarySearch(JalgorInterpreter.getVariables(), searchedVar, Collections.reverseOrder());

            if (idx >= 0) {
                variable = JalgorInterpreter.getVariables().get(idx);
                break;
            }
        }
        return variable;
    }
}
