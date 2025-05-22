package com.es2.passwords;
import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.Queue;
public class ObjectPool_SecureRamdom {
    private static final int tamanho_do_pool = 5; //tamanho do pool
    private final Queue<SecureRandom> pool = new LinkedList<>();

    public ObjectPool_SecureRamdom() {
        for (int i = 0; i < tamanho_do_pool; i++) {
            pool.offer(new SecureRandom());
        }
    }

    // chama o secure da pool
    public synchronized SecureRandom obtem_secure() {
        return pool.isEmpty() ? new SecureRandom() : pool.poll();
    }

    // devolve o secure a pool
    public synchronized void devolve_secure(SecureRandom random) {
        pool.offer(random);
    }
}
