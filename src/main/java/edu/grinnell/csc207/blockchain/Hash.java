package edu.grinnell.csc207.blockchain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {

    private byte[] data;

    /**
     * Constructs a new Hash object.
     *
     * @param data the byte array representing the hash data
     */
    public Hash(byte[] data) {
        this.data = data;
    }

    /**
     * Returns the byte array of the hash data.
     *
     * @return the byte array of the hash data
     */
    public byte[] getData() {
        return this.data;
    }

    /**
     * Returns whether the hash is valid.
     *
     * @return the resulting value of boolean `valid`
     */
    public boolean isValid() {
        boolean valid = false;
        if (data[0] == 0 && data[1] == 0 && data[2] == 0) {
            valid = true;
        }
        return valid;
    }

    /**
     * Returns the string representation of the hash as a string of hexadecimal
     * digits (2 digits per byte).
     *
     * @return the string representation of the hash
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            sb.append(String.format("%02x", Byte.toUnsignedInt(b)));
        }
        return sb.toString();
    }

    

    public boolean equals(Object other) {
        if (other instanceof Hash) {
            Hash otherHash = (Hash) other;
            return this.toString().equals(otherHash.toString());
        }
        return false;
    }

    public static byte[] calculateHash(String msg) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("sha-256");

    }

}
