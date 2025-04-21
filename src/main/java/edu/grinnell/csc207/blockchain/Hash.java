package edu.grinnell.csc207.blockchain;

/**
 * A class representing a hash in the blockchain.
 */
public class Hash {

    private byte[] data;

    /**
     * Creates a new Hash object.
     *
     * @param data the byte array representing the hash
     */
    public Hash(byte[] data) {
        this.data = data;
    }

    /**
     * Returns the byte array representing the hash.
     * 
     * @return the byte array representing the hash
     */
    public byte[] getData() {
        return this.data;
    }

    /**
     * Checks if the hash is valid.
     * 
     * @return true if the hash is valid, false otherwise
     */
    public boolean isValid() {
        boolean valid = false;
        if (data[0] == 0 && data[1] == 0 && data[2] == 0) {
            valid = true;
        }
        return valid;
    }

    /**
     * Returns a string representation of the hash.
     * 
     * @return a string representation of the hash
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            sb.append(String.format("%02x", Byte.toUnsignedInt(b)));
        }
        return sb.toString();
    }

    /**
     * Compares this hash to another object for equality.
     * 
     * @param other the object to compare to
     * @return true if the hashes are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Hash) {
            Hash otherHash = (Hash) other;
            return this.toString().equals(otherHash.toString());
        } else {
            return false;
        }
    }
}
