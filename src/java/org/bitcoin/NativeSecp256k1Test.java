package org.bitcoin;

import javax.xml.bind.DatatypeConverter;

import static org.bitcoin.NativeSecp256k1Util.*;

/**
 * This class holds test cases defined for testing this library.
 */
public class NativeSecp256k1Test {

    //TODO improve comments/add more tests
    /**
      * This tests verify() for a valid signature
      */
    public static void testVerifyPos() throws AssertFailException{
        byte[] data = DatatypeConverter.parseHexBinary("CF80CD8AED482D5D1527D7DC72FCEFF84E6326592848447D2DC0B0E87DFC9A90"); //sha256hash of "testing"
        byte[] sig = DatatypeConverter.parseHexBinary("3044022079BE667EF9DCBBAC55A06295CE870B07029BFCDB2DCE28D959F2815B16F817980220294F14E883B3F525B5367756C2A11EF6CF84B730B36C17CB0C56F0AAB2C98589");
        byte[] pub = DatatypeConverter.parseHexBinary("040A629506E1B65CD9D2E0BA9C75DF9C4FED0DB16DC9625ED14397F0AFC836FAE595DC53F8B0EFE61E703075BD9B143BAC75EC0E19F82A2208CAEB32BE53414C40");

        boolean result = NativeSecp256k1.verify( data, sig, pub);
        assertEquals( result, true , "testVerifyPos");
    }

    /**
      * This tests verify() for a non-valid signature
      */
    public static void testVerifyNeg() throws AssertFailException{
        byte[] data = DatatypeConverter.parseHexBinary("CF80CD8AED482D5D1527D7DC72FCEFF84E6326592848447D2DC0B0E87DFC9A91"); //sha256hash of "testing"
        byte[] sig = DatatypeConverter.parseHexBinary("3044022079BE667EF9DCBBAC55A06295CE870B07029BFCDB2DCE28D959F2815B16F817980220294F14E883B3F525B5367756C2A11EF6CF84B730B36C17CB0C56F0AAB2C98589");
        byte[] pub = DatatypeConverter.parseHexBinary("040A629506E1B65CD9D2E0BA9C75DF9C4FED0DB16DC9625ED14397F0AFC836FAE595DC53F8B0EFE61E703075BD9B143BAC75EC0E19F82A2208CAEB32BE53414C40");

        boolean result = NativeSecp256k1.verify( data, sig, pub);
        assertEquals( result, false , "testVerifyNeg");
    }

    /**
      * This tests secret key verify() for a valid secretkey
      */
    public static void testSecKeyVerifyPos() throws AssertFailException{
        byte[] sec = DatatypeConverter.parseHexBinary("67E56582298859DDAE725F972992A07C6C4FB9F62A8FFF58CE3CA926A1063530");

        boolean result = NativeSecp256k1.secKeyVerify( sec );
        assertEquals( result, true , "testSecKeyVerifyPos");
    }

    /**
      * This tests secret key verify() for an invalid secretkey
      */
    public static void testSecKeyVerifyNeg() throws AssertFailException{
        byte[] sec = DatatypeConverter.parseHexBinary("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");

        boolean result = NativeSecp256k1.secKeyVerify( sec );
        assertEquals( result, false , "testSecKeyVerifyNeg");
    }

    /**
      * This tests public key create() for a valid secretkey
      */
    public static void testPubKeyCreatePos() throws AssertFailException{
        byte[] sec = DatatypeConverter.parseHexBinary("67E56582298859DDAE725F972992A07C6C4FB9F62A8FFF58CE3CA926A1063530");

        byte[] resultArr = NativeSecp256k1.computePubkey(sec, false);
        String pubkeyString = DatatypeConverter.printHexBinary(resultArr);
        assertEquals( pubkeyString , "04C591A8FF19AC9C4E4E5793673B83123437E975285E7B442F4EE2654DFFCA5E2D2103ED494718C697AC9AEBCFD19612E224DB46661011863ED2FC54E71861E2A6" , "testPubKeyCreatePos");
    }

    /**
      * This tests public key create() for a invalid secretkey
      */
    public static void testPubKeyCreateNeg() throws AssertFailException{
       byte[] sec = DatatypeConverter.parseHexBinary("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");

       byte[] resultArr = NativeSecp256k1.computePubkey(sec, false);
       String pubkeyString = DatatypeConverter.printHexBinary(resultArr);
       assertEquals( pubkeyString, "" , "testPubKeyCreateNeg");
    }

