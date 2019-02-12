/*
 *  Copyright (c) Microsoft. All rights reserved.
 *  Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package com.microsoft.azure.sdk.iot.android.iothubservices.messaging;

import com.microsoft.appcenter.espresso.Factory;
import com.microsoft.appcenter.espresso.ReportHelper;
import com.microsoft.azure.sdk.iot.android.BuildConfig;
import com.microsoft.azure.sdk.iot.android.helper.TestGroupA;
import com.microsoft.azure.sdk.iot.common.helpers.ClientType;
import com.microsoft.azure.sdk.iot.common.helpers.Rerun;
import com.microsoft.azure.sdk.iot.common.tests.iothubservices.telemetry.SendMessagesTests;
import com.microsoft.azure.sdk.iot.deps.util.Base64;
import com.microsoft.azure.sdk.iot.device.InternalClient;
import com.microsoft.azure.sdk.iot.device.IotHubClientProtocol;
import com.microsoft.azure.sdk.iot.device.exceptions.ModuleClientException;
import com.microsoft.azure.sdk.iot.service.BaseDevice;
import com.microsoft.azure.sdk.iot.service.auth.AuthenticationType;
import com.microsoft.azure.sdk.iot.service.exceptions.IotHubException;
import com.microsoft.azure.sdk.iot.device.DeviceTwin.Pair;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Collection;

@TestGroupA
@RunWith(Parameterized.class)
public class ProvisioningClientAndroidRunner extends ProvisioningTests
{
    @Rule
    public Rerun count = new Rerun(3);

    @Rule
    public ReportHelper reportHelper = Factory.getReportHelper();

    public ProvisioningClientAndroidRunner(ProvisioningDeviceClientTransportProtocol protocol, AttestationType attestationType)
    {
        super(protocol, attestationType);
    }

    //This function is run before even the @BeforeClass annotation, so it is used as the @BeforeClass method
    @Parameterized.Parameters(name = "{0} with {1}")
    public static Collection inputs() throws Exception
    {
        iotHubConnectionString = Tools.retrieveEnvironmentVariableValue(IOT_HUB_CONNECTION_STRING_ENV_VAR_NAME);
        provisioningServiceConnectionString = Tools.retrieveEnvironmentVariableValue(DPS_CONNECTION_STRING_ENV_VAR_NAME);
        provisioningServiceGlobalEndpoint = Tools.retrieveEnvironmentVariableValue(DPS_GLOBAL_ENDPOINT_ENV_VAR_NAME);
        provisioningServiceIdScope = Tools.retrieveEnvironmentVariableValue(DPS_ID_SCOPE_ENV_VAR_NAME);
        tpmSimulatorIpAddress = Tools.retrieveEnvironmentVariableValue(TPM_SIMULATOR_IP_ADDRESS_ENV_NAME);
        provisioningServiceGlobalEndpointWithInvalidCert = Tools.retrieveEnvironmentVariableValue(DPS_GLOBAL_ENDPOINT_WITH_INVALID_CERT_ENV_VAR_NAME);
        provisioningServiceWithInvalidCertConnectionString = Tools.retrieveEnvironmentVariableValue(DPS_CONNECTION_STRING_WITH_INVALID_CERT_ENV_VAR_NAME);

        return ProvisioningCommon.inputs();
    }

    @Override
    public Pair<String, String> generateCertificates(String registrationId) throws GeneralSecurityException, IOException
    {
        X509Cert certs = new X509Cert(0, false, registrationId, null);
        final String leafPublicPem =  certs.getPublicCertLeafPem();
        final String leafPrivateKey = certs.getPrivateKeyLeafPem();
        return new Pair<>(leafPublicPem, leafPrivateKey);
    }
}