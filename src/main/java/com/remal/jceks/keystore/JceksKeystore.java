package com.remal.jceks.keystore;

import com.remal.jceks.util.Constants;
import picocli.CommandLine;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.Base64;

/**
 * JCEKS keystore manager.
 *
 * <p>Copyright 2021 Arnold Somogyi</p>
 *
 * @author arnold.somogyi@gmail.com
 */
public class JceksKeystore {

    private final PrintStream logWriter;
    private final String keyStoreLocation;
    private final char[] keyStorePassword;
    private KeyStore keyStore;

    /**
     * Initialize the keystore manager.
     *
     * @param logWriter log writer stream
     * @param keyStoreLocation keystore location
     * @param keyStorePassword keystore password
     */
    public JceksKeystore(PrintStream logWriter, String keyStoreLocation, char[] keyStorePassword) {
        this.logWriter = logWriter;
        this.keyStoreLocation = keyStoreLocation;
        this.keyStorePassword = keyStorePassword;

        try (FileInputStream keystoreFileStream = new FileInputStream(keyStoreLocation)) {
            logWriter.printf(Constants.INFO_MESSAGE, "loading keystore from '" + keyStoreLocation + "'...");
            keyStore = KeyStore.getInstance("jceks");
            keyStore.load(keystoreFileStream, keyStorePassword);
        } catch (CertificateException | IOException | NoSuchAlgorithmException | KeyStoreException e) {
            logWriter.printf(Constants.ERROR_MESSAGE, e);
            System.exit(CommandLine.ExitCode.SOFTWARE);
        }
    }

    /**
     * Read the value of a secret key entry.
     *
     * @param entryAlias alias for the keystore entry
     * @param entryPassword password for the keystore entry
     * @return the base64 encoded value of the secret key
     */
    public String getSecretKeyValue(String entryAlias, char[] entryPassword) {
        KeyStore.Entry keyStoreEntry = getEntry(entryAlias, entryPassword);
        logWriter.printf(Constants.INFO_MESSAGE, "reading value from keystore entry, alias: '" + entryAlias + "'...");
        KeyStore.SecretKeyEntry key = (KeyStore.SecretKeyEntry) keyStoreEntry;

        Base64.Encoder base64Encoder = Base64.getEncoder();
        return base64Encoder.encodeToString(key.getSecretKey().getEncoded());
    }

    /**
     * Get a keystore entry.
     *
     * @param entryAlias alias for the keystore entry
     * @param entryPassword password for the keystore entry
     * @return the keystore entry
     */
    public KeyStore.Entry getEntry(String entryAlias, char[] entryPassword) {
        try {
            logWriter.printf(Constants.INFO_MESSAGE, "getting keystore entry, alias: '" + entryAlias + "'...");
            KeyStore.ProtectionParameter protectionParam = new KeyStore.PasswordProtection(entryPassword);
            return keyStore.getEntry(entryAlias, protectionParam);
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableEntryException e) {
            logWriter.printf(Constants.ERROR_MESSAGE, e);
            System.exit(CommandLine.ExitCode.SOFTWARE);
        }

        return null;
    }

    /**
     * Add a new keystore entry to the keystore.
     * If the entry with the given alias name is exist then it will be overwritten.
     *
     * @param entryToAdd keystore entry to add
     * @param entryAlias alias for the keystore entry
     * @param entryPassword password for the keystore entry
     */
    public void addEntry(KeyStore.Entry entryToAdd, String entryAlias, char[] entryPassword) {
        try {
            logWriter.printf(Constants.INFO_MESSAGE, "adding a new keystore entry, alias: '" + entryAlias + "'...");
            keyStore.setEntry(entryAlias, entryToAdd, new KeyStore.PasswordProtection(entryPassword));

            logWriter.printf(Constants.INFO_MESSAGE, "saving the keystore, location: '" + keyStoreLocation + "'...");
            FileOutputStream fos = new FileOutputStream(keyStoreLocation);
            keyStore.store(fos, keyStorePassword);
        } catch (CertificateException | KeyStoreException | NoSuchAlgorithmException | IOException e) {
            logWriter.printf(Constants.ERROR_MESSAGE, e);
            System.exit(CommandLine.ExitCode.SOFTWARE);
        }
    }
}