    /**
      * This tests sign() for a valid secretkey
      */
    public static void testSignPos() throws AssertFailException{

        byte[] data = DatatypeConverter.parseHexBinary("CF80CD8AED482D5D1527D7DC72FCEFF84E6326592848447D2DC0B0E87DFC9A90"); //sha256hash of "testing"
        byte[] sec = DatatypeConverter.parseHexBinary("67E56582298859DDAE725F972992A07C6C4FB9F62A8FFF58CE3CA926A1063530");

        byte[] resultArr = NativeSecp256k1.sign(data, sec);
        String sigString = DatatypeConverter.printHexBinary(resultArr);
        assertEquals( sigString, "30440220182A108E1448DC8F1FB467D06A0F3BB8EA0533584CB954EF8DA112F1D60E39A202201C66F36DA211C087F3AF88B50EDF4F9BDAA6CF5FD6817E74DCA34DB12390C6E9" , "testSignPos");
    }

    /**
      * This tests sign() for a invalid secretkey
      */
    public static void testSignNeg() throws AssertFailException{
        byte[] data = DatatypeConverter.parseHexBinary("CF80CD8AED482D5D1527D7DC72FCEFF84E6326592848447D2DC0B0E87DFC9A90"); //sha256hash of "testing"
        byte[] sec = DatatypeConverter.parseHexBinary("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");

        byte[] resultArr = NativeSecp256k1.sign(data, sec);
        String sigString = DatatypeConverter.printHexBinary(resultArr);
        assertEquals( sigString, "" , "testSignNeg");
    }

    /**
      * This tests private key tweak-add
      */
    public static void testPrivKeyTweakAdd() throws AssertFailException {
        byte[] sec = DatatypeConverter.parseHexBinary("67E56582298859DDAE725F972992A07C6C4FB9F62A8FFF58CE3CA926A1063530");
        byte[] data = DatatypeConverter.parseHexBinary("3982F19BEF1615BCCFBB05E321C10E1D4CBA3DF0E841C2E41EEB6016347653C3"); //sha256hash of "tweak"

        byte[] resultArr = NativeSecp256k1.privKeyTweakAdd( sec , data );
        String seckeyString = DatatypeConverter.printHexBinary(resultArr);
        assertEquals( seckeyString , "A168571E189E6F9A7E2D657A4B53AE99B909F7E712D1C23CED28093CD57C88F3" , "testPrivKeyTweakAdd");
    }

    /**
      * This tests private key tweak-mul
      */
    public static void testPrivKeyTweakMul() throws AssertFailException {
        byte[] sec = DatatypeConverter.parseHexBinary("67E56582298859DDAE725F972992A07C6C4FB9F62A8FFF58CE3CA926A1063530");
        byte[] data = DatatypeConverter.parseHexBinary("3982F19BEF1615BCCFBB05E321C10E1D4CBA3DF0E841C2E41EEB6016347653C3"); //sha256hash of "tweak"

        byte[] resultArr = NativeSecp256k1.privKeyTweakMul( sec , data );
        String seckeyString = DatatypeConverter.printHexBinary(resultArr);
        assertEquals( seckeyString , "97F8184235F101550F3C71C927507651BD3F1CDB4A5A33B8986ACF0DEE20FFFC" , "testPrivKeyTweakMul");
    }

    /**
      * This tests public key tweak-add
      */
    public static void testPubKeyTweakAdd() throws AssertFailException {
        byte[] pub = DatatypeConverter.parseHexBinary("040A629506E1B65CD9D2E0BA9C75DF9C4FED0DB16DC9625ED14397F0AFC836FAE595DC53F8B0EFE61E703075BD9B143BAC75EC0E19F82A2208CAEB32BE53414C40");
        byte[] data = DatatypeConverter.parseHexBinary("3982F19BEF1615BCCFBB05E321C10E1D4CBA3DF0E841C2E41EEB6016347653C3"); //sha256hash of "tweak"

        byte[] resultArr = NativeSecp256k1.pubKeyTweakAdd( pub , data, false);
        String pubkeyString = DatatypeConverter.printHexBinary(resultArr);
        byte[] resultArrCompressed = NativeSecp256k1.pubKeyTweakAdd( pub , data, true);
        String pubkeyStringCompressed = DatatypeConverter.printHexBinary(resultArrCompressed);
        assertEquals(pubkeyString , "0411C6790F4B663CCE607BAAE08C43557EDC1A4D11D88DFCB3D841D0C6A941AF525A268E2A863C148555C48FB5FBA368E88718A46E205FABC3DBA2CCFFAB0796EF" , "testPubKeyTweakAdd");
        assertEquals(pubkeyStringCompressed , "0311C6790F4B663CCE607BAAE08C43557EDC1A4D11D88DFCB3D841D0C6A941AF52" , "testPubKeyTweakAdd (compressed)");
    }

