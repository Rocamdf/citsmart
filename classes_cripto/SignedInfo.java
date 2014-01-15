package br.com.centralit.citcorpore.util;

import java.io.Serializable;

public class SignedInfo implements Serializable {
    private String strCripto;
    public String getStrCripto() {
        return strCripto;
    }
    public void setStrCripto(String strCripto) {
        this.strCripto = strCripto;
    }
    public String getStrSigned() {
        return strSigned;
    }
    public void setStrSigned(String strSigned) {
        this.strSigned = strSigned;
    }
    private String strSigned;
}
