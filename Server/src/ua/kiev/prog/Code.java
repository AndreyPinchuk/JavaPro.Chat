package ua.kiev.prog;

public class Code {
    private String code;

    public static String toCode(String msg) {

        String s = null;
        String[] s1;
        if (msg.indexOf("@@help") != -1) {
            s = ("\n========================================" +
                    "\nКоманди:" +
                    "\n-@@listUser - список користувачів;  " +
                    "\n-@@to:name user:text - відправити повідомлення певному користувачу;" +
                    "\n-@@whois - хто присутній в чаті " +
                    "\n-@@chatkey: KEY - створення приватної кімнати за допомогою свого унікального ключа" +
                    "\n-@@@letsgo_chat:user - запрошення в вашу чат кімнату певного user" +
                    "\n-@@chatnull - повернення до загального чату" +
                    "\n-@@exit - вихід з програми" +
                    "\n========================================");
        } else if (msg.indexOf("@@listUser") != -1) {
            s = Login.haveLogin();
        } else if (msg.indexOf("@@whois") != -1) {
            s = Login.whois();
        } else if (msg.indexOf("@@exit") != -1) {
            s = Login.goodbye(msg);
        } else if (msg.indexOf("@@chatkey:") != -1) {
            s1 = msg.split(":");
            s = "Чат кімната з ключем" + s1[1] + " створена";
        } else if (msg.indexOf("@@chatnull") != -1) {
            s = "Ви повернулись до основного чату";
        } else {
            s = "Введена неправильна команда";
        }
        return s;
    }
}
