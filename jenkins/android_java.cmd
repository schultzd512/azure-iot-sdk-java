@REM Copyright (c) Microsoft. All rights reserved.
@REM Licensed under the MIT license. See LICENSE file in the project root for full license information.

setlocal

set build-root=%~dp0..
@REM // resolve to fully qualified path
for %%i in ("%build-root%") do set build-root=%%~fi

@REM -- Delete m2 folder--

call RD /S /Q "c:/users/%USERNAME%/.m2/repository/com/microsoft/azure/sdk/iot"

@REM -- Android Test Build --
cd %build-root%
call mvn install -DskipTests=true
if errorlevel 1 goto :eof
cd %build-root%\iot-e2e-tests\android
call gradle wrapper
call gradlew :clean :app:clean :app:assembleDebug :things:clean :things:assembleDebug
call gradlew :app:assembleDebugAndroidTest -PIotHubConnectionString=%IOTHUB_CONNECTION_STRING% -PIotHubPublicCertBase64=%IOTHUB_E2E_X509_CERT_BASE64% -PIotHubPrivateKeyBase64=%IOTHUB_E2E_X509_PRIVATE_KEY_BASE64% -PIotHubThumbprint=%IOTHUB_E2E_X509_THUMBPRINT% -PIotHubInvalidCertConnectionString=%IOTHUB_CONN_STRING_INVALIDCERT% -PAppCenterAppSecret=%APPCENTER_APP_SECRET%
call gradlew :things:assembleDebugAndroidTest -PIotHubConnectionString=%IOTHUB_CONNECTION_STRING% -PIotHubPublicCertBase64=%IOTHUB_E2E_X509_CERT_BASE64% -PIotHubPrivateKeyBase64=%IOTHUB_E2E_X509_PRIVATE_KEY_BASE64% -PIotHubThumbprint=%IOTHUB_E2E_X509_THUMBPRINT% -PIotHubInvalidCertConnectionString=%IOTHUB_CONN_STRING_INVALIDCERT% -PAppCenterAppSecret=%APPCENTER_APP_SECRET%
