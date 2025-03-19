package com.es2.passwords;

public interface PassGerador {
    String PalavraPasse_gerada();
}
// todas as classes que impletemn esta vao ter que usar obrigatoriamente um metodo PalavraPasse_gerada
//ou seja sempre que usamos o Pass gerador na nova calsse ,esta classe vai ter um metodo public String PalavraPasse_gerada()
// e assim podemos defenir vairos metodus de geraar passes

//e é aqui que é usado o Strategy Pattern