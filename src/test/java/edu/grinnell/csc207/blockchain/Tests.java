package edu.grinnell.csc207.blockchain;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class Tests {

    @Test
    public void testInitialChain() throws NoSuchAlgorithmException {
        BlockChain bc = new BlockChain(100);
        assertEquals(1, bc.getSize());
        assertTrue(bc.isValidBlockChain());
    }

    @Test
    public void testOverdraftInvalidChain() throws NoSuchAlgorithmException {
        BlockChain bc = new BlockChain(100);
        Block overdraft = bc.mine(-150);
        assertDoesNotThrow(() -> bc.append(overdraft));
        assertFalse(bc.isValidBlockChain());
    }

    @Test
    public void testRemoveLast() throws NoSuchAlgorithmException {
        BlockChain bc = new BlockChain(100);
        assertFalse(bc.removeLast());
        Block b = bc.mine(20);
        bc.append(b);
        assertTrue(bc.removeLast());
        assertEquals(1, bc.getSize());
        assertTrue(bc.isValidBlockChain());
    }
}
