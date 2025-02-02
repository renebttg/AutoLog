package com.example.autolog.enums;

public enum TrustedAdminDomains {
    AUTOLOG_MAIN("autolog.dev.com"),
    AUTOLOG_SUPPORT("support.autolog.dev.com"),
    AUTOLOG_TEST("test.autolog.dev.com");

    private final String domain;

    TrustedAdminDomains(String domain) {
        this.domain = domain;
    }

    public String getDomain() {
        return domain;
    }

    public static boolean isTrustedDomain(String email) {
        String emailDomain = email.substring(email.indexOf("@") + 1);
        for (TrustedAdminDomains trustedDomain : TrustedAdminDomains.values()) {
            if (trustedDomain.getDomain().equalsIgnoreCase(emailDomain)) {
                return true;
            }
        }
        return false;
    }
}