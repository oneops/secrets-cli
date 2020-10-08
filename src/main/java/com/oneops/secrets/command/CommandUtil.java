package com.oneops.secrets.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class CommandUtil {

  static String readFromSystemIn(Pattern p) {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String input = "";
    try {
      input = reader.readLine();
    } catch (IOException e) {
      throw new IllegalStateException("Exiting");
    }
    return input;
  }
}
