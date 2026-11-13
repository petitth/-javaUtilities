import java.util.UUID;

/**
 * compile from command lines :
 * 1) compile (and create GenerateId.class) : javac GenerateId.java
 * 2) launch it : java GenerateId
 */
public class GenerateId {
    public static void main(String[] args) {
        System.out.println("-- begin --");
        System.out.println("id = '" + UUID.randomUUID() + "'");
        System.out.println("-- end --");
    }
}