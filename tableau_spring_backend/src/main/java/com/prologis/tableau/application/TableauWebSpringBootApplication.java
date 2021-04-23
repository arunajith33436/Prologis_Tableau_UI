package com.prologis.tableau.application;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = "com.prologis.tableau.*")
public class TableauWebSpringBootApplication extends SpringBootServletInitializer implements WebApplicationInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(TableauWebSpringBootApplication.class);
    }

	public static void main(String[] args) {
		 SpringApplication.run(TableauWebSpringBootApplication.class, args);

	}
	
	 @Bean
	    public RestTemplate restTemplate() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
	    	/*TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

	        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
	                        .loadTrustMaterial(null, acceptingTrustStrategy)
	                        .build();

	        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

	        CloseableHttpClient httpClient = HttpClients.custom()
	                        .setSSLSocketFactory(csf)
	                        .build();

	        HttpComponentsClientHttpRequestFactory requestFactory =
	                        new HttpComponentsClientHttpRequestFactory();

	        requestFactory.setHttpClient(httpClient);
	        RestTemplate restTemplate = new RestTemplate(requestFactory);*/
		 
		 TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
		        @Override
		        public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
		            return true;
		        }
		    };
		    SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
		    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
		    CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
		    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		    requestFactory.setHttpClient(httpClient);
		    RestTemplate restTemplate = new RestTemplate(requestFactory);

	        return restTemplate;
	    }
	 
	 
	 
}
