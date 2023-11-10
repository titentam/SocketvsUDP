package Common;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

public class Service {
    public static class StringHandler{
        public static String ReverseString(String input){
            StringBuilder reversed = new StringBuilder();

            for (int i = input.length() - 1; i >= 0; i--) {
                reversed.append(input.charAt(i));
            }
            return reversed.toString();
        }

        public static String ToUpperCase(String str){
            if (str == null) {
                return null;
            }

            char[] chars = str.toCharArray();
            StringBuilder lowercase = new StringBuilder();

            for (char ch : chars) {
                if (ch >= 'a' && ch <= 'z') {
                    lowercase.append((char) (ch - 32));
                } else {
                    lowercase.append(ch);
                }
            }

            return lowercase.toString();
        }

        public static String ToLowerCase(String str){
            if (str == null) {
                return null;
            }

            char[] chars = str.toCharArray();
            StringBuilder lowercase = new StringBuilder();

            for (char ch : chars) {
                if (ch >= 'A' && ch <= 'Z') {
                    lowercase.append((char) (ch + 32));
                } else {
                    lowercase.append(ch);
                }
            }

            return lowercase.toString();
        }

        public static String ThuongHoa(String str){
            char[] chars = str.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                char c = chars[i];
                if (Character.isUpperCase(c)) {
                    chars[i] = Character.toLowerCase(c);
                } else if (Character.isLowerCase(c)) {
                    chars[i] = Character.toUpperCase(c);
                }
            }
            return new String(chars);
        }

        public static int CountWords(String str){
            if (str == null || str.isEmpty()) {
                return 0;
            }
            String[] words = str.trim().split("\\s+");
            return words.length;
        }
        public static int CountVowels(String str) {
            if (str == null || str.isEmpty()) {
                return 0;
            }

            int count = 0;
            str = str.toLowerCase(); // Chuyển đổi chuỗi thành chữ thường để so sánh

            for (int i = 0; i < str.length(); i++) {
                char ch = str.charAt(i);
                if (isVowel(ch)) {
                    count++;
                }
            }

            return count;
        }