    /**
      * This tests public key tweak-mul
      */
    public static void testPubKeyTweakMul() throws AssertFailException {
        byte[] pub = DatatypeConverter.parseHexBinary("040A629506E1B65CD9D2E0BA9C75DF9C4FED0DB16DC9625ED14397F0AFC836FAE595DC53F8B0EFE61E703075BD9B143BAC75EC0E19F82A2208CAEB32BE53414C40");
        byte[] data = DatatypeConverter.parseHexBinary("3982F19BEF1615BCCFBB05E321C10E1D4CBA3DF0E841C2E41EEB6016347653C3"); //sha256hash of "tweak"

        byte[] resultArr = NativeSecp256k1.pubKeyTweakMul( pub , data, false);
        String pubkeyString = DatatypeConverter.printHexBinary(resultArr);
        byte[] resultArrCompressed = NativeSecp256k1.pubKeyTweakMul( pub , data, true);
        String pubkeyStringCompressed = DatatypeConverter.printHexBinary(resultArrCompressed);
        assertEquals(pubkeyString , "04E0FE6FE55EBCA626B98A807F6CAF654139E14E5E3698F01A9A658E21DC1D2791EC060D4F412A794D5370F672BC94B722640B5F76914151CFCA6E712CA48CC589" , "testPubKeyTweakMul");
        assertEquals(pubkeyStringCompressed , "03E0FE6FE55EBCA626B98A807F6CAF654139E14E5E3698F01A9A658E21DC1D2791" , "testPubKeyTweakMul (compressed)");
    }

    /**
      * This tests seed randomization
      */
    public static void testRandomize() throws AssertFailException {
        byte[] seed = DatatypeConverter.parseHexBinary("A441B15FE9A3CF56661190A0B93B9DEC7D04127288CC87250967CF3B52894D11"); //sha256hash of "random"
        boolean result = NativeSecp256k1.randomize(seed);
        assertEquals( result, true, "testRandomize");
    }

    /**
     * Tests that we can decompress valid public keys
     * @throws AssertFailException
     */
    public static void testDecompressPubKey() throws AssertFailException {
        byte[] compressedPubKey = DatatypeConverter.parseHexBinary("0315EAB529E7D5EB637214EA8EC8ECE5DCD45610E8F4B7CC76A35A6FC27F5DD981");

        byte[] result1 = NativeSecp256k1.decompress(compressedPubKey);
        byte[] result2 = NativeSecp256k1.decompress(result1); // this is a no-op
        String resultString1 = DatatypeConverter.printHexBinary(result1);
        String resultString2 = DatatypeConverter.printHexBinary(result2);
        assertEquals(resultString1, "0415EAB529E7D5EB637214EA8EC8ECE5DCD45610E8F4B7CC76A35A6FC27F5DD9817551BE3DF159C83045D9DFAC030A1A31DC9104082DB7719C098E87C1C4A36C19", "testDecompressPubKey (compressed)");
        assertEquals(resultString2, "0415EAB529E7D5EB637214EA8EC8ECE5DCD45610E8F4B7CC76A35A6FC27F5DD9817551BE3DF159C83045D9DFAC030A1A31DC9104082DB7719C098E87C1C4A36C19", "testDecompressPubKey (no-op)");
    }

    /**
     * Tests that we can check validity of public keys
     * @throws AssertFailException
     */
    public static void testIsValidPubKeyPos() throws AssertFailException {
        byte[] pubkey = DatatypeConverter.parseHexBinary("0456b3817434935db42afda0165de529b938cf67c7510168a51b9297b1ca7e4d91ea59c64516373dd2fe6acc79bb762718bc2659fa68d343bdb12d5ef7b9ed002b");
        byte[] compressedPubKey = DatatypeConverter.parseHexBinary("03de961a47a519c5c0fc8e744d1f657f9ea6b9a921d2a3bceb8743e1885f752676");

        boolean result1 = NativeSecp256k1.isValidPubKey(pubkey);
        boolean result2 = NativeSecp256k1.isValidPubKey(compressedPubKey);
        assertEquals(result1, true, "testIsValidPubKeyPos");
        assertEquals(result2, true, "testIsValidPubKeyPos (compressed)");
    }

    public static void testIsValidPubKeyNeg() throws AssertFailException {
        //do we have test vectors some where to test this more thoroughly?
        byte[] pubkey = DatatypeConverter.parseHexBinary("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");

        boolean result1 = NativeSecp256k1.isValidPubKey(pubkey);
        assertEquals(result1, false, "testIsValidPubKeyNeg");
    }

    private static class SchnorrTestVector {
        String data;
        String sig;
        String pubKey;
        boolean expected;
        String comment;

        SchnorrTestVector(String d, String s, String p, boolean e, String c) {
            data = d;
            sig = s;
            pubKey = p;
            expected = e;
            comment = c;
        }
    }

