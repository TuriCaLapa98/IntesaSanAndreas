package org.unict.domain;

public class LoginDipendenti implements InterfacciaLogin{
    InterfacciaLogin login;

    @Override
    public void setMetodoAccesso(InterfacciaLogin login) {
        this.login=login;
    }

    @Override
    public void login(String identificatore) throws Exception {
        if(identificatore.contains("D")) {
            try {
                BancaISA bancaISA = BancaISA.getInstance();
                String notifica = null;
                if(identificatore.equals("DN"))
                    bancaISA.menuDipendenteN();
                else if(identificatore.equals("DT"))
                    bancaISA.menuDipendenteT(notifica);
            } catch (Exception ignored) {
            }
        }else login.login(identificatore);
    }
}
