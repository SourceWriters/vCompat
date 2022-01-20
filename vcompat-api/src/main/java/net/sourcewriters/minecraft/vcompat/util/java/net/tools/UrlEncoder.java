package net.sourcewriters.minecraft.vcompat.util.java.net.tools;

import java.io.CharArrayWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.BitSet;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public final class UrlEncoder {

    /*
     * Combined and slightly modified version of java.net.URLEncoder and
     * java.net.URLDecoder
     */

    static BitSet dontNeedEncoding;
    static final int caseDiff = ('a' - 'A');

    static ConcurrentHashMap<String, UrlEncoder> encoders = new ConcurrentHashMap<>();

    static {

        dontNeedEncoding = new BitSet(256);
        int i;
        for (i = 'a'; i <= 'z'; i++) {
            dontNeedEncoding.set(i);
        }
        for (i = 'A'; i <= 'Z'; i++) {
            dontNeedEncoding.set(i);
        }
        for (i = '0'; i <= '9'; i++) {
            dontNeedEncoding.set(i);
        }
        dontNeedEncoding.set(' '); /*
                                    * encoding a space to a + is done in the encode() method
                                    */
        dontNeedEncoding.set('-');
        dontNeedEncoding.set('_');
        dontNeedEncoding.set('.');
        dontNeedEncoding.set('*');
    }

    private final Charset charset;

    public static UrlEncoder get(String encoding) throws UnsupportedEncodingException {
        try {
            return get(Charset.forName(Objects.requireNonNull(encoding, "charsetName cant be null")));
        } catch (IllegalCharsetNameException e) {
            throw new UnsupportedEncodingException(encoding);
        } catch (UnsupportedCharsetException e) {
            throw new UnsupportedEncodingException(encoding);
        }
    }

    public static UrlEncoder get(Charset charset) {
        Objects.requireNonNull(charset, "charset cant be null");
        return encoders.computeIfAbsent(charset.name(), ignore -> new UrlEncoder(charset));
    }

    public static String encode(String content, String encoding) throws UnsupportedEncodingException {
        return get(encoding).encode(content);
    }

    public static String encode(String content, Charset charset) {
        return get(charset).encode(content);
    }

    public static String decode(String content, String encoding) throws UnsupportedEncodingException, IllegalArgumentException {
        return get(encoding).decode(content);
    }

    public static String decode(String content, Charset charset) throws IllegalArgumentException {
        return get(charset).decode(content);
    }

    private UrlEncoder(Charset charset) {
        this.charset = charset;
    }

    public String encode(String content) {
        boolean needToChange = false;
        StringBuffer out = new StringBuffer(content.length());
        CharArrayWriter charArrayWriter = new CharArrayWriter();
        for (int i = 0; i < content.length();) {
            int c = (int) content.charAt(i);
            if (dontNeedEncoding.get(c)) {
                if (c == ' ') {
                    c = '+';
                    needToChange = true;
                }
                out.append((char) c);
                i++;
            } else {
                // convert to external encoding before hex conversion
                do {
                    charArrayWriter.write(c);
                    /*
                     * If this character represents the start of a Unicode surrogate pair, then pass
                     * in two characters. It'content not clear what should be done if a bytes
                     * reserved in the surrogate pairs range occurs outside of a legal surrogate
                     * pair. For now, just treat it as if it were any other character.
                     */
                    if (c >= 0xD800 && c <= 0xDBFF) {
                        if ((i + 1) < content.length()) {
                            int d = (int) content.charAt(i + 1);
                            if (d >= 0xDC00 && d <= 0xDFFF) {
                                charArrayWriter.write(d);
                                i++;
                            }
                        }
                    }
                    i++;
                } while (i < content.length() && !dontNeedEncoding.get((c = (int) content.charAt(i))));
                charArrayWriter.flush();
                String str = new String(charArrayWriter.toCharArray());
                byte[] ba = str.getBytes(charset);
                for (int j = 0; j < ba.length; j++) {
                    out.append('%');
                    char ch = Character.forDigit((ba[j] >> 4) & 0xF, 16);
                    // converting to use uppercase letter as part of
                    // the hex value if ch is a letter.
                    if (Character.isLetter(ch)) {
                        ch -= caseDiff;
                    }
                    out.append(ch);
                    ch = Character.forDigit(ba[j] & 0xF, 16);
                    if (Character.isLetter(ch)) {
                        ch -= caseDiff;
                    }
                    out.append(ch);
                }
                charArrayWriter.reset();
                needToChange = true;
            }
        }
        return (needToChange ? out.toString() : content);
    }

    public String decode(String content) throws IllegalArgumentException {
        boolean needToChange = false;
        int numChars = content.length();
        StringBuffer sb = new StringBuffer(numChars > 500 ? numChars / 2 : numChars);
        int i = 0;
        char c;
        byte[] bytes = null;
        while (i < numChars) {
            c = content.charAt(i);
            switch (c) {
            case '+':
                sb.append(' ');
                i++;
                needToChange = true;
                break;
            case '%':
                /*
                 * Starting with this instance of %, process all consecutive substrings of the
                 * form %xy. Each substring %xy will yield a byte. Convert all consecutive bytes
                 * obtained this way to whatever character(content) they represent in the
                 * provided encoding.
                 */
                try {
                    // (numChars-i)/3 is an upper bound for the number
                    // of remaining bytes
                    if (bytes == null)
                        bytes = new byte[(numChars - i) / 3];
                    int pos = 0;
                    while (((i + 2) < numChars) && (c == '%')) {
                        int v = Integer.parseInt(content.substring(i + 1, i + 3), 16);
                        if (v < 0)
                            throw new IllegalArgumentException("URLDecoder: Illegal hex characters in escape (%) pattern - negative value");
                        bytes[pos++] = (byte) v;
                        i += 3;
                        if (i < numChars)
                            c = content.charAt(i);
                    }
                    // A trailing, incomplete byte encoding such as
                    // "%x" will cause an exception to be thrown
                    if ((i < numChars) && (c == '%'))
                        throw new IllegalArgumentException("URLDecoder: Incomplete trailing escape (%) pattern");

                    sb.append(new String(bytes, 0, pos, charset));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("URLDecoder: Illegal hex characters in escape (%) pattern - " + e.getMessage());
                }
                needToChange = true;
                break;
            default:
                sb.append(c);
                i++;
                break;
            }
        }
        return (needToChange ? sb.toString() : content);

    }

}
