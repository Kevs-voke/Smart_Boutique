package rtx.smart_boutique.Model;


    public class OTPGenerator {
        public static String generateOTP(int length) {
            String digits = "0123456789";
            StringBuilder otp = new StringBuilder();
            for (int i = 0; i < length; i++) {
                otp.append(digits.charAt((int)(Math.random() * 10)));
            }
            return otp.toString();
        }
    }


