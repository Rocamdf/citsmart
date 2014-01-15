package br.com.centralit.citcorpore.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;

public class CriptoSignedUtil {
    private static final String hexDigits = "0123456789abcdef";

    public static SignedInfo generateStringToSend(String pathJKSPK, String pathJKSSK, String txt) throws Exception {
	File cert = new File(pathJKSPK);
	String alias = "citsmart";
	String pwd = "c3ntr@lit";

	File certSK = new File(pathJKSSK);
	String aliasSK = "citsmartcripto";
	String pwdSK = "c3ntr@lit123";

	PrivateKey privateKey = getPrivateKeyFromFile(cert, alias, pwd);
	PublicKey publicKey = getPublicKeyFromFile(cert, alias, pwd);

	SecretKey secretkey = getSecretKeyFromFile(certSK, aliasSK, pwdSK);

	byte[] txtAssinado = createSignature(privateKey, txt.getBytes());

	byte[] txtCripto = cript(publicKey, secretkey, txt.getBytes());
	String hexStrMsg = byteArrayToHexString(txtCripto);

	SignedInfo signedInfo = new SignedInfo();
	signedInfo.setStrCripto(hexStrMsg);
	signedInfo.setStrSigned(byteArrayToHexString(txtAssinado));
	return signedInfo;
    }
    
    public static String translateStringReceive(String pathJKSPK, String pathJKSSK, String txtCriptoAndSigned, String txtAssinado) throws Exception {
	File cert = new File(pathJKSPK);
	String alias = "citsmart";
	String pwd = "c3ntr@lit";

	File certSK = new File(pathJKSSK);
	String aliasSK = "citsmartcripto";
	String pwdSK = "c3ntr@lit123";

	PrivateKey privateKey = getPrivateKeyFromFile(cert, alias, pwd);
	PublicKey publicKey = getPublicKeyFromFile(cert, alias, pwd);

	SecretKey secretkey = getSecretKeyFromFile(certSK, aliasSK, pwdSK);
	
	byte[] txtDecripto = decript(publicKey, privateKey, secretkey, hexStringToByteArray(txtCriptoAndSigned));

	if( verifySignature( publicKey, txtDecripto, hexStringToByteArray(txtAssinado)) ) {
	    return new String(txtDecripto);
	} else {
	    return "FAIL - ASSINATURA INVALIDA!";
	}
    }

    public static PrivateKey getPrivateKeyFromFile(File cert, String alias, String password) throws Exception {
	KeyStore ks = KeyStore.getInstance("JKS");
	char[] pwd = password.toCharArray();
	InputStream is = new FileInputStream(cert);
	ks.load(is, pwd);
	is.close();
	Key key = ks.getKey(alias, pwd);
	if (key instanceof PrivateKey) {
	    return (PrivateKey) key;
	}
	return null;
    }

    /**
     * Extrai a chave pública do arquivo.
     */
    public static PublicKey getPublicKeyFromFile(File cert, String alias, String password) throws Exception {
	KeyStore ks = KeyStore.getInstance("JKS");
	char[] pwd = password.toCharArray();
	InputStream is = new FileInputStream(cert);
	ks.load(is, pwd);
	Key key = ks.getKey(alias, pwd);
	Certificate c = ks.getCertificate(alias);
	PublicKey p = c.getPublicKey();
	return p;
    }

    /**
     * Extrai a chave pública do arquivo.
     */
    public static SecretKey getSecretKeyFromFile(File cert, String alias, String password) throws Exception {
	KeyStore ks = KeyStore.getInstance("JCEKS");
	char[] pwd = password.toCharArray();
	InputStream is = new FileInputStream(cert);
	ks.load(is, pwd);
	Key key = ks.getKey(alias, pwd);
	if (key instanceof SecretKey) {
	    return (SecretKey) key;
	}
	return null;
    }

    /**
     * Retorna a assinatura para o buffer de bytes, usando a chave privada.
     * 
     * @param key
     *            PrivateKey
     * @param buffer
     *            Array de bytes a ser assinado.
     */
    public static byte[] createSignature(PrivateKey key, byte[] buffer) throws Exception {
	Signature sig = Signature.getInstance("MD5withRSA");
	sig.initSign(key);
	sig.update(buffer, 0, buffer.length);
	return sig.sign();
    }

