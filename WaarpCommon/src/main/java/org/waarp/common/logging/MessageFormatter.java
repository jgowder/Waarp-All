/*
 * This file is part of Waarp Project (named also Waarp or GG).
 *
 *  Copyright (c) 2019, Waarp SAS, and individual contributors by the @author
 *  tags. See the COPYRIGHT.txt in the distribution for a full listing of
 * individual contributors.
 *
 *  All Waarp Project is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * Waarp is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with
 * Waarp . If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * Copyright 2013 The Netty Project
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
/**
 * Copyright (c) 2004-2011 QOS.ch
 * All rights reserved.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS  IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.waarp.common.logging;

import org.waarp.common.utility.ParametersChecker;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

// contributors: lizongbo: proposed special treatment of array parameter values
// Joern Huxhorn: pointed out double[] omission, suggested deep array copy

/**
 * Formats messages according to very simple substitution rules. Substitutions
 * can be made 1, 2 or more
 * arguments.
 * <p/>
 * <p/>
 * For example,
 * <p/>
 *
 * <pre>
 * MessageFormatter.format(&quot;Hi {}.&quot;, &quot;there&quot;)
 * </pre>
 * <p/>
 * will return the string "Hi there.".
 * <p/>
 * The {} pair is called the <em>formatting anchor</em>. It serves to designate
 * the location where arguments
 * need to be substituted within the message pattern.
 * <p/>
 * In case your message contains the '{' or the '}' character, you do not have
 * to do anything special unless
 * the '}' character immediately follows '{'. For example,
 * <p/>
 *
 * <pre>
 * MessageFormatter.format(&quot;Set {1,2,3} is not equal to {}.&quot;, &quot;1,2&quot;);
 * </pre>
 * <p/>
 * will return the string "Set {1,2,3} is not equal to 1,2.".
 * <p/>
 * <p/>
 * If for whatever reason you need to place the string "{}" in the message
 * without its <em>formatting
 * anchor</em> meaning, then you need to escape the '{' character with '\',
 * that
 * is the backslash character.
 * Only the '{' character should be escaped. There is no need to escape the '}'
 * character. For example,
 * <p/>
 *
 * <pre>
 * MessageFormatter.format(&quot;Set \\{} is not equal to {}.&quot;, &quot;1,2&quot;);
 * </pre>
 * <p/>
 * will return the string "Set {} is not equal to 1,2.".
 * <p/>
 * <p/>
 * The escaping behavior just described can be overridden by escaping the
 * escape
 * character '\'. Calling
 * <p/>
 *
 * <pre>
 * MessageFormatter.format(&quot;File name is C:\\\\{}.&quot;, &quot;file.zip&quot;);
 * </pre>
 * <p/>
 * will return the string "File name is C:\file.zip".
 * <p/>
 * <p/>
 * The formatting conventions are different than those of {@link MessageFormat}
 * which ships with the Java
 * platform. This is justified by the fact that SLF4J's implementation is 10
 * times faster than that of
 * {@link MessageFormat}. This local performance difference is both measurable
 * and significant in the larger
 * context of the complete logging processing chain.
 * <p/>
 * <p/>
 * See also {@link #format(String, Object)}, {@link #format(String, Object,
 * Object)} and
 * {@link #arrayFormat(String, Object[])} methods for more details.
 */
final class MessageFormatter {

  static final char DELIM_START = '{';
  static final char DELIM_STOP = '}';
  static final String DELIM_STR = "{}";
  private static final char ESCAPE_CHAR = '\\';

  /**
   * Performs single argument substitution for the 'messagePattern' passed as
   * parameter.
   * <p/>
   * For example,
   * <p/>
   *
   * <pre>
   * MessageFormatter.format(&quot;Hi {}.&quot;, &quot;there&quot;);
   * </pre>
   * <p/>
   * will return the string "Hi there.".
   * <p/>
   *
   * @param messagePattern The message pattern which will be parsed
   *     and
   *     formatted
   * @param arg The argument to be substituted in place of the
   *     formatting
   *     anchor
   *
   * @return The formatted message
   */
  static FormattingTuple format(final String messagePattern, final Object arg) {
    return arrayFormat(messagePattern, new Object[] { arg });
  }

  /**
   * Performs a two argument substitution for the 'messagePattern' passed as
   * parameter.
   * <p/>
   * For example,
   * <p/>
   *
   * <pre>
   * MessageFormatter.format(&quot;Hi {}. My name is {}.&quot;, &quot;Alice&quot;, &quot;Bob&quot;);
   * </pre>
   * <p/>
   * will return the string "Hi Alice. My name is Bob.".
   *
   * @param messagePattern The message pattern which will be parsed
   *     and
   *     formatted
   * @param argA The argument to be substituted in place of the first
   *     formatting anchor
   * @param argB The argument to be substituted in place of the second
   *     formatting anchor
   *
   * @return The formatted message
   */
  static FormattingTuple format(final String messagePattern, final Object argA,
                                final Object argB) {
    return arrayFormat(messagePattern, new Object[] { argA, argB });
  }

