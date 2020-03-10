package com.tecsun.tsb.network.tool;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SSLSocketFactoryEx extends SSLSocketFactory {

	private SSLSocketFactory delegate;

	public SSLSocketFactoryEx(InputStream inputStream, String passWord) {
		super();
		try {
			KeyStore keyStore = KeyStore.getInstance("PKCS12", "BC");
			keyStore.load(inputStream, passWord.toCharArray());
			inputStream.close();
			KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			keyManagerFactory.init(keyStore, passWord.toCharArray());
			KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();
			TrustManager trustManager = new X509TrustManager() {
				@Override
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

				}

				@Override
				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

				}

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[]{};
				}
			};
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(keyManagers, new TrustManager[] { trustManager }, new SecureRandom());

			delegate = sslContext.getSocketFactory();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}

		/*try {
			KeyStore keyStore = KeyStore.getInstance("PKCS12", "BC");
			keyStore.load(inputStream, passWord.toCharArray());
			KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			keyManagerFactory.init(keyStore, passWord.toCharArray());
			TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			trustManagerFactory.init(keyStore);
			*//*TrustManagerFactory trustManagerFactory =
					TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			trustManagerFactory.init(keyStore);
			X509TrustManager x509TrustManager;
			x509TrustManager = null;
			for (TrustManager trustManager : trustManagerFactory.getTrustManagers() ) {
				if (trustManager instanceof X509TrustManager) {
					x509TrustManager = (X509TrustManager) trustManager;
				}
			}
			if (x509TrustManager == null) {
				throw new NullPointerException();
			}*//*
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(keyManagerFactory.getKeyManagers(),
					trustManagerFactory.getTrustManagers(), new SecureRandom());

			delegate = sslContext.getSocketFactory();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		}
		delegate = null;*/
	}

	public SSLSocketFactory getDelegate() {
		return delegate;
	}

	@Override
	public String[] getDefaultCipherSuites() {
		return delegate.getDefaultCipherSuites();
	}

	@Override
	public String[] getSupportedCipherSuites() {
		return delegate.getSupportedCipherSuites();
	}

	@Override
	public Socket createSocket(Socket s, String host, int port,
							   boolean autoClose) throws IOException {
		return enableTLSOnSocket(delegate.createSocket(s, host, port,
				autoClose));
	}

	@Override
	public Socket createSocket(String host, int port) throws IOException {
		return enableTLSOnSocket(delegate.createSocket(host, port));
	}

	@Override
	public Socket createSocket(String host, int port, InetAddress localHost,
							   int localPort) throws IOException {
		return enableTLSOnSocket(delegate.createSocket(host, port,
				localHost, localPort));
	}

	@Override
	public Socket createSocket(InetAddress host, int port) throws IOException {
		return enableTLSOnSocket(delegate.createSocket(host, port));
	}

	@Override
	public Socket createSocket(InetAddress address, int port,
							   InetAddress localAddress, int localPort) throws IOException {
		return enableTLSOnSocket(delegate.createSocket(address, port,
				localAddress, localPort));
	}

	private Socket enableTLSOnSocket(Socket socket) {
		if(socket != null && (socket instanceof SSLSocket)) {
			((SSLSocket)socket).setEnabledProtocols(new String[] {"TLS"});
		}
		return socket;
	}

}