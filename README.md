# Project: Block Chain

Authors: Abby Ressner

## Revision Log

"Most of your functions are missing javadoc comments, which is a major style error! please run mvn checkstyle:check to see your errors and to correct them!  For your append function, the program should not crash if an invalid amount is transferred between Alice and Bob. In this case, the check function should say that the chain is invalid, but the append should still add it to the chain (refer to example execution 2 on the project instructions webpage)."

### Changes

* added Javadoc comments to all methods in BlockChain.java, Block.java, and Hash.java
* fixed `append()`
* resolved all checkstyle violations

## Resources

* Java 21.0.6
* Apache Maven 3.9.9
* Visual Studio Code
* [Project 5 instuctions page](https://osera.cs.grinnell.edu/ttap/data-structures-labs/block-chain.html)
* [List Implementation Lab](https://osera.cs.grinnell.edu/ttap/data-structures-labs/list-implementation.html#list-implementation) - for help implementing the BlockChain class
* [MessageDigest docs](https://docs.oracle.com/javase/8/docs/api/java/security/MessageDigest.html)
* [Byte docs](https://docs.oracle.com/javase/8/docs/api/java/lang/Byte.html#toUnsignedInt-byte-)
* [String docs](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html#format-java.lang.String-java.lang.Object...-)