    /**
     * This tests schnorrVerify() for a valid signature
     * It tests the following test vectors
     * https://github.com/sipa/bips/blob/bip-schnorr/bip-schnorr/test-vectors.csv
     */
    public static void testSchnorrVerify() throws AssertFailException{
        SchnorrTestVector[] tests = new SchnorrTestVector[]{
                new SchnorrTestVector(
                        "0000000000000000000000000000000000000000000000000000000000000000",
                        "787A848E71043D280C50470E8E1532B2DD5D20EE912A45DBDD2BD1DFBF187EF67031A98831859DC34DFFEEDDA86831842CCD0079E1F92AF177F7F22CC1DCED05",
                        "0279BE667EF9DCBBAC55A06295CE870B07029BFCDB2DCE28D959F2815B16F81798",
                        true,
                        "success"
                ),
                new SchnorrTestVector(
                        "243F6A8885A308D313198A2E03707344A4093822299F31D0082EFA98EC4E6C89",
                        "2A298DACAE57395A15D0795DDBFD1DCB564DA82B0F269BC70A74F8220429BA1D1E51A22CCEC35599B8F266912281F8365FFC2D035A230434A1A64DC59F7013FD",
                        "02DFF1D77F2A671C5F36183726DB2341BE58FEAE1DA2DECED843240F7B502BA659",
                        true,
                        "success"
                ),
                new SchnorrTestVector(
                        "5E2D58D8B3BCDF1ABADEC7829054F90DDA9805AAB56C77333024B9D0A508B75C",
                        "00DA9B08172A9B6F0466A2DEFD817F2D7AB437E0D253CB5395A963866B3574BE00880371D01766935B92D2AB4CD5C8A2A5837EC57FED7660773A05F0DE142380",
                        "03FAC2114C2FBB091527EB7C64ECB11F8021CB45E8E7809D3C0938E4B8C0E5F84B",
                        true,
                        "success"
                ),
                new SchnorrTestVector(
                        "4DF3C3F68FCC83B27E9D42C90431A72499F17875C81A599B566C9889B9696703",
                        "00000000000000000000003B78CE563F89A0ED9414F5AA28AD0D96D6795F9C6302A8DC32E64E86A333F20EF56EAC9BA30B7246D6D25E22ADB8C6BE1AEB08D49D",
                        "03DEFDEA4CDB677750A420FEE807EACF21EB9898AE79B9768766E4FAA04A2D4A34",
                        true,
                        "success"
                ),
                new SchnorrTestVector(
                        "0000000000000000000000000000000000000000000000000000000000000000",
                        "52818579ACA59767E3291D91B76B637BEF062083284992F2D95F564CA6CB4E3530B1DA849C8E8304ADC0CFE870660334B3CFC18E825EF1DB34CFAE3DFC5D8187",
                        "031B84C5567B126440995D3ED5AABA0565D71E1834604819FF9C17F5E9D5DD078F",
                        true,
                        "success"
                ),
                new SchnorrTestVector(
                        "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF",
                        "570DD4CA83D4E6317B8EE6BAE83467A1BF419D0767122DE409394414B05080DCE9EE5F237CBD108EABAE1E37759AE47F8E4203DA3532EB28DB860F33D62D49BD",
                        "03FAC2114C2FBB091527EB7C64ECB11F8021CB45E8E7809D3C0938E4B8C0E5F84B",
                        true,
                        "success"
                ),
                new SchnorrTestVector(
                        "4DF3C3F68FCC83B27E9D42C90431A72499F17875C81A599B566C9889B9696703",
                        "00000000000000000000003B78CE563F89A0ED9414F5AA28AD0D96D6795F9C6302A8DC32E64E86A333F20EF56EAC9BA30B7246D6D25E22ADB8C6BE1AEB08D49D",
                        "02DFF1D77F2A671C5F36183726DB2341BE58FEAE1DA2DECED843240F7B502BA659",
                        false,
                        "public key not on the curve"
                ),
                new SchnorrTestVector(
                        "243F6A8885A308D313198A2E03707344A4093822299F31D0082EFA98EC4E6C89",
                        "2A298DACAE57395A15D0795DDBFD1DCB564DA82B0F269BC70A74F8220429BA1DFA16AEE06609280A19B67A24E1977E4697712B5FD2943914ECD5F730901B4AB7",
                        "03EEFDEA4CDB677750A420FEE807EACF21EB9898AE79B9768766E4FAA04A2D4A34",
                        false,
                        "incorrect R residuosity"
                ),
                new SchnorrTestVector(
                        "5E2D58D8B3BCDF1ABADEC7829054F90DDA9805AAB56C77333024B9D0A508B75C",
                        "00DA9B08172A9B6F0466A2DEFD817F2D7AB437E0D253CB5395A963866B3574BED092F9D860F1776A1F7412AD8A1EB50DACCC222BC8C0E26B2056DF2F273EFDEC",
                        "03FAC2114C2FBB091527EB7C64ECB11F8021CB45E8E7809D3C0938E4B8C0E5F84B",
                        false,
                        "negated message hash"
                ),
                new SchnorrTestVector(
                        "0000000000000000000000000000000000000000000000000000000000000000",
                        "787A848E71043D280C50470E8E1532B2DD5D20EE912A45DBDD2BD1DFBF187EF68FCE5677CE7A623CB20011225797CE7A8DE1DC6CCD4F754A47DA6C600E59543C",
                        "0279BE667EF9DCBBAC55A06295CE870B07029BFCDB2DCE28D959F2815B16F81798",
                        false,
                        "negated s value"
                ),
                new SchnorrTestVector(
                        "243F6A8885A308D313198A2E03707344A4093822299F31D0082EFA98EC4E6C89",
                        "2A298DACAE57395A15D0795DDBFD1DCB564DA82B0F269BC70A74F8220429BA1D1E51A22CCEC35599B8F266912281F8365FFC2D035A230434A1A64DC59F7013FD",
                        "03DFF1D77F2A671C5F36183726DB2341BE58FEAE1DA2DECED843240F7B502BA659",
                        false,
                        "negated public key"
                ),
                new SchnorrTestVector(
                        "243F6A8885A308D313198A2E03707344A4093822299F31D0082EFA98EC4E6C89",
                        "00000000000000000000000000000000000000000000000000000000000000009E9D01AF988B5CEDCE47221BFA9B222721F3FA408915444A4B489021DB55775F",
                        "02DFF1D77F2A671C5F36183726DB2341BE58FEAE1DA2DECED843240F7B502BA659",
                        false,
                        "sG - eP is infinite. Test fails in single verification if jacobi(y(inf)) is defined as 1 and x(inf) as 0"
                ),
                new SchnorrTestVector(
                        "243F6A8885A308D313198A2E03707344A4093822299F31D0082EFA98EC4E6C89",
                        "0000000000000000000000000000000000000000000000000000000000000001D37DDF0254351836D84B1BD6A795FD5D523048F298C4214D187FE4892947F728",
                        "02DFF1D77F2A671C5F36183726DB2341BE58FEAE1DA2DECED843240F7B502BA659",
                        false,
                        "sG - eP is infinite. Test fails in single verification if jacobi(y(inf)) is defined as 1 and x(inf) as 1"
                ),
                new SchnorrTestVector(
                        "243F6A8885A308D313198A2E03707344A4093822299F31D0082EFA98EC4E6C89",
                        "4A298DACAE57395A15D0795DDBFD1DCB564DA82B0F269BC70A74F8220429BA1D1E51A22CCEC35599B8F266912281F8365FFC2D035A230434A1A64DC59F7013FD",
                        "02DFF1D77F2A671C5F36183726DB2341BE58FEAE1DA2DECED843240F7B502BA659",
                        false,
                        "sig[0:32] is not an X coordinate on the curve"
                ),
                new SchnorrTestVector(
                        "243F6A8885A308D313198A2E03707344A4093822299F31D0082EFA98EC4E6C89",
                        "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFC2F1E51A22CCEC35599B8F266912281F8365FFC2D035A230434A1A64DC59F7013FD",
                        "02DFF1D77F2A671C5F36183726DB2341BE58FEAE1DA2DECED843240F7B502BA659",
                        false,
                        "sig[0:32] is equal to field size"
                ),
                new SchnorrTestVector(
                        "243F6A8885A308D313198A2E03707344A4093822299F31D0082EFA98EC4E6C89",
                        "2A298DACAE57395A15D0795DDBFD1DCB564DA82B0F269BC70A74F8220429BA1DFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141",
                        "02DFF1D77F2A671C5F36183726DB2341BE58FEAE1DA2DECED843240F7B502BA659",
                        false,
                        "sig[32:64] is equal to curve order"
                )
        };
        int i = 0;
        for(SchnorrTestVector test : tests) {
            boolean expected = test.expected;
            byte[] data = DatatypeConverter.parseHexBinary(test.data);
            byte[] sig = DatatypeConverter.parseHexBinary(test.sig);
            byte[] pub = DatatypeConverter.parseHexBinary(test.pubKey);
            boolean result = NativeSecp256k1.schnorrVerify(data, sig, pub);

            String testMsg = String.join(" ", "testSchnorrVerify", String.valueOf(i++), String.valueOf(expected), test.comment);

            assertEquals(result, expected, testMsg);
        }
    }

