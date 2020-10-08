package com.oneops.secrets.command;

import static com.oneops.secrets.utils.Color.warn;

public abstract class OnConsole {
  static String readConsole(String msg) {
    return System.console().readLine(warn(msg));
  }
}
