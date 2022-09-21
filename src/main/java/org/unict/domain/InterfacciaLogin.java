package org.unict.domain;

import java.util.Map;

public interface InterfacciaLogin {

    void setMetodoAccesso(InterfacciaLogin login);

    void login(String identificatore)throws Exception;

}
