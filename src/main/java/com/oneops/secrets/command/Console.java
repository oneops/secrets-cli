package com.oneops.secrets.command;

import static com.oneops.secrets.utils.Color.warn;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class Console {

  static final String INPUT = "^y(es)?$";

  static void readConsole(String message)  throws IOException{
    Pattern p = Pattern.compile(INPUT);
    String in;
    if (null != System.console()) {
      in = System.console().readLine(warn(message));
    } else {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        in = reader.readLine();
        System.out.println(warn(message) + in);
    }
    if (in == null || !p.matcher(in.toLowerCase()).matches()) {
      throw new IllegalStateException("Exiting");
    }
  }
}
