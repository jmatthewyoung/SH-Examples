/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iot.raspberry;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.google.gson.Gson;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author matthew.young
 */
public class AWSCloudClient implements ICloudClient {
    private final String clientEndpoint = "";
    private final String clientId = "";
    private final String awsAccessKeyId = "";
    private final String awsSecretAccessKey = "";
    AWSIotMqttClient client;
    Gson gson = new Gson();

    public AWSCloudClient() throws Exception {
        
        client = new AWSIotMqttClient(clientEndpoint, clientId, awsAccessKeyId, awsSecretAccessKey);
        client.connect();
    }

    @Override
    public void WriteData(DataObject o) {
        String topic = "your/topic";
        String payload = gson.toJson(o);

        try {
            client.publish(topic, AWSIotQos.QOS0, payload);
        } catch (AWSIotException ex) {
            Logger.getLogger(AWSCloudClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
