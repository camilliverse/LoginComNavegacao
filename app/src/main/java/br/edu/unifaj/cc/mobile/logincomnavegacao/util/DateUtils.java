package br.edu.unifaj.cc.mobile.logincomnavegacao.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    
    private static final String FORMATO_DATA = "dd/MM/yyyy";
    
    public static String getDataAtual() {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_DATA, new Locale("pt", "BR"));
        return sdf.format(new Date());
    }
    
    public static String formatarData(Date date) {
        if (date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_DATA, new Locale("pt", "BR"));
        return sdf.format(date);
    }
    
    public static Date parseData(String dataStr) {
        if (dataStr == null || dataStr.isEmpty()) return null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_DATA, new Locale("pt", "BR"));
            return sdf.parse(dataStr);
        } catch (ParseException e) {
            return null;
        }
    }
    
    public static String somarDias(String data, int dias) {
        Date d = parseData(data);
        if (d == null) return "";
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.DAY_OF_MONTH, dias);
        
        return formatarData(cal.getTime());
    }
    
    public static String calcularValidadeBolsa(String dataColeta) {
        return somarDias(dataColeta, 42);
    }
}