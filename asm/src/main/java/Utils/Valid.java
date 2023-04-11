package Utils;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;

public class Valid {
	private String loi;

    public Valid() {
        this.loi = "";
    }

    public String getLoi() {
        return loi;
    }

    public void setLoi(String loi) {
        this.loi = loi;
    }

    public void sai(String input, String msg) throws Exception {
        loi += msg;
        throw new Exception(msg);
    }


    public void isEmpty(String input, String name) throws Exception {
        if (input.trim().isEmpty()) {
            sai(input, name + " cann't be empty");
        }
    }

    public void number(String input, String name) throws Exception {
        try {
            Double.valueOf(input);
        } catch (NumberFormatException num) {
            sai(input, name + " has to be number");
        }
    }

    public void numberInteger(String input, String name) throws Exception {
        number(input, name);
        try {
            Integer.valueOf(input);
        } catch (NumberFormatException num) {
            sai(input, name + " has to be number integer");
        }
    }
    
    public void bigger(String input, String name, Double so) throws Exception{
        if(Double.valueOf(input.trim()) < so){
            sai(input, name + " has to bigger than " + so);
        }
    }
    
    public void lower(String input, String name, Double so) throws Exception{
        if(Double.valueOf(input.trim()) > so){
            sai(input, name + " has to lower than " + so);
        }
    }

    public void compare(String input, String msg, String value) throws Exception {
        if(!input.equals(value)){
            sai(input, msg);
        }
    }
    
    public void reMatch(String input, String name, String reString, String msgMalformed) throws Exception {
        if(!input.matches(reString)){
            sai(input, name + " malformed. " + msgMalformed);
        }
    }

    public void reMatchLowerChar(String input, String name, String reString, String msgMalformed) throws Exception {
        String x = Normalizer.normalize(input.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "").replaceAll("đ", "d");
        if(!x.matches(reString)){
            sai(input, name + " malformed. " + msgMalformed);
        }
    }
    
    public void minLength(String input, String name, int min) throws Exception {
        if(input.length() < min){
            sai(input, name + " has to bigger " + min + " character");
        }
    }
    
    public void maxLength(String input, String name, int max) throws Exception {
        if(input.length() > max){
            sai(input, name + " has to lower " + max + " character");
        }
    }
    
    public void hasCharWriteUppercase(String input, String name, String msgMalformed) throws Exception {
        String x = input;
        if(x.equals(input.toLowerCase())){
            sai(input, name + " malformed. " + msgMalformed);
        }
    }
    
    public void hasCharNumber(String input, String name, String msgMalformed) throws Exception {
        Map<String, String> map = importMapNumber();
        String x = input;
        for (int i = 0; i < x.length(); i++) {
            if (map.get(String.valueOf(x.charAt(i))) != null) {
                return;
            }
        }
        sai(input, name + " malformed. " + msgMalformed);
    }

    public String reDate() {
        return "^([0-9]{4}[-/]?((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))|([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00)[-/]?02[-/]?29)$";
    }

    public String rePhone() {
        return "^0[1-9]\\d{8}$";
    }

    public String reEmail() {
        return "^\\w{3,}+@\\w{3,}+(\\.\\w{2,}+){1,2}$";
    }

    public String reName() {
        return "^[0-9a-zA-Z ]{3,40}$";
    }
    
    public String reUser() {
        return "^[0-9a-zA-Z]{5,40}$";
    }

    public Map<String, String> importMapNumber() {
        Map<String, String> map = new HashMap<>();
        map.put("0", "0");
        map.put("1", "1");
        map.put("2", "2");
        map.put("3", "3");
        map.put("4", "4");
        map.put("5", "5");
        map.put("6", "6");
        map.put("7", "7");
        map.put("8", "8");
        map.put("9", "9");
        return map;
    }
}
