package epi.string;

public class RobinKarp {
    static final int MULT = 997;
    static int stringHash(String s, int modulus){
        return s.chars().reduce(0, (val, c) -> (val * MULT + c) % modulus);
    }
}
