package com.geek.kotlin_api_revistas

import java.io.ByteArrayInputStream
import java.net.URL
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.*

class certificado {

    companion object {
        var certificateString:String="-----BEGIN CERTIFICATE-----\n" +
                "MIIGMjCCBRqgAwIBAgIRAIEJb7ZpwymbJYMbhOyB2mcwDQYJKoZIhvcNAQELBQAw\n" +
                "gY8xCzAJBgNVBAYTAkdCMRswGQYDVQQIExJHcmVhdGVyIE1hbmNoZXN0ZXIxEDAO\n" +
                "BgNVBAcTB1NhbGZvcmQxGDAWBgNVBAoTD1NlY3RpZ28gTGltaXRlZDE3MDUGA1UE\n" +
                "AxMuU2VjdGlnbyBSU0EgRG9tYWluIFZhbGlkYXRpb24gU2VjdXJlIFNlcnZlciBD\n" +
                "QTAeFw0yMjAyMTAwMDAwMDBaFw0yMzAzMTMyMzU5NTlaMBgxFjAUBgNVBAMMDSou\n" +
                "dXRlcS5lZHUuZWMwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQC/tlKP\n" +
                "O2OVV5so4KOA/5MaAjUFvxFHEkEZ/YOXMc699kl66vxak3UV83UEIqkoxVQ7QTHN\n" +
                "AQhVUFVfl+3JMDAkUbG3gD7wDJWudEY+dw6jCgUcyRWOoxF7dvBi7tkA1SuB/BlF\n" +
                "R/t3+X5vR9TUbR0jtN7M5kUwVodcpLCzEnYzMHpDrzq/Vf9Xx/xwKmen31VOgxAC\n" +
                "gIhyzN8gey4IArECnPFfvMxkDWQs4hW//E6f0a0L8VNaprrVuJoQcz0O+lA5omWr\n" +
                "BO2NeCteqfRk/2RaHE854WmoK9RhBmskQdDVAe/1er9ztycOEYs/QMTlngANIZFe\n" +
                "EzXwfFmubqYltYU3AgMBAAGjggL9MIIC+TAfBgNVHSMEGDAWgBSNjF7EVK2K4Xfp\n" +
                "m/mbBeG4AY1h4TAdBgNVHQ4EFgQUCc8bhGabdii8chVxyjoXft8/jJswDgYDVR0P\n" +
                "AQH/BAQDAgWgMAwGA1UdEwEB/wQCMAAwHQYDVR0lBBYwFAYIKwYBBQUHAwEGCCsG\n" +
                "AQUFBwMCMEkGA1UdIARCMEAwNAYLKwYBBAGyMQECAgcwJTAjBggrBgEFBQcCARYX\n" +
                "aHR0cHM6Ly9zZWN0aWdvLmNvbS9DUFMwCAYGZ4EMAQIBMIGEBggrBgEFBQcBAQR4\n" +
                "MHYwTwYIKwYBBQUHMAKGQ2h0dHA6Ly9jcnQuc2VjdGlnby5jb20vU2VjdGlnb1JT\n" +
                "QURvbWFpblZhbGlkYXRpb25TZWN1cmVTZXJ2ZXJDQS5jcnQwIwYIKwYBBQUHMAGG\n" +
                "F2h0dHA6Ly9vY3NwLnNlY3RpZ28uY29tMCUGA1UdEQQeMByCDSoudXRlcS5lZHUu\n" +
                "ZWOCC3V0ZXEuZWR1LmVjMIIBfwYKKwYBBAHWeQIEAgSCAW8EggFrAWkAdgCt9776\n" +
                "fP8QyIudPZwePhhqtGcpXc+xDCTKhYY069yCigAAAX7k6zlJAAAEAwBHMEUCIDON\n" +
                "wdoI3GpjQhwMnWMCxn/0cwWSsLetkiu9Pqsd2MM4AiEAxCIwyJJIbeiK64QCVGZs\n" +
                "n5UP3/xClKZx7ioAq4ng4RwAdwB6MoxU2LcttiDqOOBSHumEFnAyE4VNO9IrwTpX\n" +
                "o1LrUgAAAX7k6zlTAAAEAwBIMEYCIQDNY7Sgu2yNxt5xwvY+mhhEByiKH4ptMKXI\n" +
                "/LyYQWc5cwIhALaa3YePUw6y6pWQFOhohP2nnE+Jc0f9nKQFNKa/mNSbAHYA6D7Q\n" +
                "2j71BjUy51covIlryQPTy9ERa+zraeF3fW0GvW4AAAF+5Os5KgAABAMARzBFAiBv\n" +
                "TzuYiD2vbaXNfQGCktws0hNIMM2NsLgR8J2TqAIGkgIhAIJPqsnSsDW9z4z+9HQD\n" +
                "6OiLrOBH3XGkDR+KWJnOfzLrMA0GCSqGSIb3DQEBCwUAA4IBAQCFt2qpp0qE4T9g\n" +
                "RKLa1+n91uhJEmB5sHhu08goavIZfjeQDkZPyrlfrGRRC6gfOBPF+ClICVKoMbj4\n" +
                "OsWOSY/3Md5Mg29anEKPeHRgBMk0ccnFrbZePeAJyBVwhdfOHvxY2wxs2gHRZWsq\n" +
                "YX1HZFWoHgDakO7I7+WwZ1FT1RZrs9ld3Lfs/BtOuLnj4BFuctvep6Z373M8+Guj\n" +
                "bwEp/b+HIdNA0OEJLGthrscA/vBhfSCRw9Xpxa8fTtdNo7PA5NoOCRl9Bg6twoOs\n" +
                "/Tm4w/6JgcGF0WCflTgFxQ3XZowuB852trzjyyxd99M/CnbRQbI2v7OnlK3g63VJ\n" +
                "sYeDdbZo\n" +
                "-----END CERTIFICATE-----"
        var certificado2="-----BEGIN CERTIFICATE-----\n" +
                "MIIHFDCCBfygAwIBAgIQCLS/dX/bKN3zuMTJNXxaSTANBgkqhkiG9w0BAQsFADBP\n" +
                "MQswCQYDVQQGEwJVUzEVMBMGA1UEChMMRGlnaUNlcnQgSW5jMSkwJwYDVQQDEyBE\n" +
                "aWdpQ2VydCBUTFMgUlNBIFNIQTI1NiAyMDIwIENBMTAeFw0yMjA0MDcwMDAwMDBa\n" +
                "Fw0yMzA0MDcyMzU5NTlaMGgxCzAJBgNVBAYTAlVTMRMwEQYDVQQIEwpDYWxpZm9y\n" +
                "bmlhMRYwFAYDVQQHEw1TYW4gRnJhbmNpc2NvMRUwEwYDVQQKEwxHaXRIdWIsIElu\n" +
                "Yy4xFTATBgNVBAMMDCouZ2l0aHViLmNvbTCCASIwDQYJKoZIhvcNAQEBBQADggEP\n" +
                "ADCCAQoCggEBALyqZjatk2jnqiWmp6eusW70yJlreKz8mllyRSPxnIVeuwCHGzeQ\n" +
                "pGOOZkdRiBLcC2SWM3WgwQjBVBzqS1hWgoP5e6hzuXvGM3anlgJDE9dDUJfdC/Is\n" +
                "nzB4Q5Y4TU3FcRCUaK4GMoJGC0fu0fDbH927yKAnvdErG4u+jFSqIidwEaEfPWCC\n" +
                "o3xCyQLHTknXQ9aaDvU6GHNX0us6G+bjdErIwQtC56F0ke7biV0A/DWX5V+hVsVY\n" +
                "jY9JbYNx+KFjmUxLibccXzXs0pJ+a6Xa4OhhrFebPwS+SQA+gxTTvZotj4J5kf2l\n" +
                "nM9H+1whu6I5qPebhlTRTKpxdPm9V647Zj8CAwEAAaOCA9EwggPNMB8GA1UdIwQY\n" +
                "MBaAFLdrouqoqoSMeeq02g+YssWVdrn0MB0GA1UdDgQWBBRWmrM0shNZi0idiZiI\n" +
                "7l3ryIMwdDB7BgNVHREEdDByggwqLmdpdGh1Yi5jb22CDnd3dy5naXRodWIuY29t\n" +
                "gglnaXRodWIuaW+CCmdpdGh1Yi5jb22CCyouZ2l0aHViLmlvghVnaXRodWJ1c2Vy\n" +
                "Y29udGVudC5jb22CFyouZ2l0aHVidXNlcmNvbnRlbnQuY29tMA4GA1UdDwEB/wQE\n" +
                "AwIFoDAdBgNVHSUEFjAUBggrBgEFBQcDAQYIKwYBBQUHAwIwgY8GA1UdHwSBhzCB\n" +
                "hDBAoD6gPIY6aHR0cDovL2NybDMuZGlnaWNlcnQuY29tL0RpZ2lDZXJ0VExTUlNB\n" +
                "U0hBMjU2MjAyMENBMS00LmNybDBAoD6gPIY6aHR0cDovL2NybDQuZGlnaWNlcnQu\n" +
                "Y29tL0RpZ2lDZXJ0VExTUlNBU0hBMjU2MjAyMENBMS00LmNybDA+BgNVHSAENzA1\n" +
                "MDMGBmeBDAECAjApMCcGCCsGAQUFBwIBFhtodHRwOi8vd3d3LmRpZ2ljZXJ0LmNv\n" +
                "bS9DUFMwfwYIKwYBBQUHAQEEczBxMCQGCCsGAQUFBzABhhhodHRwOi8vb2NzcC5k\n" +
                "aWdpY2VydC5jb20wSQYIKwYBBQUHMAKGPWh0dHA6Ly9jYWNlcnRzLmRpZ2ljZXJ0\n" +
                "LmNvbS9EaWdpQ2VydFRMU1JTQVNIQTI1NjIwMjBDQTEtMS5jcnQwCQYDVR0TBAIw\n" +
                "ADCCAX8GCisGAQQB1nkCBAIEggFvBIIBawFpAHYA6D7Q2j71BjUy51covIlryQPT\n" +
                "y9ERa+zraeF3fW0GvW4AAAGABfvdbAAABAMARzBFAiAGLk49aFP9ARwPXCa59WnI\n" +
                "f5jIU5eFmqR6/W3Zm38KiwIhAIp8FySKqbKk600uO4iPsS6TW8hJl67PprwXYMlr\n" +
                "o3wPAHcANc8ZG7+xbFe/D61MbULLu7YnICZR6j/hKu+oA8M71kwAAAGABfvdXQAA\n" +
                "BAMASDBGAiEAjFarHnzcbBvQ8//um0zVd4G3T5zbW4XSUIJSTc5JGo8CIQDaT5K8\n" +
                "pji9egTYSypP9XfRK+Z2wID3j43uuGjiKSOKyQB2ALNzdwfhhFD4Y4bWBancEQlK\n" +
                "eS2xZwwLh9zwAw55NqWaAAABgAX73YsAAAQDAEcwRQIhAO/PWksY7Zd7W5NJr3e4\n" +
                "xRkx8J6Qv7a33VA3tkm96k4WAiBshJWPE2BjKzuQ/KEfiKnvD4dDa3btkmcWlpiD\n" +
                "R8AvQDANBgkqhkiG9w0BAQsFAAOCAQEARtY8iVMqqBCXGZj2NRhpxA4eS2b/e/56\n" +
                "JhnRWGz3wxf0aRjbaZ2sUH3aHe1UDyg4jVPgnSLsGnBMmN5Rk32uiB/5v6/uRhCa\n" +
                "l26Yi9MYbeQpt0980MxT5hhv8bThRiNa77+oAOcrYMJEGIf2/9k0yoefblEZTR02\n" +
                "6UU6pkDhxjMtpyNRr+IdqQM/4lCM6nu8FZ/qaLltvta1Enq+jEwEObo/PoBoQJzJ\n" +
                "j7hcu7rkyPQIK1raQ9pK7uFJ2/FgtxIUuT+by06LnUp82VB7QxlniXO2R4XgDzWd\n" +
                "umlpkAFJQvZ+Sa2rSdjynrTDedjQIv3s1jH2Tvao5fR23tW2XAQhVg==\n" +
                "-----END CERTIFICATE-----"

        fun iniciar():SSLSocketFactory{
            val derInputStream = ByteArrayInputStream(certificateString.toByteArray())
            val certificateFactory = CertificateFactory.getInstance("X.509")
            val cert = certificateFactory.generateCertificate(derInputStream) as X509Certificate
            val alias = "alias" //cert.getSubjectX500Principal().getName();

            val trustStore = KeyStore.getInstance(KeyStore.getDefaultType())
            trustStore.load(null)
            trustStore.setCertificateEntry(alias, cert)

            val kmf = KeyManagerFactory.getInstance("X509")
            kmf.init(trustStore, null)
            val keyManagers = kmf.keyManagers

            val tmf = TrustManagerFactory.getInstance("X509")
            tmf.init(trustStore)
            val trustManagers = tmf.trustManagers

            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(keyManagers, trustManagers, null)
            /*val url = URL("https://revistas.uteq.edu.ec")
            var conn = url.openConnection() as HttpsURLConnection
            conn.setSSLSocketFactory(sslContext.socketFactory)*/
            return sslContext.socketFactory;
        }
    }



}