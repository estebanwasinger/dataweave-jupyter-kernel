package com.github.estebanwasinger.dataweave;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.github.estebanwasinger.dataweave.MagicType.CELL;
import static com.github.estebanwasinger.dataweave.MagicType.LINE;

public class KernelUtils {

    public static ExecutionDescriptor parseExecution(String expression) {
        int lastPos = expression.length() - 1;
        Cursor cursor = new Cursor(lastPos);
        byte[] bytes = expression.getBytes(StandardCharsets.UTF_8);

        MagicType magicType= null;
        MagicDescriptor magicDescriptor = null;
        String magicName = null;
        List<String> args = null;
        String body= null;

        if (bytes[cursor.peek()] == '%') {
            if (bytes[cursor.peek() + 1] == '%') {
                magicType = CELL;
                cursor.next();
                cursor.next();
            } else {
                magicType = LINE;
                cursor.next();
            }

            List<String> tokens = getTokens(cursor, bytes);

            magicName = tokens.get(0);
            args = tokens.subList(1, tokens.size());

            StringBuilder stringBuilder = new StringBuilder();
            while (cursor.peek() <= lastPos) {
                stringBuilder.append((char) bytes[cursor.peek()]);
                cursor.next();
            }
            body = stringBuilder.toString();


        } else {
            body = expression;
        }

        if(magicType != null){
            magicDescriptor = new MagicDescriptor(magicType, magicName, args);
        }

        return new ExecutionDescriptor(body,magicDescriptor);


    }

    private static List<String> getTokens(Cursor cursor, byte[] bytes) {
        boolean endOfLine = false;
        List<String> tokens = new ArrayList<>();
        while (!cursor.isEndOfFile() && bytes[cursor.peek()] != '\n' && !endOfLine) {
            StringBuilder stringBuilder = new StringBuilder();

            while (!cursor.isEndOfFile() && bytes[cursor.peek()] != ' ' && bytes[cursor.peek()] != '\n') {
                stringBuilder.append((char) bytes[cursor.peek()]);
                cursor.next();
            }
            if (!cursor.isEndOfFile() && bytes[cursor.peek()] == '\n') {
                endOfLine = true;
            }
            cursor.next();

            tokens.add(stringBuilder.toString());
        }
        return tokens;
    }

    private static class Cursor {
        int cursor = 0;
        private int lastPos;

        public Cursor(int lastPos) {
            this.lastPos = lastPos;
        }

        public int next() {
            cursor++;
            return cursor;
        }

        public boolean isEndOfFile() {
            return cursor > lastPos;
        }

        public int peek() {
            return cursor;
        }
    }
}
