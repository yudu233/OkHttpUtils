package rain.coder.myohttp.https;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * https验证工具类
 * Created by Administrator on 2017/4/10 0010.
 */
public class HttpsUtils {
    //使用命令keytool -printcert -rfc -file srca.cer 导出证书为字符串，然后将字符串转换为输入流，

    public static class SSLParams {
        public SSLSocketFactory sSLSocketFactory;
        public X509TrustManager trustManager;
    }

    /**
     * 返回SSLSocketFactory
     *
     * @param certificates 证书的输入流
     * @return SSLSocketFactory
     */
    public static SSLParams getSSLSocketFactory(InputStream... certificates) {
        return getSSLSocketFactory(null, certificates);
    }


    /**
     * 双向认证
     *
     * @param keyManagers  KeyManager[]
     * @param certificates 证书的输入流
     * @return SSLSocketFactory
     */
    public static SSLParams getSSLSocketFactory(KeyManager[] keyManagers, InputStream... certificates) {

        SSLParams sslParams = new SSLParams();

        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {
                }
            }
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(keyManagers, trustManagerFactory.getTrustManagers(), new SecureRandom());
            sslParams.sSLSocketFactory = sslContext.getSocketFactory();
            return sslParams;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获得双向认证所需的参数
     *
     * @param bks          bks证书的输入流
     * @param keystorePass 秘钥
     * @return KeyManager[]对象
     */
    public static KeyManager[] getKeyManagers(InputStream bks, String keystorePass) {
        KeyStore clientKeyStore = null;
        try {
            clientKeyStore = KeyStore.getInstance("BKS");
            clientKeyStore.load(bks, keystorePass.toCharArray());
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(clientKeyStore, keystorePass.toCharArray());
            KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();
            return keyManagers;
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 忽略所有https证书
     *
     * @return
     */
    public static SSLParams createSSLSocketFactory() {
        SSLParams sslParams = new SSLParams();


        final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                X509Certificate[] x509Certificates = new X509Certificate[0];
                return x509Certificates;
            }
        }};
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            sslParams.sSLSocketFactory = sslContext.getSocketFactory();
            return sslParams;
        } catch (Exception e) {
        }
        return null;
    }
}