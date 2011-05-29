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
package org.uasd.jalgor.ui;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.uasd.jalgor.business.InvalidCommandLineParamException;
import org.uasd.jalgor.business.InvalidFileNameException;
import org.uasd.jalgor.business.JalgorInterpreter;

/**
 *
 * @author Edwin Bratini <edwin.bratini@gmail.com>
 */
public class UIJalgor {

    private HashMap<String, String> cmdLneArgs = new HashMap<String, String>() {

        {
            put("-i", null);
            put("-o", null);
            put("-gui", "0");
        }
    };
    private HashMap<String, String> cmdLneArgDesc = new HashMap<String, String>() {

        {
            put("-i", "para indicar el path name del archivo fuente");
            put("-o", "para indicar el path name del archivo .cpp de salida");
            put("-gui", "para indicar si lanza modo grafico");
        }
    };

    public static void main(String[] args) {
        String[] params = JOptionPane.showInputDialog("Digite parametros de archivo entrada y salida separados por ;").split(";");
        args = new String[] {"-i", params[0], "-o", params[1]};
        UIJalgor uiJalgor = new UIJalgor();
        try {
            uiJalgor.validarEntrada(args);
            uiJalgor.initJalgor();
        } catch (InvalidCommandLineParamException ex) {
            Logger.getLogger(UIJalgor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void initJalgor() {
        try {
            new JalgorInterpreter(cmdLneArgs.get("-i"), cmdLneArgs.get("-o")).start();
        } catch (InvalidFileNameException ex) {
            Logger.getLogger(UIJalgor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public HashMap<String, String> getCmdLneArgDesc() {
        return cmdLneArgDesc;
    }

    public HashMap<String, String> getCmdLneArgs() {
        return cmdLneArgs;
    }

    private String getAvailableCmdLneOps() {
        StringBuilder availCmdLneArgs = new StringBuilder();
        for (Map.Entry<String, String> cmdLineArg : cmdLneArgDesc.entrySet()) {
            availCmdLneArgs.append(String.format("%-4s %s", cmdLineArg.getKey(), cmdLineArg.getValue())).append("\n");
        }

        return availCmdLneArgs.toString();
    }

    private void printCmdLneArgDesc() {
        System.out.println("Argumentos de linea de comando disponibles\n");
        System.out.println(getAvailableCmdLneOps());
    }

    private void validarEntrada(String[] args) throws InvalidCommandLineParamException {
        if (args.length < 2) {
            String excMessage = "Una excepcion de tipo InvalidCommandLineParamException ha ocurrido.\n";
            excMessage += "Insuficientes argumentos de linea de comando\n";
            printCmdLneArgDesc();
            throw new InvalidCommandLineParamException(excMessage);
        }

        if (args.length >= 2) {
            if ((args.length % 2) != 0) {
                String excMessage = "Una excepcion de tipo InvalidCommandLineParamException ha ocurrido.\n";
                excMessage += "Revise el numero de argumentos enviados\n";
                printCmdLneArgDesc();
                throw new InvalidCommandLineParamException(excMessage);
            } else {
                for (int i = 0, j = 1; i < args.length; i += 2, j += 2) {
                    if (args[i].equalsIgnoreCase("-i")) {
                        if (!new File(args[j]).exists()) {
                            String excMessage = "Una excepcion de tipo InvalidCommandLineParamException ha ocurrido.\n";
                            excMessage += "Verifique el nombre/existencia de archivo\n";
                            throw new InvalidCommandLineParamException(excMessage);
                        }
                    }

                    if (cmdLneArgs.containsKey(args[i].toLowerCase())) {
                        cmdLneArgs.put(args[i], args[j]);
                    } else {
                        String excMessage = "Una excepcion de tipo InvalidCommandLineParamException ha ocurrido.\n";
                        excMessage += "Linea de comando no reconocida.\n";
                        excMessage += "Opciones Validas : " + Arrays.asList(cmdLneArgs.keySet()) + "\nHelp:\n" + getAvailableCmdLneOps();
                        throw new InvalidCommandLineParamException(excMessage);
                    }
                }
            }
        }
    }

    // TODO: agregar llamada a modo grafico del interprete
    private void startGraphicMode() {
    }
}