  static Throwable getThrowableCandidate(final Object[] argArray) {
    if (argArray == null || argArray.length == 0) {
      return null;
    }

    final Object lastEntry = argArray[argArray.length - 1];
    if (lastEntry instanceof Throwable) {
      return (Throwable) lastEntry;
    }
    return null;
  }

  /**
   * Same principle as the {@link #format(String, Object)} and {@link
   * #format(String, Object, Object)} methods
   * except that any number of arguments can be passed in an array.
   *
   * @param messagePattern The message pattern which will be parsed
   *     and
   *     formatted
   * @param argArray An array of arguments to be substituted in place
   *     of
   *     formatting anchors
   *
   * @return The formatted message
   */
  static FormattingTuple arrayFormat(final String messagePattern,
                                     final Object[] argArray) {

    final Throwable throwableCandidate = getThrowableCandidate(argArray);

    if (messagePattern == null) {
      return new FormattingTuple(null, argArray, throwableCandidate);
    }

    if (argArray == null) {
      return new FormattingTuple(messagePattern);
    }

    int i = 0;
    int j;
    final StringBuilder sbuild =
        new StringBuilder(messagePattern.length() + 50);

    int l;
    for (l = 0; l < argArray.length; l++) {

      j = messagePattern.indexOf(DELIM_STR, i);

      if (j == -1) {
        // no more variables
        if (i == 0) {
          // this is a simple string
          return new FormattingTuple(messagePattern, argArray,
                                     throwableCandidate);
        } else {
          // add the tail string which contains no variables and return the result.
          sbuild.append(messagePattern.substring(i));
          return new FormattingTuple(sbuild.toString(), argArray,
                                     throwableCandidate);
        }
      } else {
        if (isEscapedDelimeter(messagePattern, j)) {
          if (!isDoubleEscaped(messagePattern, j)) {
            l--;
            // DELIM_START was escaped, thus should not be incremented
            sbuild.append(messagePattern, i, j - 1).append(DELIM_START);
            i = j + 1;
          } else {
            // The escape character preceding the delimiter start is
            // itself escaped: "abc x:\\{}"
            // we have to consume one backward slash
            sbuild.append(messagePattern, i, j - 1);
            deeplyAppendParameter(sbuild, argArray[l],
                                  new HashMap<Object[], Void>());
            i = j + 2;
          }
        } else {
          // normal case
          sbuild.append(messagePattern, i, j);
          deeplyAppendParameter(sbuild, argArray[l],
                                new HashMap<Object[], Void>());
          i = j + 2;
        }
      }
    }
    // append the characters following the last {} pair.
    sbuild.append(messagePattern.substring(i));
    if (l < argArray.length - 1) {
      return new FormattingTuple(sbuild.toString(), argArray,
                                 throwableCandidate);
    } else {
      return new FormattingTuple(sbuild.toString(), argArray, null);
    }
  }

  static boolean isEscapedDelimeter(final String messagePattern,
                                    final int delimeterStartIndex) {
    ParametersChecker.checkParameterNullOnly("Must not be null",
                                             messagePattern);
    if (delimeterStartIndex == 0) {
      return false;
    }
    return messagePattern.charAt(delimeterStartIndex - 1) == ESCAPE_CHAR;
  }

  static boolean isDoubleEscaped(final String messagePattern,
                                 final int delimeterStartIndex) {
    ParametersChecker.checkParameterNullOnly("Must not be null",
                                             messagePattern);
    return delimeterStartIndex >= 2 &&
           delimeterStartIndex - 2 > messagePattern.length() &&
           messagePattern.charAt(delimeterStartIndex - 2) == ESCAPE_CHAR;
  }

