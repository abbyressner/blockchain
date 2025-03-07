package edu.grinnell.csc207.blockchain;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block {

    private int num;
    private int amount;
    private Hash prevHash;
    private long nonce;
    private Hash hash;

    public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
        this.num = num;
        this.amount = amount;
        this.prevHash = prevHash;
        this.nonce = miner();
        this.hash = calculateHash();
    }

    public Block(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
        this.num = num;
        this.amount = amount;
        this.prevHash = prevHash;
        this.nonce = nonce;
        this.hash = calculateHash();
    }

    public long miner() throws NoSuchAlgorithmException {
        long calculatedNonce = 0;
        Hash newHash = calculateNoNonceHash(calculatedNonce);
        while (!newHash.isValid()) {
            calculatedNonce++;
            newHash = calculateNoNonceHash(calculatedNonce);
        }
        return calculatedNonce;
    }

    public Hash calculateHash() throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("sha-256");
        ByteBuffer b = ByteBuffer.allocate(64).putInt(this.num).putInt(this.amount).putLong(this.nonce);
        if (this.prevHash != null) {
            b.put(this.prevHash.getData());
        }
        md.update(b.array());
        byte[] hashBytes = md.digest();
        return new Hash(hashBytes);
    }

    public Hash calculateNoNonceHash(long nonce) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("sha-256");
        ByteBuffer b = ByteBuffer.allocate(64).putInt(this.num).putInt(this.amount).putLong(nonce);
        if (this.prevHash != null) {
            b.put(this.prevHash.getData());
        }
        md.update(b.array());
        byte[] hashBytes = md.digest();
        return new Hash(hashBytes);
    }

    public int getNum() {
        return this.num;
    }

    public int getAmount() {
        return this.amount;
    }

    public long getNonce() {
        return this.nonce;
    }

    public Hash getHash() {
        return this.hash;
    }

    public Hash getPrevHash() {
        return this.prevHash;
    }

    @Override
    public String toString() {
        return "Block " + this.num + " (Amount: " + this.amount + ", Nonce: " + this.nonce
                + ", prevHash: " + (this.prevHash == null ? "null" : this.prevHash)
                + ", hash: " + this.hash + ")";
    }
}
