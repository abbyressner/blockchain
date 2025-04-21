package edu.grinnell.csc207.blockchain;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A class representing a block in the blockchain.
 */
public class Block {

    private final int num;
    private final int amount;
    private final Hash prevHash;
    private final long nonce;
    private final Hash hash;

    /**
     * Creates a new Block.
     *
     * @param num the block number
     * @param amount the amount of money transferred
     * @param prevHash the hash of the previous block
     * @throws NoSuchAlgorithmException if the hashing algorithm is not found
     */
    public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
        this.num = num;
        this.amount = amount;
        this.prevHash = prevHash;
        this.nonce = miner();
        this.hash = calculateHash();
    }

    /**
     * Creates a new Block with a specified nonce.
     *
     * @param num the block number
     * @param amount the amount of money transferred
     * @param prevHash the hash of the previous block
     * @param nonce the nonce for this block
     * @throws NoSuchAlgorithmException if the hashing algorithm is not found
     */
    public Block(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
        this.num = num;
        this.amount = amount;
        this.prevHash = prevHash;
        this.nonce = nonce;
        this.hash = calculateHash();
    }

    /**
     * Mines for a nonce.
     *
     * @return the nonce that produces a valid hash
     * @throws NoSuchAlgorithmException if the hashing algorithm is not found
     */
    public final long miner() throws NoSuchAlgorithmException {
        long calculatedNonce = 0;
        Hash newHash = calculateNoNonceHash(calculatedNonce);
        while (!newHash.isValid()) {
            calculatedNonce++;
            newHash = calculateNoNonceHash(calculatedNonce);
        }
        return calculatedNonce;
    }

    /**
     * Calculates the hash for this block.
     *
     * @return the hash of this block
     * @throws NoSuchAlgorithmException if the hashing algorithm is not found
     */
    public final Hash calculateHash() throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("sha-256");
        ByteBuffer b = 
            ByteBuffer.allocate(64).putInt(this.num).putInt(this.amount).putLong(this.nonce);
        if (this.prevHash != null) {
            b.put(this.prevHash.getData());
        }
        md.update(b.array());
        byte[] hashBytes = md.digest();
        return new Hash(hashBytes);
    }

    /**
     * Calculates the hash without a nonce.
     *
     * @param nonce the nonce to use for the hash calculation
     * @return the hash of this block
     * @throws NoSuchAlgorithmException if the hashing algorithm is not found
     */
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

    /**
     * Returns the block number.
     *
     * @return the block number
     */
    public int getNum() {
        return this.num;
    }

    /**
     * Returns the amount of money transferred.
     *
     * @return the amount of money transferred
     */
    public int getAmount() {
        return this.amount;
    }

    /**
     * Returns the nonce for this block.
     *
     * @return the nonce for this block
     */
    public long getNonce() {
        return this.nonce;
    }

    /**
     * Returns the hash of this block.
     *
     * @return the hash of this block
     */
    public Hash getHash() {
        return this.hash;
    }

    /**
     * Returns the hash of the previous block.
     *
     * @return the hash of the previous block
     */
    public Hash getPrevHash() {
        return this.prevHash;
    }

    /**
     * Returns a string representation of this block.
     */
    @Override
    public String toString() {
        return "Block " + this.num + " (Amount: " + this.amount + ", Nonce: " + this.nonce
                + ", prevHash: " + (this.prevHash == null ? "null" : this.prevHash)
                + ", hash: " + this.hash + ")";
    }
}
