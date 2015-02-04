package print.simulation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 * Class: Validation.java
 * File: src/GolferData/Validation.java
 * Description:This class validates various input values to confirm that they
 * are of proper format.
 * @author BrentYoung
 * Environment: Netbeans 7.3, Mac OSx
 * @see java.util.regex.Matcher
 * @see java.util.regex.Pattern
 * @see javax.swing.JOptionPane
 * @see AddEditFormJDialog
 * @see Golfers
 * @see Golfer
 */
public class Validation {

    /**
     * Method: isPhoneNumber
     * Validates that the passed value is a valid US phone number and returns
     * that value if valid.
     * @param fieldValue String to be validated
     * @return valid phone number
     * @throws NumberFormatException if String is not a valid US phone number
     * @see #isEmail(java.lang.String, boolean) 
     */
    public static String isPhoneNumber(String fieldValue) throws NumberFormatException {
        Pattern pat = Pattern.compile("^([\\(]{1}[0-9]{3}[\\)]{1}[\\.| |\\-]{0,1}|"
                + "^[0-9]{3}[\\.|\\-| ]?)?[0-9]{3}(\\.|\\-| )?[0-9]{4}$");
        Matcher mat = pat.matcher(fieldValue);
        if (mat.matches()) {
            return fieldValue;
        } else {
            throw new NumberFormatException("Invalid Phone Number");
        }
    }
    
    /**
     * Method: isEmail
     * Determines that passed value is a valid email and returns either that value
     * or a blank space if value is not valid.
     * @param fieldValue String to be validated
     * @param req true if email is a required value
     * @return valid email or blank email in the case of invalid input.
     * @see Golfers
     * @see Golfers.#addNewMember() 
     * @see AddEditFormJDialog.#confirmChanges() 
     * @see Golfer.#email
     */
    public static String isEmail(String fieldValue, boolean req) { //throws IllegalArgumentException {
        boolean emailRequired = req;
        Pattern pat = Pattern.compile("^[\\w-_\\.+]*[\\w-_\\.]\\"
                + "@([\\w]+\\.)+[\\w]+[\\w]$");
        Matcher mat = pat.matcher(fieldValue);
        if (mat.matches()) {
            return fieldValue;
        } else if (emailRequired && !mat.matches()) {
            JOptionPane.showMessageDialog(null, "Invalid Email set to blank.", "Add/Edit Member", JOptionPane.WARNING_MESSAGE);
            return " ";
        } else if (fieldValue.equals("")) {
            return " ";
        }
        return " ";
    }
    
    
    /**
     * Method: isDouble
     * Description: Validates the double value that is entered
     * @return boolean user input
     * @see java.util.regex.Pattern
     * @see java.util.regex.Matcher
     */
    public static boolean isDouble(String fieldValue) {
        Pattern pat = Pattern.compile("\\d+(\\.\\d+)?");
        Matcher mat = pat.matcher(fieldValue);
        return mat.matches();
    }
    
    /**
     * Method: isNumber
     * Description: Validates that the value entered is a number void of
     * non numerical or decimal characters.
     * @param fieldValue user input
     * @return boolean true if input is a number
     * @see java.util.regex.Matcher
     */
    public static boolean isNumber(String fieldValue) {
        Pattern pat = Pattern.compile("\\d+");
        Matcher mat = pat.matcher(fieldValue);
        return mat.matches();
    }
    
    /**
     * Method:percentToFloat
     * Description: If the input contains a percent symbol, it will be removed
     * and, given that the remaining String is a double value, it will be returned
     * as a Float.
     * @param fieldValue user input value
     * @return float parsed from user input, or 0.0 if input is not a float
     * @see #isDouble(java.lang.String) 
     */
    public static float percentToFloat(String fieldValue) {
        boolean containsPercent = fieldValue.contains("%");
        if (containsPercent) {
            fieldValue = fieldValue.substring(0, fieldValue.length() - 1);
        }
        if (isDouble(fieldValue)) {
            return Float.parseFloat(fieldValue);
        } else {
            return 0f;
        }
    }
    
    /**
     * Method: toInt
     * Casts any non-integer numerical value to an integer.
     * @param fieldValue input value
     * @return integer representing closest integer value to passed value.
     */
    public static int toInt(String fieldValue) {
        String field = fieldValue;
        if (isNumber(field) && !isDouble(field)) {
            return Integer.parseInt(field);
        } else {
            return (int) Double.parseDouble(field) / 1;
        }
    }
    
    /**
     * Method: currencyToFloat
     * Description: If the input contains a dollar sign, it will be removed and
     * the remaining float will be returned.
     * @param fieldValue user input value
     * @return float parsed from user input, or 0.0 if input is not a float
     * @see #isDouble(java.lang.String) 
     * @see #percentToFloat(java.lang.String) 
     */
    public static float currencyToFloat(String fieldValue) {
        boolean containsDollarSign = fieldValue.contains("$");
        if (containsDollarSign) {
            fieldValue = fieldValue.substring(1);
        }
        boolean containsCommas = fieldValue.contains(",");
        if (containsCommas) {
            int position = fieldValue.indexOf(',');
            fieldValue = fieldValue.substring(0, position) + fieldValue.substring(position + 1);
        }
        
        if (isDouble(fieldValue)) {
            return Float.parseFloat(fieldValue);
        } else {
            return 0f;
        }
    }
    
    /**
     * Takes any non numerical characters out of the given String.
     * @param fieldValue user input value
     * @return remaining float, or 0.0 if input did not contain a float
     * @see #isDouble(java.lang.String) 
     */
    public static long numOnly(String fieldValue) {
        char[] num = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        String index = "";
        for (int i = 0; i < fieldValue.length(); i++) {
            char c = fieldValue.charAt(i);
            for (int j = 0; j < num.length; j++) {
                if (c == num[j]) {
                    index += c;
                    break;
                }
            }
        }
        return Long.parseLong(index);
    }
    
    /**
     * Removes any numerical values from the input String.
     * @param fieldValue user input String
     * @return String modified to contain no numerical values
     * @see #toFloat(java.lang.String) 
     * @see #isNumber(java.lang.String) 
     */
    public static String noNum(String fieldValue) {
        char[] num = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        String index = fieldValue;
        for (int i = 0; i < index.length(); i++) {
            char c = index.charAt(i);
            for (int j = 0; j < num.length; j++) {
                if (c == num[j]) {
                    index.replace(c, ' ');
                } 
            }
        }
        return index;
    }
    
//    /**
//     * Method: isUniqueLevel
//     * Validates that the a members team rank is a unique value compared to other
//     * members in the passed array list.
//     * @param fieldValue String to be validated
//     * @param golf array list of Golfer objects
//     * @return unique integer, fieldValue if it is unique
//     * @see #findMax(java.util.ArrayList) 
//     * precondition: Objects in Array List contain a level or rank field comprised of
//     * integer values.
//     */
//    public static boolean isUniqueLevel(int fieldValue, ArrayList golf, Golfer joe, boolean isEdit) {
//        boolean isEditForm = isEdit;
//        int rank = fieldValue;
//       // int maxRank = findMax(golf);
//        if (rank < 1) {
//            throw new NumberFormatException("Negative Rank");
//        }
//        Iterator<Golfer> golfIt = golf.iterator();
//        Golfer g = new Golfer();
//        while (golfIt.hasNext()) {
//            g = golfIt.next();
//            if (g.getLevel() == rank && rank != joe.getLevel()) {
//                return false;
//            }
//        }
//        return true;
//    }
}
