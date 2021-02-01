package com.client;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.entities.Passager;
import com.session.IRemote_Passager;

public class Main {
	public static void main(String[] args) {
        IRemote_Passager remote = doLookup();
        
        Passager passager = new Passager();
        passager.setNom("Ouklich");
        passager.setPrenom("Oussama");
        passager.setNumeroP(1);
        
        remote.addPassager(passager);
    }
 
    private static IRemote_Passager doLookup() {
        Context context = null;
        IRemote_Passager bean = null;
        try {
            // 1. Obtaining Context
            context = getInitialContext();
            // 2. Lookup and cast
            bean = (IRemote_Passager) context.lookup(LOOKUP_STRING);
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return bean;
    }
 
    private static final String LOOKUP_STRING = "com.session.ImpPassager/remote";

    private static Context initialContext;
 
    public static Context getInitialContext() throws NamingException {
        if (initialContext == null) {
            // Properties extends HashTable
            Properties prop = new Properties();
            prop.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            prop.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
            prop.put(Context.PROVIDER_URL, "remote://localhost:4447");
            prop.put(Context.SECURITY_PRINCIPAL, "admin");
            prop.put(Context.SECURITY_CREDENTIALS, "admin");
            initialContext = new InitialContext(prop);
        }
        return initialContext;
    }
}
