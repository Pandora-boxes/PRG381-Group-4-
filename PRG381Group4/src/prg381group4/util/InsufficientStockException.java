package prg381group4.util;

/**
 * Thrown when someone tries to issue more of a material than is in stock.
 * Extends ValidationException so a form can catch either one with a single
 * catch block -- polymorphism in the exception hierarchy.
 */
public class InsufficientStockException extends ValidationException {

    public InsufficientStockException(String materialName, int requested, int available) {
        super("Cannot issue " + requested + " of '" + materialName
                + "'. Only " + available + " in stock.");
    }
}
