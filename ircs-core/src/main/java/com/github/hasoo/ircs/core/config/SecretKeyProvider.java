package com.github.hasoo.ircs.core.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import org.springframework.stereotype.Component;

@Component
public class SecretKeyProvider {

  public String getKey() throws KeyStoreException, IOException,
      NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException {
    return new String(getKeyPair().getPublic().getEncoded(), "UTF-8");
  }

  private KeyPair getKeyPair() throws KeyStoreException, IOException, NoSuchAlgorithmException,
      CertificateException, UnrecoverableKeyException {
    FileInputStream is = new FileInputStream("mykeys.jks");

    KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
    keyStore.load(is, "mypass".toCharArray());

    String alias = "mykeys";

    Key key = keyStore.getKey(alias, "mypass".toCharArray());
    if (!(key instanceof PrivateKey)) {
      throw new UnrecoverableKeyException();
    }

    Certificate cert = keyStore.getCertificate(alias);

    PublicKey publicKey = cert.getPublicKey();

    return new KeyPair(publicKey, (PrivateKey) key);
  }

}