    /**
     * This tests signSchnorr() for a valid secretkey
     */
    public static void testSchnorrSign() throws AssertFailException{
        byte[] data = DatatypeConverter.parseHexBinary("0000000000000000000000000000000000000000000000000000000000000000");
        byte[] sec = DatatypeConverter.parseHexBinary("0000000000000000000000000000000000000000000000000000000000000001");

        byte[] sig = NativeSecp256k1.schnorrSign(data, sec);
        String sigStr = DatatypeConverter.printHexBinary(sig);

        assertEquals(sigStr, "787A848E71043D280C50470E8E1532B2DD5D20EE912A45DBDD2BD1DFBF187EF67031A98831859DC34DFFEEDDA86831842CCD0079E1F92AF177F7F22CC1DCED05", "testSchnorrSigReal");

        data = DatatypeConverter.parseHexBinary("243F6A8885A308D313198A2E03707344A4093822299F31D0082EFA98EC4E6C89");
        sec = DatatypeConverter.parseHexBinary("B7E151628AED2A6ABF7158809CF4F3C762E7160F38B4DA56A784D9045190CFEF");

        sig = NativeSecp256k1.schnorrSign(data, sec);
        sigStr = DatatypeConverter.printHexBinary(sig);

        assertEquals(sigStr, "2A298DACAE57395A15D0795DDBFD1DCB564DA82B0F269BC70A74F8220429BA1D1E51A22CCEC35599B8F266912281F8365FFC2D035A230434A1A64DC59F7013FD", "testSchnorrSigReal");

        data = DatatypeConverter.parseHexBinary("5E2D58D8B3BCDF1ABADEC7829054F90DDA9805AAB56C77333024B9D0A508B75C");
        sec = DatatypeConverter.parseHexBinary("C90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B14E5C7");

        sig = NativeSecp256k1.schnorrSign(data, sec);
        sigStr = DatatypeConverter.printHexBinary(sig);

        assertEquals(sigStr, "00DA9B08172A9B6F0466A2DEFD817F2D7AB437E0D253CB5395A963866B3574BE00880371D01766935B92D2AB4CD5C8A2A5837EC57FED7660773A05F0DE142380", "testSchnorrSigReal");
    }

