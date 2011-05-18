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
import org.uasd.jalgor.business.InvalidCommandLineParamException;
import org.uasd.jalgor.business.InvalidSourceFileNameException;
import org.uasd.jalgor.business.JalgorInterpreter;

/**
 *
 * @author Edwin Bratini <edwin.bratini@gmail.com>
 */
public class UIJalgor {

    private HashMap<String, String> cmdLneArgs = new HashMap<String, String>();
    private HashMap<String, String> cmdLneArgDesc = new HashMap<String, String>();

    public UIJalgor() {
        initCmdLineOptions();
        initCmdLneArgDesc();
    }

    public static void main(String[] args) {
        UIJalgor uiJalgor = new UIJalgor();
        try {
            uiJalgor.validarEntrada(args);
            /*if (!uiJalgor.getCmdLneArgs().get("-gui").equalsIgnoreCase("0")) {
                uiJalgor.startGraphicMode();
            }*/
            try {
                new JalgorInterpreter(args[0], args[1]).start();
            } catch (InvalidSourceFileNameException ex) {
                Logger.getLogger(UIJalgor.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (InvalidCommandLineParamException ex) {
            Logger.getLogger(UIJalgor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public HashMap<String, String> getCmdLneArgDesc() {
        return cmdLneArgDesc;
    }

    public HashMap<String, String> getCmdLneArgs() {
        return cmdLneArgs;
    }

    private void initCmdLneArgDesc() {
        cmdLneArgDesc.put("-i", "para indicar el path name del archivo fuente");
        cmdLneArgDesc.put("-o", "para indicar el path name del archivo .cpp de salida");
        cmdLneArgDesc.put("-gui", "para indicar si lanza modo grafico");
    }

    private String getAvailableCmdLneOps() {
        StringBuilder availCmdLneArgs = new StringBuilder();
        for (Map.Entry<String, String> cmdLineArg : cmdLneArgDesc.entrySet()) {
            availCmdLneArgs.append(String.format("%s -- > %s", cmdLineArg.getKey(), cmdLineArg.getValue()));
        }

        return availCmdLneArgs.toString();
    }

    private void printCmdLneArgDesc() {
        System.out.println("Argumentos de linea de comando disponibles");
        System.out.println(getAvailableCmdLneOps());
    }

    private void initCmdLineOptions() {
        cmdLneArgs.put("-i", null);
        cmdLneArgs.put("-o", null);
        cmdLneArgs.put("-gui", "0");
    }

    private void validarEntrada(String[] args) throws InvalidCommandLineParamException {
        if (args.length < 2) {
            String excMessage = "Una excepcion de tipo InvalidCommandLineParamException ha ocurrido.\n";
            excMessage += "Insuficientes argumentos de linea de comando";
            throw new InvalidCommandLineParamException(excMessage);
        }

        if (args.length >= 2) {
            if ((args.length % 2) != 0) {
                String excMessage = "Una excepcion de tipo InvalidCommandLineParamException ha ocurrido.\n";
                excMessage += "Revise el numero de argumentos enviados";
                throw new InvalidCommandLineParamException(excMessage);
            } else {
                for (int i = 0, j = 1; i < args.length; i += 2, j += 2) {
                    if (args[i].equalsIgnoreCase("-i")) {
                        if (!new File(args[j]).exists()) {
                            String excMessage = "Una excepcion de tipo InvalidCommandLineParamException ha ocurrido.\n";
                            excMessage += "Verifique el nombre/existencia de archivo";
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

    private void startGraphicMode() {
    }
}