  // special treatment of array values was suggested by 'lizongbo'
  static void deeplyAppendParameter(final StringBuilder sbuild, final Object o,
                                    final Map<Object[], Void> seenMap) {
    if (o == null) {
      sbuild.append("null");
      return;
    }
    if (!o.getClass().isArray()) {
      safeObjectAppend(sbuild, o);
    } else {
      // check for primitive array types because they
      // unfortunately cannot be cast to Object[]
      if (o instanceof boolean[]) {
        booleanArrayAppend(sbuild, (boolean[]) o);
      } else if (o instanceof byte[]) {
        byteArrayAppend(sbuild, (byte[]) o);
      } else if (o instanceof char[]) {
        charArrayAppend(sbuild, (char[]) o);
      } else if (o instanceof short[]) {
        shortArrayAppend(sbuild, (short[]) o);
      } else if (o instanceof int[]) {
        intArrayAppend(sbuild, (int[]) o);
      } else if (o instanceof long[]) {
        longArrayAppend(sbuild, (long[]) o);
      } else if (o instanceof float[]) {
        floatArrayAppend(sbuild, (float[]) o);
      } else if (o instanceof double[]) {
        doubleArrayAppend(sbuild, (double[]) o);
      } else {
        objectArrayAppend(sbuild, (Object[]) o, seenMap);
      }
    }
  }

  private static void safeObjectAppend(final StringBuilder sbuild,
                                       final Object o) {
    try {
      final String oAsString = o.toString();
      sbuild.append(oAsString);
    } catch (final Exception t) {
      SysErrLogger.FAKE_LOGGER.ignoreLog(t);
      SysErrLogger.FAKE_LOGGER.syserr(
          "SLF4J: Failed toString() invocation on an object of type [" +
          o.getClass().getName() + ']' + t.getMessage());
      sbuild.append("[FAILED toString()]");
    }
  }

  private static void objectArrayAppend(final StringBuilder sbuild,
                                        final Object[] a,
                                        final Map<Object[], Void> seenMap) {
    sbuild.append('[');
    if (!seenMap.containsKey(a)) {
      seenMap.put(a, null);
      final int len = a.length;
      for (int i = 0; i < len; i++) {
        deeplyAppendParameter(sbuild, a[i], seenMap);
        if (i != len - 1) {
          sbuild.append(", ");
        }
      }
      // allow repeats in siblings
      seenMap.remove(a);
    } else {
      sbuild.append("...");
    }
    sbuild.append(']');
  }

  private static void booleanArrayAppend(final StringBuilder sbuild,
                                         final boolean[] a) {
    sbuild.append('[');
    final int len = a.length;
    for (int i = 0; i < len; i++) {
      sbuild.append(a[i]);
      if (i != len - 1) {
        sbuild.append(", ");
      }
    }
    sbuild.append(']');
  }

  private static void byteArrayAppend(final StringBuilder sbuild,
                                      final byte[] a) {
    sbuild.append('[');
    final int len = a.length;
    for (int i = 0; i < len; i++) {
      sbuild.append(a[i]);
      if (i != len - 1) {
        sbuild.append(", ");
      }
    }
    sbuild.append(']');
  }

  private static void charArrayAppend(final StringBuilder sbuild,
                                      final char[] a) {
    sbuild.append('[');
    final int len = a.length;
    for (int i = 0; i < len; i++) {
      sbuild.append(a[i]);
      if (i != len - 1) {
        sbuild.append(", ");
      }
    }
    sbuild.append(']');
  }

  private static void shortArrayAppend(final StringBuilder sbuild,
                                       final short[] a) {
    sbuild.append('[');
    final int len = a.length;
    for (int i = 0; i < len; i++) {
      sbuild.append(a[i]);
      if (i != len - 1) {
        sbuild.append(", ");
      }
    }
    sbuild.append(']');
  }

  private static void intArrayAppend(final StringBuilder sbuild,
                                     final int[] a) {
    sbuild.append('[');
    final int len = a.length;
    for (int i = 0; i < len; i++) {
      sbuild.append(a[i]);
      if (i != len - 1) {
        sbuild.append(", ");
      }
    }
    sbuild.append(']');
  }

  private static void longArrayAppend(final StringBuilder sbuild,
                                      final long[] a) {
    sbuild.append('[');
    final int len = a.length;
    for (int i = 0; i < len; i++) {
      sbuild.append(a[i]);
      if (i != len - 1) {
        sbuild.append(", ");
      }
    }
    sbuild.append(']');
  }

  private static void floatArrayAppend(final StringBuilder sbuild,
                                       final float[] a) {
    sbuild.append('[');
    final int len = a.length;
    for (int i = 0; i < len; i++) {
      sbuild.append(a[i]);
      if (i != len - 1) {
        sbuild.append(", ");
      }
    }
    sbuild.append(']');
  }

  private static void doubleArrayAppend(final StringBuilder sbuild,
                                        final double[] a) {
    sbuild.append('[');
    final int len = a.length;
    for (int i = 0; i < len; i++) {
      sbuild.append(a[i]);
      if (i != len - 1) {
        sbuild.append(", ");
      }
    }
    sbuild.append(']');
  }

  private MessageFormatter() {
  }
}
