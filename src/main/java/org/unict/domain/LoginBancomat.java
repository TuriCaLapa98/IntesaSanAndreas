package org.unict.domain;

public class LoginBancomat implements InterfacciaLogin{
    private InterfacciaLogin login;
    @Override
    public void setMetodoAccesso(InterfacciaLogin login) {
        this.login=login;
    }

    @Override
    public void login(String identificatore) throws Exception {
        BancaISA bancaISA = BancaISA.getInstance();

        try {
            bancaISA.menuBancomat();
        }catch (Exception ignored) {
        }
    }

}