    /**
     * Verifica a assinatura para o buffer de bytes, usando a chave pública.
     * 
     * @param key
     *            PublicKey
     * @param buffer
     *            Array de bytes a ser verficado.
     * @param sgined
     *            Array de bytes assinado (encriptado) a ser verficado.
     */
    public static boolean verifySignature(PublicKey key, byte[] buffer, byte[] signed) throws Exception {
	Signature sig = Signature.getInstance("MD5withRSA");
	sig.initVerify(key);
	sig.update(buffer, 0, buffer.length);
	return sig.verify(signed);
    }

    /**
     * Converte um array de byte em uma representação, em String, de seus
     * hexadecimais.
     */
    public static String txt2Hexa(byte[] bytes) {
	if (bytes == null)
	    return null;
	String hexDigits = "0123456789abcdef";
	StringBuffer sbuffer = new StringBuffer();
	for (int i = 0; i < bytes.length; i++) {
	    int j = ((int) bytes[i]) & 0xFF;
	    sbuffer.append(hexDigits.charAt(j / 16));
	    sbuffer.append(hexDigits.charAt(j % 16));
	}
	return sbuffer.toString();
    }

    public static byte[] generateWrapKey(PublicKey publicKey, Cipher cipher, SecretKey key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
	    IllegalBlockSizeException {
	byte[] wrappedKey = cipher.wrap(key);
	return wrappedKey;
    }

    public static byte[] cript(PublicKey publicKey, SecretKey secretkey, byte[] msg) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
	    IllegalBlockSizeException, ShortBufferException, BadPaddingException {
	Cipher cipher = Cipher.getInstance("AES");
	cipher.init(Cipher.ENCRYPT_MODE, secretkey);

	int blockSize = cipher.getBlockSize();
	int outputSize = cipher.getOutputSize(blockSize);
	byte[] inBytes = new byte[blockSize];
	byte[] outBytes = new byte[outputSize];
	int inLength = 0;
	;
	boolean more = true;

	outBytes = cipher.doFinal(msg, 0, msg.length);
	return outBytes;
    }

    public static byte[] decript(PublicKey publicKey, PrivateKey privateKey, SecretKey secretkey, byte[] msgCripto) throws InvalidKeyException, NoSuchAlgorithmException,
	    NoSuchPaddingException, IllegalBlockSizeException, ShortBufferException, BadPaddingException {
	Cipher cipher = Cipher.getInstance("AES");
	cipher.init(Cipher.DECRYPT_MODE, secretkey);

	int blockSize = cipher.getBlockSize();
	int outputSize = cipher.getOutputSize(blockSize);
	byte[] inBytes = new byte[blockSize];
	byte[] outBytes = new byte[outputSize];
	int inLength = 0;
	;
	boolean more = true;

	outBytes = cipher.doFinal(msgCripto, 0, msgCripto.length);
	return outBytes;
    }

    /**
     * Converte o array de bytes em uma representação hexadecimal.
     * 
     * @param input
     *            - O array de bytes a ser convertido.
     * @return Uma String com a representação hexa do array
     */
    public static String byteArrayToHexString(byte[] b) {
	StringBuffer buf = new StringBuffer();

	for (int i = 0; i < b.length; i++) {
	    int j = ((int) b[i]) & 0xFF;
	    buf.append(hexDigits.charAt(j / 16));
	    buf.append(hexDigits.charAt(j % 16));
	}

	return buf.toString();
    }

    /**
     * Converte uma String hexa no array de bytes correspondente.
     * 
     * @param hexa
     *            - A String hexa
     * @return O vetor de bytes
     * @throws IllegalArgumentException
     *             - Caso a String não sej auma representação haxadecimal válida
     */
    public static byte[] hexStringToByteArray(String hexa) throws IllegalArgumentException {

	// verifica se a String possui uma quantidade par de elementos
	if (hexa.length() % 2 != 0) {
	    throw new IllegalArgumentException("String hexa inválida");
	}

	byte[] b = new byte[hexa.length() / 2];

	for (int i = 0; i < hexa.length(); i += 2) {
	    b[i / 2] = (byte) ((hexDigits.indexOf(hexa.charAt(i)) << 4) | (hexDigits.indexOf(hexa.charAt(i + 1))));
	}
	return b;
    }
}