    /**
     * This tests signSchnorrWithNonce() against the first test case
     */
    public static void testSchnorrSignWithNonce1() throws AssertFailException{
        byte[] data = DatatypeConverter.parseHexBinary("0000000000000000000000000000000000000000000000000000000000000000");

        byte[] sec = DatatypeConverter.parseHexBinary("0000000000000000000000000000000000000000000000000000000000000001");
        byte[] pubkey = DatatypeConverter.parseHexBinary("0279be667ef9dcbbac55a06295ce870b07029bfcdb2dce28d959f2815b16f81798");

        byte[] nonce = DatatypeConverter.parseHexBinary("0000000000000000000000000000000000000000000000000000000000000002");
        String noncePubKeyHex = "c6047f9441ed7d6d3045406e95c07cd85c778e4b8cef3ca7abac09b95c709ee5";
        byte[] noncePubKey = DatatypeConverter.parseHexBinary(noncePubKeyHex);
        String expectedNoncePubKey = DatatypeConverter.printHexBinary(NativeSecp256k1.schnorrPublicNonce(nonce));

        assertEquals(expectedNoncePubKey.substring(0, 2), "02", "schnorrPublicNonce should produce the correct y coordinate");
        assertEquals(noncePubKeyHex.toUpperCase(), expectedNoncePubKey.substring(2), "schnorrPublicNonce should produce a valid x coordinate");

        byte[] bipSchnorrNonce = DatatypeConverter.parseHexBinary("58e8f2a1f78f0a591feb75aebecaaa81076e4290894b1c445cc32953604db089");

        byte[] sig1 = NativeSecp256k1.schnorrSignWithNonce(data, sec, nonce);
        byte[] sig2 = NativeSecp256k1.schnorrSignWithNonce(data, sec, nonce);
        byte[] sig3 = NativeSecp256k1.schnorrSignWithNonce(data, sec, bipSchnorrNonce);

        String sig1Str = DatatypeConverter.printHexBinary(sig1);
        String sig2Str = DatatypeConverter.printHexBinary(sig2);
        String sig3Str = DatatypeConverter.printHexBinary(sig3);

        assertEquals(sig1Str, sig2Str, sig1Str + " should be " + sig2Str);
        assertNotEquals(sig1Str, sig3Str, sig3Str + " should not be " + sig1Str);

        boolean result = NativeSecp256k1.schnorrVerify(data, sig1, pubkey);
        assertEquals(result, true, "schnorrSignWithNonce should produce a valid signature");

        String noncePubStr = DatatypeConverter.printHexBinary(noncePubKey);

        assertEquals(sig1Str.substring(0, noncePubStr.length()), noncePubStr, noncePubStr + " should prefix " + sig1Str);

        assertEquals(sig3Str, "787A848E71043D280C50470E8E1532B2DD5D20EE912A45DBDD2BD1DFBF187EF67031A98831859DC34DFFEEDDA86831842CCD0079E1F92AF177F7F22CC1DCED05", "schnorrSignWithNonce 1");
    }

