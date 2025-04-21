package edu.grinnell.csc207.blockchain;

import java.security.NoSuchAlgorithmException;

/**
 * A linked list of hash-consistent blocks representing a ledger of monetary
 * transactions.
 */
public class BlockChain {

    private static class Node {

        Block block;

        Node next;

        public Node(Block block) {
            this.block = block;
            this.next = null;
        }
    }

    private final Node head;

    private Node tail;

    private int size;

    /**
     * Creates a new BlockChain.
     *
     * @param initial the initial amount in the first block
     * @throws NoSuchAlgorithmException if the hashing algorithm is not found
     */
    public BlockChain(int initial) throws NoSuchAlgorithmException {
        Block initialBlock = new Block(0, initial, null);
        head = new Node(initialBlock);
        tail = head;
        size = 1;
    }

    /**
     * Mines for a new Block.
     * 
     * @param amount the amount to be mined
     * @return the new Block
     * @throws NoSuchAlgorithmException if the hashing algorithm is not found
    **/
    public Block mine(int amount) throws NoSuchAlgorithmException {
        Block newBlock = new Block(size, amount, tail.block.getHash());
        System.out.println("amount = " + amount + ", nonce = " + newBlock.getNonce());
        return newBlock;
    }

    /**
     * Appends a new Block to the BlockChain.
     *
     * @param blk the Block to be appended
     * @throws IllegalArgumentException if the Block is not valid
     */
    public void append(Block blk) {
        if (blk.getNum() != size || !blk.getPrevHash().equals(tail.block.getHash())) {
            throw new IllegalArgumentException();
        }
        Node newNode = new Node(blk);
        tail.next = newNode;
        tail = newNode;
        size++;
    }

    /**
     * Removes the last Block from the BlockChain.
     *
     * @return true if the Block was removed, false if it was the initial block
     */
    public boolean removeLast() {
        if (size == 1) {
            return false;
        }
        Node current = head;
        while (current.next != tail) {
            current = current.next;
        }
        current.next = null;
        tail = current;
        size--;
        return true;
    }

    /**
     * Returns the hash of the last Block in the BlockChain.
     *
     * @return the hash of the last Block
     */
    public Hash getHash() {
        return tail.block.getHash();
    }

    /**
     * Returns the size of the BlockChain.
     *
     * @return the size of the BlockChain
     */
    public int getSize() {
        return size;
    }

    /**
     * Checks if the BlockChain is valid.
     * 
     * @return true if the BlockChain is valid, false otherwise
     */
    public boolean isValidBlockChain() {
        int alice = 0;
        int bob = 0;
        Node current = head;
        Node prev = null;
        int index = 0;
        while (current != null) {
            Block currentBlock = current.block;
            if (index == 0) {
                alice = currentBlock.getAmount();
                bob = 0;
            } else {
                if (prev != null && !currentBlock.getPrevHash().equals(prev.block.getHash())) {
                    return false;
                }
                int amount = currentBlock.getAmount();
                if (amount < 0) {
                    int n = -amount;
                    if (alice < n) {
                        return false;
                    }
                    alice += amount;
                    bob -= amount;
                } else if (amount > 0) {
                    if (bob < amount) {
                        return false;
                    }
                    bob -= amount;
                    alice += amount;
                }
            }
            prev = current;
            index++;
            current = current.next;
        }
        return true;
    }

    /**
     * Prints the balances of Alice and Bob.
     */
    public void printBalances() {
        int alice = 0;
        int bob = 0;
        Node current = head;
        int index = 0;
        while (current != null) {
            Block currentBlock = current.block;
            if (index == 0) {
                alice = currentBlock.getAmount();
                bob = 0;
            } else {
                int amount = currentBlock.getAmount();
                if (amount < 0) {
                    alice += amount;
                    bob -= amount;
                } else if (amount > 0) {
                    bob -= amount;
                    alice += amount;
                }
            }
            index++;
            current = current.next;
        }
        System.out.println("Alice: " + alice + ", Bob: " + bob);
    }


    /**
     * Returns a string representation of the BlockChain.
     *
     * @return a string representation of the BlockChain
     */
    @Override
    public String toString() {
        Node current = head;
        String s = "";
        while (current != null) {
            s = s + "\n" + current.block.toString();
            current = current.next;
        }
        return s;
    }
}
