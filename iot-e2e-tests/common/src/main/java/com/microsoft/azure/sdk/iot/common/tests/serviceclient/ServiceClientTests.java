/*
 *  Copyright (c) Microsoft. All rights reserved.
 *  Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package com.microsoft.azure.sdk.iot.common.tests.serviceclient;

import com.microsoft.azure.sdk.iot.common.helpers.IntegrationTest;
import com.microsoft.azure.sdk.iot.common.helpers.Tools;
import com.microsoft.azure.sdk.iot.service.*;
import com.microsoft.azure.sdk.iot.service.exceptions.IotHubException;
import org.junit.Test;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static com.microsoft.azure.sdk.iot.common.helpers.CorrelationDetailsLoggingAssert.buildExceptionMessage;
import static org.junit.Assert.*;

/**
 * Test class containing all tests to be run on JVM and android pertaining to C2D communication using the service client. Class needs to be extended
 * in order to run these tests as that extended class handles setting connection strings and certificate generation
 */
public class ServiceClientTests extends IntegrationTest
{
    protected static String iotHubConnectionString = "";
    protected static String invalidCertificateServerConnectionString = "";
    private static String deviceId = "java-service-client-e2e-test";
    private static String content = "abcdefghijklmnopqrstuvwxyz1234567890";
    private static String hostName;

    private static final long MAX_TEST_MILLISECONDS = 1 * 60 * 1000;

    public ServiceClientTests(IotHubServiceClientProtocol protocol)
    {
        this.testInstance = new ServiceClientITRunner(protocol);
    }

    private class ServiceClientITRunner
    {
        private IotHubServiceClientProtocol protocol;

        public ServiceClientITRunner(IotHubServiceClientProtocol protocol)
        {
            this.protocol = protocol;
        }
    }

    private ServiceClientITRunner testInstance;

    //This function is run before even the @BeforeClass annotation, so it is used as the @BeforeClass method
    @Parameterized.Parameters(name = "{0}")
    public static Collection inputsCommon() throws IOException
    {
        String uuid = UUID.randomUUID().toString();
        deviceId = deviceId.concat("-" + uuid);
        hostName = IotHubConnectionStringBuilder.createConnectionString(iotHubConnectionString).getHostName();


        List inputs = Arrays.asList(
                new Object[][]
                        {
                                {IotHubServiceClientProtocol.AMQPS},
                                {IotHubServiceClientProtocol.AMQPS_WS}
                        }
        );

        return inputs;
    }

    @Test
    public void test()
    {

    }

}
