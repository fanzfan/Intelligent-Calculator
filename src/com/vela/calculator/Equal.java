package com.vela.calculator;

public class Equal {

    // "(123)" -> "123"
    private static StringBuffer deleteBracket(StringBuffer temp) {
        if (temp.lastIndexOf("(") == -1) return temp;
        return new StringBuffer(temp.substring(temp.lastIndexOf("(") + 1, temp.indexOf(")")));
    }

    // Get result and return
    protected static double calculateResult(StringBuffer[] segments) throws InfinityException {

        // split the segment and stored in segmentOfInput
        StringBuffer[] segmentOfInput = segments;

        // the temporary calculating result will be stored in tempCell
        // and will replace a element in segmentOfInput
        double tempCell;

        // Processing the Bracket
        for (int i = 0; i < segmentOfInput.length; i++) {
            if (segmentOfInput[i].toString().equals("")) continue;
            if (segmentOfInput[i].toString().lastIndexOf("(") != -1) {
                String tempString = segmentOfInput[i].substring(segmentOfInput[i].indexOf("(") + 1, segmentOfInput[i].lastIndexOf(")"));
                tempString = String.valueOf(new Equal().elicitBracket(tempString));
                segmentOfInput[i] = segmentOfInput[i].replace(segmentOfInput[i].indexOf("(") + 1, segmentOfInput[i].lastIndexOf(")"), tempString);
                segmentOfInput = Equal.compactStrings(segmentOfInput);
                //i = 0;
            }
        }


        // calculate the top priority inputs
        for (int i = 0; i < segmentOfInput.length; i++) {
            if (segmentOfInput[i].toString().equals("")) continue;
            tempCell = 0;
            // replace the consistent number pi
            if (segmentOfInput[i].toString().lastIndexOf("π") != -1) {
                tempCell += Math.PI;
                segmentOfInput[i].replace(segmentOfInput[i].toString().indexOf("π"), segmentOfInput[i].toString().indexOf("π") + 1, String.valueOf(tempCell));
                i = 0;
            }
            // replace the consistent number E
            if (segmentOfInput[i].toString().lastIndexOf("e") != -1 && segmentOfInput[i].toString().lastIndexOf("exp") == -1) {
                tempCell += Math.E;
                segmentOfInput[i].replace(segmentOfInput[i].toString().indexOf("e"), segmentOfInput[i].toString().indexOf("e") + 1, String.valueOf(tempCell));
                i = 0;
            }
            // calculate the ln()
            if (segmentOfInput[i].toString().lastIndexOf("ln") != -1) {
                String tempString = segmentOfInput[i].substring(segmentOfInput[i].indexOf("(") + 1, segmentOfInput[i].indexOf(")"));
                tempCell += Math.log(Double.parseDouble(tempString));
                segmentOfInput[i] = new StringBuffer(String.valueOf(tempCell));
                segmentOfInput = Equal.compactStrings(segmentOfInput);
                i = 0;
            }
            // calculate the log()
            if (segmentOfInput[i].toString().lastIndexOf("log") != -1) {
                String tempString = segmentOfInput[i].substring(segmentOfInput[i].indexOf("(") + 1, segmentOfInput[i].indexOf(")"));
                tempCell += Math.log10(Double.parseDouble(tempString));
                segmentOfInput[i] = new StringBuffer(String.valueOf(tempCell));
                segmentOfInput = Equal.compactStrings(segmentOfInput);
                i = 0;
            }
            // calculate the exp()
            if (segmentOfInput[i].toString().lastIndexOf("exp") != -1) {
                String tempString = segmentOfInput[i].substring(segmentOfInput[i].indexOf("(") + 1, segmentOfInput[i].indexOf(")"));
                tempCell += Math.exp(Double.parseDouble(tempString));
                segmentOfInput[i] = new StringBuffer(String.valueOf(tempCell));
                segmentOfInput = Equal.compactStrings(segmentOfInput);
                i = 0;
            }
            // calculate the sqrt()
            if (segmentOfInput[i].toString().lastIndexOf("sqrt") != -1) {
                String tempString = segmentOfInput[i].substring(segmentOfInput[i].indexOf("(") + 1, segmentOfInput[i].indexOf(")"));
                tempCell += Math.sqrt(Double.parseDouble(tempString));
                segmentOfInput[i] = new StringBuffer(String.valueOf(tempCell));
                segmentOfInput = Equal.compactStrings(segmentOfInput);
                i = 0;
            }
            // calculate the arcsin()
            if (segmentOfInput[i].toString().lastIndexOf("arcsin") != -1) {
                String tempString = segmentOfInput[i].substring(segmentOfInput[i].indexOf("(") + 1, segmentOfInput[i].indexOf(")"));
                tempCell += Math.asin(Double.parseDouble(tempString));
                segmentOfInput[i] = new StringBuffer(String.valueOf(tempCell));
                segmentOfInput = Equal.compactStrings(segmentOfInput);
                i = 0;
            }
            // calculate the arctan()
            if (segmentOfInput[i].toString().lastIndexOf("arctan") != -1) {
                String tempString = segmentOfInput[i].substring(segmentOfInput[i].indexOf("(") + 1, segmentOfInput[i].indexOf(")"));
                tempCell += Math.atan(Double.parseDouble(tempString));
                segmentOfInput[i] = new StringBuffer(String.valueOf(tempCell));
                segmentOfInput = Equal.compactStrings(segmentOfInput);
                i = 0;
            }
            // calculate the sin()
            if (segmentOfInput[i].toString().lastIndexOf("sin") != -1) {
                String tempString = segmentOfInput[i].substring(segmentOfInput[i].indexOf("(") + 1, segmentOfInput[i].indexOf(")"));
                tempCell += Math.sin(Double.parseDouble(tempString));
                segmentOfInput[i] = new StringBuffer(String.valueOf(tempCell));
                segmentOfInput = Equal.compactStrings(segmentOfInput);
                i = 0;
            }
            // calculate the cos()
            if (segmentOfInput[i].toString().lastIndexOf("cos") != -1) {
                String tempString = segmentOfInput[i].substring(segmentOfInput[i].indexOf("(") + 1, segmentOfInput[i].indexOf(")"));
                tempCell += Math.cos(Double.parseDouble(tempString));
                segmentOfInput[i] = new StringBuffer(String.valueOf(tempCell));
                segmentOfInput = Equal.compactStrings(segmentOfInput);
                i = 0;
            }
            // calculate the tan()
            if (segmentOfInput[i].toString().lastIndexOf("tan") != -1) {
                String tempString = segmentOfInput[i].substring(segmentOfInput[i].indexOf("(") + 1, segmentOfInput[i].indexOf(")"));
                tempCell += Math.tan(Double.parseDouble(tempString));
                segmentOfInput[i] = new StringBuffer(String.valueOf(tempCell));
                segmentOfInput = Equal.compactStrings(segmentOfInput);
                i = 0;
            }
            // calculate the factorial
            if (segmentOfInput[i].toString().lastIndexOf("!") != -1) {
                String tempString = segmentOfInput[i].substring(0, segmentOfInput[i].indexOf("!"));
                tempCell += new Equal().calculateFact((int) Double.parseDouble(Equal.deleteBracket(new StringBuffer(tempString)).toString()));
                segmentOfInput[i] = new StringBuffer(String.valueOf(tempCell));
                segmentOfInput = Equal.compactStrings(segmentOfInput);
                i = 0;
            }


        }

        // calculate the power
        // We should notice that the "^" operator combining from right to left
        for (int i = segmentOfInput.length - 1; i > 0; i--) {
            if (segmentOfInput[i].toString().equals("")) continue;
            tempCell = 0;
            // calculate the power
            if (segmentOfInput[i].toString().equals("^")) {
                tempCell += Math.pow(Double.parseDouble(Equal.deleteBracket(segmentOfInput[i - 1]).toString()),
                        Double.parseDouble(Equal.deleteBracket(segmentOfInput[i + 1]).toString()));
                segmentOfInput[i - 1] = new StringBuffer(String.valueOf(tempCell));
                segmentOfInput[i] = segmentOfInput[++i] = new StringBuffer();
                segmentOfInput = Equal.compactStrings(segmentOfInput);
                i = segmentOfInput.length - 1;
            }

        }

        // calculate the multiplying and dividing
        for (int i = 0; i < segmentOfInput.length; i++) {
            if (segmentOfInput[i].toString().equals("")) continue;
            tempCell = 0;
            // calculate the multiplying
            if (segmentOfInput[i].toString().equals("×")) {
                tempCell += Double.parseDouble(Equal.deleteBracket(segmentOfInput[i - 1]).toString()) *
                        Double.parseDouble(Equal.deleteBracket(segmentOfInput[i + 1]).toString());
                segmentOfInput[i - 1] = new StringBuffer(String.valueOf(tempCell));
                segmentOfInput[i] = segmentOfInput[++i] = new StringBuffer();
                segmentOfInput = Equal.compactStrings(segmentOfInput);
                i = 0;
            }
            // calculate the dividing
            if (segmentOfInput[i].toString().equals("÷")) {
                tempCell += Double.parseDouble(Equal.deleteBracket(segmentOfInput[i - 1]).toString()) /
                        (Double.parseDouble(Equal.deleteBracket(segmentOfInput[i + 1]).toString()) + 0.0);
                segmentOfInput[i - 1] = new StringBuffer(String.valueOf(tempCell));
                segmentOfInput[i] = segmentOfInput[++i] = new StringBuffer();
                segmentOfInput = Equal.compactStrings(segmentOfInput);
                i = 0;
            }
        }

        // calculate the adding and subtracting
        for (int i = 0; i < segmentOfInput.length; i++) {
            if (segmentOfInput[i].toString().equals("")) continue;
            tempCell = 0;
            // calculate the adding
            if (segmentOfInput[i].toString().equals("+")) {
                tempCell += Double.parseDouble(Equal.deleteBracket(segmentOfInput[i - 1]).toString()) +
                        Double.parseDouble(Equal.deleteBracket(segmentOfInput[i + 1]).toString());
                segmentOfInput[i + 1] = new StringBuffer(String.valueOf(tempCell));
                segmentOfInput[i - 1] = segmentOfInput[i] = new StringBuffer();
                segmentOfInput = Equal.compactStrings(segmentOfInput);
                i = 0;
            }
            // calculate the subtracting
            if (segmentOfInput[i].toString().equals("-")) {
                tempCell += Double.parseDouble(Equal.deleteBracket(segmentOfInput[i - 1]).toString()) -
                        Double.parseDouble(Equal.deleteBracket(segmentOfInput[i + 1]).toString());
                segmentOfInput[i + 1] = new StringBuffer(String.valueOf(tempCell));
                segmentOfInput[i - 1] = segmentOfInput[i] = new StringBuffer();
                segmentOfInput = Equal.compactStrings(segmentOfInput);
                i = 0;
            }
            // calculate the Mod
            if (segmentOfInput[i].toString().equals("mod")) {
                tempCell += Double.parseDouble(Equal.deleteBracket(segmentOfInput[i - 1]).toString()) %
                        Double.parseDouble(Equal.deleteBracket(segmentOfInput[i + 1]).toString());
                segmentOfInput[i + 1] = new StringBuffer(String.valueOf(tempCell));
                segmentOfInput[i - 1] = segmentOfInput[i] = new StringBuffer();
                segmentOfInput = Equal.compactStrings(segmentOfInput);
                i = 0;
            }
        }

        segmentOfInput = compactStrings(segmentOfInput);
        // the result will be stored in resultNumber
        double resultNumber = 0;
        // store the result of calculating
        for (StringBuffer strings : segmentOfInput) {
            if (strings.toString().equals("Infinity")) {
                throw new InfinityException();
            }
            if ((!strings.toString().equals("")) && (Character.isDigit(strings.charAt(0)) || strings.charAt(0) == '-') || strings.indexOf("(") != -1) {
                if (strings.indexOf("(") != -1) {
                    new Equal();
                    resultNumber = Double.parseDouble(deleteBracket(strings).toString());
                } else {
                    resultNumber = Double.parseDouble(strings.toString());
                }
            }
        }
        return resultNumber;
    }

