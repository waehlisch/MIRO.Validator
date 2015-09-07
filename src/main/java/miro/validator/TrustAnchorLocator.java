package main.java.miro.validator;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.util.Arrays;

import main.java.miro.validator.fetcher.DownloadResult;
import main.java.miro.validator.fetcher.RsyncFetcher;
import main.java.miro.validator.types.CertificateObject;
import main.java.miro.validator.types.RepositoryObjectFactory;


//https://tools.ietf.org/html/rfc6490
public class TrustAnchorLocator {
	
	private URI trustAnchorLocation;
	
	private byte[] subjectPublicKeyInfo;
	
	private String name;

	public TrustAnchorLocator(String filepath) {
		File TALFile = new File(filepath);
		byte[] talBytes = readBytesFromFile(TALFile);
		trustAnchorLocation = getURIfromTALbytes(talBytes);
		subjectPublicKeyInfo = getSubjectPubKeyInfoFromTALbytes(talBytes);
		deriveName(filepath);
	}
	
	public TrustAnchorLocator(String filepath, String name) {
		this(filepath);
		this.name = name;
	}
	
	public TrustAnchorLocator(URI taLocation, byte[] subjectPubKeyInfo) {
		trustAnchorLocation = taLocation;
		subjectPublicKeyInfo = subjectPubKeyInfo;
	}
	
	private static byte[] readBytesFromFile(File tALFile) {
		try {
			FileInputStream stream = new FileInputStream(tALFile);
			byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			stream.close();
			return bytes;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Could not read Trust Anchor Locator" + tALFile.getName());
		}
	}

	/**
	 * Gets the location of the trust anchor as an URI. The URI is the first line of the TAL
	 * @param talBytes
	 * @return
	 */
	public static URI getURIfromTALbytes(byte[] talBytes) {
		int newLineIndex = getFirstNewLineIndex(talBytes);
		return URI.create(new String(talBytes, 0, newLineIndex));
	}

	public static byte[] getSubjectPubKeyInfoFromTALbytes(byte[] talBytes) {
		int newLineIndex = getFirstNewLineIndex(talBytes);
		return Arrays.copyOfRange(talBytes, newLineIndex+1, talBytes.length);
	}

	/**
	 * Gets the index of the first CR or LF newline. See https://tools.ietf.org/html/rfc6490#section-2
	 * @param talBytes
	 * @return
	 */
	public static int getFirstNewLineIndex(byte[] talBytes) {
		int byteValue;
		for(int i = 0; i<talBytes.length; i++){
			byteValue = talBytes[i];
			if(byteValue == 0x0A || byteValue == 0x0D){
				return i;
			}
		}
		return -1;
	}
	
	private void deriveName(String filepath) {
		File f = new File(filepath);
		name = f.getName();
		if(name.endsWith(".tal"))
			name = name.substring(0, name.length() - 4);
	}

	public URI getTrustAnchorLocation() {
		return trustAnchorLocation;
	}

	public byte[] getSubjectPublicKeyInfo() {
		return subjectPublicKeyInfo;
	}
	
	public String getName() {
		return name;
	}

}
