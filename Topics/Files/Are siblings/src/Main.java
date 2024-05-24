import java.io.File;

class Siblings {

    public static boolean areSiblings(File f1, File f2) {
        // implement me
        if (f1.getParent().equals(f2.getParent())){
            return true;
        }
        return false;
    }
}