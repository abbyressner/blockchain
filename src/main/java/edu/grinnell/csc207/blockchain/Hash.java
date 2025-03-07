package edu.grinnell.csc207.blockchain;

public class Hash {

    private byte[] data;

    public Hash(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return this.data;
    }

    public boolean isValid() {
        boolean valid = false;
        if (data[0] == 0 && data[1] == 0 && data[2] == 0) {
            valid = true;
        }
        return valid;
    }

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
        } else {
            return false;
        }
    }
}