    /**
     * This tests signSchnorrWithNonce() against the second test case
     */
    public static void testSchnorrSignWithNonce2() throws AssertFailException {
        byte[] data = DatatypeConverter.parseHexBinary("243F6A8885A308D313198A2E03707344A4093822299F31D0082EFA98EC4E6C89");

        byte[] sec = DatatypeConverter.parseHexBinary("B7E151628AED2A6ABF7158809CF4F3C762E7160F38B4DA56A784D9045190CFEF");
        byte[] pubkey = DatatypeConverter.parseHexBinary("02dff1d77f2a671c5f36183726db2341be58feae1da2deced843240f7b502ba659");

        byte[] nonce = DatatypeConverter.parseHexBinary("67cbfa3d322bbabc3e15510789a7ae670cde7aaccd1d7d966aa51345381c6c6f");
        String noncePubKeyHex = "2c73d693e9dbf1caec8bb2de97e92b971573ef9773ae56ca4c11add9a8182aaf";
        byte[] noncePubKey = DatatypeConverter.parseHexBinary(noncePubKeyHex);
        String expectedNoncePubKey = DatatypeConverter.printHexBinary(NativeSecp256k1.schnorrPublicNonce(nonce));

        assertEquals(expectedNoncePubKey.substring(0, 2), "03", "schnorrPublicNonce should produce the correct y coordinate");
        assertEquals(noncePubKeyHex.toUpperCase(), expectedNoncePubKey.substring(2), "schnorrPublicNonce should produce a valid x coordinate");

        byte[] bipSchnorrNonce = DatatypeConverter.parseHexBinary("921d79a6345d16c7cf9df63620753cf69ef45db8731005cb9a3a89d757e0a5e4");

        byte[] sig1 = NativeSecp256k1.schnorrSignWithNonce(data, sec, nonce);
        byte[] sig2 = NativeSecp256k1.schnorrSignWithNonce(data, sec, nonce);
        byte[] sig3 = NativeSecp256k1.schnorrSignWithNonce(data, sec, bipSchnorrNonce);

        String sig1Str = DatatypeConverter.printHexBinary(sig1);
        String sig2Str = DatatypeConverter.printHexBinary(sig2);
        String sig3Str = DatatypeConverter.printHexBinary(sig3);

        assertEquals(sig1Str, sig2Str, sig1Str + " should be " + sig2Str);
        assertNotEquals(sig1Str, sig3Str, sig3Str + " should not be " + sig1Str);

        boolean result = NativeSecp256k1.schnorrVerify(data, sig1, pubkey);
        assertEquals(result, true, "schnorrSignWithNonce should produce a valid signature");

        String noncePubStr = DatatypeConverter.printHexBinary(noncePubKey);

        assertEquals(sig1Str.substring(0, noncePubStr.length()), noncePubStr, noncePubStr + " should prefix " + sig1Str);

        assertEquals(sig3Str, "2A298DACAE57395A15D0795DDBFD1DCB564DA82B0F269BC70A74F8220429BA1D1E51A22CCEC35599B8F266912281F8365FFC2D035A230434A1A64DC59F7013FD", "schnorrSignWithNonce 2");
    }

