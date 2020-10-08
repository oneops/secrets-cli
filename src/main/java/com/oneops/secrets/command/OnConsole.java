package com.oneops.secrets.command;

import static com.oneops.secrets.utils.Color.warn;

import java.nio.file.Path;

public abstract class OnConsole {
  static String readConsole(Path path) {
    return System.console()
        .readLine(warn("File " + path + " exists. Do you want to overwrite (y/n)? "));
  }
}
