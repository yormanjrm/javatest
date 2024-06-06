package com.test.java.infrastructure.adapter;

import com.test.java.domain.ports.IEncryptRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

@Repository
@Slf4j
public class EncryptRepository implements IEncryptRepository {


    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding"; // Constante para especificar la transformación del cifrado
    private static final String SECRET_KEY = "1234567890123456"; // Example key, use a secure key in production

    @Override
    public String encrypt(String input) {
        try {
            // Generar un IV (vector de inicialización) aleatorio de 16 bytes
            byte[] ivBytes = new byte[16];
            SecureRandom random = new SecureRandom();
            random.nextBytes(ivBytes);
            IvParameterSpec iv = new IvParameterSpec(ivBytes);

            // Crear una clave secreta utilizando la clave especificada y el algoritmo AES
            SecretKeySpec skeySpec = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "AES");

            // Obtener una instancia del cifrador con la transformación especificada
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            // Inicializar el cifrador en modo ENCRYPT_MODE con la clave y el IV
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            // Cifrar el texto de entrada
            byte[] encrypted = cipher.doFinal(input.getBytes("UTF-8"));

            // Combinar el IV y el texto cifrado en un solo arreglo de bytes
            byte[] encryptedIvAndText = new byte[ivBytes.length + encrypted.length];
            // Copiar el IV al principio del arreglo combinado
            System.arraycopy(ivBytes, 0, encryptedIvAndText, 0, ivBytes.length);
            // Copiar el texto cifrado después del IV en el arreglo combinado
            System.arraycopy(encrypted, 0, encryptedIvAndText, ivBytes.length, encrypted.length);

            // Codificar el arreglo combinado en Base64 y devolverlo como una cadena
            return Base64.getEncoder().encodeToString(encryptedIvAndText);
        } catch (Exception ex) {
            // Registrar cualquier error que ocurra durante el cifrado
            log.error("Error while encrypting", ex);
            // Lanzar una excepción en caso de error
            throw new RuntimeException("Error while encrypting", ex);
        }
    }
}