    /**
     * This tests signSchnorrWithNonce() against the third test case
     */
    public static void testSchnorrSignWithNonce3() throws AssertFailException {
        byte[] data = DatatypeConverter.parseHexBinary("5E2D58D8B3BCDF1ABADEC7829054F90DDA9805AAB56C77333024B9D0A508B75C");

        byte[] sec = DatatypeConverter.parseHexBinary("C90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B14E5C7");
        byte[] pubkey = DatatypeConverter.parseHexBinary("03fac2114c2fbb091527eb7c64ecb11f8021cb45e8e7809d3c0938e4b8c0e5f84b");

        byte[] nonce = DatatypeConverter.parseHexBinary("d00b4d3404e9cfe04b5cc6d9834e6c03acb6d4cf30d10ee733db996c5cc39eba");
        String noncePubKeyHex = "ef29043b0cb2bc7fe1174f7bbf950c20436dbb835f8d892819b9a1beba48eb0c";
        byte[] noncePubKey = DatatypeConverter.parseHexBinary(noncePubKeyHex);
        String expectedNoncePubKey = DatatypeConverter.printHexBinary(NativeSecp256k1.schnorrPublicNonce(nonce));

        assertEquals(expectedNoncePubKey.substring(0, 2), "03", "schnorrPublicNonce should produce the correct y coordinate");
        assertEquals(noncePubKeyHex.toUpperCase(), expectedNoncePubKey.substring(2), "schnorrPublicNonce should produce a valid x coordinate");

        byte[] bipSchnorrNonce = DatatypeConverter.parseHexBinary("688d7ea518846efebd8372ac6b7a3ed82927d078a4572c65cbc8729002a990b6");

        byte[] sig1 = NativeSecp256k1.schnorrSignWithNonce(data, sec, nonce);
        byte[] sig2 = NativeSecp256k1.schnorrSignWithNonce(data, sec, nonce);
        byte[] sig3 = NativeSecp256k1.schnorrSignWithNonce(data, sec, bipSchnorrNonce);

        String sig1Str = DatatypeConverter.printHexBinary(sig1);
        String sig2Str = DatatypeConverter.printHexBinary(sig2);
        String sig3Str = DatatypeConverter.printHexBinary(sig3);

        assertEquals(sig1Str, sig2Str, sig1Str + " should be " + sig2Str);
        assertNotEquals(sig1Str, sig3Str, sig3Str + " should not be " + sig1Str);

        boolean result = NativeSecp256k1.schnorrVerify(data, sig1, pubkey);
        assertEquals(result, true, "schnorrSignWithNonce should produce a valid signature");

        String noncePubStr = DatatypeConverter.printHexBinary(noncePubKey);

        assertEquals(sig1Str.substring(0, noncePubStr.length()), noncePubStr, noncePubStr + " should prefix " + sig1Str);

        assertEquals(sig3Str, "00DA9B08172A9B6F0466A2DEFD817F2D7AB437E0D253CB5395A963866B3574BE00880371D01766935B92D2AB4CD5C8A2A5837EC57FED7660773A05F0DE142380", "schnorrSignWithNonce 3");
    }

    public static void testCreateECDHSecret() throws AssertFailException{
        byte[] sec = DatatypeConverter.parseHexBinary("67E56582298859DDAE725F972992A07C6C4FB9F62A8FFF58CE3CA926A1063530");
        byte[] pub = DatatypeConverter.parseHexBinary("040A629506E1B65CD9D2E0BA9C75DF9C4FED0DB16DC9625ED14397F0AFC836FAE595DC53F8B0EFE61E703075BD9B143BAC75EC0E19F82A2208CAEB32BE53414C40");

        byte[] resultArr = NativeSecp256k1.createECDHSecret(sec, pub);
        String ecdhString = DatatypeConverter.printHexBinary(resultArr);
        assertEquals( ecdhString, "2A2A67007A926E6594AF3EB564FC74005B37A9C8AEF2033C4552051B5C87F043" , "testCreateECDHSecret");
    }

    public static void main(String[] args) throws AssertFailException{
        
        System.out.println("\n libsecp256k1 enabled: " + Secp256k1Context.isEnabled() + "\n");

        assertEquals( Secp256k1Context.isEnabled(), true, "isEnabled" );

        //Test verify() success/fail
        testVerifyPos();
        testVerifyNeg();

        //Test secKeyVerify() success/fail
        testSecKeyVerifyPos();
        testSecKeyVerifyNeg();

        //Test computePubkey() success/fail
        testPubKeyCreatePos();
        testPubKeyCreateNeg();

        //Test sign() success/fail
        testSignPos();
        testSignNeg();

        //Test privKeyTweakAdd()
        testPrivKeyTweakAdd();

        //Test privKeyTweakMul()
        testPrivKeyTweakMul();

        //Test testPubKeyTweakAdd()
        testPubKeyTweakAdd();

        //Test testPubKeyTweakMul()
        testPubKeyTweakMul();

        // Test parsing public keys
        testDecompressPubKey();
        testIsValidPubKeyPos();
        testIsValidPubKeyNeg();

        //Test randomize()
        testRandomize();

        //Test verifySchnorr() success/fail
        testSchnorrVerify();

        //Test schnorrSign()
        testSchnorrSign();

        //Test schnorrSignWithNonce() against 1st case
        testSchnorrSignWithNonce1();
        //Test schnorrSignWithNonce() against 2nd case
        testSchnorrSignWithNonce2();
        //Test schnorrSignWithNonce() against 3rd case
        testSchnorrSignWithNonce3();

        //Test ECDH
        testCreateECDHSecret();

        NativeSecp256k1.cleanup();

        System.out.println(" All tests passed." );

    }
}
