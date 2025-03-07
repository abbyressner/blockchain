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

    private Node head;
    private Node tail;
    private int size;

    public BlockChain(int initial) throws NoSuchAlgorithmException {
        Block initialBlock = new Block(0, initial, null);
        head = new Node(initialBlock);
        tail = head;
        size = 1;
    }

    public Block mine(int amount) throws NoSuchAlgorithmException {
        Block newBlock = new Block(size, amount, tail.block.getHash());
        System.out.println("amount = " + amount + ", nonce = " + newBlock.getNonce());
        return newBlock;
    }

    public void append(Block blk) {
        if (blk.getNum() != size || !blk.getPrevHash().equals(tail.block.getHash())) {
            throw new IllegalArgumentException();
        }
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
        int amount = blk.getAmount();
        if (amount < 0) {
            int n = -amount;
            if (alice < n) {
                throw new IllegalArgumentException();
            }
            alice += amount;
            bob -= amount;
        } else if (amount > 0) {
            if (bob < amount) {
                throw new IllegalArgumentException();
            }
            bob -= amount;
            alice += amount;
        }
        Node newNode = new Node(blk);
        tail.next = newNode;
        tail = newNode;
        size++;
    }

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

    public Hash getHash() {
        return tail.block.getHash();
    }

    public int getSize() {
        return size;
    }

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
                if (!currentBlock.getPrevHash().equals(prev.block.getHash())) {
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
