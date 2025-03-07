package edu.grinnell.csc207.blockchain;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * The main driver for the block chain program.
 */
public class BlockChainDriver {

    /**
     * The main entry point for the program.
     * @param args the command-line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        if (args.length != 1 || Integer.parseInt(args[0]) < 0) {
            System.out.println("Usage: java BlockChainDriver <amount>");
            System.exit(0);
        }
        BlockChain bc = new BlockChain(Integer.parseInt(args[0]));
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.print(bc.toString());
            System.out.print("Command? ");
            String command = scan.next();
            if (command.equals("mine")) {
                System.out.print("Amount transferred? ");
                int amount = scan.nextInt();
                Block blk = bc.mine(amount);
                System.out.println("amount = " + amount + ", nonce = " + blk.getNonce());
            } else if (command.equals("append")) {
                System.out.print("Amount transferred? ");
                int amount = scan.nextInt();
                System.out.print("Nonce? ");
                long nonce = scan.nextLong();
                Block blk = new Block(bc.getSize(), amount, bc.getHash(), nonce);
                bc.append(blk);
            } else if (command.equals("remove")) {
                if (!bc.removeLast()) {
                    System.out.println("Cannot remove the initial block.");
                }
            } else if (command.equals("check")) {
                if (bc.isValidBlockChain()) {
                    System.out.println("Chain is valid!");
                } else {
                    System.out.println("Chain is invalid!");
                }
            } else if (command.equals("report")) {
                bc.printBalances();
            } else if (command.equals("help")) {
                System.out.println("Valid commands: ");
                System.out.println("\tmine: discovers the nonce for a given transaction ");
                System.out.println("\tappend: appends a new block onto the end of the chain ");
                System.out.println("\tremove: removes the last block from the end of the chain ");
                System.out.println("\tcheck: checks that the block chain is valid ");
                System.out.println("\treport: reports the balances of Alice and Bob ");
                System.out.println("\thelp: prints this list of commands ");
                System.out.println("\tquit: quits the program ");
            } else if (command.equals("quit")) {
                break;
            } else {
                System.out.println("Invalid input. Please use help command to find valid inputs.");
            }
            System.out.println();
        }
        scan.close();
    }
}