        private static boolean isVowel(char ch) {
            return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u';
        }
    }
    public static class Balan{
        private static boolean isOperator(char c) {
            return (c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')');
        }
        // Kiểm tra toán tử 2 ngôi
        private static boolean isBinaryOperator(char c) {
            return (c == '+' || c == '-' || c == '*' || c == '/');
        }

        // Kiểm tra xem dấu trừ tại vị trí i có phải là toán tử 1 ngôi hay không
        private static boolean isUnaryMinus(String infix, int i) {
            if (i == 0 || isBinaryOperator(infix.charAt(i - 1)) || infix.charAt(i - 1) == '(')
                return true;

            return false;
        }

        private static String handleInfix(String infix) {
            String tmp = ""; // trả về chuỗi đã xử lí
            String num = ""; // biến để lưu số
            int i = 0;
            int check = 0;
            while (i < infix.length()) {
                char c = infix.charAt(i);
                // toán tử 1 ngôi chủ yếu là dấu trừ nên xét 2 trường hợp kí tự i có phải dấu trừ hay không
                if (c != '-') {
                    // nếu không phải dấu trừ thì thêm vào tmp
                    if (c == ')') {
                        check--;
                        if(check == 0) tmp += ")";
                    }
                    if (c == '(') {
                        if (check != 0) check++;
                    }
                    tmp += c;
                    i++;


                }
                else {
                    // nếu là dấu trừ thì kiểm tra xem có phải toán tử 1 ngôi hay không
                    if (!isUnaryMinus(infix, i)) { // không phải 1 ngôi thì thêm dấu trừ vào tmp
                        tmp += c;
                        i++;
                    }
                    else {
                        // nếu là 1 ngôi thì thêm dấu trừ vào biến num. Mục đích của num là biến
                        // dấu trừ và số sau nó thành (0 - 1 số)
                        // Ví dụ -1 * (-2) thì num = -1, sau đó biến tmp += (0 - 1)
                        // lưu ý phải có dấu ngoặc giữa 2 bên 0-1 nếu không biểu thức
                        // sẽ thành 0 - 1 * (-2) sẽ sai
                        // tương tự -2 cũng phải thành (0-2) vì là 1 ngôi



                        // trở lại code
                        // nếu là 1 ngôi thì thêm dấu trừ vào num
                        num += c;
                        // tăng i lên 1 để xem ở sau dấu trừ là số hay toán tử
                        i++;
                        // xét trường họp sau nó là toán tử '(' vì
                        // chỉ có trường hợp này là k cần dấu ngoặc
                        // ví dụ -(3 + 5 * (-4 + 3)) sẽ thành 0-(3 + 5 * (-4 + 3))

                        if (infix.charAt(i) == '(') {
                            check++;
                            num += infix.charAt(i);
                            tmp += "(0" + num;
                            num = "";
                            i++;
                        }
                        // nếu sau dấu trừ là số thì chạy vòng lặp xem thử
                        // các kí tự sau tiếp nó là số hay toán tử
                        // nếu là số thì thêm vào num
                        // ví dụ -100 thì phải chạy vòng lặp sao cho num = -100 lun
                        else {
                            while (i < infix.length() && !isOperator(infix.charAt(i))) {
                                num += infix.charAt(i);
                                i++;
                            }
                            tmp += "(0" + num + ")";
                            num = "";
                        }
                    }

                }
            }
            return tmp;
        }
        public static int evaluate(String expression) throws Exception {
            try{
                expression = expression.replaceAll("\\s", "");
                expression = handleInfix(expression);
                char[] tokens = expression.toCharArray();
                Stack<Integer> values = new Stack<Integer>();
                Stack<Character> ops = new Stack<Character>();
                for (int i = 0; i < tokens.length; i++) {
                    if(tokens[i] == '–') tokens[i] ='-'; // :)))
                    if (tokens[i] == ' ')
                        continue;
                    if (tokens[i] >= '0' && tokens[i] <= '9') {
                        StringBuffer sbuf = new StringBuffer();
                        while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9')
                            sbuf.append(tokens[i++]);
                        i--;
                        values.push(Integer.parseInt(sbuf.toString()));
                    }
                    else if (tokens[i] == '(')
                        ops.push(tokens[i]);
                    else if (tokens[i] == ')') {
                        while (ops.peek() != '(')
                            values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                        ops.pop();
                    }
                    else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/') {
                        if(ops.isEmpty()){
                            ops.push(tokens[i]);
                            continue;
                        }
                        while (!ops.empty() && hasPrecedence(tokens[i], ops.peek()))
                            values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                        ops.push(tokens[i]);
                    }
                    else{
                        throw new Exception("Biểu thức không hợp lệ!");
                    }
                }
                while (!ops.empty())
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                return values.pop();
            }
            catch (EmptyStackException e){
                throw new Exception("Biểu thức không hợp lệ!");
            }

        }

        private static boolean hasPrecedence(char op1, char op2) {
            if (op2 == '(' || op2 == ')')
                return false;
            return (op1 != '*' && op1 != '/') || (op2 != '+' && op2 != '-');
        }
        private static int applyOp(char op, int b, int a) {
            switch (op) {
                case '+':
                    return a + b;
                case '-':
                    return a - b;
                case '*':
                    return a * b;
                case '/':
                    if (b == 0)
                        throw new UnsupportedOperationException("Cannot divide by zero");
                    return a / b;
            }
            return 0;
        }
    }
    public static class DB{
        public static ArrayList<String> GetDataFromDb() throws SQLException {
            // connect db sql server
            ArrayList<String> resFromDb = new ArrayList<String>();
            SQLServerDataSource ds  = new SQLServerDataSource() ;
            ds.setUser("sa");
            ds.setPassword("1");
            ds.setEncrypt("true");
            ds.setTrustServerCertificate(true);
            ds.setServerName("DESKTOP-SC8EE29\\SQLEXPRESS");
            ds.setPortNumber(1433);
            ds.setDatabaseName("ThiThuCK_Java");

            Connection connection = ds.getConnection();
            System.out.println("Connect success!");
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM HOCVIEN");

            while (rs.next()) {
                String tmp="";
                tmp += rs.getString("MaHocVien") +" ";
                tmp += rs.getString("TenHocVien");
                resFromDb.add(tmp);
            }
            connection.close();

            return resFromDb;
        }
    }
}