    // "delete" the void element in tempStringBuffers and return
    static StringBuffer[] compactStrings(StringBuffer[] tempStringBuffers) {
        int i;
        int j;
        StringBuffer[] newStringBuffers = new StringBuffer[tempStringBuffers.length];
        for (i = 0; i < newStringBuffers.length; i++) newStringBuffers[i] = new StringBuffer();

        // "delete" the void element in tempStringBuffers and return
        for (j = 0, i = 0; j < newStringBuffers.length; j++) {
            if (!tempStringBuffers[j].toString().equals("")) {
                newStringBuffers[i] = tempStringBuffers[j];
                i++;
            }
        }
        return newStringBuffers;
    }

    // Calculate the fact
    private int calculateFact(int n) {

        if (n <= 1) {
            return 1;
        } else {
            return n * calculateFact(n - 1);
        }
    }

    // Elicit the content of bracket
    // 采用反复递归调用的方法来解决这个问题
    // 这里用到的字符串处理方法和 MainGUI类中的 313行的方法差不多
    Double elicitBracket(String tempString) throws InfinityException {
        char[] input = tempString.toCharArray();
        StringBuffer[] tempStringBuffers = new StringBuffer[100];
        for (int i = 0; i < tempStringBuffers.length; i++) tempStringBuffers[i] = new StringBuffer();
        // Split the input content to a easily processed one
        // after this for sentence, the input content such as :
        // 1.5+2×3+9÷3 will be stored in the given StringBuffer collection like the follows
        // "1.5" "+" "2" "×" "3" "+" "9" "÷" "3"
        int inBracket = 0;
        for (int i = 0, j = 0; i < input.length; i++) {
            if (Character.isDigit(input[i]) || input[i] == '.' || input[i] == '!' || input[i] == 'π' || input[i] == 'e' || inBracket != 0) {
                if (i >= 1 && !Character.isDigit(input[i - 1]) && input[i - 1] != '(' && input[i - 1] != '.' && input[i] != '!' && inBracket == 0)
                    j++;
                tempStringBuffers[j].append(input[i]);
            }
            // store the operator
            else {
                if (i >= 1 && !Character.isLetter(input[i - 1]) && input[i] != ')') j++;
                else if (i >= 1 && (Character.isDigit(input[i - 1]) || input[i - 1] == 'π' || (input[i - 1] == 'e' && input[i] != 'x')) && input[i - 1] != ')' && input[i] != ')')
                    j++;
                tempStringBuffers[j].append(input[i]);
            }
            if (input[i] == '(') inBracket++;
            if (input[i] == ')') inBracket--;
        }
        return calculateResult(tempStringBuffers);
    }

}
