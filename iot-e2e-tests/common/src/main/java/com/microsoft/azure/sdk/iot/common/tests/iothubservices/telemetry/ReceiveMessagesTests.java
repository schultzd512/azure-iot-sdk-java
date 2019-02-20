/*
 *  Copyright (c) Microsoft. All rights reserved.
 *  Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package com.microsoft.azure.sdk.iot.common.tests.iothubservices.telemetry;

import com.microsoft.azure.sdk.iot.common.helpers.ClientType;
import com.microsoft.azure.sdk.iot.common.helpers.IotHubServicesCommon;
import com.microsoft.azure.sdk.iot.common.helpers.Success;
import com.microsoft.azure.sdk.iot.common.setup.ReceiveMessagesCommon;
import com.microsoft.azure.sdk.iot.common.setup.SendMessagesCommon;
import com.microsoft.azure.sdk.iot.device.DeviceClient;
import com.microsoft.azure.sdk.iot.device.InternalClient;
import com.microsoft.azure.sdk.iot.device.IotHubClientProtocol;
import com.microsoft.azure.sdk.iot.device.ModuleClient;
import com.microsoft.azure.sdk.iot.service.BaseDevice;
import com.microsoft.azure.sdk.iot.service.Module;
import com.microsoft.azure.sdk.iot.service.auth.AuthenticationType;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.microsoft.azure.sdk.iot.common.helpers.CorrelationDetailsLoggingAssert.buildExceptionMessage;
import static com.microsoft.azure.sdk.iot.device.IotHubClientProtocol.*;
import static com.microsoft.azure.sdk.iot.service.auth.AuthenticationType.CERTIFICATE_AUTHORITY;
import static com.microsoft.azure.sdk.iot.service.auth.AuthenticationType.SELF_SIGNED;

/**
 * Test class containing all non error injection tests to be run on JVM and android pertaining to receiving messages on a device/module. Class needs to be extended
 * in order to run these tests as that extended class handles setting connection strings and certificate generation
 */
public class ReceiveMessagesTests extends SendMessagesCommon
{
    public ReceiveMessagesTests(InternalClient client, IotHubClientProtocol protocol, BaseDevice identity, AuthenticationType authenticationType, ClientType clientType, String publicKeyCert, String privateKey, String x509Thumbprint)
    {
        super(client, protocol, identity, authenticationType, clientType, publicKeyCert, privateKey, x509Thumbprint);

        System.out.println(clientType + " ReceiveMessagesTests UUID: " + (identity instanceof Module ? ((Module) identity).getId() : identity.getDeviceId()));
    }

    @Test
    public void sendMessages() throws IOException, InterruptedException
    {

        if (testInstance.protocol == MQTT_WS && (testInstance.authenticationType == SELF_SIGNED || testInstance.authenticationType == CERTIFICATE_AUTHORITY))
        {
            //mqtt_ws does not support x509 auth currently
            return;
        }

        IotHubServicesCommon.sendMessages(testInstance.client, testInstance.protocol, NORMAL_MESSAGES_TO_SEND, RETRY_MILLISECONDS, SEND_TIMEOUT_MILLISECONDS, 0, null);
    }
}
