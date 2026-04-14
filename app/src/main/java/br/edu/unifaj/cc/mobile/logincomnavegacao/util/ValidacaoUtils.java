package br.edu.unifaj.cc.mobile.logincomnavegacao.util;

import java.util.Arrays;
import java.util.List;

public class ValidacaoUtils {
    
    private static final List<String> TIPOS_SANGUINEOS_VALIDOS = 
        Arrays.asList("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");

    public static boolean validarEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(regex);
    }
    
    public static boolean validarCpf(String cpf) {
        if (cpf == null || cpf.isEmpty()) {
            return false;
        }
        String cpfNumerico = cpf.replaceAll("[^0-9]", "");
        return cpfNumerico.length() == 11;
    }
    
    public static boolean validarTipoSanguineo(String tipo) {
        if (tipo == null || tipo.isEmpty()) {
            return false;
        }
        return TIPOS_SANGUINEOS_VALIDOS.contains(tipo.toUpperCase());
    }
    
    public static boolean validarSenha(String senha) {
        if (senha == null || senha.isEmpty()) {
            return false;
        }
        return senha.length() >= 6;
    }
    
    public static boolean validarVolumeDoacao(int volumeMl) {
        return volumeMl >= 200 && volumeMl <= 470;
    }
    
    public static boolean validarData(String data) {
        if (data == null || data.isEmpty()) {
            return false;
        }
        String[] parts = data.split("/");
        if (parts.length != 3) {
            return false;
        }
        try {
            int dia = Integer.parseInt(parts[0]);
            int mes = Integer.parseInt(parts[1]);
            int ano = Integer.parseInt(parts[2]);
            return dia >= 1 && dia <= 31 && mes >= 1 && mes <= 12 && ano >= 1900;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static String validarCamposObrigatorios(String... campos) {
        for (int i = 0; i < campos.length; i++) {
            if (campos[i] == null || campos[i].trim().isEmpty()) {
                return "Campo " + (i + 1) + " é obrigatório";
            }
        }
        return null;
    }
}