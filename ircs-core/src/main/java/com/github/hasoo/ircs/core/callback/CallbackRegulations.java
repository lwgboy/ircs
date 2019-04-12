package com.github.hasoo.ircs.core.callback;

public class CallbackRegulations {

  public boolean isComply(String callback, boolean blockSpecialCallback) {

    if (0 == callback.indexOf("#")) {
      return true;
    }

    int len = callback.length();

    if (blockSpecialCallback) {
      if (7 > len) {
        return false;
      }
    }

    boolean ret = false;
    switch (len) {
      case 3:
        ret = significant3Digit(callback);
        break;
      case 4:
        ret = significant4Digit(callback);
        break;
      case 5:
        ret = significant5Digit(callback);
        break;
      case 6:
        ret = significant6Digit(callback);
        break;
      case 8:
        ret = significant8Digit(callback);
        break;
      case 9:
        ret = significant9Digit(callback);
        break;
      case 10:
      case 11:
        ret = significant1011Digit(callback);
        break;
      case 12:
        ret = significant12Digit(callback);
        break;
      case 14:
        ret = significant14Digit(callback);
        break;
    }

    return ret;
  }

  private boolean significant3Digit(String callback) {
    switch (Integer.parseInt(callback)) {
      case 100:
      case 101:
      case 106:
      case 107:
      case 108:
      case 109:
      case 110:
      case 111:
      case 112:
      case 113:
      case 114:
      case 115:
      case 116:
      case 117:
      case 118:
      case 119:
      case 120:
      case 121:
      case 122:
      case 123:
      case 124:
      case 125:
      case 126:
      case 127:
      case 128:
      case 129:
      case 132:
      case 182:
      case 188:
        return true;
    }
    return false;
  }

  private boolean significant4Digit(String callback) {
    switch (Integer.parseInt(callback)) {
      case 1262:
      case 1301:
      case 1303:
      case 1330:
      case 1331:
      case 1332:
      case 1333:
      case 1335:
      case 1336:
      case 1337:
      case 1338:
      case 1345:
      case 1350:
      case 1355:
      case 1357:
      case 1365:
      case 1366:
      case 1369:
      case 1372:
      case 1377:
      case 1379:
      case 1381:
      case 1382:
      case 1385:
      case 1388:
      case 1390:
      case 1393:
      case 1396:
      case 1397:
      case 1398:
      case 1399:
        return true;
    }
    return false;
  }

  private boolean significant5Digit(String callback) {
    switch (Integer.parseInt(callback)) {
      case 2120:
        return true;
    }
    return false;
  }

  private boolean significant6Digit(String callback) {
    switch (Integer.parseInt(callback)) {
      case 31120:
      case 32120:
      case 33120:
      case 41120:
      case 42120:
      case 43120:
      case 44120:
      case 51120:
      case 52120:
      case 53120:
      case 54120:
      case 55120:
      case 61120:
      case 62120:
      case 63120:
      case 64120:
        return true;
    }
    return false;
  }

  private boolean significant8Digit(String callback) {
    switch (Integer.parseInt(callback.substring(0, 2))) {
      case 15:
      case 16:
      case 18:
        return true;
    }
    return false;
  }

  private boolean significant9Digit(String callback) {
    if (2 == Integer.parseInt(callback.substring(0, 2))) {
      if ('0' == callback.charAt(2) || '1' == callback.charAt(2)) {
        return false;
      }
      return true;
    }
    return false;
  }

  private boolean significant1011Digit(String callback) {
    switch (Integer.parseInt(callback.substring(0, 3))) {
      case 31:
      case 32:
      case 33:
      case 41:
      case 42:
      case 43:
      case 44:
      case 51:
      case 52:
      case 53:
      case 54:
      case 55:
      case 61:
      case 62:
      case 63:
      case 64:
        if ('0' == callback.charAt(3) || '1' == callback.charAt(3)) {
          return false;
        }
        return true;
      case 10:
        if (10 == callback.length()) {
          return false;
        }
        if ('1' == callback.charAt(3)) {
          return false;
        }
      case 11:
      case 16:
      case 17:
      case 18:
      case 19:
        if ('0' == callback.charAt(3)) {
          return false;
        }
        return true;
      case 12:
      case 13:
      case 14:
      case 15:
      case 81:
      case 82:
      case 83:
      case 84:
      case 86:
      case 30:
      case 40:
      case 50:
      case 60:
      case 70:
      case 80:
      case 90:
        return true;
      case 22:
      case 23:
      case 24:
      case 25:
      case 26:
      case 27:
      case 28:
      case 29:
        if (10 != callback.length()) {
          return false;
        }
        return true;
    }
    return false;
  }

  private boolean significant12Digit(String callback) {
    switch (Integer.parseInt(callback.substring(0, 3))) {
      case 20:
      case 30:
      case 40:
      case 50:
      case 60:
      case 70:
      case 80:
      case 90:
        return true;
    }
    return false;
  }

  private boolean significant14Digit(String callback) {
    switch (Integer.parseInt(callback.substring(0, 3))) {
      case 9:
        return true;
    }
    return false;
  }
}
