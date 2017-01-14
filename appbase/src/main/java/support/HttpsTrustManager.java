package support;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class HttpsTrustManager implements X509TrustManager {

	private static TrustManager[] trustManagers;
	private static final X509Certificate[] _AcceptedIssuers = new X509Certificate[] {};
	public static boolean isSSL = false;

	@Override
	public void checkClientTrusted(
			X509Certificate[] x509Certificates, String s)
			throws CertificateException {
	}

	@Override
	public void checkServerTrusted(
			X509Certificate[] x509Certificates, String s)
			throws CertificateException {
/*
		 // Perform customary SSL/TLS checks
		try {
			TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
			tmf.init((KeyStore) null);

			for (TrustManager trustManager : tmf.getTrustManagers()) {
				((X509TrustManager) trustManager).checkServerTrusted(x509Certificates, s);
			}

			*/

		if(isSSL) {
			try {
				for (int i = 0; i < x509Certificates.length; i++) {

					x509Certificates[i].checkValidity();
				}
				TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
				tmf.init((KeyStore) null);
				for (TrustManager trustManager : tmf.getTrustManagers()) {
					X509TrustManager sa = (X509TrustManager) trustManager;
					sa.checkServerTrusted(x509Certificates, s);
				}

			} catch (Exception e) {
				throw new CertificateException(e);
			}
		}

	}

	public boolean isClientTrusted(X509Certificate[] chain) {
		return true;
	}

	public boolean isServerTrusted(X509Certificate[] chain) {
		return true;
	}



	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return _AcceptedIssuers;
	}

	public static void allowAllSSL() {
		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

			@Override
			public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}

		});

		SSLContext context = null;
		if (trustManagers == null) {
			trustManagers = new TrustManager[] { new HttpsTrustManager() };
		}

		try {
			context = SSLContext.getInstance("SSLv3");
			context.init(null, trustManagers, new SecureRandom());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}

		HttpsURLConnection.setDefaultSSLSocketFactory(context
				.getSocketFactory());
	}
	
	public static void allowAllSSLMS() {
		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

			@Override
			public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}

		});

		SSLContext context = null;
		if (trustManagers == null) {
			trustManagers = new TrustManager[] { new HttpsTrustManager() };
		}

		try {
			context = SSLContext.getInstance("TLS");
			context.init(null, trustManagers, new SecureRandom());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}

		HttpsURLConnection.setDefaultSSLSocketFactory(context
				.getSocketFactory());
	}

}
